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

For definitions of GC cycles, GC increments, operations, phases, see [Garbage collection policies](gc.md#garbage-collection-policies). 

The logs record when GC cycles and their increments start and end, and list the GC operations that run within these increments to manage or reclaim memory. You can also determine which type of event triggered the cycle or increment, and the amount of memory available to your application before and after processing.  

Not all operations that take place during GC are recorded. For example, the logs record when an increment of a concurrent GC cycle starts and ends, but most concurrent GC operations are not recorded.

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

ADD DETAILS ABOUT THE INITIATISATION SECTION

The first set of <attribute> XML elements record the GC policy type and its settings. 
The child <attribute> XML elements of the <system> stanza records information about the....
<region> XML elements...
<vmargs>....

The verbose GC log then begins recording GC activities and details. 

### GC Cycles

The start of a GC cycle is recorded by the `<cycle-start>` XML element. The trigger for the GC cycle might be captured in a preceding element, for example if the trigger is an allocation failure. In other cases, the trigger might be captured in a subsequent element, for example if a policy's memory threshold trigger has been reached.

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

Given that some policies can run more than 1 GC cycle type, and some GC cycles are split into multiple increments to reduce pause times, different GC operations from different GC cycles might interleave with each other. To follow how the different cycles and operations interleave in a log, you can identify operations and increments of a particular cycle. The XML elements and attribute values that define operations and increments of a particular cycle are specific to the policy and type of cycle. For details of the XML elements and attribute values that are used for a particular type of cycle for a particular policy, see [Example `gencon` log]() or [Example `balanced` log](). 

You can use the `contextid` and `id` attributes to locate the GC increments and operations in the log that are associated with a specific GC cycle:
 
1. Determine the ID of the GC cycle: find the value of the `id` attribute of the `<cycle-start>` element that denotes the start of the GC cycle.  Note: the `id` attribute increases incrementally with each GC event.  
2. Search for the `contextid` attribute values that match the GC cycle's ID. All GC increments, operations, and concurrent events that are associated with a particular cycle have a `contextid` attribute whose value matches GC cycle's ID. 

For examples of log output, including guidance on how to analyze the logs, see [Example `gencon` logs](vgclog_genconexamples.md) and [Example `balanced` logs](vgclog_balancedexamples.md).