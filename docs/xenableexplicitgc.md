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

# ‑Xenableexplicitgc / ‑Xdisableexplicitgc


The options enable or disable garbage collection (GC) when calls are made to `System.gc()`.

## Syntax

| Setting               | Effect     | Default                                                                            |
|-----------------------|------------|:----------------------------------------------------------------------------------:|
| `-Xenableexplicitgc`  | Enable explicit GC calls  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-Xdisableexplicitgc` | Disable explicit GC calls |                                                                                    |

## Explanation

Although it is possible to programmatically trigger a global GC by calling `System.gc()`, performance can be adversely affected by halting the application before it is really necessary. Use this option to prevent the VM responding to application requests for a GC cycle.

The default for all Eclipse OpenJ9&trade; GC policies is `-Xenableexplicitgc` except for [`-Xgcpolicy:nogc`](xgcpolicy.md#nogc), where the default is `-Xdisableexplicitgc`.

These options can be used with all OpenJ9 GC policies.

<!-- ==== END OF TOPIC ==== xenableexplicitgc.md ==== -->
<!-- ==== END OF TOPIC ==== xdisableexplicitgc.md ==== -->
