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


# What's new in version 0.18.0

The following new features and notable changes since v 0.17.0 are included in this release:

- [jextract now available on macOS for OpenJDK version 8](#jextract-now-available-on-macos-for-openjdk-version-8)
- [New shared classes cache suboption to turn off timestamp checking](#new-shared-classes-cache-suboption-to-turn-off-timestamp-checking)
- [`-Xmso` 1 MB minimum value on z/OS 64-bit](#-xmso-1-mb-minimum-value-on-zos-64-bit)
- [Add more changes here...](#add-more-changes-here)


## Features and changes

### jextract now available on macOS for OpenJDK version 8

The [`jextract` tool](tool_jextract.md) is now available on macOS&reg; platforms (as well as AIX&reg; and Linux&trade;) for _all_ current versions of OpenJDK: 8, 11, and 13.

### New shared classes cache suboption to turn off timestamp checking

You can set the `-Xshareclasses:noTimestampChecks` option to turn off timestamp checking in shared classes. For more information, see the [-Xshareclasses:noTimestampChecks](xshareclasses.md#notimestampchecks) option.

### `-Xmso` 1 MB minimum value on z/OS 64-bit

On z/OS 64-bit, [`-Xmso`](xmso.md) has a 1 MB minimum value, to match the minimum stack space provided by the operating system. If you set a value smaller than 1 MB, the value is ignored.

### Add more changes here...


## Full release information

To see a complete list of changes between Eclipse OpenJ9 v 0.17.0 and v 0.18.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.18/0.18.md).

<!-- ==== END OF TOPIC ==== version0.18.md ==== -->
