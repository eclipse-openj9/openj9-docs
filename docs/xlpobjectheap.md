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

# -Xlp:objectheap


Requests the Eclipse OpenJ9&trade; VM to allocate the Java&trade; object heap by using large page sizes.

To find out the large page sizes available and the current setting, use the `-verbose:sizes` option. Note that the current settings are the requested sizes and not the sizes obtained. For object heap size information, check the `-verbose:gc` output.

## Syntax

AIX&reg;, Linux&reg;, macOS&reg;, and Windows&trade;:

        -Xlp:objectheap:pagesize=<size>[,strict|warn]

z/OS&reg;:

        -Xlp:objectheap:pagesize=<size>[,strict|warn][,pageable|nonpageable]

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

: On z/OS systems, the default page size is 1 MB pageable large pages. For more information, see [`pageable`|`nonpageable`](#pageablenonpageable).

### `strict` | `warn`

        -Xlp:objectheap:strict
        -Xlp:objectheap:warn

:    -   `strict` causes an error message to be generated if large pages are requested but cannot be obtained. This option causes the VM to end.
    -   `warn` causes a warning message to be generated if large pages are requested but cannot be obtained. This option allows the VM to continue.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If both `strict` and `warn` are specified, `strict` takes precedence.

### `pageable`|`nonpageable`

        -Xlp:objectheap:pageable
        -Xlp:objectheap:nonpageable

: On z/OS systems, defines the type of memory to allocate for the Java object heap.

    1 MB pageable large pages, when available, are the default size for the object heap.  

    64-bit VMs support large page sizes of 1 MB nonpageable and 2 GB nonpageable with the following requirements:

    - 2 GB nonpageable sizes are supported only on IBM zEnterprise EC12 processors or later.
    - A system programmer must configure z/OS for nonpageable large pages.
    - Users who require large pages must be authorized to the **IARRSM.LRGPAGES** resource in the RACF FACILITY class with read authority.

    31-bit VMs support a large page size of only 1 MB pageable.

    A page size of 4 KB can also be used.

## Examples

1. **z/OS:** To allocate 1 GB of real memory by using 1 MB nonpageable pages when the VM starts, set the following options:

```
-Xmx1023m -Xms512m -Xlp:objectheap:pagesize=1M,nonpageable
```

2. **z/OS:** To allocate 1 GB of real memory by using 2 GB nonpageable pages, set the following options:

```
-Xmx1023m -Xms512m -Xlp:objectheap:pagesize=2G,nonpageable
```

In this example the heap is allocated on a 2 GB large page. Even though the object heap is only 1 GB, 2 GB of memory are consumed and the large page is never paged out while the VM is running.

## See also

- [Configuring large page memory allocation](configuring.md#configuring-large-page-memory-allocation).



<!-- ==== END OF TOPIC ==== xlpobjectheap.md ==== -->
