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

# -Xfuture

As described in the [Oracle "Non-Standard Options" documentation](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html#BABHDABI), this HotSpot option turns on strict class-file format checks. For compatibility, this option is also supported by the Eclipse OpenJ9&trade; VM.

## Syntax

        -Xfuture

## Explanation

Oracle recommend that you use this flag when you are developing new code because stricter checks will become the default in future releases.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** You cannot use this setting in conjunction with [-XX:+ClassRelationshipVerifier](xxclassrelationshipverifier.md).

## Default behavior

By default, strict format checks are disabled.


<!-- ==== END OF TOPIC ==== xfuture.md ==== -->
