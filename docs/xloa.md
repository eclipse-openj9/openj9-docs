<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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

# -Xloa / -Xnoloa

This option enables or prevents allocation of a large object area (LOA) during garbage collection.

## Syntax

| Setting            | Effect      | Default                                                                                                                        |
|--------------------|-------------|:------------------------------------------------------------------------------------------------------------------------------:|
| `-Xloa`            | Enable LOA  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> (see [Default behavior)](#default-behavior) |
| `-Xnoloa`          | Disable LOA |                                                                                                                                |

## Default behavior

By default, allocations are made in the small object area (SOA). If there is no room in the SOA, and an object is larger than 64KB, the object is allocated in the LOA.

If the LOA is not used, it is shrunk to zero after a few collections. You can disable it explicitly by specifying the `-Xnoloa` option.

## Explanation

The LOA is an area of the tenure area of the heap set used solely to satisfy allocations for large objects. The LOA is used when the allocation request cannot be satisfied in the main area (the SOA of the tenure heap.

As objects are allocated and freed, the heap can become fragmented in such a way that allocation can be met only by time-consuming compactions. This problem is more pronounced if an application allocates large objects. In an attempt to alleviate this problem, the LOA is allocated. A large object in this context is considered to be any object 64 KB or greater in size. Allocations for new TLH objects are not considered to be large objects.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** The Balanced Garbage Collection policy does not use the LOA. Therefore, when specifying -`Xgcpolicy:balanced`, any LOA options passed on the command line are ignored. The policy addresses the issues of LOA by reorganizing object layout with the VM to reduce heap fragmentation and compaction requirements. 

## See also

- [-Xloainitial / -Xloaminimum / -Xloamaximum](xloaminimum.md)


<!-- ==== END OF TOPIC ==== xloa.md ==== -->
<!-- ==== END OF TOPIC ==== xnoloa.md ==== -->
