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

# -XX:JITServerMetricsSSLKey / -XX:JITServerMetricsSSLCert

These options specify the names of the files that contain the private TLS or SSL key and certificate that are used for authentication and encryption of the custom metrics.

## Syntax

        -XX:JITServerMetricsSSLKey=<key_file>
        -XX:JITServerMetricsSSLCert=<cert_file>

Where `<key_file>` specifies the name of the file that contains the private TLS or SSL key and `<cert_file>` specifies the name of the file that contains the private TLS or SSL certificate.
The files must all be in `.pem` file format.

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|` -XX:JITServerMetricsSSLKey`           | Set metrics SSL key | None                                                                                    |
|`-XX:JITServerMetricsSSLCert`           | Set metrics SSL certificate | None                                                                                    |

## Explanation

Custom metrics are exported by the JITServer server and collected by a monitoring agent, such as Prometheus. The exported data is sent in clear text by default. To secure this data with TLS or SSL authentication and encryption, specify the private key (`<key>.pem`) and the certificate (`<cert>.pem`) at the server:

        -XX:JITServerMetricsSSLKey=<key>.pem -XX:JITServerMetricsSSLCert=<cert>.pem

You must specify both the options for TLS or SSL authentication and encryption.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** To enable the export of custom metrics, you must specify the [`-XX:[+|-]JITServerMetrics`](xxjitservermetrics.md) option.

## See also

- [-XX:[+|-]JITServerMetrics](xxjitservermetrics.md)
- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxjitservermetricssslkey.md ==== -->
