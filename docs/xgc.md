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

# -Xgc

Options that change the behavior of the Garbage Collector (GC).

## Syntax

        -Xgc:<parameter>

## Parameters

| Parameter                                                       | Effect                                                                                                  |
|-----------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| [`concurrentScavenge`          ](#concurrentscavenge          ) | Enables a garbage collection (GC) mode with less pause times (Linux on IBM Z<sup>&reg;</sup> and z/OS<sup>&reg;</sup> only).|
| [`dnssExpectedTimeRatioMaximum`](#dnssexpectedtimeratiomaximum) | Sets the maximum time to spend on GC of the nursery area.                                               |
| [`dnssExpectedTimeRatioMinimum`](#dnssexpectedtimeratiominimum) | Sets the minimum time to spend on GC of the nursery area.                                               |
| [`excessiveGCratio`            ](#excessivegcratio            ) | Sets a boundary value beyond which GC is deemed to be excessive.                                        |
| [`minContractPercent`          ](#mincontractpercent          ) | Sets the minimum percentage of the heap that can be contracted at any given time.                       |
| [`maxContractPercent`          ](#maxcontractpercent          ) | Sets the maximum percentage of the heap that can be contracted at any given time.                       |
| [`overrideHiresTimerCheck`     ](#overridehirestimercheck     ) | Overrides GC operating system checks for timer resolution.                                              |
| [`preferredHeapBase`           ](#preferredheapbase           ) | Sets a memory range for the Java<sup>&trade;</sup> heap. (AIX<sup>&reg;</sup>, Linux<sup>&trade;</sup>, and Windows<sup>&trade;</sup> only) |
| [`scvNoAdaptiveTenure`         ](#scvnoadaptivetenure         ) | Turns off the adaptive tenure age in the generational concurrent GC policy.                             |
| [`scvTenureAge`                ](#scvtenureage                ) | Sets the initial scavenger tenure age in the generational concurrent GC policy.                         |
| [`verboseFormat`               ](#verboseformat               ) | Sets the verbose GC format.                                                                             |

### `concurrentScavenge`

**(Linux on Z and z/OS only)**

        -Xgc:concurrentScavenge

: This option is supported only on the 64-bit VM with the Generational Concurrent (`gencon`) garbage collection policy. When this mode is enabled, the VM attempts to reduce GC pause-times for response-time sensitive, large heap applications. This option is supported only on IBM z14<sup>&trade;</sup> hardware and the following software:


Operating systems:

- z/OS V2R3
- z/OS V2R2 and <i class="fa fa-external-link" aria-hidden="true"></i> [APAR OA51643](http://www.ibm.com/support/docview.wss?uid=isg1OA51643).
- RHEL 7.5 (minimum kernel level 4.14)
- Ubuntu 18.04 (minimum kernel level 4.15)

Hypervisors:

- IBM z/VM 6.4 with <i class="fa fa-external-link" aria-hidden="true"></i> [APAR VM65987](http://www-01.ibm.com/support/docview.wss?uid=isg1VM65987)
- KVM solutions with QEMU 2.10 or later and minimum host kernel level 4.12 (for example, RHEL 7.5 with kernel level 4.14)

If these requirements are not met, the option is ignored.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span class="sr-only">Note</span> **Note:** On z/OS, the virtual storage used might exceed the Java maximum heap size. Set the z/OS memory limit, specified by **ulimit -M**, to a larger value than the maximum heap size.

### `dnssExpectedTimeRatioMaximum`

        -Xgc:dnssExpectedTimeRatioMaximum=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [percentage]   | 5                     |

: The maximum amount of time spent on garbage collection of the nursery area, expressed as a percentage of the overall time for the last three GC intervals.

### `dnssExpectedTimeRatioMinimum`

        -Xgc:dnssExpectedTimeRatioMinimum=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [percentage]   | 1                     |

: The minimum amount of time spent on garbage collection of the nursery area, expressed as a percentage of the overall time for the last three GC intervals.

### `excessiveGCratio`

        -Xgc:excessiveGCratio=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [percentage]   | 95                    |

: where `<value>` is a percentage of total application run time that is not spent in GC.

    The default value is 95, which means that anything over 5% of total application run time spent on GC is deemed excessive. This option can be used only when [`-Xenableexcessivegc`](xenableexcessivegc.md) is set.

### `minContractPercent`

        -Xgc:minContractPercent=<n>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<n>`         | [percentage]   | -                     |

: The minimum percentage of the heap that can be contracted at any given time.

### `maxContractPercent`

        -Xgc:maxContractPercent=<n>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<n>`         | [percentage]   | -                     |

: The maximum percentage of the heap that can be contracted at any given time. For example, `-Xgc:maxContractPercent=20` causes the heap to contract by as much as 20%.

### `overrideHiresTimerCheck`

        -Xgc:overrideHiresTimerCheck

: When the VM starts, the GC checks that the operating system can meet the timer resolution requirements for the requested target pause time. Typically, this check correctly identifies operating systems that can deliver adequate time resolution. However, in some cases the operating system provides a more conservative answer than strictly necessary for GC pause time management, which prevents startup. Specifying this parameter causes the GC to ignore the answer returned by the operating system. The VM starts, but GC pause time management remains subject to operating system performance, which might not provide adequate timer resolution.

    <i class="fa fa-pencil-square-o" aria-hidden="true"></i><span class="sr-only">Note</span> **Note:** Use this option with caution, and only when you are unable to use a supported operating system.

### `preferredHeapBase`

**(AIX, Linux, Windows only)**

        -Xgc:preferredHeapBase=<address>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | [hexadecimal]  | -                     |

: where, `<address>` is the base memory address for the heap. Use this option with the `-Xcompressedrefs` option to allocate the heap you specify with the [`-Xmx`](xms.md) option, in a memory range of your choice. If `-Xcompressedrefs` is not specified, this option has no effect. In the following example, the heap is located at the 4 GB mark, leaving the lowest 4 GB of address space for use by other processes.

        :::java
        -Xgc:preferredHeapBase=0x100000000

    If the heap cannot be allocated in a contiguous block at the `preferredHeapBase` address you specified, an error occurs detailing a Garbage Collection (GC) allocation failure startup. When the `preferredHeapBase` option is used with the [`-Xlp`](xlp.md) option, the `preferredHeapBase` address must be a multiple of the large page size. If you specify an inaccurate heap base address, the heap is allocated with the default page size.

### `scvNoAdaptiveTenure`

        -Xgc:scvNoAdaptiveTenure

: Turns off the adaptive tenure age in the generational concurrent GC policy. The initial age that is set is maintained throughout the run time of the VM. See `scvTenureAge`.

### `scvTenureAge`

        -Xgc:scvTenureAge=<n>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<n>`         | [1 - 14]       | 10                    |

: Sets the initial scavenger tenure age in the generational concurrent GC policy. For more information, see [Tenure age](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_generational_tenure.html).

### `verboseFormat`

        -Xgc:verboseFormat=<format>

: | Setting       | Value          | Default                                                                            |
  |---------------|----------------|:----------------------------------------------------------------------------------:|
  | `<format>`    | `default`      | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Default</span> |
  |               | `deprecated`   |                                                                                    |


    - `default`: The default verbose garbage collection format for OpenJ9. For more information, see <i class="fa fa-external-link" aria-hidden="true"></i> [Verbose garbage collection logging](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_pd_verbosegc.html).

    - `deprecated`: The verbose garbage collection format available in Version 6 and earlier releases of the IBM SDK. For more information, see the [IBM<sup>&reg;</sup> SDK, Java Technology Edition, Version 6 Diagnostic Guide](http://www.ibm.com/support/knowledgecenter/SSYKE2_6.0.0/com.ibm.java.doc.diagnostics.60/homepage/plugin-homepage-java.html).



<!-- ==== END OF TOPIC ==== xgc.md ==== -->
