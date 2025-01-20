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

# -Dcom.ibm.tools.attach.fileAccessUpdateTime

**Linux&reg; only**

This option enables Attach API to update the control file access times at specific intervals, for long-running applications.

## Syntax

        -Dcom.ibm.tools.attach.fileAccessUpdateTime=<sleep days>

where `<sleep days>` is the number of interval days after which Attach API updates the control file access times for long-running applications. The default value for `<sleep days>` is 8.

## Explanation

By default, the Attach API of a VM places its control files in the system temporary directory, `/tmp/.com_ibm_tools_attach`. The long-running Attach API uses the control files to operate. But, the VM does not open, modify, or read from these files after the files are created, if there is no attempt to attach to a target VM.

This causes a problem in Linux environments because by default `systemd-tmpfiles` automatically deletes all files and directories that are stored in the `/tmp/` folder that are not changed or read within a specific time period. By default, the files in the `/tmp/` folder are cleaned up after 10 days by `systemd-tmpfiles`.

You can prevent Linux `systemd-tmpfiles` from deleting the Attach API control files within the `/tmp/` folder with the `-Dcom.ibm.tools.attach.fileAccessUpdateTime` system property. You can specify the interval days after which Attach API updates the control file access times with the `-Dcom.ibm.tools.attach.fileAccessUpdateTime` system property.

If `0` is specified as the number of `<sleep days>`, the control file access times are not updated and if `systemd-tmpfiles` is enabled, it deletes the files in the `/tmp/` folder.

You can specify a different location outside of the `/tmp/` folder to place the Attach API control files with the [`-Dcom.ibm.tools.attach.directory`](dcomibmtoolsattachdirectory.md) system property, if you do not want to use the `-Dcom.ibm.tools.attach.fileAccessUpdateTime` system property.

## See also

- [Java&trade; Attach API](attachapi.md)
- [What's new in version 0.44.0](version0.44.md#new-system-property-added-to-prevent-the-deletion-of-the-attach-api-control-files-within-the-tmp-folder)


<!-- ==== END OF TOPIC ==== dcomibmtoolsattachfileaccessupdatetime.md ==== -->
