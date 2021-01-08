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

The output of `-verbose:gc` is printed to STDERR by default. To print the log output to a file, append the `-Xverbosegclog` option. You can print the verbose GC log file to a succession of files, where each file logs a specified number of GC cycles, by using extra parameters of `-Xverbosegclog` option. For more information, see the command-line option [`-Xverbosegclog`](xverbosegclog.md). 

## Verbose GC log contents  
The Verbose garbage collection logs are printed in XML format and consist of the following sections:  

- a summary of your GC configuration, which is captured in the `<initialized>` XML element. 

- sections that contain information about the different GC cycles run, including the GC operations and GC increments that made up the GC cycle.  

The logs record when a GC cycle or increment starts and ends, together with the GC operations that are run to manage or reclaim memory. You can also find out what triggers these activities and the amount of memory available to your application before and after processing.  

Not all operations that take place during garbage collection are recorded. For example, the logs record when an increment of a concurrent GC cycle starts and ends, but most concurrent GC operations are not recorded.

For definitions of GC cycles, GC increments, operations, phases, see [Garbage Collection Policies](gc.md#garbage-collection-policies). 

## XML Structure 

GC activities that make up a GC cycle are listed in the XML structure in a linear fashion. Some XML elements contain child XML elements while other XML elements are empty, serving as markers for starting and ending parts of the GC cycle. The XML elements contain attributes that record further information about the activity. 

The start of a GC cycle is recorded by using the <cycle-start> XML element. The trigger reason for the GC cycle is captured in an adjacent XML element, such as <af-start> for an allocation failure or <allocation taxation> for a policy’s memory threshold trigger. 

For example, the following XML structure can be found in the verbose GC logs that are generated from the `gencon` policy. In this example, the lines are indented to help illustrate the flow and some child XML elements are omitted for clarity: 

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

A GC cycle can be divided into GC increments in different ways. See specific policy details for definitions of the GC increments that make up each cycle and the associated GC increment XML elements. 
 
The verbose GC log records all GC activities in the order in which they are run, so GC increments and operations of different cycles interleave with each other in the logs. 

Use the `contextid` and `id` attributes to locate the GC increments and operations in the log that are associated with a specific GC cycle:
 
1. Determine the `id` attribute value associated with your GC cycle’s XML element `<cycle-start>`. Note: the `id` attribute increases incrementally with each GC event.  
2. Search for the `contextid` attribute values that are equal to your GC cycle’s `id` attribute value. All GC increments, operations, and concurrent events that are associated with a particular cycle have a `contextid` attribute whose value matches the `id` attribute value of the cycle. 
 
## Gencon Policy 

### Types of GC Cycle

As detailed in [Gencon Policy (Default)](gc.md#gencon-policy-default), the Gencon policy uses two types of cycle to perform garbage collection – a partial GC cycle and a global GC cycle. 
 
Note: For more information about the cycles used in a particular policy, see [garbage collection policies](gc#garbage-collection-policies). 

You can search for a particular type of cycle in the logs by searching for the “type=<value>” attribute of the <cycle-start> and <cycle-end> XML elements. The following table shows the values of the “type” XML attribute the XML elements that record the cycle trigger for the different Gencon GC cycles:  

| GC cycle | value of `type` XML attribute| XML element for cycle trigger| Triggering reason|
|----------|----------------------------|------------------------------|------------------|
|Global    | `global`                   | `<concurrent-kickoff>`       | threshold reached| 
|Partial (default) | `scavenge`           | `<af-start>`                 |allocation failure|
|Partial (non-default)| `scavenge`        | `<af-start>`                 |allocation failure|

### Gencon cycle, increment, and operation XML elements

Gencon GC cycles, increments, and operations are recorded in the verbose GC log by using the XML elements that are listed in the following table:  


| GC process             | start and end XML elements   | Details |
|------------------------|------------------------------|------------------------------|
|GC cycle                |`<cycle-start>`, `<cycle-end>`| The start and end of a GC cycle.|
|GC STW increment        |`<gc-start>`, `<gc-end>`      | The start and end of a GC increment that begins with a *stop-the-world* pause. 
|GC STW increment        | `<concurrent-kickoff>`       | The start of the initial GC increment of the global concurrent cycle that begins the initial mark operation 
|GC *stop-the-world* increment        | `<concurrent-global-final> ` | The start of the final GC increment of the global concurrent cycle that performs the final collection|
|GC operations and phases| `<gc-op>`                    | A GC operation such as mark or sweep, or a suboperation ‘phase’ such as class unload. |

**Note:** Details of a GC operation or phase are logged by using the single <gc-op> XML element rather than start and end XML elements. 

The following sections give details about specific cycles, including examples of how the cycle appears in the Verbose GC log.

### Partial GC cycle – default
The Gencon policy’s partial cycle runs by using only a *stop-the-world* pause by default. The cycle consists of only one *stop-the-world* scavenge operation, and is run on the nursery area only:

|GC Operation | GC increment | *stop-the-world* or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|-------------------------------|--------------------------------------|---------------------------------------------------------------------------|
|Scavenge     |Single        | *stop-the-world*              | `<gc-start>`, `<gc-end>`| `<gc-op'>` |Contains detailed information about root scanning and weak roots processing|


**EXAMPLE - Gencon’s default partial GC cycle**

A default partial GC cycle is recorded within the following example, which is taken from the verbose GC log output of a Gencon policy garbage collection. The following log content is broken down into sections that describe particular activities of the GC cycle. 

**Initialization** 

The first section of the log, which appears at the start of all verbose GC logs, records the configuration of the garbage collector:  

```xml
<initialized id="1" timestamp="2020-10-18T13:27:07.691"> 

<attribute name="gcPolicy" value="-Xgcpolicy:gencon" /> 

<attribute name="maxHeapSize" value="0x40000000" /> 

<attribute name="initialHeapSize" value="0x40000000" /> 

<attribute name="compressedRefs" value="true" /> 

... 
 
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
```

The verbose GC log then begins recording GC activities and details. 

**XML Structure of partial GC cycle**

The default partial GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some child XML elements are omitted for clarity:

```xml
<exclusive-start> 

  <af-start> 

    <cycle-start> 

      <gc-start>  

        <mem-info> 

          <mem></mem> 

        </mem-info> 

      </gc-start> 

        <gc-op> type="scavenge" </gc-op> 

      <gc-end>  

        <mem-info> 
          
          <mem></mem> 

        </mem-info> 
      </gc-end> 

      <cycle-end> 

  <af-end> 

<exclusive-end> 
```

**`<exclusive-start>`**

The cycle begins with a *stop-the-world* pause, recorded by the <exclusive-start> XML element:  

```xml
<exclusive-start id="2" timestamp="2020-10-18T13:27:09.603" intervalms="1913.853">  

<response-info timems="0.030" idlems="0.030" threads="0" lastid="0000000000AC4600" lastname="LargeThreadPool-thread-3" />  

</exclusive-start> 
``` 

**`<af-start>`**

The `<af-start>` XML element indicates that an allocation failure triggered the *stop-the-world* pause and subsequent GC cycle:

```xml
<af-start id="3" threadId="0000000000AC4F80" totalBytesRequested="272" timestamp="2020-10-18T13:27:09.603" intervalms="1913.921" type="nursery" />  
```

**`<cycle-start>`**

The beginning of the GC cycle itself is recorded by the `<cycle-start>` XML element and contains the XML attribute `type="scavenge"` to describe the GC operation involved in this GC cycle:

```xml
<cycle-start id="4" type="scavenge" contextid="0" timestamp="2020-10-18T13:27:09.603" intervalms="1913.959" /> 
```

Logged GC events are labeled with `timestamp`,`id`, and `contextid` XML attributes. The `id` attribute increases incrementally with each GC event. All gc increments, operations, and concurrent XML elements associate with a particular cycle have a `contextid` value that matches the `id` value of the cycle.

**`<gc-start>`** 

The start of the first GC increment is logged by using the XML element `<gc-start>`:   

```xml 
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

The `<mem-info>` XML element and its child XML elements give detailed information about the amount of memory available and where it is located in the heap. You can determine how the GC operations in the increment modify and reclaim memory by comparing the attribute values of the `<mem-info>` and `<mem>` elements.

For this example, at the start of the GC increment the available memory is configured in the heap as follows: 

- 0% of the nursery area, which is split between the allocate and survivor spaces, is available as free memory.  

- 99% of the tenure area is available as free memory, which consists of short object allocation and long object allocation areas. 

**`<gc-op>`** 

The `<gc-op>` XML element and its child XML elements contain information about the increment’s operations and phases. The example GC cycle includes only 1 GC operation, which is of type `"scavenge"`:

```xml 
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

The child XML elements of `<gc-op>` provide details about the operation itself. For example, the `<scavenger-info>` XML element shows that the tenure age is set to `"1"` for this operation. For a tenure age of `"1"`, any objects already copied between the allocate and survivor space once will be moved to the tenure area during the next scavenge operation. 

The `<memory-copied>` XML element indicates that 25 MB (25522536) were reclaimed in the nursery area for new object allocation. For more information about how the scavenge operation acts on the heap, see [Gencon policy(default)](gc.md#gencon-policy-default).

The `contextid="7"`for the`<gc-start>` and `<gc-op>` XML elements are both ‘7’. The `id` value of the <cycle-start> element of the current partial GC cycle is also `"7"`, so the increment and operations are associated with the partial GC cycle.

**`<gc-end>`** 

The end of the GC increment is recorded with the `<gc-end>` XML element. The child XML elements `<mem-info>` and `<mem>` record details of the current levels of memory in different areas of the heap at the end of the GC increment: 

```xml
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

The attribute values of `<mem>` and `<mem-info>` show that memory has been released in the nursery area:

- 40% of the nursery area available as free memory. The allocate space of the nursery area is now 80% available memory, and the survivor space is full. 
- 99% of the tenure area is now available as free memory. 

**`<cycle-end>`** 
The end of the GC cycle is recorded by the `<cycle-end>` XML element. The GC cycle finishes after only 1 GC increment:  

```xml
<cycle-end id="10" type="scavenge" contextid="4" timestamp="2020-10-18T13:27:09.635" />  

<allocation-satisfied id="11" threadId="0000000000AC4600" bytesRequested="272" />  

<af-end id="12" timestamp="2020-10-18T13:27:09.635" threadId="0000000000AC4F80" success="true" from="nursery"/>  
 
<exclusive-end id="13" timestamp="2020-10-18T13:27:09.635" durationms="31.984" />  
```

The `<allocation-satisfied>` XML element indicates that the requested memory reclaim of 272 bytes has been achieved by the GC cycle. The `<exclusive-end>` XML element indicates the end of the *stop-the-world* pause.

**Summary of the example**

So from the structure and XML schema of the example, you can determine the following:

- The GC cycle begins with a *stop-the-world* pause due to an allocation failure. 

- All GC events that are associated with this cycle occur during the *stop-the-world* pause 

- The cycle consists of only 1 GC increment, containing only one operation, a ‘scavenge’. 

- The GC cycle reclaims memory in the nursery area.  
 
### Partial GC cycle – non-default
 
The default [partial GC cycle ](vgclog.md#partial-gc-cycle-default) consists of a single scavenge operation, which runs start to finish during a single *stop-the-world* pause. The partial GC cycle can alternatively be run as a [concurrent scavenge cycle](gc.md#concurrent-scavenge) by enabling the concurrent scavenge mode. When this mode is enabled, the partial GC cycle is divided into increments to enable part of the cycle to run in parallel to running application threads. Specifically, the cycle is divided into 3 GC increments as defined in the following table:  

|GC Operation | GC increment | *stop-the-world* or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|-------------------------------|--------------------------------------|---------------------------------------------------------------------------|
|Scavenge     |initial        | *stop-the-world*              | `<gc-start>`, `<gc-end>`|Root scanning, reported as a single scavenge operation |
|Scavenge     |intermediate        | concurrent         | `<concurrent-start>`, `<concurrent-end>`|`<warning details=””>`  Root scan, live objects traversed and evacuated (copy-forwarded). Reported as a scavenge operation |
|Scavenge     |final     | *stop-the-world*              | `<gc-start>`, `<gc-end>` |weak roots scanning, reported as a complex scavenge operation (gc-op) containing specific details for each of weak root groups  |

### Global GC cycle 

The Gencon policy’s global GC cycle, which runs when the tenure area is close to full, consists of a mixture of STW and concurrent operations that run on the tenure area. The initial and final steps of the GC cycle are run during a *stop-the-world* pause. GC operations that are involved in the intermediate step are run concurrently.  

The global GC cycle is divided into GC increments, as shown in the following table: 

|GC Operation         | GC increment| *stop-the-world* or concurrent| XML element of GC increment| Details                         |
|---------------------|-------------|-------------------------------|--------------------------------------|-----------------------|
|n/a - initiates cycle|initial      | *stop-the-world*              | `<concurrent-kickoff`        |No `<gc-op>` is logged. This increment just initiates the concurrent mark increment |
|concurrent mark      |intermediate |concurrent                     | `<gc-start>`, `<gc-end>`     |`<concurrent-trace-info>` records progress of concurrent mark|
|final collection     |final        | *stop-the-world*              | `<concurrent-global-final>`  |Operations and phases include a final phase of concurrent mark, a sweep, and an optional class unload and compact. Triggered when card-cleaning threshold reached. Child XML element is `<concurrent-trace-info reason=””>`  |

**Interleaving of Gencon GC cycle increments**

While the *stop-the-world* scavenge operation of the partial GC cycle is running on the nursery area, the intermediate increment of the global GC cycle – a concurrent mark-sweep operation - runs concurrently on the tenure area.  

You can see this interleaving of the increments in the verbose GC log, which has a similar structure to the following example (for clarity, not all GC activities are listed):

<table style="width:100%">

  <tr>
    <th>Line Number</th>
    <th>Application threads running?</th>
    <th colspan="2">Logging</th>
  </tr>
  <tr>
    <th>-</th>
    <th>-</th>
    <th>Gencon global GC cycle status</th>
    <th>Gencon partial GC cycle status</th>
  </tr>
  <tr>
    <td>1-87</td>
    <td>✅ </td>
    <td colspan="2">initialization</th>
  </tr>
<tr>
    <td>87-51651</td>
    <td>✅ / ❌ </td>
    <td> - </td>
    <td> series of Gencon partial cycles start and finish </td>
  </tr>
<tr>
    <td>51655</td>
    <td>❌ </td>
    <td> - </td>
    <td>partial cycle initializes</td>
  </tr>
<tr>
    <td>51656</td>
    <td>❌ </td>
    <td> - </td>
    <td>scavenge increment runs</td>
  </tr>
  <tr>
    <td>51696</td>
    <td>❌ </td>
    <td> - </td>
    <td>partial cycle ends</td>
  </tr>
  <tr>
    <td>51700</td>
    <td> ✅  </td>
    <td> blank line - no activities logged</td>
    <td>-</td>
  </tr>
<tr>
    <td>51701</td>
    <td> ✅  </td>
    <td> global cycle's initial increment marked</td>
    <td>-</td>
  </tr>

  <tr>
    <td>51707</td>
    <td> ❌  </td>
    <td> global cycle initializes</td>
    <td>-</td>
  </tr>

  <tr>
    <td>51709</td>
    <td> ✅  </td>
    <td> concurrent increment runs (blank line)</td>
    <td>-</td>
  </tr>
  <tr>
    <td>51714</td>
    <td> ❌  </td>
    <td> concurrent increment runs</td>
    <td>partial cycle initializes</td>
  </tr>

  <tr>
    <td>51715</td>
    <td> ❌ </td>
    <td> concurrent increment runs</td>
    <td>scavenge increment runs</td>
  </tr>
  <tr>
    <td>51754</td>
    <td> ❌ </td>
    <td> concurrent increment runs</td>
    <td>partial cycle ends</td>
  </tr>
  <tr>
    <td>51758</td>
    <td> ✅  </td>
    <td> blank line - no activities logged </td>
    <td></td>
  </tr>
  <tr>
    <td>51762</td>
    <td> ❌  </td>
    <td> final collection increment runs </td>
    <td></td>
  </tr>
  <tr>
    <td>51867</td>
    <td> ❌  </td>
    <td> global cycle ends</td>
    <td></td>
  </tr>
  </table>

**Example - Gencon’s global GC cycle**

The following example shows how a global GC cycle is recorded in a Gencon policy verbose GC log. The global GC cycle is run after the completion of many partial GC cycles, so the log content in this example begins part way down the full log. For more information about the GC Initialization section and partial cycle log contents, see [Example - Gencon’s default partial GC cycle](./vgclogs.md/#example-gencons-default-partial-gc-cycle). 

 [The global GC cycle is split into increments](./verbosegc.md#interleaving-of-gencon-gc-cycle-increments) that interleave with partial GC cycles. The interleaving can be seen in the following example, where a partial GC cycle is logged between the start and end of the global cycle. 
 
**XML structure of global GC cycle**

The global GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some child XML elements are omitted for clarity:

```xml
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

<gc-op>”type=sweep” `/>`

<gc-end>  

<mem-info> 

<mem></mem> 

</mem-info> 
</gc-end> 

</cycle-end> 

<exclusive-end> 

// The logs now record a partial GC cycle that runs from start to finish 
```

**`<concurrent-kickoff>`** 

The XML element `<concurrent-kickoff>` records the start of the first increment of the Gencon Global GC cycle. The ‘<kickoff>’ element contains:

- Details of the reason for the launch of the GC cycle
- The target number of bytes the cycle aims to free up in the heap
- The current available memory in the different parts of the heap

```xml
<concurrent-kickoff id="12362" timestamp="2020-10-18T13:35:44.341"> 

<kickoff reason="threshold reached" targetBytes="239014924" thresholdFreeBytes="33024922" remainingFree="32933776" tenureFreeBytes="42439200" nurseryFreeBytes="32933776" /> 

</concurrent-kickoff> 
```

**Note:** To analyze specific parts of a cycle, you can search for the XML elements that mark a specific increment of the cycle. For example, you can search for the <concurrent-kickoff> XML element to locate the first increment of the Gencon global cycle. See the details of a particular cycle, such as the [Gencon Global Cycle](./vgclog.md/#global-gc-cycle), to determine the XML element names for particular *stop-the-world* or concurrent GC increments or operations. 

**`<exclusive-start>`** 

The `<exclusive-start>` XML element indicates the start of a *stop-the-world* pause:

```xml
<exclusive-start id="12363" timestamp="2020-10-18T13:35:44.344" intervalms="342.152"> 

<response-info timems="0.135" idlems="0.068" threads="3" lastid="00000000015DE600" lastname="LargeThreadPool-thread-24" /> 

</exclusive-start> 
```

**`<cycle-start>`** 

The beginning of the global cycle is recorded, indicated by the `"global"` value of the XML `type` attribute. All subsequent GC events recorded in the logs that are associated with this particular cycle have a `contextid` value equal to the `<cycle-start>` `id` attribute value of `“12634”`.

```xml
<cycle-start id="12364" type="global" contextid="0" timestamp="2020-10-18T13:35:44.344" intervalms="516655.052" /> 
```

**`<exclusive-end>` and the concurrent GC events** 

The `<exclusive-end>` XML element records the end of the *stop-the-world* pause:

```xml
<exclusive-end id="12365" timestamp="2020-10-18T13:35:44.344" durationms="0.048" /> 
```

**A blank line**
A blank line appears in the log before the next section. A blank line indicates that there no *stop-the-world* activities are running. However, concurrent activities might be running, in this case, the concurrent operations, and phases of the second increment of a Gencon global cycle are running.

**A partial GC cycle starts and completes** The next section of the logs records a *stop-the-world* pause associated with an allocation failure. The following XML element, `<cycle-start>`, indicates the start of a scavenge cycle. The ‘’contextid” XML attribute value of the XML elements in the following log section is “12368” not “12364. So the activities that are recorded in the section are associated with this new scavenge cycle rather than the currently running global cycle.  

This new scavenge cycle is a Gencon default partial GC cycle. For more information about how this cycle is recorded in the logs, see [Example - Gencon’s default partial GC cycle](vgclog.md#example-gencons-default-partial-gc-cycle). 

```xml
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
```

**`<exclusive-start>` and `<concurrent-global-final>`** 

After the partial GC cycle completes and the stop the world pause finishes, the log records a *stop-the-world* pause preceding a `<concurrent-global-final>` XML element. 

The `<concurrent-global-final>` XML element records the start of the third and final increment of the Global partial GC cycle, which consists of *stop-the-world* GC operations and phases. The *stop-the-world* pause is run by the garbage collector to run this final increment.  

The `reason` attribute of the `<concurrent-trace-info>` XML element indicates that the global cycle reached the card cleaning threshold and so can now complete this final increment.  

```xml
<exclusive-start id="12378" timestamp="2020-10-18T13:35:44.594" intervalms="12.075"> 

<response-info timems="0.108" idlems="0.040" threads="3" lastid="00000000018D3800" lastname="LargeThreadPool-thread-33" /> 

</exclusive-start> 

<concurrent-global-final id="12379" timestamp="2020-10-18T13:35:44.594" intervalms="516905.029" > 

<concurrent-trace-info reason="card cleaning threshold reached" tracedByMutators="200087048" tracedByHelpers="12164180" cardsCleaned="4966" workStackOverflowCount="0" /> 

</concurrent-global-final> 
```

**`<gc-start>`** 

A global GC increment begins. You can check that the increment is associated with the GC global cycle in the example by checking the `contextid` XML attribute value matches the `id` XML attribute value of the cycle's <gc-cycle> XML element. For the increment in the example, the` <gc-start>` `contextid` and `<gc-cycle>` `id` value are both `"12364"`.

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

The child XML element attribute values of the`<mem>` and `<mem-info>` XML elements indicate the configuration of the memory. For this example, at the start of this GC increment, 25% of the total heap is available as free memory. This free memory is split between the following areas of the heap:  

The nursery area, which has 87% of its total memory available as free memory. The free memory is only available in the allocate space of the nursery area. The survivor space has no free memory.  

The tenure area, which has 5% of its total memory available as free memory. All of this free memory is in the long object allocation area. No free memory is available in the short object allocation area.  

**Note:** The global GC cycle runs to free up memory in the tenure area. The freeing up of memory in the nursery area is achieved by using the partial GC cycle. For more information, see [Gencon Policy(default)](gc.md#gencon-policy-(default)).

**`<gc-op>`** 

The `<gc-op>` XML element and its child XML elements contain information about the increment’s operations and phases. 5 `<gc-op>` XML elements are recorded. The `type` XML attribute records the different operations and phases that are involved:

1. `Rs-scan`
2. `Card-cleaning`
3. `Mark`
4. `Classunload`
5. `Sweep`

**Note:** The final increment of a Gencon global cycle can include an optional compact phase.

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

**`<gc-end>`**

The `<gc-end>` XML element and its child XML elements record details about the end of the final increment of the global cycle:

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

The `<mem>` and `<mem-info>` child XML elements show that after the increment runs, the heap contains 60% free memory. This free memory is split between the following areas of the heap:  

- the nursery area remains unchanged, with 87% of its total memory available as free memory. The free memory is only available in the allocate space of the nursery area. The survivor space has no free memory. 
- the tenure area, now has 51% of its total memory available as free memory. The memory is split between the small object allocation space, which has 48% of its space available as free memory, and the large object allocation space, which is all available memory.

**`<cycle-end>`**

After the GC operations and phases of the final increment of the global cycle complete, the global cycle ends and the *stop-the-world* pause ends:

```xml
<cycle-end id="12389" type="global" contextid="12364" timestamp="2020-10-18T13:35:44.619" /> 

<exclusive-end id="12391" timestamp="2020-10-18T13:35:44.619" durationms="24.679" /> 
```

**Summary of the example**
In summary, by analyzing the structure and XML element details of the example, you have determined the following: 

- The GC global cycle is triggered when a memory threshold is reached and begins with a *stop-the-world* pause 

- After the first increment of the GC global cycle completes, the *stop-the-world* pause ends and the second increment runs concurrently. 

- While the second increment is running concurrently, a partial GC cycle starts and finishes. 

- When the second increment completes, a *stop-the-world* pause begins so that the third and final increment of the global cycle, which consists of five operations and phases, can run. 

- The global GC cycle reclaims memory in the tenure area.