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

# -XX:\[+|-\]ReadIPInfoForRAS


Use this command-line option to enable and disable network queries from being used to determine the host name and IP address for RAS (reliability, availability, and serviceability) troubleshooting purposes.

## Syntax

        -XX:[+|-]ReadIPInfoForRAS

| Setting                 | Effect  | Default                                                                            |
|-------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+ReadIPInfoForRAS` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |
| `-XX:-ReadIPInfoForRAS` | Disable |                                                                                    |

Eclipse OpenJ9&trade; captures the host name and IP address by default, for use in diagnosing problems. But if a nameserver cannot be contacted when a network query is made, the program will wait until the resolver times out.

You can avoid this situation by using the `-XX:-ReadIPInfoForRAS` command-line option to prevent the query from being performed.

<!-- ==== END OF TOPIC ==== xxreadipinfoforras.md ==== -->
