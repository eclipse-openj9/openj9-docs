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

| OpenJ9 release | Release date  | JDK8 (LTS)                                                                           | JDK11 (LTS)                                                                          | JDK17 (LTS)                                                                              | JDK18                                                                                | JDK19                                                                                    |
|----------------|---------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| 0.29.1         | Dec 2021      | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> (3) |                                                                                      |                                                                                          |
| 0.30.0         | Jan 2022      | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |                                                                                      |                                                                                          |
| 0.32.0         | Apr 2022      | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |                                                                                          |
| 0.33.0         | Jul 2022      | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |                                                                                          |
| 0.35.0         | Oct 2022 (1)  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |
| 0.36.0         | Jan 2023 (1)  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

1. These future OpenJ9 releases are expected, in line with our support statement.
2. These OpenJ9 releases are feature releases that support a new OpenJDK release only.
3. These OpenJ9 releases support a new LTS OpenJDK release only.

For any issues or limitations of an Eclipse OpenJ9 release, read the [release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/).

## Platform support

The Eclipse OpenJ9 project is open to supporting any hardware/operating system platforms
provided that we have community members available to maintain them. For practical
reasons the Eclipse OpenJ9 JVM does not currently run on every platform.

### OpenJDK 8

OpenJDK 8 binaries are expected to function on the minimum operating system levels shown in the following tables:

| Linux&reg;                                |AArch64                                                                               | x32                                                                                  | x64                                                                                  | ppc64le                                                                              | Z31                                                                                  | Z64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| CentOS 6.10                               | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| CentOS 7.9                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| CentOS Stream 8                           | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| Red Hat Enterprise Linux (RHEL) 6.10      | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| RHEL 7.8                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| RHEL 8.2                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| SUSE Linux Enterprise Server (SLES) 12 SP5| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 18.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 20.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** Not all of these distributions are tested, but Linux distributions that have a
minimum glibc version 2.12 (x) or 2.17 (others) are expected to function without problems.

| Windows&reg;                              | x32                                                                                  |  x64                                                                                 |
|-------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| Windows 10                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2012 R2                    | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2016                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2019                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

| macOS&reg;                                | x64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| OS X&reg; 10.10.0+                        | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |


| AIX&reg;                                  | ppc32                                                                                | ppc64                                                                                |
|-------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| AIX 7.1 TL5                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| AIX 7.2 TL4                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.

### OpenJDK 11

OpenJDK 11 binaries are expected to function on the minimum operating system levels shown in the following tables:


| Linux (**Note 1**)                        | AArch64                                                                              | x64                                                                                  | ppc64le                                                                              | Z64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| CentOS 6.10                               | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| CentOS 7.9                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| CentOS Stream 8                           | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| Red Hat Enterprise Linux (RHEL) 6.10      | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| RHEL 7.8                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| RHEL 8.2                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| SUSE Linux Enterprise Server (SLES) 12 SP5| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 18.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 20.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

1. Not all of these distributions are tested, but Linux distributions that have a minimum glibc version 2.12 (x) or 2.17 (others) are expected to function without problems.

| Windows                                   | x64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| Windows 10                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2012 R2                    | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2016                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2019                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

| macOS                                     | x64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| OS X 10.11+                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

| AIX                                       | ppc64                                                                                |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| AIX 7.1 TL5                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| AIX 7.2 TL4                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-bell:{: .warn aria-hidden="true"} **Important:** AIX OpenJ9 builds require the [16.1 XL C++ Runtime](https://www.ibm.com/support/pages/fix-list-xl-cc-runtime-aix#161X).

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.


### OpenJDK 17

OpenJDK 17 binaries are expected to function on the minimum operating system levels shown in the following tables:


| Linux (**Note 1**)                        | AArch64                                                                              | x64                                                                                  | ppc64le                                                                              | Z64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| CentOS 7.9                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| CentOS Stream 8                           | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| RHEL 7.8                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| RHEL 8.2                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| SUSE Linux Enterprise Server (SLES) 12 SP5| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 18.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 20.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

1. Not all of these distributions are tested, but Linux distributions that have a minimum glibc version 2.17 are expected to function without problems.

| Windows                                   | x64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| Windows 10                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2012 R2                    | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2016                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2019                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

| macOS                                     | x64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| OS X 10.14+                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

| AIX                                       | ppc64                                                                                |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| AIX 7.1 TL5                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| AIX 7.2 TL4                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-bell:{: .warn aria-hidden="true"} **Important:** AIX OpenJ9 builds require the [16.1 XL C++ Runtime](https://www.ibm.com/support/pages/fix-list-xl-cc-runtime-aix#161X).

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.


### OpenJDK 18

OpenJDK 18 binaries are expected to function on the minimum operating system levels shown in the following tables:


| Linux (**Note 1**)                        | AArch64                                                                              | x64                                                                                  | ppc64le                                                                              | Z64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| CentOS 7.9                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| CentOS Stream 8                           | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  |
| RHEL 7.8                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| RHEL 8.2                                  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| SUSE Linux Enterprise Server (SLES) 12 SP5| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no </span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 18.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Ubuntu 20.04                              | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

1. Not all of these distributions are tested, but Linux distributions that have a minimum glibc version 2.17 are expected to function without problems.

| Windows                                   | x64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| Windows 10                                | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2012 R2                    | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2016                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| Windows Server 2019                       | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

| macOS                                     | x64                                                                                  |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| OS X 10.15+                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

| AIX                                       | ppc64                                                                                |
|-------------------------------------------|--------------------------------------------------------------------------------------|
| AIX 7.1 TL5                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| AIX 7.2 TL4                               | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-bell:{: .warn aria-hidden="true"} **Important:** AIX OpenJ9 builds require the [16.1 XL C++ Runtime](https://www.ibm.com/support/pages/fix-list-xl-cc-runtime-aix#161X).

When public support for an operating system version ends, OpenJ9 can no longer be supported on that level.


## Build environments

The project builds and tests OpenJDK with OpenJ9 on a number of platforms. The operating system and compiler levels for the build systems are shown in the following tables.

### OpenJDK 8

| Platform                      | Operating system       | Compiler                              |
|-------------------------------|------------------------|---------------------------------------|
| Linux x86 64-bit              | CentOS 6.10            | gcc 7.5                               |
| Linux on POWER&reg; LE 64-bit | CentOS 7.9             | gcc 7.5                               |
| Linux on IBM Z&reg; 64-bit    | RHEL 7.9               | gcc 7.5                               |
| Linux AArch64 64-bit          | CentOS 7.9             | gcc 7.5                               |
| Windows x86 32-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2017          |
| Windows x86 64-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2017          |
| macOS x86 64-bit              | OSX 10.14.6            | xcode 10.3 and clang 10.0.1           |
| AIX POWER BE 64-bit           | AIX 7.1 TL05           | xlc/C++ 13.1.3                        |

### OpenJDK 11

| Platform                      | Operating system       | Compiler                              |
|-------------------------------|------------------------|---------------------------------------|
| Linux x86 64-bit              | CentOS 6.10            | gcc 7.5                               |
| Linux on POWER LE 64-bit      | CentOS 7.9             | gcc 7.5                               |
| Linux on IBM Z 64-bit         | RHEL 7.9               | gcc 7.5                               |
| Linux AArch64 64-bit          | CentOS 7.9             | gcc 7.5                               |
| Windows x86 64-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2017          |
| macOS x86 64-bit              | macOS 10.14.6          | xcode 10.3 and clang 10.0.1           |
| AIX POWER BE 64-bit           | AIX 7.1 TL05           | xlc/C++ 16.1.0                        |

### OpenJDK 17

| Platform                      | Operating system       | Compiler                              |
|-------------------------------|------------------------|---------------------------------------|
| Linux x86 64-bit              | CentOS 7.9             | gcc 10.3                               |
| Linux on POWER LE 64-bit      | CentOS 7.9             | gcc 10.3                               |
| Linux on IBM Z 64-bit         | RHEL 7.9               | gcc 10.3                               |
| Linux AArch64 64-bit          | CentOS 7.9             | gcc 10.3                               |
| Windows x86 64-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2019          |
| macOS x86 64-bit              | macOS 10.14.6          | xcode 10.3 and clang 10.0.1           |
| AIX POWER BE 64-bit           | AIX 7.1 TL05           | xlc/C++ 16.1.0                        |

### OpenJDK 18

| Platform                      | Operating system       | Compiler                              |
|-------------------------------|------------------------|---------------------------------------|
| Linux x86 64-bit              | CentOS 7.9             | gcc 10.3                               |
| Linux on POWER LE 64-bit      | CentOS 7.9             | gcc 10.3                               |
| Linux on IBM Z 64-bit         | RHEL 7.9               | gcc 10.3                               |
| Linux AArch64 64-bit          | CentOS 7.9             | gcc 10.3                               |
| Windows x86 64-bit            | Windows Server 2012 R2 | Microsoft Visual Studio 2019          |
| macOS x86 64-bit              | macOS 10.14.6          | xcode 10.3 and clang 10.0.1           |
| AIX POWER BE 64-bit           | AIX 7.1 TL05           | xlc/C++ 16.1.0                        |
