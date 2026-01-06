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

# -XX:[+|-]IProfileDuringStartupPhase

This option enables or disables the data collection by the interpreter profiler during a VM startup.

## Syntax

        -XX:[+|-]IProfileDuringStartupPhase

| Setting                          | Effect  |
|----------------------------------|---------|
|`-XX:+IProfileDuringStartupPhase` | Enable  |
|`-XX:-IProfileDuringStartupPhase` | Disable |

## Explanation

The VM collects interpreter profiling data that is used to optimize methods at the time of JIT compilation. To reduce the CPU usage of the interpreter profiler during the startup phase, the VM stores such profiling data in the shared classes cache. During startup, the VM uses heuristics to determine when to turn on data collection and when to rely on the data that was collected by a previous VM and stored in the cache. You can overrule the heuristics and control the collection of the profiling information during the startup phase by using the `-XX:[+|-]IProfileDuringStartupPhase` option.

- If the `-XX:+IProfileDuringStartupPhase` option is specified, the VM always collects the latest interpreter profiling information during startup and stores the information in the shared classes cache. By using this option, the quality of the generated code might increase, leading to better long-term throughput. But, this option might increase the startup time of applications.
- If the `-XX:-IProfileDuringStartupPhase` option is specified, the VM does not collect interpreter profiling information during the startup phase. Use this option if the shared classes cache already contains sufficient interpreter profiling information and therefore, you don't have to collect new information and affect the startup time.

## See also

- [The JIT compiler](jit.md)


<!-- ==== END OF TOPIC ==== xxiprofileduringstartupphase.md ==== -->
