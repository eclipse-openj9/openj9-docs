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

# -XX:\[+|-\]HandleSIGUSR2

**(AIX&reg;, Linux&reg;, macOS&reg;, and z/OS&reg; only)**

This option affects the handling of the `SIGUSR2` signal. This signal is user-defined and triggers the [`user2`](xdump.md#dump-events) event, which is commonly used for taking system dump files with exclusive access. The operating system installs a default signal handler for handling `SIGUSR2`. If there is no VM or application signal handler, then the operating system's signal handler is used.


## Syntax

        -XX:[+|-]HandleSIGAUSR2

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+HandleSIGUSR2` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-HandleSIGUSR2` | Disable |                                                                                    |


## Explanation

When enabled, the VM handles the `SIGUSR2` signal and generates the `user2` event, which can be configured with the `-Xdump` option to trigger a dump agent.

When the option is disabled, the VM does not handle the `SIGUSR2` signal and therefore, the VM signal handler is not installed. Generally, the default operating system handler that is installed for the `SIGUSR2` signal takes over the handling of the signal.

For more information about the signals and signal handling, see [Signal handling](openj9_signals.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Do not use the `-XX:+HandleSIGUSR2` and [`-Xrs`](xrs.md) options together. An error is thrown if both options are enabled. To resolve this error, one of the options should be disabled.

## See also

- [-Xdump](xdump.md)
- [-Xrs](xrs.md)
- [What's new in version 0.38.0](version0.38.md#new-xx-handlesigusr2-option-added)

<!-- ==== END OF TOPIC ==== xxhandlesigusr2.md ==== -->
