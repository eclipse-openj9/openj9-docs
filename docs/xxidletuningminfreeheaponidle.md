<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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

# -XX:IdleTuningMinFreeHeapOnIdle

**(Linux&reg; only)**

This option controls the percentage of free memory pages in the object heap that can be released when the OpenJ9 VM is in an idle state.

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restrictions:** This option applies only to Linux architectures when the Generational Concurrent (`gencon`) garbage collection policy is in use. This option is not effective if the object heap is configured to use large pages.

## Syntax

        -XX:IdleTuningMinFreeHeapOnIdle=<percentage>

| Setting      |  Value      | Default  |
|--------------|-------------|----------|
|`<percentage>`| *[0 - 100]* | 0        |


When used with `-XX:+IdleTuningGcOnIdle`, this option can be used to place an upper bound on the percentage of free memory pages in the object heap that can be released when the VM is in an idle state. If `-XX:IdleTuningMinFreeHeapOnIdle` is not specified, the VM uses a default value of 0.

## Example

If you set `-XX:IdleTuningMinFreeHeapOnIdle=10`, no more than 90% of the free memory pages in the object heap can be released by the VM when it is in an idle state.

## See also

- [-XX:IdleTuningMinIdleWaitTime](xxidletuningminidlewaittime.md)
- [-XX:\[+|-\]IdleTuningCompactOnIdle \(deprecated\)](xxidletuningcompactonidle.md)
- [-XX:\[+|-\]IdleTuningGcOnIdle](xxidletuninggconidle.md)


<!-- ==== END OF TOPIC ==== xxidletuningminfreeheaponidle.md ==== -->
