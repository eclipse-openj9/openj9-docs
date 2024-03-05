<!--
* Copyright (c) 2017, 2024 IBM Corp. and others
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

# What's new in version 0.44.0

The following new features and notable changes since version 0.43.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Change in behavior of the `-Djava.security.manager` system property for OpenJDK version 8](#change-in-behavior-of-the-djavasecuritymanager-system-property-for-openjdk-version-8)
- ![Start of content that applies to Java 21 (LTS)](cr/java21.png) [Display of multiple warnings on loading the same agent restricted on AIX&reg; systems](#display-of-multiple-warnings-on-loading-the-same-agent-restricted-on-aix-systems) ![End of content that applies to Java 21 (LTS)](cr/java_close_lts.png)
## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.44.0 supports OpenJDK 8, 11, 17, and 21.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Change in behavior of the `-Djava.security.manager` system property for OpenJDK version 8

From OpenJDK version 18 onwards, if you enable the `SecurityManager` at runtime by calling the `System.setSecurityManager()` API, you must set the `-Djava.security.manager=allow` option. To disable the `SecurityManager`, you must specify the `-Djava.security.manager=disallow` option. If an application is designed to run on multiple OpenJDK versions, the same command line might be used across multiple versions. Because of this use of the same command line across multiple versions, in OpenJDK versions before version 18, the runtime attempts to load a `SecurityManager` with the class name `allow` or `disallow` resulted in an error and the application was not starting.

To resolve this issue, OpenJDK version 17 ignores these options and from 0.41.0 release onwards, OpenJDK version 11 also ignores these options. With this release, OpenJDK version 8 too ignores the `allow` and `disallow` keywords, if specified.

### ![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) Display of multiple warnings on loading the same agent restricted on AIX systems

Earlier, on AIX systems, warnings were issued each time the agents were loaded dynamically into a running VM after startup without specifying the `-XX:+EnableDynamicAgentLoading` option, even if the same agents were loaded before.

Now, from 0.44.0 release onwards, AIX systems, like other OpenJ9 supported operating systems, can detect whether an agent was previously loaded or not. Therefore, like other platforms, on AIX systems also, the warnings are issued only once for the same agent when the `-XX:+EnableDynamicAgentLoading` option is not specified.

For more information, see [`-XX:[+|-]EnableDynamicAgentLoading`](xxenabledynamicagentloading.md). ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.43.0 and v0.44.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.44/0.44.md).

<!-- ==== END OF TOPIC ==== version0.44.md ==== -->
