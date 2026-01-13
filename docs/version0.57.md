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

# What's new in version 0.57.0

The following new features and notable changes since version 0.56.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [The `zlib` library bundled on all Linux platforms except Linux on IBM Z](#the-zlib-library-bundled-on-all-linux-platforms-except-linux-on-ibm-z)
- [The signaling process ID and process name are recorded and reported in tracepoints and javacore](#the-signaling-process-id-and-process-name-are-recorded-and-reported-in-tracepoints-and-javacore)
- [A signal handler optimization feature is temporarily disabled](#a-signal-handler-optimization-feature-is-temporarily-disabled)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.57.0 supports OpenJDK 8, 11, 17, 21, and 25.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### The `zlib` library bundled on all Linux platforms except Linux on IBM Z

The `zlib` compression library is now bundled on all Linux&reg; platforms except Linux on IBM Z&reg;. You don't have to specifically install the `zlib` library with the operating system for data compression and decompression.

For more information on hardware-accelerated data compression and decompression, see [Hardware acceleration](introduction.md#hardware-acceleration).

### The signaling process ID and process name are recorded and reported in tracepoints and javacore

On all platforms except Windows&trade;, when an asynchronous signal `SIGABRT`, `SIGQUIT`, or `SIGUSR2` occurs, the process ID (pid) and the name of the process that sent the signal is recorded and reported in a tracepoint and in the javacore.

The `SIGINT`, `SIGHUP`, and `SIGTERM` signals create a tracepoint that includes the pid but these signals do not create javacore files.

For more information, see [Signal handling](openj9_signals.md).

### A signal handler optimization feature is temporarily disabled

On the Windows platform, a feature that uses the x86-64 hardware and signal handler for improving the performance of null object checks and integer division overflows is temporarily disabled by default.

Recent VM crashes that were observed on Windows 11 and Windows Server 2022 are suspected to be related to an interaction between the Windows control flow guard feature and the VM's signal handling mechanism. Disabling this signal handling optimization feature is a temporary measure while the root cause of the problem is determined. A reliable workaround cannot be recommended until the root cause is known.

Depending on the workload, performance degradation from 0-10% on Windows was observed with this feature disabled.

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.56.0 and v0.57.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.57/0.57.md).

<!-- ==== END OF TOPIC ==== version0.57.md ==== -->
