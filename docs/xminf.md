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

# `-Xminf` / `-Xmaxf`


Specifies the minimum and maximum proportion of the heap that must remain free after a global garbage collection (GC) cycle.

If the free space is above or below these limits, the Eclipse OpenJ9&trade; VM attempts to adjust the heap size so that: `-Xminf` &le; free space &le; `-Xmaxf`.

## Syntax

| Setting        | Effect                 | Default |
|----------------|------------------------|---------|
|`-Xminf<value>` | Set minimum free space | 0.3     |
|`-Xmaxf<value>` | Set maximum free space | 0.6     |

The value range is 0.0 - 1.0.

- For the `balanced` GC policy, these values apply only to the non-eden space part of the heap. The non-eden heap resizing decision is made by observing both `-Xmint`/`-Xmaxt` and `-Xminf`/`-Xmaxf`. Free memory in eden space is not considered for `-Xminf`/`-Xmaxf` purposes.
- For the `gencon` GC policy, the values apply only to the tenure part of the heap and only at global GC points.
- For the `optthruput` and `optavgpause` GC policies, these values apply to the whole heap at every GC point.
- This option cannot be used with the metronome GC policy (`-Xgcpolicy:metronome`) because the heap is always fully expanded.

## See also

- [Heap expansion and contraction](allocation.md#expansion-and-contraction)
- [Garbage collection policies](gc.md)



<!-- ==== END OF TOPIC ==== xminf.md ==== -->
<!-- ==== END OF TOPIC ==== xmaxf.md ==== -->
