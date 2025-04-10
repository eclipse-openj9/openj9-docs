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

# Getting started with Eclipse OpenJ9

Eclipse OpenJ9&trade; is a high performance, scalable, Java&trade; virtual machine (VM) implementation that is fully compliant with the [Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/index.html).

At run time, the VM interprets the Java bytecode that is compiled by the Java compiler. The VM acts as a translator between the language and the underlying operating system and hardware. A Java program requires a specific VM to run on a particular platform, such as Linux&reg;, z/OS&reg;, or Windows&trade;.

This material provides information about the VM configuration and tuning options, together with the default settings. Follow the links provided for more detailed information.


## Configuring your system

Most Java applications should run on an OpenJDK that contains the OpenJ9 VM without changing anything on the underlying system. However, to get the most out of your system you might want to consider some configuration options. Read [Configuring your system](configuring.md) to learn more about the following options:

- Setting operating system environment variables, such as **PATH** and **CLASSPATH**.
- Increasing resource limits for running Java applications.
- Configuring large page memory allocation.
- Configuring Dynamic LPAR support on AIX&reg; systems.

## Performance tuning

OpenJ9 is configured to start with a set of default options that provide the optimal runtime environment for Java applications with typical workloads. However, if your application is atypical, you can improve performance by tuning the OpenJ9 VM. You can also improve performance by enabling hardware features or using specific APIs in your application code.

### Garbage collection policies
OpenJ9 includes several garbage collection policies. To learn more about these policies and the types of application workload that can benefit from them, see [Garbage collection policies](gc.md).

### Class data sharing
You can share class data between running VMs, which can reduce the startup time for a VM after the cache has been created. For more information, see [Introduction to class data sharing](shrc.md).

### Native data operations
If your Java application manipulates native data, consider writing your application to take advantage of methods in the Data Access Accelerator (DAA) API.

The following functions are provided:

- Arithmetic, comparison, shifting, and validation operations for packed decimal data.
- Conversion operations between different binary coded decimal and Java binary types.
- Marshalling operations: marshalling and unmarshalling Java binary types, such as `short`, `int`, `long`, `float`, and `double`, to and from byte arrays.
- External decimals support with all four sign configurations: sign embedded trailing, sign embedded leading, sign separate trailing, and sign separate leading. It also accommodates sign embedded trailing with spaces.

You can gain a number of benefits by using the APIs provided:

- Improved application performance by avoiding object creation and intermediate processing, which can also put pressure on the Java heap.
- Hardware acceleration by automatically exploiting available hardware features on specific platforms.
- Platform independence for applications that are developed to take advantage of Data Access Acceleration.
- Validate the sign and digits of a given external decimal input before operating on the data.

For more information, see the [API documentation](api-overview.md).

### Cloud optimizations
To improve the performance of applications that run in containers, try setting the following tuning options:

- Use a shared classes cache (`-Xshareclasses -XX:SharedCacheHardLimit=200m -Xscmx60m`) with Ahead-Of-Time (AOT) compilation to improve your startup time. For persistence, store the cache in a volume that you map to your container. For more information, see [Introduction to class data sharing](shrc.md) and [AOT Compiler](aot.md).

- Use the [-Xtune:virtualized](xtunevirtualized.md) option, which configures OpenJ9 for typical cloud deployments where VM guests are provisioned with a small number of virtual CPUs to maximize the number of applications that can be run. When enabled, OpenJ9 adapts its internal processes to reduce the amount of CPU consumed and trim down the memory footprint. These changes come at the expense of only a small loss in throughput.

- Provide access to the `/proc` file system for container detection on Linux systems since the detection code requires access to the `/proc/1/cgroup` and `/proc/1/sched` files. If you mount the `/proc` file system with the `hidepid=2` option on Linux systems and the VM does not have root privileges, it cannot access the `/proc` file system and the container detection fails. Even though the container detection fails, the VM does start with a warning message. For example:

    ```
    JVMPORT050W Failed to identify if the JVM is running inside a container; error message: fopen failed to open /proc/1/cgroup file with errno=2.
    ```

    Although the VM starts after the container detection fails, the VM assumes that it is running outside a container. Therefore, if the VM is running in a container, the VM cannot adapt to the container's limitations and might use an undesirable amount of resources. You can evaluate the impact on performance because of the container detection failure and take steps to resolve the performance issue, if so required. Some of the steps that you can take are as follows:

    - Remount the `/proc` file system with the `hidepid=0` option: You can use this option to allow the VM to access the `/proc` file system. This action allows all processes access to `/proc` not just the VM.

            # Remount /proc with hidepid=0 to read the contents of /proc/<PID>
            mount -o remount,rw,hidepid=0 /proc

            # Apply the change to /proc persistently by editing /etc/fstab
            proc            /proc           proc    rw,nosuid,nodev,noexec,relatime,hidepid=0   0 0

    - Remount the `/proc` file system with the `gid` and `hidepid=2` options: You can use this option to allow only certain processes to access the `/proc` file system. You can add the processes in a group and provide access to that group with the `gid` option.

            # Create a group to allow certain users to read the contents of /proc/<PID>
            groupadd -g 1000 procaccess

            # Add a user to the group procaccess
            usermod -a -G procaccess <USER>

            # Remount /proc with the gid option to allow users in group procaccess to access /proc
            mount -o remount,rw,hidepid=2,gid=1000 /proc

            # Apply the change to /proc persistently by editing /etc/fstab
            proc            /proc           proc    rw,nosuid,nodev,noexec,relatime,hidepid=2,gid=1000   0 0

The OpenJ9 VM automatically detects when it is running in a docker container and uses a mechanism to detect when the VM is idle. When an idle state is detected, OpenJ9 runs a garbage collection cycle and releases free memory pages back to the operating system. The object heap is also compacted to make best use of the available memory for further application processing. Compaction is triggered by internal heuristics that look into the number of fragmented pages. Typically there is no need to force a compaction.

For cloud services that charge based on memory usage, maintaining a small footprint can generate cost savings. For more information about tuning options that control this process, see [`-XX:IdleTuningMinIdleWaitTime`](xxidletuningminidlewaittime.md).

### Cryptographic operations

OpenJDK uses the in-built Java cryptographic implementation by default. However, native cryptographic implementations typically provide better performance. OpenSSL is a native open source cryptographic toolkit for Transport Layer Security (TLS) and Secure Sockets Layer (SSL) protocols, which is well established and used with many enterprise applications. For more information, see [OpenSSL](openssl.md).

### Exploiting GPUs

OpenJ9 provides both the [CUDA4J API](api-cuda.md)<!-- Link to API --> and the [GPU API](api-gpu.md), <!-- Link to API -->which enables you to develop applications that can take advantage of graphics processing unit (GPU) processing for suitable functions, such as sorting arrays. You can also enable the JIT compiler to offload certain processing tasks to a GPU by specifying the [`-Xjit:enableGPU`](xjit.md#enablegpu) option on the command line. When enabled, the JIT compiler determines when to offload tasks based on performance heuristics.

GPU processing is supported only on Linux little-endian systems, such as x86-64 and IBM Power LE, and Windows x86-64 systems. For more information about enabling GPU processing, see [Exploiting graphics processing units](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/gpu_overview.html).

Special consideration is needed when using the WDDM driver model for GPUs on Windows. Using the WDDM driver model means the GPU is also used as a display device and as such is subject to the [Timeout Detection and Recovery (TDR) mechanism](https://docs.microsoft.com/en-us/windows-hardware/drivers/display/timeout-detection-and-recovery) of Windows. If you are running demanding GPU workloads, you should increase the timeout from the default 2 seconds. More detail may be found in [NVIDIA's Installation Guide for Windows](https://docs.nvidia.com/cuda/cuda-installation-guide-microsoft-windows/index.html#driver-model).

### Hardware acceleration

On AIX&reg; systems that contain the Nest accelerator (NX) co-processor, OpenJ9 can take advantage of the `zlibNX` library. This library is an enhanced version of the `zlib` compression library that supports hardware-accelerated data compression and decompression. The `zlibNX` library is supported on AIX version 7.2 TL4 and later and must be installed on the system. The Nest accelerator (NX) co-processor is available on IBM POWER9&reg; systems. To learn more about `zlibNX`, see [Data compression by using the zlibNX library](https://www.ibm.com/support/knowledgecenter/ssw_aix_72/performance/zlibNX.html).

## Runtime options

Runtime options are specified on the command line and include system properties, standard options, nonstandard (**-X**) options, and **-XX** options. For a detailed list of runtime options, see [OpenJ9 command-line options](cmdline_specifying.md)

## Default settings

If you do not specify any options on the command line at run time, the OpenJ9 VM starts with default settings that define how it operates. For more information about these settings, see [Default settings for the OpenJ9 VM](openj9_defaults.md).

## ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Using jlink

On Java 11 and later, you can use the `jlink` utility to create a custom OpenJ9 runtime image, which allows you to optimize image size.
If you do not require translations from the English language, the translation files can be removed to further optimize the size. You can achieve this by specifying the `--exclude-files=**java_**.properties` option when you run `jlink`. The default English `java.properties` file is unaffected.

## ![Start of content that applies to Java 16 and later](cr/java16plus.png) Using jpackage
**(Linux, macOS, and Windows only)**

You can use the `jpackage` utility to package a Java application into a platform-specific package that includes all of the necessary dependencies. Full details of the tool are available at [JEP 392: Packaging Tool](https://openjdk.org/jeps/392). Instructions for using it and the various options available are documented in the Oracle Tool Specifications: [The jpackage Command](https://docs.oracle.com/en/java/javase/14/docs/specs/man/jpackage.html).

## Troubleshooting

The OpenJ9 diagnostic component contains extensive features to assist with problem determination. Diagnostic data is produced under default conditions, but can also be controlled by starting the VM with the [-Xdump option](xdump.md) or using the `com.ibm.jvm.Dump` API. You can also trace Java applications, methods, and VM operations by using the [-Xtrace option](xtrace.md).

To get started, read [Diagnostic tools and data](diag_overview.md).


<!-- ==== END OF TOPIC ==== introduction.md ==== -->
