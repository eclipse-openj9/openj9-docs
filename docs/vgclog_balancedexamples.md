# `balanced` examples [WIP]

The [`balanced`](gc.md#balanced-policy) policy (`-Xgcpolicy:balanced`) uses two types of cycle to perform GC – a partial GC cycle and a global GC *mark* cycle. 
 
Note: For more information about the cycles used in a particular policy, see [garbage collection policies](gc#garbage-collection-policies). 

The start of a `balanced` cycle is recorded in the log by the following elements and attributes:

| GC cycle or increment | value of `type` attribute| Element for cycle or increment trigger| Triggering reason|
|----------|----------------------------|------------------------------|------------------|
|Global mark cycle| `global mark phase`| `af-start`, `<af-end>` | threshold reached. 
| Global mark increment of global mark cycle| `GMP work packet processing` | `<concurrent-start>` `<concurrent-end>` |
partial cycle| `partial gc`           | `<allocation-taxation>`                 |taxation threshold reached|
|global cycle|     ???    | ???              |Global mark operation and partial cycle operations are unable to reclaim enough memory from the heap. Cycle runs very rarely - only occurs under very tight memory conditions. |

To locate a particular type of cycle, you can search for the `type` attribute of the `<cycle-start>` and `<cycle-end>` elements. 

You an analyze the increments and operations that are associated with a particular type of cycle by locating and interpreting the elements in the following table:

| GC process             | start and end XML elements   | Details |
|------------------------|------------------------------|------------------------------|
|GC cycle                |`<cycle-start>`, `<cycle-end>`| The start and end of a GC cycle.|
|GC STW increment        |`<gc-start>`, `<gc-end>`      | The start and end of a GC increment that begins with a *stop-the-world* pause. For example a `global mark phase` global mark GC cycle increment or a partial GC cycle increment.
|GC concurrent increment        | `<concurrent-start>`, `<concurrent-end>` | The start of the concurrent *global mark phase work packet processing* increments of the global mark operation cycle|
| ? | `<concurrent-mark-start>` | THIS IS A CHILD ELEMENT GIVING STATUS - WHERE PUT IT? |
|GC operations and phases| `<gc-op>`                    | A GC operation such as mark or sweep, or a suboperation ‘phase’ such as class unload. |

**Note:** Details of a GC operation or phase are logged by using the single <gc-op> XML element rather than start and end XML elements. 

The following sections give details about specific cycles, including examples of how the cycle appears in the Verbose GC log.

### Partial GC cycle

A partial GC cycle is recorded within the following example, which is taken from the verbose GC log output of a `balanced` policy garbage collection. The following log content is broken down into sections that describe particular activities of the GC cycle. 

The `balanced` policy’s partial GC cycle runs all it's operations by using a single *stop-the-world* pause:

|GC Operations | GC increment | *stop-the-world* or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|-------------------------------|--------------------------------------|---------------------------------------------------------------------------|
|copy forward, class unload, sweep, compact |Single        | *stop-the-world*              | `<gc-start>`, `<gc-end>`| `<gc-op'>` |ADD| 

The `balanced` partial GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some child elements are omitted for clarity:

```xml
<exclusive-start/> 

<allocation-taxation/>

<cycle-start/> 

  <gc-start/>  

    <mem-info> 

      <mem></mem> 

    </mem-info> 

  </gc-start> 

  <allocation-stats>

  <gc-op> type="copy forward" </gc-op> 

  <gc-op> type="class unload" </gc-op> 

  <gc-op> type="sweep" </gc-op> 

  <gc-op> type="compact" </gc-op> 

  <gc-end>  

    <mem-info> 
          
      <mem></mem> 

    </mem-info> 

  </gc-end> 

<cycle-end> 

<exclusive-end> 
```

ADD LOG RUN THROUGH

When the `balanced` partial GC cycle is triggered, the GC executes a STW pause to pause application threads ready for the first increment's operations. The STW pause is recorded in the logs by the `<exclusive-start>` element. 

```xml
<exclusive-start id="334" timestamp="2020-11-13T06:32:26.853" intervalms="879.225">
  <response-info timems="1.069" idlems="0.391" threads="2" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
```

A `balanced` partial GC cycle, which reduces the number of regions in the *eden* space, is triggered when the region count for the *eden* space reaches a *taxation* threshold. The logs record this trigger reason by using the`<allocation-taxation>` element.

```xml
<allocation-taxation id="335" taxation-threshold="805306368" timestamp="2020-11-13T06:32:26.853" intervalms="879.261" />
```

Details about the start of the cycle are recorded by the `<cycle-start>` element. The cycle is recorded as a `partial gc` with an `id=336`. Any subsequent elements associated with this cycle have a `contextid=336` to match the cycle's `id`. You can use the `contextid` value to distinguish increments and operations of the global cycle from the partial cycles that interleave with it.

```xml
<cycle-start id="336" type="partial gc" contextid="0" timestamp="2020-11-13T06:32:26.854" intervalms="879.022" />

```

The partial cycle begins its one and only GC increment, recorded using the `<gc-start>` element. You can understand the effect that the increment operations have on the heap by comparing snapshots of the memory taken at the start and the end of the increment.  The child elements <mem-info> and <mem> of the <gc-start> and <gc-end> elements record the amount of memory available and where it is located in the heap.

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

As expected, at the start of this increment, the eden region is full.

The following element, `<allocation-stats>`, indicates ?????

```xml
<allocation-stats totalBytes="804888808" >
  <allocated-bytes non-tlh="38821120" tlh="766067688" arrayletleaf="0"/>
  <largest-consumer threadName="Group1.Backend.CompositeBackend{Tier1}.2" threadId="00000000007B0700" bytes="111446656" />
</allocation-stats>
```

The operations and phases of the GC increment are run and details are recorded in the <gc-op> elements. The logs show that this increment begins with a copy forward operation followed by a class unload. Other `balanced` partial GC cycles can also include sweep and compact operations. For details about the operations involved in `balanced` partial GC cycles, see [Balanced Policy - GC Processing](gc.md#balanced-policy). 

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

The logs show that the copy forward operation acts on both *eden* regions, recorded as `type=eden`, and older regions recorded as `type=other`. 

<gc-end id="341" type="partial gc" contextid="336" durationms="152.457" usertimems="1148.000" systemtimems="16.000" stalltimems="26.991" timestamp="2020-11-13T06:32:27.007" activeThreads="8">
  <mem-info id="342" free="1549795328" total="3221225472" percent="48">
    <mem type="eden" free="1812208" total="58720256" percent="3" />
    <arraylet-reference objects="4" leaves="4" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <remembered-set count="1600224" freebytes="122377344" totalbytes="128778240" percent="95" regionsoverflowed="5" regionsstable="287" regionsrebuilding="0"/>
  </mem-info>
</gc-end>
<cycle-end id="343" type="partial gc" contextid="336" timestamp="2020-11-13T06:32:27.009" />
<exclusive-end id="344" timestamp="2020-11-13T06:32:27.010" durationms="157.314" />
```

The `<gc-end>` element records the end of the increment and details  the status of the memory. The memory is now ?????

```xml
<gc-end id="11" type="partial gc" contextid="4" durationms="500.843" usertimems="2952.000" systemtimems="32.000" stalltimems="954.701" timestamp="2020-11-13T06:31:36.409" activeThreads="8">
  <mem-info id="12" free="2954887168" total="3221225472" percent="91">
    <mem type="eden" free="1652152" total="266338304" percent="0" />
    <arraylet-reference objects="2" leaves="2" largest="1" />
    <arraylet-primitive objects="1" leaves="8" largest="8" />
    <pending-finalizers system="7" default="5" reference="11941" classloader="0" />
    <remembered-set count="203904" freebytes="127962624" totalbytes="128778240" percent="99" regionsoverflowed="1" regionsstable="0" regionsrebuilding="0"/>
  </mem-info>
</gc-end>
```

Memory has been reclaimed. The cycle completes and the GC restarts application threads.

```xml
<cycle-end id="13" type="partial gc" contextid="4" timestamp="2020-11-13T06:31:36.410" />
<exclusive-end id="14" timestamp="2020-11-13T06:31:36.410" durationms="503.067" />
```

**Summary of the example**

So from the structure and XML schema of the example, you can determine the following:
  
ADD


### Global mark GC cycle
 
The `balanced` policy’s global mark GC cycle, which runs when ...., consists of a mixture of STW and concurrent operations. The cycle is split into multiple increments that each run a *global mark phase* on one region of the heap. Each *global mark phase* consists of a ? operation that runs during a STW phause,followed by a processing operation that runs concurrently. 

The global mark GC cycle is divided into GC increments, as shown in the following table: 

|GC increment         | GC operations| *stop-the-world* or concurrent| XML element of GC increment| Details                         |
|---------------------|-------------|-------------------------------|--------------------------------------|-----------------------|
|From 1 to penultimate increment| global mark phase part 1 || *stop-the-world* | `<gc-start>`, `end` |The global mark phase operations start at the beginning of the cycle and run through all *regions* until the final *region* |
|| global mark phase part 2 | | concurrent | `<concurrent-start>`, `<concurrent-end>`| 
|final increment | final global mark phase, including a class unload | *stop-the-world* | `gc-start>`, `<gc-end>`| Final increment. executes the final global mark phase on the final *region* and executes operations to finish the cycle  |

START OF GLOBAL CYCLE

The first activity in the cycle is the start of a STW pause, recorded by an `<exclusive-start>` element. The GC pauses application threads to execute the cycle's initial operations. 

```xml
<exclusive-start id="345" timestamp="2020-11-13T06:32:27.347" intervalms="494.235">
  <response-info timems="3.588" idlems="1.693" threads="3" lastid="000000000074FF00" lastname="RunDataWriter.1" />
</exclusive-start>
```

```xml
<allocation-taxation id="346" taxation-threshold="402653184" timestamp="2020-11-13T06:32:27.348" intervalms="494.037" />
```
Details about the start of the global mark GC cycle are recorded by the `<cycle-start>` element. The cycle is recorded as a `global mark phase` with an `id=347`. Any subsequent elements associated with this cycle have a `contextid=347` to match the global mark GC cycle's `id`. You can use the `contextid` value to distinguish increments and operations of the global mark GC cycle from the partial cycles that interleave with it.

```xml
<cycle-start id="347" type="global mark phase" contextid="0" timestamp="2020-11-13T06:32:27.348" intervalms="55328.929" />
```

```xml
<gc-start id="348" type="global mark phase" contextid="347" timestamp="2020-11-13T06:32:27.348">
  <mem-info id="349" free="1147142144" total="3221225472" percent="35">
    <remembered-set count="1523648" freebytes="122683648" totalbytes="128778240" percent="95" regionsoverflowed="5" regionsstable="321" regionsrebuilding="0"/>
  </mem-info>
</gc-start>
```

```xml
<gc-op id="350" type="mark increment" timems="49.623" contextid="347" timestamp="2020-11-13T06:32:27.398">
  <trace-info objectcount="3117114" scancount="3048551" scanbytes="83420400" />
</gc-op>
```

```xml
<gc-end id="351" type="global mark phase" contextid="347" durationms="49.866" usertimems="344.000" systemtimems="40.000" stalltimems="9.352" timestamp="2020-11-13T06:32:27.398" activeThreads="8">
  <mem-info id="352" free="1147142144" total="3221225472" percent="35">
    <remembered-set count="1768768" freebytes="121703168" totalbytes="128778240" percent="94" regionsoverflowed="1" regionsstable="0" regionsrebuilding="326"/>
  </mem-info>
</gc-end>
```

```xml
<concurrent-start id="353" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:27.399">
  <concurrent-mark-start scanTarget="113512867" />
</concurrent-start>
```

Now that the global mark phase increment is complete, application threads are restarted:

```xml
<exclusive-end id="354" timestamp="2020-11-13T06:32:27.399" durationms="52.216" />
```

The garbage collector returns to running partial cycles to clear the heap before the next global mark phase increment is triggered. To see an example of how a `balanced` partial GC cycle appears in the logs, see the [`Balanced` examples - Partial GC Cycle](vgclog_examples#partial-gc-cycle).


ANOTHER global mark phase increment....associated with the same global cycle - can see from contextid. Look at gc0start id's and compare to previous increment. 

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
<gc-end id="371" type="global mark phase" contextid="347" durationms="0.219" usertimems="0.000" systemtimems="0.000" stalltimems="0.000" timestamp="2020-11-13T06:32:28.540" activeThreads="8">
  <mem-info id="372" free="1109393408" total="3221225472" percent="34">
    <remembered-set count="2199136" freebytes="119981696" totalbytes="128778240" percent="93" regionsoverflowed="1" regionsstable="13" regionsrebuilding="326"/>
  </mem-info>
</gc-end>
<concurrent-start id="373" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:28.541">
  <concurrent-mark-start scanTarget="118053382" />
</concurrent-start>
<exclusive-end id="374" timestamp="2020-11-13T06:32:28.554" durationms="15.172" />
```

More partial cycles. This pattern repeats until a global mark phase incremenet has been executed on all bar one region.

Final region - final increment of cycle. global mark phase then finishes the cycle:

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
<cycle-end id="497" type="global mark phase" contextid="347" timestamp="2020-11-13T06:32:34.314" />
<exclusive-end id="498" timestamp="2020-11-13T06:32:34.317" durationms="120.638" />
```