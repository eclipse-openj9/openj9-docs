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

# -XdynamicHeapAdjustment

**Linux&reg; on x86, Linux on IBM Power&reg; systems, Linux on AArch64 and Linux on IBM Z&reg; systems (64-bit only)**

![Java 11 (LTS) and later](docs/cr/java11plus.png) This option enables a VM capability that automatically adjusts the maximum Java heap size ([`-Xmx`](xms.md)) and minimum Java heap size ([`-Xms`](xms.md)) values such that they are within the physical memory limitations on the system.

## Syntax

        -XdynamicHeapAdjustment

The option is disabled by default. After it is enabled, the option cannot be disabled.

## Explanation

Large `-Xmx` or `-Xms` values can cause problems or errors. If the `-Xmx` value is set larger than the available memory, errors, such as Out of memory error, excessive paging, or a bus error, might occur. If the `-Xms` value is set larger than the available memory or the soft maximum size of the cache ([`-Xsoftmx`](xsoftmx.md)), an unrecoverable error occurs.

The `-XdynamicHeapAdjustment` option can be used to auto adjust the system, and avoid potential problems because of too large `-Xmx` or `-Xms` values. If the `-Xmx` or `-Xms` values are specified together with the `-XdynamicHeapAdjustment` option, VM automatically picks a size for `-Xmx` and `-Xms` that is within the limits of the available memory on the system. Typically, this size is 75% of the available memory.

For example, if the system has 8 GB memory but `-Xmx` is set to 12 GB, then the VM sets the value of `xsoftmx`, 6 GB, as the default maximum size of the heap.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- You can specify the `-XdynamicHeapAdjustment` option only at checkpoint time, and it applies to both checkpoint and restore.
- The `-XdynamicHeapAdjustment` option can be used only with the `-XX:+EnableCRIUSuport` option.

![End of content that applies only to Java 11 and later](cr/java_close.png)


## See also

- [What's new in version 0.46.0](version0.46.md#new-xdynamicheapadjustment-option-added)


<!-- ==== END OF TOPIC ==== xdynamicheapadjustment.md ==== -->
