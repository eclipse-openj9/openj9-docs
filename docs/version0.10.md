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


# What's new in version 0.10.0

The following new features and notable changes since v.0.9.0 are included in this release:

- [New binaries and changes to supported environments.](#binaries-and-supported-environments)
- [Change to the default shared classes cache size for OpenJDK 8 builds](#change-to-the-default-shared-classes-cache-size)
- [New information for the SHARED CLASSES section of a Javadump file](#new-information-for-the-shared-classes-section-of-a-java-dump-file)
- [Support for OpenJDK HotSpot options](#support-for-openjdk-hotspot-options)
- ![Start of content that applies only to Java 11plus](cr/java11plus.png) [New JDK 11 features](#new-jdk-11-features)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.10.0 supports OpenJDK 11, which is available from the AdoptOpenJDK community at the following link:

- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)

OpenJDK 11 with Eclipse OpenJ9 is a long term support (LTS) release and supersedes OpenJDK 10 with Eclipse OpenJ9.

Although it is possible to build an OpenJDK v8 with the OpenJ9 0.10.0 release level, testing at the project is not complete and therefore support is not available.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md)

### Change to the default shared classes cache size

For OpenJDK 8 builds, the default shared classes cache size is increased from 16 MB to 300 MB, with a "soft" maximum limit for the initial size of the cache set to 64 MB. Certain exceptions apply. For more information, see [-Xshareclasses](xshareclasses.md). The new default also applies to OpenJDK 11 builds.

### New information for the SHARED CLASSES section of a Java dump file

The value of the soft maximum size (`-Xscmx`) of the shared classes cache is now recorded in the `SHARED CLASSES` section of a Java dump file against the string `2SCLTEXTSMB`. For example output, see [Java dump](dump_javadump.md).

### Support for OpenJDK HotSpot options

For compatibility, the following OpenJDK HotSpot options are now supported by OpenJ9:

- [-XX:HeapDumpPath](xxheapdumppath.md)
- [-XX:\[+|-\]HeapDumpOnOutOfMemory](xxheapdumponoutofmemory.md)
- [-XX:ActiveProcessorCount](xxactiveprocessorcount.md)

### ![Start of content that applies to Java 11 plus](cr/java11plus.png) New JDK 11 features

The following features are supported by OpenJ9:

- [JEP 181](https://openjdk.org/jeps/181): Nest-Based Access Control
- [JEP 309](https://openjdk.org/jeps/309): Dynamic Class-File Constants
- [JEP 320](https://openjdk.org/jeps/320): Remove the Java EE and CORBA Modules
- [JEP 321](https://openjdk.org/jeps/321): HTTP Client (Standard)
- [JEP 323](https://openjdk.org/jeps/323): Local-Variable Syntax for Lambda Parameters
- [JEP 324](https://openjdk.org/jeps/324): Key Agreement with Curve25519 and Curve448
- [JEP 329](https://openjdk.org/jeps/329): ChaCha20 and Poly1305 Cryptographic Algorithms
- [JEP 330](https://openjdk.org/jeps/330): Launch Single-File Source-Code Programs
- [JEP 332](https://openjdk.org/jeps/332): Transport Layer Security (TLS) 1.3
- [JEP 335](https://openjdk.org/jeps/335): Deprecate the Nashorn JavaScript Engine
- [JEP 336](https://openjdk.org/jeps/336): Deprecate the Pack200 Tools and API

You can find the full list of features for JDK 11 at the [OpenJDK project](https://openjdk.org/projects/jdk/11/). Any remaining features that are listed do not apply to OpenJ9.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.9.0 and version 0.10.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.10/0.10.md).

<!-- ==== END OF TOPIC ==== version0.10.md ==== -->
