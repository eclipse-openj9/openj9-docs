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

# -Xverbosegclog

Causes garbage collection (GC) output from the `-verbose:gc` option to be written to a specified file.

## Syntax

        -Xverbosegclog[:<filename>[,<x>,<y>]]

: where `<filename>` is the name of the file to which output is written. Dump agent tokens can be used in the filename.

    If the file cannot be found, the file is created, and output is written to the new file.

    If the file cannot be created (for example, if an invalid filename is specified), output is redirected to `stderr`.

    If you do not specify a file name, `verbosegc.%Y%m%d.%H%M%S.%pid.txt` is used (for example, `verbosegc.20180124.093210.1234.txt`).

    If you specify `<x>` and `<y>`, output is redirected to `x` files, each containing `y` GC cycles.

## Default behavior

By default, no verbose GC logging occurs.

## See also

- [Dump agent tokens](xdump.md#dump-agent-tokens) for more information.
- [Verbose GC logs](vgclog.md) and [Log examples](vgclog_examples.md) for more information about verbose GC logs.


<!-- ==== END OF TOPIC ==== xverbosegclog.md ==== -->
