<!--
* Copyright (c) 2023, 2023 IBM Corp. and others
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
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->

# -XX:\[+|-\]HandleSIGUSR2

**(AIX&reg;, Linux&reg;, macOS&reg;, and z/OS&reg; only)**

This option affects the handling of the operating system signal `SIGUSR2`. This is a user defined signal which is set aside for the user's needs.

## Syntax

        -XX:[+|-]HandleSIGUSR2

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+HandleSIGUSR2 ` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-HandleSIGUSR2 ` | Disable |                                                                                    |


## Explanation

When enabled, the VM handles the signal `SIGUSR2` and generates the various dump files.

When the option is disabled, the VM does not handle the signal `SIGUSR2`. Generally, this signal is handled by the default operating system handler which terminates the process.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Do not use the `-XX:+HandleSIGUSR2` and [`-Xrs`](xrs.md) options together. An error is thrown if both options are enabled. To resolve this error, one of the options should be disabled.

<!-- ==== END OF TOPIC ==== xxhandlesigusr2.md ==== -->
