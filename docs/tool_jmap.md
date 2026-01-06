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

# Java memory map (`jmap`) tool

Use the `jmap` tool to get memory information for a particular Java&trade; process, or list of processes. The tool shows statistics about classes on the heap, including the number of objects and their aggregate size. The command syntax is as follows:

    jmap [<option>] [<vmid>]

`<vmid>` is the Attach API virtual machine identifier for the Java process. This ID is typically the same as the operating system *process ID*, unless you specified the `-Dcom.ibm.tools.attach.id` system property when you started the process.

VMID is shown in `jps` or other Attach API-based tools. Multiple VMIDs can be specified, separated by a space.

If you do not specify a VMID, the command reads input from `stdin`. You can therefore get information for all processes by piping the output of the [`jps`](tool_jps.md) command to `jmap`:

  `jps -q | jmap -histo`

IDs of dead processes are silently ignored.

On its own, `jmap` prints help information. To obtain memory information, a `-histo` argument must be supplied, where the available `<options>` are as follows:

- `-histo`: Prints statistics about classes on the heap, including the number of objects and their aggregate size
- `-histo:live`: Prints statistics for live objects only
- `-J`: supplies arguments to the Java VM that is running the `jmap` command. You can use multiple `-J` options, for example: `jmap -J-Xms2m -J-Xmx10m`

The output has the following format:

```
num   object count     total size    class name
-------------------------------------------------
  1           3354         107328    [C
  2            717          57360    java.lang.Class
  3           2427          38832    java.lang.String
  4             50          13200    [J
  5            717          11472    java.lang.J9VMInternals$ClassInitializationLock
  6            342           8208    java.lang.StringBuilder
  7            151           7248    jdk.internal.org.objectweb.asm.Item
  8            396           6336    [Ljava.lang.Object;
```

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This tool is not supported and is subject to change or removal in future releases. Although similar in usage and output to the HotSpot tool of the same name, this tool is a different implementation that is specific to Eclipse OpenJ9&trade;.

The following tool limitations apply:

- Displays information only for local processes that are owned by the current user, due to security considerations. You can display information for remote processes by  using `ssh user@host jmap <options> <pid>`.
- Displaying data from core dumps is not supported; use `jdmpview` instead.
- Other options , such as `-F` (force a dump of an unresponsive process) can be accomplished using `kill -QUIT <pid>`.

The tool uses the Attach API, and has the following limitations:

- Displays information for OpenJ9 Java processes only
- Does not show information for processes whose Attach API is disabled. :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The Attach API is disabled by default on z/OS.

For more information about the Attach API, including how to enable and secure it, see [Java Attach API](attachapi.md).

<!-- ==== END OF TOPIC ==== tool_jmap.md ==== -->
