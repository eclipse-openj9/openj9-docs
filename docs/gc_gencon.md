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


# The `gencon` policy

The "generational concurrent" garbage collection (GC) policy, `gencon`, is well-suited to most Java&trade; applications and generally performs the best. It is the default GC policy and is used if no other policy is specified.

This policy uses a concurrent mark phase combined with generational garbage collection to help minimize the time that is spent in any garbage collection pause. This policy is particularly useful for applications with many short-lived objects, such as transactional applications. Pause times can be significantly shorter than with the [`optthruput`](gc_optthruput.md) policy, while still producing good throughput. Heap fragmentation is also reduced.

## When to use this policy

The `gencon policy aims to minimize GC pause times without compromising throughput and so is probably best suited to transactional applications with many short lived objects.

## A more detailed explanation

With the `gencon` policy, the Java heap is divided into two main areas, the *nursery* area, where new objects are created and the *tenure* area, where objects are moved if they have reached *tenure age*.

The *nursery* area is subdivided into two further areas, the *allocate* space and the *survivor* space. The GC process is illustrated in the following diagram, which shows a sequence of 4 main events:

![The diagram is explained in the surrounding text](./cr/gencon_java_heap.jpg "Object allocation")

1. Objects are created in the *allocate* space within the *nursery* area.
2. The *allocate* space is full.
3. A local GC scavenge process runs and reachable objects are either copied into the *survivor* space or into the *tenure* area if they have reached *tenure age*. Any objects that can't be reached are left untouched and subsequently cleared.
4. The *allocate* and *survivor* spaces swap roles. The original *survivor* space becomes the *allocate* space where new objects are created, and the original *allocate* space becomes the *survivor* space ready for the next local GC scavenge process.

The relative sizes of the *allocate* and *survivor* spaces are dynamically adjusted by a technique called *tilting*. When the *nursery* area is first created, it is evenly divided between the *allocate* and *survivor* spaces. If, after a GC scavenge process is run, the amount of space required for the *survivor* area is comparatively small, the boundary between the two spaces is adjusted by *tilting*. For example, if the *survivor* space requires only 10% of the *nursery* area, the *tilt ratio* is adjusted to give 90% of the *nursery* area to the *allocate* space. With more space available for new objects, garbage collection can be delayed.

The *tenure age* of an object is determined by the VM and reflects the number of times that an object has been copied between the *allocate* space and the *survivor* space. The age is in the range 1 - 14 and is adjusted dynamically by the VM depending on the overall amount of space that is used in the *nursery area*. For example, if an object has a tenure age of 5, it has been copied backwards and forwards between *allocate* and *survivor* spaces 5 times. If the VM sets a *tenure age* of 5 based on the percentage of space remaining in the *nursery* area, the next scavenge moves the object from the *nursery* to the *tenure* area. You can set an initial *tenure age* with the [`-Xgc:scvTenureAge`](xgc.md#scvtenureage) option. You can also prevent the VM dynamically adjusting the *tenure age* by setting the [`Xgc:scvNoAdaptiveTenure`](xgc.md#scvnoadaptivetenure) option so that the intial age is maintained throughout the run time of the VM.

Within the *tenure* area, new objects are allocated into the small object area (SOA), which is illustrated in the earlier diagram (see 3.). A large object area (LOA) is set aside for objects greater than 64 KB that cannot be allocated into the SOA to minimize fragmentation. The LOA is allocated by default but is reduced and removed after a few GC cycles if it isn't populated. To prevent the creation of an LOA, you can specify the [`-Xnoloa`](xloa.md) option on the command line when you start your application. When the *tenure* area is close to full a global GC is triggered.

The local GC scavenge reduces pause times by freqently reclaiming memory in the *nursery* area which, for a transactional application with many short-lived objects, has the most recyclable space. However, over time the *tenure* area might become full. So, whilst a local GC scavenge process is operating on the *nursery* area, a concurrent global GC process also runs alongside normal program execution to mark and remove unreachable objects from the *tenure* area. These two GC approaches combine to provide a good trade-off between shorter pause times and consistent throughput.


The presence of an object pinned in the nursery by an active Java Native Interface (JNI) operation prevents a nursery collection from occurring. In that case, an allocation failure that would normally trigger a nursery collection forces a global collection. Applications that make extensive use of JNI-critical operations with `gencon` should closely monitor GC logs to determine whether JNI activity is impairing GC performance.

### Concurrent Scavenge

A special mode of the `gencon` policy is known as *Concurrent Scavenge* ([`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge)), which aims to minimize the time spent in stop-the-world pauses by collecting nursery garbage in parallel with running application threads. This mode can be enabled with hardware-based support and software-based support.

- **Hardware-based support: (Linux on IBM Z&reg; and z/OS&reg;)** This mode works on the IBM z14&reg; and later mainframe system with the Guarded Storage (GS) Facility. The GS Facility provides hardware-based support to detect when potentially stale references to objects are accessed by an application. This means that the garbage collector can start processing objects in parts of the heap without halting an application because the GS Facility is on hand to spot accesses to an object and send a notification. The object that was ready to be swept away can be moved, and references to it can be reset. You can read more about this mode in the following blog posts:

    - [Reducing Garbage Collection pause times with Concurrent Scavenge and the Guarded Storage Facility](https://developer.ibm.com/javasdk/2017/09/18/reducing-garbage-collection-pause-times-concurrent-scavenge-guarded-storage-facility/)
    - [How Concurrent Scavenge using the Guarded Storage Facility Works](https://developer.ibm.com/javasdk/2017/09/25/concurrent-scavenge-using-guarded-storage-facility-works/)

- **Software-based support: (64-bit: Linux on (x86-64, POWER, IBM Z&reg;), AIX&reg;, macOS&reg;, and z/OS&reg;)** With software-based support, *Concurrent Scavenge* can be enabled without any pre-requisite hardware although the performance throughput is not as good as hardware-based support.



## Invoking the `gencon` policy

For details of how to use this policy, see [`-Xgcpolicy:gencon`](xgcpolicy.md#gencon). 

`gencon` is the default GC policy employed by the VM, so you don't necessarily need to invoke it explicitly when you start your application.

See also the general [`-Xgc`](xgc.md) options that change the behavior of the GC.






<!--

## From `gc.md`

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

- **Hardware-based support: (Linux on IBM Z&reg; and z/OS&reg;)** This mode works on the IBM z14&reg; and later mainframe system with the Guarded Storage (GS) Facility. The GS Facility provides hardware-based support to detect when potentially stale references to objects are accessed by an application. This means that the garbage collector can start processing objects in parts of the heap without halting an application because the GS Facility is on hand to spot accesses to an object and send a notification. The object that was ready to be swept away can be moved, and references to it can be reset. You can read more about this mode in the following blog posts:

    - [Reducing Garbage Collection pause times with Concurrent Scavenge and the Guarded Storage Facility](https://developer.ibm.com/javasdk/2017/09/18/reducing-garbage-collection-pause-times-concurrent-scavenge-guarded-storage-facility/)
    - [How Concurrent Scavenge using the Guarded Storage Facility Works](https://developer.ibm.com/javasdk/2017/09/25/concurrent-scavenge-using-guarded-storage-facility-works/)

- **Software-based support: (64-bit: Linux on (x86-64, POWER, IBM Z&reg;), AIX&reg;, macOS&reg;, and z/OS&reg;)** With software-based support, *Concurrent Scavenge* can be enabled without any pre-requisite hardware although the performance throughput is not as good as hardware-based support.

For more information about enabling Concurrent Scavenge, see the [-Xgc:concurrentScavenge](xgc.md#concurrentscavenge) option.




## Invoking the `gencon` policy

For details of how to use this policy, see [`-Xgcpolicy:gencon`](xgcpolicy.md#gencon). (`gencon` is the default GC policy, so you not not need to invoke it explicitly.)

See also the general [`-Xgc`](xgc.md) options that change the behavior of the GC.




## From `xgcpolicy.md`

: The generational concurrent policy (default) uses a concurrent mark phase combined with generational garbage collection to help minimize the time that is spent in any garbage collection pause. This policy is particularly useful for applications with many short-lived objects, such as transactional applications. Pause times can be significantly shorter than with the `optthruput` policy, while still producing good throughput. Heap fragmentation is also reduced.



## Briggs -- not yet incorporated

The default GC policy for OpenJ9 Java is gencon, which is well-suited to most Java applications and generally performs the best. This policy configures a generational heap, split into a nursery region and a tenure region. New objects are allocated in the nursery until it is up to 90% full and a nursery collection is triggered. The default size of the nursery in the generational heap is 25% of the entire heap. This can be adjusted using the [`-Xmn`](xmn.md) (fixed nursery size) or [`-Xmns`](xmn.md) and [`-Xmnx`](xmn.md) (variable nursery size) options.

The figure below shows the generational heap at the start of a nursery collection. The tilted (evacuate) subspace that the application has been allocating new objects from has filled and must be evacuated. Young objects are evacuated to the survivor subspace of the nursery and objects that have aged out of the nursery are evacuated to tenure space.

[Generational heap]

Most objects have short lifecycles and are quickly reclaimed in nursery collections, while more persistent objects migrate from the nursery into the tenure region. Normally, new objects age into tenure after surviving 14 nursery collections, but this may be reduced if the allocation rate is high. Periodically, tenure space will fill, triggering a global collection to mark live objects and sweep dead objects from the tenure region. Nursery collections are typically frequent and of short duration, global collections should be relatively infrequent and may be of longer duration, especially if the tenure region becomes fragmented and requires compaction.

The generational (nursery) and global (tenure) collectors are stop-the-world collectors - all application threads pause for the duration of the collection. However, the concurrent marking collector runs along with application threads to monitor the rate at which objects are tenured. It periodically engages application threads to perform short bursts of marking work in anticipation of upcoming global collections so that global collection pause times are reduced.

Ideally, the nursery will be large enough to contain several iterations of the application's transient live set. Tenure space will be sufficient to hold all the application's persistent data, with room to spare to accommodate objects of intermediate lifespan. The frequency of global collections should be limited to about 1 per 100 nursery collections as a rule. Empirical feedback can be obtained by selecting a reasonable heap size with the default nursery size preset to 25% of heap and running the application through startup, steady and peak workloads with GC logging enabled. The resulting logs can then help to refine heap and nursery sizes until an acceptable level of performance has been obtained.

For optimal performance the nursery size should be tuned to reduce the rate at which transient objects are tenured. The tilt ratio metric, reported in GC logs, determines the proportion of the nursery that is reserved for allocation of new objects between nursery collections. It should be maintained as high as possible (up to 90%) over the application lifespan. If the tilt ratio is too low transient objects will be more likely to overflow into tenure space, triggering more frequent global collections. In that case, consider increasing the size of the nursery until the tilt ratio is above 70% at steady state. Alternatively, if the nursery size has been tuned to obtain a stable high tilt ratio, the rate of global collection can be reduced by increasing the size of the heap while keeping the nursery size fixed.

If global pause times are too frequently of unacceptably long duration, it may be possible to reduce maximal pause times by reducing the proportion of the heap reserved for tenure space, trading off more frequent global collections for shorter average pause times. This works best for applications that have compact live sets, that is, when objects that are allocated around the same time tend to drop out of the live set around the same time. Applications that manage live sets that are less tightly distributed over time will experience some measure of fragmentation in tenure space that will eventually force costly compaction of tenure space.

* This para already included* The presence of an object pinned in the nursery by an active JNI (Java Native Interface) critical operation will prevent a nursery collection from occurring. In that case, an allocation failure that would normally trigger a nursery collection will force a global collection. Applications that make extensive use of JNI critical operations with gencon should closely monitor GC logs to determine whether JNI activity is impairing GC performance.

-->


<!-- ==== END OF TOPIC ==== gc_gencon.md ==== -->
