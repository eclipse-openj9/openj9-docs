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


# What's new in version 0.16.0

The following new features and notable changes since version 0.15.1 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Some class data sharing is enabled by default](#some-class-data-sharing-is-enabled-by-default)
- [Automatic setting of initial heap size is enabled by default](#automatic-setting-of-initial-heap-size-is-enabled-by-default)
- [Option to share VM anonymous classes](#option-to-share-vm-anonymous-classes)
- [Performance improvements for JVMTI watched fields on Power Systems](#performance-improvements-for-jvmti-watched-fields-on-power-systems)
- [Linux on x86: Support for Transparent Huge Pages (THP)](#linux-on-x86-support-for-transparent-huge-pages-thp)
- [New Java&trade; diagnostic command (`jcmd`) tool](#new-jcmd-tool)
- [Changes to the shared classes cache generation number](#changes-to-the-shared-classes-cache-generation-number)
- [![Start of content that applies to Java 13 and later](cr/java13plus.png) The `-Xverify:none` and `-noverify` options are deprecated](#the-xverifynone-and-noverify-options-are-deprecated)
- ![Start of content that applies only to Java 13 plus](cr/java13plus.png) [New JDK 13 features](#new-jdk-13-features)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.16.0 supports OpenJDK 13, which is available from the AdoptOpenJDK community at the following link:

- [OpenJDK version 13](https://adoptopenjdk.net/archive.html?variant=openjdk13&jvmVariant=openj9)

OpenJDK 13 with Eclipse OpenJ9 is not a long term support (LTS) release.

The latest builds of OpenJDK with OpenJ9 for Java 8 and 11 at the AdoptOpenJDK community are for Eclipse OpenJ9 release 0.15.2. Features mentioned in these release notes are not available in these builds. Although it might be possible to build an OpenJDK 8 or OpenJDK 11 with OpenJ9 0.16.0, testing at the project is not complete and therefore support for any of these features is not available.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Some class data sharing is enabled by default

Class data sharing is enabled by default for bootstrap classes, unless your application is running in a container. You can use the `-Xshareclasses` option to change the default behavior, including using `-Xshareclasses:none` to disable all class data sharing. For more information, see [Introduction to class data sharing](shrc.md).

### Automatic setting of initial heap size is enabled by default

OpenJ9 version 0.15.1 introduced the [`-XX:[+|-]UseGCStartupHints`](xxusegcstartuphints.md) option, which, when enabled, turned on the automatic learning and setting of an appropriate heap size for an application. This option is now enabled by default.

### Option to share VM anonymous classes

Prior to version 0.16.0, anonymous classes, those created by `Unsafe.defineAnonymousClass`, were not stored in the shared classes cache. They are now stored there by default, which means they are available for ahead-of-time (AOT) compilation, potentially improving startup performance. A new command, [-XX:[+|-]ShareAnonymousClasses](xxshareanonymousclasses.md), is introduced that enables you to stop anonymous classes being stored in the shared classes cache.

### Performance improvements for JVMTI watched fields on Power Systems

OpenJ9 version 0.14.0 introduced the [`-XX:[+|-]JITInlineWatches`](xxjitinlinewatches.md) option, which turns on JIT operations to improve the performance of JVMTI watched fields. This option, which was enabled by default in version 0.15.1, is now also supported on AIX&reg; and Linux on Power Systems&trade;.

### Linux&reg; on x86: Support for Transparent Huge Pages (THP)

When you use the `madvise` (`/sys/kernel/mm/transparent_hugepage/enabled`) setting on Linux on x86 systems, THP is now enabled by default. To disable this feature, set [`-XX:-TransparentHugePage`](xxtransparenthugepage.md) on the command line when you start your application. The THP setting on other systems remains disabled by default when you use `madvise`, but can be enabled by setting [`-XX:+TransparentHugePage`](xxtransparenthugepage.md).

### New jcmd tool

For compatibility with the reference implementation, OpenJ9 now includes an independent implementation of the `jcmd` tool  for running diagnostic commands on a VM. For more information, see [Java diagnostic command tool](tool_jcmd.md).

### Changes to the shared classes cache generation number

The format of classes that are stored in the shared classes cache is changed, which causes the JVM to create a new shared classes cache rather than re-creating or reusing an existing cache. To save space, you can remove all existing shared caches unless they are in use by an earlier release. As a result of the format change, a `layer` column now appears in the output of the `-Xshareclasses:listAllCaches` option. This change is to support a future enhancement.

For more information about the `-Xshareclasses` option, including the `destroy` options that you can use to remove caches, see [`-Xshareclasses`](xshareclasses.md).

### ![Start of content that applies to Java 13 and later](cr/java13plus.png) The `-Xverify:none` and `-noverify` options are deprecated

The option [`-Xverify:none`](xverify.md) (and its equivalent `-noverify`) is deprecated in Java 13. Both options might be removed in a future release. OpenJ9 issues a warning if these options are used in Java 13 and later versions. ![End of content that applies only to Java 13](cr/java_close.png)

### ![Start of content that applies to Java 13 plus](cr/java13plus.png) New JDK 13 features

The following features are implemented in OpenJDK and available in any builds of OpenJDK 13 with OpenJ9:

- [JEP 353](https://openjdk.org/jeps/353): Reimplement the Legacy Socket API
- [JEP 354](https://openjdk.org/jeps/354): Switch Expressions (Preview)
- [JEP 355](https://openjdk.org/jeps/355): Text Blocks (Preview)

You can find the full list of features for JDK 13 at the [OpenJDK project](https://openjdk.org/projects/jdk/13/). Any remaining features that are listed do not apply to OpenJ9.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.15.1 and version 0.16.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.16/0.16.md).

<!-- ==== END OF TOPIC ==== version0.15.md ==== -->
