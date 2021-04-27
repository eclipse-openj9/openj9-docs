<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# What's new in version 0.27.0

The following new features and notable changes since v 0.26.0 are included in this release:

- [New `-XX:[+|-]AdaptiveGCThreading` option added](#new-xx-adaptivegcthreading-option-added)
- [Improved time zone information added to Java dump files](#improved-time-zone-information-added-to-java-dump-files)

## Features and changes


### New `-XX:[+|-]AdaptiveGCThreading` option added

To optimize performance, you can now enable adaptive threading to automatically tune the number of active parallel garbage collection (GC) threads. When enabled, the GC thread count is dynamically adjusted from collection cycle to cycle to account for changes in the workload and consider the available resources. When parallel workloads decrease, less threads can be used, reducing the overhead and freeing up resources for other processing activities.

Use the [`-xgcmaxthreads`](xgcmaxthreads.md) option with the [`-XX:+AdaptiveGCThreading`](xxadaptivegcthreading.md) option to specify a thread count limit.

### Improved time zone information added to Java dump files

To help with troubleshooting, additional time zone information is added to Java dump files. Two new fields are included, the date and time in UTC (`1TIDATETIMEUTC`) and the time zone according to the local system (`1TITIMEZONE`). For more information, see the [Java dump `TITLE` section](dump_javadump.md#title).

<!-- ==== END OF TOPIC ==== version0.27.md ==== -->
