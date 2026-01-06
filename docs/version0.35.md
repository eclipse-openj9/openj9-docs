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

# What's new in version 0.35.0

The following new features and notable changes since version 0.33.1 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Java&trade; dump files contain more information about waiting threads](#java-dump-files-contain-more-information-about-waiting-threads)
- [New `-XX:[+|-]ShowNativeStackSymbols` option added](#new-xx-shownativestacksymbols-option-added)
- [New `user2` event added for the `-Xdump` option](#new-user2-event-added-for-the-xdump-option)
- [New `-XX:[+|-]PerfTool` option added](#new-xx-perftool-option-added)
- [New default options added in the `options.default` file](#new-default-options-added-in-the-optionsdefault-file)
- [New options added to encrypt the JITServer exported metrics](#new-options-added-to-encrypt-the-jitserver-exported-metrics)
- ![Start of content that applies to Java 11 and later](cr/java11plus.png) [XL C++ Runtime 16.1.0.7 or later required on AIX](#xl-c-runtime-16107-or-later-required-on-aix)
- [`-XX:[+|-]JITServerLocalSyncCompiles` enabled by default](#-xx-jitserverlocalsynccompiles-enabled-by-default)
- ![Start of content that applies to Java 8](cr/java8.png) [Support for changes to the maintenance specifications of JSR 337 in Java 8](#support-for-changes-to-the-maintenance-specifications-of-jsr-337-in-java-8)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.35.0 supports OpenJDK 8, 11, and 17.

OpenJ9 Windows&reg; builds for OpenJDK 11 are now compiled with Microsoft&reg; Visual Studio 2019. The Visual Studio redistributable files included with the build are updated to match.

Apple silicon macOS&reg; is now a fully supported, production-ready target for OpenJDK 11 and later.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Java dump files contain more information about waiting threads

For threads that are waiting for a class initialization lock, the Java dump output now shows the thread that is currently working to progress the initialization of the class. This thread is indicated by the new `Initializing thread: <thread_name>` string in the existing `3XMTHREADBLOCK` line in the Java dump output. For example:

`3XMTHREADBLOCK Waiting on: java/lang/J9VMInternals$ClassInitializationLock@0x00000000FFF5DC90 Owned by: <unowned> Initializing thread: "Class Initialization Thread 1"`

For more information, see [Threads](dump_javadump.md#threads).

### New `-XX:[+|-]ShowNativeStackSymbols` option added

This option controls whether Java dumps show the names of functions in native call stacks.

For more information, see [`-XX:[+|-]ShowNativeStackSymbols`](xxshownativestacksymbols.md).

### New `user2` event added for the `-Xdump` option

On operating systems other than Windows&trade;, you can now use the `user2` event for the `-Xdump` option. This event is triggered when the VM receives the `SIGUSR2` signal.

There is a change in the `SIGUSR2` signal behavior as well whereby, the process does not exit in response to this signal.

For more information, see [`-Xdump`](xdump.md#dump-events) and [Signal handling](openj9_signals.md).

### New `-XX:[+|-]PerfTool` option added

This option enables or disables the JIT support for the `perf` tool without affecting the existing `Xjit` options.

Since this option creates a file that is used by the Linux&reg; system profiler, `perf`, it applies only to Linux.

For more information, see [`-XX:[+|-]PerfTool`](xxperftool.md).

### New default options added in the `options.default` file

`-XX:+EnsureHashed:java/lang/Class,java/lang/Thread` is added to the list of default options in the `options.default` file to improve performance.

For more information, see [`XX:[+|-]EnsureHashed`](xxensurehashed.md).

### New options added to encrypt the JITServer exported metrics

You can use the [`-XX:JITServerMetricsSSLKey`](xxjitservermetricssslkey.md) and [`-XX:JITServerMetricsSSLCert`](xxjitservermetricssslkey.md) options to encrypt the custom metrics with TLS or SSL.

### ![Start of content that applies to Java 11 and later](cr/java11plus.png) XL C++ Runtime 16.1.0.7 or later required on AIX

AIX OpenJ9 builds now require version 16.1.0.7 or later of the [IBM XL C++ Runtime](https://www.ibm.com/support/pages/fix-list-xl-cc-runtime-aix#161X).

### `-XX:[+|-]JITServerLocalSyncCompiles` enabled by default

The `-XX:[+|-]JITServerLocalSyncCompiles` option is now enabled in most cases. For more information, see [`-XX:[+|-]JITServerLocalSyncCompiles`](xxjitserverlocalsynccompiles.md).

### ![Start of content that applies to Java 8](cr/java8.png) Support for changes to the maintenance specifications of JSR 337 in Java 8

OpenJ9 now supports changes to the maintenance specifications in Java 8 as listed in the [JSR 337 Maintenance Release 4](https://jcp.org/aboutJava/communityprocess/maintenance/jsr337/jsr337-mr4-changes.html) document.

These changes include the change in the garbage collector behavior for handling the phantom references in Java 8. The garbage collector handles both the phantom as well as the weak references similarly, which is the current behavior in Java 11 and later. For more information, see [Weak reference processing](gc_overview.md#weak-reference-processing).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.33.1 and v0.35.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.35/0.35.md).

<!-- ==== END OF TOPIC ==== version0.35.md ==== -->
