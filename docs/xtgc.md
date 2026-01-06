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

# -Xtgc 


Provides garbage collection tracing options.

## Syntax

        -Xtgc:<parameter>{,<parameter>}

## Parameters

: Specify one one or more of the following parameters in a comma-separated list:

### `allocation`

        -Xtgc:allocation

: Prints both per thread (TLH) and cumulative allocation cache statistics, such as total bytes allocated since last GC, current TLH sizing, and discarded TLH size.

For more information about the allocation cache, see [Allocation caches](allocation.md#allocation-caches).

### `backtrace`

        -Xtgc:backtrace

: Before a garbage collection, a single line is printed containing the name of the main thread for garbage collection, as well as the value of the `osThread` slot in the `J9VMThread` structure.

### `compaction`

        -Xtgc:compaction

: Prints extra information showing the relative time spent by threads in the *"move"* and *"fixup"* phases of compaction

### `concurrent`

        -Xtgc:concurrent

: Prints extra information showing the activity of the concurrent mark background thread

### `dump`

        -Xtgc:dump

: Prints a line of output for every free chunk of memory in the system, including "dark matter" (free chunks that are not on the free list for some reason, typically because they are too small). Each line contains the base address and the size in bytes of the chunk. If the chunk is followed in the heap by an object, the size and class name of the object is also printed. This argument has a similar effect to the `terse` argument.

### `file`

        -Xtgc:file=<filename>

: Directs the logs to a file. Otherwise they are directed to stderr.

### `freeList`

        -Xtgc:freeList

: Before a garbage collection, prints information about the free list and allocation statistics since the last garbage collection. Prints the number of items on the free list, including "deferred" entries (with the scavenger, the unused space is a deferred free list entry). For TLH and non-TLH allocations, prints the total number of allocations, the average allocation size, and the total number of bytes discarded during allocation. For non-TLH allocations, also included is the average number of entries that were searched before a sufficiently large entry was found.

### `parallel`

        -Xtgc:parallel

: Produces statistics on the activity of the parallel threads during each operation (mark, sweep, scavenge etc.) of a GC cycle.

### `rootscantime`

        -Xtgc:rootscantime

: Prints duration of strong and weak roots scanning of a GC cycle.

### `scavenger`

        -Xtgc:scavenger

: Prints extra information after each scavenger collection. A histogram is produced showing the number of instances of each class, and their relative ages, present in the survivor space. The information is obtained by performing a linear walk-through of the space.

### `terse`

        -Xtgc:terse

: Dumps the contents of the entire heap before and after a garbage collection. For each object or free chunk in the heap, a line of trace output is produced. Each line contains the base address, "a" if it is an allocated object, and "f" if it is a free chunk, the size of the chunk in bytes, and, if it is an object, its class name.



<!-- ==== END OF TOPIC ==== xtgc.md ==== -->

