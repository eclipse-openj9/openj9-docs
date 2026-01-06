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

# -XX:[+|-]ShowNativeStackSymbols

This option controls whether Java&reg; dumps show the names of functions in native call stacks.

## Syntax

        -XX:-ShowNativeStackSymbols
        -XX:+ShowNativeStackSymbols=<value>

| Setting                    | Value  | Effect | Default |
|----------------------------|--------|------|:-------:|
| `-XX:-ShowNativeStackSymbols` |       | Don't show native stack symbols, not even in response to a signal, such as a fault signal      |   |
| `-XX:+ShowNativeStackSymbols` | basic | Show all the available native stack symbols for a signaled or faulting thread, but only easily acquired native stack symbols for other threads     | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:+ShowNativeStackSymbols` | all | Show all the available native stack symbols for all threads     |   |

## Explanation

Java dumps take longer to produce when the native stack symbols are included. If your Java application has a lot of threads, you might get an incomplete Java dump. You can avoid this situation by reducing the number of native stack symbols in the Java dump or by omitting them altogether.

<!-- ==== END OF TOPIC ==== xxshownativestacksymbols.md ==== -->
