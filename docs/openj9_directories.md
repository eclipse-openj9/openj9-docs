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

# Directory conventions

The following tables provide a quick reference to the Eclipse OpenJ9&trade; VM directory location on different Java&trade; versions and different platform architectures. Some
pages refer to the VM directory location as `<vm_dir>`.


| Operating system | Java 8                                  | Java 11 and later                          |
|------------------|-----------------------------------------|--------------------------------------------|
| AIX&reg;         | `<install_dir>/jre/lib/ppc[64]/default` | `<install_dir>/` |
| Linux&reg;       | `<install_dir>/jre/lib/<arch>/default`  | `<install_dir>/` |
| macOS&reg;        | `<install_dir>/jre/lib/default`         | `<install_dir>/` |
| Windows&trade;   | `<install_dir>\jre\bin\default`         | `<install_dir>\` |
| z/OS&reg;        | `<install_dir>/jre/lib/s390[x]/default` | `<install_dir>/` |

Where:

- `<install_dir>` is your JDK installation directory.
- `<arch>` depends on the architecture your Linux distribution is running on. See the following table for possible values:

| Architecture                                       | Value of `<arch>`     |
|----------------------------------------------------|-----------------------|
| x86 32-bit                                         | `i386`                |
| x86 64-bit                                         | `x86-64`              |
| IBM POWER&reg; 32-bit (Big Endian)      | `ppc`                 |
| IBM POWER 64-bit (Big Endian)                      | `ppc64`               |
| IBM POWER 64-bit (Little Endian)                   | `ppc64le`             |
| IBM Z&reg; 31-bit                       | `s390`                |
| IBM Z 64-bit                                       | `s390x`               |
