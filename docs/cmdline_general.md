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

# Standard command-line options


The OpenJ9 virtual machine supports the standard Java&trade; options that are common to all Java virtual machine implementations, including Oracle's HotSpot VM.
Some of the common options supported are summarised in the following table:

| Standard option name                                                | Purpose                                                                                                                   |
|---------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| `-classpath:<resource_name>[:<resource_name>]`                      | Sets the search path for application classes and resources (directories and compressed or .jar files). `cp` can be used instead of `classpath`.|
| `-help`, `-?`                                                       | Prints a usage message.                                                                                                   |
| `-fullversion`                                                      | Prints the build and version information for a VM                                                                         |
| `-showversion`                                                      | Prints product version and continues.                                                                                     |
| `-verbose:<option>[,<option>]`                                      | Enables verbose output. Options include `class`, `dynload`, `gc`, `init`, `jni`, `sizes` and `stack`. (See **Notes**)     |
| `-version`                                                          | Prints the full build and version information a VM                                                                        |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Notes:**

- `-verbose:class`: Writes an entry to `stderr` for each class that is loaded.
- `-verbose:dynload`: Writes detailed class information to `stderr` as each bootstrap class is loaded by the VM:
- `-verbose:gc`: Provides verbose garbage collection information.
- `-verbose:init`: Writes information to `stderr` describing VM initialization and termination.
- `-verbose:jni`: Writes information to `stderr` describing the JNI services called by the application and VM.
- `-verbose:sizes`: Writes information to `stderr` describing the active memory usage settings.
- `-verbose:stack`: Writes information to `stderr` describing the Java and C stack usage for each thread.

For more information about standard options, see [Oracle Java SE Standard Options](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/java.html#BABDJJFI)


<!-- ==== END OF TOPIC ==== cmdline_general.md ==== -->
