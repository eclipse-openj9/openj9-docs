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

# -XX:-StackTraceInThrowable


This option removes stack traces from exceptions.

## Syntax

        -XX:-StackTraceInThrowable

| Setting                      | Effect  | Default                                                          |
|------------------------------|---------|------------------------------------------------------------------|
|`-XX:-StackTraceInThrowable`  | Disable | No                                                               |

:   While stack traces are included in exceptions by default, recording them can have a negative impact on performance. Use this option if you want to remove stack traces, although this might cause difficulties with problem determination.

    When this option is enabled, `Throwable.getStackTrace()` returns an empty array and the stack trace is displayed when an uncaught exception occurs. `Thread.getStackTrace()` and `Thread.getAllStackTraces()` are not affected by this option.

## See also

- [`-XX:\[+|-\]ShowCarrierFrames`](xxshowcarrierframes.md)

<!-- ==== END OF TOPIC ==== xxstacktraceinthrowable.md ==== -->
