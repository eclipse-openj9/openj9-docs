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

# Java dump

Java dumps, sometimes referred to as *Java cores*, are produced when the VM ends unexpectedly because of an operating system signal, `OutOfMemoryError` exception, or a user-initiated keystroke combination. You can also generate a Java dump by calling the Dump API programmatically from your application or specifying the `-Xdump:java` option on the command line.

Java dumps summarize the state of the VM when the event occurs, with most of the information relating to components of the VM.
The file is made up of a number of sections that provide different types of information. The information that follows describes
each section and provides examples to help you interpret the data.

## TITLE

The first section of the Java dump file provides information about the event that triggered the production of the dump.
In the following example you can see that a `vmstop` event triggered the dump at a specified date and time.

```
0SECTION       TITLE subcomponent dump routine
NULL           ===============================
1TICHARSET     UTF-8
1TISIGINFO     Dump Event "vmstop" (00000002) Detail "#0000000000000000" received
1TIDATETIME    Date: 2018/08/30 at 21:55:47:607
1TINANOTIME    System nanotime: 22012355276134
1TIFILENAME    Javacore filename:    /home/doc-javacore/javacore.20180830.215547.30285.0001.txt
1TIREQFLAGS    Request Flags: 0x81 (exclusive+preempt)
1TIPREPSTATE   Prep State: 0x106 (vm_access+exclusive_vm_access+trace_disabled)
```

## GPINFO

The GPINFO section provides general information about the system that the JVM is running on. The following example is taken
from a Java dump that was generated on a Linux system.

```
NULL           ------------------------------------------------------------------------
0SECTION       GPINFO subcomponent dump routine
NULL           ================================
2XHOSLEVEL     OS Level         : Linux 3.10.0-862.11.6.el7.x86_64
2XHCPUS        Processors -
3XHCPUARCH       Architecture   : amd64
3XHNUMCPUS       How Many       : 4
3XHNUMASUP       NUMA is either not supported or has been disabled by user
NULL           
1XHERROR2      Register dump section only produced for SIGSEGV, SIGILL or SIGFPE.
NULL           
```

The content of this section can vary, depending on the cause of the dump. For example, if the dump was caused by a
general protection fault (gpf), the library in which the crash occurred is also recorded, together with a value shown
as `VM flags`. This value can provide some clues about which component of the VM might have been involved. Look for the
following line in the output:

```
1XHFLAGS       VM flags:0000000000000000
```

The hexadecimal number recorded for `VM flags` ends in MSSSS, where M is the VM component and SSSS is component-specific code as shown in the following table:

| Component     | Code value |
|---------------|------------|
| INTERPRETER   | 0x10000    |
| GC            | 0x20000    |
| GROW_STACK    | 0x30000    |
| JNI           | 0x40000    |
| JIT_CODEGEN   | 0x50000    |
| BCVERIFY      | 0x60000    |
| RTVERIFY      | 0x70000    |
| SHAREDCLASSES | 0x80000    |

A value of `0000000000000000` (0x00000) indicates that a crash occurred outside of the VM.

## ENVINFO

This section contains useful information about the environment in which the crash took place, including the following data:

- Java version (`1CIJAVAVERSION`)
- OpenJ9 VM and subcomponent version information (`1CIVMVERSION`, `1CIJ9VMVERSION`, `1CIJITVERSION`, `1CIOMRVERSION`, `1CIJCLVERSION`)
- VM start time (`1CISTARTTIME`) and process information (`1CIPROCESSID`)
- Java home (`1CIJAVAHOMEDIR`) and DLL (`1CIJAVADLLDIR`) directories
- User arguments passed on the command line (`1CIUSERARG`)
- User limits imposed by the system (`1CIUSERLIMITS`)
- Environment variables in place (`1CIENVVARS`)
- System information (`1CISYSINFO`)
- CPU information (`1CICPUINFO`)
<!--- Control group (Cgroup) information (`1CICGRPINFO`)-->

For clarity, the following example shows a shortened version of this section, where `...` indicates that lines are removed:

```
NULL           ------------------------------------------------------------------------
0SECTION       ENVINFO subcomponent dump routine
NULL           =================================
1CIJAVAVERSION JRE 9 Linux amd64-64 (build 9.0.4-internal+0-adhoc..openj9-openjdk-jdk9)
1CIVMVERSION   20180830_000000
1CIJ9VMVERSION 8e7c6ec
1CIJITVERSION  8e7c6ec
1CIOMRVERSION  553811b_CMPRSS
1CIJCLVERSION  ec1d223 based on jdk-9.0.4+12
1CIJITMODES    JIT enabled, AOT enabled, FSD disabled, HCR enabled
1CIRUNNINGAS   Running as a standalone JVM
1CIVMIDLESTATE VM Idle State: ACTIVE
1CISTARTTIME   JVM start time: 2018/08/30 at 21:55:47:387
1CISTARTNANO   JVM start nanotime: 22012135233549
1CIPROCESSID   Process ID: 30285 (0x764D)
1CICMDLINE     [not available]
1CIJAVAHOMEDIR Java Home Dir:   /home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk
1CIJAVADLLDIR  Java DLL Dir:    /home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk/bin
1CISYSCP       Sys Classpath:   
1CIUSERARGS    UserArgs:
2CIUSERARG               -Xoptionsfile=/home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk/lib/options.default
...
NULL
1CIUSERLIMITS  User Limits (in bytes except for NOFILE and NPROC)
NULL           ------------------------------------------------------------------------
NULL           type                            soft limit           hard limit
2CIUSERLIMIT   RLIMIT_AS                        unlimited            unlimited
2CIUSERLIMIT   RLIMIT_CORE                              0            unlimited
2CIUSERLIMIT   RLIMIT_CPU                       unlimited            unlimited
2CIUSERLIMIT   RLIMIT_DATA                      unlimited            unlimited
2CIUSERLIMIT   RLIMIT_FSIZE                     unlimited            unlimited
2CIUSERLIMIT   RLIMIT_LOCKS                     unlimited            unlimited
2CIUSERLIMIT   RLIMIT_MEMLOCK                       65536                65536
2CIUSERLIMIT   RLIMIT_NOFILE                         4096                 4096
2CIUSERLIMIT   RLIMIT_NPROC                          4096                30592
2CIUSERLIMIT   RLIMIT_RSS                       unlimited            unlimited
2CIUSERLIMIT   RLIMIT_STACK                       8388608            unlimited
2CIUSERLIMIT   RLIMIT_MSGQUEUE                     819200               819200
2CIUSERLIMIT   RLIMIT_NICE                              0                    0
2CIUSERLIMIT   RLIMIT_RTPRIO                            0                    0
2CIUSERLIMIT   RLIMIT_SIGPENDING                    30592                30592
NULL
1CIENVVARS     Environment Variables
NULL           ------------------------------------------------------------------------
2CIENVVAR      XDG_VTNR=1
2CIENVVAR      SSH_AGENT_PID=2653
...
NULL           
1CISYSINFO     System Information
NULL           ------------------------------------------------------------------------
2CISYSINFO     /proc/sys/kernel/core_pattern = core
2CISYSINFO     /proc/sys/kernel/core_uses_pid = 1
NULL          
1CICPUINFO     CPU Information
NULL           ------------------------------------------------------------------------
2CIPHYSCPU     Physical CPUs: 4
2CIONLNCPU     Online CPUs: 4
2CIBOUNDCPU    Bound CPUs: 4
2CIACTIVECPU   Active CPUs: 0
2CITARGETCPU   Target CPUs: 4
```

<!--
NULL 			
2CICONTINFO    Running in container : FALSE
2CICGRPINFO    JVM support for cgroups enabled : FALSE

1CICGRPINFO    Cgroup Information
NULL           ------------------------------------------------------------------------
2CICGRPINFO    subsystem : cpu
2CICGRPINFO    cgroup name : /
2CICGRPINFO		CPU Period : 100000 microseconds
2CICGRPINFO		CPU Quota : Not Set
2CICGRPINFO		CPU Shares : 1024
2CICGRPINFO		Period intervals elapsed count : 0
2CICGRPINFO		Throttled count : 0
2CICGRPINFO		Total throttle time : 0 nanoseconds
2CICGRPINFO    subsystem : memory
2CICGRPINFO    cgroup name : /
2CICGRPINFO		Memory Limit : Not Set
2CICGRPINFO		Memory + Swap Limit : Not Set
2CICGRPINFO		Memory Usage : 5485015040 bytes
2CICGRPINFO		Memory + Swap Usage : 5486088192 bytes
2CICGRPINFO		Memory Max Usage : 2933260288 bytes
2CICGRPINFO		Memory + Swap Max Usage : 2935902208 bytes
2CICGRPINFO		Memory limit exceeded count : 0
2CICGRPINFO		Memory + Swap limit exceeded count : 0
2CICGRPINFO		OOM Killer Disabled : 0
2CICGRPINFO		Under OOM : 0
2CICGRPINFO    subsystem : cpuset
2CICGRPINFO    cgroup name : /
2CICGRPINFO		CPU exclusive : 1
2CICGRPINFO		Mem exclusive : 1
2CICGRPINFO		CPUs : 0-3
2CICGRPINFO		Mems : 0
NULL           
-->

## NATIVEMEMINFO

This section records information about native memory that is requested by using library functions such as `malloc()` and `mmap()`.
Values are provided as a breakdown, per component, indicating the total number of bytes allocated and the number of native memory allocations.
In the following example, 4,682,840 bytes of native memory are allocated (but not yet freed) to VM Classes, which corresponds to 141 allocations.

```
NULL           ------------------------------------------------------------------------
0SECTION       NATIVEMEMINFO subcomponent dump routine
NULL           =================================
0MEMUSER
1MEMUSER       JRE: 2,569,088,312 bytes / 4653 allocations
1MEMUSER       |
2MEMUSER       +--VM: 2,280,088,336 bytes / 2423 allocations
2MEMUSER       |  |
3MEMUSER       |  +--Classes: 4,682,840 bytes / 141 allocations
2MEMUSER       |  |
3MEMUSER       |  +--Memory Manager (GC): 2,054,966,784 bytes / 433 allocations
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Java Heap: 2,014,113,792 bytes / 1 allocation
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Other: 40,852,992 bytes / 432 allocations
2MEMUSER       |  |
3MEMUSER       |  +--Threads: 10,970,016 bytes / 156 allocations
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Java Stack: 197,760 bytes / 16 allocations
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Native Stack: 10,616,832 bytes / 17 allocations
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Other: 155,424 bytes / 123 allocations
2MEMUSER       |  |
3MEMUSER       |  +--Trace: 180,056 bytes / 263 allocations
2MEMUSER       |  |
3MEMUSER       |  +--JVMTI: 17,776 bytes / 13 allocations
2MEMUSER       |  |
3MEMUSER       |  +--JNI: 36,184 bytes / 52 allocations
2MEMUSER       |  |
3MEMUSER       |  +--Port Library: 208,179,632 bytes / 72 allocations
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Unused <32bit allocation regions: 208,168,752 bytes / 1 allocation
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Other: 10,880 bytes / 71 allocations
2MEMUSER       |  |
3MEMUSER       |  +--Other: 1,055,048 bytes / 1293 allocations
1MEMUSER       |
2MEMUSER       +--JIT: 288,472,816 bytes / 140 allocations
2MEMUSER       |  |
3MEMUSER       |  +--JIT Code Cache: 268,435,456 bytes / 1 allocation
2MEMUSER       |  |
3MEMUSER       |  +--JIT Data Cache: 2,097,216 bytes / 1 allocation
2MEMUSER       |  |
3MEMUSER       |  +--Other: 17,940,144 bytes / 138 allocations
1MEMUSER       |
2MEMUSER       +--Class Libraries: 13,432 bytes / 25 allocations
2MEMUSER       |  |
3MEMUSER       |  +--VM Class Libraries: 13,432 bytes / 25 allocations
3MEMUSER       |  |  |
4MEMUSER       |  |  +--sun.misc.Unsafe: 3,184 bytes / 13 allocations
4MEMUSER       |  |  |  |
5MEMUSER       |  |  |  +--Direct Byte Buffers: 1,056 bytes / 12 allocations
4MEMUSER       |  |  |  |
5MEMUSER       |  |  |  +--Other: 2,128 bytes / 1 allocation
3MEMUSER       |  |  |
4MEMUSER       |  |  +--Other: 10,248 bytes / 12 allocations
1MEMUSER       |
2MEMUSER       +--Unknown: 513,728 bytes / 2065 allocations
NULL           
```

This section does not record memory that is allocated by application or JNI code and is typically a little less than the
value recorded by operating system tools.

## MEMINFO

This section relates to memory management, providing a breakdown of memory usage in the VM for the object heap,
internal memory, memory used for classes, the JIT code cache, and JIT data cache in decimal and hexadecimal format.
You can also find out which garbage collection policy is in use when the dump is produced.  

The object memory area (`1STHEAPTYPE`) records each memory region in use, its start and end address, and region size.
Further information is recorded about the memory segments used for internal memory, class memory, the JIT code cache and JIT data cache (`1STSEGMENT`).
This information includes the address of the segment control data structure, the start and end address of the native memory segment, as well as
the segment size.

For clarity, the following example shows a shortened version of this section, where `...` indicates that lines are removed:

```
NULL           ------------------------------------------------------------------------
0SECTION       MEMINFO subcomponent dump routine
NULL           =================================
NULL           
1STHEAPTYPE    Object Memory
NULL           id                 start              end                size               space/region
1STHEAPSPACE   0x00007FF4F00744A0         --                 --                 --         Generational
1STHEAPREGION  0x00007FF4F0074CE0 0x0000000087F40000 0x0000000088540000 0x0000000000600000 Generational/Tenured Region
1STHEAPREGION  0x00007FF4F0074930 0x00000000FFE00000 0x00000000FFF00000 0x0000000000100000 Generational/Nursery Region
1STHEAPREGION  0x00007FF4F0074580 0x00000000FFF00000 0x0000000100000000 0x0000000000100000 Generational/Nursery Region
NULL
1STHEAPTOTAL   Total memory:                     8388608 (0x0000000000800000)
1STHEAPINUSE   Total memory in use:              2030408 (0x00000000001EFB48)
1STHEAPFREE    Total memory free:                6358200 (0x00000000006104B8)
NULL
1STSEGTYPE     Internal Memory
NULL           segment            start              alloc              end                type       size
1STSEGMENT     0x00007FF4F004CBC8 0x00007FF4CD33C000 0x00007FF4CD33C000 0x00007FF4CE33C000 0x01000440 0x0000000001000000
1STSEGMENT     0x00007FF4F004CB08 0x00007FF4DE43D030 0x00007FF4DE517770 0x00007FF4DE53D030 0x00800040 0x0000000000100000
NULL
1STSEGTOTAL    Total memory:                    17825792 (0x0000000001100000)
1STSEGINUSE    Total memory in use:               894784 (0x00000000000DA740)
1STSEGFREE     Total memory free:               16931008 (0x00000000010258C0)
NULL           
1STSEGTYPE     Class Memory
NULL           segment            start              alloc              end                type       size
1STSEGMENT     0x00007FF4F03B5638 0x0000000001053D98 0x000000000105BD98 0x000000000105BD98 0x00010040 0x0000000000008000
1STSEGMENT     0x00007FF4F03B5578 0x0000000001048188 0x0000000001050188 0x0000000001050188 0x00010040 0x0000000000008000
...
NULL
1STSEGTOTAL    Total memory:                     3512520 (0x00000000003598C8)
1STSEGINUSE    Total memory in use:              3433944 (0x00000000003465D8)
1STSEGFREE     Total memory free:                  78576 (0x00000000000132F0)
NULL           
1STSEGTYPE     JIT Code Cache
NULL           segment            start              alloc              end                type       size
1STSEGMENT     0x00007FF4F00961F8 0x00007FF4CE43D000 0x00007FF4CE445790 0x00007FF4DE43D000 0x00000068 0x0000000010000000
NULL
1STSEGTOTAL    Total memory:                   268435456 (0x0000000010000000)
1STSEGINUSE    Total memory in use:                34704 (0x0000000000008790)
1STSEGFREE     Total memory free:              268400752 (0x000000000FFF7870)
1STSEGLIMIT    Allocation limit:               268435456 (0x0000000010000000)
NULL           
1STSEGTYPE     JIT Data Cache
NULL           segment            start              alloc              end                type       size
1STSEGMENT     0x00007FF4F0096668 0x00007FF4CC553030 0x00007FF4CC753030 0x00007FF4CC753030 0x00000048 0x0000000000200000
NULL
1STSEGTOTAL    Total memory:                     2097152 (0x0000000000200000)
1STSEGINUSE    Total memory in use:              2097152 (0x0000000000200000)
1STSEGFREE     Total memory free:                      0 (0x0000000000000000)
1STSEGLIMIT    Allocation limit:               402653184 (0x0000000018000000)
NULL           
1STGCHTYPE     GC History  
NULL           
```
In the example, the GC History (`1STGCHTYPE`) section is blank. This section is populated if a garbage collection cycle occurred in
a VM that is being diagnosed with the trace facility.

## LOCKS

This section of the Java dump provides information about locks, which protect shared resources from being accessed by more than one entity at a time. The information is essential in a deadlock situation, where two threads attempt to synchronize on an object and lock an instance of a class. Precise information is recorded about the threads that are causing the problem, which enables you to identify the root cause.

The following example shows a typical LOCKS section, where no deadlocks existed at the time the dump was triggered. For clarity, the following example shows a shortened version of this section, where `...` indicates that lines are removed:

```
NULL           ------------------------------------------------------------------------
0SECTION       LOCKS subcomponent dump routine
NULL           ===============================
NULL           
1LKPOOLINFO    Monitor pool info:
2LKPOOLTOTAL     Current total number of monitors: 3
NULL           
1LKMONPOOLDUMP Monitor Pool Dump (flat & inflated object-monitors):
2LKMONINUSE      sys_mon_t:0x00007FF4B0001D78 infl_mon_t: 0x00007FF4B0001DF8:
3LKMONOBJECT       java/lang/ref/ReferenceQueue@0x00000000FFE26A10: <unowned>
3LKNOTIFYQ            Waiting to be notified:
3LKWAITNOTIFY            "Common-Cleaner" (J9VMThread:0x0000000000FD0100)
NULL           
1LKREGMONDUMP  JVM System Monitor Dump (registered monitors):
2LKREGMON          Thread global lock (0x00007FF4F0004FE8): <unowned>
2LKREGMON          &(PPG_mem_mem32_subAllocHeapMem32.monitor) lock (0x00007FF4F0005098): <unowned>
2LKREGMON          NLS hash table lock (0x00007FF4F0005148): <unowned>
...
NULL           
```

## THREADS

The THREADS section of a Java dump file provides summary information about the VM thread pool and detailed information about Java threads, native threads, and stack traces. Understanding the content of this section can help you diagnose problems that are caused by blocked or waiting threads.

A Java thread runs on a native thread. Several lines are recorded for each Java thread in the `Thread Details` subsection, which include the following key pieces of information:

- `3XMTHREADINFO`: The thread name, address information for the VM thread structures and Java thread object, the thread state, and thread priority.
- `3XMJAVALTHREAD`: The Java thread ID and daemon status from the thread object.
- `3XMTHREADINFO1`: The native operating system thread ID, priority, scheduling policy, internal VM thread state, and VM thread flags.
- `3XMTHREADINFO2`: The native stack address range.
- `3XMTHREADINFO3`: Java callstack information (`4XESTACKTRACE`) or Native call stack information (`4XENATIVESTACK`).
- `5XESTACKTRACE`: This line indicates whether locks were taken by a specific method.

Java thread priorities are mapped to operating system priority values. Thread states are shown in the following table:

| Thread state value | Status         | Description                                     |
|--------------------|----------------|-------------------------------------------------|
| R                  | Runnable       | The thread is able to run                       |
| CW                 | Condition Wait | The thread is waiting                           |
| S                  | Suspended      | The thread is suspended by another thread        |
| Z                  | Zombie         | The thread is destroyed                         |
| P                  | Parked         | The thread is parked by `java.util.concurrent`  |
| B                  | Blocked        | The thread is waiting to obtain a lock          |

For threads that are parked (P), blocked (B), or waiting (CW), an additional line (`3XMTHREADBLOCK`) is included in the output that shows what the thread is parked on, blocked on, or waiting for.

For clarity, the following example shows a shortened version of a typical THREADS section, where `...` indicates that lines are removed:


```
NULL           ------------------------------------------------------------------------
0SECTION       THREADS subcomponent dump routine
NULL           =================================
NULL
1XMPOOLINFO    JVM Thread pool info:
2XMPOOLTOTAL       Current total number of pooled threads: 18
2XMPOOLLIVE        Current total number of live threads: 16
2XMPOOLDAEMON      Current total number of live daemon threads: 15
NULL           
1XMTHDINFO     Thread Details
NULL           
3XMTHREADINFO      "JIT Diagnostic Compilation Thread-7 Suspended" J9VMThread:0x0000000000EFC500, omrthread_t:0x00007FF4F00A77E8, java/lang/Thread:0x00000000FFE97480, state:R, prio=10
3XMJAVALTHREAD            (java/lang/Thread getId:0xA, isDaemon:true)
3XMTHREADINFO1            (native thread ID:0x7657, native priority:0xB, native policy:UNKNOWN, vmstate:CW, vm thread flags:0x00000081)
3XMTHREADINFO2            (native stack address range from:0x00007FF4CCC36000, to:0x00007FF4CCD36000, size:0x100000)
3XMCPUTIME               CPU usage total: 0.000037663 secs, current category="JIT"
3XMHEAPALLOC             Heap bytes allocated since last GC cycle=0 (0x0)
3XMTHREADINFO3           No Java callstack associated with this thread
3XMTHREADINFO3           No native callstack available for this thread
NULL
...
3XMTHREADINFO      "Common-Cleaner" J9VMThread:0x0000000000FD0100, omrthread_t:0x00007FF4F022A520, java/lang/Thread:0x00000000FFE26F40, state:CW, prio=8
3XMJAVALTHREAD            (java/lang/Thread getId:0x2, isDaemon:true)
3XMTHREADINFO1            (native thread ID:0x765A, native priority:0x8, native policy:UNKNOWN, vmstate:CW, vm thread flags:0x00080181)
3XMTHREADINFO2            (native stack address range from:0x00007FF4CC0B8000, to:0x00007FF4CC0F8000, size:0x40000)
3XMCPUTIME               CPU usage total: 0.000150926 secs, current category="Application"
3XMTHREADBLOCK     Waiting on: java/lang/ref/ReferenceQueue@0x00000000FFE26A10 Owned by: <unowned>
3XMHEAPALLOC             Heap bytes allocated since last GC cycle=0 (0x0)
3XMTHREADINFO3           Java callstack:
4XESTACKTRACE                at java/lang/Object.wait(Native Method)
4XESTACKTRACE                at java/lang/Object.wait(Object.java:221)
4XESTACKTRACE                at java/lang/ref/ReferenceQueue.remove(ReferenceQueue.java:138)
5XESTACKTRACE                   (entered lock: java/lang/ref/ReferenceQueue@0x00000000FFE26A10, entry count: 1)
4XESTACKTRACE                at jdk/internal/ref/CleanerImpl.run(CleanerImpl.java:148)
4XESTACKTRACE                at java/lang/Thread.run(Thread.java:835)
4XESTACKTRACE                at jdk/internal/misc/InnocuousThread.run(InnocuousThread.java:122)
3XMTHREADINFO3           No native callstack available for this thread
NULL
NULL
3XMTHREADINFO      "IProfiler" J9VMThread:0x0000000000F03D00, omrthread_t:0x00007FF4F00B06F8, java/lang/Thread:0x00000000FFE97B60, state:R, prio=5
3XMJAVALTHREAD            (java/lang/Thread getId:0xC, isDaemon:true)
3XMTHREADINFO1            (native thread ID:0x7659, native priority:0x5, native policy:UNKNOWN, vmstate:CW, vm thread flags:0x00000081)
3XMTHREADINFO2            (native stack address range from:0x00007FF4F8940000, to:0x00007FF4F8960000, size:0x20000)
3XMCPUTIME               CPU usage total: 0.004753103 secs, current category="JIT"
3XMHEAPALLOC             Heap bytes allocated since last GC cycle=0 (0x0)
3XMTHREADINFO3           No Java callstack associated with this thread
3XMTHREADINFO3           No native callstack available for this thread
NULL
...
1XMWLKTHDERR   The following was reported while collecting native stacks:
2XMWLKTHDERR             unable to count threads(3, -2)
NULL
1XMTHDSUMMARY  Threads CPU Usage Summary
NULL           =========================
NULL
1XMTHDCATINFO  Warning: to get more accurate CPU times for the GC, the option -XX:-ReduceCPUMonitorOverhead can be used. See the user guide for more information.
NULL
1XMTHDCATEGORY All JVM attached threads: 0.280083000 secs
1XMTHDCATEGORY |
2XMTHDCATEGORY +--System-JVM: 0.270814000 secs
2XMTHDCATEGORY |  |
3XMTHDCATEGORY |  +--GC: 0.000599000 secs
2XMTHDCATEGORY |  |
3XMTHDCATEGORY |  +--JIT: 0.071904000 secs
1XMTHDCATEGORY |
2XMTHDCATEGORY +--Application: 0.009269000 secs
NULL
```

## HOOKS

This section shows internal VM event callbacks, which are used for diagnosing performance problems in the VM. Multiple hook interfaces are listed, which include their individual hook events. The following example shows data for the `J9VMHookInterface`, including the call site location (source file:line number), start time, and duration of the last callback and the longest callback.

```
NULL           ------------------------------------------------------------------------
0SECTION       HOOK subcomponent dump routine
NULL           ==============================
1HKINTERFACE   MM_OMRHookInterface
NULL           ------------------------------------------------------------------------
1HKINTERFACE   MM_PrivateHookInterface
NULL           ------------------------------------------------------------------------
1HKINTERFACE   MM_HookInterface
NULL           ------------------------------------------------------------------------
1HKINTERFACE   J9VMHookInterface
NULL           ------------------------------------------------------------------------
2HKEVENTID     1
3HKCALLCOUNT       18
3HKLAST            Last Callback
4HKCALLSITE           trcengine.c:392
4HKSTARTTIME          Start Time: 2018-08-30T21:55:47.601
4HKDURATION           DurationMs: 0
3HKLONGST          Longest Callback
4HKCALLSITE           trcengine.c:392
4HKSTARTTIME          Start Time: 2018-08-30T21:55:47.460
4HKDURATION           DurationMs: 1
NULL
...
1HKINTERFACE   J9VMZipCachePoolHookInterface
NULL           ------------------------------------------------------------------------
1HKINTERFACE   J9JITHookInterface
NULL           ------------------------------------------------------------------------
2HKEVENTID     3
3HKCALLCOUNT       65
3HKLAST            Last Callback
4HKCALLSITE           ../common/mgmtinit.c:191
4HKSTARTTIME          Start Time: 2018-08-30T21:55:47.601
4HKDURATION           DurationMs: 0
3HKLONGST          Longest Callback
4HKCALLSITE           ../common/mgmtinit.c:191
4HKSTARTTIME          Start Time: 2018-08-30T21:55:47.486
4HKDURATION           DurationMs: 0
...
NULL
```

## SHARED CLASSES

If the shared classes cache is enabled at run time, the information provided in a Java dump file describes settings that were used when creating the cache, together with summary information about the size and content of the cache.

In the following example, the shared classes cache was created with a Class Debug Area (`-Xnolinenumbers=false`). Byte code instrumentation (BCI) is enabled, which is the default, and VMs sharing the cache are allowed to store classpaths, which is also the default.

The `Cache Summary` shows a cache size (`2SCLTEXTCSZ`) of 16776608 bytes, with a soft maximum size (`2SCLTEXTSMB`) also of 16776608 bytes, which leaves 12691668 bytes of free space (`2SCLTEXTFRB`). The size of the Class Debug Area (`2SCLTEXTDAS`) is 1331200 bytes and only 11% of this space is used.

In the `Cache Memory Status` subsection, the line `2SCLTEXTCMDT` indicates the name and location of the shared cache and `cr` indicates that the cache is a 64-bit compressed references cache.


```
NULL           ------------------------------------------------------------------------
0SECTION       SHARED CLASSES subcomponent dump routine
NULL           ========================================
NULL
1SCLTEXTCRTW   Cache Created With
NULL           ------------------
NULL
2SCLTEXTXNL        -Xnolinenumbers       = false
2SCLTEXTBCI        BCI Enabled           = true
2SCLTEXTBCI        Restrict Classpaths   = false
NULL
1SCLTEXTCSUM   Cache Summary
NULL           ------------------
NULL
2SCLTEXTNLC        No line number content                    = false
2SCLTEXTLNC        Line number content                       = true
NULL
2SCLTEXTRCS        ROMClass start address                    = 0x00007F423061C000
2SCLTEXTRCE        ROMClass end address                      = 0x00007F42307B9A28
2SCLTEXTMSA        Metadata start address                    = 0x00007F42313D42FC
2SCLTEXTCEA        Cache end address                         = 0x00007F4231600000
2SCLTEXTRTF        Runtime flags                             = 0x00102001ECA6028B
2SCLTEXTCGN        Cache generation                          = 35
NULL
2SCLTEXTCSZ        Cache size                                = 16776608
2SCLTEXTSMB        Softmx bytes                              = 16776608
2SCLTEXTFRB        Free bytes                                = 12691668
2SCLTEXTRCB        ROMClass bytes                            = 1694248
2SCLTEXTAOB        AOT code bytes                            = 0
2SCLTEXTADB        AOT data bytes                            = 0
2SCLTEXTAHB        AOT class hierarchy bytes                 = 32
2SCLTEXTATB        AOT thunk bytes                           = 0
2SCLTEXTARB        Reserved space for AOT bytes              = -1
2SCLTEXTAMB        Maximum space for AOT bytes               = -1
2SCLTEXTJHB        JIT hint bytes                            = 308
2SCLTEXTJPB        JIT profile bytes                         = 2296
2SCLTEXTJRB        Reserved space for JIT data bytes         = -1
2SCLTEXTJMB        Maximum space for JIT data bytes          = -1
2SCLTEXTNOB        Java Object bytes                         = 0
2SCLTEXTZCB        Zip cache bytes                           = 919328
2SCLTEXTRWB        ReadWrite bytes                           = 114080
2SCLTEXTJCB        JCL data bytes                            = 0
2SCLTEXTBDA        Byte data bytes                           = 0
2SCLTEXTMDA        Metadata bytes                            = 23448
2SCLTEXTDAS        Class debug area size                     = 1331200
2SCLTEXTDAU        Class debug area % used                   = 11%
2SCLTEXTDAN        Class LineNumberTable bytes               = 156240
2SCLTEXTDAV        Class LocalVariableTable bytes            = 0
NULL
2SCLTEXTNRC        Number ROMClasses                         = 595
2SCLTEXTNAM        Number AOT Methods                        = 0
2SCLTEXTNAD        Number AOT Data Entries                   = 0
2SCLTEXTNAH        Number AOT Class Hierarchy                = 1
2SCLTEXTNAT        Number AOT Thunks                         = 0
2SCLTEXTNJH        Number JIT Hints                          = 14
2SCLTEXTNJP        Number JIT Profiles                       = 20
2SCLTEXTNCP        Number Classpaths                         = 1
2SCLTEXTNUR        Number URLs                               = 0
2SCLTEXTNTK        Number Tokens                             = 0
2SCLTEXTNOJ        Number Java Objects                       = 0
2SCLTEXTNZC        Number Zip Caches                         = 5
2SCLTEXTNJC        Number JCL Entries                        = 0
2SCLTEXTNST        Number Stale classes                      = 0
2SCLTEXTPST        Percent Stale classes                     = 0%
NULL
2SCLTEXTCPF        Cache is 24% full
NULL
1SCLTEXTCMST   Cache Memory Status
NULL           ------------------
1SCLTEXTCNTD       Cache Name                    Feature                  Memory type              Cache path
NULL
2SCLTEXTCMDT       sharedcc_doc-javacore         CR                       Memory mapped file       /tmp/javasharedresources/C290M4F1A64P_sharedcc_doc-javacore_G35
NULL
1SCLTEXTCMST   Cache Lock Status
NULL           ------------------
1SCLTEXTCNTD       Lock Name                     Lock type                TID owning lock
NULL
2SCLTEXTCWRL       Cache write lock              File lock                Unowned
2SCLTEXTCRWL       Cache read/write lock         File lock                Unowned
NULL
```

## CLASSES

The classes section shows information about class loaders. The first part is a summary that records each available class loader (`2CLTEXTCLLOADER`) followed by the number of libraries and classes that it loaded. This information is followed by a more detailed list of libraries (`1CLTEXTCLLIB`) and classes (`1CLTEXTCLLO`) that are loaded.

In the example we can see that the `java/lang/InternalAnonymousClassLoader` loaded 2 classes, `jdk/internal/loader/BuiltinClassLoader$$Lambda$2/00000000F03876A0(0x0000000001030F00)` and    			`jdk/internal/loader/BuiltinClassLoader$$Lambda$1/00000000F00D2460(0x0000000001018A00)`.


```
NULL           ------------------------------------------------------------------------
0SECTION       CLASSES subcomponent dump routine
NULL           =================================
1CLTEXTCLLOS   	Classloader summaries
1CLTEXTCLLSS   		12345678: 1=primordial,2=extension,3=shareable,4=middleware,5=system,6=trusted,7=application,8=delegating
2CLTEXTCLLOADER		p---st-- Loader *System*(0x00000000FFE1D258)
3CLNMBRLOADEDLIB		Number of loaded libraries 5
3CLNMBRLOADEDCL			Number of loaded classes 638
2CLTEXTCLLOADER		-x--st-- Loader jdk/internal/loader/ClassLoaders$PlatformClassLoader(0x00000000FFE1D4F0), Parent *none*(0x0000000000000000)
3CLNMBRLOADEDLIB		Number of loaded libraries 0
3CLNMBRLOADEDCL			Number of loaded classes 0
2CLTEXTCLLOADER		----st-- Loader java/lang/InternalAnonymousClassLoader(0x00000000FFE1DFD0), Parent *none*(0x0000000000000000)
3CLNMBRLOADEDLIB		Number of loaded libraries 0
3CLNMBRLOADEDCL			Number of loaded classes 2
2CLTEXTCLLOADER		-----ta- Loader jdk/internal/loader/ClassLoaders$AppClassLoader(0x00000000FFE1DAD0), Parent jdk/internal/loader/ClassLoaders$PlatformClassLoader(0x00000000FFE1D4F0)
3CLNMBRLOADEDLIB		Number of loaded libraries 0
3CLNMBRLOADEDCL			Number of loaded classes 0
1CLTEXTCLLIB   	ClassLoader loaded libraries
2CLTEXTCLLIB  		Loader *System*(0x00000000FFE1D258)
3CLTEXTLIB   			/home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk/lib/compressedrefs/jclse9_29
3CLTEXTLIB   			/home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk/lib/java
3CLTEXTLIB   			/home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk/lib/compressedrefs/j9jit29
3CLTEXTLIB   			/home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk/lib/zip
3CLTEXTLIB   			/home/me/openj9-openjdk-jdk9/build/linux-x86_64-normal-server-release/images/jdk/lib/nio
1CLTEXTCLLOD   	ClassLoader loaded classes
2CLTEXTCLLOAD  		Loader *System*(0x00000000FFE1D258)
3CLTEXTCLASS   			[Ljava/lang/Thread$State;(0x0000000001056400)
...
2CLTEXTCLLOAD  		Loader jdk/internal/loader/ClassLoaders$PlatformClassLoader(0x00000000FFE1D4F0)
2CLTEXTCLLOAD  		Loader java/lang/InternalAnonymousClassLoader(0x00000000FFE1DFD0)
3CLTEXTCLASS   			jdk/internal/loader/BuiltinClassLoader$$Lambda$2/00000000F03876A0(0x0000000001030F00)
3CLTEXTCLASS   			jdk/internal/loader/BuiltinClassLoader$$Lambda$1/00000000F00D2460(0x0000000001018A00)
2CLTEXTCLLOAD  		Loader jdk/internal/loader/ClassLoaders$AppClassLoader(0x00000000FFE1DAD0)
```



<!-- ==== END OF TOPIC ==== dump_javadump.md ==== -->
