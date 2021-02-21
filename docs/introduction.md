<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# Getting started with OpenJ9

OpenJ9 is a high performance, scalable, Java&trade; virtual machine (VM) implementation that is fully compliant with the [Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/index.html).

At run time, the VM interprets the Java bytecode that is compiled by the Java compiler. The VM acts as a translator between the language and the underlying operating system and hardware. A Java program requires a specific VM to run on a particular platform, such as Linux&reg;, z/OS&reg;, or Windows&trade;.

This reference material provides information about the VM configuration and tuning options, together with the default settings.


## Configuring your system

For normal operation, certain environment variables must be set at the operating system level. Depending on your system environment, you might also want to set other configuration options that allow the VM to exploit hardware and operating system features. Read [Customizing your system](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure.html) to learn more about the following options:

- Setting the PATH and CLASSPATH environment variable.
- Setting the LIBPATH or LD_LIBRARY_PATH environment variable (AIX&reg; and Linux) to indicate where to find shared libraries.
- Setting ulimits on AIX and Linux systems to ensure that the operating system allocates sufficient resources for your application.
- Setting region size, BPXPRM parameters, and Language Environment&reg; runtime options on z/OS systems.
- If your application allocates a large amount of memory and frequently accesses that memory, you might want to enable large page support on your system. See [Configuring large page memory allocation](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_large_page.html).
- Configuring Dynamic LPAR support on AIX systems.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

1. On macOS&reg; systems, you must set the DYLD_LIBRARY_PATH environment variable to indicate where to find shared libraries. In addition, to ensure there are sufficient resources for your application, you might need to increase system limits by using `launchctl`, `ulimit`, or `kern.<VARIABLES>`. For further instructions, refer to the documentation for your macOS release.

2. On AIX systems, the [XL C++ Runtime](https://www.ibm.com/support/pages/xl-cc-runtime-aix-v16101-fix-pack-december-2018) is required to run JDK 14 or later.


## Performance tuning

OpenJ9 is configured to start with a set of default options that provide the optimal runtime environment for Java applications with typical workloads. However, if your application is atypical, you can improve performance by tuning the OpenJ9 VM. You can also improve performance by enabling hardware features or using specific APIs in your application code.

### Garbage collection policies
OpenJ9 includes several garbage collection policies. To learn more about these policies and the types of application workload that can benefit from them, see [Garbage Collection](gc.md).

### Class data sharing
You can share class data between running VMs, which can reduce the startup time for a VM once the cache has been created. For more information, see [Class Data Sharing](shrc.md).

### Native data operations
If your Java application manipulates native data, consider writing your application to take advantage of methods in the Data Access Accelerator (DAA) API.

The following functions are provided:

- Arithmetic, comparison, shifting, and validation operations for packed decimal data.
- Conversion operations between different binary coded decimal and Java binary types.
- Marshalling operations: marshalling and unmarshalling Java binary types, such as `short`, `int`, `long`, `float`, and `double`, to and from byte arrays.

You can gain a number of benefits by using the APIs provided:

- Improved application performance by avoiding object creation and intermediate processing, which can also put pressure on the Java heap.
- Hardware acceleration by automatically exploiting available hardware features on specific platforms.
- Platform independence for applications that are developed to take advantage of Data Access Acceleration.

For more information, see the [API documentation](api-overview.md).

### Cloud optimizations
To improve the performance of applications that run in containers, try setting the following tuning options:

- Use a shared classes cache (`-Xshareclasses -XX:SharedCacheHardLimit=200m -Xscmx60m`) with Ahead-Of-Time (AOT) compilation to improve your startup time. For more information, see [Class Data Sharing](shrc.md) and [AOT Compiler](aot.md).

- Use the [-Xtune:virtualized](xtunevirtualized.md) option, which configures OpenJ9 for typical cloud deployments where VM guests are provisioned with a small number of virtual CPUs to maximize the number of applications that can be run. When enabled, OpenJ9 adapts its internal processes to reduce the amount of CPU consumed and trim down the memory footprint. These changes come at the expense of only a small loss in throughput.

The OpenJ9 VM automatically detects when it is running in a docker container and uses a mechanism to detect when the VM is idle. When an idle state is detected, OpenJ9 runs a garbage collection cycle and releases free memory pages back to the operating system. The object heap is also compacted to make best use of the available memory for further application processing. Compaction is triggered by internal heuristics that look into the number of fragmented pages. Typically there is no need to force a compaction.

For cloud services that charge based on memory usage, maintaining a small footprint can generate cost savings. For more information about tuning options that control this process, see [`-XX:IdleTuningMinIdleWaitTime`](xxidletuningminidlewaittime.md).

### Cryptographic operations

OpenJDK uses the in-built Java cryptographic implementation by default. However, native cryptographic implementations
typically provide better performance. OpenSSL is a native open source cryptographic toolkit for Transport Layer Security (TLS) and
Secure Sockets Layer (SSL) protocols, which is well established and used with many enterprise applications. The OpenSSL V1.0.x and V1.1.x implementations are currently supported for the Digest, CBC, GCM, and RSA algorithms. The OpenSSL V1.1.x implementation is also supported for the ChaCha20 and ChaCha20-Poly1305 algorithms.

On Linux and AIX platforms, the OpenSSL 1.0.x or 1.1.x library is expected to be found on the system path. If you use a package manager to install OpenSSL, the system path will be updated automatically. On other platforms, the OpenSSL 1.1.x library is typically bundled.

OpenSSL support is enabled by default for all supported algorithms. If you want to limit support to specific algorithms, a number of
system properties are available for tuning the implementation.

Each algorithm can be disabled individually by setting the following system properties on the command line:


- To turn off **Digest**, set `-Djdk.nativeDigest=false`
- To turn off **ChaCha20** and **ChaCha20-Poly1305**, set `-Djdk.nativeChaCha20=false`. <i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** ![Start of content that applies to Java 8 (LTS)](cr/java8.png) These algorithms are not supported on Java 8 ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)
- To turn off **CBC**, set `-Djdk.nativeCBC=false`
- To turn off **GCM**, set `-Djdk.nativeGCM=false`
- To turn off **RSA**, set `-Djdk.nativeRSA=false`

You can turn off all the algorithms by setting the following system property on the command line:

```
-Djdk.nativeCrypto=false
```

To build a version of OpenJDK with OpenJ9 that includes OpenSSL support, follow the steps in our detailed build instructions:

- [OpenJDK 8 with OpenJ9](https://github.com/eclipse/openj9/blob/master/doc/build-instructions/Build_Instructions_V8.md).
- [OpenJDK 11 with OpenJ9](https://github.com/eclipse/openj9/blob/master/doc/build-instructions/Build_Instructions_V11.md).
- [OpenJDK 15 with OpenJ9](https://github.com/eclipse/openj9/blob/master/doc/build-instructions/Build_Instructions_V15.md).

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** If you obtain an OpenJDK with OpenJ9 build that includes OpenSSL or build a version yourself that includes OpenSSL support, the following acknowledgements apply in accordance with the license terms:

- *This product includes software developed by the OpenSSL Project for use in the OpenSSL Toolkit. (http://www.openssl.org/).*
- *This product includes cryptographic software written by Eric Young (eay@cryptsoft.com).*

### Exploiting GPUs

OpenJ9 provides both the [CUDA4J API](api-cuda.md) <!-- Link to API --> and the [GPU API](api-gpu.md), <!-- Link to API -->
which enables you to develop applications that can take advantage of graphics processing unit (GPU) processing for suitable functions, such as sorting arrays. You can also enable the JIT compiler to offload certain processing tasks to a GPU by specifying the [`-Xjit:enableGPU`](xjit.md#enablegpu) option on the command line. When enabled, the JIT compiler determines when to offload tasks based on performance heuristics.

GPU processing is supported only on Linux little-endian systems, such as x86-64 and IBM Power LE, and Windows x86-64 systems. For more information about enabling GPU processing, see [Exploiting graphics processing units](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/gpu_overview.html).

Special consideration is needed when using the WDDM driver model for GPUs on Windows. Using the WDDM driver model means the GPU is also used as a display device and as such is subject to the [Timeout Detection and Recovery (TDR) mechanism](https://docs.microsoft.com/en-us/windows-hardware/drivers/display/timeout-detection-and-recovery) of Windows. If you are running demanding GPU workloads, you should increase the timeout from the default 2 seconds. More detail may be found in [NVIDIA's Installation Guide for Windows](https://docs.nvidia.com/cuda/cuda-installation-guide-microsoft-windows/index.html#driver-model).

### Hardware acceleration

OpenJ9 on AIX&reg; uses the hardware-accelerated `zlibNX` library when available. `zlibNX` is an enhanced version of the `zlib` compression library that supports hardware-accelerated data compression and decompression by using the Nest accelerators (NX) co-processor, available in the IBM POWER9&reg; or newer processors. The library can be installed on AIX&reg; 7.2 with Technology Level 4 Expansion Pack and later. OpenJ9 in AIX&reg; systems with the NX co-processor enabled and `zlibNX` installed will make use of the library. To learn more about `zlibNX`, see [Data compression by using the zlibNX library](https://www.ibm.com/support/knowledgecenter/ssw_aix_72/performance/zlibNX.html).

## Runtime options

Runtime options are specified on the command line and include system properties, standard options, nonstandard (**-X**) options, and **-XX** options. For a detailed list of runtime options, see [OpenJ9 command-line options](cmdline_specifying.md)

## Default settings

If you do not specify any options on the command line at run time, the OpenJ9 VM starts with default settings that define how it operates. For more information about these settings, see [Default settings for the OpenJ9 VM](openj9_defaults.md).

## ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Using jlink

On Java 11 and later, you can use the `jlink` utility to create a custom OpenJ9 runtime image, which allows you to optimize image size.
If you do not require translations from the English language, the translation files can be removed to further optimize the size. You can achieve this by specifying the `--exclude-files=**java_**.properties` option when you run `jlink`. The default English `java.properties` file is unaffected.

## Using jpackage
**(Linux, macOS, and Windows only)**

You can use the `jpackage` utility to package a Java application into a platform-specific package that includes all of the necessary dependencies. Full details of the tool are available at [JEP 392: Packaging Tool](https://openjdk.java.net/jeps/392). Instructions for using it and the various options available, are documented in the Oracle Tool Specifications: [The jpackage Command](https://docs.oracle.com/en/java/javase/14/docs/specs/man/jpackage.html).

## Troubleshooting

The OpenJ9 diagnostic component contains extensive features to assist with problem determination. Diagnostic data is produced under default conditions, but can also be controlled by starting the VM with the [-Xdump option](xdump.md) or using the `com.ibm.jvm.Dump` API. You can also trace Java applications, methods, and VM operations by using the [-Xtrace option](xtrace.md).

To get started, read [Diagnostic tools and data](diag_overview.md).


<!-- ==== END OF TOPIC ==== introduction.md ==== -->
