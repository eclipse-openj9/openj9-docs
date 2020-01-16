<!--
* Copyright (c) 2020, 2020 IBM Corp. and others
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

# -Djava.lang.string.substring.nocopy

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> **Restriction:** This system property is supported only on Java&trade; 8. String sharing cannot be enabled on Java 11 and later.

![Start of content that applies only to Java 8 (LTS)](cr/java8.png) Setting this property to `true` avoids sharing a String object when substring() is used to subset a String beginning from offset zero. Avoiding sharing is compatible with the Oracle Hotspot VM.

## Syntax

        -Djava.lang.string.substring.nocopy=[true|false]

| Setting      | Effect     | Default                                                                            |
|--------------|------------|:----------------------------------------------------------------------------------:|
| true         | No sharing |                                                                                    |
| false        | Sharing    | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span>     |


<!-- ==== END OF TOPIC ==== djavalangstringsubstringnocopy.md ==== -->
