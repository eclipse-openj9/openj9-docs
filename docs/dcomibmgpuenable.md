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

# -Dcom.ibm.gpu.enable

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This system property is supported only on Java&trade; 11 and later.

Use this system property to control the type of processing that can be offloaded to a graphics processing unit (GPU) when processing requirements meet a specific threshold. This feature can improve the performance of certain Java functions.

## Syntax

        -Dcom.ibm.gpu.enable=[all|sort]

| Setting      | Effect                                                      |
|--------------|-------------------------------------------------------------|
| all          | Turns on GPU processing for all possible Java functions.    |
| sort         | Turns on GPU processing only for the Java `sort()` function.|

By default, this property is not set.

## Explanation

Because establishing and completing communication with a GPU incurs an additional overhead, not all processing requirements benefit from being offloaded to the GPU. When set, this property enables GPU processing for any array that meets a minimum size.

## See also

- [Exploiting GPUs](introduction.md#exploiting-gpus)
- [-Dcom.ibm.gpu.disable](dcomibmgpudisable.md)
- [-Dcom.ibm.gpu.verbose](dcomibmgpuverbose.md)


<!-- ==== END OF TOPIC ==== dcomibmgpuenable.md ==== -->
