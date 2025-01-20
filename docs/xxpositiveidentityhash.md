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

# -XX:\[+|-\]PositiveIdentityHash

Eclipse OpenJ9&trade; allows both positive and negative identity hashcodes (`System.identityHashCode` / `Object.hashCode`). This is problematic for programs that incorrectly assume hashcodes can only be positive.

When enabled, this option limits identity hash codes to non-negative values.

Because limiting identity hash codes to non-negative values can have an impact on the performance of hash-intensive operations, this option is not enabled by default.

## Syntax

        -XX:[+|-]PositiveIdentityHash

| Setting                     | Effect  | Default                                                                        |
|-----------------------------|---------|:------------------------------------------------------------------------------:|
| `-XX:+PositiveIdentityHash` | Enable  |                                                                                |
| `-XX:-PositiveIdentityHash` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |




<!-- ==== END OF TOPIC ==== xxpositiveidentityhash.md ==== -->
