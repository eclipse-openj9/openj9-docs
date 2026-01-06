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

# -XX:ParallelGCMaxThreads

This option specifies the maximum number of threads that can be used during parallel operations of the garbage collector. Unlike [`-XX:ParallelGCThreads`](xxparallelgcthreads.md), this option does not enforce a thread count, but can be used to allow the garbage collector to adjust the number of parallel GC threads, if used with the [Adaptive GC Threading](xxadaptivegcthreading.md) option.

## Syntax

        -XX:ParallelGCMaxThreads=<number>

Where `<number>` is the maximum number of threads that can be used for parallel operations. 

This option is directly mapped to [`-Xgcmaxthreads`](xgcmaxthreads.md).

<!-- ==== END OF TOPIC ==== xxparallelgcmaxthreads.md ==== -->
