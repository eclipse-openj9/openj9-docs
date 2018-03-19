<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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

# -Xmint / -Xmaxt


Sets the minimum and maximum proportion of time to spend in the garbage collection (GC) process as a percentage of the overall running time that included the last three GC runs. 

- If the percentage of time drops to less than the minimum, the OpenJ9 VM tries to shrink the heap.

- If the percentage of time exceeds the maximum, the OpenJ9 VM tries to expand the heap. 

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span class="sr-only">Restrictions</span> **Restrictions:**  
- This option applies only to GC policies that include stop-the-world (STW) operations, such as `-Xgcpolicy:optthruput`.  
-This option is ignored by the default policy `-Xgcpolicy:gencon`.

## Syntax

| Setting        | Effect                 | Default |
|----------------|------------------------|---------|
|`-Xmint<value>` | Set minimum time in GC | 5       |
|`-Xmaxt<value>` | Set maximum time in GC | 13      |


<!-- ==== END OF TOPIC ==== xmint.md ==== -->
<!-- ==== END OF TOPIC ==== xmaxt.md ==== -->
