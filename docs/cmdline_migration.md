<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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

# Switching to OpenJ9

If you are already familiar with HotSpot command-line options but want the advantages of OpenJ9, the following information will prove helpful. In all cases, check individual topics for minor discrepancies in the way these options might work.

## Compatible options

You can use the following command-line options in OpenJ9, just as you did in Hotspot; you can continue to use the HotSpot option in OpenJ9 without having to change your code:

| Option                                                           | Usage                                                                                                                                        |
|------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| [`-X`](x.md)                                                     | Displays help on nonstandard options.                                                                                                        |
| [`-Xbootclasspath`](xbootclasspath.md)                           | Specifies the search path for bootstrap classes and resources.                                                                               |
| [`-Xcheck:jni`](xcheck.md)                                       | Runs additional checks for JNI functions during VM startup.                                                                                  |
| [`-Xfuture`](xfuture.md)                                         | Turns on strict class-file format checks.                                                                                                    |
| [`-Xint`](xint.md)                                               | Runs an application in interpreted-only mode.                                                                                                |
| [`-Xmn`](xmn.md)                                                 | Sets the initial and maximum size of the new area when using -Xgcpolicy:gencon.                                                              |
| [`-Xms`](xms.md)                                                 | Sets the initial size of the heap. (Equivalent to `-XX:InitialHeapSize`)                                                                     |
| [`-Xmx`](xms.md)                                                 | Specifies the maximum size of the object memory allocation pool. (Equivalent to `-XX:MaxHeapSize`)                                           |
| [`-Xnoclassgc`](xclassgc.md)                                     | Disables class garbage collection (GC).                                                                                                      |
| [`-Xrs`](xrs.md)                                                 | Prevents the OpenJ9 run time environment from handling signals.                                                                              |
| [`-Xss`](xss.md)                                                 | Sets the thread stack size. (Equivalent to `-XX:ThreadStackSize`)                                                                            |
| [`-Xverify:mode`](xverify.md)                                    | Enables or disables the verifier.                                                                                                            |
| [`-XX:[+|-]DisableExplicitGC`](xxdisableexplicitgc.md)           | Enables/disables `System.gc()` calls. (Alias for [`-Xdisableexplicitgc` / `-Xenableexplicitgc`](xenableexplicitgc.md))                       |
| [`-XX:[+|-]HeapDumpOnOutOfMemory`](xxheapdumponoutofmemory.md)   | Enables/disables dumps on out-of-memory conditions.                                                                                          |
| [`-XX:HeapDumpPath`](xxheapdumppath.md)                          | Specifies a directory for all VM dumps including heap dumps, javacores, and system dumps. (Alias for [`-Xdump:directory`](xdump.md#syntax))    |
| [`-XX:MaxDirectMemorySize`](xxmaxdirectmemorysize.md)            | Sets a limit on the amount of memory that can be reserved for all direct byte buffers.                                                       |
| [`-XX:InitialHeapSize`](xxinitialheapsize.md)                    | Sets the initial size of the heap. (Alias for [`-Xms`](xms.md))                                                                              |
| [`-XX:MaxHeapSize`    ](xxinitialheapsize.md)                    | Specifies the maximum size of the object memory allocation pool. (Alias for [`-Xmx`](xms.md))                                                |
| [`-XX:-UseCompressedOops`](xxusecompressedoops.md)               | Disables compressed references in 64-bit JVMs. (See also [`-Xcompressedrefs`](xcompressedrefs.md))                                           |

## Equivalent options

These Hotspot command-line options have equivalents in OpenJ9 that are not specified in the same way, but perform a related function:

| HotSpot Option          | OpenJ9 Option                                    | Usage                                                            |
|-------------------------|--------------------------------------------------|------------------------------------------------------------------|                                                                        
| `-Xcomp`                | [`-Xjit:count=0`](xjit.md#count)**<sup>1</sup>** | `-Xcomp` disables interpreted method invocations.                |
| `-Xgc`                  | [`-Xgcpolicy`](xgcpolicy.md)**<sup>2</sup>**     | Configuring your garbage collection policy.                      |
| `-XX:ParallelGCThreads` | [`-Xgcthreads`](xgcthreads.md)                   | Configure number of GC threads.                                  |
| `-XX:+UseNUMA`          | [`-Xnuma:none`](xnumanone.md)**<sup>3</sup>**    | Controls non-uniform memory architecture (NUMA) awareness.       |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

1. Hotspot uses `-Xcomp` to force compilation of methods on first invocation. However, this option is deprecated. Whilst it can be used for compatibility, using `-Xjit:count=0` is preferred.

2. Hotspot uses `-Xgc` to both select policies and configure them; OpenJ9 uses `-Xgcpolicy` to select policies, reserving `-Xgc` for configuration.

3. In Hotspot, NUMA awareness is turned off by default and is turned on by using the `-XX:+UseNUMA` option. Conversely, the OpenJ9 VM automatically enables NUMA awareness and uses `-Xnuma:none` to turn it *off*.
    - If you were previously using Hotspot in its default mode, you must now explicitly turn off NUMA awareness in OpenJ9.
    - If you are used to using `-XX:+UseNUMA` in Hotspot, you no longer need to explicitly turn on NUMA awareness; it's on by default.

## Equivalent tools

OpenJ9 provides some tools for compatibility with the reference implementation. These tools might differ in behavior from the HotSpot equivalent:

| HotSpot Tool          | OpenJ9 Tool                                    | Usage                                                            |
|-------------------------|--------------------------------------------------|------------------------------------------------------------------|                                                                        
| `jps`                | [`jps`](tool_jps.md)**<sup>1</sup>** | Displays information about running Java<sup>&trade;</sup> processes                |

1. The main differences from HotSpot are as follows: `jps` uses the Attach API, you can use it for the current host only, and there is no `-V` option.

<!-- ==== END OF TOPIC ==== cmdline_migration.md ==== -->
