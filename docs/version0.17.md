<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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
* [2] http://openjdk.java.net/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->


# What's new in version 0.17

The following new features and notable changes since v.0.16 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Digest algorithm is re-enabled](#digest-algorithm-is-re-enabled)
- [Option to share 'Unsafe' classes](#option-to-share-unsafe-classes)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.17.0 supports OpenJDK 8, 11, and 13. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 13](https://adoptopenjdk.net/archive.html?variant=openjdk13&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Digest algorithm is re-enabled

Issue [#5611](https://github.com/eclipse/openj9/issues/5611) is fixed, so support for the Digest algorithm is re-enabled. For more information about this support, see [Cryptographic operations]( introduction.md#cryptographic-operations).

### Option to share 'Unsafe' classes

Classes created through `Unsafe.defineClass` are now stored by default in the shared classes cache. You can use the `-XX:-ShareUnsafeClasses` option to change the default behavior. For more information, see [-XX:[+|-]ShareUnsafeClasses](xxshareunsafeclasses.md).


## Full release information

To see a complete list of changes between Eclipse OpenJ9 V0.16 and V0.17.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.17/0.17.md).

<!-- ==== END OF TOPIC ==== version0.17.md ==== -->
