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

# -XX:[+|-]TransparentHugePage

**(Linux&reg; systems only: x86, POWER&reg;, and IBM Z&reg;)**

If Transparent Huge Pages (THP) is set to `madvise` on your system, this option, when enabled, promotes all memory allocated to huge pages. On systems without THP, or if THP is set to `always` or `never` on your system, this option is ignored. When transparent huge pages are used, your application footprint might increase.

## Syntax

| Setting                    | Effect  | Default                                                                                                  |
|----------------------------|---------|--------------------------------------------------------------------------------------------------------|
| `-XX:+TransparentHugePage` | Enable  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span>            |
| `-XX:-TransparentHugePage` | Disable |  |                                                                                    

<!-- ==== END OF TOPIC ==== xxtransparenthugepage.md ==== -->
