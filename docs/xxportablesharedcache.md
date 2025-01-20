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

# -XX:\[+|-\]PortableSharedCache

Use this command line option to choose whether AOT-compiled code should be portable.

This option, when enabled, increases the portability of AOT-compiled code, in the following ways:

- The code is generated based on a particular set of processor features that ensures the AOT-compiled code to be portable across processors of different microarchitectures. AOT-compiled code generated with this option is guaranteed to be portable across Intel&reg; Sandy Bridge or newer microarchitectures on x86 platforms, IBM&reg; z10 or newer microarchitectures on s390 platforms and IBM POWER8&reg; or newer microarchitectures on POWER platforms.

- The code is generated to be portable across Eclipse OpenJ9&trade; VMs that use compressed references and have a heap size of 1 MB to 28 GB (previously, AOT-compiled code could not be shared between VMs that use compressed references and that have different heap sizes). This feature might introduce a small (1-2%) steady-state throughput penalty when compressed references are used and the heap size is between 1 MB and 3 GB.

This feature is particularly relevant for packaging a shared classes cache into a container image (for example, applications deployed on the cloud in the form of Docker containers) due to the following reasons:
- The processor on which the container image is built is likely to be different from the processor on which the container is deployed. 
- In a multi-layered container image where the shared classes cache is multi-layered as well, the AOT-compiled code in shared classes cache will likely come from multiple OpenJ9 VMs with different heap size requirements.

## Syntax

        -XX:[+|-]PortableSharedCache

| Setting                            | Effect  | Default               |
|------------------------------------|---------|:---------------------:|
| `-XX:+PortableSharedCache`         | Enable  | See notes that follow |
| `-XX:-PortableSharedCache`         | Disable |                       |

### Default settings

This option is _enabled_ by default in containers. To disable the option in a container, specify `-XX:-PortableSharedCache`.

The option is _disabled_ by default outside containers. To enable the option outside a container, specify `-XX:+PortableSharedCache` for the initial JVM instance (when the creation of the shared classes cache happens) as well as for every subsequent instance that makes use of the same shared classes cache.

<!-- ==== END OF TOPIC ==== xxportablesharedcache.md ==== -->
