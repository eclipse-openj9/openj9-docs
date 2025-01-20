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

# -XX:\[+|-\]HeapManagementMXBeanCompatibility

The MXBean interface now reports more detailed information about memory pools and garbage collectors for a garbage collection policy. In addition, the names of memory pools and garbage collectors are changed to match the naming convention that is used for verbose garbage collection logging. This option provides compatibility with earlier versions of the VM.

## Syntax

        -XX:[+|-]HeapManagementMXBeanCompatibility

| Setting                                  | Effect  | Default                                                                            |
|------------------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+HeapManagementMXBeanCompatibility` | Enable  |                                                                                    |
| `-XX:-HeapManagementMXBeanCompatibility` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

Setting `-XX:+HeapManagementMXBeanCompatibility` on the command line turns on compatibility with earlier versions of the VM. Information about memory pools and garbage collectors are reported in the older format.

When compatibility is turned off, the VM reports more detailed information and matches the naming of memory pools and garbage collectors to the naming convention that is used for verbose garbage collection logging.

## Explanation

The additional information that is available from the MXBean interface for later versions is shown in the following table:

| Garbage collection policy        | `MemoryPool` names                                                     | `GarbageCollector` names           |
|----------------------------------|------------------------------------------------------------------------|------------------------------------|
| **gencon**                       | nursery-allocate, nursery-survivor, tenured-LOA, tenured-SOA, tenured  | scavenge, global                   |
| **optthruput** or **optavgpause**| tenured-LOA, tenured-SOA, tenured                                      | global                             |
| **balanced**                     | balanced-reserved, balanced-eden, balanced-survivor, balanced-old      | partial gc, global garbage collect |
| **metronome**                    | JavaHeap                                                               | global                             |                    

The `MemoryPoolMXBean` API reports values for 4 detailed memory pools instead of a single value for the overall Java&trade; heap. In some cases the total sum of the 4 pools is more than the maximum heap size. This irregularity can be caused if data for each pool is collected between garbage collection cycles, where objects have been moved or reclaimed. If you want to collect memory usage data that is synchronized across the memory pools, use the `GarbageCollectionNotificationInfo` or `GarbageCollectorMXBean.getLastGcInfo` extensions.

Earlier releases included only the following names:

-   `MemoryPool` pool name: `Java heap`
-   `GarbageCollector` name: `Copy` and `MarkSweepCompact`.

## See also

- [Verbose garbage collection logging](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_pd_verbosegc.html).

For more information about IBM&reg; MXBeans, see the `com.ibm.lang.management` API documentation.



<!-- ==== END OF TOPIC ==== xxheapmanagementmxbeancompatibility.md ==== -->
