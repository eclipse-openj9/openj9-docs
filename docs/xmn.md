<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# -Xmn / -Xmns / -Xmnx


Sets the initial and maximum size of the new area when using `-Xgcpolicy:gencon`.

If the scavenger is disabled, this option is ignored.

You can use the `-verbose:sizes` option to find out the value that is being used by the VM.

## Syntax

| Setting       | Effect                                         | Default                 |
|---------------|------------------------------------------------|-------------------------|
| `-Xmn<size>`  | Equivalent to setting both `-Xmns` and `-Xmnx` | Not set                 |
| `-Xmns<size>` | Set initial size                               | 25% of [`-Xms`](xms.md) |
| `-Xmnx<size>` | Set maximum size                               | 25% of [`-Xmx`](xms.md) |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

:fontawesome-solid-exclamation-triangle:{: .warn aria-hidden="true"} **Restriction:** If you try to set `-Xmn` with either `-Xmns` or `-Xmnx`, the VM does not start, returning an error. 



<!-- ==== END OF TOPIC ==== xmn.md ==== -->
<!-- ==== END OF TOPIC ==== xmns.md ==== -->
<!-- ==== END OF TOPIC ==== xmnx.md ==== -->

