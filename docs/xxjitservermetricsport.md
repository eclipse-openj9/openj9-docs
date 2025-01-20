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

# -XX:JITServerMetricsPort

This option specifies the port number on which the JITServer metrics are provided to a monitoring agent.

Use this option with the `-XX:+JITServerMetrics` option, which enables the provision of the JITServer metrics.

## Syntax

        -XX:JITServerMetricsPort=<port>

| Setting                   | Effect                         | Default |
|---------------------------|--------------------------------|:-------:|
|`-XX:JITServerMetricsPort` | Set JITServer port for metrics | 38500   |


## See also

- [-XX:[+|-]JITServerMetrics](xxjitservermetrics.md)
- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxjitservermetricsport.md ==== -->
