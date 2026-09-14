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

This option specifies the directory for storing the backing files that are required by the memory disclaim mechanism. The backing files hold the data of the physical memory pages that were disclaimed.

## Syntax

        -XX:DisclaimDir=<directory>

## Explanation

OpenJ9's memory disclaiming mechanism releases physical memory pages that are occupied by internal data structures, such as data caches and interpreter profiler, back to the operating system when they are unlikely to be needed soon. However, the VM still reserves the virtual address space of the data structures if the VM needs to access that data later. Instead of consuming actual physical memory, the data is moved to a temporary backing file located in the specified directory, while retaining the virtual address. The physical memory that was released is marked as free and other processes or the operating system can immediately take over that physical memory for other tasks.

When the VM accesses the virtual address of the disclaimed memory page, the operating system loads the stored data back into the physical memory for the VM to use.

If the option is not specified, the VM selects a location automatically in either of the following ways:

- Swap space, if available
- `/tmp` directory, if swap cannot be used

The status of the directory that is specified for the backing files affects the memory disclaiming process status as explained in the following table.

|  Status of the specified directory          |  VM behavior    |    Memory disclaiming status     |
|---------------------------------------------|-----------------|--------------------------------|
|  Does not exist or is not a directory       |   VM terminates (fatal startup error)    |   Process exits   |
|  Exists, but not writeable                  |   VM continues running                   |   Disabled        |
|  Exists, but has less than 1 GB free space  |   VM continues running                   |   Disabled        |
|  Directory is on a remote or network file system, such as NFS  |   VM continues running   |   Disabled (to avoid high network I/O latency)       |
|  Directory is backed by RAM or memory-based file system, such as ramfs, tmpfs         |   VM continues running |   Disabled (storing disclaimed memory pages in RAM defeats the purpose of freeing physical memory)     |


## See also

- [-XX:\[+|-\]MemoryDisclaim](xxmemorydisclaim.md)


<!-- ==== END OF TOPIC ==== xxdisclaimdir.md ==== -->
