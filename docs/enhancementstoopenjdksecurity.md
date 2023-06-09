<!--
* Copyright (c) 2017, 2023 IBM Corp. and others
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

# Enhancements to OpenJDK security

Eclipse OpenJ9&trade; includes the following enhancements to the OpenJDK security components.

## ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) Support for PKCS#11 token labels

**(z/OS&reg; and Linux on IBM Z&reg; only)**

On z/OS and Linux on IBM Z, OpenJ9 supports the use of an extra attribute, `tokenlabel`, in the SunPKCS11 configuration file. Use this attribute to assign a label to a PKCS#11 token.

The number of slots and their order depend on the number of tokens in the ICSF token database, their values, and the SAF CRYPTOZ class protection profiles that are currently defined. The ICSF PKCS#11 support ensures that a token resides in its current slot only for the duration of a PKCS#11 session (if the token is not deleted). If you restart an application, or tokens are created or removed, the token might move to a different slot. An application that uses the `slot` or `slotListIndex` attributes might fail if it doesn’t first check which slot the token is in. You can avoid this issue by using the `tokenlabel` attribute instead.

You can specify only one of the attributes - `slot`, `slotListIndex`, or `tokenlabel`. If you do not specify any of these attributes, the default behavior is that the `slotListIndex` attribute is set to 0.

For more information about the SunPKCS11 configuration file, see [PKCS#11 Reference Guide](https://docs.oracle.com/en/java/javase/11/security/pkcs11-reference-guide1.html).


<!-- ==== END OF TOPIC ==== enhancementstoopenjdksecurity.md ==== -->
