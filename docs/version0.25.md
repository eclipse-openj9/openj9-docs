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

# What's new in version 0.25.0

The following new features and notable changes since version 0.24.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 16 plus](cr/java16plus.png) [New JDK 16 features](#new-jdk-16-features)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [Support for the `-verbose:module` option](#support-for-the-verbosemodule-option)
- [Enabling `zlibNX` hardware-accelerated data compression and decompression on AIX](#enabling-zlibnx-hardware-accelerated-data-compression-and-decompression-on-aix)
- [z/OS support for the `%sysname` dump token](#zos-support-for-the-sysname-dump-token)
- [Single build for compressed references and non-compressed references](#single-build-for-compressed-references-and-non-compressed-references)


## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.25.0 supports OpenJDK 16. OpenJDK 16 with Eclipse OpenJ9 is *not* a long term support (LTS) release.

Although it might be possible to build an OpenJDK 8 or OpenJDK 11 with OpenJ9 release 0.25.0, testing at the project is not complete and therefore support for new features that apply to these Java versions is not available.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 16 plus](cr/java16plus.png) New JDK 16 features

The following features are supported by OpenJ9:

- [JEP 338](https://openjdk.org/jeps/338): Vector API (incubator)
    - OpenJ9 adds optimizations for this feature.
- [JEP 390](https://openjdk.org/jeps/390): Warnings for value-based classes
    - OpenJ9 adds option [`-XX:DiagnoseSyncOnValueBasedClasses=<number>`](xxdiagnosesynconvaluebasedclasses.md) for compatibility with the reference implementation.
- [JEP 395](https://openjdk.org/jeps/395): Records
- [JEP 397](https://openjdk.org/jeps/397): Sealed Classes (second preview)

The following features will be supported by OpenJ9 in a future release:

- [JEP 389](https://openjdk.org/jeps/389): Foreign linker API (incubator)
- [JEP 393](https://openjdk.org/jeps/393): Foreign-memory access API (third incubator)

The following features are implemented in OpenJDK and available in any builds of OpenJDK 16 with OpenJ9:

- [JEP 347](https://openjdk.org/jeps/347): Enable C++ 14 language features
- [JEP 380](https://openjdk.org/jeps/380): Unix-domain socket channels
- [JEP 394](https://openjdk.org/jeps/394): Pattern matching for `instanceof`
- [JEP 396](https://openjdk.org/jeps/396): Strongly encapsulate JDK internals by default
- [JEP 392](https://openjdk.org/jeps/392): Packaging tool
    - **(Linux&reg;, macOS&reg;, and Windows&trade; only)** Promoted from incubation to a production-ready feature in this release. See [Using `jpackage`](introduction.md#using-jpackage) for details.

You can find the full list of features for JDK 16 at the [OpenJDK project](https://openjdk.org/projects/jdk/16/). Any remaining features that are listed do not apply to OpenJ9.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Applications might be adversely affected by
JEP 396 if they make use of internal APIs. You should update your application to use standard APIs. To temporarily work around this problem, set `--illegal-access=permit` on the command line, which prints a warning that is similar to the following example when an illegal access call is made:

```
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.openj9.test.com.ibm.jit.Test_JITHelpers (file:/home/jenkins/workspace/Test_openjdk11_j9.functional_ppc64_aix_Nightly_testList_1/jvmtest/functional/Java8andUp/GeneralTest.jar) to field java.lang.String.value
WARNING: Please consider reporting this to the maintainers of org.openj9.test.com.ibm.jit.Test_JITHelpers
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
```

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Support for the `-verbose:module` option

The `-verbose:module` option is now supported for Java 11 and later releases. This option writes information to `stderr` for each module that is loaded and unloaded.

### Enabling `zlibNX` hardware-accelerated data compression and decompression on AIX

By default, AIX&reg; uses the system `zlib` library for data compression and decompression.

On systems that contain the Nest accelerator (NX) co-processor, OpenJ9 now uses the `zlibNX` library instead, if it is installed. To learn more about hardware acceleration and the `zlibNX` library, see [Hardware acceleration](introduction.md#hardware-acceleration).

### z/OS support for the `%sysname` dump token

The `%sysname` dump token is added on z/OS, which equates to the SYSNAME sysparm. See [Dump agent tokens](xdump.md#dump-agent-tokens).

### Single build for compressed references and non-compressed references

A single build now supports both compressed references and non-compressed references. The object reference mode is selected at run time based on the specified heap size ([`-Xmx`](xms.md)) or by using command-line options that control the selection of compressed references.

If you used a large heap build for an earlier release of OpenJ9 because you did not require compressed references, you might need to turn it off if compressed references mode is being selected automatically at run time. Use the [`-Xnocompressedrefs`](xcompressedrefs.md) option when you start your application.

The `compressedrefs` directory is no longer present in the single build.

To learn about the benefits of using compressed references, see [Compressed references](allocation.md#compressed-references).

## Full release information

To see a complete list of changes between Eclipse OpenJ9 version 0.24.0 and version 0.25.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.25/0.25.md).


<!-- ==== END OF TOPIC ==== version0.25.md ==== -->
