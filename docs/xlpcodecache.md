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

# -Xlp:codecache

Requests the Eclipse OpenJ9&trade; VM to allocate the JIT code cache by using large page sizes.

If the requested large page size is not available, the VM starts, but the JIT code cache is allocated by using a platform-defined size. A warning is displayed when the requested page size is not available.

To find out the large page sizes available and the current setting, use the `-verbose:sizes` option. These current settings are the requested sizes and not the sizes obtained. For object heap size information, check the `-verbose:gc` output.

To use the large pages in the VM, enable the large pages support on your local system. The process for enabling the large page support differs in different operating systems. For more information, see [Configuring large page memory allocation](configuring.md#configuring-large-page-memory-allocation).

If the configured large page size is greater than the size of the total code cache for JIT, then the page size that is used for code cache allocation is recalculated. The next available lower page size on the system is identified and used for the code cache allocation.

For example, if 1 GB, 2 MB and 4 KB pages are available on a system, the VM checks the total size of the JIT code cache. If the total JIT code cache size is not modified (by using the [`-Xcodecachetotal`](xcodecachetotal.md) option), then for a 64-bit VM, the JIT code cache size will be the default size, 256 MB. In this case, the VM does not use 1 GB pages for the code cache because the size of the page exceeds the total size of the code cache (256 MB for 64-bit systems). Thus, the next available page size lower than 256 MB is used for code cache allocation. In this example, the next available lower size page is 2 MB. 128 pages (of 2 MB each) are allocated for the code cache.

## Syntax

AIX&reg;, Linux&reg;, macOS&reg;, and Windows&trade;:

        -Xlp:codecache:pagesize=<size>

z/OS&reg;:

        -Xlp:codecache:pagesize=<size>,pageable

For more information about the `<size>` parameter, see [Using -X command-line options](x_jvm_commands.md).

## Default values

### AIX

:   The code cache page size is controlled by the `DATAPSIZE` setting of the `LDR_CNTRL` environment variable. The page size cannot be controlled by the `-Xlp:codecache:pagesize=<size>` option. Specifying any other page size results in a warning that the page size is not available. The `-verbose:sizes` output reflects the current operating system setting.

    For more information about the `LDR_CNTRL` environment variable, see [Configuring large page memory allocation: AIX systems](configuring.md#aix-systems).

### Linux

: The default size for the code cache depends on the architecture:

    - Linux on x86 and AMD64/EM64T systems: 2 MB large pages
    - Linux on IBM Z&reg;: 1 MB large pages
    - Linux on Power Systems&trade;: The code cache page size cannot be controlled by the `-Xlp:codecache:pagesize=<size>` option. Specifying any other page size results in a warning that the page size is not available. The `-verbose:sizes` output reflects the current operating system setting.
    - On other architectures, the VM uses the default operating system page size.

### macOS

: The default size for the code cache is 4 KB large pages.

### z/OS

: 1 MB pageable pages, when available, are the default size for the code cache.

    The `-Xlp:codecache:pagesize=<size>,pageable` option supports only a large page size of 1 MB pageable large pages. The use of 1 MB pageable large pages for the JIT code cache can improve the runtime performance of some Java&trade; applications. A page size of 4 KB can also be used.

## See also

- [Configuring large page memory allocation](configuring.md#configuring-large-page-memory-allocation)



<!-- ==== END OF TOPIC ==== xlpcodecache.md ==== -->
