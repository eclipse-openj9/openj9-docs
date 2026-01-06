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


# What's new in version 0.23.0

The following new features and notable changes since version 0.22.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [`-XX:[+|-]PortableSharedCache` option behavior update](#-xx-portablesharedcache-option-behavior-update)
- [`-XX:[+|-]IdleTuningCompactOnIdle` option now inactive](#-xx-idletuningcompactonidle-option-now-inactive)
- [Support for OpenJDK HotSpot options](#support-for-openjdk-hotspot-options)
- [Extended platform support for the JITServer technology preview](#extended-platform-support-for-the-jitserver-technology-preview)


## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.23.0 supports OpenJDK 8, 11, and 15. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 15](https://adoptopenjdk.net/archive.html?variant=openjdk15&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### `-XX:[+|-]PortableSharedCache` option behavior update

The `-XX:[+|-]PortableSharedCache` option is updated to improve the portability of AOT-compiled code further. This update allows AOT-compiled code to be portable across OpenJ9 VMs that use compressed references and have a heap size of 1 MB to 28 GB when this option is enabled. This option might introduce a small (1-2%) steady-state throughput penalty when compressed references are used and the heap size is between 1 MB and 3 GB. See [`-XX:[+|-]PortableSharedCache`](xxportablesharedcache.md) for more details about this option.

### `-XX:[+|-]IdleTuningCompactOnIdle` option now inactive

Setting the `-XX:[+|-]IdleTuningCompactOnIdle` option now has no effect. A compaction is triggered by internal heuristics that look into the number of fragmented pages. Typically there is no need to force a compaction. This option was deprecated in release 0.15.0, and will be removed in the future. See [`-XX:[+|-]IdleTuningCompactOnIdle`](xxidletuningcompactonidle.md) for details about this option.

### Support for OpenJDK HotSpot options

For compatibility, the following OpenJDK HotSpot options are now supported by OpenJ9:

- [`-XX:[+|-]AlwaysPreTouch`](xxalwayspretouch.md)

### Extended platform support for the JITServer technology preview

Platform support for the JITServer technology preview is now extended to 64-bit Linux&reg; on IBM Power&reg; systems, and 64-bit Linux on IBM Z&reg; systems. JITServer decouples the JIT compiler from the OpenJ9 VM, freeing up CPU and memory for an application. JITServer runs in its own process, either locally or on a remote machine, where resources can be separately managed. This preview was initially introduced in Eclipse OpenJ9 V0.18.1 for Linux on 64-bit x86 systems. For more information, see [JITServer technology (technical preview)](jitserver.md).

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.22.0 and version 0.23.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.23/0.23.md).

<!-- ==== END OF TOPIC ==== version0.23.md ==== -->
