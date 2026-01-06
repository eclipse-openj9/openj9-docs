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

# -XX:JITServerAOTmx

**(Linux&reg; only)**

This option specifies the maximum amount of memory allocated to the JITServer AOT cache for storing the compiled code and for the associated data structures that are used in the cache's implementation.

## Syntax

        -XX:JITServerAOTmx=<size>

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
| `-XX:JITServerAOTmx=<size>`    | Limits the amount of memory used by the JITServer AOT cache |  300 MB                       |

## Explanation

When the JITServer receives an AOT compilation request, it checks its AOT cache for a compatible compiled method body. If one is not found, the server performs the AOT compilation, sends the response to the client JVM, then stores the compiled method in its local AOT cache, for future use. Multiple requests and storing of the compiled methods can use a lot of memory and degrade the system's overall performance.

You can specify the maximum memory limit for the AOT cache by using the `-XX:JITServerAOTmx=<size>` option, so that when the JITServer reaches that limit, it will not be able to add new AOT methods to its AOT cache. This will limit the amount of memory used for caching the compiled code and prevent memory shortages at JITServer that could lead to poor performance or even native out-of-memory events.

## See also

- [JITServer AOT cache](jitserver_tuning.md#jitserver-aot-cache)

<!-- ==== END OF TOPIC ==== xxjitserveraotmx.md ==== -->
