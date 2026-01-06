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

# What's new in version 0.47.0

The following new features and notable changes since version 0.46.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [`-Xshareclasses` option automatically enables `-XX:+ShareOrphans`](#-xshareclasses-option-automatically-enables-xxshareorphans)
- [Restrictions in the `-XX:[+|-]ShareOrphans` option fixed](#restrictions-in-the-xx-shareorphans-option-fixed)
- ![Start of content that applies to Java 23 and later](cr/java23plus.png) [New JDK 23 features](#new-jdk-23-features) ![End of content that applies to Java 23 and later](cr/java_close.png)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.47.0 supports OpenJDK 23.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### `-Xshareclasses` option automatically enables `-XX:+ShareOrphans`

The `-XX:+ShareOrphans` option automatically enables the `-Xshareclasses` option. From release 0.47.0 onwards, if the `-Xshareclasses` option is specified in the command line, it automatically enables the `-XX:+ShareOrphans` option.

For more information, see [`-XX:[+|-]ShareOrphans`](xxshareorphans.md).

### Restrictions in the `-XX:[+|-]ShareOrphans` option fixed

The `-XX:[+|-]ShareOrphans` option had the following restrictions:

- The class comparison might not detect the removal of method access modifiers. For example, a change of a method from public to package-private.
- `java.lang.StackTraceElement.getClassLoaderName()` might return null for classes that are stored in the shared cache.

These restrictions are no longer applicable from this release onwards.

### ![Start of content that applies to Java 23 and later](cr/java23plus.png) New JDK 23 features

The following features are supported by OpenJ9:

- [JEP 469](https://openjdk.java.net/jeps/469): Vector API (Eighth Incubator)
- [JEP 471](https://openjdk.java.net/jeps/471): Deprecate the Memory-Access Methods in sun.misc.Unsafe for Removal
- [JEP 481](https://openjdk.java.net/jeps/481): Scoped Values (Third Preview)

The following features are implemented in OpenJDK and available in any build of OpenJDK 23 with OpenJ9:

- [JEP 455](https://openjdk.java.net/jeps/455): Primitive Types in Patterns, instanceof, and switch (Preview)
- [JEP 466](https://openjdk.java.net/jeps/466): Class-File API (Second Preview)
- [JEP 467](https://openjdk.java.net/jeps/467): Markdown Documentation Comments
- [JEP 473](https://openjdk.java.net/jeps/473): Stream Gatherers (Second Preview)
- [JEP 476](https://openjdk.java.net/jeps/476): Module Import Declarations (Preview)
- [JEP 477](https://openjdk.java.net/jeps/477): Implicitly Declared Classes and Instance Main Methods (Third Preview)
- [JEP 480](https://openjdk.java.net/jeps/480): Structured Concurrency (Third Preview)
- [JEP 482](https://openjdk.java.net/jeps/482): Flexible Constructor Bodies (Second Preview)

You can find the full list of features for JDK 23 at the [OpenJDK project](https://openjdk.org/projects/jdk/23/).
Any remaining features that are listed either do not apply to OpenJ9 or are not implemented and hence not applicable to OpenJ9 in this release. ![End of content that applies to Java 23 and later](cr/java_close.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.46.0 and v0.47.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.47/0.47.md).

<!-- ==== END OF TOPIC ==== version0.47.md ==== -->
