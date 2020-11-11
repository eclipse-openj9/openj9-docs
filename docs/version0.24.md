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

# What's new in version 0.24.0

The following new features and notable changes since v 0.23.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Support the `JAVA_OPTIONS` environment variable](#support-the-java_options-environment-variable)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.24.0 supports OpenJDK 8, 11, and 15. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 15](https://adoptopenjdk.net/archive.html?variant=openjdk15&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).


### Support the JAVA_OPTIONS environment variable

Support the `JAVA_OPTIONS` environment variable for compatibility with Hotspot. `JAVA_OPTIONS` can be used to set command line options as described in [OpenJ9 command-line options](cmdline_specifying.md) and [Environment variables](env_var.md). Options specified in `JAVA_OPTIONS` can be overridden by `OPENJ9_JAVA_OPTIONS`.


### `-XX:[+|-]PortableSharedCache` option behavior update

The `-XX:[+|-]PortableSharedCache` option is now supported on s390 and POWER platforms. AOT-compiled code generated with this option is guaranteed to be portable across IBM® z10 or newer microarchitectures on s390 platforms and IBM® POWER8 or newer microarchitectures on POWER platforms. See [`-XX:[+|-]PortableSharedCache`](xxportablesharedcache.md) for more details about this option.


## Full release information

To see a complete list of changes between Eclipse OpenJ9 v 0.23.0 and v 0.24.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.24/0.24.md).

<!-- ==== END OF TOPIC ==== version0.24.md ==== -->

