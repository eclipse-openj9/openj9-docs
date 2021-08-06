<!--
* Copyright (c) 2021, 2021 IBM Corp. and others
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

# -XX:\[+|-\]JITServerLocalSyncCompiles

When this option is specified, synchronous JIT compilations are downgraded to cold optimization level and compiled locally, with a remote asynchronous recompilation scheduled at a later point.

## Syntax

        -XX:[+|-]JITServerLocalSyncCompiles

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+JITServerLocalSyncCompiles`           | Enable |                                                                                    |
|`-XX:-JITServerLocalSyncCompiles`           | Disable| :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

During a synchronous compilation, Java application threads have to wait for it to complete.
Since remote compilations usually take longer, due to network latency, remote synchronous
compilations can result in large pauses in the client application.

With this option enabled, the client performs synchronous compilations locally at cold optimization level,
and later recompiles asynchronously at a higher level remotely. Real-time applications can benefit from using this option.

<!-- ==== END OF TOPIC ==== xxjitserverlocalsynccompiles.md ==== -->