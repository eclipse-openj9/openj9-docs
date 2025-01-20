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

# What's new in version 0.48.0

The following new features and notable changes since version 0.47.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Loading of the `zlibnx` library on AIX is disabled by default](#loading-of-the-zlibnx-library-on-aix-is-disabled-by-default)
- ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) [JDWP support on Checkpoint/Restore In Userspace (CRIU) restore is enabled](#jdwp-support-on-checkpointrestore-in-userspace-criu-restore-is-enabled) ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.48.0 supports OpenJDK 8, 11, 17, 21, and 23.

macOS 11 is out of support. macOS 11 and earlier versions are removed from the list of supported platforms.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Loading of the `zlibnx` library on AIX is disabled by default

From release 0.25.0 onwards, `zlibNX` hardware-accelerated data compression and decompression was enabled by default on AIX&reg;. From this release onwards, loading of the `zlibnx` library on AIX is disabled by default because using `zlibNX` might cause a `ClassNotFoundException` error. You can enable adding of the `zlibNX` library by using the `-XX:+UseZlibNX` option.

For more information, see [`-XX:[+|-]UseZlibNX`](xxusezlibnx.md).

### ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) JDWP support on Checkpoint/Restore In Userspace (CRIU) restore is enabled

You can use the options that enable the JDWP support both on CRIU pre-checkpoint, and on restore as well.

Also, a new parameter `suspendOnRestore` for the `-Xrunjdwp` option is added to control the suspension of the target VM application on restore. This option is specific to OpenJ9. You can use the `suspendOnRestore=n` setting to prevent the suspension of the target application.

For more information, see [`-Xrunjdwp`](xrunjdwp.md). ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.47.0 and v0.48.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.48/0.48.md).

<!-- ==== END OF TOPIC ==== version0.48.md ==== -->
