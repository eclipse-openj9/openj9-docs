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

# -XX:\[+|-\]IdleTuningCompactOnIdle

**(Linux&reg; only)**

This option controls garbage collection processing with compaction when the status of the OpenJ9 VM is set to idle.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restrictions:** This option applies only to Linux architectures when the Generational Concurrent (`gencon`) garbage collection policy is in use. This option is not effective if the object heap is configured to use large pages.

## Syntax

        -XX:[+|-]IdleTuningCompactOnIdle

| Setting                        | Effect  | Default                                                                            |
|--------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+IdleTuningCompactOnIdle` | Enable  |                                                                                    |
| `-XX:-IdleTuningCompactOnIdle` | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |


If you enable this option, the OpenJ9 VM attempts to compact the object heap when the VM status is idle.

The `-XX:+IdleTuningCompactOnIdle` option can be used with the `-XX:+IdleTuningMinIdleWaitTime`. If a value for the `-XX:+IdleTuningMinIdleWaitTime` option is not explicitly specified, the VM sets a default value of 180 seconds. If you want the VM to subsequently attempt to release the free memory pages in the object heap, use the `-XX:+IdleTuningGcOnIdle` option.

## See also

- [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md)
- [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md)
- [-XX:\[+|-\]IdleTuningGcOnIdle](xxidletuninggconidle.md)



<!-- ==== END OF TOPIC ==== xxidletuningcompactonidle.md ==== -->
