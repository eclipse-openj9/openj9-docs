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

# -XX:\[+|-\]ReduceCPUMonitorOverhead

**(AIX&reg;, Linux&trade;, macOS&reg;, and Windows&trade; only)**

This option relates to the CPU usage of thread categories that can be obtained with the `com.ibm.lang.management.JvmCpuMonitorMXBean` application programming interface. This option affects the way that the VM records the amount of CPU usage of non-Garbage Collection (GC) threads that do work on behalf of GC.

Most GC policies require non-GC threads to do some GC housekeeping work in proportion to the amount of memory allocation that they do. Ideally the exact amount of CPU time that the thread spends doing this housekeeping work should be accounted for in the GC thread category. However there is an overhead that is associated with maintaining the CPU usage data in the correct thread category.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restriction:** This option is not supported on z/OS&reg;. If you attempt to use this option, the following message is generated:

```
JVMJ9VM145E -XX:-ReduceCPUMonitorOverhead is unsupported on z/OS. Error: Could not create the Java Virtual Machine.
```

## Syntax

-XX:[+|-]ReduceCPUMonitorOverhead

| Setting                         | Effect  | Default                                                                            |
|---------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+ReduceCPUMonitorOverhead` | Enable  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| `-XX:-ReduceCPUMonitorOverhead` | Disable |                                                                                    |


When you enable this option, the VM does not maintain information on the amount of CPU usage that non-GC threads spend in doing work on behalf of GC.
If you set `-XX:-ReduceCPUMonitorOverhead`, the OpenJ9 VM monitors the amount of GC work that a non-GC thread does and accounts for it in the GC category. This information is made available in the `com.ibm.lang.management.JvmCpuMonitorMXBean`. Setting this option results in a small increase in application startup time, which varies according to platform.

## See also

- [-XX:\[+|-\]EnableCPUMonitor](xxenablecpumonitor.md#xx/|-/enablecpumonitor "This option relates to the information about the CPU usage of thread categories that is available with the com.ibm.lang.management.JvmCpuMonitorMXBean application programming interface. CPU monitoring is enabled by default, and can be disabled by the command line option -XX:-EnableCPUMonitor. This option might not be supported in subsequent releases.")


<!-- ==== END OF TOPIC ==== xxreducecpumonitoroverhead.md ==== -->
