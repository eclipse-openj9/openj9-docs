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

# -Xmso

Sets the native stack size for operating system threads.

You can use the `-verbose:sizes` option to find out the values that the VM is currently using.

When a native method makes a call into the VM, the VM calculates whether the memory required for the call will exceed the `-Xmso` value. If so, a `java/lang/StackOverflowError` error is thrown.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Java methods and native methods run on two different stacks and the VM handles switching between them for JNI calls. Each stack is sized using separate options; this option applies to the native stack only. For the Java stack option, see the link in the [See also](#see-also) section.

## Syntax

        -Xmso<size>

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.  

## Default setting

Default values vary by platform. See [Default settings for the Eclipse OpenJ9&trade; VM](openj9_defaults.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On 64-bit z/OS, the default size is the minimum that can be allocated by the operating system. So if you set a value that is smaller, that value is ignored by the VM.

## See also

- [`-Xiss/-Xss/-Xssi`](xss.md) (stack size and increment for Java&trade; threads)


<!-- ==== END OF TOPIC ==== xmso.md ==== -->
