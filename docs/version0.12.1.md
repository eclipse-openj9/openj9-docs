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


# What's new in version 0.12.1

The following change is implemented since v.0.12.0:

By default, OpenJ9 provides native cryptographic acceleration using OpenSSL V1.1.x for the Digest, CBC, GCM, and RSA algorithms. Under certain
circumstances acceleration of the Digest algorithm was found to cause a segmentation error. Cryptographic acceleration of the Digest algorithm is now turned off by default. The system property `-Djdk.nativeDigest` cannot be used to turn on support. This property is ignored by the VM.

## Full release information

Release notes to describe the changes between Eclipse OpenJ9 V0.12.0 and V0.12.1 releases, can be found in the [OpenJ9 GitHub repository](https://github.com/eclipse/openj9/blob/master/doc/release-notes/0.12/0.12.1md).

<!-- ==== END OF TOPIC ==== version0.12.1.md ==== -->
