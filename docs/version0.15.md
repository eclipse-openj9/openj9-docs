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


# What's new in version 0.15.0

 The following new features and notable changes since v.0.14.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Performance improvements for JVMTI watched fields](#performance-improvements-for-jvmti-watched-fields)
- [Support for pause-less garbage collection on IBM Z systems](#support-for-pause-less-garbage-collection-on-ibm-z-systems)
- [Support for OpenJDK HotSpot options](#support_for_openjdk_hotspot_options)


## Features and changes

### Binaries and supported environments

 OpenJ9 release 0.15.0 supports OpenJDK 8, 11, and 12.

 <!--Binaries are available from the AdoptOpenJDK community at the following links:
 - [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 12](https://adoptopenjdk.net/archive.html?variant=openjdk12&jvmVariant=openj9)-->

 To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Performance improvements for JVMTI watched fields

 OpenJ9 version 0.14.0 introduced the [`-XX:[+|-]JITInlineWatches`](xxjitinlinewatches.md) option, which, when enabled, turned on experimental JIT operations to improve the performance of JVMTI watched fields. Following successful results, this option is now enabled by default. Currently, performance improvements are enabled only on x86 platforms (Windows&reg;, macOS&reg;, and Linux).

### Support for pause-less garbage collection on IBM Z systems

Support for Concurrent scavenge mode is now extended to Linux on IBM Z&reg; systems and z/OS&reg;. For more information, see [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge).

### Support for OpenJDK HotSpot options

For compatibility, the [`-XX:OnOutOfMemoryError`](xxonoutofmemoryerror.md) OpenJDK Hotspot option is now supported by OpenJ9.

<!--## Full release information

To see a complete list of changes between Eclipse OpenJ9 V0.14.0 and V0.15.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.15/0.15.md).-->

<!-- ==== END OF TOPIC ==== version0.15.md ==== -->
