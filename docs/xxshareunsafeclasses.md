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

# -XX:[+|-]ShareUnsafeClasses

This option enables or disables the storage of VM classes created through `Unsafe.defineClass` in the shared classes cache.

The option is enabled by default, which means that unsafe classes are stored in the shared classes cache and are therefore available for ahead-of-time (AOT) compilation, potentially improving startup performance.

## Syntax

        -XX:[+|-]ShareUnsafeClasses

| Setting                      | Effect  | Default                                                                        |
|------------------------------|---------|:------------------------------------------------------------------------------:|
| `-XX:+ShareUnsafeClasses`    | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-ShareUnsafeClasses`    | Disable |                                                                                |


## See also

- [AOT compiler](aot.md)
- [Introduction to class data sharing](shrc.md)
- [-Xshareclasses](xshareclasses.md)
- [-XX:[+|-]ShareAnonymousClasses](xxshareanonymousclasses.md)



<!-- ==== END OF TOPIC ==== xxshareunsafeclasses.md ==== -->
