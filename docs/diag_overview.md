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

# Diagnostic data and tooling

Eclipse OpenJ9&trade; contains a broad range of diagnostic capabilities to help identify, isolate, and solve run time problems. These capabilities include dump files, verbose logs, and trace files, which are supported by a variety of diagnostic tools and interfaces.

## Dumps

Various types of dumps are produced by default in response to certain events, such as a GPF fault or an `OutOfMemoryError` exception. You can also trigger the production of dumps by using the `com.ibm.jvm.Dump` API or by specifying `-Xdump` options on the command line.

All dumps are produced by dump agents, which are initialized when the OpenJ9 VM starts. Different dumps target different areas of the runtime environment. If you want to generate a dump to diagnose a particular type of problem, you need to understand what data the dump will provide. The following dumps are typically used for problem diagnosis:   

- [Java dumps](dump_javadump.md) (`-Xdump:java`) contain information that relates to the OpenJ9 VM and the Java&trade; application, such as the operating environment, locks, threads, hooks, shared classes, and class loaders.
- [Heap dumps](dump_heapdump.md) (`-Xdump:heap`) show the content of the Java heap.
- [System dumps](dump_systemdump.md) (`-Xdump:system`) contain a raw process image or address space of an application.  

Other types of dump include binary JIT dumps, stack dumps, and snap dumps. For a complete list of dump agents and the diagnostic data they produce, see [Dump agents](xdump.md#dump-agents).

## Verbose log files

Some components of OpenJ9 can also produce verbose output or log files to assist with problem determination.

- Class data sharing provides a number of `-Xshareclasses` suboptions to provide detailed data about the content of a shared classes cache, cache I/O activity, and information about the Java Helper API (where used). For example, the `-Xshareclasses:printAllStats` suboption lists every class in chronological order with a reference to the location from which it was loaded. For more information, see [-Xshareclasses](xshareclasses.md).   

- Garbage collection operations can be analyzed by producing verbose output from the `-verbose:gc` standard option. This output can be redirected to a file by specifying the `-Xverbosegclog` option. Information can be obtained about GC initialization, *stop-the-world* processing, finalization, reference processing, and allocation failures. Even more granular information can be obtained with the [-Xtgc](xtgc.md) option. For more information, see [verbose GC logs](vgclog.md).

- The JIT compiler provides verbose logging, which records all compiler operations. To find out how to enable logging, read the [JIT troubleshooting](jit.md#troubleshooting) content.

- Class loader operations can be analyzed by producing verbose output from the `-verbose:dynload` standard option, which shows detailed information as each class is loaded by the VM.


## Trace files

The OpenJ9 trace facility can be used to trace applications, Java methods, or internal JVM operations with minimal impact on performance. Trace is configured by using the [-Xtrace](xtrace.md) command line option, which allows you to control what is traced and when.

Trace data is produced in binary format and must be processed by the OpenJ9 trace formatter to convert it to a readable form. For more information, see [Trace formatter](tool_traceformat.md).

## Diagnostic tools

A number of diagnostic tools are available with OpenJ9 to assist with the analysis of dump and trace files.

### Dump extractor

The dump extractor (`jpackcore`) supports a full analysis of core files on specific platforms by collecting key files from a system and packaging them into an archive along with a core dump. This archive file is extremely useful when reporting issues to the OpenJ9
community, helping to ensure a faster analysis and turnaround. For more information, see
[Dump extractor](tool_jextract.md).

### Dump viewer

Because system dumps are binary files, OpenJ9 provides a dump viewer tool (`jdmpview`) to analyze the contents. This tool can work with dumps from any platforms independently of a system debugger. For more information, see [Dump viewer](tool_jdmpview.md).

### Eclipse marketplace tools

OpenJ9 provides support for a number of monitoring and diagnostic tools that can be found in the [Eclipse marketplace](https://marketplace.eclipse.org/). Each tool provides a graphical user interface to help you visualize data and, in some cases, can provide tuning or debugging recommendations.

- [**Health Center:**](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-health-center) Provides real-time monitoring of running applications with minimal overhead over the network. You can monitor a whole range of operations including, class loading, CPU usage, GC heap and pause times, I/O activity, lock contention, method trace, native memory usage, profiling, and live threads. For more information, read the [Health Center documentation](https://www.ibm.com/support/knowledgecenter/en/SS3KLZ/com.ibm.java.diagnostics.healthcenter.doc/homepage/plugin-homepage-hc.html).
- [**Garbage Collection Memory Vizualizer (GCMV):**](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv) Plots GC and native memory data over time. You can view and save data as a report, raw log, tabulated data, or in graphical format. The tool helps to diagnose problems such as memory leaks with data presented in various visual formats for analysis. Tuning recommendations are also provided. For more information, read the [GCMV documentation](https://www.ibm.com/support/knowledgecenter/en/SS3KLZ/com.ibm.java.diagnostics.visualizer.doc/homepage/plugin-homepage-gcmv.html).
- [**Eclipse Memory Analyzer&trade;:**](https://marketplace.eclipse.org/content/memory-analyzer-0) Examines the Java object heap to help find memory leaks or reduce memory consumption. Support is available for OpenJ9 via the DTFJ interface (Install from the Eclipse Help menu; Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and Diagnostic Tools > Diagnostic Tool Framework for Java). More information about Eclipse MAT can be found on the [project website page](https://www.eclipse.org/mat/).

If you are familiar with using HotSpot as part of an Oracle JDK or OpenJDK, the Java VisualVM utility is functionally similar to Health Center.

### HotSpot-compatible tools

A number of tools are available for compatibility with the reference implementation. These tools are independently implemented by
OpenJ9 but have similar functions, allowing users to migrate more easily. The available tools are listed in the Tools section.  

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If you are already familiar with tools that are provided with HotSpot, see [Switching to OpenJ9](tool_migration.md), which explains some of the differences you might encounter when using OpenJ9.

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) JDK Flight Recorder

JDK Flight Recorder (JFR) is an integral component of OpenJ9. The main function of JFR is to run in conjunction with the running Java application, collect application level and JVM level metrics, and record these metrics in the form of a JFR record. JFR records are binary files, and hence another tool, such as JDK Mission Control (JMC), is used to extract, interpret, and visualize the data.

For more information, see [`-XX:[+|-]FlightRecorder`](xxflightrecorder.md). ![End of content that applies only to Java 11 and later](cr/java_close.png)

### Option builder

OpenJ9 contains an extensive set of command-line options to assist with problem diagnosis. Certain options are complex, containing
many sub-options with numerous parameters. Whilst these offer a great degree of flexibility, the syntax can be difficult to construct.
Option builder tools are available that provide a simple graphical user interface to help you construct your command-line argument.
For more information, see [Option builder](tool_builder.md).

### Trace formatter

The trace formatter tool converts binary trace point data in a trace file into a readable format for analysis. For more information, see
[Trace formatter](tool_traceformat.md).

## Interfaces

### JVM tool interface (JVMTI)

OpenJ9 supports the Java Virtual Machine Tool Interface (JVMTI) and provides extensions that allow JVMTI tools to obtain diagnostic information or trigger diagnostic operations in the VM. For more information, see [Java Virtual Machine Tool Interface](interface_jvmti.md).

### DTFJ Interface

OpenJ9 includes the Diagnostic Tool Framework for Java (DTFJ) API. Custom applications can be written that use this API to access a wide range of information in a system dump or a Java dump. DTFJ can be used with the Eclipse Memory Analyzer tool (MAT) to examine the Java object heap for memory leaks and to reduce memory consumption. For more information, see [Diagnostic Tool Framework for Java](interface_dtfj.md).

### Language Management interface

OpenJ9 provides MXBean additions and extensions to the standard `java.lang.management` API, which enables you to use tools such as JConsole to monitor and manage your Java applications. For more information, see [Language management interface](interface_lang_management.md).

### JPDA tools

OpenJ9 is compliant with the Java Platform Debugging Architecture (JPDA), which means you can use any JPDA tool for diagnosis, including [Eclipse JDT Debug](https://www.eclipse.org/eclipse/debug/index.php).




<!-- ==== END OF TOPIC ==== dump_overview.md ==== -->
