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

# -XX:JITServerTimeout

This option specifies the socket timeout for JITServer communication.

You can specify this option on both the server and the client sides.

## Syntax

        -XX:JITServerTimeout=<timeout_ms>

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:JITServerTimeout`           | Set the timeout value in milliseconds for socket operations | 30000 ms for the JITServer process and 10000 ms when Eclipse OpenJ9&trade; is launched as a client VM                                                                                    |


## See also

- [JITServer technology](jitserver.md)

<!-- ==== END OF TOPIC ==== xxjitservertimeout.md ==== -->
