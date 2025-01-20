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

# Java diagnostic command (`jcmd`) tool

Use the `jcmd` tool to run diagnostic commands on a specified VM.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Running diagnostic commands can significantly affect the performance of the target VM.

The command syntax is as follows:

    jcmd [<options>] [<vmid | display name | 0> <arguments>]

Where:

- The available `<options>` are:
    - `-l`: lists current Java&trade; processes recognized by the `jcmd` tool. The list includes VMID, which is usually the *process ID* (pid) and the display name, which refers to the target Java VM process that can be attached by `jcmd`. `-l` is the default option, therefore specifying `jcmd` without any options also displays the VMIDs.
    - `-J`: supplies arguments to the Java VM that is running the `jcmd` command. You can use multiple `-J` options, for example: `jcmd -J-Xmx10m -J-Dcom.ibm.tools.attach.enable=yes`
    - `-h`: prints the `jcmd` help

- `<vmid>` is the Attach API virtual machine identifier for the Java VM process. This ID is often, but not always, the same as the operating system pid. One example where the ID might be different is if you specified the system property `-Dcom.ibm.tools.attach.id` when you started the process. In addition to the `jcmd` command, you can also use the [`jps`](tool_jps.md) command to find the VMID.

    You can also specify the full or partial target Java process display name instead of the VMID. The `jcmd` tool finds the corresponding VMID of the display name and runs the `jcmd` command.

    You can specify the display name for a target VM through the [`com.ibm.tools.attach.displayName`](dcomibmtoolsattachdisplayname.md) system property. If the display name is not set through the system property, then the main class name along with the application arguments is set as the default display name.

    If you specify `0`, the `jcmd` command is sent to all Java processes that are detected by the current `jcmd` command.

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

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This tool is not supported and is subject to change or removal in future releases. Although similar in usage and output to the HotSpot tool of the same name, this tool is a different implementation that is specific to Eclipse OpenJ9&trade;. For information about the differences between these two implementations, see [Switching to OpenJ9](tool_migration.md).

The tool uses the Attach API, and has the following limitations:

- Displays information only for local processes that are owned by the current user, due to security considerations.
- Displays information for OpenJ9 Java processes only
- Does not show information for processes whose Attach API is disabled. :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The Attached API is disabled by default on z/OS.

For more information about the Attached API, including how to enable and secure it, see [Java Attach API](attachapi.md).

## See Also

-  [What's new in version 0.44.0](version0.44.md#vmid-query-in-the-jcmd-tool-enhanced)


<!-- ==== END OF TOPIC ==== tool_jcmd.md ==== -->
