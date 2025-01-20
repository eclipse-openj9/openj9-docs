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

# -XX:\[+|-\]JITServerLogConnections

This option enables the logging of connection/disconnection events between the JITServer server and the JITServer client. You can use the option on both the server and the client sides.

## Syntax

        -XX:[+|-]JITServerLogConnections

| Setting                 | Effect | Default                                                                            |
|-------------------------|--------|:----------------------------------------------------------------------------------:|
|`-XX:+JITServerLogConnections`           | Enable |                                                                                    |
|`-XX:-JITServerLogConnections`           | Disable| :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

This option is useful when you need to know when the server and the client successfully establish or terminate connections but verbose logs provide too much information.

You can also enable the same logging by specifying the  `-Xjit:verbose={JITServerConns}` option.

If you do not specify a `vlog` log file (`-Xjit:vlog=<vlog_filename>`), output is written to `stderr`, otherwise it is written to the `vlog` file.

## Example

This is what the typical output looks like:

On the **server** side:

```
#JITServer: t=  2318 A new client (clientUID=11937826481210274991) connected. Server allocated a new client session.
...
...
#JITServer: t= 48518 Client (clientUID=4213447851416537492) disconnected. Client session deleted
```

On the **client** side:

```
#JITServer: t=     0 Connected to a server (serverUID=10444660844386807777)
...
...
#JITServer: t=   698 Lost connection to the server (serverUID=10444660844386807777)
```
## See also

- [JITServer technology](jitserver.md)
- [-Xjit / -Xnojit](xjit.md)

<!-- ==== END OF TOPIC ==== xxjitserverlogconnections.md ==== -->
