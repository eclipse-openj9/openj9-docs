# `balanced` examples

The [`balanced`](gc.md#balanced-policy) policy (`-Xgcpolicy:balanced`) uses two types of cycle to perform GC – a partial GC cycle and a global GC *mark* cycle. The policy GC may have to perform a third type of cycle - a global cycle - to reclaim memory after an allocation failure that has resulted from tight memory conditions. 
 
Note: For more information about the cycles used in a particular policy, see [garbage collection policies](gc#garbage-collection-policies). 

The start of a `balanced` cycle is recorded in the log by the following elements and attributes:

| GC cycle or increment | value of `type` attribute of the `<cycle-start>` and `<cycle-end` elements| Element that logs the cycle or increment trigger| Triggering reason|
|----------|----------------------------|------------------------------|------------------|
|Global mark cycle| `global mark phase`| `<allocation-taxation>` | taxation threshold reached. |
| Global mark increment of global mark cycle| `GMP work packet processing` | `<concurrent-start>` `<concurrent-end>` |
partial cycle| `partial gc`           | `<allocation-taxation>`                 |taxation threshold reached|
|global cycle|     `global garbage collect`    | `af-start`, `<af-end>`           |An allocation failure. Occurs under tight memory conditions. Cycle runs very rarely. |

To locate a particular type of cycle, you can search for the `type` attribute of the `<cycle-start>` and `<cycle-end>` elements.

You an analyze the increments and operations that are associated with a particular type of cycle by locating and interpreting the elements in the following table:

| GC process             | start and end XML elements   | Details |
|------------------------|------------------------------|------------------------------|
|GC cycle                |`<cycle-start>`, `<cycle-end>`| The start and end of a GC cycle.|
|GC STW increment        |`<gc-start>`, `<gc-end>`      | The start and end of a GC increment or sub-increment that begins with a *stop-the-world* pause. For example a `global mark phase` global mark GC cycle increment or a partial GC cycle increment.
|GC concurrent increment        | `<concurrent-start>`, `<concurrent-end>` | The start of the concurrent *global mark phase work packet processing* sub-increments of the global mark cycle|
| ? | `<concurrent-mark-start>` | THIS IS A CHILD ELEMENT GIVING STATUS - WHERE PUT IT? |
|GC operations and phases| `<gc-op>`                    | A GC operation such as mark or sweep, or a suboperation ‘phase’ such as class unload. |

**Note: For more information about the XML structure of GC cycles, see [GC cycles](vgclog.md#gc-cycles).  For more information about GC cycle increments, see [GC increments and interleaving](vgclog.md#gc-increments-and-interleaving).

The following sections use log excerpts to show how the different types of give details about `balanced` cycle are logged. 

### `balanced` Partial GC cycle

The following example is taken from a [`balanced` partial GC cycle](gc.md#balanced-policy) verbose GC log. The output is broken down into sections to explain the GC processing that is taking place.

To search for a `balanced` partial GC cycle, you can search for the `type` attribute value `partial gc` in `<cycle-start>` and `<cycle-end>` elements.

The `balanced` policy’s partial GC cycle reclaims memory in the heap for allocation of new objects by reducing the number of used *regions*. The partial GC cycle always reduces used regions in the *eden* space and may also reclaim memory from older regions. All the cycle's operations run during a single *stop-the-world* pause:

|GC Operations | GC increment | *stop-the-world* or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|-------------------------------|--------------------------------------|---------------------------------------------------------------------------|
|copy forward, optionally class unload, sweep, compact |Single        | *stop-the-world*              | `<gc-start>`, `<gc-end>`| `<gc-op'>` |ADD| 

The `balanced` partial GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some child elements are omitted for clarity:

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
<exclusive-start id="334" timestamp="2020-11-13T06:32:26.853" intervalms="879.225">
  <response-info timems="1.069" idlems="0.391" threads="2" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
```

A `balanced` partial GC cycle is triggered when the region count for the *eden* space reaches a *taxation* threshold. At this threshold, the GC 'taxes' the application threads, who have been 'paid' with memory allocation, to run some GC work - in this case, a partial GC cycle.The logs record this trigger reason by using the`<allocation-taxation>` element.

```xml
<allocation-taxation id="335" taxation-threshold="805306368" timestamp="2020-11-13T06:32:26.853" intervalms="879.261" />
```
< TAKE OUT?----
If the partial GC cycle is not run within a global *mark* cycle, the end of the last partial GC cycle sets the allocation taxation threshold to be the size of the *eden* region.

If the partial GC cycle is run within a global *mark* cycle, the allocation taxation threshold is set to be smaller than the size of the *eden* region to allow for global mark phase increments to run in between partial GC cycles TRUE? Specifically, when the previous partial GC cycle completes, the allocation taxation threshold is set to be half the size of the eden area. The increment, which could be a partial GC cycle or could be a GMP increment, runs.......
--->

Details about the start of the cycle are recorded by the `<cycle-start>` element. The cycle is recorded as a `partial gc` with an `id=336`. Any subsequent elements associated with this cycle have a `contextid=336` to match the cycle's `id`. You can use this `contextid` value to distinguish the partial GC cycle increment and operations from interleaving increments and operations from other `balanced` cycles, such as global *mark* cycles.

```xml
<cycle-start id="336" type="partial gc" contextid="0" timestamp="2020-11-13T06:32:26.854" intervalms="879.022" />

```

The partial cycle begins its one and only GC increment, recorded using the `<gc-start>` element. You can understand the effect that the increment operations have on the heap by comparing snapshots of the memory taken at the start and the end of the increment.  The child elements `<mem-info>` and `<mem>` of the `<gc-start>` and `<gc-end>` elements record the amount of memory available and where it is located in the heap.

```xml
<gc-start id="337" type="partial gc" contextid="336" timestamp="2020-11-13T06:32:26.855">
  <mem-info id="338" free="765460480" total="3221225472" percent="23">
    <mem type="eden" free="0" total="805306368" percent="0" />
    <arraylet-reference objects="4" leaves="4" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <remembered-set count="1546944" freebytes="122590464" totalbytes="128778240" percent="95" regionsoverflowed="5" regionsstable="287" regionsrebuilding="0"/>
  </mem-info>
</gc-start>
```

As expected, at the start of this increment, the eden region is full. 730MB (765,460,480B) of the total  3072MB (3,221,225,472B) heap is available as free memory.

The following element, `<allocation-stats>`, records information about the state of application threads before the start of the current cycle. For this example, the thread `Group1.Backend.CompositeBackend{Tier1}.2` was the largest consumer of memory. 

```xml
<allocation-stats totalBytes="804888808" >
  <allocated-bytes non-tlh="38821120" tlh="766067688" arrayletleaf="0"/>
  <largest-consumer threadName="Group1.Backend.CompositeBackend{Tier1}.2" threadId="00000000007B0700" bytes="111446656" />
</allocation-stats>
```

The operations of the GC increment are run and details are recorded in the `<gc-op> elements`. The logs show that this increment begins with a copy forward operation followed by a class unload. Other `balanced` partial GC cycles can also include sweep and compact operations. For details about the operations involved in `balanced` partial GC cycles, see [Balanced Policy - GC Processing](gc.md#balanced-policy). 

```xml
<gc-op id="339" type="copy forward" timems="148.093" contextid="336" timestamp="2020-11-13T06:32:27.006">
  <memory-copied type="eden" objects="2119359" bytes="56147800" bytesdiscarded="657864" />
  <memory-copied type="other" objects="3962555" bytes="106884464" bytesdiscarded="4476016" />
  <memory-cardclean objects="1007488" bytes="36932792" />
  <regions eden="384" other="74" />
  <remembered-set-cleared processed="1364519" cleared="205556" durationms="4.874" />
  <finalization candidates="3" enqueued="0" />
  <ownableSynchronizers candidates="77937" cleared="44879" />
  <references type="soft" candidates="37104" cleared="0" enqueued="0" dynamicThreshold="15" maxThreshold="32" />
  <references type="weak" candidates="406" cleared="116" enqueued="0" />
  <stringconstants candidates="10847" cleared="0"  />
  <object-monitors candidates="4996" cleared="4885"  />
</gc-op>
<gc-op id="340" type="classunload" timems="0.031" contextid="336" timestamp="2020-11-13T06:32:27.006">
  <classunload-info classloadercandidates="170" classloadersunloaded="0" classesunloaded="0" anonymousclassesunloaded="0" quiescems="0.000" setupms="0.030" scanms="0.000" postms="0.000" />
</gc-op>
```

The logs show that the copy forward operation acts on the *eden* region, recorded as `type=eden`, and older regions, which are recorded as `type=other`. 53.5 MB (56,147,800 B) have been copied from the *eden* space TO WHERE? and 101.9 MB (106,884,464 B) from older regions TO WHERE?.

The element `<gc-end>` records the end of the increment and provides another snapshopt of memory allocation on the heap, similar to `<gc-start>`. 

```xml
<gc-end id="341" type="partial gc" contextid="336" durationms="152.457" usertimems="1148.000" systemtimems="16.000" stalltimems="26.991" timestamp="2020-11-13T06:32:27.007" activeThreads="8">
  <mem-info id="342" free="1549795328" total="3221225472" percent="48">
    <mem type="eden" free="1812208" total="58720256" percent="3" />
    <arraylet-reference objects="4" leaves="4" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <remembered-set count="1600224" freebytes="122377344" totalbytes="128778240" percent="95" regionsoverflowed="5" regionsstable="287" regionsrebuilding="0"/>
  </mem-info>
</gc-end>

```

The heap memory allocation at the end of the increment is as follows:

- The increment has reclaimed 748 MB of memory. The heap now has 1478 MB(1,549,795,328 B) of memory available compared to the 730 MB available at the start of the increment.

- The increment has reclaimed 1.73MB (1,812,208 B). 3% of the *eden* space is now available as free memory. The *eden* space was full at the start of the increment.

The cycle completes and the GC restarts application threads.

```xml
<cycle-end id="343" type="partial gc" contextid="336" timestamp="2020-11-13T06:32:27.009" />
<exclusive-end id="344" timestamp="2020-11-13T06:32:27.010" durationms="157.314" />
```

**Summary of the example**

Analyzing the structure and elements of this example log output shows that this example `balanced` partial GC cycle has the following characteristics:

- The GC cycle begins with an STW pause and is triggered because a memory allocation threshold was reached. The *eden* space is full.
- All GC operations that are associated with this cycle occur during the STW pause.
- The GC cycle consists of only one increment, which runs a copy-forward operation and a class-unload operation.
- The GC cycle reclaims 1.73 MB of memory from the *eden* space that was full, which represents 3% of the *eden* space. 
- 748 MB of the total 3072 MB heap was reclaimed. Free regions from the *eden* space and also some older regions were reclaimed.

### `balanced` global mark GC cycle
 
The `balanced` policy’s global *mark* GC cycle uses a mixture of STW and concurrent operations to build a new record of object liveness across the heap for use by the partial GC cycle. When the global *mark* cycle allocation taxation threshold is reached, the GC 'taxes' application threads to run a global *mark* GC cycle. The global *mark* cycle performs a [global *mark* phase](vgclog_balancedexamples.md#mark-phase) and also invokes an associated [*sweep* phase](vgclog_balancedexamples.md#sweep-phase) within the partial GC cycle that immediately follows the final global *mark* cycle increment.

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

#### Global mark phase

The first activity of the global mark cycle is a STW pause, recorded by an `<exclusive-start>` element. The GC pauses application threads to execute the cycle's initial operations. 

```xml
<exclusive-start id="345" timestamp="2020-11-13T06:32:27.347" intervalms="494.235">
  <response-info timems="3.588" idlems="1.693" threads="3" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
```

The cycle is triggered when the global mark cycle allocation taxation is reached. The threshold is set by the policy configuration and is often resized by the GC when a `balanced` global mark GC cycle completes. The <allocation-taxation> element records the memory threshold value that was in use when this example global mark cycle was triggered. The`taxation-threshold` attribute shows the taxation threshold is `402GB`.

```xml
<allocation-taxation id="346" taxation-threshold="402653184" timestamp="2020-11-13T06:32:27.348" intervalms="494.037" />
```

Details about the start of the global mark GC cycle are recorded by the `<cycle-start>` element. The cycle is recorded as type `global mark phase` with an `id=347`. Any subsequent elements associated with this cycle have a `contextid=347` to match the global mark GC cycle's `id`. You can use the `contextid` value to distinguish increments and operations of the global mark GC cycle from the partial cycles that interleave with it.

```xml
<cycle-start id="347" type="global mark phase" contextid="0" timestamp="2020-11-13T06:32:27.348" intervalms="55328.929" />
```

The cycle begins with the STW subincrement of a global *mark* phase increment. The STW subincrement is recorded using the `<gc-start>` element of type `global mark phase`.

```xml
<gc-start id="348" type="global mark phase" contextid="347" timestamp="2020-11-13T06:32:27.348">
  <mem-info id="349" free="1147142144" total="3221225472" percent="35">
    <remembered-set count="1523648" freebytes="122683648" totalbytes="128778240" percent="95" regionsoverflowed="5" regionsstable="321" regionsrebuilding="0"/>
  </mem-info>
</gc-start>

```

The `<gc-start>` element provides a snapshot of the free memory available in the heap and the status of marked objects. At the start of the increment, the heap is 35% free, with 1094 MB (1147142144 B) of the total 3072 MB (3221225472 B) heap size free. 

The `<remembered-set>` elements records.....

The `<gc-op>` element records that the STW subincrement runs a mark operation. This operation begins the process of building a record of object liveness across the heap. 

```xml
<gc-op id="350" type="mark increment" timems="49.623" contextid="347" timestamp="2020-11-13T06:32:27.398">
  <trace-info objectcount="3117114" scancount="3048551" scanbytes="83420400" />
</gc-op>
```

The object count.....

The scancount and scan bytes....

The STW `global mark phase` subincrement ends, as recorded by `<gc-end>`, which records a snapshot of the memory status in the heap in a similar way to `<gc-start>`. 

```xml
<gc-end id="351" type="global mark phase" contextid="347" durationms="49.866" usertimems="344.000" systemtimems="40.000" stalltimems="9.352" timestamp="2020-11-13T06:32:27.398" activeThreads="8">
  <mem-info id="352" free="1147142144" total="3221225472" percent="35">
    <remembered-set count="1768768" freebytes="121703168" totalbytes="128778240" percent="94" regionsoverflowed="1" regionsstable="0" regionsrebuilding="326"/>
  </mem-info>
</gc-end>
```

Comparing the snapshot at the beginning and end of the heap shows that:

- The marking operation has increased the `count` value of the `<remembered-set>` from...to....
- As expected, there is no change in the amount of free memory available, which is 1094 MB.

The beginning of the second part of the global *mark* phase increment, the GMP work packet procesing subincrememt, is recorded by `<concurrent-start>`. The child element `<concurrent-mark-start>` records the scan target of this subincrement as 108.25 MB.

```xml
<concurrent-start id="353" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:27.399">
  <concurrent-mark-start scanTarget="113512867" />
</concurrent-start>
```

Now that the global mark phase sub-increment is complete, application threads are restarted. The second part of the global mark phase increment, the GMP work packet processing sub-increment, continues to run concurrently:

```xml
<exclusive-end id="354" timestamp="2020-11-13T06:32:27.399" durationms="52.216" />
```

The end of the concurrent `GMP work packet processing` sub-increment operations are recorded using the `<concurrent-end>` element. 

```xml
<concurrent-end id="355" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:27.538">
  <concurrent-mark-end bytesScanned="113629324" reasonForTermination="Work target met" />
</concurrent-end>
```

The child element `<concurrent-mark-end>` shows that the processing has scanned 108.37 MB (113629324 B), which exceeds the 108.25 MB scan target.

The garbage collector now returns to running partial cycles to reclaim free space in the heap before the next global mark phase increment is triggered. To see an example of how a `balanced` partial GC cycle appears in the logs, see the [`Balanced` examples - Partial GC Cycle](vgclog_examples#partial-gc-cycle).

Following some partial GC cycles, a global mark phase increment allocation taxation threshold is reached which triggers a STW pause. The element `<gc-start>` has a `contextid=347` and type `global mark phase` which indicates that this is a global mark phase sub-increment associated with our global *mark* cycle example.

```xml
<exclusive-start id="367" timestamp="2020-11-13T06:32:28.539" intervalms="692.067">
  <response-info timems="3.365" idlems="1.153" threads="2" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
<allocation-taxation id="368" taxation-threshold="402653184" timestamp="2020-11-13T06:32:28.540" intervalms="691.869" />
<gc-start id="369" type="global mark phase" contextid="347" timestamp="2020-11-13T06:32:28.540">
  <mem-info id="370" free="1109393408" total="3221225472" percent="34">
    <remembered-set count="2199136" freebytes="119981696" totalbytes="128778240" percent="93" regionsoverflowed="1" regionsstable="13" regionsrebuilding="326"/>
  </mem-info>
</gc-start>
```

`<allocation-taxation>` shows the taxation threshold for this global mark phase increment is set to 384 MB. `<gc-start>` records the heap to have 1058 MB of free memory available at the beginning of this global mark phase increment. This compares to the 1094 MB of free memory available at the end of the previous global mark phase increment. Although free memory was reclaimed by the partial GC cycles that ran between these global mark phase increments, free memory was allocated to objects during this period resutling in a net reduction of free memory available. 

The status of the heap at the beginning and end of STW sub-increments are automatically recorded. For this STW subincrement, there are no `<gc-op>` elements recorded - `<gc-end>` immediately follows `<gc-start>` in the logs. For some STW sub-increments, some GC operations are run, such as ......

<gc-end id="371" type="global mark phase" contextid="347" durationms="0.219" usertimems="0.000" systemtimems="0.000" stalltimems="0.000" timestamp="2020-11-13T06:32:28.540" activeThreads="8">
  <mem-info id="372" free="1109393408" total="3221225472" percent="34">
    <remembered-set count="2199136" freebytes="119981696" totalbytes="128778240" percent="93" regionsoverflowed="1" regionsstable="13" regionsrebuilding="326"/>
  </mem-info>
</gc-end>

Comparing the heap status at the beginning and end of the subincrement shows that there is no change in free memory available or `<remembered-set>`  values.

The second part of the increment, the `GMP work packet processing` subincrement, is recorded using the `<concurrent-start>` and <concurrent-end>` elements. 

```xml
<concurrent-start id="373" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:28.541">
  <concurrent-mark-start scanTarget="118053382" />
</concurrent-start>
<exclusive-end id="374" timestamp="2020-11-13T06:32:28.554" durationms="15.172" />

<concurrent-end id="375" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:28.671">
  <concurrent-mark-end bytesScanned="118142880" reasonForTermination="Work target met" />
</concurrent-end>
```

The log excerpt shows the concurrent GMP work packet processing subincrement has achieved the scan target of 112.58 MB (118053382 B). 112.67 MB (118142880 B) were scanned.

More partial cycles run. This pattern of interleaving of global mark increments with partial gc cycles repeats until a final global mark increment completes the global mark cycle. The final global mark phase increment consists of a STW `global mark phase` sub-increment that includes `mark increment` and `class unload` operations.

```xml
<exclusive-start id="489" timestamp="2020-11-13T06:32:34.197" intervalms="504.129">
  <response-info timems="1.834" idlems="0.841" threads="3" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
<allocation-taxation id="490" taxation-threshold="402653184" timestamp="2020-11-13T06:32:34.197" intervalms="504.136" />
<gc-start id="491" type="global mark phase" contextid="347" timestamp="2020-11-13T06:32:34.198">
  <mem-info id="492" free="899678208" total="3221225472" percent="27">
    <remembered-set count="2496480" freebytes="118792320" totalbytes="128778240" percent="92" regionsoverflowed="7" regionsstable="62" regionsrebuilding="326"/>
  </mem-info>
</gc-start>
<gc-op id="493" type="mark increment" timems="115.487" contextid="347" timestamp="2020-11-13T06:32:34.313">
  <trace-info objectcount="6609052" scancount="6587138" scanbytes="191633500" />
  <cardclean-info objects="3488561" bytes="95550996" />
  <finalization candidates="138" enqueued="0" />
  <ownableSynchronizers candidates="543532" cleared="10731" />
  <references type="soft" candidates="656314" cleared="0" enqueued="0" dynamicThreshold="12" maxThreshold="32" />
  <references type="weak" candidates="7039" cleared="1811" enqueued="1711" />
  <references type="phantom" candidates="133" cleared="0" enqueued="0" />
  <stringconstants candidates="10849" cleared="0"  />
  <object-monitors candidates="2699" cleared="2589"  />
</gc-op>
<gc-op id="494" type="classunload" timems="0.029" contextid="347" timestamp="2020-11-13T06:32:34.313">
  <classunload-info classloadercandidates="170" classloadersunloaded="0" classesunloaded="0" anonymousclassesunloaded="0" quiescems="0.000" setupms="0.029" scanms="0.000" postms="0.000" />
</gc-op>
<gc-end id="495" type="global mark phase" contextid="347" durationms="115.878" usertimems="892.000" systemtimems="12.000" stalltimems="20.143" timestamp="2020-11-13T06:32:34.313" activeThreads="8">
  <mem-info id="496" free="899678208" total="3221225472" percent="27">
    <pending-finalizers system="0" default="0" reference="1711" classloader="0" />
    <remembered-set count="2670048" freebytes="118098048" totalbytes="128778240" percent="91" regionsoverflowed="7" regionsstable="62" regionsrebuilding="0"/>
  </mem-info>
</gc-end>
```

Comparing the status of the memory before and after this final `global mark phase` increment shows that .....
- as expected, the final global mark phase increment does not reclaim any free memory. 
- 113.29MB `freeBytes` of 122.81 `totalbytes` which represents 92% of the total. 7 regions overflowed. At the end of the final increment, 112.63MB of 122.81 which represents 91%, same number of regions overflowed. 

Following the final global mark increment, the global mark cycles completes and the GC ends the STW pause.

```xml
<cycle-end id="497" type="global mark phase" contextid="347" timestamp="2020-11-13T06:32:34.314" />
<exclusive-end id="498" timestamp="2020-11-13T06:32:34.317" durationms="120.638" />
```

#### Sweep phase

The operations to create a record of object liveness across the heap, which together with the partial GC cycle enables the `balanced` GC to reclaim memory, is completed with a sweep phase.  While the global *sweep* operation is logically associated with the global *mark* phase, it does not run in the same global *mark* cycle. Instead, the *sweep* operation runs in the same STW increment as the first partial GC cycle that runs after the completion of the global *mark* cycle. This can be seen in the following log excerpt.  After the log records the end of the global mark cycle it records a STW pause followed by a `partial gc` cycle of `id=501`. The global *sweep* operation that runs after the the global *mark* phase is recorded in the `<gc-op>` element tagged as `id=504`. 

```xml
<exclusive-start id="499" timestamp="2020-11-13T06:32:34.656" intervalms="459.751">
  <response-info timems="3.254" idlems="1.146" threads="2" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
<allocation-taxation id="500" taxation-threshold="402653184" timestamp="2020-11-13T06:32:34.657" intervalms="459.964" />
<cycle-start id="501" type="partial gc" contextid="0" timestamp="2020-11-13T06:32:34.658" intervalms="964.201" />
<gc-start id="502" type="partial gc" contextid="501" timestamp="2020-11-13T06:32:34.658">
  <mem-info id="503" free="497025024" total="3221225472" percent="15">
    <mem type="eden" free="0" total="805306368" percent="0" />
    <arraylet-reference objects="4" leaves="4" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <remembered-set count="1547520" freebytes="122588160" totalbytes="128778240" percent="95" regionsoverflowed="7" regionsstable="381" regionsrebuilding="0"/>
  </mem-info>
</gc-start>
<allocation-stats totalBytes="804892736" >
  <allocated-bytes non-tlh="45077576" tlh="759815160" arrayletleaf="0"/>
  <largest-consumer threadName="Group1.Backend.CompositeBackend{Tier1}.1" threadId="0000000000773600" bytes="59907016" />
</allocation-stats>
<gc-op id="504" type="sweep" timems="12.863" contextid="501" timestamp="2020-11-13T06:32:34.672" />
<gc-op id="505" type="copy forward" timems="116.013" contextid="501" timestamp="2020-11-13T06:32:34.790">
  <memory-copied type="eden" objects="1484789" bytes="37822688" bytesdiscarded="663111" />
  <memory-copied type="other" objects="3285904" bytes="87231496" bytesdiscarded="11788521" />
  <memory-cardclean objects="842406" bytes="32218760" />
  <regions eden="192" other="259" />
  <remembered-set-cleared processed="1381676" cleared="475799" durationms="6.064" />
  <finalization candidates="1" enqueued="0" />
  <ownableSynchronizers candidates="27456" cleared="10362" />
  <references type="soft" candidates="13920" cleared="0" enqueued="0" dynamicThreshold="8" maxThreshold="32" />
  <references type="weak" candidates="129" cleared="87" enqueued="0" />
  <stringconstants candidates="10849" cleared="0"  />
  <object-monitors candidates="2182" cleared="2071"  />
</gc-op>
<gc-op id="506" type="classunload" timems="0.039" contextid="501" timestamp="2020-11-13T06:32:34.790">
  <classunload-info classloadercandidates="170" classloadersunloaded="0" classesunloaded="0" anonymousclassesunloaded="0" quiescems="0.000" setupms="0.039" scanms="0.000" postms="0.000" />
</gc-op>
<gc-end id="507" type="partial gc" contextid="501" durationms="132.600" usertimems="988.000" systemtimems="20.000" stalltimems="26.608" timestamp="2020-11-13T06:32:34.791" activeThreads="8">
  <mem-info id="508" free="1272971264" total="3221225472" percent="39">
    <mem type="eden" free="2301824" total="62914560" percent="3" />
    <arraylet-reference objects="4" leaves="4" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <remembered-set count="1280704" freebytes="123655424" totalbytes="128778240" percent="96" regionsoverflowed="7" regionsstable="381" regionsrebuilding="0"/>
  </mem-info>
</gc-end>
<cycle-end id="509" type="partial gc" contextid="501" timestamp="2020-11-13T06:32:34.791" />
<exclusive-end id="510" timestamp="2020-11-13T06:32:34.792" durationms="135.503" />
```

A record of object liveness is now complete.

**Summary of the example**

Analyzing the structure and elements of this example log output shows that this example `balanced` global mark GC cycle has the following characteristics:

ADD

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