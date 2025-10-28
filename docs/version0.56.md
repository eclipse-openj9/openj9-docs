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

# What's new in version 0.56.0

The following new features and notable changes since version 0.55.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Change in the `getCpuLoad()` method return value based on the platform](#change-in-the-getcpuload-method-return-value-based-on-the-platform)
- [New `-Xgc` parameters related to macro fragmentation added](#new-xgc-parameters-related-to-macro-fragmentation-added)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png)[The NativeLibrary and SystemProcess JFR events are supported in all platforms except z/OS](#the-nativelibrary-and-systemprocess-jfr-events-are-supported-in-all-platforms-except-zos) ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)
- [The format of the `java.vm.version` system property value is updated to be compatible with the Runtime.Version parser](#the-format-of-the-javavmversion-system-property-value-is-updated-to-be-compatible-with-the-runtimeversion-parser)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.56.0 supports OpenJDK 8, 11, 17, 21, and 25.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Change in the `getCpuLoad()` method return value based on the platform

In all platforms except z/OS&reg;, if the `-XX:+CpuLoadCompatibility` option is set to enable the OpenJDK behavior of the `getProcessCpuLoad()` and `getCpuLoad()` methods in OpenJ9, the `getCpuLoad()` method now collects two internal samples so that a valid CPU usage value can be returned instead of `0` return value on the first call.

On z/OS, the CPU load that is reported by the `getCpuLoad` method is obtained directly from the system control blocks, without the need to collect samples. The `-XX:[+|-]CpuLoadCompatibility` option has no effect on z/OS, and a valid CPU usage value is always returned whatever be the setting of this option.

For more information, see [`-XX:[+|-]CpuLoadCompatibility`](xxcpuloadcompatibility.md).

### New `-Xgc` parameters related to macro fragmentation added

New parameters, `enableEstimateFragmentation` and `disableEstimateFragmentation` are added to the `-Xgc` option. You can use these options to control the calculating and reporting of the estimates of the macro fragmentation, as reported by verbose garbage collection (GC) at the end of Scavenge GCs.

For more information, see [`-Xgc`](xgc.md#disableestimatefragmentation).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) The NativeLibrary and SystemProcess JFR events are supported in all platforms except z/OS

From this release onwards, the NativeLibrary and SystemProcess JFR events are supported in all platforms except z/OS. Earlier, these JFR events were supported only in Linux&reg;.

To record and analyze the SystemProcess JFR event on z/OS (2.5 and 3.1), you must install [APAR OA62757](https://www.ibm.com/support/pages/apar/OA62757).

For more information, see [`-XX:[+|-]FlightRecorder`](xxflightrecorder.md). ![End of content that applies only to Java 11 and later](cr/java_close_lts.png)

### The format of the `java.vm.version` system property value is updated to be compatible with the Runtime.Version parser

The format of the `java.vm.version` system property value is modified from the previous releases. This change was initiated in the 0.55.0 release and it affects 0.56.0 and all future releases.

The new format of the value is standardized and structured and is parse-able by the `java.lang.Runtime.Version` class. This modification also changes the `-version` output. For example, `build openj9-0.56.0` changes to `build 11.0.29+7-openj9-0.56.0`. The OpenJ9 version, `openj9-0.56.0` in the example, is at the end of the optional component.

With this new structured format, the `java.lang.Runtime.Version` class parses the value and you can extract specific information such as the information related to the VM version. For example, following information can be extracted from `java.vm.version = 11.0.29+7-openj9-0.56.0`:

- `11.0.29` - Feature version of the OpenJDK
- `7` - Build number of the OpenJDK
- `openj9-0.56.0` - Additional build information

This also applies to the z/OS releases. For example, `z/OS-Release-17.0.16.0-b01` is changed to `17.0.17+10-zOS-Release-17.0.17.0-b01`.

For more information, see [Class Runtime.Version](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Runtime.Version.html).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.55.0 and v0.56.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.56/0.56.md).

<!-- ==== END OF TOPIC ==== version0.56.md ==== -->
