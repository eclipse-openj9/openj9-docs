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

# System dump

System dumps, often known as *core dumps*, are platform-specific and contain a raw binary dump of the process memory. This type of dump has a complete copy of the Java heap, including the contents of all Java objects in the application.

## Obtaining system dumps

System dumps are produced in response to specific events. To discover which events generate a dump, run the `-Xdump:what` command. The following output captures the information shown for a system dump:

```
-Xdump:system:
    events=gpf+abort+traceassert+corruptcache,
    label=/home/user/core.%Y%m%d.%H%M%S.%pid.%seq.dmp,
    range=1..0,
    priority=999,
    request=serial
```

This output shows that events such as a general protection fault (gpf) or native `abort()` call can trigger a system dump. For more information about controlling the behavior of dump agents, see [Dump agents](xdump.md#dump-agents).

### Enabling a full system dump (AIX, Linux, and macOS)

If you require a system dump that contains details of all the native threads that are running, you must change the resource limits for your operating system. Otherwise, the native thread details that are stored in the dump are only for the native thread that was running when the VM ended.

1. Set the system resource limits by running the following commands:
   ```
   ulimit -c unlimited; ulimit -n unlimited; ulimit -d unlimited; ulimit -f unlimited;
   ```
  
    Where:

   - `-c` sets core files
   - `-n` sets the number of open files
   - `-d` sets the data limit
   - `-f` sets the file limit

2. For AIX systems, use the system management interface tool (SMIT) to enable a full CORE dump that is not a *pre-430 style CORE dump*. You can also set this configuration with the following command line option:

  ```
  chdev -l sys0 -a fullcore='true' -a pre430core='false'
  ```

## Analyzing a system dump

To examine a system dump you can use the [Dump viewer](tool_jdmpview.md) (`jdmpview`), a platform-specific debugging tool, or the [Eclipse Memory Analyzer&trade; tool](https://www.eclipse.org/mat/) (MAT).

If you want to use MAT to analyze your system dump, you must install the  Diagnostic Tool Framework for Java (DTFJ) plug-in in the Eclipse IDE. Select the following menu items:

```
Help > Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and Diagnostic Tools > Diagnostic Tool Framework for Java   
```

<!-- ==== END OF TOPIC ==== dump_systemdump.md ==== -->
