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
# JITServer tuning and practical considerations

## Server caches
Multiple client JVMs can be connected at the same time to a single JIT server. For each client, the server maintains a client-session cache with information about the environment the client is running in (Java classes, class hierarchy, profiling information, JVM options, etc.). Typically, the information in these caches is kept separately, per client. However, if you specify the `-XX:+JITServerShareROMClasses` option, the read-only part of the Java classes (ROMClasses in OpenJ9 parlance) is shared between the different clients. This option can generate memory savings at the server when the connected clients run identical or similar Java applications.

The client-session caches are deleted when the clients terminate, but this can happen only if the clients are shutdown gracefully, giving them the opportunity to send a termination message to the server. To address the scenario of clients ending abruptly, the server also deletes the cache for a client that hasn’t issued a compilation request for 1000 minutes, or 5 minutes under memory pressure. If needed, you can change these values with the following options:

    -Xjit:oldAge=<time-in-ms>,oldAgeUnderLowMemory=<time-in-ms>

## Number of concurrent clients

The amount of CPU and memory resources consumed by the server is expected to increase with the number of connected clients. Finding the appropriate number of clients to connect to a server is a tricky proposition that depends on many factors: number of methods that need to be compiled by the clients, optimization levels for these compilations, how clients are started (staggered or not), how clients are shutdown (gracefully or not), etc.

As a rule of thumb, you should have 10-20 JVMs simultaneously connected to a server with 1-2 GB of memory. With respect to CPU resources, in Kubernetes you might want to set a low "request" value at the server (1-2 vCPUs) and a larger "limit" value (4-8 vCPUs) in order to soak all those idle cycles. It is possible to connect even more clients to one server instance if memory and CPU resources are increased, but in general, two medium-sized server instances placed on different nodes are better than a single, larger server.

## Alleviating CPU congestion at the server

When too many clients connect to the server, the server can become flooded with compilation requests, leading to increased compilation times and slower start-up/ramp-up for applications. It should be noted that a client JVM issues most of its compilation requests during the start-up phase and ramp-up phase of an application, when load is first applied to it. Thus, from the CPU consumption point of view what matters is the number of clients that start-up or ramp-up concurrently. To alleviate the CPU strain on the server, you can start the client JVMs in a staggered fashion, rather than all at the same time. Sometimes the staggering happens naturally; for instance, when using Kubernetes horizontal pod auto-scaling, additional application instances are launched gradually as the load increases.

Another idea is to use the `-Xjit:enableJITServerHeuristics` command line option at the clients. When this option is present, the client JVMs share some of the compilation burden by performing the cheap compilations locally and send only expensive compilations to the server. What constitutes a cheap compilation is determined by JIT heuristics that look at the method size, optimization level and the amount of CPU and memory available to the JVM.

## Avoiding memory shortages at the server

Roughly speaking, the server uses two types of memory:
1. "Scratch" memory. This is allocated during a compilation (for JIT internal data structures) and released to the operating system at the end of the compilation.
2. "Persistent" memory. This is used for client-session caches and only gets deleted when a client terminates gracefully (or when JITServer purging mechanism is triggered).

The total amount of scratch memory at any given moment depends on how many compilations are in progress and how expensive those compilations are. To reduce this amount, you can start the clients in a staggered fashion as suggested above, or reduce the number of compilation threads per client. Note that the latter already happens automatically: when the server senses that it is about to run out of memory, it provides feedback to the connected clients to reduce their number of active compilation threads.

To reduce the amount of persistent memory you can use the techniques described in section [Server caches](#server-caches).

## Traffic encryption

Enabling network encryption can increase the CPU overhead, both at the client and at the server. For this reason, you should turn on encryption only if needed. Note that some technologies like Istio, Weave, Linkerd, Calico, Cilium already encrypt all network traffic, so using JITServer encryption might be redundant.

## Minimizing application stalls

For the most part, the compilation threads in OpenJ9 JVM execute in parallel with Java application threads. However, for correctness reasons a small number of compilations are performed synchronously, meaning that Java application threads have to wait for the compilation result before being allowed to execute the method being compiled. Since remote compilations typically take longer to complete due to network latency, application stalls caused by synchronous compilations can be more severe in a JITServer setting. If this becomes a problem, you should add the following command line option at the client:

    -XX:+JITServerLocalSyncCompiles

This option instructs the client JVM to perform the synchronous compilations locally, at a low optimization level (thus the compilation is relatively quick), and to follow-on with remote asynchronous recompilations at a higher optimization level to avoid any performance loss.

## Session affinity

For technical reasons, a client JVM must use a single JITServer at a time. In a Kubernetes environment, where a JITServer service can be backed up by several server instances, you can satisfy this requirement by using session affinity. Note that if a server crashes (or gets terminated by the Kubernetes controller) the clients can connect to another server instance. This scenario imposes some performance penalty because the client-session caches that the server maintains need to be built anew. Below we show an example of a Kubernetes service definition that uses sessionAffinity:

```
apiVersion: v1
kind: Service
metadata:
  name: jitserver
spec:
  type: ClusterIP
selector:
    app: jitserver
  ports:
    - protocol: TCP
      port: 38400
      targetPort: 38400
  sessionAffinity: ClientIP
  sessionAffinityConfig:
    clientIP:
      timeoutSeconds: 86400
```

## Resilience

If the client JVM does not find a compatible server to connect to, compilations are performed locally, by the client itself. To account for the case where the server is temporarily unavailable (e.g, server crash followed by Kubernetes launching another server instance), from time to time the client retries to connect to a server at the indicated address and port. The retry mechanism uses an exponential back-off where the retry interval is doubled with each unsuccessful attempt.

## Monitoring

There are several ways to inspect the behavior of a JITServer instance, but all are based on the [OpenJ9 verbose logging facility](https://blog.openj9.org/2018/06/07/reading-verbose-jit-logs/). Note that if the name of the verbose log is not specified, the relevant information is printed to stderr.
When you use the `-XX:+JITServerLogConnections` command line option, the server prints a message to the verbose log every time a new client JVM connects to it or disconnects from it. This is an easy way to determine that the clients are able to reach the server. Example of output:
```
#JITServer: t= 74232 A new client (clientUID=14692403771747196083) connected. Server allocated a new client session.
#JITServer: t= 74282 A new client (clientUID=2599593246759846167) connected. Server allocated a new client session.
#JITServer: t= 86281 Client (clientUID=14692403771747196083) disconnected. Client session deleted
```

The server has a heart-beat thread that periodically prints to the verbose log information related to the number of clients connected, the number of active compilation threads, the amount of CPU used, the amount of available memory and the number of times the internal server caches have been cleared. This last bit of information is important for diagnosing performance problems. The heart-beat information is enabled with the following option:

    -Xjit:statisticsFrequency=<period-in-ms>

Example of output:
```
#JITServer: CurrentTime: Aug 06 17:25:15 2021
#JITServer: Compilation Queue Size: 0
#JITServer: Number of clients : 2
#JITServer: Total compilation threads : 63
#JITServer: Active compilation threads : 2
#JITServer: Physical memory available: 14299 MB
#JITServer: CpuLoad 206% (AvgUsage 25%) JvmCpu 113%
...
```
A value greater than 0 for the `Compilation Queue Size` is a sign that the server is overloaded. Compilation requests that wait in the compilation queue face greater delays and run the risk of exceeding network timeouts. To avoid this scenario, you can reduce the number of connected clients, use the techniques described in section [Alleviating CPU congestion at the server](#alleviating-CPU-congestion-at-the-server) or increase the number of compilation threads at the server with the following option:

    -XcompilationThreads<N> (default is 63)

More detailed diagnostics can be obtained with the option `-Xjit:verbose={JITServer},verbose={compilePerformance}` which is typically used for debugging server behavior.

<!-- ==== END OF TOPIC ==== jitservertuning.md ==== -->
