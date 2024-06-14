<!--
* Copyright (c) 2017, 2024 IBM Corp. and others
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

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.46.0 supports OpenJDK 8, 11, 17, 21, and 22.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### MD5 message digest algorithm support for OpenSSL

OpenSSL native cryptographic support is added for the MD5 message digest algorithm, providing improved cryptographic performance. OpenSSL support is enabled by default. If you want to turn off support for the MD5 message digest algorithm, set the [`-Djdk.nativedigest`](djdknativedigest.md) system property to `false`.

### Support added for the `com.sun.management.ThreadMXBean.getTotalThreadAllocatedBytes()` API

With this release, the OpenJ9 VM implementation supports measurement of the total memory allocation for all threads (`com.sun.management.ThreadMXBean.getTotalThreadAllocatedBytes()` API).

The `getTotalThreadAllocatedBytes()` method now returns the total thread allocated bytes instead of `-1`.

### The JITServer AOT caching feature enabled by default at the JITServer server

`-XX:+JITServerUseAOTCache` is the default setting at the JITServer server now. That means that you don't have to specify the `-XX:+JITServerUseAOTCache` option at the server to enable the JITServer AOT caching feature.

Although this option is by default enabled at the server, it is still disabled for the JITServer clients. The clients that want to use the JITServer AOT caching, must still specify the `-XX:+JITServerUseAOTCache` option on the command line. Also, now the clients don't have to enable the [shared classes cache](https://www.eclipse.org/openj9/docs/shrc/) feature to use the `-XX:+JITServerUseAOTCache` option.

For more information, see [ `-XX:[+|-]JITServerUseAOTCache`](xxjitserveruseaotcache.md).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.45.0 and v0.46.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.46/0.46.md).

<!-- ==== END OF TOPIC ==== version0.46.md ==== -->
