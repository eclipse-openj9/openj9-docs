<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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


# What's new in version 0.20.0

The following new features and notable changes since v 0.19.0 are included in this release:

- [Binaries and supported environments](#binaries-and-supported-environments)
- [Balanced GC uses double mapping of arraylets by default on Linux systems](#balanced-gc-policy-uses-double-mapping-of-arraylets-by-default-on-linux-systems)


## Features and changes

### Binaries and supported environments

OpenJ9 releases 0.20.0 supports OpenJDK 8, 11, and 14. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 14](https://adoptopenjdk.net/archive.html?variant=openjdk14&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Balanced GC uses double mapping of arraylets by default on Linux systems

The Balanced GC uses a region-based policy to increase the efficiency of garbage collection. To support large arrays that require more memory than is available in a single region, the Balanced GC uses an arraylet representation that splits the array between several (not necessarily contiguous) regions. However, when JNI is being used, all the elements of the array must normally be copied to a temporary contiguous array. To avoid this overhead, a second set of contiguous virtual memory addresses can be mapped to the original physical memory addresses. Double mapping is enabled by default on Linux systems. To learn more about arraylets and double mapping, see  [Garbage collection: Balanced policy](gc.md#balanced-policy).


## Full release information

To see a complete list of changes between Eclipse OpenJ9 v 0.19.0 and v 0.20.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.20/0.20.md).

<!-- ==== END OF TOPIC ==== version0.20.md ==== -->
