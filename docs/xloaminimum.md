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

# -Xloainitial / -Xloaminimum / -Xloamaximum

Specifies the initial, minimum, and maximum proportion of the current tenure space allocated to the large object area (LOA).

The LOA does not shrink to less than the minimum value.

## Syntax

| Setting               | Effect            | Default  |
|-----------------------|-------------------|----------|
| `-Xloainitial<value>` | Set initial space | `0.05`   |
| `-Xloaminimum<value>` | Set minimum space | `0.01`   |
| `-Xloamaximum<value>` | Set minimum space | `0.5`    |

## See also

- [-Xloa / Xnoloa](xloa.md)

This option is not supported with the balanced GC policy (`-Xgcpolicy:balanced`) or metronome GC policy (`-Xgcpolicy:metronome`), which do not use an LOA.

<!-- ==== END OF TOPIC ==== xloainitial.md ==== -->
<!-- ==== END OF TOPIC ==== xloaminimum.md ==== -->
<!-- ==== END OF TOPIC ==== xloamaximum.md ==== -->
