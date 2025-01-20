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

# -XX:\[+|-\]PerfTool

This option enables or disables JIT support for the `perf` tool without affecting the existing `Xjit` options.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** Since this option creates a file that is used by the Linux&reg; system profiler, `perf`, it applies only to Linux.

## Syntax

        -XX:[+|-]PerfTool

| Setting                 | Effect  | Default                                                                            |
|-------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+PerfTool` | Enable  |   |
| `-XX:-PerfTool` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>                                                          |

## Explanation

The `-XX:+PerfTool` option is a stand-alone alternative for the existing `-Xjit:perfTool` option. If multiple `-Xjit` options exist, only the last option takes effect. Therefore, if the `-Xjit:perfTool` option is added later, it overrides the existing `-Xjit` options. To overcome this issue, the `XX:+PerfTool` option enables JIT support for the `perf` tool without having any impact on the existing `Xjit` options. This enabling option creates a `/tmp/perf-<pid>.map` file that is used by the `perf` tool to map the samples in the JIT-compiled code to the corresponding Java method names.

To disable the JIT support for the `perf` tool, set the `-XX:-PerfTool` option on the command line.

## See also

- [`xjit`](xjit.md)

<!-- ==== END OF TOPIC ==== xxperftool.md ==== -->
