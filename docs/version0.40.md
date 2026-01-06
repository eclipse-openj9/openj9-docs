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

# What's new in version 0.40.0

The following new features and notable changes since version 0.39.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [New `-XX:codecachetotalMaxRAMPercentage` option added](#new-xxcodecachetotalmaxrampercentage-option-added)
- [VM starts with a warning message after the container detection fails](#vm-starts-with-a-warning-message-after-the-container-detection-fails)
- [Support for persistent shared classes cache added on z/OS](#support-for-persistent-shared-classes-cache-added-on-zos)
- [EC key generation algorithm and XDH key agreement and generation algorithms support for OpenSSL](#ec-key-generation-algorithm-and-xdh-key-agreement-and-generation-algorithms-support-for-openssl)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.40.0 supports OpenJDK 8, 11, 17 and 20.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### New `-XX:codecachetotalMaxRAMPercentage` option added

In environments with low physical memory availability, the VM might use too much memory for JIT code caches, leaving little memory for critical operations. With the `-XX:codecachetotalMaxRAMPercentage` option, you can set an upper limit for the total code cache size, where the upper limit is specified as a percentage of the physical memory the VM process is allowed to use.

For more information, see [`-XX:codecachetotalMaxRAMPercentage`](xxcodecachetotalmaxrampercentage.md).

### VM starts with a warning message after the container detection fails

If you mount the `/proc` file system with the `hidepid=2` option on Linux® systems and the VM does not have root privileges, it cannot access the `/proc` file system. In previous releases, the VM exits when it encounters this scenario. From this release, the VM starts with a warning message after the container detection fails.

For more information, see the [Cloud optimizations](introduction.md#cloud-optimizations) topic.

### Support for persistent shared classes cache added on z/OS

Persistent shared caches are now supported on z/OS systems. The default cache type on z/OS is still non-persistent. You can use the [`-Xshareclasses:persistent`](xshareclasses.md#persistent) option to switch to persistent shared cache.

### EC key generation algorithm and XDH key agreement and generation algorithms support for OpenSSL

The EC key generation algorithm can now use the native OpenSSL library for all OpenJDK versions.![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) The XDH key agreement and generation algorithms can also use the native OpenSSL library.![End of content that applies to Java 11 (LTS) and later](cr/java_close_lts.png) For more information, see [`-Djdk.nativeECKeyGen`](djdknativeeckeygen.md), [`-Djdk.nativeXDHKeyAgreement`](djdknativexdhkeyagreement.md), and [`-Djdk.nativeXDHKeyGen`](djdknativexdhkeygen.md).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.39.0 and v0.40.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.40/0.40.md).

<!-- ==== END OF TOPIC ==== version0.40.md ==== -->
