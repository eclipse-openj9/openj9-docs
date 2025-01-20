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

# Java stack (`jstack`) tool

Use the `jstack` tool to obtain Java stack traces and thread information for processes. The tool is similar to the HotSpot tool of the same name; the Eclipse OpenJ9&trade; version of `jstack` is an independent implementation, added for compatibility.

The command syntax is as follows:

    jstack <options>* <pid>*

Where `<pid>*` is a list of process IDs. If none are supplied, the process IDs are read from `stdin`, which allows a user running a Bourne or equivalent shell to query all processes via `jps -q | jstack`. IDs of inactive processes are silently ignored. The output contains Java stacks and thread information of the specified processes (equivalent to the information provided in `java.lang.management.ThreadInfo`).

The values for `<options>*` are as follows:

- `-J`: supplies arguments to the Java VM that is running the `jstack` command. You can use multiple `-J` options, for example: `jstack -J-Xmx10m -J-Dcom.ibm.tools.attach.enable=yes`
- `-p`: prints the system and agent properties of the process
- `-l`: prints more verbose output, including information about locks
- `-h`: prints help information


:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- This tool is not supported and is subject to change or removal in future releases.
- Although similar in usage and output to the HotSpot tool of the same name, this tool is a different implementation that is specific to OpenJ9. For more information about differences, see [Switching to OpenJ9](tool_migration.md).

The tool uses the Attach API, and has the following limitations:

- Displays information only for local processes that are owned by the current user, due to security considerations.
- Displays information for OpenJ9 Java processes only
- Does not show information for processes whose Attach API is disabled. :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The Attach API is disabled by default on z/OS.

For more information about the Attach API, including how to enable and secure it, see [Java Attach API](attachapi.md).

<!-- ==== END OF TOPIC ==== tool_jstack.md ==== -->
