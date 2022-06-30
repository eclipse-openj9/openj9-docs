<!--
* Copyright (c) 2022, 2022 IBM Corp. and others
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

# -XX:\[+|-\]JITServerMetrics

This option enables or disables the JITServer custom metrics exporter.

## Syntax

        -XX:[+|-]ITServerMetrics

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+JITServerMetrics`  | Enable |                                                                                    |
|`-XX:-JITServerMetrics`  | Disable| :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

When you enable this option, the JITServer process will open a TCP port which can be used by monitoring agents (like Prometheus) to scrape custom metrics exported by JITServer. Monitoring agents need to issue http `GET` requests to an url of the form: `http://jitserveraddress:metricsport/metrics`.
The default value of the `metricsport` is 38500 and can changed with [-XX:JITServerMetricsPort=<NNN>](xxjitservermetricsport.md) command line option.
The path for the http request needs to be `/metrics`.
The exposition format for the metrics returned by JITServer follows the [OpenMetrics](https://openmetrics.io/) protocol.


Example of a recognized HTTP request:

    GET /metrics HTTP/1.1
    Host: 127.0.0.1:9403
    User-Agent: Prometheus/2.31.1
    Accept: application/openmetrics-text; version=0.0.1,text/plain;version=0.0.4;q=0.5,*/*;q=0.1
    Accept-Encoding: gzip
    X-Prometheus-Scrape-Timeout-Seconds: 3

Example of a metrics response sent back:

    # HELP cpu_utilization Cpu utilization of the JITServer
    # TYPE cpu_utilization gauge
    jitserver_cpu_utilization 12.000000
    # HELP available_memory Available memory for JITServer
    # TYPE available_memory gauge
    jitserver_available_memory 32036204544.000000
    # HELP connected_clients Number of connected clients
    # TYPE connected_clients gauge
    jitserver_connected_clients 1.000000
    # HELP active_threads Number of active compilation threads
    # TYPE active_threads gauge
    jitserver_active_threads 1.000000


## See also

- [JITServer technology](jitserver.md)
- [-XX:JITServerMetricsPort=](xxjitservermetricsport.md)

<!-- ==== END OF TOPIC ==== xxjitservermetrics.md ==== -->