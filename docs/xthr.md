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

# -Xthr 


## Syntax

        -Xthr:<parameter>

## Parameters

### `AdaptSpin` | `noAdaptSpin`

        -Xthr:AdaptSpin
        -Xthr:noAdaptSpin

: This tuning option is available to test whether performance optimizations are negatively impacting an application.

### `fastNotify` | `noFastNotify`

        -Xthr:fastNotify
        -Xthr:noFastNotify

: When a large number of threads try to acquire a Java&trade; monitor, throughput of an application can be reduced. This issue is known as high contention. If high contention occurs when the Java `wait` and `notify` features are regularly used, you can use `-Xthr:fastNotify` to increase throughput. However, `-Xthr:noFastNotify` is the default setting, because it is faster in all other scenarios.

### `cfsYield` | `noCfsYield` (Linux&reg; only)

        -Xthr:cfsYield
        -Xthr:noCfsYield

: The default value, `cfsYield`, enables threading optimizations for running on Linux with the Completely Fair Scheduler (CFS) in the default mode (`sched_compat_yield=0`). The `noCfsYield` value disables these threading optimizations. You might want to use the `noCfsYield` value if your application uses the `Thread.yield()` method extensively, because otherwise you might see a performance decrease in cases where yielding is not beneficial.

### `minimizeUserCPU`

        -Xthr:minimizeUserCPU

: Minimizes user-mode CPU usage in thread synchronization where possible. The reduction in CPU usage might be a trade-off in exchange for decreased performance.

### `secondarySpinForObjectMonitors` | `noSecondarySpinForObjectMonitors`

        -Xthr:secondarySpinForObjectMonitors
        -Xthr:noSecondarySpinForObjectMonitors

: This tuning option is available to test whether performance optimizations are negatively impacting an application.



<!-- ==== END OF TOPIC ==== xthr.md ==== -->

