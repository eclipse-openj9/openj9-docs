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

# -Xclassgc / -Xnoclassgc

These options enable or disable the garbage collection (GC) of storage that is associated with Java classes that are no longer being used by the Eclipse OpenJ9&trade; VM.

When enabled, GC occurs only on class loader changes. To always enable dynamic class unloading regardless of class loader changes, set [`-Xalwaysclassgc`](xalwaysclassgc.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Disabling class GC is not recommended because unlimited native memory growth can occur, which can lead to out-of-memory errors.

## Syntax

| Setting      | Action     | Default                                                                            |
|--------------|------------|:----------------------------------------------------------------------------------:|
|`-Xclassgc`   | Enables dynamic class unloading on demand  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|`-Xnoclassgc` | Disables dynamic class unloading |                                                                                    |

These options can be used with all OpenJ9 GC policies.

## See also

- [`-Xalwaysclassgc`](xalwaysclassgc.md)

<!-- ==== END OF TOPIC ==== xclassgc.md ==== -->
<!-- ==== END OF TOPIC ==== xnoclassgc.md ==== -->
