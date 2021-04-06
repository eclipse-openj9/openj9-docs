<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# What's new in version 0.26.0

The following new features and notable changes since v 0.25.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Change in default behavior for the `balanced` garbage collection policy](#change-in-default-behavior-for-the-balanced-garbage-collection-gc-policy)


## Features and changes

### Binaries and supported environments

OpenJ9 release 0.26.0 supports OpenJDK 8, 11, and 16.

For OpenJDK 8 and 11 builds that contain OpenJ9, see [Version 0.25.0](version0.25.md) for additional changes that have now been fully tested for these versions.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Change in default behavior for the `balanced` garbage collection (GC) policy

In this release, a new scan mode, [`-Xgc:dynamicBreadthFirstScanOrdering`](xgc.md#dynamicbreadthfirstscanordering), is used during `balanced` GC copy forward operations that is expected to improve performance.

For more information about this type of operation, see [GC copy forward operation](gc_overview.md#gc-copy-forward-operation).

You can revert to the behavior in earlier releases by setting [`-Xgc:breadthFirstScanOrdering`](xgc.md#breadthfirstscanordering) when you start your application.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 v0.25.0 and v0.26.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.26/0.26.md).


<!-- ==== END OF TOPIC ==== version0.26.md ==== -->
