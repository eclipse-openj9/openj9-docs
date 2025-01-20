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

# -Djava.lang.stringBuffer.growAggressively

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This system property is supported only on Java&trade; 8.

![Start of content that applies only to Java 8 (LTS)](cr/java8.png) Setting this property to `false` reverts to the behavior (Eclipse OpenJ9&trade; 0.18 and earlier) of growing a 1 G `char[]` or larger `StringBuffer` or `StringBuilder` only as much as necessary to accommodate the `String` being added. The default behavior is to immediately grow to the maximum possible size, similarly to Java 11 and later. The default behavior is compatible with the Oracle HotSpot VM.



## Syntax

        -Djava.lang.stringBufferAndBuilder.growAggressively=[true|false]

| Setting      | Effect                                | Default                                                                            |
|--------------|---------------------------------------|:----------------------------------------------------------------------------------:|
| true         | Above 1 G, grow to the maximum size  |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>    |
| false        | Above 1 G, grow only as required     |                                                                                    |


<!-- ==== END OF TOPIC ==== djavalangstringbuffergrowaggressively.md ==== -->
