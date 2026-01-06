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

# -Xconcurrentbackground

Specifies the number of low-priority background threads that are attached to assist the mutator threads in concurrent mark operations. This option maps directly to the HotSpot `-XX:ParallelCMSThreads=N` and `-XX:ConcGCThreads=N` options.

## Syntax

        -Xconcurrentbackground<n>

## Default behavior

The default value is `1`.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This value is reported in the header section of a verbose GC log with the entry `<attribute name="gcthreads Concurrent Mark" value="1" />`.

This option is not supported with the balanced GC policy (`-Xgcpolicy:balanced`) or metronome GC policy (`-Xgcpolicy:metronome`).

## See also

- [`-XX:ParallelCMSThreads`](xxparallelcmsthreads.md)
- [`-XX:ConcGCThreads`](xxconcgcthreads.md)

<!-- ==== END OF TOPIC ==== xconcurrentbackground.md ==== -->
