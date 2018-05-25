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

# Release notes - version 0.9.0

[Version 0.9.0 release plan](https://projects.eclipse.org/projects/technology.openj9/releases/0.9.0/plan).

What's new in this release?

- [The idle tuning feature is now supported on Linux running on Power Systems and IBM Z Systems.](#idle-tuning-feature)
- ![Start of content that applies only to Java 9 and later](cr/java9plus.png)[ A command line option is provided to automatically set a larger Java heap size for applications that run in containers. ](#modifying-the-default-java-heap-size-for-applications-that-run-in-containers)![End of content that applies only to Java 9 or later](cr/java_close.png)
- [You can now specify the maximum Java heap size as a percentage value.](#specifying-the-maximum-java-heap-size-as-a-percentage-value)
<!--- [A restriction is removed for Linux on IBM Z and z/OS systems when specifying an object heap size that is a multiple of the page size](#large-page-support-on-ibm-z-systems)-->
- [Shared classes now supports nested jar files.](#shared-classes-support-for-nested-jar-files)
- ![Start of content that applies only to Java 9 and later](cr/java9plus.png)[ Changes to the `java.lang.String` class. ](#changes-to-the-javalangstring-class)![End of content that applies only to Java 9 or later](cr/java_close.png)



## Idle tuning feature

The idle tuning feature in OpenJ9 keeps your memory footprint small by releasing unused memory back to the
operating system. Prior to Eclipse v0.9.0 this feature was available only on Linux x86 architectures with the
`gencon` garbage collection policy. From v0.9.0, this feature is now available on Linux for IBM POWER and IBM Z
architectures. For more information about this feature, see the following command line options, which control this
behavior:

 

## Modifying the default Java heap size for applications that run in containers

![Start of content that applies only to Java 9 and later](cr/java9plus.png) However, when using container technology, applications are typically run on their own and do not need to compete for memory. In this release, changes
are introduced for OpenJDK 9 and later builds to detect when OpenJ9 is running inside a container. If your application is running in a container and
you want the VM to allocate a larger fraction of memory to the Java heap, set the `-XX:+UserContainerSupport` option on the command line.

The following table shows the changes that are made to the maximum Java heap size setting when `-XX:+UserContainerSupport` is set:

| Physical memory `<size>`   | Maximum Java heap size (v0.8.0) | Maximum Java heap size (v0.9.0) |
|----------------------------|---------------------------------|---------------------------------|
| Less than 1 GB             | 50% `<size>`                    | 50% `<size>`                    |
| 1 GB - 2 GB                | `<size>` - 512M                 | `<size>` - 512M                 |
| Greater than 2 GB          | 25% `<size>`                    | 75% `<size>`                    |

![End of content that applies only to Java 9 or later](cr/java_close.png)

## Specifying the maximum Java heap size as a percentage value

OpenJ9 now supports setting the heap size as a percentage of the physical memory. The following OpenJDK options are recognized and can be
set for the VM:

- [-XX:MaxRAMPercentage](add link when topic available)
- [-XX:InitialRAMPercentage](add link when topic available)

<!--## Large page support on IBM Z systems

On IBM Z systems, specifying an object heap size that is a multiple of the page size used to require another page of memory. For example, if the page size is 2 GB, setting `-Xmx2G`used to require 4 GB of memory. The extra page of memory is no longer required.-->

## Shared classes support for nested jar files

Changes are made to the `com.ibm.oti.shared` API to support nested jar files.

## Changes to the `java.lang.String` class

![Start of content that applies only to Java 9 and later](cr/java9plus.png) To match the behavior of OpenJDK, `java.lang.String` no longer has a count field, which changes the way that `String.subString()` works compared to Java 8. `String.subString()` now copies the value array. Similarly `StringBuffer`  and `StringBuilder` do not share the value array with any `String` created by `toString()`. ![End of content that applies only to Java 9 or later](cr/java_close.png)


For a complete list of changes, including known limitations, see the [release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.9/0.9.md)

<!-- ==== END OF TOPIC ==== cmdline_general.md ==== -->
