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

# -Xaot / -Xnoaot

Use this option to control the behavior of the ahead-of-time (AOT) compiler. When the AOT compiler is active, the compiler selects the methods to be AOT compiled with the primary goal of improving startup time.

AOT compilation allows the compilation of Java&trade; classes into native code for subsequent executions of the same program. The AOT compiler works with the class data sharing framework.

The AOT compiler generates native code dynamically while an application runs and caches any generated AOT code in the shared data cache. Subsequent VMs that execute the method can load and use the AOT code from the shared data cache without incurring the performance decrease experienced with JIT-compiled native code.


## Performance

Because AOT code must persist over different program executions, AOT-generated code does not perform as well as JIT-generated code. AOT code usually performs better than interpreted code.

In a VM without an AOT compiler or with the AOT compiler disabled, the JIT compiler selectively compiles frequently used methods into optimized native code. There is a time cost associated with compiling methods because the JIT compiler operates while the application is running. Because methods begin by being interpreted and most JIT compilations occur during startup, startup times can be increased.

Startup performance can be improved by using the shared AOT code to provide native code without compiling. There is a small time cost to load the AOT code for a method from the shared data cache and bind it into a running program. The time cost is low compared to the time it takes the JIT compiler to compile that method.


## Default behavior

The AOT compiler is enabled by default, but is only active for classes that are found in the shared classes cache. See [Introduction to class data sharing](shrc.md) for information about the shared classes cache, how class sharing is enabled, and what options are available to modify class sharing behavior.

## Syntax

| Setting       | Action       | Default   |
|---------------|--------------|:---------:|
|`-Xaot`        | Enable AOT   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|`-Xaot:<parameter>[=<value>]` (See **Note**) | Enable AOT with modifications |           |
|`-Xnoaot`      | Disable AOT  |           |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** You can concatenate several parameters in a comma-separated list, for example: `-Xaot:<parameter1>[=<value1>], <parameter2>[=<value2>]`.

Specify the `-Xaot` option only once. By default, if you specify this option multiple times, only the last option takes effect.
You can use the [`-XX:+MergeCompilerOptions`](xxmergecompileroptions.md) option to merge all the existing instances of the `-Xaot` options logically.


## Parameters for `-Xaot`

| Parameter                        | Effect                                                                                    |
|----------------------------------|-------------------------------------------------------------------------------------------|
| [`verbose`      ](#verbose      )| Reports information about the AOT and JIT compiler configuration and method compilation.  |
| [`count`        ](#count        )| Specifies the number of times a method is called before it is compiled.                   |
| [`exclude`      ](#exclude      )| Excludes specified methods when AOT code is compiled.                                     |
| [`limit`        ](#limit        )| Includes specified methods when AOT code is compiled.                                     |
| [`limitFile`    ](#limitfile    )| Compiles only the methods listed in the specified limit file.                             |
| [`loadExclude`  ](#loadexclude  )| Excludes specified methods when AOT code is loaded.                                       |
| [`loadLimit`    ](#loadlimit    )| Includes specified methods when AOT code is loaded.                                       |
| [`loadLimitFile`](#loadlimitfile)| Loads only the methods listed in the specified limit file.                                |



### `verbose`

        -Xaot:verbose

: Reports information about the AOT and JIT compiler configuration and method compilation.


### `count`

        -Xaot:count=<n>

: Specifies the number of times, `<n>`, a method is called before it is compiled or loaded from an existing shared classes cache. Setting `-Xaot:count=0` forces the AOT compiler to compile everything on first execution, which is useful for problem determination.

### `exclude`

        -Xaot:exclude={<method_name>}

: Excludes a Java method when AOT code is compiled from the shared classes cache. Use this option if the method causes the program to fail.

    `<method_name>` is the method or methods that are to be excluded; the wildcard `*` may be used. Specify as much of the full package, class and method as necessary.

    For example, `-Xaot:exclude={test/sample/MyClass.testMethod()V}` excludes the single method specified.  
    However, `-Xaot:exclude={test/sample/MyClass.testMethod()*}` excludes the method regardless of return type.  
    Similarly, `-Xaot:exclude={*}` excludes _all_ methods.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `exclude` has the same effect regardless of whether it's specified on [`-Xjit`](xjit.md) or `-Xaot`. In consequence, if you specify `-Xaot:exclude`, JIT compilation is also prevented and the methods specified are always interpreted.


### `limit`

        -Xaot:limit={<method_name>}

: Only the Java methods specified are included when AOT code is compiled from the shared classes cache. `<method_name>` is the method or methods that are to be included (the wildcard `*` may be used, see [`-Xaot:exclude`](#exclude) for details).

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `limit` has the same effect regardless of whether it's specified on [`-Xjit`](xjit.md) or `-Xaot`. In consequence, if you specify `-Xaot:limit`, JIT compilation is also restricted to those methods specified; other methods are always interpreted.


### `limitFile`

        -Xaot:limitFile=(<filename>,<m>,<n>)

: Compiles or loads only the methods listed on lines `<m>` to, and including, `<n>` in the specified limit file, `<filename>`. Methods not listed in the limit file and methods listed on lines outside the range are not compiled or loaded.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `limitFile` has the same effect regardless of whether it's specified on [`-Xjit`](xjit.md) or `-Xaot`. In consequence, if you specify `-Xaot:limitFile`, JIT compilation is also restricted to those methods specified; other methods are always interpreted.


### `loadExclude`

        -Xaot:loadExclude={<method_name>}

: Excludes the specified Java methods when AOT code is loaded from the shared classes cache. In consequence, the compiler does a JIT compilation on those methods instead.

    `<method_name>` is the method or methods that are to be excluded (the wildcard `*` may be used, see [`-Xaot:exclude`](#exclude) for details). This option does _not_ prevent the method from being compiled.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `loadExclude` can only be specified on `-Xaot`; it does not have an equivalent on [`-Xjit`](xjit.md).


### `loadLimit`

        -Xaot:loadLimit={<method_name>}

: Only the Java methods specified are included when AOT code is loaded from the shared classes cache. In consequence, the compiler does a JIT compilation on other methods instead.

    `<method_name>` is the method or methods that are to be included (the wildcard `*` may be used; see [`-Xaot:exclude`](#exclude) for details).

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `loadLimit` can only be specified on `-Xaot`; it does not have an equivalent on [`-Xjit`](xjit.md). This option filters what AOT code the compiler is allowed to load from the shared classes cache.

### `loadLimitFile`

        -Xaot:loadLimitFile=(<filename>,<m>,<n>)

: Loads only the methods listed on lines `<m>` to, and including, `<n>` in the specified limit file. In consequence, the compiler does a JIT compilation on other methods instead.

    `<filename>`. Methods not listed in the limit file and methods listed on lines outside the range are not loaded.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `loadLimitFile` can only be specified on `-Xaot`; it does not have an equivalent on [`-Xjit`](xjit.md).


## See also

- [Introduction to class data sharing](shrc.md)
- [`-Xquickstart`](xquickstart.md)
- [`-Xshareclasses`](xshareclasses.md)
- [`-Xjit`](xjit.md)



<!-- ==== END OF TOPIC ==== xaot.md ==== -->
<!-- ==== END OF TOPIC ==== xnoaot.md ==== -->
