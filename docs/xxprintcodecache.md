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

# -XX:\[+|-\]PrintCodeCache

This Oracle HotSpot option prints the code cache memory usage when the application exits. This option is recognized by Eclipse OpenJ9&trade; and provided for compatibility.

## Syntax

        -XX:[+|-]PrintCodeCache

| Setting                      | Effect  | Default                                                                            |
|------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+PrintCodeCache`        | Enable  |                                                                                    |
| `-XX:-PrintCodeCache`        | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |

As discussed in the Oracle documentation, the code cache usage can be shown when the application exits, by specifying `–XX:+PrintCodeCache` on the Java launcher command line. The output looks similar to the following: 

```
CodeCache: size=262144Kb used=454Kb max_used=457Kb free=261690Kb
```

- `size`: The maximum size of the code cache.
- `used`: The amount of code cache memory actually in use.
- `max_used`: The high water mark for code cache usage.
- `free`: `size` minus `used`.

<!-- ==== END OF TOPIC ==== xxprintcodecache.md ==== -->
