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

# -XX:\[+|-\]MemoryDisclaim

**Linux&reg; only**

This option enables or disables the return of unused physical memory pages back to the operating system while still keeping the virtual address space reserved.

## Syntax

        -XX:[+|-]MemoryDisclaim

|   Setting               |   Effect |  Default                                                                                   |
|-------------------------|----------|:------------------------------------------------------------------------------------------:|
|  `-XX:+MemoryDisclaim`  |  Enable  |   :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |
|  `-XX:-MemoryDisclaim`  |  Disable |                                                                                            |

## Explanation

With the `-XX:+MemoryDisclaim` option, OpenJ9 can *disclaim* the physical memory for data structures, such as data caches, interpreter profiler, and runtime assumptions, when it estimates that these data structures are unlikely to be used soon. The operating system can immediately reuse the released physical memory.

For each of these data structures, there is a corresponding internal `-Xjit` option that can be used to disable disclaiming for that specific data structure. Any such `-Xjit` options that affect memory disclaiming take precedence over the `-XX:[-|+]MemoryDisclaim` option.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- If `-XX:-MemoryDisclaim` is specified, then all forms of memory disclaiming are disabled.
- If `-XX:+MemoryDisclaim` is specified after the last occurrence of `-XX:-MemoryDisclaim`, then default memory disclaiming settings are restored.
- The data of the physical memory pages that are disclaimed are stored in backing files. The data is restored from these backing files. For more information, see [`-XX:DisclaimDir`](xxdisclaimdir.md).
- Memory disclaiming and therefore the `-XX:[+|-]MemoryDisclaim` option applies only to Linux operating system. For all other operating systems, disclaiming is disabled and this option has no effect.

## See also

- [`-XX:DisclaimDir`](xxdisclaimdir.md)

<!-- ==== END OF TOPIC ==== xxmemorydisclaim.md ==== -->
