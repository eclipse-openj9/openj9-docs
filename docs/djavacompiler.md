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

# -Djava.compiler

This Oracle HotSpot property is used for loading a JIT compiler from a named, native library. This option can be used on the command line to specify the JIT compiler for the Eclipse OpenJ9&trade; VM.

## Syntax

        -Djava.compiler=j9jit29


<!-- OLD: 
## Explanation

Enable JIT compilation by setting to `j9jit<vm_version>`, where `<vm_version>` is the version of the J9 virtual machine. Use only digits, for example "29" for VM version 2.9. Check the output of the `java -version` command to confirm your VM level. (Equivalent to `–Xjit`).-->



<!-- ==== END OF TOPIC ==== djavacompiler.md ==== -->
