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

# Language management interface

Eclipse OpenJ9&trade; provides MXBean extensions to the standard `java.lang.management` API, which can be used to monitor and manage the Java&trade; virtual machine.
These extensions provide access to information about the state of the OpenJ9 VM and the environment in which it is running. The following
tables list the MXBeans by package and describe the monitoring or management capabilities.


**Package: `com.ibm.lang.management`**

|  MXBean  | Description                                                                                                  |
|---------------------------|--------------------------------------------------------------------------------------------------------------|
| `GarbageCollectorMXBean`    | Discovers Garbage Collection (GC) operations (collection times, compactions, heap memory usage, and freed memory). |
| `JvmCpuMonitorMXBean`       | Discovers CPU consumption by category (GC, JIT, or other threads).                                             |
| `MemoryMXBean`              | Discovers memory usage (minimum and maximum heap sizes, and shared classes cache sizes).             |
| `MemoryPoolMXBean`          | Discovers memory pool usage for specific GC policies.                                                         |
| `OperatingSystemMXBean`     | Discovers information about the operating system (memory, CPU capacity/utilization).                         |
| `RuntimeMXBean`             | Discovers information about the runtime environment (CPU load, Java process ID, and VM state)                |
| `ThreadMXBean`              | Discovers information about native thread IDs.                                                                |
| `UnixOperatingSystemMXBean` | Discovers information for Unix operating systems (memory, file descriptors, processors, processor usage, and hardware)|


**Package: `com.ibm.virtualization.management`**


| MXBean | Description                                                                                                  |
|---------------------------|--------------------------------------------------------------------------------------------------------------|
| `GuestOSMXBean`             | Discovers CPU and memory statistics of a virtual machine or logical partition as seen by the Hypervisor.       |
| `HypervisorMXBean`          | Discovers whether the operating system is running on a hypervisor and provides information about the hypervisor.|


**Package: `openj9.lang.management`**

| MXBean | Description                                                                                                  |
|---------------------------|--------------------------------------------------------------------------------------------------------------|
| `OpenJ9DiagnosticsMXBean`   | Configures and dynamically triggers dump agents.                                                              |


For more information about using these MXBeans, read the API documentation. For Java 8, see the [OpenJ9 Language Management API documentation](api-langmgmt.md). <!-- Link to API -->


<!-- ==================================================================================================== -->


<!-- ==== END OF TOPIC ==== interface_lang_management.md ==== -->
