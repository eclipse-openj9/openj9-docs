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


# Memory management overview

The process of managing memory in the VM is handled by the Allocator and the Garbage Collector. These components operate on an area of memory that is reserved for VM processing called the Java&trade; heap.


## The Allocator

The Allocator assigns areas of the Java heap for Java objects. Objects are considered as *live* when they have a chain of references to them that start from root references, such as those found in thread stacks. When that reference or pointer no longer exists, the objects are considered as *garbage*.

The Allocator manages pools of free memory and how the free memory is consumed. It is also responsible for allocating areas of storage in the Java heap for objects at the request of applications, class libraries, or the VM.

Every allocation requires a *heap lock* to stop concurrent threads trying to access the same area of memory at the same time. When an object is allocated, the heap lock is released. If there is insufficient space to allocate the object, allocation fails, the heap lock is released, and the GC is called. If the GC manages to recover some space on the heap, the Allocator can resume operations. If the GC does not recover enough space, it returns an `OutOfMemoryError` exception.

Acquiring a heap lock for every allocation would be an intensive operation with a knock on impact to performance. To get around this problem, small objects are allocated to thread local heaps (TLH).

### Allocation caches

To improve performance, allocation caches are reserved in the heap for different threads. These allocation caches are known as thread local heaps (TLH) and allow each thread to allocate memory from its cache without acquiring the heap lock. A TLH is typically used for small objects of less than 512 bytes (768 bytes on 64-bit VMs) although larger objects can be allocated from the cache if there is sufficient space.

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

### SOA and LOA

Some GC policies subdivide areas of the heap for object allocation into the Small Object Area (SOA) and the Large Object Area (LOA).

The allocator initially attempts to allocate objects in the SOA, regardless of size. If the allocation cannot be satisfied the following actions are possible, depending on object size:

- If the object is smaller than 64 KB, an allocation failure occurs, which triggers a GC action.
- If the object is larger than 64 KB, the allocator attempts to allocate the object in the LOA. If the allocation cannot be satisfied, an allocation failure occurs, which triggers a GC action.

The GC action that is triggered by the allocation failure depends on the GC policy in force.

The overall size of the LOA is calculated when the heap is initialized, and recalculated at the end of each global GC cycle. The GC can expand or shrink the LOA, depending on usage, to avoid allocation failures.

You can control the size of the LOA by using the `-Xloainitial`, `-Xloaminimum`, and `-Xloamaximum` command line options (See [LOA sizing options](xloaminimum.md)). If the LOA is not used, the GC shrinks the LOA after a few cycles, down to the value of `-Xloaminimum`. You can also specify [`-Xnoloa`](xloa.md) to prevent an LOA being created.

An SOA and LOA are used by the OpenJ9 standard GC policies: `gencon`, `optavgpause` and `optthruput`. However, the `balanced` and `metronome` GC policies use a heap configuration that does not include the SOA or LOA. For more information about policies, see [Garbage collection policies](gc.md).

### Compressed references

On 64-bit systems, the VM can use compressed references to decrease the size of Java objects and make better use of the available space in the Java heap. By storing objects in a 32-bit representation, the object size is identical to a 32-bit object, which occupies a smaller memory footprint. These 4 byte (32-bit) compressed references are converted to 64-bit values at runtime with minimal overhead. Smaller objects enable larger heap sizes that result in less frequent garbage collection and improve memory cache utilization. Overall, the performance of 64-bit applications that store compressed rather than uncompressed 64-bit object references is significantly improved.

Compressed references are used by default when the maximum Java heap size is in the range 0 - 57 GB on AIX&reg;, Linux&reg;, and Windows&reg; systems. The upper limit is also 57 GB on z/OS&reg; systems that have APAR OA49416
installed (25 GB without APAR OA49416). All GC policies observe these limits except for the [`metronome`](gc.md#metronome-policy) policy, which can only support a heap size of up to 25 GB with compressed references.

When the VM uses compressed references, classes, threads, and monitors are stored in the lowest 4 GB of address space. However, this area of memory is also used by native libraries, the operating system, and for small Java heaps. If you receive native memory `OutOfMemoryError` exceptions in the lowest 4 GB when running with compressed references enabled, these errors might result from the lowest 4 GB of address space becoming full. Try specifying a large heap with the [`-Xmx`](xms.md) option, which puts the Java heap into a higher area of address space or using the [`-Xmcrs`](xmcrs.md) option to reserve space in the lowest 4 GB of address space for compressed references.

To turn off compressed references, use the [`-Xnocompressedrefs`](xcompressedrefs.md) command-line option.


## The Garbage Collector

To prevent applications running out of memory, objects in the Java heap that are no longer required must be reclaimed. This process is known as *garbage collection* (GC). When garbage is collected, the Garbage Collector must obtain exclusive access to the heap, which causes an application to pause while the clean up is done. This pause is often referred to as a *stop-the-world* (STW) pause because an application must halt until the process completes. In general, the first step in the GC process is to *mark* the objects that are reachable, which means they are still in use. The next step is to *sweep* away the unmarked objects to reclaim memory. The final step is to *compact* the heap if the heap is badly fragmented.

OpenJ9 provides several GC policies that are used to manage garbage collection. For more information, see [Garbage collection policies](gc.md).


<!-- ==== END OF TOPIC ==== gc_overview.md ==== -->
