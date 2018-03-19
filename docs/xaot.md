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

# -Xaot / -Xnoaot

Use this option to control the behavior of the ahead-of-time (AOT) compiler.

AOT compilation allows the compilation of Java<sup>&trade;</sup> classes into native code for subsequent executions of the same program. The AOT compiler works with the class data sharing framework.

The AOT compiler generates native code dynamically while an application runs and caches any generated AOT code in the shared data cache. Subsequent VMs that execute the method can load and use the AOT code from the shared data cache without incurring the performance decrease experienced with JIT-compiled native code.

## Performance

Because AOT code must persist over different program executions, AOT-generated code does not perform as well as JIT-generated code. AOT code usually performs better than interpreted code.

In a VM without an AOT compiler or with the AOT compiler disabled, the JIT compiler selectively compiles frequently used methods into optimized native code. There is a time cost associated with compiling methods because the JIT compiler operates while the application is running. Because methods begin by being interpreted and most JIT compilations occur during startup, startup times can be increased.

Startup performance can be improved by using the shared AOT code to provide native code without compiling. There is a small time cost to load the AOT code for a method from the shared data cache and bind it into a running program. The time cost is low compared to the time it takes the JIT compiler to compile that method.

## Default behavior

The AOT compiler is enabled by default, but is only active when [shared classes](xshareclasses.md) are enabled. By default, shared classes are disabled so that no AOT activity occurs.

When the AOT compiler is active, the compiler selects the methods to be AOT compiled with the primary goal of improving startup time.

## Syntax

| Setting      | Action      | Default                                                                            |
|--------------|-------------|:----------------------------------------------------------------------------------:|
|`-Xaot`       | Enable AOT  | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Default</span> |
|`-Xaot:<parameter>[=<value>]{,<parameter>[=<value>]}` | Enable AOT with modifications |                          |
|`-Xnoaot`     | Disable AOT |                                                                                    |


## Parameters for `-Xaot`

: <i class="fa fa-pencil-square-o" aria-hidden="true"></i><span class="sr-only">Note</span> **Note:** Although the AOT compiler is enabled by default, it is not active unless shared classes are enabled. Using this option on its own therefore has no effect. Use the [-Xshareclasses](xshareclasses.md) option to enable shared classes.

    You can concatenate several parameters by using commas.

| Parameter                        |  Effect                                                                                                        |
|----------------------------------|----------------------------------------------------------------------------------------------------------------|
| [`count`](#count)                | The number of times a method is called before it is compiled or loaded from an existing shared class cache.    |
| [`exclude`](#exclude)            | The method you want to exclude when AOT code is compiled or loaded from the shared classes cache.              |
| [`limitFile`](#limitfile)        | Compile or load only the methods listed in the specified limit file.                                           |
| [`loadExclude`](#loadexclude)    | Do not load specified methods.                                                                                 |
| [`loadLimit`](#loadlimit)        | Load specified methods only.                                                                                   |
| [`loadLimitFile`](#loadlimitfile)| Load only the methods listed in the specified limit file).                                                     |
| [`verbose`](#verbose)            | Reports information about the AOT and JIT compiler configuration and method compilation.                       |

### `count`

        -Xaot:count=<n>

: where `<n>` is the number of times a method is called before it is compiled or loaded from an existing shared class cache. For example, setting `-Xaot:count=0` forces the AOT compiler to compile everything on first execution.

### `exclude`

        -Xaot:exclude=<method>

: where `<method>` is the Java method you want to exclude when AOT code is compiled or loaded from the shared classes cache.

    Use this option if the method causes the program to fail.

### `limitFile`

        -Xaot:limitFile=(<filename>,<m>,<n>)

: Compile or load only the methods listed on lines `<m>` to `<n>` in the specified limit file (`<filename>`). Methods not listed in the limit file and methods listed on lines outside the range are not compiled or loaded.

### `loadExclude`

        -Xaot:loadExclude=<method_prefix>

: Do not load methods beginning with `<method_prefix>`.

### `loadLimit`

        -Xaot:loadLimit=<method_prefix>

: Load methods beginning with `<method_prefix>` only.

### `loadLimitFile`

        -Xaot:loadLimitFile=(<filename>,<m>,<n>)

: Load only the methods listed on lines `<m>` to `<n>` in the specified limit file (`<filename>`). Methods not listed in the limit file and methods listed on lines outside the range are not loaded.

### `verbose`

        -Xaot:verbose

: Reports information about the AOT and JIT compiler configuration and method compilation.


## See also

- [-Xquickstart](xquickstart.md)



<!-- ==== END OF TOPIC ==== xaot.md ==== -->
<!-- ==== END OF TOPIC ==== xnoaot.md ==== -->
