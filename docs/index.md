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

Welcome to the user documentation for the Eclipse OpenJ9 virtual machine (VM).

This user documentation supports the configuration, tuning, and diagnosis of the OpenJ9 VM in an OpenJDK runtime. However, due to differences between the Java SE class libraries, specific options might apply only to one Java SE version. Icons are used to indicate where differences apply. For example:

![Start of content that applies only to Java 8 (LTS)](cr/java8.png) This sentence applies only to Java 8 binaries that include the OpenJ9 VM. Icons for LTS releases are this colour. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)

![Start of content that applies only to Java 10 and later](cr/java10plus.png) This sentence applies only to Java 10 or later binaries that include the OpenJ9 VM. Icons for feature releases are this colour. ![End of content that applies only to Java 10 or later](cr/java_close.png)

To see which Java releases are LTS releases and which are feature releases, and for information about release cadence, supported platforms, and build environments, see [Supported environments](openj9_support.md).

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Documentation to support OpenJ9 is still under construction. The current content covers
some high level information about OpenJ9 components together with the command-line options and environment variables that you can use to configure the VM when you start your application. We expect further content to be contributed over time. Because OpenJ9 was contributed to the Eclipse Foundation by IBM, this content contains some links to additional information that forms part of the [IBM&reg; SDK, Java&trade; Technology Edition product documentation](https://www.ibm.com/support/knowledgecenter/SSYKE2/welcome_javasdk_family.html) in IBM Knowledge Center. That content supplements the documentation here until a more complete set of user documentation is available.

We welcome contributions to the user documentation. If you would like to get involved, please read our [Contribution guidelines](https://github.com/eclipse/openj9-docs/blob/master/CONTRIBUTING.md). If you spot any errors in the documentation, please raise an [issue](https://github.com/eclipse/openj9-docs/issues/new?template=documentation-error.md) at our GitHub repository.

## What is OpenJ9

OpenJ9 is a high performance, scalable, Java&trade; virtual machine (VM) implementation that is fully compliant with the [Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/index.html).

At run time, the VM interprets the Java bytecode that is compiled by the Java compiler. The VM acts as a translator between the language and the underlying operating system and hardware. A Java program requires a specific VM to run on a particular platform, such as Linux&trade;, z/OS&reg;, or Windows&trade;.

This reference material provides information about the VM configuration and tuning options, together with the default settings.

## Useful links

- [Eclipse OpenJ9 website home page](https://www.eclipse.org/openj9)
- [Eclipse OpenJ9 GitHub repository](https://github.com/eclipse/openj9)
- [Eclipse Foundation OpenJ9 project page](https://projects.eclipse.org/projects/technology.openj9)

You can obtain pre-built OpenJDK binaries from the AdoptOpenJDK project:

- [OpenJDK8 with OpenJ9](https://adoptopenjdk.net/releases.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK10 with OpenJ9](https://adoptopenjdk.net/releases.html?variant=openjdk10&jvmVariant=openj9)
- [OpenJDK11 with OpenJ9](https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=openj9) (coming soon)

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

## Runtime options

Runtime options are specified on the command line and include system properties, standard options, nonstandard (**-X**) options, and **-XX** options. For a detailed list of runtime options, see [OpenJ9 command-line options](cmdline_specifying.md)

## Default settings

If you do not specify any options on the command line at run time, the OpenJ9 VM starts with default settings that define how it operates. For more information about these settings, see [Default settings for the OpenJ9 VM](openj9_defaults.md).

## Supported environments

OpenJDK binaries that contain the OpenJ9 VM are supported on a range of hardware and operating systems. This range is expanding as work progresses at the Eclipse foundation. See the [current list of supported environments](openj9_support.md) for details.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** This user guide contains information about configuring, tuning, and debugging OpenJ9 on the z/OS&reg; platform. This content was contributed by IBM so that it is available when the work to create OpenJDK binaries for the z/OS platform is complete.

## Troubleshooting

The OpenJ9 diagnostic component contains extensive features to assist with problem determination. Diagnostic data is produced under default conditions, but can also be controlled by starting the VM with the [-Xdump option](xdump.md) or using the `com.ibm.jvm.Dump` API. You can also trace Java applications, methods, and VM operations by using the [-Xtrace option](xtrace.md).

To get started, read [Diagnostic tools and data](diag_overview.md).


<!-- ==== END OF TOPIC ==== index.md ==== -->
