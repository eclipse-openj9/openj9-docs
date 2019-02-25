<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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


# What's new in version 0.13.0

The following new features and notable changes since v.0.12.1 are included in this release:

- [New Java&trade; process status tool](#new-java-process-status-tool)
- [Writing a Java dump to STDOUT or STDERR](#writing-a-java-dump-to-stdout-or-stderr)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.13.0 supports OpenJDK 12, which is available from the AdoptOpenJDK community at the following link:

- [OpenJDK version 12](https://adoptopenjdk.net/archive.html?variant=openjdk12&jvmVariant=openj9)

OpenJDK 12 with Eclipse OpenJ9 is not a long term support (LTS) release.

Although it is possible to build an OpenJDK 8, or other versions, with OpenJ9 0.13.0, testing at the project is not complete and therefore support is not available.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md)

### New Java process status tool

A Java process status tool (`jps`) is available for querying running Java processes. This tool is added for compliance with the Java reference implementation; its behaviour differs from the equivalent HotSpot tool. For more information, see [Java process status](tool_jps.md)

### Writing a Java dump to STDOUT or STDERR

You can now write a Java dump file to STDOUT or STDERR by using the [`-Xdump`](xdump.md) command-line option. See [Writing to `STDOUT`/`STDERR`](xdump.md#writing-to-stdoutstderr) for details.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 V0.12.1 and V0.13.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.13/0.13.md).
<!-- ==== END OF TOPIC ==== cmdline_general.md ==== -->
