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

# -Xlog

This option is supported for better compatibility with the reference implementation. However, only forms of `-Xlog` that enable garbage collection (GC) logging are recognized. Note that the behavior of this option changed in Eclipse OpenJ9&trade; 0.24.0.

## Syntax

        -Xlog[:<parameters>]

:fontawesome-solid-pencil:{: .note aria-hidden="true"}**Note:** In Eclipse OpenJ9 version 0.24.0, the [`-Xsyslog`](xsyslog.md) option replaced the existing OpenJ9 `-Xlog` option for message logging to avoid conflicts with the reference implementation. For backward compatibility, you can control the behavior of the `-Xlog` option with the [`-XX:[+|-]LegacyXlogOption`](xxlegacyxlogoption.md) option.


## Explanation

Use of the `-Xlog` option is supported for GC logging only. The following table describes the behavior of the option depending on what you specify on the command line.

| `-Xlog` option type         | Behavior                                    |
|-------------------------|--------------------------------------------------|
| An option that returns GC data. For example `-Xlog:gc`                | An equivalent OpenJ9 GC logging option is enabled. See the next table for more details. |
| An option that, in the reference implementation, returns GC data and also other data. For example: `-Xlog`, `-Xlog:all`, `-Xlog:gc+<other_tag>`, or  `-Xlog:gc:stdout` | An equivalent OpenJ9 GC logging option is enabled as before but because non-GC data is not supported, the following error message is also produced: <br/> `JVMJ9VM007W Command-line option unrecognised: <option>`      |
| An option that, in the reference implementation, returns only non-GC data            | Non-GC data is not supported, so the following error message is produced: <br/> `JVMJ9VM007W Command-line option unrecognised: <option>` |   

The following table shows some examples of the mapping between `-Xlog` parameters and the equivalent OpenJ9 GC parameters:

| `-Xlog` parameter         | OpenJ9 GC equivalent                                 |
|-------------------------|--------------------------------------------------|
| `-Xlog:gc` <br/> `-Xlog:gc:stderr`                | `-verbose:gc` |
| `-Xlog:gc:<filename>` <br/> `-Xlog:gc:file=<filename>` | `-Xverbosegclog:<updated_filename>`      |


In the table, the value of `<filename>` can contain the following tokens, which are processed and passed to the `-Xverbosegclog` option as `<updated_filename>`:

- `%p` is replaced with the process ID (equivalent to [dump agent token `%pid`](xdump.md#dump-agent-tokens))
- `%t` is replaced with the dump agent tokens `%Y-%m-%d_%H-%M-%S`.


## See also

- [`-Xsyslog`](xsyslog.md)
- [`-Xverbosegclog`](xverbosegclog.md)
- [`-XX:[+|-]LegacyXlogOption`](xxlegacyxlogoption.md)


<!-- ==== END OF TOPIC ==== xlog.md ==== -->
