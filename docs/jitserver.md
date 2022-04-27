<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# JITServer technology

**Linux&reg; on x86, Linux on IBM Power&reg; systems, and Linux on IBM Z&reg; systems (64-bit only)**

JITServer technology decouples the JIT compiler from the VM and lets the JIT compiler run remotely in its own process. This mechanism prevents your Java&trade; application suffering possible negative effects due to CPU and memory consumption caused by JIT compilation.

This technology can improve quality of service, robustness, and performance of Java applications. You might want to try this technology if the following criteria are met:

- Your Java application is required to compile many methods using JIT in a relatively short time.
- The application is running in an environment with limited CPU or memory, which can worsen interference from the JIT compiler.
- The network latency between JITServer and client VM is relatively low.

For more details about JITServer technology, its pros and cons, and when best to use it, see the blog post [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/).

## Using JITServer technology

JITServer technology is not enabled by default: you must explicitly invoke it. Running OpenJ9 without either of the following options launches it as a regular VM with embedded JIT compilation.

### Launch OpenJ9 in client mode

Use the following command-line option to launch OpenJ9 in client mode. In this mode, the VM sends compilation requests to an available JITServer. The client operates as a regular VM with its own JIT compiler if a server is not available.

     -XX:+UseJITServer

### Launch OpenJ9 in server mode

Use the following command to start a JITServer process that listens for incoming compilation requests:

    jitserver

## Configuring JITServer technology

You can use command line options to further configure the JITServer and the client VM processes. For example:

- [`-XX:JITServerPort=<port>`](xxjitserverport.md): Specifies the port the server listens to for compilation requests
- [`-XX:JITServerAddress=<address>`](xxjitserveraddress.md): Specifies the name or IP of the server
- [`-XX:JITServerTimeout=<timeout>`](xxjitservertimeout.md): Specifies a timeout value in milliseconds for socket operations
- [`-XX:[+|-]JITServerShareROMClasses`](xxjitservershareromclasses.md): Specifies whether the server shares cached ROM classes between clients
- [`-XX:[+|-]JITServerLocalSyncCompiles`](xxjitserverlocalsynccompiles.md): Improves performance for real-time applications by compiling synchronous JIT compilations locally, with a remote asynchronous recompilation scheduled at a later point
- [`-XX:[+|-]JITServerLogConnections`](xxjitserverlogconnections.md): Enables logging of connection/disconnection events between the server and the client

If a JITServer server crashes, the client is forced to perform compilations locally. You can change this behavior by using the [`-XX:[+|-]RequireJITServer`](xxrequirejitserver.md) option so that the client crashes with an assert when it detects that the server is unavailable. This feature is useful when you are running a test suite with JITServer enabled and you want the server crash to cause the test to fail.

## Security

You can encrypt network communication between the client VM and JITServer by using OpenSSL 1.0.x or 1.1.x (JITServer technology currently does not support OpenSSL 3.0.x). To enable encryption, you specify the private key and the certificate at the server and use the certificate at the client. For more information, see [-XX:JITServerSSLCert / -XX:JITServerSSLKey / -XX:JITServerSSLRootCerts](xxjitserversslcert.md).

## Tuning JITServer

For best practices regarding JITServer configuration and tuning, please see the document [JITServer tuning and practical considerations](jitserver_tuning.md).

## Building a JDK with JITServer technology

If you want to build a JDK with JITServer technology, see Appendix A of [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/).

## See also

- [The JIT compiler](jit.md)



<!-- ==== END OF TOPIC ==== jitserver.md ==== -->
