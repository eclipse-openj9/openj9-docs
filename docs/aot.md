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

# Ahead-Of-Time (AOT) compiler

The AOT compiler dynamically compiles Java methods into native AOT code at runtime and stores them in the shared classes cache. This activity enables the VM to start an application faster the next time it runs because it doesn't need to spend time interpreting Java methods. The VM automatically chooses which methods should be AOT-compiled based on heuristics that identify the start-up phase of large applications. AOT code is always used in combination with class data sharing and is enabled automatically when `-Xshareclasses` is set on the command line. When a cached AOT method is run it might also be optimized further by the Just-In-Time (JIT) compiler.

If you want to turn off AOT compilation and disable the use of AOT-compiled code, set the `-Xnoaot` suboption. When the AOT compiler is disabled, the JIT compiles frequently used methods into native code. However, because the JIT compiler operates while the application is running, the startup time for an application will increase.

## See also

- [Diagnosing a JIT or AOT problem](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/jit_pd_diagnose.html)
- [JIT compiler](jit.md)
- [Introduction to class data sharing](shrc.md)


<!-- ==== END OF TOPIC ==== aot.md ==== -->
