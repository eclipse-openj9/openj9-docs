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

# -XXsetHWPrefetch  

**(AIX&reg; only)**

This option enables or disables hardware prefetch. Hardware prefetch can improve the performance of applications by prefetching memory. However, because of the workload characteristics of many Java&trade; applications, prefetching often has an adverse effect on performance.

## Syntax

        -XXsetHWPrefetch=[none|os-default]

| Setting    | Effect  | Default                                                                            |
|------------|---------|:----------------------------------------------------------------------------------:|
| none       | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| os-default | Enable  |                                                                                    |

The `-XXsetHWPrefetch:none` option disables hardware prefetch. Although you can disable hardware prefetch on AIX by issuing the command `dscrctl -n -s 1`, this command disables hardware prefetch for all processes, and for all future processes, which might not be desirable in a mixed workload environment. The `-XXsetHWPrefetch:none` option allows hardware prefetch to be disabled for individual VMs.

To enable hardware prefetch with the default value for the operating system, specify `-XXsetHWPrefetch:os-default`. Use this option only for applications that can obtain a performance gain from hardware prefetch.



<!-- ==== END OF TOPIC ==== xxsethwprefetch.md ==== -->
