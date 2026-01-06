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


# What's new in version 0.14.x

## Version 0.14.0

The following new features and notable changes since version 0.13.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Support for OpenSSL 1.0.2](#support-for-openssl-102)
- [New option for ignoring or reporting unrecognized -XX: options](#new-option-for-ignoring-or-reporting-unrecognized-xx-options)
- [Improved support for pause-less garbage collection](#improved-support-for-pause-less-garbage-collection)
- [New Java stack (`jstack`) tool for obtaining stack traces and thread information](#new-jstack-tool-for-obtaining-stack-traces-and-thread-information)
- [New Java process status (`jps`) tool](#new-jps-tool)
- [New experimental option to improve the performance of JVMTI watched fields](#new-experimental-option-to-improve-the-performance-of-jvmti-watched-fields)
- [New option to prevent a network query being used to determine host name and IP address](#new-option-to-prevent-a-network-query-being-used-to-determine-host-name-and-ip-address)
- [Changes to the shared classes cache generation number](#changes-to-the-shared-classes-cache-generation-number)
- [Change to the default native stack size on 64-bit z/OS&reg;](#change-to-the-default-native-stack-size-on-64-bit-zos)

### Features and changes

#### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.14.0 supports OpenJDK 8, 11, and 12. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 12](https://adoptopenjdk.net/archive.html?variant=openjdk12&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

#### Support for OpenSSL 1.0.2

OpenJ9 release 0.13.0 introduced support for OpenSSL 1.0.2 for Java 12. In this release, support is extended to Java 8 and Java 11. OpenSSL is enabled by default for the CBC, Digest, GCM, and RSA cryptographic algorithms. On Linux&reg; and AIX&reg; platforms, the OpenSSL libraries are expected to be available on the system path. For more information about cryptographic acceleration with OpenSSL, see [Cryptographic operations](introduction.md#cryptographic-operations).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Support for the OpenSSL Digest algorithm on Java 8 and 11 is re-enabled in this release following the resolution of issue [#4530](https://github.com/eclipse-openj9/openj9/issues/4530).

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Warning:** Earlier versions of OpenJDK with OpenJ9 from the AdoptOpenJDK project bundle OpenSSL as part of the binary package. On Linux and AIX systems, OpenSSL is no longer bundled and the libraries are expected to be available on the system path.

#### New option for ignoring or reporting unrecognized -XX: options

By default, unrecognized `-XX:` command-line options are ignored, which prevents an application failing to start. You can now use  `-XX:-IgnoreUnrecognizedXXColonOptions` to turn off this behavior, so that unrecognized `-XX:` options are reported instead. For more information, see [`-XX:[+|-]IgnoreUnrecognizedXXColonOptions`](xxignoreunrecognizedxxcolonoptions.md).

#### Improved support for pause-less garbage collection

Support for Concurrent scavenge mode is now extended to Linux on POWER&reg; BE architectures. For more information, see [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge).

#### New jstack tool for obtaining stack traces and thread information

For compatibility with the reference implementation, OpenJ9 now includes an independent implementation of the `jstack` tool. To learn how to use the tool and
about any differences compared to the HotSpot tool of the same name, see [Java stack tool](tool_jstack.md).

#### New jps tool

OpenJ9 release 0.13.0 introduced support for the `jps` tool for Java 12. In this release, support is added for Java 8 and 11. The `jps` tool can be used to  query running Java processes. For more information, see [Java process status](tool_jps.md).

#### New experimental option to improve the performance of JVMTI watched fields

The [`-XX:[+|-]JITInlineWatches`](xxjitinlinewatches.md) option is introduced in this release. When enabled, the option turns on experimental
JIT operations that are intended to improve the performance of JVMTI watched fields. This option is currently supported only on x86 platforms (Windows&reg;, macOS&reg;, and Linux).

#### New option to prevent a network query being used to determine host name and IP address

By default, a network query is used to determine the host name and IP address for troubleshooting purposes. To avoid your program waiting to time out if a nameserver cannot be contacted, you can now prevent the query from being performed. For more information, see [`-XX:[+|-]ReadIPInfoForRAS`](xxreadipinfoforras.md).

#### Changes to the shared classes cache generation number

On all platforms, the format of classes that are stored in the shared classes cache is changed, which causes the JVM to create a new shared classes cache, rather than re-creating or reusing an existing cache. To save space, all existing shared caches can be removed unless they are in use by an earlier release. For more information about destroying a shared classes cache, see [`-Xshareclasses`](xshareclasses.md).

### Change to the default native stack size on 64-bit z/OS
The default stack size for operating system threads on 64-bit z/OS is changed from 384 KB to the operating system minimum of 1 MB. For more information about this setting, see [`-Xmso`](xmso.md).


### Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.13.0 and version 0.14.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.14/0.14.md).

## Version 0.14.2

The following new features and notable changes since version 0.14.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Support for OpenSSL 1.0.1](#support-for-openssl-101)
- [OpenSSL Digest algorithm disabled](#openssl-digest-algorithm-disabled)

### Features and changes

#### Binaries and supported environments

OpenJ9 release 0.14.2 supports OpenJDK 8 and 11. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)

The Windows (MSI) installer for OpenJDK v8 (64-bit) can now be used to optionally install the IcedTea-Web package, which provides
equivalent functionality to Java Web Start. For more information about the installer, see the AdoptOpenJDK [Installation page](https://adoptopenjdk.net/installation.html). For more information about migrating to IcedTea-Web, read the AdoptOpenJDK
[Migration Guide](https://adoptopenjdk.net/migration.html).

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

#### Support for OpenSSL 1.0.1

OpenSSL version 1.0.1 support is now enabled; Earlier releases supported only OpenSSL 1.0.2 and 1.1.x. On Linux&reg; and AIX&reg; platforms, the OpenSSL libraries are expected to be available on the system path. For more information about cryptographic acceleration with OpenSSL, see [Cryptographic operations](introduction.md#cryptographic-operations).

#### OpenSSL Digest algorithm disabled

Due to issue [#5611](https://github.com/eclipse-openj9/openj9/issues/5611), the Digest algorithm is disabled.


<!-- ==== END OF TOPIC ==== version0.14.md ==== -->
