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

# -XX:CheckpointGCThreads

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

At the VM startup, you can set the number of threads that the garbage collector uses for parallel operations ([`-Xgcthreads`](xgcthreads.md)). At the time of taking the checkpoint, the `-XX:CheckpointGCThreads` option reduces the number of garbage collection (GC) threads that was set at startup.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This option takes effect only when `-XX:+EnableCRIUSupport` is enabled.

## Syntax

        -XX:CheckpointGCThreads=<number>

| Setting               | Value                            | Default                                                  |
|-----------------------|-----------------------------------|:---------------------------------------------------------:|
| `<number>` | Greater than 0 and less than or equal to the number of GC threads at the VM startup |  4           |

If the `<number>` is greater than the number of GC threads at startup, the VM ignores this option.

## Explanation

When taking a checkpoint, the VM reduces the number of GC threads that was specified at the startup and makes it equal to the thread count specified in the `-XX:CheckpointGCThreads` option. Since restoring threads adds latency to the overall VM restore time, you can minimize the restore time by reducing the GC thread count. After restoring from the checkpoint, the VM increases the GC thread count. The VM checks the following settings to determine the new GC thread count at the time of restore:

- `-Xgcthreads` set at restore
- Default thread count at restore
- Checkpoint GC threads

If you have specified the GC thread count at restore in the `-Xgcthreads` option and the count is greater than the checkpoint GC threads, the VM increases the thread count to the GC thread count set at restore.

If you have not specified the GC thread count in the `-Xgcthreads` option or if the thread count is less than the checkpoint GC threads, then the VM determines the default thread count (as explained in the [`-Xgcthreads`](xgcthreads.md#explanation) topic) at restore. If the default thread count is greater than the checkpoint thread count, then the VM increases the thread count to the default thread count.

If the default thread count is lesser than the checkpoint thread count, then the thread count at restore time is same as the checkpoint thread count.

## See also

- [CRIU support](criusupport.md)
- [What's new in version 0.38.0](version0.38.md##technical-preview-of-criu-support)

<!-- ==== END OF TOPIC ==== xxcheckpointgcthread.md ==== -->
