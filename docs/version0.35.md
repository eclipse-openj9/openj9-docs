<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# What's new in version 0.35.0

The following new features and notable changes since version 0.33.1 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Java&reg; dump files contain more information about waiting threads](#java-dump-files-contain-more-information-about-waiting-threads)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.35.0 supports OpenJDK 8, 11, and 17.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Java dump files contain more information about waiting threads

For threads that are waiting for a class initialization lock, the Java dump output now shows the thread that is currently working to progress the initialization of the class. This thread is indicated by the new `Initializing thread: <thread_name>` string in the existing `3XMTHREADBLOCK` line in the Java dump output. For example:

`3XMTHREADBLOCK Waiting on: java/lang/J9VMInternals$ClassInitializationLock@0x00000000FFF5DC90 Owned by: <unowned> Initializing thread: "Class Initialization Thread 1"`

For more information, see [Threads](dump_javadump.md#threads).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.33.1 and v0.35.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.35/0.35.md).

<!-- ==== END OF TOPIC ==== version0.35.md ==== -->
