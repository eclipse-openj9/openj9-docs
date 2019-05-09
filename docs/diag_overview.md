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

# Diagnostic data and tooling

OpenJ9 contains extensive trace and debugging capabilities to help identify, isolate, and solve run time problems. Various types of dumps are produced by default in response to certain events, such as a GPF fault or an `OutOfMemoryError` exception. You can also trigger the production of dumps by using the `com.ibm.jvm.Dump` API or by specifying `-Xdump` options on the command line.

## Dumps

All dumps are produced by dump agents, which are initialized when the OpenJ9 VM starts. Different dumps target different areas of the runtime environment. If you want to generate a dump to diagnose a particular type of problem, you need to understand what data the dump will provide. The following dumps are typically used for problem diagnosis:   

- [Java dumps](dump_javadump.md) (`-Xdump:java`) contain information that relates to the OpenJ9 VM and the Java&trade; application, such as the operating environment, locks, threads, hooks, shared classes, and class loaders.
- [Heap dumps](dump_heapdump.md) (`-Xdump:heap`) show the content of the Java heap.
- [System dumps](dump_systemdump.md) (`-Xdump:system`) contain a raw process image or address space of an application.  

Other types of dump include binary JIT dumps, stack dumps, and snap dumps. For a complete list of dump agents and the diagnostic data they produce, see [Dump agents](xdump.md#dump-agents).

## Verbose log files

Some components of OpenJ9 can also produce verbose output or log files to assist with problem determination.

- Class data sharing provides a number of `-Xshareclasses` suboptions to provide detailed data about the content of a shared classes cache, cache I/O activity, and information about the Java Helper API (where used). For example, the `-Xshareclasses:printAllStats` suboption lists every class in chronological order with a reference to the location from which it was loaded. For more information, see [-Xshareclasses](xshareclasses.md).   

- Garbage collection operations can be analyzed by producing verbose output from the `-verbose:gc` standard option. This output can be redirected to a file by specifying the `-Xverbosegclog` option. Information can be obtained about GC initialization, *stop-the-world* processing, finalization, reference processing, and allocation failures. Even more granular information can be obtained with the [-Xtgc](xtgc.md) option.

- The JIT compiler provides verbose logging, which records all compiler operations. To find out how to enable logging, read the [JIT troubleshooting](jit.md#troubleshooting) content.

- Class loader operations can be analyzed by producing verbose output from the `-verbose:dynload` standard option, which shows detailed information as each class is loaded by the VM.

## Trace facility

The OpenJ9 trace facility can be used to trace applications, Java methods, or internal JVM operations with minimal impact on performance. Trace is configured by using the [-Xtrace](xtrace.md) command line option, which allows you to control what is traced and when.

Trace data is produced in binary format and must be processed by the OpenJ9 trace formatter to convert it to a readable form. For more information, see [Trace formattter](tool_traceformat.md).

## Debugging tools and interfaces

### Dump viewer tool

Because system dumps are binary files, OpenJ9 provides a dump viewer tool (`jdmpview`) to analyze the contents. This tool can work with dumps from any platforms independently of a system debugger. For more information, see [Dump viewer](tool_jdmpview.md).

### JPDA tools

OpenJ9 is compliant with the Java Platform Debugging Architecture (JPDA), which means you can use any JPDA tool for diagnosis, including [Eclipse JDT Debug](https://www.eclipse.org/eclipse/debug/index.php).

### Java Virtual Machine Tool Interface (JVMTI)

OpenJ9 supports the JVMTI and provides extensions that allow JVMTI tools to obtain diagnostic information or trigger diagnostic operations in the VM. For more information, see [Java Virtual Machine Tool Interface](interface_jvmti.md).

### Diagnostic Tool Framework for Java (DTFJ) Interface

OpenJ9 includes the DTFJ API. Custom applications can be written that use this API to access a wide range of information in a system dump or a Java dump. For more information, see [Diagnostic Tool Framework for Java](interface_dtfj.md).




<!-- ==== END OF TOPIC ==== dump_overview.md ==== -->
