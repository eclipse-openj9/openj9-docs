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

# Java diagnostic command (`jcmd`) tool

Use the `jcmd` tool to run diagnostic commands on a specified VM.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Running diagnostic commands can significantly affect the performance of the target VM.

The command syntax is as follows:

    jcmd [<options>] [<vmid> <arguments>]

Where:

- The available `<options>` are:    
    - `-J`: supplies arguments to the Java VM that is running the `jcmd` command. You can use multiple `-J` options, for example: `jcmd -J-Xmx10m -J-Dcom.ibm.tools.attach.enable=yes`
    - `-h`: prints the `jcmd` help

- `<vmid>` is the Attach API virtual machine identifier for the Java&trade; VM process. This ID is often, but not always, the same as the operating system *process ID*. One example where the ID might be different is if you specified the system property `-Dcom.ibm.tools.attach.id` when you started the process. You can use the [`jps`](tool_jps.md) command to find the VMID.

- The available `arguments` are:

    - `help`: shows the diagnostic commands that are available for this VM. This list of commands can vary between VMs.
    - `help <command>`: shows help information for the specified diagnostic command
    - `<command> [<command_arguments>]`: runs the specified diagnostic command, with optional command arguments

Examples:

```
jcmd 31452 Thread.print
jcmd 31452 help Dump.heap
jcmd 31452 Dump.heap myHeapDump
```

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restrictions:** This tool is not supported and is subject to change or removal in future releases. Although similar in usage and output to the HotSpot tool of the same name, this tool is a different implementation that is specific to OpenJ9. For information about the differences between these two implementations, see [Switching to OpenJ9](tool_migration.md).

The tool uses the Attach API, and has the following limitations:

- Displays information only for local processes that are owned by the current user, due to security considerations.
- Displays information for OpenJ9 Java processes only
- Does not show information for processes whose Attach API is disabled. <i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** The Attach API is disabled by default on z/OS.

For more information about the Attach API, including how to enable and secure it, see [Support for the Java Attach API](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/attachapi.html).


<!-- ==== END OF TOPIC ==== tool_jcmd.md ==== -->
