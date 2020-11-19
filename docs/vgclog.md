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

The verbose GC logs are event-based, recording each *stop-the-world* event as it happens. Operations that are running concurrently with application threads are not logged. However, concurrent operations run as part of a concurrent cycle, and these concurrent cycles begin and finish with *stop-the-world* events. So you can locate and analyze aspects of the concurrent cycles in the verbose GC logs that run concurrent events. (For some garbage collectors, the concurrent cycle also includes intermediate *stop-the-world* events).  

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

- **`<af-start>`** The reason for the triggering of this GC cycle is recorded by the `<af-start>` tag, indicating an allocation failure.  

- **`<cycle-start>`** The beginning of the GC cycle itself is recorded by the `<cycle-start>` tag and contains the XML attribute `type=scavenge` to describe the GC involved in this GC cycle.  

- **`timestamp`**, **`id`**, and **`context-id`** Each event that is logged in the GC cycle stanza is labeled with a `timestamp` and `id` xml attribute. The `id` attribute increases incrementally with each event so that you can use the id to search for particular events. You can use the `context-id` attribute to identify GC events that are part of a GC increment, for example incremental GC events of an incremental-concurrent collection.

- **`<gc-start>`** The `<gc-start>` tag logs the beginning of a GC increment, in this case of type `scavenge`, nested within the GC cycle. 

- **`<gc-op>`** Nested within the GC increment is the `<gc-op>` tag, which logs information about the individual GC ops that make up the GC increment. In this case, only one operation, the scavenge operation, makes up the GC increment, and only one GC increment makes up this *stop-the-world* cycle. In particular, a scavenge operation evacuates the local area of the heap in a single operation and so a single operation makes up the whole GC cycle. 
 
A GC algorithm can also involve multiple GC operations, such as a combination of mark, sweep, compact, or copy. For a common GC of mark-sweep, the GC increment consists of two operations, mark and sweep. This increment might be accompanied by other GC increments to make up a GC cycle that reclaims memory.


<!-- Add link to topics for analysing logs and tag definitions once they have been created -->
<!-- ==== END OF TOPIC ==== cmdline_specifying.md ==== -->