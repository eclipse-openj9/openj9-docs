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

# -XX:DisclaimDir

**(Linux&reg; only)**

This option specifies the directory that is used to store some files that are required by OpenJ9's memory disclaiming mechanism.

## Syntax

        -XX:DisclaimDir=<directory>

## Explanation

When OpenJ9's memory disclaiming mechanism releases physical memory pages, the files that are required in the future by this mechanism are stored in the directory that is specified by the `-XX:DisclaimDir` option.

If the option is not specified, the VM selects a location automatically by using either of the following ways:

- Swap space, if available
- `/tmp` directory, if a suitable swap space is not available

## See also

- [-XX:\[+|-\]MemoryDisclaim](xxmemorydisclaim.md)


<!-- ==== END OF TOPIC ==== xxdisclaimdir.md ==== -->
