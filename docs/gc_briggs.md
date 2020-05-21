# Memory management in Eclipse OpenJ9
## Garbage collection tradeoffs and tuning with OpenJ9

By Kim Briggs - Published January 11, 2019

Since releasing the OpenJ9 Java Virtual Machine (VM) to the open source community we have received many queries relating to the garbage collection (GC) policies that are available to manage the runtime heap. These typically concern heap configuration, performance, tuning capabilities, and, most importantly, guidelines for configuring and tuning the heap to best suit their application characteristics.

This article addresses some of these concerns, starting with an overview of the available GC policies and heap configurations, with emphasis on the default GC policy gencon. Take a look at two common classes of applications, and see how their characteristics map to GC policies and how these policies can be tuned to optimize application performance without memory management pain.

## Garbage collection policies

OpenJ9 presents several different GC policies, each one specialized for operation with a specific heap configuration and one or more collectors. These policies are summarized in the followng table:

| GC Policy   | Heap Configuration              | Collectors                         |
|-------------|---------------------------------|------------------------------------|
| Gencon      | Generational (nursery/tenure)   | Scavenger, Concurrent Mark, Global |
| Balanced    | Generational (multiple regions) | Incremental Generational           |
| OptAvgPause | Flat                            | Concurrent Mark, Global            |
| OptThruput  | Flat                            | Global                             |
| Metronome   | Segregated                      | Realtime                           |


All OpenJ9 GC policies support compressed references on 64-bit platforms, which compresses heap pointers to 32 bits if the total heap size does not exceed 63gb. Applications that require more heap space can forgo compressed pointers and select any heap size within the bounds imposed by the operating system and available system RAM.

OpenJ9 Java applications can determine the initial and maximal sizes of the heap for any policy using the standard `-Xms` and `-Xmx` command line options to set the initial and maximal heap extent.


## Policy selection and tuning

The selection of an appropriate GC policy for the OpenJ9 JVM and tuning the heap is guided first and foremost by a good understanding of the application dynamics and observation of how the application interacts with the heap during startup and at steady state. To help with this, all OpenJ9 GC policies are instrumented to collect a wide range of GC-related metric data for reporting in a GC log.

To enable GC logging for the OpenJ9 Java runtime, include the -verbose:gc command line option. The instrumented metric data can then be interactively visualized when the GC log is loaded into the [Garbage Collector and Memory Visualizer (GCMV) plugin for Eclipse](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv). OpenJ9 Java GC logs can also be analyzed by some online services, such as [GCEasy](https://gceasy.io/).

### Gencon

The default GC policy for OpenJ9 Java is gencon, which is well-suited to most Java applications and generally performs the best. This policy configures a generational heap, split into a nursery region and a tenure region. New objects are allocated in the nursery until it is up to 90% full and a nursery collection is triggered. The default size of the nursery in the generational heap is 25% of the entire heap. This can be adjusted using the [`-Xmn`](xmn.md) (fixed nursery size) or [`-Xmns`](xmn.md) and [`-Xmnx`](xmn.md) (variable nursery size) options.

The figure below shows the generational heap at the start of a nursery collection. The tilted (evacuate) subspace that the application has been allocating new objects from has filled and must be evacuated. Young objects are evacuated to the survivor subspace of the nursery and objects that have aged out of the nursery are evacuated to tenure space.

[Generational heap]

Most objects have short lifecycles and are quickly reclaimed in nursery collections, while more persistent objects migrate from the nursery into the tenure region. Normally, new objects age into tenure after surviving 14 nursery collections, but this may be reduced if the allocation rate is high. Periodically, tenure space will fill, triggering a global collection to mark live objects and sweep dead objects from the tenure region. Nursery collections are typically frequent and of short duration, global collections should be relatively infrequent and may be of longer duration, especially if the tenure region becomes fragmented and requires compaction.

The generational (nursery) and global (tenure) collectors are stop-the-world collectors - all application threads pause for the duration of the collection. However, the concurrent marking collector runs along with application threads to monitor the rate at which objects are tenured. It periodically engages application threads to perform short bursts of marking work in anticipation of upcoming global collections so that global collection pause times are reduced.

Ideally, the nursery will be large enough to contain several iterations of the application's transient live set. Tenure space will be sufficient to hold all the application's persistent data, with room to spare to accommodate objects of intermediate lifespan. The frequency of global collections should be limited to about 1 per 100 nursery collections as a rule. Empirical feedback can be obtained by selecting a reasonable heap size with the default nursery size preset to 25% of heap and running the application through startup, steady and peak workloads with GC logging enabled. The resulting logs can then help to refine heap and nursery sizes until an acceptable level of performance has been obtained.

For optimal performance the nursery size should be tuned to reduce the rate at which transient objects are tenured. The tilt ratio metric, reported in GC logs, determines the proportion of the nursery that is reserved for allocation of new objects between nursery collections. It should be maintained as high as possible (up to 90%) over the application lifespan. If the tilt ratio is too low transient objects will be more likely to overflow into tenure space, triggering more frequent global collections. In that case, consider increasing the size of the nursery until the tilt ratio is above 70% at steady state. Alternatively, if the nursery size has been tuned to obtain a stable high tilt ratio, the rate of global collection can be reduced by increasing the size of the heap while keeping the nursery size fixed.

If global pause times are too frequently of unacceptably long duration, it may be possible to reduce maximal pause times by reducing the proportion of the heap reserved for tenure space, trading off more frequent global collections for shorter average pause times. This works best for applications that have compact live sets, that is, when objects that are allocated around the same time tend to drop out of the live set around the same time. Applications that manage live sets that are less tightly distributed over time will experience some measure of fragmentation in tenure space that will eventually force costly compaction of tenure space.

The presence of an object pinned in the nursery by an active JNI (Java Native Interface) critical operation will prevent a nursery collection from occurring. In that case, an allocation failure that would normally trigger a nursery collection will force a global collection. Applications that make extensive use of JNI critical operations with gencon should closely monitor GC logs to determine whether JNI activity is impairing GC performance.

#### Case study

The figure below is a GCMV plot of gencon metrics from an XML validation service during startup and at steady state. Heap size is fixed at 1280 Mb, and the default 25% (320 Mb) is reserved for the nursery. This shows about >1000 nursery collections (Scavenge time) with average duration about 41 ms (about 20% of runtime).

[GMCV plot of gencon metrics]

There are also 10 global collections, but these are of short duration because most of the tenured objects are transients that have been pushed out of the nursery from validation cycles that span nursery collections. This is evident from the unstable tenure age and tilt ratio dynamics. These tenured transients are quickly swept away, and global collections account for <1% of total GC pause time. In this case, the total time consumed in nursery collections can be reduced by doubling the nursery size to 640 Mb, while maintaining the total heap size at 1280 Mb. With this adjustment the service runs more smoothly, as seen in the GCMV plot below.

[Adjusted GMCV plot of gencon metrics]

In this configuration, most validation cycles complete while the objects in their live sets are still in the nursery and there is negligible overflow of transient objects into tenure space. Tenure age dynamics have a stable mode around 4 and the tilt ratio is pushing the ceiling (90%), suggesting that almost all objects drop out of the live set after no more than 4 nursery collections and less than 10% of the live set survives with each generation. Over the sampled runtime, global cycles were eliminated, the number of nursery collections was reduced to <500 and the mean nursery collection duration was reduced to about 38 ms. Less than 8% of total runtime was consumed by GC in this case.

### Balanced

The balanced policy accommodates applications that require large heaps (>64 Mb) on 64-bit platforms. This policy may be a good alternative for applications that experience unacceptable pause times with gencon. Also, the incremental generational collector can leverage Non-Uniform Memory Architecture (NUMA) capabilities on supporting platforms to optimize collection throughput.

An incremental generational collector is used to manage a heap that is split into a large number, typically 1 or 2 thousand, of regions. Each region is assigned an age ranging from 0 (youngest) to 24 (oldest). New objects are allocated from a subset of regions of age 0 or 1 comprising eden space. The default size of eden space in the incremental generational heap is 25% of the entire heap. This can be adjusted using the Xmn (fixed eden size) or Xmns and Xmnx (variable eden size) options. Regions of intermediate age serve as survivor regions to receive live objects copied forwarded from younger regions. Regions of maximal age (24) are collectively analogous to tenure space within the generational heap.

A conceptual view of the incremental generational heap is shown in the figure below.

[Conceptual view of incremental generational heap]

The incremental generational collector proceeds in three main phases or cycles. Generational copying occurs in incremental partial global cycles (PGCs) when a subset of regions is selected for clearing. This always includes eden regions but older regions of age <24 may also be included if they are in an age group that has a historically high mortality rate. Objects within collectible regions of intermediate age N are evacuated to survivor regions of age N+1. Regions of age 24 are never included in PGC collection sets but may be included for compaction in global GC (GGC) cycles, which mark and sweep the whole heap and compact regions that need defragmentation.

Over time, transient objects drop out of the live set and viable survivor regions become scarce. At some point a global mark phase (GMP) will commence to mark and sweep the entire heap. Each increment of the GMP is followed by an increment of PGC, so regions that are left sparse after sweeping can be collected, cleared, and recycled during and after GMP.

The balanced GC policy uses a special representation for arrays that should be considered when selecting a GC policy. Arrays that can be contained within a single region are represented contiguously in the heap, as with gencon. Larger arrays have discontiguous representation in the heap, consisting of a spine that contains the object header and an array of 0 or more pointers to arraylets, where each arraylet occupies an entire region. With gencon, large arrays always have contiguous representation and must be flipped through the nursery, which is to say they will be copied up to 14 times if they reach tenure age. With the balanced GC policy, only the array spine and remainder array elements contiguous with the spine need to be copied in PGC cycles.

As with gencon, objects that are pinned for JNI access prevent the containing regions from being included in PGC collection sets. Additionally, and importantly for applications that export large arrays through JNI, the discontiguous representation of large arrays requires that the entire array must be reconstituted contiguously for JNI access and this may place a crippling load on the system if this type of access is frequently used.

#### Case study

The GCMV plot below illustrates the operation of the balanced policy in a well-tuned incremental generational heap. In this case the application is a database server maintaining an in-memory database that is loaded when the server starts up. The solid green line (Free heap after collection) represents the volume of free heap included in empty regions only - free memory in partially full or fragmented regions in not included in this metric.

[GCMV plot of the operation of the balanced policy in a well-tuned incremental generational heap]

The objects associated with the in-memory database are instantiated early in eden space and quickly migrate into progressively older regions until they reach maximal age, accounting for the relatively high volume of material copied from eden and intermediate regions during the startup phase (30-40 seconds). At steady state, the server processes a low volume of activity where short-lived transient objects are allocated to support database queries and batch updates. These operations impact the core database, resulting is a relatively low rate of migration of persistent objects to maximal age and a moderate rate of turnover in eden space.

Transient objects that escape the nursery accumulate in regions of intermediate age that are occasionally cleared in PGC cycles. Over time, intermediate regions fill up until a series of incremental global mark phase (GMP) cycles is initiated. These alternate with PGC cycles until marking is complete, at which point the entire heap is swept to clear dead objects from all regions. This will leave some regions sparsely populated with live objects. Subsequent PGC cycles continue to clear these regions, freeing up more heap regions for recycling back into eden space. This activity is reflected in GCMV plot by the continued increase of free heap regions after each GMP phase completes.

See [Balanced garbage collection as a new option](https://www.ibm.com/developerworks/websphere/techjournal/1108_sciampacone/1108_sciampacone.html) for more information on the balanced GC policy.

### Optavgpause and optthruput

The optavgpause and optthruput policies use the global collector to manage a flat heap comprised of a single region. The application runs until the heap is exhausted and the global collector runs to mark and sweep the heap. Additionally, if the heap becomes fragmented, the global collector will compact the heap.

These policies are best suited to short-lived applications and to long-running services involving concurrent sessions that have short lifespans. Short-lived applications with adequate heap sizes will usually complete without compaction, and the flat heap fragments more slowly when session-bound objects are allocated and drop out of the live set in short overlapping clusters.

- The optavgpause policy employs concurrent marking as for gencon to anticipate global collections and initiate some marking before the stop-the-world global collection phase. This reduces the likelihood of long interruptions of service due to GC activity.

- The optthruput policy inhibits concurrent marking and is best suited for applications that can tolerate longer pauses to obtain better overall throughput.

#### Case study

The xml validation service presented for gencon runs well with optavgpause or optthrupt, because concurrent sets of live objects per document are short-lived and leave little trace in the heap when validation is complete. The GCMV plot below shows this service running for about 6 minutes in a 1280 Mb flat heap with optavgpause.

[GCMV plot of XML validation service]

There were about 600 global collections of short duration (around 40 ms), maintaining a regular volume of free heap at about 1100 Mb at steady state and consuming less than 7% of total runtime. The flat line pattern for free heap suggests that work cycles (per document in this case) are well contained and leave little residue in the heap, so this service could run with either optavgpause or optthruput for a long time without incurring compaction.

### Metronome

The metronome policy is available on 64-bit AIX&reg; and Linux&tm;/x86_64 platforms. It is intended for use in applications that require a precise upper bound on collection pause times. The realtime collector runs in short interruptible bursts with a preset upper bound on pause duration.

The realtime heap is allocated as a contiguous range of RAM partitioned into small regions of equal size, usually about 64kb. Arrays are represented as arraylets with a spine pointing to a series of regions containing the array elements. Each region is either empty or contains only objects of the same size or an arraylet. This organization simplifies new object allocation and consolidation of free heap space, allowing the realtime collector to maintain GC throughput while supporting a consistent service level. As for the balanced GC policy, JNI access to array data may involve reconstituting arraylets as contiguous arrays, which may severely degrade realtime operation.

Pause duration is limited by selecting a target pause time using -Xgc:targetPauseTime=N (milliseconds). Application bandwidth is reserved by selecting a target utilization with Xgc:targetUtilization=T (%), expressed as the percentage of run time required to be available to the application between collection pauses. Reasonable targets for utilization are typically in the range 50-80% but may be set as high as 90%, depending on the object allocation rate characteristics of the application.

In practice, the realtime collector may not be able maintain the heap with selected constraints and it may be necessary to adjust heap size or target utilization or pause time to achieve an acceptable runtime configuration.

See [Real-time garbage collection](https://www.ibm.com/developerworks/library/j-rtj4/index.html) for more information on the realtime heap and metronome GC policy.

## Choosing a GC policy

The default GC policy for OpenJ9 Java is gencon, which manages a generational heap. This is OpenJ9's most performant collector, and it can be tuned to obtain smooth runtime characteristics for most applications. But some applications have atypical dynamics or concerns that make them better suited for operation with an alternative collector.

One issue is frequent use of JNI critical operations to export on-heap structures for access by native code, which may inhibit gencon nursery collections and exclude some balanced regions form PGC collection sets. Another is the presence of very large objects, typically arrays, in the gencon nursery. These objects are repeatedly flipped between the two nursery subspaces until they drop out of the live set or are tenured. Applications that make heavy use of large arrays may realize better overall performance using the balanced collection policy, as the balanced collector can gracefully age large arrays without copying them from one generation to the next.

Applications that make frequent use of classloaders may experience suboptimal performance with gencon because these objects can only be unloaded during global collections. Thus, even short-lived interactions with classloaders may cause them to accumulate in tenure space, leading to more frequent global collections of longer duration. The balanced policy can unload classloaders incrementally and should be considered for services that experience classloader-related issues with gencon. The balanced policy may provide more stable and reliable performance than gencon, mainly owing to its incremental approach to defragmentation. Applications that experience unacceptably long collection pauses due to compaction with gencon may run more smoothly with the balanced policy.

Applications that have work cycles with well-contained working sets, where objects that are allocated together tend to drop out of the live set together, may be well-suited for operation with a flat heap using the optavgpause or optthruput policy.

