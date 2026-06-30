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

# -Xconcurrentslack

This option specifies an absolute amount of free space or headroom (in bytes) in the tenure space on top of the automatic concurrent kickoff point. By using this option, you can trigger the concurrent global garbage collection (GC) cycle earlier than the GC heuristic computed kickoff point.

## Syntax

        -Xconcurrentslack<size>

: See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

## Explanation

Ideally, concurrent GC needs to complete its operation before application consumes all free memory. Otherwise, GC will transition into the stop-the-world (STW) mode, which means longer pauses. Due to fluctuations in application characteristics such as allocation rate, object survival rate, CPU utilization, GC might mis-predict the concurrent GC kickoff point, and start it too late or progress too slowly.

To compensate for such fluctuations, you can specify this option so that the VM starts concurrent GC operations earlier to try to keep free heap space available.

Starting concurrent GC operations earlier can reduce pause times, but it might increase frequency because of suboptimal use of free memory.

The default value is `0`, which is optimal for most applications.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This option is not supported by the following GC policies:

- Balanced ([`-Xgcpolicy:balanced`](gc.md#balanced-policy))
- Optimize for throughput ([`-Xgcpolicy:optthruput`](gc.md#optthruput-policy))
- Metronome ([`-Xgcpolicy:metronome`](gc.md#metronome-policy))

### How free space is interpreted

The meaning of free space depends on the GC policy in use.

**Optimize for average pause time (`optavgpause`) policy**

In this policy, application threads and the concurrent GC kickoff heuristic measure free space in the same way. Therefore, the `-Xconcurrentslack` value can be used directly.

**Generational concurrent (`gencon`) policy**

The `<size>` value that you specify for `-Xconcurrentslack` corresponds to additional free space for allocation. But, in `gencon` policy, application threads and the concurrent GC kickoff heuristic do not measure free space in the same way. Application threads allocate mostly in nursery space, but the concurrent GC kickoff heuristic monitors free memory in tenure space.

Because the concurrent GC kickoff heuristic monitors tenure space rather than nursery space, the `<size>` value does not map directly to the free space seen by allocating threads.

Therefore, in `gencon` policy, the amount of headroom is calculated as follows:

> Headroom = (`<size>` ÷ Average promotion rate) × Average application allocation rate

where:

- **`<size>`** is the free memory in tenure space that is specified in the `-Xconcurrentslack` option.
- **Average promotion rate** is the average rate at which objects are promoted from nursery space into tenure space.
- **Average application allocation rate** is the average allocation rate from nursery.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The average promotion rate and average application allocation rate can be derived from verbose GC logs. But, in a typical generational workload, the promotion rate is approximately ten times  lesser than the allocation rate. Hence, the formula can be approximated with `<size>` * 10.

In the case of `gencon` policy, use the [`-Xgc:concurrentKickoffTenuringHeadroom`](xgc.md#concurrentkickofftenuringheadroom) option instead of the `-Xconcurrentslack` option for ease.

## See also

- [`-Xgc:concurrentKickoffTenuringHeadroom`](xgc.md#concurrentkickofftenuringheadroom)
- [Garbage collection policies](gc_overview.md)

<!-- ==== END OF TOPIC ==== xconcurrentslack.md ==== -->
