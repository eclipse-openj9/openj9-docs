<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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


# The `nogc` policy

The `nogc` garbage collection (GC) policy handles only memory allocation and heap expansion, but doesn't reclaim any memory. If the available Java&trade; heap becomes exhausted, an `OutOfMemoryError` exception is triggered and the VM stops.

Because there is no GC pause and most overheads on allocations are eliminated, the impact on runtime performance is minimized. This policy therefore provides benfits for "garbage-free" applications. See the following section, "When to use nogc", for some possible use cases.

You should be especially careful when using any of the following techniques with `nogc` because memory is never released under this policy:  
- Finalization  
- Direct memory access  
- Weak, soft, and phantom references

This policy can also be enabled with the [`-XX:+UseNoGC`](xxusenogc.md) option.

Further details are available at [JEP 318: Epsilon: A No-Op Garbage Collector](http://openjdk.java.net/jeps/318).


##When to use the `nogc` policy

For most Java applications, you should _not_ use `nogc`. However, there are some particular situations where it can be appropriate:

Testing during development

- GC performance. Use `nogc` as a baseline when testing the performance of other GC policies, including the provision of a low-latency baseline.

- Application memory. Use `nogc` to test your settings for allocated memory. If you use [`-Xmx`](xms.md) to set the heap size that should not be exceeded, then your application will crash with a heap dump if it tries to exceed your memory limit.

Running applications with minimal or no GC requrements

- You might use `nogc` when an application is so short lived that allocated memory is never exhausted and running a full GC cycle is therefore a waste of resources.

- Similarly, when memory application is well understood or where there is rarely memory to be reclaimed, you might prefer to avoid unnecessary GC cycles and rely on a failover mechanism to occasionally restart the VM as necessary.


## Invoking the `nogc` policy

For details of how to use this policy, see [`-Xgcpolicy:nogc`](xgcpolicy.md#nogc).

See also the general [`-Xgc`](xgc.md) options that change the behavior of the GC.





<!--

## From `gc.md`

- [`-Xgcpolicy:nogc`](xgcpolicy.md#nogc) handles only memory allocation and heap expansion, but doesn't reclaim any memory. The GC impact on runtime performance is therefore minimized, but if the available Java heap becomes exhausted, an `OutOfMemoryError` exception is triggered and the VM stops.


## From `xgcpolicy.md`

: This policy handles only memory allocation and heap expansion, but doesn't reclaim any memory. If the available Java heap becomes exhausted, an `OutOfMemoryError` exception is triggered and the VM stops.

    Because there is no GC pause and most overheads on allocations are eliminated, the impact on runtime performance is minimized. This policy therefore provides benfits for "garbage-free" applications. See the following section, "When to use nogc", for some possible use cases.

    You should be especially careful when using any of the following techniques with `nogc` because memory is never released under this policy:  
    - Finalization  
    - Direct memory access  
    - Weak, soft, and phantom references

    This policy can also be enabled with the [`-XX:+UseNoGC`](xxusenogc.md) option.

    Further details are available at [JEP 318: Epsilon: A No-Op Garbage Collector](http://openjdk.java.net/jeps/318).

####When to use nogc

: For most Java applications, you should _not_ use `nogc`. However, there are some particular situations where it can be appropriate:

    Testing during development

    - GC performance. Use `nogc` as a baseline when testing the performance of other GC policies, including the provision of a low-latency baseline.

    - Application memory. Use `nogc` to test your settings for allocated memory. If you use [`-Xmx`](xms.md) to set the heap size that should not be exceeded, then your application will crash with a heap dump if it tries to exceed your memory limit.

    Running applications with minimal or no GC requrements

    - You might use `nogc` when an application is so short lived that allocated memory is never exhausted and running a full GC cycle is therefore a waste of resources.

    - Similarly, when memory application is well understood or where there is rarely memory to be reclaimed, you might prefer to avoid unnecessary GC cycles and rely on a failover mechanism to occasionally restart the VM as necessary.

-->


<!-- ==== END OF TOPIC ==== gc_nogc.md ==== -->
