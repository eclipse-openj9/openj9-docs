<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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

# `-Xminf` / `-Xmaxf`


Specifies the minimum and maximum proportion of the heap that must remain free after a global garbage collection (GC) cycle.

If the free space is above or below these limits, the OpenJ9 VM attempts to adjust the heap size so that: `-Xminf` &le; free space &le; `-Xmaxf`.

## Syntax

| Setting        | Effect                 | Default |
|----------------|------------------------|---------|
|`-Xminf<value>` | Set minimum free space | 0.3     |
|`-Xmaxf<value>` | Set maximum free space | 0.6     |

The value range is 0.0 - 1.0.

- For the `gencon` GC policy, the values apply only to the Tenure part of the heap and only at global GC points.
- For the `optthruput` and `optavgpause` GC policies, these values apply to the whole heap at every GC point.

## See also

- [Heap shrinkage](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_heapshrinkage.html)
- [Heap expansion](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_heapexpansion.html)


<!-- ==== END OF TOPIC ==== xminf.md ==== -->
<!-- ==== END OF TOPIC ==== xmaxf.md ==== -->
