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

# -XX:\[+|-\]UseZlibNX

**AIX&reg; with IBM POWER9&reg; and later only**

This option enables or disables the adding of the `zlibNX` library directory location to the `LIBPATH` environment variable.


## Syntax

        -XX:[+|-]UseZlibNX

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+UseZlibNX` |  Enable  |           |
| `-XX:-UseZlibNX` | Disable |   :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>   |


## Explanation

AIX system adds the `zlibNX` library location, if available, to the `LIBPATH` variable by default. But having the `zlibNX` library directory location in the `LIBPATH` variable might cause some issues. For example, Git clone fails when executed from Java&reg; when `zlibNX` is on the `LIBPATH` in the environment. Therefore, `-XX:-UseZlibNX` was made the default setting from release 0.48.0 onwards to disable the adding of the `zlibNX` library location.

You can enable adding of the `zlibNX` library location to the `LIBPATH` variable with the `-XX:+UseZlibNX` option.

## See also

- [Hardware acceleration](introduction.md#hardware-acceleration)
- [Configuring your system](configuring.md)
- [What's new in version 0.25.0](version0.25.md#enabling-zlibnx-hardware-accelerated-data-compression-and-decompression-on-aix)
- [What's new in version 0.41.0](version0.41.md#new-xx-usezlibnx-option-added)
- [What's new in version 0.48.0](version0.48.md#loading-of-the-zlibnx-library-on-aix-is-disabled-by-default)

<!-- ==== END OF TOPIC ==== xxusezlibnx.md ==== -->
