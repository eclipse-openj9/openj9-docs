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

# What's new in version 0.41.0

The following new features and notable changes since version 0.40.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) [New `-XX:[+|-]ShowCarrierFrames` option added](#new-xx-showcarrierframes-option-added) ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)
- ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) [New `-XX:ContinuationCache` option added](#new-xxcontinuationcache-option-added) ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)
- ![Start of content that applies only to Java 11+ (LTS)](cr/java11plus.png) [`-XX:+CompactStrings` option enabled by default](#-xxcompactstrings-option-enabled-by-default) ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)
- [Change in behavior of `-Xshareclasses:readonly`](#change-in-behavior-of-xshareclassesreadonly)
- [New `-XX:[+|-]EnableDynamicAgentLoading` option added](#new-xx-enabledynamicagentloading-option-added)
- [New `-XX:[+|-]UseZlibNX` option added](#new-xx-usezlibnx-option-added)
- [Support for OpenSSL 3.x](#support-for-openssl-3x)
- [Performance improvement](#performance-improvement)
- [Support added for the `com.sun.management.ThreadMXBean.getThreadAllocatedBytes()` API](#support-added-for-the-comsunmanagementthreadmxbeangetthreadallocatedbytes-api)
- [Change in behavior of the `-Djava.security.manager` system property for OpenJDK version 11](#change-in-behavior-of-the-djavasecuritymanager-system-property-for-openjdk-version-11)
- [JITServer support for Linux on AArch64](#jitserver-support-for-linux-on-aarch64)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.41.0 supports OpenJDK 8, 11, and 17.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) New `-XX:[+|-]ShowCarrierFrames` option added

A VM maintains multiple platform threads that are used as carrier threads to run the virtual threads. Although the virtual thread runs on a carrier thread, the stack trace of the virtual thread and its carrier thread are separate. You can use the `-XX:+ShowCarrierFrames` option to add the stack trace of the carrier thread in addition to the virtual thread stack trace to the `Throwable.getStackTrace()` method, if an exception occurs.

For more information, see [`-XX:[+|-]ShowCarrierFrames`](xxshowcarrierframes.md). ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

### ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) New `-XX:ContinuationCache` option added

When a virtual thread is created, it is associated with a continuation, which holds the target task of the virtual thread. The VM saves the current thread state in an internal data structure that is linked with the continuation and allocates the memory for that structure. The VM stores the internal data structure in a continuation cache. New continuations can reuse the cached structure instead of allocating new memory for it.

You can optimize the virtual thread performance by tuning the continuation tier 1 and 2 cache size with the [`-XX:ContinuationCache`](xxcontinuationcache.md) option. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

### ![Start of content that applies only to Java 11+ (LTS)](cr/java11plus.png) `-XX:+CompactStrings` option enabled by default

Like HotSpot, the`-XX:+CompactStrings` option is now enabled by default on Java&trade; 11 and later. In the earlier versions, this option is disabled by default.

For more information, see [`-XX:[+|-]CompactStrings`](xxcompactstrings.md). ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)

### Change in behavior of `-Xshareclasses:readonly`

In the earlier releases, if the `-Xshareclasses:readonly` option and the JITServer AOT cache feature were both enabled at the same time at a JITServer client, the client could not use the JITServer AOT cache because the cache for storing data that the JITServer AOT cache needed was read-only.

Now, with the change in behavior of the [`-Xshareclasses:readonly`](xshareclasses.md#readonly) option, the shared classes cache startup creates a temporary new (writable) top layer that the JITServer AOT cache can use to store data that it needs to function.

### New `-XX:[+|-]EnableDynamicAgentLoading` option added

This option enables or disables the dynamic loading of agents into a running VM. The `-XX:+EnableDynamicAgentLoading` option is the default setting.

![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) For Java 21 and later, warnings are issued when the agents are loaded dynamically into a running VM after startup without specifying the `-XX:+EnableDynamicAgentLoading` option and the same agents were not loaded before. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

For more information, see [`-XX:[+|-]EnableDynamicAgentLoading`](xxenabledynamicagentloading.md).

### New `-XX:[+|-]UseZlibNX` option added

AIX&reg; system adds the `zlibnx` library directory path in the `LIBPATH` environment variable by default, if it is available in the system. You can control the loading of the `zlibnx` library by using the [`-XX:[+|-]UseZlibNX`](xxusezlibnx.md) option.

### Support for OpenSSL 3.x

OpenSSL 3.x is now supported on all operating systems. For more information about OpenSSL support, see [`Cryptographic operations`](introduction.md#cryptographic-operations).

### Performance improvement

Performance of algorithms with the use of OpenSSL version 3 and later is now enhanced. Improved algorithms include SHA256, AES, HmacSHA256, and ChaCha20. To obtain these improved algorithms and further enhance the performance of these algorithms on Linux&reg; and AIX, use OpenSSL 3.0.12 or a later 3.0.x version, or 3.1.4 or a later 3.1.x version.

### Support added for the `com.sun.management.ThreadMXBean.getThreadAllocatedBytes()` API

With this release, the OpenJ9 VM implementation supports thread memory allocation measurement (`com.sun.management.ThreadMXBean.getThreadAllocatedBytes()` API). The `isThreadAllocatedMemorySupported()` method now returns true instead of false. The `setThreadAllocatedMemoryEnabled()` and `isThreadAllocatedMemoryEnabled()` methods do not throw the "UnsupportedOperationException" error now and are enabled by default.

Support for the `com.sun.management.ThreadMXBean.getThreadAllocatedBytes()` API is not added on z/OS&reg; platforms in this release. In the coming future z/OS release, the support for this API will be added on z/OS platforms as well.

### Change in behavior of the `-Djava.security.manager` system property for OpenJDK version 11

From OpenJDK version 18 onwards, if you enable the `SecurityManager` at runtime by calling the `System.setSecurityManager()` API, you must set the `-Djava.security.manager=allow` option. To disable the `SecurityManager`, you must specify the `-Djava.security.manager=disallow` option. If an application is designed to run on multiple OpenJDK versions, the same command line might be used across multiple versions. Because of this use of the same command line across multiple versions, in OpenJDK versions before version 18, the runtime attempts to load a `SecurityManager` with the class name `allow` or `disallow` resulted in an error and the application was not starting.

To resolve this issue, OpenJDK version 17 ignores these options. With this release, OpenJDK version 11 also ignores the `allow` and `disallow` keywords, if specified.

### JITServer support for Linux on AArch64
JITServer technology is now a fully supported feature on Linux on AArch64 systems. For more information, see [JITServer technology](jitserver.md).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.40.0 and v0.41.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.41/0.41.md).

<!-- ==== END OF TOPIC ==== version0.41.md ==== -->
