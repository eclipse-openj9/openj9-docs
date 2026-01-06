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

# What's new in version 0.39.0

The following new features and notable changes since version 0.38.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [EC key generation algorithm support for OpenSSL](#ec-key-generation-algorithm-support-for-openssl)
- [New JDK 20 features](#new-jdk-20-features)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.39.0 supports OpenJDK 20.

OpenJDK 20 with Eclipse OpenJ9 is *not* a long term support (LTS) release.

RHEL 8.4 is out of support. RHEL 8.6 is the new minimum operating system level.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### EC key generation algorithm support for OpenSSL

The EC key generation algorithm can now use the native OpenSSL library for OpenJDK 20. For more information, see [`-Djdk.nativeECKeyGen`](djdknativeeckeygen.md).

### ![Start of content that applies to Java 20 plus](cr/java20plus.png) New JDK 20 features

The following features are supported by OpenJ9:

- [JEP 434](https://openjdk.java.net/jeps/434): Foreign Function & Memory API (Second Preview)
- [JEP 436](https://openjdk.java.net/jeps/436): Virtual Threads (Second Preview)
- [JEP 437](https://openjdk.java.net/jeps/437): Structured Concurrency (Second Incubator)
- [JEP 438](https://openjdk.java.net/jeps/438): Vector API (Fifth Incubator)

The following features are implemented in OpenJDK and available in any build of OpenJDK 20 with OpenJ9:

- [JEP 432](https://openjdk.java.net/jeps/432): Record Patterns (Second Preview)
- [JEP 433](https://openjdk.java.net/jeps/433): Pattern Matching for switch (Fourth Preview)

You can find the full list of features for JDK 20 at the [OpenJDK project](https://openjdk.org/projects/jdk/20/).
Any remaining features that are listed either do not apply to OpenJ9 or are not implemented and hence not applicable to OpenJ9 in this release.

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.38.0 and v0.39.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.39/0.39.md).

<!-- ==== END OF TOPIC ==== version0.39.md ==== -->
