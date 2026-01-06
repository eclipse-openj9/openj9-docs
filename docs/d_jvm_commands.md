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

# Using system property command-line options

Java&trade; system properties determine the environment in which a Java program runs by starting a Java virtual machine with a set of values.
You can choose to use the default values for Java system properties or you can specify values for them by adding parameters to the command line when you start your application.

To set a system property from the command line, use:

```
java -D<property_name>=<value> <program_name>
```

For example, to specify the UTF-8 file encoding for your application `MyProgram`, use:

```
java -Dfile.encoding=UTF-8 MyProgram
```




<!-- ==== END OF TOPIC ==== x_jvm_commands.md ==== -->
