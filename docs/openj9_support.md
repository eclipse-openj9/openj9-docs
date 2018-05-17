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

# Supported environments

The Eclipse OpenJ9 project source code can be built against multiple JDK levels starting with JDK8,
so the question of support has a more complicated answer than at OpenJDK. Our community is committed
to supporting JDK levels as long as they are supported at the OpenJDK open source project with a significant
user base. Currently, Eclipse OpenJ9 produces a new release every quarter that can build against all JDK levels
currently supported by the OpenJDK community. We are committed to accepting problem reports when using
Eclipse OpenJ9 against a supported OpenJDK level, with fixes being delivered in each release of Eclipse OpenJ9.

The following table summarizes which JDK levels are expected to be supported by which Eclipse OpenJ9 releases,
along with projected release dates. All future dates and support expectations are predictions that might change
depending on how the OpenJDK and OpenJ9 projects evolve over time. Note also that columns may be removed from
this table over time. For example, JDK9 will be removed from this table shortly because it will no longer be
supported by any open project.


## Eclipse OpenJ9 releases

| OpenJ9 release  | Release date       | JDK8 (LTS)| JDK9 | JDK10 | JDK11 (LTS) | JDK12 | JDK13 |
|-----------------|--------------------|-----------|------|-------|-------------|-------|-------|
| v0.8.0          | March 2018         | Yes       | No   |       |             |       |       |
| v0.9.0          | May 2018 (\*1)     | Yes       | No   | Yes     |             |       |       |
| v0.10.0         | August 2018 (\*2)  | Yes       | No   | Yes   |             |       |       |
| v0.11.0         | October 2018 (\*2) | Yes       | No   | No    | Yes         |       |       |
| v0.12.0         | January 2019 (\*2) | Yes       | No   | No    | Yes         |       |       |
| v0.13.0         | April 2019 (\*2)   | No (\*3)  | No   | No    | No (\*3)    | Yes   |       |
| v0.14.0         | July 2019 (\*2)    | No (\*3)  | No   | No    | No (\*3)    | Yes   |       |
| v0.15.0         | October 2019 (\*2) | No (\*3)  | No   | No    | No (\*3)    | No    | Yes   |


**Notes:**

- (\*1): This OpenJ9 release is in plan.
- (\*2): These OpenJ9 releases are expected, in line with our support statement.
- (\*3): We fully expect that OpenJDK8 will have open community maintainers beyond January 2019,
so we expect to be able to continue supporting JDK8 beyond that date. Until maintainers have been established
we are unable to make a definitive support statement. This position is the same for JDK11 and all future "LTS" releases.

## Platform support

The Eclipse OpenJ9 project is open to supporting any hardware/operating system platforms
provided that we have community members available to maintain them. For practical
reasons the Eclipse OpenJ9 JVM does not currently run on every platform. The following tables
show the minimum build configurations that we have tested to build an OpenJDK binary.

For any issues or limitations of an Eclipse OpenJ9 release, read the release notes:

- [OpenJ9 0.8.0 release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.8/0.8.md)
- [OpenJ9 0.9.0 release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.9/0.9.md)

**NOTE:** If you obtain pre-built binaries from [AdoptOpenJDK.net](https://adoptopenjdk.net/index.html),
platform support might vary, depending on their build environment.

### OpenJDK 8 build environment

| Platform                    | Operating system          |  Compiler                       |  Comments      |
|-----------------------------|---------------------------|---------------------------------|----------------|
| Linux x86 64-bit            | Ubuntu 14.04              | gcc 4.8.4                       |                |
| Linux on POWER LE 64-bit    | Ubuntu 14.04              | gcc 4.8.4                       |                |
| Linux on IBM Z 64-bit       | Ubuntu 14.04              | gcc 4.8.4                       |                |
| Windows x86 64-bit          | Windows Server 2012       | Microsoft Visual Studio 2010 SP1|                |
| AIX POWER BE 64-bit         | AIX 7.1 TL04              | xlc/C++ 13.1.3                  |                |

OpenJDK8 binaries are supported on the minimum operating system levels shown in the table.
For Linux, other distributions are supported provided that they have a Linux kernel v3.2 or later
(Look in the `/proc/version` file to find the kernel version number).

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.

### OpenJDK 9 build environment

| Platform                    | Operating system         |  Compiler                       |   Comments     |
|-----------------------------|--------------------------|---------------------------------|----------------|
| Linux x86 64-bit            | Ubuntu 16.04 (kernel 4.4)| gcc 4.8.4                       |                |
| Linux on POWER LE 64-bit    | Ubuntu 16.04             | gcc 4.8.4                       |                |
| Linux on IBM Z 64-bit       | Ubuntu 16.04             | gcc 4.8.4                       |                |
| Windows x86 64-bit          | Windows Server 2016      | Microsoft Visual Studio 2013    |                |
| AIX POWER BE 64-bit         | AIX 7.1 TL04                 | xlc/C++ 13.1.3                  |                |


OpenJDK9 binaries are supported on the minimum operating system levels shown in the table.
For Linux, other distributions are supported provided that they have a Linux kernel v4.4 or later
(Look in the `/proc/version` file to find the kernel version number).

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.

### OpenJDK 10 build environment

| Platform                    | Operating system         |  Compiler                       |   Comments     |
|-----------------------------|--------------------------|---------------------------------|----------------|
| Linux x86 64-bit            | Ubuntu 16.04             | gcc 4.8.4                       |                |
| Linux on POWER LE 64-bit    | Ubuntu 16.04             | gcc 4.8.4                       |                |
| Linux on IBM Z 64-bit       | Ubuntu 16.04             | gcc 4.8.4                       |                |
| Windows x86 64-bit          | Windows Server 2016      | Microsoft Visual Studio 2013    |                |
| AIX POWER BE 64-bit         | AIX 7.1 TL04             | xlc/C++ 13.1.3                  |                |

OpenJDK10 binaries are supported on the minimum operating system levels shown in the table.
For Linux, other distributions are supported provided that they have a Linux kernel v4.4 or later
(Look in the `/proc/version` file to find the kernel version number).

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.
