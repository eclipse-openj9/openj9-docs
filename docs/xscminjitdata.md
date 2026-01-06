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

# -Xscminjitdata / -Xscmaxjitdata

When you create a shared classes cache, you can use these options to set a minimum and maximum number of bytes in the class cache that can be used for JIT data.

When you run an application with the [`-Xshareclasses:verbose`](xshareclasses.md#verbose) option, the VM writes to the console the amount of JIT data that is not stored due to the current setting of the the maximum JIT data space. You can also get this information by using the `MemoryMXBean.getSharedClassCacheMaxJitDataUnstoredBytes()` method in the `com.ibm.lang.management` API. 

You can increase the maximum JIT data space size accordingly if you want to add the unstored data to the shared cache. However, the VM that provided the information no longer has the opportunity to store the JIT data. Subsequent VMs can store JIT data in the shared cache.

## Syntax

| Setting               | Effect                        | Default                                       |
|-----------------------|-------------------------------|-----------------------------------------------|
|`-Xscminjitdata<size>` | Set minimum size for JIT data | 0 (See [Default behavior](#default-behavior)) |
|`-Xscmaxjitdata<size>` | Set maximum size for JIT data | The amount of free space in the cache         |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## Default behavior

If `-Xscminjitdata` is not specified, no space is reserved for JIT data, although JIT data is still written to the cache until the cache is full or the `-Xscmaxjitdata` limit is reached. 

## Explanation

### `-Xscminjitdata`

The value of `-Xscminjitdata` must not exceed the value of `-Xscmx` or `-Xscmaxjitdata`. The value of `-Xscminjitdata` must always be considerably less than the total cache size, because JIT data can be created only for cached classes. If the value of `-Xscminjitdata` equals the value of `-Xscmx`, no class data or JIT data can be stored.

- You can also adjust the `-Xscminjitdata` value by using:
    - [`-Xshareclasses:adjustminjitdata=<size>`](xshareclasses.md#adjustminjitdata-cache-utility) option
    - `MemoryMXBean.setSharedClassCacheMinJitDataBytes()` method in the `com.ibm.lang.management` API.

### `-Xscmaxjitdata`

Setting `-Xscmaxjitdata` is useful if you want a certain amount of cache space guaranteed for non-JIT data. If this option is not specified, the maximum limit for JIT data is the amount of free space in the cache. The value of this option must not be smaller than the value of `-Xscminjitdata`, and must not be larger than the value of `-Xscmx`.

- You can also adjust the `-Xscmaxjitdata` value by using:
    - [`-Xshareclasses:adjustmaxjitdata=<size>`](xshareclasses.md#adjustmaxjitdata-cache-utility) option
    - `MemoryMXBean.setSharedClassCacheMinJitDataBytes()` method in the `com.ibm.lang.management` API.


## See also

- [`-Xscmx`](xscmx.md)












<!-- ==== END OF TOPIC ==== xscminjitdata.md ==== -->
<!-- ==== END OF TOPIC ==== xscmaxjitdata.md ==== -->




