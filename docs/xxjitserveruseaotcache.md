<!--
* Copyright (c) 2017, 2023 IBM Corp. and others
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

# -XX:\[+|-\]JITServerUseAOTCache

This option enables the caching of AOT-compiled methods in the JITServer server.

## Syntax

        -XX:[+|-]JITServerUseAOTCache

| Setting                    | Effect  | Default                                                                              |
|----------------------------|---------|:------------------------------------------------------------------------------------:|
|`-XX:+JITServerUseAOTCache` | Enable  |                                                                                      |
|`-XX:-JITServerUseAOTCache` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

When you enable this option, the JITServer server caches the AOT-compiled methods. When a JITServer client requests an AOT compilation and the requested method exists in the cache, the server does not have to recompile the method. This feature, therefore, improves the CPU utilization of the JITServer technology and of the cluster.

 :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This option has the following requirements:

 - You must specify this option both at the client JVM and at the server.
 - The client JVM must have the [shared class cache](https://www.eclipse.org/openj9/docs/shrc/) feature enabled and be allowed to generate AOT compilation requests.

The [`-XX:+JITServerShareROMClasses`](xxjitservershareromclasses.md) option is enabled by default at the server on specifying the`-XX:+JITServerUseAOTCache` option.

## See also

- [JITServer technology](jitserver.md)
- [`-XX:JITServerAOTCacheName`](xxjitserveraotcachename.md)
- [`-XX:JITServerAOTCacheDir`](xxjitserveraotcachedir.md)
- [`-XX:[+|-]JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md)

<!-- ==== END OF TOPIC ==== xxjitserveruseaotcache.md ==== -->
