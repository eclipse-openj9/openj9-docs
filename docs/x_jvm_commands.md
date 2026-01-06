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

# Using -X command-line options


Use these options to configure the Eclipse OpenJ9&trade; virtual machine (VM). Unlike standard options, options prefixed with `-X` are nonstandard and are typically unique to a Java&trade; virtual
machine implementation. However, in some cases, `-X` option names are common to different VM implementations and might have the same function.

For options that take a `<size>` parameter, add a suffix to the size value: "k" or "K" to indicate kilobytes, "m" or "M" to indicate megabytes, "g" or "G" to indicate gigabytes, or "t" or "T" to indicate terabytes.

For example, to set the `-Xmx` value to 16 MB, you can specify `-Xmx16M`, `-Xmx16m`, `-Xmx16384K`, `-Xmx16384k`, or `-Xmx16777216` on the command line.


<!-- ==== END OF TOPIC ==== x_jvm_commands.md ==== -->
