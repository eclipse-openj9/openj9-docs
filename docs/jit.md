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

# The JIT compiler

The Just-In-Time (JIT) compiler is a key component of the Eclipse OpenJ9&trade; VM that improves the performance of Java applications by compiling platform-neutral Java bytecode into native machine code at run time. Without the JIT, the VM has to interpret the bytecodes itself - a process that requires extra CPU and memory.

The JIT compiler doesn't compile every method that gets called because thousands of methods can be called at startup. Instead, OpenJ9 records the number of times a method is called. When the count reaches a pre-defined *invocation threshold*, JIT compilation is triggered. Once a method has been compiled by the JIT, the VM can call the compiled method rather than interpreting it.

## Optimization levels

The JIT compiler can compile a method at different optimization levels: **cold**, **warm**, **hot**, **very hot (with profiling)**, or **scorching**. The hotter the optimization level, the better the expected performance, but the higher the cost in terms of CPU and memory.

- **cold** is used during startup processing for large applications where the goal is to achieve the best compiled code speed for as many methods as possible.
- **warm** is the workhorse; after start-up, most methods are compiled when they reach the invocation threshold.

For higher optimization levels, the VM uses a sampling thread to identify methods that continue to take a lot of time. Methods that consume more than 1% are compiled at hot. Methods that consume more than 12.5% are scheduled for a scorching compilation. However, before that happens the methods are compiled at very hot with profiling to collect detailed profile data that is used by the scorching compilation.

The higher optimization levels use special techniques such as escape analysis and partial redundancy elimination, or loop through certain optimization sequences more times. Although these techniques use more CPU and memory, the improved performance that is delivered by the optimizations can make the tradeoff worthwhile.

## Troubleshooting

The JIT compiler is enabled by default to optimize performance. However, if you experience a problem running your application, temporarily turning off the JIT will tell you whether the JIT is at fault.

Because JIT starts at the same time as the VM, you can only modify JIT behavior at startup.

There are a number of ways to disable the JIT:

- Specify `-Djava.compiler=NONE` on the command line.
- Specify `-Xint` on the command line, which turns off the JIT and AOT compiler. To eliminate problems with one or the other you can turn these compilers off selectively with the `-Xnojit` and `-Xnoaot` options.
- Call the `java.lang.Compiler` API programmatically.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** `java.lang.Compiler` is deprecated for removal in Java SE 9.

If turning off the JIT solves your problem, you can investigate JIT operations in more detail by using a number of options to control behavior.

Turning on verbose logging with the `verbose` suboption causes the JIT to record all compiler operations. However, the log file can be difficult to read because there are so many complex operations occuring in rapid succession. Follow these steps to simplify operations, which helps you pinpoint the root cause:

Turn off multiple compilation threads

: The JIT compiler can use more than one compilation thread, which typically improves startup performance. The number of threads is determined by the VM, depending on the system configuration. You can turn off multiple threads by using the `-XcompilationThreads` option, which simplifies the output in the verbose log.

Lower the invocation threshold

: When the invocation count is set to `0`, the JIT compiles every method and your application will fail immediately when the method causing the problem is reached. You can alter the threshold with the `count` suboption.

Turn off inlining

: Inlining is a complex process that generates larger and more complex code. To eliminate errors caused by these operations, use the `disableInlining` suboption.

Decrease the optimization levels

: Use the `optlevel` suboption to gradually decrease the compiler optimization levels to see whether you can isolate the level at which your problem occurs.

More information about these suboptions and the command line syntax is covered in [-Xjit](xjit.md).


### Understanding JIT verbose logs

At first glance, a JIT verbose log can look very complex. To help you understand the log we'll look at JIT compiler operations when you run the `java -version` command.

The following option turns on verbose logging and directs output to a log file called `vlogfile`:

```
java -Xjit:verbose,vlog=vlogfile -version
```

The first section of the log includes lines that start with `#INFO:`, which provides information about the environment that the JIT is operating in. You can determine the version of the JIT and VM that you are using, and the type and number of processors that the JIT has access to.

```
#INFO:  _______________________________________
#INFO:  Version Information:
#INFO:       JIT Level  - e24e8aa9
#INFO:       JVM Level  - 20180315_120
#INFO:       GC Level   - e24e8aa9
#INFO:  
#INFO:  Processor Information:
#INFO:       Platform Info:X86 Intel P6
#INFO:       Vendor:GenuineIntel
#INFO:       numProc=1
#INFO:  
#INFO:  _______________________________________
#INFO:  AOT
#INFO:  options specified:
#INFO:       samplingFrequency=2
#INFO:  
#INFO:  options in effect:
#INFO:       verbose=1
#INFO:       vlog=vlogfile
#INFO:       compressedRefs shiftAmount=0
#INFO:       compressedRefs isLowMemHeap=1
#INFO:  _______________________________________
#INFO:  JIT
#INFO:  options specified:
#INFO:       verbose,vlog=vlogfile
#INFO:  
#INFO:  options in effect:
#INFO:       verbose=1
#INFO:       vlog=vlogfile
#INFO:       compressedRefs shiftAmount=0
#INFO:       compressedRefs isLowMemHeap=1
#INFO:  StartTime: Apr 23 09:49:10 2018
#INFO:  Free Physical Memory: 996188 KB
#INFO:  CPU entitlement = 100.00
```

This section also shows the AOT and JIT options that are in force. The last few lines detail the start time of the compilation activity, how much free physical memory is available to the process, and the CPU entitlement.

The information section is followed by a sequence of lines that describe the methods that are being compiled, as well as other events significant to the operation of the JIT compiler.

Here is a typical line from the verbose log:

```
+ (cold) sun/reflect/Reflection.getCallerClass()Ljava/lang/Class; @ 00007FCACED1303C-00007FCACED13182 OrdinaryMethod - Q_SZ=0 Q_SZI=0 QW=1 j9m=00000000011E7EA8 bcsz=2 JNI compThread=0 CpuLoad=2%(2%avg) JvmCpu=0%
```

In this example:

- The method compiled is `sun/reflect/Reflection.getCallerClass()Ljava/lang/Class`.
- The `+` indicates that this method is successfully compiled. Failed compilations are marked by a `!`.
- `(cold)` tells you the optimization level that was applied. Other examples might be `(warm)` or `(scorching)`.
- `00007FCACED1303C-00007FCACED13182` is the code range where the compiled code was generated.
- `Q` values provide information about the state of the compilation queues when the compilation occurred.
- `bcsz` shows the bytecode size. In this case it is small because this is a native method, so the JIT is simply providing an accelerated JNI transition into the native `getCallerClass` method.

Each line of output represents a method that is compiled.

The following example requests information about the performance of JIT compiler threads, with output written to `vlogfile`.

```
java -Xjit:verbose={compilePerformance},vlog=vlogfile -version
```
The output generated by using this command adds the values `time` and `mem` into each line, as shown in the following example:

```
+ (cold) java/lang/System.getEncoding(I)Ljava/lang/String; @ 00007F29183A921C-00007F29183A936D OrdinaryMethod - Q_SZ=0 Q_SZI=0 QW=1 j9m=0000000000F13A70 bcsz=3 JNI time=311us mem=[region=704 system=16384]KB compThread=0 CpuLoad=2%(2%avg) JvmCpu=0%
```

- time=311us reflects the amount of time taken to do the compilation.
- mem=[region=704 system=16384]KB reflects the amount of memory that was allocated during the compilation.

The following example can be used to create verbose output that includes lines to show when compilation for a method starts and ends, and any methods that are inlined during the compilation.

```
java '-Xjit:verbose={compileStart|compileEnd|inlining},count=5,vlog=vlogfile' -XcompilationThreads1 -version
```
:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The suboptions `count` and `-XcompilationThreads1` are included only to simplify the output for this example and are not recommended for production.

The following section is taken from the output and describes the compilation and inlining of one method `java/lang/String.equals`:

```
(warm) Compiling java/lang/String.equals(Ljava/lang/Object;)Z  OrdinaryMethod j9m=0000000001300B30 t=90 compThread=0 memLimit=262144 KB freePhysicalMemory=969 MB
#INL: 7 methods inlined into 4dce72bd java/lang/String.equals(Ljava/lang/Object;)Z @ 00007F53190A3E40
#INL: #0: 4dce72bd #-1 inlined 4dce72bd@22 -> 81670d20 bcsz=37 java/lang/String.lengthInternal()I
#INL: #1: 4dce72bd #-1 inlined 4dce72bd@28 -> 81670d20 bcsz=37 java/lang/String.lengthInternal()I
#INL: #2: 4dce72bd #-1 inlined 4dce72bd@104 -> bf62dcaf bcsz=182 java/lang/String.regionMatchesInternal(Ljava/lang/String;Ljava/lang/String;[C[CIII)Z
#INL: #3: 4dce72bd #2 inlined bf62dcaf@121 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
#INL: #4: 4dce72bd #2 inlined bf62dcaf@131 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
#INL: #5: 4dce72bd #2 inlined bf62dcaf@156 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
#INL: #6: 4dce72bd #2 inlined bf62dcaf@166 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
#INL: 4dce72bd called 4dce72bd@120 -> f734b49c bcsz=233 java/lang/String.deduplicateStrings(Ljava/lang/String;Ljava/lang/String;)V
#INL: 4dce72bd coldCalled 4dce72bd@104 -> bf62dcaf bcsz=182 java/lang/String.regionMatchesInternal(Ljava/lang/String;Ljava/lang/String;[C[CIII)Z
#INL: 4dce72bd coldCalled 4dce72bd@104 -> bf62dcaf bcsz=182 java/lang/String.regionMatchesInternal(Ljava/lang/String;Ljava/lang/String;[C[CIII)Z
+ (warm) java/lang/String.equals(Ljava/lang/Object;)Z @ 00007F53190A3E40-00007F53190A40D0 OrdinaryMethod - Q_SZ=277 Q_SZI=277 QW=1667 j9m=0000000001300B30 bcsz=127 GCR compThread=0 CpuLoad=2%(2%avg) JvmCpu=0%
```

The first line is included as a result of setting the `compileStart` suboption and shows the start of the warm method compilation:

```
(warm) Compiling java/lang/String.equals(Ljava/lang/Object;)Z OrdinaryMethod j9m=0000000001300B30 t=90 compThread=0 memLimit=262144 KB freePhysicalMemory=969 MB
```

Similarly, the last line shows the successful compilation of this method, as denoted by the `+`:

```
+ (warm) java/lang/String.equals(Ljava/lang/Object;)Z @ 00007F53190A3E40-00007F53190A40D0 OrdinaryMethod - Q_SZ=277 Q_SZI=277 QW=1667 j9m=0000000001300B30 bcsz=127 GCR compThread=0 CpuLoad=2%(2%avg) JvmCpu=0%
```

The lines inbetween that start with `#INL` describe the inlining operations that took place. A total of 7 methods were inlined into `java/lang/String.equals`:

The first three methods (`#0`, `#1`, `#2`) are inlined into the top level method, denoted as `#-1`:

```
#INL: #0: 4dce72bd #-1 inlined 4dce72bd@22 -> 81670d20 bcsz=37 java/lang/String.lengthInternal()I
#INL: #1: 4dce72bd #-1 inlined 4dce72bd@28 -> 81670d20 bcsz=37 java/lang/String.lengthInternal()I
#INL: #2: 4dce72bd #-1 inlined 4dce72bd@104 -> bf62dcaf bcsz=182 java/lang/String.regionMatchesInternal(Ljava/lang/String;Ljava/lang/String;[C[CIII)Z
```

The next four methods (`#3`, `#4`, `#5`, `#6`) are inlined into the method denoted by `#2`.

```
#INL: #3: 4dce72bd #2 inlined bf62dcaf@121 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
#INL: #4: 4dce72bd #2 inlined bf62dcaf@131 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
#INL: #5: 4dce72bd #2 inlined bf62dcaf@156 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
#INL: #6: 4dce72bd #2 inlined bf62dcaf@166 -> bbb5af92 bcsz=39 java/lang/String.charAtInternal(I[C)C
```

Here's how to interpret the line for `#INL: #0:`:

The method is inlined into `4dce72bd`, where `4dce72bd` is an internal pointer that corresponds to this method (in this case, `java/lang/String.equals(Ljava/lang/Object;)Z`).
The value `@22` at the end of the pointer is a bytecode index, which describes the bytecode index of the call that is being inlined.
The call is `81670d20 bcsz=37 java/lang/String.lengthInternal()I`, which shows the corresponding internal pointer, bytecode size (bcsz) and the name of the method that got inlined.
Going through the `#INL` output line by line then:

```
java/lang/String.lengthInternal()I got inlined into its caller 4dce72bd at bytecode index @22.
java/lang/String.lengthInternal()I also got inlined into its caller 4dce72bd at bytecode index @28.
java/lang/String.regionMatchesInternal(...) got inlined at call reference 4dce72bd at bytecode index @104.
```

Then 4 distinct calls to `java/lang/String.charAtInternal(I[C)C` were also inlined into `java/lang/String.regionMatchesInternal(...)` :

```
#3 at bytecode index @121 of regionMatchesInternal
#4 at bytecode index @131 of regionMatchesInternal
#5 at bytecode index @156 of regionMatchesInternal
#6 at bytecode index @166 of regionMatchesInternal
```

These were all the calls that the inliner decided to inline into the method being compiled. There is some additional output that describes calls to methods that weren't inlined:

```
#INL: 4dce72bd called 4dce72bd@120 -> f734b49c bcsz=233 java/lang/String.deduplicateStrings(Ljava/lang/String;Ljava/lang/String;)V
#INL: 4dce72bd coldCalled 4dce72bd@104 -> bf62dcaf bcsz=182 java/lang/String.regionMatchesInternal(Ljava/lang/String;Ljava/lang/String;[C[CIII)Z
#INL: 4dce72bd coldCalled 4dce72bd@104 -> bf62dcaf bcsz=182 java/lang/String.regionMatchesInternal(Ljava/lang/String;Ljava/lang/String;[C[CIII)Z
```

While the output does not specifically say why these methods were not inlined, the relatively larger bytecode size (`bcsz=233`) probably prevented the first method from being inlined. It's possible that, at a higher optimization level than cold, this `deduplicateStrings` method may get inlined. The `coldCalled` label on the last two lines, however, indicate that these calls are located in a part of the method that has not ever been executed, so the JIT decided that inlining those last two methods will probably increase compile time without much promise that it will improve performance.

By reading the log in this way you can reconstruct the tree of inlines that are taking place as the compilation proceeds. You can see which methods are being inlined and which methods are not being inlined.

## See also

- [Diagnosing a JIT or AOT problem](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/jit_pd_diagnose.html)



<!-- ==== END OF TOPIC ==== jit.md ==== -->
