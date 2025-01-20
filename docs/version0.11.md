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


# What's new in version 0.11.0

The following new features and notable changes since version 0.10.0 are included in this release:

- [New binaries and changes to supported environments.](#binaries-and-supported-environments)
- ![Start of content that applies only to Java 8 (LTS)](cr/java8.png) [OpenSSL is now supported for improved native cryptographic performance](#openssl-is-now-supported-for-improved-native-cryptographic-performance)
- ![Start of content that applies only to Java 11 (LTS)](cr/java11.png) [Changes to the location of the default shared cache and cache snapshot directory](#changes-to-the-location-of-the-default-shared-cache-and-cache-snapshot-directory)
- [New class data sharing suboptions](#new-class-data-sharing-suboptions)
- [Container awareness in the Eclipse OpenJ9&trade; VM is now enabled by default](#container-awareness-in-the-openj9-vm-is-now-enabled-by-default)
- [Pause-less garbage collection mode is now available on Linux x86 platforms](#pause-less-garbage-collection-mode-is-now-available-on-linux-x86-platforms)
- [You can now restrict identity hash codes to non-negative values](#you-can-now-restrict-identity-hash-codes-to-non-negative-values)
- [Support for OpenJDK HotSpot options](#support-for-openjdk-hotspot-options)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9 release 0.11.0 provides limited support for the macOS&reg; platform on OpenJDK 11. Early builds of OpenJDK 11 with OpenJ9 on macOS are available at the AdoptOpenJDK project at the following link:

- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)

Support for macOS on OpenJDK 8 is coming soon.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md)

### OpenSSL is now supported for improved native cryptographic performance

![Start of content that applies only to Java 8 (LTS)](cr/java8.png)

OpenSSL is a native open source cryptographic toolkit for Transport Layer Security (TLS) and Secure Sockets Layer (SSL) protocols, which provides improved cryptographic performance compared to the in-built OpenJDK Java cryptographic implementation. The OpenSSL V1.1.x implementation is enabled by default and  supported for the Digest, CBC, and GCM algorithms. Binaries obtained from AdoptOpenJDK include OpenSSL v1.1.x (see Note). For more information about tuning the OpenSSL implementation, see [Performance tuning](introduction.md#cryptographic-operations).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Currently, OpenSSL is not bundled as part of the AdoptOpenJDK AIX binary due to an unresolved problem.

![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)

### Changes to the location of the default shared cache and cache snapshot directory

![Start of content that applies only to Java 11 (LTS)](cr/java11.png)

To increase security, the default shared classes cache directory is changed on non-Windows platforms from `/tmp/javasharedresources/` to the user's home directory, unless you specify `-Xshareclasses:groupAccess`. If you use the `groupAccess` suboption, the default directory is unchanged because some members of the group might not have access to the user home directory.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** For persistent caches, the shared classes cache directory cannot be on an NFS mount. If your user home directory is on an NFS mount, either move it or use the `-Xshareclasses:cacheDir=<directory>` suboption to specify a different directory for the cache.

In general, caches cannot be shared across different Java releases, so you cannot re-use a cache that was created by a previous level of Java 11; if you use the name and cacheDir suboptions to specify an existing cache, the VM attempts to delete the cache and create a new one. However, on Windows, the cache cannot be deleted if it is in use, in which case the VM continues to use the existing cache.

You can find and remove old caches or snapshots by using the following command-line options:

For persistent caches:
- `-Xshareclasses:cacheDir=/tmp/javasharedresources/,listAllCaches` to find the cache
- `-Xshareclasses:cacheDir=/tmp/javasharedresources/,name=<cacheName>,destroy` to remove the cache

For nonpersistent caches or snapshots:
- `-Xshareclasses:cacheDir=/tmp,listAllCaches` to find the item
- `-Xshareclasses:cacheDir=/tmp,name=<snapshotName>,destroySnapshot` to remove the item


![End of content that applies only to Java 11 (LTS)](cr/java_close_lts.png)

### New class data sharing suboptions

`-Xshareclasses:bootClassesOnly`: disables caching of classes that are loaded by non-bootstrap class loaders. This suboption also enables the `nonfatal` suboption, which allows the VM to start even if there was an error creating the shared classes cache.

`-Xshareclasses:fatal`: prevents the VM from starting if there was an error creating the shared classes cache. You might want to enable this suboption when using the -Xshareclasses:bootClassesOnly suboption, to troubleshoot problems when creating the cache.

### Container awareness in the OpenJ9 VM is now enabled by default

When using container technology, applications are typically run on their own and do not need to compete for memory. If the VM detects that it is running in a container environment, and a memory limit for the container is set, the VM automatically adjusts the maximum default Java heap size.

In earlier releases, this behavior was enabled by setting the `-XX:+UseContainerSupport` option. This setting is now the default. For more information
about the Java heap size set for a container, see [-XX:\[+|-\]UseContainerSupport](xxusecontainersupport.md).

### Pause-less garbage collection mode is now available on Linux x86 platforms

Pause-less garbage collection mode is aimed at large heap, response-time sensitive applications. When enabled, the VM attempts to reduce GC pause times. In earlier releases, pause-less garbage collection mode ([`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge)) was available only on IBM z14 hardware. This mode is now available on 64-bit x86 Linux platforms.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- The Generational Concurrent (`gencon`) garbage collection policy must be used. (This is the default policy.)
- Compressed references must be used. See [`-Xcompressedrefs`](xcompressedrefs.md). Compressed references are enabled by default when the maximum heap size (`-Xmx`) ≤ 57 GB. The concurrent scavenge option is ignored if the maximum heap size is > 57 GB.


### You can now restrict identity hash codes to non-negative values

OpenJ9 allows both positive and negative identity hashcodes, which can be problematic if your program (incorrectly) assumes hashcodes can only be positive. However, you can now use the [-XX:\[+|-\]PositiveIdentityHash](xxpositiveidentityhash.md) option to limit identity hash codes to non-negative values.

### Support for OpenJDK HotSpot options

For compatibility, the following OpenJDK HotSpot options are now supported by OpenJ9:

- [-XX:MaxHeapSize](xxinitialheapsize.md)
- [-XX:InitialHeapSize](xxinitialheapsize.md)

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.10.0 and version 0.11.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.11/0.11.md).

<!-- ==== END OF TOPIC ==== version0.11.md ==== -->
