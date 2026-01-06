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

# What's new in version 0.29.1

The following new features and notable changes since version 0.29.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 17 plus](cr/java17plus.png) [New JDK 17 features](#new-jdk-17-features)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.29.1 supports OpenJDK 17.

AArch64 Linux is now a fully supported, production-ready target for OpenJDK 17.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 17 plus](cr/java17plus.png) New JDK 17 features

The following features are supported by OpenJ9:

- [JEP 409](https://openjdk.org/jeps/409): Sealed Classes
    - Promoted from preview to a production-ready feature in this release.
- [JEP 411](https://openjdk.org/jeps/411): Deprecate the Security Manager for Removal

The following features will be supported by OpenJ9 in a future release:

- [JEP 391](https://openjdk.org/jeps/391): macOS/AArch64 Port
- [JEP 412](https://openjdk.org/jeps/412): Foreign Function & Memory API (Incubator)
- [JEP 414](https://openjdk.org/jeps/414): Vector API (Second Incubator)

The following features are implemented in OpenJDK and available in any build of OpenJDK 17 with OpenJ9:

- [JEP 306](https://openjdk.org/jeps/306): Restore Always-Strict Floating-Point Semantics

- [JEP 356](https://openjdk.org/jeps/356): Enhanced Pseudo-Random Number Generators
- [JEP 382](https://openjdk.org/jeps/382): New macOS Rendering Pipeline
- [JEP 398](https://openjdk.org/jeps/398): Deprecate the Applet API for Removal
- [JEP 403](https://openjdk.org/jeps/403): Strongly Encapsulate JDK Internals
- [JEP 406](https://openjdk.org/jeps/406): Pattern Matching for switch (Preview)
- [JEP 407](https://openjdk.org/jeps/407): Remove RMI Activation
- [JEP 415](https://openjdk.org/jeps/415): Context-Specific Deserialization Filters

You can find the full list of features for JDK 17 at the [OpenJDK project](https://openjdk.org/projects/jdk/17/).
Any remaining features that are listed do not apply to OpenJ9.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Applications might be adversely affected by JEP 403
if they make use of internal APIs. You should update your application to use standard APIs. To temporarily work around
this problem, use `--add-opens`, `--add-exports` on the command line for each package required.


## Full release information

To see a complete list of changes between Eclipse OpenJ9 v0.29.0 and v0.29.1 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.29/0.29.1.md).

<!-- ==== END OF TOPIC ==== version0.29.1.md ==== -->
