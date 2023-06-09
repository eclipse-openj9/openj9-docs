<!--
* Copyright (c) 2017, 2023 IBM Corp. and others
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

To find out the large page sizes available and the current setting, use the `-verbose:sizes` option. Note that the current settings are the requested sizes and not the sizes obtained. For object heap size information, check the `-verbose:gc` output.

## Syntax

AIX&reg;, Linux&reg;, macOS&reg;, and Windows&trade;:

        -Xlp:codecache:pagesize=<size>

z/OS&reg;:

        -Xlp:codecache:pagesize=<size>,pageable

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

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

- [Configuring large page memory allocation](configuring.md#configuring-large-page-memory-allocation).



<!-- ==== END OF TOPIC ==== xlpcodecache.md ==== -->
