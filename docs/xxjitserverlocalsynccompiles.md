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

# -XX:\[+|-\]JITServerLocalSyncCompiles

When you specify this JITServer option, synchronous JIT compilations are downgraded to `cold` optimization level and compiled locally, with a remote asynchronous recompilation scheduled at a later point.

## Syntax

        -XX:[+|-]JITServerLocalSyncCompiles

| Setting                 | Effect | Default                                                                             |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+JITServerLocalSyncCompiles`           | Enable |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>                                                          |
|`-XX:-JITServerLocalSyncCompiles`           | Disable|                             |

The option `-XX:[+|-]JITServerLocalSyncCompiles` is enabled by default in most cases. The option remains disabled when you specify [`-Xjit:count=0`](xjit.md#count) and in a few advanced use cases such as running the JVM in debug mode (as described in the [Improved JVM debug mode based on OSR](https://blog.openj9.org/2019/04/30/introduction-to-full-speed-debug-base-on-osr/) post in the Eclipse OpenJ9&trade; blog).

## Explanation

During a synchronous compilation, Java&trade; application threads have to wait for the compilation to complete.
Because remote compilations usually take longer, due to network latency, remote synchronous compilations can result in large pauses in the client application.

If you enable this option, the client performs synchronous compilations locally at `cold` optimization level and later recompiles asynchronously at a higher level remotely. This behavior can be beneficial for real-time applications.

## See also

- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxjitserverlocalsynccompiles.md ==== -->
