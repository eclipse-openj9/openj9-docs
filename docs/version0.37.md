<!--
* Copyright (c) 2017, 2023 IBM Corp. and others
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
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->

# What's new in version 0.37.0

The following new features and notable changes since version 0.36.x are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [AIX is now built on AIX 7.2 TL5](#aix-is-now-built-on-aix-72-tl5)
- ![Start of content that applies to Java 19 plus](cr/java19plus.png) [Linux&reg; reference compiler updated to gcc 11.2](#linux-reference-compiler-updated-to-gcc-112)
- [Support added for the `com.sun.management.ThreadMXBean` interface](#support-added-for-the-comsunmanagementthreadmxbean-interface)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [Support for PKCS#11 token labels added on z/OS&reg; and Linux on IBM Z&reg;](#support-for-pkcs11-token-labels-added-on-zos-and-linux-on-ibm-z)
- [New message added to help find system dumps on Linux](#new-message-added-to-help-find-system-dumps-on-linux)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.37.0 supports OpenJDK 19.

RHEL 8.2 is out of support. RHEL 8.4 is the new minimum operating system level.

OpenJ9 Windows&reg; builds for OpenJDK 19 and later are now compiled with Microsoft&reg; Visual Studio 2022. The Visual Studio redistributable files included with the build are updated to match.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### AIX is now built on AIX 7.2 TL5

All AIX compiles are now moved from AIX 7.1 TL5 to AIX 7.2 TL5.

For more information, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 19 plus](cr/java19plus.png) Linux reference compiler updated to gcc 11.2

Linux builds for platforms Linux x86 64-bit, Linux on POWER LE 64-bit, and Linux on IBM Z 64-bit now use gcc 11.2 instead of gcc 10.3. Linux AArch64 64-bit continues to use the gcc 10.3 compiler. See the list of [build environments](openj9_support.md#build-environments).

### Support added for the `com.sun.management.ThreadMXBean` interface

The OpenJ9 [`ThreadMXbean` interface](https://www.eclipse.org/openj9/docs/api-langmgmt/) (`com.ibm.lang.management.ThreadMXBean`) was extending the [`java.lang.management.ThreadMXBean`](https://docs.oracle.com/javase/8/docs/api/java/lang/management/ThreadMXBean.html) interface. With this release, the OpenJ9 `ThreadMXBean` interface extends the [`com.sun.management.ThreadMXBean`](https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/ThreadMXBean.html) interface, whereby it now contains all the methods of the `java.lang.management.ThreadMXBean` interface with additional methods from the `com.sun.management.ThreadMXBean` interface.

The OpenJ9 VM implementation does not support thread memory allocation measurement (`isThreadAllocatedMemorySupported` method returns false).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Support for PKCS#11 token labels added on z/OS and Linux on IBM Z

On restarting an application, or creating or removing of tokens, the token might move to a different slot. An application that uses the `slot` or `slotListIndex` attributes might fail if it doesn’t first check which slot the token is in.

OpenJ9 now supports the use of an extra attribute, `tokenlabel`, in the SunPKCS11 configuration file on z/OS and Linux on IBM Z, which helps to avoid this issue.

For more information, see [Support for PKCS#11 token labels](enhancementstoopenjdksecurity.md#support-for-pkcs11-token-labels).

### New message added to help find system dumps on Linux

A new message, `JVMPORT049I`, is added to help find the system dump files for `kernel.core_pattern` piped programs on Linux.

For more information about system dumps and piped system dumps, see [System dumps on Linux](xdump.md#system-dumps-on-linux).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.36.x and v0.37.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.37/0.37.md).

<!-- ==== END OF TOPIC ==== version0.37.md ==== -->
