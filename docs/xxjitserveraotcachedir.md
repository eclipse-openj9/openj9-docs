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

# -XX:JITServerAOTCacheDir

**(Linux&reg; only)**

This option specifies the directory to be used for saving and loading JITServer AOT cache files.

## Syntax

        -XX:JITServerAOTCacheDir=<directory>

 If the option is not specified, the cache files are saved in the current working directory of the JITServer server.

## Explanation

 A JITServer instance can have several AOT caches, each with its own name. To enable reusing of the AOT caches by other JITServer instances (such as the instances started later), you can use the [`-XX:+JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md) option. With this option enabled, JITServer server periodically saves its AOT caches to files. Other JITServer instances can then load these caches the first time a client requests a particular cache.

 You can specify the directory for saving the AOT cache files with the `-XX:JITServerAOTCacheDir=<directory>` option. When the server receives the location of the requested AOT cache file through the `-XX:JITServerAOTCacheDir` option and a request for a specific cache name, if that cache does not exist in-memory, the server searches the specified cache directory for the file with the matching name and loads it, if available.

 This option is not applicable if the JITServer AOT cache persistence feature is not enabled with the `-XX:+JITServerAOTCachePersistence` option.

## See also

- [JITServer AOT cache](jitserver_tuning.md#jitserver-aot-cache)
- [`-XX:[+|-]JITServerUseAOTCache`](xxjitserveruseaotcache.md)
- [`-XX:JITServerAOTCacheName`](xxjitserveraotcachename.md)
- [`-XX:[+|-]JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md)


<!-- ==== END OF TOPIC ==== xxjitserveraotcachedir.md ==== -->
