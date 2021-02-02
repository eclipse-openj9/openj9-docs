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

# Supported environments

The Eclipse OpenJ9 project source code can be built against multiple JDK levels starting with JDK8,
so the question of support has a more complicated answer than at OpenJDK. Our community is committed
to supporting JDK levels as long as they are supported at the OpenJDK open source project with a significant
user base. Currently, Eclipse OpenJ9 produces a new release every quarter that can build against all JDK levels
currently supported by the OpenJDK community. We are committed to accepting problem reports when using
Eclipse OpenJ9 against a supported OpenJDK level, with fixes being delivered in each release of Eclipse OpenJ9.

In order to track the OpenJDK 6 month release cadence, OpenJ9 also produces two releases a year that support only
a single JDK level.  These releases will occur in March and September with the intention of supporting only
the corresponding new OpenJDK feature release.

The following table summarizes which JDK levels are expected to be supported by which Eclipse OpenJ9 releases,
along with projected release dates. All future dates and support expectations are predictions that might change
depending on how the OpenJDK and OpenJ9 projects evolve over time. To keep this table concise, some rows and
columns will be removed over time.


## Eclipse OpenJ9 releases

| OpenJ9 release | Release&nbsp;date | JDK8&nbsp;(LTS)                                                                | JDK11&nbsp;(LTS)                                                               |  JDK15                                                                             | JDK16                                                                              | JDK17&nbsp;(LTS)                                                                   |
|----------------|-------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| v 0.21.0       | Jul 2020          | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |                                                                                    |                                                                                    |                                                                                    |
| v 0.22.0       | Sep 2020          | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> (2) |                                                                                    |                                                                                    |
| v 0.23.0       | Oct 2020          | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span>     |                                                                                    |                                                                                    |
| v 0.24.0       | Jan 2021          | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span>     |                                                                                    |                                                                                    |
| v 0.25.0       | Mar 2021          | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span>     | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> (2) |                                                                                    |
| v 0.26.0       | Apr 2021 (1)      | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span>     | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span>     |                                                                                    |
| v 0.27.0       | Jul 2021 (1)      | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span>     | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span>     |                                                                                    |
| v 0.28.0       | Sep 2021 (1)      | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span>     | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span>     | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> (3) |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

1. These future OpenJ9 releases are expected, in line with our support statement.
2. These OpenJ9 releases are feature releases that support a new OpenJDK release only.
3. These OpenJ9 releases support a new LTS OpenJDK release and current LTS releases.

For any issues or limitations of an Eclipse OpenJ9 release, read the [release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/).

## Platform support

The Eclipse OpenJ9 project is open to supporting any hardware/operating system platforms
provided that we have community members available to maintain them. For practical
reasons the Eclipse OpenJ9 JVM does not currently run on every platform.

### OpenJDK 8

OpenJDK 8 binaries are expected to function on the minimum operating system levels shown in the following tables:

| Linux&reg;                                | x32                                                                            | x64                                                                            | ppc64le                                                                        | Z31                                                                            | Z64                                                                            |
|-------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| CentOS 6.10                               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> |
| CentOS 7.6                                | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> |
| Red Hat Enterprise Linux (RHEL) 6.10      | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> |
| RHEL 7.6                                  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| SUSE Linux Enterprise Server (SLES) 12 SP5| <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Ubuntu 18.04                              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Ubuntu 20.04                              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Not all of these distributions are tested, but Linux distributions that have a
minimum glibc version 2.12 (x) or 2.17 (others) are expected to function without problems.

| Windows&reg;                              | x32                                                                            |  x64                                                                           |
|-------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| Windows 10                                | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2012 R2                    | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2016                       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2019                       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
                                            
| macOS&reg;                                | x64                                                                            |
|-------------------------------------------|--------------------------------------------------------------------------------|
| OS X&reg; 10.10.0+                        | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
                                            
                                            
| AIX&reg;                                  | ppc32                                                                          | ppc64                                                                          |
|-------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| AIX 7.1 TL5                               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| AIX 7.2 TL3                               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.

### OpenJDK 11

OpenJDK 11 binaries are expected to function on the minimum operating system levels shown in the following tables:


| Linux (**Note 1**)                        | AArch64 (**Note 2**)                                                           | x64                                                                            | ppc64le                                                                         | Z64                                                                           |
|-------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|---------------------------------------------------------------------------------|-------------------------------------------------------------------------------|
| CentOS 6.10                               | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> |
| CentOS 7.6                                | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> |
| Red Hat Enterprise Linux (RHEL) 6.10      | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> |
| RHEL 7.6                                  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| SUSE Linux Enterprise Server (SLES) 12 SP5| <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Ubuntu 18.04                              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Ubuntu 20.04                              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

1. Not all of these distributions are tested, but Linux distributions that have a minimum glibc version 2.12 (x) or 2.17 (others) are expected to function without problems.
2. Only limited support for the 64-bit ARM architecture is currently available. For a list of known issues, see the [Release notes](https://github.com/eclipse/openj9/tree/master/doc/release-notes) for the latest version of Eclipse OpenJ9.

| Windows                                   | x64                                                                            |
|-------------------------------------------|--------------------------------------------------------------------------------|
| Windows 10                                | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2012 R2                    | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2016                       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2019                       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
                                            
| macOS                                     | x64                                                                            |
|-------------------------------------------|--------------------------------------------------------------------------------|
| OS X 10.10.0+                             | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
                                            
| AIX                                       | ppc64                                                                          |
|-------------------------------------------|--------------------------------------------------------------------------------|
| AIX 7.1 TL5                               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| AIX 7.2 TL3                               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.


### OpenJDK 16

OpenJDK 16 binaries are expected to function on the minimum operating system levels shown in the following tables:


| Linux (**Note 1**)                        | AArch64 (**Note 2**)                                                           | x64                                                                            | ppc64le                                                                        | Z64                                                                            |
|-------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| CentOS 7.6                                | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> |
| RHEL 7.6                                  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| SUSE Linux Enterprise Server (SLES) 12 SP5| <i class="fa fa-times" aria-hidden="true"></i><span class="sr-only">no </span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Ubuntu 18.04                              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Ubuntu 20.04                              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

1. Not all of these distributions are tested, but Linux distributions that have a minimum glibc version 2.17 are expected to function without problems.
2. Only limited support for the 64-bit ARM architecture is currently available. For a list of known issues, see the [Release notes](https://github.com/eclipse/openj9/tree/master/doc/release-notes) for the latest version of Eclipse OpenJ9.

| Windows                                   | x64                                                                            |
|-------------------------------------------|--------------------------------------------------------------------------------|
| Windows 10                                | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2012 R2                    | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2016                       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| Windows Server 2019                       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
                                            
| macOS                                     | x64                                                                            |
|-------------------------------------------|--------------------------------------------------------------------------------|
| OS X 10.10.0+                             | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
                                            
| AIX                                       | ppc64                                                                          |
|-------------------------------------------|--------------------------------------------------------------------------------|
| AIX 7.1 TL5                               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| AIX 7.2 TL3                               | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

<i class="fa fa-bell" aria-hidden="true"></i> **Important:** AIX OpenJ9 builds require the [XL C++ Runtime](https://www.ibm.com/support/pages/xl-cc-runtime-aix-v16101-fix-pack-december-2018).

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.


## Build environments

The project builds and tests OpenJDK with OpenJ9 on a number of platforms. The operating system and compiler levels for the build systems are shown in the following tables.

### OpenJDK 8

| Platform                      | Operating system       | Compiler                              |
|-------------------------------|------------------------|---------------------------------------|
| Linux x86 64-bit              | CentOS 6.10            | gcc 7.5                               |
| Linux on POWER&reg; LE 64-bit | CentOS 7.8             | gcc 7.5                               |
| Linux on IBM Z&reg; 64-bit    | RHEL 7.7               | gcc 7.5                               |
| Windows x86 32-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2013 Update 5 |
| Windows x86 64-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2013 Update 5 |
| macOS x86 64-bit              | OSX 10.14.6            | xcode 10.3 and clang 10.0.1           |
| AIX POWER BE 64-bit           | AIX 7.1 TL05           | xlc/C++ 13.1.3                        |

### OpenJDK 11

| Platform                      | Operating system       | Compiler                              |
|-------------------------------|------------------------|---------------------------------------|
| Linux x86 64-bit              | CentOS 6.10            | gcc 7.5                               |
| Linux on ARM 64-bit           | CentOS 7               | gcc 7.5                               |
| Linux on POWER LE 64-bit      | CentOS 7.8             | gcc 7.5                               |
| Linux on IBM Z 64-bit         | RHEL 7.7               | gcc 7.5                               |
| Windows x86 64-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2017          |
| macOS x86 64-bit              | macOS 10.14.6          | xcode 10.3 and clang 10.0.1           |
| AIX POWER BE 64-bit           | AIX 7.1 TL05           | xlc/C++ 13.1.3                        |

### OpenJDK 16

| Platform                      | Operating system       | Compiler                              |
|-------------------------------|------------------------|---------------------------------------|
| Linux x86 64-bit              | CentOS 7.8             | gcc 7.5                               |
| Linux on POWER LE 64-bit      | CentOS 7.8             | gcc 7.5                               |
| Linux on IBM Z 64-bit         | RHEL 7.7               | gcc 7.5                               |
| Windows x86 64-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2019          |
| macOS x86 64-bit              | macOS 10.14.6          | xcode 10.3 and clang 10.0.1           |
| AIX POWER BE 64-bit           | AIX 7.1 TL05           | xlc/C++ 16.1.0                        |
