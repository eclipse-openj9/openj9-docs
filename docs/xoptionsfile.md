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

# -Xoptionsfile


Specifies a file that contains VM options and definitions.

The contents of the options file are recorded in the `ENVINFO` section of a Java&trade; dump.

## Syntax

        -Xoptionsfile=<file_name>

:   where `<file_name>` specifies a file that contains options that are processed as if they had been entered directly as command-line options.

## Explanation

At startup, the VM automatically adds `-Xoptionsfile=<path>/options.default` at the beginning of the command line, where `<path>` is the path to the VM directory.

![Start of content that applies only to Java 8 (LTS)](cr/java8.png) `<path>` is the VM directory, as shown in [Directory conventions](openj9_directories.md). ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)  
![Start of content that applies only to Java 11 and later](cr/java11plus.png) `<path>` is the `<java_home>/lib` directory, where `<java_home>` is the directory for your runtime environment. ![End of content that applies only to Java 11 or later](cr/java_close.png)

The file `options.default` can be updated with any options that you want to specify at run time.

The options file does not support these options:

- `-assert`
- `-fullversion`
- `-help`
- `-showversion`
- `-version`
- [`-Xcompressedrefs`](xcompressedrefs.md)
- [`-Xcheck:memory`](xcheck.md#memory)
- [`-Xoptionsfile`](xoptionsfile.md)
- `-XshowSettings`

Although you cannot use `-Xoptionsfile` recursively within an options file, you can use `-Xoptionsfile` multiple times on the same command line to load more than one options files.

Some options use quoted strings as parameters. Do not split quoted strings over multiple lines using the forward slash line continuation character (\\). The Yen symbol (¥) is not supported as a line continuation character. For example, the following example is not valid in an options file:

    -Xevents=vmstop,exec="cmd /c \
    echo %pid has finished."

The following example is valid in an options file:

    -Xevents=vmstop, \
    exec="cmd /c echo %pid has finished."

## Example

Here is an example of an options file:

    #My options file
    -X<option1>
    -X<option2>=\
    <value1>,\
    <value2>
    -D<sysprop1>=<value1>

## See also

- [Specifying command-line options](cmdline_specifying.md)
- [Javadump: TITLE, GPINFO, and ENVINFO sections](dump_javadump.md#envinfo)


<!-- ==== END OF TOPIC ==== xoptionsfile.md ==== -->
