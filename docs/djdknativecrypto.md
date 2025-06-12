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

# -Djdk.nativeCrypto

This option controls the use of OpenSSL native cryptographic support.


## Syntax

        -Djdk.nativeCrypto=[true|false]

| Setting              | value    | Default                                                                        |
|----------------------|----------|:------------------------------------------------------------------------------:|
| `-Djdk.nativeCrypto` | true     | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-Djdk.nativeCrypto` | false    |                                                                                |

## Explanation

OpenSSL support is enabled by default for the following algorithms:

- CBC
- ChaCha20 and ChaCha20-Poly1305
- ECDH key agreement
- ECDSA signature
- EC key generation
- GCM
- MD5
- PBE cipher
- PBKDF2
- RSA
- SHA-224
- SHA-256
- SHA-384
- SHA-512
- XDH key agreement
- XDH key generation

If you want to turn off the OpenSSL implementation, set the `-Djdk.nativeCrypto` option to `false`.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- ![Start of content that applies to Java 8 (LTS)](cr/java8.png) The ChaCha20 and ChaCha20-Poly1305 algorithms are not supported on Java&trade; 8. The XDH key agreement and XDH key generation algorithms also are not supported on Java 8. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)
- OpenSSL native cryptographic support is not available for the following algorithms on AIX&reg;:

    - EC key generation (`-Djdk.nativeECKeyGen`)
    - MD5 (part of `-Djdk.nativeDigest`)
    - XDH key generation (`-Djdk.nativeXDHKeyGen`)
    - XDH key agreement (`-Djdk.nativeXDHKeyAgreement`)

If you want to turn off the algorithms individually, use the following system properties:

- [`-Djdk.nativeCBC`](djdknativecbc.md)
- [`-Djdk.nativeChaCha20`](djdknativechacha20.md) (![Start of content that applies to Java 8 (LTS)](cr/java8.png) Not supported on Java 8. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png))
- [`-Djdk.nativeDigest`](djdknativedigest.md)
- [`-Djdk.nativeEC`](djdknativeec.md)
- [`-Djdk.nativeECDSA`](djdknativeecdsa.md)
- [`-Djdk.nativeECKeyGen`](djdknativeeckeygen.md)
- [`-Djdk.nativeGCM`](djdknativegcm.md)
- [`-Djdk.nativePBE`](djdknativepbe.md)
- [`-Djdk.nativePBKDF2`](djdknativepbkdf2.md)
- [`-Djdk.nativeRSA`](djdknativersa.md)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [`-Djdk.nativeXDHKeyAgreement`](djdknativexdhkeyagreement.md)
- [`-Djdk.nativeXDHKeyGen`](djdknativexdhkeygen.md) ![End of content that applies to Java 11 (LTS) and later](cr/java_close_lts.png)

<!-- ==== END OF TOPIC ==== djdknativecrypto.md ==== -->
