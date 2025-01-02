<!--
* Copyright (c) 2017, 2024 IBM Corp. and others
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

# -Xmcrs 


Sets an initial size for an area in memory that is reserved within the lowest 4 GB memory area for any native classes, monitors, and threads that are used by compressed references.

You can use the `-verbose:sizes` option to find out the value that is being used by the VM.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- Native memory `OutOfMemoryError` exceptions might occur when using compressed references if the lowest 4 GB of address space becomes full, particularly when loading classes, starting threads, or using monitors. 
- If you are not using compressed references and this option is set, the option is ignored and the output of `-verbose:sizes` shows `-Xmcrs0`.
- This option is overridden by the [`-Xgc:suballocatorInitialSize`](xgc.md#suballocatorinitialsize) option, irrespective of the order of the options on the command line. If the `-Xmcrs` option is thus overridden, the `-Xmcrs` output of `-verbose:sizes` shows the `suballocatorInitialSize` value.

## Syntax

        -Xmcrs<size>

For more information about the `<size>` parameter, see [Using -X command-line options](x_jvm_commands.md).


<!-- ==== END OF TOPIC ==== xmcrs.md ==== -->

