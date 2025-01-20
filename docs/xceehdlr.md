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

# -XCEEHDLR

**(31-bit z/OS&reg; only)**

Controls Eclipse OpenJ9&trade; VM Language Environment&reg; condition handling.

## Syntax

        -XCEEHDLR

## Explanation

Use the `-XCEEHDLR` option if you want the new behavior for the Java&trade; and COBOL interoperability batch mode environment, because this option makes signal and condition handling behavior more predictable in a mixed Java and COBOL environment.

When the `-XCEEHDLR` option is enabled, a condition triggered by an arithmetic operation while executing a Java Native Interface (JNI) component causes the VM to convert the Language Environment condition into a Java `ConditionException`.

When the `-XCEEHDLR` option is used the VM does not install POSIX signal handlers for the following signals:

- `SIGBUS`
- `SIGFPE`
- `SIGILL`
- `SIGSEGV`
- `SIGTRAP`

Instead, user condition handlers are registered by the VM, using the `CEEHDLR()` method. These condition handlers are registered every time a thread calls into the VM. Threads call into the VM using the Java Native Interface and including the invocation interfaces, for example `JNI\_CreateJavaVM`.

The Java runtime continues to register POSIX signal handlers for the following signals:

- `SIGABRT`
- `SIGINT`
- `SIGQUIT`
- `SIGTERM`

Signal chaining using the `libjsig.so` library is not supported.

When the `-XCEEHDLR` option is used, condition handler actions take place in the following sequence:

1. All severity 0 and severity 1 conditions are percolated.
2. If a Language Environment condition is triggered in JNI code as a result of an arithmetic operation, the VM condition handler resumes executing Java code as if the JNI native code had thrown a `com.ibm.le.conditionhandling.ConditionException` exception. This exception class is a subclass of `java.lang.RuntimeException`.  
:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The Language Environment conditions that correspond to arithmetic operations are `CEE3208S` through `CEE3234S`. However, the Language Environment does not deliver conditions `CEE3208S`, `CEE3213S`, or `CEE3234S` to C applications, so the VM condition handler will not receive them.
3. If the condition handling reaches this step, the condition is considered to be unrecoverable. RAS diagnostic information is generated, and the VM ends by calling the `CEE3AB2()` service with abend code 3565, reason code 0, and cleanup code 0.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** You cannot use `-Xsignal:userConditionHandler=percolate` and `-XCEEHDLR` together.


## See also

- [-Xsignal:userConditionHandler=percolate](xsignal.md#userconditionhandler)

- [Signal handling](openj9_signals.md)




<!-- ==== END OF TOPIC ==== xceehdlr.md ==== -->
