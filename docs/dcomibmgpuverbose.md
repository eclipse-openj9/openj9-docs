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

# -Dcom.ibm.gpu.verbose

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This system property is supported only on Java&trade; 11 and later.

This system property can be used to help identify problems with graphics processing unit (GPU) processing.

## Syntax

        -Dcom.ibm.gpu.verbose

This property is not set by default.

## Explanation

When specified, this option generates verbose output to STDOUT, which can be piped to a file.

## See also

- [Exploiting GPUs](introduction.md#exploiting-gpus)
- [-Dcom.ibm.gpu.disable](dcomibmgpudisable.md)
- [-Dcom.ibm.gpu.enable](dcomibmgpuenable.md)


<!-- ==== END OF TOPIC ==== dcomibmgpuverbose.md ==== -->
