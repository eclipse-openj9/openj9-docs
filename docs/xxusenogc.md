<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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

# -XX:\[+|-\]UseNoGC  

The `-XX:+UseNoGC` option enables a garbage collection policy that expands the Java object heap in the normal way until the limit is reached, but memory is not
reclaimed through garbage collection.

## Syntax

        -XX:[+|-]UseNoGC

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+UseNoGC`           | Enable |                                                                                    |
|`-XX:-UseNoGC`           | Disable| <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

## Explanation

This policy can be useful for test purposes and for short-lived applications. When the limit is reached an `OutOfMemory` error is generated and the VM shuts down. This policy can also be enabled with the [`-Xgcpolicy:nogc`](xgcpolicy.md) option.

The `-XX:-UseNoGC` option turns off a previously enabled `-XX:+UseNoGC` option.



<!-- ==== END OF TOPIC ==== xxusenogc.md ==== -->
