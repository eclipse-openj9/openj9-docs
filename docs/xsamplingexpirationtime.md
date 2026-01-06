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

# -XsamplingExpirationTime 

Disables JIT sampling after a specified amount of time. 

When the JIT sampling thread is disabled, no processor cycles are used by an idle Eclipse OpenJ9&trade; VM.

Use this option with care; after the sampling thread is disabled, you cannot reactivate it. However, because the profiling frequency is automatically reduced, you should not have to use this option. 

Allow the sampling thread to run for long enough to identify important optimizations.

## Syntax

        -XsamplingExpirationTime<time>
        
: where `<time>` is specified in seconds.

## Explanation

The JIT sampling thread profiles the running Java&trade; application to discover commonly used methods. The memory and processor usage of the sampling thread is negligible, and the frequency of profiling is automatically reduced when the OpenJ9 VM is idle, to once per second instead of once every 10ms, or once every 100 seconds if the idle state lasts more than 50 seconds.


<!-- ==== END OF TOPIC ==== xsamplingexpirationtime.md ==== -->

