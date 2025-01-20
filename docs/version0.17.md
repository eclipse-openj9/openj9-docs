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


# What's new in version 0.17.0

The following new features and notable changes since version 0.16.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [New shared classes cache suboptions for layered caches](#new-shared-classes-cache-suboptions-for-layered-caches)
- [New shared classes cache suboption to skip disk space check](#new-shared-classes-cache-suboption-to-skip-disk-space-check)
- [Option to share 'Unsafe' classes](#option-to-share-unsafe-classes)
- [Option to record class relationships in the verifier](#option-to-record-class-relationships-in-the-verifier)
- [Support for the IBM z15&reg; processor](#support-for-the-ibm-z15-processor)
- [Digest algorithm is re-enabled](#digest-algorithm-is-re-enabled)
- [Direct Dump Reader (DDR) VM restriction removed](#direct-dump-reader-ddr-vm-restriction-removed)
- [The format of the HOOKS section of a Java dump has changed](#the-format-of-the-hooks-section-of-a-java-dump-has-changed)
- [LUDCL caching disabled by default](#ludcl-caching-disabled-by-default)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.17.0 supports OpenJDK 8, 11, and 13. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 13](https://adoptopenjdk.net/archive.html?variant=openjdk13&jvmVariant=openj9)

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The Windows&reg; and macOS&reg; binaries from the AdoptOpenJDK community for OpenJDK 8, 11, and 13 have been updated to OpenSSL v1.1.1d. Look for the following release names to identify these packages:

- OpenJDK 8: `jdk8u232-b09.1_openj9-0.17.0`
- OpenJDK 11: `jdk-11.0.5+10.1_openj9-0.17.0`
- OpenjDK 13: `jdk-13.0.1+9.1_openj9-0.17.0)`

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The last release of OpenJDK 8 and 11 from AdoptOpenJDK is Eclipse OpenJ9 0.15.1. To read about other features and changes in the VM since 0.15.1, check the [Version 0.16.0](version0.16.md) release notes too.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### New shared classes cache suboptions for layered caches

**(Experimental, 64-bit only)**

New suboptions are available for creating layered caches, where a cache builds on another cache with the same name. You can use these suboptions to save space when building a Docker container, for example.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Because these suboptions are experimental, do not use them in a production environment.

The new options are:

- [`createLayer`](xshareclasses.md#createlayer)
- [`layer=<number>`](xshareclasses.md#layer) (see this section for more information about layered caches)
- [`printTopLayerStats`](xshareclasses.md#printtoplayerstats-cache-utility)
- [`destroyAllLayers`](xshareclasses.md#destroyalllayers)

### New shared classes cache suboption to skip disk space check

When creating a persistent shared classes cache, the OpenJ9 VM checks that there is sufficient disk space available on the file
system. For file systems that do not support the checking of free space, you can set the `-Xshareclasses:noPersistentDiskSpaceCheck` option, which causes the VM to skip the space checking operation. If there isn't enough disk space available when the cache is written, a **SIGBUS** or **SIGSEGV** signal occurs and the VM ends. For more information, see the [-Xshareclasses:noPersistentDiskSpaceCheck](xshareclasses.md#nopersistentdiskspacecheck) option.

### Option to share 'Unsafe' classes

Classes created through `Unsafe.defineClass` are now stored by default in the shared classes cache. You can use the `-XX:-ShareUnsafeClasses` option to change the default behavior. For more information, see [-XX:[+|-]ShareUnsafeClasses](xxshareunsafeclasses.md).

### Option to record class relationships in the verifier

A new command line option `-XX:+ClassRelationshipVerifier` allows you to record class relationships in the verifier, which avoids unnecessary class loading and reduces VM startup time. This is a new approach to bytecode verification that delays validating the relationships between classes until the classes are required to be loaded for a program's execution thus loading only those classes that are needed. For more information, see [-XX:[+|-]ClassRelationshipVerifier](xxclassrelationshipverifier.md).

### Support for the IBM z15 processor

This release adds JIT compiler support for exploiting z15 instructions.

### Digest algorithm is re-enabled

Issue [#5611](https://github.com/eclipse-openj9/openj9/issues/5611) is fixed, so support for the Digest algorithm is re-enabled. For more information about this support, see [Cryptographic operations]( introduction.md#cryptographic-operations).

### Direct Dump Reader (DDR) VM restriction removed

Prior to this version, you had to use a 32-bit VM to look at a 32-bit core, and a 64-bit VM to look at a 64-bit core when using DDR. This restriction has now been removed.

### The format of the HOOKS section of a Java dump has changed

The format of the `HOOKS` section of a Java dump, which shows internal VM event callbacks, has changed:

- Recorded times have been changed from milliseconds to microseconds to provide increased precision.
- A new field, `3HKTOTALTIME`, is included, which gives the total duration of previous events.
- The hook data is now reset after each Java dump.

For more information and an example of the new format, see [Java dump: HOOKS](dump_javadump.md#hooks)

### LUDCL caching disabled by default

By caching the Latest User Defined Class Loader (LUDCL), Java applications that use deserialization extensively can see a performance improvement. This
capability is controlled by the [-Dcom.ibm.enableClassCaching](dcomibmenableclasscaching.md) system property and is now disabled by default due to [issue #7332](https://github.com/eclipse-openj9/openj9/issues/7332).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Versions of the documentation before 0.17.0 incorrectly identified this property as disabled by default when it was actually enabled by default in the VM.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.16 and version 0.17.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.17/0.17.md).

<!-- ==== END OF TOPIC ==== version0.17.md ==== -->
