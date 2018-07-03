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

# -Dcom.ibm.signalhandling.ignoreLogoff

**(Windows&trade; only)**

This property controls the way the OpenJ9 VM handles a `CTRL_LOGOFF_EVENT` signal when the VM is running as an interactive Windows service.

## Syntax

        -Dcom.ibm.signalhandling.ignoreLogoff=[true|false]

| Setting      | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| true         | Enable  |                                                                                    |
| false        | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

## Explanation

Windows issues a `CTRL_LOGOFF_EVENT` when a user logs out of an interactive Windows service. By default, the VM ends when this signal is received. Setting this property to `true` prevents the VM ending when a `CTRL_LOGOFF_EVENT` signal is received. The default value for this property is `false`.



<!-- ==== END OF TOPIC ==== dcomibmsignalhandlingignorelogoff.md ==== -->
