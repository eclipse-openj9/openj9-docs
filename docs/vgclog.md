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

# Verbose Garbage Collection Logs 

[Garbage collection](gc.md)(GC) reclaims used memory in the heap for reuse. During cleanup of the heap, the verbose garbage collection logs capture information about the different garbage collection operations that are involved in the garbage collection cycles. GC operations include GC initialization, stop-the-world processing, finalization, reference processing, and allocation failures. 

A garbage collection event is triggered in the following situations: 

- The VM calls \system.gc() to run a global *stop-the-world* GC. 

- A memory allocation failure. Allocation failures are triggered when a request to allocate an object fails due to a full region of the heap. 

- Available memory in a region of the heap size decreased to a threshold value that triggers a preemptive cleaning of the heap.

- Intermediate or final stages of a concurrent GC cycle are reached. A *stop-the-world* collection is run to complete the incremental or final stage. 

Verbose GC logs contain information on garbage collection operations to assist with the following actions:  

- Tuning GC performance to reduce pause times and speed up GC operations

- Troubleshooting GC operations and policies. For example, analyzing long pauses, or determining how free memory is divided in the heap before and after a GC cycle. 

Verbose GC logs, when enabled, begin capturing information as soon as garbage collection is initialized. By default, the information is printed to STDERR. The information can also be [printed to a file](dump_gcdump.html/#how-to-generate-a-verbonse-garbage-collection-log).

To help you visualize and analyze the garbage collection, you can feed verbose GC log files into various diagnostic tools and interfaces. Examples include tools such as the [Garbage Collection and memory visualizer extension for Eclipse](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv), and online services such as [GCEasy](https://gceasy.io). 

**Note:** You can run one or more GC traces by using the (['-XTgc' option](xtgc.md)) to get more detailed information to help diagnose GC problems or perform finer tuning.

## How to generate a verbose garbage collection log 

You can enable verbose GC logs by specifying the `-verbose:gc` standard option when you start your application. For more information, see [standard command-line options](cmdline_general).

The output of `-verbose:gc` is printed to STDERR by default. To print the log output to a file, append the `-Xverbosegclog` option. You can print the verbose GC log file to a succession of files, where each file logs a specified number of GC cycles, by using additional parameters of `-Xverbosegclog` option. For more information, see the command-line option [-Xverbosegclog](xverbosegclog.md).

## Verbose GC log contents 

The Verbose garbage collection logs are printed in XML format and consist of the following sections:  

- a summary of your GC configuration, which is captured in the stanza beginning with the [`<INITIATIZED>` tag](dump_gcdump.html#garbage-collection-configuration) 

- stanzas that contain information about the different garbage collection cycles run, including the GC increments and GC operations that made up the GC cycle. 

The verbose GC logs are event-based, recording each *stop-the-world* event as it happens. While *stop-the-world* cycles are fully logged, some aspects of concurrent cycles are also logged, even though operations that are running concurrently with application threads are not logged. Concurrent operations run as part of a concurrent cycle, and these concurrent cycles begin and finish with *stop-the-world* events - events that are logged. (For some garbage collectors, the concurrent cycle also includes intermediate *stop-the-world* events). In addition, for some types of concurrent collections, verbose GC logs also include information about when certain targets are reached. 

The highest level stanzas in the verbose GC log represent GC cycles and begin with xml tags that define the kickoff event and reason. Except for some of the more complex GC algorithms (such as the global marking phase GC of the balanced policy), a GC cycle, when complete, generally results in reclaimed memory for reuse. Each cycle consists of 1 or more GC increments and GC operations.  

Events that make up a garbage collector cycle are listed in the stanza in a linear fashion, but are ‘nested’ by a start and end tag that identify the start and end of an event. Details of the event are recorded in the XML attributes associated with the tag and also within tags that are nested within the GC event's start and end tags.

The following table shows the tagging that is used for the nested GC processes: 

| GC Process   | Start and end tag in verbose GC log  | Details                                                         |
|--------------|-------------------------------|-----------------------------------------------------------------|
| GC cycle     | `<cycle-start>`, `<cycle-end>`| Completion of GC cycle results in reclaimed memory in most cases|
| GC increment | `<gc-start>`, `<gc-end>`      | 1 or more GC increments make up a GC cycle                      |
| GC operation | `<gc-op>`                     | 1 or more GC operations make up a GC increment. Examples of a GC operation include mark, sweep, scavenge.|

**Note:** GC operations are the smallest fragment in a verbose GC log. All details of a GC operation are logged by using a single tag rather than start and end tags.  

### An example of a *stop-the-world* cycle 
 
This example shows output of a simple *stop-the-world* GC cycle from the verbose GC log of a gencon policy collection: 
```
<exclusive-start id="2" timestamp="2020-10-18T13:27:09.603" intervalms="1913.853"> 

  <response-info timems="0.030" idlems="0.030" threads="0" lastid="0000000000AC4600" lastname="LargeThreadPool-thread-3" /> 

</exclusive-start> 

<af-start id="3" threadId="0000000000AC4F80" totalBytesRequested="272" timestamp="2020-10-18T13:27:09.603" intervalms="1913.921" type="nursery" /> 

<cycle-start id="4" type="scavenge" contextid="0" timestamp="2020-10-18T13:27:09.603" intervalms="1913.959" /> 

<gc-start id="5" type="scavenge" contextid="4" timestamp="2020-10-18T13:27:09.603"> 

  <mem-info id="6" free="802568640" total="1073741824" percent="74"> 

    <mem type="nursery" free="0" total="268435456" percent="0"> 

      <mem type="allocate" free="0" total="134217728" percent="0" /> 

      <mem type="survivor" free="0" total="134217728" percent="0" /> 

    </mem> 

    <mem type="tenure" free="802568640" total="805306368" percent="99"> 

      <mem type="soa" free="762302912" total="765040640" percent="99" /> 

      <mem type="loa" free="40265728" total="40265728" percent="100" /> 

    </mem> 

    <remembered-set count="30758" /> 

  </mem-info> 

</gc-start> 

<allocation-stats totalBytes="136862352" > 

  <allocated-bytes non-tlh="7097008" tlh="129765344" /> 

  <largest-consumer threadName="Start Level: Equinox Container: c25c3c67-836a-49e7-9949-a16d2c2a5a4d" threadId="000000000045E300" bytes="46137952" /> 

</allocation-stats> 

<gc-op id="7" type="scavenge" timems="30.675" contextid="4" timestamp="2020-10-18T13:27:09.634"> 

  <scavenger-info tenureage="1" tenuremask="fffe" tiltratio="50" /> 

  <memory-copied type="nursery" objects="461787" bytes="25522536" bytesdiscarded="367240" /> 

  <finalization candidates="317" enqueued="157" /> 

  <ownableSynchronizers candidates="6711" cleared="2579" /> 

  <references type="soft" candidates="9233" cleared="0" enqueued="0" dynamicThreshold="32" maxThreshold="32" /> 

  <references type="weak" candidates="13149" cleared="4781" enqueued="4495" /> 

  <references type="phantom" candidates="359" cleared="8" enqueued="8" /> 

  <object-monitors candidates="79" cleared="36"  /> 

</gc-op> 

<gc-end id="8" type="scavenge" contextid="4" durationms="30.815" usertimems="89.130" systemtimems="3.179" stalltimems="1.944" timestamp="2020-10-18T13:27:09.634" activeThreads="4"> 

  <mem-info id="9" free="910816000" total="1073741824" percent="84"> 

    <mem type="nursery" free="108247360" total="268435456" percent="40"> 

      <mem type="allocate" free="108247360" total="134217728" percent="80" /> 

      <mem type="survivor" free="0" total="134217728" percent="0" /> 

    </mem> 

    <mem type="tenure" free="802568640" total="805306368" percent="99"> 

      <mem type="soa" free="762302912" total="765040640" percent="99" /> 

      <mem type="loa" free="40265728" total="40265728" percent="100" /> 

    </mem> 

    <pending-finalizers system="1" default="156" reference="4503" classloader="0" /> 

    <remembered-set count="30758" /> 

  </mem-info> 

</gc-end> 

<cycle-end id="10" type="scavenge" contextid="4" timestamp="2020-10-18T13:27:09.635" /> 

<allocation-satisfied id="11" threadId="0000000000AC4600" bytesRequested="272" /> 

<af-end id="12" timestamp="2020-10-18T13:27:09.635" threadId="0000000000AC4F80" success="true" from="nursery"/> 

<exclusive-end id="13" timestamp="2020-10-18T13:27:09.635" durationms="31.984" /> 
```
You can analyze this *stop-the-world* cycle by inspecting a few tags and xml attributes.  

- **`<exclusive-start>`** The *stop-the-world* cycle stanza begins with a triggering event, represented for *stop-the-world* cycles with a <exclusive-start> tag. The triggering event for the other type of GC cycle, a concurrent cycle, is recorded by the `<concurrent-kickoff>` tag.

- **`<af-start>`** The reason for the triggering of this GC cycle is recorded by the `<af-start>` tag, which indicates that the triggering event is an allocation failure.

- **`<cycle-start>`** The beginning of the GC cycle itself is recorded by the `<cycle-start>` tag and contains the XML attribute `type=scavenge` to describe the GC involved in this GC cycle.  

- **`timestamp`**, **`id`**, and **`context-id`** Each event that is logged in the GC cycle stanza is labeled with a `timestamp`,`id`, and `context-id` XML attribute. The `id` attribute increases incrementally with each event. All gc increments, operations, and concurrent tags associate with a particular cycle have a `contxtid` value that matches the `id` value of the cycle. 

- **`<gc-start>`** The `<gc-start>` tag logs the beginning of a GC increment, in this case of type `scavenge`, nested within the GC cycle. 

- **`<gc-op>`** Nested within the GC increment is the `<gc-op>` tag, which logs information about the individual GC ops that make up the GC increment. In this case, only one operation, the scavenge operation, makes up the GC increment, and only one GC increment makes up this *stop-the-world* cycle. In particular, a scavenge operation evacuates the local area of the heap in a single operation and so a single operation makes up the whole GC cycle. 
 A GC algorithm can also involve multiple GC operations, such as a combination of mark, sweep, compact, or copy. For a common GC of mark-sweep, the GC increment consists of two operations, mark and sweep. This increment might be accompanied by other GC increments to make up a GC cycle that reclaims memory.

- **`'type'`** *Stop-the-world* and concurrent cycles can be solely local or global events, or can consist of a mixture of local and global operations. You can determine whether the cycles, increments, or operations are local or global from the XML attribute `“type”`. The gencon policy has two types of GC collection; global concurrent, and scavenge, which is a local operation.


<!-- Add link to topics for analysing logs and tag definitions once they have been created -->

## Using the verbose GC log to troubleshoot

### *Stop-the-world* cycles and concurrent cycles 

Garbage collectors can be considered to implement two types of gc cycle - *stop-the-world* cycles and concurrent cycles. The first step in reading the verbose GC log is to understand how these different types of cycle are recorded. 

Verbose GC logs only record *stop-the-world* events, which are events that involve pauses of the application threads of the VM. *Stop-the-world* cycles consist solely of *stop-the-world* events so all events of a *stop-the-world* cycle are recorded in the verbose GC logs. For concurrent cycles, while some operations of concurrent cycles run at the same time as application threads, others run as *stop-the-world* events. In particular, concurrent cycles consist of: 

- *stop-the-world* events that begin and complete the cycle, and sometimes intermediate *stop-the-world* events. 

- Concurrent events that do not require exclusive access to the JVM, such as the collection of memory that is marked as garbage. 

Because *stop-the-world* events run in both *stop-the-world* cycles and concurrent cycles, the verbose GC logs record useful information about both types of cycle. 
 
You can locate the different types of gc cycle within the logs by searching for specific tags or `type` values. You can then identify all gc increments, operations and, if applicable, concurrent events by searching for a `contextid` value that is equal to the value of the cycle's `id` attribute. For example, in this example of part of a log output for a gencon policy garbage collection, you can identify the following features:

- The [scavenge collector of gencon](gc.md#garbage-collection-policies) by searching for `type=”scavenge”`

- The beginning of the *stop-the-world* event by locating the `<exclusive-start>` tag

- The GC increments associated with the GC cycle. The `id` value of the GC cycle start event is `id="16"`, so the associated GC increment, which is tagged `<gc-start>`, has a `contextid` value of `contextid="16"`.

```
<exclusive-start id="14" timestamp="2020-10-18T13:27:11.442" intervalms="1839.193"> 

<response-info timems="0.034" idlems="0.034" threads="0" lastid="00000000014E2F00" lastname="LargeThreadPool-thread-21" /> 

</exclusive-start> 

<af-start id="15" threadId="00000000014E3880" totalBytesRequested="72" timestamp="2020-10-18T13:27:11.442" intervalms="1839.205" type="nursery" /> 

<cycle-start id="16" type="scavenge" contextid="0" timestamp="2020-10-18T13:27:11.442" intervalms="1839.206" /> 

<gc-start id="17" type="scavenge" contextid="16" timestamp="2020-10-18T13:27:11.442"> 

<mem-info id="18" free="801095872" total="1073741824" percent="74"> 

<mem type="nursery" free="0" total="268435456" percent="0"> 

```

The following example shows the beginning of a [concurrent global cycle for a gencon garbage collection](gc.md#garbage-collection-policies). This cycle can be located in the verbose GC logs by searching for the `<concurrent-kickoff>` tag, and also noting the `type="global"` tag. In this example, the `<concurrent-kickoff>` tag precedes an `<exclusive-start>` tag that marks the start of a *stop-the-world* event. Therefore, the following part of the log shows the *stop-the-world* event that begins the gencon’s concurrent global cycle collection:

```
<concurrent-kickoff id="12362" timestamp="2020-10-18T13:35:44.341"> 

<kickoff reason="threshold reached" targetBytes="239014924" thresholdFreeBytes="33024922" remainingFree="32933776" tenureFreeBytes="42439200" nurseryFreeBytes="32933776" /> 

</concurrent-kickoff> 

<exclusive-start id="12363" timestamp="2020-10-18T13:35:44.344" intervalms="342.152"> 

<response-info timems="0.135" idlems="0.068" threads="3" lastid="00000000015DE600" lastname="LargeThreadPool-thread-24" /> 

</exclusive-start> 

<cycle-start id="12364" type="global" contextid="0" timestamp="2020-10-18T13:35:44.344" intervalms="516655.052" /> 

<exclusive-end id="12365" timestamp="2020-10-18T13:35:44.344" durationms="0.048" /> 

```

Finally, the following example from a balanced policy garbage collection log shows the start of a [concurrent global marking collection](gc.md#garbage-collection-policies), which can be identified by either:

- Searching for the `<concurrent-start>` tag, determining the `contextid` value and searching backwards in the log for the gc cycle with an `id` value that matches this `contextid` value.

- Searching for `type="global mark phase"` to locate the GC cycle for the global marking collection. 

```   
<exclusive-start id="345" timestamp="2020-11-13T06:32:27.347" intervalms="494.235"> 

<response-info timems="3.588" idlems="1.693" threads="3" lastid="000000000074FF00" lastname="RunDataWriter.1" /> 

</exclusive-start> 

<allocation-taxation id="346" taxation-threshold="402653184" timestamp="2020-11-13T06:32:27.348" intervalms="494.037" /> 

<cycle-start id="347" type="global mark phase" contextid="0" timestamp="2020-11-13T06:32:27.348" intervalms="55328.929" /> 

<gc-start id="348" type="global mark phase" contextid="347" timestamp="2020-11-13T06:32:27.348"> 

<mem-info id="349" free="1147142144" total="3221225472" percent="35"> 

<remembered-set count="1523648" freebytes="122683648" totalbytes="128778240" percent="95" regionsoverflowed="5" regionsstable="321" regionsrebuilding="0"/> 

</mem-info> 

</gc-start> 

<gc-op id="350" type="mark increment" timems="49.623" contextid="347" timestamp="2020-11-13T06:32:27.398"> 

<trace-info objectcount="3117114" scancount="3048551" scanbytes="83420400" /> 

</gc-op> 

<gc-end id="351" type="global mark phase" contextid="347" durationms="49.866" usertimems="344.000" systemtimems="40.000" stalltimems="9.352" timestamp="2020-11-13T06:32:27.398" activeThreads="8"> 

<mem-info id="352" free="1147142144" total="3221225472" percent="35"> 

<remembered-set count="1768768" freebytes="121703168" totalbytes="128778240" percent="94" regionsoverflowed="1" regionsstable="0" regionsrebuilding="326"/> 

</mem-info> 

</gc-end> 

<concurrent-start id="353" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:27.399"> 

<concurrent-mark-start scanTarget="113512867" /> 

</concurrent-start> 

<exclusive-end id="354" timestamp="2020-11-13T06:32:27.399" durationms="52.216" /> 

 
 

<concurrent-end id="355" type="GMP work packet processing" contextid="347" timestamp="2020-11-13T06:32:27.538"> 

<concurrent-mark-end bytesScanned="113629324" reasonForTermination="Work target met" /> 

</concurrent-end>
```

You can determine the following features by analyzing this portion of the log: 

- The `<exclusive-start>` tag precedes the`<cycle-start>` tag, indicating that the concurrent global marking starts with a *stop-the-world* event.

- The *stop-the-world* event consists of two operations; the mark increment operation and mark phase operation. After these 2 GC operations, a GMP work processing event starts as a concurrent event.

- No *stop-the-world* events are running when the concurrent threads are running. Blank lines indicate that no *stop-the-world* events ran, so the placement of the blank line between the start and end concurrent event indicates that no interleaving occurred. For more information about how to interpret blank lines in the log and the interleaving of *stop-the-world* cycles and concurrent cycles, see the following section [Interleaving of concurrent cycles and *stop-the-world* cycles](vgclog.md#interleaving-of-concurrent-cycles-and-stop-the-world-cycles). 

- The `ReasonForTermination` attribute indicates that the concurrent event ended because the work target was met. 



### Interleaving of concurrent cycles and *stop-the-world* cycles  

Concurrent cycles involve concurrent events that, by definition, can run during application threads or *stop-the-world* events. As such, *stop-the-world* cycles can appear in the logs in between the *stop-the-world* events of a concurrent cycle.   

Blank lines occur when no *stop-the-world* event is running, so blank lines separate individual *stop-the-world* cycles. Blank lines can also exist within a concurrent cycle, separating the *stop-the-world* events of a particular concurrent cycle, and also separating *stop-the-world* cycles that interleave with the concurrent cycle’s *stop-the-world* events.  

You can analyze any interleaving by locating the `<cycle-start>` and `<cycle-end>` tags that nest a cycle and the tags that log the concurrent events. Use the tags to locate the start and end of a concurrent cycle, and to determine whether a particular *stop-the-world* event is part of a *stop-the-world* cycle or concurrent cycle. 

The concurrent gc cycles that are used by most gc policies in OpenJ9 consist of only two *stop-the-world* events; a kickoff *stop-the-world* event and a final collection *stop-the-world* event. Some policies, such as the balanced policy, also include intermediate *stop-the-world* events during the cycle. For example, the concurrent global mark cycle of the balanced policy can include intermediate *stop-the-world* "mark" GC increments.
 
See [*stop-the-world* events of the Gencon policy gc](vgclog.md#stop-the-world-events-of-the-gencon-policy) for details of *stop-the-world* events and their associated verbose GC log tags. For more information about the verbose GC tags used for other GC gc policies, see [Verbose GC log XML tags and attributes](vgclog.md#xml-tags-and-attributes). 

### Stop-the-world events of the Gencon policy 

The OpenJ9 default policy is the [Gencon policy](./gc.md) and consists of two types of garbage collection – *scavenge* and *concurrent global mark*. You can also enable the non-default gencon *concurrent scavenge* collector. The concurrent cycle consists of two *stop-the-world* events - a kickoff event and a final collection event. 

The different garbage collections can be located in the logs by searching for the following tags that are associated with *stop-the-world* events: 

*stop-the-world* cycle or concurrent cycle| Gencon Collector | Local or global  | Stop-the-world event | concurrent tag |  Details |
|----------------------|------------------|------------------|----------------------------|----------------|----------|
| *stop-the-world* cycle | scavenge| local | scavenge | n/a  | Moves nursery objects by using one gc operation only |
| concurrent cycle| concurrent global mark and sweep   | global | kickoff of concurrent cycle| `<concurrent-kickoff>`|First *stop-the-world* event of a concurrent cycle |
|concurrent cycle | concurrent global mark and sweep | global | final collection of the cycle, which is a stop-the-world event | `<concurrent-collection-start>`,`<concurrent-collection-end>`  | Final *stop-the-world* GC event of a concurrent cycle|
| concurrent cycle | concurrent scavenge | local |n/a |`<concurrent-collection-end>` | not enabled by default|   


The *stop-the-world* events that are part of Gencon’s concurrent global cycle consist of the following gc operations:  


| *stop-the-world* event of the concurrent cycle | GC operations|
|-----------------------------------|--------------|
| kickoff event| scavenge|
| final collection stop-the-world event| tracing|
| | RS-scan|
| | card-cleaning|
| | mark-and-sweep|

**Note:** If two explicit global concurrent garbage collections are triggered in close succession, one of these concurrent collections processes a heap compaction. To prevent a compaction during a \system.gc() collection, you can specify the `-Xnocompactexplicitgc` option. 
 

For more information about the tags, xml attributes and values that are used in verbose GC logs, see [verbose GC log XML tags and attributes](vgclog.md#xml-tags-and-attributes). 

## Analyzing GC pauses 

When you analyze the logs for particular events that require exclusive access to the VM, you are analyzing *stop-the-world* events. During a *stop-the-world* event, an application is stopped so that the GC has exclusive access to the VM for actioning the freeing up of memory and memory compaction.

The following scenarios are examples of how you can use the verbose GC logs to troubleshoot and improve performance: 

- You determine that your application’s performance is slow due to global collections that include compactions. The non-default balanced policy would be a better gc policy choice for your VM. 

- The `“durationms”` value of the final *stop-the-world* event of a concurrent global GC cycle is long. You analyze the individual GC operations of a *stop-the-world* GC cycle to determine which operations are causing the biggest pauses. You modify the configuration of your GC to reduce this pause.

The following table lists some tags that provide useful information for analyzing pauses in the VM. You can use these tags for the following actions:

- Determining where the longest pauses are during a GC.

- Determining how memory allocation in the heap is modified before and after particular GC increments and operations are complete. 


| XML tag | useful attribute or nested XML tag| Details |
|----------|----------------------------|--------|
|all GC event tags | `timestamp` |time the event was logged|
|various |  `timem"` | duration of the GC event | 
|various | `durationms`| duration of a GC increment that contains multiple GC events |
`<af-start>` | various | The XML tag contains a GC cycle that is triggered by an allocation failure|
|various | `reason` | Reason for triggering the GC cycle or event within a GC cycle.|
| various | `reasonForTermination`| Reason for ending a GC cycle, increment, or operation |
|`<concurrent-trace-info>`| various| records why the final *stop-the-world* cycle of a concurrent collection was triggered|
| `<concurrent-mark-start>`| `scan-target`| target bytes to be marked during the concurrent mark collection |
|`<gc-start>`| `<mem-info>`|cumulative amount of free space and total space in the heap|
|`<mem-info>`| `<mem>`| records division of available memory across the different areas of the heap by using attribute such as `mem-type`|

<!-- ### Heap sizing using the verbose GC logs --!>

<!-- ## XML tags and attributes --!>
 

<!-- ==== END OF TOPIC ==== cmdline_specifying.md ==== -->