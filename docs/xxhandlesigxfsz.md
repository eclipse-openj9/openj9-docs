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

# -XX:\[+|-\]HandleSIGXFSZ

**(AIX&reg;, Linux&reg;, macOS&reg;, and z/OS&reg; only)**

This option affects the handling of the operating system signal `SIGXFSZ`. This signal is generated when a process attempts to write to a file that causes the maximum file size `ulimit` to be exceeded.


## Syntax

        -XX:[+|-]HandleSIGXFSZ

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+HandleSIGXFSZ ` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-HandleSIGXFSZ ` | Disable |                                                                                    |


## Explanation

When enabled, the VM handles the signal `SIGXFSZ` and continues, without ending. When a file is written from a Java&trade; API class that exceeds the maximum file size `ulimit`, an exception is raised. Log files that are created by the VM are silently truncated when they reach the maximum file size `ulimit`.

When the option is disabled, the VM does not handle the signal `SIGXFSZ`. In this situation, if the maximum file size `ulimit` for any file is reached, the operating system ends the process with a core dump.



<!-- ==== END OF TOPIC ==== xxhandlesigxfsz.md ==== -->
