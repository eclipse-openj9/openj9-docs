<!--
* Copyright (c) 2017, 2025 IBM Corp. and others
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


# What's new in version 0.15.1

 The following new features and notable changes since version 0.14.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Performance improvements for JVMTI watched fields](#performance-improvements-for-jvmti-watched-fields)
- [Support for pause-less garbage collection on IBM Z systems](#support-for-pause-less-garbage-collection-on-ibm-z-systems)
- ![Start of content that applies only to Java 11 (LTS)](cr/java11.png) [ChaCha20 algorithm support for OpenSSL](#chacha20-algorithm-support-for-openssl)![End of content that applies only to Java 11 (LTS)](cr/java_close_lts.png)
- ![Start of content that applies only to Java 12)](cr/java12.png) [OpenSSL Digest algorithm disabled](#openssl-digest-algorithm-disabled)![End of content that applies only to Java 12](cr/java_close.png)
- [Support for OpenJDK HotSpot options](#support-for-openjdk-hotspot-options)
- [Support for Transparent Huge Pages (THP)](#support-for-transparent-huge-pages-thp)
-  ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [Support for low-overhead heap profiling (JEP 331)](#support-for-low-overhead-heap-profiling)![End of content that applies only to Java 11 (LTS)](cr/java_close_lts.png)
- [New Java memory map (jmap) tool](#new-java-memory-map-tool)
- [Automatically setting an initial heap size](#automatically-setting-an-initial-heap-size)
- [Removal of -Xdiagnosticscollector option](#removal-of-xdiagnosticscollector-option)
- [Change in behaviour of -XX:\[+|-\]IdleTuningCompactOnIdle](#change-in-behaviour-of-xxidletuningcompactonidle)
- [Addition of heuristics for compaction during idle GC](#heuristics-for-compaction-during-idle-gc)
- [Change in shared classes behavior for checking timestamps of `jar` or `zip` files](#change-in-shared-classes-behavior-for-checking-timestamps-of-jar-or-zip-files)


## Features and changes

### Binaries and supported environments

 Eclipse OpenJ9&trade; 0.15.0 and 0.15.1 supports OpenJDK 8, 11, and 12.

 Binaries are available from the AdoptOpenJDK community at the following links:
 
- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 12](https://adoptopenjdk.net/archive.html?variant=openjdk12&jvmVariant=openj9)

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The binaries at AdoptOpenJDK are labeled 0.15.1 due to a missing change.

 To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Performance improvements for JVMTI watched fields

 OpenJ9 version 0.14.0 introduced the [`-XX:[+|-]JITInlineWatches`](xxjitinlinewatches.md) option, which, when enabled, turned on experimental JIT operations to improve the performance of JVMTI watched fields. Following successful results, this option is now enabled by default. This option is now also supported on z/OS&reg; and Linux for IBM Z&reg;, in addition to x86 platforms (Windows&reg;, macOS&reg;, and Linux).

### Support for pause-less garbage collection on IBM Z systems

Support for Concurrent scavenge mode is now extended to Linux on IBM Z&reg; systems and z/OS&reg;. For more information, see [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge).

### ![Start of content that applies only to Java 11 (LTS)](cr/java11.png) ChaCha20 algorithm support for OpenSSL

The ChaCha20 and ChaCha20-Poly1305 algorithms can now use OpenSSL on Java 11. For more information, see [`-Djdk.nativeChaCha20`](djdknativechacha20.md). ![End of content that applies only to Java 11 (LTS)](cr/java_close_lts.png)

### ![Start of content that applies only to Java 12)](cr/java12.png) OpenSSL Digest algorithm disabled

Due to issue [#5611](https://github.com/eclipse-openj9/openj9/issues/5611), the Digest algorithm is disabled. This algorithm was disabled
for Java 8 and 11 in release 0.14.2, which did not support Java 12.

### Support for OpenJDK HotSpot options

For compatibility, the [`-XX:OnOutOfMemoryError`](xxonoutofmemoryerror.md) OpenJDK HotSpot option is now supported by OpenJ9.

### Support for Transparent Huge Pages (THP)

The VM now supports the allocation of huge pages on Linux when you use the `madvise` (`/sys/kernel/mm/transparent_hugepage/enabled`) setting. To enable this feature, set [`-XX:+TransparentHugePage`](xxtransparenthugepage.md) on the command line when you start your application. This option is currently not enabled by default.

###  ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Support for low-overhead heap profiling

[JEP 331](https://openjdk.org/jeps/331) provides a mechanism for sampling Java heap allocations with a low overhead via
the JVM Tool Interface (JVMTI).

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** JEP 331 is implemented for OpenJ9 with the following limitations:

- The `balanced` and `metronome` garbage collection policies are not supported.
- The JEP331 JVMTI agent and the Health Center agent both set a sampling interval, which by default is different. If both agents are used at the same time the Health Center agent will get incorrect results, unless the sampling intervals are adjusted to use the same value.
![End of content that applies only to Java 11 (LTS)](cr/java_close_lts.png)

### New Java memory map tool

The Java memory map (jmap) tool is similar to the HotSpot tool of the same name, and can be used to print statistics about classes on the heap, including the number of objects and their aggregate size. For usage information, see [Java memory map (jmap) tool](tool_jmap.md).

### Automatically setting an initial heap size

OpenJ9 can now learn and set an appropriate initial heap size for an application as an alternative to a user manually sizing and setting an `-Xms` value. The VM records the size of the heap when startup processing ends, writing this data to the shared classes cache. An average value is set over a few restarts, helping to ensure that the value used for the initial heap size is as accurate as possible. The heap size recorded is specific to the application command line, therefore a different hint is stored for every unique command line.

To turn on this behavior, set the [`-XX:+UseGCStartupHints`](xxusegcstartuphints.md) option on the command line when you start your application.

### Removal of -Xdiagnosticscollector option
This option was redundant and has now been removed. If you try to use this option on the command line, the VM outputs this error message:

`JVMJ9VM007E Command-line option unrecognised: -Xdiagnosticscollector`

### Change in behaviour of -XX:IdleTuningCompactOnIdle
-XX:[+|-]IdleTuningCompactOnIdle is now no longer effective when -XX:+IdleTuningGcOnIdle is not specified.

### Heuristics for compaction during idle GC
OpenJ9 now automatically compacts the heap when certain triggers are met during idle garbage collection (GC). As a result of this change, [`-XX:[+|-]IdleTuningCompactOnIdle`](xxidletuningcompactonidle.md) is deprecated.

### Change in shared classes behavior for checking timestamps of `jar` or `zip` files

In earlier releases, the shared classes cache checks timestamps of `jar` or `zip` files every time a class is loaded and reloads a class if the timestamp has changed. This behavior is now changed; timestamps are checked only when `zip` or `jar` files are added to class loaders and used for the first time to look for a class, which can improve class-loading performance. If `jar` or `zip` files are updated after a class loader starts loading classes from them, an older version of the class might be loaded from the shared classes cache. To revert to the behavior of earlier releases, set the [`-Xshareclasses:checkURLTimestamps`](xshareclasses.md#checkurltimestamps) option on the command line when you start your application.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Multiple `-Xshareclasses:` options are not combined, only the last one is used.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.14.0 and version 0.15.1 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.15/0.15.md).

<!-- ==== END OF TOPIC ==== version0.15.md ==== -->
