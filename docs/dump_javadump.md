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

# Java dump

Java dumps, sometimes referred to as *Java cores*, are produced when the VM ends unexpectedly because of an operating system signal, `OutOfMemoryError` exception, or a user-initiated keystroke combination. You can also generate a Java dump by calling the Dump API programmatically from your application or specifying the `-Xdump:java` option on the command line.

Java dumps summarize the state of the VM when the event occurs, with most of the information relating to components of the VM.

For more information about using the Javadump feature, see [Using Javadump](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/javadump.html).



<!-- ==== END OF TOPIC ==== dump_javadump.md ==== -->
