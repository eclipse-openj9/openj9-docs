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

# -Dcom.ibm.tools.attach.timeout

Specify a time that an application should wait when attempting to connect to a target virtual machine (VM) before ending.

## Syntax

        -Dcom.ibm.tools.attach.timeout=<ms>

| Setting | Value                          | Default                          |
|---------|--------------------------------|----------------------------------|
| `<ms>`  | [1 millisecond or greater]     | 120000 milliseconds (120 seconds)|

## Example

To timeout after 60 seconds, specify:

`-Dcom.ibm.tools.attach.timeout=60000`

## See also

- [Support for the Java&trade; Attach API](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/attachapi.html)
- [-Dcom.ibm.tools.attach.enable](dcomibmtoolsattachenable.md)
- [-Dcom.ibm.tools.attach.directory](dcomibmtoolsattachdirectory.md)
- [-Dcom.ibm.tools.attach.displayName](dcomibmtoolsattachdisplayname.md)
- [-Dcom.ibm.tools.attach.id](dcomibmtoolsattachid.md)


<!-- ==== END OF TOPIC ==== dcomibmtoolsattachtimeout.md ==== -->
