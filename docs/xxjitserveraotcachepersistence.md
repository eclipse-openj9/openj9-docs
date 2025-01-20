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

# -XX:[+|-]JITServerAOTCachePersistence

**(Linux&reg; only)**

This option enables or disables the JITServer server's ability to allow other JITServer instances to reuse AOT caches.

## Syntax

        -XX:[+|-]JITServerAOTCachePersistence

| Setting                    | Effect  | Default                                                                              |
|----------------------------|---------|:------------------------------------------------------------------------------------:|
|`-XX:+JITServerAOTCachePersistence` | Enable  |                                                                                      |
|`-XX:-JITServerAOTCachePersistence` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

 With the `-XX:+JITServerAOTCachePersistence` option, the JITServer server periodically saves its AOT caches to files. Other JITServer instances can then load these caches from files the first time a client requests a particular named cache. This feature is useful to improve performance, especially in scenarios where JITServer instances are started up and shut down regularly.

 This feature depends on the [`-XX:+JITServerUseAOTCache`](xxjitserveruseaotcache.md) command-line option, which is used to enable caching of AOT-compiled methods. You must specify this option both at the client JVM and at the server.

 Use the [`-XX:JITServerAOTCacheName=<cache_name>`](xxjitserveraotcachename.md) option at the client to request a particular AOT cache. If the requested cache does not exist at the server in-memory, but the AOT cache persistence feature is enabled (`-XX:+JITServerAOTCachePersistence`), the server checks whether a file for that cache exists. If the AOT cache file exists, it is loaded in the background. If the AOT cache file does not exist or the AOT cache persistence feature is disabled (`-XX:-JITServerAOTCachePersistence`), the server creates an empty AOT cache and gradually populates it with AOT method bodies it compiles.

 You can use the [`-XX:JITServerAOTCacheDir=<directory>`](xxjitserveraotcachedir.md) option to specify the directory where the AOT cache must be saved to or loaded from.

 The name of an AOT cache file has the following structure:

        JITServerAOTCache.<CACHE_NAME>.J<JAVA_VERSION>
 Where,

 - `<CACHE_NAME>` is the name of the AOT cache requested by the client, and
 - `<JAVA_VERSION>` is the version of Java used by JITServer instance (for example, 17 will be used for Java 17)

## See also

- [`-XX:JITServerAOTCacheName`](xxjitserveraotcachename.md)
- [`-XX:JITServerAOTCacheDir`](xxjitserveraotcachedir.md)
- [`-XX:[+|-]JITServerUseAOTCache`](xxjitserveruseaotcache.md)
- [JITServer AOT cache](jitserver_tuning.md#jitserver-aot-cache)

<!-- ==== END OF TOPIC ==== xxjitserveraotcachepersistence.md ==== -->
