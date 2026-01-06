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

# -Xshareclasses:disableOnRestore

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

The `-Xshareclasses` option is an existing option and `disableOnRestore` is its new parameter that can be used if the CRIU feature is available. This option is disabled by default.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- This option takes effect only when the `-XX:+EnableCRIUSupport` option is enabled.
- This option can be used only on restore.

## Parameters

### `disableOnRestore`

        -Xshareclasses:disableOnRestore

The `-Xshareclasses:disableOnRestore` option disables further use of the shared classes cache (SCC) on restore. You can still use the [shared classes utility APIs](shrc.md#the-java-shared-classes-utility-api), for example, to obtain metrics about the shared cache.

The `-Xshareclasses:disableOnRestore` option is similar to the existing [`-Xshareclasses:none`](xshareclasses.md#none) option but with the following differences:

- The `-Xshareclasses:none` option turns off the cache on the VM startup but the `-Xshareclasses:disableOnRestore` option turns off the cache on restore. If you turn off the cache on startup, then specifying the `-Xshareclasses:disableOnRestore` option has no impact because there is no cache existing in the checkpoint image for the option to disable.
- The `-Xshareclasses:none` option disables the shared classes utility APIs but the `-Xshareclasses:disableOnRestore` option keeps the utility APIs working.

## See also

- [-Xshareclasses](xshareclasses.md)
- [CRIU support](criusupport.md)

<!-- ==== END OF TOPIC ==== xsharxshareclassesdisableonrestore.md ==== -->
