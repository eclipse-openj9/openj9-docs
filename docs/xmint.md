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

# -Xmint / -Xmaxt


Sets the minimum and maximum proportion of time to spend in the garbage collection (GC) process as a percentage of the overall running time that included the last three GC runs. Therefore, the time spent in the GC process includes time spent in global mark phase and global GC operations but excludes partial garbage collection pauses because the latter apply only to the eden space.

- If the percentage of time drops to less than the minimum, the Eclipse OpenJ9&trade; VM tries to shrink the heap.
- If the percentage of time exceeds the maximum, the VM tries to expand the heap.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- This option applies only to GC policies that include stop-the-world (STW) operations, such as `-Xgcpolicy:optthruput`.  


## Syntax

| Setting        | Effect                 | Default for balanced policy|Default for other policies|
|----------------|------------------------|:---------:|:-------------------------------:|
|`-Xmint<value>` | Set minimum time in GC | 0.02    |0.05                          |
|`-Xmaxt<value>` | Set maximum time in GC | 0.05   |0.13                           |

- For the `balanced` GC policy, the values apply only to the non-eden space part of the heap. The non-eden heap resizing decision is made by observing both `-Xmint`/`-Xmaxt` and `-Xminf`/`-Xmaxf`.
- For the `gencon` GC policy, the values apply only to the tenure part of the heap.
- For the `optthruput`, and `optavgpause` GC policies, these values apply to the whole heap.
- This option cannot be used with the `metronome` GC policy (`-Xgcpolicy:metronome`) because the heap is always fully expanded.


## See also

- [Heap expansion and contraction](allocation.md#expansion-and-contraction)
- [Garbage collection policies](gc.md)

<!-- ==== END OF TOPIC ==== xmint.md ==== -->
<!-- ==== END OF TOPIC ==== xmaxt.md ==== -->
