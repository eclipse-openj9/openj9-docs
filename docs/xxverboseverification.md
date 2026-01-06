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

# -XX:\[+|-\]VerboseVerification


You can use this option to control the output of verbose diagnostic data that relates to verification.

The Oracle documentation to support this option is no longer available, because it is no longer used by the HotSpot VM. An explanation is provided here.

## Syntax

        -XX:[+|-]VerboseVerification

| Setting                    | Effect  | Default                                                                            |
|----------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:-VerboseVerification` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:+VerboseVerification` | Enable  |                                                                                    |

Use  `-XX:-VerboseVerification` to enable the output of verbose diagnostic data to `stderr` that is generated during verification from the class file `StackMapTable` attribute. The data provides extra contextual information about bytecode verification, which helps diagnose bytecode or stackmap deficiencies in the field.

Class files that have `StackMapTable` attributes (that is, class files that conform to version 50.0 or later of the class file format specification), are introduced in Java&trade; V6. Class files with `StackMapTable` attributes are marked as `new format` in the verbose output, as shown in the example. Class files without the `StackMapTable` attributes are marked as `old format`. The `StackMapTable` diagnostic information is available only to classes verified with the new format.


Here is an example of `StackMapTable` diagnostic output:


```
Verifying class java.example.ibm.com with new format
Verifying method java.example.ibm.com.foo(Ljava/lang/String;Ljava/lang/Class;[Ljava/lang/String;Ljava/io/PrintStream;)I
StackMapTable: frame_count = 3
table = {
  bci: @37
  flags: { }
  locals: { 'java/lang/String', 'java/lang/Class', '[Ljava/lang/String;', 'java/io/PrintStream', 'java/lang/Class' }
  stack: { 'java/lang/ThreadDeath' }
  bci: @42
  flags: { }
  locals: { 'java/lang/String', 'java/lang/Class', '[Ljava/lang/String;', 'java/io/PrintStream', 'java/lang/Class' }
  stack: { 'java/lang/Throwable' }
  bci: @79
  flags: { }
  locals: { 'java/lang/String', 'java/lang/Class', '[Ljava/lang/String;', 'java/io/PrintStream', 'java/lang/Class',
        'java/lang/Throwable' }
  stack: { }
 }
End class verification for: java.example.ibm.com
```



<!-- ==== END OF TOPIC ==== xxverboseverification.md ==== -->
