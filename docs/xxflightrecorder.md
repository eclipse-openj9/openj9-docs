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

# -XX:[+|-]FlightRecorder

**Linux&reg; on x86, Linux on AArch64**

![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) This option enables or disables the Java Flight Recorder (JFR) tool in the VM. This built-in profiling and troubleshooting tool in the VM collects profiling and diagnostic information.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Support for the JFR tool is currently provided as a technical preview. All JFR-related `jcmd` options might change in future releases.

## Syntax

        -XX:[+|-]FlightRecorder

| Setting                            | Effect  | Default                                                                            |
|------------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+FlightRecorder` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-FlightRecorder` | Disable |               |

## Explanation

If the JFR tool is enabled in the VM with the `-XX:+FlightRecorder` option, then you can trigger profile and diagnostic recording with the [`jcmd`](https://eclipse.dev/openj9/docs/tool_jcmd/) tool. Recording does not start automatically, it must be triggered.

To start a recording, specify the following command:

```
jcmd <vmid | display name | 0> JFR.start [filename=<file_name_with_path>] [duration=<time_with_unit_of_time>]

```
where:

- Optional parameters are shown in brackets, [ ].

- `filename=<file_name_with_path>` specifies the name of the file and its location where the recording is saved. The file name should have a `.jfr` extension. If a file name is not specified, the recording is saved in the `defaultJ9recording.jfr` file in the process working directory by default.

- `duration=<time_with_unit_of_time>` specifies the duration of the recording. Units of time are `s` (seconds), `m` (minutes), `h` (hours), and `d` (days). You can specify only one unit of time, for example, 54s, 12m, 1h, or 2d. If the duration is not specified, the tool continues to record until the recording is manually stopped or the VM is shut down.

Example:

```
jcmd 1234 JFR.start filename=/path/ABCD.jfr duration=4s

```

![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)

## See Also

- [What's new in version 0.49.0](version0.49.md#new-xx-flightrecorder-option-added)


<!-- ==== END OF TOPIC ==== xxflightrecorder.md ==== -->
