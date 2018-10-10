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

# Eclipse OpenJ9

OpenJ9 is a high performance, scalable, Java&trade; virtual machine (VM) implementation that is fully compliant with the [Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/index.html).

At run time, the VM interprets the Java bytecode that is compiled by the Java compiler. The VM acts as a translator between the language and the underlying operating system and hardware. A Java program requires a specific VM to run on a particular platform, such as Linux&trade;, z/OS&reg;, or Windows&trade;.

This reference material provides information about the VM configuration and tuning options, together with the default settings.


## Configuring your system

For normal operation, certain environment variables must be set at the operating system level. Depending on your system environment, you might also want to set other configuration options that allow the VM to exploit hardware and operating system features. Read [Customizing your system](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure.html) to learn more about the following options:

- Setting the PATH and CLASSPATH environment variable.
- Setting the LIBPATH or LD_LIBRARY_PATH environment variable (AIX&reg; and Linux).
- Setting ulimits on AIX and Linux systems to ensure that the operating system allocates sufficient resources for your application.
- Setting region size, BPXPRM parameters, and Language Environment&reg; runtime options on z/OS systems.
- If your application allocates a large amount of memory and frequently accesses that memory, you might want to enable large page support on your system. See [Configuring large page memory allocation](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/j9_configure_large_page.html).
- Configuring Dynamic LPAR support on AIX systems.

## Performance tuning

OpenJ9 is configured to start with a set of default options that provide the optimal runtime environment for Java applications with typical workloads. However, if your application is atypical, you can improve performance by tuning the OpenJ9 VM. You can also improve performance by enabling hardware features or using specific APIs in your application code.

### Garbage collection policies
OpenJ9 includes several garbage collection policies. To learn more about these policies and the types of application workload that can benefit from them, see [Garbage Collection](gc.md).

### Class data sharing
You can share class data between running VMs, which can reduce the startup time for a VM once the cache has been created. For more information, see [Class Data Sharing](shrc.md).

### Native data operations
If your Java application manipulates native data, consider writing your application to take advantage of methods in the Data Access Accelerator API.

### Cloud optimizations  
To improve the performance of applications that run in the cloud, try setting the following tuning options:

  - Use a shared classes cache (`-Xshareclasses -XX:SharedCacheHardLimit=200m -Xscmx60m`) with Ahead-Of-Time (AOT) compilation to improve your startup time. For more information, see [Class Data Sharing](shrc.md) and [AOT Compiler](aot.md).
  - Use the idle VM settings to maintain a smaller footprint, which can generate cost savings for cloud services that charge based on memory usage. The idle tuning mechanism detects when the VM is idle, releasing free memory pages to keep the footprint as small as possible and keep your running costs to a minimum. For more information, see [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md).
  - Use the [-Xtune:virtualized](xtunevirtualized) option, which configures OpenJ9 for typical cloud deployments where VM guests are provisioned with a small number of virtual CPUs to maximize the number of applications that can be run. When enabled, OpenJ9 adapts its internal processes to reduce the amount of CPU consumed and trim down the memory footprint. These changes come at the expense of only a small loss in throughput.

### Cryptographic operations

![Start of content that applies only to Java 8 (LTS)](cr/java8.png)

OpenJDK uses the in-built Java cryptographic implementation by default. However, native cryptographic implementations
typically provide better performance. OpenSSL is a native open source cryptographic toolkit for Transport Layer Security (TLS) and
Secure Sockets Layer (SSL) protocols, which is well established and used with many enterprise applications. The OpenSSL V1.1.x implementation is
currently supported for OpenJDK 8 with OpenJ9 for the Digest, CBC, and GCM algorithms.

OpenSSL support is not enabled by default. If you want to use this implementation to improve cryptographic performance, a number of system properties are
available to enable it.

If you want to enable native OpenSSL for all three supported algorithms, set the following system property on the command line when you start your application:

```
-Djdk.nativeCrypto=yes
```

Alternatively, you can enable one or more of the algorithms individually by using the following system properties:

- For **digest**, set `-Djdk.nativeDigest=yes`
- For **CBC**, set `-Djdk.nativeCBC=yes`
- For **GCM**, set `-Djdk.nativeGCM=yes`


To build a version of OpenJDK with OpenJ9 that includes OpenSSL v1.1.x support, follow the steps in our [detailed build instructions](https://github.com/eclipse/openj9/blob/master/doc/build-instructions/Build_Instructions_V8.md).

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** If you obtain an OpenJDK with OpenJ9 build from [AdoptOpenJDK](https://adoptopenjdk.net/) that includes OpenSSL v1.1.x or build a version yourself that includes OpenSSL v1.1.x support, the following acknowledgements apply in accordance with the license terms:

- *This product includes software developed by the OpenSSL Project for use in the OpenSSL Toolkit. (http://www.openssl.org/).*
- *This product includes cryptographic software written by Eric Young (eay@cryptsoft.com).*

![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)

## Runtime options

Runtime options are specified on the command line and include system properties, standard options, nonstandard (**-X**) options, and **-XX** options. For a detailed list of runtime options, see [OpenJ9 command-line options](cmdline_specifying.md)

## Default settings

If you do not specify any options on the command line at run time, the OpenJ9 VM starts with default settings that define how it operates. For more information about these settings, see [Default settings for the OpenJ9 VM](openj9_defaults.md).

## Troubleshooting

The OpenJ9 diagnostic component contains extensive features to assist with problem determination. Diagnostic data is produced under default conditions, but can also be controlled by starting the VM with the [-Xdump option](xdump.md) or using the `com.ibm.jvm.Dump` API. You can also trace Java applications, methods, and VM operations by using the [-Xtrace option](xtrace.md).

To get started, read [Diagnostic tools and data](diag_overview.md).


<!-- ==== END OF TOPIC ==== index.md ==== -->
