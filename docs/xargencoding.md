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

# -Xargencoding 

The `java` and `javaw` launchers accept arguments and class names containing any character that is in the character set of the current locale. You can also specify any Unicode character in the class name and arguments by using Java&trade; escape sequences.

To do this, use the `-Xargencoding` command-line option.

## Syntax

        -Xargencoding:<parameter>

## Parameters

### No parameter

        -Xargencoding

: You can use Unicode escape sequences in the argument list that you pass to this option. To specify a Unicode character, use escape sequences in the form `\u####`, where `#` is a hexadecimal digit (0-9, A-F). For example, to specify a class that is called `HelloWorld` and use Unicode encoding for both capital letters, use this command:

        java -Xargencoding \u0048ello\u0057orld

### `utf8`

        -Xargencoding:utf8

: Use utf8 encoding.

### `latin`

        -Xargencoding:latin

: Use ISO8859_1 encoding.



<!-- ==== END OF TOPIC ==== xargencoding.md ==== -->



