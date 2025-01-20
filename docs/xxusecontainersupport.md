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

# -XX:[+|-]UseContainerSupport

**(Linux&reg; only)**

If your application is running in a container that imposes a memory limit, the VM allocates a larger fraction of memory to the Java heap. To turn off this behavior, set the `-XX:-UseContainerSupport` option on the command line.

## Syntax

        -XX:[+|-]UseContainerSupport



| Setting                    | Effect  | Default                                                                            |
|----------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:-UseContainerSupport` | Disable |                                                                                    |
| `-XX:+UseContainerSupport` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |


When using container technology, applications are typically run on their own and do not need to compete for memory. The Eclipse OpenJ9&trade; VM detects when it is running inside a container that imposes a memory limit, and adjusts the maximum Java heap size appropriately.

The following table shows the values that are used when `-XX:+UseContainerSupport` is set:

| Container memory limit *&lt;size&gt;* | Maximum Java heap size  |
|---------------------------------------|-------------------------|
| Less than 1 GB                        | 50% *&lt;size&gt;*      |
| 1 GB - 2 GB                           | *&lt;size&gt;* - 512 MB |
| Greater than 2 GB                     | 75% *&lt;size&gt;*      |


The default heap size is capped at 25 GB, which is the limit of heap size for 3-bit shift of compressed references (see [-Xcompressedrefs](xcompressedrefs.md)), to prevent silent switching to 4-bit shift of compressed references, which has possible performance penalties. You can use the [`-Xmx`](xms.md) option or the [`-XX:MaxRAMPercentage`](xxinitialrampercentage.md) option to overwrite the 25 GB limit.

The default heap size for containers takes affect only when the following conditions are met:

1. The application is running in a container environment.
2. The memory limit for the container is set.
3. The `-XX:+UseContainerSupport` option is set, which is the default behavior.

To prevent the VM adjusting the maximum heap size when running in a container, set `-XX:-UseContainerSupport`.

When [`-XX:MaxRAMPercentage` / `-XX:InitialRAMPercentage`](xxinitialrampercentage.md) are used with `-XX:+UseContainerSupport`, the corresponding heap setting is determined based on the memory limit of the container. For example, to set the maximum heap size to 80% of the container memory, specify the following options:

    -XX:+UseContainerSupport -XX:MaxRAMPercentage=80

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If you set a value for [`-Xms`](xms.md), the `-XX:InitialRAMPercentage` option is ignored.
If you set a value for [`-Xmx`](xms.md), the `-XX:MaxRAMPercentage` option is ignored.

<!-- ==== END OF TOPIC ==== xxusecontainersupport.md ==== -->
