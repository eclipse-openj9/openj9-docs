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

# -Xmine / -Xmaxe


Set the minimum and maximum amounts by which the garbage collector expands the heap.

## Syntax

| Setting         | Default                                     |
|-----------------|---------------------------------------------|
|`-Xmine<size>`   | 1 MB                                        |
|`-Xmaxe<size>`   | 0 (unlimited expansion)                     |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## Explanation

Typically, the garbage collector expands the heap by the amount required to restore the free space to 30% (or the amount specified by [`-Xminf`](xminf.md)).

If heap expansion is required:

- `-Xmine` forces the expansion to be at least the specified value. For example, `-Xmine10M` sets the expansion size to a minimum of 10 MB.  
- `-Xmaxe` limits the expansion to the specified value. For example `-Xmaxe50M` prevents expansion by more than 50 MB. (`-Xmaxe0` allows unlimited expansion.)

For the `gencon` GC policy, the values apply only to the tenure part of the heap. For the `balanced`, `optthruput`, and `optavgpause` GC policies, these values apply to the whole heap. This option cannot be used with the metronome GC policy (`-Xgcpolicy:metronome`) because the heap is always fully expanded.

## See also

- [Heap expansion and contraction](allocation.md#expansion-and-contraction)


<!-- ==== END OF TOPIC ==== xmine.md ==== -->
<!-- ==== END OF TOPIC ==== xmaxe.md ==== -->
