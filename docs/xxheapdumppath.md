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

# -XX:HeapDumpPath

This HotSpot option is recognized by Eclipse OpenJ9&trade; for compatibility, and you can use it as an alias for [`-Xdump:directory=<path>`](xdump.md#syntax).

This option sets the directory for all VM dumps including heap dumps, Java&trade; dumps, and system dumps.

## Syntax

        -XX:HeapDumpPath=<path>

where `<path>` is the directory to which all dump types are written. This directory path is prefixed to the path of all non-absolute dump file names, including the file names for the default dump agents.

<!-- ==== END OF TOPIC ==== xxheapdumppath.md ==== -->
