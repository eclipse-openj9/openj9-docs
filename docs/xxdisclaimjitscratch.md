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

# -XX:\[+|-\]DisclaimJitScratch  

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This option is deprecated; the option is accepted but ignored.

**(Linux&reg; only)**

The `-XX:+DisclaimJitScratch` option signals to the operating system to discard temporary physical memory that is consumed by the JIT compilation threads.

## Syntax

        -XX:[+|-]DisclaimJitScratch

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+DisclaimJitScratch`| Enable |                                                                                    |
|`-XX:-DisclaimJitScratch`| Disable| :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

Discarding temporary physical memory can reduce the physical memory reported in use by the Java&trade; application. The physical memory that is released is available to other processes without the operating system needing to search for the least recently used frames.

The `-XX:-DisclaimJitScratch` option turns off a previously enabled `-XX:+DisclaimJitScratch` option.



<!-- ==== END OF TOPIC ==== xxdisclaimjitscratch.md ==== -->
