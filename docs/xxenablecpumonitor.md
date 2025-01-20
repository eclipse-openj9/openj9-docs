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

# -XX:\[+|-\]EnableCPUMonitor

This option relates to the information about the CPU usage of thread categories that is available with the `com.ibm.lang.management.JvmCpuMonitorMXBean` application programming interface.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This option might not be supported in subsequent releases.

## Syntax

        -XX:[+|-]EnableCPUMonitor

| Setting                 | Effect  | Default                                                                            |
|-------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+EnableCPUMonitor` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-EnableCPUMonitor` | Disable |                                                                                    |

## Explanation

The `-XX:+EnableCPUMonitor` option enables CPU monitoring, which allows a JMX bean to track CPU usage on a per thread basis and attributes the usage against different categories. For more information, see the `JvmCpuMonitorMXBean` interface in the `com.ibm.lang.management` API documentation.

To turn off CPU monitoring, set the `-XX:-EnableCPUMonitor` option on the command line.

## See also

- [-XX:\[+|-\]ReduceCPUMonitorOverhead](xxreducecpumonitoroverhead.md)

<!-- ==== END OF TOPIC ==== xxenablecpumonitor.md ==== -->
