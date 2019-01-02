<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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

# -Xifa

**(z/OS&reg; only)**

Enables Java&trade; applications to run on IFAs if they are available.

z/OS V1R6 or later can run Java applications on a special-purpose assist processor called the System z&trade; Application Assist Processor (zAAP). zAAPs operate asynchronously with the general purpose processors to execute Java programming under control of the VM. The zAAP is also known as an IFA (Integrated Facility for Applications).

Only Java code and system native methods can be on IFA processors.

## Syntax

| Setting         | Value              | Default                                                                            |
|-----------------|--------------------|:----------------------------------------------------------------------------------:|
| `-Xifa:<value>` | `on`               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
|                 | `off`              |                                                                                    |
|                 | `force` (obsolete) |                                                                                    |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** The `force` option is obsolete and should not normally be used. This option is superseded by the `SYS1.PARMLIB(IEAOPTxx) PROJECTCPU=YES` parameter, which is available on all supported levels of z/OS. `Xifa:force` can be used for testing purposes when a zAAP is not available, but can have a negative performance impact.



<!-- ==== END OF TOPIC ==== xifa.md ==== -->
