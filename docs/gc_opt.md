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


# The `optavgpause` and `optthroughput` policies

The "optimize for pause time" (`optavgpause`) and the "optimize for throughput" (`optthruput`) garbage collection (GC) policies use the global collector to manage a flat heap comprised of a single region. The application runs until the heap is exhausted and the global collector runs to mark and sweep the heap. Additionally, if the heap becomes fragmented, the global collector compacts the heap.

## When to use these policies

These policies are best suited to short-lived applications and to long-running services involving concurrent sessions that have short lifespans. Short-lived applications with adequate heap sizes usually complete without compaction, and the flat heap fragments more slowly when session-bound objects are allocated and drop out of the live set in short overlapping clusters.

- **The `optavgpause` policy** uses concurrent mark and sweep phases, that is, it emplys concurrent marking as for [`gencon`](gc_gencon.md) to anticipate global collections and initiate some marking before the stop-the-world global collection phase. This reduces the likelihood of long interruptions of service due to GC activity; pause times are reduced when compared to `optthruput`, but at the expense of some performance throughput.

    Consider using this policy if you have a large heap size (available on 64-bit platforms), because this policy limits the effect of increasing heap size on the length of the garbage collection pause. However, if your application uses many short-lived objects, the [`gencon`](gc_gencon.md) policy might produce better performance.

- **The `optthruput` policy** is optimized for throughput as it inhibits concurrent marking, which means that applications stop for long pauses while garbage collection takes place. It is therefore best suited for applications that can tolerate longer pauses to obtain better overall throughput.

    You might consider using this policy when high application throughput, rather than short garbage collection pauses, is the main performance goal. If your application cannot tolerate long garbage collection pauses, consider using another policy, such as [`gencon`](gc_gencon.md).


## Invoking these policies

For details of how to use these policies, see:

- [`-Xgcpolicy:optavgpause`](xgcpolicy.md#optavgpause)
- [`-Xgcpolicy:optthruput`](xgcpolicy.md#optthruput)

See also the general [`-Xgc`](xgc.md) options that change the behavior of the GC.


<!--

## From `gc.md`

- [`-Xgcpolicy:optavgpause`](xgcpolicy.md#optavgpause) uses concurrent mark and sweep phases, which means that pause times are reduced when compared to optthruput, but at the expense of some performance throughput.
- [`-Xgcpolicy:optthruput`](xgcpolicy.md#optthruput) is optimized for throughput by disabling the concurrent mark phase, which means that applications will stop for long pauses while garbage collection takes place. You might consider using this policy when high application throughput, rather than short garbage collection pauses, is the main performance goal.


## From `xgcpolicy.md`

### `optavgpause`

        -Xgcpolicy:optavgpause

: The "optimize for pause time" policy uses concurrent mark and concurrent sweep phases. Pause times are shorter than with `optthruput`, but application throughput is reduced because some garbage collection work is taking place while the application is running. Consider using this policy if you have a large heap size (available on 64-bit platforms), because this policy limits the effect of increasing heap size on the length of the garbage collection pause. However, if your application uses many short-lived objects, the `gencon` policy might produce better performance.

### `optthruput`

        -Xgcpolicy:optthruput

: The "optimize for throughput" policy disables the concurrent mark phase. The application stops during global garbage collection, so long pauses can occur. This configuration is typically used for large-heap applications when high application throughput, rather than short garbage collection pauses, is the main performance goal. If your application cannot tolerate long garbage collection pauses, consider using another policy, such as `gencon`.




## Briggs

The optavgpause and optthruput policies use the global collector to manage a flat heap comprised of a single region. The application runs until the heap is exhausted and the global collector runs to mark and sweep the heap. Additionally, if the heap becomes fragmented, the global collector will compact the heap.

These policies are best suited to short-lived applications and to long-running services involving concurrent sessions that have short lifespans. Short-lived applications with adequate heap sizes will usually complete without compaction, and the flat heap fragments more slowly when session-bound objects are allocated and drop out of the live set in short overlapping clusters.

- The optavgpause policy employs concurrent marking as for gencon to anticipate global collections and initiate some marking before the stop-the-world global collection phase. This reduces the likelihood of long interruptions of service due to GC activity.

- The optthruput policy inhibits concurrent marking and is best suited for applications that can tolerate longer pauses to obtain better overall throughput.

-->

<!-- ==== END OF TOPIC ==== gc_gencon.md ==== -->
