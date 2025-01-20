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

# What's new in version 0.38.0

The following new features and notable changes since version 0.37.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [New `-XX:[+|-]HandleSIGUSR2` option added](#new-xx-handlesigusr2-option-added)
- [Technical preview of CRIU support](#technical-preview-of-criu-support)


## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.38.0 supports OpenJDK 8, 11 and 17.

Support for running OpenJ9 with OpenJDK 8 or OpenJDK 11 on CentOS 6.10 is deprecated and might be removed in a future release. Since release 0.37, OpenJ9 is no longer tested with OpenJDK 11 on CentOS 6.10. Testing of OpenJ9 with OpenJDK 8 on CentOS 6.10 is likely to end in a future release.

OpenJ9 Windows&reg; builds for OpenJDK 8 are now compiled with Microsoft&reg; Visual Studio 2019. The Visual Studio redistributable files included with the build are updated to match.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### New `-XX:[+|-]HandleSIGUSR2` option added

This option controls the handling of the `SIGUSR2` signal by the VM signal handler. The VM signal handler is installed only if this option is enabled.

For more information, see [`-XX:[+|-]HandleSIGUSR2`](xxhandlesigusr2.md).

### Technical preview of CRIU support

This release supports, as a technical preview, the Checkpoint/Restore In Userspace (CRIU) tool that is included with many Linux distributions. This tool is available for OpenJDK 11 and later running on Linux&reg; x86, Linux on POWER&reg; (Little Endian), and Linux on IBM Z&reg; only.

You can use the CRIU feature to stop the VM at a checkpoint, save its state, then run the VM from the point where it was stopped. You can restore the checkpoint image in multiple other environments with improved start-up time. This capability means that the CRIU support is particularly useful in container environments. For more information, see [CRIU support](criusupport.md).


## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.37.0 and v0.38.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.38/0.38.md).

<!-- ==== END OF TOPIC ==== version0.38.md ==== -->
