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

# Switching to OpenJ9

OpenJ9 provides the following tools for compatibility with the reference implementation. These tools might differ in behavior from the HotSpot equivalent.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** For information about HotSpot equivalences and differences for items other than tools, see [New to OpenJ9?](openj9_newuser.md).

#### Java process status (`jps`)

Displays information about running Java<sup>&trade;</sup> processes. The main differences from the HotSpot `jps` tool are as follows:

- runs on Windows&reg;, AIX&reg;, and z/OS&reg;
- uses the Attach API
- shows processes on the current host only
- there is no `-V` option

For more information, see [`Java process status`](tool_jps.md).




<!-- ==== END OF TOPIC ==== tools_migration.md ==== -->
