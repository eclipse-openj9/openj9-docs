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

# -XX:\[+|-\]CRIURestoreNonPortableMode

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

This option specifies whether the JIT and AOT compilers generate nonportable compiled code on restore.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- This option takes effect only when [`-XX:+EnableCRIUSupport`](xxenablecriusupport.md) is enabled.
- When you disable this option,
    - By default, `CRIUSECProvider` is the only security provider in both checkpoint and restore. ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) But, if `CRIUSECProvider` is disabled with the [`-XX:-CRIUSecProvider`](xxcriusecprovider.md) option, then the existing security providers are used during the checkpoint phase and restore phase. You can use any algorithm in the existing providers. ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)
    - JITServer technology is disabled both before you take a checkpoint and after you restore the VM.
    - the VM generates only portable code, both before you take a checkpoint and after you restore the VM.


## Syntax

        -XX:[+|-]CRIURestoreNonPortableMode

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+CRIURestoreNonPortableMode` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>    |
| `-XX:-CRIURestoreNonPortableMode` | Disable |                                                                                 |


## Explanation

The JIT compiler can use hardware features of the system on which it is running to generate compiled code. That code might therefore fail if it is included in a checkpoint image that you later restore on a different system. For example, the compiled code might try to use a hardware feature that doesn't exist on the new machine, then fail. To avoid this problem, the JIT compiler by default generates code that uses only basic hardware features, therefore portable to different systems.

The `-XX:+CRIURestoreNonPortableMode` option is set by default so that on restore the JIT compiler can start generating nonportable compiled code. Likewise, the VM can also load nonportable AOT code post-restore. Generating nonportable compiled code also means that no further checkpoints are permitted. Only a single checkpoint can be taken in the VM's lifetime.

If you require multiple checkpoints, you can use the `-XX:-CRIURestoreNonPortableMode` option to generate portable JIT compiled code after restore.

## See also

- [CRIU support](criusupport.md)

<!-- ==== END OF TOPIC ==== xxcriurestorenonportablemode.md ==== -->
