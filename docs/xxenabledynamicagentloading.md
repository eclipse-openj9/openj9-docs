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

# -XX:\[+|-\]EnableDynamicAgentLoading

This option enables or disables the dynamic loading of agents into a running VM.


## Syntax

        -XX:[+|-]EnableDynamicAgentLoading

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+EnableDynamicAgentLoading` |  Enable   |   :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>                                 |
| `-XX:-EnableDynamicAgentLoading` |  Disable  |     |


## Explanation

With the [Attach API](attachapi.md), your application can connect to a running VM and load an agent dynamically into that VM to run tasks. Some libraries misuse the Attach API to dynamically load an agent to run tasks without the approval of the application owner.

You can disable the dynamic loading of the agents into a VM after startup with the `-XX:-EnableDynamicAgentLoading` option.

![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) For Java&trade; 21 and later, the following warnings are issued when the agents are loaded dynamically into a running VM after startup without specifying the `-XX:+EnableDynamicAgentLoading` option. These warnings are not issued if the same agents were loaded before either by using the [command-line options](interface_jvmti.md#overview) or an earlier dynamic loading.

```
WARNING: A {Java,JVM TI} agent has been loaded dynamically (file:/u/bob/agent.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release
```

These warnings are issued only once for the same agent when the `-XX:+EnableDynamicAgentLoading` option is not specified.

If the `-XX:+EnableDynamicAgentLoading` option is set, all agents that are dynamically loaded are considered as approved by the application owner, and therefore, no warnings are issued.

Eclipse OpenJ9&trade; supported operating systems have APIs to determine whether the same agent was loaded before or not, even if an agent is loaded with a platform-independent name or an absolute path to the platform-dependent library.

From 0.44.0 release onwards, AIX systems also can detect whether an agent was previously loaded or not if the agent was loaded through a platform-independent name or an absolute path to the platform-dependent library. Therefore, like other platforms, on AIX systems also, the warnings are issued only once for the same agent when the `-XX:+EnableDynamicAgentLoading` option is not specified. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

## See also

- [Java Virtual Machine Tool Interface](interface_jvmti.md)
- [Java Attach API](attachapi.md)

<!-- ==== END OF TOPIC ==== xxenabledynamicagentloading.md ==== -->
