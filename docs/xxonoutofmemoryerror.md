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

# -XX:OnOutOfMemoryError

You can use this Oracle HotSpot option to run commands when a `java.lang.OutOfMemoryError` is thrown. This option is recognized by Eclipse OpenJ9&trade; and provided for compatibility.

## Syntax

        -XX:OnOutOfMemoryError="<command_string>"

where `<command_string>` is a command or list of commands to run when a `java.lang.OutOfMemoryError` occurs.

For example, the following command specifies that the `java -version` command is run if the `Test` application throws a `java.lang.OutOfMemoryError` exception:

`java -XX:OnOutOfMemoryError="java -version" Test`

If you want to run multiple commands, use semicolons to separate them  within `<command_string>`. For example:

`-XX:OnOutOfMemoryError="<java_path> <java_program>; cat file.txt"`

The `-XX:OnOutOfMemoryError` option is equivalent to the following `-Xdump` option:

`-Xdump:tool:events=systhrow,filter=java/lang/OutOfMemoryError,exec=<command_string>`

For more information, see [`-Xdump`](xdump.md).  


<!-- ==== END OF TOPIC ==== xxonoutofmemoryerror.md ==== -->
