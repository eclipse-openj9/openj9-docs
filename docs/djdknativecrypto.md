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

# -Djdk.nativeCrypto

This option controls the use of OpenSSL native cryptographic support.


## Syntax

        -Djdk.nativeCrypto=[yes|no]

| Setting              | value    | Default                                                                        |
|----------------------|----------|:------------------------------------------------------------------------------:|
| `-Djdk.nativeCrypto` | yes      |                                                                                |
| `-Djdk.nativeCrypto` | no       | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

## Explanation

OpenSSL support is not enabled by default. If you want to use this implementation to improve cryptographic performance, set this option to `yes` to enable support for the Digest, CBC, and GCM algorithms.

If you want to enable individual algorithms, use the following system properties:

- [`-Djdk.nativeCBC`](djdknativecbc.md)
- [`-Djdk.nativeDigest`](djdknativedigest.md)
- [`-Djdk.nativeGCM`](djdknativegcm.md)





<!-- ==== END OF TOPIC ==== dcomibmdbgmalloc.md ==== -->
