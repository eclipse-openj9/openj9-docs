<!--
* Copyright (c) 2017, 2026 IBM Corp. and others
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

# Switching to Eclipse OpenJ9

If you are already familiar with HotSpot command-line options but want the advantages of Eclipse OpenJ9&trade;, the following information will prove helpful. In all cases, check individual topics for minor discrepancies in the way these options might work.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** For information about HotSpot equivalences and differences for items other than command-line options, see [New to Eclipse OpenJ9?](openj9_newuser.md)

## Compatible options

You can use the following command-line options in OpenJ9, just as you did in HotSpot; you can continue to use the HotSpot option in OpenJ9 without having to change your code:

| Option                                                           | Usage                                                                                                                                        |
|------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| [`-X`](x.md)                                                     | Displays help on nonstandard options.                                                                                                        |
| [`-Xbootclasspath`](xbootclasspath.md)                           | Specifies the search path for bootstrap classes and resources.                                                                               |
| [`-Xcheck:jni`](xcheck.md)                                       | Runs additional checks for JNI functions during VM startup.                                                                                  |
| [`-Xfuture`](xfuture.md)                                         | Turns on strict class-file format checks.                                                                                                    |
| [`-Xint`](xint.md)                                               | Runs an application in interpreted-only mode.                                                                                                |
| [`-Xlog`](xlog.md)                                               | Some forms of `-Xlog` that enable garbage collection logging are recognized. (Equivalent to [`-Xverbosegclog`](xverbosegclog.md)).
| [`-Xmn`](xmn.md)                                                 | Sets the initial and maximum size of the new area when using -Xgcpolicy:gencon.                                                              |
| [`-Xms`](xms.md)                                                 | Sets the initial size of the heap. (Equivalent to `-XX:InitialHeapSize`)                                                                     |
| [`-Xmx`](xms.md)                                                 | Specifies the maximum size of the object memory allocation pool. (Equivalent to `-XX:MaxHeapSize`)                                           |
| [`-Xnoclassgc`](xclassgc.md)                                     | Disables class garbage collection (GC).                                                                                                      |
| [`-Xrs`](xrs.md)                                                 | Prevents the OpenJ9 run time environment from handling signals.                                                                              |
| [`-Xss`](xss.md)                                                 | Sets the Java&trade; thread stack size. (Equivalent to `-XX:ThreadStackSize`). :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Unlike HotSpot, this option applies only to the Java stack. OpenJ9 has a separate native stack for operating system threads (see [`-Xmso`](xmso.md))  |
| [`-Xverify:mode`](xverify.md)                                    | Enables or disables the verifier.                                                                                                            |
| [`-XX:ConcGCThreads`](xxconcgcthreads.md)                        | Configures the number of GC mutator background threads.                                                                                      |
| [`-XX:[+|-]AlwaysPreTouch`](xxalwayspretouch.md)                 | Enables or disables committing of memory during initial heap inflation or heap expansion.                                                          |
| [`-XX:[+|-]CompactStrings`](xxcompactstrings.md)                 | Enables or disables `String` compression.:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This option is enabled by default in OpenJ9 on Java 11 and later. In the earlier versions, this option is disabled by default.                               |
| ![Start of content that applies to Java 16 plus](cr/java16plus.png) [`-XX:DiagnoseSyncOnValueBasedClasses=<number>`](xxdiagnosesynconvaluebasedclasses.md) | Configure warnings for value-based classes. |
| [`-XX:[+|-]DisableExplicitGC`](xxdisableexplicitgc.md)           | Enables or disables explicit `System.gc()` calls. (Alias for [`-Xdisableexplicitgc` / `-Xenableexplicitgc`](xenableexplicitgc.md))                       |
| [`-XX:[+|-]ExitOnOutOfMemoryError`](xxexitonoutofmemoryerror.md) | Triggers VM shutdown on out-of-memory conditions.                                                                                            |
| [`-XX:[+|-]HeapDumpOnOutOfMemoryError`](xxheapdumponoutofmemoryerror.md)   | Enables or disables dumps on out-of-memory conditions.                                                                                          |
| [`-XX:HeapDumpPath`](xxheapdumppath.md)                          | Specifies a directory for all VM dumps including heap dumps, javacores, and system dumps. (Alias for [`-Xdump:directory`](xdump.md#syntax))  |
| [`-XX:[+|-]IgnoreUnrecognizedVMOptions`](xxignoreunrecognizedvmoptions.md) | Specifies whether to ignore unrecognized top-level VM options. |                                                                    |
| [`-XX:InitialHeapSize`](xxinitialheapsize.md)                    | Sets the initial size of the heap. (Alias for [`-Xms`](xms.md))                                                                              |
| [`-XX:InitialRAMPercentage`](xxinitialrampercentage.md)          | Sets the initial size of the Java heap as a percentage of total memory.                                                                      |
| [`-XX:MaxDirectMemorySize`](xxmaxdirectmemorysize.md)            | Sets a limit on the amount of memory that can be reserved for all direct byte buffers.                                                       |
| [`-XX:MaxHeapSize`    ](xxinitialheapsize.md)                    | Specifies the maximum size of the object memory allocation pool. (Alias for [`-Xmx`](xms.md))                                                |
| [`-XX:MaxRAMPercentage`](xxinitialrampercentage.md)              | Sets the maximum size of the Java heap as a percentage of total memory.                                                                      |
| [`-XX:OnOutOfMemoryError`](xxonoutofmemoryerror.md)              | Runs specified commands when a `java.lang.OutOfMemoryError` is thrown. (Equivalent to `-Xdump:tool:events=systhrow,filter=java/lang/OutOfMemoryError,exec=`) |
| [`-XX:ParallelCMSThreads`](xxparallelcmsthreads.md)              | Configures the number of GC mutator background threads.                                                                                      |
| [`-XX:ParallelGCThreads`](xxparallelgcthreads.md)                | Configures the number of GC threads.                                                                                                         |
| [`-XX:[+|-]PrintCodeCache`](xxprintcodecache.md)                 | Prints code cache usage when the application exits.                                                                                          |
| [`-XX:[+|-]ShowHiddenFrames`](xxshowhiddenframes.md)             | Specifies whether generated hidden `MethodHandle` frames are displayed in a stack trace. :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Unlike HotSpot, this option doesn't require the `+UnlockDiagnosticVMOptions` option.                                                                                         |
| [`-XX:[+|-]UseCompressedOops`](xxusecompressedoops.md)           | Disables compressed references in 64-bit JVMs. (See also [`-Xcompressedrefs`](xcompressedrefs.md))                                           |
| [`-XX:[+|-]UseContainerSupport`](xxusecontainersupport.md)       | Sets a larger fraction of memory to the Java heap when the VM detects that it is running in a container.                                     |



## Equivalent options

These HotSpot command-line options have equivalents in OpenJ9 that are not specified in the same way, but perform a related function:

| HotSpot Option          | OpenJ9 Option                                    | Usage                                                     |
|-------------------------|--------------------------------------------------|-----------------------------------------------------------|
| `-Xcomp`                | [`-Xjit:count=0`](xjit.md#count)**<sup>1</sup>** | `-Xcomp` disables interpreted method invocations.         |
| `-Xgc`                  | [`-Xgcpolicy`](xgcpolicy.md)**<sup>2</sup>**     | Configuring your garbage collection policy.               |
| `-XX:+UseNUMA`          | [`-Xnuma:none`](xnumanone.md)**<sup>3</sup>**    | Controls non-uniform memory architecture (NUMA) awareness.|

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

1. HotSpot uses `-Xcomp` to force compilation of methods on first invocation. However, this option is deprecated. Whilst it can be used for compatibility, using `-Xjit:count=0` is preferred.

2. HotSpot uses `-Xgc` to both select policies and configure them; OpenJ9 uses `-Xgcpolicy` to select policies, reserving `-Xgc` for configuration.

3. In HotSpot, NUMA awareness is turned off by default and is turned on by using the `-XX:+UseNUMA` option. Conversely, the OpenJ9 VM automatically enables NUMA awareness and uses `-Xnuma:none` to turn it *off*.
    - If you were previously using HotSpot in its default mode, you must now explicitly turn off NUMA awareness in OpenJ9.
    - If you are used to using `-XX:+UseNUMA` in HotSpot, you no longer need to explicitly turn on NUMA awareness; it's on by default.


## Creating compatible behavior

You can set the following options to make OpenJ9 behave in the same way as HotSpot.

| Option                                      | Usage                                                           |
|---------------------------------------------|-----------------------------------------------------------------|
| ![Start of content that applies to Java 8 (LTS)](cr/java8.png) ![Start of content that applies to Java 11 (LTS)](cr/java11.png) [`-Djava.lang.string.substring.nocopy=true`](djavalangstringsubstringnocopy.md) |  Avoid String sharing by `String.substring()`. ![End of content that applies to Java 8 and 11 (LTS)](cr/java_close_lts.png)|
| [`-Xnuma:none`](xnumanone.md)               | Disable non-uniform memory architecture (NUMA) awareness.       |
| [`-XX:[+|-]HandleSIGABRT`](xxhandlesigabrt.md)    | Force handling of SIGABRT signals to be compatible with HotSpot. |


## Compatible environment variables

The `JAVA_TOOL_OPTIONS` environment variable can be used to set command line options as described in [OpenJ9 command-line options](cmdline_specifying.md) and [Environment variables](env_var.md).

<!-- ==== END OF TOPIC ==== cmdline_migration.md ==== -->
