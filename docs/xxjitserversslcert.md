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

# -XX:JITServerSSLCert / -XX:JITServerSSLKey / -XX:JITServerSSLRootCerts

Options for encrypting network communication between JITServer servers and JITServer client VMs.

## Syntax

        -XX:JITServerCert=<cert_file>
        -XX:JITServerKey=<key_file>
        -XX:JITServerSSLRootCerts=<root_certs_file>

The files must all be in `.pem` file format.

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:JITServerSSLCert`           | Set server's SSL certificate | None                                                                                    |
|`-XX:JITServerSSLKey`           | Set server's SSL key | None                                                                                    |
|`-XX:JITServerSSLRootCerts`           | Set client's SSL root certificate | None                                                                                    |

## Explanation

You can encrypt network communication by using OpenSSL 1.0.x, 1.1.x, or 3.x. To enable encryption, specify the private key (`<key>.pem`) and the certificate (`<cert>.pem`) at the server:

    -XX:JITServerSSLKey=<key>.pem -XX:JITServerSSLCert=<cert>.pem

and use the certificate at the client:

    -XX:JITServerSSLRootCerts=<cert>.pem

You must specify all three options for the client to be able to connect to the server. If the client cannot connect, it is forced to perform all compilations locally instead.

For more details and further discussion about security considerations, see the blog post [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/).

## See also

- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxjitserversslcert.md ==== -->
