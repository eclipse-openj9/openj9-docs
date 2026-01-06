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

# -XX:[+|-]CRIUSecProvider

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) This option enables or disables the use of the `CRIUSECProvider` during the checkpoint phase.

## Syntax

        -XX:[+|-]CRIUSecProvider

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+CRIUSecProvider` |  Enable   |   :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>                                 |
| `-XX:-CRIUSecProvider` |  Disable  |     |                                                 |


## Explanation

When you enable CRIU support (with the [`-XX:+EnableCRIUSupport`](xxenablecriusupport.md) option), all the existing security providers are removed from the security provider list during the checkpoint phase and `CRIUSECProvider` is added. `CRIUSECProvider` supports only a limited number of cryptography services and therefore, you can use only those security algorithms that are available in `CRIUSECProvider`.

You can now choose to disable the use of `CRIUSECProvider` with the `-XX:-CRIUSecProvider` option and continue to use all the existing security providers during the checkpoint and restore phase. When you use the security algorithms of other security providers, you must have alternative approaches to protect your files, such as initialization before you take checkpoints with nonsensitive data. ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)

## See also

- [CRIU support](criusupport.md)
- [What's new in version 0.38.0](version0.38.md#technical-preview-of-criu-support)

<!-- ==== END OF TOPIC ==== xxcriusecprovider.md ==== -->
