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

# -Xlog 

Enables message logging. 

<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span class="sr-only">Note</span> **Note:** Changes made to message logging using the `-Xlog` option do not affect messages written to the standard error stream (`stderr`).

## Syntax

        -Xlog:<parameter>{,<parameter>}

## Parameters

<i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span class="sr-only">Restriction</span> **Restriction:** The parameters `all`, `none` and `help` must be used on their own and cannot be combined. However, the other parameters can be grouped. For example, to include error, vital and warning messages use `-Xlog:error,vital,warn`. 

For message details see [OpenJ9 VM messages](messages_intro.md#jvm-messages).

### `help`

        -Xlog:help

: Gives details the available parameters. (This parameter cannot be combined with others.)

### `error`

        -Xlog:error

: Turns on logging for all OpenJ9 VM error messages (default).

### `vital`

        -Xlog:vital

: Turns on logging for selected information messages `JVMDUMP006I`, `JVMDUMP032I`, and `JVMDUMP033I`, which provide valuable additional information about dumps produced by the OpenJ9 VM (default).

### `info`

        -Xlog:info

: Turns on logging for all OpenJ9 VM information messages.

### `warn`

        -Xlog:warn

: Turns on logging for all OpenJ9 VM warning messages.

### `config`

        -Xlog:config

: Turns on logging for all OpenJ9 VM configuration messages.

### `all`

        -Xlog:all

: Turns on logging for all OpenJ9 VM messages. (This parameter cannot be combined with others.)

### `none`

        -Xlog:none

: Turns off logging for all OpenJ9 VM messages. (This parameter cannot be combined with others.)


<!-- ==== END OF TOPIC ==== xlog.md ==== -->

