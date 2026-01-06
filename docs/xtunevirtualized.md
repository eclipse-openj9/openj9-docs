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

# -Xtune:virtualized

Optimizes Eclipse OpenJ9&trade; VM function for virtualized environments, such as a cloud, by reducing OpenJ9 VM CPU consumption when idle.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Performance is optimized if there is a large shared classes cache (SCC) and AOT space in the SCC is not capped.

## Syntax

        -Xtune:virtualized

This option is recommended for CPU-constrained environments, such as those found in cloud deployments that use containers. Internally, the option makes the JIT compiler more conservative with inlining and recompilation decisions, which saves CPU resources. The Garbage Collector also reduces the rate of heap expansion, which reduces the memory footprint. These changes to reduce the amount of CPU that is consumed are at the expense of a small loss in throughput.

When `-Xtune:virtualized` is used in conjunction with the [`-Xshareclasses`](xshareclasses.md) option, the JIT compiler is more aggressive with its use of [AOT-compiled code](aot.md) compared to setting only `-Xshareclasses`. This action provides additional CPU savings during application start-up and ramp-up, but comes at the expense of an additional small loss in throughput.

## See also

- For an example of the effect of using this option, see: [Measuring the strengths of OpenJDK with Eclipse OpenJ9](https://github.com/eclipse-openj9/openj9-website/blob/master/benchmark/daytrader3.md)


<!-- ==== END OF TOPIC ==== xtunevirtualized.md ==== -->
