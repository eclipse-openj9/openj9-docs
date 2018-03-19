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

# -Dcom.ibm.dbgmalloc

##<i class="fa fa-eye"></i>

This option provides memory allocation diagnostic information for class library native code.


## Syntax

        -Dcom.ibm.dbgmalloc=true

## Explanation

When an application is started with this option, a Java<sup>&trade;</sup> dump records the amount of memory allocated by the class library components.

You can use this option together with the `-Xcheck:memory` option to obtain information about class library call sites and their allocation sizes.

Enabling this option has an impact on throughput performance. For sample Java dump output, see <i class="fa fa-external-link" aria-hidden="true"></i> [Native memory (NATIVEMEMINFO)](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/javadump_tags_nativememinfo.html).


<!-- ==== END OF TOPIC ==== dcomibmdbgmalloc.md ==== -->
