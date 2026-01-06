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

# -Dcom.ibm.tools.attach.retry

**Windows&trade; only**

This option specifies the number of times the `jcmd` tool retries attaching to a running VM when the tool encounters the `SocketException` error.

## Syntax

        -Dcom.ibm.tools.attach.retry=<number_of_retries>

The default value for `<number_of_retries>` is 3.

## Explanation

When the `jcmd` tool sends a command to a running VM, the command might throw the `Socket Exception` error in case of issues, such as a network failure or a connection reset. Instead of failing the attaching request, you can specify the number of times the tool retries attaching to the target VM with the `-Dcom.ibm.tools.attach.retry` system property.

## See also

- [Java&trade; Attach API](attachapi.md)
- [What's new in version 0.46.0](version0.46.md#new-system-property-added-to-improve-jcmd-attaching-in-case-of-the-socketexception-error-on-windows-platform)


<!-- ==== END OF TOPIC ==== dcomibmtoolsattachretry.md ==== -->
