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

# -Xiss / -Xss / -Xssi

Sets the stack size and increment for Java&trade; threads.

If you exceed the maximum Java thread stack size, a `java/lang/OutOfMemoryError` message is reported.

You can use the `-verbose:sizes` option to find out the values that the VM is currently using.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Java methods and native methods run on two different stacks and the VM handles switching between them for JNI calls. Each stack is sized using separate options; these options apply to the Java stack only. For the native stack option, see the link in the [See also](#see-also) section.

## Syntax

| Setting       | Effect                               | Default                              |
|---------------|--------------------------------------|--------------------------------------|
| `-Xiss<size>` | Set initial Java thread stack size   | 2 KB                                 |
| `-Xss<size>`  | Set maximum Java thread stack size   | 320 KB (31/32-bit); 1024 KB (64-bit) |
| `-Xssi<size>` | Set Java thread stack size increment | 16 KB                                |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.  

See [Default settings for the Eclipse OpenJ9&trade; VM](openj9_defaults.md) for more about default values.

## See also

- [`-Xmso`](xmso.md) (Native stack size for operating system threads)




<!-- ==== END OF TOPIC ==== xss.md ==== -->
<!-- ==== END OF TOPIC ==== xssi.md ==== -->
<!-- ==== END OF TOPIC ==== xiss.md ==== -->
