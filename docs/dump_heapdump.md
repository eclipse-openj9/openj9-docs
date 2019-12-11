<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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

# Heap dump

Heap dumps contain a snapshot of all the live objects that are being used by a running Java application on the Java heap. There are two formats for heap dumps; the classic format, which is ascii text and the PHD format, which is compressed and not readable.

A heap dump contains a list of all object instances. For each object instance you can find the following additional data:

- The object address
- The type or class name
- The size
- Any references to other objects

For a visual analysis of a heap dump as an aid to problem determination, use the [Eclipse Memory Analyzer tool (MAT)](https://www.eclipse.org/mat/) or the [IBM Memory Analyzer tool](https://www.ibm.com/support/knowledgecenter/en/SSYKE2_8.0.0/com.ibm.java.80.doc/diag/tools/tool_memoryanalyzer.html). Both tools require the Diagnostic Tool Framework for Java (DTFJ) plugin. To install the DTFJ plugin in the Eclipse IDE, select the following menu items:

```
Help > Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and Diagnostic Tools > Diagnostic Tool Framework for Java   
```

For more information about using the Heapdump feature, see [Using Heapdump](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/heapdump.html).

## See also

- [Using DTFJ](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/dtfj.html)

<!-- ==== END OF TOPIC ==== dump_heapdump.md ==== -->
