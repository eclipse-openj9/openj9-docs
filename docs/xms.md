<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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

# -Xms / -Xmx


These Oracle&reg; Hotspot&trade; options set the initial/minimum Java&trade; heap size, and the maximum heap size respectively. These options are recognized by the OpenJ9 VM.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes: **

- If you set `-Xms` &gt; `-Xmx`, the OpenJ9 VM fails with the message `-Xms too large for -Xmx`.

- If you exceed the limit set by the `-Xmx` option, the OpenJ9 VM generates an `OutofMemoryError`.

If you are allocating the Java heap with large pages, see also [-Xlp](xlp.md) and
[More effective heap usage using compressed references](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/mm_gc_compressed_refs.html).

You can also use the [`-Xmo`](xmo.md) option (not supported by the balanced garbage collection policy):  
If the scavenger is enabled, `-Xms` &ge; `-Xmn` + `-Xmo`  
If the scavenger is disabled, `-Xms` &ge; `-Xmo`  

## Syntax

| Setting       | Effect                  | Default                                 |
|---------------|-------------------------|-----------------------------------------|
| `-Xms<size>`  | Set initial heap size   | 8 MB                                    |
| `-Xmx<size>`  | Set maximum heap size   | 25% of available memory (25 GB maximum) |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.  
See [Default settings for the OpenJ9 VM](openj9_defaults.md) for more about default values.

## Examples

`-Xms2m -Xmx64m`
:   Heap starts at 2 MB and grows to a maximum of 64 MB.

`-Xms100m -Xmx100m`
:   Heap starts at 100 MB and never grows.

`-Xms50m`
:   Heap starts at 50 MB and grows to the default maximum.

`-Xmx256m`
:   Heap starts at default initial value and grows to a maximum of 256 MB.

## See also

- [-Xsoftmx (Set "soft" maximum Java heap size)](xsoftmx.md)



<!-- ==== END OF TOPIC ==== xms.md ==== -->
<!-- ==== END OF TOPIC ==== xmx.md ==== -->
