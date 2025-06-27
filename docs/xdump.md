<!--
* Copyright (c) 2017, 2025 IBM Corp. and others
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
* [2] https://openjdk.org/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0-only WITH Classpath-exception-2.0 OR GPL-2.0-only WITH OpenJDK-assembly-exception-1.0
-->

# -Xdump


OpenJ9 produces various types of diagnostic information for analysis when different events occur, such as a general protection fault. The dumps produced are controlled by dump agents, which are initialized when the OpenJ9 virtual machine (VM) starts. The default settings for the dump agents are sufficient for most cases. However, you can use the `-Xdump` option on the command line to fine tune the dump agent settings. For example, you can use the `-Xdump` option to add and remove dump agents for various VM events, update default dump settings, and limit the number of dumps that are produced.

A large set of options and suboptions are available for controlling dumps, which provides a lot of flexibility.

## Xdump Option Builder

Use the <a href="https://www.eclipse.org/openj9/tools/xdump_option_builder.html" target="_blank">Xdump Option Builder tool</a> to help you specify the correct options and avoid incompatibilities.

## Syntax

        -Xdump:<parameter>

The following table lists the help options for `-Xdump`, which provide usage and configuration information:

| Command                      | Result                                                                                                                                                      |
|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `-Xdump:help`                | Displays general dump help.                                                                                                                                 |
| `-Xdump:events`              | Lists available trigger events.                                                                                                                             |
| `-Xdump:request`             | Lists additional VM requests.                                                                                                                               |
| `-Xdump:tokens`              | Lists recognized label tokens.                                                                                                                              |
| `-Xdump:what`                | Shows registered agents on startup.                                                                                                                         |
| `-Xdump:<agent>:help`        | Displays dump agent usage information.                                                                                                                      |


The following options can be used to control the production of diagnostic data:  

| Parameter                    | Result                                                                                                                                                      |
|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `-Xdump:none`                | Removes all default dump agents and any preceding dump options.                                                                                             |
| `-Xdump:dynamic`             | Enable support for pluggable agents                                                                                                                         |
| `-Xdump:nofailover`          | Discards dumps when the default or specified dump location is full.                                                                                         |
| `-Xdump:directory=<path>`    | Specifies a directory for all dump types to be written to. This directory path is prefixed to the path of all non-absolute dump file names, including the file names for the default dump agents. |
| `-Xdump:suspendwith=<offset>`| Modifies the signal that is used to suspend VM threads while a dump file is being written. Use `<offset>` to change the default signal number. (Linux&reg; only) |
| `-Xdump:<agent>:<suboptions>`| Provides detailed suboptions per dump agent that provide more granular control.                                                                             |

Dump agents can be configured at a very granular level by specifying suboptions. The `<events>` suboption is the prime trigger mechanism. If no events are specified explicitly, then the default settings for the corresponding agent are added. For more information, see [Default dump agents](#default-dump-agents). You can update the default dump settings by using the [`defaults`](#defaults) suboption. The full set of suboptions are listed in the following table:

| Dump agent suboptions                                 | Result                                                                     |
|-------------------------------------------------------|----------------------------------------------------------------------------|
| `-Xdump:<agent>:none`                                 | Removes the dump agent.                                                    |
| `-Xdump:<agent>:defaults`                             | Prints the default options for the dump agent.                             |
| `-Xdump:<agent>:events=<events>`                      | Triggers a dump agent when a specific event occurs.                        |
| `-Xdump:<agent>:exec=<command>`                       | Starts an external application for the dump agent.                         |
| `-Xdump:<agent>:file=<filename>`                      | Specifies where to write the dump for the dump agent.                      |
| `-Xdump:<agent>:filter=<filter>`                      | Filters dumps by wildcards or events.                                      |
| `-Xdump:<agent>:msg_filter=<filter>`                  | Filters on text strings within an exception message.                       |
| `-Xdump:<agent>:opts=<options>`                       | Used by specific dump agents to select the type of dump file to produce.   |
| `-Xdump:<agent>:priority=<0-999>`                     | Specifies the priority that the dump agents run in.                        |
| `-Xdump:<agent>:range=<ranges>`                       | Starts and stops a dump agent on a particular occurrence of a VM.          |
| `-Xdump:<agent>:request=<requests>`                   | Asks the VM to prepare the state before starting the dump agent.           |

You can have multiple `-Xdump` options on the command line. You can also have multiple dump types triggered by multiple events. For example, the following command turns off the creation of heap dump files, and creates a dump agent that produces a heap dump file and a Java&trade; dump file when either a **vmstart** or **vmstop** event occurs:

```
java -Xdump:heap:none -Xdump:heap+java:events=vmstart+vmstop -mp . -m <class> [args...]
```

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Multiple suboptions that follow an `Xdump` suboption must be split with a comma (,), for example:

```
java -Xdump:java:events=vmstart,file=/STDERR/ -version
```

For more detailed information on these parameters and suboptions, including examples, see [Parameters](#parameters).

## Dump agents

A dump agent performs diagnostic tasks when triggered. Most dump agents save information on the state of the VM in some form of dump or trace file for later analysis.
An exception is the "tool" agent, which can be used to trigger external processes when specific events occur.

| Dump agent                | Description                                                                                            |
|---------------------------|--------------------------------------------------------------------------------------------------------|
| stack                     | Stack dumps are very basic dumps in which the status and Java stack of the thread is written to stderr.|
| console                   | Console dumps are very basic dumps, in which the status of every Java thread is written to stderr.     |
| system                    | System dumps capture the raw process image or address space of an application.                         |
| tool                      | The tool option allows external processes to be started when an event occurs.                          |
| java                      | Java dumps are an internally generated and formatted analysis of the VM, giving information that includes the Java threads present, the classes loaded, and heap statistics.|
| heap                      | Heap dumps capture all object instances in the heap, including each object address, type or class name, size, and references to other objects.|
| snap                      | Take a snap of the trace buffers, which contain tracepoint data.                                       |
| ceedump                   | LE CEEDUMP dumps are z/OS&reg; formatted summary system dumps that show stack traces for each thread that is in the VM process, together with register information and a short dump of storage for each register. |
| jit                       | JIT compiler dumps contain diagnostic data in a binary format.                                         |
| exit                       | Shut down the VM.                                                                                     |

### Default dump agents

During VM initialization a set of dump agents are added by default. You can override this set of dump agents using `-Xdump` on the command line. To show the registered dump agents, user the `Xdump:what` option on the command line. The following sample output shows the default dump agents that are in place on a Linux system:

```
java -Xdump:what

Registered dump agents
----------------------
-Xdump:system:
    events=gpf+abort+traceassert+corruptcache,
    file=/home/user/core.%Y%m%d.%H%M%S.%pid.%seq.dmp,
    range=1..0,
    priority=999,
    request=serial
----------------------
-Xdump:system:
    events=systhrow,
    filter=java/lang/OutOfMemoryError,
    file=/home/user/core.%Y%m%d.%H%M%S.%pid.%seq.dmp,
    range=1..1,
    priority=999,
    request=exclusive+compact+prepwalk
----------------------
-Xdump:heap:
    events=systhrow,
    filter=java/lang/OutOfMemoryError,
    file=/home/user/heapdump.%Y%m%d.%H%M%S.%pid.%seq.phd,
    range=1..4,
    priority=500,
    request=exclusive+compact+prepwalk,
    opts=PHD
----------------------
-Xdump:java:
    events=gpf+user+abort+traceassert+corruptcache,
    file=/home/user/javacore.%Y%m%d.%H%M%S.%pid.%seq.txt,
    range=1..0,
    priority=400,
    request=exclusive+preempt
----------------------
-Xdump:java:
    events=systhrow,
    filter=java/lang/OutOfMemoryError,
    file=/home/user/javacore.%Y%m%d.%H%M%S.%pid.%seq.txt,
    range=1..4,
    priority=400,
    request=exclusive+preempt
----------------------
-Xdump:snap:
    events=gpf+abort+traceassert+corruptcache,
    file=/home/user/Snap.%Y%m%d.%H%M%S.%pid.%seq.trc,
    range=1..0,
    priority=300,
    request=serial
----------------------
-Xdump:snap:
    events=systhrow,
    filter=java/lang/OutOfMemoryError,
    file=/home/user/Snap.%Y%m%d.%H%M%S.%pid.%seq.trc,
    range=1..4,
    priority=300,
    request=serial
----------------------
-Xdump:jit:
    events=gpf+abort,
    file=/home/user/jitdump.%Y%m%d.%H%M%S.%pid.%seq.dmp,
    range=1..0,
    priority=200,
    request=serial
----------------------
```
### Dump agent tokens

You can use tokens to add context to dump file names and directories, and to pass command-line arguments to the tool agent. The tokens available are listed in the following tables:

| Token | Description                                                                                                                   |
|-------|-------------------------------------------------------------------------------------------------------------------------------|
| %Y    | Year (4 digits)                                                                                                               |
| %y    | Year (2 digits)                                                                                                               |
| %m    | Month (2 digits)                                                                                                              |
| %d    | Day of the month (2 digits)                                                                                                   |
| %H    | Hour (2 digits)                                                                                                              |
| %M    | Minute (2 digits)                                                                                                             |
| %S    | Second (2 digits)                                                                                                             |
| %home | Java home directory                                                                                                           |
| %last | Last dump                                                                                                                     |
| %pid  | Process ID                                                                                                                    |
| %seq  | Dump counter                                                                                                                  |   
| %tick | msec counter                                                                                                                  |
| %uid  | User name                                                                                                                     |

The following tokens are applicable only to z/OS:

| Token    | Description                                                                                                                |
|----------|----------------------------------------------------------------------------------------------------------------------------|
| %asid    | Address space ID                                                                                                           |
| %job     | Job name                                                                                                                   |
| %jobid   | Job ID                                                                                                                     |
| %sysname | SYSNAME sysparm                                                                                                            |
| &DS      | Dump Section. An incrementing sequence number used for splitting TDUMP files to be less than 2 GB in size. (64-bit only)   |

### Merging dump agents

If you configure more than one dump agent, each responds to events according to its configuration. However, the internal structures representing the dump agent configuration might not match the command line because dump agents are merged for efficiency. Two sets of options can be merged as long as none of the agent settings conflict. This means that the list of installed dump agents and their parameters produced by `-Xdump:what` might not be grouped in the same way as the original `-Xdump` options that configured them.


For example, you can use the following command to specify that a dump agent creates a Java dump file on class unload:

```
java -Xdump:java:events=unload -Xdump:what
```

This command does not create a new agent, as can be seen in the results from the `-Xdump:what` option.

```
...
----------------------
-Xdump:java:
    events=gpf+user+abort+unload+traceassert+corruptcache,
    file=/home/user/javacore.%Y%m%d.%H%M%S.%pid.%seq.txt,
    range=1..0,
    priority=400,
    request=exclusive+preempt
----------------------
```

The configuration is merged with the existing Java dump agent for events **gpf**, **user**, **abort**, **traceassert**, and **corruptcache**, because none of the specified options for the new unload agent conflict with those for the existing agent.

In the previous example, if one of the parameters for the unload agent is changed so that it conflicts with the existing agent, then it cannot be merged. For example, the following command specifies a different priority, forcing a separate agent to be created:

```
java -Xdump:java:events=unload,priority=100 -Xdump:what
```

The results of the `-Xdump:what` option in the command are as follows.

```
...
----------------------
-Xdump:java:
    events=unload,
    file=/home/user/javacore.%Y%m%d.%H%M%S.%pid.%seq.txt,
    range=1..0,
    priority=100,
    request=exclusive+preempt
----------------------
-Xdump:java:
    events=gpf+user+abort+traceassert+corruptcache,
    file=/home/user/javacore.%Y%m%d.%H%M%S.%pid.%seq.txt,
    range=1..0,
    priority=400,
    request=exclusive+preempt
----------------------
```

To merge dump agents, the *request*, *filter*, *opts*, *file*, and *range* parameters must match exactly. If you specify multiple agents that filter on the same string, but keep all other parameters the same, the agents are merged. For example:
```
java -Xdump:none -Xdump:java:events=uncaught,filter=java/lang/NullPointerException -Xdump:java:events=unload,filter=java/lang/NullPointerException -Xdump:what
```

The results of this command are as follows:

```
Registered dump agents
----------------------
-Xdump:java:
    events=unload+uncaught,
    filter=java/lang/NullPointerException,
    file=/home/user/javacore.%Y%m%d.%H%M%S.%pid.%seq.txt,
    range=1..0,
    priority=400,
    request=exclusive+preempt
----------------------
```

## Dump events

Dump agents are triggered by events occurring during operation of the OpenJ9 VM. Some events can be filtered to improve the relevance of the output.

The following table shows the events that are available as dump agent triggers:

| Event           |  Triggered when....                                                         | Filters on....                                                |
| ----------------|-----------------------------------------------------------------------------|----------------------------------------------------------------|
| **gpf**         | A General Protection Fault (GPF) occurs.                                    | Not applicable                                                 |
| **user**        | The VM receives the SIGQUIT (Linux, macOS&reg;, AIX&reg;, z/OS) or SIGBREAK (Windows&trade;) signal from the operating system.|  Not applicable                      |
| **user2**       | The VM receives the SIGUSR2 (Linux, AIX, z/OS, and macOS) signal from the operating system.               | Not applicable                                                 |
| **abort**       | The VM receives the SIGABRT signal from the operating system.               | Not applicable                                                 |
| **vmstart**     | The virtual machine is started.                                             | Not applicable                                                 |
| **vmstop**      | The virtual machine stops.                                                  | Exit code; for example, `filter=#129..#192#-42#255`            |
| **load**        | A class is loaded.                                                          | Class name; for example, `filter=java/lang/String`             |
| **unload**      | A class is unloaded.                                                        | Not applicable                                                 |
| **throw**       | An exception is thrown explicitly in Java code. Use 'systhrow' for unexpected VM exceptions.                                                     | Exception class name; for example, `filter=java/lang/OutOfMem*`|
| **catch**       | An exception is caught.                                                     | Exception class name; for example, `filter=*Memory*`           |
| **uncaught**    | A Java exception is not caught by the application.                          | Exception class name; for example, `filter=*MemoryError`       |
| **systhrow**    | A Java exception is about to be thrown by the VM. This is different from the 'throw' event because it is only triggered for error conditions detected internally in the VM.| Exception class name; for example, `filter=java/lang/OutOfMem*`.|
| **thrstart**    | A new thread is started.                                                    | Not applicable                                                 |
| **blocked**     | A thread becomes blocked.                                                   | Not applicable                                                 |
| **thrstop**     | A thread stops.                                                             | Not applicable                                                 |
| **fullgc**      | A garbage collection cycle is started.                                      | Not applicable                                                 |
| **slow**        | A thread takes longer than 50 ms to respond to an internal VM request.       | Time taken; for example, filter=#300ms will trigger when a thread takes longer than 300 ms to respond to an internal VM request.|
| **allocation**  | A Java object is allocated with a size matching the given filter specification.| Object size; a filter must be supplied. For example, filter=#5m will trigger on objects larger than 5 Mb. Ranges are also supported; for example, filter=#256k..512k will trigger on objects 256 - 512 Kb in size.|
| **traceassert** | An internal error occurs in the VM.                                         | Not applicable                                                 |
| **corruptcache**| The VM finds that the shared classes cache is corrupt.                      | Not applicable                                                 |
| **excessivegc** | An excessive amount of time is being spent in the garbage collector.        | Not applicable                                                 |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- The **gpf**, **traceassert**, and **abort** events cannot trigger a heap dump, prepare the heap (request=prepwalk), or compact the heap (request=compact).
- The Java dump agent behaves differently when triggered by the `user` and `user2` events. For more information, see [`request=<requests>`](#requestrequests).
- The `user2` event is commonly used for taking system dump files with exclusive access without overriding the `user` event, which is generally left for taking Java dump files for performance investigations. For example:

```
-Xdump:system:events=user2,request=exclusive+prepwalk
```

## Parameters

`-Xdump:<agent>:<suboptions>` descriptions and examples.

### `help`

To print usage information for a specific dump agent, use `-Xdump:<agent>:help`

### `none:<options>`

Use the `-Xdump:none` option to add and remove dump agents for various VM events, update default dump settings (such as the dump name), and limit the number of dumps that are produced.

The option can be used to affect all agents by specifying `-Xdump:none:<options>` or specific agents by specifying `-Xdump:<agent>:none:<suboptions>`

where `<suboptions>` is one of the following control types:

- `events=<event>`
- `exec=<command>`
- `file=<filename>`
- `filter=<filter>`
- `opts=<options>`
- `priority=<0-999>`
- `range=<ranges>`
- `request=<requests>`

Explanations for these suboptions are provided elsewhere in this topic.

To remove all default dump agents and any preceding dump options, use `-Xdump:none`. Use this option so that you can subsequently specify a completely new dump configuration.

You can also remove dump agents of a particular type. Here are some examples:

To turn off all heap dumps (including default agents) but leave Java dumps enabled, use the following option:

```
-Xdump:java+heap:events=vmstop -Xdump:heap:none
```

To turn off all dump agents for corruptcache events:

```
-Xdump:none:events=corruptcache
```
To turn off just system dumps for corruptcache events:

```
-Xdump:system:none:events=corruptcache
```

To turn off all dumps when a `java/lang/OutOfMemory` error is thrown:

```
-Xdump:none:events=systhrow,filter=java/lang/OutOfMemoryError
```

To turn off just system dumps when a `java/lang/OutOfMemory` error is thrown:

```
-Xdump:system:none:events=systhrow,filter=java/lang/OutOfMemoryError
```

If you remove all dump agents by using `-Xdump:none` with no further `-Xdump` options, the VM still provides these basic diagnostic outputs:

- If a user signal (kill -QUIT) is sent to the VM, a brief listing of the Java threads including their stacks, status, and monitor information is written to stderr.
- If a crash occurs, information about the location of the crash, VM options, and native and Java stack traces are written to stderr. A system dump file is also written to the user's home directory.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Removing dump agents and specifying a new dump configuration can require a long set of command-line options. To reuse command-line options, save the new dump configuration in a file and use the `-Xoptionsfile` option. For more information, see [-Xoptionsfile](xoptionsfile.md).

### `defaults`

Each dump type has default options. To view the default options for a particular dump type, use `-Xdump:<agent>:defaults`.

You can change the default options at run time. For example, you can direct Java dump files into a separate directory for each process, and guarantee unique files by adding a sequence number to the file name using:

```
-Xdump:java:defaults:file=dumps/%pid/javacore-%seq.txt
```

Or, for example, on z/OS, you can add the jobname to the Java dump file name using:

```
-Xdump:java:defaults:file=javacore.%job.%H%M%S.txt
```

This option does not add a Java dump agent; it updates the default settings for Java dump agents. Further Java dump agents will then create dump files using this specification for filenames, unless overridden.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Changing the defaults for a dump type will also affect the default agents for that dump type added by the VM during initialization. For example, if you change the default file name for Java dump files, that will change the file name used by the default Java dump agents. However, changing the default range option will not change the range used by the default Java dump agents, because those agents override the range option with specific values.

### `events=<event>`

To trigger a dump as a result of an event, use the `-Xdump:<agent>:events=<event>` suboption. For a list of possible events, see [Dump events](#dump-events).

For example, the following command instructs the VM to create a dump agent at startup that produces a Heap dump whenever the *vmstop* event happens:
```
-Xdump:heap:events=vmstop
```

### `exec=<command>`

The exec suboption is used by the tool dump agent to specify an external application to start. You can set a specific command to run for a particular dump agent with the following command:
```
-Xdump:<agent>:exec=<command>
```

### `file=<filename>`

The file suboption specifies where the diagnostics information is written for the specified dump type. The syntax is `-Xdump:<agent>:file=<filename>`.

For example, to create a Heap dump called `my.dmp` when a **vmstop** event is received, use:

```
java -Xdump:heap:events=vmstop,file=my.dmp
```

When producing system dump files on z/OS platforms, use the `dsn` option instead of the `file` option. For example:

```
java -Xdump:system:events=vmstop,dsn=%uid.MYDUMP
```


#### Writing to `STDOUT`/`STDERR`

Add one of the following options to write a Java dump file to STDOUT or STDERR respectively:

```
-Xdump:java:file=/STDOUT/
-Xdump:java:file=/STDERR/
```

- The keywords `/STDOUT/` and `/STDERR/` are *not* case sensitive; `/stdout/` and `/stderr/` are equivalent.
- By common convention, you can use a dash (`-`) to refer to STDOUT:

        -Xdump:java:file=-

#### Tokens

You can use tokens to add context to dump file names. For a list of tokens, see [Dump agent tokens](#dump-agent-tokens).

#### File location

The location for the dump file is selected from the following options, in this order:

1. The location specified by the `-Xdump:<agent>:file` suboption on the command line (if that location includes a path). This location applies to the specified dump agent type only.
2. The location specified by the `-Xdump:directory` option on the command line. This location applies to all dump agent types.
3. The location specified by the relevant environment variable:

    | Dump agent type            | z/OS operating systems     | Other operating systems |
    |----------------------------|----------------------------|-------------------------|
    | Java dumps                 | `_CEE_DMPTARG`             | `IBM_JAVACOREDIR`       |
    | Heap dumps                 | `_CEE_DMPTARG`             | `IBM_HEAPDUMPDIR`       |
    | System dumps               | `JAVA_DUMP_TDUMP_PATTERN`  | `IBM_COREDIR`           |
    | JIT dumps                  | `_CEE_DMPTARG`             | `IBM_COREDIR`           |
    | Snap traces                | `_CEE_DMPTARG`             | `IBM_COREDIR`           |

4. The current working directory of the OpenJ9 VM process.

If the directory does not exist, it is created.

If the dump file cannot be written to the selected location, the VM reverts to using the following locations, in this order:

1. On Windows platforms only, the system default location is `C:\WINDOWS`.
2. The location specified by the `TMPDIR` environment variable.
3. The `C:\Temp` on Windows operating systems, or the `/tmp` directory on other operating systems.

This VM action does not apply to system dumps on z/OS operating systems that use the `dsn` option.
You can prevent the VM reverting to different dump locations by using the `-Xdump:nofailover` option.


### `filter=<filter>`

Some VM events occur thousands of times during the lifetime of an application. Dump agents can use filters and ranges to avoid producing an excessive number of dump files. The following syntax must be used:

```
-Xdump:<agent>:filter=<filter>
```

#### Wildcards

You can use a wildcard in your exception event filter by placing an asterisk only at the beginning or end of the filter. The following command does not work because the second asterisk is not at the end:

```
-Xdump:java:events=throw,filter=*InvalidArgumentException#*.myVirtualMethod
```

To fix the problem, change this filter to the following string:

```
-Xdump:java:events=throw,filter=*InvalidArgumentException#MyApplication.*
```

#### Class loading and exception events

You can filter class loading (**load**) and exception (**throw**, **catch**, **uncaught**, **systhrow**) events by the name of the class that is being loaded, thrown or caught. For example:

```
-Xdump:java:events=load,filter=java/lang/String
-Xdump:java:events=throw,filter=java/lang/ArrayStoreException
-Xdump:java:events=catch,filter=java/lang/NullPointerException
```

In addition, you can filter **throw**, **uncaught**, and **systhrow** exception events by the name of the method that throws the exception. The name of the parent class must include the full package name, using the forward slash (/) as a separator. Use a dot (.) to separate the method name from the class name. You can use an asterisk (\*) as a wildcard character, to include all methods (optional portions are shown in brackets). For example:

```
-Xdump:java:events=throw,filter=ExceptionClassName[#com/ibm/ThrowingClassName.throwingMethodName[#stackFrameOffset]]
```

For example, to trigger a Java dump when method `MyApplication.myMethod()` throws a `NullPointerException` exception, use the following syntax:

```
-Xdump:java:events=throw,filter=java/lang/NullPointerException#com/ibm/MyApplication.myMethod
```

The stack frame offset allows you to filter on the name of a method that calls the throwing method. This option is useful if the exception is being thrown from a general purpose or utility class. For example, to trigger a Java dump when a method called by `MyApplication.main()` throws a `NullPointerException`, use the following syntax:

```
-Xdump:java:events=throw,filter=java/lang/NullPointerException#com/ibm/MyApplication.main#1
```

The default value of the stack frame offset is zero.

You can filter the **catch** exception events by Java method name (optional portions are shown in brackets). For example:

```
-Xdump:java:events=catch,filter=ExceptionClassName[#com/ibm/CatchingClassName.catchingMethodName]
```

You can filter **throw**, **uncaught**, and **systhrowexception** events by Java method name (optional portions are shown in brackets):

```
-Xdump:java:events=throw,filter=ExceptionClassName[#com/ibm/ThrowingClassName.throwingMethodName[#stackFrameOffset]]
```

You can filter the **catch** exception events by Java method name (optional portions are shown in brackets):

```
-Xdump:java:events=catch,filter=ExceptionClassName[#com/ibm/CatchingClassName.catchingMethodName]
```

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The filters apply to the stacktrace and fire every time the same exception is rethrown, which might result in multiple Java core files.

#### vmstop event

You can filter the VM shut down event (**vmstop**) by using one or more exit codes:

```
-Xdump:java:events=vmstop,filter=#129..192#-42#255
```

#### slow event

You can filter the **slow** event to change the time threshold from the default of 50 ms:

```
-Xdump:java:events=slow,filter=#300ms
```

#### allocation event

You must filter the **allocation** event to specify the size of objects that cause a trigger. You can set the filter size from zero up to the maximum value of a 32-bit pointer on 32-bit platforms, or the maximum value of a 64-bit pointer on 64-bit platforms. Setting the lower filter value to zero triggers a dump on all allocations.

For example, to trigger dumps on allocations greater than 5 Mb in size, use:

```
-Xdump:stack:events=allocation,filter=#5m
```

To trigger dumps on allocations between 256 Kb and 512 Kb in size, use:

```
-Xdump:stack:events=allocation,filter=#256k..512k
```

#### Other events

If you apply a filter to an event that does not support filtering, the filter is ignored.

### `msg_filter=<filter>`

You can use the msg_filter suboption to filter on text strings within an exception message, allowing you to reduce the number of dump files produced. This option is supported only for the following events: **throw**, **catch**, **systhrow**, and **uncaught**.

Use the following syntax to include message filtering in your dump output:

```
-Xdump:<agent>:events=<event>,msg_filter=<filter>`
```
where `<filter>` is a text string from the exceptions that you want to include in the dump file. This suboption supports asterisks as wild cards.

The following example filters `java/lang/VerifyError` exceptions that contain the text string *class format*:

```
-Xdump:java:events=throw,filter=java/lang/VerifyError,msg_filter=*class format*
```

### `opts=<options>`

The full syntax is `-Xdump:<agent>:opts=<options>`.

The heap dump agent uses this suboption to specify the type of file to produce. On z/OS, the system dump agent uses this suboption to specify the type of dump to produce.

#### Heap dumps

You can specify a PHD heap dump file (PHD), a classic text heap dump file (CLASSIC), or both. The default is a PHD file. For example:

```
-Xdump:heap:opts=PHD  
-Xdump:heap:opts=CLASSIC
-Xdump:heap:opts=PHD+CLASSIC
```

#### z/OS system dumps

You can specify a system transaction dump (IEATDUMP), an LE dump (CEEDUMP), or both. The default is an IEADUMP file. For example:

```
-Xdump:system:opts=IEATDUMP
-Xdump:system:opts=CEEDUMP
-Xdump:system:opts=IEATDUMP+CEEDUMP
```

The ceedump agent is the preferred way to specify LE dumps, for example:

```
-Xdump:ceedump:events=gpf
```

#### Tool dumps

The tool dump agent supports two suboptions that can be specified using the `opts` subption. You can run the external process asynchronously with opts=ASYNC. You can also specify a delay in milliseconds that produces a pause after starting the command. These two options can be used independently or together. The following examples show different options for starting a new process that runs `myProgram`:

```
-Xdump:tool:events=vmstop,exec=myProgram
```

Without the `opts` suboption, the tool dump agent starts the process, and waits for the process to end before continuing.

```
-Xdump:tool:events=vmstop,exec=myProgram,opts=ASYNC
```

When `opts=ASYNC` is specified, the tool dump agent starts the process, and continues without waiting for the new process to end.

```
-Xdump:tool:events=vmstop,exec=myProgram,opts=WAIT1000
```

This option starts the process, waits for the process to end, and then waits a further 1 second (1000 milliseconds) before continuing.

```
-Xdump:tool:events=vmstop,exec=myProgram,opts=ASYNC+WAIT10000
```

Finally, the last example starts the process and waits for 10 seconds before continuing, whether the process is still running or not. This last form is useful if you are starting a process that does not end, but requires time to initialize properly.

### `priority=<0-999>`

One event can generate multiple dump files. The agents that produce each dump file run sequentially and their order is determined by the priority keyword set for each agent. The full syntax for this command is `-Xdump:<agent>:priority=<0-999>`.

Examination of the output from `-Xdump:what` shows that a **gpf** event produces a snap trace, a Java dump file, and a system dump file. In this example, the system dump runs first, with priority 999. The snap dump runs second, with priority 500. The Java dump runs last, with priority 10:

```
-Xdump:heap:events=vmstop,priority=123
```

The maximum value allowed for priority is 999. Higher priority dump agents are started first.

If you do not specifically set a priority, default values are taken based on the dump type. The default priority and the other default values for a particular type of dump, can be displayed by using `-Xdump:<type>:defaults`. For example:

```
java -Xdump:heap:defaults -version
```

Default `-Xdump:heap` settings:

```
  events=gpf+user
  filter=
  file=/home/user/heapdump.%Y%m%d.%H%M%S.%pid.phd
  range=1..0
  priority=500
  request=exclusive+compact+prepwalk
  opts=PHD
```

### `range=<ranges>`

You can start and stop dump agents on a particular occurrence of a VM event by using the `range` suboption: `-Xdump:<agent>:range=<ranges>`

For example:

```
-Xdump:java:events=fullgc,range=100..200
```

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** range=1..0 against an event means "on every occurrence".

The VM default dump agents have the range suboption set to 1..0 for all events except systhrow. Most systhrow events with `filter=java/lang/OutOfMemoryError` have the range suboption set to 1..4, which limits the number of dump files produced on `OutOfMemory` conditions to a maximum of 4. For more information, see [Default dump agents](#default-dump-agents).

If you add a new dump agent and do not specify the range, a default of 1..0 is used.

### `request=<requests>`

Use the request suboption to ask the VM to prepare the state before starting the dump agent: `-Xdump:<agent>:request=<requests>`

The available suboptions are listed in the following table:

| suboption value  | Description                                                                                                               |
|------------------|---------------------------------------------------------------------------------------------------------------------------|
| exclusive        | Request exclusive access to the VM.<br>:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `-Xdump:request=exclusive` does not wait for exclusive access if the dump event is `user` or `slow` and another thread already has exclusive access. For such cases, use other events such as `user2` or other mechanisms such as `com.ibm.jvm.Dump.triggerDump`. For more information, see the content that follows the table.                          |
| compact          | Run garbage collection. This option removes all unreachable objects from the heap before the dump file is generated.      |
| prepwalk         | Prepare the heap for walking. You must also specify exclusive when you use this option.                                   |
| serial           | Suspend other dumps until this dump is finished.                                                                          |
| preempt          | Applies to the Java dump agent and controls whether native threads in the process are forcibly pre-empted in order to collect stack traces. If this option is not specified, only Java stack traces are collected in the Java dump.|

You can specify more than one request option by using **+**. For example:

```
-Xdump:heap:request=exclusive+compact+prepwalk
```

The VM exclusive access mechanism allows a VM thread to halt the activity of other VM threads in a controlled way by using internal VM locks. When the `request=exclusive` option is specified for a dump agent, the VM thread that is producing the dump waits for threads that are running Java code to halt, and for garbage collection operations to complete, before the dump file is written. This process helps ensure that the dump has consistent data. When the dump is complete, the mechanism allows the other threads to resume.

By default, only system dumps for `OutOfMemoryError` exceptions request exclusive access. Other system dump events typically result from a crash. In these cases, exclusive access is not requested because acquiring locks during a crash can be problematic.

If system dumps are requested by using the `com.ibm.jvm.Dump.SystemDump()` API, the default system dump agent settings are used, and exclusive access is not requested. However, if you intend to use the system dump file for Java heap memory analysis, use the following option to request exclusive access when the dump is taken:

```
-Xdump:system:defaults:request=exclusive+compact+prepwalk
```

These settings avoid capturing a dump file with in-flight data during garbage collection.
As an alternative, you can use the `com.ibm.jvm.Dump.triggerDump()` API and specify `request=exclusive+compact+prepwalk` on the API call.

For more information about the `com.ibm.jvm.Dump API`, see the API reference information.

The default setting of the `request` suboption for Java dump files is `request=exclusive+preempt`. To change the settings so that Java dump files are produced without pre-empting threads to collect native stack traces, use the following option:

```
-Xdump:java:request=exclusive
```

The Java dump agent ignores the `request=exclusive` setting if a `user` event occurs and another event already has exclusive access. In this scenario, the Java dump agent shares the access instead. This behavior is useful because it allows you to obtain a Java dump file during a deadlock situation, when exclusive access is not released. However, the resulting Java dump file, even in other situations, might omit thread stacks and contain inconsistent thread information, as indicated by the following line in the file:

```
1TIPREPINFO    Exclusive VM access not taken: data may not be consistent across javacore sections
```

On operating systems other than Windows, you can enforce exclusive access and obtain a complete dump file by specifying that the `user2` event triggers the Java dump agent instead of the `user` event. For example:

```
-Xdump:java:events=user2,request=exclusive+preempt
```
When a `user2` event occurs, for example, when you enter `kill -USR2 <pid>` on the command line, the Java dump agent accepts the `request=exclusive` setting and waits for exclusive access before creating the Java dump file.

For more information about events, see [Dump events](#dump-events).

In general, the default request options are sufficient.

## Dump output

Dump output is written to different files, depending on the type of dump and the platform. File names include a time stamp.

| Dump type      | File name  (AIX, Linux, macOS, Windows)                                | File name (z/OS)                                                                                         |
|----------------|-----------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| System dump    | core.%Y%m%d.%H%M%S.%pid.dmp                                     |  %uid.JVM.TDUMP.%job.D%Y%m%d.T%H%M%S (31-bit),  %uid.JVM.%job.D%y%m%d.T%H%M%S.X&DS (64-bit) See **Note** |
| Java dump      | javacore.%Y%m%d.%H%M%S.%pid.%seq.txt                            | javacore.%Y%m%d.%H%M%S.%pid.%seq.txt                                                                     |
| Heap dump      | heapdump.%Y%m%d.%H%M%S.%pid.phd                                 | heapdump.%Y%m%d.T%H%M%S.phd                                                                              |
| JIT dump       | jitdump%Y%m%d.%H%M%S.%pid.%seq.dmp                              | jitdump%Y%m%d.%H%M%S.%pid.%seq.dmp                                                                       |
| LE CEEDUMP     | -                                                               | CEEDUMP.%Y%m%d.%H%M%S.%pid See **Note**                                                                  |

The tokens used in this table, for example `%Y`, are described in [Dump agent tokens](#dump-agent-tokens).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On z/OS, the system dump file name can be set with the `JAVA_DUMP_TDUMP_PATTERN` environment variable. The CEEDUMP, which is not produced by default, is stored in the directory specified by
`_CEE_DMPTARG` or the current directory if `_CEE_DMPTARG` is not specified.

### System dumps on Linux

Linux does not provide an operating system API for generating a system dump from a running process. The VM produces system dumps on Linux by using the fork() API to start an identical process to the parent VM process. The VM then generates a SIGSEGV signal in the child process. The SIGSEGV signal causes Linux to create a system dump for the child process. The parent VM processes and renames the system dump, as required, by the `-Xdump` options, and might add additional data into the dump file.

The system dump file for the child process contains an exact copy of the memory areas used in the parent. The [dump viewer](tool_jdmpview.md) can obtain information about the Java threads, classes, and heap from the system dump. However, the dump viewer, and other system dump debuggers show only the single native thread that was running in the child process.

You can use the Linux `kernel.core_pattern` setting to specify the name and path for system dumps. The VM dump agents override the Linux system dump name and path by renaming the dump as specified in the `-Xdump` options. If the `kernel.core_pattern` setting specifies a different file system to the `-Xdump` options, the VM dump agents might be unable to change the file path. In this case the VM renames the dump file, but leaves the file path unchanged. You can find the dump file name and location in the `JVMDUMP010I` message.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If you use the `%t` specifier in the `kernel.core_pattern` setting, the VM does not rename the dump. The VM cannot determine the exact time that Linux generated the core file, and therefore cannot be certain which Linux dump file is the correct one to rename.

#### Piped system dumps

The Linux option, `kernel.core_pattern=|<program>`, might be set to pipe system dumps to a system dump processing program (specified by `<program>`). If such a program is specified and the VM cannot find the system dump, you will see the following messages:

- `JVMPORT030W`
- `JVMDUMP012E`
- `JVMPORT049I`

These messages do not necessarily indicate a problem with the system dump. Review the documentation for the program listed in the `kernel.core_pattern` property to find the location of the system dump and how to configure the program to ensure that the dump file is not truncated.

You can find the current setting of `kernel.core_pattern` by running one of the following commands. For example,

```
$ cat /proc/sys/kernel/core_pattern
|/usr/lib/systemd/systemd-coredump %P %u %g %s %t %c %h
```
```
$ sysctl kernel.core_pattern
kernel.core_pattern = |/usr/lib/systemd/systemd-coredump %P %u %g %s %t %c %h
```
The `kernel.core_pattern` setting is also available in a Java dump. For example:

```
2CISYSINFO     /proc/sys/kernel/core_pattern = |/usr/lib/systemd/systemd-coredump %P %u %g %s %t %c %h
```

Here are some of the most common system dump processing programs and their default system dump locations:

- **systemd-coredump**:

    - Sample setting - `kernel.core_pattern=|/usr/lib/systemd/systemd-coredump`
    - By default, dump files go to `/var/lib/systemd/coredump/`.
    - Versions before v251 truncate 64-bit Java dumps at 2 GB. To avoid this, consider updating the configuration file, `/etc/systemd/coredump.conf` to increase the values of the `ProcessSizeMax` and `ExternalSizeMax` properties and load the updated configuration by running the `systemctl daemon-reload` command. For v251 and later versions, similar changes are needed if the dump files are greater than 32 GB.

- **apport**:

    - Sample setting - `kernel.core_pattern=|/usr/share/apport/apport`
    - By default, dump files go to `/var/crash/` or `/var/lib/apport/coredump/`.
    - Default settings do not truncate the dump files.

- **abrt-hook-ccpp**:

    - Sample setting - `kernel.core_pattern=|/usr/libexec/abrt-hook-ccpp`
    - By default, dump files go to `/var/spool/abrt`.
    - Default settings might truncate the dump files. Consider updating `/etc/abrt/abrt.conf` to set `MaxCrashReportsSize=0` and restart `abrtd`.

- **Dynatrace rdp**:

    - Sample setting - `kernel.core_pattern=|/opt/dynatrace/oneagent/agent/rdp`
    - The program passes the dump files to the underlying system dump processing program specified in `/opt/dynatrace/oneagent/agent/conf/original_core_pattern` (in OneAgent version 1.301 or earlier) or `/var/lib/dynatrace/oneagent/agent/backup/original_core_pattern` (in OneAgent version 1.302 or later).

In container environments, such as OpenShift, piped system dump files are stored in the worker node rather than in the container. Here is one example method for retrieving a dump file that was piped to the `systemd-coredump` program:

1. To access the system dump files, you must have access to the cluster as a user that has the `cluster-admin` role.
2. Find the worker node of the pod. For example:

    ```
    oc get pod --namespace MyNamespace --output "jsonpath={.spec.nodeName}{'\n'}" MyPodname
    ```

    Where, MyNamespace and MyPodname are names of your namespace and pod.

3. Start a debug pod on the worker node. For example:

    ```
    oc debug node/MyNode -t
    ```

    Where, MyNode is the worker node retrieved in step 2.

4. List the available system dump files. For example:

```
#chroot /host coredumpctl

TIME                             PID        UID   GID SIG COREFILE  EXE

Wed 2022-08-03 18:52:29 UTC  2923161 1000650000     0  11 present   /opt/java/semeru/jre/bin/java
```

5. Find the line for the system dump that you want to download, copy the PID number from that row, and then pass it to the `info` sub-command to search for the dump location. For example:

```
# chroot /host coredumpctl info 2923161 | grep Storage:
Storage: /var/lib/systemd/coredump/core.kernel-command-.1000650000.08b9e28f46b348f3aabdffc6896838e0.2923161.1659552745000000.lz4
```

6. Run a command to print output on a loop so that the debug pod doesn't timeout:</li>

    ```
    while true; do echo 'Sleeping'; sleep 8; done
    ```

7. Open a new terminal and then find the debug pod and namespace. For example:

```
$ oc get pods --field-selector=status.phase==Running --all-namespaces | grep debug
openshift-debug-node-pwcn42r47f       worker3-debug       1/1     Running            0                  3m38s
```

8. Use the namespace (first column) and pod name (second column) that you retrieved in step 7 to download the system dump file from the `Storage` location that was listed in the output of step 5 on the worker node. Ensure that you prefix the `Storage` location with `/host/`. For example:

```
oc cp --namespace openshift-debug-node-pwcn42r47f worker3-debug:/host/var/lib/systemd/coredump/core.kernel-command-.1000650000.08b9e28f46b348f3aabdffc6896838e0.2923161.1659552745000000.lz4 core.dmp.lz4
```
This example stores the system dump locally in a compressed file, `core.dmp.lz4`.

9. After the download completes, in the first terminal window, exit the loop by pressing `Ctrl+C` and end the debug pod by typing `exit`.


## See also

- [`-Xtrace`](xtrace.md)
- [`-Xdisablejavadump`](xdisablejavadump.md)
- [`-XX[+|-]HandleSIGUSR2`](xxhandlesigusr2.md)


<!-- ==== END OF TOPIC ==== xdump.md ==== -->
