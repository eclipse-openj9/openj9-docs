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

# -Dil8n.vs

<!-- Not yet supported by OpenJ9 -->

This system property enables awareness of Unicode Ideographic Variation Sequence (IVS) to draw characters, except in peered components.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restriction:** This option is supported only for Japanese.

## Syntax

        -Dil8n.vs=true

| Setting      | Effect  |  Default                                                                           |
|--------------|---------|:----------------------------------------------------------------------------------:|
| true         | Enable  |                                                                                    |
|              | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |


## Explanation

The behavior depends on the font specified. If the font supports IVS, and has a glyph based on the combination of a base character and a variation selector character, an accurate glyph can be picked up. If not, the base character is displayed and the variation selector character is ignored. Because this option changes the behavior of the font drawing engine, the option is disabled by default. When disabled, a variation selector is displayed as an undefined character.



<!-- ==== END OF TOPIC ==== dil8nvs.md ==== -->
