<!--
* Copyright (c) 2017, 2025 IBM Corp. and others
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
* [2] https://openjdk.org/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0-only WITH Classpath-exception-2.0 OR GPL-2.0-only WITH OpenJDK-assembly-exception-1.0
-->


# Heap allocation

The process of managing memory in the VM is handled by the allocator and the garbage collector. These components operate on an area of memory that is reserved for VM processing called the Java&trade; heap.

The allocator assigns areas of the heap for Java objects. Objects are considered as *live* when they have a chain of references to them that start from root references, such as those found in thread stacks. When that reference or pointer no longer exists, the objects are considered as *garbage*.

The garbage collector reclaims memory by removing objects when they are no longer required. To find out more about the garbage collector, see [Garbage collection](gc_overview.md).

Depending on your application workload or service level agreement, you can choose from a number of Eclipse OpenJ9&trade; *garbage collection (GC) policies*. Each GC policy uses a different strategy to manage memory on the heap. The structure of the heap also depends on the strategy in force. For more information about choosing a GC policy, see [Garbage collection policies](gc.md).

## The allocator

The allocator manages pools of free memory and how the free memory is consumed. It is also responsible for allocating areas of storage in the Java heap for objects at the request of applications, class libraries, or the VM.

In general, allocation requires a *heap lock* to synchronize concurrent threads that try to access the same area of memory at the same time. When an object is allocated, the heap lock is released. If there is insufficient space to allocate the object, allocation fails, the heap lock is released, and the garbage collector is called. If GC manages to recover some space on the heap, the allocator can resume operations. If GC does not recover enough space, it returns an `OutOfMemoryError` exception.

Acquiring a heap lock for every allocation would be an intensive operation with an impact to performance. To get around this problem, small objects are allocated to allocation caches.

### Allocation caches

To improve performance, allocation caches are reserved in the heap for different threads. These allocation caches are known as thread local heaps (TLH) and allow each thread to allocate memory from its cache without acquiring the heap lock. Objects are allocated from the TLH unless there is insufficient space remaining in the TLH to satisfy the request. In this situation, the allocation might proceed directly from the heap for larger objects by using a heap lock or the TLH might be refreshed for smaller objects.

If a thread allocates a lot of objects, the allocator gives that thread a larger TLH to reduce contention on the heap lock.

A TLH is predefined with an initial default size of 2 KB. On every TLH refresh, the requested size for that thread
is increased by an increment (default 4 KB). The requested size can grow up to a predefined maximum (default 128 KB). If a TLH refresh fails to complete, a GC cycle is triggered.

After every GC cycle, the requested size of the TLH for each thread is reduced, sometimes by as much as 50%, to take account of threads that reduce their allocation rate and no longer need large TLHs.

For very inactive threads, the requested size can even drop below the initial value, down to the predefined minimum (512/768 bytes).
For very active threads, the maximum TLH requested size might be reached before the next GC occurs.

Larger TLHs can help reduce heap lock contention, but might also reduce heap utilization and increase heap fragmentation.

The following options control the requested TLH size:

- [`-Xgc:tlhMaximumSize=<bytes>`](xgc.md#tlhmaximumsize)
- [`-Xgc:tlhInitialSize=<bytes>`](xgc.md#tlhinitialsize)
- [`-Xgc:tlhIncrementSize=<bytes>`](xgc.md#tlhincrementsize)

Typically, when the maximum TLH size is increased, you should also increase the increment proportionally, so that active threads can
reach the maximum requested TLH size more quickly.

## Heap configuration

Depending on the memory management strategy in force, the Java heap can be configured in a number of ways. The simplest configuration consists of a single area of memory, often referred to as a *flat* heap. Other configurations divide the heap into different *areas* or *regions*, which might contain objects of different ages (generations) or sizes.

### Area-based heaps

The default GC policy for OpenJ9 uses a heap configuration that is divided into two main areas: the *nursery* area for new object allocation, and the *tenure* area for objects that continue to be reachable for a longer period of time. Most objects have short lifecycles and can be reclaimed by the garbage collector more quickly by focusing only on the nursery area. Global GC cycles that cause application pauses in order to clear and defragment the tenure area are less frequent.

### SOA and LOA

All area-based heaps subdivide part of the heap into the Small Object Area (SOA) and the Large Object Area (LOA).

The allocator initially attempts to allocate objects in the SOA, regardless of size. If the allocation cannot be satisfied the following actions are possible, depending on object size:

- If the object is smaller than 64 KB, an allocation failure occurs, which triggers a GC action.
- If the object is larger than 64 KB, the allocator attempts to allocate the object in the LOA. If the allocation cannot be satisfied, an allocation failure occurs, which triggers a GC action.

The GC action that is triggered by the allocation failure depends on the GC policy in force.

The overall size of the LOA is calculated when the heap is initialized, and recalculated at the end of each global GC cycle. The garbage collector can expand or contract the LOA, depending on usage, to avoid allocation failures.

You can control the size of the LOA by using the [`-Xloainitial`, `-Xloaminimum`, and `-Xloamaximum`](xloa.md) command line options. If the LOA is not used, the garbage collector contracts the LOA after a few cycles, down to the value of `-Xloaminimum`. You can also specify [`-Xnoloa`](xloa.md) to prevent an LOA being created.

An SOA and LOA are used by the OpenJ9 GC policies: `gencon`, `optavgpause`, and `optthruput`. For the `gencon` policy, the LOA and SOA are contained within the tenure area, which is designated for ageing objects. For more information about policies, see [Garbage collection policies](gc.md).

### Region-based heaps

The Java heap can also be subdivided into multiple regions. The `balanced` GC policy uses a heap that is divided into thousands of equal size regions in order to manage multiple generations of objects. The `metronome` GC policy also uses multiple regions, which are grouped by size-class to manage a singe generation of objects. To learn more about how the regions are configured for each policy, see [Garbage collection policies](gc.md).

In addition to regions, the `balanced` and `metronome` policies use structures called *arraylets* to store large arrays in the heap.

### Arraylets

A Java heap that is subdivided into regions might not be able to contain a large enough region for data arrays. This problem is solved by using *arraylets*. An arraylet has a *spine*, which contains the class pointer and size, and *leaves*, which contain the data associated with the array. The spine also contains *arrayoids*, which are pointers to the respective arraylet leaves, as shown in the following diagram.

![The diagram is explained in the surrounding text](./cr/arraylet_diagram1.png "Arraylet diagram")

There are a number of advantages to using arraylets.

- Because the heap tends to fragment over time, some GC policies might be forced to run a global garbage collection and defragmentation (compaction) to recover sufficient contiguous memory to allocate a large array. By removing the requirement for large arrays to be allocated in contiguous memory, the garbage collector is more likely to be able to satisfy such an allocation without requiring unscheduled garbage collection, particularly a global defragmentation operation.

- Additionally, the garbage collector never needs to move an arraylet leaf once it has been allocated. The cost of relocating an array is therefore limited to the cost of relocating the spine, so large arrays do not contribute to higher defragmentation times.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Despite the general advantage of using arraylets, they can slow down processing when the Java Native Interface (JNI) is being used. The JNI provides flexibility by enabling Java programs to call native code; for example, C or C++, and if direct addressability to the inside of an object is needed, a JNI critical section can be used. However, that requires the object to be in a contiguous region of memory, or at least _appear_ to be so. The JNI, therefore, creates a temporary contiguous array that is the same size as the original array and copies everything, element by element, to the temporary array. After the JNI critical section is finished, everything is copied from the temporary array back to the arraylet, element by element.

## Heap sizing

The overall size of the Java heap is determined by two command-line options, `-Xms`, which sets the initial size of the heap, and `-Xmx`, which sets the maximum size of the heap. Finer tuning of the heap depends on the heap configuration that is being used by a GC policy. For example, an LOA within the heap can be sized by using the [`-Xloainitial`, `-Xloaminimum`, and `-Xloamaximum`](xloa.md) command-line options. A nursery area within the heap can be sized by using the [`-Xmn`, `-Xmns`, and `-Xmnx`](xmn.md) command-line options. For more information about policies and the heap configurations that are used, see [GC policies](gc.md). To determine the values that are in use for the Java heap, use the `-verbose:sizes` option when you run your Java application.

When the Java heap runs out of space, `OutOfMemoryError` exceptions are generated. If you are confident that your heap settings are appropriate for your application but are still receiving an `OutOfMemoryError` exception, check the Java dump file that gets automatically generated when the error occurs. A Java dump file can tell you more about what your application was attempting to do at the time of the error. For example, see the [Java OutOfMemoryError](dump_javadump.md#java-outofmemoryerror) scenario.

### Expansion and contraction

At startup, the VM allocates a single contiguous area of virtual storage to match the value of `-Xmx`. By default, this is 25% of the available memory up to a maximum of 25 GB. The actual Java heap size starts at the value set by `-Xms` and expands automatically, as required, up to the maximum heap size. The VM can also contract the size of the Java heap. Expansion and contraction occur as part of a GC cycle when the VM has exclusive access to the heap. The only GC policy that does not support heap expansion and contraction is the `metronome` GC policy, where the heap is always fully expanded.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On operating systems that support paging, the VM allocates a single contiguous area that matches the value of `-Xms`. Additional memory is committed as the heap expands by using the paging process.

Expansion occurs to maintain free space on the Java heap for object allocation. By default, the heap is expanded to maintain 30% free space, but this proportion can be adjusted by setting one of the following command-line options:

- `-Xminf` determines the minimum proportion of the heap that must be free after garbage is removed.
- `-Xmaxf` determines the maximum proportion of the heap that must be free after garbage is removed.

If expansion is required, the amount of memory that the heap can expand by is controlled by the following command-line options:

- `-Xmine` sets the minimum amount that the heap can expand by.
- `-Xmaxe` sets the maximum amount that the heap can expand by. The default is unlimited expansion up to the maximum heap size (`-Xmx`).

Expansion can also be triggered if more time is being spent on GC processing than is specified by the `-Xmaxt` option. In this case, the heap expands by an amount that provides 17% more free space, within the limits imposed by the `-Xmine` and `-Xmaxe` values.

Heap contraction occurs under certain conditions and might be preceded by heap compaction. If the last three GC cycles caused a heap expansion, contraction does not occur. Otherwise, contraction is triggered when the proportion of free heap space that is specified by the `-Xmaxf` option is reached. The amount of memory to reduce the heap by is calculated to the nearest 1024-byte boundary, down to the minimum size specified for the initial Java heap (`-Xms`). To prevent heap contraction, set the `-Xmaxf` value to `1`, which sets the maximum free space allowed on the heap to 100%.

When the heap contracts, physical memory is not released unless paging is supported by the underlying operating system.

#### `balanced` GC policy

For the `balanced` GC policy, if the `-Xminf`/`-Xmaxf` and/or `-Xmint`/`-Xmaxt` criteria are not being met and this results in a heap resize, then the heap resize that occurs, happens only on non-eden heap (similar to how these options apply to tenure part for gencon).

The non-eden heap resizing occurs at the end of a GMP cycle, or global collection. At this point, heap resizing decision is made by observing both `-Xmint`/`-Xmaxt` and `-Xminf`/`-Xmaxf` and comparing them to the appropriate proportion of time spent in GC, and free heap respectively.

If either `-Xmint`/`-Xmaxt` and/or `-Xminf`/`-Xmaxf` criteria are not being met, there is no guarantee that a heap resize will occur. The heap sizing logic is looking at the following two things:

- if % of time in GC pauses is between `-Xmint`/`-Xmaxt`. If it's greater than `-Xmaxt`, the VM will try to expand the heap, if it's less than `-Xmint`, then contract it.
- if % of free heap is between `-Xminf`/`-Xmaxf`. If it's too high, i.e. greater than `-Xmaxf`(too much free), heap size will contract, if too low, i.e. lesser than `-Xminf`, it will expand.

Since these two criteria may be providing opposite recommendations (for example, lots of free memory, but high % of time in GC) causing oscillations in heap size, the `balanced` GC heap sizing logic finds a balance between these two criteria.


### Compressed references

On 64-bit systems, the VM can use compressed references to decrease the size of Java objects and make better use of the available space in the Java heap. By storing objects in a 32-bit representation, the object size is identical to that in a 32-bit VM, which creates a smaller memory footprint. These 4 byte (32-bit) compressed references are converted to 64-bit values at runtime with minimal overhead. Smaller objects enable larger heap sizes that result in less frequent garbage collection and improve memory cache utilization. Overall, the performance of 64-bit applications that store compressed rather than uncompressed 64-bit object references is significantly improved.

Compressed references are used by default when the maximum Java heap size is in the range 0 - 57 GB on AIX&reg;, Linux&reg;, and Windows&reg; systems. The upper limit is also 57 GB on z/OS&reg; systems that have APAR OA49416
installed (25 GB without APAR OA49416). All GC policies observe these limits except for the [`metronome`](gc.md#metronome-policy) policy, which can support a heap size of up to 25 GB only with compressed references.

When the VM uses compressed references, classes, threads, and monitors are stored in the lowest 4 GB of address space. However, this area of memory is also used by native libraries, the operating system, and for small Java heaps. If you receive native memory `OutOfMemoryError` exceptions in the lowest 4 GB when running with compressed references enabled, these errors might result from the lowest 4 GB of address space becoming full. Try specifying a large heap with the [`-Xmx`](xms.md) option, which puts the Java heap into a higher area of address space or using the [`-Xmcrs`](xmcrs.md) option to reserve space in the lowest 4 GB of address space for compressed references.

You can control the compressed reference allocation with the following options:

 - [`-Xgc:suballocatorCommitSize=<size>`](xgc.md#suballocatorcommitsize)
 - [`-Xgc:suballocatorIncrementSize=<size>`](xgc.md#suballocatorincrementsize)
 - [`-Xgc:suballocatorInitialSize=<size>`](xgc.md#suballocatorinitialsize)
 - [`-Xgc:suballocatorQuickAllocDisable`](xgc.md#suballocatorquickallocdisable)
 - [`-Xgc:suballocatorQuickAllocEnable`](xgc.md#suballocatorquickallocenable)

To turn off compressed references, use the [`-Xnocompressedrefs`](xcompressedrefs.md) command-line option.


<!-- ==== END OF TOPIC ==== allocation.md ==== -->
