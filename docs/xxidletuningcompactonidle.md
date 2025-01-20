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

# -XX:\[+|-\]IdleTuningCompactOnIdle

**(Linux&reg; only)**

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Warning:** From Eclipse OpenJ9&trade; version 0.23.0 this option has no effect.

In versions of OpenJ9 before 0.23.0, this option controls garbage collection processing with compaction when the state of the OpenJ9 VM is set to idle.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**
  
1. This option was deprecated in release 0.15.0 due to operational changes. A compaction is now triggered by internal heuristics that look into the number of fragmented pages. Typically there is no need to force a compaction. This option will be removed in the future.
2. This option applies only to Linux architectures when the Generational Concurrent (`gencon`) garbage collection policy is in use.
3. This option is not effective if the object heap is configured to use large pages.
4. This option is not effective if [-XX:+IdleTuningGcOnIdle](xxidletuninggconidle.md) is not specified.

## Syntax

        -XX:[+|-]IdleTuningCompactOnIdle

| Setting                        | Effect  | Default  | Default when running in a docker container                                 |
|--------------------------------|---------|:--------:|:--------------------------------------------------------------------------:|
| `-XX:+IdleTuningCompactOnIdle` | Enable  |  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>     |
| `-XX:-IdleTuningCompactOnIdle` | Disable |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>   |   |

The default depends on whether or not the OpenJ9 VM is running in a container. As indicated in the table, when the VM is running in a container and the state is set to idle, the VM attempts to compact the object heap following a garbage collection cycle. The garbage collection cycle is controlled by the `-XX:+IdleTuningGcOnIdle` option, which is also enabled by default when the OpenJ9 VM is running inside a container.

If your application is not running in a container and you want compaction to be attempted every time idle GC happens as part of the idle-tuning process, set the `-XX:+IdleTuningCompactOnIdle` option on the command line when you start your application.

The `-XX:+IdleTuningCompactOnIdle` option can be used with the `-XX:+IdleTuningMinIdleWaitTime`, which controls the amount of time that the VM must be idle before an idle state is set. If a value for the `-XX:+IdleTuningMinIdleWaitTime` option is not explicitly specified, the VM sets a default value of 180 seconds.

## See also

- [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md)
- [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md)
- [-XX:\[+|-\]IdleTuningGcOnIdle](xxidletuninggconidle.md)



<!-- ==== END OF TOPIC ==== xxidletuningcompactonidle.md ==== -->
