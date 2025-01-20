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

# -Djdk.nativeXDHKeyAgreement

![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) This option enables or disables OpenSSL native cryptographic support for the XDH key agreement algorithm.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- This algorithm is supported on Java&trade; 11 and later.
- This algorithm is supported on OpenSSL 1.1.1 onwards.


## Syntax

        -Djdk.nativeXDHKeyAgreement=[true|false]


| Setting           | value    | Default                                                                        |
|-------------------|----------|:------------------------------------------------------------------------------:|
| `-Djdk.nativeXDHKeyAgreement` | true     | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-Djdk.nativeXDHKeyAgreement` | false    |                                                                                |

## Explanation

OpenSSL support is enabled by default for the XDH key agreement algorithm. If you want to turn off support for this algorithm only, set this option to `false`. To turn off support for this and other algorithms, see the [`-Djdk.nativeCrypto`](djdknativecrypto.md) system property command line option.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The XDH key agreement algorithm is not supported for OpenSSL on AIX&reg;. This option is ignored on AIX and the Java implementation is always used.



<!-- ==== END OF TOPIC ==== djdknativexdhkeyagreement.md ==== -->
