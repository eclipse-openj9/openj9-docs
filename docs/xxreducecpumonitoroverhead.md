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

# -XX:\[+|-\]ReduceCPUMonitorOverhead

**(AIX&reg;, Linux&reg;, macOS&reg;, and Windows&trade; only)**

This option relates to the CPU usage of thread categories that can be obtained with the `com.ibm.lang.management.JvmCpuMonitorMXBean` application programming interface. This option affects the way that the VM records the amount of CPU usage of non-Garbage Collection (GC) threads that do work on behalf of GC.

Most GC policies require non-GC threads to do some GC housekeeping work in proportion to the amount of memory allocation that they do. Ideally the exact amount of CPU time that the thread spends doing this housekeeping work should be accounted for in the GC thread category. However there is an overhead that is associated with maintaining the CPU usage data in the correct thread category.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This option is not supported on z/OS&reg;. If you attempt to use this option, the following message is generated:

```
JVMJ9VM145E -XX:-ReduceCPUMonitorOverhead is unsupported on z/OS. Error: Could not create the Java Virtual Machine.
```

## Syntax

-XX:[+|-]ReduceCPUMonitorOverhead

| Setting                         | Effect  | Default                                                                            |
|---------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+ReduceCPUMonitorOverhead` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-ReduceCPUMonitorOverhead` | Disable |                                                                                    |


When you enable this option, the VM does not maintain information on the amount of CPU usage that non-GC threads spend in doing work on behalf of GC.
If you set `-XX:-ReduceCPUMonitorOverhead`, the Eclipse OpenJ9&trade; VM monitors the amount of GC work that a non-GC thread does and accounts for it in the GC category. This information is made available in the `com.ibm.lang.management.JvmCpuMonitorMXBean`. Setting this option results in a small increase in application startup time, which varies according to platform.

## See also

- [-XX:\[+|-\]EnableCPUMonitor](xxenablecpumonitor.md#xx/|-/enablecpumonitor "This option relates to the information about the CPU usage of thread categories that is available with the com.ibm.lang.management.JvmCpuMonitorMXBean application programming interface. CPU monitoring is enabled by default, and can be disabled by the command line option -XX:-EnableCPUMonitor. This option might not be supported in subsequent releases.")


<!-- ==== END OF TOPIC ==== xxreducecpumonitoroverhead.md ==== -->
