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

# MBeans and MXBeans

MBeans and MXBeans can be used to provide information about the state of a Java&trade; virtual machine (JVM). Additional MXBeans are provided with OpenJ9 that extend the monitoring and management capabilities.

MXBeans are a generalized variant of MBeans. Because MXBeans are constructed by using only a pre-defined set of data types, MXBeans can be referenced and used more easily by applications such as JConsole.

For information about using JConsole, see [https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html](https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html), particularly the section [Monitoring and Managing MBeans](https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html#gdeap).

See also the reference information at [https://docs.oracle.com/javase/6/docs/technotes/tools/share/jconsole.html](https://docs.oracle.com/javase/6/docs/technotes/tools/share/jconsole.html).

For more information about the standard platform MBeans and MXBeans, see the Oracle API documentation for the `java.lang.management package` at [https://docs.oracle.com/javase/8/docs/api/java/lang/management/package-summary.html](https://docs.oracle.com/javase/8/docs/api/java/lang/management/package-summary.html).

## OpenJ9 MXBeans

OpenJ9 provides further MXBeans to extend the monitoring and management capabilities. These are summarized in the following sections. For API information about these MXBeans, see the [Application programming reference](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.api.80.doc/com.ibm.lang.management/index.html?view=kc).

#### `GarbageCollectorMXBean`
For monitoring garbage collection operations. You can obtain data about GC collection times, heap memory usage, number of compactions, and the amount of total freed memory.

#### `GuestOSMXBean`
: For monitoring guest operating system statistics, as seen by the host hypervisor. This data includes memory and processor usage.

#### `HypervisorMXBean`
: This MXBean provides information to determine whether the operating system is running in a virtualized environment, such as a virtual machine or LPAR. A method is also provided to determine the vendor of the hypervisor, if the operating system is running in a virtualized environment.

#### `JVMCpuMonitorMXBean`
: For monitoring CPU consumption, including CPU time that is used by system and application threads, with a further breakdown to show time that is spent on garbage collection and JIT compilation processes.

#### `MemoryMXBean`
: For monitoring memory usage, including data about maximum and minimum heap sizes, and shared class caches sizes.

#### `MemoryPoolMXBean`
: For monitoring the usage of the memory pool, where supported.

#### `OpenJ9DiagnosticsMXBean`
: For configuring dump options and dynamically triggering dump agents.

#### `OperatingSystemMXBean`
: For monitoring operating system settings such as physical and virtual memory size, processor capacity, and processor utilization.

#### `ProcessorMXBean`
: For monitoring processor resources. You can get the number of CPUs (for example, physical, online, or bound CPUs) and set the number of CPUs that the process should be restricted to.

#### `RuntimeMXBean`
: For monitoring the runtime environment, including data about the average CPU load, the Java process ID, and whether the VM is idle.

#### `ThreadMXBean`
: For obtaining native thread IDs.

#### `UnixOperatingSystemMXBean`
: For monitoring statistics on operating systems such as AIX or Linux. You can obtain data about memory (physical and virtual), file descriptors, processors, processor usage, and hardware (model and emulation).



<!-- ==== END OF TOPIC ==== interface_mxbeans.md ==== -->
