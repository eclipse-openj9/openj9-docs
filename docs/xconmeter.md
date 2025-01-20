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

# -Xconmeter

This option determines the usage of which area, LOA (large object area) or SOA (small object area), is metered and therefore which allocations are taxed during concurrent mark operations.

## Syntax

        -Xconmeter:<parameter>

## Parameters

### `soa`

        -Xconmeter:soa

: (Default) Applies the allocation tax to allocations from the small object area (SOA).

### `loa`

        -Xconmeter:loa

: Applies the allocation tax to allocations from the large object area (LOA).

### `dynamic`

        -Xconmeter:dynamic

: The collector dynamically determines which area to meter based on which area is exhausted first, whether it is the SOA or the LOA.

## Default behavior

By default, allocation tax is applied to the SOA.

This option is not supported with the balanced GC policy (`-Xgcpolicy:balanced`) or metronome GC policy (`-Xgcpolicy:metronome`).


<!-- ==== END OF TOPIC ==== xconmeter.md ==== -->
