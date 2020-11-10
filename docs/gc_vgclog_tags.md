# vgc log XML tags and attributes
The following sections show sample log output for different gc events.

# Garbage Collection Configuration

**Section tag:** `<INITIATISED>`

When garbage collection is initialised, the verbose garbage collection log records the garbage collection configuration, including:
- the garbage collection policy type
- the policy options, such as ADD
- JVM command-line options that are active at the time of garbage collection initialisation, such as ADD

# stop-the-world operations

**Section tags:** `<exclusive-start>` and `<exclusive-end>`

During a stop-the-world  operation, an application is stopped so that the garbage collector has exclusive access to the JVM for actioning the freeing up of memory and memory compaction. The Verbose garbage collection log records the following information of the event:
The tags have the following attributes:

timestamp
The local timestamp at the start or end of the stop-the-world operation.

<response-info>
This tag provides details about the process of acquiring exclusive access to the virtual machine. This tag has the following attributes:

timems
The time, in milliseconds, that was taken to acquire exclusive access to the virtual machine. To obtain exclusive access, the garbage collection thread requests all other threads to stop processing, then waits for those threads to respond to the request. If this time is excessive, you can use the 

-Xdump:system:events command-line parameter to create a system dump. The dump file might help you to identify threads that are slow to respond to the exclusive access request. For example, the following option creates a system dump when a thread takes longer than one second to respond to an internal virtual machine request:

-Xdump:system:events=slow,filter=1000msCopy code
For more information about creating dumps, see -Xdump.

idlems
'Idle time' is the time between one of the threads responding and the final thread responding. During this time, the first thread is waiting, or 'idle'. The reported time for idlems is the mean idle time (in milliseconds) of all threads.

threads
The number of threads that were requested to release VM access. All threads must respond.

lastid
The last thread to respond.

lastname
The name of the thread that is identified by the lastid attribute.

durationms
The total time for which exclusive access was held by the garbage collection thread.

## Concurrent Cycles

### Concurrent kickoff event

### Concurrent final collection event

### Concurrent incremental collection event

## Garbage collection cycle
**Section tags:** `<cycle-start>` and `<cycle-end>`

## Garbage collection increment

**Section tags:** `<gc-start>`, `<gc-op>` and `<gc-end>`

## Garbage collection operation

**Section tag:** `<gc-op>`

Global:

Scavenge: