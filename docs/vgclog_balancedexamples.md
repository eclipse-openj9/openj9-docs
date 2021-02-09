# `balanced` examples [WIP]

The `balanced` policy (`-Xgcpolicy:balanced`) uses two types of cycle to perform GC – a partial GC cycle and a global GC *mark* cycle. 
 
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
The `balanced` policy’s partial GC cycle runs all it's operations by using a single *stop-the-world* pause:

|GC Operations | GC increment | *stop-the-world* or concurrent| XML element of GC increment          | Details                                                                   |
|-------------|--------------|-------------------------------|--------------------------------------|---------------------------------------------------------------------------|
|copy forward, class unload, sweep, compact |Single        | *stop-the-world*              | `<gc-start>`, `<gc-end>`| `<gc-op'>` |ADD|


A partial GC cycle is recorded within the following example, which is taken from the verbose GC log output of a `balanced` policy garbage collection. The following log content is broken down into sections that describe particular activities of the GC cycle. 

The default partial GC cycle follows a general structure in the verbose GC log as shown. The lines are indented to help illustrate the flow and some child elements are omitted for clarity:

UPDATE THIS STRUCTURE WITH THE BALANCED CYCLE

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
