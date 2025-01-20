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


# New to Eclipse OpenJ9?

The Eclipse OpenJ9&trade; virtual machine (VM) implements the [Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/index.html). Most Java applications should run on an OpenJDK that contains the OpenJ9 VM without changing anything. However, because it is an independent implementation there are some differences compared to the HotSpot VM, which is the default OpenJDK VM and is also included in an Oracle JDK.

## Command-line options

Although OpenJ9 implements its own command-line interface, many HotSpot options are recognized and accepted by the VM for compatibility. Any `-XX:` options that are not recognized by the VM are ignored by default, which prevents an application failing to start. You can turn off this behavior with the [-XX:-IgnoreUnrecognizedXXColonOptions](xxignoreunrecognizedxxcolonoptions.md) option.

For a list of compatible options, equivalent options, and options that need to be set for compatibility, see [Switching to OpenJ9](cmdline_migration.md).


## Garbage collection policies

Eclipse OpenJ9 has a number of garbage collection (GC) policies designed around different types of applications and workloads. By default, OpenJ9 uses the Generational Concurrent (`gencon`) GC policy, which is best suited for transactional applications that have many short-lived objects. The policy aims to minimize GC pause times without compromising throughput. If you are using Java 8, the `gencon` policy is similar to the HotSpot CMS (concurrent mark sweep). If you are using Java 11, the OpenJ9 balanced (`balanced`) policy is most similar to the default HotSpot policy.

If you have a different type of workload, you might want to select a different GC policy. For details about the available policies and when to choose them, see [Garbage collection](gc.md).


## Operational tooling

If you are a Java application developer or you are responsible for managing large server or desktop deployments of a Java runtime environment, you probably use a number of tools for monitoring, management, and troubleshooting. Because OpenJ9 is an independent implementation, it has evolved with its own approach for these areas and, in some cases, its own unique tools.

In other cases, tools have been added for compatibility with the reference implementation, but these tools might differ in behavior from equivalent tools in HotSpot. For a list of these tools, see [Switching to OpenJ9](tool_migration.md) in the Tools section.

### Dumps, logs, and trace files

OpenJ9 contains extensive trace and debugging capabilities to help identify, isolate, and solve run time problems.

- **Dump files:** Various types of dump are produced by default in response to certain events and can also be triggered for a whole range of events by using the `com.ibm.jvm.Dump` API or by specifying `-Xdump` options on the command line. Dumps include [Java dumps](dump_javadump.md), [heap dumps](dump_heapdump.md), [system dumps](dump_systemdump.md), JIT dumps, stack dumps, and snap dumps (tracepoint data). For more information, see the [`-Xdump`](xdump.md) option.
- **Verbose log files:** Some components of OpenJ9 can also produce verbose output or log files to assist with problem determination, including [class data sharing](https://www.eclipse.org/openj9/docs/xshareclasses/#printallstats-cache-utility), [garbage collection](https://www.eclipse.org/openj9/docs/gc/#troubleshooting), and the [JIT compiler](https://www.eclipse.org/openj9/docs/xjit/#verbose).
- **Trace files:** The OpenJ9 implementation contains extensive tracepoints used to log information and exceptional conditions, with minimal impact on performance. Some tracepoints are enabled by default; others can be enabled on demand. For more information, see the [`-Xtrace`](xtrace.md) option for tracing Java applications and the VM, and the [`-Xtgc`](xtgc.md) option for tracing garbage collection.

If you are familiar with using HotSpot as part of an Oracle JDK or OpenJDK, you probably make use of the monitoring and diagnostic tools that are provided with the VM. OpenJ9 has implemented a different approach to providing similar data; rather than running a number of different tools to obtain a different piece of information, the Java dump file provides a comprehensive set of information in one place. You can find the following information in an OpenJ9 Java dump:

- The system the VM is running on and the resources available.
- The Java execution environment, including the options set from the command line.
- The native memory used by the VM, broken down by VM component.
- Memory usage in the VM for the object heap and internal VM structures, such as the JIT code cache.
- Lock operations that protect shared resources during runtime.
- Java threads, native threads, and stack traces.
- Hook interfaces, for performance analysis.
- Details about the shared classes cache, if used.
- Detailed information about classloaders, together with a list of libraries and classes that are loaded.

For more information, see [Java dump](dump_javadump.md).

### Tools

OpenJ9 provides support for a number of monitoring and diagnostic tools that can be found in the [Eclipse marketplace](https://marketplace.eclipse.org/). Each tool provides a graphical user interface to help you visualize data and, in some cases, can provide tuning or debugging recommendations.

- [**Health Center:**](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-health-center) Provides real-time monitoring of running applications with minimal overhead over the network. You can monitor a whole range of operations including, class loading, CPU usage, GC heap and pause times, I/O activity, lock contention, method trace, native memory usage, profiling, and live threads. For more information, read the [Health Center documentation](https://www.ibm.com/support/knowledgecenter/en/SS3KLZ/com.ibm.java.diagnostics.healthcenter.doc/homepage/plugin-homepage-hc.html).
- [**Garbage Collection Memory Vizualizer (GCMV):**](https://marketplace.eclipse.org/content/ibm-monitoring-and-diagnostic-tools-garbage-collection-and-memory-visualizer-gcmv) Plots GC and native memory data over time. You can view and save data as a report, raw log, tabulated data, or in graphical format. The tool helps to diagnose problems such as memory leaks with data presented in various visual formats for analysis. Tuning recommendations are also provided. For more information, read the [GCMV documentation](https://www.ibm.com/support/knowledgecenter/en/SS3KLZ/com.ibm.java.diagnostics.visualizer.doc/homepage/plugin-homepage-gcmv.html).
- [**Eclipse Memory Analyzer&trade;:**](https://marketplace.eclipse.org/content/memory-analyzer-0) Examines the Java object heap to help find memory leaks or reduce memory consumption. Support is available for OpenJ9 via the DTFJ interface (Install from the Eclipse Help menu; Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and Diagnostic Tools > Diagnostic Tool Framework for Java). More information about Eclipse MAT can be found on the [project website page](https://www.eclipse.org/mat/).

If you are familiar with using HotSpot as part of an Oracle JDK or OpenJDK, the Java VisualVM utility is functionally similar to Health Center. Most of the other tools provided with HotSpot are not officially supported, but equivalent functionality is available in OpenJ9 through command-line
options, dump agents, and AttachAPI.

### Interfaces

OpenJ9 provides the following interfaces, which can be used for monitoring and diagnostic purposes:

- **[JVMTI](interface_jvmti.md):** OpenJ9 supports the Java Virtual Machine Tool Interface (JVMTI) and provides extensions that allow JVMTI tools to obtain diagnostic information or trigger diagnostic operations in the VM. For more information about this interface, see [Java Virtual Machine Tool Interface](interface_jvmti.md).
- **[DTFJ interface](interface_dtfj.md):** The Diagnostic Tool Framework for Java (DTFJ) API allows custom applications to be written that can access a wide
range of information in a system dump or a Java dump. DTFJ can be used with the Eclipse Memory Analyzer Toolkit (MAT) to examine the Java object heap
for memory leaks and to reduce memory consumption. For more information about DTFJ, see [Diagnostic Tool Framework for Java](interface_dtfj.md).
- **[java.lang.management API](api-langmgmt.md):** OpenJ9 provides MXBean additions and extensions to this standard API, which enables you to use tools such as JConsole to monitor and manage your Java applications. For more information, see [Language management interface](interface_lang_management.md).

If you are familiar with using HotSpot as part of an Oracle JDK or OpenJDK, you might make use of certain `com.sun.management` interfaces. Although OpenJ9 implements some of these interfaces, a few are specific to the HotSpot VM. The following table indicates alternative classes or mechanisms that you can use for equivalent function in OpenJ9:

| HotSpot-specific classes   | Alternatives for OpenJ9  |
|----------------------------|--------------------------|
|`HotSpotDiagnosticMXBean`   | `OpenJ9DiagnosticsMXBean` (for heap dumps)|
|`MissionControl`            | Use Health Center        |
|`MissionControlMXBean`      | Use Health Center        |
|`ThreadMXBean`              | `JvmCpuMonitorMXBean` (for thread time)    |
|`VMOption`                  | OpenJ9 Java dump (option `-Xdump:java`) |
|`DiagnosticCommandMBean`    | None                     |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** OpenJ9 implements the following `com.sun.management` interfaces: `GarbageCollectorMXBean`, `GarbageCollectionNotificationInfo`, `GcInfo`, `OperatingSystemMXBean`, `UnixOperatingSystemMXBean`.

For information about OpenJ9 application programming interfaces, see [API documentation](api-overview.md).

## Other differences

This topic describes the differences between the HotSpot VM and the Eclipse OpenJ9 VM. Therefore, if you are currently using an OpenJDK with the default HotSpot VM and you want to switch to using an OpenJDK with the OpenJ9 VM, these are the only differences you might be concerned about. If however, you are using an Oracle JDK, you might want to learn about differences between other components that make up an Oracle JDK or an OpenJDK from the AdoptOpenJDK community. For more information, read the [Migration guide](https://adoptopenjdk.net/migration.html).
