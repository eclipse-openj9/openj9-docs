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

# What's new in version 0.59.0

The following new features and notable changes since version 0.58.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [On AIX systems, `LDR_CNTRL` is set by default, and the `-XX:[+|-]UseMediumPageSize` option is used to control this default setting](#on-aix-systems-ldr_cntrl-is-set-by-default-and-the-xx-usemediumpagesize-option-is-used-to-control-this-default-setting)
- [A signal handler optimization feature that was disabled on Windows&trade; is enabled again](#a-signal-handler-optimization-feature-that-was-disabled-on-windows-is-enabled-again)
- [Compiler changes for Linux&reg;](#compiler-changes-for-linux)
- ![Start of content that applies to Java 8 (LTS)](cr/java8.png) ![Start of content that applies to Java 11 (LTS)](cr/java11.png) [glibc version is changed to 2.17 on Linux x86 64-bit builds for OpenJDK 8 and 11](#glibc-version-is-changed-to-217-on-linux-x86-64-bit-builds-for-openjdk-8-and-11) ![End of content that applies to Java 8 and 11 (LTS)](cr/java_close_lts.png)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [New `-XX:StartFlightRecording` command-line option is added](#new-xxstartflightrecording-command-line-option-is-added) ![End of content that applies to Java 8 and 11 (LTS)](cr/java_close_lts.png)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [New JDK Flight Recorder (JFR) events are added in this release](#new-jdk-flight-recorder-jfr-events-are-added-in-this-release) ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)
- [`compact` is added by default to the `request=<requests>` parameter for the `jcmd Dump.heap` command](#compact-is-added-by-default-to-the-requestrequests-parameter-for-the-jcmd-dumpheap-command)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.59.0 supports OpenJDK 8, 11, 17, 21, 25, and 26.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### On AIX systems, `LDR_CNTRL` is set by default, and the `-XX:[+|-]UseMediumPageSize` option is used to control this default setting

With the new option, `-XX:-UseMediumPageSize `, you can now disable the default setting of the `LDR_CNTRL` environment variable on AIX systems whereby medium page sizes (64 KB) are configured for text, data, stack, and shared memory segments.

For more information, see [`-XX:[+|-]UseMediumPageSize`](xxusemediumpagesize.md).

### A signal handler optimization feature that was disabled on Windows is enabled again

In the [0.57.0 release](version0.57.md#a-signal-handler-optimization-feature-is-temporarily-disabled), on the Windows platform, a signal handler optimization feature was temporarily disabled by default. The feature was disabled because VM crashes were suspected to be related to an interaction between the Windows control flow guard feature and the VM's signal handling mechanism.

Now the root cause for these VM crashes has been identified and fixed. Therefore, in this release, the disabled signal handler optimization feature is enabled again.

### Compiler changes for Linux

Linux x86 64-bit, Linux on POWER&reg; LE 64-bit, and Linux on IBM Z&reg; 64-bit builds on all OpenJDK versions now use the gcc 14.2 compiler.

For more information, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 8 (LTS)](cr/java8.png) ![Start of content that applies to Java 11 (LTS)](cr/java11.png) glibc version is changed to 2.17 on Linux x86 64-bit builds for OpenJDK 8 and 11

For OpenJDK versions 8 and 11, Linux x86 64-bit is now compiled on CentOS 7, modifying the minimum glibc version to 2.17 from 2.12. The VM will fail to start on CentOS 6 from this release onwards. ![End of content that applies to Java 8 and 11 (LTS)](cr/java_close.png)

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) New `-XX:StartFlightRecording` command-line option is added

Support for the `-XX:StartFlightRecording` command-line option is now available from this release onwards. Instead of the JDK Flight Recorder (JFR)-related `jcmd` option, you can now use the `-XX:StartFlightRecording` command-line option to start the JFR recording in the VM.

The `-XX:StartFlightRecording` command-line option cannot be used simultaneously with JFR-related `jcmd` options.

For more information, see [`-XX:StartFlightRecording`](xxstartflightrecording.md).

 ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) New JDK Flight Recorder (JFR) events are added in this release

In this release, the following new JFR events are added:

- GarbageCollection
- OldGarbageCollection
- YoungGarbageCollection

For more information, see [`-XX:[+|-]FlightRecorder`](xxflightrecorder.md). ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)

### `compact` is added by default to the `request=<requests>` parameter for the `jcmd Dump.heap` command

When `jcmd Dump.heap` is used to request a heap dump, the `compact` option is now added to the dump request by default. The default request for heap dumps is now: `request=exclusive+compact+prepwalk`.

For more information, see `-Xdump` suboptions: [`request=<requests>`](xdump.md#requestrequests).

For more information about `jcmd`, see [Java diagnostic command (jcmd) tool](tool_jcmd.md).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.58.0 and v0.59.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.59/0.59.md).

<!-- ==== END OF TOPIC ==== version0.59.md ==== -->
