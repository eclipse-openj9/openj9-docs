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

# -XX:\[+|-\]JITServerShareROMClasses

This option enables or disables the JITServer server to share cached ROM classes between JITServer clients.

## Syntax

        -XX:[+|-]JITServerShareROMClasses

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+JITServerShareROMClasses`           | Enable |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  |
|`-XX:-JITServerShareROMClasses`           | Disable|                                                                                        |

 :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The JITServer AOT caching feature is now enabled at the server by default with the [`-XX:+JITServerUseAOTCache`](xxjitserveruseaotcache.md) option. Because the `-XX:+JITServerShareROMClasses` option is a prerequisite for the `-XX:+JITServerUseAOTCache` option, therefore the `-XX:+JITServerShareROMClasses` option is also the default setting. If you disabled the JITServer AOT cache at the server (`-XX:-JITServerUseAOTCache`), specify the `-XX:+JITServerShareROMClasses` option explicitly to enable the sharing of the cached ROM classes feature.

## Explanation

Enable this option when multiple clients that are running identical or similar applications connect to a single server.

This option enables a caching optimization that allows the server to use ROM classes that are cached for one client while compiling for a different client. This behavior reduces the memory usage at the server because only a single copy of a particular Java&trade; class is cached.

## See also

- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxjitservershareromclasses.md ==== -->
