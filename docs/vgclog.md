# Verbose garbage collection Logs  

[Garbage collection](gc.md) (GC) reclaims used memory in the heap for reuse. During cleanup of the heap, the verbose GC logs, when enabled, capture information about the different GC operations that are involved in the GC cycles. GC operations aim to reorganize or reclaim memory. 
 
Verbose GC logs contain information about GC operations to assist with the following actions:  

- Tuning GC and improving application performance.
- Troubleshooting GC operations and policies. For example, analyzing long pauses, or determining how free memory is divided in the heap before and after a GC cycle.  

Verbose GC logs, when enabled, begin capturing information as soon as GC is initialized. 

To help you visualize and analyze the GC, you can feed verbose GC log files into various diagnostic tools and interfaces. Examples include tools such as the [Garbage Collection and Memory Visualizer extension for Eclipse](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv), and online services such as [GCEasy](https://gceasy.io).  

**Note:** You can run one or more GC traces by using the ['-Xtgc' option](xtgc.md) to get more detailed information to help diagnose GC problems or perform finer tuning. 

## How to generate a verbose GC log  

You can enable verbose GC logs by specifying the `-verbose:gc` standard option when you start your application. For more information, see [standard command-line options](cmdline_general.md). 

The output of `-verbose:gc` is printed to STDERR by default. To print the log output to a file, append the [`-Xverbosegclog`](xverbosegclog.md) option. You can also use this option to print to a succession of files, where each file logs a specified number of GC cycles.

## Verbose GC log contents and structure 
The verbose GC logs are printed in XML format and consist of the following sections:  

- A summary of your GC configuration, which is captured in the `<initialized>` XML element. 

- Information about the GC cycles that ran, including each cycle's GC operations and GC increments.  

For definitions of GC cycles, operations, phases, see [Garbage collection policies](gc.md#garbage-collection-policies). For definitions of GC increments, see [GC increments and interleaving](#gc-increments-and-interleaving).

The logs record when GC cycles and their increments start and end, and list the GC operations that run within these increments to manage or reclaim memory. You can also determine which type of event triggered the cycle or increment, and the amount of memory available to your application before and after processing.  

### Initialization

The first section of the log records the configuration of the garbage collector, for example:  

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
...
    <vmarg name="-Xms1024m" />
    <vmarg name="-Xmx1024m" />
...
    <vmarg name="-Dsun.java.launcher.pid=6068" /> 

</vmargs> 

</initialized> 

```

ADD DETAILS ABOUT THE INITIATISATION SECTION

The first set of <attribute> XML elements record the GC policy type and its settings. 
The child <attribute> XML elements of the <system> stanza records information about the....
<region> XML elements...
<vmargs>....

The verbose GC log then begins recording GC activities and details. 

### GC Cycles

The start of a GC cycle is recorded by the `<cycle-start>` XML element. The trigger for the start of a GC cycle is captured in a preceding element to the `<cycle-start>`. The most common triggers for starting or ending a GC cycle or GC increment are:

- Allocation failures, recorded by the `<af-start>` element. Allocation failures occur when a specific area of the heap is unable to fulfil allocation.
- Memory thresholds being reached, recorded by the `reason` attribute of the element associated with the start of the first GC increment of the cycle. Memory threshold values, which set the conditions for performing certain types of GC activities, are defined by the policy type and configuration options.
- Allocation taxation, recorded by the `reason` attribute of the element associated with the GC increment end or cycle end. When the GC has achieved the specific allocation threshold required of the cycle or increment, the allocation taxation triggers the end of the GC increment or cycle.

The following XML structure is an example of the verbose GC logs that are generated from the Generational Concurrent GC policy (`-Xgcpolicy:gencon`). In this example, the lines are indented to help illustrate the flow and attributes and some child elements are omitted for clarity: 

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

Some elements serve as markers for starting and ending parts of the GC cycle and do not contain child elements, while other elements do contain child elements. In this example, the `<af-start/>`, `<cycle-start/>`, `<cycle-end/>`, `<allocation-satisfied/>`, and `<af-end/>` XML elements are empty and contain only attributes. All other XML elements contain child XML elements, which are omitted from this simplified example.  For detailed examples of log output for a specific cycle, see [Example `gencon` log](vgclog_genconexamples.md) or [Example `balanced` log](vgclog_balancedexamples.md).

### GC increments and interleaving

Some GC cycle types are executed in piecemeal blocks of operations or phases called GC increments. This reduces pause times by enabling blocks of operations or phases to interleave with operations and phases from other types of cycle. For example, consider the GC for the `gencon` policy which uses partial cycles and global cycles. The partial cycle consists of just 1 GC operation, scavenge, that runs on the *nursery* area during a STW pause. The global cycle, which runs when the *tenure* area is close to full, consists of some operations that are run during a stop the world pause, and others that are run concurrently. 

The global cycle is split into three increments - the initial and final increments which run during an STW pause, and an intermediate increment that executes it's GC operations concurrently. By splitting the global cycle operations and phases into increments, the STW scavenge operation of the partial GC cycle can run on the *nursery* area while the intermediate increment of the global GC cycle – a concurrent mark-sweep operation - runs concurrently on the *tenure* area. 

You can see this interleaving of the increments in the verbose GC log. The following table illustrates how the different increments interleave in a `gencon` policy log (for clarity, not all GC activities are listed):

<table style="width:100%">

  <tr>
    <th>Line Number</th>
    <th>Application threads running?</th>
    <th colspan="2">Logging</th>
  </tr>
  <tr>
    <th>-</th>
    <th>-</th>
    <th>`gencon` global GC cycle status</th>
    <th>`gencon` partial GC cycle status</th>
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
    <td> series of `gencon` partial cycles start and finish </td>
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

The XML elements and attribute values that define operations and increments of a particular cycle are specific to the policy and type of cycle. To follow how the different cycles's increments interleave in a log, you can locate the elements and attributes that record the increments and operations belonging to a particular type of cycle. 

For example, the XML elements corresponding to the different increments of the `gencon` policy's global GC cycle are shown in the following table: 

|GC Operation         | GC increment| STW or concurrent| XML element of GC increment| Details                         |
|---------------------|-------------|-------------------------------|--------------------------------------|-----------------------|
|n/a - initiates cycle|initial      | STW              | `<concurrent-kickoff`        |No `<gc-op>` is logged. This increment just initiates the concurrent mark increment |
|concurrent mark      |intermediate |concurrent                     | `<gc-start>`, `<gc-end>`     |`<concurrent-trace-info>` records progress of concurrent mark|
|final collection     |final        | STW              | `<concurrent-global-final>`  |Operations and phases include a final phase of concurrent mark, a sweep, and an optional class unload and compact. Triggered when card-cleaning threshold reached. Child XML element is `<concurrent-trace-info reason=””>`  |


For details of the XML elements and attribute values that are used for a particular type of cycle for a particular policy, and examples of log output, see [Example `gencon` log]() or [Example `balanced` log](). 

Operations are recorded in the logs once they have completed. As such, there may be cases where the start of a concurrent increment is recorded, but concurrent operations that are running at the same time as a STW increment of another cycle are not recorded until after the STW increment operations are recorded. For example, for the `gencon` policy, operations from the second, concurrent increment of the global cycle operations run during the partial cycle, but they are not logged until after the partial cycle has completed. In the following `genocon` log output example, the lines are indented to help illustrate the flow and attributes and some child elements are omitted for clarity: 

```xml

<concurrent-kickoff></concurrent-kickoff> <!--Trigger for global cycle -->
    <exclusive-start></exclusive-start> <!-- STW pause started -->
        <cycle-start></cycle-start> <!-- global cycle start recorded-->
    <exclusive-end></exclusive-end> <!-- STW pause finished 1st increment complete-->

<!-- concurrent operations of the global cycle's second increment running -->

<exclusive-start></exclusive-start>
  <af-start></af-start>
     <cycle-start></cycle-start> <!-- start of partial cycle-->
       <gc-start></gc-start>
         <gc-op type="scavenge"> </gc-op>
      <gc-end></gc-end>
     <cycle-end></cycle-end> <!-- start of partial cycle-->
  <af-end></af-end>
<exclusive-end></exclusive-end>

<exclusive-start><exclusive-start>
  <concurrent-collection-start> <!-- global cycle's final increment triggered-->
     <concurrent-trace-info reason="card cleaning threshold reached"> 
      <!-- completion of the concurrent operations of the global cycle's
      second increment -->
  </concurrent-collection-start>
  <gc-start> <!-- global cycle's final increment started-->
    <gc-op>
    ...
  <gc-end>
<cycle-end>
<concurrent-collection-end> <!-- final increment of the global cycle ended-->
<exclusive-end>
```

When analysing the logs, you can determine the GC increments and operations associated with a particular *instance* of a cycle by using the `contextid` and `id` attributes:
 
1. Determine the ID of the GC cycle: find the value of the `id` attribute of the `<cycle-start>` element that denotes the start of the GC cycle.  Note: the `id` attribute increases incrementally with each GC event.  
2. Search for the `contextid` attribute values that match the GC cycle's ID. All GC increments, operations, and concurrent events that are associated with a particular cycle have a `contextid` attribute whose value matches GC cycle's ID. 

For examples of log output, including guidance on how to analyze the logs, see [Example `gencon` logs](vgclog_genconexamples.md) and [Example `balanced` logs](vgclog_balancedexamples.md).