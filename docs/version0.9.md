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


# What's new in version 0.9.0

The following new features and notable changes from v.0.8.0 are included in this release:

- [New binaries and supported environments.](#binaries-and-supported-platforms)
- [The idle tuning feature is now supported on Linux running on Power&reg; Systems and IBM Z&reg; Systems.](#idle-tuning-feature)
- [A new Garbage Collection (GC) policy is available that performs no housekeeping.](#new-gc-policy)
- [A command line option is provided to automatically set a larger Java heap size for applications that run in containers.](#modifying-the-default-java-heap-size-for-applications-that-run-in-containers)
- [You can now specify the maximum Java heap size as a percentage value.](#specifying-the-maximum-java-heap-size-as-a-percentage-value)
- [The shared classes feature now supports nested jar files.](#shared-classes-support-for-nested-jar-files)
- [System dump data can now be read to help diagnose problems on Linux and Windows platforms.](#direct-dump-reader-enabled-on-linux-and-windows)
- [There are notable changes to the `java.lang.String` class.](#changes-to-the-javalangstring-class)
- [There are notable changes to the `com.ibm.oti.shared.SharedClassCacheInfo` class.](#changes-to-the-SharedClassCacheInfo-class)
- ![Start of content that applies only to Java 9](cr/java9plus.png) [New JDK 9 features](#new-jdk-9-features)
- ![Start of content that applies only to Java 10](cr/java10plus.png) [New JDK 10 features](#new-jdk-10-features)

## Features and changes

### Binaries and supported platforms

The following additional OpenJDK binaries that contain the Eclipse OpenJ9&trade; VM are now available from the AdoptOpenJDK community:

- [OpenJDK version 10](https://adoptopenjdk.net/?variant=openjdk10-openj9)
- [OpenJDK version 8 for 32-bit Windows](https://adoptopenjdk.net/releases.html?variant=openjdk8-openj9#x32_win)
- [OpenJDK version 8 for x86 64-bit Linux (Large Heap)](https://adoptopenjdk.net/nightly.html?variant=openjdk8-openj9) for Java heaps >57 GB.

Complete platform support information for OpenJ9 can be found in [Supported environments](#openj9_support.md)

### Idle tuning feature

The idle tuning feature in OpenJ9 keeps your memory footprint small by releasing unused memory back to the
operating system. Prior to Eclipse version 0.9.0 this feature was available only on Linux x86 architectures with the
`gencon` garbage collection policy. From version 0.9.0, this feature is now available on Linux for IBM POWER&reg; and IBM Z&reg; architectures.
For more information about this feature, see the following command line options, which control this
behavior:

- [-XX:\[+|-\]IdleTuningCompactOnIdle](xxidletuningcompactonidle.md)
- [-XX:\[+|-\]IdleTuningGcOnIdle](xxidletuninggconidle.md)
- [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md)
- [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md)

The following blog post describes the benefits of using this feature: [Are you still paying for unused memory when your Java app is idle?](https://developer.ibm.com/javasdk/2017/09/25/still-paying-unused-memory-java-app-idle/)


### New GC policy

A new GC policy is introduced for [JEP 318: Epsilon: A No-Op Garbage Collector](https://openjdk.org/jeps/318).

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
Framework for Java (DTFJ) API or the [`jdmpview`](tool_jdmpview.md) tool. If you use the [Eclipse Memory Analyzer&trade; tool (MAT)](https://www.eclipse.org/mat/), you can also analyze OpenJ9 or IBM VMs by installing the DTFJ plug-in.
(Install from the Eclipse Help menu; Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and  Diagnostic Tools > Diagnostic Tool Framework for Java)

You must use a 32-bit VM to look at a 32-bit core, and a 64-bit VM to look at a 64-bit core. This restriction will be fixed in a later version of OpenJ9.


### Changes to the `java.lang.String` class

![Start of content that applies only to Java 9 and later](cr/java9plus.png) To match the behavior of OpenJDK, `java.lang.String` no longer has a count field, which changes the way that `String.subString()` works compared to Java 8. `String.subString()` now copies the value array. Similarly, `StringBuffer` and `StringBuilder` do not share the value array with any `String` created by `toString()`.

For performance and compatibility with the new String object layout, the OpenJ9 implementations of `StringBuffer` and `StringBuilder` have been deprecated in favor of the OpenJDK implementations.
![End of content that applies only to Java 9 or later](cr/java_close.png)

### Changes to the `SharedClassCacheInfo` class

![Start of content that applies only to Java 10 and later](cr/java10plus.png) `SharedClassCacheInfo.getCacheJVMLevel()` used to return the JVMLEVEL constant that maps to a Java version number, for example **JVMLEVEL_JAVA8**. This call now returns only the Java version number, for example **10** for Java 10.
![End of content that applies only to Java 10 or later](cr/java_close.png)

### ![Start of content that applies to Java 9 plus](cr/java9plus.png) New JDK 9 features

The following features are supported by OpenJ9:

- [JEP 102](https://openjdk.org/jeps/102): Process API Updates
- [JEP 110](https://openjdk.org/jeps/110): HTTP 2 Client
- [JEP 143](https://openjdk.org/jeps/143): Improve Contended Locking
- [JEP 193](https://openjdk.org/jeps/193): Variable Handles
- [JEP 199](https://openjdk.org/jeps/199): Smart Java Compilation, Phase Two
- [JEP 200](https://openjdk.org/jeps/200): The Modular JDK
- [JEP 201](https://openjdk.org/jeps/201): Modular Source Code
- [JEP 211](https://openjdk.org/jeps/211): Elide Deprecation Warnings on Import Statements
- [JEP 213](https://openjdk.org/jeps/213): Milling Project Coin
- [JEP 215](https://openjdk.org/jeps/215): Tiered Attribution for javac
- [JEP 216](https://openjdk.org/jeps/216): Process Import Statements Correctly
- [JEP 217](https://openjdk.org/jeps/217): Annotations Pipeline 2.0
- [JEP 219](https://openjdk.org/jeps/219): Datagram Transport Layer Security (DTLS)
- [JEP 220](https://openjdk.org/jeps/220): Modular Run-Time Images
- [JEP 221](https://openjdk.org/jeps/221): Simplified Doclet API
- [JEP 222](https://openjdk.org/jeps/222): jshell: The Java Shell (Read-Eval-Print Loop)
- [JEP 223](https://openjdk.org/jeps/223): New Version-String Scheme
- [JEP 224](https://openjdk.org/jeps/224): HTML5 Javadoc
- [JEP 225](https://openjdk.org/jeps/225): Javadoc Search
- [JEP 226](https://openjdk.org/jeps/226): UTF-8 Property Files
- [JEP 227](https://openjdk.org/jeps/227): Unicode 7.0
- [JEP 229](https://openjdk.org/jeps/229): Create PKCS12 Keystores by Default
- [JEP 232](https://openjdk.org/jeps/232): Improve Secure Application Performance
- [JEP 235](https://openjdk.org/jeps/235): Test Class-File Attributes Generated by javac
- [JEP 236](https://openjdk.org/jeps/236): Parser API for Nashorn
- [JEP 238](https://openjdk.org/jeps/238): Multi-Release JAR Files
- [JEP 240](https://openjdk.org/jeps/240): Remove the JVM TI hprof Agent
- [JEP 241](https://openjdk.org/jeps/241): Remove the jhat Tool
- [JEP 244](https://openjdk.org/jeps/244): TLS Application-Layer Protocol Negotiation Extension
- [JEP 247](https://openjdk.org/jeps/247): Compile for Older Platform Versions
- [JEP 249](https://openjdk.org/jeps/249): OCSP Stapling for TLS
- [JEP 251](https://openjdk.org/jeps/251): Multi-Resolution Images
- [JEP 252](https://openjdk.org/jeps/252): Use CLDR Locale Data by Default
- [JEP 253](https://openjdk.org/jeps/253): Prepare JavaFX UI Controls and CSS APIs for Modularization
- [JEP 254](https://openjdk.org/jeps/254): Compact Strings (not enabled by default)
- [JEP 255](https://openjdk.org/jeps/255): Merge Selected Xerces 2.11.0 Updates into JAXP
- [JEP 256](https://openjdk.org/jeps/256): BeanInfo Annotations
- [JEP 257](https://openjdk.org/jeps/257): Update JavaFX/Media to Newer Version of GStreamer
- [JEP 258](https://openjdk.org/jeps/258): HarfBuzz Font-Layout Engine
- [JEP 259](https://openjdk.org/jeps/259): Stack-Walking API
- [JEP 260](https://openjdk.org/jeps/260): Encapsulate Most Internal APIs
- [JEP 261](https://openjdk.org/jeps/261): Module System
- [JEP 262](https://openjdk.org/jeps/262): TIFF Image I/O
- [JEP 263](https://openjdk.org/jeps/263): HiDPI Graphics on Windows and Linux®
- [JEP 264](https://openjdk.org/jeps/264): Platform Logging API and Service
- [JEP 265](https://openjdk.org/jeps/265): Marlin Graphics Renderer
- [JEP 266](https://openjdk.org/jeps/266): More Concurrency Updates
- [JEP 267](https://openjdk.org/jeps/267): Unicode 8.0
- [JEP 268](https://openjdk.org/jeps/268): XML Catalogs
- [JEP 269](https://openjdk.org/jeps/269): Convenience Factory Methods for Collections
- [JEP 272](https://openjdk.org/jeps/272): Platform-Specific Desktop Features
- [JEP 273](https://openjdk.org/jeps/273): DRBG-Based SecureRandom Implementations
- [JEP 274](https://openjdk.org/jeps/227): Enhanced Method Handles
- [JEP 275](https://openjdk.org/jeps/275): Modular Java Application Packaging
- [JEP 276](https://openjdk.org/jeps/276): Dynamic Linking of Language-Defined Object Models
- [JEP 277](https://openjdk.org/jeps/277): Enhanced Deprecation
- [JEP 280](https://openjdk.org/jeps/280): Indify String Concatenation
- [JEP 282](https://openjdk.org/jeps/282): jlink: The Java Linker
- [JEP 283](https://openjdk.org/jeps/283): Enable GTK 3 on Linux
- [JEP 285](https://openjdk.org/jeps/285): Spin-Wait Hints
- [JEP 287](https://openjdk.org/jeps/287): SHA-3 Hash Algorithms
- [JEP 288](https://openjdk.org/jeps/288): Disable SHA-1 Certificates
- [JEP 289](https://openjdk.org/jeps/289): Deprecate the Applet API
- [JEP 290](https://openjdk.org/jeps/290): Filter Incoming Serialization Data
- [JEP 292](https://openjdk.org/jeps/292): Implement Selected ECMAScript 6 Features in Nashorn
- [JEP 298](https://openjdk.org/jeps/298): Remove Demos and Samples
- [JEP 299](https://openjdk.org/jeps/299): Reorganize Documentation

You can find the full list of features for JDK 9 at the [OpenJDK project](https://openjdk.org/projects/jdk9/). Any remaining features that are listed do not apply to OpenJ9.

### ![Start of content that applies to Java 10 plus](cr/java10plus.png) New JDK 10 features

The following features are supported by OpenJ9:

- [JEP 286](https://openjdk.org/jeps/286): Local-Variable Type Inference
- [JEP 313](https://openjdk.org/jeps/313): Remove the Native-Header Generation Tool (javah)
- [JEP 314](https://openjdk.org/jeps/314): Additional Unicode Language-Tag Extensions
- [JEP 319](https://openjdk.org/jeps/319): Root Certificates
- [JEP 322](https://openjdk.org/jeps/322): Time-Based Release Versioning

You can find the full list of features for JDK 10 at the [OpenJDK project](https://openjdk.org/projects/jdk/10/). Any remaining features that are listed do not apply to OpenJ9.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.8.0 and version 0.9.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.9/0.9.md).
<!-- ==== END OF TOPIC ==== cmdline_general.md ==== -->
