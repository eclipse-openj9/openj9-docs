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

# -XX:ParallelGCThreads

This Oracle HotSpot option specifies the number of threads that are used during parallel operations of the default garbage collector. This option is recognized by Eclipse OpenJ9&trade; and provided for compatibility.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

This option enforces the thread count and cannot be used with the [`-XX:+AdaptiveGCThreading`](xxadaptivegcthreading.md) option, which enables the garbage collector to adjust the number of parallel threads based on heuristics. If you want to use [`-XX:+AdaptiveGCThreading`](xxadaptivegcthreading.md), use [`-XX:ParallelGCMaxThreads`](xxparallelgcmaxthreads.md) instead of `-XX:ParallelGCThreads`.

## Syntax

        -XX:ParallelGCThreads=<number>

Where `<number>` is the number of threads that are used for parallel operations. 

Within OpenJ9 this option is directly mapped to [`-Xgcthreads`](xgcthreads.md).

<!-- ==== END OF TOPIC ==== xxparallelgcthreads.md ==== -->
