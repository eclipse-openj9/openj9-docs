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

# -Xcodecache

Use this option to tune performance.

This option sets the size of each block of memory that is allocated to store the native code of compiled Java&trade; methods. By default, this size is selected internally according to the processor architecture and the capability of your system. The maximum value you can specify is 32 MB. If you set a value larger than 32 MB, the JIT ignores the input and sets the value to 32 MB.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The JIT compiler might allocate more than one code cache for an application. Use the [`-Xcodecachetotal`](xcodecachetotal.md) option to set the maximum amount of memory that is used by all code caches.


## Syntax

        -Xcodecache<size>

: See [Using -X command-line options](x_jvm_commands.md) for more information about specifying the `<size>` parameter.




<!-- ==== END OF TOPIC ==== xcodecache.md ==== -->
