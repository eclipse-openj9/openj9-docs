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

# -Djdk.nativeChaCha20

This option enables or disables OpenSSL native cryptographic support for the ChaCha20 and ChaCha20-Poly1305 algorithms.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- ![Start of content that applies to Java 8 (LTS)](cr/java8.png) These algorithms are not supported on Java&trade; 8. ![End of content that applies only to Java 8](cr/java_close_lts.png)
- These algorithms are not supported on OpenSSL 1.0.x.


## Syntax

        -Djdk.nativeChaCha20=[true|false]


| Setting           | value    | Default                                                                        |
|-------------------|----------|:------------------------------------------------------------------------------:|
| `-Djdk.nativeChaCha20` | true     | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-Djdk.nativeChaCha20` | false    |                                                                                |

## Explanation

OpenSSL support is enabled by default for the ChaCha20 and ChaCha20-Poly1305 algorithms. If you want to turn off support for these algorithms only, set this option to `false`. To turn off support for these and other algorithms, see the [`-Djdk.nativeCrypto`](djdknativecrypto.md) system property command line option.




<!-- ==== END OF TOPIC ==== djdknativechacha20.md ==== -->
