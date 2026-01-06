<!--
* Copyright (c) 2017, 2026 IBM Corp. and others
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

# -Dcom.ibm.enableLegacyTraceSecurity


To improve security, the security checks in certain `com.ibm.jvm.Trace` APIs are now enabled by default, when the `SecurityManager` is enabled. Use this system property to turn off security checking for these APIs.

## Syntax

        -Dcom.ibm.enableLegacyTraceSecurity=[true|false]

| Setting      | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| true         | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| false        | Disable |                                                                                    |

## Explanation

Security checking is enabled in the following APIs:

- `com.ibm.jvm.Trace.set(String)`
- `com.ibm.jvm.Trace.snap()`
- `com.ibm.jvm.Trace.suspend()`
- `com.ibm.jvm.Trace.suspendThis()`
- `com.ibm.jvm.Trace.resume()`
- `com.ibm.jvm.Trace.resumeThis()`
- `com.ibm.jvm.Trace.registerApplication(String, String[])`


## See also

- [-Dcom.ibm.enableLegacyDumpSecurity](dcomibmenablelegacydumpsecurity.md)
- [-Dcom.ibm.enableLegacyLogSecurity](dcomibmenablelegacylogsecurity.md)



<!-- ==== END OF TOPIC ==== dcomibmenablelegacytracesecurity.md ==== -->
