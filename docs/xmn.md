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

# -Xmn / -Xmns / -Xmnx


Sets the initial and maximum size of the nursery area when using the [`gencon` garbage collection (GC) policy](gc.md#gencon-policy-default) (`-Xgcpolicy:gencon`), and sets the size of eden when using the [`balanced` garbage collection (GC) policy](gc.md#balanced-policy) (`-Xgcpolicy:balanced`). These options are ignored if they are used with any other GC policy.

You can use the `-verbose:sizes` option to find out the value that is being used by the VM.

## Syntax

| Setting       | Effect                                         | Default                                                                                       |
|---------------|------------------------------------------------|-----------------------------------------------------------------------------------------------|
| `-Xmn<size>`  | Equivalent to setting both `-Xmns` and `-Xmnx` | Not set                                                                                       |
| `-Xmns<size>` | Set initial size                               | 25% of [`-Xms`](xms.md) when using `gencon` and `balanced` policies                           |
| `-Xmnx<size>` | Set maximum size                               | 25% of [`-Xmx`](xms.md) when using `gencon`, and 75% of [`-Xmx`](xms.md) when using `balanced`|

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** If you try to set `-Xmn` with either `-Xmns` or `-Xmnx`, the VM does not start, returning an error.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- When using the `balanced` GC policy without specifying `-Xmn` or `-Xmns`, the GC may decide to shrink eden size below its initial size (25% of `-Xms`) if it determines that doing so will improve GC performance.
- When using the `balanced` GC policy, specifying `-Xmn`/`-Xmns`/`-Xmnx` may affect `balanced` GC's ability to satisfy [`-Xgc:targetPausetime`](xgc.md#targetpausetime)
- Specifying `-Xmn`/`-Xmns`/`-Xmnx` may affect both `gencon` and `balanced` GC's ability to satisfy [`-Xgc:dnssexpectedtimeratiomaximum`](xgc.md#dnssexpectedtimeratiomaximum) and [`-Xgc:dnssexpectedtimeratiominimum`](xgc.md#dnssexpectedtimeratiominimum)

To set the size of the tenure area of the heap when using `gencon` GC policy, see [`-Xmo/-Xmos/-Xmox`](xmo.md).

## See also

- [`gencon` GC policy (default)](gc.md#gencon-policy-default)
- [`balanced` GC policy](gc.md#balanced-policy)
- [`-Xmo/-Xmos/-Xmox`](xmo.md)
- [`-Xms`/`-Xmx`](xms.md)

<!-- ==== END OF TOPIC ==== xmn.md ==== -->
<!-- ==== END OF TOPIC ==== xmns.md ==== -->
<!-- ==== END OF TOPIC ==== xmnx.md ==== -->
