<!--
* Copyright (c) 2017, 2024 IBM Corp. and others
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

# What's new in version 0.49.0

The following new features and notable changes since version 0.48.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Change to the shared classes cache generation number](#change-to-the-shared-classes-cache-generation-number)
- [New shared classes cache suboption added to adjust the number of startup hints that can be stored](#new-shared-classes-cache-suboption-added-to-adjust-the-number-of-startup-hints-that-can-be-stored)
- [`subAllocator` related `-Xgc` options are added to control the compressed reference allocation](#suballocator-related-xgc-options-are-added-to-control-the-compressed-reference-allocation)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.49.0 supports OpenJDK 8, 11, 17, 21, and 23.

macOS 12 is out of support and is removed from the list of supported platforms.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Change to the shared classes cache generation number

The shared classes cache generation number is incremented. The increment in the shared classes cache generation number causes the VM to create a new shared classes cache, rather than re-creating or reusing an existing cache.

To save space, all existing shared caches can be removed unless they are in use by an earlier release. For more information, see [Housekeeping](shrc.md#housekeeping) and [`-Xshareclasses`](xshareclasses.md).

The shared classes cache generation number is modified because of a change in the format of ROMClasses that are stored in the shared classes cache. A new flag `J9AccClassIsShared` is added to ROMClasses to indicate whether a ROMClass was loaded from a shared classes cache or from a VM.

### New shared classes cache suboption added to adjust the number of startup hints that can be stored

You can use the `-Xshareclasses:extraStartupHints=<number>` option to adjust the number of startup hints that can be stored in a shared cache. By default, you can store only up to 64 startup hints in a shared cache.

For more information, see [`-Xshareclasses:extraStartupHints`](xshareclasses.md#extrastartuphints).

### `subAllocator` related `-Xgc` options are added to control the compressed reference allocation

The VM can use compressed references to decrease the size of Java objects and make better use of the available space in the Java heap. You can now control the compressed reference allocation with the following options:

 - [`-Xgc:suballocatorCommitSize=<size>`](xgc.md#suballocatorcommitsize)
 - [`-Xgc:suballocatorIncrementSize=<size>`](xgc.md#suballocatorincrementsize)
 - [`-Xgc:suballocatorInitialSize=<size>`](xgc.md#suballocatorinitialsize)
 - [`-Xgc:suballocatorQuickAllocDisable`](xgc.md#suballocatorquickallocdisable)
 - [`-Xgc:suballocatorQuickAllocEnable`](xgc.md#suballocatorquickallocenable)

For more information, see [Compressed references](allocation.md#compressed-references).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.48.0 and v0.49.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.49/0.49.md).

<!-- ==== END OF TOPIC ==== version0.49.md ==== -->
