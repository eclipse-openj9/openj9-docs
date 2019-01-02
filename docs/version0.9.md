<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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


# What's new in version 0.9.0

The following new features and notable changes from v.0.8.0 are included in this release:

- [New binaries and supported environments.](#binaries-and-supported-platforms)
- [The idle tuning feature is now supported on Linux running on Power&reg; Systems and IBM Z&reg; Systems.](#idle-tuning-feature)
- [A new Garbage Collection (GC) policy is available that performs no housekeeping.](#new-gc-policy)
- [A command line option is provided to automatically set a larger Java heap size for applications that run in containers.](#modifying-the-default-java-heap-size-for-applications-that-run-in-containers)
- [You can now specify the maximum Java heap size as a percentage value.](#specifying-the-maximum-java-heap-size-as-a-percentage-value)
- [The shared classes feature now supports nested jar files.](#shared-classes-support-for-nested-jar-files)
- [System dump data can now be read to help diagnose problems on Linux and Windows platforms.](Direct-Dump-Reader-enabled-on-linux)
- [There are notable changes to the `java.lang.String` class.](#changes-to-the-javalangstring-class)
- [There are notable changes to the `com.ibm.oti.shared.SharedClassCacheInfo` class.](#changes-to-the-SharedClassCacheInfo-class)

## Features and changes

### Binaries and supported platforms

The following additional OpenJDK binaries that contain the OpenJ9 VM are now available from the AdoptOpenJDK community:

- [OpenJDK version 10](https://adoptopenjdk.net/?variant=openjdk10-openj9)
- [OpenJDK version 8 for 32-bit Windows](https://adoptopenjdk.net/releases.html?variant=openjdk8-openj9#x32_win)
- [OpenJDK version 8 for x86 64-bit Linux (Large Heap)](https://adoptopenjdk.net/nightly.html?variant=openjdk8-openj9) for Java heaps >57 GB.

Complete platform support information for OpenJ9 can be found in [Supported environments](#openj9_support.md)

### Idle tuning feature

The idle tuning feature in OpenJ9 keeps your memory footprint small by releasing unused memory back to the
operating system. Prior to Eclipse v0.9.0 this feature was available only on Linux x86 architectures with the
`gencon` garbage collection policy. From v0.9.0, this feature is now available on Linux for IBM POWER&reg; and IBM Z&reg; architectures.
For more information about this feature, see the following command line options, which control this
behavior:

- [-XX:\[+|-\]IdleTuningCompactOnIdle](xxidletuningcompactonidle.md)
- [-XX:\[+|-\]IdleTuningGcOnIdle](xxidletuninggconidle.md)
- [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md)
- [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md)

The following blog post describes the benefits of using this feature: [Are you still paying for unused memory when your Java app is idle?](https://developer.ibm.com/javasdk/2017/09/25/still-paying-unused-memory-java-app-idle/)


### New GC policy

A new GC policy is introduced for [JEP 318: Epsilon: A No-Op Garbage Collector](http://openjdk.java.net/jeps/318).

When this policy is enabled, the Java object heap is expanded in the normal way until the limit is
reached, but memory is not reclaimed through garbage collection. When the limit is reached the VM shuts down.

This JEP has a number of use cases including short-lived applications and certain test scenarios.

To enable this policy you can use one of the following options:

- [-Xgcpolicy:nogc](xgcpolicy.md)
- [-XX:+UseNoGC](xxusenogc.md)

### Modifying the default Java heap size for applications that run in containers

When using container technology, applications are typically run on their own and do not need to compete for memory. In this release, changes
are introduced to detect when OpenJ9 is running inside a container. If your application is running in a container and
you want the VM to allocate a larger fraction of memory to the Java heap, set the [`-XX:+UseContainerSupport`](xxusecontainersupport.md) option on the command line.

The following table shows the maximum Java heap size that gets set, depending on the memory available:


| Physical memory `<size>`   | Maximum Java heap size          |
|----------------------------|---------------------------------|
| Less than 1 GB             | 50% `<size>`                    |
| 1 GB - 2 GB                | `<size>` - 512M                 |
| Greater than 2 GB          | 75% `<size>`                    |

The default heap size for containers only takes affect when running in a container environment and when `-XX:+UseContainerSupport` is specified,
which is expected to be the default in a future release.

### Specifying the maximum Java heap size as a percentage value

OpenJ9 now supports setting the heap size as a percentage of the physical memory. The following OpenJDK options are recognized and can be
set for the VM:

- `-XX:MaxRAMPercentage`
- `-XX:InitialRAMPercentage`

To understand how to set these options, see [-XX:InitialRAMPercentage / -XX:MaxRAMPercentage](xxinitialrampercentage.md).

If your application is running in a container and you have specified `-XX:+UseContainerSupport`, as described in [Modifying the default Java heap size for applications that run in containers](#modifying-the-default-java-heap-size-for-applications-that-run-in-containers), both the default heap size for containers and the `-XX:MaxRAMPercentage` and `-XX:InitialRAMPercentage`
options are based on the available container memory.

### Shared classes support for nested jar files

Changes are made to the `com.ibm.oti.shared` API to support nested jar files.

### Direct Dump Reader enabled on Linux and Windows

Direct Dump Reader (DDR) support is now enabled for the OpenJ9 VM on all Linux architectures and on Windows. The DDR code enables the VM to read system dump data by using the OpenJ9 Diagnostic Tool
Framework for Java (DTFJ) API or the [`jdmpview`](tool_jdmpview.md) tool. If you use the [Eclipse Memory Analyzer Tool (MAT)](https://www.eclipse.org/mat/), you can also analyze OpenJ9 or IBM VMs by installing the DTFJ plugin.
(Install from the Eclipse Help menu; Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and  Diagnostic Tools > Diagnostic Tool Framework for Java)

You must use a 32-bit JVM to look at a 32-bit core, and a 64-bit JVM to look at a 64-bit core. This restriction will be fixed in a later version of OpenJ9.


### Changes to the `java.lang.String` class

![Start of content that applies only to Java 9 and later](cr/java9plus.png) To match the behavior of OpenJDK, `java.lang.String` no longer has a count field, which changes the way that `String.subString()` works compared to Java 8. `String.subString()` now copies the value array. Similarly, `StringBuffer` and `StringBuilder` do not share the value array with any `String` created by `toString()`.

For performance and compatibility with the new String object layout, the OpenJ9 implementations of `StringBuffer` and `StringBuilder` have been deprecated in favor of the OpenJDK implementations.
![End of content that applies only to Java 9 or later](cr/java_close.png)

### Changes to the `SharedClassCacheInfo` class

![Start of content that applies only to Java 10 and later](cr/java10plus.png) `SharedClassCacheInfo.getCacheJVMLevel()` used to return the JVMLEVEL constant that maps to a Java version number, for example **JVMLEVEL_JAVA8**. This call now returns only the Java version number, for example **10** for Java 10.
![End of content that applies only to Java 10 or later](cr/java_close.png)

## Full release information

To see a complete list of changes between Eclipse OpenJ9 V0.8.0 and V0.9.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.9/0.9.md).
<!-- ==== END OF TOPIC ==== cmdline_general.md ==== -->
