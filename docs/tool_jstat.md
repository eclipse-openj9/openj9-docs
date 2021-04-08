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

# Java statistics monitoring (`jstat`) tool

Use the `jstat` tool to obtain Java Virtual Machine (JVM) statistics. The tool is similar to the HotSpot tool of the same name; the OpenJ9 version of `jstat` is an independent implementation, added for compatibility.

The command syntax is as follows:

    jstat [<option>] [<vmid>]

where `vmid` is the Attach API virtual machine identifier for the Java process. This ID is typically the same as
the operating system *process ID*, unless you specified the [-Dcom.ibm.tools.attach.id](dcomibmtoolsattachid.md) system property when you started the process.

VMID is shown in [Java process status (jps) tool](tool_jps.md) or other Attach API-based tools.

On its own, `jstat` prints help information. The values for `<option>` are as follows:

- `-J`: supplies arguments to the JVM that is running the `jstat` command. You can use multiple `-J` options, for example: `jstat -J-Xmx10m -J-Dcom.ibm.tools.attach.enable=yes`
- `-h`: prints help information
- `-options`: lists the available command options
- `-class`: displays classloading statistics


The output has the following format:

```
Class Loaded    Class Unloaded
         860                 0
```

:fontawesome-solid-exclamation-triangle:{: .warn aria-hidden="true"} **Restrictions:**

- This tool is not supported and is subject to change or removal in future releases.
- Although similar in usage and output to the HotSpot tool of the same name, this tool is a different implementation that is specific to OpenJ9. For more information about differences, see [Switching to OpenJ9](tool_migration.md).

<!-- ==== END OF TOPIC ==== tool_jstat.md ==== -->

