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

# Garbage collection

To prevent applications running out of memory, objects in the Java heap that are no longer required must be reclaimed. This process is known as garbage collection (GC). When garbage is collected, the garbage collector must obtain exclusive access to the heap, which causes an application to pause while the clean up is done. This pause is often referred to as a *stop-the-world* pause because an application must halt until the process completes. In general, the first step in the GC process is to mark the objects that are reachable, which means they are still in use. The next step is to sweep away the unmarked objects to reclaim memory. Depending on the type of application you are running, you might want to choose when and how garbage collection is done.

Eclipse OpenJ9 has a number of GC policies designed around different types of applications and workloads. Picking the right policy very much depends on your usage and performance goals.

## Gencon policy

If you have a transactional application, with many short lived objects, the Generational Concurrent (`-Xgcpolicy:gencon`) GC policy is probably best suited, which aims to minimize GC pause times without compromising throughput. This is the default policy employed by the VM, so if you want to use it you don't need to specify it on the command line when you start your application.

A special mode of the `gencon` policy is known as *Concurrent Scavenge* (`-Xgc:concurrentScavenge`). This mode works with the Guarded Storage (GS) Facility, which is a feature of the IBM z14™ mainframe system. The aim is to minimize the time spent in stop-the-world pauses by collecting garbage in parallel with running application threads. The GS Facility provides hardware-based support to detect when potentially stale references to objects are accessed by an application. This means that the garbage collector can start processing objects in parts of the heap without halting an application because the GS Facility is on hand to spot accesses to an object and send a notification. The object that was ready to be swept away can be moved, and references to it can be reset. You can read more about this mode in the following blog posts:

- [Reducing Garbage Collection pause times with Concurrent Scavenge and the Guarded Storage Facility](https://developer.ibm.com/javasdk/2017/09/18/reducing-garbage-collection-pause-times-concurrent-scavenge-guarded-storage-facility/)
- [How Concurrent Scavenge using the Guarded Storage Facility Works](https://developer.ibm.com/javasdk/2017/09/25/concurrent-scavenge-using-guarded-storage-facility-works/)

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Concurrent scavenge mode is available on Linux on IBM Z&reg; and the z/OS&reg; platform.

## Other policies

OpenJ9 has the following alternative GC policies:

- `-Xgcpolicy:balanced` divides the Java heap into regions, which are individually managed to reduce the maximum pause time on large heaps and increase the efficiency of garbage collection. The aim of the policy is to avoid global collections by matching object allocation and survival rates. If you have problems with application pause times that are caused by global garbage collections, particularly compactions, this policy might improve application performance, particularly on large systems that have Non-Uniform Memory Architecture (NUMA) characteristics (x86 and POWER™ platforms).
- `-Xgcpolicy:metronome` is designed for applications that require precise response times. Garbage collection occurs in small interruptible steps to avoid stop-the-world pauses. This policy is available only on x86 Linux and AIX&reg; platforms.
- `-Xgcpolicy:optavgpause` uses concurrent mark and sweep phases, which means that pause times are reduced when compared to optthruput, but at the expense of some performance throughput.
- `-Xgcpolicy:optthruput` is optimized for throughput by disabling the concurrent mark phase, which means that applications will stop for long pauses while garbage collection takes place. You might consider using this policy when high application throughput, rather than short garbage collection pauses, is the main performance goal.

For more information about these garbage collection policies and options, see [-Xgcpolicy](xgcpolicy.md).

## Troubleshooting

You can diagnose problems with garbage collection operations by turning on verbose garbage collection logging. By default, the information is printed to STDERR but can be redirected to a file by specifying the `-Xverbosegclog` option. The log files contain detailed information about all operations, including initialization, stop-the-world processing, finalization, reference processing, and allocation failures. For more information, see [Verbose garbage collection](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_pd_verbosegc.html)

If verbose logs do not provide enough information to help you diagnose GC problems, you can use GC trace to analyze operations at a more granular level. For more information, see [-Xtgc](xtgc.md).


<!-- ==== END OF TOPIC ==== gc.md ==== -->
