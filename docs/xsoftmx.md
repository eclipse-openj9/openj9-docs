<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# -Xsoftmx

This option sets a "soft" maximum limit for the initial size of the Java&trade; heap.

## Syntax

        -Xsoftmx<size>

## Explanation

Use the [`-Xmx`](xms.md) option to set a "hard" limit for the maximum size of the heap. By default, `-Xsoftmx` is set to the same value as `-Xmx`. The value of `-Xms` must be less than, or equal to, the value of `-Xsoftmx`. See the introduction to this topic for more information about specifying *&lt;size&gt;* parameters.

You can set this option on the command line, then modify it at run time by using the `MemoryMXBean.setMaxHeapSize()` method in the `com.ibm.lang.management` API. By using this API, Java applications can dynamically monitor and adjust the heap size as required. This function can be useful in virtualized or cloud environments, for example, where the available memory might change dynamically to meet business needs. When you use the API, you must specify the value in bytes, such as `2147483648` instead of `2g`.

For example, you might set the initial heap size to 1 GB and the maximum heap size to 8 GB. You might set a smaller value, such as 2 GB, for `-Xsoftmx`, to limit the heap size that is used initially:

    -Xms1g -Xsoftmx2g -Xmx8g

You can then use the `com.ibm.lang.management` API from within a Java application to increase the `-Xsoftmx` value during run time, as load increases. This change allows the application to use more memory than you specified initially.

If you reduce the `-Xsoftmx` value, the garbage collector attempts to respect the new limit. However, the ability to shrink the heap depends on a number of factors. There is no guarantee that a decrease in the heap size will occur. If or when the heap shrinks to less than the new limit, the heap will not grow beyond that limit.

When the heap shrinks, the garbage collector might release memory. The ability of the operating system to reclaim and use this memory varies based on the capabilities of the operating system.

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

- When using `-Xgcpolicy:gencon`, `-Xsoftmx` applies only to the non-nursery portion of the heap. In some cases the heap grows to greater than the `-Xsoftmx` value because the nursery portion grows, making the heap size exceed the limit that is set. See `-Xmn` for limiting the nursery size.

- When using `-Xgcpolicy:metronome`, `-Xsoftmx` is ignored because the Metronome garbage collector does not support contraction or expansion of the heap.

- There might be little benefit in reducing the `-Xsoftmx` value when the Java heap is using large pages. Large pages are pinned in memory and are not reclaimed by the operating system, with the exception of 1M pageable pages on z/OS&reg;. On certain platforms and processors the VM starts with large pages enabled by default for the Java heap when the operating system is configured to provide large pages.
For more information, see [Configuring large page memory allocation](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_large_page.html). A future version of the Java virtual machine might provide a hint to the operating system when large pages are no longer in use.



<!-- ==== END OF TOPIC ==== xsoftmx.md ==== -->
