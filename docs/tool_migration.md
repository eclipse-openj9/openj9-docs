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

# Switching to Eclipse OpenJ9

Eclipse OpenJ9&trade; provides the following tools, which might differ in behavior from the HotSpot equivalent.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** For information about HotSpot equivalences and differences for items other than tools, see [New to Eclipse OpenJ9?](openj9_newuser.md)

#### Java diagnostic command tool (`jcmd`)

Runs diagnostic commands on a specified VM. The main difference from the HotSpot `jcmd` tool is that the following options are not currently supported:

- The `-f` option to read commands from a file.
- The `Perfcounter.print` option for displaying performance counters for the target VM.

#### Java memory map tool (`jmap`)

Displays information about classes on the heap, including the number of objects and their aggregate size. The main differences from the HotSpot `jmap` tool are as follows:

- Uses the Attach API.
- Displays information only for local processes that are owned by the current user, due to security considerations. You can display information for remote processes by using `ssh user@host jmap <option> <vmid>`, where `<vmid>` is the Attach API virtual machine identifier for the Java&trade; process.
- Does not support displaying data from core dumps; use [Dump viewer](tool_jdmpview.md) instead.
- Does not include a `-F` option to force a dump of an unresponsive process. User `kill -QUIT <pid>` instead, where `<pid>` is the process
identifier.

For more information, see [`jmap`](tool_jmap.md).

#### Java process status (`jps`)

Displays information about running Java processes. The main differences from the HotSpot `jps` tool are as follows:

- Runs on Windows&reg;, AIX&reg;, and z/OS&reg;, as well as Linux&reg;.
- Uses the Attach API.
- Shows processes on the current host only.
- There is no `-V` option.

For more information, see [`Java process status`](tool_jps.md).

#### Java stack (`jstack`) tool

Displays information about Java stack traces and thread information for processes. The main differences from the HotSpot `jstack` tool are as follows:

- In the interests of security, the OpenJ9 implementation of `jstack` prints only information about local processes that are owned by the current user.
- Printing data for core dumps is not supported. Use the [Dump viewer](tool_jdmpview.md) instead.
- There is no `-m` option. Printing data for native stack frames is not supported.
- There is no `-F` option to force a dump, although this might be accomplished using `kill -QUIT <pid>` on some platforms.

For more information, see [`jstack`](tool_jstack.md).

#### Java statistics monitoring (`jstat`) tool

Displays information about Java statistics for processes. The main difference from the HotSpot `jstat` tool is that this tool only provides the number of classes loaded and the number of class unloaded.

For more information, see [`jstat`](tool_jstat.md).

<!-- ==== END OF TOPIC ==== tools_migration.md ==== -->
