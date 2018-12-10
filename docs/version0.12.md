<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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


# What's new in version 0.12.0

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** This release is under development. For more information about the features that are expected in this release, read the [release plan](https://projects.eclipse.org/projects/technology.openj9/releases/0.12.0).

<!--Use the line below for the final release notes and remove the one below that. Don't forget to add a download and full release changes at the bottom of the topic -->
<!--The following new features and notable changes since v.0.11.0 are included in this release:-->
The following new features and notable changes since v.0.11.0 are delivered in the OpenJ9 code base:

- [Improved flexibility for managing the size of the JIT code cache](#improved-flexibility-for-managing-the-size-of-the-jit-code-cache)
- [Class data sharing is enabled by default](#class-data-sharing-is-enabled-by-default)


## Improved flexibility for managing the size of the JIT code cache

The JIT code cache stores the native code of compiled Java&trade; methods. By default, the size of the code cache is 256 MB for a 64-bit VM and 64 MB for a 31/32-bit VM. In earlier releases the size of the code cache could be increased from the default value by using the `-Xcodecachetotal` command line option. In this release the size can also be decreased by using this option, with a minimum size of 2 MB. The size of the JIT code cache also affects the size of the JIT data cache, which holds metadata about compiled methods. If you use the `-Xcodecachetotal` option to manage the size of the code cache, the size of the data cache is adjusted by the same proportion. For more information, see [`-Xcodecachetotal`](codecachetotal.md).

## Class data sharing is enabled by default

For operating systems other than macOS&reg;, class data sharing is enabled by default for bootstrap classes, unless your application is running in a container. You can use the `-Xshareclasses` option to change the default behavior. For more information, see [`Class Data Sharing`](shrc.md).

<!-- ==== END OF TOPIC ==== version0.12.md ==== -->
