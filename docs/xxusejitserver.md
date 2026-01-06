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

# -XX:\[+|-\]UseJITServer

This option starts the Eclipse OpenJ9&trade; VM in JITServer client mode.

## Syntax

        -XX:[+|-]UseJITServer

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+UseJITServer`           | Enable |                                                                                    |
|`-XX:-UseJITServer`           | Disable| :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

When you enable this option, the VM tries to connect to a server and send all JIT compilation requests to it. For more information, see [JITServer Technology](jitserver.md).

You must specify this option for any other `-XX:*JITServer*` options to take effect.

## See also

- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxusejitserver.md ==== -->
