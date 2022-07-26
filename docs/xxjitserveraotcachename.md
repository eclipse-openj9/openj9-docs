<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# -XX:JITServerAOTCacheName

 This option, used at client JVMs, specifies the name of the AOT cache to be used at the JITServer server.

## Syntax

         -XX:JITServerAOTCacheName=<cache-name>

 | Setting                 | Effect | Default                                                            |
 |-------------------------|--------|:------------------------------------------------------------------:|
 |`-XX:JITServerAOTCacheName` | Specifies the name of the server-side AOT cache to use | No name         |

## Explanation

 A single JITServer instance can use multiple AOT caches that are distinguished by their name.
 The idea behind this design choice is to allow clients running applications with significantly different execution profiles to connect to the same JITServer instance.
 This option must be specified at the client JVM. If no such option is provided, the client will use a nameless cache.

 Note that [`-XX:+JITServerUseAOTCache`](xxjitserveruseaotcache.md) must also be specified, otherwise the `-XX:JITServerAOTCacheName` option is ignored.

## See also

 - [`JITServer technology`](jitserver.md)
 - [`-XX:JITServerUseAOTCache`](xxjitserveruseaotcache.md)

<!-- ==== END OF TOPIC ==== xxjitserveruseaotcache.md ==== -->
