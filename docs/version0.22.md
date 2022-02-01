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


# What's new in version 0.22.0

The following new features and notable changes since version 0.21.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Performance improvements](#performance-improvements)
- [New `-XX:[+|-]PortableSharedCache` option added](#new-xx-portablesharedcache-option-added)
- [Methods in `com.ibm.lang.management.MemoryMXBean` deprecated and replaced](#methods-in-comibmlangmanagementmemorymxbean-deprecated-and-replaced)
- [![Start of content that applies to AIX Java 15+](cr/java15plus.png) `java.lang.System.mapLibraryName()` string suffix](#javalangsystemmaplibraryname-string-suffix)


## Features and changes

### Binaries and supported environments

OpenJ9 release 0.22.0 supports OpenJDK 15. Binaries are available from the AdoptOpenJDK community at the following link:

- [OpenJDK version 15](https://adoptopenjdk.net/archive.html?variant=openjdk15&jvmVariant=openj9)

OpenJDK 15 with Eclipse OpenJ9 is not a long term support (LTS) release.

The latest builds of OpenJDK with OpenJ9 for Java 8 and 11 at the AdoptOpenJDK community are for Eclipse OpenJ9 release 0.21.0. Features mentioned in these release notes are not available in these Java 8 and 11 builds. Although it might be possible to build an OpenJDK 8 or OpenJDK 11 with OpenJ9 0.22.0, testing at the project is not complete and therefore support for any of these features is not available.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Performance improvements

The performance of zero initializing Java heap memory improved on the IBM Z&reg; platform because of a change to use `memset` instead of an outdated handcrafted assembly sequence in the JVM.

### New `-XX:[+|-]PortableSharedCache` option added

On x86 only, the `-XX:[+|-]PortableSharedCache` option enables you to choose whether AOT code should be generated using an older processor (Intel&reg; Sandybridge) feature set, which therefore allows the AOT code to be portable. This feature is particularly relevant for packaging a shared classes cache into a container image (for example, applications deployed on the cloud in the form of Docker containers) because the processor on which the container image is built is likely to be different from the processor on which the container is deployed. For more information, see [`-XX:[+|-]PortableSharedCache`](xxportablesharedcache.md).

### Methods in `com.ibm.lang.management.MemoryMXBean` deprecated and replaced

The methods `com.ibm.lang.management.MemoryMXBean.getGCMasterThreadCpuUsed()` and `com.ibm.lang.management.MemoryMXBean.getGCSlaveThreadsCpuUsed()` are deprecated for removal in Java 16. The recommended methods to be used are `com.ibm.lang.management.MemoryMXBean.getGCMainThreadCpuUsed()` and `com.ibm.lang.management.MemoryMXBean.getGCWorkerThreadsCpuUsed()` respectively.

For more information see Java 8: [`com.ibm.lang.management.MemoryMXBean`](api/jdk8/jre/management/extension/com/ibm/lang/management/MemoryMXBean.html) and for Java 11: [`com.ibm.lang.management.MemoryMXBean`](api/jdk11/jdk.management/com/ibm/lang/management/MemoryMXBean.html)

### ![Start of content that applies to Java 15+](cr/java15plus.png) `java.lang.System.mapLibraryName()` string suffix

On AIX&reg; systems, `java.lang.System.mapLibraryName(libname)` returns a representation of a native library in a platform-specific string with a `.so` suffix.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.21.0 and version 0.22.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.22/0.22.md).

<!-- ==== END OF TOPIC ==== version0.22.md ==== -->
