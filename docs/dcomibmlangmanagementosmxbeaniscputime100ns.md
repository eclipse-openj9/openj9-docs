<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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

# -Dcom.ibm.lang.management.<br>OperatingSystemMXBean.isCpuTime100ns

Changes the unit of the return value of the `OperatingSystemMXBean.getProcessCpuTime()` method.

## Syntax

        -Dcom.ibm.lang.management.OperatingSystemMXBean.isCpuTime100ns=[true|false]

| Setting | Effect  | Default                                                                            |
|---------|---------|:----------------------------------------------------------------------------------:|
| true    | Enable  |                                                                                    |
| false   | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Default</span> |

## Explanation

The Oracle `java.lang.management` package includes MBean categories such as `Memory`, `OperatingSystem`, and `GarbageCollector`. The OpenJ9 VM  provides additional MXBeans to extend the monitoring and management capabilities. For example, the `OperatingSystemMXBean`, which monitors operating system settings such as physical and virtual memory size, processor capacity, and processor utilization.

The `OperatingSystemMXBean.getProcessCpuTime()` method returns a value in nanoseconds (10<sup>-9</sup> s), for compatibility with the `com.sun.management.OperatingSystemMXBean` and `UnixOperatingSystemMXBean` interfaces.

In earlier VM releases, the return value was in hundreds of nanoseconds. If you want to revert to this behavior, set the `-Dcom.ibm.lang.management.OperatingSystemMXBean.isCpuTime100ns` property to `true`.

The default value for this property is `false`.

## See also

- <i class="fa fa-external-link" aria-hidden="true"></i> [com.ibm.lang.management API documentation](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.api.80.doc/com.ibm.lang.management/index.html)


<!-- ==== END OF TOPIC ==== dcomibmlangmanagementosmxbeaniscputime100ns.md ==== -->
