<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# -XX:\[+|-\]LazySymbolResolution

**(Linux&reg; and macOS&reg; only)**

This option affects the timing of symbol resolution for functions in user native libraries.

## Syntax

        -XX:[+|-]LazySymbolResolution

| Setting                     | Effect  | Default                                                                            |
|-----------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+LazySymbolResolution` | Enable  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| `-XX:-LazySymbolResolution` | Disable |                                                                                    |

## Explanation

Enabling this option forces the VM to delay symbol resolution for each function in a user native library, until the function is called.
The `-XX:-LazySymbolResolution` option forces the VM to immediately resolve symbols for all functions in a user native library when the library is loaded.

These options apply only to functions; variable symbols are always resolved immediately when loaded. If you attempt to use these options on an operating system other than Linux or macOS, the options are accepted, but ignored.



<!-- ==== END OF TOPIC ==== xxlazysymbolresolution.md ==== -->
