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

# -XX:\[+|-\]UseCompressedOops

**(64-bit only)**

This Oracle HotSpot option enables or disables compressed references in 64-bit JVMs. The option is recognized by the Eclipse OpenJ9&trade; VM and is provided to help when porting applications from the HotSpot JVM to the OpenJ9 VM. This option might not be supported in subsequent releases.

## Syntax

        -XX:[+|-]UseCompressedOops

| Setting                  | Effect | Default                                                          |
|--------------------------|--------|------------------------------------------------------------------|
|`-XX:+UseCompressedOops`  | Enable |                                                                  |
|`-XX:-UseCompressedOops`  | Disable|                                                                  |

The `-XX:+UseCompressedOops` option is similar to specifying `-Xcompressedrefs`. Compressed references are used by default when the maximum memory size for an application is set above a platform-specific value. For more information, see [`-Xcompressedrefs`](xcompressedrefs.md).  



<!-- ==== END OF TOPIC ==== xxusecompressedoops.md ==== -->
