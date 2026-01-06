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

# What's new in version 0.32.0

The following new features and notable changes since version 0.30.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Creation of system dumps on macOS 12](#creation-of-system-dumps-on-macos-12)
- [Support for OpenJDK HotSpot options](#support-for-openjdk-hotspot-options)
- [`SharedClassStatistics` API updated](#sharedclassstatistics-api-updated)
- [Support for OpenSSL 3.0.x](#support-for-openssl-30x)
- [New `-XX:[+|-]OpenJ9CommandLineEnv` option added](#new-xx-openj9commandlineenv-option-added)
- [JITServer technology support for Linux on IBM Z&reg; systems](#jitserver-support-for-linux-on-ibm-z-systems)
- ![Start of content that applies to Java 11 plus](cr/java11plus.png) [Modified default value for `-XX:MaxDirectMemorySize`](#modified-default-value-for-xxmaxdirectmemorysize)
- ![Start of content that applies to Java 18 plus](cr/java18plus.png) [New JDK 18 features](#new-jdk-18-features)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.32.0 supports OpenJDK 8, 11, 17, and 18.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Creation of system dumps on macOS&reg; 12

You can now create system (core) dumps on macOS 12 or later.


### Support for OpenJDK HotSpot options

For compatibility, the following OpenJDK HotSpot options are now supported by OpenJ9:

- [`-XX:[+|-]ShowHiddenFrames`](xxshowhiddenframes.md). This option specifies whether generated hidden `MethodHandle` frames are displayed in a stack trace.

### `SharedClassStatistics` API updated

You can now use the `SharedClassStatistics` API to get the name, path, and directory of a shared classes cache. Depending on the operating system, you can also get the number of attached VMs for a non-persistent cache. This information is available through the following new methods: `cacheDir()`, `cacheName()`, `cachePath()`, and `numberAttached()`. For more information, see the API documentation.

### Support for OpenSSL 3.0.x

OpenSSL 3.0.x is supported but on Linux only. The JITServer technology feature currently does not support OpenSSL 3.0.x. For more information about OpenSSL support, see [`Cryptographic operations`](introduction.md#cryptographic-operations).

### New `-XX:[+|-]OpenJ9CommandLineEnv` option added

This option specifies whether the VM captures the command line in the environment variable `OPENJ9_JAVA_COMMAND_LINE`. For more information, see [`-XX:[+|-]OpenJ9CommandLineEnv`](xxopenj9commandlineenv.md).

### JITServer support for Linux on IBM Z&reg; systems
JITServer technology is now a fully supported feature on Linux on IBM Z&reg; systems (64-bit only). For more information, see [JITServer technology](jitserver.md).

### ![Start of content that applies to Java 11 plus](cr/java11plus.png) Modified default value for `-XX:MaxDirectMemorySize`

The default value of the [`-XX:MaxDirectMemorySize`](xxmaxdirectmemorysize.md) option, which limits the amount of heap memory that is used for direct byte buffers, is now the same as the maximum heap size.

### ![Start of content that applies to Java 18 plus](cr/java18plus.png) New JDK 18 features

The following features are supported by OpenJ9:

- [JEP 400](https://openjdk.org/jeps/400): UTF-8 by Default
- [JEP 416](https://openjdk.org/jeps/416): Reimplement Core Reflection with Method Handles
- [JEP 421](https://openjdk.org/jeps/421): Deprecate Finalization for Removal

The following features will be supported by OpenJ9 in a future release:

- [JEP 391](https://openjdk.org/jeps/391): macOS/AArch64 Port
    - An early access build is available in this release
- [JEP 417](https://openjdk.org/jeps/417): Vector API (Third Incubator)
- [JEP 419](https://openjdk.org/jeps/419): Foreign Function & Memory API (Second Incubator)

The following features are implemented in OpenJDK and available in any build of OpenJDK 18 with OpenJ9:

- [JEP 408](https://openjdk.org/jeps/408): Simple Web Server
- [JEP 413](https://openjdk.org/jeps/413): Code Snippets in Java API Documentation
- [JEP 418](https://openjdk.org/jeps/418): Internet-Address Resolution SPI
- [JEP 420](https://openjdk.org/jeps/420): Pattern Matching for switch (Second Preview)

You can find the full list of features for JDK 18 at the [OpenJDK project](https://openjdk.org/projects/jdk/18/).


## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.30.0 and v0.32.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.32/0.32.md).

<!-- ==== END OF TOPIC ==== version0.32.md ==== -->
