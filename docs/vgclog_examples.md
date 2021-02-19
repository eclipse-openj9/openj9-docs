# Log examples

To help you understand how garbage collection (GC) processes memory for your application and how these processes are recorded, a number of annotated log examples are provided from different GC policies. Each example covers a particular type of cycle from a particular policy. You will learn how to interpret the XML elements in the example log and determine the characteristics of the cycle that has been recorded.

## `gencon` examples

As detailed in [`gencon` policy (default)](gc.md#gencon-policy-default), this policy uses two types of cycle; a partial GC cycle and a global GC cycle. By default, the partial GC cycle runs a *stop-the-world* (STW) *scavenge* operation but can run a concurrent *scavenge* operation ([-Xgc:concurrentScavenge](xgc.md#concurrentscavenge)) if enabled at run time.

The start of a `gencon` cycle is recorded in the log by the following elements and attributes:

| GC cycle | Value of `type` attribute| Element that logs the cycle trigger|  Trigger reason|
|----------|----------------------------|------------------------------|------------------|
|Global    | `global`                   | `<concurrent-kickoff>`       | Low free memory tenure area threshold reached. Cycle trigger element is located before the `<cycle-start>` element|
|Partial | `scavenge`                   | `<af-start>`                 |Allocation failure. Cycle trigger element is located before the `<cycle-start>` element|

The element that records the cycle trigger is located before the '<cycle-start>` element and can be used to locate a particuler type of cycle. 

You can analyze the increments and operations that are associated with a particular type of cycle by locating and interpreting the elements in the following table:


| GC process             | Elements that log the start and end of the event  | Details |
|------------------------|------------------------------|------------------------------|
|GC cycle                |`<cycle-start>`, `<cycle-end>`| The start and end of a GC cycle.|
|GC STW increment        |`<gc-start>`, `<gc-end>`      | The start and end of a GC increment that begins with a pause.
|GC STW increment        | `<concurrent-kickoff>`       | The start of the initial GC increment of the global concurrent cycle that begins the initial mark operation
|GC STW increment        | `<concurrent-global-final> ` | The start of the final GC increment of the global concurrent cycle that executes the final collection|
|GC operations and sub-operations| `<gc-op>`                    | A GC operation such as mark or sweep, or a suboperation such as class unload. |

**Note:** For more information about the XML structure of GC cycles, see [GC cycles](vgclog.md/#gc-cycles). For more information about GC cycle increments, see [GC increments and interleaving](vgclog.md#gc-increments-and-interleaving).

The following examples use log excerpts to show how the different types of `gencon` cycle are logged.

### Scavenge partial GC cycle

The following example is taken from a `gencon` log. The output is broken down into sections with supporting text to explain the GC processing that is taking place.

By default, the `gencon` partial GC cycle runs by using a single *stop-the-world* (STW) pause. The cycle performs only 1 operation,  a *scavenge* operation, which runs only on the *nursery* area. The cycle consists of a single GC increment, which is labelled using the elements shown in the following table:

|GC Operation | GC increment | STW or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|------------------|--------------------------------------|---------------------------------------------------------------------------|
|Scavenge     |Single        | STW              | `<gc-start>`, `<gc-end>` |Contains detailed information about objects being copied and weak roots processing|

The scavenge partial GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some elements are omitted for clarity:

```xml

<exclusive-start/>          (STW Pause starts)

<af-start/>                 (allocation failure trigger recorded)

<cycle-start/>              (scavenge cycle starts)

<gc-start>                  (scavenge cycle increment starts)

<mem-info>                  (memory status before operation)

<mem></mem>                 (status of different types of memory)

</mem-info>

</gc-start>

<allocation-stats/>         (memory allocation status)

<gc-op> “scavenge"</gc-op>  (scavenge operation completed)

<gc-end>                    (scavenge cycle increment ends)

<mem-info>                  (memory status after operation)

<mem></mem>                 (status of different types of memory)

</mem-info>

</gc-end> 

</cycle-end>                (scavenge cycle ends)

<allocation-satisfied/>     (required allocation has been achieved)

<af-end/>

<exclusive-end>             (STW for scavenge cycle ends)
...

```

The first activity in the cycle is recorded by an `<exclusive-start>` element, which indicates the start of the STW pause. Application threads are halted to give
the garbage collector exclusive access to the heap:

```xml
<!-- Start of gencon scavenge partial GC cycle example -->

<exclusive-start id="12392" timestamp="2020-10-18T13:35:45.000" intervalms="406.180">
  <response-info timems="0.070" idlems="0.070" threads="0" lastid="00000000013D6900" lastname="LargeThreadPool-thread-68" />
</exclusive-start>
```

The `<af-start>` element indicates that the cycle was triggered by an allocation failure in the *nursery* (`type="nursery"`) area of the heap:

```xml
<af-start id="12393" threadId="00000000013D7280" totalBytesRequested="8200" timestamp="2020-10-18T13:35:45.000" intervalms="418.233" type="nursery" />
```

The `<cycle-start>` element marks the start of the cycle. The attribute `type="scavenge"` confirms that this activity is a *scavenge* partial GC cycle:

```xml
<cycle-start id="12394" type="scavenge" contextid="0" timestamp="2020-10-18T13:35:45.000" intervalms="418.231" />
```

Most elements are labeled with an `id` attribute that increases in value incrementally, a`timestamp` attribute, and a `contextid` attribute. All elements that record GC increments and operations that are associated with a particular cycle have a `contextid` value that matches the `id` value of the cycle. The `<cycle-start>` element of this example cycle has an `id="12394"`, so all subsequent elements that have a `contextid="4"`, such as the `<gc-start>` increment element and the `<gc-op>` operation element, are associated with this particular example cycle.  

The `<gc-start>` element records the first GC increment. In this section, you can find information about the amount of memory available (`<mem-info>`) and where it is located in the heap.

The memory snapshot within the `<gc-start>` element is taken before the *scavenge* operation and can be compared with a similar snapshot that is taken afterwards to understand the effect on the heap.

```xml
<gc-start id="12395" type="scavenge" contextid="12394" timestamp="2020-10-18T13:35:45.000">
  <mem-info id="12396" free="414960320" total="1073741824" percent="38">
    <mem type="nursery" free="0" total="268435456" percent="0">
      <mem type="allocate" free="0" total="241565696" percent="0" />
      <mem type="survivor" free="0" total="26869760" percent="0" />
    </mem>
    <mem type="tenure" free="414960320" total="805306368" percent="51">
      <mem type="soa" free="374694592" total="765040640" percent="48" />
      <mem type="loa" free="40265728" total="40265728" percent="100" />
    </mem>
    <remembered-set count="21474" />
  </mem-info>
</gc-start>
```

The heap memory allocation at the start of the increment is as follows:

- The *allocate* space of the *nursery* area is full, or close to full. The allocation failure was triggered by the lack of available memory in this space.
- The *survivor* space of the *nursery* area is reported as 'full' to reflect that no available memory is available to allocate to the mutator threads. The entire *survivor* space is reserved for GC operations during the GC increment. 
- The *tenure* area has 414.96MB (414960320) of free memory available.

The next element, `<allocation-stats>`, shows a snapshot that was taken before the cycle started of the allocation status of the mutator threads. In this example, the thread that used the most memory was `LargeThreadPool-thread-79`.

```xml
<allocation-stats totalBytes="235362176" >
  <allocated-bytes non-tlh="32880" tlh="235329296" />
  <largest-consumer threadName="LargeThreadPool-thread-79" threadId="00000000013F0C00" bytes="6288544" />
</allocation-stats>
```

The *scavenge* GC operation is recorded by the `<gc-op>` element; child elements record details about the operation. For example, 

```xml
<gc-op id="12397" type="scavenge" timems="11.649" contextid="12394" timestamp="2020-10-18T13:35:45.012">
  <scavenger-info tenureage="7" tenuremask="4080" tiltratio="89" />
  <memory-copied type="nursery" objects="154910" bytes="6027440" bytesdiscarded="394832" />
  <memory-copied type="tenure" objects="16171" bytes="562848" bytesdiscarded="3064" />
  <ownableSynchronizers candidates="10838" cleared="10824" />
  <references type="soft" candidates="24" cleared="0" enqueued="0" dynamicThreshold="16" maxThreshold="32" />
  <references type="weak" candidates="390" cleared="269" enqueued="269" />
  <references type="phantom" candidates="1" cleared="0" enqueued="0" />
  <object-monitors candidates="132" cleared="0"  />
</gc-op>
```

The `<memory-copied>` element indicates that 6 MB (6,027,440) of reachable objects were moved by the *scavenge* operation from the allocate space to the survivor space in the nursery area, and 562KB(562,848) were moved to the tenure area. 

The `<scavenger-info>` element shows that the *tenure age* is set to `7`. Any object in the *allocate* space with age below or equal to a tenure age of `7` is copied to the survivor space during this `scavenge`operation. Any object that has been copied between the *allocate* and *survivor* areas more than `7` times is moved to the tenure area. 

For more information about how the scavenge operation acts on the heap, see [`gencon` policy(default)](gc_overview.md#gencon-policy-default).

The end of the increment is recorded with `<gc-end>` and provides another snapshot of memory allocation on the heap, similar to `<gc-start>`. 

```xml
<gc-end id="12398" type="scavenge" contextid="12394" durationms="11.785" usertimems="46.278" systemtimems="0.036" stalltimems="0.145" timestamp="2020-10-18T13:35:45.012" activeThreads="4">
  <mem-info id="12399" free="649473560" total="1073741824" percent="60">
    <mem type="nursery" free="235142120" total="268435456" percent="87">
      <mem type="allocate" free="235142120" total="241565696" percent="97" />
      <mem type="survivor" free="0" total="26869760" percent="0" />
    </mem>
    <mem type="tenure" free="414331440" total="805306368" percent="51" macro-fragmented="0">
      <mem type="soa" free="374065712" total="765040640" percent="48" />
      <mem type="loa" free="40265728" total="40265728" percent="100" />
    </mem>
    <pending-finalizers system="0" default="0" reference="269" classloader="0" />
    <remembered-set count="13792" />
  </mem-info>
</gc-end>
```

The heap memory allocation at the end of the increment is as follows:

- 97% of the *allocate* space of the *nursery* area is now available as free memory.
- The *survivor* space of the *nursery* area is still reported as 'full' to reflect that the entire *survivor* space is reserved for GC operations during the next GC increment. 
- The *tenure* region has 414.33MB (414,331,440) of free memory available. The *scavenge* operation copied 562kB from the *nursery* region to the *tenure* region so less memory is now available in the *tenure* region.

The *scavenge* operation has successfully reclaimed memory in the *allocate* space of the nursery region by copying objects from the *allocate* space into the *survivor* space of the nursery region and tenure region.

The cycle ends (`<cycle-end>`). The following `<allocation-satisfied>` element indicates that the allocation request that caused the allocation failure should now be able to complete successfully. The STW pause ends with the `<exclusive-end>` element:

```xml
<cycle-end id="12400" type="scavenge" contextid="12394" timestamp="2020-10-18T13:35:45.012" />
<allocation-satisfied id="12401" threadId="00000000013D6900" bytesRequested="8200" />
<af-end id="12402" timestamp="2020-10-18T13:35:45.012" threadId="00000000013D7280" success="true" from="nursery"/>
<exclusive-end id="12403" timestamp="2020-10-18T13:35:45.012" durationms="12.319" />

<!-- End of gencon partial GC cycle example -->
```


#### Summary of the example

By analyzing the structure and elements of the example log output, this example shows that the global cycle has the following characteristics:

- The GC cycle begins with a STW pause due to an allocation failure.

- All GC operations and sub-operations that are associated with this cycle occur during the STW pause

- The cycle consists of only 1 GC increment which runs a single *scavenge* operation.

- The GC cycle reclaims memory in the *allocate* area of the *nursery* region by coping objects from the *allocate* area to the *survivor* area and also to the *tenure* region.

### Concurrent scavenge partial GC cycle (non-default)

When concurrent scavenge mode is enabled, the partial GC cycle is run as a [concurrent scavenge cycle](gc.md#concurrent-scavenge). When this mode is enabled, the scavenge partial GC cycle is divided into increments to enable part of the cycle to run in parallel to running application threads. Specifically, the cycle is divided into 3 GC increments as defined in the following table:

|GC Operation | GC increment | STW or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|-------------------------------|--------------------------------------|---------------------------------------------------------------------------|
|Scavenge     |initial        | STW              | `<gc-start>`, `<gc-end>`|Root scanning, reported as a single scavenge operation |
|Scavenge     |intermediate        | concurrent         | `<concurrent-start>`, `<concurrent-end>`|`<warning details=>`  Root scan, live objects traversed and evacuated (copy-forwarded). Reported as a scavenge operation |
|Scavenge     |final     | STW              | `<gc-start>`, `<gc-end>` |weak roots scanning, reported as a complex scavenge operation (`<gc-op>`) containing specific details for each of weak root groups  |

### Global GC cycle

The following example shows how a global GC cycle is recorded in a `gencon` policy verbose GC log. The output is broken down into sections with supporting text to explain the GC processing that is taking place.

The global GC cycle runs when the *tenure* area is close to full, which typically occurs after many partial cycles. As such, the output can be found part way down the full log. For more information about the GC Initialization section, see [Verbose GC log contents and structure ](vgclog-md#verbose-gc-log-contents-and-structure). For an example log output for a `gencon` partial cycle, see [Example - `gencon`’s default partial GC cycle](./vgclogs.md/#example-gencons-default-partial-gc-cycle).

 [The global GC cycle is split into 3 increments](./vgclog.md#gc-increments-and-interleaving) that interleave with partial GC cycles. Splitting the cycle operations into the following increments means that the STW pause times can be as short as possible, with the majority of the GC work being done concurrently. Specifically, the intermediate increment of the global cycle can run concurrently on the *tenure* area while the partial GC cycle runs on the *nursery* area. The interleaving can be seen in the following example, where a partial GC cycle is logged between the initial and final increments of the global cycle.

 The following elements log the GC increments and operations of the global GC cycle:

 |GC Operation         | GC increment| STW or concurrent| XML element of GC increment| Details                         |
|---------------------|-------------|-------------------------------|--------------------------------------|-----------------------|
|n/a - initiates cycle|initial      | STW              | `<concurrent-kickoff`        |No `<gc-op>` is logged. This increment just initiates the concurrent mark increment |
|concurrent mark      |intermediate |concurrent                     | `<gc-start>`, `<gc-end>`     |`<concurrent-trace-info>` records the progress of the concurrent mark increment|
|final collection     |final        | STW              | `<concurrent-global-final>`  |The increment is typically triggered when a card cleaning threshold is reached. The completion of a tracing phase can also trigger the increment. Operations and sub-operations include a final concurrent mark, a sweep, and an optional class unload and compact.|

The global GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and attributes and some child elements are omitted for clarity. The structure includes a partial GC cycle that starts and finishes within the global cycle:

```xml

<concurrent-kickoff/>       (global cycle 1st increment recorded)

<exclusive-start/>          (STW pause starts)

<cycle-start/>              (global cycle starts)

<exclusive-end/>            (STW pause ends)

(mutator threads running, global cycle concurrent increment running concurrently)


<exclusive-start/>          (STW for partial GC cycle starts)


...                         (partial GC cycle starts and completes)


<exclusive-end/>            (STW for partial GC cycle ends)

(mutator threads running, global cycle concurrent increment running concurrently)

<exclusive-start/>          (STW pause starts)

<concurrent-global-final/>  (global cycle final increment recorded)

<gc-start/>                 (global cycle final increment starts)

<allocation-stats/>

<mem-info>                  (memory status before operations)

<mem></mem>                 (status of different types of memory)

</mem-info>

</gc-start>

<gc-op> “type=rs-scan"</gc-op>        (remembered set scan completed)

<gc-op>”type=card-cleaning" </gc-op>  (card cleaning completed)

<gc-op> “type=mark”</gc-op>           (final mark operation and weak roots processing completed)

<gc-op> “type=classunload”</gc-op>    (class unload operation completed)

<gc-op ”type=sweep” />                (sweep operation completed)

<gc-end>                     (global cycle final increment ends)

<mem-info>                   (memory status after operations)

<mem></mem>                  (status of different types of memory)

</mem-info>

</gc-end> 

</cycle-end>                 (global cycle ends)

<exclusive-end>              (STW pause ends)

<exclusive-start>            (STW pause starts)
...

```

The first activity in the cycle is recorded by a `<concurrent-kickoff>` element, which records the start of the first of three increments that make up a `gencon` Global GC cycle. The `<concurrent-kickoff>` element records:

- the reason why the GC cycle was triggered. For a `gencon` global cycle, the cycle is triggered when the amount of available memory reaches a particular threshold value.
- The target number of bytes the cycle aims to reclaim in the heap.
- The current available memory in the different parts of the heap.

```xml
<concurrent-kickoff id="12362" timestamp="2020-10-18T13:35:44.341">

<kickoff reason="threshold reached" targetBytes="239014924" thresholdFreeBytes="33024922" remainingFree="32933776" tenureFreeBytes="42439200" nurseryFreeBytes="32933776" />

</concurrent-kickoff>
```

**Note:** To analyze specific parts of a cycle, you can search for the elements that mark a specific increment of the cycle. For example, you can search for the <concurrent-global-final> element to locate the final increment of the `gencon` global cycle. See the details of a particular cycle, such as the [`gencon` Global Cycle](./vgclog.md/#global-gc-cycle), to determine the element names for particular STW or concurrent GC increments or operations.

The next element recorded in the log, the `<exclusive-start>`element, tells you that a STW pause has started:

```xml
<exclusive-start id="12363" timestamp="2020-10-18T13:35:44.344" intervalms="342.152">

<response-info timems="0.135" idlems="0.068" threads="3" lastid="00000000015DE600" lastname="LargeThreadPool-thread-24" />

</exclusive-start>
```

The following <`gc-start>` element records details of the cycle that has started. 

```xml
<cycle-start id="12364" type="global" contextid="0" timestamp="2020-10-18T13:35:44.344" intervalms="516655.052" />
```

The `type` atrribute tells you that the cycle is a global cycle. The `contextid` of the cycle is `0` which indicates that all GC events associated with this cycle are tagged in relation to the `id` of this cycle. In particular, all subsequent elements recorded in the logs that are associated with this particular example cycle have a `contextid` value equal to the `<cycle-start>` `id` attribute value of `“12634”`.

The next element in the log is `<exclusive-end>` which records the end of the STW pause:

```xml
<exclusive-end id="12365" timestamp="2020-10-18T13:35:44.344" durationms="0.048" />
```

The operations and sub-operations of the second increment of the `gencon` global cycle are now running concurrently. 

<!--A blank line appears in the log after this `<exclusive-end>` line and before the next section, which tells you that no STW activities are running. However, concurrent activities might be running. In this case, the concurrent operations  and sub-operations of the second increment of the `gencon` global cycle are running.-->

The next section of the logs records an STW pause that is associated with an allocation failure. The `<cycle-start>` element that follows this STW pause indicates that the cycle is a `scavenge` cycle, which is the partial GC cycle used by the `gencon` GC:

```xml
...
<cycle-start id="12368" type="scavenge" contextid="0" timestamp="2020-10-18T13:35:44.582" intervalms="580.047" />
...
```

Subsequent elements have a `contextid=“12368”` which matches the `id` of this new `scavenge`cycle. For detailed information about how this cycle is recorded in the logs, see [Scavenge partial GC cycle](#scavenge-partial-gc-cycle).


<!---
```xml
<exclusive-start id="12366" timestamp="2020-10-18T13:35:44.582" intervalms="237.874">

<response-info timems="0.094" idlems="0.033" threads="5" lastid="00000000014E0F00" lastname="LargeThreadPool-thread-67" />

</exclusive-start>

...

<af-start id="12367" threadId="00000000013D7280" totalBytesRequested="96" timestamp="2020-10-18T13:35:44.582" intervalms="580.045" type="nursery" />

<cycle-start id="12368" type="scavenge" contextid="0" timestamp="2020-10-18T13:35:44.582" intervalms="580.047" />

...

<exclusive-end id="12377" timestamp="2020-10-18T13:35:44.594" durationms="11.816" />
```
--->

The operations and sub-operations of the second, concurrent increment of the `gencon` global cycle are paused while the STW `scavenge` operation is running, and resume when the STW pause finishes. 

After the partial GC cycle completes and the STW pause finishes, the log records a new STW pause, which is triggered to enable the final `gencon` global GC increment to run. This final increment finishes marking the nursery area and completes the global cycle. The `<exclusive-start>` element is followed by a `<concurrent-global-final>` element which logs the beginning of this final increment (and by implication, the end of the second increment).

```xml
<exclusive-start id="12378" timestamp="2020-10-18T13:35:44.594" intervalms="12.075">

<response-info timems="0.108" idlems="0.040" threads="3" lastid="00000000018D3800" lastname="LargeThreadPool-thread-33" />

</exclusive-start>

<concurrent-global-final id="12379" timestamp="2020-10-18T13:35:44.594" intervalms="516905.029" >

<concurrent-trace-info reason="card cleaning threshold reached" tracedByMutators="200087048" tracedByHelpers="12164180" cardsCleaned="4966" workStackOverflowCount="0" />

</concurrent-global-final>
```

The final increment of a global cycle is triggered when the card-cleaning threshold is reached, indicated by the `reason` attribute of the `<concurrent-trace-info>` element.

In the next section that begins with the `gc-start` element, you can find information about the amount of memory available (`<mem-info>`) and where it is located in the heap. This snapshot is taken before the final increment's operations and sub-operations are run and can be compared with a similar snapshot that is taken afterwards to understand the effect on the heap. The child element attribute values of the`<mem>` and `<mem-info>` elements indicate the status of the memory. 

**Note:** You can double check that the increment is associated with the GC global cycle in the example by checking the `contextid` attribute value matches the `id=12364` attribute value of the cycle's <gc-cycle> element.

```xml
<gc-start id="12380" type="global" contextid="12364" timestamp="2020-10-18T13:35:44.594">

<mem-info id="12381" free="277048640" total="1073741824" percent="25">

<mem type="nursery" free="234609440" total="268435456" percent="87">

<mem type="allocate" free="234609440" total="241565696" percent="97" />

<mem type="survivor" free="0" total="26869760" percent="0" />

</mem>

<mem type="tenure" free="42439200" total="805306368" percent="5">

<mem type="soa" free="2173472" total="765040640" percent="0" />

<mem type="loa" free="40265728" total="40265728" percent="100" />

</mem>

<pending-finalizers system="0" default="0" reference="405" classloader="0" />

<remembered-set count="17388" />

</mem-info>

</gc-start>

<allocation-stats totalBytes="827488" >

<allocated-bytes non-tlh="96" tlh="827392" />

<largest-consumer threadName="LargeThreadPool-thread-68" threadId="00000000013D6900" bytes="65632" />

</allocation-stats>
```

For this example, at the start of this GC increment, 25% of the total heap is available as free memory. This free memory is split between the following areas of the heap:

- The *nursery* area, which has 87% of its total memory available as free memory. The free memory is only available in the *allocate* space of the *nursery* area. The *survivor* space has no free memory.

- The *tenure* area, which has 5% of its total memory available as free memory. All of this free memory is in the long object allocation area(LOA). No free memory is available in the short object allocation area(SOA).

The `<gc-op>` elements and their child elements contain information about the increment’s operations and sub-operations. The final increment of the `gencon` global cycle consists of multiple operations, each logged with a `<gc-op>` element. The type of operation is shown by the `<gc-op>` `type` attribute. There are 5 types of operation involved in the final increment of the example log:

1. `Rs-scan`
2. `Card-cleaning`
3. `Mark`
4. `Classunload`
5. `Sweep`

**Note:** The final increment of a `gencon` global cycle can include an optional compact sub-operation. 

**Note:** For more information about the different types of GC operation, see [GC Operations](gc_overview.md#gc-operations).

```xml
<gc-op id="12382" type="rs-scan" timems="3.525" contextid="12364" timestamp="2020-10-18T13:35:44.598">

<scan objectsFound="11895" bytesTraced="5537600" workStackOverflowCount="0" />

</gc-op>

<gc-op id="12383" type="card-cleaning" timems="2.910" contextid="12364" timestamp="2020-10-18T13:35:44.601">

<card-cleaning cardsCleaned="3603" bytesTraced="5808348" workStackOverflowCount="0" />

</gc-op>

<gc-op id="12384" type="mark" timems="6.495" contextid="12364" timestamp="2020-10-18T13:35:44.607">

<trace-info objectcount="1936" scancount="1698" scanbytes="61200" />

<finalization candidates="389" enqueued="1" />

<ownableSynchronizers candidates="5076" cleared="523" />

<references type="soft" candidates="18420" cleared="0" enqueued="0" dynamicThreshold="32" maxThreshold="32" />

<references type="weak" candidates="19920" cleared="114" enqueued="60" />

<references type="phantom" candidates="671" cleared="50" enqueued="50" />

<stringconstants candidates="40956" cleared="109" />

<object-monitors candidates="182" cleared="51" />

</gc-op>

<gc-op id="12385" type="classunload" timems="1.607" contextid="12364" timestamp="2020-10-18T13:35:44.609">

<classunload-info classloadercandidates="425" classloadersunloaded="6" classesunloaded="2" anonymousclassesunloaded="1" quiescems="0.000" setupms="1.581" scanms="0.019" postms="0.007" />

</gc-op>

<gc-op id="12386" type="sweep" timems="9.464" contextid="12364" timestamp="2020-10-18T13:35:44.618" />
```

The end of the increment is recorded with `<gc-end>` and provides another snapshot of memory in the heap, similar to `<gc-start>`. In the example, 60% of the heap now contains free memory as a result of the final global cycle increment. The *nursery* area remains unchanged which means the final increment reclaimed memory only in the *tenure* space, which now has 51% of free memory available. The LOA is now all available as free memory. The SOA has 48% of it's memory available as free memory.

**Note:** The global GC cycle runs to reclaim memory in the *tenure* area. The freeing up of memory in the *nursery* area is achieved by using the partial GC cycle. For more information, see [`gencon` Policy(default)](gc.md#gencon-policy-(default)).


```xml
<gc-end id="12387" type="global" contextid="12364" durationms="24.220" usertimems="86.465" systemtimems="0.000" stalltimems="2.846" timestamp="2020-10-18T13:35:44.618" activeThreads="4">

<mem-info id="12388" free="650476504" total="1073741824" percent="60">

<mem type="nursery" free="235516088" total="268435456" percent="87">

<mem type="allocate" free="235516088" total="241565696" percent="97" />

<mem type="survivor" free="0" total="26869760" percent="0" />

</mem>

<mem type="tenure" free="414960416" total="805306368" percent="51" micro-fragmented="98245682" macro-fragmented="0">

<mem type="soa" free="374694688" total="765040640" percent="48" />

<mem type="loa" free="40265728" total="40265728" percent="100" />

</mem>

<pending-finalizers system="1" default="0" reference="515" classloader="0" />

<remembered-set count="13554" />

</mem-info>

</gc-end>
```

After the GC operations and sub-operations of the final increment of the global cycle complete, the global cycle ends and the STW pause ends, as shown in the following output:

```xml
<cycle-end id="12389" type="global" contextid="12364" timestamp="2020-10-18T13:35:44.619" />

<exclusive-end id="12391" timestamp="2020-10-18T13:35:44.619" durationms="24.679" />
```

### Summary of the example

By analyzing the structure and elements of the example log output, this example shows that the global cycle has the following characteristics:

- The GC global cycle is triggered when a memory threshold is reached and begins with an STW pause

- After the first increment of the GC global cycle completes, the STW pause ends and the second increment runs concurrently.

- While the second increment is running concurrently, a partial GC cycle starts and finishes.

- When the second increment completes, an STW pause begins so that the third and final increment of the global cycle, which consists of five operations and sub-operations, can run.

- The global GC cycle reclaims memory in the *tenure* area.-->


## `balanced` example

Add here
