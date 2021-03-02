# `balanced` examples

The [`balanced`](gc.md#balanced-policy) policy (`-Xgcpolicy:balanced`) uses two types of cycle to perform GC – a partial GC cycle and a global GC *mark* cycle. The `balanced` policy GC may have to perform a third type of cycle - a global cycle - to reclaim memory after an allocation failure that has resulted from tight memory conditions.
 
Note: For more information about the cycles used in a particular policy, see [garbage collection policies](gc#garbage-collection-policies).

The start of a `balanced` cycle is recorded in the log by the following elements and attributes:

| GC cycle or increment | value of `type` attribute of the `<cycle-start>` and `<cycle-end` elements| Element that logs the cycle or increment trigger| Triggering reason|
|----------|----------------------------|------------------------------|------------------|
|Global mark cycle| `global mark phase`| `<allocation-taxation>` | taxation threshold reached. |
| Global mark increment of global mark cycle| `GMP work packet processing` | `<concurrent-start>` `<concurrent-end>` |
partial cycle| `partial gc`           | `<allocation-taxation>`                 |taxation threshold reached|
|global cycle|     `global garbage collect`    | `af-start`, `<af-end>`           |An allocation failure. Occurs under tight memory conditions. Cycle runs very rarely. |

To locate a particular type of cycle, you can search for the `type` attribute of the `<cycle-start>` and `<cycle-end>` elements.

Partial GC cycles, global mark cycles, and global cycles set the allocation taxation threshold at the end of the cycle. If a partial GC cycle is not run between global mark phase increments of a global *mark* cycle, the allocation taxation threshold is set to trigger the next cycle when the *eden* space is full. Specifically, at the end of the partial gc cycle, the allocation threshold is set to be equal to the size of the *eden* space.

If a partial GC cycle is run within a global *mark* cycle, the allocation taxation threshold is set to be smaller than the size of the *eden* space to allow for global mark phase increments to run in between partial GC cycles. Specifically, when the partial GC cycle completes within a global *mark* cycle, the allocation taxation threshold is set to be half the size of the *eden* space. The increment, which could be a partial GC cycle or could be a GMP increment, runs when only half of the *eden* space is available as free memory.

Note: For more information about GC increments, see [GC increments and interleaving](vglog.md#gc-increments-and-interleaving).

You an analyze the increments and operations that are associated with a particular type of cycle by locating and interpreting the elements in the following table:

| GC process             | start and end XML elements   | Details |
|------------------------|------------------------------|------------------------------|
|GC cycle                |`<cycle-start>`, `<cycle-end>`| The start and end of a GC cycle.|
|GC STW increment        |`<gc-start>`, `<gc-end>`      | The start and end of a GC increment or sub-increment that begins with a *stop-the-world* pause. For example a `global mark phase` global mark GC cycle increment or a partial GC cycle increment.
|GC concurrent increment        | `<concurrent-start>`, `<concurrent-end>` | The start of the concurrent *global mark phase work packet processing* sub-increments of the global mark cycle|
|GC operations and phases| `<gc-op>`                    | A GC operation such as mark or sweep, or a suboperation ‘phase’ such as class unload. |

Note: For more information about the XML structure of GC cycles, see [GC cycles](vgclog.md#gc-cycles).

The following sections use log excerpts to show how the different types of give details about `balanced` cycle are logged. 

### `balanced` Partial GC cycle

The following example is taken from a [`balanced` partial GC cycle](gc.md#balanced-policy) verbose GC log. The output is broken down into sections to explain the GC processing that is taking place.

To search for a `balanced` partial GC cycle, you can search for the `type` attribute value `partial gc` in `<cycle-start>` and `<cycle-end>` elements.

The `balanced` policy’s partial GC cycle reclaims memory in the heap for allocation of new objects by reducing the number of used regions. The partial GC cycle always reduces used regions in the *eden* space and may also reclaim memory from older regions. Multiple partial GC cycles often run in between global mark phase increments of the [`balanced` global mark cycle](vgclog_examples.md#balanced-global-mark-cycle).  

All the partial GC cycle's operations run during a single *stop-the-world* pause:

|GC Operations | GC increment | *stop-the-world* or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|-------------------------------|--------------------------------------|---------------------------------------------------------------------------|
|copy forward, optionally class unload, sweep, compact |Single        | *stop-the-world*              | `<gc-start>`, `<gc-end>`| `<gc-op'>` |ADD| 

The `balanced` partial GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some child elements are omitted for clarity:

CHECK THIS ILLUSTRATION

```xml
<exclusive-start/>                       (STW pause starts)

<allocation-taxation/>                   (memory threshold trigger recorded)

<cycle-start/>                            (partial cycle starts)

<gc-start/>                             (partial  cycle increment starts)

<mem-info>                            (memory status before operations)

 <mem></mem>                         (status of different types of memory)

</mem-info>         

</gc-start> 

<allocation-stats>                      (snapshot of application threads status...
                                          ... taken before the cycle starts)
<gc-op> type="copy forward" </gc-op>    (copy forward operation completed)

<gc-op> type="class unload" </gc-op>    (class unload operation completed)

<gc-op> type="sweep" </gc-op>           (sweep operation completed)

<gc-op> type="compact" </gc-op>         (compact operation completed)

<gc-end>                                (partial cycle increment ends)

<mem-info>                            (memory status after operations)
          
<mem></mem>                         (status of different types of memory)

</mem-info>         

</gc-end>

<cycle-end>                               (partial cycle ends)

<exclusive-end>                           (STW pause ends)
```

When the `balanced` partial GC cycle is triggered, the GC runs an STW pause. Application threads are halted to give the garbage collector exclusive access to the heap. The STW pause is recorded in the logs by the `<exclusive-start>` element. 

```xml
<exclusive-start id="184" timestamp="2021-02-26T11:11:42.310" intervalms="3745.790">
  <response-info timems="3.138" idlems="1.056" threads="2" lastid="00000000006EDE00" lastname="RunDataWriter.1" />
</exclusive-start>
```

A `balanced` partial GC cycle is triggered when the region count for the *eden* space reaches a *taxation* threshold. At this threshold, the GC 'taxes' the application threads, who have been 'paid' with memory allocation, to run some GC work - in this case, a partial GC cycle.The logs record this trigger reason by using the`<allocation-taxation>` element.

```xml
<allocation-taxation id="185" taxation-threshold="2147483648" timestamp="2021-02-26T11:11:42.311" intervalms="3745.785" />
```

Details about the start of the cycle are recorded by the `<cycle-start>` element. The cycle is recorded as a `partial gc` with an `id=336`. Any subsequent elements associated with this cycle have a `contextid=186` to match the cycle's `id`. You can use this `contextid` value to distinguish the partial GC cycle increment and operations from interleaving increments and operations from other `balanced` cycles, such as global *mark* cycles.

```xml
<cycle-start id="186" type="partial gc" contextid="0" timestamp="2021-02-26T11:11:42.311" intervalms="3745.805" />

```

The partial cycle begins its one and only GC increment, recorded using the `<gc-start>` element. You can understand the effect that the increment operations have on the heap by comparing snapshots of the memory taken at the start and the end of the increment.  The child elements `<mem-info>` and `<mem>` of the `<gc-start>` and `<gc-end>` elements record the amount of memory available and where it is located in the heap.

```xml
<gc-start id="187" type="partial gc" contextid="186" timestamp="2021-02-26T11:11:42.311">
  <mem-info id="188" free="897581056" total="4294967296" percent="20">
    <mem type="eden" free="0" total="2147483648" percent="0" />
    <arraylet-primitive objects="1" leaves="4" largest="4" />
    <remembered-set count="2749664" freebytes="160705664" totalbytes="171704320" percent="93" regionsoverflowed="1" regionsstable="12" regionsrebuilding="0"/>
  </mem-info>
</gc-start>
```

As expected, at the start of this increment, the eden region is full. 856 MB (897581056 B) of the total 2048 MB (2147483648 B) *eden* space is available as free memory. 856MB (897,581,056 B) of the total 4096MB (4294,967,296 B) heap is available as free memory.

The status of the remembered set, a metastructure specific to openJ9 JVM generational garbage collectors, is reported by the `<remembered-set>` element. The *remembered set* metastructure keeps track of any object references that cross different regions. Each region corresponds to a single remembered set. The partial gc cycle uses and prunes the remembered sets. 

At the start of the partial gc cycle, the *remembered set* is using 93% of its available memory capacity, with 153.26 MB (160705664 B) available. The `regionsoverflowed` value records the number of regions that have exceeded the non-heap memory allocation reserved for the *remembered set*. The partial GC cycle cannot reclaim memory from these overflow regions. The partial GC cycle also cannot reclaim memory from any regions whose *remembered set* is being rebuilt by an increment of a global mark cycle that is in progress.

The following element, `<allocation-stats>`, records information about the state of application threads before the start of the current cycle. For this example, the thread `Group1.Backend.CompositeBackend{Tier1}.7` was the largest consumer of memory. 

```xml
<allocation-stats totalBytes="2146431360" >
  <allocated-bytes non-tlh="96417448" tlh="2050013912" arrayletleaf="0"/>
  <largest-consumer threadName="Group1.Backend.CompositeBackend{Tier1}.7" threadId="00000000007E9300" bytes="275750048" />
</allocation-stats>
```

The operations of the GC increment are run and details are recorded in the `<gc-op> elements`. The logs show that this increment begins with a copy forward operation followed by a class unload. Other `balanced` partial GC cycles can also include sweep and compact operations. For details about the operations involved in `balanced` partial GC cycles, see [Balanced Policy - GC Processing](gc.md#balanced-policy). 

```xml
<gc-op id="189" type="copy forward" timems="400.637" contextid="186" timestamp="2021-02-26T11:11:42.713">
  <memory-copied type="eden" objects="4434622" bytes="119281928" bytesdiscarded="1382272" />
  <memory-copied type="other" objects="8847813" bytes="244414264" bytesdiscarded="6243176" />
  <memory-cardclean objects="1446970" bytes="64143048" />
  <regions eden="512" other="80" />
  <remembered-set-cleared processed="2435794" cleared="887129" durationms="8.667" />
  <finalization candidates="66" enqueued="56" />
  <ownableSynchronizers candidates="256500" cleared="78012" />
  <references type="soft" candidates="153648" cleared="0" enqueued="0" dynamicThreshold="22" maxThreshold="32" />
  <references type="weak" candidates="1266" cleared="610" enqueued="430" />
  <stringconstants candidates="9479" cleared="0"  />
  <object-monitors candidates="13576" cleared="13505"  />
</gc-op>
<gc-op id="190" type="classunload" timems="0.010" contextid="186" timestamp="2021-02-26T11:11:42.713">
  <classunload-info classloadercandidates="179" classloadersunloaded="0" classesunloaded="0" anonymousclassesunloaded="0" quiescems="0.000" setupms="0.010" scanms="0.000" postms="0.000" />
</gc-op>
```

The logs show that the copy forward operation acts on the the entire *eden* space (512 regions), recorded as `type=eden`, and 80 older regions, which are recorded as `type=other`. 113.76 MB (119281928 B) have been copied from the *eden* space to 1st generation regions and 233.10 MB (244414264 B) of memory in non-*eden* regions have been copied to the next generation of regions. The copy forward operation is followed by a class unload operation.

The element `<gc-end>` records the end of the increment and provides another snapshot of memory allocation on the heap, similar to `<gc-start>`.

```xml
<gc-end id="191" type="partial gc" contextid="186" durationms="402.645" usertimems="3157.520" systemtimems="4.000" stalltimems="47.689" timestamp="2021-02-26T11:11:42.714" activeThreads="8">
  <mem-info id="192" free="3003121664" total="4294967296" percent="69">
    <mem type="eden" free="2147483648" total="2147483648" percent="100" />
    <arraylet-primitive objects="1" leaves="4" largest="4" />
    <pending-finalizers system="56" default="0" reference="430" classloader="0" />
    <remembered-set count="2922048" freebytes="160016128" totalbytes="171704320" percent="93" regionsoverflowed="1" regionsstable="12" regionsrebuilding="0"/>
  </mem-info>
</gc-end>

```

The heap memory allocation at the end of the increment is as follows:

- The heap now has 2864 MB(3003121664 B) of memory available compared to the 856 MB available at the start of the increment. The increment reclaimed 2008 MB of memory in the heap. 

- 100% of the *eden* space is available as free memory. All the used memory in the *eden* space is reclaimed.

- The *remembered set* count has increased by 172,384 WHAT UNIT?, and the number of freebytes in the *remembered set* has decreased by 0.66 MB (689,536 B).

The cycle completes and the GC restarts application threads.

```xml
<cycle-end id="193" type="partial gc" contextid="186" timestamp="2021-02-26T11:11:42.714" />
<exclusive-end id="194" timestamp="2021-02-26T11:11:42.714" durationms="404.145" />
```

The next cycle recorded in the logs is another partial GC cycle. The `<gc-start>`element records the following information:

```xml
<gc-start id="198" type="partial gc" contextid="197" timestamp="2021-02-26T11:11:46.072">
  <mem-info id="199" free="855638016" total="4294967296" percent="19">
    <mem type="eden" free="0" total="2147483648" percent="0" />
    <arraylet-primitive objects="1" leaves="4" largest="4" />
    <remembered-set count="2922048" freebytes="160016128" totalbytes="171704320" percent="93" regionsoverflowed="1" regionsstable="12" regionsrebuilding="0"/>
  </mem-info>
</gc-start>
```

The `<mem-info>` element shows that the following occurred in between the end of the last (partial) GC cycle and the start of this one:

- All available memory in the *eden* area was allocated to application threads.
- Application threads also used some memory from non-*eden* heap areas. The heap's total available memory reduced from the 69% to 19%.

The `<remembered-set` element shows that the *remembered set* status is unchanged, as expected. The *remembered set* metastructure is only rebuilt when GC cycles run.

**Summary of the example**

Analyzing the structure and elements of this example log output shows that this example `balanced` partial GC cycle has the following characteristics:

- The GC cycle begins with an STW pause and is triggered because a memory allocation threshold was reached. The *eden* space is full.
- All GC operations that are associated with this cycle occur during the STW pause.
- The GC cycle consists of only one increment, which runs a copy-forward operation on 512 *eden* regions and 80 other regions, followed by a class-unload operation.
- The GC cycle reclaims all of the memory in the *eden* space and some memory in other generations.
- 2864 MB of the total 4096 MB heap was reclaimed. All free regions from the *eden* space were reclaimed, and also some older regions were reclaimed.
- the *remembered set* count increases by 172,384 WHAT UNIT? and the number of free bytes decreases by 0.66 MB (689,536 B). After copying objects forward to older regions, the partial GC cycle rebuilds the *remembered set* of any regions that received these moved objects. During a partial cycle, the *remembered sets* are also pruned. Overall, the rebuilding and pruning can lead to either an increase or a decrease in the *remembered set* count and free memory available. 
- The *remembered set* metastructure remains unchanged between GC cycles. 

### `balanced` global mark GC cycle
 
The `balanced` policy’s global *mark* GC cycle uses a mixture of STW and concurrent operations to build a new record of object liveness across the heap for use by the partial GC cycle. When an allocation taxation threshold is reached, the GC 'taxes' application threads to run a global *mark* GC cycle. The global *mark* cycle performs a [global *mark* phase](vgclog_balancedexamples.md#mark-phase) and also invokes an associated [*sweep* phase](vgclog_balancedexamples.md#sweep-phase) within the partial GC cycle that immediately follows the final global *mark* cycle increment.

To search for a `balanced` global mark cycle, you can search for the `type` attribute  value `global mark phase` in `<cycle-start>` and `<cycle-end>` elements. 

The global cycle is split into multiple increments, each recorded as `type="global mark phase"`. A global mark phase increment involves an STW subincrement, which runs a global mark operation during an STW pause, followed by a *global mark phase(GMP) work packet* subincrement. The`GMP work packet` subincrement involves a processing operation that runs concurrently. The `GMP work packet` subincrement might also use an STW pause to complete if the subincrement is interrupted by a partial or global cycle trigger.

Splitting the global mark phase into these increments and subincrements reduces pause times by running the majority of the GC work concurrently and interleaving global mark phase increments with partial GC cycles, and, rarely, [global GC cycles](vgclog_examples.md#balanced-global-gc-cycle).

A global *mark* GC cycle increment is triggered when ????

The following elements log the GC increments, subincrements and operations of the global *mark* GC cycle:

|GC increment         | GC operations| *stop-the-world* or concurrent| XML element of GC increment| Details                         |
|---------------------|-------------|-------------------------------|--------------------------------------|-----------------------|
|`global mark phase` subincrement| mark | *stop-the-world* | `<gc-start>`, `<gc-end>` |The global mark phase operations start at the beginning of the cycle and run through all *regions* until the final *region* |
|`GMP work packet processing` subincrement| work packet processing operations | concurrent and sometimes final operations during a *STW* to complete the subincrement | `<concurrent-start>`, `<concurrent-end>`| The `GMP work packet processing subincrement runs immediately after the `global mark phase` subincrement |
|final global mark phase increment | final global mark phase operations including class unload | *stop-the-world* | `gc-start>`, `<gc-end>`| Final increment. Runs the final global mark phase operations followed by operations to finish the cycle  |

MODIFY THIS FOR  GLOBAL MARK CYCLE

The `balanced` global *mark* GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some child elements are omitted for clarity:

```xml
<exclusive-start/>                        (STW pause starts)

<allocation-taxation/>                    (memory threshold trigger recorded)

<cycle-start/> type="global mark phase"   (global mark cycle starts)

<gc-start/> type="global mark phase"      (GMP subincrementstarts)

<mem-info>                                (memory status before operations)

<remembered-set>

</mem-info>         

</gc-start> 

<gc-op> type="mark increment" </gc-op>  (copy forward operation completed)

<gc-op> type="class unload" </gc-op>    (optional class unload operation completed)

<gc-end>                                (partial cycle increment ends)

<mem-info>                            (memory status after operations)
          
<remembered-set>

</mem-info>         

<concurrent-start/> type="GMP work packet processing" (GMP WPP sub increment starts)

<exclusive-end/>                       (STW pause ends)

<concurrent-end> type="GMP work packet processing" (GMP WPP sub increment ends)

<gc-op type="mark increment"> </gc-op>

</concurrent-end>

...                                       (partial GC cycles run)

<exclusive-start/>                        (STW pause starts)

<gc-start/> type="global mark phase"      (another STW GMP subincrement runs)

...   

<concurrent-start/> type="GMP work packet processing" (another concurrent global mark phase subincrement runs)

...

<exclusive-end/>

...                                       (more partial GC cycles may run)
<concurrent-end> type="GMP work packet processing"

<gc-op type="mark increment"> </gc-op>

</concurrent-end>

...

...                                       (more partial and GMP increments GC cycles interleave)

<exclusive-start/>                        (STW pause starts)

<allocation-taxation/>                    (memory threshold trigger recorded)

<gc-start/> type="global mark phase"      (final GMP increment starts)

<mem-info>                                (memory status before operations)

<remembered-set>

</mem-info>         

</gc-start> 

<gc-op> type="mark increment" </gc-op>  (copy forward operation completed)

<gc-op> type="class unload" </gc-op>    (class unload operation completed)

<gc-end>                                (final GMP increment ends)

<mem-info>                            (memory status after operations)
          
<remembered-set>

</mem-info>         

<cycle-end/> type "global mark phase"

<exclusive-end/>

<exclusive-start/>

<cycle-start/> type "partial gc"

...

<gc-op> type="sweep"

...

<cycle-end/> type "partial gc"

<exclusive-end/>

```


#### Global mark phase

The first activity of the global mark cycle is a STW pause, recorded by an `<exclusive-start>` element. The GC pauses application threads to execute the cycle's initial operations.

```xml
<exclusive-start id="1152" timestamp="2021-02-26T11:17:25.033" intervalms="1931.263">
  <response-info timems="3.082" idlems="1.041" threads="2" lastid="00000000006EDE00" lastname="RunDataWriter.1" />
</exclusive-start>
```

The cycle is triggered when the allocation taxation is reached. The threshold is set by the policy configuration and is often resized by the GC when a `balanced` global mark GC cycle completes. The <allocation-taxation> element records the memory threshold value that was in use when this example global mark cycle was triggered. The`taxation-threshold` attribute shows the taxation threshold is 1024MB (1,073,741,824), which is half the total memory of the *eden* space (2048 MB), as expected. For more information taxation thresholds for the `balanced` policy, see [`balanced` examples](vgclog_examples.md#balanced-examples).

```xml
<allocation-taxation id="1153" taxation-threshold="1073741824" timestamp="2021-02-26T11:17:25.034" intervalms="1931.251" />
```

Details about the start of the global mark GC cycle are recorded by the `<cycle-start>` element. The cycle is recorded as type `global mark phase` with an `id=1154`. Any subsequent elements associated with this cycle have a `contextid=1154` to match the global mark GC cycle's `id`. You can use the `contextid` value to distinguish increments and operations of the global mark GC cycle from the partial cycles that interleave with it.

```xml
<cycle-start id="1154" type="global mark phase" contextid="0" timestamp="2021-02-26T11:17:25.034" intervalms="374365.075" />
```

The cycle begins with the STW subincrement of a global *mark* phase increment. The STW subincrement is recorded using the `<gc-start>` element of type `global mark phase`.

```xml
<gc-start id="1155" type="global mark phase" contextid="1154" timestamp="2021-02-26T11:17:25.034">
  <mem-info id="1156" free="1442840576" total="4294967296" percent="33">
    <remembered-set count="2197888" freebytes="162912768" totalbytes="171704320" percent="94" regionsoverflowed="3" regionsstable="130" regionsrebuilding="0"/>
  </mem-info>
</gc-start>

```

The `<gc-start>` element provides a snapshot of the free memory available in the heap and the status of marked objects. At the start of the increment, the heap is 33% free, with 1376 MB (1442840576 B) of the total 4096 MB (4294967296 B) heap size free. 

The `<remembered-set>` element records the status of the *remembered set* metastructure of the JVM, a structure that records object references that cross different regions. One *remembered set* corresponds to one region of the heap. During the rebuilding of the *remembered sets* metastructure, any regions that cannot be rebuilt into a *remembered set* due to a lack of memory resource in the metastructure are marked as *overflow* regions. Cycles cannot reclaim memory from *overflow* regions.

The global *mark* cycle populates *remembered sets* and completely rebuilds *remembered sets* for overflowed regions. The logs show that at the start of this STW subincrement, the *remembered set* count WHAT DOES THIS MEAN? is 2197888, the metastructure is using 94% of its total available memory, and three *overflow* regions need to be rebuilt.

The `<gc-op>` element records that the STW subincrement runs a mark operation. This operation begins the process of building a record of object liveness across the heap. 

```xml
<gc-op id="1157" type="mark increment" timems="122.825" contextid="1154" timestamp="2021-02-26T11:17:25.157">
  <trace-info objectcount="7726701" scancount="7584109" scanbytes="213445656" />
</gc-op>
```

The object count.....

The scancount and scan bytes....

The STW `global mark phase` subincrement ends, as recorded by `<gc-end>`, which records a snapshot of the memory status in the heap in a similar way to `<gc-start>`. 

```xml
<gc-end id="1158" type="global mark phase" contextid="1154" durationms="123.139" usertimems="977.851" systemtimems="0.000" stalltimems="1.453" timestamp="2021-02-26T11:17:25.157" activeThreads="8">
  <mem-info id="1159" free="1442840576" total="4294967296" percent="33">
    <remembered-set count="3263968" freebytes="158648448" totalbytes="171704320" percent="92" regionsoverflowed="0" regionsstable="0" regionsrebuilding="133"/>
  </mem-info>
</gc-end>
```

Comparing the snapshot at the beginning and end of the heap shows that:

- The marking operation has increased the `count` value of the `<remembered-set>` by 1,066,080 UNITS (from 2,197,888 to 3,263,968)
- The number of *overflow* regions has reduced from three to zero. This GMP increment has rebuilt all *overflow regions.
- As expected, there is no change in the amount of free memory available, which is 1376 MB.

The beginning of the second part of the global *mark* phase increment, the GMP work packet procesing subincrememt, is recorded by `<concurrent-start>`. The child element `<concurrent-mark-start>` records the scan target of this subincrement as 242.74 MB (254,532,672 B).

```xml
<concurrent-start id="1160" type="GMP work packet processing" contextid="1154" timestamp="2021-02-26T11:17:25.157">
  <concurrent-mark-start scanTarget="254532672" />
</concurrent-start>
```

Now that the global mark phase sub-increment is complete, application threads are restarted. The second part of the global mark phase increment, the GMP work packet processing sub-increment, continues to run concurrently:

```xml
<exclusive-end id="1161" timestamp="2021-02-26T11:17:25.157" durationms="123.936" />
```

The end of the concurrent `GMP work packet processing` sub-increment operations are recorded using the `<concurrent-end>` element. 

```xml
<concurrent-end id="1162" type="GMP work packet processing" contextid="1154" timestamp="2021-02-26T11:17:25.469" terminationReason="Work target met">
<gc-op id="1163" type="mark increment" timems="311.867" contextid="1154" timestamp="2021-02-26T11:17:25.469">
  <trace-info scanbytes="254708852" />
</gc-op>
</concurrent-end>
```

The child element `<concurrent-mark-end>` shows that the processing has scanned 242.91 MB (254,708,852 B), which exceeds the 108.25 MB scan target.

The garbage collector now returns to running partial cycles to reclaim free space in the heap before the next global mark phase increment is triggered. To see an example of how a `balanced` partial GC cycle appears in the logs, see the [`Balanced` examples - Partial GC Cycle](vgclog_examples#partial-gc-cycle).

Following some partial GC cycles, an allocation taxation threshold is reached which triggers a STW pause followed by a global mark phase increment. The element `<gc-start>` has a `contextid=1154` and type `global mark phase` which indicates that this is a global mark phase sub-increment associated with our global *mark* cycle example.

```xml
<exclusive-start id="1175" timestamp="2021-02-26T11:17:28.993" intervalms="1978.886">
  <response-info timems="5.111" idlems="1.714" threads="2" lastid="00000000006EDE00" lastname="RunDataWriter.1" />
</exclusive-start>
<allocation-taxation id="1176" taxation-threshold="1073741824" timestamp="2021-02-26T11:17:28.994" intervalms="1978.879" />
<gc-start id="1177" type="global mark phase" contextid="1154" timestamp="2021-02-26T11:17:28.994">
  <mem-info id="1178" free="1451229184" total="4294967296" percent="33">
    <remembered-set count="3325824" freebytes="158401024" totalbytes="171704320" percent="92" regionsoverflowed="2" regionsstable="0" regionsrebuilding="133"/>
  </mem-info>
</gc-start>
```

`<allocation-taxation>` shows the taxation threshold for is set to 1024 MB, half of the size of the *eden* space. `<gc-start>` records the heap to have 1384 MB (1,451,229,184 B)of free memory available at the beginning of this global mark phase increment. This compares to the 1376 MB (1,442,840,576 B) of free memory available at the end of the previous global mark phase increment. Although free memory was reclaimed by the partial GC cycles that ran between these global mark phase increments, free memory was allocated to objects during this period resulting in a net reduction of free memory available. 

There are two *overflow* regions to be rebuilt.

The status of the heap at the beginning and end of STW sub-increments are automatically recorded. For this STW subincrement, there are no `<gc-op>` elements recorded - `<gc-end>` immediately follows `<gc-start>` in the logs. For some STW sub-increments, some GC operations are run, such as ADD

```xml
<gc-end id="1179" type="global mark phase" contextid="1154" durationms="0.289" usertimems="1.000" systemtimems="0.000" stalltimems="0.000" timestamp="2021-02-26T11:17:28.994" activeThreads="8">
  <mem-info id="1180" free="1451229184" total="4294967296" percent="33">
    <remembered-set count="3325824" freebytes="158401024" totalbytes="171704320" percent="92" regionsoverflowed="2" regionsstable="0" regionsrebuilding="133"/>
  </mem-info>
</gc-end>
```

Comparing the heap status at the beginning and end of the subincrement shows that there is no change in free memory available or `<remembered-set>`  values.

The second part of the increment, the `GMP work packet processing` subincrement, is recorded using the `<concurrent-start>` and <concurrent-end>` elements. 

```xml
<concurrent-start id="1181" type="GMP work packet processing" contextid="1154" timestamp="2021-02-26T11:17:28.994">
  <concurrent-mark-start scanTarget="258671414" />
</concurrent-start>
<exclusive-end id="1182" timestamp="2021-02-26T11:17:28.994" durationms="0.816" />

<concurrent-end id="1183" type="GMP work packet processing" contextid="1154" timestamp="2021-02-26T11:17:29.273" terminationReason="Work target met">
<gc-op id="1184" type="mark increment" timems="279.311" contextid="1154" timestamp="2021-02-26T11:17:29.274">
  <trace-info scanbytes="258767612" />
</gc-op>
</concurrent-end>
```

The log excerpt shows the concurrent GMP work packet processing subincrement has achieved the scan target of 246.69 MB (258671414 B). 246.78 MB (258767612 B) were scanned.

More partial cycles run. This pattern of interleaving of global mark increments with partial gc cycles repeats until a final global mark increment completes the global mark cycle. The final global mark phase increment consists of a STW `global mark phase` sub-increment that includes `mark increment` and `class unload` operations.

```xml
<exclusive-start id="1217" timestamp="2021-02-26T11:17:36.864" intervalms="1986.124">
  <response-info timems="0.287" idlems="0.104" threads="2" lastid="00000000006EDE00" lastname="RunDataWriter.1" />
</exclusive-start>
<allocation-taxation id="1218" taxation-threshold="1073741824" timestamp="2021-02-26T11:17:36.865" intervalms="1986.101" />
<gc-start id="1219" type="global mark phase" contextid="1154" timestamp="2021-02-26T11:17:36.865">
  <mem-info id="1220" free="1438646272" total="4294967296" percent="33">
    <remembered-set count="3514496" freebytes="157646336" totalbytes="171704320" percent="91" regionsoverflowed="3" regionsstable="0" regionsrebuilding="133"/>
  </mem-info>
</gc-start>
<gc-op id="1221" type="mark increment" timems="164.843" contextid="1154" timestamp="2021-02-26T11:17:37.030">
  <trace-info objectcount="7715572" scancount="7665293" scanbytes="214739196" />
  <cardclean-info objects="3962203" bytes="117924792" />
  <finalization candidates="206" enqueued="30" />
  <ownableSynchronizers candidates="601780" cleared="16925" />
  <references type="soft" candidates="718240" cleared="2858" enqueued="2832" dynamicThreshold="18" maxThreshold="32" />
  <references type="weak" candidates="2321" cleared="142" enqueued="0" />
  <references type="phantom" candidates="8" cleared="0" enqueued="0" />
  <stringconstants candidates="9522" cleared="0"  />
  <object-monitors candidates="7142" cleared="7066"  />
</gc-op>
<gc-op id="1222" type="classunload" timems="0.704" contextid="1154" timestamp="2021-02-26T11:17:37.030">
  <classunload-info classloadercandidates="185" classloadersunloaded="13" classesunloaded="13" anonymousclassesunloaded="0" quiescems="0.000" setupms="0.644" scanms="0.043" postms="0.016" />
</gc-op>
<gc-end id="1223" type="global mark phase" contextid="1154" durationms="169.521" usertimems="1244.810" systemtimems="3.000" stalltimems="27.792" timestamp="2021-02-26T11:17:37.034" activeThreads="8">
  <mem-info id="1224" free="1438646272" total="4294967296" percent="33">
    <pending-finalizers system="30" default="0" reference="2832" classloader="0" />
    <remembered-set count="2241440" freebytes="162738560" totalbytes="171704320" percent="94" regionsoverflowed="3" regionsstable="127" regionsrebuilding="0"/>
  </mem-info>
</gc-end>
```

Comparing the status of the memory at the start and end of this final `global mark phase` increment shows that:

- As expected, the final global mark phase increment does not reclaim any free memory.
- The remembered set metastructure has been rebuilt. The count has reduced and the number of available memory in the metastructure has increased from 91% to 94%.
- The number of *overflow* regions remains unchanged. 

Following the final global mark increment, the global mark cycles completes and the GC ends the STW pause.

```xml
<cycle-end id="1225" type="global mark phase" contextid="1154" timestamp="2021-02-26T11:17:37.034" />
<exclusive-end id="1226" timestamp="2021-02-26T11:17:37.034" durationms="170.186" />

```

The operations to create a record of object liveness across the heap, which began with the global *mark* cycle, is completed with a sweep phase. The sweep phase is triggered by the end of the global *mark* cycle to be included in the next partial GC cycle that runs.

#### Sweep phase

While the global *sweep* operation is logically associated with the global *mark* phase, it does not run in the same global *mark* cycle. Instead, the *sweep* operation runs in the same STW increment as the first partial GC cycle that runs after the completion of the global *mark* cycle. This can be seen in the following log excerpt.  After the log records the end of the global mark cycle it records a STW pause followed by a `partial gc` cycle of `id=1229`. The global *sweep* operation that runs after the the global *mark* phase is recorded in the `<gc-op>` element tagged as `id=1229`.

```xml
<exclusive-start id="1227" timestamp="2021-02-26T11:17:38.804" intervalms="1940.125">
...
<cycle-start id="1229" type="partial gc" contextid="0" timestamp="2021-02-26T11:17:38.805" intervalms="3926.202" />
...
</gc-start>
...
</gc-start>
<gc-op id="1232" type="sweep" timems="9.472" contextid="1229" timestamp="2021-02-26T11:17:38.815" />
<gc-op id="1233" type="copy forward" timems="308.258" contextid="1229" timestamp="2021-02-26T11:17:39.124">
...
<gc-op id="1234" type="classunload" timems="0.012" contextid="1229" timestamp="2021-02-26T11:17:39.125">
...
<gc-end>
...
</gc-end>
<cycle-end id="1237" type="partial gc" contextid="1229" timestamp="2021-02-26T11:17:39.125" />
<exclusive-end id="1238" timestamp="2021-02-26T11:17:39.125" durationms="320.792" />
```

A record of object liveness is now complete.

**Summary of the example**

Analyzing the structure and elements of this example log output shows that this example `balanced` global mark GC cycle has the following characteristics:

- The GC cycle begins with an STW pause and is triggered because a memory allocation threshold was reached. The *eden* space was half full.
- The global *mark* cycle does not reclaim memory. The cycle creates a record of object liveness, which can be seen by inspecting the status of the *remembered set* metastructure using the `<remembered-set>` attributes.
- Each global mark phase increment rebuilds the *remembered set* metastructure. Sometimes, the increment has managed to rebuilt some of the overflow regions.
- Partial cycles run in between global mark phase increments. The partial cycles set the memory allocation threshold to be half of the *eden* space so that ?????
- The final global mark phase increment includes a class unload. The final increment also triggers a sweep phase to run in the next partial cycle.
- WHAT ELSE?

### `balanced` Global Cycle

If the GC cannot reclaim enough memory using partial and global *mark* cycles to prevent the whole heap becoming full, an allocation failure occurs and a global cycle is triggered.

```xml
<exclusive-start id="41" timestamp="2020-11-13T06:31:42.006" intervalms="2429.574">
  <response-info timems="2.338" idlems="0.802" threads="2" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
<sys-start reason="explicit" id="42" timestamp="2020-11-13T06:31:42.006" intervalms="9987.458" />
<cycle-start id="43" type="global garbage collect" contextid="0" timestamp="2020-11-13T06:31:42.007" intervalms="9987.772" />
<gc-start id="44" type="global garbage collect" contextid="43" timestamp="2020-11-13T06:31:42.007">
  <mem-info id="45" free="2281852504" total="3221225472" percent="70">
    <mem type="eden" free="151128" total="130023424" percent="0" />
    <arraylet-reference objects="4" leaves="4" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <remembered-set count="453472" freebytes="126964352" totalbytes="128778240" percent="98" regionsoverflowed="4" regionsstable="0" regionsrebuilding="0"/>
  </mem-info>
</gc-start>
<allocation-stats totalBytes="127765576" >
  <allocated-bytes non-tlh="11566360" tlh="116199216" arrayletleaf="0"/>
  <largest-consumer threadName="ForkJoinPool-3-worker-5" threadId="000000000077E900" bytes="53174768" />
</allocation-stats>
<gc-op id="46" type="global mark" timems="35.727" contextid="43" timestamp="2020-11-13T06:31:42.044">
  <trace-info objectcount="1856388" scancount="1838558" scanbytes="66324640" />
  <finalization candidates="87" enqueued="0" />
  <ownableSynchronizers candidates="200093" cleared="200034" />
  <references type="soft" candidates="4427" cleared="0" enqueued="0" dynamicThreshold="23" maxThreshold="32" />
  <references type="weak" candidates="2968" cleared="31" enqueued="29" />
  <references type="phantom" candidates="97" cleared="0" enqueued="0" />
  <stringconstants candidates="8948" cleared="0"  />
  <object-monitors candidates="20" cleared="7"  />
</gc-op>
<gc-op id="47" type="classunload" timems="0.020" contextid="43" timestamp="2020-11-13T06:31:42.044">
  <classunload-info classloadercandidates="74" classloadersunloaded="0" classesunloaded="0" anonymousclassesunloaded="0" quiescems="0.000" setupms="0.020" scanms="0.000" postms="0.000" />
</gc-op>
<gc-op id="48" type="sweep" timems="2.322" contextid="43" timestamp="2020-11-13T06:31:42.047" />
<gc-op id="49" type="compact" timems="66.804" contextid="43" timestamp="2020-11-13T06:31:42.114">
  <compact-info movecount="1651540" movebytes="56069168" />
  <remembered-set-cleared processed="37569" cleared="33373" durationms="1.000" />
</gc-op>
<gc-end id="50" type="global garbage collect" contextid="43" durationms="107.552" usertimems="688.000" systemtimems="36.000" stalltimems="130.908" timestamp="2020-11-13T06:31:42.115" activeThreads="8">
  <mem-info id="51" free="3128950784" total="3221225472" percent="97">
    <arraylet-reference objects="2" leaves="2" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <pending-finalizers system="0" default="0" reference="29" classloader="0" />
    <remembered-set count="26656" freebytes="128671616" totalbytes="128778240" percent="99" regionsoverflowed="0" regionsstable="0" regionsrebuilding="0"/>
  </mem-info>
</gc-end>
<cycle-end id="52" type="global garbage collect" contextid="43" timestamp="2020-11-13T06:31:42.115" />
<sys-end id="53" timestamp="2020-11-13T06:31:42.115" />
<exclusive-end id="54" timestamp="2020-11-13T06:31:42.115" durationms="108.749" />
```