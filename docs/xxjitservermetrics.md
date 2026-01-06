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

# -XX:[+|-]JITServerMetrics

This option enables the provision of JITServer performance metrics to a monitoring agent that follows the OpenMetrics standard.

When you enable this option, the following JITServer metrics are provided:

- The CPU utilization of the JITServer
- The physical memory that is available to the JITServer
- The number of clients that are connected to the JITServer
- The number of active compilation threads in the JITServer

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** You can use the [`-XX:JITServerMetricsSSLKey`](xxjitservermetricssslkey.md) and [`-XX:JITServerMetricsSSLCert`](xxjitservermetricssslkey.md) options for encrypting the data with SSL or TLS.

## Syntax

        -XX:[+|-]JITServerMetrics

| Setting                    | Effect  | Default                                                                              |
|----------------------------|---------|:------------------------------------------------------------------------------------:|
|`-XX:+JITServerMetrics` | Enable  |                                                                                      |
|`-XX:-JITServerMetrics` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

 When you enable this option, the JITServer process opens a TCP port, which can be used by monitoring agents (like Prometheus) to collect the custom metrics exported by the JITServer. Monitoring agents must issue HTTP `GET` requests to a URL of the form: `http://<jitserveraddress>:<port>/metrics`. The path for the HTTP request must be `/metrics`.

 The default value of `<port>` is 38500. You can change this value by using the [`-XX:JITServerMetricsPort`](xxjitservermetricsport.md) command line option.

 The format for the metrics returned by the JITServer follows the [OpenMetrics](https://openmetrics.io/) protocol.

## Example

The following example shows an HTTP request from a [Prometheus](https://prometheus.io/docs/introduction/overview/) monitoring agent and the response from the JITServer:

HTTP request:

   ```
   GET /metrics HTTP/1.1
   Host: 127.0.0.1:9403
   User-Agent: Prometheus/2.31.1
   Accept: application/openmetrics-text; version=0.0.1,text/plain;version=0.0.4;q=0.5,*/*;q=0.1
   Accept-Encoding: gzip
   X-Prometheus-Scrape-Timeout-Seconds: 3
   ```

JITServer response:

   ```
   # HELP jitserver_cpu_utilization Cpu utilization of the JITServer
   # TYPE jitserver_cpu_utilization gauge
   jitserver_cpu_utilization 12.000000
   # HELP jitserver_available_memory Available memory for JITServer
   # TYPE jitserver_available_memory gauge
   jitserver_available_memory 32036204544.000000
   # HELP jitserver_connected_clients Number of connected clients
   # TYPE jitserver_connected_clients gauge
   jitserver_connected_clients 1.000000
   # HELP jitserver_active_threads Number of active compilation threads
   # TYPE jitserver_active_threads gauge
   jitserver_active_threads 1.000000
   ```

## See also

- [-XX:JITServerMetricsPort](xxjitservermetricsport.md)
- [JITServer technology](jitserver.md)
- [`-XX:JITServerMetricsSSLKey`](xxjitservermetricssslkey.md)

<!-- ==== END OF TOPIC ==== xxjitservermetrics.md ==== -->
