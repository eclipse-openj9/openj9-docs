<!--
* Copyright (c) 2017, 2026 IBM Corp. and others
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

# API documentation

The Eclipse OpenJ9&trade; VM provides supplementary application programming interfaces and extensions, which can be used to improve performance, assist with problem determination, or help monitor and manage the OpenJ9 VM. The documentation also includes links to the API documentation for the Java&trade; SE and JDK reference implementation.

## Native data operations

If your Java application manipulates native data, the Data Access Accelerator API package (`com.ibm.dataaccess`) can help improve application performance. Classes are available for the following types of operation:

- performing arithmetic, comparison, and validation of packed decimal data
- converting between decimal data types as well as to and from `BigDecimal` and `BigInteger` types
- marshalling Java binary types to and from byte arrays
- validating the sign and digits of a given external decimal input before operating on the data.

## GPU acceleration

You can improve the performance of your applications by offloading certain processing functions from your processor (CPU) to a graphics processing unit (GPU). If your application contains code that would benefit from parallel processing, you can use the CUDA4J API package (`com.ibm.cuda`) to specify in your code when to offload processing to the GPU. You can also use the GPU API package (`com.ibm.gpu`)  to accelerate certain Java functions, such as `sort` operations.

## Problem determination

The JVM diagnostic utilities API package (`com.ibm.jvm`) provides classes for controlling dump, log, and trace operations.

The Diagnostic Tool Framework for Java (DTFJ) API packages (`com.ibm.dtfj.*`) allow custom applications to be written that can access a wide range of information in a system dump or a Java dump.

## Monitoring and management

The shared classes API package (`com.ibm.oti.shared`) provides a large number of classes for managing permissions, finding and storing classes and byte data, and obtaining statistics about a shared classes cache. Classes are also available to enable class sharing for a custom class loader implementation.

OpenJ9 includes MXBean extensions to the `java.lang.management` API (`com.ibm.lang.management` and `openj9.lang.management`), which can be used to monitor and manage the VM. These extensions provide access to information about the state of the OpenJ9 VM and the environment in which it is running.

<!-- ==== END OF TOPIC ==== api-overview.md ==== -->
