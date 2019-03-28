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


# What's new in version 0.14.0

The following new features and notable changes since v.0.13.0 are included in this release:

- [New option for ignoring or reporting unrecognized -XX: options](#new-option-for-ignoring-or-reporting-unrecognized-xx-options)

## Features and changes

### New option for ignoring or reporting unrecognized -XX: options

By default, unrecognized `-XX:` command-line options are ignored, which prevents an application failing to start. You can now use  `-XX:-IgnoreUnrecognizedXXColonOptions` to turn off this behavior, so that unrecognized `-XX:` options are reported instead. For more information, see [`-XX:[+|-]IgnoreUnrecognizedXXColonOptions`](xxignoreunrecognizedxxcolonoptions.md).

## Full release information

To see a complete list of changes between Eclipse OpenJ9 V0.13.0 and V0.14.0 releases, see the [Release notes](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.14/0.14.md).

<!-- ==== END OF TOPIC ==== version0.14.md ==== -->
