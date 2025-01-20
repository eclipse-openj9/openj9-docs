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

# -Xcompactgc / -Xnocompactgc

These options enable or disable full compaction on system and global garbage collection (GC) activities.

## Syntax

| Setting        | Action                  |
|----------------|-------------------------|
|`-Xcompactgc`   | Enable full compaction  |
|`-Xnocompactgc` | Disable full compaction |


## Default behavior

If a compaction option is not specified, the garbage collector compacts based on a series of triggers. These triggers attempt to compact only when it is beneficial to the future performance of the VM.

These options are not applicable to the following GC policies:

- balanced GC policy (`-Xgcpolicy:balanced`): compaction is always enabled.
- metronome GC policy (`-Xgcpolicy:metronome`): compaction is not supported.

## See also

- [GC compact operation](gc_overview.md#gc-compact-operation)


<!-- ==== END OF TOPIC ==== xcompactgc.md ==== -->
<!-- ==== END OF TOPIC ==== xnocompactgc.md ==== -->
