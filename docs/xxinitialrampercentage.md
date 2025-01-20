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

# -XX:InitialRAMPercentage / -XX:MaxRAMPercentage

These Oracle HotSpot options can be used to specify the initial and maximum size of the Java heap as a percentage of the total memory available to the VM. The options are recognized by Eclipse OpenJ9&trade; and provided for compatibility.

## Syntax

| Setting                      | Effect                                                 |
|------------------------------|--------------------------------------------------------|
| `-XX:InitialRAMPercentage=N` | Set initial heap size as a percentage of total memory  |
| `-XX:MaxRAMPercentage=N`     | Set maximum heap size as a percentage of total memory  |

: Where N is a value between 0 and 100, which can be of type "double". For example, 12.3456.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If you set a value for [`-Xms`](xms.md), the `-XX:InitialRAMPercentage` option is ignored.
If you set a value for [`-Xmx`](xms.md), the `-XX:MaxRAMPercentage` option is ignored.

If your application is running in a container and you have specified [`-XX:+UseContainerSupport`](xxusecontainersupport.md), both the default heap size for containers, the `-XX:InitialRAMPercentage` option, and the `-XX:MaxRAMPercentage` option are based on the available container memory.


<!-- ==== END OF TOPIC ==== xxinitialrampercentage.md ==== -->
