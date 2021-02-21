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

# What's new in version 0.25.0

The following new features and notable changes since v 0.24.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 16 plus](cr/java16plus.png) [New JDK 16 features](#new-jdk-16-features)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [Support for the `-verbose:module` option](#support-for-the-verbosemodule-option)
- [Change the default `zlib` library on AIX&reg;](#change-the-default-zlib-library-on-aix)


## Features and changes

### Binaries and supported environments

OpenJ9 release 0.25.0 supports OpenJDK 16. OpenJDK 16 with Eclipse OpenJ9 is *not* a long term support (LTS) release.

Although it might be possible to build an OpenJDK 8 or OpenJDK 11 with OpenJ9 release 0.25.0, testing at the project is not complete and therefore support for new features that apply to these Java versions is not available.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 16 plus](cr/java16plus.png) New JDK 16 features

The following features are supported by OpenJ9:

- [JEP 338](https://openjdk.java.net/jeps/338): Vector API (incubator)
    - The OpenJ9 project is adding optimizations for this feature.
- [JEP 390](https://openjdk.java.net/jeps/390): Warnings for value-based classes
- [JEP 395](https://openjdk.java.net/jeps/395): Records
- [JEP 397](https://openjdk.java.net/jeps/397): Sealed Classes (second preview)

The following features will be supported by OpenJ9 in a future release:

- [JEP 389](https://openjdk.java.net/jeps/389): Foreign linker API (incubator)
- [JEP 393](https://openjdk.java.net/jeps/393): Foreign-memory access API (third incubator)

The following features are implemented in OpenJDK and available in any builds of OpenJDK 16 with OpenJ9:

- [JEP 347](https://openjdk.java.net/jeps/347): Enable C++ 14 language features
- [JEP 380](https://openjdk.java.net/jeps/380): Unix-domain socket channels
- [JEP 394](https://openjdk.java.net/jeps/394): Pattern matching for `instanceof`
- [JEP 396](https://openjdk.java.net/jeps/396): Strongly encapsulate JDK internals by default
- [JEP 392](https://openjdk.java.net/jeps/392): Packaging tool
    - **(Linux&reg;, macOS&reg;, and Windows&trade; only)** Promoted from incubation to a production-ready feature in this release. See [Using `jpackage`](introduction.md#using-jpackage) for details.

You can find the full list of features for JDK 16 at the [OpenJDK project](http://openjdk.java.net/projects/jdk/16/). Any remaining features that are listed do not apply to OpenJ9.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Applications might be adversely affected by
JEP 396 if they make use of internal APIs. You should update your application to use standard APIs. To temporarily work around this problem, set `--illegal-access=permit` on the command line, which prints a warning that is similar to the following example when an illegal access call is made:

```
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.openj9.test.com.ibm.jit.Test_JITHelpers (file:/home/jenkins/workspace/Test_openjdk11_j9_sanity.functional_ppc64_aix_Nightly_testList_1/jvmtest/functional/Java8andUp/GeneralTest.jar) to field java.lang.String.value
WARNING: Please consider reporting this to the maintainers of org.openj9.test.com.ibm.jit.Test_JITHelpers
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
```

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Support for the `-verbose:module` option

The `-verbose:module` option is now supported for Java 11 and later releases. This option writes information to `stderr` for each module that is loaded and unloaded.

### Change the default `zlib` library on AIX&reg;

OpenJ9 for AIX&reg; uses the system `zlib` library by default instead of a bundled copy.

OpenJ9 on AIX&reg; systems uses the hardware-accelerated `zlibNX` if the Nest accelerators (NX) co-processor is enabled and the library is installed. To learn more about hardware acceleration and the `zlibNX` library see [Hardware acceleration](introduction.md#hardware-acceleration).

## Full release information

To see a complete list of changes between Eclipse OpenJ9 v 0.24.0 and v 0.25.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.25/0.25.md).


<!-- ==== END OF TOPIC ==== version0.25.md ==== -->
