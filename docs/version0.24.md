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

# What's new in version 0.24.0

The following new features and notable changes since version 0.23.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Changes to message logging](#changes-to-message-logging)
- [Support for the `JAVA_OPTIONS` environment variable](#support-for-the-java_options-environment-variable)
- [`-XX:[+|-]PortableSharedCache` option support update](#-xx-portablesharedcache-option-support-update)
- [![Start of content that applies to Java 15+](cr/java15plus.png) `-XX:[+|-]ShareAnonymousClasses` option behavior update](#-xx-shareanonymousclasses-option-behavior-update)
- [Additional parameters for `jcmd Dump` commands](#additional-parameters-for-jcmd-dump-commands)
- [Change in behavior for the `jextract` utility](#change-in-behavior-for-the-jextract-utility)
- [New diagnostic suboption for `-Xcheck:jni` for fatal JNI errors](#new-diagnostic-suboption-for-xcheckjni-for-fatal-jni-errors)
- [Improved diagnostic information in a Java dump file on processor features](#improved-diagnostic-information-in-a-java-dump-file-on-processor-features)
- [Helm chart available for deploying JITServer](#helm-chart-available-for-deploying-jitserver)


## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.24.0 supports OpenJDK 8, 11, and 15.

Windows&reg; builds for Java&trade; 8 are now compiled with Microsoft&reg; Visual Studio 2013. The Visual Studio redistributable files included with the build are updated to match.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).


### Changes to message logging

[JEP 158](https://openjdk.org/jeps/158) introduces the `-Xlog` option as a common logging system for all components of a Java virtual machine. To avoid confusion with the reference implementation, the [`-Xsyslog`](xsyslog.md) option replaces the existing OpenJ9 [`-Xlog`](xlog.md) option for message logging. For compatibility with the reference implementation, a limited set of `-Xlog` suboptions are supported.

A new option, [`-XX:[+|-]LegacyXlogOption`](xxlegacyxlogoption.md), controls how `-Xlog` is processed when set on the command line.

- When `-XX:-LegacyXlogOption` is set, the `-Xlog` option is recognized when a form of this option  runs that requests garbage collection (GC) logging.
    - If any `-Xlog` GC log requests are set, these options are mapped to the equivalent OpenJ9 verbose GC command line options. For more information, see [`-Xlog`](xlog.md).
- Setting `-XX:+LegacyXLogOption` provides backward compatibility with the legacy `-Xlog` option, which can be specified on the command line with the parameters documented for the `-Xsyslog` option. That is, these options can be used interchangeably. If you rely on the legacy `-Xlog` option and cannot easily migrate to the `-Xsyslog` option, you must set this option on the command line.


### Support for the `JAVA_OPTIONS` environment variable

For compatibility with the reference implementation, OpenJ9 now supports the `JAVA_OPTIONS` environment variable. This environment variable  can be used to set command line options, as described in [OpenJ9 command-line options](cmdline_specifying.md) and [Environment variables](env_var.md). Options specified by `JAVA_OPTIONS` can be overridden by options specified by `OPENJ9_JAVA_OPTIONS`.


### `-XX:[+|-]PortableSharedCache` option support update

The `-XX:[+|-]PortableSharedCache` option is now supported on IBM Z&reg; and POWER&reg; platforms. AOT-compiled code that is generated with this option is guaranteed to be portable across IBM z10 or newer microarchitectures on IBM Z platforms and IBM POWER8&reg; or newer microarchitectures on POWER platforms. See [`-XX:[+|-]PortableSharedCache`](xxportablesharedcache.md) for more details about this option.


### ![Start of content that applies to Java 15+](cr/java15plus.png) `-XX:[+|-]ShareAnonymousClasses` option behavior update

In earlier releases of OpenJ9, the `-XX:[+|-]ShareAnonymousClasses` option enables or disables the storage of VM anonymous classes in the shared classes cache. From OpenJ9 0.24.0 for OpenJDK 15 binaries, this option also controls the storage of hidden classes. See [`-XX:[+|-]ShareAnonymousClasses`](xxshareanonymousclasses.md) for more details about this option.


### Additional parameters for `jcmd Dump` commands

You can now include additional parameters for `jcmd Dump` commands as indicated in the following list:

- `Dump.system`, `Dump.heap`, `Dump.java`, and `Dump.snap` accept an optional `request=<requests>` parameter.
- `Dump.heap` accepts an optional `opts=<options>` parameter.

These parameters, including the `<file path>` parameter, can be in any order. The default for both system and heap dumps is now: `request=exclusive+prepwalk`. For further details, refer to the following `-Xdump` suboptions: [`request=<requests>`](xdump.md#requestltrequestsgt) and [`opts=<options>`](xdump.md#optsltoptionsgt). For more information about `jcmd`, see [Java diagnostic command (jcmd) tool](tool_jcmd.md).


### Change in behavior for the `jextract` utility

The `jextract` utility gathers relevant files following a system dump to assist with problem determination. It is important that the `jextract` utility is run from the same SDK that generated the dump. From this release, if the build ID of the `jextract` utility does not match the build ID of the SDK that is recorded in the system dump, an exception message is generated. To force `jextract` to continue, a new option, `-r`, is introduced. For more information, see [Dump extractor](tool_jextract.md).


### New diagnostic suboption for `-Xcheck:jni` for fatal JNI errors

A new `abortonerror` suboption for `-Xcheck:jni` provides diagnostic data when fatal JNI errors occur. For more information, run `-Xcheck:jni:help`.


### Improved diagnostic information in a Java dump file on processor features

The **ENVINFO** section of a Java dump file now includes further information about processor features. This information helps to diagnose problems associated with JIT and AOT compilations that depend on underlying hardware. For an example that shows the information provided when JIT is enabled, see the **CPU Information** (`2CIJITFEATURE`,`2CIAOTFEATURE`) section in the [Java dump](dump_javadump.md#envinfo) topic.

### Helm chart available for deploying JITServer

A Helm Chart is now available for easier deployment of JITServer technology in a Kubernetes or OpenShift cluster. You can find the chart (**openj9-jitserver-chart**) in the [JITServer Helm repository](https://github.com/eclipse-openj9/openj9-utils/tree/master/helm-chart/openj9-jitserver-chart), which contains a complete set of usage instructions. For an introduction to JITServer technology, see [JITServer (tech. preview)](jitserver.md).

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.23.0 and version 0.24.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.24/0.24.md).

<!-- ==== END OF TOPIC ==== version0.24.md ==== -->
