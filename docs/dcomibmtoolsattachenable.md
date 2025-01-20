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

# -Dcom.ibm.tools.attach.enable

Enable the Attach API for this application.

## Syntax

        -Dcom.ibm.tools.attach.enable=[yes|no]

On AIX&reg;, Linux&reg;, macOS&reg;, and Windows&trade; systems, the following default applies:

| Value        | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| yes          | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| no           | Disable |                                                                                    |

On z/OS&reg; systems, the following default applies:

| Value        | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| yes          | Enable  |                                                                                    |
| no           | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |



## Explanation

A useful reference for information about the Java&trade; Attach API can be found at [http://docs.oracle.com/javase/8/docs/technotes/guides/attach/index.html](https://docs.oracle.com/javase/8/docs/technotes/guides/attach/index.html). The following extract is taken from the Oracle documentation:

> The Attach API is an extension that provides a mechanism to attach to a Java virtual machine. A tool written in the Java Language, uses this API to attach to a target  virtual machine and load its tool agent into that virtual machine.

A usage example is to late attach the IBM&reg; Health Center agent to a virtual machine (VM) that is already running.

The Eclipse OpenJ9&trade; implementation of the Attach API is equivalent to the Oracle implementation. However, the OpenJ9 implementation cannot be used to attach to, or accept attach requests from, other VM implementations.

## See also

- [Java&trade; Attach API](attachapi.md)
- [-Dcom.ibm.tools.attach.command_timeout](dcomibmtoolsattachcommand_timeout.md)
- [-Dcom.ibm.tools.attach.directory](dcomibmtoolsattachdirectory.md)
- [-Dcom.ibm.tools.attach.displayName](dcomibmtoolsattachdisplayname.md)
- [-Dcom.ibm.tools.attach.id](dcomibmtoolsattachid.md)
- [-Dcom.ibm.tools.attach.logging](dcomibmtoolsattachlogging.md)
- [-Dcom.ibm.tools.attach.log.name](dcomibmtoolsattachlogname.md)
- [-Dcom.ibm.tools.attach.shutdown_timeout](dcomibmtoolsattachshutdown_timeout.md)
- [-Dcom.ibm.tools.attach.timeout](dcomibmtoolsattachtimeout.md)


<!-- ==== END OF TOPIC ==== dcomibmtoolsattachenable.md ==== -->
