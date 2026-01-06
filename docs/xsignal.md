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

# -Xsignal

**(z/OS&reg; only)**

This option controls the behavior of Eclipse OpenJ9&trade; VM signal handlers.

## Syntax

        -Xsignal:<parameter>=<value>

## Parameters

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** You cannot use these parameters together.

### `posixSignalHandler`

        -Xsignal:posixSignalHandler=cooperativeShutdown

: When the VM signal handlers for SIGSEGV, SIGILL, SIGBUS, SIGFPE, SIGTRAP, and SIGABRT end a process, they call `exit()`, by default. In this case, the z/OS&trade; Language Environment&reg; is not aware that the VM ended abnormally.

    With `-Xsignal:posixSignalHandler=cooperativeShutdown`, the VM no longer uses `exit()` to end the process from the signal handlers. Instead, the VM behaves in one of the following ways:

    - In response to a z/OS hardware exception, the VM uses `return()`.
    - In response to signals raised or injected by software, the VM ends the enclave with `abend 3565`.

    Language Environment detects that the VM is ending abnormally and initiates Resource Recovery Services.


### `userConditionHandler`

: **(31-bit z/OS only)**

        -Xsignal:userConditionHandler=percolate

: This option results in similar behavior to the [`-XCEEHDLR`](xceehdlr.md) option: the VM registers user condition handlers to handle the z/OS exceptions that would otherwise be handled by the VM POSIX signal handlers for the SIGBUS, SIGFPE, SIGILL, SIGSEGV, and SIGTRAP signals.

    As with the `-XCEEHDLR` option, the VM does not install POSIX signal handlers for these signals.

    This option differs from the `-XCEEHDLR` option in that the VM percolates *all* Language Environment&reg; conditions that were not triggered and expected by the VM during normal running, including conditions that are severity 2 or greater. The VM generates its own diagnostic information before percolating severity 2 or greater conditions.

    The VM is in an undefined state after percolating a severity 2 or greater condition. Applications cannot resume running then call back into, or return to, the VM.


## See also

- [`-XCEEHDLR`](xceehdlr.md)
- [Signal handling](openj9_signals.md)

<!-- ==== END OF TOPIC ==== xsignal.md ==== -->
<!-- ==== END OF TOPIC ==== xsignalposixsignalhandlercooperativeshutdown.md ==== -->
<!-- ==== END OF TOPIC ==== xsignaluserconditionhandlerpercolate.md ==== -->
