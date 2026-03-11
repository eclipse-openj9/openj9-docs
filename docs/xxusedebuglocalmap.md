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

# -XX:[+|-]UseDebugLocalMap

This option is used to enable or disable the use of the debug version of the stack local mapper.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This option is intended for debugging and diagnostic purposes only and not for usage in production environments.

## Syntax

        -XX:[+|-]UseDebugLocalMap

| Setting                 | Effect  | Default                                                                            |
|-------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+UseDebugLocalMap` | Enable  |                   |
| `-XX:-UseDebugLocalMap` | Disable |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>                                                   |

## Explanation

The Eclipse OpenJ9&trade; virtual machine (VM) has two versions of the stack local mapper, the default and the debug version. These mappers track local variables on the Java stack during the program execution and help the garbage collector (GC) to identify the objects that are no longer needed and hence can be reclaimed to improve performance.

With the default mapper, the objects might be collected too early from a debugging perspective. In certain scenarios, such as when investigating a memory leak or finalization issues, the objects must be kept reachable even if they might not be used again. The debug local mapper helps to keep the objects reachable longer.

Earlier the debug local mapper was enabled only by running the entire VM in debug mode which significantly impacted performance. With the `-XX:+UseDebugLocalMap` option, you can enable the debug local mapper without running the entire VM in debug mode to investigate memory leak or finalization issues.

To disable the debug local mapper, set the `-XX:-UseDebugLocalMap` option on the command line.

## See also

- [What's new in version 0.58.0](version0.58.md#new-xx-usedebuglocalmap-option-is-added)

<!-- ==== END OF TOPIC ==== xxusedebuglocalmap.md ==== -->
