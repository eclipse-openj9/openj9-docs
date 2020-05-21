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


# The `metronome` policy

**(AIX&reg;, Linux&reg; x86_64 only)**

The `metronome` garbage collection (GC) policy is intended for use in applications that require a precise upper bound on collection pause times. The real-time collector runs in short interruptible bursts with a preset upper bound on pause duration.

The real-time heap is allocated as a contiguous range of RAM partitioned into small regions of equal size, usually about 64kB. Arrays are represented as arraylets with a spine pointing to a series of regions containing the array elements. Each region is either empty or contains only objects of the same size or an arraylet. This organization simplifies new object allocation and the consolidation of free heap space, allowing the real-time collector to maintain GC throughput while supporting a consistent service level. This compares with the [`balanced` GC policy](gc_balanced.md), where JNI access to array data might involve reconstituting arraylets as contiguous arrays, which can severely degrade real-time operation.

Pause duration is limited by selecting a target pause time using [-Xgc:targetPauseTime](xgc.md#targetpausetime). Application bandwidth is reserved by selecting a target utilization with [-Xgc:targetUtilization](xgc.md#targetutilization), expressed as the percentage of run time required to be available to the application between collection pauses. Reasonable targets for utilization are typically in the range 50-80% but may be set as high as 90%, depending on the object allocation rate characteristics of the application.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Warning: Neither of these parameters mentioned by Kim Briggs currently exist in the docs.**

In practice, the real-time collector might not be able maintain the heap with selected constraints and you might need to adjust heap size, target utilization, or pause time to achieve an acceptable runtime configuration.

See [Real-time garbage collection](https://www.ibm.com/developerworks/library/j-rtj4/index.html) at "IBM Developer" for more information about the real-time heap and the `metronome` GC policy.


## Invoking the `metronome` policy

For details of how to use this policy, see [`-Xgcpolicy:metronome`](xgcpolicy.md#metronome).

See also the general [`-Xgc`](xgc.md) options that change the behavior of the GC.



<!--

## From `gc.md`

- [`-Xgcpolicy:metronome`](xgcpolicy.md#metronome-aix-linux-x86-only) is designed for applications that require precise response times. Garbage collection occurs in small interruptible steps to avoid stop-the-world pauses. This policy is available only on x86 Linux and AIX platforms.


## From `xgcpolicy.md`

: The metronome policy is an incremental, deterministic garbage collector with short pause times. Applications that are dependent on precise response times can take advantage of this technology by avoiding potentially long delays from garbage collection activity. The metronome policy is supported on specific hardware and operating system configurations.

    For more information, see [Using the Metronome Garbage Collector](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_mgc.html).




## Briggs

The metronome policy is available on 64-bit AIX&reg; and Linux&tm;/x86_64 platforms. It is intended for use in applications that require a precise upper bound on collection pause times. The real-time collector runs in short interruptible bursts with a preset upper bound on pause duration.

The real-time heap is allocated as a contiguous range of RAM partitioned into small regions of equal size, usually about 64kb. Arrays are represented as arraylets with a spine pointing to a series of regions containing the array elements. Each region is either empty or contains only objects of the same size or an arraylet. This organization simplifies new object allocation and consolidation of free heap space, allowing the real-time collector to maintain GC throughput while supporting a consistent service level. As for the balanced GC policy, JNI access to array data may involve reconstituting arraylets as contiguous arrays, which may severely degrade real-time operation.

Pause duration is limited by selecting a target pause time using -Xgc:targetPauseTime=N (milliseconds). Application bandwidth is reserved by selecting a target utilization with Xgc:targetUtilization=T (%), expressed as the percentage of run time required to be available to the application between collection pauses. Reasonable targets for utilization are typically in the range 50-80% but may be set as high as 90%, depending on the object allocation rate characteristics of the application.

In practice, the real-time collector may not be able maintain the heap with selected constraints and it may be necessary to adjust heap size or target utilization or pause time to achieve an acceptable runtime configuration.

See [Real-time garbage collection](https://www.ibm.com/developerworks/library/j-rtj4/index.html) for more information on the real-time heap and metronome GC policy.

-->

<!-- ==== END OF TOPIC ==== gc_metronome.md ==== -->
