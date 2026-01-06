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


# What's new in version 0.20.0

The following new features and notable changes since version 0.19.0 are included in this release:

- [Binaries and supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 11](cr/java11.png) [Limited support for 64-bit Linux on ARM](#limited-support-for-64-bit-linux-on-arm)
- [`-XX:[+|-]ExitOnOutOfMemoryError` option behavior update](#-xx-exitonoutofmemoryerror-option-behavior-update)
- [New `-XX:[+|-]GlobalLockReservation` option added](#new-xx-globallockreservation-option-added)
- ![Start of content that applies to Java 8](cr/java8.png) [Change to default maximum heap size for Java 8](#change-to-default-maximum-heap-size-for-java-8)
- [Change to `jcmd` default options](#change-to-jcmd-default-options)



## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.20.0 supports OpenJDK 8, 11, and 14. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 14](https://adoptopenjdk.net/archive.html?variant=openjdk14&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

###  ![Start of content that applies to Java 11](cr/java11.png) Limited support for 64-bit Linux on ARM

Limited support is available in this release for the 64-bit ARM (AArch64) architecture. An early access build on OpenJDK 11 is available from the
[AdoptOpenJDK community](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9). See the [OpenJ9 release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.20/0.20.md) for any known issues that are still being worked on before this
platform is fully supported.  

### `-XX:[+|-]ExitOnOutOfMemoryError` option behavior update

The `-XX:[+|-]ExitOnOutOfMemoryError` option is updated to exit only on VM `OutOfMemoryErrors` instead of both VM and Java&trade; thrown errors to match the HotSpot option. See [`-XX:[+|-]ExitOnOutOfMemoryError`](xxexitonoutofmemoryerror.md) for more details about this option.

### New `-XX:[+|-]GlobalLockReservation` option added

**(AIX&reg; and Linux on Power Systems&trade; only)**

Option `-XX:[+|-]GlobalLockReservation` enables a new optimization targeted towards more efficient handling of locking and unlocking Java  objects. See [`-XX:[+|-]GlobalLockReservation`](xxgloballockreservation.md) for more details about this option.

### ![Start of content that applies to Java 8](cr/java8.png) Change to default maximum heap size for Java 8

For consistency with Java 11, the default maximum heap size (`-Xmx`) is changed to be 25% of the available memory with a maximum of 25 GB.
Where there is 2 GB or less of physical memory, the value set is 50% of available memory with a minimum value of 16 MB and a maximum value of 512 MB. If you want to revert to the default setting in earlier releases of OpenJ9, use the [-XX:+OriginalJDK8HeapSizeCompatibilityMode](xxoriginaljdk8heapsizecompatibilitymode.md) option.

### Change to `jcmd` default options

The Java diagnostic command (`jcmd`) tool no longer requires a filename when used with the `Dump.java`, `Dump.snap`, or `Dump.system` options. See [`jcmd`](tool_jcmd.md) for more information about the tool.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.19.0 and version 0.20.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.20/0.20.md).

<!-- ==== END OF TOPIC ==== version0.20.md ==== -->
