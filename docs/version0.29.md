<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# What's new in version 0.29.0

The following new features and notable changes since version 0.27.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [JITServer technology is fully supported on some  systems](#jitserver-technology-is-fully-supported-on-some-systems)
- [New `-XX:[+|-]UTFCache` option added](#new-xx-utfcache-option-added)
- [`-Xsoftmx` updates for gencon](#-xsoftmx-updates-for-gencon)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.29.0 supports OpenJDK 8 and 11.

AArch64 Linux is now a fully supported, production-ready target for OpenJDK 8 and 11.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### JITServer technology is fully supported on some systems

JITServer technology is now a fully supported feature on Linux&reg; on x86 and Linux on IBM Power&reg; systems (64-bit only). This feature remains a technical preview for Linux on IBM Z&reg; systems (64-bit only). For more information, see [JITServer technology](jitserver.md).

### New `-XX:[+|-]UTFCache` option added

A UTF to String cache is added to enhance reflection performance. The cache is enabled by default but can be disabled using the [`-XX:[+|-]UTFCache`](xxutfcache.md) option.

### `-Xsoftmx` updates for gencon

When using gencon, the [`-Xsoftmx`](xsoftmx.md) limit is proportional to the maximum amount of nursery space specified relative to the `-Xmx` value.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 v0.27.0 and v0.29.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.29/0.29.md).

<!-- ==== END OF TOPIC ==== version0.29.md ==== -->
