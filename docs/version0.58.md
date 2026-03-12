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

# What's new in version 0.58.0

The following new features and notable changes since version 0.57.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [New `-XX:[+|-]UseDebugLocalMap` option is added](#new-xx-usedebuglocalmap-option-is-added)
- ![Start of content that applies to Java 26 and later](cr/java26plus.png) [New JDK 26 features](#new-jdk-26-features) ![End of content that applies to Java 26 and later](cr/java_close.png)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.58.0 supports OpenJDK 26.

OpenJDK 26 with Eclipse OpenJ9 is *not* a long term support (LTS) release.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### New `-XX:[+|-]UseDebugLocalMap` option is added

With the new option, `-XX:+UseDebugLocalMap`, you can now enable the debug local mapper without running the entire VM in debug mode. Earlier this was not possible which significantly impacted performance.

For more information, see [`-XX:[+|-]UseDebugLocalMap`](xxusedebuglocalmap.md).

### ![Start of content that applies to Java 26 and later](cr/java26plus.png) New JDK 26 features

The following features are supported by OpenJ9:

- [JEP 500](https://openjdk.java.net/jeps/500): Prepare to Make Final Mean Final
- [JEP 529](https://openjdk.java.net/jeps/529): Vector API (Eleventh Incubator)

The following features are implemented in OpenJDK and available in any build of OpenJDK 26 with OpenJ9:

- [JEP 504](https://openjdk.java.net/jeps/504): Remove the Applet API
- [JEP 517](https://openjdk.java.net/jeps/517): HTTP/3 for the HTTP Client API
- [JEP 524](https://openjdk.java.net/jeps/524): PEM Encodings of Cryptographic Objects (Second Preview)
- [JEP 525](https://openjdk.java.net/jeps/525): Structured Concurrency (Sixth Preview)
- [JEP 526](https://openjdk.java.net/jeps/526): Lazy Constants (Second Preview)
- [JEP 530](https://openjdk.java.net/jeps/530): Primitive Types in Patterns, instanceof, and switch (Fourth Preview)

You can find the full list of features for JDK 26 at the [OpenJDK project](https://openjdk.org/projects/jdk/26/).
Any remaining features that are listed either do not apply to OpenJ9 or are not implemented and hence not applicable to OpenJ9 in this release. ![End of content that applies to Java 26 and later](cr/java_close.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.57.0 and v0.58.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.58/0.58.md).

<!-- ==== END OF TOPIC ==== version0.58.md ==== -->
