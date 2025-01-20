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

# Eclipse OpenJ9 command-line options

When you start a Java&trade; application you can specify various options on the command line to configure the runtime environment. These options include:

- [System properties](d_jvm_commands.md)
- [Standard options](cmdline_general.md)
- [Nonstandard (or -X) options](x_jvm_commands.md)
- [-XX options](xx_jvm_commands.md)

Although the command line is the traditional way to specify command-line options, you can also pass options to the Eclipse OpenJ9&trade; virtual machine (VM) by using a manifest file, options files, and environment variables.

Options specified on the command line override the equivalent environment variables. For example, specifying `java -cp <dir1>` completely overrides setting the environment variable `CLASSPATH=<dir2>`.

## Quotation marks

Use single or double quotation marks for command-line options only when explicitly directed to do so. Single and double quotation marks have different meanings on different platforms, operating systems, and shells. Do not use `'-X<option>'` or `"-X<option>"`. Instead, you must use `-X<option>`. For example, do not use `'-Xmx500m'` and `"-Xmx500m"`. Write this option as `-Xmx500m`.

## Precedence

The sequence of the Java options on the command line defines which options take precedence during startup. Rightmost options have precedence over leftmost options. In the following example, the `-Xjit` option takes precedence:

    java -Xint -Xjit myClass

At startup, the list of VM arguments is constructed in the following order, with the lowest precedence first:

1.  Certain options are created automatically by the VM, which specify arguments such as search paths and version information. The VM automatically adds `-Xoptionsfile=<path>/options.default` at the beginning of the command line, where `<path>` is the path to the VM directory.

    You can modify the `options.default` file to include any options that you want to specify for your application instead of entering these options on the command line. For more information about the path and construction of the file, see [-Xoptionsfile](xoptionsfile.md).

2.  Options can be specified in an executable JAR file by using the **META-INF/MANIFEST.MF** file. Options are placed in the main section in a header named `IBM-Java-Options`. Only one `IBM-Java-Options` header is permitted, but the header can contain multiple options, separated by spaces. A long sequence of options can be split using a header continuation but are treated as a single line.

    Example manifest file:

        Manifest-Version: 1.0
        Class-Path: .
        Main-Class: HelloWorld
        IBM-Java-Options: -Xshareclasses:name=mycache,nonfa
         tal,cacheDirPerm=1000 -Dproperty=example -Da.long.system.pro
         perty="this is a long system property value to
          demonstrate long VM arguments
         in the manifest file"

    This example manifest file is parsed as the following string:

        -Xshareclasses:name=mycache,nonfatal,cacheDirPerm=1000
        -Dproperty=example
        -Da.long.system.property=this is a long system property value to
        demonstrate long VM arguments in the manifest file

    Options specified in the manifest file are subject to the same restrictions as options files. For more information, see the [-Xoptionsfile](xoptionsfile.md#xoptionsfile "Specifies a file that contains VM options and definitions. The contents of the options file are recorded in the ENVINFO section of a Java dump.") topic in the user guide.

3.  Environment variables that are described in [OpenJ9 environment variables](env_var.md "In general, environment variables are superseded by command-line arguments but retained for compatibility.") are translated into command-line options. For example, the following environment variable adds the parameter `-Xrs` to the list of arguments:

    - On Windows&trade; systems:

            set IBM_NOSIGHANDLER=<non_null_string>

    - On AIX&reg;, Linux&reg;, macOS&reg;, and z/OS&reg; systems:

            export IBM_NOSIGHANDLER=<non_null_string>

4.  The `OPENJ9_JAVA_OPTIONS` environment variable. You can set command-line options using this environment variable. The options that you specify with this environment variable are added to the command line when a VM starts in that environment. The environment variable can contain multiple blank-delimited argument strings, but must not contain comments. For example:

    - On Windows systems:

            set OPENJ9_JAVA_OPTIONS=-Dmysysprop1=tcpip -Dmysysprop2=wait -Xdisablejavadump

    - On AIX, Linux, macOS, and z/OS systems:

            export OPENJ9_JAVA_OPTIONS="-Dmysysprop1=tcpip -Dmysysprop2=wait -Xdisablejavadump"

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The environment variable `JAVA_TOOL_OPTIONS` is equivalent to `OPENJ9_JAVA_OPTIONS` and is available for compatibility with JVMTI. The equivalent `IBM_JAVA_OPTIONS` environment variable is deprecated and will be removed in a future release.

5.  Options that are specified on the command line. For example:

        java -Dmysysprop1=tcpip -Dmysysprop2=wait -Xdisablejavadump MyJavaClass

    The Java launcher adds some automatically generated arguments to this list, such as the names of the main class.

6.  The `_JAVA_OPTIONS` environment variable. You can override previous options using this environment variable. The options that you specify with this environment variable are added to the end of the command line when a VM starts in that environment. The environment variable can contain multiple blank-delimited argument strings, but must not contain comments. For example:

    - On Windows systems:

            set _JAVA_OPTIONS=-Dmysysprop1=tcpip -Dmysysprop2=wait -Xdisablejavadump

    - On AIX, Linux, macOS, and z/OS systems:

            export _JAVA_OPTIONS="-Dmysysprop1=tcpip -Dmysysprop2=wait -Xdisablejavadump"

You can also use the `-Xoptionsfile` parameter to specify VM options. This parameter can be used on the command line, or as part of the `OPENJ9_JAVA_OPTIONS` environment variable. The contents of an option file are expanded in place during startup. For more information about the structure and contents of this type of file, see [-Xoptionsfile](xoptionsfile.md#xoptionsfile "Specifies a file that contains VM options and definitions. The contents of the options file are recorded in the ENVINFO section of a Java dump.").

To troubleshoot startup problems, you can check which options are used by the OpenJ9 VM. Append the following command-line option, and inspect the Java core file that is generated:

    -Xdump:java:events=vmstart

Here is an extract from a Java core file that shows the options that are used:

        2CIUSERARG               -Xdump:java:file=/home/test_javacore.txt,events=vmstop
        2CIUSERARG               -Dtest.cmdlineOption=1
        2CIUSERARG               -XXallowvmshutdown:true
        2CIUSERARG               -Xoptionsfile=test1.test_options_file

<!-- ==== END OF TOPIC ==== cmdline_specifying.md ==== -->
