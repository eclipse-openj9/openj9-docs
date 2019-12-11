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

# Class data sharing

Sharing class data between JVMs improves start up performance and reduces memory footprint.

Start up performance is improved by placing classes that an application needs when initializing into a shared classes cache. The next time the
application runs, it takes much less time to start because the classes are already available. When you enable class data sharing, AOT compilation is also enabled by default, which dynamically compiles certain methods into AOT code at runtime. By using these features in combination, startup performance can be improved even further because the cached AOT code can be used to quickly enable native code performance for subsequent runs of your application.

When class data sharing is enabled, OpenJ9 automatically creates a memory mapped file that stores and shares the classes in memory.

The shared classes cache is updated dynamically; When an application loads new classes, the JVM automatically stores them in the cache without any user intervention.

Memory footprint is reduced by sharing common classes between applications that run in separate Java VMs.

## Enabling class data sharing

Class data sharing is enabled by default for bootstrap classes only (the equivalent of specifying `-Xshareclasses:bootClassesOnly,nonFatal,silent`), unless your application is running in a container. If you want to change the default behaviour, use the [`-Xshareclasses`](xshareclasses.md) option on the command line. For example:

- You can change the name and location of the default shared classes cache.
- You can enable messages about the default shared classes cache by using the default command line option without the `silent` suboption: `-Xshareclasses:bootClassesOnly,nonFatal`.

<i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** If you have multiple VMs and you do not change the default shared classes behavior, the VMs will share a single default cache, assuming that the VMs are from a single Java installation. If the VMs are from different Java installations, the cache might be deleted and recreated; for more information, see the following section about best practices.

You can treat the default cache like any other shared classes cache, for example you can print statistics for it, change the soft maximum limit size, or delete it.

You can enable class data sharing for non-bootstrap classes as well, by using `-Xshareclasses` without the `bootClassesOnly` suboption. You can also disable all class data sharing by using the `none` suboption.

## Best practices for using `-Xshareclasses`

The [-Xshareclasses](xshareclasses.md) option is highly configurable, allowing you to specify where to create the cache, how much space to allocate for AOT code and more. You can also set the cache size by using the [-Xscmx](xscmx.md) option. When shared classes is enabled, it is good practice to specify some of the cache behavior:

- Set an application-specific cache name ([`-Xshareclasses:name=<name>`](xshareclasses.md#name)).

    If a cache with the specified name doesn't already exist, a new cache is created.

    This avoids sharing your application cache with a cache that is enabled by default or with another application that doesn't set a name, and ensures that the size of your application cache can be set appropriately and that cache space is used exclusively for your application.

    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> **Note:** You cannot change the size of a default cache that already exists by using the [`-Xscmx`](xscmx.md) option, as that option has no effect on a pre-existing cache.

- Set a specific cache directory ([`-Xshareclasses:cacheDir=<directory>`](xshareclasses.md#cachedir)).

    Set a cache directory that is specific to your application, to  avoid sharing the default cache directory with the default cache, or other application caches that don't set a cache directory. Your application will be unaffected by a user running [`java -Xshareclasses:destroyAll`](xshareclasses.md#destroyall-cache-utility).

    In addition, if you have VMs from different Java installations, of the same Java release and installed by the same user, each VM checks whether the existing default shared cache in the cache directory is from the same Java installation as the VM. If not, the VM deletes that shared cache, then creates a new one. Specifying a different cache directory for each Java installation avoids this situation.



- Ensure that the cache directory permissions are set appropriately ([`-Xshareclasses:cacheDirPerm`](xshareclasses.md#cachedirperm)).

    It is good practice to explicitly set permissions for the cache directory when the defaults are not appropriate.

- Set the [`-Xshareclasses:nonfatal`](xshareclasses.md#nonfatal) option.

    This option means that your application can start even if there is a problem opening or creating the shared cache, in which case, the VM might be able to start *without* class data sharing.



## Support for custom class loaders

Classes are shared by the bootstrap class loader internally in the OpenJ9 VM. If you are using a custom class loader, you can use the Java Helper API to find and store classes in the shared class cache.

For more information, see [Using the Java Helper API](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/shrc_pd_helper.html).

## Cache utilities

Several utilities are provided for managing active caches, which are invoked by specifying `-Xshareclasses` suboptions. These utilities control the following types of operations:

- Adjust the minimum and maximum amount of cache space reserved for AOT or JIT data.
- Adjust the soft maximum size of the cache.
- Create snapshots of a cache.
- Create a cache from a snapshot.
- Remove caches and cache snapshots.
- List all the compatible and incompatible caches and snapshots.
- For problem determination, invalidate and revalidate AOT methods that cause a failure in an application.
- For problem determination, provide information to analyze the contents of a shared classes cache.

For more information, see [-Xshareclasses](xshareclasses.md).

## See also

- [AOT compiler](aot.md)
- [Class Sharing](https://www.ibm.com/developerworks/java/library/j-ibmjava4/index.html)
- [Diagnosing class data sharing problems](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/shrc_pd_intro.html)


<!-- ==== END OF TOPIC ==== shrc.md ==== -->
