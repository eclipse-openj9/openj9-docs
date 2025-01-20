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

# -Xscmx

For a new shared classes cache, specifies either:

- the actual size of the cache, if the `-XX:SharedCacheHardLimit` option is not present
- the soft maximum size of the cache, if used with the `-XX:SharedCacheHardLimit` option
(See [`-XX:SharedCacheHardLimit`](xxsharedcachehardlimit.md))

This option applies only if a cache is being created and no cache of the same name exists.

When you run an application with the [`-Xshareclasses:verbose`](xshareclasses.md#verbose) option, the VM writes to the console the number of bytes that are not stored due to the current setting of the soft maximum size. You can also get this information by using the `MemoryMXBean.getSharedClassCacheSoftmxUnstoredBytes()` method in the `com.ibm.lang.management` API.

You can increase the soft maximum size accordingly if you want to add the unstored data to the shared cache. However, the VM that provided the information no longer has the opportunity to store that data. Therefore, increasing the soft maximum size does not necessarily cause any more data to be stored in the shared cache by the current VM, but subsequent VMs can add data to the shared cache.

## Syntax

        -Xscmx<size>

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## Explanation

### Setting a soft maximum size

If you specify the `-Xscmx` option with the [-XX:SharedCacheHardLimit](xxsharedcachehardlimit.md) option, the `-Xscmx` option sets the *soft maximum size* of a new shared classes cache, and the `-XX:SharedCacheHardLimit` option sets the actual maximum size. The value of the `-Xscmx` option must therefore not exceed the value of `-XX:SharedCacheHardLimit`.

When the soft maximum limit is reached, no more data can be added into the shared cache, unless there is reserved AOT or JIT data space. If such reserved space exists, AOT or JIT data can still be added into the reserved space. The reserved AOT and JIT data spaces are counted as used space within the soft maximum size, so the soft maximum size should not be less than the minimum reserved space for AOT and JIT data if you specify the [-Xscminaot](xscminaot.md) or [-Xscminjitdata](xscminjitdata.md) options.

You can change the soft maximum size by using the `-Xshareclasses:adjustsoftmx=<size>` option or the `MemoryMXBean.setSharedClassCacheSoftmxBytes()` method in the `com.ibm.lang.management` API. By using this API, Java&trade; applications can dynamically monitor and adjust the cache soft maximum size as required. This function can be useful in virtualized or cloud environments, for example, where the shared cache size might change dynamically to meet business needs.

For example, you might create a 64 MB shared cache and set a smaller value, such as 16 MB, for the `-Xscmx` option, to limit the data stored in the cache:

    -XX:SharedCacheHardLimit=64m -Xscmx16m

You can then use the `com.ibm.lang.management` API from within a Java application to increase the soft maximum value during run time, as load increases. This change allows the application to use more shared cache space than you specified initially.

You can increase the soft maximum size if it is currently less than the actual cache size. If you attempt to reduce the soft maximum size to a value that is less than the number of bytes already used in the cache, the number of used bytes is set as the new soft maximum size.

### Cache size limits

The theoretical cache size limit is 2 GB. The size of the cache that you can specify is limited by the following factors:

- AIX&reg;, Linux&reg;, and macOS&reg;: The amount of physical memory and paging space available to the system.
- Windows&reg;: The amount of available disk space and available virtual address space.
- z/OS&reg;: The amount of swap space available to the system.

Non-persistent caches are stored in shared memory and are removed when a system is rebooted. On systems other than Windows, non-persistent caches are allocated by using the System V IPC shared memory mechanism. To ensure that sufficient shared memory is available for class data sharing, see [Setting shared memory values](configuring.md#setting-shared-memory-values).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** By default, a cache is persistent on all platforms, except z/OS.

## See also

- [`-Xscdmx`](xscdmx.md) (control the size of the class debug area)


<!-- ==== END OF TOPIC ==== xscmx.md ==== -->
