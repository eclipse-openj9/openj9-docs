<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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
* [2] http://openjdk.java.net/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->

# -XX:IdleTuningMinIdleWaitTime

** (Linux<sup>&trade;</sup> only) **

When the OpenJ9 VM is idle, this option controls the minimum length of time that the VM must be idle before the status of the VM is set to idle. Further tuning options control the compaction of the object heap and the release of free memory pages, which reduces the footprint of the VM.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span class="sr-only">Restrictions</span> **Restrictions:** This option applies only to Linux on x86-32 and x86-64 architectures when the Generational Concurrent (`gencon`) garbage collection policy is in use. This option is not effective if the object heap is configured to use large pages.

## Syntax

        -XX:IdleTuningMinIdleWaitTime=<secs>

| Setting     |  Value           | Default  |
|-------------|------------------|----------|
|`<secs>`     | *[0 or greater]* | 0        |

The value used for `<secs>` specifies the minimum length of time in seconds that the VM is idle before the state is set to idle. Setting the value to 0 disables this feature, which causes the following idle tuning options to have no effect:

- `-XX:+IdleTuningCompactOnIdle`
- `-XX:+IdleTuningGcOnIdle`
- `-XX:IdleTuningMinFreeHeapOnIdle`


## See also

-   [-XX:\[+|-\]IdleTuningCompactOnIdle](xxidletuningcompactonidle.md#xx/|-/idletuningcompactonidle "This option controls garbage collection processing with compaction when the status of the VM is set to idle.")
-   [-XX:\[+|-\]IdleTuningGcOnIdle](xxidletuninggconidle.md#xx/|-/idletuninggconidle "This option can be used to reduce the memory footprint of the VM when it is in an idle state.")
-   [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md#xxidletuningminfreeheaponidle "This option controls the percentage of free memory pages in the object heap that can be released when the VM is in an idle state.")



<!-- ==== END OF TOPIC ==== xxidletuningminidlewaittime.md ==== -->
