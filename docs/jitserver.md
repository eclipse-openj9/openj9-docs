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

For more details about JITServer technology, including its advantages and disadvantages and when best to use it, see blog posts such as the following:

- [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/)
- [JITServer - Optimize your Java cloud-native applications](https://developer.ibm.com/articles/jitserver-optimize-your-java-cloud-native-applications/)

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
- [`-XX:JITServerAOTCacheName`](xxjitserveraotcachename.md): Specifies the name of the server-side AOT cache to use
- [`-XX:[+|-]JITServerUseAOTCache`](xxjitserveruseaotcache.md): Specifies whether the server caches AOT-compiled methods

If a JITServer server crashes, the client is forced to perform compilations locally. You can change this behavior by using the [`-XX:[+|-]RequireJITServer`](xxrequirejitserver.md) option so that the client crashes with an assert when it detects that the server is unavailable. This feature is useful when you are running a test suite with JITServer enabled and you want the server crash to cause the test to fail.

## Security

You can encrypt network communication between the client VM and JITServer by using OpenSSL 1.0.x, 1.1.x, or 3.0). To enable encryption, you specify the private key and the certificate at the server and use the certificate at the client. For more information, see [-XX:JITServerSSLCert / -XX:JITServerSSLKey / -XX:JITServerSSLRootCerts](xxjitserversslcert.md).

## JITServer AOT cache

 The JITServer technology provides an optional mechanism that allows caching of AOT compiled methods at the server.
 This allows the JITServer to avoid carrying out an AOT compilation when a compatible AOT method body already exists in the cache, thereby saving CPU and improving remote compilation latency. This mechanism works in conjunction with the [dynamic AOT technology](https://www.eclipse.org/openj9/docs/aot/) at the client and therefore the client needs to have the [shared class cache](https://www.eclipse.org/openj9/docs/shrc/) (SCC) enabled (the SCC is the repository for the AOT code).

 When the JITServer receives an AOT compilation request, first it will check its AOT cache for a compatible compiled method body. In case of a cache miss, the server will perform the AOT compilation as usual, and send the response to the client JVM. Moreover, the server will serialize the compiled method and store it in its local AOT cache, for future use. In case of a cache hit, the server will directly send the client the serialized compiled method from its cache, thus avoiding a compilation. The client will deserialize the response, store the result in its local SCC and load the compiled method as regular dynamic AOT code.

 To enable this feature you must add [`-XX:+JITServerUseAOTCache`](xxjitserveruseaotcache.md) command line option, both at the server and at the client JVM.

 A JITserver instance can have several AOT caches, each with its own name. This addresses the situation when client JVMs with significantly different profiles of execution use the same JITServer instance. A client JVM can indicate a specific AOT cache it wants to use by providing its name with the following command line option [`-XX:JITServerAOTCacheName=<cache_name>`](xxjitserveraotcachename.md). The default is to use a nameless cache.

 Limitations that may be lifted in future releases:

 - Currently, there is no limit on the amount of memory an AOT cache can consume at the server. Moreover, there is no limit on the number of caches a JITServer can hold.
 - The AOT cache is a non-persistent in-memory cache. If the JITServer instance terminates, the cache content is lost.
 - There is no sharing of AOT cache entries among different JITServer instances.
 - Caching works only for AOT compilation requests. For this reason, when JITServer AOT caching is enabled, the client JVM will attempt to generate as many AOT requests as possible.

## Tuning JITServer

For best practices regarding JITServer configuration and tuning, see the document [JITServer tuning and practical considerations](jitserver_tuning.md).

## Building a JDK with JITServer technology

If you want to build a JDK with JITServer technology, see Appendix A of [Free your JVM from the JIT with JITServer Technology](https://blog.openj9.org/2020/01/09/free-your-jvm-from-the-jit-with-jitserver-technology/).

## See also

- [The JIT compiler](jit.md)



<!-- ==== END OF TOPIC ==== jitserver.md ==== -->
