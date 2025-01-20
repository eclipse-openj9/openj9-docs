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

# -Xgc:splitheap


**(Windows&trade; 32-bit only)**

By default, the VM uses a contiguous Java&trade; heap to store Java objects. However, on Windows 32-bit systems, there are restrictions in the 32-bit memory space that prevents a process accessing more than 2GB of memory, even if there is more memory available. To increase the maximum allocatable heap size, Eclipse OpenJ9&trade; can split the heap, allowing memory use up to the 4GB limit.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- A split heap forces the garbage collector to use the `gencon` policy and allocates the new and old areas of the generational Java heap in separate areas of memory. Resizing of the new and old memory areas is disabled.
- ![Start of content that applies only to Java 8 (LTS)](cr/java8.png) This option can be used only with Java SE version 8 runtime environments. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png) This option is deprecated in Version 8 and will be removed from future versions.

## Syntax

        -Xgc:splitheap

## Explanation

Use `-Xgc:splitheap` for applications that must run on the 32-bit VM because of 32-bit JNI libraries, a 32-bit operating system, or 32-bit hardware, but need large Java heaps. By using a larger heap, you can allocate more objects before incurring a garbage collection (GC) and you can increase the number of live objects that you can use before an `OutOfMemoryError` exception occurs.

With a split heap, the old area is committed to its maximum size (set with `-Xmox`) in a lower region of memory and the new area is committed to its maximum size (set with `-Xmnx`) in a higher region of memory.

This option is not recommended if your application works in the any of the following ways:

- Performs poorly under the `gencon` GC policy.
- Loads a very large number of classes.
- Uses large amounts of native system memory in JNI libraries; the increased size Java heap might reserve too much of the application's address space.



<!-- ==== END OF TOPIC ==== xgcsplitheap.md ==== -->
