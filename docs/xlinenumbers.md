<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# -Xlinenumbers / -Xnolinenumbers

Enables or disables line numbers in stack traces for debugging.

## Syntax

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-Xlinenumbers`       | Enable  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| `-Xnolinenumbers`     | Disable |                                                                                    |

## Explanation

If you start the OpenJ9 VM with `-Xnolinenumbers` when creating a new shared classes cache, the Class Debug Area is not created. The option `-Xnolinenumbers` advises the VM not to load any class debug information, so there is no need for this region. If [`-Xscdmx`](xscdmx.md) is also used on the command line to specify a non zero debug area size, then a debug area is created despite the use of `-Xnolinenumbers`.


<!-- ==== END OF TOPIC ==== xlinenumbers.md ==== -->
<!-- ==== END OF TOPIC ==== xnolinenumbers.md ==== -->
