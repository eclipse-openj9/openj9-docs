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

# -XX:[+|-]LegacyXlogOption

Controls processing of the `-Xlog` option.

## Syntax

| Setting                 | Effect                                     | Default                                                                       |
|-------------------------|--------------------------------------------|:-----------------------------------------------------------------------------:|
| `-XX:+LegacyXlogOption` | Enable legacy `-Xlog` behavior             |                                                                               |
| `-XX:-LegacyXlogOption` | Process `-Xlog` requests for GC logging    | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|

## Explanation

From Eclipse OpenJ9&trade; 0.24.0, the `-Xlog` option is replaced by the [`-Xsyslog`](xsyslog.md) option. The `-XX:[+|-]LegacyXlogOption` controls how the `-Xlog` option is processed.

- When `-XX:-LegacyXlogOption` is set, the `-Xlog` option is recognized only when a form of this option is run that requests garbage collection (GC) logging (for example, `-Xlog:gc[:stderr|:file=<filename>]`). For more information, see [`-Xlog`](xlog.md).
- When `-XX:+LegacyXlogOption` is set, the legacy `-Xlog` behavior is enabled. When enabled, the option is equivalent to the [`-Xsyslog`](xsyslog.md) option. That is, the `-Xlog` option can be used with the parameters documented in [`-Xsyslog`](xsyslog.md).


<!-- ==== END OF TOPIC ==== xxlegacyxlogoption.md ==== -->
