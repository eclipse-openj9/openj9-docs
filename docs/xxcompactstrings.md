﻿<!--
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

# -XX:[+|-]CompactStrings

![Start of content that applies only to Java 8 and later](cr/java8plus.png) This HotSpot option is reimplemented by OpenJ9 and when enabled causes an ISO8859-1 (also known as Latin-1) character representation to be used internally for `String` objects, while preserving full API compatibility.

This feature provides heap space savings by using an 8-bit character set internally. Most benefit is gained when the majority of the `String` objects that your application uses can be encoded using the ISO8859-1 character encoding. 

If the option is not enabled, the JIT compiler is nevertheless optimized so that although there is no saving in heap space, there is also no performance penalty.

Further details are available at [JEP 254: Compact Strings](https://openjdk.java.net/jeps/254).

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** With OpenJ9, this option is supported on OpenJDK version 8 and later versions, whereas HotSpot supports it only from Java version 9.

## Syntax

| Setting                  | Effect                       | Default |
|--------------------------|------------------------------|:-------:|
| `-XX:+CompactStrings`    | Enable `String` compression  |         |
| `-XX:-CompactStrings`    | Disable `String` compression | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |

<!-- ==== END OF TOPIC ==== xxcompactstrings.md ==== -->