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

# -XXnosuballoc32bitmem  

**(z/OS&reg; only)**

When compressed references are used with a 64-bit Eclipse OpenJ9&trade; VM on z/OS&reg;, this option forces the VM to use 31-bit memory allocation functions provided by z/OS.

## Syntax

        -XXnosuballoc32bitmem

| Setting                 | Effect  | Default                                                                            |
|-------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XXnosuballoc32bitmem` | Enable  |                                                                                    |   
| No setting              | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

This option is provided as a workaround for customers who need to use fewer pages of 31-bit virtual storage per VM invocation. Using this option might result in a small increase in the number of frames of central storage used by the VM. However, the option frees 31-bit pages for use by native code or other applications in the same address space.

If this option is not specified, the VM uses an allocation strategy for 31-bit memory that reserves a region of 31-bit virtual memory.



<!-- ==== END OF TOPIC ==== xxnosuballoc32bitmem.md ==== -->
