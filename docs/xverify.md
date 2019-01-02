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

# -Xverify

As described in the [Oracle documentation](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/java.html), this Hotspot option enables or disables the verifier. For compatibility, this option is also supported by the OpenJ9 VM.

## Syntax

| Setting           | Effect | Default |
|-------------------|--------|:-------:|
| `-Xverify`        | Enables verification for all non-bootstrap classes. [`-Xfuture`](xfuture.md) verification is not enabled. | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| `-Xverify:all`    | Enables verification for all classes and enables [`-Xfuture`](xfuture.md) verification. &nbsp; **Note:** This setting might have an impact on performance. | |
| `-Xverify:remote` | For compatibility, this parameter is accepted, but is equivalent to the default `-Xverify`. | |
| `-Xverify:none`   | Disables the verifier. &nbsp; **Note:** This is not a supported configuration. If you encounter problems with the verifier turned off, remove this option and try to reproduce the problem. | |


<!-- ==== END OF TOPIC ==== xverify.md ==== -->
