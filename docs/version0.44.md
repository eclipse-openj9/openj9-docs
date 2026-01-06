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

# What's new in version 0.44.0

The following new features and notable changes since version 0.43.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Change in behavior of the `-Djava.security.manager` system property for OpenJDK version 8](#change-in-behavior-of-the-djavasecuritymanager-system-property-for-openjdk-version-8)
- ![Start of content that applies to Java 21 (LTS)](cr/java21.png) [Display of multiple warnings on loading the same agent restricted on AIX&reg; systems](#display-of-multiple-warnings-on-loading-the-same-agent-restricted-on-aix-systems) ![End of content that applies to Java 21 (LTS)](cr/java_close_lts.png)
- [XL C++ Runtime 16.1.0.7 or later required for AIX OpenJ9 builds on OpenJDK 8](#xl-c-runtime-16107-or-later-required-for-aix-openj9-builds-on-openjdk-8)
- [New `-XX:[+|-]ShowUnmountedThreadStacks` option added](#new-xx-showunmountedthreadstacks-option-added)
- [VMID query in the `jcmd` tool enhanced](#vmid-query-in-the-jcmd-tool-enhanced)
- [DDR field names in `J9BuildFlags` changed](#ddr-field-names-in-j9buildflags-changed)
- [New system property added to prevent the deletion of the Attach API control files within the `/tmp/` folder](#new-system-property-added-to-prevent-the-deletion-of-the-attach-api-control-files-within-the-tmp-folder)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.44.0 supports OpenJDK 8, 11, 17, and 21.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Change in behavior of the `-Djava.security.manager` system property for OpenJDK version 8

From OpenJDK version 18 onwards, if you enable the `SecurityManager` at runtime by calling the `System.setSecurityManager()` API, you must set the `-Djava.security.manager=allow` option. To disable the `SecurityManager`, you must specify the `-Djava.security.manager=disallow` option. If an application is designed to run on multiple OpenJDK versions, the same command line might be used across multiple versions. Because of this use of the same command line across multiple versions, in OpenJDK versions before version 18, the runtime attempts to load a `SecurityManager` with the class name `allow` or `disallow` resulted in an error and the application was not starting.

To resolve this issue, OpenJDK version 17 ignores these options and from 0.41.0 release onwards, OpenJDK version 11 also ignores these options. With this release, OpenJDK version 8 too ignores the `allow` and `disallow` keywords, if specified.

### ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) Display of multiple warnings on loading the same agent restricted on AIX systems

Earlier, on AIX systems, warnings were issued each time the agents were loaded dynamically into a running VM after startup without specifying the `-XX:+EnableDynamicAgentLoading` option, even if the same agents were loaded before.

Now, from 0.44.0 release onwards, AIX systems, like other OpenJ9 supported operating systems, can detect whether an agent was previously loaded or not. Therefore, like other platforms, on AIX systems also, the warnings are issued only once for the same agent when the `-XX:+EnableDynamicAgentLoading` option is not specified.

For more information, see [`-XX:[+|-]EnableDynamicAgentLoading`](xxenabledynamicagentloading.md). ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

### XL C++ Runtime 16.1.0.7 or later required for AIX OpenJ9 builds on OpenJDK 8

AIX OpenJ9 builds now require version 16.1.0.7 or later of the [IBM XL C++ Runtime](https://www.ibm.com/support/pages/fix-list-xl-cc-runtime-aix#161X) on OpenJDK 8 as well.

### ![Start of content that applies to Java 21 (LTS)](cr/java21.png) New `-XX:[+|-]ShowUnmountedThreadStacks` option added

Java&trade; core file lists stacks of only those threads that are mapped to platform threads. An unmounted virtual thread is not mapped to any platform thread. Therefore, the stack of any unmounted virtual thread is not included in the Java core file and thus, the virtual thread information remains incomplete.

You can use the `-XX:+ShowUnmountedThreadStacks` option to include all the thread data that a VM is aware of, both regular Java threads and the unmounted virtual threads, in a Java core file.

For more information, see [` -XX:[+|-]ShowUnmountedThreadStacks`](xxshowunmountedthreadstacks.md). ![End of content that applies to Java 21 (LTS)](cr/java_close_lts.png)

### VMID query in the `jcmd` tool enhanced

Earlier in OpenJ9, when sending a `jcmd` command to a VM, you had to run `jcmd -l` to retrieve all the pids for all the VMs found on the machine. Then, you had to use `jcmd [vmid] [command]` to send the command to the specific VM.

For OpenJDK compatibility, OpenJ9 now supports direct use of the Java process name, full or partial, as the ID to send the `jcmd` command.

The `jcmd` tool also now supports specifying `0` as a VMID to target all VMs.

For more information, see [Java diagnostic command (`jcmd`) tool](tool_jcmd.md).

### DDR field names in `J9BuildFlags` changed

The Direct Dump Reader (DDR) code enables reading system dump data by using the OpenJ9
Diagnostic Tool Framework for Java (DTFJ) API or the [`jdmpview`](tool_jdmpview.md) tool.
DDR code uses fields of `J9BuildFlags` to access build flags in the system dump data.
The names of `J9BuildFlags` fields changed over time and therefore, supporting system dumps with different build flags became a challenge.

Earlier, field names in `J9BuildFlags` were based on names defined in `j9.flags`. Now, when the `J9BuildFlags` is generated for each build, the flag names are those names that are specified in `j9cfg.h` (derived from `j9cfg.h.ftl` or `j9cfg.h.in`) instead of the names that are defined in `j9.flags`. For example, `env_data64` is now referred to as `J9VM_ENV_DATA64`.

You can extend the DDR code, adding your own commands, by writing plug-ins. If the user plug-in code contains references to fields of `J9BuildFlags` to read the build flags in the system dump data, you must change references to use the names as specified in `j9cfg.h`.

### New system property added to prevent the deletion of the Attach API control files within the `/tmp/` folder

You can use the `-Dcom.ibm.tools.attach.fileAccessUpdateTime` system property to prevent Linux&reg; `systemd-tmpfiles` from deleting the Attach API control files within the `/tmp/` folder. By updating the Attach API control file access times to avoid deletion by `systemd-tmpfiles`, the long-running Attach API can continue to use the control files to operate. This system property enables Attach API to update the control file access times at specific intervals.

For more information, see [`-Dcom.ibm.tools.attach.fileAccessUpdateTime`](dcomibmtoolsattachfileaccessupdatetime.md).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.43.0 and v0.44.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.44/0.44.md).

<!-- ==== END OF TOPIC ==== version0.44.md ==== -->
