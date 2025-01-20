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

# -Xquickstart 

This option causes the JIT compiler to run with a subset of optimizations, which can improve the performance of short-running applications.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** For compatibility with other Java&trade; virtual machines, you can also specify the `-client` option, which results in identical behavior to `-Xquickstart`.

## Syntax

        -Xquickstart

## Default behavior

By default, `-Xquickstart` is disabled.

## Explanation

The JIT compiler is tuned for long-running applications typically used on a server. When you specify this option, the compiler uses a subset of optimizations, which results in faster compilation times that improve startup time. However, longer running applications might run more slowly, especially applications that contain hot methods and other methods using a large amount of processing resource.

When the AOT compiler is active (both shared classes and AOT compilation enabled), `-Xquickstart` causes all methods to be AOT compiled. The AOT compilation improves the startup time of subsequent runs, but might reduce performance for longer running applications, especially those that contain hot methods.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The implementation of `-Xquickstart` is subject to change in future releases.


<!-- ==== END OF TOPIC ==== xquickstart.md ==== -->

