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

# -XX:\[+|-\]VMLockClassLoader

This option affects synchronization on class loaders that are not parallel-capable class loaders, during class loading.

## Syntax

        -XX:[+|-]VMLockClassLoader

| Setting                  | Effect  | Default                                                                            |
|--------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+VMLockClassLoader` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-VMLockClassLoader` | Disable |                                                                                    |

:   The option, `-XX:+VMLockClassLoader`, causes the VM to force synchronization on a class loader that is not a parallel capable class loader during class loading. This action occurs even if the `loadClass()` method for that class loader is not synchronized. For information about parallel capable class loaders, see `java.lang.ClassLoader.registerAsParallelCapable()`. Note that this option might cause a deadlock if class loaders use non-hierarchical delegation. For example, setting the system property `osgi.classloader.lock=classname` with Equinox is known to cause a deadlock. This is the default option.

    When specifying the `-XX:-VMLockClassLoader` option, the VM does not force synchronization on a class loader during class loading. The class loader still conforms to class library synchronization, such as a synchronized `loadClass()` method.



<!-- ==== END OF TOPIC ==== xxvmlockclassloader.md ==== -->
