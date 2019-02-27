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

# -Djdk.nativeRSA

This option enables or disables OpenSSL native cryptographic support for the RSA algorithm.


## Syntax

        -Djdk.nativeRSA=[true|false]


| Setting           | value    | Default                                                                        |
|-------------------|----------|:------------------------------------------------------------------------------:|
| `-Djdk.nativeRSA` | true     | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| `-Djdk.nativeRSA` | false    |                                                                                |

## Explanation

OpenSSL support is enabled by default for the CBC, ![Start of content that applies only to Java 12](cr/java12.png)Digest![End of content that applies only to Java 12](cr/java_close.png), GCM, and RSA algorithm. If you want to turn off the RSA algorithm, set this option to `false`.

To turn off all the algorithms, see the [-Djdk.nativeCrypto](djdknativecrypto.md) system property command line option.




<!-- ==== END OF TOPIC ==== djdknativersa.md ==== -->
