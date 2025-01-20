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

# -XX:\[+|-\]AdaptiveGCThreading

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** Currently, this feature is available only with the `gencon` GC policy.

When this option is enabled, the active GC thread count is adjusted for each garbage collection (GC) cycle based on heuristics. That is, when a GC cycle successfully completes, the collector evaluates parallelism using aggregated thread statistics gathered during the completed cycle and projects a new thread count for the next cycle. For example, the thread count might be reduced if it is determined that an unnecessary overhead was incurred as a result of synchronization, lack of work sharing, or CPU availability. Similarly, the thread count may be increased if there's an opportunity to gain benefits from increased parallelism. 

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- This option is ignored when the GC thread count is enforced, for example when using the [`-xgcthreads`](xgcthreads.md) or [`-XX:ParallelGCThreads`](xxparallelgcthreads.md) options.
- By default, the number of GC threads is based on the number of CPUs reported by the operating system. This value is used as the maximum thread count. However, an upper bound can be specified by using [`-xgcmaxthreads`](xgcmaxthreads.md) or [` XX:ParallelGCMaxThreads`](xxparallelgcmaxthreads.md).

## Syntax

        -XX:[+|-]AdaptiveGCThreading

| Setting                            | Effect  | Default                                                                            |
|------------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+AdaptiveGCThreading` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-AdaptiveGCThreading` | Disable |               |

This optimization aims to automatically tune the GC thread count. Manually tuning and setting a thread count can be suboptimal because workloads typically change over the lifetime of an application.

You can check the active thread count value that is used by the garbage collector to complete the cycle by inspecting verbose GC output. The following example shows active thread count being reduced from 8 to 3:

```
<gc-end id="8" type="scavenge" contextid="4" durationms="2.248" usertimems="3.694" systemtimems="1.345" stalltimems="11.003" timestamp="2021-03-12T01:35:10.768" activeThreads="8">
<gc-end id="20" type="scavenge" contextid="16" durationms="7.045" usertimems="6.360" systemtimems="0.955" stalltimems="31.964" timestamp="2021-03-12T01:35:10.777" activeThreads="6">
<gc-end id="32" type="scavenge" contextid="28" durationms="1.943" usertimems="7.112" systemtimems="0.454" stalltimems="6.076" timestamp="2021-03-12T01:35:10.781" activeThreads="5">
<gc-end id="44" type="scavenge" contextid="40" durationms="1.253" usertimems="2.910" systemtimems="0.297" stalltimems="2.416" timestamp="2021-03-12T01:35:10.788" activeThreads="4">
<gc-end id="56" type="scavenge" contextid="52" durationms="1.487" usertimems="3.991" systemtimems="0.447" stalltimems="2.918" timestamp="2021-03-12T01:35:10.790" activeThreads="4">
<gc-end id="68" type="scavenge" contextid="64" durationms="0.400" usertimems="1.002" systemtimems="0.178" stalltimems="0.658" timestamp="2021-03-12T01:35:10.791" activeThreads="4">
<gc-end id="80" type="scavenge" contextid="76" durationms="0.187" usertimems="1.099" systemtimems="0.127" stalltimems="0.112" timestamp="2021-03-12T01:35:10.792" activeThreads="3">
<gc-end id="92" type="scavenge" contextid="88" durationms="0.195" usertimems="0.940" systemtimems="0.114" stalltimems="0.067" timestamp="2021-03-12T01:35:10.796" activeThreads="3">
<gc-end id="104" type="scavenge" contextid="100" durationms="0.277" usertimems="0.899" systemtimems="0.118" stalltimems="0.139" timestamp="2021-03-12T01:35:10.797" activeThreads="3">
```
<!-- ==== END OF TOPIC ==== xxadaptivegcthreading.md ==== -->
