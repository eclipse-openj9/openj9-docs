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

# -Xscdmx 

Use the `-Xscdmx` option to control the size of the class debug area when you create a shared classes cache.

## Syntax

        -Xscdmx<size>

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## Explanation

The `-Xscdmx` option works in a similar way to the [`-Xscmx`](xscmx.md) option, which is used to control the overall size of the shared classes cache. The size of `-Xscdmx` must be smaller than the size of `-Xscmx`. By default, the size of the class debug area is a percentage of the free class data bytes in a newly created or empty cache.

A class debug area is still created if you use the [`-Xnolinenumbers`](xlinenumbers.md) option with the `-Xscdmx` option on the command line.



<!-- ==== END OF TOPIC ==== xscdmx.md ==== -->

