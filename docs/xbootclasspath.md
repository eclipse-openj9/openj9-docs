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

# -Xbootclasspath

This Oracle&reg; HotSpot&trade; option specifies the search path for bootstrap classes and resources. The default is to search for bootstrap classes and resources in the internal VM directories and `.jar` files. The option is recognized by the Eclipse OpenJ9&trade; VM.

## Syntax

|Limited to...       | Setting                   | Effect                                                                   |
|--------------------|---------------------------|--------------------------------------------------------------------------|
|![](cr/java8.png)  |`-Xbootclasspath:<path>`   |Sets the search path for bootstrap classes and resources.                 |
|![](cr/java8.png)  |`-Xbootclasspath/p:<path>` |Prepends the specified resources to the front of the bootstrap class path.|
|                    |`-Xbootclasspath/a:<path>` |Appends the specified resources to the end of the bootstrap class path.   |

: where `<path>` represents directories and compressed or Java&trade; archive files separated with colons (:). On Windows&trade; systems, use a semicolon (;) as a separator.

![](cr/java8.png) Oracle advise that you should "not deploy applications that use this option to override a class in `rt.jar`, because this violates the JRE binary code license."


<!-- ==== END OF TOPIC ==== xbootclasspatha.md ==== -->
