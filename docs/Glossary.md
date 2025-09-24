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

# Glossary Terms and Definitions

| Glossary Term | Definition |
|---------------|------------|
| **Allocator** | Manages pools of free memory and allocates storage in the Java heap for objects requested by applications, libraries, or the VM. |
| **Arraylet** | A structure for storing large arrays in fragmented heaps, consisting of a spine and leaves. Helps avoid contiguous memory allocation. |
| **Area-based Heap** | Divides the heap into nursery and tenure areas to optimize garbage collection based on object lifespan. |
| **Compressed References** | A memory optimization technique that stores object references in 32-bit format on 64-bit systems to reduce memory footprint. |
| **GC Policy** | A strategy used by the garbage collector to manage memory. Examples include `gencon`, `optavgpause`, `balanced`, etc. |
| **Garbage Collector (GC)** | Reclaims memory by removing objects that are no longer referenced. Different GC policies determine its behavior. |
| **Heap Configuration** | Defines the structure of the Java heap, which can be flat or divided into areas or regions. |
| **Heap Expansion and Contraction** | The VM grows or shrinks the heap based on memory needs and GC activity, controlled by options like `-Xminf`, `-Xmaxf`, etc. |
| **Heap Lock** | A synchronization mechanism to prevent concurrent threads from accessing the same memory area during allocation. |
| **Heap Sizing** | Refers to setting initial and maximum heap sizes using options like `-Xms` and `-Xmx`, with finer tuning for specific areas. |
| **LOA (Large Object Area)** | A heap subdivision for large object allocation. Its size is dynamically adjusted to avoid allocation failures. |
| **OutOfMemoryError** | An exception thrown when the JVM cannot allocate memory due to insufficient heap space. |
| **Region-based Heap** | Divides the heap into many equal-sized regions to manage object generations. Used by `balanced` and `metronome` GC policies. |
| **Small Object Allocation** | The process of allocating memory for objects that are typically smaller than 64 KB in size in a designated part of the heap called the Small Object Area (SOA). |
| **SOA (Small Object Area)** | A heap subdivision for small object allocation. Allocation failures here trigger garbage collection. |
| **Thread Local Heap (TLH)** | A thread-specific memory cache that allows memory allocation without acquiring the heap lock, improving performance. |


<!-- ==== END OF TOPIC ==== glossary.md ==== -->