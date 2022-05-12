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

# Configuring your system

Configuring your local system can help you optimize the runtime environment for your Java application. Options include setting operating system environment variables and configuring system resources so that OpenJ9 can exploit the underlying operating system and hardware capabilities.

When you install a Java&trade; runtime environment on your system you can set the **PATH** environment variable so that the operating system can find the Java programs and utilities to run your application. To tell your application where to find user classes, you can use the **-cp** option or set the **CLASSPATH** environment variable. However, if you set **CLASSPATH** globally, all invocations of Java are affected. How to set these environment variables is covered in many publications about Java, such as [The Java Tutorials: PATH and CLASSPATH](https://docs.oracle.com/javase/tutorial/essential/environment/paths.html).

On some systems, a further environment variable might be required if your application requires shared libraries but does not specify their exact location. You can set the following environment variables to specify the directory location of the shared libraries, although setting a global value affects all invocations of Java:

- **LIBPATH** (AIX&reg; and z/OS&reg;)
- **LD_LIBRARY_PATH** (Linux&reg;)
- **DYLD_LIBRARY_PATH** (macOS&reg;)
- **PATH** (Windows&reg;)

![Start of content that applies to Java 11+](cr/java11plus.png) Although most Java applications should run without changing anything on the underlying system, a unique pre-requisite exists for AIX systems on OpenJDK version 11 and later; you must have the [16.1 XL C++ Runtime](https://www.ibm.com/support/pages/fix-list-xl-cc-runtime-aix#161X) installed. ![End of content that applies only to Java 11 and later](cr/java_close.png)

## Setting resource limits (AIX, Linux, and macOS)

The operating system sets resource limits for a shell, and to processes started by the shell, to ensure that a single process cannot consume all available resources. However, these limits can affect certain operations that might need to run for a Java application, such as producing a dump file.

### Setting ulimit values

Some resource limits are controlled by the **ulimit** command. A *soft* limit is the value set by the kernel for a resource and a *hard* limit imposes a maximum value on the soft limit. A privileged process can change either limit, but an unprivileged process can change only its soft limit (between 0 and the hard limit) or irreversibly lower its hard limit. To see the current limits set for a system, run `ulimit -a`. The output is similar to the following example:

```
core file size          (blocks, -c) 0
data seg size           (kbytes, -d) unlimited
file size               (blocks, -f) unlimited
max locked memory       (kbytes, -l) unlimited
max memory size         (kbytes, -m) unlimited
open files                      (-n) 256
pipe size            (512 bytes, -p) 1
stack size              (kbytes, -s) 8192
cpu time               (seconds, -t) unlimited
max user processes              (-u) 2784
virtual memory          (kbytes, -v) unlimited
```

To show hard limits, use `ulimit -Ha`.

You can change limits for specific resources on a temporary basis by running the **ulimit** command. Alternatively, you can store limit settings in a configuration file, which is `/etc/security/limits` for AIX or `etc/security/limits.conf` for Linux. For more information about configuring resource limits, refer to the documentation for your operating system.   

The main use case for changing `ulimit` resources is when enabling a system dump to ensure that all the required data can be collected for analysis. For more information, see [Enabling a full system dump](dump_systemdump.md#enabling-a-full-system-dump-aix-and-linux).

### Setting shared memory values

Another use case for changing resource limits is to ensure that there is sufficient shared memory allocated for class data sharing. By default, the shared classes cache consists of memory-mapped files that are created on disk and *persist* when the system is restarted. If you choose to use *non-persistent* caches by setting the `-Xshareclasses:nonpersistent` option, caches are not retained on startup and are allocated by using the System V IPC shared memory mechanism.

- On AIX systems, the kernel dynamically adjusts the shared memory settings as required. No special configuration is required.
- On Linux systems, the **SHMMAX** setting limits the amount of shared memory that can be allocated, which affects the shared classes cache size. You can find the value of **SHMMAX** for your system in the `/proc/sys/kernel/shmmax` file. For non-persistent caches, set this value to an appropriate size for your applications. To make these changes permanent, edit `/etc/sysctl.conf` and reboot your system.
- On macOS systems, you must set **kern.sysv.shmmax** and **kern.sysv.shmall** when using a nonpersistent cache. Modify the settings in your `/etc/sysctl.conf` file and reboot your system. To check the value, run `sysctl kern.sysv.shmmax`.

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** The virtual address space of a process is shared between the shared classes cache and the Java heap. Increasing the maximum size for the shared classes cache might reduce the size of the Java heap that you can create.

Shared memory limits are also important when configuring large page memory allocation on Linux systems. For more information, see [Configuring large page memory allocation: Linux systems](#linux-systems).

## Setting resource limits (z/OS)

Resource limits imposed by z/OS might affect Java operations. To learn how these resource limits are set, see [Customizing the BPXPRMxx member of SYS1.PARMLIB](https://www.ibm.com/support/knowledgecenter/SSLTBW_2.4.0/com.ibm.zos.v2r4.bpxb200/cusis.htm).

The OpenJ9 class data sharing feature is implemented by using shared memory segments on z/OS. Special consideration should be given to the following parameters that relate to the shared memory and IPC semaphore settings:

<!-- - **MAXSHAREPAGES** (This no longer seems to do anything, per https://www.ibm.com/support/knowledgecenter/SSLTBW_2.4.0/com.ibm.zos.v2r4.ieae200/spb.htm). Also removed from table below. -->

- **IPCSHMSPAGES**
- **IPCSHMMPAGES**
- **IPCSHMNSEGS**

Incorrect or suboptimal settings might prevent shared classes from working or impact performance. By default, the VM attempts to create a 16 MB cache. If you set a cache size for your application by specifying the [`-Xscmx`](xscmx.md) option on the command line, the VM rounds the value up to the nearest megabyte. Ensure that the value set for **IPCSHMMPAGES** takes this adjustment into consideration.

To see the current settings, enter the following z/OS operator command:

```
D OMVS,O
```
The suggested minimum values for Java applications are shown in the following table:

| Parameter         | Value      |
|-------------------|------------|
|**MAXPROCSYS**     | 900        |
|**MAXPROCUSER**    |	512        |
|**MAXUIDS**	      | 500        |
|**MAXTHREADS**     |	10000      |
|**MAXTHREADTASKS**	| 5000       |
|**MAXASSIZE**	    | 2147483647 |
|**MAXCPUTIME**	    | 2147483647 |
|**MAXMMAPAREA** 	  | 40960      |
|**IPCSHMSPAGES**   | 262144     |
|**IPCSHMMPAGES**   | 256        |
|**IPCSHMNSEGS**    | 10         |
|**IPCSEMNIDS**	    | 500        |
|**IPCSEMNSEMS**	  | 1000       |


:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** The number of threads that can be created by a Java process is limited by the lower of the two values for **MAXTHREADS** and **MAXTHREADSTASKS**.

You can change these settings dynamically without re-IPLing the system. For example, to set **MACPROCUSER** to 256, run `SETOMVS MAXPROCUSER=256`

z/OS uses region sizes to determine the amount of storage available to running programs. For a Java runtime environment, the  region size must be sufficiently large to avoid storage related error messages or abends. Rather than restricting region size, allow the VM to use what it needs. Region size can be affected by one of the following parameters: **JCL REGION**, **BPXPRMxx MAXASSIZE**, the RACF OMVS segment **ASSIZEMAX**, or IEFUSI (Step initiation exit).


### Configuring Language Environment runtime options

Language Environment&reg; runtime options affect performance and storage usage. These options can be optimized for your application.

Runtime options are typically embedded in programs by using **#pragma runopts** settings. In many cases, these options provide suitable default values that are known to produce good performance results. However, these options can be overridden to tune the runtime environment of your application.

On 64-bit z/OS systems, the following runtime options affect Java applications:

- **HEAP64**: Controls allocation of the user heap. A suggested starting point for an override is **HEAP64(512M,4M,KEEP,16M,4M,KEEP,0K,0K,FREE)**.
- **HEAPPOOLS64**: Used to manage Java heap storage above the 2 G bar. The Java USS launcher sets **HEAPPOOLS64(ALIGN)** for more optimal management of multi-threaded applications. Other Java launchers might have different settings. Before you set an override for **HEAPPOOLS64**, use **RPTOPTS(ON)** to confirm the active settings for your environment and **RPTSTG(ON)** to review storage usage and tuning recommendations. Note that the host product might have already set cell sizes and numbers that are known to produce good performance.
- **STACK64**: Controls the allocation and management of stack storage. A suggested default is **STACK64(1M,1M,128M)**.
- **THREADSTACK64**: Controls the allocation of thread-level stack storage for both the upward and downward-growing stack. A suggested default is **THREADSTACK64(OFF,1M,1M,128M)**.

A suitable **MEMLIMIT** value is also required. The OpenJ9 VM requirement is the sum of the following amounts:

- User heap storage required by the VM and native libraries, as controlled by **HEAP64** (minimum 512 M) settings.
- User stack storage (3 MB multiplied by the expected number of concurrent threads), as controlled by **STACK64** settings.
- `-Xmx` largest expected VM heap size.
- The JIT data cache maximum size.
- The JIT code cache maximum size, if **RMODE64** is supported.

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** If you intend to use the Concurrent Scavenge mode of the default Generational Concurrent (`gencon`) garbage collection policy by using hardware-based support, the virtual storage used might exceed the Java maximum heap size. Set the z/OS memory limit to a larger value than the maximum heap size. For more information, see [`-Xgc:concurrentScavenge`](xgc.md#concurrentscavenge).

The following guides are available to help you configure Language Environment runtime options and callable services:

- See [z/OS Language Environment Programming Guide](https://www.ibm.com/support/knowledgecenter/SSLTBW_2.4.0/com.ibm.zos.v2r4.ceea200/abstract.htm) for guidance on how to override the default options. Use **RPTOPTS (ON)** to write the options that are in effect to *stderr* on termination.
- See [z/OS Language Environment Programming Reference](https://www.ibm.com/support/knowledgecenter/SSLTBW_2.4.0/com.ibm.zos.v2r4.ceea300/abstract.htm) for a full list of the available runtime options.
- See [z/OS Language Environment Debugging Guide](https://www.ibm.com/support/knowledgecenter/SSLTBW_2.4.0/com.ibm.zos.v2r4.ceea100/abstract.htm) for tuning guidance by using **RPTSTG (ON)**.

:fontawesome-solid-exclamation-triangle:{: .warn aria-hidden="true"} **Warning:** Changing the runtime options can often degrade performance.

## Configuring large page memory allocation

If your application allocates a large amount of memory and frequently accesses that memory, you might be able to improve performance by enabling large page support on your system.

Some Linux kernels implement Transparent HugePage Support (THP), which automates the provision of large pages to back virtual memory, as described in [Linux systems](#linux-systems). Alternatively, you can enable large page support by setting the [`-Xlp:objectheap`](xlpobjectheap.md) and [`-Xlp:codecache`](xlpcodecache.md) options on the command line when you start your application. These options have the following effects:

- The `-Xlp:objectheap` option requests that the Java object heap is allocated by using large pages.
- The `-Xlp:codecache` option requests that the JIT code cache is allocated by using large pages.

You must also enable large pages on your local system. This process differs according to the operating system.

### AIX systems

AIX supports large page sizes of 64 KB and 16 MB, and a huge page size of 16 GB depending on the underlying system P hardware. To determine which page sizes are supported on a particular system, run `pagesize -a`.

To use large pages to back an application's data and heap segments, specify the **LDR_CNTRL** environment variable. You can set different page sizes for different purposes. The following variables can be used:

- **TEXTPSIZE**: Page size to use for text
- **STACKPSIZE**: Page size to use for stacks
- **DATAPSIZE**: Page size to use for native data or HEAP64

The following example sets 4 KB for text and 64 KB for stack, native data, and heap areas:

```
LDR_CNTRL=TEXTPSIZE=4K@STACKPSIZE=64K@DATAPSIZE=64K
```

For more information, including support considerations, see [Large pages](https://www.ibm.com/support/knowledgecenter/ssw_aix_72/performance/large_page_ovw.html) and [Multiple page size support](https://www.ibm.com/support/knowledgecenter/ssw_aix_72/performance/multiple_page_size_support.html) in the AIX documentation.

The 16 MB and 16 GB page sizes, which are intended for very high performance environments, require special user permissions. You must also configure the number of pages that you require, which cannot be adjusted on demand. For 16 MB large pages, you set the number of large pages by using the `vmo` command. For 16 GB huge pages you must define the number of pages by using the hardware management console. For more information, see [Page sizes for very high-performance environments](https://www.ibm.com/support/knowledgecenter/ssw_aix_72/performance/page_sizes_very_high-perf_env.html) in the AIX documentation.

### Linux systems

Large pages are typically referred to as *huge pages* on Linux systems. To configure huge page memory allocation, the kernel must support huge pages. If huge pages are supported, the following lines are present in the **/proc/meminfo** file:

```
HugePages_Total:    
HugePages_Free:     
Hugepagesize:     
```

If these lines do not exist, update your Linux kernel. If `HugePages_Total` has a value of `0`, huge pages are available, but not enabled. To enable huge pages, add the following line to your **/etc/sysctl.conf** file and reload the configuration by running `sysctl -p`:

```
vm.nr_hugepages=<number>
```

Where `<number>` is the number of huge pages required.

Configure the number of huge pages that you require at boot time to ensure that the VM has access to sufficient contiguous pages. The following kernel parameters must be set appropriately for your system:

- **SHMMAX**: The maximum size of the shared memory segment (bytes).
- **SHMALL**: The total amount of shared memory in the system (bytes or pages).

The user running the Java process must either be ROOT or have permissions to use huge pages. For the appropriate permissions, the user must be a member of a group that has its group identifier (gid) stored in **/proc/sys/vm/hugetlb_shm_group**. The locked memory limit must also be increased to at least the size of the Java heap by using the **ulimit -l** command.

Where huge page support is available, the following default sizes apply for the object heap:

- Linux on x86: 2 MB
- Linux on IBM Power Systems: Varies depending on kernel version, check `/proc/meminfo`
- Linux on IBM Z: 1 MB

Transparent HugePage Support (THP) is an automated mechanism of using huge pages to back virtual memory. On Linux kernels that support THP, it is typically enabled by default with the **madvise** option and can be relied on to provide huge pages as required without any user configuration. To disable THP for your application, use the OpenJ9 [`-XX:-TransparentHugePage`](xxtransparenthugepage.md) option on the command line. To disable THP system-wide, change the **sysfs** boot time defaults with the command `transparent_hugepage=never `. For more information about THP see [Transparent HugePage Support](https://www.kernel.org/doc/html/latest/admin-guide/mm/transhuge.html).

### Windows systems

On Windows systems, large pages are typically 2 MB in size. To use large pages, the VM user must have the Windows **Lock pages in memory** setting enabled in the Local Security Policy. Applications must also be run with Admin privileges in order to use large page memory allocations.

For more information, see the following resources from Microsoft:

- [Large page support](https://docs.microsoft.com/en-gb/windows/win32/memory/large-page-support)
- [`GetLargePageMinimum` function (`memoryapi.h`)](https://docs.microsoft.com/en-gb/windows/win32/api/memoryapi/nf-memoryapi-getlargepageminimum)

### z/OS systems  

When available, 1 MB pageable pages are the default size for the object heap and the code cache. Other page sizes are available for the object heap, depending on the system architecture as shown in the following table:

| Large page size  | System architecture required              | `-Xlp:codecache` | `-Xlp:objectheap`          |
|------------------|-------------------------------------------|------------------|----------------------------|
| 2 GB nonpageable | IBM zEnterprise EC12 processor or later   | Not supported    | Supported (64-bit VM only) |
| 1 MB nonpageable | System z10 processor or later             | Not supported    | Supported (64-bit VM only) |
| 1 MB pageable    | IBM zEnterprise EC12 processor or later (see Note) |Supported| Supported                  |

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** The Flash Express feature (#0402) helps avoid demoting 1 MB pageable pages to 4 KB pages when there is system paging activity.

If a particular page size cannot be allocated, a smaller page size is attempted, in descending order. For example, if 2 GB nonpageable pages are requested but not available, the VM tries to allocate 1MB nonpageable pages. If 1 MB nonpageable pages are not available, the VM tries to allocate 1MB pageable pages. If large pages are not available, 4 KB pages are allocated.

If you want to use nonpageable large pages for the object heap, a system programmer must configure z/OS for nonpageable large pages in the **IEASYSxx** parmlib member. Users who require large pages must also be authorized to the **IARRSM.LRGPAGES** resource in the RACF FACILITY class with read authority.

Use the following z/OS system command to show large page usage for an LPAR:

```
MODIFY AXR,IAXDMEM
```
For more information, see [Displaying real storage memory statistics](https://www.ibm.com/support/knowledgecenter/en/SSLTBW_2.4.0/com.ibm.zos.v2r4.ieag100/iaxdmem.htm) in the z/OS product documentation.

For usage information, including examples, see [`-Xlp:objectheap`](xlpobjectheap.md#pageablenonpageable).


## Configuring Dynamic LPAR support (AIX only)

*Dynamic logical partitioning* (DLPAR) provides a mechanism to add or remove system resources, such as memory or CPU, to or from the operating system in a logical partition without rebooting. Changing these resources dynamically can have an impact on Java applications that are running on the LPAR.

To enable an application to respond to DLPAR events, you can use OpenJ9 MXBean extensions to the `java.lang.management` API. The following classes are available in the `com.ibm.lang.management` package:

- `AvailableProcessorsNotificationInfo`: Use to listen for changes to the number of available processors.
- `ProcessingCapacityNotificationInfo`: Use to listen for changes to processing capacity.
- `TotalPhysicalMemoryNotificationInfo`: Use to listen for changes to the total amount of physical memory that is available.

These extensions can listen for events and trigger any necessary adjustments to the runtime environment. For example, if a Java VM is running in an LPAR with 2GB of memory, but the available memory might be adjusted between 1GB and 8GB, you might set the following options for the Java heap at run time:

```
–Xms1g –Xsoftmx2g –Xmx8g
```  

This command-line string sets an initial heap size of 1 GB, a *soft* (adjustable) maximum heap size of 2 GB, and a maximum heap size of 8 GB. You can then use the `MemoryMXBean` API to dynamically respond to changes in memory resources. The following classes can be used:

- `getMaxHeapSize()`: Query the maximum heap size.
- `isSetMaxHeapSizeSupported()`: Query whether the VM can support dynamic updates.
- `setMaxHeapSize()`: Adjust the maximum heap size.

For more information about the `com.ibm.lang.managment` package, which extends the `jdk.management` module, see the [API documentation](api-overview.md#monitoring-and-management).





<!-- ==== END OF TOPIC ==== configuring.md ==== -->
