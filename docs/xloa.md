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

# -Xloa / -Xnoloa / -Xloainitial / -Xloaminimum / -Xloamaximum

The large object area (LOA) is an area of the tenure area of the heap set used solely to satisfy allocations for large objects. The LOA is used when the allocation request cannot be satisfied in the small object area (SOA), which is the main area of the tenure heap.

As objects are allocated and freed, the heap can become fragmented in such a way that allocation can be met only by time-consuming compactions. This problem is more pronounced if an application allocates large objects. In an attempt to alleviate this problem, the LOA is allocated. A large object in this context is considered to be any object 64 KB or greater in size. Allocations for new thread local heaps (TLH) objects are not considered to be large objects.

You can use the following `-X` options to control the LOA allocation:

- [`-Xloa` / `-Xnoloa`](#-xloa-xnoloa)
- [`-Xloainitial`](#-xloainitial)
- [`-Xloaminimum`](#-xloaminimum)
- [`-Xloamaximum`](#-xloamaximum)

## -Xloa / -Xnoloa

The `-Xloa` option enables and the `-Xnoloa` option prevents the allocation of a large object area during garbage collection (GC).

These options are not supported with the balanced GC policy (`-Xgcpolicy:balanced`) or metronome GC policy (`-Xgcpolicy:metronome`), which do not use an LOA. Any LOA options that are passed on the command line are ignored. These policies address the issues that are solved by an LOA by reorganizing object layout with the VM to reduce heap fragmentation and compaction requirements.

### Syntax

| Setting            | Effect      | Default                                                                                                                        |
|--------------------|-------------|:------------------------------------------------------------------------------------------------------------------------------:|
| `-Xloa`            |  Enable LOA  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> (see [Default behavior](#default-behavior)) |
| `-Xnoloa`          |  Disable LOA  |                                                                                             |

### Default behavior

By default, allocations are made in the SOA. If the SOA has no room, and an object is larger than 64 KB, the object is allocated in the LOA if you enable LOA with the `-Xloa` option.

If the LOA is not used, it is shrunk to zero after a few collections. You can disable it explicitly by specifying the `-Xnoloa` option.

## -Xloainitial

This option specifies the initial proportion of the current tenure space that is allocated to the LOA.

### Syntax

| Setting               | Effect            | Default  |
|-----------------------|-------------------|----------|
| `-Xloainitial<value>` | Set initial space | `0.05`   |

## -Xloaminimum

This option specifies the minimum proportion of the current tenure space that is allocated to the LOA.

The LOA does not shrink to less than the minimum value.

### Syntax

| Setting               | Effect            | Default  |
|-----------------------|-------------------|----------|
| `-Xloaminimum<value>` | Set minimum space | `0.01`   |

## -Xloamaximum

This option specifies the maximum proportion of the current tenure space that is allocated to the large object area (LOA).

### Syntax

| Setting               | Effect            | Default  |
|-----------------------|-------------------|----------|
| `-Xloamaximum<value>` | Set minimum space | `0.5`    |

## See also

- [Allocation caches](allocation.md#allocation-caches)

<!-- ==== END OF TOPIC ==== xloa.md ==== -->
