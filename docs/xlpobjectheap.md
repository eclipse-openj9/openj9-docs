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

# -Xlp:objectheap


Requests the OpenJ9 VM to allocate the Java&trade; object heap by using large page sizes.

To find out the large page sizes available and the current setting, use the `-verbose:sizes` option. Note that the current settings are the requested sizes and not the sizes obtained. For object heap size information, check the `-verbose:gc` output.

## Syntax

AIX&reg;, Linux&reg;, macOS&reg;, Windows&trade; and Z/OS&reg;:

        -Xlp:objectheap:pagesize=<size>[,strict|warn]

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## Parameters

### `pagesize`

        -Xlp:objectheap:pagesize=<size>

: The large page size that you require.

    If the operating system does not have sufficient resources to satisfy the request, the page size you requested might not be available when the VM starts up. By default, the VM starts and the Java object heap is allocated by using a different platform-defined page size. Alternatively, you can use the `strict` or `warn` suboptions to customize behavior.

#### Default page sizes

: On Linux systems, the default size for the object heap depends on the architecture:

    - Linux on x86 and AMD64/EM64T systems: 2 MB large pages
    - Linux on IBM Z&reg;: 1 MB large pages
    - On other architectures, the VM uses the default operating system page size.

: On macOS, the default page size is 4 KB.

: On Z/OS&reg If both 1M pageable, and nonpageable large pages are available then 1M pageable large pages will be used by default if [non]pageable is not specified.

### `strict` | `warn`

        -Xlp:objectheap:strict
        -Xlp:objectheap:warn

:    -   `strict` causes an error message to be generated if large pages are requested but cannot be obtained. This option causes the VM to end.
    -   `warn` causes a warning message to be generated if large pages are requested but cannot be obtained. This option allows the VM to continue.

    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** If both `strict` and `warn` are specified, `strict` takes precedence.

### `pageable`|`nonpageable`

        -Xlp:objectheap:pageable
        -Xlp:objectheap:nonpageable

: On z/OS systems, defines the type of memory to allocate for the Java object heap.

    1 MB pageable pages, when available, are the default size for the object heap.  
    Supported large page sizes are 2 GB nonpageable, 1 MB nonpageable, and 1 MB pageable. A page size of 4 KB can also be used.

## See also

- [Configuring large page memory allocation](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_large_page.html).



<!-- ==== END OF TOPIC ==== xlpobjectheap.md ==== -->
