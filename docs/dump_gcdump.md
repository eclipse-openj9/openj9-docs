

# Verbose garbage collection logs 

Garbage collection reclaims unused memory for reuse. When the heap is full, a request to create an object causes an an allocation failure to occur, and garbage collection is instigated.

Garbage collection is triggered in the following instances:

- Calls to System.gc() 

- Allocation failures

- Completing concurrent collections (MEANING?) 

- Decisions based on the cost of making resource allocations (MEANING?) 

When verbose garbage collection (vgc) logs are enabled, the logs are collected as soon as [garbage collection](gc.md) is initialised.

vgc logs contain information on garbage collection operations to assist with the following:

- tuning of the gc
- troubleshooting gc operations and policies
- ADD

Verbose garbage collection log files can be fed into a variety of diagnostics tools and interfaces to provide monitoring and analysis capabilities.

By default, the information is printed to STDERR. To print the output to a file, use the [`-Xverbosegclog](Xverbosegclog.md) option. 

To help you understand how vgc logs can help you with tuning and troubleshooting, this topic includes a few scenarios to help you interpret the data:

1. Configuring the garbage collection logs - using nonstandard options -XLog (AND/OR OTHER USEFUL NONSTANDARD OPTIONS)
2. Determining optimum initial and maximum heap sizes for your JVM. For more information see ()[mm_heapsizing_verbosegc.html]
3. Tuning particular parameters for the type of garbage collection policy your JVM is using. ()[mm_gc_pd_mgc_verbose.html]
4. Troubleshooting the Metronome garbage collector (AIX and Linux only)

*Note:* If vgc logs do not provide enough information to help you diagnose gc problems, or you require more granular information to perform finer tuning, you can call one or more trace garbage collector (TGC) traces (['-XTgc' options](xtgc.md)) to add further information to your vgc log output.

## How to generate a verbose garbage collection log

You can generate a verbose garbage collection log file using the `-verbose:gc` standard Java ^{TM} option and configure the ???  using options such as ???

by default,the output of `-verbose:gc` is printed to STDERR. To output the verbose garbage collection log to a file, you must instead use the use the option `-Xverbosegclog`.

To configure the metrics/xml tags outputted by the garbage collection log, add options to the unified logging feature `-Xlog`



The following nonstandard (or -X) options can be used with the '-verbose:gc' option:

*ADD A TABLE HERE?  or simply point to the -X command parent topic?*

*Add trace options here?)
-Xgc: verboseGCCycleTime=N


## Verbose garbage collection log contents

The Verbose garbage collection logs summarise the configuration of your garbage collector, captured in the [`<INITIATISED>` tag section](./dump_gcdump.html#garbage-collection-configuration), and garbage collector events, which are recorded in sections that correspond to the individual operations, increments and cycles that make up that event. 

Garbage collector events are logged within sections labelled by the type of collection used - either concurrent or [stop-the-world](./dump_gcdump.html#stop-the-world-operations) collectors.

The logs capture details of the operations, increments and cycles tha make up each specific garbage collection event.

A **gc cycle** is a set of gc increments which, when complete, result in reclaimed memory for reuse.

Each gc increment consists of 1 or more gc operations.

Some cycles consist of just 1 increment. This is the case for a type of stop-the-world garbage collection that only implements the scavenge operation to reclaim unused memory. During this operation, the gc traverses all object references (in the heap?/ in the nursery? Or ALSO in the tenure section?) and if the object is not live, the memory is reclaimed. 

Conversely, other cycles consist of multiple operations, such as the mark-and-sweep cycle(?). WHERE DOES INCREMENT FIT IN? 

MARK – collector traverses all object references and marks any that are live. 

SWEEP – collector then traverses all object references again, reclaiming any objects that are not marked as live. 

Other operations include: 

COPY FORWARD – DOES WHAT? 

COMPACT – defragmentation? 

The verbose garbage collection logs also record other attributes of a collection such as:

**Allocation failures** tags `<afstart>` and `<af-end>` 

The following sections show sample results for different gc events.

### Garbage Collection Configuration

**Section tag:** `<INITIATISED>`

When garbage collection is initialised, the verbose garbage collection log records the garbage collection configuration, including:
- the garbage collection policy type
- the policy options, such as ADD
- JVM command-line options that are active at the time of garbage collection initialisation, such as ADD

### stop-the-world operations

**Section tags:** `<exclusive-start>` and `<exclusive-end>`

During a stop-the-world  operation, an application is stopped so that the garbage collector has exclusive access to the JVM for actioning the freeing up of memory and memory compaction. The Verbose garbage collection log records the following information of the event:

### Garbage collection cycle
**Section tags:** `<cycle-start>` and `<cycle-end>`

A garbage collection cycle is WHAT IS THIS?

### Garbage collection increment

**Section tags:** `<gc-start>`, `<gc-op>` and `<gc-end>`

### Garbage collection operation

**Section tag:** `<gc-op>`

Global:

Scavenge:

