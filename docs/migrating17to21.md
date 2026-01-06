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
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->


# Migrating from Java 17 to Java 21

Support for OpenJDK 21 was added in Eclipse OpenJ9&trade; version 0.42.0.

The following new OpenJ9 changes apply when OpenJ9 is built with Java&reg; SE 21 class libraries. This information exists elsewhere in the documentation but is summarized here for convenience.

## Support for JDK enhancement proposals (JEP)

The new JEPs that are supported are listed in the following topics:

- JDK 18: [What's new in version 0.32.0](version0.32.md)
- JDK 19: [What's new in version 0.37.0](version0.37.md)
- JDK 20: [What's new in version 0.39.0](version0.39.md)
- JDK 21: [What's new in version 0.42.0](version0.42.md)

## New OpenJ9 features and changes

The following table lists the new OpenJ9 features and notable changes with the OpenJ9 release in which they were added:

| Features and changes  | OpenJ9 release  |
|-----------------------|-----------------|
| The OpenJ9 `jextract` tool is removed. | 0.41.0  |
| New `-XX:[+\|-]ShowCarrierFrames` option added. You can use the `-XX:+ShowCarrierFrames` option to add the stack trace of the carrier thread in addition to the virtual thread stack trace to the `Throwable.getStackTrace()` method, if an exception occurs. | 0.41.0  |
| New `-XX:ContinuationCache` option added. You can optimize the virtual thread performance by tuning the continuation tier 1 and 2 cache size with the `-XX:ContinuationCache` option | 0.41.0  |
| Warnings are issued when the agents are loaded dynamically into a running VM after startup without specifying the `-XX:+EnableDynamicAgentLoading` option and the same agents were not loaded before. |  0.41.0    |
<!--| Linux&reg; builds for all platforms, except for the AArch64 64-bit, use gcc 11.2 instead of gcc 10.3. Linux AArch64 64-bit continues to use gcc 10.3. The Windows Visual Studio compiler is also changed from Microsoft Visual Studio 2019 to Microsoft Visual Studio 2022. See the list of [build environments](openj9_support.md#build-environments). | 0.42.0 |(SG - Commented this in 0.55.0 release (like the similar content that was deleted in the Migrating from Java 11 to Java 17 topic) It is an out-of-date statement because now all the latest JDK 8, JDK 11, JDK 17 also use gcc 11.2 and VS2022 as informed by Peter https://github.com/eclipse-openj9/openj9-docs/pull/1570#issuecomment-3200390025.)-->
