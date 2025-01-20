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

# Signal handling

Signals used by the Eclipse OpenJ9&trade; VM include the following types:

- Exceptions (Exc): Raised synchronously by the operating system whenever an unrecoverable condition occurs (not applicable on Windows&trade; systems).
- Errors (Err): Raised by the OpenJ9 VM when an unrecoverable condition occurs.
- Interrupts (Int): Raised asynchronously from outside a VM process to request a VM exit.
- Controls (Con): Other signals that are used by the VM for control purposes.

For exceptions and errors, if the VM cannot handle the condition and recover, dumps are produced and a controlled shut down sequence takes place. Interrupts also cause the VM to enter a controlled shut down sequence, but without generating dumps. The shutdown sequence is equivalent to calling `System.exit()`, which results in the following steps:

1. The VM calls the equivalent application signal handler.
2. The VM calls any hooks installed by the application (unexpected shutdown hooks for exceptions or errors, shutdown or exit hooks for interrupts).
3. The VM does any final clean up.

Control signals are used for internal control purposes and do not cause the VM to end.

The VM takes control of any signals for Java&trade; threads. For non-Java threads, the VM passes control to an application handler, if one is installed. If the application does not install a signal handler, or signal chaining is turned off, the signal is either ignored or the default action is taken. Signal chaining is controlled by the [`-Xsigchain` / `-Xnosigchain`](xsigchain.md) command-line option.

The signals relevant to each platform are detailed in the sections that follow.

When reading each table, a number supplied after the signal name is the standard numerical value for that signal.

Note that certain signals on VM threads cause OpenJ9 to shutdown. An application signal handler should not attempt to recover from these signals unless it no longer requires the VM.

## Signals on Linux

| Signal            | Type | Description                                                        | Option to disable signal  |
| ------------------|------|--------------------------------------------------------------------|-----------------------|
| `SIGBUS (7)`      | Exc  | Incorrect memory access (data misalignment)                        | `-Xrs` or `-Xrs:sync` |
| `SIGSEGV (11)`    | Exc  | Incorrect memory access (write to inaccessible area)               | `-Xrs` or `-Xrs:sync` |
| `SIGILL (4)`      | Exc  | Illegal instruction (attempt to call unknown machine instruction)  | `-Xrs` or `-Xrs:sync` |
| `SIGFPE (8)`      | Exc  | Floating point exception (divide by zero)                          | `-Xrs` or `-Xrs:sync` |
| `SIGABRT (6)`     | Err  | Abnormal termination, raised by the VM when a VM fault is detected | `-Xrs` or `-Xrs:sync` |
| `SIGINT (2)`      | Int  | Interactive attention (CTRL-C), VM exits normally                  | `-Xrs` |
| `SIGTERM (15)`    | Int  | Termination request, VM exits normally                             | `-Xrs`  |
| `SIGHUP (1)`      | Int  | Hang up, VM exits normally                                         | `-Xrs` |
| `SIGUSR2 (12)`    | Int  | User-defined signal for triggering a dump agent                    | `-Xrs` |
| `SIGQUIT (3)`     | Con  | Quit signal from a terminal, which triggers a Java dump by default | `-Xrs` |
| `SIGTRAP (5)`     | Con  | Used by the JIT                                                    | `-Xrs` or `-Xrs:sync` |
| `SIGRTMIN (34)`   | Con  | Used by the VM for thread introspection                       | - |
| `SIGRTMIN +1 (35)`| Con  | Used by the VM for Runtime Instrumentation (Linux for IBM Z&reg; systems only)| - |
| `SIGRTMAX -2 (62)`| Con  | Used by the `java.net` class library code                                         | - |
| `SIGCHLD (17)`    | Con  | Used by the `java.lang.Process` implementation                     | - |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- The use of `SIGRTMIN` is configurable with the [`-Xdump:suspendwith=<num>`](xdump.md) option.
- The handling of `SIGABRT` is configurable with the [`-XX:[+|-]HandleSIGABRT`](xxhandlesigabrt.md) option.
- The handling of `SIGUSR2` is configurable with the [`-XX:[+|-]HandleSIGUSR2`](xxhandlesigusr2.md) option.

## Signals on macOS

| Signal            | Type | Description                                                        | Option to disable signal  |
| ------------------|------|--------------------------------------------------------------------|-----------------------|
| `SIGBUS (10)`     | Exc  | Incorrect memory access (data misalignment)                        | `-Xrs` or `-Xrs:sync` |
| `SIGSEGV (11)`    | Exc  | Incorrect memory access (write to inaccessible area)               | `-Xrs` or `-Xrs:sync` |
| `SIGILL (4)`      | Exc  | Illegal instruction (attempt to call unknown machine instruction)) | `-Xrs` or `-Xrs:sync` |
| `SIGFPE (8)`      | Exc  | Floating point exception (divide by zero)                          | `-Xrs` or `-Xrs:sync` |
| `SIGABRT (6)`     | Err  | Abnormal termination, raised by the VM when a VM fault is detected | `-Xrs` or `-Xrs:sync` |
| `SIGINT (2)`      | Int  | Interactive attention (CTRL-C), VM exits normally                  | `-Xrs` |
| `SIGTERM (15)`    | Int  | Termination request, VM exits normally                             | `-Xrs` |
| `SIGHUP (1)`      | Int  | Hang up, VM exits normally                                         | `-Xrs` |
| `SIGUSR2 (31)`    | Int  | User-defined signal for triggering a dump agent                    | `-Xrs` |
| `SIGQUIT (3)`     | Con  | Quit signal from a terminal, which triggers a Java dump by default | `-Xrs` |
| `SIGTRAP (5)`     | Con  | Used by the JIT                                                    | `-Xrs` or `-Xrs:sync` |
| `SIGCHLD (20)`    | Con  | Used by the `java.lang.Process` implementation                     | - |
| `SIGUSR1 (30)`    | Con  | Used by the VM for thread introspection                                       | - |
| `SIGIO (23)`      | Con  | Used by the `java.net` class library code                                        | - |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:**

- The handling of `SIGABRT` is configurable with the [`-XX:[+|-]HandleSIGABRT`](xxhandlesigabrt.md) option.
- The handling of `SIGUSR2` is configurable with the [`-XX:[+|-]HandleSIGUSR2`](xxhandlesigusr2.md) option.

## Signals on Windows

| Signal            | Type | Description                                                            | Option to disable signal   |
| ------------------|------|------------------------------------------------------------------------|-----------------------|
| `SIGINT (2)`      | Int  | Interactive attention (CTRL-C), VM exits normally                      | `-Xrs` |
| `SIGTERM (15)`    | Int  | Termination request, VM exits normally                                 | `-Xrs` |
| `SIGBREAK`        | Con  | A break signal from a terminal. By default, this triggers a Java dump  | `-Xrs` |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

The following mechanisms are used by OpenJ9 for signal handling:

- structured exception handling (32-bit VM only)
- `AddVectoredExceptionHandler()` API (64-bit JVM only)
- `SetConsoleCtrlHandler()` applicable

All mechanisms can be disabled by using the `-Xrs` option. However, only structured exception handling and the use of the `AddVectoredExceptionHandler()` API can be disabled  by using the `-Xrs:sync` option. The option `-Xnosigchain`, which turns off signal handler chaining, is ignored on Windows systems.

## Signals on z/OS

| Signal            | Type | Description                                                        | Option to disable signal  |
| ------------------|------|--------------------------------------------------------------------|-----------------------|
| `SIGBUS (10)`     | Exc  | Incorrect memory access (data misalignment)                        | `-Xrs` or `-Xrs:sync` |
| `SIGSEGV (11)`    | Exc  | Incorrect memory access (write to inaccessible area)               | `-Xrs` or `-Xrs:sync` |
| `SIGILL (4)`      | Exc  | Illegal instruction (attempt to call unknown machine instruction)) | `-Xrs` or `-Xrs:sync` |
| `SIGFPE (8)`      | Exc  | Floating point exception (divide by zero)                          | `-Xrs` or `-Xrs:sync` |
| `SIGABRT (3)`     | Err  | Abnormal termination, raised by the VM when a VM fault is detected | `-Xrs` or `-Xrs:sync` |
| `SIGINT (2)`      | Int  | Interactive attention (CTRL-C), VM exits normally                  | `-Xrs` |
| `SIGTERM (15)`    | Int  | Termination request, VM exits normally                             | `-Xrs` |
| `SIGHUP (1)`      | Int  | Hang up, VM exits normally                                         | `-Xrs` |
| `SIGUSR2 (17)`    | Int  | User-defined signal for triggering a dump agent                    | `-Xrs` |
| `SIGQUIT (24)`    | Con  | Quit signal from a terminal, triggers a Java dump by default       | `-Xrs` |
| `SIGTRAP (26)`    | Con  | Used by the JIT                                                    | `-Xrs` or `-Xrs:sync` |
| `SIGCHLD (20)`    | Con  | Used by the `java.lang.Process` implementation                     | - |
| `SIGUSR1 (16)`    | Con  | Used by the `java.net` class library code                          | - |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:**

- The handling of `SIGABRT` is configurable with the [`-XX:[+|-]HandleSIGABRT`](xxhandlesigabrt.md) option.
- The handling of `SIGUSR2` is configurable with the [`-XX:[+|-]HandleSIGUSR2`](xxhandlesigusr2.md) option.

## Signals on AIX

| Signal            | Type | Description                                                        | Option to disable signal   |
| ------------------|------|--------------------------------------------------------------------|-----------------------|
| `SIGBUS (10)`     | Exc  | Incorrect memory access (data misalignment)                        | `-Xrs` or `-Xrs:sync` |
| `SIGSEGV (11)`    | Exc  | Incorrect memory access (write to inaccessible area)               | `-Xrs` or `-Xrs:sync` |
| `SIGILL (4)`      | Exc  | Illegal instruction (attempt to call unknown machine instruction)) | `-Xrs` or `-Xrs:sync` |
| `SIGFPE (8)`      | Exc  | Floating point exception (divide by zero)                          | `-Xrs` or `-Xrs:sync` |
| `SIGABRT (6)`     | Err  | Abnormal termination, raised by the VM when a VM fault is detected | `-Xrs` or `-Xrs:sync` |
| `SIGINT (2)`      | Int  | Interactive attention (CTRL-C), VM exits normally                  | `-Xrs` |
| `SIGTERM (15)`    | Int  | Termination request, VM exits normally                             | `-Xrs`  |
| `SIGHUP (1)`      | Int  | Hang up, VM exits normally                                         | `-Xrs` |
| `SIGUSR2 (31)`    | Int  | User-defined signal for triggering a dump agent                    | `-Xrs` |
| `SIGQUIT (3)`     | Con  | Triggers a Java dump by default                                    | `-Xrs` |
| `No Name (40)`    | Con  | Used by the VM for control purposes                                | `-Xrs` |
| `SIGRECONFIG (58)`| Con  | Reserved to detect changes to resources (CPUs, processing capacity, or physical memory)| `-Xrs` |
| `SIGTRAP (5)`     | Con  | Used by the JIT                                                    | `-Xrs` or `-Xrs:sync` |
| `SIGRTMIN (50)`   | Con  | Used by the VM for thread introspection                      | - |
| `SIGRTMAX -1 (56)`| Con  | Used by the `java.net` class library code                                         | - |
| `SIGCHLD (20)`    | Con  | Used by the `java.lang.Process` implementation                     | - |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- VM performance is affected if you install a signal handler for SIGTRAP (5) or SIGRECONFIG (58) because these signals are used for internal control purposes.
- If you want to generate floating point exceptions, use the following call in your code to generate a `SIGFPE` signal: `fp_trap( P_TRAP_SYNC)`. Although you can use the C compiler `-qflttrap` setting to generate `SIGTRAP` signals to trap floating point exceptions, this mechanism can affect the JIT compiler.
- The handling of `SIGABRT` is configurable with the [`-XX:[+|-]HandleSIGABRT`](xxhandlesigabrt.md) option.
- The handling of `SIGUSR2` is configurable with the [`-XX:[+|-]HandleSIGUSR2`](xxhandlesigusr2.md) option.

## Signal chaining

Signal chaining allows application code to interoperate with VM signal handling. By linking and loading a shared library, certain calls can be intercepted so that the application handlers do not replace the VM signal handlers already installed by the VM. Instead, the application handlers are chained behind the VM handlers. If signals that are raised do not target the VM, the application handlers take over. Signals that can be chained include `signal()`, `sigset()`, and `sigaction()`.

The following table shows the shared library that must be linked with the application that creates or embeds a VM, and the
command line syntax to use with the compiler, where available:

|Operating system        | Shared library | Method for linking |
|------------------------|----------------|--------------------|
| Linux&reg;, macOS&reg;, and z/OS&reg; | `libjsig.so`   | `gcc -L$JAVA_HOME/bin -ljsig -L$JAVA_HOME/lib/j9vm -ljvm <java_application>.c` |
| Windows                | `jsig.dll`     | Link the DLL with the application that creates or embeds a VM |
| AIX                    | `libjsig.so`   | `cc_r [-q64] <other_compile/link_parameter> -L<java_install_dir> -ljsig -L<java_install_dir>/lib/j9vm -ljvm <java_application>.c` |

In the absence of signal chaining, the VM does not allow application signal handlers for certain signals that are used internally by the VM, including the `SIGUSR2` signal. You can use the `-XX:-HandleSIGUSR2` option instead, whereby the VM signal handler is not installed on VM startup. Therefore, the application signal handler, if available, takes over the handling of the `SIGUSR2` signal. If there is no application signal handler, then the operating system's default signal handler is used.

For more information about this option that affects the handling of the `SIGUSR2` signal, see [`-XX:[+|-]HandleSIGUSR2`](xxhandlesigusr2.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On Linux, macOS, and z/OS systems, you can use the `LD_PRELOAD`
environment variable as an alternative method to the command line for linking the shared library as shown in the following list:

- bash and ksh shells: `export LD_PRELOAD=$JAVA_HOME/lib/libjsig.so; <java_application>`
- csh shell: `setenv LD_PRELOAD=$JAVA_HOME/lib/libjsig.so; <java_application>`

## See also

- [-Xrs](xrs.md)
- [-Xsigcatch](xsigcatch.md)
- [-Xsigchain](xsigchain.md)
- [-Xsignal (z/OS only)](xsignal.md)

<!-- ==== END OF TOPIC ==== openj9_signals.md ==== -->
