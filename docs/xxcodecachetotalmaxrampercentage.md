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

# -XX:codecachetotalMaxRAMPercentage

This option sets the maximum limit for the total JIT code cache size based on the amount of physical memory available to the VM.

## Syntax

        -XX:codecachetotalMaxRAMPercentage=<number>

| Setting                 | Value      | Default                     |
|-------------------------|------------|:---------------------------:|
| `<number>`    | Between 1 and 100 (fractional numbers are allowed) |  25                       |

Where `<number>` is the maximum limit that is expressed as a percentage of the available physical memory.

For example, if you specify `-XX:codecachetotalMaxRAMPercentage=30`, the VM is not allowed to use more than 30% of the available physical memory for the internal JIT code caches.

## Explanation

The default total size of the JIT code cache is computed as the minimum of the following two limits:

- A platform-based limit that is expressed as an absolute memory value. This limit is set at 256 MB for 64-bit systems and at 64 MB for 31/32-bit systems.
- A percentage of the amount of available physical memory the VM process is allowed to use. By default, the VM is not allowed to use more than 25% of the available physical memory for its code caches.

In memory constrained environments, the percentage limit is relevant because the code cache size is then based on the available physical memory. The percentage limit prevents the VM from using too much memory for its code caches and thus, leaving too little memory for other VM or JIT data structures.

For example, on a 64-bit system, the platform-specific code cache limit is 256 MB. If the VM is constrained to less than 1024 MB, say 512 MB, then the code cache limit becomes 128 MB (25% of 512 MB) because the percentage limit is less than the platform limit.

To fine-tune the code cache size limit for your specific application as a percentage of the available physical memory, you can use the `-XX:codecachetotalMaxRAMPercentage` option.

As an alternative, you can use the [`-XX:codecachetotal`](xxcodecachetotal.md) or the [`-Xcodecachetotal`](xcodecachetotal.md) options to set the code cache size limit as an absolute value (platform-based limit). The absolute value that is specified with these options takes precedence over the percentage that is specified with the `-XX:codecachetotalMaxRAMPercentage` option.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- If the `-XX:codecachetotal` or `-Xcodecachetotal` option is specified, then the `-XX:codecachetotalMaxRAMPercentage` option is ignored.
- The percentage of the available physical memory that is specified in the `-XX:codecachetotalMaxRAMPercentage` option is used only if the total cache value thus calculated is less than the default total code cache value set for the platform (minimum of the two limits).

## See also

[What's new in version 0.40.0](version0.40.md#new-xxcodecachetotalmaxrampercentage-option-added)


<!-- ==== END OF TOPIC ==== xxcodecachetotalmaxrampercentage.md ==== -->
