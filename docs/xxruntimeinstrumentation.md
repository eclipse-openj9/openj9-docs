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

# -XX:\[+|-\]RuntimeInstrumentation  

**(AIX&reg;, Linux&reg;, and z/OS&reg; only)**

This option controls the use of the Runtime Instrumentation (RI) facility in the virtual machines that support it.

The RI facility is a feature that is available in POWER8&reg;, zEC12, and later processors that offers hardware support for collecting profiling information at run time. The process uses minimal resources. The use of the RI facility is not enabled by default.

## Syntax

        -XX:[+|-]RuntimeInstrumentation

| Setting                       | Effect  | Default                                                                            |
|-------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+RuntimeInstrumentation` | Enable  |                                                                                    |
| `-XX:-RuntimeInstrumentation` | Disable |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|


:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On Linux, the RI facility on Power 8 and later processors uses the Performance Monitoring Unit (PMU) inside the processor. However, the PMU is also used by system profilers like **oprofile** or **perf**. Due to the current Linux kernel implementation, a user cannot reliably profile a Java&trade; application when RI is enabled. Although this limitation might be addressed in future Linux kernels, for reliable profiling on Power systems that use Linux, the `-XX:-RuntimeInstrumentation` option must be used.


<!-- ==== END OF TOPIC ==== xxruntimeinstrumentation.md ==== -->
