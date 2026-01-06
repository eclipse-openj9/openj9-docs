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

# -XX:[+|-]ShowUnmountedThreadStacks

![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) This option enables or disables the inclusion of the unmounted virtual thread stacks in a Java&trade; core file.

## Syntax

        -XX:[+|-]ShowUnmountedThreadStacks

| Setting               | Effect  | Default                                         |
|-----------------------|---------|:-----------------------------------------------:|
| `-XX:+ShowUnmountedThreadStacks` | Enable  |                                                                                 |
| `-XX:-ShowUnmountedThreadStacks` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  |

## Explanation

In a VM, Java&trade; threads are mapped one-to-one to platform threads and each thread is allocated a new native Java stack.

Virtual threads require platform threads to run because the operating system identifies only platform threads, but these virtual threads are not tied one-to-one to a platform thread. The VM mounts the virtual threads on available platform threads, called carrier threads. When the VM faces a blocking operation on a virtual thread, the thread is unmounted from its carrier thread and hence, the virtual thread is no longer mapped to a platform thread.

Java core file lists stacks of only those threads that are mapped to platform threads. Therefore, the stack of any unmounted virtual thread is not included in the Java core file and thus, the virtual thread information remains incomplete. The unmounted threads also include the carrier thread if a virtual thread was mounted on it. Unmounting the virtual thread, unmounts the carrier thread itself. The stack of any unmounted carrier thread is also not included in the Java core file.

You can use the `-XX:+ShowUnmountedThreadStacks` option to include all the thread data that a VM is aware of, both regular Java threads and the unmounted threads, in the Java core file.

The `-XX:-ShowUnmountedThreadStacks` option is the default option because the chance of having issues with unmounted virtual threads is low. Usually the running or mounted threads are what causes failures. Including the unmounted virtual thread information increases the Java core file size and that might affect performance. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

## See also

- [`-XX:[+|-]ShowCarrierFrames`](xxshowcarrierframes.md)
- [`-XX:ContinuationCache`](xxcontinuationcache.md)


<!-- ==== END OF TOPIC ==== xxshowunmountedthreadstacks.md ==== -->
