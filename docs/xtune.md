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

# -Xtune

This option is used to configure settings on Eclipse OpenJ9&trade; VM to tune performance. The different `-Xtune` options are used to optimize the VM for a specific performance objective by adjusting a set of internal heuristics.

## Syntax

        -Xtune:<parameter>

## Parameters

| Parameter                       | Effect                                                           |
|---------------------------------|------------------------------------------------------------------|
| [`footprint`](#footprint)       | Minimizes OpenJ9 VM memory usage                                 |
| [`quickstart`](#quickstart)     | Causes the JIT compiler to run with a subset of optimizations    |
| [`throughput`](#throughput)     | Increases resource utilization to maximize throughput            |
| [`virtualized`](#virtualized)   | Reduces OpenJ9 VM CPU consumption when idle                      |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** These parameters are mutually exclusive and if these parameters are used together, the last one that is specified in the command-line takes precedence.

### `footprint`

       -Xtune:footprint

: Optimizes Eclipse OpenJ9 VM function by minimizing OpenJ9 VM memory usage. When `-Xtune:footprint` is specified, the VM adjusts its internal heuristics to reduce memory consumption, possibly at the expense of other performance metrics.

### `quickstart`

       -Xtune:quickstart

: Causes the JIT compiler to run with a subset of optimizations, which can improve the performance of short-running applications.

: `-Xtune:quickstart` is an alias of [`-Xquickstart`](xquickstart.md).

### `throughput`

       -Xtune:throughput

: Increases resource utilization to maximize throughput. When this option is used, the VM will change its internal heuristics to favor throughput over other performance metrics. In particular, if not explicitly provided, the initial heap size (`-Xms`) is set to the maximum heap size value (-`Xmx`). Moreover, the JIT internally sets some options to optimize the code more aggressively.

### `virtualized`

       -Xtune:virtualized

: Optimizes Eclipse OpenJ9 VM function for virtualized environments, such as a cloud, by reducing OpenJ9 VM CPU consumption.

: :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The effect of `-Xtune:virtualized` is maximized when a large shared classes cache (SCC) is provided and the AOT space in the SCC is not capped.

: This option is recommended for CPU-constrained environments, such as those found in cloud deployments that use containers. Internally, the option makes the JIT compiler more conservative with inlining and recompilation decisions, which saves CPU resources. The Garbage Collector also reduces the rate of heap expansion, which reduces the memory footprint. These changes to reduce the amount of CPU that is consumed are at the expense of a small loss in throughput.

: When `-Xtune:virtualized` is used along with the [`-Xshareclasses`](xshareclasses.md) option, the JIT compiler is more aggressive with its use of [AOT-compiled code](aot.md) compared to setting only `-Xshareclasses`. This action provides additional CPU savings during application start-up and ramp-up, but might come at the expense of an additional small loss in throughput.

: For an example of the effect of using this option, see [Measuring the strengths of OpenJDK with Eclipse OpenJ9](https://github.com/eclipse-openj9/openj9-website/blob/master/benchmark/daytrader3.md).

## See also

- [What's new in version 0.21.0](version0.21.md#performance-improvements)
- [What's new in version 0.30.1](version0.30.1.md#new-parameter-throughput-added-to-the-xtune-command-line-option)
- [What's new in version 0.60.0](version0.60.md#new-parameter-footprint-added-to-the-xtune-command-line-option)

<!-- ==== END OF TOPIC ==== xtune.md ==== -->
