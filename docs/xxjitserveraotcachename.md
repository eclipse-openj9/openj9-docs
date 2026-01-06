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

# -XX:JITServerAOTCacheName

 This option, used at the JITServer client JVMs, specifies the name of the AOT cache to be used at the JITServer server.

## Syntax

         -XX:JITServerAOTCacheName=<cache_name>

:   where `<cache_name>` specifies the name of the server-side AOT cache to be used.

## Explanation

 A single JITServer instance can use multiple AOT caches, which are distinguished by their name.
 Clients that are running applications with significantly different execution profiles can, therefore, connect to the same JITServer instance.
 Specify this option at the client JVM. If you do not specify this option, the client uses a cache named `default`.

 :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** You must specify [`-XX:+JITServerUseAOTCache`](xxjitserveruseaotcache.md), otherwise the `-XX:JITServerAOTCacheName` option is ignored.

## See also

 - [JITServer technology](jitserver.md)
 - [`-XX:[+|-]JITServerUseAOTCache`](xxjitserveruseaotcache.md)
 - [`-XX:JITServerAOTCacheDir`](xxjitserveraotcachedir.md)
 - [`-XX:[+|-]JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md)

<!-- ==== END OF TOPIC ==== xxjitserveraotcachename.md ==== -->
