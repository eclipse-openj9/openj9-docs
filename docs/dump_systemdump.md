<!--
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

# System dump

System dumps, often known as *core dumps*, are platform-specific and contain a raw binary dump of the process memory. This type of dump has a complete copy of the Java heap, including the contents of all Java objects in the application.

To examine a system dump you can use the [OpenJ9 dump viewer](tool_jdmpview.md) (`jdmpview`), a platform-specific debugging tool, or the [Eclipse Memory Analyzer tool (MAT)](https://www.eclipse.org/mat/).

If you want to use MAT to analyze your system dump, you must install the  Diagnostic Tool Framework for Java (DTFJ) plugin in the Eclipse IDE. Select the following menu items:

```
Help > Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and Diagnostic Tools > Diagnostic Tool Framework for Java   
```


<!-- ==== END OF TOPIC ==== dump_systemdump.md ==== -->
