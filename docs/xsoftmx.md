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

# -Xsoftmx

This option sets a "soft" maximum limit for the Java&trade; heap.

## Syntax

        -Xsoftmx<size>

## Explanation

Use the [`-Xmx`](xms.md) option to set a "hard" limit for the maximum size of the heap. By default, `-Xsoftmx` is set to the same value as `-Xmx`. The value of `-Xms` must be less than, or equal to, the value of `-Xsoftmx`.

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

You can set this option on the command line, then modify it at run time by using the `MemoryMXBean.setMaxHeapSize()` method in the `com.ibm.lang.management` API. By using this API, Java applications can dynamically monitor and adjust the heap size as required. This function can be useful in virtualized or cloud environments, for example, where the available memory might change dynamically to meet business needs. When you use the API, you must specify the value in bytes, such as `2147483648` instead of `2g`.

For example, you might set the initial heap size to 1 GB and the maximum heap size to 8 GB. You might set a smaller value, such as 2 GB, for `-Xsoftmx`, to limit the heap size that is used initially:

    -Xms1g -Xsoftmx2g -Xmx8g

You can then use the `com.ibm.lang.management` API from within a Java application to increase the `-Xsoftmx` value during run time, as load increases. This change allows the application to use more memory than you specified initially.

If you reduce the `-Xsoftmx` value, the garbage collector attempts to respect the new limit. However, the ability to shrink the heap depends on a number of factors. There is no guarantee that a decrease in the heap size will occur. If or when the heap shrinks to less than the new limit, the heap will not grow beyond that limit.

When the heap shrinks, the garbage collector might release memory. The ability of the operating system to reclaim and use this memory varies based on the capabilities of the operating system.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- When using `-Xgcpolicy:gencon` with `-Xsoftmx`, the proportion of heap space used for nursery within the `-Xsoftmx` limit is proportional to the maximum amount of nursery space specified (see [Xmn/Xmnx](xmn.md)) relative to the `-Xmx` value. For example, if the following is specified on the command line `-Xsoftmx2g -Xmnx4g  -Xmx8g`, nursery space is allowed to use 50%(4G/8G) of the specified `-Xsoftmx` value, which in this example is 1G.
- When using `-Xgcpolicy:balanced` with `-Xsoftmx` and `-Xmn`/`-Xmnx`/`-Xmns` options,  the maximum and minimum size for eden are absolute (rather than the proportional nursery behaviour for gencon), and do not depend on the `-Xsoftmx` value specified. For example, if `-Xmnx1G` is specified, then eden will be able to grow up to `1G` in size, regardless of the `-Xsoftmx` value specified.
- This option is ignored if used with the metronome GC policy (`-Xgcpolicy:metronome`) because the heap is always fully expanded.
- There might be little benefit in reducing the `-Xsoftmx` value when the Java heap is using large pages. Large pages are pinned in memory and are not reclaimed by the operating system, with the exception of 1M pageable pages on z/OS&reg;. On certain platforms and processors the VM starts with large pages enabled by default for the Java heap when the operating system is configured to provide large pages. For more information, see [Configuring large page memory allocation](configuring.md#configuring-large-page-memory-allocation). A future version of the Java virtual machine might provide a hint to the operating system when large pages are no longer in use.



<!-- ==== END OF TOPIC ==== xsoftmx.md ==== -->
