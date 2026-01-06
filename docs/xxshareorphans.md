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

# -XX:[+|-]ShareOrphans

This option enables or disables sharing of orphan classes from class loaders that do not implement the OpenJ9's [public shared classes cache APIs](https://eclipse.dev/openj9/docs/api-shrc/).

## Syntax

        -XX:[+|-]ShareOrphans

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+ShareOrphans` |  Enable   |                                                                                    |
| `-XX:-ShareOrphans` |  Disable  |   :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>   |

## Explanation

In the previous versions, OpenJ9 stored only bootstrap classes and hidden classes in the shared classes cache by default, that is the `-Xshareclasses` option is not specified by default. When the `-Xshareclasses` option was specified, only those class loaders that implemented the OpenJ9's public shared classes cache APIs (and its child class loaders) could store classes to the shared classes cache. For classes from custom class loaders that did not implement the shared classes cache APIs, the VM does not have their module or class path information. Such classes were not stored to the cache.

You can enable class sharing from all class loaders, irrespective of whether the class loader implements the cache API, with the `-XX:+ShareOrphans` option. This option automatically enables the `-Xshareclasses` option. Conversely, if the `-Xshareclasses` option is specified in the command line, it automatically enables the `-XX:+ShareOrphans` option (from release 0.47.0 onwards).

When the class sharing from all class loaders is enabled, following is the sharing behavior:

- For classes from class loaders that implement the shared class cache API, they are shared as normal ROM classes, which is same as enabling `-Xshareclasses`.

- For classes from class loaders that do not implement the shared class cache API, the VM won't have their class or module path information. They are shared as orphan ROM classes with other VMs after extra comparisons.

Storing additional classes in the cache makes more classes available for Ahead-of-Time (AOT) compilation and therefore might improve startup performance.

You can disable sharing class as orphans from class loader that does not implement the shared class cache API with the `-XX:-ShareOrphans` option. This option is the default mode.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- Sharing classes as orphans requires more comparison on the classes by the VM. The comparison itself has a negative performance impact. However, the benefits of more AOT generated for the cached class might offset the negative impact.
- More cached classes usually result in more AOT-compiled methods. The relative advantage of orphan sharing decreases when the CPUs are less. With more CPUs, the compilation threads can compile those additional methods in parallel with the application threads, while with fewer CPUs, extra compilation activity hinders application threads.

For more information, see [What's new in version 0.47.0](version0.47.md#restrictions-in-the-xx-shareorphans-option-fixed).

## See also

- [What's new in version 0.46.0](version0.46.md#new-xx-shareorphans-option-added)
- [What's new in version 0.47.0](version0.47.md#-xshareclasses-option-automatically-enables-xxshareorphans)



<!-- ==== END OF TOPIC ==== xxshareorphans.md ==== -->
