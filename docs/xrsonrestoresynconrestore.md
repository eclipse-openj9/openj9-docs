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

# -Xrs:onRestore / -Xrs:syncOnRestore

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

The `-Xrs` option is an existing option for disabling signal handling in the VM. CRIU support adds new suboptions `onRestore` and `syncOnRestore` for disabling signal handling when you restore a VM.

If the VM was started with signal handling enabled, then when you restore the VM, the signal handling is enabled by default. It is possible that there are frames with attached signal handlers already on the stack. These frames remain unchanged and their signal handlers remain active. Likewise, some global handlers might not be removed as well. The new options prevent the addition of new signal handlers and force recompilation of Java&trade; methods that make use of the existing signal handlers.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This option takes effect only when the `-XX:+EnableCRIUSupport` option is enabled. This option can be used only on restore.

## Parameters

### `onRestore`

        -Xrs:onRestore

The `-Xrs:onRestore` option is similar to the [`-Xrs`](xrs.md) option.

### `syncOnRestore`

        -Xrs:syncOnRestore

The `-Xrs:syncOnRestore` option is similar to the [`-Xrs:sync`](xrs.md#parameters) option.

Although there are similarities between the existing options and the new CRIU related options, there are behavioural differences that limits what can be disabled with the existing options at the restore time.

## See also

- [CRIU support](criusupport.md)

<!-- ==== END OF TOPIC ==== xrsonrestoresynconrestore.md ==== -->
