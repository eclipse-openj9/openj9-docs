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


# What's new in version 0.12.x

## Version 0.12.0

The following new features and notable changes since version 0.11.0 are included in this release:

- [Improved flexibility for managing the size of the JIT code cache](#improved-flexibility-for-managing-the-size-of-the-jit-code-cache)
<!-- - [Class data sharing is enabled by default](#class-data-sharing-is-enabled-by-default) -->
- [Idle-tuning is enabled by default when Eclipse OpenJ9&trade; runs in a docker container](#idle-tuning-is-enabled-by-default-when-openj9-runs-in-a-docker-container)
- [Changes to default shared classes cache directory permissions (not Windows)](#changes-to-default-shared-classes-cache-directory-permissions-not-windows)
- ![Start of content that applies only to Java 11 (LTS)](cr/java11.png) [OpenSSL is now supported for improved native cryptographic performance](#openssl-is-now-supported-for-improved-native-cryptographic-performance)
- [Improved support for pause-less garbage collection](#improved-support-for-pause-less-garbage-collection)
- [RSA algorithm support for OpenSSL](#rsa-algorithm-support-for-openssl)
- [`IBM_JAVA_OPTIONS` is deprecated](#ibm_java_options-is-deprecated)

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Warning:** Following the release of OpenJ9 0.12.0, an intermittent problem
was identified with OpenSSL V1.1.x acceleration of the cryptographic Digest algorithm. For more information about the issue, see [#4530](https://github.com/eclipse-openj9/openj9/issues/4530). You can turn off the Digest algorithm by setting the [-Djdk.nativeDigest](djdknativedigest.md) system property to `false`. A new release of OpenJ9 (0.12.1) is available that disables the Digest algorithm by default.


### Features and changes

#### Binaries and supported environments

Eclipse OpenJ9 release 0.12.0 provides support for *OpenJDK 8 with OpenJ9* and *OpenJDK 11 with OpenJ9*. In this release support is extended to the 64-bit macOS&reg; platform on OpenJDK with OpenJ9.

Builds for all platforms are available from the AdoptOpenJDK project at the following links:

- [OpenJDK 8 with OpenJ9](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK 11 with OpenJ9](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

#### Improved flexibility for managing the size of the JIT code cache

The JIT code cache stores the native code of compiled Java&trade; methods. By default, the size of the code cache is 256 MB for a 64-bit VM and 64 MB for a 31/32-bit VM. In earlier releases the size of the code cache could be increased from the default value by using the `-Xcodecachetotal` command line option. In this release the size can also be decreased by using this option, with a minimum size of 2 MB. The size of the JIT code cache also affects the size of the JIT data cache, which holds metadata about compiled methods. If you use the `-Xcodecachetotal` option to manage the size of the code cache, the size of the data cache is adjusted by the same proportion. For more information, see [`-Xcodecachetotal`](xcodecachetotal.md).

<!-- ### Class data sharing is enabled by default

Class data sharing is enabled by default for bootstrap classes, unless your application is running in a container. You can use the `-Xshareclasses` option to change the default behavior. For more information, see [Class Data Sharing](shrc.md). -->

#### Idle-tuning is enabled by default when OpenJ9 runs in a docker container

In an earlier release, a set of idle-tuning options were introduced to manage the footprint of the Java heap when the OpenJ9 VM is in an idle state. These options could be set manually on the command line. In this release, the following two options are enabled by default when OpenJ9 is running in a container:

- `-XX:[+|-]IdleTuningGcOnIdle`, which runs a garbage collection cycle and releases free memory pages back to the operating system when the VM state is set to idle.
- `-XX:[+|-]IdleTuningCompactOnIdle`, which compacts the object heap to reduce fragmentation when the VM state is set to idle.

By default, the VM must be idle for 180 seconds before the status is set to idle. To control the wait time before an idle state is set, use the [`-XX:IdleTuningMinIdleWaitTime`](xxidletuningminidlewaittime.md) option. To turn off idle detection, set the value to `0`.

#### Changes to default shared classes cache directory permissions (not Windows)

If you do not use the `cachDirPerm` suboption to specify permissions for a shared classes cache directory, and the cache directory is not the `/tmp/javasharedresources` default, the following changes apply:

- When creating a new cache directory, the default permissions are now stricter.
- If the cache directory already exists, permissions are now unchanged (previously, when a cache was opened using this directory, the permissions would be set to 0777).

For more information, see [`-Xshareclasses`](xshareclasses.md#cachedirperm).

#### OpenSSL is now supported for improved native cryptographic performance

![Start of content that applies only to Java 11 (LTS)](cr/java11.png)

OpenSSL is a native open source cryptographic toolkit for Transport Layer Security (TLS) and Secure Sockets Layer (SSL) protocols, which provides improved cryptographic performance compared to the in-built OpenJDK Java cryptographic implementation. The OpenSSL V1.1.x implementation is enabled by default and  supported for the Digest, CBC, and GCM algorithms. Binaries obtained from AdoptOpenJDK include OpenSSL v1.1.x (see Note). For more information about tuning the OpenSSL implementation, see [Performance tuning](introduction.md#cryptographic-operations).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** OpenJDK 8 with OpenJ9 includes OpenSSL support since version 0.11.0. Currently, OpenSSL is not bundled as part of the AdoptOpenJDK AIX binaries due to an unresolved problem.

![End of content that applies only to Java 11 (LTS)](cr/java_close_lts.png)


#### Improved support for pause-less garbage collection

Concurrent scavenge mode is now supported on 64-bit Windows operating systems.

In Eclipse OpenJ9 version 0.11.0, support was added for `-Xgc:concurrentScavenge` on Linux x86-64 virtual machines that use compressed references. In this release, support is now available for Linux x86-64 large-heap virtual machines (non-compressed references).

For more information, see the [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge) option.

#### RSA algorithm support for OpenSSL

OpenSSL v1.1 support for the RSA algorithm is added in this release, providing improved cryptographic performance. OpenSSL support is enabled by default. If you want to turn off support for the RSA algorithm, set the [`-Djdk.nativeRSA`](djdknativersa.md) system property to `false`.

#### `IBM_JAVA_OPTIONS` is deprecated

The VM environment variable `IBM_JAVA_OPTIONS` is deprecated and is replaced by `OPENJ9_JAVA_OPTIONS`. `IBM_JAVA_OPTIONS` will be removed in a future release. For more information about the use of this variable, see the [general options](env_var.md#general-options) in [Environment variables](env_var.md).

### Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.11.0 and version 0.12.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.12/0.12.md).

## Version 0.12.1

The following change is implemented since version 0.12.0:

By default, OpenJ9 provides native cryptographic acceleration using OpenSSL version 1.1.x for the Digest, CBC, GCM, and RSA algorithms. Under certain circumstances acceleration of the Digest algorithm was found to cause a segmentation error. Cryptographic acceleration of the Digest algorithm is now turned off by default. The system property `-Djdk.nativeDigest` cannot be used to turn on support. This property is ignored by the VM.

### Full release information

Release notes to describe the changes between Eclipse OpenJ9 version 0.12.0 and version 0.12.1 releases, can be found in the [OpenJ9 GitHub repository](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.12/0.12.1.md).



<!-- ==== END OF TOPIC ==== version0.12.md ==== -->
