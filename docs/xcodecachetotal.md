<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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

# -Xcodecachetotal


Use this option to set the maximum size limit for the JIT code cache. This option also affects the size of the JIT data cache.

## Syntax

        -Xcodecachetotal<size>

: See [Using -X command-line options](x_jvm_commands) for more information about specifying the `<size>` parameter.

## Explanation

By default, the total size of the JIT code cache is determined by your operating system, architecture, and the version of the VM that you are using. Long-running, complex, server-type applications can fill the JIT code cache, which can cause performance problems because not all of the important methods can be JIT-compiled. Use the `-Xcodecachetotal` option to increase the maximum code cache size beyond the default setting, to a setting that suits your application.

The value that you specify is rounded up to a multiple of the code cache block size, as specified by the [-Xcodecache](xcodecache.md) option. If you specify a value for the `-Xcodecachetotal` option that is smaller than the default setting, that value is ignored.

When you use this option, the maximum size limit for the JIT data cache, which holds metadata about compiled methods, is increased proportionally to support the additional JIT compilations.

The maximum size limits, for both the JIT code and data caches, that are in use by the VM are shown in Javadump output. Look for lines that begin with `1STSEGLIMIT`. Use this information together with verbose JIT tracing to determine suitable values for this option on your system. For example Javadump output, see <i class="fa fa-external-link" aria-hidden="true"></i> [Interpreting a Java dump: Storage Management (MEMINFO)](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/javadump_tags_meminfo.html).

## See also

- [-Xjit](xjit.md)

- <i class="fa fa-external-link" aria-hidden="true"></i> [Using Javadump](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/javadump.html)

<!-- ==== END OF TOPIC ==== xcodecachetotal.md ==== -->
