<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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
the corresponding new OpenJDK feature release (ie: 11, 13, ...).

The following table summarizes which JDK levels are expected to be supported by which Eclipse OpenJ9 releases,
along with projected release dates. All future dates and support expectations are predictions that might change
depending on how the OpenJDK and OpenJ9 projects evolve over time. To keep this table concise, some rows and
columns will be removed over time.


## Eclipse OpenJ9 releases

| OpenJ9 release   | Release date        | JDK8 (LTS)| JDK11 (LTS) | JDK13     | JDK14     |
|------------------|---------------------|-----------|-------------|-----------|-----------|
| v 0.12.0         | January 2019        | Yes       | Yes         |           |           |
| v 0.13.0         | March 2019          | No        | No          |           |           |
| v 0.14.0         | April 2019          | Yes       | Yes         |           |           |
| v 0.15.0         | July 2019           | Yes       | Yes         |           |           |
| v 0.16.0         | September 2019      | No        | No          | Yes (\*2) |           |
| v 0.17.0         | October 2019        | Yes       | Yes         | Yes       |           |
| v 0.18.0         | January 2020        | Yes       | Yes         | Yes       |           |
| v 0.19.0         | March 2020 (\*1)    | No        | No          | No        | Yes (\*2) |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

- (\*1): These OpenJ9 releases are expected, in line with our support statement.
- (\*2): These OpenJ9 releases are the feature releases that only support the new OpenJDK release.

For any issues or limitations of an Eclipse OpenJ9 release, read the [release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/).

## Platform support

The Eclipse OpenJ9 project is open to supporting any hardware/operating system platforms
provided that we have community members available to maintain them. For practical
reasons the Eclipse OpenJ9 JVM does not currently run on every platform.

### OpenJDK 8

<i class="fa fa-bell" aria-hidden="true"></i> **Important:** If you obtain pre-built binaries from [AdoptOpenJDK.net](https://adoptopenjdk.net/index.html),
platform support might vary, depending on their build environment. Check the AdoptOpenJDK [Platform support matrix](https://adoptopenjdk.net/supported_platforms.html).

OpenJDK8 binaries are supported on the minimum operating system levels shown in the following tables:

| Linux&reg;                            |   x32  |  x64   | ppc64le    | Z31  | Z64 |
|---------------------------------------|--------|--------|------------|------|------|
| Centos 6.9                            |   Y    |   Y    |     Y      |   N  |  N   |
| Centos 7.4                            |   Y    |   Y    |     Y      |   N  |  N   |
| Red Hat Enterprise Linux (RHEL) 6.9   |   Y    |   Y    |     Y      |   Y  |  Y   |
| RHEL 7.4                              |   Y    |   Y    |     Y      |   Y  |  Y   |
| SUSE Linux Enterprise Server (SLES) 12|   Y    |   Y    |     Y      |   Y  |  Y   |
| Ubuntu 16.04                          |   Y    |   Y    |     Y      |   N  |  Y   |
| Ubuntu 18.04                          |   Y    |   Y    |     Y      |   N  |  Y   |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Not all of these distributions are tested, but Linux distributions that have a
minimum glibc version 2.12 are expected to function without problems.

| Windows&reg;               |  x32   |  x64  |
|----------------------------|--------|-------|
| Windows 8.1                |   Y    |   Y   |
| Windows 10                 |   Y    |   Y   |
| Windows Server 2012        |   Y    |   Y   |
| Windows Server 2012 R2     |   Y    |   Y   |
| Windows Server 2016        |   Y    |   Y   |

| macOS&reg;              |   x64    |
|-------------------------|----------|
| OS X&reg; 10.9.0+       |    Y     |


| AIX&reg;     |  ppc32   |  ppc64   |
|--------------|----------|----------|
| AIX 7.1 TL4  |    Y     |    Y     |
| AIX 7.2      |    Y     |    Y     |

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.

### OpenJDK 11

<i class="fa fa-bell" aria-hidden="true"></i> **Important:** If you obtain pre-built binaries from [AdoptOpenJDK.net](https://adoptopenjdk.net/index.html),
platform support might vary, depending on their build environment. Check the AdoptOpenJDK [Platform support matrix](https://adoptopenjdk.net/supported_platforms.html).

OpenJDK11 binaries are supported on the minimum operating system levels shown in the following tables:


| Linux                                 |  x64   |  ppc64le   | Z64  |
|---------------------------------------|--------|------------|------|
| Centos 6.9                            |   Y    |     Y      |  N   |
| Centos 7.4                            |   Y    |     Y      |  N   |
| Red Hat Enterprise Linux (RHEL) 6.9   |   Y    |     Y      |  Y   |
| RHEL 7.4                              |   Y    |     Y      |  Y   |
| SUSE Linux Enterprise Server (SLES) 12|   Y    |     Y      |  Y   |
| Ubuntu 16.04                          |   Y    |     Y      |  Y   |
| Ubuntu 18.04                          |   Y    |     Y      |  Y   |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Not all of these distributions are tested, but Linux distributions that have a
minimum glibc version 2.12 are expected to function without problems.

| Windows                    |  x64   |
|----------------------------|--------|
| Windows 8.1                |   Y    |
| Windows 10                 |   Y    |
| Windows Server 2012        |   Y    |
| Windows Server 2012 R2     |   Y    |
| Windows Server 2016        |   Y    |

| macOS                   |   x64    |
|-------------------------|----------|
| OS X 10.9.0+            |    Y     |

| AIX          |  ppc64   |
|--------------|----------|
| AIX 7.1 TL4  |    Y     |
| AIX 7.2      |    Y     |

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.


### OpenJDK 13

<i class="fa fa-bell" aria-hidden="true"></i> **Important:** If you obtain pre-built binaries from [AdoptOpenJDK.net](https://adoptopenjdk.net/index.html),
platform support might vary, depending on their build environment. Check the AdoptOpenJDK [Platform support matrix](https://adoptopenjdk.net/supported_platforms.html).

OpenJDK13 binaries are supported on the minimum operating system levels shown in the following tables:


| Linux                                 |  x64   |  ppc64le   | Z64  |
|---------------------------------------|--------|------------|------|
| Centos 6.9                            |   Y    |     Y      |  N   |
| Centos 7.4                            |   Y    |     Y      |  N   |
| Red Hat Enterprise Linux (RHEL) 6.9   |   Y    |     Y      |  Y   |
| RHEL 7.4                              |   Y    |     Y      |  Y   |
| SUSE Linux Enterprise Server (SLES) 12|   Y    |     Y      |  Y   |
| Ubuntu 16.04                          |   Y    |     Y      |  Y   |
| Ubuntu 18.04                          |   Y    |     Y      |  Y   |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Not all of these distributions are tested, but Linux distributions that have a
minimum glibc version 2.12 are expected to function without problems.

| Windows                    |  x64   |
|----------------------------|--------|
| Windows 8.1                |   Y    |
| Windows 10                 |   Y    |
| Windows Server 2012        |   Y    |
| Windows Server 2012 R2     |   Y    |
| Windows Server 2016        |   Y    |

| macOS                   |   x64    |
|-------------------------|----------|
| OS X 10.9.0+            |    Y     |

| AIX          |  ppc64   |
|--------------|----------|
| AIX 7.1 TL4  |    Y     |
| AIX 7.2      |    Y     |

<i class="fa fa-bell" aria-hidden="true"></i> **Important:** AIX OpenJ9 builds require the [XL C++ Runtime](https://www.ibm.com/support/pages/xl-cc-runtime-aix-v16101-fix-pack-december-2018)

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.


## Build environments

The project build and test OpenJDK with OpenJ9 on a number of platforms. The operating system and compiler levels for the build systems are shown in the following tables.

### OpenJDK 8

| Platform                                    | Operating system          |  Compiler                       |
|---------------------------------------------|---------------------------|---------------------------------|
| Linux x86 64-bit                            | Ubuntu 16.04              | gcc 7.3                         |
| Linux on POWER&reg; LE 64-bit               | Ubuntu 16.04              | gcc 7.3                         |
| Linux on IBM Z&reg; 64-bit                  | Ubuntu 16.04              | gcc 7.4                         |
| Windows x86 32-bit                          | Windows Server 2012 R2    | Microsoft Visual Studio 2010 SP1|
| Windows x86 64-bit                          | Windows Server 2012 R2    | Microsoft Visual Studio 2010 SP1|
| macOS x86 64-bit                            | OSX 10.11                 | xcode/clang 4.6.3 and 7.2.1     |
| AIX POWER BE 64-bit                         | AIX 7.1 TL04              | xlc/C++ 13.1.3                  |

### OpenJDK 11

| Platform                    | Operating system         |  Compiler                       |
|-----------------------------|--------------------------|---------------------------------|
| Linux x86 64-bit            | Ubuntu 16.04             | gcc 7.3                         |
| Linux on POWER LE 64-bit    | Ubuntu 16.04             | gcc 7.3                         |
| Linux on IBM Z 64-bit       | Ubuntu 16.04             | gcc 7.4                         |
| Windows x86 64-bit          | Windows Server 2012 R2   | Microsoft Visual Studio 2017    |
| macOS x86 64-bit            | macOS 10.13.5            | xcode/clang 9.4                 |
| AIX POWER BE 64-bit         | AIX 7.1 TL04             | xlc/C++ 13.1.3                  |

### OpenJDK 13

| Platform                    | Operating system         |  Compiler                       |
|-----------------------------|--------------------------|---------------------------------|
| Linux x86 64-bit            | Ubuntu 16.04             | gcc 7.3                         |
| Linux on POWER LE 64-bit    | Ubuntu 16.04             | gcc 7.3                         |
| Linux on IBM Z 64-bit       | Ubuntu 16.04             | gcc 7.4                         |
| Windows x86 64-bit          | Windows Server 2012 R2   | Microsoft Visual Studio 2017    |
| macOS x86 64-bit            | macOS 10.13.5            | xcode/clang 9.4                 |
| AIX POWER BE 64-bit         | AIX 7.1 TL04             | xlc/C++ 16.1.0                  |
