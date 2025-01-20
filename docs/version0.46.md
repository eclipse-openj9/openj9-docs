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

# What's new in version 0.46.0

The following new features and notable changes since version 0.45.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [MD5 message digest algorithm support for OpenSSL](#md5-message-digest-algorithm-support-for-openssl)
- [Support added for the `com.sun.management.ThreadMXBean.getTotalThreadAllocatedBytes()` API](#support-added-for-the-comsunmanagementthreadmxbeangettotalthreadallocatedbytes-api)
- [The JITServer AOT caching feature enabled by default at the JITServer server](#the-jitserver-aot-caching-feature-enabled-by-default-at-the-jitserver-server)
- [The extended Hot Code Replace (HCR) capability disabled and `-XX:[+|-]EnableExtendedHCR` option added](#the-extended-hot-code-replace-hcr-capability-disabled-and-xx-enableextendedhcr-option-added)
- [New system property added to improve `jcmd` attaching in case of the `SocketException` error on Windows&trade; platform](#new-system-property-added-to-improve-jcmd-attaching-in-case-of-the-socketexception-error-on-windows-platform)
- [`-Xtgc:allocation` report includes core allocation cache statistics per thread](#-xtgcallocation-report-includes-core-allocation-cache-statistics-per-thread)
- [New `-XX:[+|-]ShareOrphans` option added](#new-xx-shareorphans-option-added)
- [New `-XX:[+|-]JITServerAOTCacheIgnoreLocalSCC` option added](#new-xx-jitserveraotcacheignorelocalscc-option-added)
- [New `-XdynamicHeapAdjustment` option added](#new-xdynamicheapadjustment-option-added)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.46.0 supports OpenJDK 8, 11, 17, 21, and 22.

CentOS 6, CentOS 7, Red Hat Enterprise Linux (RHEL) 6, and RHEL 7 are removed from the list of supported platforms.

RHEL 8.6 and 9.0 are out of support. RHEL 8.8 and 9.2 are the new minimum operating system levels.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### MD5 message digest algorithm support for OpenSSL

OpenSSL native cryptographic support is added for the MD5 message digest algorithm, providing improved cryptographic performance. OpenSSL support is enabled by default. If you want to turn off support for the MD5 message digest algorithm, set the [`-Djdk.nativeDigest`](djdknativedigest.md) system property to `false`.

### Support added for the `com.sun.management.ThreadMXBean.getTotalThreadAllocatedBytes()` API

With this release, the OpenJ9 VM implementation supports measurement of the total memory allocation for all threads (`com.sun.management.ThreadMXBean.getTotalThreadAllocatedBytes()` API).

The `getTotalThreadAllocatedBytes()` method now returns the total thread allocated bytes instead of `-1`.

### The JITServer AOT caching feature enabled by default at the JITServer server

`-XX:+JITServerUseAOTCache` is the default setting at the JITServer server now. That means that you don't have to specify the `-XX:+JITServerUseAOTCache` option at the server to enable the JITServer AOT caching feature.

Although this option is by default enabled at the server, it is still disabled for the JITServer clients. The clients that want to use the JITServer AOT caching, must still specify the `-XX:+JITServerUseAOTCache` option on the command line. Also, now the clients don't have to enable the [shared classes cache](https://www.eclipse.org/openj9/docs/shrc/) feature to use the `-XX:+JITServerUseAOTCache` option.

For more information, see [ `-XX:[+|-]JITServerUseAOTCache`](xxjitserveruseaotcache.md).

### The extended Hot Code Replace (HCR) capability disabled and `-XX:[+|-]EnableExtendedHCR` option added

By default, the extended HCR capability in the VM is disabled for all OpenJDK versions, which is a change from the previous releases. You can enable the HCR capability by using the new option, [`-XX:+EnableExtendedHCR`](xxenableextendedhcr.md) option.

The extended HCR feature is deprecated in this release and will be removed in a future release. From OpenJDK 25 onwards, extended HCR will not be supported. Following that, the extended HCR support will be removed from other earlier OpenJDK versions also.

### New system property added to improve `jcmd` attaching in case of the `SocketException` error on Windows platform

When the `jcmd` tool sends a command to a running VM, the command might throw the `Socket Exception` error on Windows platform. Instead of failing the attaching request, you can specify the number of times the tool retries attaching to the target VM with the new system property, [`-Dcom.ibm.tools.attach.retry`](dcomibmtoolsattachretry.md).

### `-Xtgc:allocation` report includes core allocation cache statistics per thread

The [`-Xtgc:allocation`](xtgc.md#allocation) option prints thread-specific allocation cache (TLH) statistics in addition to the cumulative allocation statistics.

### New `-XX:[+|-]ShareOrphans` option added

When `-Xshareclasses` was specified, only those class loaders that implemented the OpenJ9's public shared classes cache APIs (and its child class loaders) could store classes to the shared classes cache. Custom class loaders that did not implement these cache APIs cannot pass the module or class path information to the VM. Classes of such class loaders were not stored to the cache.

You can enable class sharing from all class loaders, irrespective of whether the class loader implements the shared classes cache API, with the `-XX:+ShareOrphans` option.

For more information, see [`-XX:[+|-]ShareOrphans`](xxshareorphans.md).

### New `-XX:[+|-]JITServerAOTCacheIgnoreLocalSCC` option added

From this release onwards, the default behavior of the client when it uses the JITServer AOT cache is to bypass its local shared classes cache (if one is set up) during JITServer AOT cache compilations. You can control how the JITServer AOT cache feature interacts with the local cache at JITServer client VMs with the [`-XX:[+|-]JITServerAOTCacheIgnoreLocalSCC`](xxjitserveraotcacheignorelocalscc.md) option.

### ![Java 11 (LTS) and later](docs/cr/java11plus.png) New `-XdynamicHeapAdjustment` option added

By default, if a checkpoint is taken in a container with no memory limits and then restored in a container with memory limits, the restored VM instance does not detect the memory limits.

You can now create a single image file and restore it on various nodes with different memory limits. The new option [`-XdynamicHeapAdjustment`](xdynamicheapadjustment.md) automatically adjusts the maximum Java heap size ([`-Xmx`](xms.md)) and minimum Java heap size ([`-Xms`](xms.md)) values such that they are within the physical memory limitations on the system. ![End of content that applies only to Java 11 and later](cr/java_close.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.45.0 and v0.46.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.46/0.46.md).

<!-- ==== END OF TOPIC ==== version0.46.md ==== -->
