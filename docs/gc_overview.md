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


# Garbage collection

To prevent applications running out of memory, objects in the Java heap that are no longer required must be reclaimed. This process is known as *garbage collection* (GC). When garbage is collected, the garbage collector must obtain exclusive access to the heap, which causes an application to pause while the cleanup is done. This pause is often referred to as a *stop-the-world* (STW) pause because an application must halt until the process completes. In general, the first step in the GC process is to *mark* the objects that are reachable, which means they are still in use. The next step is to *sweep* away the unmarked objects to reclaim memory. The final step is to *compact* the heap if the heap is badly fragmented.

A GC cycle is a repeatable process that involves a set of GC operations. These operations process all or parts of the Java heap. When operating on the
whole of the Java heap, the cycle is referred to as a *global GC cycle*; When operating on part of the heap, the cycle is referred to as a *partial GC cycle*.

A global GC cycle can be triggered explicitly or implicitly according to the following rules:

- A global GC cycle is triggered implicitly if it occurs because of internal mechanisms, such as an allocation failure or a *taxation* threshold being reached.
- A global GC cycle is triggered explicitly if it is started directly by an application calling `System.gc()` or indirectly, for example when requesting a heap dump.

 The `System.gc()` call triggers the GC cycle twice internally to clear unreachable objects that were not identified during the first GC cycle. This call also triggers finalization to release resources that were used by the unreachable objects so that the resources can be reused.

Partial GC cycles are triggered only implicitly under the control of a particular GC policy. For more information about the GC policies available with Eclipse OpenJ9&trade;, see [Garbage collection policies](gc.md).

The GC process is designed to operate without intervention from an application. Developers should not attempt to predict GC behavior, for example, by making calls to `System.gc()` to trigger a cycle or by using finalizers to clean up objects in memory. Such actions might degrade the performance of an application.

## GC operations

GC operations run discrete functions on the Java heap. For example, a mark operation traces all objects in the heap to determine which ones are reachable. A sweep operation runs to clear away unreachable objects. Together, a mark and sweep operation are capable of reclaiming used memory as part of a GC cycle. Not all GC cycles include operations to reclaim memory. For example, the `balanced` GC policy involves a global cycle that includes only a mark operation; reclaiming the memory with a sweep operation occurs as part of a separate partial GC cycle that *evacuates* younger regions and defragments older regions.

A GC operation might complete in one step, or it might involve multiple steps. For example, a mark operation consists of three steps, as described in the following section.

### GC mark operation

A mark operation identifies which objects on the Java heap are reachable from outside of the heap and which objects are unreachable. Reachable objects are in use and must be retained, whereas unreachable objects are not in use and can be cleared away as garbage.

The process of marking involves a bit array called a *mark map* that is allocated by the VM at startup, based on the maximum heap size setting. Each bit in the mark map corresponds to 8 bytes of heap space. When an object is *marked*, its location in the heap is recorded by setting the appropriate bit in the mark map.

A mark operation can be broken down into the following steps:

1. Initial

    A root object is an object that is accessible from outside of the heap such as a stack, class static field, or JNI reference. For other objects in the heap to be reachable, they must retain a connection from a root object. In the initial step, tracing identifies all root objects by running a root scan. Root objects are pushed onto a work stack for processing in the next step.

2. Main

    The list of reachable root objects in the work stack is recursively traced for references to other objects in the heap. Objects that are not marked are new objects and these are added to the work stack. If an object is reachable, the appropriate bit is set in the mark map.

3. Final

    The final step processes weakly reachable roots such as finalizable objects, weak references, monitor sets, and string sets. For more information about the processing of *soft*, *weak*, and *phantom* references, see [Weak reference processing](#weak-reference-processing).

In general, helper threads are used in parallel to speed up mark operations on the heap. The helper threads share the work stack with the application thread and are involved in identifying root objects, tracing objects in the heap, and updating the mark map. By default, the number of helper threads is based on the number of CPUs reported by the operating system. You can control the number of helper threads available by specifying the [-Xgcthreads](xgcthreads.md) command line option when starting your application.

In a verbose GC log, this operation is shown by the `<gc-op type="mark">` XML element. For more information, see [Verbose GC logs](vgclog.md).

#### Concurrent mark processing

A mark operation can run with exclusive access to the heap, which requires application threads to pause while processing takes place. Alternatively, it can run concurrently with application threads to avoid pauses in application processing.

With concurrent mark, the process of root scanning is handed over to application stack threads, which populate the work stack with root objects in their stack. The root objects in the work stack are then traced by a background thread and by each application thread during a heap lock allocation to find reachable objects and update the mark map. Because the mark operation runs concurrently with application threads, any changes to objects that are already traced must be updated. This process works by using a write barrier that can flag the update and trigger a further scan of part of the heap.

To track updates to objects, concurrent mark operations use single-byte *cards* in a *card table*. Each card corresponds to a 512-byte section of the Java heap. When an object is updated, the start address for the object in the heap is marked on the appropriate card. These cards are used to determine what must be retraced later in the GC cycle.

A GC cycle that includes concurrent mark operations aims to trace all reachable objects and complete processing at the same time as the heap is exhausted. Continuous adjustments are made to the start time of each cycle to get as close to heap exhaustion as possible. When the heap is exhausted a sweep operation is able to reclaim memory. This operation requires a STW pause. Before sweep operations start, the root objects are rescanned and the cards are checked to determine which areas of memory must be retraced.

An advantage of concurrent mark operations over STW mark operations is reduced pause times, because the garbage collector is able to identify garbage without halting application threads. Pause times are also more consistent because the collector is able to tune start times to maximize heap usage.

Disadvantages of concurrent mark operations include the additional CPU for operating the write barrier and additional work for application threads to trace objects when requesting a heap lock.

Concurrent mark operations are used by the [`gencon` GC policy](gc.md#gencon-policy-default) and the [`optavgpause` GC policy](gc.md#optavgpause-policy).

#### Incremental concurrent mark processing

Incremental concurrent mark processing evens out pause times by avoiding global STW garbage collections. This type of marking is also known as the *global mark phase*, whereby mark operations take place incrementally across the entire heap. The global mark operations are interleaved with a partial GC cycle that is responsible for moving objects and clearing unreachable objects in the heap.

These operations also use mark map in a card table to manage updates to objects that occur whilst mark operations are in progress. However, unlike the concurrent mark operations used by other policies, application threads are not involved in tracing objects; only background threads are used to trace objects and update the mark map.

Incremental concurrent mark operations are used by the [`balanced GC policy`](gc.md#balanced-policy).

### GC sweep operation

The purpose of a sweep operation is to identify memory that can be reclaimed for object allocation and update a central record, known as the *freelist*.

sweep operations occur in 2 steps:

1. Initial

    This step analyzes the mark map for free memory.

2. Final

    Based on the analysis, the sections of the heap are connected to the freelist.

As with mark operations, multiple helper threads can be used to sweep the Java heap in parallel to speed up processing times. Because these helper threads are the same ones that are used for parallel mark operations, the number of threads can be controlled by using the [-Xgcthreads](xgcthreads.md) option.

Parallel sweep operations run on 256 KB sections of the heap. Each helper thread scans a section at a time. The results are stored and used to generate a freelist of empty regions.

In a verbose GC log, this operation is shown by the `<gc-op type="sweep">` XML element. For more information, see [Verbose GC logs](vgclog.md).


#### Concurrent sweep processing

Concurrent sweep processing works in tandem with concurrent mark processing and uses the same mark map. Concurrent sweep operations start after a STW collection and complete a section of the heap before concurrent mark operations continue.

Concurrent sweep is used by the `optavgpause` GC policy.

### GC scavenge operation

A GC *scavenge* operation is triggered by an allocation failure in the *nursery* area of the heap. The operation occurs in the following 3 steps:

1. Initial

    A root object is an object that is accessible from outside of the heap such as a stack, class static field, or JNI reference. For other objects in the heap to be reachable, they must retain a connection from a root object. In the initial step, tracing identifies all root objects by running a root scan. Root objects are pushed onto a work stack for processing in the next step.

2. Main

    The list of reachable root objects in the work stack is recursively traced for references to other objects in the heap by using the *hierarchical scan ordering* mode ([`-Xgc:hierarchicalScanOrdering`](xgc.md#hierarchicalscanordering)). If new objects are found, they are added to the work stack. If an object is reachable, it is copied from the *allocate* space to the *survivor* space in the nursery area or to the *tenure* area if the object has reached a particular age.

3. Final

    The final step processes weakly reachable roots such as finalizable objects, weak references, monitor sets, and string sets. For more information about the processing of soft, weak, and phantom references, see [Weak reference processing](#weak-reference-processing).

In a verbose GC log, this operation is shown by the `<gc-op type="scavenge">` XML element. For more information, see [Verbose GC logs](vgclog.md).

The scavenge operation is used by the [`gencon` GC policy](gc.md#gencon-policy-default).

### GC copy forward operation

A GC *copy forward* operation is similar to a scavenge operation but is triggered by a taxation threshold being reached. The operation occurs in the following 3 steps:

1. Initial

    A root object is an object that is accessible from outside of the heap such as a stack, class static field, or JNI reference. For other objects in the heap to be reachable, they must retain a connection from a root object. In the initial step, tracing identifies all root objects by running a root scan. Root objects are pushed onto a work stack for processing in the next step.

2. Main

    The list of reachable root objects in the work stack is recursively traced for references to other objects in the heap by using *dynamic breadth first scan ordering* mode ([`-Xgc:dynamicBreadthFirstScanOrdering`](xgc.md#dynamicbreadthfirstscanordering)). If new objects are found, they are added to the work stack. If an object is reachable, it is moved to another region of the same age or to an empty region of the same age in the heap. The age of all regions in the heap is then incremented by 1, except for the oldest region (age 24).

3. Final

    The final step processes weakly reachable roots such as finalizable objects, weak references, monitor sets, and string sets. For more information about the processing of soft, weak, and phantom references, see [Weak reference processing](#weak-reference-processing).

The operation aims to empty or *evacuate* fragmented regions that can then be reclaimed for new object allocation.

In a verbose GC log, this operation is shown by the `<gc-op type="copy forward">` XML element. For more information, see [Verbose GC logs](vgclog.md).

The copy forward operation is used by the [`balanced GC policy`](gc.md#balanced-policy).

### GC classunloading operation

The *classunloading* operation is single threaded, not parallel threaded.

In a verbose GC log, this operation is shown by the `<gc-op type="classunload">` XML element. For more information, see [Verbose GC logs](vgclog.md).


### GC compact operation

Compaction of the heap is an expensive operation because when objects are moved to defragment the heap, the references to each object change. Therefore, compact operations do not occur by default but only when the following triggers occur:

- The `-Xcompactgc` option is specified on the command line.
- After sweeping the heap, there is not enough free space available to satisfy an allocation request.
- A `System.gc()` is requested and the last allocation failure that triggered a global GC cycle did not compact or `-Xcompactexplicitgc` is specified.
- At least half the previously available memory has been consumed by TLH allocations (ensuring an accurate sample) and the average TLH size falls to less than 1024 bytes.
- The largest object that the `gencon` GC policy failed to move to the tenure area in the most recent scavenge operation is bigger than the largest free slot in the tenure area.
- The heap is fully expanded and less than 4% of the tenure area is free.
- Less than 128 KB of the heap is free.

The following two options can be used to control compaction:

- [`-Xcompactgc`](xcompactgc.md) forces compaction of the heap.
- [`-Xnocompactgc`](xcompactgc.md) avoids compaction of the heap as a result of all the triggers shown in the preceding list. However a compaction can still occur in rare circumstances.

In a verbose GC log, this operation is shown by the `<gc-op type="compact">` XML element. For more information, see [Verbose GC logs](vgclog.md).

## Weak reference processing

Weak reference processing includes soft references, weak references, and phantom references. These references are created by the user for specific use cases and allow
some level of interaction with the garbage collector. For example, a soft reference to an object allows that object to persist in memory for a longer period of time before being
cleared. For example, a software cache. The garbage collector handles the reference types in the order shown and with the behavior detailed in the following table:


| Reference type | Class                            | Garbage collector behavior |
|----------------|----------------------------------|----------------------------|
| soft         | `java.lang.ref.SoftReference`    | A soft reference is cleared only when its *referent* is not marked for a number of GC cycles or if space on the heap is likely to cause an out of memory error. Use the [-Xsoftrefthreshold](xsoftrefthreshold.md) option to control the collection of soft reference objects. |
| weak         | `java.lang.ref.WeakReference`    | A weak reference is cleared as soon as its *referent* is not marked by a GC cycle. |
| phantom      | `java.lang.ref.PhantomReference` | A phantom reference is cleared automatically as soon as its *referent* is not marked by a GC cycle. The cleared reference is then added to the associated reference queue at the same time or later. |

If your application uses the Java Native Interface (JNI) to interact with other application types, you can also create weak JNI object references. These references have a similar life cycle to a weak Java reference. The garbage collector processes weak JNI references after all other Java reference types.


<!-- ==== END OF TOPIC ==== gc_overview.md ==== -->
