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
# JITServer tuning and practical considerations

## Server caches

### Client-session caches

Multiple client JVMs can be connected at the same time to a single JIT server. For each client, the server maintains a client-session cache with information about the environment the client is running in (Java classes, class hierarchy, profiling information, JVM options, and so on). Typically, the information in these caches is kept separately per client. However, if you specify the `-XX:+JITServerShareROMClasses` option, the read-only part of the Java classes (ROMClasses in Eclipse OpenJ9&trade; parlance) is shared between the different clients. This option can generate memory savings at the server when the connected clients run identical or similar Java applications.

The client-session caches are deleted when the clients terminate, but this can happen only if the clients are shutdown gracefully, giving them the opportunity to send a termination message to the server. To address the scenario of clients ending abruptly, the server also deletes the cache for a client that hasn’t issued a compilation request for 1000 minutes, or 5 minutes under memory pressure. If needed, you can change these values with the following options:

    -Xjit:oldAge=<time-in-ms>,oldAgeUnderLowMemory=<time-in-ms>

### JITServer AOT cache

 The JITServer technology can cache AOT compiled methods at the server.
 The JITServer can, therefore, avoid carrying out an AOT compilation when a compatible AOT method body already exists in the cache, thereby saving CPU resource and improving remote compilation latency. This mechanism uses the [dynamic AOT technology](https://eclipse.dev/openj9/docs/aot/), but does not require a shared class cache at the client.<!-- and therefore the client needs to have the [shared classes cache](https://eclipse.dev/openj9/docs/shrc/) (SCC) enabled (the SCC is the repository for the AOT code) Deleted this content because from the 0.46.0 release onwards the local SCC is ignored by default -->

 When the JITServer receives an AOT compilation request, it checks its AOT cache for a compatible compiled method body. If one is not found, the server performs the AOT compilation, sends the response to the client JVM, then serializes the compiled method and stores it in its local AOT cache, for future use. If a compatible compiled method is found, the server sends the client the serialized compiled method from its cache, thus avoiding a compilation. The client deserializes the response and loads the compiled method as a regular dynamic AOT code.

 This JITServer AOT caching feature is by default enabled at the server but disabled for the JITServer clients. To enable this feature for the JITServer clients, specify the [`-XX:+JITServerUseAOTCache`](xxjitserveruseaotcache.md) command-line option for each client.

 A JITServer instance can have several AOT caches, each with its own name. These named AOT caches address the situation when client JVMs with significantly different profiles of execution use the same JITServer instance. A client JVM can indicate a specific AOT cache that it wants to use by providing its name with the following command-line option [`-XX:JITServerAOTCacheName=<cache_name>`](xxjitserveraotcachename.md). If the client doesn't specify a name for the AOT cache, the server uses a cache named `default`.

 The maximum amount of memory that all the AOT cache instances combined can use at the server is 300 MB, by default. You can change this value by using the [`-XX:JITServerAOTmx=<size>`](xxjitserveraotmx.md) option. When the cache size reaches the specified limit, new clients cannot create new AOT cache instances or add new compiled methods to the existing AOT cache instances.

 Typically, each JITServer server populates its own AOT caches independently of other existing servers. To help with JITServer auto-scaling, and in particular with scaling down to zero, JITServer instances can save their AOT caches to files by setting the [`-XX:+JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md) command-line option. Other JITServer instances that are started later can load the existing AOT cache files into their memory, and then continue to gradually add new AOT compiled methods. Saving an AOT cache to a file is performed periodically based on the following conditions:

 - The number of extra AOT methods added to the in-memory cache since the last save operation is equal to or more than the value specified by the `-Xjit:aotCachePersistenceMinDeltaMethods=<number_of_methods>` option (default value - 200 methods), and
 - The time passed since the last AOT cache save is equal to or later than the time specified by the `-Xjit:aotCachePersistenceMinPeriodMs=<milliseconds>` option (default time gap - 10000 milliseconds).

 <!-- Deleted this content because from the 0.46.0 release onwards the local SCC is ignored by default
 If the JITServer AOT cache feature and the [`-Xshareclasses:readonly`](xshareclasses.md#readonly) option are both enabled at the same time at a JITServer client, the shared classes cache startup creates a temporary new (writable) top layer that the JITServer AOT cache can use to store data that it needs to function. -->

 Current limitation:

 - Caching works only for AOT compilation requests. For this reason, when JITServer AOT caching is enabled, the client JVM will attempt to generate as many AOT requests as possible.

## Number of concurrent clients

The amount of CPU and memory resources consumed by the server is expected to increase with the number of connected clients. Finding the appropriate number of clients to connect to a server is a tricky proposition that depends on many factors, such as the number of methods that need to be compiled by the clients, optimization levels for these compilations, how clients are started (staggered or not), and how clients are shutdown (gracefully or not).

Generally, you should have 10-20 JVMs simultaneously connected to a server with 1-2 GB of memory. With respect to CPU resources, in Kubernetes you might want to set a low "request" value at the server (1-2 vCPUs) and a larger "limit" value (4-8 vCPUs) in order to soak all those idle cycles. It is possible to connect even more clients to one server instance if memory and CPU resources are increased, but in general, two medium-sized server instances that are placed on different nodes are better than a single, larger server.

## Alleviating CPU congestion at the server

When too many clients connect to the server, the server can become flooded with compilation requests, leading to increased compilation times and slower start-up/ramp-up for applications. A client JVM issues most of its compilation requests during the start-up phase and ramp-up phase of an application, when load is first applied to it. Thus, from the CPU consumption point of view what matters is the number of clients that start-up or ramp-up concurrently. To alleviate the CPU strain on the server, you can start the client JVMs in a staggered fashion, rather than all at the same time. Sometimes the staggering happens naturally; for instance, when using Kubernetes horizontal pod auto-scaling, additional application instances are started gradually as the load increases.

Another idea is to use the `-Xjit:enableJITServerHeuristics` command-line option at the clients. When this option is present, the client JVMs share some of the compilation burden by performing the cheap compilations locally and send only expensive compilations to the server. What constitutes a cheap compilation is determined by JIT heuristics that look at the method size, optimization level and the amount of CPU and memory available to the JVM.

## Avoiding memory shortages at the server

Roughly speaking, the server uses two types of memory:
1. "Scratch" memory. This memory is allocated during a compilation (for JIT internal data structures) and released to the operating system at the end of the compilation.
2. "Persistent" memory. This memory is used for client-session caches and gets deleted only when a client terminates gracefully (or when the JITServer purging mechanism is triggered).

The total amount of scratch memory at any particular moment depends on how many compilations are in progress and how expensive those compilations are. To reduce this amount, you can start the clients in a staggered fashion as suggested previously, or reduce the number of compilation threads per client. Note that the latter already happens automatically: when the server senses that it is about to run out of memory, it provides feedback to the connected clients to reduce their number of active compilation threads.

To reduce the amount of persistent memory, you can use the techniques that are described in section [Server caches](#server-caches).

## Traffic encryption

Enabling network encryption can increase the CPU overhead, both at the client and at the server. For this reason, you should turn on encryption only if needed. Note that some technologies like Istio, Weave, Linkerd, Calico, Cilium already encrypt all network traffic, so using JITServer encryption might be redundant.

## Minimizing application stalls

Usually, the compilation threads in OpenJ9 JVM execute in parallel with Java application threads. However, for correctness reasons a small number of compilations are performed synchronously, meaning that Java application threads have to wait for the compilation result before being allowed to execute the method being compiled. Since remote compilations typically take longer to complete due to network latency, application stalls that are caused by synchronous compilations can be more severe in a JITServer setting. If this becomes a problem, you should add the following command line option at the client:

    -XX:+JITServerLocalSyncCompiles

This option instructs the client JVM to perform the synchronous compilations locally, at a low optimization level (thus the compilation is relatively quick), and to follow-on with remote asynchronous recompilations at a higher optimization level to avoid any performance loss.

## Session affinity

For technical reasons, a client JVM must use a single JITServer at a time. In a Kubernetes environment, where a JITServer service can be backed up by several server instances, you can satisfy this requirement by using session affinity. Note that if a server crashes (or gets terminated by the Kubernetes controller) the clients can connect to another server instance. This scenario imposes some performance penalty because the client-session caches that the server maintains need to be built anew. Following is an example of a Kubernetes service definition that uses sessionAffinity:

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

If the client JVM does not find a compatible server to connect to, compilations are performed locally, by the client itself. To account for the case where the server is temporarily unavailable (for example, server crash followed by Kubernetes launching another server instance), from time to time the client retries to connect to a server at the indicated address and port. The retry mechanism uses an exponential back-off where the retry interval is doubled with each unsuccessful attempt.

## Monitoring

### Performance metrics

You can enable the provision of performance metrics by specifying the `-XX:+JITServerMetrics` command-line option. After enabling this option, you can use a monitoring tool that follows the OpenMetrics standard, such as Prometheus, to collect the data by issuing an HTTP `GET` request to the following url: `http://<jitserveraddress>:<port>/metrics`.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** There is a limit of four concurrent `GET` requests at any given time.

You can use the [`-XX:JITServerMetricsSSLKey`](xxjitservermetricssslkey.md) and [`-XX:JITServerMetricsSSLCert`](xxjitservermetricssslkey.md) options to encrypt the data with TLS or SSL.

For more information, including the types of metrics that are provided, see the [`-XX:[+|-]JITServerMetrics`](xxjitservermetrics.md) topic.

### Verbose logging

You can inspect the behavior of a JITServer instance by using the [OpenJ9 verbose logging facility](https://blog.openj9.org/2018/06/07/reading-verbose-jit-logs/). Note that if the name of the verbose log is not specified, the relevant information is printed to stderr.
When you use the `-XX:+JITServerLogConnections` command-line option, the server prints a message to the verbose log every time a new client JVM connects to it or disconnects from it. This is an easy way to determine that the clients are able to reach the server. Example of output:
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
A value greater than 0 for the `Compilation Queue Size` is a sign that the server is overloaded. Compilation requests that wait in the compilation queue face greater delays and run the risk of exceeding network timeouts. To avoid this scenario, you can reduce the number of connected clients, use the techniques that are described in section [Alleviating CPU congestion at the server](#alleviating-CPU-congestion-at-the-server), or increase the number of compilation threads at the server by using the [`-XcompilationThreads`](xcompilationthreads.md) option.

Increasing the maximum number of client threads can improve performance in high network latency settings because there can be more in-progress concurrent compilation requests. Increasing the number of threads at the server can improve performance if the server has many CPU cores available and serves a large number of clients concurrently.

More detailed diagnostics can be obtained with the option `-Xjit:verbose={JITServer},verbose={compilePerformance}`, which is typically used for debugging server behavior.

<!-- ==== END OF TOPIC ==== jitservertuning.md ==== -->
