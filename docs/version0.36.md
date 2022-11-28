<!--
* Copyright (c) 2017, 2022 IBM Corp. and others
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

# What's new in version 0.36.0

The following new features and notable changes since version 0.35.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [New `-XX:JITServerAOTmx` option added](#new-xxjitserveraotmx-option-added)
- [Changes to the location of the default directory for the shared cache and snapshot](#changes-to-the-location-of-the-default-directory-for-the-shared-cache-and-snapshot)

## Features and changes

### Binaries and supported environments

Eclipse OpenJ9&trade; release 0.36.0 supports OpenJDK 8, 11, and 17.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### New `-XX:JITServerAOTmx` option added

This option specifies the maximum amount of memory that can be used by the JITServer AOT cache. Instead of unlimited memory consumption, the maximum amount of memory that all AOT cache instances combined can use at the server is now limited to 300 MB, by default.

For more information, see [`-XX:JITServerAOTmx`](xxjitserveraotmx.md).

### Changes to the location of the default directory for the shared cache and snapshot

On non-Windows&trade; and non-z/OS&reg; systems platforms, the default shared classes cache directory in the user's home directory is changed from `javasharedresources` to `.cache/javasharedresources`. This change is to avoid cluttering of the home directory. If you specify `-Xshareclasses:groupAccess`, the default directory remains `/tmp/javasharedresources/`.

If the `javasharedresources` directory in the user's home directory is empty, the `javasharedresources` directory can be deleted.

You can find and remove caches or snapshots in the old default directory on non-Windows and non-z/OS platforms by using the following command-line options:

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

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.33.1 and v0.35.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.35/0.35.md).

<!-- ==== END OF TOPIC ==== version0.35.md ==== -->
