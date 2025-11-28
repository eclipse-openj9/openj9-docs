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

# What's new in version 0.53.0

The following new features and notable changes since version 0.51.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [OpenSSL support added for PBKDF2 algorithm](#openssl-support-added-for-pbkdf2-algorithm)
- [OpenSSL 3.5.1 is bundled on all platforms](#openssl-351-is-bundled-on-all-platforms)
- [Offheap support is added for the `balanced` GC policy](#offheap-support-is-added-for-the-balanced-gc-policy)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [New JDK Flight Recorder (JFR) events are added in this release](#new-jdk-flight-recorder-jfr-events-are-added-in-this-release) ![End of content that applies only to Java 11 and later](cr/java_close.png)
- [The default maximum heap size is automatically adjusted based on the initial heap size](#the-default-maximum-heap-size-is-automatically-adjusted-based-on-the-initial-heap-size)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.53.0 supports OpenJDK 8, 11, 17, and 21.

RHEL 8.8 and 9.2 are out of support and are removed from the list of supported platforms. RHEL 8.10 and 9.4 are the new minimum operating system levels.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### OpenSSL support added for PBKDF2 algorithm

OpenSSL native cryptographic support is added for the Password based key derivation (PBKDF2) algorithm, providing improved cryptographic performance. OpenSSL support is enabled by default for the PBKDF2 algorithm. If you want to turn off support for the PBKDF2 algorithm, set the [`-Djdk.nativePBKDF2`](djdknativepbkdf2.md) system property to `false`.

### OpenSSL 3.5.1 is bundled on all platforms

OpenSSL 3.5.1 is now supported and bundled on all platforms. You can use the `jdk.native.openssl.skipBundled` property to specify whether to load the pre-packaged OpenSSL library or the library available on the system path.

For more information, see [OpenSSL](openssl.md).

### Offheap support is added for the `balanced` GC policy

Before the 0.53.0 release, the `balanced` GC policy used an arraylet representation in the heap to support large arrays that cannot be contained in a region. Now, if the array's data are larger than a region size, the data are stored into a separate area, Offheap.

For more information, see [GC processing](gc.md#balanced-policy).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) New JDK Flight Recorder (JFR) events are added in this release

In this release, the following new JFR events are added:

- NativeLibrary (Linux&reg; only)
- SystemGC
- SystemProcess (Linux only)
- YoungGenerationConfiguration

For more information, see [`-XX:[+|-]FlightRecorder`](xxflightrecorder.md). ![End of content that applies only to Java 11 and later](cr/java_close.png)

### The default maximum heap size is automatically adjusted based on the initial heap size

If the maximum object heap size is not explicitly set with the `-Xmx` option and the VM uses the default value of `-Xmx`, then even if `-Xms` is greater than `-Xmx` initially, the VM starts successfully. The VM starts because the default `-Xmx` value is increased automatically to match `-Xms`.

Until the 0.53.0 release, if `-Xms` was greater than `-Xmx`, the VM used to fail. For more information, see [`-Xms` / `-Xmx`](xms.md).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.51.0 and v0.53.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.53/0.53.md).

<!-- ==== END OF TOPIC ==== version0.53.md ==== -->
