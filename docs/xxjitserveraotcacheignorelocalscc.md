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

# -XX:[+|-]JITServerAOTCacheIgnoreLocalSCC

This option, which is used at JITServer client VMs, controls how the JITServer AOT cache feature interacts with the local shared classes cache. You can enable or disable the usage of JITServer AOT cache artifacts (AOT methods and associated metadata) in the absence of a local shared class cache with write permissions and free space.

## Syntax

        -XX:[+|-]JITServerAOTCacheIgnoreLocalSCC

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+JITServerAOTCacheIgnoreLocalSCC` |  Enable   |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>                                                        |
| `-XX:-JITServerAOTCacheIgnoreLocalSCC` |  Disable  |       |

## Explanation

Earlier, to use the JITServer AOT cache feature, the client VM had to use a shared class cache with some empty space and write permissions. From release 0.46.0 onwards, the default behavior of the client when it uses the JITServer AOT cache is to bypass its local shared classes cache (if one is set up) during JITServer AOT cache compilations. Class sharing and local AOT method loading will still occur if possible, but no additional data, such as class data or methods received from the server, will be stored in or loaded from the local cache during these compilations. With this behavior, the clients can take advantage of the JITServer AOT cache even if the local shared classes cache is not available.

You can disable this feature with the `-XX:-JITServerAOTCacheIgnoreLocalSCC` option. If you disable this option, the client will not bypass the local shared classes cache during JITServer AOT cache compilations. The client will instead store new data, including methods received from the server's AOT cache, in the local cache before using them. Clients with this option disabled must have a local shared classes cache set up, with write permissions and some free space, to use the JITServer AOT cache.


## See also

- [What's new in version 0.46.0](version0.46.md#new-xx-jitserveraotcacheignorelocalscc-option-added)



<!-- ==== END OF TOPIC ==== xxjitserveraotcacheignorelocalscc.md ==== -->
