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

# -XX:\[+|-\]PrintFlagsFinal

When enabled, this option outputs the values of a subset of configuration parameters in a format compatible with that produced by HotSpot. The parameters currently output are those expected by various software projects and packages.

## Syntax

        -XX:[+|-]PrintFlagsFinal

| Setting                | Effect  | Default |
|------------------------|---------|:-------:|
| `-XX:+PrintFlagsFinal` | Enable  |         |
| `-XX:-PrintFlagsFinal` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Example

Here is an example of typical output from `-XX:+PrintFlagsFinal`:

```
[Global flags]
   size_t MaxHeapSize              = 4294967296          {product} {ergonomic}
 uint64_t MaxDirectMemorySize      = 3758096384          {product} {ergonomic}
```
 
 
<!-- ==== END OF TOPIC ==== xxprintflagsfinal.md ==== -->

