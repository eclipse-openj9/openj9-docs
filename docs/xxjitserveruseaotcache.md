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

# -XX:\[+|-\]JITServerUseAOTCache

This option enables or disables the caching of AOT-compiled methods in the JITServer server.

## Syntax

        -XX:[+|-]JITServerUseAOTCache

| Setting                    | Effect  | Default                                                                              |
|----------------------------|---------|:------------------------------------------------------------------------------------:|
|`-XX:+JITServerUseAOTCache` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|`-XX:-JITServerUseAOTCache` | Disable |                                                                                      |

## Explanation

When you enable this option, the JITServer server caches the AOT-compiled methods. When a JITServer client requests an AOT compilation and the requested method exists in the cache, the server does not have to recompile the method. This feature, therefore, improves the CPU utilization of the JITServer technology and of the cluster.

 :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This option must be enabled both at the client JVM and at the server. Although this option is by default enabled at the server, it is still disabled for the JITServer clients. That means that you don't have to specify the `-XX:+JITServerUseAOTCache` option at the server to enable the JITServer AOT caching feature, but you must specify this option for each client who wants to use this feature.
 <!-- Deleted this point because from the 0.46.0 release onwards the local SCC is ignored by default
 - The client JVM must have the [shared classes cache](https://eclipse.dev/openj9/docs/shrc/) feature enabled and be allowed to generate AOT compilation requests. -->

The [`-XX:+JITServerShareROMClasses`](xxjitservershareromclasses.md) option is also enabled by default at the server because this option is a prerequisite for the `-XX:+JITServerUseAOTCache` option.

## See also

- [JITServer technology](jitserver.md)
- [`-XX:JITServerAOTCacheName`](xxjitserveraotcachename.md)
- [`-XX:JITServerAOTCacheDir`](xxjitserveraotcachedir.md)
- [`-XX:[+|-]JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md)
- [What's new in version 0.33.x](version0.33.md#jitserver-technology-feature-updated)
- [What's new in version 0.46.0](version0.46.md#the-jitserver-aot-caching-feature-enabled-by-default-at-the-jitserver-server)

<!-- ==== END OF TOPIC ==== xxjitserveruseaotcache.md ==== -->
