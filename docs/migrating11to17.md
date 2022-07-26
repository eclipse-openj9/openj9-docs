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


# Migrating from Java 11 to Java 17

Support for OpenJDK 17 was added in OpenJ9 version 0.29.1.

The following new OpenJ9 changes apply when OpenJ9 is built with Java SE 17 class libraries. This information exists elsewhere in the documentation but is summarized here for convenience.

### Support for JDK enhancement proposals (JEP)

The new JEPs that are supported are listed in the following topics:

- JDK 12: [What's new in version 0.13.0](version0.13.md)
- JDK 13: [What's new in version 0.16.0](version0.16.md)
- JDK 14: [What's new in version 0.19.0](version0.19.md)
- JDK 15: [What's new in version 0.22.0](version0.22.md)
- JDK 16: [What's new in version 0.25.0](version0.25.md)
- JDK 17: [What's new in version 0.29.1](version0.29.1.md)

### New OpenJ9 features and changes

The following table lists the new OpenJ9 features and notable changes with the OpenJ9 release in which they were added:

| Features and changes  | OpenJ9 release &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;|
|-----------------------|-------------------------------------|
|Linux builds for all platforms use gcc 10.3 instead of gcc 7.5. See the list of [build environments](openj9_support.md#build-environments).               | 0.33.0  |
