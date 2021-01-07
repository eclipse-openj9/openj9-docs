# Verbose Garbage Collection Logs  

[Garbage collection](gc.md)(GC) reclaims used memory in the heap for reuse. During cleanup of the heap, the verbose garbage collection logs, when enabled, capture information about the different garbage collection operations that are involved in the garbage collection cycles. GC operations aim to reorganize or reclaim memory. 
 
Verbose GC logs contain information on garbage collection operations to assist with the following actions:  

- To tune GC and improve application performance. 
- Troubleshooting GC operations and policies. For example, analyzing long pauses, or determining how free memory is divided in the heap before and after a GC cycle.  

Verbose GC logs, when enabled, begin capturing information as soon as garbage collection is initialized. 

To help you visualize and analyze the garbage collection, you can feed verbose GC log files into various diagnostic tools and interfaces. Examples include tools such as the [Garbage Collection and memory visualizer extension for Eclipse](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv), and online services such as [GCEasy](https://gceasy.io).  

**Note:** You can run one or more GC traces by using the (['-XTgc' option](xtgc.md)) to get more detailed information to help diagnose GC problems or perform finer tuning. 

## How to generate a verbose garbage collection log  

You can enable verbose GC logs by specifying the `-verbose:gc` standard option when you start your application. For more information, see [standard command-line options](cmdline_general.md). 

The output of `-verbose:gc` is printed to STDERR by default. To print the log output to a file, append the `-Xverbosegclog` option. You can print the verbose GC log file to a succession of files, where each file logs a specified number of GC cycles, by using additional parameters of `-Xverbosegclog` option. For more information, see the command-line option [-Xverbosegclog](xverbosegclog.md). 

# Verbose GC log contents  
The Verbose garbage collection logs are printed in XML format and consist of the following sections:  

- a summary of your GC configuration, which is captured in the `<initialized>` XML element. 

- stanzas that contain information about the different GC cycles run, including the GC operations and GC increments that made up the GC cycle.  

The logs can tell you when a GC cycle or increment starts and ends, together with the GC operations that are run to manage or reclaim memory. You can also find out what triggers these activities and the amount of memory available to your application before and after processing.  

Not all operations that take place during garbage collection are recorded. For example, the logs can tell you when an increment of a concurrent GC cycle starts and ends; the explicit concurrent GC operations that take place during the increment are mostly not recorded. 

For definitions of GC cycles, GC increments, operations, phases, see [Garbage Collection Policies](gc.md#garbage-collection-policies). 

# XML Structure 

GC activities that make up a GC cycle are listed in the XML structure in a linear fashion. Some XML elements contain child XML elements while other XML elements are empty, serving as markers for starting and ending parts of the cycle. The XML elements contain attributes that record further information about the activity. 

The start of a GC cycle is recorded using the <cycle-start> XML element. The trigger reason for the GC cycle is captured in an adjacent XML element, such as <af-start> for an allocation failure or <allocation taxation> for a policy’s memory threshold trigger. 

For example, the following XML structure can be found in the verbose GC logs that are generated from the `gencon` policy. In this example, the lines have been indented to help illustrate the flow and some child XML elements are omitted for clarity: 

```
<exclusive-start> </exclusive-start> 

  <af-start/> 

    <cycle-start/> 

      <gc-start> </gc-start> 

        <allocation-stats> </allocation-stats> 

        <gc-op> </gc-op> 

      <gc-end> </gc-end> 

    <cycle-end/> 

    <allocation-satisfied/> 

  <af-end/> 

<exclusive-end/> 
 ```

The `<af-start/>`, `<cycle-start/>`, `<cycle-end/>`, `<allocation-satisfied/>`, and `<af-end/>` XML elements are empty and contain only attributes. 

All other XML elements contain child XML elements, which are omitted from this simplified example. 

## GC increments and interleaving 

Some GC policies include more than 1 GC cycle. GC cycles might be divided into multiple GC increments so that GC operations from the different GC cycles can interleave with each other to reduce pause times.  

A GC cycle can be divided into GC increments in different ways. See specific policy details in this topic for definitions of the GC increments that make up each cycle and the associated GC increment XML elements. 
 
The verbose GC log records all GC activities in the order in which they are run, so GC increments and operations of different cycles interleave with each other in the logs. 

When analyzing the logs, use the `contextid` and `id` attributes to locate the GC increments and operations associated with a specific GC cycle. For example: 
 
1. Determine the `id` attribute value associated with your GC cycle’s XML XML element `<cycle-start>`. Note: the `id` attribute increases incrementally with each GC event.  
2. Search for the `contextid` attribute values that are equal to your GC cycle’s `id` attribute value. All GC increments, operations, and concurrent events associated with a particular cycle have a `contextid` attribute whose value matches the `id` attribute value of the cycle. 
 
## Gencon Policy 

As detailed in [Gencon Policy (Default)](gc.md#gencon-policy-default), the Gencon policy makes use of 2 types of cycle to perform garbage collection – a partial GC cycle and a global GC cycle. 
 
Note: For details of the different cycles used per policy type, see [garbage collection policies](gc#garbage-collection-policies). 

### Partial GC cycle – STW scavenge operation(default) 
The Gencon policy’s partial cycle, scavenge, is run by default using only a stop-the-world pause. The cycle only consists of 1 STW scavenge operation, and is run on the nursery area only. 

GC Operation 

GC increment 

STOP-THE-WORLD or concurrent 

GC increment XML element 

Details 

Scavenge 

Single 

STW 

<gc-start>, <gc-end> 

‘<gc-op'>  contains detailed information about root scanning and weak roots processing 

 

 

Log example: Gencon’s default partial GC cycle 

 

A default partial GC cycle is recorded within the following example, which is taken from the verbose GC log output of a Gencon policy garbage collection. The following log content is broken down into sections that describe particular activities of the GC cycle. 

 

The first section of the log, which appears at the start of all verbose GC logs, records the configuration of the garbage collector: 

 

**INITIALIZATION** 

 

<initialized id="1" timestamp="2020-10-18T13:27:07.691"> 

<attribute name="gcPolicy" value="-Xgcpolicy:gencon" /> 

<attribute name="maxHeapSize" value="0x40000000" /> 

<attribute name="initialHeapSize" value="0x40000000" /> 

<attribute name="compressedRefs" value="true" /> 

… 
 
<system> 

<attribute name="physicalMemory" value="100335456256" /> 

<attribute name="numCPUs" value="28" /> 

... 

</system> 

<vmargs> 

<vmarg name="-Xoptionsfile=/java/perffarm/sdks/O11_j9_x64_linux-20201014/sdk/lib/options.default" /> 
… 
<vmarg name="-Dsun.java.launcher.pid=6068" /> 

</vmargs> 

</initialized> 
 

</verbosegc> 

 

UNNAMED" /> 

<vmarg name="--add-opens=java.sql/java.sql=ALL-UNNAMED" /> 

<vmarg name="--add-opens=java.management/javax.management=ALL-UNNAMED" /> 
... 

</vmargs> 

</initialized> 

 

The verbose GC log then begins recording GC activities and details. 

 
XML Structure of partial GC cycle 

The default partial GC cycle follows a general structure in the verbose GC log as shown.  The lines have been indented to help illustrate the flow and some child XML elements are omitted for clarity: 

 

 
<exclusive-start> 

<af-start> 

<cycle-start> 

<gc-start>  

<mem-info> 

<mem></mem> 

</mem-info> 
</gc-start> 

<gc-op> </gc-op> 

<gc-end>  

<mem-info> 

<mem></mem> 

</mem-info> 
</gc-end> 

<cycle-end> 

<af-end> 

<exclusive-end> 

 

**’<gc-cycle>’** 

The cycle begins with a stop-the-world pause, recorded by the <exclusive-start> XML element.  

 

 

``` 

<exclusive-start id="2" timestamp="2020-10-18T13:27:09.603" intervalms="1913.853">  

 
 

<response-info timems="0.030" idlems="0.030" threads="0" lastid="0000000000AC4600" lastname="LargeThreadPool-thread-3" />  

 
 

</exclusive-start> 

``` 

 

 

The <af-start> XML tag indicates that an allocation failure triggered the stop-the-world pause and subsequent GC cycle.  

 

``` 

<af-start id="3" threadId="0000000000AC4F80" totalBytesRequested="272" timestamp="2020-10-18T13:27:09.603" intervalms="1913.921" type="nursery" />  

``` 

 
 

The beginning of the GC cycle itself is recorded by the `<cycle-start>` XML element and contains the XML attribute `type=scavenge` to describe the GC operation involved in this GC cycle.  
 

Each event that is logged in the GC cycle stanza is labeled with a `timestamp`,`id`, and `context-id` XML attribute. The `id` attribute increases incrementally with each event. All gc increments, operations, and concurrent XML elements associate with a particular cycle have a `contxtid` value that matches the `id` value of the cycle. 

 
``` 

<cycle-start id="4" type="scavenge" contextid="0" timestamp="2020-10-18T13:27:09.603" intervalms="1913.959" /> 

``` 

  
 

**’<gc-start>’** 

The start of the first GC increment is logged using the XML element <gc-start>.   

 

``` 

 

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
 
``` 

 
 
The <mem-info> XML element and it’s child XML elements give detailed information about the amount of memory available and where it is located in the heap. 
 

You can determine how the GC operations in the increment modify and reclaim memory by comparing the attribute values of the <mem-info> and <mem> elements.   
 

For this example, at the start of the GC increment the available memory is configured in the heap as follows: 

0% of the nursery area, which is split between the allocate and survivor spaces, is available as free memory.  

99% of the tenure area is available as free memory, which consists of short object allocation and long object allocation areas. 

 

**’<gc-op'>** 

 

The <gc-op> element and it’s child elements contain information about the increment’s operations and phases: 
 

``` 

 

<gc-op id="7" type="scavenge" timems="30.675" contextid="4" timestamp="2020-10-18T13:27:09.634">  

<scavenger-info tenureage="1" tenuremask="fffe" tiltratio="50" />  

<memory-copied type="nursery" objects="461787" bytes="25522536" bytesdiscarded="367240" />  

<finalization candidates="317" enqueued="157" />  

<ownableSynchronizers candidates="6711" cleared="2579" />  

<references type="soft" candidates="9233" cleared="0" enqueued="0" dynamicThreshold="32" maxThreshold="32" />  

<references type="weak" candidates="13149" cleared="4781" enqueued="4495" />  
<references type="phantom" candidates="359" cleared="8" enqueued="8" />  

<object-monitors candidates="79" cleared="36" />  

</gc-op>  

 

``` 

 

The <gc-op> has ‘type’ attribute value ‘scavenge’, indicating the operation is a scavenge operation. The XML child elements provide details about the operation itself. For example the <memory-copied> element indicates that 461787 objects were copied from the nursery area TO WHERE? The <references> XML elements HOW MUCH DETAIL TO ADD HERE? 
 
The GC increment only includes this one GC operation.  
 

**’<gc-end>’** 

 

``` 

 

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

 

``` 

 

The attribute values of <mem> and <mem-info> show that by the end of the GC increment, the configuration of the heap has been modified to release memory for use in the nursery area: 
 

40% of the nursery area available as free memory. The allocate space of the nursery area is now 80% available memory, and the survivor space is full. 

99% of the tenure area is now available as free memory. 

 

This is as expected – the Gencon partial GC cycle runs a *stop-the-world* scavenge operation, which acts solely on the nursery area to reclaim memory in the event of a memory allocation failure.  
 

**’cycle-end’>** 

 

``` 

 
 

<cycle-end id="10" type="scavenge" contextid="4" timestamp="2020-10-18T13:27:09.635" />  

 

<allocation-satisfied id="11" threadId="0000000000AC4600" bytesRequested="272" />  

 

<af-end id="12" timestamp="2020-10-18T13:27:09.635" threadId="0000000000AC4F80" success="true" from="nursery"/>  
 

<exclusive-end id="13" timestamp="2020-10-18T13:27:09.635" durationms="31.984" />  

``` 

 

The GC cycle finishes after only 1 GC increment.  The <allocation-satisfied> xml element indicates that the requested memory reclaim of 272 bytes has been achieved by the GC cycle.  
 
The “contextid” value of the ‘<gc-start>’ ‘<gc-op>’ XML elements are both ‘7’, which  matches the “id=7” value of the <cycle-start> XML element, indicating that the GC increment and GC operation are both associated with this default partial GC cycle. 

 

So from the structure and XML schema of the example, you can determine that: 

The GC cycle begins with a *stop-the-world* pause due to an allocation failure. 

All GC events associated with this cycle occur during the *stop-the-world> pause 

The cycle consists of only 1 GC increment, containing only 1 operation, a ‘scavenge’. 

The GC cycle reclaims memory in the nursery area.  
 

You can determine further details, such as <allocation-stats> and < allocation-satisfied> ...See []() for more information. 

 

### Partial GC cycle – concurrent scavenge operation(non-default)
 

The partial scavenge GC cycle can be alternatively run as a [concurrent scavenge cycle](gc.md#concurrent-scavenge). This non-default concurrent scavenge partial GC cycle is divided into 3 GC increments, to reduce pause times and maximise throughput, as defined in the following table:  

 

GC Operation 

GC increment 

STOP-THE-WORLD or concurrent 

GC increment XML element 

Details 

Scavenge 

Initial 

STW 

<gc-start>, <gc-end> 

 

Root scanning, reported as a single scavenge operation 

scavenge 

intermediate 

Concurrent 

<concurrent-start>, <concurrent-end> 

 

<warning details=””> 

Root scan, live objects traversed and evacuated (copy-forwarded). Reported as a scavenge operation 

scavenge 

final 

STW 

<gc-start>, <gc-end> 

 

weak roots scanning, reported as a complex scavenge operation (gc-op) containing specific details for each of weak root groups 

 

 

### Global GC cycle 

The Gencon policy’s global GC cycle, which runs when the tenure area is close to full, consists of a mixture of STW and concurrent operations that run on the tenure area.  

 

The initial and final steps of the GC cycle are executed using a STW pause to run the GC operations involved in the step. GC operations involved in the intermediate step are run concurrently.  

 

The global GC cycle is divided into GC increments as shown in the following table: 

 

 

GC Operation 

GC increment 

STOP-THE-WORLD or concurrent 

GC increment XML element 

Details 

n/a - Initiates cycle 

Initial 

STW 

‘<concurrent-kickoff' 

No <gc-op> is logged. This increment just initiates the concurrent mark increment 

Concurrent Mark 

intermediate 

concurrent 

<gc-start>, <gc-end> 

<concurrent-trace-info> records progress of concurrent mark 

Final Collection 

final 

STW 

‘<concurrent-global-final>’ 

 

Operations and phases include a final phase of concurrent mark, a sweep, and optionally class unload and compact.  
Triggered when card-cleaning threshold reached. Child XML element: <concurrent-trace-info reason=””> 

 

 

 

You can search for a particular type of cycle in the logs by searching for the “type=<value>” attribute of the <cycle-start> and <cycle-end> XML elements. The following table shows the “type” attribute values and trigger XML elements associated with the different Gencon GC cycles:  
 

GC cycle 

“Type” attribute value 

Cycle trigger XML element  

Triggering reason 

Global 

global 

<concurrent-kickoff> 

threshold reached 

Partial 

scavenge 

<af-start> 

allocation failure 

 

Gencon GC cycles, increments and operations are recorded in the verbose GC log using the XML elements listed in the following table:  
 

GC process 

Start and end XML element 

details 

GC cycle 

`<cycle-start>`, `<cycle-end>` 

The start and end of a GC cycle; a repeatable process that involves a set of GC operations. These operations process all or parts of the Java heap to complete a discrete function. 

GC STW increment 

`<gc-start>` `<gc-end>` 

The start and end of a GC increment that begins with a STW pause. 

GC STW increment 

<concurrent-kickoff> 

The start of the initial GC increment of the global concurrent cycle that kicks off the initial mark operation 

 

GC STW increment 

 

<concurrent-global-final 

 

The start of the final GC increment of the global concurrent cycle that performs the final collection 

GC operations and phases 

<gc-op> 

A GC operation such as mark or sweep, or a sub-operation ‘phase’ such as class unload. 

 

**Note:** Details of a GC operation or phase are logged using the single <gc-op> XML element rather than start and end XML elements. 

 

Interleaving of Gencon GC cycle increments 

While the stop-the-world scavenge operation of the partial GC cycle is running on the nursery 

area, the intermediate increment of the global GC cycle – a concurrent mark-sweep operation - runs concurrently on the tenure area.  

 

You can see this interleaving of the increments in the verbose GC log, which will take on a similar structure to the following example (for clarity, not all GC activities are listed): 

 

Line number 

 

Application threads running? 

 

Logging 

 

 

 

Gencon global cycle status 

Gencon partial cycle status 

1-87 

✅ 

 

Initialization information 

87-51651 

✅/❌ 

 

 

Series of gencon partial cycles start and finish 

51655 

❌ 

 

 

Partial cycle initializes 

 

51656 

❌ 

 

 

Scavenge increment executes 

51696 

❌ 

 

 

Partial cycle ends 

51700 

✅ 

 

Blank line – no activities logged 

 

51701 

✅ 

 

Global cycle initial increment marked 

 

51707 

❌ 

 

Global cycle initiatizes 

 

51709 

✅ 

 

Concurrent increment executes (blank line) 

 

51714 

❌ 

 

Concurrent increment executing 

 

Partial cycle initialises 

51715 

❌ 

 

Concurrent increment executing 

 

Scavenge increment executes 

51754 

❌ 

 

Concurrent increment executing 

 

Partial cycle ends 

51758 

✅ 

 

Blank line – no activities logged 

 

 

51762 

❌ 

 

Final collection increment executes 

 

51867 

❌ 

 

Global cycle ends 

 

 
 

Log example: Gencon’s global GC cycle 

 

The following example shows how a global GC cycle is recorded in a Gencon policy verbose GC log. The global GC cycle is run after many partial GC cycles have completed, so the log content in this example begins part way down the full log. For details of the GC Initialisation section and partial cycle log contents, see [Log example: Gencon’s default partial GC cycle](./vgclogs.md/#log-example-gencons-default-partial-gc-cycle). 

 

In contrast to the previous log example of a default partial GC cycle, where the whole of the GC cycle is recorded in a single block in the log, [the global GC cycle is split into increments](./verbosegc.md#global-gc-cycle) that interleave with partial GC cycles. The interleaving can be seen in the following example, where a partial GC cycle is logged between the start and end of the global cycle. 

 

 
XML Structure of balanced GC cycle 

 

The global GC cycle follows a general structure in the verbose GC log as shown.  The lines have been indented to help illustrate the flow and some child XML elements are omitted for clarity: 

 

<concurrent-kickoff> 

<exclusive-start></exclusive-start> 

<cycle-start> 
<exclusive-end> 

 

// The logs now record a partial GC cycle that runs from start to finish  

 
<exclusive-start> 

<af-start> 

<cycle-start> 

<gc-start>  

<mem-info> 

<mem></mem> 

</mem-info> 
</gc-start> 

<gc-op> </gc-op> 

<gc-end>  

<mem-info> 

<mem></mem> 

</mem-info> 
</gc-end> 

<cycle-end> 

<af-end> 

<exclusive-end> 

//the partial GC cycle has ended 
 

//the final increment of the global GC cycle now runs from start to finish 

 
<exclusive-start> </exclusive-start> 

<concurrent-global-final></concurrent-global-final> 

<gc-start> 

<mem-info> 

<mem></mem> 

</mem-info> 

</gc-start> 

<gc-op> “type=rs-scan"</gc-op> 

<gc-op>”type=card-cleaning" </gc-op> 

<gc-op> “type=mark”</gc-op> 

<gc-op> “type=classunload”</gc-op> 

<gc-op>”type=sweep” 

<gc-end>  

<mem-info> 

<mem></mem> 

</mem-info> 
</gc-end> 

</cycle-end> 

<exclusive-end> 

 

// The logs now record a partial GC cycle that runs from start to finish 

 

``` 

**<concurrent-kickoff>** 

 

The XML element <concurrent-kickoff> records the start of the first increment of the Gencon Global GC cycle. The ‘<kickoff>’ element contains details of the reason for the kickoff, the target number of bytes the cycle aims to free up in the heap, as well as the current available memory in the different parts of the heap. 

 

 

<concurrent-kickoff id="12362" timestamp="2020-10-18T13:35:44.341"> 

<kickoff reason="threshold reached" targetBytes="239014924" thresholdFreeBytes="33024922" remainingFree="32933776" tenureFreeBytes="42439200" nurseryFreeBytes="32933776" /> 

</concurrent-kickoff> 

 

**note:* To analyze specific parts of a cycle, you can search for the XML elements that record  the GC activities of a specific increment of the cycle – in this case, you can search for the <concurrent-kickoff> XML element. See the details of a particular cycle, such as the [Gencon Global Cycle](./vgclog.md/#global-cycle), to determine the XML element names for particular stop-the-world or concurrent GC increments or operations. 

 

**<exclusive-start>** 

 

The ‘<exclusive-start>’ XML element indicates the start of a stop-the-world pause. 

 

<exclusive-start id="12363" timestamp="2020-10-18T13:35:44.344" intervalms="342.152"> 

<response-info timems="0.135" idlems="0.068" threads="3" lastid="00000000015DE600" lastname="LargeThreadPool-thread-24" /> 

</exclusive-start> 

 

**<cycle-start>** 

The beginning of the global cycle is recorded, indicated by the ‘global’ value of the XML ‘type’ attribute’. All subsequent GC events recorded in the logs that are associated with this particular cycle will have a ‘contextid’ value equal to the <cycle-start> ‘id’ attribute value of “12634”. 

 

<cycle-start id="12364" type="global" contextid="0" timestamp="2020-10-18T13:35:44.344" intervalms="516655.052" /> 

 

**<exclusive-end** and the concurrent GC events** 

The <exclusive-end> xml tag records the end of the stop-the-world pause. 

<exclusive-end id="12365" timestamp="2020-10-18T13:35:44.344" durationms="0.048" /> 

 

The next activity recorded in the logs, which is recorded using an <exclusive-end> xml element, follows a blank line. A blank line indicates there are no stop-the-world activities running. However, concurrent activities may be running, in this case, the concurrent operations and phases of the 2nd increment of a Gencon global cycle. 

 
**A partial GC cycle starts and completes** 

 

The next section of the logs records a stop-the-world pause associated with an allocation failure. The following XML element, <cycle-start>, indicates the start of a scavenge cycle. The ‘’contextid” XML attribute value of the XML elements in the following log section is “12368” not “12364. So the activities recorded in this section are associated with this new scavenge cycle rather than the currently executing global cycle.  

 

This new scavenge cycle is a Gencon default partial GC cycle. For more information about how this cycle is recorded in the logs, see the [log example for Gencon’s default partial G cycle](verbosegclog.md#ADD). 

 

<exclusive-start id="12366" timestamp="2020-10-18T13:35:44.582" intervalms="237.874"> 

<response-info timems="0.094" idlems="0.033" threads="5" lastid="00000000014E0F00" lastname="LargeThreadPool-thread-67" /> 

</exclusive-start> 

<af-start id="12367" threadId="00000000013D7280" totalBytesRequested="96" timestamp="2020-10-18T13:35:44.582" intervalms="580.045" type="nursery" /> 

<cycle-start id="12368" type="scavenge" contextid="0" timestamp="2020-10-18T13:35:44.582" intervalms="580.047" /> 

<gc-start id="12369" type="scavenge" contextid="12368" timestamp="2020-10-18T13:35:44.582"> 

<mem-info id="12370" free="42439200" total="1073741824" percent="3"> 

<mem type="nursery" free="0" total="268435456" percent="0"> 

<mem type="allocate" free="0" total="241565696" percent="0" /> 

<mem type="survivor" free="0" total="26869760" percent="0" /> 

</mem> 

<mem type="tenure" free="42439200" total="805306368" percent="5"> 

<mem type="soa" free="2173472" total="765040640" percent="0" /> 

<mem type="loa" free="40265728" total="40265728" percent="100" /> 

</mem> 

<remembered-set count="23069" /> 

</mem-info> 

</gc-start> 

<allocation-stats totalBytes="235709920" > 

<allocated-bytes non-tlh="104" tlh="235709816" /> 

<largest-consumer threadName="LargeThreadPool-thread-79" threadId="00000000013F0C00" bytes="7369720" /> 

</allocation-stats> 

<gc-op id="12371" type="scavenge" timems="11.110" contextid="12368" timestamp="2020-10-18T13:35:44.593"> 

<scavenger-info tenureage="14" tenuremask="4000" tiltratio="89" /> 

<memory-copied type="nursery" objects="158429" bytes="6018264" bytesdiscarded="108848" /> 

<ownableSynchronizers candidates="1701" cleared="1685" /> 

<references type="soft" candidates="57" cleared="0" enqueued="0" dynamicThreshold="32" maxThreshold="32" /> 

<references type="weak" candidates="514" cleared="406" enqueued="406" /> 

<object-monitors candidates="182" cleared="0" /> 

</gc-op> 

<gc-end id="12372" type="scavenge" contextid="12368" durationms="11.249" usertimems="43.025" systemtimems="0.000" stalltimems="1.506" timestamp="2020-10-18T13:35:44.593" activeThreads="4"> 

<mem-info id="12373" free="277876128" total="1073741824" percent="25"> 

<mem type="nursery" free="235436928" total="268435456" percent="87"> 

<mem type="allocate" free="235436928" total="241565696" percent="97" /> 

<mem type="survivor" free="0" total="26869760" percent="0" /> 

</mem> 

<mem type="tenure" free="42439200" total="805306368" percent="5" macro-fragmented="0"> 

<mem type="soa" free="2173472" total="765040640" percent="0" /> 

<mem type="loa" free="40265728" total="40265728" percent="100" /> 

</mem> 

<pending-finalizers system="0" default="0" reference="406" classloader="0" /> 

<remembered-set count="17354" /> 

</mem-info> 

</gc-end> 

<cycle-end id="12374" type="scavenge" contextid="12368" timestamp="2020-10-18T13:35:44.594" /> 

<allocation-satisfied id="12375" threadId="00000000013D6900" bytesRequested="96" /> 

<af-end id="12376" timestamp="2020-10-18T13:35:44.594" threadId="00000000013D7280" success="true" from="nursery"/> 

<exclusive-end id="12377" timestamp="2020-10-18T13:35:44.594" durationms="11.816" /> 

 
**<exclusive-start> and <concurrent-global-final>** 

 

After the partial GC cycle completes and the stop the world pause finishes, the log records a stop-the-world pause preceding a <concurrent-global-final> XML element. 

 

The <concurrent-global-final> element records the start of the 3rd and final increment of the Global partial GC cycle which consists of stop-the-world GC operations and phases. The *stop-the-world* pause is executed by the garbage collector to execute this final increment.  

 

The ‘reason’ attribute of the <concurrent-trace-info> XML element indicates that the global cycle has reached the card cleaning threshold and so can now complete this final increment.  

 

<exclusive-start id="12378" timestamp="2020-10-18T13:35:44.594" intervalms="12.075"> 

<response-info timems="0.108" idlems="0.040" threads="3" lastid="00000000018D3800" lastname="LargeThreadPool-thread-33" /> 

</exclusive-start> 

<concurrent-global-final id="12379" timestamp="2020-10-18T13:35:44.594" intervalms="516905.029" > 

<concurrent-trace-info reason="card cleaning threshold reached" tracedByMutators="200087048" tracedByHelpers="12164180" cardsCleaned="4966" workStackOverflowCount="0" /> 

</concurrent-global-final> 

 

**<gc-start>** 

 

A global GC increment begins. You can check that this increment is associated with the particular GC global cycle of this example by checking that the `contextid=”12364”’ matches the ‘id’ attribute value for our global GC cycle’s <gc-cycle> XML element.  

 

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

 

The child XML element attribute values of the <mem> and <mem-info> elements indicate the configuration of the memory. For this example, at the start of this GC increment, 25% of the total heap is available as free memory. This free memory is split between the following areas of the heap:  

the nursery area, which has 87% of it’s total memory available as free memory. The free memory is only available in the allocate space of the nursery area. The survivor space has no free memory.  

the tenure area, which has 5% of it’s total memory available as free memory. All of this free memory is in the long object allocation area. There is no free memory available in the short object allocation area.  

 

**note** The global GC cycle runs to free up memory in the tenure area. The freeing up of memory in the nursery area is achieved using the partial GC cycle. For more information, see [Gencon Policy(default)](gc.md#gencon-policy-(default)). 

 

**<gc-op>** 

 

The <gc-op> element and it’s child elements contain information about the increment’s operations and phases. There are 5 <gc-op> XML elements recorded in this section of the logs. The ‘type’ XML attribute shows the different operations and phases involved, which include a final concurrent mark phase, a sweep, and a class unload: 

Rs-scan 

Card-cleaning 

Mark 

Classunload 

Sweep 

 

**note: ** Sometimes this final increment of a Gencon global cycle will include an optional compact phase. 

 

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

 

**<gc-end>** 

 

The <gc-end> xml element logs the end of the final increment of the global cycle. The <mem> and <mem-info> child elements show that after the increment has run, the heap contains 60% free memory. This free memory is split between the following areas of the heap:  

the nursery area remains unchanged, with 87% of it’s total memory available as free memory. The free memory is only available in the allocate space of the nursery area. The survivor space has no free memory. 

the tenure area, now has 51% of it’s total memory available as free memory. The memory is split between the small object allocation space, which has 48% of it’s space available as free memory, and the large object allocation space, which is all available memory.  

 

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

 

 

**<cycle-end>** 

 

After the GC operations and phases of the final increment of the global cycle have completed, the global cycle ends and the stop-the-world pause ends. 

 

<cycle-end id="12389" type="global" contextid="12364" timestamp="2020-10-18T13:35:44.619" /> 

 

<exclusive-end id="12391" timestamp="2020-10-18T13:35:44.619" durationms="24.679" /> 

 

So from the structure and XML schema of this example, you can determine that: 

 

The GC global cycle is triggered when a memory threshold is reached and begins with a *stop-the-world* pause 

After the first increment of the GC global cycle completes, the stop-the-world pause ends and the 2nd increment runs concurrently. 

Whilst the 2nd increment is running concurrently, a partial GC cycle starts and finishes. 

When the 2nd increment has completed, a stop-the-world pause begins so that the 3rd and final increment of the global cycle, which consists of 5 operations and phases, can run. 

The global GC cycle reclaims memory in the tenure area. 