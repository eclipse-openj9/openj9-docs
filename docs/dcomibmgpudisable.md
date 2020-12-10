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

# -Dcom.ibm.gpu.disable

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restriction:** This system property is supported only on Java&trade; 11 and later

If you have enabled GPU processing with `-Dcom.ibm.gpu.enable`, use this system property to turn off processing that can be offloaded to a graphics processing unit (GPU).

## Syntax

        -Dcom.ibm.gpu.disable


## Explanation

Because establishing and completing communication with a GPU incurs an additional overhead, not all processing requirements benefit from being offloaded to the GPU. GPU processing is therefore disabled by default. However, if you have enabled GPU processing with `-Dcom.ibm.gpu.enable`, this property turns GPU processing off.

## See also

- [Exploiting GPUs](introduction.md#exploiting-gpus)
- [-Dcom.ibm.gpu.enable](dcomibmgpuenable.md)
- [-Dcom.ibm.gpu.verbose](dcomibmgpuverbose.md)

<!-- ==== END OF TOPIC ==== dcomibmgpudisable.md ==== -->
