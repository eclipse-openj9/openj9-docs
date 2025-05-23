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

# -XX:ConcGCThreads

This Oracle HotSpot option affects the number of threads used by the concurrent garbage collector. This option is recognized by Eclipse OpenJ9&trade; and provided for compatibility.

## Syntax

        -XX:ConcGCThreads=<number>

Where `<number>` is the number of low-priority background threads that are attached to assist the mutator threads in concurrent mark.

Within OpenJ9 this option is directly mapped to [`-Xconcurrentbackground`](xconcurrentbackground.md).

## See also

- [What's new in version 0.18.0](version0.18.md#support-for-openjdk-hotspot-options)

<!-- ==== END OF TOPIC ==== xxconcgcthreads.md ==== -->
