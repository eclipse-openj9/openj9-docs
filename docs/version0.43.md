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

# What's new in version 0.43.0

The following new features and notable changes since version 0.42.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Compiler changes for Linux&reg;](#compiler-changes-for-linux)
- [Change in the large page memory allocation behavior](#change-in-the-large-page-memory-allocation-behavior)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [New `-XX:[+|-]CRIUSecProvider` option added](#new-xx-criusecprovider-option-added) ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)
- [New `-XX:Compatibility` option added](#new-xxcompatibility-option-added)
- [New `-XX:[+|-]CpuLoadCompatibility` option added](#new-xx-cpuloadcompatibility-option-added)
- [Support added for the `com.sun.management.ThreadMXBean.getThreadAllocatedBytes()` API on z/OS platforms](#support-added-for-the-comsunmanagementthreadmxbeangetthreadallocatedbytes-api-on-zos-platforms)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.43.0 supports OpenJDK 8, 11, 17, and 21.

OpenJ9 Windows&reg; builds for OpenJDK 8, 11, and 17 are now compiled with Microsoft&reg; Visual Studio 2022. The Visual Studio redistributable files included with the build are updated to match.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Compiler changes for Linux

Linux x86 64-bit, Linux on POWER&reg; LE 64-bit, and Linux on IBM Z&reg; 64-bit builds on OpenJDK 8, 11, and 17 now use gcc 11.2 compiler. Linux AArch64 64-bit moved to gcc 10.3 compiler from gcc 7.5 compiler on OpenJDK 8 and 11.

On OpenJDK 19 and later, the Linux reference compiler was already updated to gcc 11.2 in [release 0.37.0](version0.37.md).

For more information, see [Supported environments](openj9_support.md).

### Change in the large page memory allocation behavior

Earlier, the JIT code cache was allocated memory as a multiple of the available page size even if the configured large page size was greater than the total JIT code cache size.

Now, if the configured large page size is greater than the size of the total code cache for JIT, then the page size that is used for code cache allocation is recalculated. The next available lower page size on the platform is used for the code cache allocation.

For more information, see [`-Xlp:codecache`](xlpcodecache.md).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) New `-XX:[+|-]CRIUSecProvider` option added

When you enable CRIU support, all the existing security providers are removed from the security provider list during the checkpoint phase and `CRIUSECProvider` is added by default.

You can now control the use of `CRIUSECProvider` during the checkpoint phase with the `-XX:[+|-]CRIUSecProvider` option. You can use all the existing security providers instead of the `CRIUSECProvider` by specifying the `-XX:-CRIUSecProvider` option.

For more information, see [`-XX:[+|-]CRIUSecProvider`](xxcriusecprovider.md). ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)

### New `-XX:Compatibility` option added

The Elasticsearch application was facing incompatibility issues when it was running on OpenJ9 and required many workarounds. With the `-XX:Compatibility` option, you can enable a compatibility mode that OpenJ9 can run in to support applications that require specific capabilities.

In release 0.43.0, the compatibility mode is provided for the Elasticsearch application only.

For more information, see [`-XX:Compatibility`](xxcompatibility.md).

### New `-XX:[+|-]CpuLoadCompatibility` option added

The `getProcessCpuLoad()` and `getSystemCpuLoad()` methods were returning `-1` to indicate that the recent CPU usage is not available when these methods were called in OpenJ9 for the first time. It was difficult to identify whether the reason for the `-1` value was an error or because the call was the first call and therefore, no recent CPU usage was available.

In OpenJDK, these methods return `0` value in the case of the first call, which makes it easier to differentiate between the first call behavior and an error that needs further investigation.

The `-XX:+CpuLoadCompatibility` option is used to enable the OpenJDK behavior of the `getProcessCpuLoad()` and `getSystemCpuLoad()` methods in OpenJ9 so that these methods return `0` when called in OpenJ9 for the first time.

For more information, see [`-XX:[+|-]CpuLoadCompatibility`](xxcpuloadcompatibility.md).

### Support added for the `com.sun.management.ThreadMXBean.getThreadAllocatedBytes()` API on z/OS platforms

With this release, support for the `com.sun.management.ThreadMXBean.getThreadAllocatedBytes()` API is added on z/OS&reg; platforms as well.

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.42.0 and v0.43.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.43/0.43.md).

<!-- ==== END OF TOPIC ==== version0.43.md ==== -->
