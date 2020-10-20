<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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
* [2] http://openjdk.java.net/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->

# JITServer technology (technical preview)

![Start of content that applies to Java 8 and later](cr/java8.png)
![Start of content that applies to Java 11 and later](cr/java11.png)  
**Linux&reg; on x86, Linux on IBM Power&reg; systems, and Linux on IBM Z&reg; systems (64-bit only)**

JITServer technology decouples the JIT compiler from the VM and lets the JIT compiler run remotely in its own process. This mechanism prevents your Java&trade; application suffering possible negative effects due to CPU and memory consumption caused by JIT compilation.

This technology can improve quality of service, robustness, and even performance of Java applications. We recommend trying this technology if the following criteria are met:

- Your Java application is required to compile many methods using JIT in a relatively short time.
- The application is running in an environment with limited CPU or memory, which can worsen interference from the JIT compiler.
- The network latency between JITServer and client VM is relatively low.

For more details about JITServer technology, its pros and cons, and when best to use it, see the blog [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/).

## Using JITServer technology

JITServer technology is not enabled by default: you must explicitly invoke it. Running OpenJ9 without either of the following options launches it as a regular VM with embedded JIT compilation.

### Launch OpenJ9 in client mode

Use the following command-line option to launch OpenJ9 in client mode. In this mode, the VM sends compilation requests to an available JITServer. The client operates as a regular VM with its own JIT compiler if a server is not available.

     -XX:+UseJITServer

### Launch OpenJ9 in server mode

Use the following command to start a JITServer process that listens for incoming compilation requests:

    jitserver

## Configuring JITServer technology

You can use the following command line options to further configure the JITServer and the client VM processes:

|  Setting                         | Effect                                                            | Default     |
|----------------------------------|-------------------------------------------------------------------|-------------|
| `-XX:JITServerPort=<port>`       | Specifies the port the server listens to for compilation requests | `38400`     |
| `-XX:JITServerAddress=<address>` | Specifies the name or IP of the server                            | `localhost` |
| `-XX:JITServerTimeout=<timeout>` | Specifies a timeout value in milliseconds for socket operations   |(**Note 1**) |

**Note 1:** The timeout default is 30000 ms for the JITServer process, and 2000 ms when OpenJ9 is launched as a client VM. You might need to increase the latter value if network latency is large.

## Security issues

You can encrypt network communication between the client VM and JITServer by using OpenSSL 1.0.x or 1.1.x. To enable encryption, specify the private key (`<key>.pem`) and the certificate (`<cert>.pem`) at the server:

    -XX:JITServerSSLKey=<key>.pem -XX:JITServerSSLCert=<cert>.pem

and use the certificate at the client:

    -XX:JITServerSSLRootCerts=<cert>.pem

For more details and further discussion about security considerations, see [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/).

## Building an SDK with JITServer technology

If you want to build a JDK with JITServer technology for yourself, see Appendix A of [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/).

## See also

- [The JIT compiler](jit.md)



<!-- ==== END OF TOPIC ==== jitserver.md ==== -->
