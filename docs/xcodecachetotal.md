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

# -Xcodecachetotal


Use this option to set the maximum size limit for the JIT code cache. This option also affects the size of the JIT data cache.

## Syntax

        -Xcodecachetotal<size>

The default size is 256 MB for a 64-bit VM and 64 MB for a 31/32-bit VM.

See [Using -X command-line options](x_jvm_commands.md) for more information about specifying the `<size>` parameter.

## Explanation

By default, the total JIT code cache size is 256 MB for a 64-bit VM and 64 MB for a 31/32-bit VM or 25% of the physical memory available to the VM process, whichever is lesser. Long-running, complex, server-type applications can fill the JIT code cache, which can cause performance problems because not all of the important methods can be JIT-compiled. Use the `-Xcodecachetotal` option to increase or decrease the maximum code cache size to a setting that suits your application. The minimum size of the code cache is restricted to 2 MB.

The value that you specify is rounded up to a multiple of the code cache block size, as specified by the [-Xcodecache](xcodecache.md) option. If you specify a value for the `-Xcodecachetotal` option that is smaller than the default setting, that value is ignored.

When you use this option, the maximum size limit for the JIT data cache, which holds metadata about compiled methods, is increased or decreased proportionally to support the JIT compilations.

The maximum size limits, for both the JIT code and data caches, that are in use by the VM are shown in Javadump output. Look for lines that begin with `1STSEGLIMIT`. Use this information together with verbose JIT tracing to determine suitable values for this option on your system. For example Javadump output, see [Java dump: Storage Management (MEMINFO)](dump_javadump.md#meminfo).

## See also

- [`-XX:codecachetotalMaxRAMPercentage`](xxcodecachetotalmaxrampercentage.md)
- [-Xjit](xjit.md)


<!-- ==== END OF TOPIC ==== xcodecachetotal.md ==== -->
