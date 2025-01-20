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

# -XX:ContinuationCache

![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) This option sets the continuation tier 1 and 2 cache size to optimize virtual thread performance.

## Syntax

        -XX:ContinuationCache:t1=<size>,t2=<size>

| Setting | Value | Default |
|---------|-------|:-------:|
| `t1=<size>` | 0 to 2<sup>32</sup> - 1 (4,294,967,295) | 1 |
| `t2=<size>` | 0 to 2<sup>32</sup> - 1 (4,294,967,295) | Number of processors x 2 |

Where `t1` is the tier 1 cache and `t2` is the tier 2 cache.

The tier 1 cache holds data that is not shareable between threads.

The tier 2 cache holds data that can be shared globally between threads.

## Explanation

When a virtual thread is created, it is associated with a continuation, which holds the target task of the virtual thread. When a virtual thread starts, the VM saves the current thread (carrier thread) state in an internal data structure that is linked with the continuation. When the associated continuation starts, the VM allocates the memory for the internal data structure and stores its reference in the continuation. When the continuation finishes, the VM stores the internal data structure in a cache instead of freeing the associated memory. New continuations can reuse the cached structure instead of allocating new memory for it. The VM can resume thread execution from the saved state later, typically on a different thread.

If the virtual thread stops, the VM loads the state of the previous thread from the continuation and runs that thread. When the virtual thread does not complete running, the last state of the virtual thread is stored in the continuation during the stop operation. At a later point, the scheduler runs the incomplete virtual thread using the thread state stored in the continuation.

Reusing the cached structure improves the performance of creating virtual threads. The performance benefits are more evident in applications that scale to millions of virtual threads.

The continuation cache is implemented in two tiers, tier 1 and tier 2. You can set the maximum size for the two tiers with the `-XX:ContinuationCache` option. If an application uses more than 10000 virtual threads, setting a larger cache size might improve performance.

When the size of the cache increases, the number of allocations reduces but the cache lookup time might also increase. Evaluate both the number of allocations and the optimal lookup time to set the appropriate cache size. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

## See also

- [`-XX:[+|-]ShowCarrierFrames`](xxshowcarrierframes.md)


<!-- ==== END OF TOPIC ==== xxcontinuationcache.md ==== -->
