<!--
* Copyright (c) 2022, 2022 IBM Corp. and others
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

# -XX:\[+|-\]JITServerUseAOTCache

This option enables caching of AOT compiled methods at JITServer

## Syntax

        -XX:[+|-]JITServerUseAOTCache

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+JITServerUseAOTCache` | Enable |                                                                                    |
|`-XX:-JITServerUseAOTCache` | Disable| :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

When you enable this option, JITServer will cache the result of any AOT compilation it performs. Future AOT compilation requests (from other client JVMs) that hit in the cache can be served directly, avoiding the expensive process of a compilation. Correcteness is ensured by cryptograhic hashes and class inheritance chain checks for any class the method being compiled refers to.

You must specify this option both at the client JVM and at the server to benefit from this feature. Moreover, the client JVM must have the [shared class cache](https://www.eclipse.org/openj9/docs/shrc/) feature enabled and be allowed to generate AOT compilation requests.

## See also

- [`JITServer technology`](jitserver.md)
- [`-XX:JITServerAOTCacheName`](xxjitserveraotcachename)

<!-- ==== END OF TOPIC ==== xxjitserveruseaotcache.md ==== -->
