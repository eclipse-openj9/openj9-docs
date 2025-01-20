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

# -XX:\[+|-\]RequireJITServer

When you enable this option, the JITServer client crashes with an assert if it detects that a JITServer server is unavailable.

## Syntax

        -XX:[+|-]RequireJITServer

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+RequireJITServer`           | Enable |                                                                                    |
|`-XX:-RequireJITServer`           | Disable| :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

This option is for debugging purposes only.

When this option is disabled, a server crash forces the client to perform compilations locally. You might want to enable this option if you are running a test suite with JITServer enabled, so that a test fails if the server crashes, instead of switching to local compilations and hiding the failure.

## See also

- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxrequirejitserver.md ==== -->
