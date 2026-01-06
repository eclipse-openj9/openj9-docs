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

# -XX:\[+|-\]OpenJ9CommandLineEnv

This option controls whether the VM captures the command line in the environment variable `OPENJ9_JAVA_COMMAND_LINE`. If enabled, the variable is set by the VM after it starts. Using this variable, you can find the command-line parameters set when the VM started. It applies not just to launchers (e.g. `java`) that are included in a JDK, but to any application that uses `JNI_CreateJavaVM()`.

## Syntax

        -XX:[+|-]OpenJ9CommandLineEnv

| Setting                     | Effect  | Default on z/OS                                                                      | Default on other platforms                                                           |
|-----------------------------|---------|:------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------:|
| `-XX:+OpenJ9CommandLineEnv` | Enable  |                                                                                      | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-OpenJ9CommandLineEnv` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |                                                                                      |

This option is currently disabled by default on z/OS&reg; because it might cause the VM to crash.

## See also

- [Environment variables](env_var.md)

<!-- ==== END OF TOPIC ==== xxopenj9commandlineenv.md ==== -->
