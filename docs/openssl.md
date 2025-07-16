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

# OpenSSL

OpenJDK uses the built-in Java&trade; cryptographic implementation by default but Eclipse OpenJ9&trade; also provides some support for the OpenSSL cryptographic library. OpenSSL is an open source cryptographic toolkit for Transport Layer Security (TLS) and Secure Sockets Layer (SSL) protocols, which is well established and used with many enterprise applications. Because it is a native library, OpenSSL often provides better performance.

OpenSSL is bundled with Eclipse OpenJ9 and is enabled by default. The following algorithms are supported for OpenSSL:

- AES-CBC cipher
- AES-GCM cipher
- ChaCha20 cipher
- ChaCha20-Poly1305 cipher
- ECDH key agreement
- ECDSA signature
- EC key generation
- MD5 message digest
- PBE cipher
- PBKDF2 secret key factory
- RSA cipher
- SHA message digests
- XDH key agreement
- XDH key generation

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** OpenSSL 3.x and later does not support initialization vector (IV) sizes above 16 Bytes for the GCM algorithm. In earlier OpenSSL versions, you can use such sizes but they might cause unpredictable behavior. This should occur only when users are not using the default OpenSSL library that is bundled with Semeru. If you need to use a larger size, disable OpenSSL support for the GCM algorithm.

OpenSSL support is enabled by default for all supported algorithms. If you want to limit support to specific algorithms, a number of system properties are available for tuning the implementation.

Each algorithm can be disabled individually by setting the following system properties on the command line:

- To turn off all **Digests**, set [`-Djdk.nativeDigest=false`](djdknativedigest.md)

    - To turn off **MD5**, set [`-Djdk.nativeMD5=false`](djdknativemd5.md)
    - To turn off **SHA-1**, set [`-Djdk.nativeSHA=false`](djdknativesha.md)
    - To turn off **SHA-224**, set [`-Djdk.nativeSHA224=false`](djdknativesha224.md)
    - To turn off **SHA-256**, set [`-Djdk.nativeSHA256=false`](djdknativesha256.md)
    - To turn off **SHA-384**, set [`-Djdk.nativeSHA384=false`](djdknativesha384.md)
    - To turn off **SHA-512**, set [`-Djdk.nativeSHA512=false`](djdknativesha512.md)
    - To turn off **SHA-512/224**, set [` -Djdk.nativeSHA512_224=false`](djdknativesha512_224.md)
    - To turn off **SHA-512/256**, set [`-Djdk.nativeSHA512_256=false`](djdknativesha512_256.md)

- To turn off **ChaCha20** and **ChaCha20-Poly1305**, set [`-Djdk.nativeChaCha20=false`](djdknativechacha20.md). :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** ![Start of content that applies to Java 8 (LTS)](cr/java8.png) These algorithms are not supported on Java 8 ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)
- To turn off **CBC**, set [`-Djdk.nativeCBC=false`](djdknativecbc.md)
- To turn off **ECDH key agreement**, set [`-Djdk.nativeEC=false`](djdknativeec.md)
- To turn off **ECDSA signature**, set [`-Djdk.nativeECDSA=false`](djdknativeecdsa.md)
- To turn off **EC key generation**, set [`-Djdk.nativeECKeyGen=false`](djdknativeeckeygen.md)
- To turn off **GCM**, set [`-Djdk.nativeGCM=false`](djdknativegcm.md)
- To turn of **PBE cipher**, set [`-Djdk.nativePBE=false`](djdknativepbe.md)
- To turn off **PBKDF2** (Password based key derivation), set [`-Djdk.nativePBKDF2=false`](djdknativepbkdf2.md)
- To turn off **RSA**, set [`-Djdk.nativeRSA=false`](djdknativersa.md)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) To turn off **XDH key agreement**, set [`-Djdk.nativeXDHKeyAgreement=false`](djdknativexdhkeyagreement.md)
- To turn off **XDH key generation**, set [`-Djdk.nativeXDHKeyGen=false`](djdknativexdhkeygen.md) ![End of content that applies to Java 11 (LTS) and later](cr/java_close_lts.png)

You can turn off all the algorithms by setting the following system property on the command line:

```
-Djdk.nativeCrypto=false
```

You can set the `jdk.native.openssl.skipBundled` property to `true` to skip loading of the OpenSSL libraries that come with OpenJ9. The system will instead attempt to load the libraries from the system path where the libraries are expected to be available. When this value is set to `false`, the system will attempt to load the pre-packaged OpenSSL libraries. This option cannot be set in conjunction with `jdk.native.openssl.lib`.

You can use the `jdk.native.openssl.lib` property to specify user-supplied OpenSSL libraries. This option can be set to a full path name from where you would like to explicitly load the libraries instead of the bundled OpenSSL libraries. This option cannot be set in conjunction with `jdk.native.openssl.skipBundled`.

To build a version of OpenJDK with OpenJ9 that includes OpenSSL support, follow the steps in the detailed build instructions:

- [OpenJDK 8 with OpenJ9](https://github.com/eclipse-openj9/openj9/blob/master/doc/build-instructions/Build_Instructions_V8.md).
- [OpenJDK 11 with OpenJ9](https://github.com/eclipse-openj9/openj9/blob/master/doc/build-instructions/Build_Instructions_V11.md).
- [OpenJDK 17 with OpenJ9](https://github.com/eclipse-openj9/openj9/blob/master/doc/build-instructions/Build_Instructions_V17.md).
- [OpenJDK 21 with OpenJ9](https://github.com/eclipse-openj9/openj9/blob/master/doc/build-instructions/Build_Instructions_V21.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If you obtain an OpenJDK with OpenJ9 build that includes OpenSSL or build a version yourself that includes OpenSSL support, the following acknowledgments apply in accordance with the license terms:

- *This product includes software developed by the OpenSSL Project for use in the OpenSSL Toolkit. (http://www.openssl.org/).*
- *This product includes cryptographic software written by Eric Young (eay@cryptsoft.com).*


<!-- ==== END OF TOPIC ==== openssl.md ==== -->
