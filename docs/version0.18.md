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


# What's new in version 0.18.1

The following new features and notable changes since version 0.17.0 are included in this release:

- [Binaries and supported environments](#binaries-and-supported-environments)
- [Technical preview of JITServer technology](#technical-preview-of-jitserver-technology)
- [`jextract` now available on macOS&reg; for OpenJDK version 8](#jextract-now-available-on-macos-for-openjdk-version-8)
- [New shared-classes cache suboption to turn off timestamp checking](#new-shared-classes-cache-suboption-to-turn-off-timestamp-checking)
- [Removal of restriction on layered shared cache](#removal-of-restriction-on-layered-shared-cache)
- [`-Xmso` 1 MB minimum value on z/OS&reg; 64-bit](#-xmso-1-mb-minimum-value-on-zos-64-bit)
- [`jstat`: new Java&trade; statistics monitoring tool](#jstat-new-java-statistics-monitoring-tool)
- [`-XX:+TransparentHugePage` is enabled by default on more Linux&reg; systems](#-xxtransparenthugepage-is-enabled-by-default-on-more-linux-systems)
- [New exit dump agent and `ExitOnOutOfMemoryError` option](#new-exit-dump-agent-and-exitonoutofmemoryerror-option)
- [LUDCL caching enabled by default](#ludcl-caching-enabled-by-default)
- [Terabytes suffix support for `-X` and `-XX` options that take a size](#terabytes-suffix-support-for-x-and-xx-options-that-take-a-size)
- [Improved support for pause-less garbage collection](#improved-support-for-pause-less-garbage-collection)
- [`-Xgc:noConcurrentScavenge` option](#-xgcnoconcurrentscavenge-option)
- [Support for OpenJDK HotSpot options](#support-for-openjdk-hotspot-options)
- [Shared classes cache suboptions for layered caches no longer experimental](#shared-classes-cache-suboptions-for-layered-caches-no-longer-experimental)
- [`-Djava.lang.string.substring.nocopy` option](#-djavalangstringsubstringnocopy-option)


## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; releases 0.18.0 and 0.18.1 support OpenJDK 8, 11, and 13. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 13](https://adoptopenjdk.net/archive.html?variant=openjdk13&jvmVariant=openj9)

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Binaries at AdoptOpenJDK that are labeled 0.18.1 include additional bug fixes. For more information, see the [release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.18/0.18.md).


To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Technical preview of JITServer technology

A technical preview of JITServer technology is included in this release. It's currently available for OpenJDK 8 and OpenJDK 11 running on Linux on x86-64.

JITServer technology decouples the JIT compiler from the VM and lets the JIT compiler run remotely in its own process. This mechanism prevents your Java application suffering possible negative effects due to CPU and memory consumption caused by JIT compilation. This technology can improve quality of service, robustness, and even performance of Java applications. For more information, see [JITServer technology](jitserver.md).

### `jextract` now available on macOS for OpenJDK version 8

The [`jextract` tool](tool_jextract.md) is now available on macOS platforms (as well as AIX&reg; and Linux) for _all_ current versions of OpenJDK: 8, 11, and 13.

### New shared-classes cache suboption to turn off timestamp checking

You can set the `-Xshareclasses:noTimestampChecks` option to turn off timestamp checking in shared classes. For more information, see the [-Xshareclasses:noTimestampChecks](xshareclasses.md#notimestampchecks) option.

### Removal of restriction on layered shared cache

In the previous release, there is a restriction that the [`jvmtiSharedCacheInfo.isCorrupt`](interface_jvmti.md#jvmtisharedcacheinfo-structure) field and the `SharedClassCacheInfo.isCacheCorrupt()` method cannot detect a corrupted cache that has a layer number other than `0`. This restriction is now removed. See the [Shared classes API documentation](api-shrc.md). <!-- Link to API -->

### `-Xmso` 1 MB minimum value on z/OS 64-bit

On z/OS 64-bit, [`-Xmso`](xmso.md) has a 1 MB minimum value, to match the minimum stack space provided by the operating system. If you set a value smaller than 1 MB, the value is ignored.

### `jstat`: new Java statistics monitoring tool

For compatibility with the HotSpot implementation, OpenJ9 now includes an independent implementation of the `jstat` tool for retrieving statistics on a VM. For more information, see [Java statistics monitoring tool](tool_jstat.md).

### `-XX:+TransparentHugePage` is enabled by default on more Linux systems

[-XX:+TransparentHugePage](xxtransparenthugepage.md) is enabled by default on Linux systems for POWER&reg; and IBM Z&reg; as well as x86 systems. This option takes affect only when Transparent Huge Pages (THP) is set to `madvise` on your system. When Transparent Huge Pages are used, your application footprint might increase.

### New exit dump agent and `ExitOnOutOfMemoryError` option

The new exit dump agent shuts down the VM when the specified event occurs. The exit agent is at priority level 0 and the tool agent has been moved to priority level 1 to aid in mimicking the behavior of HotSpot options. For more information about dump agents, see [`-Xdump`](xdump.md#dump-agents).

OpenJ9 now supports the HotSpot option [`-XX:[+|-]ExitOnOutOfMemoryError`](xxexitonoutofmemoryerror.md). You can set this option to have the VM shut down when a `java.lang.OutOfMemory` error is thrown by the VM or in Java code. The exit dump agent is used in the implementation of `-XX:[+|-]ExitOnOutOfMemoryError`.

### LUDCL caching enabled by default
By caching the Latest User Defined Class Loader (LUDCL), Java applications that use deserialization extensively can see a performance improvement. This capability is controlled by the [`-Dcom.ibm.enableClassCaching`](dcomibmenableclasscaching.md) system property and is now enabled by default. This feature was disabled for the 0.17.0 release due to [issue #7332](https://github.com/eclipse-openj9/openj9/issues/7332) which has now been resolved.


### Terabytes suffix support for `-X` and `-XX` options that take a size

OpenJ9 now supports 't' and 'T' suffixes (indicating terabytes) for `-X` and `-XX` options that take a `<size>` parameter.

### Improved support for pause-less garbage collection

Support for Concurrent scavenge mode is now extended to macOS. For more information, see [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge).

### `-Xgc:noConcurrentScavenge` option

The previously undocumented option [`-Xgc:noConcurrentScavenge`](xgc.md#noconcurrentscavenge) disables pause-less garbage collection.

### Support for OpenJDK HotSpot options

For compatibility, the following OpenJDK HotSpot options are now supported by OpenJ9:

- [-XX:ParallelGCThreads](xxparallelgcthreads.md)
- [-XX:ConcGCThreads](xxconcgcthreads.md)
- [-XX:ParallelCMSThreads](xxparallelcmsthreads.md)

### Shared classes cache suboptions for layered caches no longer experimental

The suboptions for creating layered caches are no longer marked experimental.

The new options are:

- [`createLayer`](xshareclasses.md#createlayer)
- [`layer=<number>`](xshareclasses.md#layer) (see this section for more information about layered caches)
- [`printTopLayerStats`](xshareclasses.md#printtoplayerstats-cache-utility)
- [`destroyAllLayers`](xshareclasses.md#destroyalllayers)

### `-Djava.lang.string.substring.nocopy` option

The previously undocumented Java 8 option [`-Djava.lang.string.substring.nocopy=true`](djavalangstringsubstringnocopy.md) avoids String sharing by String.substring(), which is the same behavior as the Oracle HotSpot VM.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.17.0 and version 0.18.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.18/0.18.md).

<!-- ==== END OF TOPIC ==== version0.18.md ==== -->
