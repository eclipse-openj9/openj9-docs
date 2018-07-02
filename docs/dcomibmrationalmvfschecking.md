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

# -Dcom.ibm.rational.mvfs.checking

**(Windows&trade; only)**

Use this property to improve the performance of Multi Version File System (MVFS) file systems.

## Syntax

        -Dcom.ibm.rational.mvfs.checking=[true|false]

| Setting      | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| true         | Enable  |                                                                                    |
| false        | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

## Explanation

The `WinNTFilesystem` methods `getModifiedTime` and `getBooleanAttributes` use the Windows methods `API_wstati64()` and `_wfindfirsti64()` instead of the defaults. This property is not enabled by default because it can cause performance degradation on local file systems. The property also causes degradation on remote Windows shares where there is no Windows directory cache for the remote file system.



<!-- ==== END OF TOPIC ==== dcomibmrationalmvfschecking.md ==== -->
