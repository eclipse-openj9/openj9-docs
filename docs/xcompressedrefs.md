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

# -Xcompressedrefs / -Xnocompressedrefs


**(64-bit only)**

These options enable or disable the use of compressed references.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** You cannot include `-Xcompressedrefs` in the options file (see [`-Xoptionsfile`](xoptionsfile.md)).

## Syntax

| Setting             | Action              | Default                                                                                                                        |
|---------------------|---------------------|:------------------------------------------------------------------------------------------------------------------------------:|
|`-Xcompressedrefs`   | Enable compression  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> (see [Default behavior](#default-behavior)) |
|`-Xnocompressedrefs` | Disable compression |                                                                                                                                |

## Default behavior

Compressed references are enabled by default when [`-Xmx`](xms.md) &le; 57 GB.

**z/OS&reg;:** This threshold value assumes that you have [APAR OA49416](https://www.ibm.com/support/docview.wss?uid=isg1OA49416) installed. If you do not have the APAR installed, the threshold value is 25 GB.

**AIX&reg; and Linux&reg;:** For the metronome garbage collection policy, the threshold is 25 GB.

## See also

- [Compressed references](allocation.md#compressed-references)



<!-- ==== END OF TOPIC ==== xcompressedrefs.md ==== -->
<!-- ==== END OF TOPIC ==== xnocompressedrefs.md ==== -->
