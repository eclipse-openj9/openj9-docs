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

[Garbage collection](https://www.eclipse.org/openj9/docs/gc/)(gc) reclaims unused memory in the heap for reuse. During cleanup of the heap, the verbose garbage collection (vgc) logs capture information about the different garbage collection operations that are involved in the garbage collection cycles. Gc operations include GC initialization, stop-the-world processing, finalization, reference processing, and allocation failures. 

A garbage collection event is triggered in the following situations: 

- The JVM calls system.gc() to run a global STW gc. 

- A memory allocation failure. Allocation failures are triggered when a request to allocate an object fails due to a full region of the heap. 

- Decrease in available memory to a value that triggers a concurrent cycle to clean the heap or a region of the heap. 

- Concurrent events that are part of a concurrent cycle are complete, which triggers an STW event that completes either an incremental or final part of the concurrent.

Verbose garbage collection (vgc) logs contain information on garbage collection operations to assist with the following actions:  

- Tuning the performance of the garbage collectors 

- Troubleshooting gc operations and policies. For example, analyzing long pauses, or determining how free memory is divided in the heap before and after a gc cycle. 

When verbose garbage collection (vgc) logs are enabled, the logs are collected as soon as [garbage collection](gc.md) is initialized. 
 
By default, the information is printed to STDERR. You can also [print to a file](dump_gcdump.html/#how-to-generate-a-verbonse-garbage-collection-log).

You can feed verbose gc log files into variety of diagnostic tools and interfaces to visualize and analyze the garbage collection, such as the [Garbage Collection and memory visualizer extension for Eclipse](./https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv), or online services such as [GCEasy](./ https://gceasy.io). 

**Note:** If vgc logs do not provide enough information to help you diagnose gc problems or perform finer tuning, you can add further information to the vgc log by calling one or more trace garbage collector (TGC) traces by using the (['-XTgc' option](xtgc.md)).

## How to generate a verbose garbage collection log 

You can enable the verbose gc output by using the Java&trade; virtual machine standard command line option `-verbose:gc`. For more information, see [how to use OpenJ9 virtual machine command line option](./cmdline_specifying.md).

The output of `-verbose:gc` is printed to STDERR by default. You can instead output the logs to a file by appending the `-Xverbosegclog` non-standard option.

You can configure the vgc log output to be written to a succession of files, each logging a specified number of gc cycles, by specifying additional parameters of the `-Xverbosegclog` non-standard option. For more information, see the command-line option [xverbosegclog](./xverbosegclog.md).

## Vgc log contents 

The Verbose garbage collection logs are output as XML and consist of:  

- a summary of the configuration of your garbage collector policy and policy options, which are captured in the stanza beginning with the [`<INITIATISED>` tag](./dump_gcdump.html#garbage-collection-configuration) 

- stanzas that contain information about the different garbage collection cycles run, including the gc increments and gc operations that made up the gc cycle. 

The vgc logs are event-based, recording each STW event as it happens. Operations that are running concurrently with application threads are not logged. However, concurrent operations run as part of a concurrent cycle, and these concurrent cycles begin and finish with STW events. So you can locate and analyze aspects of the concurrent cycles in the vgc logs that run concurrent events. (For some garbage collectors, the concurrent cycle also includes intermediate STW events).  

The highest level stanzas in the vgc log represent gc cycles and begin with xml tags that define the kickoff event and reason. Except for some of the more complex gc algorithms (such as the global marking phase gc of the balanced policy), a gc cycle, when complete, generally results in reclaimed memory for reuse. Each cycle consists of 1 or more gc increments and gc operations.  

Events that make up a garbage collector cycle are listed in the stanza in a linear fashion, but are ‘nested’ by a start and end tag that identify the start and end of an event. Details of the event are recorded in the XML attributes associated with the tag and also within tags that are nested within the gc event's start and end tags.

The following table shows the tagging that is used for the nested gc processes: 

| GC Process   | Start and end tag in vgc log  | Details                                                         |
|--------------|-------------------------------|-----------------------------------------------------------------|
| gc cycle     | `<cycle-start>`, `<cycle-end>`| Completion of gc cycle results in reclaimed memory in most cases|
| gc increment | `<gc-start>`, `<gc-end>`      | 1 or more gc increments make up a gc cycle                      |
| gc operation | `<gc-op>`                     | 1 or more gc operations make up a gc increment. Examples of a gc operation include mark, sweep, scavenge.|

**Note:** Gc operations are the smallest fragment in a vgc log. All details of a gc operation are logged by using a single tag rather than a start and end tags.  

### An example of an STW cycle 
 
An example output from the vgc log for a simple STW cycle for gencon is displayed in fig 1. 
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
You can analyze this STW cycle by inspecting a few tags and xml attributes.  

**`<exclusive-start>`** The STW cycle stanza begins with a triggering event, represented for STW cycles with a <exclusive-start> tag. The triggering event for the other type of gc cycle, a concurrent cycle, is recorded by the <concurrent-kickoff> tag. 


**`<af-start>`** The reason for the triggering of this gc cycle is recorded by the <af-start> tag, indicating an allocation failure.  

**`<cycle-start>`** The beginning of the gc cycle itself is recorded by the <cycle-start> tag and contains the XML attribute ‘type=scavenge’ to describe the gc involved in this gc cycle.  


**`timestamp`** and **`id`** Each event that is logged in the gc cycle stanza is labeled with a `timestamp` and `id` xml attribute. The `id` attribute increases incrementally with each event so that you can use the id to search for particular events. 

**`<gc-start>`** The `<gc-start>` tag logs the beginning of a gc increment, in this case of type ‘scavenge’, nested within the gc cycle. 

**`<gc-op>`** Nested within the gc increment is the <gc-op> tag, which logs information about the individual gc ops that make up the gc increment. In this case, only one operation, the scavenge operation, makes up the gc increment, and only one gc increment makes up this STW Cycle. In particular, a scavenge operation evacuates the local area of the heap in a single operation and so a single operation makes up the whole gc cycle. 
 
A gc algorithm can also involve multiple gc operations, such as a combination of mark, sweep, compact, or copy. For a common gc of mark-sweep, the gc increment consists of two operations, mark and sweep. This increment might be accompanied by other gc increments to make up a gc cycle that reclaims memory.


<!-- Add link to topics for analysing logs and tag definitions once they have been created -->
<!-- ==== END OF TOPIC ==== cmdline_specifying.md ==== -->