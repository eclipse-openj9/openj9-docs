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

# -Xgcpolicy


Controls the behavior of the garbage collector by specifying different garbage collection policies.

## Syntax

        -Xgcpolicy:<parameter>

## Parameters

| Parameter                                                                     | Default  |
|-------------------------------------------------------------------------------|----------|
| [`gencon`](#gencon)                                                       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| [`balanced`](#balanced)                                                       |          |
| [`metronome`](#metronome-aix-linux-x86-only) (AIX&reg;, Linux&reg; x86 only)  |          |
| [`optavgpause`](#optavgpause)                                                 |          |
| [`optthruput`](#optthruput)                                                   |          |
| [`nogc`](#nogc)                                                               |          |


See [](gc.md) for information about choosing the appropriate policy. Follow the links in the table for information about individual policies.

Specify the garbage collection policy that you want the OpenJ9 VM to use with the following command-line options:

### `gencon`

        -Xgcpolicy:gencon

: The generational concurrent policy (default) uses a concurrent mark phase combined with generational garbage collection to help minimize the time that is spent in any garbage collection pause. See [`gencon`](gc_gencon.md) for more information about how this policy operates.

### `balanced`

        -Xgcpolicy:balanced

: The balanced policy uses mark, sweep, compact and generational style garbage collection to help avoid problems caused by global garbage collections, particularly compactions. If you are using large systems that have Non-Uniform Memory Architecture (NUMA) characteristics (x86 and POWER&trade; platforms only), the balanced policy might further improve application throughput. See [`balanced`](gc_balanced.md) for more information about how this policy operates.

#### Defaults and options

The initial heap size is *<max>*/1024, rounded down to the nearest power of 2, where *<max>* is the maximum heap size available. You can override this value by specifying the [`-Xms`](xmn.md) option on the command line.

The following options can also be specified on the command line with `-Xgcpolicy:balanced`:

- `-Xalwaysclassgc`
- `-Xclassgc`
- `-Xcompactexplicitgc`
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
- `-Xnoclassgc`
- `-Xnocompactexplicitgc`
- `-Xnuma:none`
- `-Xsoftmx<size>`
- `-Xsoftrefthreshold<number>`
- `-Xverbosegclog[:<file> [, <X>,<Y>]]`

The behavior of the following options is different when specified with `-Xgcpolicy:balanced`:

`-Xcompactgc`
: Compaction occurs when a System.gc() call is received (default). Compaction always occurs on all other collection types.

`-Xnocompactgc`
: Compaction does not occur when a System.gc() call is received. Compaction always occurs on all other collection types.

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
- `-Xnopartialcompactgc` (deprecated)
- `-Xpartialcompactgc` (deprecated)

### `metronome` (AIX, Linux x86 only)

        -Xgcpolicy:metronome

: The metronome policy is an incremental, deterministic garbage collector with short pause times. Applications that are dependent on precise response times can take advantage of this technology by avoiding potentially long delays from garbage collection activity. The metronome policy is supported on specific hardware and operating system configurations. See [`metronome`](gc_metronome.md) for more information about how this policy operates and for further information, see [Using the Metronome Garbage Collector](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_mgc.html).

#### Defaults and options

`-Xgc:synchronousGCOnOOM | -Xgc:nosynchronousGCOnOOM`
: One occasion when garbage collection occurs is when the heap runs out of memory. If there is no more free space in the heap, using `-Xgc:synchronousGCOnOOM` stops your application while garbage collection removes unused objects. If free space runs out again, consider decreasing the target utilization to allow garbage collection more time to complete. Setting `-Xgc:nosynchronousGCOnOOM` implies that when heap memory is full your application stops and issues an out-of-memory message. The default is `-Xgc:synchronousGCOnOOM`.

`-Xnoclassgc`
: Disables class garbage collection. This option switches off garbage collection of storage associated with Java classes that are no longer being used by the OpenJ9 VM. The default behavior is -Xnoclassgc.

`-Xgc:targetPauseTime=N`
: Sets the garbage collection pause time, where N is the time in milliseconds. When this option is specified, the GC operates with pauses that do not exceed the value specified. If this option is not specified the default pause time is set to 3 milliseconds. For example, running with `-Xgc:targetPauseTime=20` causes the GC to pause for no longer than 20 milliseconds during GC operations.

`-Xgc:targetUtilization=N`
: Sets the application utilization to `N%`; the Garbage Collector attempts to use at most (100-N)% of each time interval. Reasonable values are in the range of 50-80%. Applications with low allocation rates might be able to run at 90%. The default is 70%.

    This example shows the maximum size of the heap memory is 30 MB. The garbage collector attempts to use 25% of each time interval because the target utilization for the application is 75%.


      java -Xgcpolicy:metronome -Xmx30m -Xgc:targetUtilization=75 Test


`-Xgc:threads=N`
: Specifies the number of GC threads to run. The default is the number of processor cores available to the process. The maximum value you can specify is the number of processors available to the operating system.

`-Xgc:verboseGCCycleTime=N`
: N is the time in milliseconds that the summary information should be dumped.

    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** The cycle time does not mean that the summary information is dumped precisely at that time, but when the last garbage collection event that meets this time criterion passes.

`-Xmx<size>`
: Specifies the Java heap size. Unlike other garbage collection strategies, the real-time Metronome GC does not support heap expansion. There is not an initial or maximum heap size option. You can specify only the maximum heap size.

### `optavgpause`

        -Xgcpolicy:optavgpause

: The "optimize for pause time" policy uses concurrent mark and concurrent sweep phases. Pause times are shorter than with `optthruput`, but application throughput is reduced because some garbage collection work is taking place while the application is running. See [`optavgpause`](gc_opt.md) for more information about how this policy operates.

### `optthruput`

        -Xgcpolicy:optthruput

: The "optimize for throughput" policy disables the concurrent mark phase. The application stops during global garbage collection, so long pauses can occur. See [`optthruput`](gc_opt.md) for more information about how this policy operates.

### `nogc`

        -Xgcpolicy:nogc

: This policy handles only memory allocation and heap expansion, but doesn't reclaim any memory. However, if the available Java heap becomes exhausted, an `OutOfMemoryError` exception is triggered and the VM stops. Because there is no GC pause and most overheads on allocations are eliminated,The impact on runtime performance is therefore minimized. This policy therefore provides benfits for "garbage-free" applications. See [`nogc`](gc_nogc.md) for more information about how this policy operates.

    This policy can also be enabled with the [`-XX:+UseNoGC`](xxusenogc.md) option.

    Further details are available at [JEP 318: Epsilon: A No-Op Garbage Collector](http://openjdk.java.net/jeps/318).








<!-- ==== END OF TOPIC ==== xgcpolicy.md ==== -->
