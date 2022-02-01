<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# -Xsyslog

Enables operating system message logging.

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

- Changes made to message logging by using the `-Xsyslog` option do not affect messages written to the standard error stream (`stderr`).
- This option replaces the OpenJ9 [`-Xlog`](xlog.md) option in Eclipse OpenJ9 version 0.24.0. If the [`-XX:+LegacyXlogOption`](xxlegacyxlogoption.md) is set, `-Xlog` behaves in the same way as `-Xsyslog` and with the same parameters.

## Syntax

        -Xsyslog:<parameter>{,<parameter>}

## Parameters

:fontawesome-solid-exclamation-triangle:{: .warn aria-hidden="true"} **Restriction:** The parameters `all`, `none` and `help` must be used on their own and cannot be combined. However, the other parameters can be grouped. For example, to include error, vital and warning messages use `-Xsyslog:error,vital,warn`.

For message details see [OpenJ9 VM messages](messages_intro.md#jvm-messages).

### `help`

        -Xsyslog:help

: Gives details the available parameters. (This parameter cannot be combined with others.)

### `error`

        -Xsyslog:error

: Turns on logging for all OpenJ9 VM error messages (default).

### `vital`

        -Xsyslog:vital

: Turns on logging for selected information messages `JVMDUMP006I`, `JVMDUMP032I`, and `JVMDUMP033I`, which provide valuable additional information about dumps produced by the OpenJ9 VM (default).

### `info`

        -Xsyslog:info

: Turns on logging for all OpenJ9 VM information messages.

### `warn`

        -Xsyslog:warn

: Turns on logging for all OpenJ9 VM warning messages.

### `config`

        -Xsyslog:config

: Turns on logging for all OpenJ9 VM configuration messages.

### `all`

        -Xsyslog:all

: Turns on logging for all OpenJ9 VM messages. (This parameter cannot be combined with others.)

### `none`

        -Xsyslog:none

: Turns off logging for all OpenJ9 VM messages. (This parameter cannot be combined with others.)



<!-- ==== END OF TOPIC ==== xsyslog.md ==== -->
