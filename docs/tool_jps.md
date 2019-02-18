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

# Java process status

Use the `jps` tool to query running Java<sup>&trade;</sup> processes on the current host. The command syntax is as follows:

    jps [<options>]

    jps: Print a list of Java processes and information about them
    -J: supply arguments to the Java VM running jps
    -l: print the application package name
    -q: print only the virtual machine identifiers
    -m: print the application arguments
    -v: print the Java VM arguments, including those produced automatically

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** This tool is the OpenJ9 implementation of the `jps` tool in the Java reference implementation, and differs from the HotSpot implementation. The tool is subject to change or removal in future releases.

The tool uses the Attach API, and has the following limitations:
- Does not list Java processes on other hosts, for security reasons
- Does not list Java processes owned by other users
- Does not list non-OpenJ9 Java processes
- Does not list processes whose attach API is disabled. <i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** The Attach API is disabled by default on z/OS.

For more information about the Attach API, including how to enable and secure it, see [Support for the Java Attach API](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/attachapi.html).


<!-- ==== END OF TOPIC ==== tool_builder.md ==== -->
