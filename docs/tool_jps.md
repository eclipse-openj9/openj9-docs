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

# Java process status (`jps`) tool

Use the `jps` tool to query running Java&trade; processes. The tool shows information for every Java process that is owned by the current user ID on the current host. The command syntax is as follows:

    jps [<options>]

where the available `<options>` are as follows:    


- `-J`: supplies arguments to the Java VM that is running the `jps` command. You can use multiple `-J` options, for example: `jps -J-Xmx10m -J-Dcom.ibm.tools.attach.enable=yes`
- `-l`: prints the application package name
- `-q`: prints only the virtual machine identifiers
- `-m`: prints the application arguments
- `-v`: prints the Java VM arguments, including those that are produced automatically

The output has the following format:

    <VMID> [[<class_name>|<jar_name>|"Unknown"] [<application_args>][<vm_args>]]

where `VMID` is the Attach API virtual machine identifier for the Java process. This ID is often, but not always, the same as the operating system *process ID*. One example where the ID might be different is if you specified the system property `-Dcom.ibm.tools.attach.id` when you started the process.

For example:

    $ jps -l
    5462  org.foo.bar.MyApplication
    14332 openj9.tools.attach.diagnostics.Jps

    $ jps -q
    5462
    14332

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This tool is not supported and is subject to change or removal in future releases. Although similar in usage and output to the HotSpot tool of the same name, this tool is a different implementation that is specific to Eclipse OpenJ9&trade;.

The tool uses the Attach API, and has the following limitations:

- Does not list Java processes on other hosts, to enhance security
- Does not list Java processes owned by other users
- Does not list non-OpenJ9 Java processes
- Does not list processes whose attach API is disabled. :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The Attach API is disabled by default on z/OS.

For more information about the Attach API, including how to enable and secure it, see [Java Attach API](attachapi.md).


<!-- ==== END OF TOPIC ==== tool_jps.md ==== -->
