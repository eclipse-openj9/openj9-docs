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

The process of managing memory in the VM is handled by the allocator and the garbage collector. These components operate on an area of memory that is reserved for VM processing called the Java&trade; heap.


## The allocator

The allocator assigns areas of the Java heap for Java objects. Objects are considered as *live* when they have a chain of references to them that start from root references, such as those found in thread stacks. When that reference or pointer no longer exists, the objects are considered as *garbage*.

The allocator manages pools of free memory and how the free memory is consumed. It is also responsible for allocating areas of storage in the Java heap for objects at the request of applications, class libraries, or the VM.

Every allocation requires a *heap lock* to stop concurrent threads trying to access the same area of memory at the same time. When an object is allocated, the heap lock is released. If there is insufficient space to allocate the object, allocation fails, the heap lock is released, and the GC is called. If the GC manages to recover some space on the heap, the allocator can resume operations. If the GC does not recover enough space, it returns an `OutOfMemoryError` exception.

Acquiring a heap lock for every allocation would be an intensive operation with a knock on impact to performance. To get around this problem, small objects are allocated to allocation caches.

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


## The garbage collector

To prevent applications running out of memory, objects in the Java heap that are no longer required must be reclaimed. This process is known as *garbage collection* (GC). When garbage is collected, the garbage collector must obtain exclusive access to the heap, which causes an application to pause while the clean up is done. This pause is often referred to as a *stop-the-world* (STW) pause because an application must halt until the process completes. In general, the first step in the GC process is to *mark* the objects that are reachable, which means they are still in use. The next step is to *sweep* away the unmarked objects to reclaim memory. The final step is to *compact* the heap if the heap is badly fragmented.

A GC cycle is a repeatable process that involves a set of GC operations. These operations process all or parts of the Java heap. When operating on the
whole of the Java heap, the cycle is referred to as a *global GC cycle*; When operating on part of the heap, the cycle is referred to as a *partial GC cycle*.

A global GC cycle can be triggered explicitly or implicitly according to the following rules:

- A global GC cycle is triggered implicitly if it occurs because of internal mechanisms, such as an allocation failure or a taxation threshold being reached.
- A global GC cycle is triggered explicitly if it is started directly by an application calling `System.gc()` or indirectly, for example when requesting a heap dump.

Partial GC cycles are triggered only implicitly under the control of a particular GC policy. For more information about the GC policies available with OpenJ9, see [Garbage collection policies](gc.md).

### GC operations

GC operations run discrete functions on the Java heap. For example, a *mark* operation traces all objects in the heap to determine which ones are reachable. A *sweep* operation runs to clear away unreachable objects. Together, a *mark* and *sweep* operation are capable of reclaiming used memory as part of a GC cycle. Not all GC cycles include operations to reclaim memory. For example, the balanced GC policy involves a global cycle that includes only a *mark* operation; reclaiming the memory with a *sweep* operation occurs as part of a separate partial GC cycle.

A GC operation might complete in one step, or it might involve phases. For example, a mark operation consists of the 3 phases, as described in the following section.

#### GC *mark* operation

A *mark* operation identifies which objects on the Java heap are reachable from outside of the heap and which objects are unreachable. Reachable objects are in use and must be retained, whereas unreachable objects are not in use and can be cleared away as *garbage*.

The process of marking involves a bit array called a *mark map* that is allocated by the VM at startup, based on the maximum heap size setting. Each bit in the *mark map* corresponds to 8 bytes of heap space. When an object is *marked*, its location in the heap is recorded by setting the appropriate bit in the *mark map*.

A *mark* operation can be broken down into the following phases:

1. Initial phase

A root object is an object that is accessible from outside of the heap such as a stack, class static field, or JNI reference. For other objects in the heap to be reachable, they must retain a connection to a root object. In the initial phase, tracing identifies all root objects by running a root scan. Root objects are listed in work packets that can be processed for references to other objects in the heap.

2. Main phase

The list of reachable root objects in each work packet is recursively traced for references to other objects in the heap. If reachable objects are found, the appropriate bit is set in the *mark map*.

3. Final phase

The final phase processes weakly reachable roots such as finalizable object lists, soft/weak references, monitor tables, and string tables. If reachable objects are found, the appropriate bit is set in the *mark map*.

#### GC parallel *mark* operation

On a multi-processor system, helper threads can be used in parallel to speed up *mark* operations on the heap. The helper threads share the pool of work packets with the application thread and are involved in identifying root objects, tracing objects in the heap, and updating the *mark map*. By default, the number of helper threads is based on the number of CPUs reported by the operating system. You can control the number of helper threads available by specifying the [-Xgcthreads](xgcthreads.md) command line option when starting your application.

#### GC concurrent *mark* operation

A *mark* operation can run with exclusive access to the heap, which requires application threads to pause while processing takes place. Alternatively, it can run concurrently with application threads to avoid pauses in application processing.

With concurrent *mark* operations, the process of root scanning is handed over to application stack threads, which populate work packets with root objects in their stack. The root objects in each work packet are then traced by a background thread and by each application thread during a heap lock allocation to find reachable objects and update the *mark map*. Because the *mark* operation runs concurrently with application threads, any changes to objects that are already traced must be updated. This process works by using a write barrier that can flag the update and trigger a further scan of part of the heap.

To track updates to objects, concurrent *mark* operations use single-byte *cards* in a *card table*. Each *card* corresponds to a 512-byte section of the Java heap. When an object is updated, the start address for the object in the heap is marked on the appropriate card. These cards are used to determine what must be retraced later in the GC cycle.

A GC cycle that includes concurrent *mark* operations aims to trace all reachable objects and complete processing at the same time as the heap is exhausted. Continuous adjustments are made to the start time of each cycle to get as close to heap exhaustion as possible. When the heap is exhausted a *sweep* operation is able to reclaim memory. This operation requires a *stop-the-world* (STW) pause. Before *sweep* operations start, the root objects are rescanned and the cards are checked to determine which areas of memory must be retraced.
**Question: Are these marked now or in the next cycle?**

An advantage of concurrent *mark* operations over STW *mark* operations is reduced pause times, because the garbage collector is able to identify garbage without halting application threads. Pause times are also more consistent because the collector is able to tune start times to maximize heap usage.

Disadvantages of concurrent *mark* operations include the additional CPU for operating the write barrier and additional work for application threads to trace objects when requesting a heap lock.

Concurrent *mark* operations are used by the following GC policies:

- [`gencon`](gc.md#gencon-policy-default)
- [`optavgpause`](gc.md#optavgpause-policy)

#### GC incremental concurrent *mark* operation

Incremental concurrent mark operations are used in the `balanced` GC policy to even out pause times by avoiding global STW garbage collections. These operations are also known as the *global mark phase*, whereby *mark* operations take place incrementally across the entire heap. The global *mark* operations are interleaved with a partial GC cycle that is responsible for moving objects and clearing unreachable objects in the heap.

These operations also use *cards* in a *card table* to manage updates to objects that occur whilst *mark* operations are in progress. However, unlike the concurrent *mark* operations used by other policies, application threads are not involved in tracing objects; only background threads are used to trace objects and update the *cards*.

For more information about GC operations for the `balanced` GC policy, see [Garbage collection policies](gc.md#balanced-policy)

#### GC *sweep* operation

The purpose of a *sweep* operation is to identify memory that can be reclaimed for object allocation and update a central record, known as the *freelist*.

#### GC parallel *sweep* operation

Similar to the parallel *mark* operation, multiple helper threads can be used to *sweep* the Java heap to speed up processing times. Because these helper threads are the same ones that are used for parallel *mark* operations, the number of threads can be controlled by using the [-Xgcthreads](xgcthreads.md) option.

Parallel *sweep* operations run on 256 KB sections of the heap. Each helper thread scans a section at a time. The results are stored and used to generate a *freelist* of empty regions.

This GC operation is used on the `balanced` GC policy, where the heap is divided into thousands of regions.

#### GC concurrent *sweep* operation

Concurrent *sweep* operations occur in 2 phases:

1. Initial phase

This phase analyzes the *mark map* for free memory.

2. Final phase

Based on the analysis, the sections of the heap are connected to the *freelist*.

Concurrent *sweep* operations work in tandem with concurrent *mark* operations and use the same *mark map*. Concurrent *sweep* operations start after a STW collection and complete a section of the heap before concurrent *mark* operations continue.

Concurrent *sweep* is used by the `optavgpause` GC policy.

#### GC *scavenge* operation

A GC *scavenge* operation is used by the `gencon` policy to move an object in the heap to create space for new object allocations. The object is copied from the *nursery* area of the heap to the *tenure* area, which is reserved for older generations of objects.

#### GC *copy forward* operation

A GC *copy forward* operation is similar to a *scavenge* operation. The *copy forward* operation is used by the `balanced` GC Policy to move an object from a younger region to an older region in the heap. The operation aims to empty or *evacuate* fragmented regions that then become available for new object allocation.

#### GC *classunloading* operation

#### GC *compact* operation

Compaction of the heap is an expensive operation because when objects are moved to defragment the heap, the references to each object change. Therefore, *compact* operations do not occur by default but only when certain triggers occur.

**Add the triggers:**

Two options are available to control *compact* operations; [`-Xcompactgc`](xcompactgc.md) and [`-Xnocompactgc`](xnocompactgc.md). If `-Xnocompactgc` is specified, compaction occurs only in rare circumstances.

#### GC *mark compact* operation

A GC *mark compact* operation is sometimes used instead of a *copy forward* operation by the `balanced` GC policy. Rather than moving or *evacuating* objects to empty regions, objects within a region are *marked* and the region *compacted* to free up contiguous space.

<!-- ==== END OF TOPIC ==== gc_overview.md ==== -->
