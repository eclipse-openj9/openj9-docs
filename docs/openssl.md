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

OpenJDK uses the in-built Java&trade; cryptographic implementation by default but Eclipse OpenJ9&trade; also provides some support for the OpenSSL cryptographic library. OpenSSL is an open source cryptographic toolkit for Transport Layer Security (TLS) and Secure Sockets Layer (SSL) protocols, which is well established and used with many enterprise applications. Because it is a native library, OpenSSL might provide better performance. To use OpenSSL cryptographic acceleration, install OpenSSL 1.0.x, 1.1.x, or 3.x on your system. The OpenSSL V1.0.x, V1.1.x, and V3.x implementations are currently supported for the Digest, CBC, GCM, RSA, ECDH key agreement, and EC key generation algorithms. The OpenSSL V1.1.x and V3.x implementations are also supported for the ChaCha20 and ChaCha20-Poly1305 algorithms. The OpenSSL V1.1.1 onwards implementations are supported for the XDH key agreement and XDH key generation algorithms.

On Linux&reg; and AIX&reg; operating systems, the OpenSSL 1.0.x, 1.1.x, or 3.x library is expected to be found on the system path. If you use a package manager to install OpenSSL, the system path will be updated automatically. On Windows&trade; and MacOS&reg; the OpenSSL 3.x library is bundled. Later levels of some Linux operating systems also bundle OpenSSL 3.x.

If you have multiple versions of OpenSSL on your system, the OpenJ9 VM uses the latest version.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** OpenSSL 3.x does not support initialization vector (IV) sizes above 16 Bytes for the GCM algorithm. (In earlier OpenSSL versions, you can use such sizes but they might cause unpredictable behavior.) If you need to use a larger size, disable OpenSSL support for the GCM algorithm.

OpenSSL support is enabled by default for all supported algorithms. If you want to limit support to specific algorithms, a number of system properties are available for tuning the implementation.

Each algorithm can be disabled individually by setting the following system properties on the command line:


- To turn off **Digest**, set `-Djdk.nativeDigest=false`
- To turn off **ChaCha20** and **ChaCha20-Poly1305**, set `-Djdk.nativeChaCha20=false`. :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** ![Start of content that applies to Java 8 (LTS)](cr/java8.png) These algorithms are not supported on Java 8 ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)
- To turn off **CBC**, set `-Djdk.nativeCBC=false`
- To turn off **GCM**, set `-Djdk.nativeGCM=false`
- To turn off **RSA**, set `-Djdk.nativeRSA=false`
- To turn off **ECDH key agreement**, set `-Djdk.nativeEC=false`
- To turn off **EC key generation**, set `-Djdk.nativeECKeyGen=false`
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) To turn off **XDH key agreement**, set `-Djdk.nativeXDHKeyAgreement=false`
- To turn off **XDH key generation**, set `-Djdk.nativeXDHKeyGen=false` ![End of content that applies to Java 11 (LTS) and later](cr/java_close_lts.png)

You can turn off all the algorithms by setting the following system property on the command line:

```
-Djdk.nativeCrypto=false
```

To build a version of OpenJDK with OpenJ9 that includes OpenSSL support, follow the steps in our detailed build instructions:

- [OpenJDK 8 with OpenJ9](https://github.com/eclipse-openj9/openj9/blob/master/doc/build-instructions/Build_Instructions_V8.md).
- [OpenJDK 11 with OpenJ9](https://github.com/eclipse-openj9/openj9/blob/master/doc/build-instructions/Build_Instructions_V11.md).
- [OpenJDK 17 with OpenJ9](https://github.com/eclipse-openj9/openj9/blob/master/doc/build-instructions/Build_Instructions_V17.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If you obtain an OpenJDK with OpenJ9 build that includes OpenSSL or build a version yourself that includes OpenSSL support, the following acknowledgments apply in accordance with the license terms:

- *This product includes software developed by the OpenSSL Project for use in the OpenSSL Toolkit. (http://www.openssl.org/).*
- *This product includes cryptographic software written by Eric Young (eay@cryptsoft.com).*


<!-- ==== END OF TOPIC ==== openssl.md ==== -->
