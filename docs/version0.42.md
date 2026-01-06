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

# What's new in version 0.42.0

The following new features and notable changes since version 0.41.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) [OpenJ9 `jextract` removed](#openj9-jextract-removed) ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)
- [Change in the `System.gc()` call behavior](#change-in-the-systemgc-call-behavior)
- [New `-XX:[+|-]IProfileDuringStartupPhase` option added](#new-xx-iprofileduringstartupphase-option-added)
- ![Start of content that applies to Java 21 (LTS)](cr/java21.png) [New `-XX:[+|-]CRIUSecProvider` option added](#new-xx-criusecprovider-option-added) ![End of content that applies to Java 21 (LTS)](cr/java_close_lts.png)
- ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) [New JDK 21 features](#new-jdk-21-features) ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.42.0 supports OpenJDK 21.

OpenJDK 21 with Eclipse OpenJ9 is a long term support (LTS) release and supersedes OpenJDK 20 with Eclipse OpenJ9.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) OpenJ9 `jextract` removed

The dump extractor tool, OpenJ9 `jextract`, that was deprecated since the [0.26.0 release](version0.26.md) is now removed from Java 21 and later. The [`jpackcore`](tool_jextract.md) tool replaced the OpenJ9 `jextract` tool after its deprecation. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

### Change in the `System.gc()` call behavior

The `System.gc` call triggers a global garbage collection (GC) cycle, which is a stop-the-world GC. If the `System.gc()` call was made during an active GC cycle where the concurrent operation was in progress, the status of objects might not be updated. An object that was reachable when the concurrent operation started might be considered as reachable even if the object is no longer in use later. The `System.gc()` call had to be called twice explicitly to ensure that all unreachable objects are identified and cleared.

Now, the `System.gc()` call triggers the GC cycle twice internally to clear the unreachable objects that were not identified during the first GC cycle. The call also triggers finalization of the objects in the Finalization queues.

For more information, see [Garbage collection](gc_overview.md).

### New `-XX:[+|-]IProfileDuringStartupPhase` option added

The VM uses heuristics to decide whether to collect interpreter profiling information during the VM startup. You can overrule the heuristics and control the collection of the profiling information during the startup phase by using the [`-XX:[+|-]IProfileDuringStartupPhase`](xxiprofileduringstartupphase.md) option.

### ![Start of content that applies to Java 21 (LTS)](cr/java21.png) New `-XX:[+|-]CRIUSecProvider` option added

When you enable CRIU support, all the existing security providers are removed from the security provider list during the checkpoint phase and `CRIUSECProvider` is added by default.

You can now control the use of `CRIUSECProvider` during the checkpoint phase with the `-XX:[+|-]CRIUSecProvider` option. You can use all the existing security providers instead of the `CRIUSECProvider` by specifying the `-XX:-CRIUSecProvider` option.

For more information, see [`-XX:[+|-]CRIUSecProvider`](xxcriusecprovider.md). ![End of content that applies to Java 21 (LTS)](cr/java_close_lts.png)

### ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) New JDK 21 features

The following features are supported by OpenJ9:

- [JEP 440](https://openjdk.java.net/jeps/440): Record Patterns
- [JEP 442](https://openjdk.java.net/jeps/442): Foreign Function & Memory API (Third Preview)
- [JEP 444](https://openjdk.java.net/jeps/444): Virtual Threads
- [JEP 446](https://openjdk.java.net/jeps/446): Scoped Values (Preview)
- [JEP 448](https://openjdk.java.net/jeps/448): Vector API (Sixth Incubator)
- [JEP 451](https://openjdk.java.net/jeps/451): Prepare to Disallow the Dynamic Loading of Agents
- [JEP 453](https://openjdk.java.net/jeps/453): Structured Concurrency (Preview)


The following features are implemented in OpenJDK and available in any build of OpenJDK 21 with OpenJ9:

- [JEP 430](https://openjdk.java.net/jeps/430): String Templates (Preview)
- [JEP 431](https://openjdk.java.net/jeps/431): Sequenced Collections
- [JEP 441](https://openjdk.java.net/jeps/441): Pattern Matching for switch
- [JEP 443](https://openjdk.java.net/jeps/443): Unnamed Patterns and Variables (Preview)
- [JEP 445](https://openjdk.java.net/jeps/445): Unnamed Classes and Instance Main Methods (Preview)
- [JEP 452](https://openjdk.java.net/jeps/452): Key Encapsulation Mechanism API


You can find the full list of features for JDK 21 at the [OpenJDK project](https://openjdk.org/projects/jdk/21/).
Any remaining features that are listed either do not apply to OpenJ9 or are not implemented and hence not applicable to OpenJ9 in this release. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.41.0 and v0.42.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.42/0.42.md).

<!-- ==== END OF TOPIC ==== version0.42.md ==== -->
