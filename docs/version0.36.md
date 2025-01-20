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

# What's new in version 0.36.x

The following new features and notable changes since version 0.35.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Changes to the location of the default directory for the shared cache and snapshot](#changes-to-the-location-of-the-default-directory-for-the-shared-cache-and-snapshot)
- [New `-XX:[+|-]MergeCompilerOptions` option added](#new-xx-mergecompileroptions-option-added)
- [Default JITServer AOT cache name changed](#default-jitserver-aot-cache-name-changed)
- [New `-XX:JITServerAOTmx` option added](#new-xxjitserveraotmx-option-added)
- [New `-XX:[+|-]JITServerAOTCachePersistence` option added](#new-xx-jitserveraotcachepersistence-option-added)
- [New `-XX:JITServerAOTCacheDir` option added](#new-xxjitserveraotcachedir-option-added)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.36.0 supports OpenJDK 8 and 17.

Release 0.36.1 supports OpenJDK 11.

Support for running OpenJ9 with OpenJDK 8 or OpenJDK 11 on CentOS 6.10 is deprecated and might be removed in a future release. OpenJ9 will not be tested with OpenJDK 11 on CentOS 6.10 after the 0.36.1 release.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Changes to the location of the default directory for the shared cache and snapshot

On operating systems other than Windows&trade; and z/OS&reg;, the default shared classes cache directory in the user's home directory is changed from `javasharedresources` to `.cache/javasharedresources`. This change is to avoid cluttering of the home directory. If you specify `-Xshareclasses:groupAccess`, the default directory remains `/tmp/javasharedresources/`.

If the `javasharedresources` directory in the user's home directory is empty, the `javasharedresources` directory can be deleted.

You can find and remove caches or snapshots in the old default directory by using the following command-line options:

For persistent caches:

- `-Xshareclasses:cacheDir=<HomeDir>/javasharedresources/,listAllCaches` to find the caches
- `-Xshareclasses:cacheDir=<HomeDir>/javasharedresources/,name=<cacheName>,destroy` to remove a particular cache
- `-Xshareclasses:cacheDir=<HomeDir>/javasharedresources/,destroyAll` to remove all caches
- `-Xshareclasses:cacheDir=<HomeDir>/javasharedresources/,destroyAllLayers` to remove multi-layer caches

For nonpersistent caches or snapshots:

- `-Xshareclasses:cacheDir=<HomeDir>,listAllCaches` to find the cache or snapshot
- `-Xshareclasses:cacheDir=<HomeDir>,name=<cacheName>,destroy` to remove a particular shared cache
- `-Xshareclasses:cacheDir=<HomeDir>,destroyAll` to remove all caches
- `-Xshareclasses:cacheDir=<HomeDir>,destroyAllLayers` to remove multi-layer caches
- `-Xshareclasses:cacheDir=<HomeDir>,name=<snapshotName>,destroySnapshot` to remove a particular snapshot
- `-Xshareclasses:cacheDir=<HomeDir>,destroyAllSnapshots` to remove all snapshots

### New `-XX:[+|-]MergeCompilerOptions` option added

This option enables or disables the merging of multiple `-Xjit` or `-Xaot` options into a single `-Xjit` or `-Xaot` option.

For more information, see [`-XX:[+|-]MergeCompilerOptions`](xxmergecompileroptions.md).

### Default JITServer AOT cache name changed

A JITServer instance can have several AOT caches, each with its own name and containing different versions of compiled methods. Client JVMs with different profiles of execution can connect to the same JITServer instance, but access the cache with compiled methods optimized for their own profile with the `-XX:JITServerAOTCacheName` option. Earlier, if the cache name was not specified in this option, the default was to use a nameless cache. The default AOT cache name is now changed to `default`.

This change is to allow AOT cache persistence, whereby JITServer can periodically save its AOT caches to files with names that include the name of the cache. JITServer can then load caches from such files when a client requests a particular cache.

For more information, see [`-XX:JITServerAOTCacheName`](xxjitserveraotcachename.md) and [`-XX:[+|-]JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md).

### New `-XX:JITServerAOTmx` option added

This option specifies the maximum amount of memory that can be used by the JITServer AOT cache. Instead of unlimited memory consumption, the maximum amount of memory that all AOT cache instances combined can use at the server is now limited to 300 MB, by default.

For more information, see [`-XX:JITServerAOTmx`](xxjitserveraotmx.md).

### New `-XX:[+|-]JITServerAOTCachePersistence` option added

The JITServer AOT cache was a non-persistent in-memory cache. If the JITServer instance terminated, the cache content was lost. Now, with the `-XX:+JITServerAOTCachePersistence` option, the JITServer server periodically saves its AOT caches to files. Other JITServer instances can then load these caches from files the first time a client requests a particular cache.

For more information, see [`-XX:[+|-]JITServerAOTCachePersistence`](xxjitserveraotcachepersistence.md).

### New `-XX:JITServerAOTCacheDir` option added

You can specify the directory for saving or loading the JITServer AOT cache files with the `-XX:JITServerAOTCacheDir=<directory>` option. If the option is not used, AOT cache files are saved to (or loaded from) the current working directory of the JITServer server.

For more information, see [`-XX:JITServerAOTCacheDir`](xxjitserveraotcachedir.md).

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.35.0 and v0.36.x releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.36/0.36.md).

<!-- ==== END OF TOPIC ==== version0.36.md ==== -->
