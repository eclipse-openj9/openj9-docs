<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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


# What's new in version 0.21.0

The following new features and notable changes since v 0.20.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [New `-XX:[+|-]HandleSIGABRT` option added](#new--xx-handlesigabrt-option-added)
- [Update to `NoClassDefFoundError` exception message](#update-to-noclassdeffounderror-exception-message)
- [OSX shared libraries version updated](#osx-shared-libraries-version-updated)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.21.0 supports OpenJDK 8, 11, and 14. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK version 14](https://adoptopenjdk.net/archive.html?variant=openjdk14&jvmVariant=openj9)

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### New `-XX:[+|-]HandleSIGABRT` option added

Option `-XX:[+|-]HandleSIGABRT` affects the handling of the operating system signal `SIGABRT`. See [`-XX:[+|-]HandleSIGABRT`](xxhandlesigabrt.md) for more details about this option.

### Update to `NoClassDefFoundError` exception message

The class names printed in a `NoClassDefFoundError` exception message have been updated to match the same order as reported by Hotspot.

For example, in the following exception message:
```
java.lang.NoClassDefFoundError: mypackage/Main (wrong name: Main)
```
`mypackage/Main` was the class name encountered by the VM in the `.class` file, but "wrong name" `Main` was the provided class name. Prior to this update to the exception message, the encountered class name and the provided class name were swapped in the `NoClassDefFoundError` exception message.

### OSX shared libraries version updated

The version information for shared libraries on OSX has been updated from 0.0.0 to 1.0.0. If an application has linked against a shared library from a previous OpenJ9 release, it needs to be re-linked against the new release. Failure to re-link causes an error `Incompatible library version`, `requires version 0.0.0`.

## Full release information

To see a complete list of changes between Eclipse OpenJ9 v 0.20.0 and v 0.21.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.21/0.21.md).

<!-- ==== END OF TOPIC ==== version0.21.md ==== -->
