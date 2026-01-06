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

# -Xscminaot / -Xscmaxaot

When you create a shared classes cache, you can use these options to set the minimum and maximum number of bytes in the class cache to reserve for AOT data.

Setting `-Xscmaxaot` is useful if you want a certain amount of cache space guaranteed for non-AOT data.


## Syntax

| Setting           | Effect                               | Default                                       |
|-------------------|--------------------------------------|-----------------------------------------------|
|`-Xscminaot<size>` | Set minimum size for AOT class cache | 0                                             |
|`-Xscmaxaot<size>` | Set maximum size for AOT class cache | The amount of free space in the cache         |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## `-Xscminaot`

If `-Xscminaot` is not specified, no space is reserved for AOT data. However, AOT data is still written to the cache until the cache is full or the `-Xscmaxaot` limit is reached. 

The value of `-Xscminaot` must not exceed the value of [`-Xscmx`](xscmx.md) or `-Xscmaxaot` and should be considerably less than the total cache size, because AOT data can be created only for cached classes. If the value of `-Xscminaot` equals the value of `-Xscmx`, no class data or AOT data can be stored.

- You can also adjust the `-Xscminaot` value by using:
    - [`-Xshareclasses:adjustminaot=<size>`](xshareclasses.md#adjustminaot-cache-utility) option
    - `MemoryMXBean.setSharedClassCacheMinAotBytes()` method in the `com.ibm.lang.management` API
- You can also adjust the `-Xscmaxaot` value by using:
    - [`-Xshareclasses:adjustmaxaot=<size>`](xshareclasses.md#adjustmaxaot-cache-utility) option
    - `MemoryMXBean.setSharedClassCacheMaxAotBytes()` method in the `com.ibm.lang.management` API.

## `-Xscmaxaot`

The value of this option must not be smaller than the value of `-Xscminaot` and must not be larger than the value of `-Xscmx`.

When you run an application with the `-Xshareclasses:verbose` option, the VM writes to the console the amount of AOT data that is not stored due to the current setting of the maximum AOT data space. You can also get this information by using the `MemoryMXBean.getSharedClassCacheMaxAotUnstoredBytes()` method in the `com.ibm.lang.management` API. You can increase the maximum AOT data space size accordingly if you want to add the unstored AOT data to the shared cache.

## See also

- [-Xshareclasses](xshareclasses.md#method-specification-syntax "Enables class sharing. This option can take a number of suboptions, some of which are cache utilities.")


<!-- ==== END OF TOPIC ==== xscminaot.md ==== -->
<!-- ==== END OF TOPIC ==== xscmaxaot.md ==== -->
