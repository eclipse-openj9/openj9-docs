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

# -XX:StartFlightRecording

![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) This command-line option starts the JDK Flight Recorder (JFR) recording in the VM. JFR is a built-in profiling and troubleshooting feature in the VM that is used to collect profiling and diagnostic information.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- JFR recording can also be enabled or disabled on OpenJ9 by using the `jcmd` options as explained in the [-XX:[+|-]FlightRecorder](xxflightrecorder.md#explanation) topic. The JDK Mission Control (JMC) tool is the only way to view the produced JFR file.
- The command-line option cannot be used simultaneously with JFR-related `jcmd` options.

## Syntax

        -XX:StartFlightRecording[[=filename=<file_name_with_path>.jfr][,duration=<time_with_unit_of_time>][,delay=<time_with_unit_of_time>]

where:

- Optional parameters are shown in brackets, [ ].

- `filename=<file_name_with_path>` specifies the name of the file and its location where the recording is saved. The file name should have a `.jfr` extension. If a file name is not specified, the recording is saved in the `defaultJ9recording.jfr` file in the process working directory by default.

- `duration=<time_with_unit_of_time>` specifies the duration of the recording. Units of time are `ns` (nanoseconds), `us` (microseconds), `ms` (milliseconds), `s` (seconds), `m` (minutes), `h` (hours), and `d` (days). You can specify only one unit of time, for example, 54s, 12m, 1h, or 2d. If the duration is not specified, the tool continues to record until the VM is shut down.

- `delay=<time_with_unit_of_time>` specifies the delay in the start of the recording. The VM waits for that amount of time before starting the flight recording. Units of time are `ns` (nanoseconds), `us` (microseconds), `ms` (milliseconds), `s` (seconds), `m` (minutes), `h` (hours), and `d` (days). You can specify only one unit of time, for example, 54s, 12m, 1h, or 2d. If the delay is not specified, the default value of `0s` is used. The recording then starts immediately when the VM starts.

## Explanation

If JFR is enabled in the VM with the `-XX:+FlightRecorder` option, then you can trigger profile and diagnostic recording with either the `-XX:StartFlightRecording` command-line option or the [`jcmd`](https://eclipse.dev/openj9/docs/tool_jcmd/) tool. Recording does not start automatically, it must be triggered.

Example of the command-line option:

```
-XX:StartFlightRecording=filename=/path/ABCD.jfr,duration=4s,delay=2s
```

The recording stops automatically with the `duration` parameter or when the VM stops.

![End of content that applies to Java 11 (LTS) and later](cr/java_close_lts.png)

## See Also

- [What's new in version 0.59.0](version0.59.md#new-xxstartflightrecording-command-line-option-is-added)
- [`-XX:[+|-]FlightRecorder`](xxflightrecorder.md)

<!-- ==== END OF TOPIC ==== xxstartflightrecording.md ==== -->
