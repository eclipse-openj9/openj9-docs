<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# What's new in version 0.31.0

The following new features and notable changes since version 0.30.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Creation of system dumps on macOS 12](#creation-of-system-dumps-on-macos-12)
- [New `-XX:[+|-]ShowHiddenFrames` option added](#new-xx-showhiddenframes-option-added)
- [New `SharedClassStatistics` API](#new-sharedclassstatistics-api)
- ![Start of content that applies to Java 11 plus](cr/java11plus.png) [Modified default for `-XX:MaxDirectMemorySize`](#modified-default-for-xxmaxdirectmemorysize)
- ![Start of content that applies to Java 18 plus](cr/java18plus.png) [New JDK 18 features](#new-jdk-18-features)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.31.0 supports OpenJDK 18.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Creation of system dumps on macOS 12

Creation of system (core) dumps on macOS 12 or later is now possible.

### New `-XX:[+|-]ShowHiddenFrames` option added

This option specifies if generated hidden MethodHandle frames are displayed in a stacktrace. See [`-XX:[+|-]ShowHiddenFrames`](xxshowhiddenframes.md) for more details.

### New `SharedClassStatistics` API

New shared classes API is added in `SharedClassStatistics` for `cacheDir()`, `cacheName()`, `cachePath()`, `numberAttached()`. For more details see the API documentation.
Only the [Java 18 API documentation](api-jdk18.md) is updated in this release, API documentation for the other versions will be updated in the next release.

### ![Start of content that applies to Java 11 plus](cr/java11plus.png) Modified default for `-XX:MaxDirectMemorySize`

[`-XX:MaxDirectMemorySize`](xxmaxdirectmemorysize.md) is no longer set by default and the class library limits the amount of heap memory used for
Direct Byte Buffers to the same value as the maximum heap size.

### ![Start of content that applies to Java 18 plus](cr/java18plus.png) New JDK 18 features

The following features are supported by OpenJ9:

- [JEP 400](https://openjdk.java.net/jeps/400): UTF-8 by Default
- [JEP 416](https://openjdk.java.net/jeps/416): Reimplement Core Reflection with Method Handles
- [JEP 421](https://openjdk.java.net/jeps/421): Deprecate Finalization for Removal

The following features will be supported by OpenJ9 in a future release:

- [JEP 391](https://openjdk.java.net/jeps/391): macOS/AArch64 Port
    - An early access build is available in this release
- [JEP 417](https://openjdk.java.net/jeps/417): Vector API (Third Incubator)
- [JEP 419](https://openjdk.java.net/jeps/419): Foreign Function & Memory API (Second Incubator)

The following features are implemented in OpenJDK and available in any build of OpenJDK 18 with OpenJ9:

- [JEP 408](https://openjdk.java.net/jeps/408): Simple Web Server
- [JEP 413](https://openjdk.java.net/jeps/413): Code Snippets in Java API Documentation
- [JEP 418](https://openjdk.java.net/jeps/418): Internet-Address Resolution SPI
- [JEP 420](https://openjdk.java.net/jeps/420): Pattern Matching for switch (Second Preview)

You can find the full list of features for JDK 18 at the [OpenJDK project](http://openjdk.java.net/projects/jdk/18/).


## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.30.0 and v0.31.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.31/0.31.md).

<!-- ==== END OF TOPIC ==== version0.30.md ==== -->
