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

# What's new in version 0.60.0

The following new features and notable changes since version 0.59.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [The `-Xcheck:dump` option is enhanced on z/OS systems](#the-xcheckdump-option-is-enhanced-on-zos-systems) ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)
- [Compiler changes for Linux AArch64 64-bit](#compiler-changes-for-linux-aarch64-64-bit)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [New JDK Flight Recorder (JFR) events are added in this release](#new-jdk-flight-recorder-jfr-events-are-added-in-this-release) ![End of content that applies only to Java 11 and later](cr/java_close.png)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.60.0 supports OpenJDK 8, 11, 17, 21, 25, and 26.

RHEL 9.4 is out of Extended Update Support (EUS) and is removed from the list of supported platforms. RHEL 9.6 is the new minimum operating system level.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) The `-Xcheck:dump` option is enhanced on z/OS systems

The `-Xcheck:dump` option is enhanced on z/OS systems to trigger extra checking for `-Xdump:system` agents. When this option is used, the VM now validates dataset names that are specified in `-Xdump:system` options. This validation allows earlier detection of improper VM options and prevents deployment of misconfigured applications. You can ensure that when system dumps are triggered, your diagnostic dumps will be created successfully and will not fail because of invalid dataset names.

For more information, see [`-Xcheck:dump`](xcheck.md#dump). ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)

### Compiler changes for Linux AArch64 64-bit

Linux AArch64 64-bit builds on all OpenJDK versions now use the gcc 14.2 compiler.

For more information, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) New JDK Flight Recorder (JFR) events are added in this release

In this release, the following new JFR events are added:

- GCHeapSummary
- NetworkUtilization

For more information, see [`-XX:[+|-]FlightRecorder`](xxflightrecorder.md). ![End of content that applies only to Java 11 and later](cr/java_close.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.59.0 and v0.60.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.60/0.60.md).

<!-- ==== END OF TOPIC ==== version0.60.md ==== -->
