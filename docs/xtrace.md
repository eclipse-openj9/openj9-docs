<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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

# -Xtrace

OpenJ9 VM tracing is a powerful feature to help you diagnose problems with minimal effect on performance. Tracing is enabled by default, together with a small set of trace points going to memory buffers. You can enable tracepoints at run time by using levels, components, group names, or individual tracepoint identifiers to trace VM internal operations and instrumented Java&trade; applications. You can also trace Java methods. See the [About trace](#about-trace) section that follows for more detail.

Trace data can be output in human-readable or in compressed binary formats. The VM provides a tool to process and convert the compressed binary data into a readable format. See [Trace formatter](tool_traceformat.md).  

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** You can also control trace by using the `com.ibm.jvm.Trace` API or by using JVMTI from an external agent.

## Xtrace Option Builder

Use the <a href="https://www.eclipse.org/openj9/tools/xtrace_option_builder.html" target="_blank">Xtrace Option Builder tool</a> to help you specify the correct options and avoid incompatibilities.

## Syntax

    -Xtrace:<parameter>

You can get help with `-Xtrace`by using the following options:

- `-Xtrace:help` &nbsp; Displays general trace help
- `-Xtrace:what` &nbsp; Shows the current trace settings

<!--
The following table lists the parameters for `-Xtrace` that provide usage and configuration information:

| Command        | Result                             |
|----------------|------------------------------------|
| `-Xtrace:help` | Displays general trace help        |
| `-Xtrace:what` | Shows the current trace settings   |
 -->


### Configuring trace

The following parameters can be used to configure trace. (Follow links for more information about individual options.)

| Command                                                            | Result                                                                                             |
|--------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| [`-Xtrace:properties[=<filename>]`](#properties)                   | Configures trace options based on a file                                                           |
| [`-Xtrace:buffers=<size>[dynamic\|nodynamic]`](#buffers)           | Modifies the size of buffers that are used to store trace data                                     |
| [`-Xtrace:exception.output=<filename>[,<size>]`](#exceptionoutput) | Redirects exceptions trace data to a file.                                          |
| [`-Xtrace:methods=<method_specification>`](#methods)               | Traces methods                                                                                     |
| [`-Xtrace:output=<filename>[,<size>[,<generations>]]`](#output)    | Sends trace data to a file, optionally of a specific size and number of generations.               |
| [`-Xtrace:resume`](#resume)                                        | Resumes tracing globally.                                                                          |
| [`-Xtrace:resumecount=<count>`](#resumecount)                      | Enables tracing at a thread level after a specified count.                                         |
| [`-Xtrace:sleeptime=<time>`](#sleeptime)                           | Pauses trace operations for a specified length of time.                                            |
| [`-Xtrace:stackdepth=<n>`](#stackdepth)                            | Limits the maximum number of stack frames reported by the jstacktrace trace trigger action.        |
| [`-Xtrace:suspend`](#suspend)                                      | Suspends tracing globally.                                                                         |
| [`-Xtrace:suspendcount=<count>`](#suspendcount)                    | Suspends tracing at a thread level after a specified count.                                        |
| [`-Xtrace:trigger=<clause>`](#trigger)                             | Determines when various triggered trace actions occur, including turning trace on or off.          |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** If an option value contains commas, it must be enclosed in braces. For example: `methods={java/lang/*,com/ibm/*}`

### Controlling tracepoint activation

The following parameters can be used to control tracepoint activation. (Follow links for more information about individual options.)

| Command                                                            | Result                                                                              |
|--------------------------------------------------------------------|-------------------------------------------------------------------------------------|
| [`-Xtrace:maximal=<tracepoint_specification>`](#maximal-tracepoint)           | Records all associated data.                                                        |
| [`-Xtrace:minimal=<tracepoint_specification>`](#minimal-tracepoint)           | Records only the time stamp and tracepoint identifier.                              |
| [`-Xtrace:count=<tracepoint_specification>`](#count-tracepoint)               | Counts the tracepoints that are used in a trace configuration.                      |
| [`-Xtrace:print=<tracepoint_specification>`](#print-tracepoint)               | Prints the specified tracepoints to stderr in real time.                            |
| [`-Xtrace:iprint=<tracepoint_specification>`](#iprint-tracepoint)             | Prints the specified tracepoints to stderr in real time with indentation.           |
| [`-Xtrace:exception=<tracepoint_specification>`](#exception-tracepoint)       | Enables exception tracing.                                                          |
| [`-Xtrace:external<tracepoint_specification>`](#external-tracepoint)          | Routes trace data to trace listeners, which are registered by using the JVMTI APIs. |
| [`-Xtrace:none[=<tracepoint_specification>]`](#none-tracepoint)               | Prevents the trace engine from loading if it is the only trace option specified.    |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** These options control which individual tracepoints are activated at run time and the implicit destination of the trace data. All these properties are independent of each other and can be mixed and matched in any way that you choose. For more information, see [Tracepoint activation](#tracepoint-activation).

## About trace

With the OpenJ9 trace feature, you can trace VM internal operations, Java applications, and Java methods, or any combination of these.

VM internal operations
: The OpenJ9 virtual machine (VM) is extensively instrumented with tracepoints for tracing operations. Interpreting this trace data requires detailed knowledge of the VM, and is intended to diagnose VM problems. No guarantee is given that tracepoints will not vary from release to release and from platform to platform.

Applications
: VM trace contains an application trace facility that allows tracepoints to be placed in Java code, enabling you to combine trace data with the other forms of trace. This capability is supported by the `com.ibm.jvm.Trace` API. Note that an instrumented Java application runs only on an OpenJ9 VM. For more information, see [Application trace](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/trace_application.html).

Java methods
: Use method trace to debug and trace application code and the system classes provided with the VM. You can trace entry to and exit from Java methods run by the VM. You can select method trace by classname, method name, or both. You can also use wildcards to create complex method selections. For more information about command syntax, see [methods](#methods).

Trace can produce large amounts of data in a very short time. Before running trace, think carefully about what information you need in order to solve the problem. Here are some considerations:

- If you need only the trace information that is produced shortly before the problem occurs, consider wrapping the file by using the `output` option.
- In many cases, just use internal trace with an increased buffer size and snap the trace when the problem occurs.
- If the problem results in a thread stack dump or operating system signal or exception, trace buffers are snapped automatically to a file that is in the current directory. The file is called: `Snapnnnn.
yyyymmdd.hhmmssth.process.trc`.

You must also think carefully about which components need to be traced and what level of tracing is required. For example, if you are tracing a suspected shared classes problem, it might be enough to trace all components at level 1, and **j9shr** at level 9, while `maximal` can be used to show parameters and other information for the failing component. Tracepoint components and trace levels are described in the following sections: [Tracepoint specification](#tracepoint-specification) and [Trace levels](#trace-levels).

There are two types of tracepoints inside the VM:  

- Regular tracepoints include method tracepoints, application tracepoints, data tracepoints inside the VM and data tracepoints inside class libraries. You can display regular tracepoint data on the screen or save the data to a file. You can also use command line options to trigger specific actions when regular tracepoints fire.
- Auxiliary tracepoints are a special type of tracepoint that can be fired only when another tracepoint is being processed. For example, the stack frame information produced by the jstacktrace `-Xtrace:trigger` command. You cannot control where auxiliary tracepoint data is sent and you cannot set triggers on auxiliary tracepoints. Auxiliary tracepoint data is sent to the same destination as the tracepoint that caused them to be generated.

Trace data can be written to one of the following locations:

- Memory buffers that can be dumped or snapped when a problem occurs. Use the `-Xtrace:buffers=<size>` option to control the size of the buffer allocated to each thread. Buffers allocated to a thread are discarded when that thread terminates. To examine the trace data captured in these memory buffers, you must snap or dump the data, then format the buffers.
- One or more files that are using buffered I/O. Use the `-Xtrace:output` option.
- An external agent in real time, using the `-Xtrace:external` option.
- stderr in real time.
- Any combination of the other items in this list.

## Default tracing

By default, the equivalent of the following trace command line is always available in the VM:

    -Xtrace:maximal=all{level1},exception=j9mm{gclogger}

When startup is complete, the equivalent of the following command line is added to enable level 2 trace points:

    -Xtrace:maximal=all{level2}

Level 2 is used for default tracing that would produce too much data during the startup of the VM. If you set other trace options on the command line, or before the VM finishes startup (through use of JVMTI or the `com.ibm.jvm.Trace` API), the level 2 trace points are enabled just before your trace options are processed. This behavior ensures that the default level 2 trace points do not override any changes that you specify.

The data generated by the tracepoints is continuously captured in wrapping memory buffers for each thread.

You can find tracepoint information in the following diagnostics data:

- System memory dumps, extracted by using jdmpview.
- Snap traces, generated when the VM encounters a problem or an output file is specified. Using dump agents describes more ways to create a snap trace.
- For exception trace only, in Javadumps.

### Default memory management tracing
The default trace options are designed to ensure that Javadumps always contain a record of the most recent memory management history, regardless of how much work the VM has performed since the garbage collection cycle was last called.

The `exception=j9mm{gclogger}` clause of the default trace set specifies that a history of garbage collection cycles that have occurred in the VM is continuously recorded. The **gclogger** group of tracepoints in the **j9mm** component constitutes a set of tracepoints that record a snapshot of each garbage collection cycle. These tracepoints are recorded in their own separate buffer, called the exception buffer. The effect is that the tracepoints are not overwritten by the higher frequency tracepoints of the VM.

The *GC History* section of the Javadump is based on the information in the exception buffer. If a garbage collection cycle has occurred in a traced VM, the Java dump probably contains a *GC History* section.

### Default assertion tracing
The VM includes assertions, implemented as special trace points. By default, internal assertions are detected and diagnostics logs are produced to help assess the error.

Assertion failures often indicate a serious problem, and the VM usually stops immediately. In these circumstances, raise an issue, including the standard error output and any diagnostic files that are produced.

When an assertion trace point is reached, a message like the following output is produced on the standard error stream:

    16:43:48.671 0x10a4800    j9vm.209    *   ** ASSERTION FAILED ** at jniinv.c:251:
    ((javaVM == ((void *)0)))

This error stream is followed with information about the diagnostic logs produced:

    JVMDUMP007I JVM Requesting System Dump using 'core.20060426.124348.976.dmp'
    JVMDUMP010I System Dump written to core.20060426.124348.976.dmp
    JVMDUMP007I JVM Requesting Snap Dump using 'Snap0001.20060426.124648.976.trc'
    JVMDUMP010I Snap Dump written to Snap0001.20060426.124648.976.trc

Assertions are special trace points. They can be enabled or disabled by using the standard trace command-line options.

Assertion failures might occur early during VM startup, before trace is enabled. In this case, the assert message has a different format, and is not prefixed by a timestamp or thread ID. For example:

    ** ASSERTION FAILED ** j9vmutil.15 at thrinfo.c:371 Assert_VMUtil_true((
      publicFlags & 0x200))

Assertion failures that occur early during startup cannot be disabled. These failures do not produce diagnostic dumps, and do not cause the VM to stop.

## Tracepoint activation

The options that control which individual tracepoints are activated at run time and the implicit destination of the trace data are listed under [Syntax: Controlling tracepoint activation](#controlling-tracepoint-activation)


<!--
The following options control which individual tracepoints are activated at run time and the implicit destination of the trace data. (Follow links for more information about individual options.)

- [`-Xtrace:maximal=<tracepoint_specification>`](#maximal)          
- [`-Xtrace:minimal=<tracepoint_specification>`](#minimal)          
- [`-Xtrace:count=<tracepoint_specification>`](#count)              
- [`-Xtrace:print=<tracepoint_specification>`](#print)              
- [`-Xtrace:iprint=<tracepoint_specification>`](#iprint)            
- [`-Xtrace:exception=<tracepoint_specification>`](#exception)      
- [`-Xtrace:exception.output=<filename>[,<size>]`](#exceptionoutput)
- [`-Xtrace:external<tracepoint_specification>`](#external)         
- [`-Xtrace:none[=<tracepoint_specification>]`](#none)              

-->
In some cases, you must use them with other options. For example, if you specify `maximal` or `minimal` tracepoints, the trace data is put into memory buffers. If you are going to send the data to a file, you must use an `output` option to specify the destination file name.

With the exception of `none`, all options require at least one `<tracepoint_specification>`, which is described in the following section. Multiple statements of each type of trace are allowed and their effect is cumulative. If you want to use multiple trace options of the same name, use a properties file. (See [`properties`](#properties).)

### Tracepoint specification

Tracepoints are enabled by specifying *component* and *tracepoint*.

If no qualifier parameters are entered, all tracepoints are enabled, except for `<exception.output>` trace, where the default is all {exception}.

The `<tracepoint_specification>` syntax can be further broken down as follows:

    [!]<component>[{<group>}] or [!]<component>[{<type>}] or [!]<tracepoint_id>[,<tracepoint_id>]

Where:

- The `!` symbol is a logical *not*. That is, the tracepoints that are in a specification starting with ! are turned off.
- `<component>` is a Java component.
- `<group>` is a tracepoint group, which is a set of tracepoints that are defined within a component.
- `<type>` is the tracepoint type: `entry`, `exit`, `event`, `exception`, and `eem`.
- `<tracepoint_id>` is the tracepoint identifier. The tracepoint identifier constitutes the component name of the tracepoint, followed by its integer number inside that component. For example, `j9mm.49`, `j9shr.20-29`, `j9vm.15`. To understand these numbers, see Determining the tracepoint ID of a tracepoint.

Some tracepoints can be both an `exit` and an `exception`; that is, the function ended with an error. If you specify either `exit` or `exception`, these tracepoints are included.

Lists of Java components and tracepoint groups can be found in the tables that follow.

The following table lists the possible Java components (`<component>`). To include all Java components, specify **all**.

|  Component name |  Description                                                                   |
|-----------------|--------------------------------------------------------------------------------|
| **avl**         | VM AVL tree support                                                            |
| **io**          | Class library java.io native code                                              |
| **j9bcu**       | VM byte code utilities                                                         |
| **j9bcverify**  | VM byte code verification                                                      |
| **j9codertvm**  | VM byte code run time                                                          |
| **j9dmp**       | VM dump                                                                        |
| **j9jcl**       | VM class libraries                                                             |
| **j9jit**       | VM JIT interface                                                               |
| **j9jni**       | VM JNI support                                                                 |
| **j9jvmti**     | VM JVMTI support                                                               |
| **j9mm**        | VM memory management                                                           |
| **j9prt**       | VM port library                                                                |
| **j9scar**      | VM class library interface                                                     |
| **j9shr**       | VM shared classes                                                              |
| **j9trc**       | VM trace                                                                       |
| **j9util**      | VM utilities                                                                   |
| **j9vm**        | VM general                                                                     |
| **j9vmutil**    | VM utilities                                                                   |
| **j9vrb**       | VM verbose stack walker                                                        |
| **map**         | VM mapped memory support                                                       |
| **mt**          | Java methods (see **Note**)                                                    |
| **net**         | Class library TCP/IP networking native code                                    |
| **pool**        | VM storage pool support                                                        |
| **rpc**         | VM RPC support                                                                 |
| **simplepool**  | VM storage pool support                                                        |
| **sunvmi**      | VM class library interface                                                     |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** When specifying the **mt** component you must also specify the `methods` option.

The following table lists all the tracepoint groups (`<group>`). Each group is associated with one or more Java components:

|Component name or names     |	Group name       |	Description                                                                                                              |
|----------------------------|-------------------|---------------------------------------------------------------------------------------------------------------------------|
|**j9mm**                    | `gclogger`	       | A set of tracepoints that record each garbage collection cycle. Equivalent to `-verbose:gc` output                        |
|**j9prt**                   | `nlsmessage`      | A set of tracepoints that record each NLS message that is issued by the VM.                                               |
|**j9jcl**, **j9vm**         | `verboseclass`	   | A set of tracepoints that record each class as it is loaded. Equivalent to `-verbose:class` output.                       |
|**j9jni**, **j9vm**         | `checkjni`	       | A set of tracepoints that record JNI function checks. Equivalent to `-Xcheck:jni` output.                                 |
|**j9vm**	                   | `checkmemory`	   | A set of tracepoints that record memory checks. Equivalent to `-Xcheck:memory` output.                                    |
|**j9vm**	                   | `checkvm`	       | A set of tracepoints that record VM checks. Equivalent to `-Xcheck:vm` output.                                            |
|**j9jit**	                 | `verbose`	       | A set of tracepoints that record JIT compiler configuration and method compilation. Equivalent to `-Xjit:verbose` output. |
|**mt**	                     | `compiledMethods` | A set of tracepoints that record compiled Java methods.                                                                   |
|**mt**	                     | `nativeMethods`	 | A set of tracepoints that record Java native methods.                                                                     |
|**mt**	                     | `staticMethods`	 | A set of tracepoints that record Java static methods.                                                                     |

Here are some examples:

To trace all tracepoints, specify the following command:

    -Xtrace:maximal=all
```
To trace all tracepoints except **j9vrb** and **j9trc**, specify the following command:

    -Xtrace:minimal={all},minimal={!j9vrb,j9trc}
```
To trace all entry and exit tracepoints in **j9bcu**, specify the following command:

    -Xtrace:maximal={j9bcu{entry},j9bcu{exit}}
```
To trace all tracepoints in **j9mm** except tracepoints 20-30, specify the following command:

    -Xtrace:maximal=j9mm,maximal=!j9mm.20-30
```
To trace tracepoints `j9prt.5` through `j9prt.15`, specify the following command:

    -Xtrace:print=j9prt.5-15
```
To trace all **j9trc** tracepoints, specify the following command:

    -Xtrace:count=j9trc
```
To trace all `entry` and `exit` tracepoints, specify the following command:

    -Xtrace:external={all{entry},all{exit}}

### Trace levels

Tracepoints have been assigned levels 0 through 9 that are based on the importance of the tracepoint.

A level 0 tracepoint is the most important. It is reserved for extraordinary events and errors. A level 9 tracepoint is in-depth component detail. To specify a given level of tracing, the `level0` through `level9` keywords are used. You can abbreviate these keywords to l0 through l9. For example, if `level5` is selected, all tracepoints that have levels 0 through 5 are included. Level specifications do not apply to explicit tracepoint specifications that use the **TPNID** keyword.

The level is provided as a modifier to a component specification, for example:

    -Xtrace:maximal={all{level5}}

or

    -Xtrace:maximal={j9mm{L2},j9trc,j9bcu{level9},all{level1}}

In the first example, tracepoints that have a level of 5 or less are enabled for all components. In the second example, all level 1 tracepoints are enabled. All level 2 tracepoints in **j9mm** are enabled. All tracepoints up to level 9 are enabled in **j9bcu**.

<i class="fa fa-pencil-square-o"></i> **Note:** The level applies only to the current component. If multiple trace selection components are found in a trace properties file, the level is reset to the default for each new component.
Level specifications do not apply to explicit tracepoint specifications that use the **TPNID** keyword.

When the not operator is specified, the level is inverted; that is, `!j9mm{level5}` disables all tracepoints of level 6 or greater for the **j9mm** component. The following example enables trace for all components at level 9 (the default), but disables level 6 and higher for the **locking** component, and level 7 and higher for the **storage** component:

    -Xtrace:print={all},print={!j9trc{l5},j9mm{l6}}

Here are some examples:

To count the level zero and level one tracepoints matched, specify the following command:

    -Xtrace:count=all{L1}

To produce maximal trace of all components at level 5 and **j9mm** at level 9, specify the following command:

    -Xtrace:maximal={all{level5},j9mm{L9}}

To trace all components at level 6, but do not trace `j9vrb` at all, and do not trace the `entry` and `exit` tracepoints in the **j9trc** component, specify the following command:

    -Xtrace:minimal={all{l6}},minimal={!j9vrb,j9trc{entry},j9trc{exit}}

## Parameters

Parameters to use with the `-Xtrace` option:

### `buffers`

You can modify the size of the buffers to change how much diagnostic output is provided in a snap dump. This buffer is allocated for each thread that makes trace entries. The following table shows
how this parameter can be set:

| Command                          |   Effect                                                                                                                                               |
|----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
|`-Xtrace:buffers=<size>`          | Creates buffers of the specified `<size>` in *k* (KB) or *m* (MB), allocated as needed to match the rate of trace data generation to the output media. |
|`-Xtrace:buffers=<size>dynamic`   | Creates buffers of the specified `<size>`, allocated as needed to match the rate of trace data generation to the output media.                         |
|`-Xtrace:buffers=<size>nodynamic` | Creates buffers of the specified `<size>`, with a maximum allocation of two buffers per thread.                                                        |

If external trace is enabled, the number of buffers is doubled; that is, each thread allocates two or more buffers. The same buffer size is used for state and exception tracing, but, in this case, buffers are allocated globally. The default is 8 KB per thread.

The `dynamic` and `nodynamic` suboptions have meaning only when tracing to an output file.

<i class="fa fa-pencil-square-o"></i> **Note:** If `nodynamic` is specified, you might lose trace data if the volume of trace data exceeds the bandwidth of the trace output file. Message **UTE115** is issued when the first trace entry is lost, and message **UTE018** is issued when the VM ends.

Here are some command line examples:

To set a buffer size of 2 MB per thread, with dynamic buffering, use:

    -Xtrace:buffers=2m

To limit each thread to 2 trace buffers, each of 128 KB:

    -Xtrace:buffers={128k,nodynamic}

### `count` (tracepoint)

    -Xtrace:count=<tracepoint_specification>             

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

The count option requests that only a count of the selected tracepoints is kept. When the VM ends, all nonzero totals of tracepoints (sorted by tracepoint id) are written to a file, called `utTrcCounters`, in the current directory. This information is useful if you want to determine the overhead of particular tracepoints, but do not want to produce a large amount (GB) of trace data.

For example, to count the tracepoints that are used in the default trace configuration, use the following command:

    -Xtrace:count=all{level1},count=j9mm{gclogger}

### `exception` (tracepoint)

    -Xtrace:exception=<tracepoint_specification>

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

When exception trace is enabled, the trace data is collected in internal buffers that are separate from the normal buffers. These internal buffers can then be written to a snap file or written to the file that is specified in an [`exception.output`](#exceptionoutput) option.

The `exception` option allows low-volume tracing in buffers and files that are distinct from the higher-volume information that `minimal` and `maximal` tracing have provided. In most cases, this information is exception-type data, but you can use this option to capture any trace data that you want.

This form of tracing is channeled through a single set of buffers, as opposed to the buffer-per-thread approach for normal trace. Buffer contention might occur if high volumes of trace data are collected. A difference exists in the `<tracepoint_specification>` defaults for exception tracing;  see [Tracepoint specification](#tracepoint-specification).

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

- The exception trace buffers are intended for low-volume tracing. By default, the exception trace buffers log garbage collection (GC) event tracepoints, see Default tracing. You can send additional tracepoints to the exception buffers or turn off the GC tracepoints. Changing the exception trace buffers alters the contents of the GC History section in any Javadumps.
- When exception trace is entered for an active tracepoint, the current thread ID is checked against the previous caller's thread ID. If it is a different thread, or this is the first call to exception trace, a context tracepoint is put into the trace buffer first. This context tracepoint consists only of the current thread ID, which is necessary because of the single set of buffers for exception trace. (The formatter identifies all trace entries as coming from the Exception trace pseudo thread when it formats exception trace files.)

### `exception.output`

Use exception output to redirect exceptions trace data to a file.

    -Xtrace:exception.output=<filename>[,<size>]

Where:

- `<filename>` is a file name, which is created automatically if it does not exist. Otherwise, it is overwritten. To embed specific values in the file name use any of the following variables: *%d%* (today's date in "
yyyymmdd" format), *%p* (process ID number of the process generating the trace), or *%t%* (time in 24-hour hhmmss format).
- Optionally, `<size>` is a value in megabytes (MB), for example, use *4m* to specify 4 MB. When full, it wraps nondestructively to the beginning. If you do not limit the file, it grows indefinitely, until limited by disk space.

Here are some examples:

Exception trace output goes to file `/u/traces/exception.trc` with no size limit:

    -Xtrace:exception.output=/u/traces/exception.trc,maximal

Exception trace output goes to file `except` and wraps at 2 MB:

    -Xtrace:exception.output={except,2m},maximal

Exception trace output goes to a file whose filename contains today's date in *
yyyymmdd* format (for example, `traceout.20181025.trc`):

    -Xtrace:exception.output=traceout.%d.trc,maximal

Exception trace output goes to a file whose filename contains the number of the process (the PID number) that generated it (for example, `tracefrompid2112.trc`):

    -Xtrace:exception.output=tracefrompid%p.trc,maximal

Exception trace output goes to a file whose filename contains the time in *hhmmss* format (for example, `traceout.080312.trc`):

    -Xtrace:exception.output=traceout.%t.trc,maximal

### `external` (tracepoint)

    -Xtrace:external<tracepoint_specification>

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

The `external` option routes trace data to trace listeners, which are registered by using the JVMTI `RegisterTracePointSubscriber()` and `DeregisterTracePointSubscriber()` APIs.

### `help`

    -Xtrace:help

Displays general trace help

### `iprint` (tracepoint)

    -Xtrace:iprint=<tracepoint_specification>          

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

The `iprint` option is the same as the `print` option, but uses indenting to format the trace.

### `maximal` (tracepoint)

    -Xtrace:maximal=<tracepoint_specification>         

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

When specified, trace data is placed into internal trace buffers that can then be written to a snap file or written to the files that are specified in an output trace option. All associated data is traced.

`minimal` and `maximal` traces are independent from any types that follow them. For example, if the `maximal` option is specified, it does not affect a later option such as `print`.

### `methods`

Using method trace provides a complete and potentially large diagnosis of code paths inside your application and the system classes. Use wild cards and filtering to control method trace so that you can focus on the sections of code that interest you. To specify one or more method specifications, use the following syntax:

    -Xtrace:methods=<method_specification>[,<method_specification>]

The syntax for `<method_specification>` can be further broken down to the following suboptions:

    -Xtrace:methods={[!][*][<package>/]<class>[*],[[*]<method>[*]|[()]]}

Where:

- The delimiter between parts of the package name is a forward slash, "/".
- The **!** in the methods parameter is a NOT operator that allows you to tell the VM not to trace the specified method or methods.
- The parentheses, (), define whether or not to include method parameters in the trace.
- If a method specification includes any commas, the whole specification must be enclosed in braces, for example: `-Xtrace:methods={java/lang/*,java/util/*},print=mt`
- It might be necessary to enclose your command line in quotation marks to prevent the shell intercepting and fragmenting comma-separated command lines, for example: `"-Xtrace:methods={java/lang/*,java/util/*},print=mt"`

To output all method trace information to stderr, use either the `print` or `iprint` suboptions:

    -Xtrace:print=mt,methods=*.*
    -Xtrace:iprint=mt,methods=*.*

The `iprint` suboption prints to stderr with indentation.
To output method trace information in binary format, see the [`output`](#output) option.

Here are some examples:

*Tracing entry and exit of all methods in a given class:* To trace all method entry and exit of the `ReaderMain` class in the default package and the `java.lang.String` class, specify the following command:

    -Xtrace:methods={ReaderMain.*,java/lang/String.*},print=mt

*Tracing entry, exit and input parameters of all methods in a class:* To trace all method entry, exit, and input of the `ReaderMain` class in the default package, specify the following command:

    -Xtrace:methods=ReaderMain.*(),print=mt

*Tracing all methods in a given package:* To trace all method entry, exit, and input of all classes in the package `com.ibm.socket`, specify the following command:

    -Xtrace:methods=com/ibm/socket/*.*(),print=mt

*Multiple method trace:* To trace all method entry, exit, and input in the `Widget` class in the default package and all method entry and exit in the `common` package, specify the following command:

    -Xtrace:methods={Widget.*(),common/*},print=mt

*Using the ! operator:* To trace all methods in the `ArticleUI` class in the default package except those beginning with "get", specify the following command:

    -Xtrace:methods={ArticleUI.*,!ArticleUI.get*},print=mt

*Tracing a specific method in a class:* This example traces entry and exit of the substring method of the `java.lang.String class`. If there is more than one method with the same name, they are all traced. You cannot filter method trace by the signature of the method.

    -Xtrace:print=mt,methods={java/lang/String.substring}

*Tracing the constructor of a class:* This example traces entry and exit of the constructors of the `java.lang.String` class.

    -Xtrace:print=mt,methods={java/lang/String.<init>}

Here is some example output:

    java "-Xtrace:methods={java/lang*.*},iprint=mt" HW
    10:02:42.281*0x9e900      mt.4         > java/lang/J9VMInternals.initialize(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.4          > java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.4           > java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.4            > java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.10           < java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.10          < java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.4           > java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.10          < java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.10         < java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.4          > java/lang/J9VMInternals.initialize(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.281 0x9e900      mt.4           > java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.296 0x9e900      mt.10          < java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.296 0x9e900      mt.10         < java/lang/J9VMInternals.initialize(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.296 0x9e900      mt.4          > java/lang/String.<clinit>()V Compiled static method
    10:02:42.296 0x9e900      mt.4           > java/lang/J9VMInternals.initialize(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.296 0x9e900      mt.4            > java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.296 0x9e900      mt.4             > java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.296 0x9e900      mt.10            < java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.296 0x9e900      mt.4             > java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.328 0x9e900      mt.10            < java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.328 0x9e900      mt.10           < java/lang/J9VMInternals.verify(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.328 0x9e900      mt.4            > java/lang/J9VMInternals.initialize(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.328 0x9e900      mt.10           < java/lang/J9VMInternals.initialize(Ljava/lang/Class;)
                                  V Compiled static method
    10:02:42.328 0x9e900      mt.4            > java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.328 0x9e900      mt.10           < java/lang/J9VMInternals.setInitStatus(Ljava/lang/Class;I)
                                  V Compiled static method
    10:02:42.328 0x9e900      mt.10          < java/lang/J9VMInternals.initialize(Ljava/lang/Class;)
                                  V Compiled static method

The output lines comprise of:

- `0x9e900`, the current `execenv` (execution environment). Because every VM thread has its own `execenv`, you can regard `execenv` as a `thread-id`. All trace with the same `execenv` relates to a single thread.
- The individual tracepoint ID in the **mt** component that collects and emits the data.
- The remaining fields show whether a method is being entered (>) or exited (<), followed by details of the method.

### `minimal` (tracepoint)

    -Xtrace:minimal=<tracepoint_specification>          

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

When specified, trace data is placed into internal trace buffers that can then be written to a snap file or written to the files that are specified in an output trace option. Only the time stamp and tracepoint identifier are recorded. When the trace is formatted, missing trace data is replaced with the characters "???" in the output file.

`minimal` and `maximal` traces are independent from any types that follow them. For example, if the `minimal` option is specified, it does not affect a later option such as `print`.

### `none` (tracepoint)

    -Xtrace:none[=<tracepoint_specification>]

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

`-Xtrace:none` prevents the trace engine from loading if it is the only trace option specified. However, if other `-Xtrace` options are on the command line, it is treated as the equivalent of `-Xtrace:none=all` and the trace engine still loads.

If you specify other tracepoints without specifying `-Xtrace:none`, the tracepoints are added to the default set.

### `output`

Sends trace data to a file, optionally of a specific size and number of generations.


    -Xtrace:output=<filename>[,<size>[,<generations>]]`   

Where:

- `<filename>` is a file name, which is created automatically if it does not exist. Otherwise, it is overwritten. To embed specific values in the file name use any of the following variables: *%d%* (today's date in "
yyyymmdd" format), *%p%* (process ID number of the process generating the trace), or *%t%* (time in 24-hour hhmmss format).
- Optionally, `<size>` is a value in megabytes (MB), for example, use *4m* to specify 4 MB. When full, it wraps to the beginning. If you do not limit the file, it grows indefinitely, until limited by disk space.
- Optionally, `<generations>` is a value 2 through 36. These values cause up to 36 files to be used sequentially as each file reaches its `<size>` threshold. When a file needs to be reused, it is overwritten. If `<generations>` is specified, the filename must contain a **#** (hash, pound symbol), which will be substituted with its generation identifier, the sequence of which is 0 through 9 followed by A through Z.

<i class="fa fa-pencil-square-o"></i> **Note:** When tracing to a file, buffers for each thread are written when the buffer is full or when the VM ends. If a thread has been inactive for a period of time before the VM ends, what seems to be 'old' trace data is written to the file. When formatted, it then seems that trace data is missing from the other threads, but this is an unavoidable side-effect of the buffer-per-thread design. This effect becomes especially noticeable when you use the generation facility, and format individual earlier generations.

Here are some examples:

Trace output goes to file `/u/traces/gc.problem` with no size limit:

    -Xtrace:output=/u/traces/gc.problem,maximal=j9gc

Trace output goes to file `trace`, which will wrap at 2 MB:

    -Xtrace:output={trace,2m},maximal=j9gc

Trace output goes to files `gc0.trc`, `gc1.trc`, and `gc2.trc`, each 10 MB in size:

    -Xtrace:output={gc#.trc,10m,3},maximal=j9gc

Trace output goes to a file, where the filename contains today's date in *
yyyymmdd* format (for example, `traceout.20181025.trc`):

    -Xtrace:output=traceout.%d.trc,maximal=j9gc

Trace output goes to a file whose name contains the number of the process (the PID number) that generated it (for example, `tracefrompid2112.trc`):

    -Xtrace:output=tracefrompid%p.trc,maximal=j9gc

Trace output goes to a file whose name contains the time in *hhmmss* format (for example, `traceout.080312.trc`):

    -Xtrace:output=traceout.%t.trc,maximal=j9gc

### `print` (tracepoint)

    -Xtrace:print=<tracepoint_specification>            

For further information about `<tracepoint_specification>` syntax, see [Tracepoint specification](#tracepoint-specification).

The print option causes the specified tracepoints to be routed to stderr in real time. The VM tracepoints are formatted by using `J9TraceFormat.dat`. The class library tracepoints are formatted by `J9TraceFormat.dat` and `TraceFormat.dat`.

### `properties`

You can use properties files to control trace. A properties file saves typing and allows you to create a library of files that are tailored to solving problems in a particular area.

    -Xtrace:properties[=<filename>]

If `<filename>` is not specified, the VM searches for a default name of **IBMTRACE.properties** in the current directory.

All the options that are in the file are processed in the sequence in which they are stored in the file, before the next option that is obtained through the normal mechanism is processed. Therefore, a command-line property always overrides a property that is in the file.

Here is an example of a properties file:

    minimal=all
    // maximal=j9mm
    maximal=j9shr
    buffers=128k,nodynamic
    output=c:\traces\classloader.trc
    print=tpnid(j9vm.23-25)

The following restrictions apply to the file:

- The file must be a flat ASCII file.
- Nesting is not supported; that is, the file cannot contain a properties option.
- You cannot leave properties that have the form `<name>=<value>` to default if they are specified in the property file; that is, you must specify a value, for example `maximal=all`.
- Do not add white space before, after, or within the trace options.

If any error is found when the file is accessed, VM initialization fails with an explanatory error message and return code.

To use a file `trace.props` stored in the `c:\trc\gc` directory, specify the following command:

    -Xtrace:properties=c:\trc\gc\trace.props


### `resume`

The resume option resumes tracing globally.

    -Xtrace:resume

The `suspend` and `resume` options are not recursive. That is, two suspends that are followed by a single resume cause trace to be resumed.

### `resumecount`

This trace option determines whether tracing is enabled for each thread.

    -Xtrace:resumecount=<count>

If `<count>` is greater than zero, each thread initially has its tracing disabled and must receive `<count>` `resumethis` actions before it starts tracing. This option is used with the [trigger](#trigger) option.

<i class="fa fa-pencil-square-o"></i> **Note:** You cannot use `resumecount` and `suspendcount` together because they use the same internal counter.

The following example starts with all tracing turned off. Each thread starts tracing when it has had three `resumethis` actions performed on it:

    -Xtrace:resumecount=3

### `sleeptime`

You can specify how long the sleep lasts when using the `sleep` trigger action.

    -Xtrace:sleeptime=nnn|aaams|bbbs

Where:

- *nnn* sleeps for *nnn* milliseconds.
- *aaams* sleeps for *aaa* milliseconds.
- *bbbs* sleeps for *bbb* seconds.

The default length of time is 30 seconds. If no units are specified, the default time unit is milliseconds.

### `stackdepth`

Use this option to limit the maximum number of stack frames reported by the `jstacktrace` trace trigger action.

    -Xtrace:stackdepth=<n>

Where `<n>` is the maximum number of stack frames reported.

### `suspend`

    -Xtrace:suspend

Suspends tracing globally for all threads and all forms of tracing but leaves tracepoints activated.

### `suspendcount`

This trace option determines whether tracing is enabled for each thread.

    -Xtrace:suspendcount=<count>

If `<count>` is greater than zero, each thread initially has its tracing enabled and must receive `<count>` `suspendthis` actions before it stops tracing.

<i class="fa fa-pencil-square-o"></i> You cannot use `resumecount` and `suspendcount` together because they both set the same internal counter.

This trace option is for use with the [trigger](#trigger) option.

The following example starts with tracing turned on. Each thread stops tracing when it has had three `suspendthis` actions performed on it:

    -Xtrace:suspendcount=3

### `trigger`

The `trigger` parameter determines when various triggered trace actions occur. Supported actions include turning tracing on and off for all threads, turning tracing on or off for the current thread, or producing various dumps.

    -Xtrace:trigger=<clause>[,<clause>]

This trace option does not control *what* is traced. It controls only whether the information that has been selected by the other trace options is produced as normal or is blocked.

#### Types

Each clause of the `trigger` parameter can be one of the following types:

- a method (`-Xtrace:trigger=method{...}`)
- a tracepoint ID (`-Xtrace:trigger=tpnid{...}`)
- a group (`-Xtrace:trigger=group{...}`)

You can specify multiple clauses of the same type if required, but you do not need to specify all types.

*method*

:        -Xtrace:trigger=method{<methodspec>[,<entryAction>[,<exitAction>[,<delayCount>[,<matchcount>]]]]}

    On entering a method that matches `<methodspec>`, the specified `<entryAction>` is run. On leaving a method that matches `<methodspec>`, the specified `<exitAction>` is run. If you specify a `<delayCount>`, the actions are performed only after a matching `<methodspec>` has been entered that many times. If you specify a `<matchCount>`, `<entryAction>` and `<exitAction>` are performed at most that many times.

    `<methodspec>` is the specification of a Java method, consisting of a class and a method name separated by a dot. For example, specify `HelloWorld.main`. If the class is in a package, the package name must be included, separated by slashes. For example, specify `java/lang/String.getBytes`.

    A wildcard "\*" can be used at the start or end of the class and method names, or both. For example, you can specify `*/String.get*`. To specify a constructor method, use `<init>` as the method name. Method signatures cannot be specified, so a method specification applies to all overloaded methods.

*tracepoint ID*

:        -Xtrace:trigger=tpnid{<tpnid>|<tpnidRange>,<action>[,<delayCount>[,<matchcount>]]}

    On finding the specified active tracepoint ID (`<tpnid>`) or a tracepoint ID) that falls inside the specified `<tpnidRange>`, the specified action is **run**. If you specify a `<delayCount>`, the action is performed only after the VM finds such an active `<tpnid>` that many times. If you specify a `<matchCount>`, `<action>` is performed at most that many times.

*group*

:        -Xtrace:trigger=group{<groupname>,<action>[,<delayCount>[,<matchcount>]]}

    On finding any active tracepoint that is defined as being in trace group `<groupname>`, for example **Entry** or **Exit**, the specified action is **run**. If you specify a `<delayCount>`, the action is performed only after that many active tracepoints from group `<groupname>` have been found. If you specify a `<matchCount>`, `<action>` is performed at most that many times.

#### Actions

Wherever an action (`<action>`, `<entryAction>`, or `<exitAction>`) must be specified in one of the `trigger` parameter clauses, you must select from these options:

| **`<action>`**              | Effect                                                                                                                                                           |
|-----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `abort`                     | Halt the VM.                                                                                                                                                     |
| `ceedump`                   | This action is applicable to z/OS&reg; only. For more information, see z/OS LE CEEDUMPs.                                                                         |
| `coredump`                  | See `sysdump`.                                                                                                                                                   |
| `heapdump`                  | Produce a Heapdump. See [Using Heapdump](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/heapdump.html).                    |
| `javadump`                  | Produce a Javadump. See [Using Javadump](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/javadump.html).                    |
| `jstacktrace`               | Examine the Java stack of the current thread and generate auxiliary tracepoints for each stack frame. The auxiliary tracepoints are written to the same destination as the tracepoint or method trace that triggered the action. You can control the number of stack frames examined with the stackdepth=n option. See the [stackdepth](#stackdepth) option.   |
| `resume`                    | Resume all tracing (except for threads that are suspended by the action of the resumecount property and `Trace.suspendThis()` calls).                            |
| `resumethis`                | Decrement the suspend count for this thread. If the suspend count is zero or less, resume tracing for this thread.                                               |
| `sigsev`                    | Cause a segmentation violation. (Intended for use in debugging.)                                                                                                 |
| `sleep`                     | Delay the current thread for a length of time controlled by the sleeptime option. The default is 30 seconds. See sleeptime option.                               |
| `snap`                      | Snap all active trace buffers to a file in the current working directory. The file name has the format: `Snapnnnn.yyyymmdd.hhmmssth.ppppp.trc`, where *nnnn* is the sequence number of the snap file since VM startup, *yyyymmdd* is the date, *hhmmssth* is the time, and *ppppp* is the process ID in decimal with leading zeros removed.                                                                              |
| `suspend`                   | Suspend all tracing (except for special trace points).                                                                                                           |
| `suspendthis`               | Increment the suspend count for this thread. If the suspend-count is greater than zero, prevent all tracing for this thread.                                     |
| `sysdump` (or `coredump`)   | Produce a system dump. See [Dump agents(`-Xdump:system`)](xdump.md#dump-agents). |

Here are some examples of using the `trigger` option:

To produce a Java dump when a method is entered, specify the following command:

    -Xtrace:trigger=method{java/lang/String.getBytes,javadump}

To produce a system dump when a method is entered, specify the following command:

    -Xtrace:trigger=method{java/lang/String.getBytes,sysdump}

To produce a Java dump when a class constructor is called, specify the following command:
```
"-Xtrace:trigger=method{java/lang/Thread.<init>,javadump}"
```
<i class="fa fa-pencil-square-o"></i> **Note:** This trace option is enclosed in quotation marks to avoid unwanted shell expansion of some of the characters.

To produce a Java dump when a class static initializer is called, specify the following command:
```
"-Xtrace:trigger=method{java/lang/Thread.<clinit>,javadump}"
```
<i class="fa fa-pencil-square-o"></i> **Note:** This trace option is enclosed in quotation marks to avoid unwanted shell expansion of some of the characters.

To produce a Java dump when a method is entered 1000 times and 1001 times, specify the following command:

    -Xtrace:trigger=method{java/lang/String.getBytes,javadump,,1000,2}

To start tracing this thread when it enters any method in `java/lang/String`, and to stop tracing the thread after exiting the method, specify the following command:

    -Xtrace:resumecount=1
-Xtrace:trigger=method{java/lang/String.*,resumethis,suspendthis}

To resume all tracing when any thread enters a method in any class that starts with *error*, specify the following command:

    -Xtrace:trigger=method{*.error*,resume}

To trace (all threads) while the application is active; that is, not starting or shut down. (The application name is `HelloWorld`), specify the following command:

    -Xtrace:suspend,trigger=method{HelloWorld.main,resume,suspend}

To print a Java stack trace to the console when the mycomponent.1 tracepoint is reached, specify the following command:

    -Xtrace:print=mycomponent.1,trigger=tpnid{mycomponent.1,jstacktrace}

To write a Java stack trace to the trace output file when the `Sample.code()` method is called, specify the following command:

    -Xtrace:maximal=mt,output=trc.out,methods={mycompany/mypackage/Sample.code},trigger=method{mycompany/mypackage/Sample.code,jstacktrace}

### `what`

    -Xtrace:what

Shows the current trace settings

## See also

- [Application trace](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/trace_application.html)
- [Using Heapdump](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/heapdump.html)
- [Using Javadump](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/javadump.html)
- [Dump viewer](tool_jdmpview.md)

<!-- ==== END OF TOPIC ==== xtrace.md ==== -->
