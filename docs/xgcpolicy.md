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

# -Xgcpolicy


Controls which garbage collection (GC) policy is used for your Java&trade; application.

## Syntax

        -Xgcpolicy:<parameter>

## Parameters

| Parameter                                                                    | Default |
|------------------------------------------------------------------------------|---------|
| [`gencon`](#gencon)                                                          | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| [`balanced`](#balanced-64-bit-only) (64-bit only)                            |         |
| [`metronome`](#metronome-aix-linux-x86-only) (AIX&reg;, Linux&reg; x86 only) |         |
| [`optavgpause`](#optavgpause)                                                |         |
| [`optthruput`](#optthruput)                                                  |         |
| [`nogc`](#nogc)                                                              |         |

For a detailed description of the policies, when to use them, and how they work, see [Garbage Collection policies](gc.md). The following GC policies are available:

### `gencon`

        -Xgcpolicy:gencon

: The generational concurrent policy (default) requires a heap that is divided into two main areas (*nursery* and *tenure*) to manage two generation groups (*new* and *older*). The policy uses a global GC cycle of concurrent *mark-sweep* operations, optionally followed by *compact* operations. The policy also uses a partial GC cycle to run *scavenge* operations on the *nursery* area. The partial cycle helps reduce the frequency and duration of the global GC cycle. Note that *scavenge* is a *stop-the-world* operation, unless `-Xgcpolicy:gencon` is specified with the [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge) option.

: To learn more about this policy, when to use it, and how it works, see [Garbage collection: `gencon` policy](gc.md#gencon-policy-default).


### `balanced` (64-bit only)

        -Xgcpolicy:balanced

: The Balanced policy requires a multi-region heap to manage multiple generations of objects. The policy uses a global GC cycle that involves an incremental concurrent *mark* operation (global mark phase), followed by *stop-the-world* (STW) *sweep* operation. The policy also uses a partial GC cycle to run *copy forward* or *mark-compact* operations. Regions are individually managed to reduce the maximum pause time on large heaps and increase the efficiency of garbage collection. The policy tries to avoid global collections by matching object allocation and survival rates. With the `balanced` policy, the global mark and partial GC cycles interleave. The global STW *sweep* operation runs within the same GC increment as the first partial GC cycle that follows the global mark phase.

: The `balanced` policy also exploits large systems that have Non-Uniform Memory Architecture (NUMA) characteristics (x86 and POWER&trade; platforms only), which might further improve application throughput.

: :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If you are using this GC policy in a Docker container that uses the default `seccomp` Docker profile, you must start the container with `--security-opt seccomp=unconfined` to exploit NUMA characteristics. These options are not required if you are running in Kubernetes because `unconfined` is set by default (see [Seccomp]( https://kubernetes.io/docs/concepts/policy/pod-security-policy/#seccomp)).

: To learn more about this policy, how it works, and when to use it, see [Garbage collection: `balanced` policy](gc.md#balanced-policy).


#### `balanced` defaults and options

The initial heap size is *Xmx/1024*, rounded down to the nearest power of 2, where *Xmx* is the maximum heap size available. You can override this value by specifying the `-Xms` option on the command line.

The following options can also be specified on the command line with `-Xgcpolicy:balanced`:

- `-Xdisableexcessivegc`
- `-Xdisableexplicitgc`
- `-Xenableexcessivegc`
- `-Xgcthreads<number>`
- `-Xgcworkpackets<number>`
- `-Xmaxe<size>`
- `-Xmaxf<percentage>`
- `-Xmaxt<percentage>`
- `-Xmca<size>`
- `-Xmco<size>`
- `-Xmine<size>`
- `-Xminf<percentage>`
- `-Xmint<percentage>`
- `-Xmn<size>`
- `-Xmns<size>`
- `-Xmnx<size>`
- `-Xms<size>`
- `-Xmx<size>`
- `-Xnuma:none`
- `-Xsoftmx<size>`
- `-Xsoftrefthreshold<number>`
- `-Xverbosegclog[:<file> [, <X>,<Y>]]`

The behavior of the following options is different when specified with `-Xgcpolicy:balanced`:

[`-Xcompactgc`](xcompactgc.md) (default)
: Forces compaction in each Global GC cycle.

[`-Xnocompactgc`](xcompactgc.md)
: Disables internal compaction heuristics in Global GC cycles.

[`-Xcompactexplicitgc`](xcompactexplicitgc.md) (default)
: Forces compaction in explicit Global GC cycles, such as those invoked by `System.gc()`. Compaction in implicit Global GC remains optional, triggered by internal heuristics.

[`-Xnocompactexplicitgc`](xcompactexplicitgc.md)
: Disables compaction in explicit Global GC cycles. Compaction in implicit Global GC remains optional, triggered by internal heuristics.

[`-Xgc:targetPausetime`](xgc.md#targetpausetime)
: Uses the specified GC pause time as a soft GC pause time target.

The following options are ignored when specified with `-Xgcpolicy:balanced`:

- `-Xconcurrentbackground<number>`
- `-Xconcurrentlevel<number>`
- `-Xconcurrentslack<size>`
- `-Xconmeter:<soa | loa | dynamic>`
- `-Xdisablestringconstantgc`
- `-Xenablestringconstantgc`
- `-Xloa`
- `-Xloainitial<percentage>`
- `-Xloamaximum<percentage>`
- `-Xloaminimum<percentage>`
- `-Xmo<size>`
- `-Xmoi<size>`
- `-Xmos<size>`
- `-Xmr<size>`
- `-Xmrx<size>`
- `-Xnoloa`


### `optavgpause`

        -Xgcpolicy:optavgpause

: The *optimize for pause time* policy requires a *flat* heap and uses a global GC cycle to run concurrent *mark-sweep* operations, optionally followed by *compact* operations. Pause times are shorter than with `optthruput`, but application throughput is reduced. The impact on throughput occurs because some garbage collection work is taking place in the context of mutator (application) threads, and because GC frequency is increased.

    To learn more about this policy and when to use it, see [Garbage collection: `optavgpause` policy](gc.md#optavgpause-policy).


### `optthruput`

        -Xgcpolicy:optthruput

: The *optimize for throughput* policy requires a *flat* heap and uses a global GC cycle to run *mark-sweep* operations, optionally followed by *compact* operations. Because the application stops during a global GC cycle, long pauses can occur.

    To learn more about this policy, how it works, and when to use it, see [Garbage collection: `optthruput` policy](gc.md#optthruput-policy).


### `metronome` (AIX, Linux x86 only)

        -Xgcpolicy:metronome

: The metronome policy is an incremental, deterministic garbage collector with short pause times. Applications that are dependent on precise response times can take advantage of this technology by avoiding potentially long delays from GC activity. The `metronome` policy is supported on specific hardware and operating system configurations.

    To learn more about this policy, how it works, and when to use it, see [Garbage collection: `metronome` policy](gc.md#metronome-policy).

#### `metronome` options

The following options are specific to the `metronome` GC policy:

- [`-Xgc:nosynchronousGCOnOOM`](xgc.md#nosynchronousgconoom)
- [`-Xgc:overrideHiresTimerCheck`](xgc.md#overridehirestimercheck)
- [`-Xgc:synchronousGCOnOOM`](xgc.md#synchronousgconoom)
- [`-Xgc:targetUtilization`](xgc.md#targetutilization)
- [`-Xgc:verbosegcCycleTime`](xgc.md#verbosegccycletime)

[`-Xgc:targetPausetime`](xgc.md#targetpausetime) option also applies to the `metronome` GC policy. This option applies only to the `metronome` and `balanced` GC policies.

### `nogc`

        -Xgcpolicy:nogc

: This policy handles only memory allocation and heap expansion, but doesn't reclaim any memory. If the available Java heap becomes exhausted, an `OutOfMemoryError` exception is triggered and the VM stops.

    You should be especially careful when using any of the following techniques with `nogc` because memory is never released under this policy:  
    - Finalization  
    - Direct memory access  
    - Weak, soft, and phantom references

: To learn when to use this policy, see [Garbage collection: `nogc` policy](gc.md#nogc-policy).

This policy can also be enabled with the [`-XX:+UseNoGC`](xxusenogc.md) option.

Further details are available at [JEP 318: Epsilon: A No-Op Garbage Collector](https://openjdk.org/jeps/318).



<!-- ==== END OF TOPIC ==== xgcpolicy.md ==== -->
