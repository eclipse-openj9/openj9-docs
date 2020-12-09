<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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


# Memory allocation

The process of managing memory in the VM is handled by the Allocator and the Garbage Collector (GC). These components operate on an area of memory that is reserved for VM processing called the Java&trade; heap. 

See [Garbage collection](gc.md) for more information about the GC.

## The Allocator

The Allocator assigns areas of the Java heap for Java objects. Objects are considered as *live* when they have a chain of references to them that start from root references, such as those found in thread stacks. When that reference or pointer no longer exists, the objects are considered as *garbage*.

The Allocator manages pools of free memory and how the free memory is consumed. It is also responsible for allocating areas of storage in the Java heap for objects at the request of applications, class libraries, or the VM.

Every allocation requires a *heap lock* to stop concurrent threads trying to access the same area of memory at the same time. When an object is allocated, the heap lock is released. If there is insufficient space to allocate the object, allocation fails, the heap lock is released, and the GC is called. If the GC manages to recover some space on the heap, the Allocator can resume operations. If the GC does not recover enough space, it returns an `OutOfMemoryError` exception.

Acquiring a heap lock for every allocation would be an intensive operation with a knock on impact to performance. To get around this problem, small objects are allocated to thread local heaps (TLH).

### Thread local heaps (TLH)

To improve performance, allocation caches are reserved in the heap for different threads. These allocation caches are known as thread local heaps (TLH) and allow each thread to allocate memory from its cache without acquiring the heap lock. A TLH is typically used for small objects of less than 512 bytes (768 bytes on 64-bit VMs) although larger objects can be allocated from the cache if there is sufficient space.

If a thread allocates a lot of objects, the allocator gives that thread a larger TLH to reduce contention on the heap lock.

A TLH is predefined with an initial default size of 2 KB. On every TLH refresh, the requested size for that thread
is increased by an increment (default 4 KB). The requested size can grow up to a predefined maximum (default 128 KB).

After every GC cycle, the TLH requested size for each thread is reduced, sometimes by as much as 50%, to take account of threads that
reduce their allocation rate and no longer need large TLHs.

For very inactive threads, the requested size can even drop below the initial value, down to the predefined minimum (512/768 bytes).
For very active threads, the maximum TLH requested size might be reached before the next GC occurs.

Larger TLHs can help reduce heap lock contention, but might also reduce heap utilisation and increase heap fragmentation.

The following options control the requested TLH size:

- [`-Xgc:tlhMaximumSize=<bytes>`](xgc.md#tlhmaximumsize)
- [`-Xgc:tlhInitialSize=<bytes>`](xgc.md#tlhinitialsize)
- [`-Xgc:tlhIncrementSize=<bytes>`](xgc.md#tlhincrementsize)

Typically, when the maximum TLH size is increased, you should also increase the increment proportionally, so that active threads can
reach the maximum requested TLH size more quickly.

<!-- ==== END OF TOPIC ==== allocator.md ==== -->

