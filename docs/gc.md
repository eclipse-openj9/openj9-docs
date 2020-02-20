<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution and is available at
* https://www.eclipse.org/legal/epl-2.0/ or the Apache
* License, Version 2.0 which accompanies this distribution and
* is available at https://www.apache.org/licenses/LICENSE-2.0.
*
* This Source Code may also be made available under the
* following Secondary Licenses when the conditions for such
* availability set forth in the Eclipse Public License, v. 2.0
* are satisfied: GNU General Public License, version 2 with
* the GNU Classpath Exception [1] and GNU General Public
* License, version 2 with the OpenJDK Assembly Exception [2].
*
* [1] https://www.gnu.org/software/classpath/license.html
* [2] http://openjdk.java.net/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->


# Garbage collection

The process of managing memory in the VM is handled by the Allocator and the Garbage Collector (GC). These components operate on
an area of memory that is reserved for VM processing called the Java heap. The Allocator assigns areas of the Java heap
for Java objects. Objects are considered as *live* when they have a chain of references to them that start from root references,
such as those found in thread stacks. When that reference or pointer no longer exists, the objects are considered as *garbage*. The role of the Garbage Collector is to manage the storage in the Java heap and reclaim memory by removing garbage.

## The Allocator

The Allocator is a component of memory management that is typically overshadowed by the task of garbage collection. However,
it is a critical, if not small part, of the overall process. The Allocator manages pools of free memory and how the free memory
is consumed. It is also responsible for allocating areas of storage in the Java heap for objects at the request of applications,
class libraries, or the VM. Every allocation requires a *heap lock* to stop concurrent threads trying to access the same area of memory at the same time. When an object is allocated, the heap lock is released. If there is insufficient space to allocate the object, allocation fails, the heap lock is released, and the GC is called. If the GC manages to recover some space on the heap, the Allocator can resume operations. If the GC does not
recover enough space, it returns an `OutOfMemoryError` exception.

Acquiring a heap lock for every allocation would be an intensive operation with a knock on impact to performance. To get around this
problem, small objects are allocated to thread local heaps (TLH).

### Thread local heaps (TLH)

To improve performance, allocation caches are reserved in the heap for different threads. These allocation caches are known as
thread local heaps (TLH) and allow each thread to allocate memory from its cache without acquiring the heap lock. A TLH is typically
used for small objects of less than 512 bytes (768 bytes on 64-bit VMs) although larger objects can be allocated from the cache if
there is sufficient space.

If a thread allocates a lot of objects, the allocator gives that thread a larger TLH to reduce contention on the heap lock.

A TLH is predefined with an initial default size of 2 KB. On every TLH refresh, the requested size for that thread
is increased by an increment (default 4 KB). The requested size can grow up to a predefined maximum (default 128 KB).

After every GC cycle, the TLH requested size for each thread is reduced, sometimes by as much as 50%, to take account of threads that
reduce their allocation rate and no longer need large TLHs.

For very inactive threads, the requested size can even drop below the initial value, down to the predefined minimum (512/768 bytes).
For very active threads, the maximum TLH requested size might be reached before the next GC occurs.

Larger TLHs can help reduce heap lock contention, but might also reduce heap utilisation and increase heap fragmentation.

The following options control the requested TLH size:

- [`-Xgc:tlhMaximumSize=<bytes>`](xgc.md#tlhmaximumsize)
- [`-Xgc:tlhInitialSize=<bytes>`](xgc.md#tlhinitialsize)
- [`-Xgc:tlhIncrementSize=<bytes>`](xgc.md#tlhincrementsize)

Typically, when the maximum TLH size is increased, you should also increase the increment proportionally, so that active threads can
reach the maximum requested TLH size more quickly.

## Garbage collection

To prevent applications running out of memory, objects in the Java heap that are no longer required must be reclaimed. This process is known as garbage collection (GC). When garbage is collected, the garbage collector must obtain exclusive access to the heap, which causes an application to pause while the clean up is done. This pause is often referred to as a *stop-the-world* pause because an application must halt until the process completes. In general, the first step in the GC process is to mark the objects that are reachable, which means they are still in use. The next step is to sweep away the unmarked objects to reclaim memory. The last step, which isn't always required unless the heap has become very fragmented, is to compact the heap.

Eclipse OpenJ9 has a number of GC policies designed around different types of applications and workloads. Picking the right policy very much depends on your usage and performance goals.

### Generational Concurrent policy

If you have a transactional application, with many short lived objects, the Generational Concurrent GC policy ([`-Xgcpolicy:gencon`](xgcpolicy.md#gencon)) is probably best suited, which aims to minimize GC pause times without compromising throughput. This is the default policy employed by the VM, so if you want to use it you don't need to specify it on the command line when you start your application.

With the `gencon` policy, the Java heap is divided into two main areas, the *nursery* area, where new objects are created and the *tenure* area, where objects are moved if they have reached *tenure age*.

The *nursery* area is subdivided into two further areas, the *allocate* space and the *survivor* space. The GC process is illustrated in the following diagram, which shows a sequence of 4 main events:

![The diagram is explained in the surrounding text](./cr/gencon_java_heap.jpg "Object allocation")

1. Objects are created in the *allocate* space.
2. The *allocate* space is full.
3. A local GC scavenge process runs and reachable objects are either copied into the *survivor* space or into the *tenure* area if they have reached *tenure age*. Any objects that can't be reached are left untouched and subsequently cleared.
4. The *allocate* and *survivor* spaces swap roles. The original *survivor* space becomes the *allocate* space where new objects are created, and the original *allocate* space becomes the *survivor* space ready for the next local GC scavenge process.

The relative sizes of the *allocate* and *survivor* spaces are dynamically adjusted by a technique called *tilting*. When the *nursery* area is first created, it is evenly divided between the *allocate* and *survivor* spaces. If, after a GC scavenge process is run, the amount of space required for the *survivor* area is comparatively small, the boundary between the two spaces is adjusted by *tilting*. For example, if the *survivor* space requires only 10% of the *nursery* area, the *tilt ratio* is adjusted to give 90% of the *nursery* area to the *allocate* space. With more space available for new objects, garbage collection can be delayed.

The *tenure age* of an object is determined by the VM and reflects the number of times that an object has been copied between the *allocate* space and the *survivor* space. The age is in the range 1 - 14 and is adjusted dynamically by the VM depending on the overall amount of space that is used in the *nursery area*. For example, if an object has a tenure age of 5, it has been copied backwards and forwards between *allocate* and *survivor* spaces 5 times. If the VM sets a *tenure age* of 5 based on the percentage of space remaining in the *nursery* area, the next scavenge moves the object from the *nursery* to the *tenure* area. You can set an initial *tenure age* with the [`-Xgc:scvTenureAge`](xgc.md#scvtenureage) option. You can also prevent the VM dynamically adjusting the *tenure age* by setting the [`Xgc:scvNoAdaptiveTenure`](xgc.md#scvnoadaptivetenure) option so that the intial age is maintained throughout the run time of the VM.

Within the *tenure* area, new objects are allocated into the small object area (SOA), which is illustrated in the earlier diagram (see 3.). A large object area (LOA) is set aside for objects greater than 64 KB that cannot be allocated into the SOA to minimize fragmentation. The LOA is allocated by default but is reduced and removed after a few GC cycles if it isn't populated. To prevent the creation of an LOA, you can specify the [`-Xnoloa`](xloa.md) option on the command line when you start your application. When the *tenure* area is close to full a global GC is triggered.

The local GC scavenge reduces pause times by freqently reclaiming memory in the *nursery* area which, for a transactional application with many short-lived objects, has the most recyclable space. However, over time the *tenure* area might become full. So, whilst a local GC scavenge process is operating on the *nursery* area, a concurrent global GC process also runs alongside normal program execution to mark and remove unreachable objects from the *tenure* area. These two GC approaches combine to provide a good trade-off between shorter pause times and consistent throughput.

A special mode of the `gencon` policy is known as *Concurrent Scavenge* (`-Xgc:concurrentScavenge`), which aims to minimize the time spent in stop-the-world pauses by collecting nursery garbage in parallel with running application threads. This mode can be enabled with hardware-based support and software-based support.

- **Hardware-based support: (Linux on IBM Z&reg; and z/OS&reg;)** This mode works on the IBM z14™ and later mainframe system with the Guarded Storage (GS) Facility. The GS Facility provides hardware-based support to detect when potentially stale references to objects are accessed by an application. This means that the garbage collector can start processing objects in parts of the heap without halting an application because the GS Facility is on hand to spot accesses to an object and send a notification. The object that was ready to be swept away can be moved, and references to it can be reset. You can read more about this mode in the following blog posts:

    - [Reducing Garbage Collection pause times with Concurrent Scavenge and the Guarded Storage Facility](https://developer.ibm.com/javasdk/2017/09/18/reducing-garbage-collection-pause-times-concurrent-scavenge-guarded-storage-facility/)
    - [How Concurrent Scavenge using the Guarded Storage Facility Works](https://developer.ibm.com/javasdk/2017/09/25/concurrent-scavenge-using-guarded-storage-facility-works/)

- **Software-based support: (64-bit: Linux on (x86-64, POWER, IBM Z&reg;), AIX&reg;, macOS&reg;, and z/OS&reg;)** With software-based support, *Concurrent Scavenge* can be enabled without any pre-requisite hardware although the performance throughput is not as good as hardware-based support.

For more information about enabling Concurrent Scavenge, see the [-Xgc:concurrentScavenge](xgc.md#concurrentscavenge) option.

### Balanced policy

The Balanced GC policy ([`-Xgcpolicy:balanced`](xgcpolicy.md#balanced)) evens out pause times and reduces the overhead of some of the costlier operations typically associated with garbage collection. It divides the Java heap into regions, which are managed individually to reduce the maximum pause time on large heaps and increase the efficiency of garbage collection. The aim of the policy is to avoid global garbage collections by matching object allocation and survival rates. 

If you have problems with application pause times that are caused by global garbage collections, particularly compactions, this policy might improve application performance. If you are using large systems that have Non-Uniform Memory Architecture (NUMA) characteristics (x86 and POWER&trade; platforms only), the Balanced policy might further improve application throughput.

The Balanced GC policy evens out pause times and reduces the overhead of some of the costlier operations typically associated with garbage collection. It divides the Java heap into regions, which are managed individually to reduce the maximum pause time on large heaps and increase the efficiency of garbage collection. The aim of the policy is to avoid global collections by matching object allocation and survival rates. However, even though pause times are typically evened out across GC operations, actual pause times are affected by object allocation rates, object survival rates, and fragmentation levels within the heap, and cannot therefore be bound to a certain maximum nor can a certain utilization level be guaranteed. 

During VM startup, the GC divides the heap memory into regions of equal size. These regions remain static for the lifetime of the VM and are the basic unit of garbage collection and allocation operations. For example, when the heap is expanded or contracted, the memory committed or released corresponds to a certain number of regions. Although the Java heap is a contiguous range of memory addresses, any region within that range can be committed or released as required. This enables the Balanced GC to contract the heap more dynamically and aggressively than other garbage collectors, which typically require the committed portion of the heap to be contiguous.

Regions impose a maximum object size. Objects are always allocated within the bounds of a single region and are never permitted to span regions. The region size is always a power of two; for example, 512 KB, 1 MB, and so on (where KB is 2<sup>10</sup> bytes and MB is 2<sup>20</sup> bytes). The region size is selected at startup based on the maximum heap size. The collector chooses the smallest power of two which will result in less than 2048 regions, with a minimum region size of 512 KB. Except for small heaps (less than about 512 MB) the VM aims to have between 1024 and 2047 regions. 

![The diagram is explained in the surrounding text](./cr/balanced_gc_regions.png "Region structure and characteristics found in the object heap")


#### Arraylets: dealing with large arrays

Most objects are easily contained within the minimum region size of 512 KB. However, some large arrays might require more memory than is available in a single region. To support such arrays, the Balanced GC uses an **arraylet** representation to more effectively store large arrays in the heap. (Arraylets are also used by the Metronome GC; both Balanced and Metronome GC policies are region-based garbage collectors.)

Arraylets have a **spine**, which contains the class pointer and size, and **leaves**, which contain the data associated with the array. The spine also contains **arrayoids** which are pointers to the respective arraylet leaves, as shown in the following diagram.

![The diagram is explained in the surrounding text](./cr/arrayalet_diagram.png "Arraylet diagram")

There are a number of advantages to using arraylets. 

- Because the heap tends to fragment over time, other collector policies might be forced to run a global garbage collection and defragmentation (compaction) phase to recover sufficient contiguous memory to allocate a large array. By removing the requirement for large arrays to be allocated in contiguous memory, the Balanced GC is more likely to be able to satisfy such an allocation without requiring unscheduled garbage collection, particularly a global defragmentation operation. 
- Additionally, the Balanced GC never needs to move an arraylet leaf once it has been allocated. The cost of relocating an array is therefore limited to the cost of relocating the spine, so large arrays do not contribute to higher defragmentation times.

However, arraylets can slow down processing when the Java Native Interface (JNI) is being used. JNI provides flexibility by enabling Java programs to call native code; for example C or C++. If direct addressability to the inside of an object is needed, a JNI critical section can be used. But that requires the object to be in a contiguous region of memory, or at least _appear_ to be so. The JNI therefore creates a temporary contiguous array that is the same size as the original array and copies everything, element by element, to the temporary array. After the JNI critical section is finished, everything is copied from the temporary array back to the arraylet, element by element.

#### Double Mapping

**(Linux&reg; only)**

Double mapping of arraylets solves the problems caused when using JNI with arraylets on Linux systems. Double mapping makes a large array, which is divided across the heap in different chunks, look like a contiguous array by making use of the system's virtual memory. 

The virtual memory address space on 64-bit systems is very large whereas physical memory is typically much more limited. Using more virtual memory address space is not a significant overhead for the application compared with using more physical memory. There is therefore a saving in resources by using two different virtual memory addresses mapped to the same physical memory address instead of using more physical memory and creating temporary arrays.

![The diagram is explained in the surrounding text](./cr/arraylet_doublemap.png "Arraylet double mapping diagram")

The double mapping is created as soon as an array in a Java program is instantiated and the double-mapped mirror of the original array, which now appears to be in contiguous memory, can be referenced immediately. Modifications made to the contiguous representation of the arraylet are reflected on the discontiguous version and vice-versa.

Double mapping is enabled by default. If you want to disable it, use the command line option [`-Xgc:disableArrayletDoubleMapping`](xgc.md#disablearrayletdoublemapping).

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Double mapping cannot be supported if the object heap is allocated in Large (Huge) Pages. In such systems double mapping is ignored even if requested explicitly.

- Use the [`-Xlp:objectheap:pagesize`](xlpobjectheap.md#pagesize) option to configure the object heap in small pages; for example, `-Xlp:objectheap:pagesize=4k`.
- Use the [`-verbose:sizes`](cmdline_general.md) option to see the page sizes available in the system.

### Other policies

OpenJ9 has the following alternative GC policies:

- [`-Xgcpolicy:metronome`](xgcpolicy.md#metronome-aix-linux-x86-only) is designed for applications that require precise response times. Garbage collection occurs in small interruptible steps to avoid stop-the-world pauses. This policy is available only on x86 Linux and AIX platforms.
- [`-Xgcpolicy:nogc`](xgcpolicy.md#nogc) handles only memory allocation and heap expansion, but doesn't reclaim any memory. The GC impact on runtime performance is therefore minimized, but if the available Java heap becomes exhausted, an `OutOfMemoryError` exception is triggered and the VM stops.
- [`-Xgcpolicy:optavgpause`](xgcpolicy.md#optavgpause) uses concurrent mark and sweep phases, which means that pause times are reduced when compared to optthruput, but at the expense of some performance throughput.
- [`-Xgcpolicy:optthruput`](xgcpolicy.md#optthruput) is optimized for throughput by disabling the concurrent mark phase, which means that applications will stop for long pauses while garbage collection takes place. You might consider using this policy when high application throughput, rather than short garbage collection pauses, is the main performance goal.

For more information about these garbage collection policies and options, see [-Xgcpolicy](xgcpolicy.md).

## Troubleshooting

You can diagnose problems with garbage collection operations by turning on verbose garbage collection logging. By default, the information is printed to STDERR but can be redirected to a file by specifying the `-Xverbosegclog` option. The log files contain detailed information about all operations, including initialization, stop-the-world processing, finalization, reference processing, and allocation failures. For more information, see [Verbose garbage collection](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_pd_verbosegc.html)

If verbose logs do not provide enough information to help you diagnose GC problems, you can use GC trace to analyze operations at a more granular level. For more information, see [-Xtgc](xtgc.md).

<!-- ==== END OF TOPIC ==== gc.md ==== -->
