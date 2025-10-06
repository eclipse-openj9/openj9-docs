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

# -XX:IdleTuningMinIdleWaitTime

**(Linux&reg;, macOS&reg;, and z/OS&reg;)**

When the Eclipse OpenJ9&trade; VM is idle, this option controls the minimum length of time that the VM must be idle before the state of the VM is set to idle. When the state changes to idle, a garbage collection cycle runs, the object heap is compacted, and free memory pages are released back to the operating system, which reduces the footprint of the VM. Garbage collection and compaction are controlled by the `-XX:+IdleTuningGcOnIdle` and `-XX:+IdleTuningCompactOnIdle` options, which are enabled by default when the OpenJ9 VM is running inside a docker container. (Note that from OpenJ9 version 0.23.0 the `-XX:+IdleTuningCompactOnIdle` option has no effect.)

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This option applies only to Linux architectures when the Generational Concurrent (`gencon`) garbage collection policy is in use. This option is not effective if the object heap is configured to use large pages.

## Syntax

        -XX:IdleTuningMinIdleWaitTime=<secs>

| Setting     |  Value           |  Default  | Default when running in a docker container   |
|-------------|------------------|-----------|----------------------------------------------|
|`<secs>`     | *[0 or greater]* |    0      |                   180                        |

The value used for `<secs>` specifies the minimum length of time in seconds that the VM is idle before the state is set to idle. Idle tuning is enabled by default when the OpenJ9 VM is running in a docker container and the VM is detected as idle for 180 seconds. 

Setting the value to 0 disables this feature, which causes the following idle tuning options to have no effect:

- `-XX:+IdleTuningCompactOnIdle`
- `-XX:+IdleTuningGcOnIdle`
- `-XX:IdleTuningMinFreeHeapOnIdle`


## See also

- [-XX:\[+|-\]IdleTuningGcOnIdle](xxidletuninggconidle.md)
- [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md#xxidletuningminfreeheaponidle)
- [-XX:\[+|-\]IdleTuningCompactOnIdle](xxidletuningcompactonidle.md) (From OpenJ9 version 0.23.0 this option has no effect.)

<!-- ==== END OF TOPIC ==== xxidletuningminidlewaittime.md ==== -->
