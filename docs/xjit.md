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

# -Xjit / -Xnojit


Use this option to control the behavior of the JIT compiler.

Specifying `-Xjit` with no parameters, has no effect as the JIT compiler is enabled by default.

`-Xnojit` (it takes no parameters) turns off the JIT compiler. This option does not affect the AOT compiler.

## Syntax

| Setting                                             | Action                  | Default                                                                            |
|-----------------------------------------------------|-------------------------|:----------------------------------------------------------------------------------:|
| `-Xjit`                                             | Enable JIT              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Default</span> |
| `-Xjit[:<parameter>=<value>{,<parameter>=<value>}]` | Enable JIT with options |                                                                                    |
| `-Xnojit`                                           | Disable JIT             |                                                                                    |

## Parameters

These parameters can be used to modify the behavior of `-Xjit`:

| Parameter                           | Effect                                                                                  |
|-------------------------------------|-----------------------------------------------------------------------------------------|
| [`count`         ](#count         ) | Forces compilation of methods on first invocation.                                      |
| [`disableRMODE64`](#disablermode64) | Allows the JIT to allocate executable code caches above the 2 GB memory bar.            |
| [`exclude`       ](#exclude       ) | Excludes the specified method from compilation.                                         |
| [`<filename>`    ](#filename      ) | Compile methods that are listed in the limit file.                                      |
| [`optlevel`      ](#optlevel      ) | Forces the JIT compiler to compile all methods at a specific optimization level.        |
| [`verbose`       ](#verbose       ) | Reports information about the JIT and AOT compiler configuration and method compilation.|
| [`vlog`          ](#vlog          ) | Sends verbose output to a file.                                                         |

### `count`

        -Xjit:count=<n>

: where `<n>` is the number of times a method is called before it is compiled. For example, setting `count=0` forces the JIT compiler to compile everything on first execution.

### `disableRMODE64`

**(z/OS<sup>&reg;</sup> only)**

        -Xjit:disableRMODE64

: From z/OS V2R3, residency mode for 64-bit applications (RMODE64) is enabled by default. This feature allows the JIT to allocate executable code caches above the 2 GB memory bar, which is the default behavior. Use this option to turn off this JIT behavior.

### `exclude`

        -Xjit:exclude=<method>

: Excludes the specified method from compilation.

### `<filename>`

        -Xjit:(<filename>, <m>, <n>)

: Compile only the methods that are listed on lines `<m>` to `<n>` in the specified limit file. Methods that are not listed in the limit file and methods that are listed on lines outside the range are not compiled.

### `optlevel`

        -Xjit:optlevel=[noOpt|cold|warm|hot|veryHot|scorching]

: Forces the JIT compiler to compile all methods at a specific optimization level. Specifying `optlevel` might have an unexpected effect on performance, including reduced overall performance.

### `verbose`

        -Xjit:verbose
        -Xjit:verbose=compileStart
        -Xjit:verbose=compileEnd
        -Xjit:verbose=compileStart,compileEnd

: Reports information about the JIT and AOT compiler configuration and method compilation.

    `compileStart` and `compileEnd` reports when the JIT starts to compile a method, and when it ends.

### `vlog`
        -Xjit:vlog=<filename>

:   Sends verbose output to a file. If you do not specify this parameter, the output is sent to the standard error output stream (STDERR).

## See also

- <i class="fa fa-external-link" aria-hidden="true"></i> [Diagnosing a JIT or AOT problem](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/jit_pd_diagnose.html)


<!-- ==== END OF TOPIC ==== xjit.md ==== -->
<!-- ==== END OF TOPIC ==== xnojit.md ==== -->
