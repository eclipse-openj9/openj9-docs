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

# Java Attach API

With the Attach API, your application can connect to a running VM and load an agent into that VM to run tasks. The typical use case for this feature is to load an agent that can be used to monitor the application that's running in the target VM.

For example, if you wanted to start monitoring an application that is already running with the Attach API enabled, you can use a tool such as the [IBM Health Center](https://www.ibm.com/support/knowledgecenter/en/SS3KLZ/com.ibm.java.diagnostics.healthcenter.doc/topics/introduction.html). In this case, a Health Center agent can start in its own VM and attach to the target VM where the application is running to start recording and sending data to the Health Center client.

The Eclipse OpenJ9&trade; implementation of the Attach API is equivalent to the reference implementation (API documentation is available on the [Oracle website](https://docs.oracle.com/javase/8/docs/jdk/api/attach/spec/index.html)). However, you can use the Attach API only to connect to another OpenJ9 VM.

When you run a Java&trade; application, VM support for the Attach API is enabled by default on all platforms except z/OS&reg;. For security reasons on z/OS,
processes that use the default z/OS OMVS segment cannot enable the Attach API.

To enable or disable the Attach API, use the [`-Dcom.ibm.tools.attach.enable=[yes|no]`](dcomibmtoolsattachenable.md) command line option.

## Securing the Attach API

Because the Attach API can be used to connect to a running application, you must control access to it to ensure that only
authorized users or processes can use it. Disable the Attach API if you do not intend to use it.

If you do not want to disable the Attach API but want to control the unauthorized dynamic loading of agents into the VM by using the Attach API, use the [`-XX:-EnableDynamicAgentLoading`](xxenabledynamicagentloading.md) option.

On Windows&trade; systems, the Attach API uses the system temporary directory, which is typically `C:\Users\<USERNAME>\AppData\Local\Temp`.
The Attach API creates a common subdirectory, which is `.com_ibm_tools_attach` by default. Because files and directories in the system temporary directory are handled by Windows security, only the process owner can connect to their processes.

On UNIX systems, the Attach API uses `/tmp` and creates a common subdirectory, which is `.com_ibm_tools_attach` by default. The common subdirectory must be on a local drive, not a network drive. Security is handled by POSIX file permissions. The Attach API directory must be owned by `root` user and must have read, write, and execute file permissions for `user`, `group`, and `other` (`drwxrwxrwx`). The sticky bit is set so that only the owner and `root` can delete or rename files or directories within it. A process that uses the Java Attach API must be owned by the same UNIX user ID as the target process.

```
~/tmp $ ls -al
total 0
drwxr-xr-x   3 user_a staff    96  6 Aug 17:11 .
drwxr-xr-x+ 89 user_a staff  2848  6 Aug 17:11 ..
drwxrwxrwx+  7 root   staff   224  6 Aug 17:22 .com_ibm_tools_attach
```

In the default Attach API directory, you can find certain files that start with an underscore `_*`, which are involved in synchronization.
By default, any user that has read and write permissions can own these files. The files are empty and are automatically re-created if deleted. A user might interfere with the functioning of the Attach API by modifying the file permissions. To prevent such an interference, you can protect the file permissions by setting `root` as the owner of the files.

When your application attaches to a VM, a process directory is created.

```
~/tmp/.com_ibm_tools_attach $ ls -l
total 3
-rw-rw-rw-  1 user_a  staff    0  6 Aug 17:12 _attach_lock
-rw-rw-rw-  1 user_a  staff    0  6 Aug 17:12 _controller
-rw-rw-rw-  1 user_a  staff    0  6 Aug 17:12 _notifier
drwx--x--x  6 user_b  staff  192  6 Aug 17:21 process_a
```

The files in the subdirectory for a process, with the exception of a lock file, are accessible only by the owner of a process. The permissions
for these files are `rwxr-xr-x` with the exception of the `attachNotificationSync` file, as shown in the following example.

```
~/tmp/.com_ibm_tools_attach/process_a $ ls -l
total 4
-rwxrw-rw-  1 user_b  staff  0  6 Aug 17:18 attachNotificationSync
-rwxr-xr-x  1 user_b  staff  0  6 Aug 17:21 file_a
-rwxr-xr-x  1 user_b  staff  0  6 Aug 17:21 file_b
-rwxr-xr-x  1 user_b  staff  0  6 Aug 17:21 file_c
```

**Notes for z/OS:**

- z/OS systems must also set POSIX permissions on files and cannot rely on RACF&reg; or system level security to protect applications.
- To avoid z/OS console messages reporting security violations in `/tmp`, add a security exception or specify a different common subdirectory by setting the `com.ibm.tools.attach.directory` system property.

## Configuring

A number of system properties are available to configure the Attach API when you start a Java application, as shown in the following table:

| System property                                                                                             |    Description                                                           |
|-------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| [`-Dcom.ibm.tools.attach.directory=<directory_name>`](dcomibmtoolsattachdirectory.md)                       | Specify a different common directory for Attach API working files.       |
| [`-Dcom.ibm.tools.attach.displayName=<my_display_name>`](dcomibmtoolsattachdisplayname.md)                  | Change the display name recorded by an agent                             |
| [`-Dcom.ibm.tools.attach.id=<my_vm_ID>`](dcomibmtoolsattachid.md)                                           | Change the VM identifier recorded by an agent                            |
| [`-Dcom.ibm.tools.attach.timeout=<value_in_milliseconds>`](dcomibmtoolsattachtimeout.md)                    | Change the connection timeout                                            |
| [`-Dcom.ibm.tools.attach.shutdown_timeout=<value_in_milliseconds>`](dcomibmtoolsattachshutdown_timeout.md)  | Specify the timeout for ending the Attach API wait loop thread           |
| [`-Dcom.ibm.tools.attach.command_timeout=<value_in_milliseconds>`](dcomibmtoolsattachcommand_timeout.md)    | Specify the timeout for sending a command to the target VM after initial attachment   |
| [`-Dcom.ibm.tools.attach.retry=<number_of_retries>`](dcomibmtoolsattachretry.md)    | Specify the number of times the `jcmd` tool retries attaching to a running VM when the tool encounters the `SocketException` error on Windows platform    |


To learn more about each property, click the link in the table.

## Troubleshooting

Problems with the Attach API generate one of the following exceptions:

- `com.sun.tools.attach.AgentLoadException`
- `com.sun.tools.attach.AgentInitializationException`
- `com.sun.tools.attach.AgentNotSupportedException`
- `com.sun.tools.attach.AttachOperationFailedException`
- `java.io.IOException`
- `java.net.SocketException`

Exceptions from agents on the target VM go to `stderr` or `stdout` for the target VM. These exceptions are not reported in the output of the attaching VM.

Here are some problems that you might encounter:

- On Unix systems, the file permissions are incorrectly set, causing access issues. Resolve these issues by reading and complying with [Securing the Attach API](#securing-the-attach-api). Also check that the Attach API is not disabled.
- The common directory is deleted, the contents of the common directory are deleted, or permissions of the common directory or subdirectories are changed. As a result, the source VM might not be able list target VMs or attach to them. Deletion of the common directory can also cause semaphore leaks.
- The system temporary directory is full or inaccessible and the Attach API cannot initialize. Try specifying a different directory in which to create the common subdirectory by using the `-Dcom.ibm.tools.attach.directory` system property.
- A short delay between the start of the target VM and the initialization of the Attach API process can cause an `AttachNotSupportedException: No provider for virtual machine id` issue when the `VirtualMachine.attach(String id)` method is called.
- The target process is overloaded, suspended, or no longer running, or the port that is used to connect to the target is subject to a wait time (use the `netstat -a` command to check for ports in the `TIME_WAIT` state). These situations can cause an `AttachNotSupportedException` when the `attach` method is called.
- A JVMTI agent is corrupt or attempts to run an operation that is not available after the VM starts. These situations can cause an `AgentLoadException` or `AgentInitializationException` when one of the following methods is called: `loadAgent()`, `loadAgentLibrary()`, or `loadAgentPath()`. Depending on the method invoked, try loading the agent at VM startup by using one of the following command-line options `-javaagent`, `-agentlib`, or `-agentpath`. For more information about these options, see [Java Virtual Machine Tool Interface](interface_jvmti.md).

If you have checked for these potential issues but you are still experiencing problems, a number of command line system properties are available to help narrow down the cause. These options are shown in the following table:

| System property                                                                                             |    Description                                                           |
|-------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| [`-Dcom.ibm.tools.attach.logging=<yes|no>`](dcomibmtoolsattachlogging.md)                                   | Turn on tracing of attach API events                                     |
| [`-Dcom.ibm.tools.attach.log.name=<my_log_name>`](dcomibmtoolsattachlogname.md)                             | Specify the path and prefix for the log files                            |


To learn more about each property, click the link in the table.
