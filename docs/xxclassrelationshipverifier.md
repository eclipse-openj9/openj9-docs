<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

# -XX:[+|-]ClassRelationshipVerifier

This option enables and disables the recording of class relationships in the verifier to delay validation until triggered by class loading.

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** You cannot use this setting in conjunction with [`-Xfuture`](xfuture.md) or [`-Xverify:all`](xverify.md), which itself enables `-Xfuture`.

## Syntax

        -XX:[+|-]ClassRelationshipVerifier

| Setting                          | Effect  | Default                                                                        |
|----------------------------------|---------|:------------------------------------------------------------------------------:|
| `-XX:+ClassRelationshipVerifier` | Enable  |                                                                                |
| `-XX:-ClassRelationshipVerifier` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

When enabled, this option delays validating the relationships between classes until the classes are required to be loaded during program execution. In this way, classes that are not required, are never loaded thus reducing VM startup time. 

A verify error is thrown if validation fails.


<!-- ==== END OF TOPIC ==== xxclassrelationshipverifier.md ==== -->


