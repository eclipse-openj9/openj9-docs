<!--
* Copyright (c) 2017, 2026 IBM Corp. and others
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

# -Xmr / &nbsp; -Xmrx


Sets the initial and maximum size of the the garbage collection (GC) *remembered set* in the `gencon` GC policy. The remembered set is a list of objects in the tenure area of the heap that have references to objects in the new area.

## Syntax

| Setting       | Effect            | Default                   |
|---------------|-------------------|---------------------------|
| `-Xmr<size>`  | Set initial size  | 16 K                      |
| `-Xmrx<size>` | Set maximium size |                           |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

This option applies only to the `gencon` GC policy.

<!-- ==== END OF TOPIC ==== xmr.md ==== -->
<!-- ==== END OF TOPIC ==== xmrx.md ==== -->
