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

# What's new in version 0.25.0

The following new features and notable changes since v 0.24.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [Support for the `-verbose:module` option](#support-for-the-verbosemodule-option)


## Features and changes

### Binaries and supported environments

OpenJ9 release 0.25.0 supports OpenJDK 16. OpenJDK 16 with Eclipse OpenJ9 is *not* a long term support (LTS) release.

Windows&reg; builds for Java&trade; 8 are now compiled with Microsoft&reg; Visual Studio 2013. The Visual Studio redistributable files included with the build are updated to match.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Support for the `-verbose:module` option

The `-verbose:module` option is now supported for Java 11 and later releases. This option writes information to `stderr` for each module that is loaded and unloaded.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 v 0.24.0 and v 0.25.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.25/0.25.md).


<!-- ==== END OF TOPIC ==== version0.25.md ==== -->

