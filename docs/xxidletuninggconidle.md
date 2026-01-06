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

# -XX:\[+|-\]IdleTuningGcOnIdle  

**(Linux&reg;, macOS&reg;, and z/OS&reg;)**

This option controls whether a garbage collection cycle takes place when the state of the Eclipse OpenJ9&trade; VM is set to idle. Compaction of the heap is also attempted during the idle GC when certain triggers are met.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This option applies only to Linux architectures when the Generational Concurrent (`gencon`) garbage collection policy is in use. This option is not effective if the object heap is configured to use large pages.

## Syntax

        -XX:[+|-]IdleTuningGcOnIdle

| Setting                   | Effect  | Default  | Default when running in a docker container                                 |
|---------------------------|---------|:--------:|:--------------------------------------------------------------------------:|
| `-XX:+IdleTuningGcOnIdle` | Enable  |  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |
| `-XX:-IdleTuningGcOnIdle` | Disable |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>   |   |

The default depends on whether or not the OpenJ9 VM is running in a docker container. As indicated in the table, when the VM is running in a container and the state is set to idle, this option causes the VM to release free memory pages in the object heap without resizing the Java&trade; heap and attempts to compact the heap after the garbage collection cycle if certain heuristics are triggered. The pages are reclaimed by the operating system, which reduces the physical memory footprint of the VM.

If your application is not running in a container and you want to enable idle-tuning, set the `-XX:+IdleTuningGcOnIdle` option on the command line when you start your application.

When enabled, the `-XX:+IdleTuningGcOnIdle` option is used with the `-XX:IdleTuningMinIdleWaitTime` and `-XX:IdleTuningMinFreeHeapOnIdle` options. If values for these options are not explicitly specified, the VM sets the following defaults:

-   `-XX:IdleTuningMinIdleWaitTime`=180
-   `-XX:IdleTuningMinFreeHeapOnIdle`=0

## See also

- [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md)
- [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md)
- [-XX:\[+|-\]IdleTuningCompactOnIdle](xxidletuningcompactonidle.md) (From OpenJ9 version 0.23.0 this option has no effect.)
- [What's new in version 0.9.0](version0.9.md#idle-tuning-feature)
- [What's new in version 0.12.0](version0.12.md#idle-tuning-is-enabled-by-default-when-openj9-runs-in-a-docker-container)
- [What's new in version 0.15.1](version0.15.md#change-in-behaviour-of-xxidletuningcompactonidle)

<!-- ==== END OF TOPIC ==== xxidletuninggconidle.md ==== -->
