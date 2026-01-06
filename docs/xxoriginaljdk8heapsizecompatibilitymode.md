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

# -XX:\[+|-\]OriginalJDK8HeapSizeCompatibilityMode

![Start of content that applies only to Java 8 (LTS)](cr/java8.png) The default value for the maximum heap size (`-Xmx`) is 25% of the available memory with a maximum of 25 GB. However, where there is 2 GB or less of physical memory, the value set is 50% of available memory with a minimum value of 16 MB and a maximum value of 512 MB. In Eclipse OpenJ9&trade; 0.18.0 and earlier releases the default is half the available memory with a minimum of 16 MB and a maximum of 512 MB. Enable this option to revert to the earlier default value.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This option is supported only on Java&trade; 8. It is ignored on Java 11 and later versions.

## Syntax

    -XX:[+|-]OriginalJDK8HeapSizeCompatibilityMode

| Setting                                     | Effect     | Default                                                                            |
|---------------------------------------------|------------|:----------------------------------------------------------------------------------:|
| -XX:+OriginalJDK8HeapSizeCompatibilityMode  | Enable     |                                                                                    |
| -XX:-OriginalJDK8HeapSizeCompatibilityMode  | Disable    | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |

![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)
