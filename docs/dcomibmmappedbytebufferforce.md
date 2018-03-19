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

# -Dcom.ibm.mappedByteBufferForce


Setting this value to true forces data to be committed to disk during system failure.

## Syntax

        -Dcom.ibm.mappedByteBufferForce=[true|false]

| Setting      | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| true         | Enable  |                                                                                    |
| false        | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Default</span> |


## Explanation

During system failure, the `MappedByteBuffer.force` API does not commit data to disk, which prevents data integrity issues. Setting this value to `true` forces data to be committed to disk during system failure. Because this setting can cause performance degradation, this switch is not enabled by default.



<!-- ==== END OF TOPIC ==== dcomibmmappedbytebufferforce.md ==== -->
