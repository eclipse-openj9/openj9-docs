<!--
* Copyright (c) 2017, 2026 IBM Corp. and others
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

# Trace formatter (`traceformat`)


The trace formatter is a Java&trade; program that converts binary trace point data in a trace file to a readable form. The formatter requires the `TraceFormat.dat` and `J9TraceFormat.dat` files, which contain the formatting templates. The formatter produces a file that contains header information about the VM that produced the binary trace file, a list of threads for which trace points were produced, and the formatted trace points with their time stamp, thread ID, trace point ID, and trace point data.

## Syntax

To use the trace formatter on a binary trace file type:

        traceformat <input_file>  [<output_file>] <parameters>

Where `<input_file>` is the name of the binary trace file to be formatted, and `<output_file>` is the name of the output file. If you do not specify an output file, the output file is called `input_file.fmt`.

The size of the heap that is needed to format the trace is directly proportional to the number of threads present in the trace file. For large numbers of threads the formatter might run out of memory, generating the error `OutOfMemoryError`. In this case, increase the heap size by using the `-Xmx` option.

## Parameters

The following `<parameters>` are available with the trace formatter:

|  Option                                  | Explanation                                                                                                               |
|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| `-datfile=<file1.dat>[,<file2.dat>]`     | A comma-separated list of trace formatting data files. By default, the following files are used:<ul><li> $JAVA_HOME/lib/J9TraceFormat.dat</li><li>$JAVA_HOME/lib/TraceFormat.dat </li></ul>       |
| `-format_time=yes|no`                    | Specifies whether to format the time stamps into human readable form. The default is `yes`.                               |
| `-help`                                  | Displays usage information.                                                                                               |
| `-indent`                                | Indents trace messages at each `Entry` trace point and outdents trace messages at each `Exit` trace point. The default is not to indent the messages. |
| `-summary`                               | Prints summary information to the screen without generating an output file.                                               |
| `-threads=<thread id>[,<thread id>]...`  | Filters the output for the given thread IDs only. thread id is the ID of the thread, which can be specified in decimal or hex (0x) format. Any number of thread IDs can be specified, separated by commas. |
| `-timezone=+|-HH:MM`                     | Specifies the offset from UTC, as positive or negative hours and minutes, to apply when formatting time stamps.           |
| `-verbose`                               | Output detailed warning and error messages, and performance statistics.                                                   |

## Examples

The following example shows output from running the trace formatter command:

```
    C:\test>traceformat sample.trc
    Writing formatted trace output to file sample.trc.fmt
    Processing 0.4921875Mb of binary trace data
    Completed processing of 6983 tracepoints with 0 warnings and 0 errors
```

The formatted trace output looks similar to the following extract, which is truncated to show the key areas of information:

```
    Trace Summary

    Service level:
    JRE 1.8.0 Windows 7 amd64-64 build  (pwa6480sr2-20150624_06(SR2))

    JVM startup options:
    -Xoptionsfile=c:\build\pwa6480sr2-20150624\sdk\lib\compressedrefs\options.default
    ....

    Processor information:
    Arch family:         AMD64
    Processor Sub-type:  Opteron
    Num Processors:      8
    Word size:           64

    Trace activation information::
    FORMAT=c:\build\pwa6480sr2-20150624\sdk\lib;.
    MAXIMAL=all{level1}
    EXCEPTION=j9mm{gclogger}
    MAXIMAL=all{level2}
    output=sample

    Trace file header:
    JVM start time: 08:58:35.527000000
    Generations:    1
    Pointer size:   8

    Active threads
    ....
    0x000000000f155f00  Attach API wait loop
    0x000000000f18b200  Thread-1
    0x000000000f190200  Thread-3


     Trace Formatted Data

    Time (UTC)          Thread ID          Tracepoint ID Type   Tracepoint Data
    08:58:35.527291919 *0x000000000f010500 j9trc.0       Event  Trace engine initialized for VM = 0x3ad4d0
    08:58:35.527349836  0x000000000f010500 j9prt.0       Event  Trace engine initialized for module j9port
    08:58:35.527354040  0x000000000f010500 j9thr.0       Event  Trace engine initialized for module j9thr
    08:58:35.529409621 *0x000000000f01eb00 j9trc.5       Event  Thread started VMthread = 0xf01eb00, name = (unnamed thread), nativeID = 0x24a798
    ....
    08:58:35.536134516  0x000000000f010500 j9vm.1        Entry >Create RAM class from ROM class 0x3cab680 in class loader 0x3042338
    08:58:35.536136384  0x000000000f010500 j9vm.80       Event  ROM class 0x3cab680 is named java/lang/Object
    08:58:35.536200373  0x000000000f010500 j9vm.2        Exit  <Created RAM class 0xf03ef00 from ROM class 0x3cab680
```

<!-- ==== END OF TOPIC ==== xss.md ==== -->
