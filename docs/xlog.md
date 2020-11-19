<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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

To control logging to the operating system message log, use [`-Xsyslog`](xsyslog.md).
- When [`-XX:-LegacyXlogOption`](xxlegacyxlogoption.md) is set, which is the default, the `-Xlog` option is processed for compatibility as described in the explanation.
- When `-XX:+LegacyXlogOption` is set, `-Xlog` is equivalent to `-Xsyslog`.

## Syntax

        -Xlog:gc[:stderr|:file=<filename>]

## Explanation

`-Xlog:gc` or `-Xlog:gc:stderr` is equivalent to [`-Xverbosegclog`](xverbosegclog.md). `-Xlog:gc:file=<filename>` is equivalent to `-Xverbosegclog:<filename>`. The tokens `%p` and `%t` are recognized in the `<filename>` for compatibility. `%p` is replaced with the process ID and is equivalent to the [dump agent token `%pid`](xdump.md#dump-agent-tokens). `%t` is replaced with the dump agent tokens `%Y-%m-%d_%H-%M-%S`.

Other forms of `-Xlog` which should enable GC logging are also recognized, such as `-Xlog` or `-Xlog:all`, but since these options are intended to enable other forms of logging which are not supported, a warning is output `JVMJ9VM007W Command-line option unrecognised: <option>` in addition to enabling verbose GC logging . Similarly `-Xlog:gc:stdout` enables `-Xverbosegclog` but also outputs the warning. The `-Xverbosegclog` output goes to `stderr`, logging to `stdout` is not supported.

<!-- ==== END OF TOPIC ==== xsyslog.md ==== -->

