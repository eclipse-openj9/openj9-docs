<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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

# -Xlp

Requests the OpenJ9 VM to allocate the Java&trade; object heap and JIT code cache memory with large pages.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** This option is deprecated in all versions later than Java 8. Use the [`-Xlp:codecache`](xlpcodecache.md) and [`-Xlp:objectheap`](xlpobjectheap.md) options instead.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restriction:** This option does not work on macOS&reg;.

If you use the [`-Xgc:preferredHeapBase`](xgc.md#preferredheapbase) option with `-Xlp`, the preferredHeapBase address must be a multiple of the large page size. If you specify an inaccurate heap base address, the heap is allocated with the default page size.

To find out the large page sizes available and the current setting, use the `-verbose:sizes` option. Note that the current settings are the requested sizes and not the sizes obtained. For object heap size information, check the `-verbose:gc` output.

## Syntax

        -Xlp[<size>]

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## Explanation

### AIX&reg;

:   If `<size>` is specified, the VM attempts to allocate the JIT code cache memory by using pages of that size. Allocating large pages by using `-Xlp` is supported only on the 64-bit VM, not the 32-bit VM.

    If a size is not specified, this option requests the VM to allocate the Java object heap (the heap from which Java objects are allocated) with large (16 MB) pages.

    If large pages are not available, the Java object heap is allocated with the next smaller page size that is supported by the system. AIX requires special configuration to enable large pages.

    The VM supports the use of large pages only to back the Java object heap shared memory segments. The VM uses `shmget()` with the SHM_LGPG and SHM_PIN flags to allocate large pages. The `-Xlp` option replaces the environment variable `IBM_JAVA_LARGE_PAGE_SIZE`, which is now ignored if set.

    For more information about configuring AIX support for large pages, see [Large pages](https://www.ibm.com/support/knowledgecenter/ssw_aix_72/com.ibm.aix.performance/large_page_ovw.htm) in the AIX product documentation.

### Linux&reg;

:   If `<size>` is specified, the VM attempts to allocate the JIT code cache memory by using pages of that size. Allocating large pages by using `-Xlp` is supported only on the 64-bit VM, not the 32-bit VM.

    If large pages are not available, the VM does not start and produces an error message. The VM uses `shmget()` to allocate large pages for the heap. Large pages are supported by systems that have Linux kernels v2.6 or higher.

    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Linux for IBM Z&reg; supports only a large page size of 1 M.

    Depending on the architecture, 1 MB or 2 MB large pages, when available, are the default size for the object heap and the code cache. The options that control these sizes are [`Xlp:codecache`](xlpcodecache.md) and [`-Xlp:objectheap`](xlpobjectheap.md).

### Windows&trade;

:   If `<size>` is specified, the VM attempts to allocate the JIT code cache memory by using pages of that size.

    Allocating large pages by using `-Xlp` is supported only on the 64-bit VM, not the 32-bit VM.

### z/OS&reg;

:   If `<size>` is specified but unsuccessful, or if executable pages of that size are not supported, 1 M pageable is attempted. If 1 M pageable is not available, the JIT code cache memory is allocated by using the default or smallest available executable page size.

    If `<size>` is not specified, the 1 M nonpageable size is used. If large pages are not supported by the hardware, or enabled in RACF&reg;, the VM does not start and produces an error message.

    Allocating large pages by using `-Xlp` is supported only on the 64-bit VM, not the 31-bit VM. The `-Xlp[<size>]` option supports only a large page size of 2 G and 1 M (nonpageable).

    1 M pageable pages, when available, are the default size for the object heap and the code cache. The options that control these sizes are [`Xlp:codecache`](xlpcodecache.md) and [`-Xlp:objectheap`](xlpobjectheap.md).

    Specifying `-Xlp1M` uses a 1 M pageable size for the code cache, when available. Specifying `-Xlp2G` sets the object heap size, but generates a warning that 2 G nonpageable pages cannot be used for the code cache. Use the `-Xlp:objectheap:pagesize=2G,nonpageable` option to avoid the warning.

## Limitation and workaround

The VM ends if insufficient operating system resources are available to satisfy the request. However, an error message is not issued. There are a number of reasons why the VM cannot honor a large page request. For example, there might be insufficient large pages available on the system at the time of the request. To check whether the `-Xlp` request was honored, you can review the output from `-verbose:gc`. Look for the attributes `requestedPageSize` and `pageSize` in the `-verbose:gc` log file. The attribute `requestedPageSize` contains the value specified by `-Xlp`. The attribute `pageSize` is the actual page size used by the VM.

## See also

- [Configuring large page memory allocation](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_large_page.html).


<!-- ==== END OF TOPIC ==== xlp.md ==== -->
