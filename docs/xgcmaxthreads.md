<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# -Xgcmaxthreads

Specifies the maximum number of threads that the garbage collector can use for parallel operations. This option behaves in the same way as [`-Xgcthreads`](xgcthreads.md) but does not enforce a fixed thread count, which allows the garbage collector to adjust the thread count when used with the [`-XX:+AdaptiveGCThreading`](xxadaptivegcthreading.md) option.

## Syntax

        -Xgcmaxthreads<number>

Where `<number>` is the maximum number of threads that can be used for parallel operations.

<!-- ==== END OF TOPIC ==== xgcmaxthreads.md ==== -->
