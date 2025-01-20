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


# What's new in version 0.13.0

The following new features and notable changes since version 0.12.1 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies only to Java 12](cr/java12.png) [Support for OpenSSL 1.0.2](#support-for-openssl-102)
- ![Start of content that applies only to Java 12](cr/java12.png) [New Java&trade; process status tool](#new-java-process-status-tool)
- [Writing a Java dump to STDOUT or STDERR](#writing-a-java-dump-to-stdout-or-stderr)
- [Better diagnostic information for Linux systems that implement control groups](#better-diagnostic-information-for-linux-systems-that-implement-control-groups)
- [Improved support for pause-less garbage collection](#improved-support-for-pause-less-garbage-collection)
- ![Start of content that applies only to Java 12 plus](cr/java12plus.png) [New JDK 12 features](#new-jdk-12-features)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.13.0 supports OpenJDK 12, which is available from the AdoptOpenJDK community at the following link:

- [OpenJDK version 12](https://adoptopenjdk.net/archive.html?variant=openjdk12&jvmVariant=openj9)

OpenJDK 12 with Eclipse OpenJ9 is not a long term support (LTS) release.

The latest builds of OpenJDK with OpenJ9 for Java 8 and 11 at the AdoptOpenJDK community are for Eclipse OpenJ9 release 0.12.0. Features mentioned in these release notes are not available in these builds. Although it might be possible to build an OpenJDK 8 or OpenJDK 11 with OpenJ9 0.13.0, testing at the project is not complete and therefore support for any of these features is not available.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Support for OpenSSL 1.0.2

![Start of content that applies only to Java 12](cr/java12.png)

OpenSSL cryptographic support is extended to include OpenSSL 1.0.2 for the Digest, CBC, GCM, and RSA algorithms. Support is enabled by default. On Linux and AIX platforms, the OpenSSL libraries are expected to be available on the system path. For more information about cryptographic acceleration with OpenSSL, see [Cryptographic operations](introduction.md#cryptographic-operations).

In addition, support for the OpenSSL Digest algorithm is re-enabled in this release following the resolution of issue [#4530](https://github.com/eclipse-openj9/openj9/issues/4530).

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Warning:** Earlier versions of OpenJDK with OpenJ9 from the AdoptOpenJDK project bundle OpenSSL as part of the binary package. On Linux and AIX systems, OpenSSL is no longer bundled and the libraries are expected to be available on the system path.

![End of content that applies only to Java 12](cr/java_close.png)

### New Java process status tool

![Start of content that applies only to Java 12](cr/java12.png)

A Java process status tool (`jps`) is available for querying running Java processes. For more information, see [Java process status](tool_jps.md).

![End of content that applies only to Java 12](cr/java_close.png)

### Writing a Java dump to STDOUT or STDERR

You can now write a Java dump file to STDOUT or STDERR by using the [`-Xdump`](xdump.md) command-line option. See [Writing to `STDOUT`/`STDERR`](xdump.md#writing-to-stdoutstderr) for details.

### Better diagnostic information for Linux systems that implement control groups

If you use control groups (cgroups) to manage resources on Linux systems, information about CPU and memory limits is now recorded in a Java dump file. This information is particularly important for applications that run in Docker containers, because when resource limits are set inside a container, the Docker Engine relies on cgroups to enforce the settings. If you are getting a Java `OutOfMemoryError` error because a container limit has been set on the amount of memory available to an application and this allocation is not sufficient, you can diagnose this problem from the Java dump file. You can find the cgroup information in the ENVINFO section. For sample output, see [Java dump (ENVINFO)](dump_javadump.md#envinfo).

### Improved support for pause-less garbage collection

Concurrent scavenge mode is now supported on the following platforms:

- Linux on POWER LE
- AIX

For more information, see the [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge) option.

### ![Start of content that applies to Java 12 plus](cr/java12plus.png) New JDK 12 features

The following feature is supported by OpenJ9:

- [JEP 334](https://openjdk.org/jeps/334): JVM Constants API

The following feature is implemented in OpenJDK and available in any builds of OpenJDK 12 with OpenJ9:

- [JEP 325](https://openjdk.org/jeps/325): Switch Expressions (Preview) 

You can find the full list of features for JDK 12 at the [OpenJDK project](https://openjdk.org/projects/jdk/12/). Any remaining features that are listed do not apply to OpenJ9.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.12.1 and version 0.13.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.13/0.13.md).

<!-- ==== END OF TOPIC ==== version0.13.md ==== -->
