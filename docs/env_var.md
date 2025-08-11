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

# Environment variables

Although the Eclipse OpenJ9&trade; virtual machine (VM) recognizes many environment variables, most are superseded by command-line arguments. Use command-line arguments rather than environment variables, which are retained only for compatibility.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Environment variables are overridden by command-line arguments.

## Finding and setting environment variables

To show the current environment, run:

-   `set` (Windows&trade;)
-   `env` (AIX&reg;, Linux&reg;, and macOS&reg;)
-   `set` (z/OS&reg;)

To show a particular environment variable, run:

-   `echo %ENVNAME%` (Windows)
-   `echo $ENVNAME` (AIX, Linux, macOS, and z/OS)

Use values exactly as shown in the documentation. The names of environment variables are case-sensitive in AIX, Linux, macOS, and z/OS.

To set the environment variable **LOGIN\_NAME** to *Fred*, run:

-   `set LOGIN_NAME=Fred` (Windows)
-   `export LOGIN_NAME=Fred` (AIX/Linux/macOS: ksh or bash shells)
-   `setenv LOGIN_NAME Fred` (csh shells)

These variables are set only for the current shell or command-line session.

If you are setting multiple values for an environment variable in a list:

-   On AIX, Linux, macOS, and z/OS the separator is typically a colon (:).
-   On Windows the separator is typically a semicolon (;).

## General options

General VM environment variables are shown in the following table:

|Environment&nbsp;variable                | Usage information                                                                                |
|-----------------------------------------|--------------------------------------------------------------------------------------------------|
|`OPENJ9_JAVA_COMMAND_LINE`               | This variable is set by the VM after it starts. Using this variable, you can find the command-line parameters set when the VM started. See [`-XX:[+|-]OpenJ9CommandLineEnv`](xxopenj9commandlineenv.md) for more information. |
|`OPENJ9_JAVA_OPTIONS=<option>`           | Set this variable to store default Java options, including **-X**, **-D**, or **-verbose:gc** style options. For example, **-Xms256m -Djava.compiler**. Any options set are overridden by equivalent options that are specified when Java is started. This variable does not support certain options, see the list in [-Xoptionsfile](xoptionsfile.md). If you specify the name of a trace output file either directly, or indirectly, by using a properties file, the output file might be accidentally overwritten if you run utilities such as the trace formatter, dump extractor, or dump viewer. To avoid this problem, add %d, %p or %t to the trace file names. See [-Xtrace:output](xtrace.md)</a>.<br/>:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The equivalent to `OPENJ9_JAVA_OPTIONS`, `IBM_JAVA_OPTIONS` is deprecated and will be removed in a future release. |
|`JAVA_FONTS=<list of directories>`       | Set this environment variable to specify the font directory. Setting this variable is equivalent to setting the property `java.awt.fonts` on Windows operating systems, and `sun.java2d.fontpath` on other operating systems. |
|`_JAVA_OPTIONS=<option>`                 | Set this variable to add Java options to the end of the command line. Supported options and trace file issues are the same as for `OPENJ9_JAVA_OPTIONS`. |


## Dump agent options

The preferred mechanism for controlling the production of dumps is by using the `-Xdump` option. However, these legacy environment variables are preserved and can still be used. The following table describes dump agent options:

| Environment Variable                                            | Usage Information                                              |
|-----------------------------------------------------------------|----------------------------------------------------------------|
| `JAVA_DUMP_OPTS`                                                | Used to control the conditions under which dumps are produced. |

If you set agents for a condition by using the `JAVA_DUMP_OPTS` environment variable, default dump agents for that condition are disabled; however, any `-Xdump` options that are specified on the command line are used.

The `JAVA_DUMP_OPTS` environment variable uses the following syntax:


    JAVA_DUMP_OPTS="ON<condition>(<agent>[<count>],<agent>[<count>]),
    ON<condition>(<agent>[<count>],...),...)"

Where:

-   `<condition>` is one of the following values:
    -   `ANYSIGNAL`
    -   `DUMP`
    -   `ERROR`
    -   `INTERRUPT`
    -   `EXCEPTION`
    -   `OUTOFMEMORY`
-   `<agent>` is one of the following values:
    -   `ALL`
    -   `NONE`
    -   `JAVADUMP`
    -   `SYSDUMP`
    -   `HEAPDUMP`
    -   `CEEDUMP` (z/OS specific)
-   `<count>` is the number of times to run the specified agent for the specified condition. This value is optional. By default, the agent runs every time that the condition occurs.

`JAVA_DUMP_OPTS` is parsed by taking the leftmost occurrence of each condition, so duplicates are ignored. The following setting produces a system dump for the first error condition only:

    ONERROR(SYSDUMP[1]),ONERROR(JAVADUMP)

Also, the `ONANYSIGNAL` condition is parsed before all others, so

    ONINTERRUPT(NONE),ONANYSIGNAL(SYSDUMP)

has the same effect as

    ONANYSIGNAL(SYSDUMP),ONINTERRUPT(NONE)


If the `JAVA_DUMP_TOOL` environment variable is set, that variable is assumed to specify a valid executable name and is parsed for replaceable fields, such as *%pid*. If *%pid* is detected in the string, the string is replaced with the VM's own process ID. The tool that is specified by `JAVA_DUMP_TOOL` is run after any system dump or heap dump is taken, before anything else.

The dump settings are applied in the following order. Settings later in the list take precedence:

1.  Default VM dump behavior.
2.  `-Xdump` command-line options that specify `-Xdump:<type>:defaults`, see [OpenJ9 default options](openj9_defaults.md).
3.  `DISABLE_JAVADUMP`, `IBM_HEAPDUMP`, and `IBM_HEAP_DUMP` environment variables.
4.  `IBM_JAVADUMP_OUTOFMEMORY` and `IBM_HEAPDUMP_OUTOFMEMORY` environment variables.
5.  `JAVA_DUMP_OPTS` environment variable.
6.  Remaining `-Xdump` command-line options.

Setting `JAVA_DUMP_OPTS` affects only those conditions that you specify. Actions on other conditions are unchanged.

### Signal mapping

When setting the `JAVA_DUMP_OPTS` environment variable, the mapping of operating system signals to the "condition" is shown in the following table:

| Condition        | z/OS                                                      |  Windows                | Linux, macOS, and AIX                                     |
|------------------|-----------------------------------------------------------|-------------------------|---------------------------------------------------|
| **EXCEPTION**    | SIGTRAP, SIGILL, SIGSEGV, SIGFPE, SIGBUS, SIGSYS, SIGXFSV | SIGILL, SIGSEGV, SIGFPE | SIGTRAP, SIGILL, SIGSEGV, SIGFPE, SIGBUS, SIGXFSV |
| **INTERRUPT**    | SIGINT, SIGTERM, SIGHUP                                   | SIGINT, SIGTERM         | SIGINT, SIGTERM, SIGHUP                           |
| **ERROR**        | SIGABRT                                                   | SIGABRT                 | SIGABRT                                           |
| **DUMP**         | SIGQUIT                                                   | SIGBREAK                | SIGQUIT                                           |

## Java dump options

The preferred mechanism for controlling the production of Java dumps is by using the `-Xdump:java` option. However, these legacy environment variables are preserved and can still be used.

| Environment Variable                   | Usage Information                                                                                                                 |
|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| `DISABLE_JAVADUMP=[TRUE|FALSE]`        | Setting `DISABLE_JAVADUMP` to `TRUE` is the equivalent of using `-Xdump:java:none` and stops the default production of Java dumps.  |
| `IBM_JAVACOREDIR=<directory>`          | The default location into which the Java dump is written. On z/OS, the `_CEE_DMPTARG` environment variable is used instead.       |
| `IBM_JAVADUMP_OUTOFMEMORY=[TRUE|FALSE]`| By setting this environment variable to `FALSE`, you disable Java dumps for an out-of-memory exception. When not set, a Java dump is generated when an out-of-memory exception is thrown but not caught and handled by the application. Set to `TRUE` to generate a dump when an out-of-memory exception is thrown, even if it is handled by the application. Set to `FALSE` to disable Java dumps for an out-of-memory exception.                             |
| `TMPDIR=<directory>`                   | This variable specifies an alternative temporary directory. This directory is used only when Java dumps and Heap dumps cannot be written to their target directories, or the current working directory. The default is `/tmp` (`C:\temp` for Windows).<br><br>From the 0.51.0 release onwards, in all the JDK versions except for the IBM Java 8, OpenJDK is used to initialize the `java.io.tmpdir` system property. Because of this change, even if the `TMPDIR=<directory>` variable is specified, it is ignored when setting the `java.io.tmpdir` system property. You can modify this system property directly by using the `-Djava.io.tmpdir=<directory>` command-line option.  |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** You can use the dump agent `JAVA_DUMP_OPTS` variable to control the conditions under which Java dumps are produced.

## Heap dump options

The preferred mechanism for controlling the production of Java dumps is by using the `-Xdump:heap` option. However, these legacy environment variables are preserved and can still be used.

| Environment Variable                  | Usage Information                                                                                                          |
|---------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
|`IBM_HEAPDUMP=[TRUE|FALSE]`            | Setting this option to `TRUE` enables heap dump production by using signals.                                               |
|`IBM_HEAP_DUMP=[TRUE|FALSE]`           |  Setting this option to `TRUE` enables heap dump production by using signals.                                               |
|`IBM_HEAPDUMPDIR=<directory>`          | The default location into which the heap dump is written. On z/OS, the `_CEE_DMPTARG` environment variable is used instead.                             |
|`IBM_HEAPDUMP_OUTOFMEMORY=[TRUE|FALSE]`| Controls the generation of a heap dump when an out-of-memory exception is thrown. When not set, a heap dump is generated when an out-of-memory exception is thrown but not caught and handled by the application. Set to TRUE to generate a dump when an out-of-memory exception is thrown, even if it is handled by the application. Set to FALSE to disable heap dump for an out-of-memory exception.                                                   |
|`IBM_JAVA_HEAPDUMP_TEST`               | Use this environment variable to cause the VM to generate both PHD and text versions of heap dumps. Equivalent to `opts=PHD+CLASSIC` on the `-Xdump:heap` option.|
|`IBM_JAVA_HEAPDUMP_TEXT`               | Use this environment variable to cause the VM to generate a text (human readable) Heap dump. Equivalent to `opts=CLASSIC` on the `-Xdump:heap` option.|
|`TMPDIR=<directory>`                   | This variable specifies an alternative temporary directory. This directory is used only when Java dumps and heap dumps cannot be written to their target directories, or the current working directory. The default is `/tmp` (`C:\temp` for Windows).<br><br>From the 0.51.0 release onwards, in all the JDK versions except for the IBM Java 8, OpenJDK is used to initialize the `java.io.tmpdir` system property. Because of this change, even if the `TMPDIR=<directory>` variable is specified, it is ignored when setting the `java.io.tmpdir` system property. You can modify this system property directly by using the `-Djava.io.tmpdir=<directory>` command-line option.  |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** You can use the dump agent `JAVA_DUMP_OPTS` variable to control the conditions under which Heap dumps are produced.

## Other diagnostic options

The following table lists other environment variables that can be set for diagnostic purposes:

| Environment variable                          | Usage Instructions                                                                                              |
|-----------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
|`IBM_COREDIR=<directory>`                      | Set this variable to specify an alternative location for system dumps, JIT dumps, and snap trace. On z/OS, `_CEE_DMPTARG` is used instead for snap trace, and transaction dumps are written to TSO according to `JAVA_DUMP_TDUMP_PATTERN`. On Linux and macOS, the dump is written to the directory that is specified directory by the operating system before being moved to the specified location.|
|`IBM_JAVA_ABEND_ON_FAILURE=Y` (z/OS only)      | This setting tells the Java launcher to mark the Task Control Block (TCB) with an abend code if the OpenJ9 VM fails to load or is terminated by an uncaught exception. By default, the Java launcher does not mark the TCB.|
|`JAVA_DUMP_TDUMP_PATTERN=<string>` (z/OS only) | The specified `<string>` is passed to IEATDUMP to use as the data/set name for the Transaction Dump. The default `<string>` is `%uid.JVM.TDUMP.%job.D%y%m%d.T%H%M%S` (31-bit) or `%uid.JVM.%job.D%y%m%d.T%H%M%S.X&amp;DS` (64-bit), where `%uid` is found from the C code fragment shown in **Notes**.|
|`JAVA_THREAD_MODEL=<string>`                   | `<string>` can be defined as one of the following values: *NATIVE* (all threads are created as `_MEDIUM_WEIGHT`), *HEAVY* (all threads are created as `_HEAVY_WEIGHT`), *MEDIUM* (same as *NATIVE*), or *NULL*. The default is *NATIVE*. |
|`IBM_XE_COE_NAME=<value>`                      | Set this variable to generate a system dump when the specified exception occurs. The value that is supplied is the package description of the exception; for example, `java/lang/InternalError`. A Signal 11 is followed by a JVMXE message and then the VM ends.|
|`JAVA_PLUGIN_TRACE=TRUE`                       | When this variable is set to `TRUE` or 1, a Java plug-in trace is produced for the session when an application runs. Traces are produced from both the Java and Native layer. By default, this variable is set to `FALSE`, so that a Java plug-in trace is not produced. |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**
C code fragment to discover `%uid` for `JAVA_DUMP_TDUMP_PATTERN=<string>`:

```
pwd = getpwuid(getuid());
pwd->pw_name;
```

## Deprecated JIT options

The following table describes deprecated environment variables for the JIT compiler:

| Environment Variable              | Usage Information                                                   |
|-----------------------------------|---------------------------------------------------------------------|
| `IBM_MIXED_MODE_THRESHOLD`        | Use `-Xjit:count=<value>` instead of this variable.                 |
| `JAVA_COMPILER`                   | Use `-Djava.compiler=<value>` instead of this variable.             |


<!-- ==== END OF TOPIC ==== env_var.md ==== -->
