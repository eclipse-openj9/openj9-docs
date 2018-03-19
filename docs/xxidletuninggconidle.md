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

# -XX:\[+|-\]IdleTuningGcOnIdle  

**(Linux<sup>&trade;</sup> only)**

This option can be used to reduce the memory footprint of the OpenJ9 VM when it is in an idle state.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span class="sr-only">Restrictions</span> **Restrictions:**  
- This option applies only to Linux on x86-32 and x86-64 architectures when the Generational Concurrent (`gencon`) garbage collection policy is in use.  
- This option is not effective if the object heap is configured to use large pages.

## Syntax

        -XX:[+|-]IdleTuningGcOnIdle

| Setting                   | Effect  | Default                                                                            |
|---------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+IdleTuningGcOnIdle` | Enable  |                                                                                    |
| `-XX:-IdleTuningGcOnIdle` | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Default</span> |


When the VM enters the idle state, enabling this option causes the VM to release free memory pages in the object heap without resizing the Java<sup>&trade;</sup> heap. The pages are reclaimed by the operating system, which reduces the physical memory footprint of the VM.

This option is used with `-XX:IdleTuningMinIdleWaitTime` and `-XX:IdleTuningMinFreeHeapOnIdle` . If values for these options are not explicitly specified, the VM sets the following defaults:

-   `-XX:IdleTuningMinIdleWaitTime`=180
-   `-XX:IdleTuningMinFreeHeapOnIdle`=0


If you want the VM to attempt to compact the object heap before releasing memory, use the`-XX:+IdleTuningCompactOnIdle` option.

## See also

- [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md)
- [-XX:IdleTuningMinFreeHeapOnIdle](xxidletuningminfreeheaponidle.md)
- [-XX:\[+|-\]IdleTuningCompactOnIdle](xxidletuningcompactonidle.md)


<!-- ==== END OF TOPIC ==== xxidletuninggconidle.md ==== -->
