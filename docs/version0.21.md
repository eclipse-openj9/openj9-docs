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


# What's new in version 0.21.0

The following new features and notable changes since version 0.20.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Application Programming Interface (API) documentation](#application-programming-interface-api-documentation)
- [Performance improvements](#performance-improvements)
- [New `-XX:[+|-]HandleSIGABRT` option added](#new-xx-handlesigabrt-option-added)
- [New `-XX:[+|-]PrintFlagsFinal` option added](#new-xx-printflagsfinal-option-added)
- [Update to `NoClassDefFoundError` exception message](#update-to-noclassdeffounderror-exception-message)
- [macOS&reg; shared libraries version updated](#macos-shared-libraries-version-updated)
- [Support for OpenJDK HotSpot options](#support-for-openjdk-hotspot-options)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.21.0 supports OpenJDK 8, 11, and 14. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 14](https://adoptopenjdk.net/archive.html?variant=openjdk14&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).


### Application Programming Interface (API) documentation

API documentation that applies to OpenJ9 can now be found in this user documentation for both JDK 8 and JDK 11. The documentation includes links to Oracle API documentation for information that is not specific to OpenJ9. See [API overview](api-overview.md).

### Performance improvements

- If the [-Xtune:virtualized](xtunevirtualized.md) command line option is used, the default JIT scratch memory limit is now reduced from 256 MB to 16 MB. This reduces the peak from JIT compilation activity, allowing you to size containers more easily, based on the particular application's memory usage.

- If the JIT is running in a container and no swap space is defined, the JIT dynamically adjusts its scratch memory consumption based on the amount of free physical memory available, to avoid out-of-memory (OOM) occurrences.

- Several performance features were added to the AArch64 JIT compiler implementation that led to a throughput improvement on multiple applications of at least 20%. The most notable improvements were seen in global register allocation, recompilation (without profiling), CUDA support, concurrent scavenge GC policy, and the inlined code sequence for object allocations.

### New `-XX:[+|-]HandleSIGABRT` option added

This option affects the handling of the operating system signal `SIGABRT`. For compatibility with the reference implementation, set `-XX:-HandleSIGABRT`. For more information, see [`-XX:[+|-]HandleSIGABRT`](xxhandlesigabrt.md). 

### New `-XX:[+|-]PrintFlagsFinal` option added

This release provides an initial implementation of the `-XX:[+|-]PrintFlagsFinal` option. It is currently incomplete and outputs only a subset of parameters. Over time, we expect more options to be added to the output. See [`-XX:[+|-]PrintFlagsFinal`](xxprintflagsfinal.md) for more details about this option.

### Update to `NoClassDefFoundError` exception message

The order in which class names are printed in a `NoClassDefFoundError` exception message now matches the output reported by HotSpot.

For example, in the following exception message:
```
java.lang.NoClassDefFoundError: mypackage/Main (wrong name: Main)
```
`mypackage/Main` is the class name encountered by the VM in the `.class` file, but "wrong name" `Main` was the provided class name. Prior to this update to the exception message, the encountered class name and the provided class name were swapped in the `NoClassDefFoundError` exception message.

### macOS shared libraries version updated

The version information for shared libraries on macOS has been updated from 0.0.0 to 1.0.0. If an application has linked against a shared library from a previous OpenJ9 release, it needs to be re-linked against the new release. Failure to re-link causes an error `Incompatible library version`, `requires version 0.0.0`.

### ![Start of content that applies to Java 14 and later](cr/java14plus.png) Support for OpenJDK HotSpot options

For compatibility, the following OpenJDK HotSpot option is now supported by OpenJ9:

- [`-XX:[+|-]ShowCodeDetailsInExceptionMessages`](xxshowcodedetailsinexceptionmessages.md). This option is a part of [JEP 358: Helpful NullPointerExceptions](https://openjdk.org/jeps/358) and specifies whether the extended message is displayed or not when a `NullPointerException` is generated by a VM. You can enable this feature with the `-XX:+ShowCodeDetailsInExceptionMessages` option. ![End of content that applies to Java 14 and later](cr/java_close.png)

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.20.0 and version 0.21.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.21/0.21.md).

<!-- ==== END OF TOPIC ==== version0.21.md ==== -->
