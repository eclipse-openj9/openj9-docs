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

# -Djdk.nativeCrypto

This option controls the use of OpenSSL native cryptographic support.


## Syntax

        -Djdk.nativeCrypto=[true|false]

| Setting              | value    | Default                                                                        |
|----------------------|----------|:------------------------------------------------------------------------------:|
| `-Djdk.nativeCrypto` | true     | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| `-Djdk.nativeCrypto` | false    |                                                                                |

## Explanation

OpenSSL support is enabled by default for the <!--Digest,--> CBC, GCM, and RSA algorithms. If you want to turn off the OpenSSL implementation, set this option to `false`.

If you want to turn off the an algorithms individually, use the following system properties:

- [`-Djdk.nativeCBC`](djdknativecbc.md)
<!--- [`-Djdk.nativeDigest`](djdknativedigest.md)-->
- [`-Djdk.nativeGCM`](djdknativegcm.md)
- [`-Djdk.nativeRSA`](djdknativersa.md)



<!-- ==== END OF TOPIC ==== dcomibmdbgmalloc.md ==== -->
