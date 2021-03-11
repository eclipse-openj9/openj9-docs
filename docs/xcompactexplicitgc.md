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

# ‑Xcompactexplicitgc / ‑Xnocompactexplicitgc


Enables or disables full compaction each time `System.gc()` is called. 

Compaction takes place on global garbage collections if you specify `-Xcompactgc` or if compaction triggers are met. 

## Syntax

| Setting                | Action             | Default                                                                            |
|------------------------|--------------------|:----------------------------------------------------------------------------------:|
|`-Xcompactexplicitgc`   | Enable compaction  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
|`-Xnocompactexplicitgc` | Disable compaction |                                                                                    |

## See also

- [Global garbage collection: Compaction phase](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_compact.html)


<!-- ==== END OF TOPIC ==== xcompactexplicitgc.md ==== -->
<!-- ==== END OF TOPIC ==== xnocompactexplicitgc.md ==== -->

