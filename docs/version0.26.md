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

# What's new in version 0.26.0

The following new features and notable changes since version 0.25.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Dump extractor tool deprecated](#dump-extractor-tool-deprecated)


## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.26.0 supports OpenJDK 8, 11, and 16.

For OpenJDK 8 and 11 builds that contain OpenJ9, see [Version 0.25.0](version0.25.md) for additional changes that have now been fully tested for these versions.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Dump extractor tool deprecated

The dump extractor tool, `jextract`, is deprecated in this release and replaced with the `jpackcore` tool. This tool uses the same syntax and parameters as `jextract` to collect diagnostic files for analysis. The change is necessary because the reference implementation will be introducing a tool in a future release that is also called `jextract`.

For more information, see [Dump extractor](tool_jextract.md).


## Full release information

To see a complete list of changes between Eclipse OpenJ9 v0.25.0 and v0.26.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.26/0.26.md).


<!-- ==== END OF TOPIC ==== version0.26.md ==== -->
