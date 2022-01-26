<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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
* [2] http://openjdk.java.net/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->

# -Xmo / -Xmos / -Xmox

Sets the size of the tenure area of the heap for the `gencon` garbage collection (GC) policy.

You can use the `-verbose:sizes` option to find out the values that the VM is currently using.

## Syntax

| Setting       | Effect                                            | Default                   |
|---------------|---------------------------------------------------|---------------------------|
| `-Xmo<size>`  | Equivalent to setting both `-Xmos` and `-Xmox`    | not set                   |            |
| `-Xmos<size>` | Set initial size of the tenure area of the heap   | 75% of [`-Xms`](xms.md)   |
| `-Xmox<size>` | Set maximum size of the tenure area of the heap   | Same as [`-Xmx`](xms.md)  |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

:fontawesome-solid-exclamation-triangle:{: .warn aria-hidden="true"} **Restriction:** If you try to set `-Xmo` with either `-Xmos` or `-Xmox`, the VM does not start, returning an error.

To set the size of the nursery area of the heap, see [`-Xmn/-Xmns/-Xmnx`](xmn.md).

## See also

- [`gencon` policy (default)](gc.md#gencon-policy-default)
- [`-Xmn/-Xmns/-Xmnx`](xmn.md)
- [`-Xms`/`-Xmx`](xms.md)

<!-- ==== END OF TOPIC ==== xmo.md ==== -->
<!-- ==== END OF TOPIC ==== xmos.md ==== -->
<!-- ==== END OF TOPIC ==== xmox.md ==== -->
