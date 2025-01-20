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

# -Xmoi

Sets the heap expansion allocation increment.

You can use the `-verbose:sizes` option to find out the values that the VM is currently using.

## Syntax

| Setting       | Effect                                            | Default                   |
|---------------|---------------------------------------------------|---------------------------|
| `-Xmoi<size>` | Sets the heap expansion allocation increment      | See **Notes**              |


:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- By default, the increment size (`-Xmoi`) is calculated on the expansion size, set by [`-Xmine`](xmine.md) and [`-Xminf`](xminf.md).  If you set `-Xmoi` to zero, no expansion is allowed.
- For the `gencon` GC policy, the expansion increment applies to the tenure area of the heap.  

This option is not supported for the `metronome` GC policy, because the heap is always fully expanded.

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## See also

- [Heap expansion and contraction](allocation.md#expansion-and-contraction)


<!-- ==== END OF TOPIC ==== xmoi.md ==== -->
