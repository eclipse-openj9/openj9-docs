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

# What's new in version 0.45.0

The following new features and notable changes since version 0.44.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 22 and later](cr/java22plus.png) [New JDK 22 features](#new-jdk-22-features) ![End of content that applies to Java 22 and later](cr/java_close.png)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.45.0 supports OpenJDK 22.

OpenJDK 22 with Eclipse OpenJ9 is *not* a long term support (LTS) release.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 22 and later](cr/java22plus.png) New JDK 22 features

The following features are supported by OpenJ9:

- [JEP 454](https://openjdk.java.net/jeps/454): Foreign Function & Memory API
- [JEP 460](https://openjdk.java.net/jeps/460): Vector API (Seventh Incubator)
- [JEP 464](https://openjdk.java.net/jeps/464): Scoped Values (Second Preview)

The following features are implemented in OpenJDK and available in any build of OpenJDK 22 with OpenJ9:

- [JEP 447](https://openjdk.java.net/jeps/447): Statements before super(...) (Preview)
- [JEP 456](https://openjdk.java.net/jeps/456): Unnamed Variables & Patterns
- [JEP 457](https://openjdk.java.net/jeps/457): Class-File API (Preview)
- [JEP 458](https://openjdk.java.net/jeps/458): Launch Multi-File Source-Code Programs
- [JEP 459](https://openjdk.java.net/jeps/459): String Templates (Second Preview)
- [JEP 461](https://openjdk.java.net/jeps/461): Stream Gatherers (Preview)
- [JEP 462](https://openjdk.java.net/jeps/462): Structured Concurrency (Second Preview)
- [JEP 463](https://openjdk.java.net/jeps/463): Implicitly Declared Classes and Instance Main Methods (Second Preview)

You can find the full list of features for JDK 22 at the [OpenJDK project](https://openjdk.org/projects/jdk/22/).
Any remaining features that are listed either do not apply to OpenJ9 or are not implemented and hence not applicable to OpenJ9 in this release. ![End of content that applies to Java 22 and later](cr/java_close.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.44.0 and v0.45.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.45/0.45.md).

<!-- ==== END OF TOPIC ==== version0.45.md ==== -->
