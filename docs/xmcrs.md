<!--
* Copyright (c) 2017, 2023 IBM Corp. and others
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
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->

# -Xmcrs 


Sets an initial size for an area in memory that is reserved for any native classes, monitors, and threads that are used by compressed references within the lowest 4 GB memory area.

You can use the `-verbose:sizes` option to find out the value that is being used by the VM.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- Native memory `OutOfMemoryError` exceptions might occur when using compressed references if the lowest 4 GB of address space becomes full, particularly when loading classes, starting threads, or using monitors. 
- If you are not using compressed references and this option is set, the option is ignored and the output of `-verbose:sizes` shows `-Xmcrs0`.



## Syntax

        -Xmcrs<size>

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.


<!-- ==== END OF TOPIC ==== xmcrs.md ==== -->

