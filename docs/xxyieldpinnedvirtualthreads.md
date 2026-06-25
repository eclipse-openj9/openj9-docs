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

# -XX:[+|-]YieldPinnedVirtualThreads

![Start of content that applies to Java 24 and later](cr/java24plus.png) This option enables or disables the yielding of CPU time by pinned continuations for virtual threads. You can control virtual thread scheduling behavior, especially in the case of applications that frequently encounter pinning scenarios.

The `-XX:+YieldPinnedVirtualThreads` option is the default setting.

## Syntax

        -XX:[+|-]YieldPinnedVirtualThreads

| Setting                      | Effect  | Default                                                                        |
|------------------------------|---------|:------------------------------------------------------------------------------:|
| `-XX:+YieldPinnedVirtualThreads`    | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-YieldPinnedVirtualThreads`    | Disable |                                                                                |

## Explanation

When a virtual thread is created, it is associated with a data structure called *continuation* that represents the execution state of a virtual thread. Continuation saves and restores the current state of a virtual thread. For example, when a virtual thread unmounts, continuation saves the current state and when it remounts on an available carrier thread, continuation restores that state.

In some scenarios, such as executing native code or some VM internal operations, the continuation is *pinned*. When pinned, a virtual thread cannot unmount its carrier thread even if it encounters a blocking operation. It also holds onto CPU time, thus preventing other virtual threads from using that carrier thread.

A VM maintains a limited pool of carrier threads. Virtual threads are supposed to unmount when blocked, freeing carriers for other virtual threads to use. You can use the `-XX:+YieldPinnedVirtualThreads` option to enable the pinned virtual thread to yield CPU time to OS scheduler. A pinned virtual thread cannot unmount its carrier thread but the yielding option helps by at least allowing sharing of CPU time even when carrier thread sharing is impossible.

You can use the `-XX:-YieldPinnedVirtualThreads` option to disable this yielding of CPU time feature.

For more information about pinning of virtual threads, see [JEP 491](https://openjdk.java.net/jeps/491): Synchronize Virtual Threads without Pinning.


![End of content that applies to Java 24 and later](cr/java_close.png)

## See also

- [`-XX:[+|-]ShowCarrierFrames`](xxshowcarrierframes.md)
- [`-XX:ContinuationCache`](xxcontinuationcache.md)
- [`-XX:[+|-]ShowUnmountedThreadStacks`](xxshowunmountedthreadstacks.md)
- [What's new in version 0.54.0](version0.54.md#new-xx-yieldpinnedvirtualthreads-command-line-option-is-added)



<!-- ==== END OF TOPIC ==== xxyieldpinnedvirtualthreads.md ==== -->
