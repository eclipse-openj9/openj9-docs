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

# Using -XX command-line options

Java&trade; VM command-line options that are specified with `-XX:` are not checked for validity. If the VM does not recognize the option, the option is ignored. These options can therefore be used across different VM versions without ensuring a particular level of the VM.  If you want
to turn off this behavior to test whether your -XX options are valid, use the [-XX:-IgnoreUnrecognizedXXColonOptions](xxignoreunrecognizedxxcolonoptions.md) option.

For options that take a `<size>` parameter, add a suffix to the size value: "k" or "K" to indicate kilobytes, "m" or "M" to indicate megabytes, "g" or "G" to indicate gigabytes, or "t" or "T" to indicate terabytes.

For example, to set the `-XX:MaxDirectMemorySize` value to 16 MB, you can specify `-XX:MaxDirectMemorySize16M`, `-XX:MaxDirectMemorySize16m`, `-XX:MaxDirectMemorySize16384K`, or `XX:MaxDirectMemorySize16384k` on the command line.



<!-- ==== END OF TOPIC ==== xx_jvm_commands.md ==== -->
