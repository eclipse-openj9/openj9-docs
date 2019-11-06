<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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

# -Xcompressedrefs / -Xnocompressedrefs


**(64-bit only)**

Enables or disables the use of compressed references.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restriction:** You cannot include `-Xcompressedrefs` in the options file (see [`-Xoptionsfile`](xoptionsfile.md)).

## Syntax

| Setting             | Action              | Default                                                                                                                        |
|---------------------|---------------------|:------------------------------------------------------------------------------------------------------------------------------:|
|`-Xcompressedrefs`   | Enable compression  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> (see [Default behavior](#default-behavior)) |
|`-Xnocompressedrefs` | Disable compression |                                                                                                                                |

## Default behavior

Compressed references are enabled by default when [`-Xmx`](xms.md) &le; 57 GB.

**z/OS&reg;:** This threshold value assumes that you have [APAR OA49416](http://www.ibm.com/support/docview.wss?uid=isg1OA49416) installed. If you do not have the APAR installed, the threshold value is 25 GB.

**AIX&reg; and Linux&reg;:** For the metronome garbage collection policy, the threshold is 25 GB.

## See also

- [Compressed references](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_compressed_references.html)


<!-- ==== END OF TOPIC ==== xcompressedrefs.md ==== -->
<!-- ==== END OF TOPIC ==== xnocompressedrefs.md ==== -->
