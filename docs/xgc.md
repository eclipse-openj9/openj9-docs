<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# -Xgc

Options that change the behavior of the garbage collector.

## Syntax

        -Xgc:<parameter>

## Parameters

| Parameter                                                                 | Effect                                                                                                    |
|---------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| [`breadthFirstScanOrdering`         ](#breadthfirstscanordering         ) | Sets the scan mode to breadth first.                                             |
| [`classUnloadingKickoffThreshold`   ](#classunloadingkickoffthreshold   ) | Sets a threshold to start an early concurrent global garbage collection (GC) cycle due to recent, heavy class loading activity  |
| [`classUnloadingThreshold`          ](#classunloadingthreshold          ) | Sets a threshold to trigger a class unloading operation in a global GC cycle                                     |
| [`concurrentScavenge`               ](#concurrentscavenge               ) | Enables a GC mode with less pause times.                                             |
| [`dnssExpectedTimeRatioMaximum`     ](#dnssexpectedtimeratiomaximum     ) | Sets the maximum time to spend on GC of the nursery area.                                                 |
| [`dnssExpectedTimeRatioMinimum`     ](#dnssexpectedtimeratiominimum     ) | Sets the minimum time to spend on GC of the nursery area.                                                 |
| [`dynamicBreadthFirstScanOrdering`  ](#dynamicbreadthfirstscanordering  ) | Sets scan mode to dynamic breadth first.                                             |
| [`excessiveGCratio`                 ](#excessivegcratio                 ) | Sets a boundary value beyond which GC is deemed to be excessive.                                          |
| [`hierarchicalScanOrdering`         ](#hierarchicalscanordering         ) | Sets scan mode to hierarchical.                                             |
| [`minContractPercent`               ](#mincontractpercent               ) | Sets the minimum percentage of the heap that can be contracted at any given time.                         |
| [`maxContractPercent`               ](#maxcontractpercent               ) | Sets the maximum percentage of the heap that can be contracted at any given time.                         |
| [`noConcurrentScavenge`             ](#noconcurrentscavenge             ) | Disables concurrent scavenge.                                                                             |
| [`noSynchronousGCOnOOM`             ](#nosynchronousgconoom             ) | Prevents an application stopping to allow GC activity.                                                                             |
| [`overrideHiresTimerCheck`          ](#overridehirestimercheck          ) | Overrides GC operating system checks for timer resolution.                                                |
| [`preferredHeapBase`                ](#preferredheapbase                ) | Sets a memory range for the Java&trade; heap. (AIX&reg;, Linux&reg;, macOS&reg;, and Windows&trade; only) |
| [`scvNoAdaptiveTenure`              ](#scvnoadaptivetenure              ) | Turns off the adaptive tenure age in the generational concurrent GC policy.                               |
| [`scvTenureAge`                     ](#scvtenureage                     ) | Sets the initial scavenger tenure age in the generational concurrent GC policy.                           |
| [`stdGlobalCompactToSatisfyAllocate`](#stdglobalcompacttosatisfyallocate) | Prevents the GC from performing a compaction unless absolutely required.                                  |
| [`synchronousGCOnOOM`               ](#synchronousgconoom               )     | Stops an application to allow GC activity.                                                                             |
| [`targetPausetime`                  ](#targetpausetime                  )   | Sets the GC pause time for the `metronome` GC policy.                                                   |
| [`targetUtilization`                ](#targetutilization                )   | Sets application utilization for the `metronome` GC policy.                                                   |
| [`tlhIncrementSize`                 ](#tlhincrementsize                 ) | Sets the size of the thread local heap (TLH) increment.                                                   |
| [`tlhInitialSize`                   ](#tlhinitialsize                   ) | Sets the initial size of the thread local heap.                                                           |
| [`tlhMaximumSize`                   ](#tlhmaximumsize                   ) | Sets the maximum size of the thread local heap.                                                           |
| [`verboseFormat`                    ](#verboseformat                    ) | Sets the verbose GC format.                                                                               |
| [`verbosegcCycleTime`               ](#verbosegccycletime                    ) | Sets the criteria for verbose GC logging.                                                                              |

### `breadthFirstScanOrdering`

         -Xgc:breadthFirstScanOrdering

: This option sets the scan mode for GC operations that evacuate objects in the heap (scavenge operations (`gencon`) and copy forward operations (`balanced`)) to breadth first mode. The scan mode reflects the method for traversing the object graph and is also known as *Cheney's algorithm*.

### `classUnloadingKickoffThreshold`

         -Xgc:classUnloadingKickoffThreshold=<value>

: Where `<value>` is equal to the number of class loaders plus the number of anonymous classes that are loaded since the previous class unloading operation.

: This option sets a threshold that is used to start an early concurrent global GC cycle due to recent class loading activity. The default value is 80000.

: This option is applicable to the following GC policies: `gencon` and `optavgpause`.

### `classUnloadingThreshold`

         -Xgc:classUnloadingThreshold=<value>

: Where `<value>` is equal to the number of class loaders plus the number of anonymous classes that are loaded since the previous class unloading operation.

: This option sets a threshold that is used to trigger an optional GC class unloading operation in a global GC cycle, irrespective of how the global GC cycle is triggered. The default value is 6.

: This option is applicable to the following GC policies: `gencon`, `optavgpause`, and `optthruput`.

### `concurrentScavenge`

**(64-bit only)**

        -Xgc:concurrentScavenge

: This option supports pause-less garbage collection mode when you use the Generational Concurrent ([`gencon`](xgcpolicy.md#gencon)) garbage collection policy (the default policy). This option cannot be used with any other GC policies.

    If you set this option, the VM attempts to reduce GC pause times for response-time sensitive, large-heap applications. This mode can be enabled with hardware-based support (Linux on IBM Z&reg; and z/OS&reg;) and software-based support (64-bit: Linux on (x86-64, POWER&reg;, IBM Z&reg;) AIX&reg;, macOS&reg;, and z/OS).

    :fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note: Linux on IBM Z and z/OS**

    This option is supported by all generations of IBM Z hardware to enable pause-less GC with two modes of operation: hardware-based and software-based operations. IBM z13&trade; and earlier hardware operates in software-based pause-less GC mode; IBM z14&trade; and later hardware (with supported software) operates in hardware-based mode.

    Hardware-based pause-less GC is supported on IBM z14 and later hardware running the following software:

    Operating systems:

    - z/OS V2R3
    - z/OS V2R2 and [APAR OA51643](https://www.ibm.com/support/docview.wss?uid=isg1OA51643).
    - RHEL 7.5 (minimum kernel level 4.14)
    - Ubuntu 18.04 (minimum kernel level 4.15)

    Hypervisors:

    - IBM z/VM 6.4 with [APAR VM65987](http://www-01.ibm.com/support/docview.wss?uid=isg1VM65987)
    - IBM z/VM 7.1
    - KVM solutions with QEMU 2.10 or later and minimum host kernel level 4.12 (for example, RHEL 7.5 with kernel level 4.14)

    If these requirements are not met, the option is ignored.

    :fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** On z/OS, the virtual storage used might exceed the Java maximum heap size. Set the z/OS memory limit, specified by `ulimit -M`, to a larger value than the maximum heap size.

### `dnssExpectedTimeRatioMaximum`

        -Xgc:dnssExpectedTimeRatioMaximum=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [percentage]   | 5                     |

: The maximum amount of time spent on garbage collection of the nursery area, expressed as a percentage of the overall time for the last three GC intervals.

: This option applies only to the `gencon` GC policy.

### `dnssExpectedTimeRatioMinimum`

        -Xgc:dnssExpectedTimeRatioMinimum=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [percentage]   | 1                     |

: The minimum amount of time spent on garbage collection of the nursery area, expressed as a percentage of the overall time for the last three GC intervals.

: This option applies only to the `gencon` GC policy.

### `dynamicBreadthFirstScanOrdering`

         -Xgc:dynamicBreadthFirstScanOrdering

: This option sets the scan mode for GC operations that evacuate objects in the heap (scavenge operations (`gencon`) and copy forward operations (`balanced`)) to dynamic breadth first mode. This scan mode reflects the method for traversing the object graph and is a variant that adds *partial depth first traversal* on top of the breadth first scan mode. The aim of dynamic breadth first mode is driven by object field hotness. This mode is the default for the `balanced` GC policy.

### `excessiveGCratio`

        -Xgc:excessiveGCratio=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [percentage]   | 95                    |

: where `<value>` is a percentage of total application run time that is not spent in GC.

    The default value is 95, which means that anything over 5% of total application run time spent on GC is deemed excessive. This option can be used only when [`-Xenableexcessivegc`](xenableexcessivegc.md) is set (enabled by default).

: This option can be used with all OpenJ9 GC policies.

### `hierarchicalScanOrdering`

        -Xgc:hierarchicalScanOrdering

: This option sets the scan mode for the scavenge operation (`gencon` GC policy) to hierarchical mode. This mode reflects the method for traversing the object graph and adds partial depth first traversal on top of breadth first scan mode. The aim of hierarchical mode is to minimize object distances. This option is the default for the `gencon` GC policy.

### `minContractPercent`

        -Xgc:minContractPercent=<n>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<n>`         | [percentage]   | -                     |

: The minimum percentage of the heap that can be contracted at any given time.

: This option can be used with all OpenJ9 GC policies.

### `maxContractPercent`

        -Xgc:maxContractPercent=<n>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<n>`         | [percentage]   | -                     |

: The maximum percentage of the heap that can be contracted at any given time. For example, `-Xgc:maxContractPercent=20` causes the heap to contract by as much as 20%.

: This option can be used with all OpenJ9 GC policies.

### `noConcurrentScavenge`

**(64-bit only)**

        -Xgc:noConcurrentScavenge

: This option disables pause-less garbage collection that you might have enabled with the [`-Xgc:concurrentScavenge`](#concurrentscavenge) option when using the default [`gencon`](xgcpolicy.md#gencon) GC policy. This option applies only to the `gencon` GC policy.

    :fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** No concurrent scavenge is the default state, but the `noConcurrentScavenge` option is useful as it will disable concurrent scavenge even if it has been enabled by a previous option; the right-most option always takes precedence.

### `nosynchronousGCOnOOM`

        -Xgc:nosynchronousGCOnOOM

: Setting `-Xgc:nosynchronousGCOnOOM` implies that when heap memory is full your application stops and issues an *out-of-memory* message. The default is [`-Xgc:synchronousGCOnOOM`](#synchronousgconoom).

: This option applies only to the `metronome` GC policy.

### `overrideHiresTimerCheck`

        -Xgc:overrideHiresTimerCheck

: When the VM starts, the GC checks that the operating system can meet the timer resolution requirements for the requested target pause time. Typically, this check correctly identifies operating systems that can deliver adequate time resolution. However, in some cases the operating system provides a more conservative answer than strictly necessary for GC pause time management, which prevents startup. Specifying this parameter causes the GC to ignore the answer returned by the operating system. The VM starts, but GC pause time management remains subject to operating system performance, which might not provide adequate timer resolution.

    :fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** Use this option with caution, and only when you are unable to use a supported operating system.

: This option applies only to the `metronome` GC policy.

### `preferredHeapBase`

**(AIX, Linux, macOS, and Windows only)**

        -Xgc:preferredHeapBase=<address>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [hexadecimal]  | -                     |

: where, `<address>` is the base memory address for the heap. Use this option with the `-Xcompressedrefs` option to allocate the heap you specify with the [`-Xmx`](xms.md) option, in a memory range of your choice. If `-Xcompressedrefs` is not specified, this option has no effect. In the following example, the heap is located at the 4 GB mark, leaving the lowest 4 GB of address space for use by other processes.

        -Xgc:preferredHeapBase=0x100000000

    If the heap cannot be allocated in a contiguous block at the `preferredHeapBase` address you specified, an error occurs detailing a Garbage Collection (GC) allocation failure startup. When the `preferredHeapBase` option is used with the [`-Xlp`](xlp.md) option, the `preferredHeapBase` address must be a multiple of the large page size. If you specify an inaccurate heap base address, the heap is allocated with the default page size.

: This option can be used with all OpenJ9 GC policies.

### `scvNoAdaptiveTenure`

        -Xgc:scvNoAdaptiveTenure

: Turns off the adaptive tenure age in the `gencon` GC policy. The initial age that is set is maintained throughout the run time of the VM. See `scvTenureAge`.

: This option applies only to the `gencon` GC policy.

### `scvTenureAge`

        -Xgc:scvTenureAge=<n>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<n>`         | [1 - 14]       | 10                    |

: Sets the initial scavenger tenure age in the `gencon` GC policy. For more information, see [`gencon` policy (default)](gc.md#gencon-policy-default).

: This option applies only to the `gencon` GC policy.

### `stdGlobalCompactToSatisfyAllocate`

        -Xgc:stdGlobalCompactToSatisfyAllocate

: Prevents the GC from performing a compaction unless absolutely required to satisfy the current allocation failure by removing
the dynamic compaction triggers that look at heap occupancy. This option works only with the following GC policies:

- `gencon`
- `optthruput`
- `optavgpause`

: This option is not supported with the balanced GC policy (`-Xgcpolicy:balanced`) or metronome GC policy (`-Xgcpolicy:metronome`).


### `synchronousGCOnOOM`

        -Xgc:synchronousGCOnOOM

: GC cycles can occur when the Java heap runs out of memory. If there is no more free space in the heap, using `-Xgc:synchronousGCOnOOM` stops your application while GC operations remove unused objects. If free space runs out again, consider decreasing the target utilization to allow GC operations more time to complete. Setting `-Xgc:nosynchronousGCOnOOM` implies that when heap memory is full your application stops and issues an *out-of-memory* message. The default is `-Xgc:synchronousGCOnOOM`.

: This option applies only to the `metronome` GC policy.

### `targetPausetime`

        -Xgc:targetPausetime=N

: Sets the GC pause time, where `N` is the time in milliseconds. When this option is specified, the garbage collector operates with pauses that do not exceed the value specified. If this option is not specified the default pause time is set to 3 milliseconds. For example, running with `-Xgc:targetPausetime=20` causes the garbage collector to pause for no longer than 20 milliseconds during GC operations.

: This option applies only to the `metronome` GC policy.

### `targetUtilization`

        -Xgc:targetUtilization=N

: Sets the application utilization to `N%`; the garbage collector attempts to use at most (100-N)% of each time interval. Reasonable values are in the range of 50-80%. Applications with low allocation rates might be able to run at 90%. The default is 70%.

    In the following example, the maximum size of the heap is set to 30 MB. The garbage collector attempts to use 25% of each time interval because the target utilization for the application is set to 75%.

        java -Xgcpolicy:metronome -Xmx30m -Xgc:targetUtilization=75 Test

: This option applies only to the `metronome` GC policy.

### `tlhIncrementSize`

        -Xgc:tlhIncrementSize=<bytes>

: Sets the increment size of the thread local heap (TLH), which plays a key role in cache allocation. Threads start creating TLHs with a predefined initial size (default 2 KB). On every TLH refresh, the requested size for that thread is increased by an increment (default 4 KB). Use this option to control the increment size.

: This option can be used with all OpenJ9 GC policies.

### `tlhInitialSize`

        -Xgc:tlhInitialSize=<bytes>

: Sets the initial size of the TLH. The default size is 2 KB.

: This option can be used with all OpenJ9 GC policies.

### `tlhMaximumSize`

        -Xgc:tlhMaximumSize=<bytes>

: Sets the maximum size of the TLH. The size of the TLH varies from 512 bytes (768 on 64-bit JVMs) to 128 KB, depending on the allocation rate of the thread.
Larger TLHs can help reduce heap lock contention, but might also reduce heap utilisation and increase heap fragmentation. Typically, when the maximum TLH size is increased, you should also increase the increment size (`-XtlhIncrementSize`) proportionally, so that active threads can reach the maximum requested TLH size more quickly.

: This option can be used with all OpenJ9 GC policies.

### `verboseFormat`

        -Xgc:verboseFormat=<format>

: | Setting       | Value          | Default                                                                            |
  |---------------|----------------|:----------------------------------------------------------------------------------:|
  | `<format>`    | `default`      | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
  |               | `deprecated`   |                                                                                    |


    - `default`: The default verbose garbage collection format for OpenJ9. For more information, see [Verbose garbage collection logs](vgclog.md).
    - `deprecated`: The verbose garbage collection format available in the IBM J9 VM V2.4 and earlier.

: This option does not apply to the `metronome` GC policy. The verbose log format for the `metronome` GC policy is equivalent to `-Xgc:verboseFormat=deprecated`.

### `verbosegcCycleTime`

        -Xgc:verbosegcCycleTime=N

: `N` is the time in milliseconds that the summary information should be logged.

    :fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** The cycle time does not mean that the summary information is logged precisely at that time, but when the last GC event that meets this time criterion passes.

: This option applies only to the `metronome` GC policy.

<!-- ==== END OF TOPIC ==== xgc.md ==== -->
