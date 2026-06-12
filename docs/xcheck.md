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

# -Xcheck

You can use the `-Xcheck` option to run checks during Eclipse OpenJ9&trade; virtual machine (VM) startup, such as memory checks or checks on JNI functions.

## Syntax

        -Xcheck:<parameter>

## Parameters

| Parameter                |  Effect                                                                                                        |
|--------------------------|----------------------------------------------------------------------------------------------------------------|
| [`classpath`](#classpath)| Checks the classpath and reports errors such as a missing directory or JAR file.                               |
| [`dump`](#dump)          | Checks the settings that might prevent proper system dump operation.                                           |
| [`gc`](#gc)              | Runs additional checks on garbage collection.                                                                  |
| [`jni`](#jni)            | Runs additional checks for JNI functions.                                                                      |
| [`memory`](#memory)      | Identifies memory leaks inside the VM using strict checks that cause the VM to exit on failure.                |
| [`vm`](#vm)              | Performs additional checks on the VM.                                                                          |

### `classpath`

        -Xcheck:classpath

: Checks the classpath and reports errors such as a missing directory or JAR file.


### `dump`

        -Xcheck:dump

: Checks operating system settings during VM startup. Messages are issued if the operating system has settings that might truncate system dumps.

    On **AIX** systems, the following messages are possible:

    - `JVMJ9VM133W The system core size hard ulimit is set to <value>, system dumps may be truncated`

    : This message indicates that the AIX operating system user limit is set to restrict the size of system dumps to the value indicated. If a system dump is produced by the VM it might be truncated, and therefore of greatly reduced value in investigating the cause of crashes and other issues. For more information about how to set user limits on AIX, see [Setting system resource limits on AIX and Linux systems](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_ulimits.html).

    - `JVMJ9VM134W The system fullcore option is set to FALSE, system dumps may be truncated`

    : This message indicates that the AIX operating system `Enable full CORE dump` option is set to *FALSE*. This setting might result in truncated system dumps. For more information about how to set this option correctly on AIX, see [Setting system resource limits on AIX and Linux systems](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_ulimits.html).

    <!--![Linux](cr/_lnx.png) -->On **Linux** systems, the following messages are possible:

    - `JVMJ9VM133W The system core size hard ulimit is set to <value>, system dumps may be truncated.`

    : This message indicates that the Linux operating system user limit is set to restrict the size of system dumps to the value indicated. If a system dump is produced by the VM, it might be truncated and therefore of greatly reduced value in investigating the cause of crashes and other issues. Review the documentation that is provided for your operating system to correctly configure the value for `ulimits`. For further information, see [Setting system resource limits on AIX and Linux systems](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_ulimits.html).

    - `JVMJ9VM135W /proc/sys/kernel/core_pattern setting "|/usr/libexec/abrt-hook-ccpp %s %c %p %u %g %t e" specifies that core dumps are to be piped to an external program. The JVM may be unable to locate core dumps and rename them.`

    : This message means that an external program, **abrt-hook-ccpp**, is configured in the operating system to intercept any system dump files that are generated. This program is part of the Automatic Bug Reporting Tool (ABRT).

    : For more information, see [Automatic Bug Reporting Tool](https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/7/html/system_administrators_guide/ch-abrt). This tool might interfere with the VM's system dump file processing by renaming or truncating system dumps. Review the configuration of the ABRT tool and messages that are written by the tool in` /var/log/messages`. If problems occur when generating system dumps from the VM, consider disabling ABRT.

    - `JVMJ9VM135W /proc/sys/kernel/core_pattern setting "|/usr/share/apport/apport %p %s %c" specifies that core dumps are to be piped to an external program. The JVM may be unable to locate core dumps and rename them.`

    : This message means that an external program, **apport**, is configured in the operating system to intercept any system dump files that are generated. For more information about this tool, see: [Apport](https://wiki.ubuntu.com/Apport). The tool might interfere with the VM's system dump file processing by renaming or truncating system dumps. Review the configuration of the Apport tool and messages that are written by the tool in `/var/log/apport.log`. If problems occur when generating system dumps from the VM, consider disabling the Apport tool.

    - `JVMJ9VM136W "/proc/sys/kernel/core_pattern setting "/tmp/cores/core.%e.%p.%h.%t " specifies a format string for renaming core dumps. The JVM may be unable to locate core dumps and rename them.`

    : This message indicates that the Linux `/proc/sys/kernel/core_pattern` option is set to rename system dumps. The tokens that are used in the operating system dump name might interfere with the VM's system dump file processing, in particular with file names specified in the VM `-Xdump` options. If problems occur when generating system dumps from the VM, consider changing the `/proc/sys/kernel/core_pattern` setting to the default value of `core`.

: ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) **z/OS only**

: From the 0.60.0 release onwards, the `-Xcheck:dump` option is enhanced on z/OS systems to trigger extra checking for `-Xdump:system` agents. When this option is used, the VM now validates dataset names specified in `-Xdump:system` options. This validation allows earlier detection of improper VM options and prevents deployment of misconfigured applications. You can correct the invalid datasets as soon as they are identified, and when system dumps are triggered, your diagnostic dumps will be created successfully.

: If the `-Xcheck:dump` option is not used, then invalid datasets remain undetected. If and when system dumps are triggered, dumps with improper dataset names fail and critical diagnostic data will be lost.

: Examples:

: No change when all dataset names are valid (as is the case with the defaults):

```
$ java -Xcheck:dump -version
java version "21.0.11-internal" 2026-04-21
IBM Semeru Runtime Certified Edition for z/OS (build 21.0.11-internal-adhoc.JENKINS.BuildJDK21s390xzosPersonal)
IBM J9 VM (build 21.0.11-internal-adhoc.JENKINS.BuildJDK21s390xzosPersonal-dsntest-5de00868719, JRE 21 z/OS s390x-64-Bit Compressed References 20260429_3867 (JIT enabled, AOT enabled)
OpenJ9   - 5de00868719
OMR      - 4a4463e1977
IBM      - 764a44bc845
JCL      - 166e9d03068 based on jdk-21.0.11+10)
```

: When the `-Xcheck:dump` option is not used, the VM reports the invalid dataset name if and when the dump is triggered and the normal message output is omitted:

```
$ java -Xdump:system:events=user,dsn='BAD..NAME.X&DS' -version
```

: When the `-Xcheck:dump` option is used, the VM reports the problematic `-Xdump` options in the same format that is used by `-Xdump:what` and the VM fails to start:

```
$ java -Xdump:system:events=user,dsn='BAD..NAME.X&DS' -Xcheck:dump -version
JVMJ9VM268E The following -Xdump:system options contain invalid dsn specifications:
-Xdump:system:
    events=user,
    dsn=BAD..NAME.X&DS,
    range=1..0,
    priority=999,
    request=serial
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.
```

: If dump support is disabled, `-Xcheck:dump` is also disabled. The VM proceeds normally except for an additional message.

```
$ java -Xdump:none -Xcheck:dump -version
JVMJ9VM063W seekDumpAgent requires j9dmp29
```

: ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)

### `gc`

        -Xcheck:gc[:help][:<scan options>][:<verify options>][:<misc options>]

: Runs additional checks on garbage collection. By default, no checks are made. There are many scan, verify, and miscellaneous suboptions available. If you do not specify any, all possible scan and verify suboptions are run, plus the miscellaneous verbose and check suboptions. For more information, see the output of `-Xcheck:gc:help`.


### `jni`

        -Xcheck:jni[:help][:<option>]

: Runs additional checks for JNI functions. By default, no checks are made. For more information, see the output of `-Xcheck:jni:help`.


### `memory`

        -Xcheck:memory[:<option>]

: Identifies memory leaks inside the VM by using strict checks that cause the VM to exit on failure.

: :fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** You cannot include `-Xcheck:memory` in the options file (see [`-Xoptionsfile`](xoptionsfile.md)).

: The available parameters are as follows:

    `:all`
    : **(Default if no options specified)** Enables checking of all allocated and freed blocks on every free and allocate call. This check of the heap is the most thorough. It typically causes the VM to exit on nearly all memory-related problems soon after they are caused. This option has the greatest effect on performance.

    `:callsite=<number_of_allocations>`
    :   Displays callsite information every `<number_of_allocations>`. De-allocations are not counted. Callsite information is presented in a table with separate information for each callsite. Statistics include:

        -   The number and size of allocation and free requests since the last report.
        -   The number of the allocation requests responsible for the largest allocation from each site.


        Callsites are presented as `sourcefile:linenumber` for C code and assembly function name for assembler code.

        Callsites that do not provide callsite information are accumulated into an "unknown" entry.

    `:failat=<number_of_allocations>`

    : Causes memory allocation to fail (return NULL) after `<number_of_allocations>`. For example, setting `<number_of_allocations>` to 13 causes the 14th allocation to return NULL. De-allocations are not counted. Use this option to ensure that VM code reliably handles allocation failures. This option is useful for checking allocation site behavior rather than setting a specific allocation limit.

    `:ignoreUnknownBlocks`
    :   Ignores attempts to free memory that was not allocated by using the `-Xcheck:memory` tool. Instead, the **-Xcheck:memory** statistics that are printed at the end of a run indicates the number of *"unknown"* blocks that were freed.

    `:mprotect=[top|bottom]`
    : Locks pages of memory on supported platforms, causing the program to stop if padding before or after the allocated block is accessed for reads or writes. An extra page is locked on each side of the block returned to the user.

    : If you do not request an exact multiple of one page of memory, a region on one side of your memory is not locked. The `top` and `bottom` options control which side of the memory area is locked. `top` aligns your memory blocks to the top of the page (lower address), so buffer underruns result in an application failure. `bottom` aligns your memory blocks to the bottom of the page (higher address) so buffer overruns result in an application failure.

    : Standard padding scans detect buffer underruns when using `top` and buffer overruns when using `bottom`.

    `:nofree`
    :   Keeps a list of blocks that are already used instead of freeing memory. This list, and the list of currently allocated blocks, is checked for memory corruption on every allocation and deallocation. Use this option to detect a dangling pointer (a pointer that is "dereferenced" after its target memory is freed). This option cannot be reliably used with long-running applications (such as WebSphere&reg; Application Server) because *"freed"* memory is never reused or released by the VM.

    `:noscan`
    :   Checks for blocks that are not freed. This option has little effect on performance, but memory corruption is not detected. This option is compatible only with `subAllocator`, `callsite`, and `callsitesmall`.

    `:quick`
    :   Enables block padding only and is used to detect basic heap corruption. Every allocated block is padded with sentinel bytes, which are verified on every allocate and free. Block padding is faster than the default of checking every block, but is not as effective.

    `:skipto=<number_of_allocations>`
    :   Causes the program to check only on allocations that occur after `<number_of_allocations>`. De-allocations are not counted. Use this option to speed up VM startup when early allocations are not causing the memory problem. The VM performs approximately 250+ allocations during startup.

    `:subAllocator[=<size_in_MB>]`
    :   Allocates a dedicated and contiguous region of memory for all VM allocations. This option helps to determine whether user JNI code or the VM is responsible for memory corruption. Corruption in the VM `subAllocator` heap suggests that the VM is causing the problem; corruption in the user-allocated memory suggests that user code is corrupting memory. Typically, user and VM allocated memory are interleaved.

    `:zero`
    :   Newly allocated blocks are set to 0 instead of being filled with the `0xE7E7xxxxxxxxE7E7` pattern. Setting these blocks to 0 helps you to determine whether a callsite is expecting zeroed memory, in which case the allocation request is followed by `memset(pointer, 0, size)`.


### `vm`

        -Xcheck:vm[:<option>]

:   Performs additional checks on the VM. By default, no checking is performed. For more information, run `-Xcheck:vm:help`.

## See also

- [What's new in version 0.60.0](version0.60.md#the-xcheckdump-option-is-enhanced-on-zos-systems)

<!-- ==== END OF TOPIC ==== xcheck.md ==== -->
