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

# -Xgcthreads


Sets the number of threads that the Garbage Collector uses for parallel operations. 

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

- This option forces the thread count and prevents the collector from adjusting the thread count (disables the [Adaptive GC Threading](xxadaptivegcthreading.md) optimization).
- To allow for collector adjustments, [`-xgcmaxthreads`](xgcmaxthreads.md) can be used instead. Rather than forcing the thread count `-Xgcmaxthreads` sets a limit on the thread count.

## Syntax

        -Xgcthreads<number>

## Explanation

The total number of GC threads is composed of one application thread with the remainder being dedicated GC threads. By default, the number is set to `n-1`, where `n` is the number of reported CPUs, up to a maximum of 64. Where SMT or hyperthreading is in place, the number of reported CPUs is larger than the number of physical CPUs. Likewise, where virtualization is in place, the number of reported CPUs is the number of virtual CPUs assigned to the operating system. To set it to a different number, for example 4, use `-Xgcthreads4`. The minimum valid value is 1, which disables parallel operations, at the cost of performance. No advantage is gained if you increase the number of threads to more than the default setting.

On systems running multiple VMs or in LPAR environments where multiple VMs can share the same physical CPUs, you might want to restrict the number of GC threads used by each VM. The restriction helps prevent the total number of parallel operation GC threads for all VMs exceeding the number of physical CPUs present, when multiple VMs perform garbage collection at the same time.


<!-- ==== END OF TOPIC ==== xgcthreads.md ==== -->
