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


# Migrating from Java 11 to Java 17

Support for OpenJDK 17 was added in Eclipse OpenJ9&trade; version 0.29.1.

The following new OpenJ9 changes apply when OpenJ9 is built with Java SE 17 class libraries. This information exists elsewhere in the documentation but is summarized here for convenience.

## Support for JDK enhancement proposals (JEP)

The new JEPs that are supported are listed in the following topics:

- JDK 12: [What's new in version 0.13.0](version0.13.md)
- JDK 13: [What's new in version 0.16.0](version0.16.md)
- JDK 14: [What's new in version 0.19.0](version0.19.md)
- JDK 15: [What's new in version 0.22.0](version0.22.md)
- JDK 16: [What's new in version 0.25.0](version0.25.md)
- JDK 17: [What's new in version 0.29.1](version0.29.1.md)

## New OpenJ9 features and changes

The following table lists the new OpenJ9 features and notable changes with the OpenJ9 release in which they were added:

| Features and changes  | OpenJ9 release|
|-----------------------|---------------|
|The default operating system stack size on x64 platforms is increased from 256 KB to 512 KB to accommodate vector support. You can change the operating system stack size by using the [-Xmso](xmso.md) option.|  0.33.0   |
|When the VM loads an application native library (DLL), it searches for the library in the path that is specified in the `java.library.path` system property or in the following environment variables:<ul><li>`LIBPATH` - z/OS&reg; and AIX&reg;</li><li>`LD_LIBRARY_PATH` - Linux&reg;<li>`PATH` - Windows&trade;</li></ul><br>In Java 11 and earlier versions, the VM searched for the application native libraries by using both the `java.library.path` system property and the environment variable.<br>From Java 17 onwards, the search is based on only the `java.library.path` system property value that is obtained when the VM starts. Changing the `java.library.path` system property value after VM startup doesn't change the search. If the path is set on the command line by using the `-Djava.library.path` option, only this path is used for the search. The path that is specified by the environment variable is not used to search for the application native libraries.<br>The default value of the `java.library.path` system property is still derived from the environment variable.  |  0.29.1   |

<!--|Linux builds for all platforms use gcc 10.3 instead of gcc 7.5. See the list of [build environments](openj9_support.md#build-environments).| 0.33.0  | (SG - Commented this in 0.47.0 release It is an out-of-date statement because both 11 and 17 use gcc 11.2 now.)-->
