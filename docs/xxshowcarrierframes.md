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

# -XX:\[+|-\]ShowCarrierFrames

![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) This option controls the addition of the carrier threads' stack trace to the `Throwable.getStackTrace()` method. You can add the stack trace of the carrier threads to facilitate the debugging process.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- The platform thread that runs a virtual thread is that virtual thread's carrier thread.
- The `-XX:[+|-]ShowCarrierFrames` option is applicable only if the current thread is a virtual thread.
- If you specify the [`-XX:-StackTraceInThrowable`](xxstacktraceinthrowable.md) option, the `-XX:[+|-]ShowCarrierFrames` option has no effect. The `-XX:-StackTraceInThrowable` option removes stack trace of all threads from exceptions.

## Syntax

        -XX:[+|-]ShowCarrierFrames

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+ShowCarrierFrames` | Enable  |                                                                                 |
| `-XX:-ShowCarrierFrames` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  |


## Explanation

Virtual threads require platform threads to run because the operating system identifies only platform threads. Therefore, a VM maintains multiple platform threads that are used as carrier threads to run the virtual threads. The VM assigns a virtual thread to a platform thread in a process called **mounting**.

Although the virtual thread runs on a carrier thread, the stack trace of the virtual thread and its carrier thread are separate. If an exception occurs on running the virtual threads, the thread dumps do not include stack frames from the carrier thread's stack.

You can use the `-XX:+ShowCarrierFrames` option to add the stack trace of the carrier thread in addition to the virtual thread stack trace to the `Throwable.getStackTrace()` method, if an exception occurs. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)



<!-- ==== END OF TOPIC ==== xxshowcarrierframes.md ==== -->
