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

# -XX:\[+|-\]PageAlignDirectMemory

This Oracle HotSpot option affects the alignment of direct byte buffer allocation and is implemented by the Eclipse OpenJ9&trade; VM for compatibility.

## Syntax

        -XX:[+|-]PageAlignDirectMemory

| Setting                      | Effect  | Default                                                                            |
|------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+PageAlignDirectMemory` | Enable  |                                                                                    |
| `-XX:-PageAlignDirectMemory` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

As discussed in the Oracle documentation, before Java&trade; SE 7, direct buffers that were allocated using `java.nio.ByteBuffer.allocateDirect(int)` were aligned on a page boundary. This behavior changed in Java SE 7 and the `-XX:+PageAlignDirectMemory` option is provided to revert to the previous behavior.

For more information about the changes, see [RFE 4837564](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4837564), which was introduced in the [Java SE 7 release notes](https://www.oracle.com/technetwork/java/javase/jdk7-relnotes-418459.html).


<!-- ==== END OF TOPIC ==== xxpagealigndirectmemory.md ==== -->
