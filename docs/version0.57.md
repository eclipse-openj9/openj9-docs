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

# What's new in version 0.57.0

The following new features and notable changes since version 0.56.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [The `zlib` library bundled on all Linux platforms except Linux on IBM Z](#the-zlib-library-bundled-on-all-linux-platforms-except-linux-on-ibm-z)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.57.0 supports OpenJDK 8, 11, 17, 21, and 25.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### The `zlib` library bundled on all Linux platforms except Linux on IBM Z

The `zlib` compression library is now bundled on all Linux&reg; platforms except Linux on IBM Z&reg;. You don't have to specifically install the `zlib` library with the operating system for data compression and decompression.

For more information on hardware-accelerated data compression and decompression, see [Hardware acceleration](introduction.md#hardware-acceleration).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.56.0 and v0.57.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.57/0.57.md).

<!-- ==== END OF TOPIC ==== version0.57.md ==== -->
