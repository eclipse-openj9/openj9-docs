<!--
* Copyright (c) 2021, 2021 IBM Corp. and others
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

# -XX:JITServerSSLRootCerts

Set SSL root certificate on the JITServer client (required for enabling network traffic encryption). The certificate needs to be in a `.pem` file format.

For encrypted communication to work, server must also specify [-XX:JITServerSSLCert](xxjitserversslcert.md) and [-XX:JITServerSSLKey](xxjitserversslkey.md).
Otherwise the client will not be able to connect to server and will be forced to perform
all compilations locally.

## Syntax

        -XX:JITServerSSLRootCerts=<root_certs_File>

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:JITServerSSLRootCerts`           | Set client's SSL root certificate | None                                                                                    |
<!-- ==== END OF TOPIC ==== xxjitserversslrootcerts.md ==== -->