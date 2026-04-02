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

# -XX:[+|-]UseMediumPageSize

**AIX only**

This option is used to enable or disable the default definition setting of the `LDR_CNTRL` environment variable.

## Syntax

        -XX:[+|-]UseMediumPageSize

| Setting                      | Effect  | Default                                                                        |
|------------------------------|---------|:------------------------------------------------------------------------------:|
| `-XX:+UseMediumPageSize` | Enable  |      :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>    |
| `-XX:-UseMediumPageSize` | Disable |                                                                           |

## Explanation

On AIX systems, by default the Java launcher sets the `LDR_CNTRL` environment variable as `TEXTPSIZE=64K@DATAPSIZE=64K@STACKPSIZE=64K@SHMPSIZE=64K` to specify medium page sizes (64 KB) for the text, data, stack, and shared memory segments. This default setting is used to improve performance.

You can use `-XX:-UseMediumPageSize` to disable the default setting of the `LDR_CNTRL` environment variable. When this option is used, the Java launcher uses the default AIX page sizes (4 KB for each segment).

You can re-enable the default setting with the `-XX:+UseMediumPageSize` option.

## See also

- [What's new in version 0.59.0](version0.59.md#new-xx-usemediumpagesize-option-is-added)
- [Configuring your systems](configuring.md#aix-systems)
- [-Xlp:codecache](xlpcodecache.md#aix)

<!-- ==== END OF TOPIC ==== xxusemediumpagesize.md ==== -->
