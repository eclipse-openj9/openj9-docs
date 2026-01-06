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

# -XX:[+|-]CpuLoadCompatibility

This option enables or disables the OpenJDK behavior of the `getProcessCpuLoad()` and `getCpuLoad()` methods in OpenJ9.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Before OpenJDK 14, the `getCpuLoad()` method name was `getSystemCpuLoad()`. Because OpenJDK 14 is no longer supported, the relevant LTS release where this change is visible is OpenJDK 17 and later.

## Syntax

        -XX:[+|-]CpuLoadCompatibility

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+CpuLoadCompatibility` |  Enable   |                             |
| `-XX:-CpuLoadCompatibility` |  Disable  |   :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>         |

## Explanation

The `getProcessCpuLoad()` method in the `com.sun.management.OperatingSystemMXBean` class returns the recent CPU usage value for the Java virtual machine process, while the `getCpuLoad()` method returns the recent CPU usage value for the whole system. When these methods were called in OpenJ9 for the first time, these methods were returning `-1` to indicate that the recent CPU usage is not available. It was difficult to identify whether the reason for the `-1` value was an error or because the call was the first call and therefore, no recent CPU usage was available.

In OpenJDK, `getProcessCpuLoad()` returns `0` value on the first call, while `getCpuLoad()` collects two internal samples so that a valid CPU usage value can be returned. This `0` return value on the first call and the two internal samples make it easier to differentiate between the first call behavior and an error that needs further investigation.

The `-XX:+CpuLoadCompatibility` option is used to enable the OpenJDK behavior of the `getProcessCpuLoad()` and `getCpuLoad()` methods in OpenJ9.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The `-XX:[+|-]CpuLoadCompatibility` option is applicable to all platforms except z/OS&reg;. On z/OS, the CPU load that is reported by the `getCpuLoad` method is obtained directly from the system control blocks, without the need to collect samples. The `-XX:[+|-]CpuLoadCompatibility` option has no effect on z/OS, and a valid CPU usage value is always returned whatever be the setting of this option.

## See also

- [What's new in version 0.43.0](version0.43.md#new-xx-cpuloadcompatibility-option-added)
- [What's new in version 0.56.0](version0.56.md#change-in-the-getcpuload-method-return-value-based-on-the-platform)

<!-- ==== END OF TOPIC ==== xxcpuloadcompatibility.md ==== -->
