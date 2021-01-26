<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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


# Garbage collection policies

OpenJ9 provides several garbage collection (GC) policies that are designed around different application workloads and service level agreements. Each GC policy consists of a set of characteristics and features that aim to optimize one or more performance aspects of a running application. These performance aspects include application throughput, memory footprint, average pause times, worst-case pause times, and startup time.

Different policies require a Java heap that is configured in different ways in order to achieve different goals. The simplest configuration consists of a single area of memory, often referred to as a *flat* heap. Other configurations divide the heap into different areas or regions, which might contain objects of different ages (*generations*) or sizes.

A GC cycle is a repeatable process that involves a set of GC operations. These operations process all or parts of the Java heap to complete a discrete function. For example, a *mark* operation traces all objects in the heap to determine which ones are reachable. A *sweep* operation runs to clear away unreachable objects. Together, a *mark* and *sweep*  operation are capable of reclaiming used memory as part of a GC cycle. Not all GC cycles include operations to reclaim memory. For example, the `balanced` policy involves a global cycle that includes only a *mark* operation; reclaiming the memory with a *sweep* operation occurs as part of a separate partial GC cycle.

A GC operation might complete in one step, or it might involve phases. For example, a *mark* operation consists of the following 3 phases:

- *Initial phase:* Identify all the root objects by running a root scan.
- *Main phase:* From the identified list, recursively trace references. Reachable objects are marked.
- *Final phase:* Process weakly reachable roots such as finalizable object lists, soft/weak references, monitor tables, and string tables.

GC policies use different GC cycles to manage different aspects of the heap. For example, the `gencon` policy runs a partial GC cycle on the *nursery* area of the heap to complete a *scavenge* operation. At other times, `gencon` also runs a global GC on the entire Java heap to complete *mark* and *sweep* (and optionally *compact*) operations.  

GC cycles might be divided into increments that run over a period of time to reduce maximum pause times. These increments might involve *stop-the-world* (STW) pauses that must halt application threads to give certain GC operations exclusive access to the Java heap. Alternatively, increments might include GC operations that can run concurrently with application processing.

The following table shows the heap configuration and the GC cycles and operations used by different policies:

| Policy         | Heap configuration |  GC cycles / operations |
|----------------|--------------------|-------------------------|
|`gencon`        | Two areas: *nursery* and *tenure* <br> Two generation groups: new/older |Global GC cycle: concurrent *mark-sweep* operations, optionally followed by a *compact* operation <br> Partial GC cycle: STW *scavenge* operation or concurrent *scavenge* operation (if optionally enabled) |
|`balanced`      | Multiple regions of equal size <br> Multiple generations |Global GC *mark* cycle: incremental concurrent *mark* operation (*global mark phase*) <br> Partial GC cycle: STW *copy forward* operation and optional *mark*, *sweep* or *compact* operations |
|`optavgpause`   | One area: *flat* <br> One generation | Global GC cycle: concurrent *mark-sweep* operations, optionally followed by a *compact* operation |
|`optthruput`    | One area: *flat* <br> One generation  | Global GC cycle: STW *mark-sweep* operations, optionally followed by a *compact* operation|
|`metronome`     | Multiple regions by size class <br> One generation | Global GC cycle: incremental STW *mark-sweep* operation in small interruptible steps |
|` nogc`         | One area: *flat* | No GC cycles |


<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** All OpenJ9 GC policies support compressed references on 64-bit platforms, which compresses heap pointers to 32 bits if the total heap size does not exceed the theoretical upper bound of 64 GB. Applications that require more heap space can select any heap size within the bounds imposed by the operating system and available system RAM, without using compressed references. For more information, see [compressed references](gc_overview.md#compressed-references).


## Policy selection and tuning

The default policy is the Generational Concurrent (`gencon`) GC policy, which suits a broad spectrum of applications. Choosing a different GC policy should be guided by the application dynamics and an observation of how the application interacts with the heap during startup and at steady state. To help with this analysis, all OpenJ9 GC policies are instrumented to collect a wide range of GC-related metric data for reporting in a GC log file.

To enable GC logging for the OpenJ9 Java runtime, include the `-verbose:gc` option on the command line. By default, this option prints output to `stderr` but you can send the output to a log file by using [`-Xverbosegclog`](xverbsoegclog). You can then visualize the output by loading the GC log into the [Garbage Collector and Memory Visualizer (GCMV)](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv) plugin for the Eclipse IDE. OpenJ9 Java GC logs can also be analyzed by some online services, such as [GCEasy](https://gceasy.io/).

The following sections provide more information about each policy and when you might choose it for your application. To select a GC policy other than `gencon`, specify the [`-Xgcpolicy`](xgcpolicy.md) option on the command line. To adjust the initial and maximum size of the Java heap, use the [`-Xms` and `-Xmx`](xms.md) command line options. For generational GC policies, you can also set the [`-Xmn`, `-Xmns`, and `-Xmnx`](xmn.md) options.

## `gencon` policy (default)

The Generational Concurrent GC policy ([`-Xgcpolicy:gencon`](xgcpolicy.md#gencon)) is probably best suited if you have a transactional application, with many short-lived objects. This policy aims to minimize GC pause times without compromising throughput. This is the default policy employed by the VM, so if you want to use it you don't need to specify it on the command line when you start your application.

If your application requires the allocation of objects of very different sizes and liveness on the Java heap, you might experience heap fragmentation, which in turn might lead to global heap compaction. In these circumstances, the [Balanced GC policy](xgcpolicy.md#balanced-policy) might be more appropriate.

### GC operations

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

### Concurrent Scavenge

A special mode of the `gencon` policy is known as *Concurrent Scavenge*. This mode aims to further reduce the average time spent in *stop-the-world* (STW) pauses by collecting nursery garbage in parallel with running application threads. Whilst aiming to reduce the average time, this mode does not improve the worst case pause time when compared to running `gencon` without Concurrent Scavenge enabled.

To enable Concurrent Scavenge, see [-Xgc:concurrentScavenge](xgc.md#concurrentscavenge).

This mode can be enabled with hardware-based support and software-based support:

- **Hardware-based support: (Linux on IBM Z&reg; and z/OS&reg;)** This mode works on the IBM z14™ and later mainframe system with the Guarded Storage (GS) Facility. The GS Facility provides hardware-based support to detect when potentially stale references to objects are accessed by an application. This means that the garbage collector can start processing objects in parts of the heap without halting an application because the GS Facility is on hand to spot accesses to an object and send a notification. The object that was ready to be swept away can be moved, and references to it can be reset.

- **Software-based support: (64-bit: Linux on (x86-64, POWER, IBM Z&reg;), AIX&reg;, macOS&reg;, and z/OS&reg;)** With software-based support, Concurrent Scavenge can be enabled without any pre-requisite hardware although the performance throughput is not as good as hardware-based support.

More information about Concurrent Scavenge mode can be found in the blog post [Concurrent Scavenge Garbage Collection Policy](https://blog.openj9.org/2019/03/25/concurrent-scavenge-garbage-collection-policy/).

## `balanced` policy

The Balanced GC policy ([`-Xgcpolicy:balanced`](xgcpolicy.md#balanced)) evens out pause times and reduces the overhead of some of the costlier operations that are typically associated with garbage collection, such as compaction and class unloading. The Java heap is divided into a large number of regions (1,000 - 2,000), which are managed individually by an incremental generational collector to reduce the maximum pause time on large heaps and increase the efficiency of garbage collection. The aim of the policy is to avoid global garbage collections by matching object allocation and survival rates.

###  When to use

The Balanced policy suits applications that require large heaps (>64 Mb) on 64-bit platforms. This policy might be a good alternative for applications that experience unacceptable pause times with `gencon`.


- If you have problems with application pause times that are caused by global garbage collections, particularly compactions, this policy might improve application performance.

- If you are using large systems that have Non-Uniform Memory Architecture (NUMA) characteristics (x86 and POWER&trade; platforms only), the Balanced policy might further improve application throughput.

However, even though pause times are typically evened out across GC operations, actual pause times are affected by object allocation rates, object survival rates, and fragmentation levels within the heap, and cannot therefore be bound to a certain maximum nor can a certain utilization level be guaranteed.

### GC operations


During VM startup, the GC divides the heap memory into regions of equal size. These regions remain static for the lifetime of the VM and are the basic unit of garbage collection and allocation operations. For example, when the heap is expanded or contracted, the memory committed or released corresponds to a certain number of regions. Although the Java heap is a contiguous range of memory addresses, any region within that range can be committed or released as required. This enables the Balanced GC to contract the heap more dynamically and aggressively than other garbage collectors, which typically require the committed portion of the heap to be contiguous.

Regions impose a maximum object size. Objects are always allocated within the bounds of a single region and are never permitted to span regions. The region size is always a power of two; for example, 512 KB, 1 MB, and so on (where KB is 2<sup>10</sup> bytes and MB is 2<sup>20</sup> bytes). The region size is selected at startup based on the maximum heap size. The collector chooses the smallest power of two which will result in less than 2048 regions, with a minimum region size of 512 KB. Except for small heaps (less than about 512 MB) the VM aims to have between 1024 and 2047 regions.

Object ages are tracked for each region with a maximum of 24 possible generations. The following diagram illustrates the structure of the object heap:

![The diagram is explained in the surrounding text](./cr/balanced_regions.png "Region structure and characteristics found in the object heap")

The *eden* space is a set of regions of age 0, which contain the newest objects allocated. When the *eden* space is full, a partial GC cycle runs to reclaim space, typically by using a *copy forward* operation. In specific cases, a *mark-compact* operation might be used, for example, when there are not enough free survivor regions available. The partial GC cycle is a *stop-the-world* (STW) operation, after which new regions are allocated to create a new *eden* space. A partial cycle always includes the *eden* space, but might include older regions. Objects from collectible regions of age N are moved into another region of the same age N or to an empty region that is assigned an age of N. Then, the ages of all regions across the heap are incremented by 1, except for the maximum age 24 regions. Regions of age 24 are included in partial GC collection sets in order to defragment them.

Partial GC cycles work to reclaim free regions in the heap for allocating new objects. Because some objects from *eden* regions always survive, a partial GC cycle can reclaim only about  90% of this memory. To keep up with object allocation, partial GC cycles also reclaim free regions by defragmenting older regions. For example, a partial GC cycle that moves objects from 5 fragmented older regions into 2 empty regions, reclaims 3 regions for new object allocation. However, over time the overall amount of fragmented memory decreases and records about object liveness in older regions become less accurate. Eventually, the work done by partial GC cycles to reclaim memory cannot keep pace with memory consumption. Free regions become so scarce that a global *mark* operation (GMP) is required to build a new record of object liveness across the heap. A *sweep* operation uses this record to measure the amount of free memory in fragmented older regions, which later partial GC cycles can act upon to move objects and reclaim free regions.

A global *sweep* operation also runs to reclaim memory so that it can create empty regions. The global *sweep* operation, while logically associated with the global *mark* operation, runs in the same STW increment as the first partial GC cycle after the *mark* operation completes. Because the GC cycle responsible for the global *mark* operation runs concurrently, it might overlap and interleave with a few partial GC cycles.

With the `balanced` policy, a global GC cycle is sometimes required in addition to the global *mark* operations and partial GC cycle. This global GC cycle is rare, occurring only in very tight memory conditions when other GC cycles cannot free enough memory on the heap.

To learn about the default heap size and the tuning options that can be used with the `balanced` policy, see [`-Xgcpolicy:balanced`](xgcpolicy.md#balanced-defaults-and-options).

### Arraylets: dealing with large arrays

Most objects are easily contained within the minimum region size of 512 KB. However, some large arrays might require more memory than is available in a single region. To support such arrays, the `balanced` GC policy uses an **arraylet** representation to more effectively store large arrays in the heap. (Arraylets are also used by the `metronome` GC policy; both `balanced` and `metronome` GC policies are region-based garbage collectors.)

Arraylets have a **spine**, which contains the class pointer and size, and **leaves**, which contain the data associated with the array. The spine also contains **arrayoids**, which are pointers to the respective arraylet leaves, as shown in the following diagram.

![The diagram is explained in the surrounding text](./cr/arraylet_diagram1.png "Arraylet diagram")

There are a number of advantages to using arraylets.

- Because the heap tends to fragment over time, other collector policies might be forced to run a global garbage collection and defragmentation (compaction) to recover sufficient contiguous memory to allocate a large array. By removing the requirement for large arrays to be allocated in contiguous memory, the `balanced` policy is more likely to be able to satisfy such an allocation without requiring unscheduled garbage collection, particularly a global defragmentation operation.

- Additionally, the `balanced` GC never needs to move an arraylet leaf once it has been allocated. The cost of relocating an array is therefore limited to the cost of relocating the spine, so large arrays do not contribute to higher defragmentation times.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Despite the general advantage of using arraylets, they can slow down processing when the Java Native Interface (JNI) is being used. The JNI provides flexibility by enabling Java programs to call native code; for example C or C++, and if direct addressability to the inside of an object is needed, a JNI critical section can be used. However, that requires the object to be in a contiguous region of memory, or at least _appear_ to be so. The JNI therefore creates a temporary contiguous array that is the same size as the original array and copies everything, element by element, to the temporary array. After the JNI critical section is finished, everything is copied from the temporary array back to the arraylet, element by element.


## `optavgpause` policy

The *optimize for pause time* policy ([`-Xgcpolicy:optavgpause`](xgcpolicy.md#optavgpause)) uses a global GC to manage a *flat* heap comprised of a single area and to compact the heap if the heap becomes fragmented. The global GC cycle starts preemptively so that the cycle finishes before the heap is exhausted. By anticipating global collections and initiating some *mark* operations ahead of the collection phase, the `optavgpause` policy reduces GC pause times when compared to `optthruput`. However, the reduction in pause time comes at the expense of some performance throughput.

### When to use

Consider using this policy if you have a large heap size (available on 64-bit platforms), because this policy limits the effect of increasing heap size on the length of the GC pause.

Although `optavgpause` uses a write barrier to support concurrent *mark* operations, it does not use a generational write barrier. For some application workloads, such as those that frequently change large and old reference arrays, this strategy might be of greater benefit. However, in many situations, the default [`gencon`](#gencon-policy-default) policy offers better performance.

By using a *flat* heap, `optavgpause` avoids potential issues with very large objects. With `gencon`, the heap is divided into areas (*nursery* and *tenure*) in order to manage generations of objects. Although there might be sufficient free space on the overall Java heap for a very large object, it might not fit into the *nursery* area. If the allocator does succeed in allocating a very large object, further GC cycles might be required to create enough contiguous free space.

Overall, `optavgpause`, along with `optthruput`, is best suited to short-lived applications and to long-running services that involve concurrent sessions with short lifespans. Short-lived applications with adequate heap sizes usually complete without compaction. The *flat* heap fragments more slowly when session-bound objects are allocated and drop out of the live set in short overlapping clusters.

### GC operations

The `optavgpause` policy requires a *flat* Java heap. A global GC cycle runs concurrent *mark-sweep* operations, optionally followed by *compact* operations. By running most operations concurrently with application threads, this strategy aims to reduce GC pause times as much as possible.

## `optthruput` policy

The *optimize for throughput* policy ([`-Xgcpolicy:optthruput`](xgcpolicy.md#optthruput)) uses a global GC cycle to manage a *flat* heap that is comprised of a single area and to compact the heap if the heap becomes fragmented. The global collector runs *mark* and *sweep* operations when the heap is exhausted, which means that applications stop for long pauses while garbage collection takes place.

### When to use

You might consider using this policy when a large heap application can tolerate longer GC pauses to obtain better overall throughput. Unlike `gencon`, the `optthruput` policy does not use object access barriers. In some workloads, the cost of these barriers might be high enough to make `optthruput` preferable. However, in many situations, the default [`gencon`](#gencon-policy-default) policy offers better performance.

By using a *flat* heap, `optthruput` avoids potential issues with very large objects. With `gencon`, the heap is divided into areas (*nursery* and *tenure*) in order to manage generations of objects. Although there might be sufficient free space on the overall Java heap for a very large object, it might not fit into the *nursery* area. If the allocator does succeed in allocating a very large object, further GC cycles might be required to create enough contiguous free space.

Overall, `optthruput`, along with `optavgpause`, is best suited to short-lived applications and to long-running services that involve concurrent sessions with short lifespans. Short-lived applications with adequate heap sizes usually complete without compaction. The *flat* heap fragments more slowly when session-bound objects are allocated and drop out of the live set in short overlapping clusters.

### GC operations

The `optthruput` policy requires a *flat* Java heap. A  global GC cycle runs *mark-sweep* operations, optionally followed by *compact* operations. The cycle requires exclusive access to the heap, causing application threads to halt while operations take place. As such, long pauses can occur.

## `metronome` policy

**(Linux on x86-64 and AIX platforms only)**

The metronome policy ([`-Xgcpolicy:metronome`](xgcpolicy.md#metronome-aix-linux-x86-only)) is an incremental, deterministic garbage collector with short pause times. Applications that are dependent on precise response times can take advantage of this technology by avoiding potentially long delays from GC activity.

### When to use

`metronome` is designed for applications that require a precise upper bound on collection pause times as well as specified application utilization: the proportion of time that the application is permitted to use, with the remainder being devoted to GC. The `metronome` GC runs in short interruptible bursts to avoid long *stop-the-world* (STW) pauses.

### GC operations

The Java heap is allocated as a contiguous range of memory, partitioned into small regions of equal size (~64 KB). The `metronome` policy does not dynamically resize the heap; the heap is always fully expanded, even if [`-Xms`](xms.md) is not set to [`-Xmx`](xms.md).

As with the `balanced` GC policy, arrays are represented as arraylets with a spine that points to a series of regions that contain the array elements. For more information, see [Arraylets: dealing with large arrays](#arraylets-dealing-with-large-arrays). Note that JNI access to array data might involve reconstituting arraylets as contiguous arrays, which can significantly slow down processing.

Each region of the heap is either empty, or contains only objects in one of 16 size classes, or an arraylet. This organization improves the use of available heap space, reducing the need for heap compaction and defragmentation, and providing more precise control over the incremental *sweep* operation.

Although high application utilization is desirable for optimal throughput, the GC must be able to keep up with the application's memory allocation rate.

A higher utilization typically requires a larger heap because the GC isn't allowed to run as much as a lower utilization would permit. The relationship between utilization and heap size is highly application dependent, and striking an appropriate balance requires iterative experimentation with the application and VM parameters. You might need to adjust heap size or pause time or target utilization to achieve an acceptable runtime configuration.

To learn about default options and tuning options that can be used with the `metronome` policy, see [`-Xgcpolicy:metronome`](xgcpolicy.md#metronome-defaults-and-options).


## `nogc` policy

[`-Xgcpolicy:nogc`](xgcpolicy.md#nogc) handles only memory allocation and heap expansion, but doesn't reclaim any memory. The GC impact on runtime performance is therefore minimized, but if the available Java heap becomes exhausted, an `OutOfMemoryError` exception is triggered and the VM stops.

### When to use

This policy is not suited to the majority of Java applications. However, the following use cases apply:

- Testing during development

    - GC performance: Use `nogc` as a baseline when testing the performance of other GC policies, including the provision of a low-latency baseline.

    - Application memory: Use `nogc` to test your settings for allocated memory. If you use `-Xmx` to set the heap size that should not be exceeded, your application terminates with a heap dump if it tries to exceed your memory limit.

- Running applications with minimal or no GC requirements

    - You might use `nogc` when an application is so short-lived that allocated memory is never exhausted and running a full GC cycle is therefore a waste of resources.

    - Similarly, when memory application is well understood or where there is rarely memory to be reclaimed, you might prefer to avoid unnecessary GC cycles and rely on a failover mechanism to occasionally restart the VM.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** You should be especially careful when using any of the following techniques with `nogc` because memory is never released under this policy:  

- Finalization  
- Direct memory access  
- Weak, soft, and phantom references

## Troubleshooting

You can diagnose problems with garbage collection operations by turning on verbose garbage collection logging. By default, the information is printed to STDERR but can be redirected to a file by specifying the `-Xverbosegclog` option. The log files contain detailed information about all operations, including initialization, *stop-the-world* processing, finalization, reference processing, and allocation failures. For more information, see [Verbose garbage collection](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_pd_verbosegc.html)

If verbose logs do not provide enough information to help you diagnose GC problems, you can use GC trace to analyze operations at a more granular level. For more information, see [-Xtgc](xtgc.md).

<!-- ==== END OF TOPIC ==== gc.md ==== -->
