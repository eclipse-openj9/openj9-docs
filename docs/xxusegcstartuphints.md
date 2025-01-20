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

# -XX:\[+|-\]UseGCStartupHints

When this option is enabled, the VM calculates, over several application restarts, an appropriate startup heap size for your application. You can therefore use this option instead of calculating and setting an `-Xms` value yourself. Setting an initial size for the heap that is larger than the default helps to avoid frequent garbage collections during the startup phase of an application.

## Syntax

        -XX:[+|-]UseGCStartupHints

| Setting                            | Effect  | Default                                                                            |
|------------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+UseGCStartupHints` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-UseGCStartupHints` | Disable |               |

When enabled, the VM records the heap size when a *startup complete* event occurs, storing the value into the shared classes cache.
On subsequent restarts, the garbage collector (GC) reads this value early in startup processing and expands the heap to an appropriate
value. For accuracy and stability, averages are taken over a few restarts to stabilize the value used. The heap size recorded is specific to the application command line,
therefore a different hint is stored for every unique command line.

You can check the value used by the garbage collector for heap expansion by inspecting verbose GC output. The following example shows heap expansion based on hints from the previous run when using the `gencon` policy:

```
<heap-resize id="2" type="expand" space="nursery" amount="205258752" count="1" timems="0.328" reason="hint from previous runs" timestamp="2019-06-05T13:26:32.021" />
<heap-resize id="3" type="expand" space="tenure" amount="692649984" count="1" timems="0.326" reason="hint from previous runs" timestamp="2019-06-05T13:26:32.022" />
```

The final value stored to the shared cache is not recorded in the verbose GC output.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- When enabled, this option overrides any initial heap size that is specified on the command line but only if the new size is within the initial heap size limits of that command-line option. For example, the new initial heap size will be greater than the initial heap size set by the [`-Xms`](xms.md) option or its default value.
- Because the shared classes cache is used to store heap size information, this option does not work if class data sharing ([`-Xshareclasses`](xshareclasses.md)) is disabled.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** This feature is not currently available with the Balanced GC policy.


<!-- ==== END OF TOPIC ==== xxusegcstartuphints.md ==== -->
