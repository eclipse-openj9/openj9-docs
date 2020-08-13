<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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

# -Dcom.ibm.tools.attach.displayName

Change the default display name for the target virtual machine.

## Syntax

        -Dcom.ibm.tools.attach.displayName=<my_display_name>


| Setting          | Value   | Default                                                     |
|------------------|---------|-------------------------------------------------------------|
| `<my_display_name>` | [string]| The command line invocation used to start the application   |

To change the value for `<my_display_name>` that is recorded by an agent, enter a character string of your choice.

## See also

- [Java&trade; Attach API](attachapi.md)
- [-Dcom.ibm.tools.attach.command_timeout](dcomibmtoolsattachcommand_timeout.md)
- [-Dcom.ibm.tools.attach.directory](dcomibmtoolsattachdirectory.md)
- [-Dcom.ibm.tools.attach.enable](dcomibmtoolsattachenable.md)
- [-Dcom.ibm.tools.attach.id](dcomibmtoolsattachid.md)
- [-Dcom.ibm.tools.attach.logging](dcomibmtoolsattachlogging.md)
- [-Dcom.ibm.tools.attach.log.name](dcomibmtoolsattachlogname.md)
- [-Dcom.ibm.tools.attach.shutdown_timeout](dcomibmtoolsattachshutdown_timeout.md)
- [-Dcom.ibm.tools.attach.timeout](dcomibmtoolsattachtimeout.md)


<!-- ==== END OF TOPIC ==== dcomibmtoolsattachdisplayname.md ==== -->
