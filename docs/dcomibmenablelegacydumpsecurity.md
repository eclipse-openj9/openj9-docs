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

# -Dcom.ibm.enableLegacyDumpSecurity


To improve security, the security checks in the certain `com.ibm.jvm.Dump` APIs are now enabled by default, when the `SecurityManger` is enabled. Use this system property to turn off security checking for these APIs.

## Syntax

        -Dcom.ibm.enableLegacyDumpSecurity=[true|false]

| Setting      | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| true         | Enable  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| false        | Disable |                                                                                    |

## Explanation

Security checking is enabled in the following APIs:

- `com.ibm.jvm.Dump.JavaDump()`
- `com.ibm.jvm.Dump.HeapDump()`
- `com.ibm.jvm.Dump.SnapDump()`

## See also

- [-Dcom.ibm.enableLegacyLogSecurity](dcomibmenablelegacylogsecurity.md)
- [-Dcom.ibm.enableLegacyTraceSecurity](dcomibmenablelegacytracesecurity.md)


<!-- ==== END OF TOPIC ==== dcomibmenableclasscaching.md ==== -->
