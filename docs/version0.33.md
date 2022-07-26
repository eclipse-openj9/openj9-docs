<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# What's new in version 0.33.0

The following new features and notable changes since version 0.30.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 11](cr/java11.png) [XL C++ Runtime required on AIX](#xl-c-runtime-required-on-aix)
- ![Start of content that applies to Java 17 plus](cr/java17plus.png) [Linux reference compiler updated to gcc 10.3](#linux-reference-compiler-updated-to-gcc-103)
- [The maximum number of JIT compilation threads is increased](#the-maximum-number-of-jit-compilation-threads-is-increased)
- [Control groups v2 support](#control-groups-v2-support)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.33.0 supports OpenJDK 8, 11, 17, and 18.

OpenJ9 Windows&reg; builds for OpenJDK 8 are now compiled with Microsoft&reg; Visual Studio 2017. The Visual Studio redistributable files included with the build are updated to match.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 11](cr/java11.png) XL C++ Runtime required on AIX

AIX OpenJ9 builds now require version 16.1 of the [IBM XL C++ Runtime](https://www.ibm.com/support/pages/fix-list-xl-cc-runtime-aix#161X).
This was already required for OpenJDK 17 and is now also required from 11.0.16 to accommodate a security update to the HarfBuzz text shaping library.

### ![Start of content that applies to Java 17 plus](cr/java17plus.png) Linux reference compiler updated to gcc 10.3

Linux&reg; builds for all platforms now use gcc 10.3 instead of gcc 7.5. See the list of [build environments](openj9_support.md#build-environments).

### The maximum number of JIT compilation threads is increased

The maximum number of JIT compilation threads is increased from 7 to 15. If the JITServer feature is enabled, the maximum number of JIT compilation threads is increased further on the server, to 999. You can set the number of compilation threads to be used by specifying the [`-XcompilationThreads`](xcompilationthreads.md) command line option.

### Control groups v2 support

The Linux kernel has two variants of [control groups (cgroups): v1 and v2](https://man7.org/linux/man-pages/man7/cgroups.7.html). Many Linux operating systems are gradually transitioning from cgroups v1 to v2 as their default choice. Now, OpenJ9 has added cgroups v2 support which is identical to the support for cgroups v1.

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.32.0 and v0.33.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.33/0.33.md).

<!-- ==== END OF TOPIC ==== version0.33.md ==== -->
