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

# -Xjit / -Xnojit


Use this option to control the behavior of the JIT compiler.

Specifying `-Xjit` with no parameters, has no effect as the JIT compiler is enabled by default.

`-Xnojit` turns off the JIT compiler but does not affect the AOT compiler.

## Syntax

| Setting                                             | Action                  | Default                                                                            |
|-----------------------------------------------------|-------------------------|:----------------------------------------------------------------------------------:|
| `-Xjit`                                             | Enable JIT              | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">yes</span> |
| `-Xjit[:<parameter>=<value>{,<parameter>=<value>}]` | Enable JIT with options |                                                                                    |
| `-Xnojit`                                           | Disable JIT             |                                                                                    ||

## Parameters

These parameters can be used to modify the behavior of `-Xjit`:

| Parameter                           | Effect                                                                                  |
|-------------------------------------|-----------------------------------------------------------------------------------------|
| [`count`         ](#count         ) | Forces compilation of methods on first invocation.                                      |
| [`disableRMODE64`](#disablermode64) | Allows the JIT to allocate executable code caches above the 2 GB memory bar.            |
| [`enableGPU`     ](#enablegpu     ) | Allows the JIT to offload certain processing tasks to a graphics processing unit (GPU)  |
| [`exclude`       ](#exclude       ) | Excludes the specified method from compilation.                                         |
| [`<limitFile>`   ](#limitfile     ) | Compile methods that are listed in the limit file.                                      |
| [`optlevel`      ](#optlevel      ) | Forces the JIT compiler to compile all methods at a specific optimization level.        |
| [`verbose`       ](#verbose       ) | Reports information about the JIT and AOT compiler configuration and method compilation.|
| [`vlog`          ](#vlog          ) | Sends verbose output to a file.                                                         |

### `count`

        -Xjit:count=<n>

: where `<n>` is the number of times a method is called before it is compiled. For example, setting `count=0` forces the JIT compiler to compile everything on first execution, which is useful for problem determination.

### `disableRMODE64`

**(z/OS&reg; only)**

        -Xjit:disableRMODE64

: From z/OS V2R3, residency mode for 64-bit applications (RMODE64) is enabled by default. This feature allows the JIT to allocate executable code caches above the 2 GB memory bar, which is the default behavior. Use this option to turn off this JIT behavior.

### `enableGPU`

**(Windows (x86-64) or Linux (x86-64 and IBM POWER LE))**

        -Xjit:enableGPU

: Enables the JIT compiler to offload certain processing tasks to a graphics processing unit (GPU). The JIT determines which functions to offload based on performance heuristics. Systems must support NVIDIA Compute Unified Device Architecture (CUDA).  The JIT requires the CUDA Toolkit 7.5 and your GPU device must have a minimum compute capability of 3.0.

: To troubleshoot operations between the JIT compiler and the GPU, use `-Xjit:enableGPU={verbose}`, which provides output showing the processing tasks that are offloaded and their status. To send this output to a file (`output.txt`), run `-Xjit:enableGPU={verbose},vlog=output.txt` when you start your application.

### `exclude`

        -Xjit:exclude=<method>

: Excludes the specified method from compilation.

### `limitFile`

        -Xjit:limitFile=(<vlog_filename>, <m>, <n>)

: Compile only the methods that are listed on lines `<m>` to `<n>` in the specified limit file, where the limit file is a verbose log that you generated with the `-Xjit:verbose,vlog=<vlog_filename>` option. Methods that are not listed in the limit file and methods that are listed on lines outside the range are not compiled.

### `optlevel`

        -Xjit:optlevel=[noOpt|cold|warm|hot|veryHot|scorching]

: Forces the JIT compiler to compile all methods at a specific optimization level. Specifying `optlevel` might have an unexpected effect on performance, including reduced overall performance.

### `verbose`

        -Xjit:verbose

: Generates a JIT verbose log. The log provides a summary of which methods were compiled by the JIT and some of the compilation heurisic decisions that were taken while the JIT operates inside the OpenJ9 VM.

        -Xjit:verbose={compileStart}

: Prints out a line when the JIT is about to start compiling a method.

        -Xjit:verbose={compileEnd}

: Prints out a line when the JIT stops compiling a method.

        -Xjit:verbose={compilePerformance}

: Adds the values `time` (time taken to do the compilation) and `mem` (the amount of memory that was allocated during the compilation) into each line. This option includes the `compileStart` and `compileEnd` suboptions by default.

        -Xjit:verbose={disableInlining}

: Turns off inlining operations.

        -Xjit:verbose={inlining}

: Shows the methods that are inlined.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** Suboptions can be chained together by using a  `|` character. When used, you must enclose the full option name in single quotes (') to avoid the shell misinterpreting these characters as pipe commands. For example:

```
java '-Xjit:verbose={compileStart|compileEnd|inlining}' -version
```

### `vlog`
        -Xjit:vlog=<vlog_filename>

: Sends verbose output to a file, of the format `<vlog_filename>.<date>.<time>.<JVM_process_ID>`, which is created in your current directory. Running the command multiple times produces multiple distinct versions of this file. If you do not specify this parameter, the output is sent to the standard error output stream (STDERR). This type of log file can be used with the `limitFile` suboption to target the compilation of specific methods.

## Examples

### Generating a JIT verbose log

The following example requests a JIT verbose log of the `java -version` command:

```
java -Xjit:verbose,vlog=vlogfile -version
```

### Analyzing JIT performance

The following example requests information about the performance of JIT compiler threads, with output written to `vlogfile`.

```
java -Xjit:verbose={compilePerformance},vlog=vlogfile -version
```
The output generated by using this command adds the following information to compilation entry:

- the amount of time taken to do the compilation.
- the amount of memory that was allocated during the compilation.

### Analyzing inlining operations

The following example generates output that contains performance data and inlining operations. The suboptions `count` and `-XcompilationThreads1` are used only to simplify the output. These options are not recommended for production because performance will be affected.

```
java '-Xjit:verbose={compileStart|compileEnd|inlining},count=5,vlog=vlogfile' -XcompilationThreads1 -version
```

<!--Include this line when the JIT section is added to the docs -> To learn how to read verbose logs, see [Troubleshooting](#jit_pd.md).-->


## See also

- [Diagnosing a JIT or AOT problem](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/jit_pd_diagnose.html)


<!-- ==== END OF TOPIC ==== xjit.md ==== -->
<!-- ==== END OF TOPIC ==== xnojit.md ==== -->
