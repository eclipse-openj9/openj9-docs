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

# What's new in version 0.27.1

The following new features and notable changes since version 0.26.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [New `-XX:[+|-]AdaptiveGCThreading` option added](#new-xx-adaptivegcthreading-option-added)
- [Improved time zone information added to Java dump files](#improved-time-zone-information-added-to-java-dump-files)
- [Change in default behavior for the `balanced` garbage collection policy](#change-in-default-behavior-for-the-balanced-garbage-collection-gc-policy)
- [Stop parsing the JAVA_OPTIONS environment variable](#stop-parsing-the-java_options-environment-variable)
- [Global lock reservation enabled by default](#global-lock-reservation-enabled-by-default)
- [Increase in default operating system stack size on PPC64 platforms](#increase-in-default-operating-system-stack-size-on-ppc64-platforms)
- [New `-x` option added to `jpackcore` / `jextract`](#new-x-option-recognized-by-jpackcore-jextract)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.27.1 supports OpenJDK 8, 11, and 16.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### New `-XX:[+|-]AdaptiveGCThreading` option added

Adaptive threading is enabled by default, which automatically tunes the number of active parallel garbage collection (GC) threads.
When this feature is enabled, the GC thread count is dynamically adjusted from collection cycle to cycle to account for changes in the the amount
of time that parallel threads spend doing useful GC work (such as object graph traversal) compared to time spent synchronizing among themselves.
When GC work decreases, fewer threads are used, which reduces the overhead, effectively reducing GC pause times.
Resources are freed up for other processing activities.

Use the [`-xgcmaxthreads`](xgcmaxthreads.md) option with the [`-XX:+AdaptiveGCThreading`](xxadaptivegcthreading.md) option to specify a thread count limit.

### Improved time zone information added to Java dump files

To help with troubleshooting, additional time zone information is added to Java&trade; dump files. Two new fields are included, the date and time in UTC (`1TIDATETIMEUTC`) and the time zone according to the local system (`1TITIMEZONE`). For more information, see the [Java dump `TITLE` section](dump_javadump.md#title).

### Change in default behavior for the `balanced` garbage collection (GC) policy

In this release, a new scan mode, [`-Xgc:dynamicBreadthFirstScanOrdering`](xgc.md#dynamicbreadthfirstscanordering), is used during `balanced` GC copy forward operations that is expected to improve performance.

For more information about this type of operation, see [GC copy forward operation](gc_overview.md#gc-copy-forward-operation).

You can revert to the behavior in earlier releases by setting [`-Xgc:breadthFirstScanOrdering`](xgc.md#breadthfirstscanordering) when you start your application.

### Stop parsing the JAVA_OPTIONS environment variable

The 0.24 release started parsing the JAVA_OPTIONS environment variable. This variable was added in error and has been removed.
The [_JAVA_OPTIONS environment variable](cmdline_specifying.md) (with different behavior) is added for compatibility.

### Global lock reservation enabled by default

**(AIX&reg; and Linux on Power Systems&trade; only)**

Global lock reservation is now enabled by default. This is an optimization targeted towards more efficient handling of locking and unlocking Java objects. The older locking behavior can be restored via the `-XX:-GlobalLockReservation` option. See [-XX:[+|-]GlobalLockReservation](xxgloballockreservation.md) for more details.

### Increase in default operating system stack size on PPC64 platforms

The default operating system stack size on AIX and Linux PPC64 is increased from 256KB to 512KB. You can change the operating system stack size by using the [-Xmso](xmso.md) option.

### New `-x` option recognized by `jpackcore` / `jextract`

The new option, `-x`, causes the system dump to be omitted from the archive created. In its place, the file `excluded-files.txt` is added, which names the excluded file. For more information, see [Dump extractor](tool_jextract.md).

## Full release information

To see a complete list of changes between Eclipse OpenJ9 v0.26.0 and v0.27.1 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.27/0.27.md).

<!-- ==== END OF TOPIC ==== version0.27.md ==== -->
