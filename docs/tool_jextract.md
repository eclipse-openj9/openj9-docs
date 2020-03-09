<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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

# Dump extractor (`jextract`)

**(AIX&reg;, Linux&reg;, macOS&reg;)**

On some operating systems, copies of executable files and libraries are required for a full analysis of a core dump (you can get some information from the dump without these files, but not as much). Run the `jextract` utility to collect these extra files and package them into an archive file along with the core dump. To analyze the output, use the [dump viewer (`jdmpview`)](tool_jdmpview.md).

## Syntax

        jextract <core file name> [<zip_file>]

where:

- `<core file name>` is the name of the system dump.
- `<zip_file>` is the name you want to give to the processed file. If you do not specify a name, output is written to `<core file name>.zip` by default. The output is written to the same directory as the core file.

: <i class="fa fa-exclamation-triangle"></i> **Restriction: ** You must run `jextract` on the same system that produced the system dump in order to collect the correct executables and libraries referenced in the system dump. You should also run `jextract` using the same VM level, to avoid any problems.

## See also

- [Dump viewer (`jdmpview`)](tool_jdmpview.md)




<!-- ==== END OF TOPIC ==== tool_jextract.md ==== -->
