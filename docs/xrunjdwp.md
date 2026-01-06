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

# -Xrunjdwp

The `-Xrunjdwp` option enables the Java&trade; Debug Wire Protocol (JDWP) agent in the VM. The JDWP agent provides a connection point for the debugger to debug a Java application in that VM.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** You can enable the JDWP support on Checkpoint/Restore In Userspace (CRIU) restore by specifying the following command-line options pre-checkpoint:

- `-Xrunjdwp`
- `-agentlib:jdwp`
- `-agentpath:/path/to/libjdwp.so`

<!-- - In OpenJ9, instead of specifying `-Xrunjdwp`, `-agentlib:jdwp`, or `-agentpath:/path/to/libjdwp.so` as command-line options pre-checkpoint, you can add these command-line options in a restore option file and specify it by using the `CRIUSupport.registerRestoreOptionsFile` API when you restore a checkpoint image. [To be added in the next release when the -XX:debugOnRestore option is used in liberty]-->

## Parameters

These parameters are applicable for both the `-Xrunjdwp` and `-agentlib:jdwp` options.

### `address`

        -Xrunjdwp:address=<value>

: Specifies the *transport* address for the connection. If [`server=y`](#server) and `address` is specified, the VM listens for a connection at this address. If `server=y` and `address` is not specified, the VM chooses a [`transport`](#transport) address to listen for a debugger application. If `server=n`, the `address` parameter is mandatory. The VM attempts to attach to the debugger application at the specified address.

### `help`

        -Xrunjdwp:help

: Prints a brief help message that describes all the available options for the `-Xrunjdwp` option and exits the VM.

### `launch`

        -Xrunjdwp:launch=<path>

: Starts the debugger process from the specified path. The values of the `address` and `transport` parameters, if available, are also added to the specified path. These parameters help the debugger to connect to the target VM. This option is used with `onthrow`, `onuncaught` or both the parameters to provide *Just-In-Time debugging* in which a debugger process is started when a particular event occurs in this VM.

### `onthrow`

        -Xrunjdwp:onthrow=<exception>

: Delays the debugging process until the specified exception is thrown in this VM. The JDWP agent attaches to the VM and starts debugging when the exception is thrown.

### `onuncaught`

        -Xrunjdwp:onuncaught=[y|n]

: If `onuncaught=y`, delays the debugging process until an uncaught exception is thrown in this VM.

### `server`

        -Xrunjdwp:server=[y|n]

: If `server=n`, the VM does not listen to any incoming debugger connection and it attempts to attach to the local debugger application at the specified [`address`](#address). In this case, specifying the`address` parameter is mandatory. If `server=y`, the target VM acts as a server listening for an incoming debugger connection through the JDWP agent. If `server=y` and `address` is specified, the VM listens for a debugger connection at this address. If `server=y` and `address` is not specified, the VM selects a [`transport`](#transport) address at which to listen for a debugger application.

### `suspend`

        -Xrunjdwp:suspend=[y|n]

: If `suspend=y`, the VM suspends running of the target Java application until the debugger is attached successfully and is ready for debugging. If `suspend=n`, the VM does not suspend running of the target Java application while the debug connection is established. The VM continues running the application as normal. The debugger can attach to the running application at any time.

: When a debug session is restored from a checkpoint, the VM suspends the running of the Java application that was being debugged even if `suspend=n`. The VM waits for the debugger to send a `Resume` request before it restarts the Java application to continue the debug session. When the debug session is complete, the debugger disconnects from the JDWP agent, and the agent resumes normal running of the Java application.

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) `suspendOnRestore`

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

`suspendOnRestore` is an OpenJ9 specific parameter and is similar to the `suspend` parameter. But unlike `suspend`, this parameter only affects a VM on restore.

        -Xrunjdwp:suspendOnRestore=[y|n]

: If `suspendOnRestore=y`, the VM suspends running of the target Java application until the debugger is attached successfully and is ready for debugging on CRIU restore. If `suspendOnRestore=n`, the VM does not suspend the running of the target Java application while the debug connection is established on restore.

: If [`suspend=y`](#suspend) is set pre-checkpoint, the VM suspends the application pre-checkpoint, and on restore. The application remains suspended when you restore the VM even if you specify `suspend=n` pre-checkpoint. Therefore, to ensure that the application is not suspended on restore, you can use the `suspendOnRestore=n` setting. ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)

### `timeout`

        -Xrunjdwp:timeout=<milliseconds>

: If [`server=y`](#server), the `timeout` parameter specifies the time the VM waits for the debugger to attach before the VM ends the connection. If `server=n`, the parameter specifies the time until the VM tries attaching to the debugger before it ends the connection.

### `transport`

        -Xrunjdwp:transport=<value>

: Specifies the name of the transport to use in connecting to the debugger application. Transport refers to a method of communication between a debugger and the VM that is being debugged. `<value>` can be `dt_socket` or `dt_shmem`. `dt_socket` or the socket transport relies on TCP sockets that listen on a port for debugger connections, and uses that connection to transfer the debug session packets. This transport mechanism enables debugging remote target VM application. `dt_shmem` or the shared memory transport uses shared memory to send and receive packets. This transport mechanism enables debugging only locally running applications.

For more information, see [-agentlib:jdwp and -Xrunjdwp sub-options](https://docs.oracle.com/en/java/javase/11/docs/specs/jpda/conninv.html#jdwpoptions).

## See also

- [CRIU support](criusupport.md)
- [What's new in version 0.48.0](version0.48.md#jdwp-support-on-checkpointrestore-in-userspace-criu-restore-is-enabled)

<!-- ==== END OF TOPIC ==== xrunjdwp.md ==== -->
