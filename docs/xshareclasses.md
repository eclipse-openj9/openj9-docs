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

# -Xshareclasses

Use the `-Xshareclasses` option to enable, disable, or modify class sharing behavior. Class data sharing is enabled by default for bootstrap classes only (the equivalent of specifying `-Xshareclasses:bootClassesOnly,nonFatal,silent`), unless your application is running in a container.

This option can take a number of parameters, some of which are *cache utilities*. Cache utilities carry out specific operations on a specified cache without starting the Java virtual machine (VM). Although you can combine multiple suboptions, which are separated by commas, the cache utilities are mutually exclusive.

Some cache utilities can work with caches from previous Java&trade; versions or caches that are created by VMs with different bit-widths. These caches are referred to as *"incompatible"* caches.

See also the [Class data sharing](shrc.md) topic, which includes some [best practices for using `-Xshareclasses`](shrc.md#best-practices-for-using-xshareclasses).

## Syntax

        -Xshareclasses:<parameter>

When you specify `-Xshareclasses` without any parameters and without specifying either the `-Xscmx` or `-XX:SharedCacheHardLimit` options, a shared classes cache is created with a default size, as follows:

  - For 64-bit platforms, the default size is 300 MB, with a "soft" maximum limit for the initial size of the cache (`-Xscmx`) of 64 MB, with the following exceptions:
    - For a persistent cache, if the free disk space is less than 6 GB, the default size is set to 64 MB and an `-Xscmx` size is not set.
    - For a non-persistent cache on Linux&reg; or macOS&reg; systems, the cache size is limited by the maximum amount of memory that can be reserved by a process (`SHMMAX`). If `SHMMAX` is less than 300 MB, the default shared cache size is set to equal `SHMMAX`. If `SHMMAX` is greater than 80 MB, `-Xscmx` is set to 64 MB. If `SHMMAX` is less than 80 MB an `-Xscmx` size is not set.
  - For other platforms, the default size is 16 MB.

## Parameters

### `adjustmaxaot` (Cache utility)

        -Xshareclasses:adjustmaxaot=<size>

: Adjusts the maximum shared classes cache space that is allowed for AOT data. When you use the `-Xshareclasses:verbose` option, the VM writes to the console the number of bytes that are not stored due to the current setting of the [`-Xscmaxaot`](xscminaot.md "When you create a shared classes cache, you can use this option to apply a maximum number of bytes in the class cache that can be used for AOT data.") option.

### `adjustminaot` (Cache utility)

        -Xshareclasses:adjustminaot=<size>

: Adjusts the minimum shared classes cache space that is reserved for AOT data. Use the [`-Xscminaot`](xscminaot.md "When you create a shared classes cache, you can use this option to apply a minimum number of bytes in the class cache to reserve for AOT data.") option to set the initial minimum size.

### `adjustmaxjitdata` (Cache utility)

        -Xshareclasses:adjustmaxjitdata=<size>

: Adjusts the maximum shared classes cache space that is allowed for JIT data. When you use the `-Xshareclasses:verbose` option, the VM writes to the console the number of bytes that are not stored due to the current setting of the [`-Xscmaxjitdata`](xscminjitdata.md "When you create a shared classes cache, you can use this option to apply a maximum number of bytes in the class cache that can be used for JIT data.") option.

### `adjustminjitdata` (Cache utility)

        -Xshareclasses:adjustminjitdata=<size>

: Adjusts the minimum shared classes cache space that is reserved for JIT data. Use the [`-Xscminjitdata`](xscminjitdata.md "When you create a shared classes cache, you can use this option to apply a minimum number of bytes in the class cache to reserve for JIT data.") option to set the initial minimum size.

### `adjustsoftmx` (Cache utility)

        -Xshareclasses:adjustsoftmx=<size>

: Adjusts the soft maximum size of the cache. When you use the `-Xshareclasses:verbose` option, the VM writes to the console the number of bytes that are not stored due to the current setting of the soft maximum size. For more information about the soft maximum size, see [-Xscmx](xscmx.md "For a new shared classes cache, specifies either the actual size of the cache (if the -XX:SharedCacheHardLimit option is not present) or the soft maximum size of the cache (if used with the `-XX:SharedCacheHardLimit` option).").

### `allowClasspaths`

        -Xshareclasses:allowClasspaths

: Allows a VM to store classpaths into an existing shared cache that was created by using the `restrictClasspaths` option.

### `bootClassesOnly`

        -Xshareclasses:bootClassesOnly

: Disables caching of classes that are loaded by class loaders other than the bootstrap class loader. If you use this suboption, the `nonfatal` suboption is also set, so this suboption is the equivalent of specifying `-Xshareclasses:bootClassesOnly,nonfatal`.

### `cacheDir`

        -Xshareclasses:cacheDir=<directory>

: Sets the directory in which cache data is read and written. Do not set the cache directory on an NFS mount or a shared mount across systems or LPARs. The following defaults apply:

    - On Windows&trade; systems, `<directory>` is the user's `C:\Users\<username>\AppData\Local\javasharedresources` directory.
    - On z/OS&reg; systems, `<directory>` is the `/tmp/javasharedresources` directory.
    - On other operating systems, `<directory>` is `.cache/javasharedresources` in the user's home directory, unless the `groupAccess` parameter is specified, in which case it is `/tmp/javasharedresources`, because some members of the group might not have access to the user's home directory. You must have sufficient permissions in `<directory>`. Do not set user's home directory on a NFS or shared mount.

: On all operating systems, the VM writes persistent cache files directly into the directory specified. Persistent cache files can be safely moved and deleted from the file system.

: Non-persistent caches are stored in shared memory and have control files that describe the location of the memory. Control files are stored in a `javasharedresources` subdirectory of the `cacheDir` specified. Do not move or delete control files in this directory. The `listAllCaches` utility, the `destroyAll` utility, and the `expire` suboption work only in the scope of a given `cacheDir`.

: On AIX, Linux, and macOS systems, if you specify the `cacheDir=<directory>` option, persistent caches are created with the following permissions (`-rw-r--r--`):

    -   User: read/write
    -   Group: read (read/write if you also specify `-Xshareclasses:groupAccess`)
    -   Other: read only


: Otherwise, persistent caches are created with the same permissions as non-persistent caches. The permissions for non-persistent caches are `-rw-r-----`, or `-rw-rw----` if you also specify `-Xshareclasses:groupAccess`.

: :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** It is good practice to set an application-specific cache directory to avoid sharing the default cache directory with the default cache, or other application caches that don't set a cache directory, and means that your application is therefore unaffected by a user running [`java -Xshareclasses:destroyAll`](xshareclasses.md#destroyall-cache-utility). See [Class data sharing: Best practices for using `-Xshareclasses`](shrc.md#best-practices-for-using-xshareclasses).

### `cacheDirPerm`

: **(Not Windows)**

        -Xshareclasses:cacheDirPerm=<permission>

: Sets Unix-style permissions when you are creating a cache directory. `<permission>` must be an octal number in the ranges 0700 - 0777 or 1700 - 1777. If `<permission>` is not valid, the VM ends with an appropriate error message.

: The permissions that are specified by this suboption are used only when you are creating a new cache directory. If the cache directory already exists, this suboption is ignored and the cache directory permissions are not changed.

: If you set this suboption to 0000, the default directory permissions are used. If you set this suboption to 1000, the machine default directory permissions are used, but the sticky bit is enabled.

: If the cache directory is the platform default directory, this suboption is ignored. The permission is set as specified in the following conditions and permissions table.

: If you do not set this suboption, the default permissions are used according to the following conditions:

| Condition | Permissions |
| ---------- | ----------- |
| The cache directory is `/tmp/javasharedresources`. If this directory already exists with different permissions, the permissions are changed when the cache is opened.† | 0777 |
| The cache directory already exists and is not `/tmp/javasharedresources` | Unchanged |
| The cache directory is a new directory and you also specify the `groupAcess` suboption | 0770 |
| The cache directory is a new directory and you do not specify the `groupAccess` suboption | 0700 |

: †On z/OS systems, permissions for existing cache directories are unchanged, to avoid generating RACF&reg; errors, which generate log messages.

: :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** It is good practice to explicitly set permissions for the cache directory when the defaults are not appropriate. See [Class data sharing: Best practices for using `-Xshareclasses`](shrc.md#best-practices-for-using-xshareclasses).

### `cacheRetransformed`

        -Xshareclasses:cacheRetransformed

: Enables caching of classes that are transformed by using the JVMTI `RetransformClasses` function. For more information, see [Redefined and retransformed classes](shrc.md#redefined-and-retransformed-classes).

The option `enableBCI` is enabled by default. However, if you use the `cacheRetransformed` option, this option forces cache creation into `-Xshareclasses:disableBCI` mode.

### `checkURLTimestamps`

        -Xshareclasses:checkURLTimestamps

: Causes timestamps of `jar` or `zip` files to be checked every time a class is loaded. If a timestamp has changed, the class is loaded from the `jar` or `zip` file and not from the shared cache. This suboption is not enabled by default and reflects the legacy behavior of the shared classes cache.

: :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The timestamp of a bootstrap `jar` or `zip` file is checked once when it is used for the first time to load a class.

### `createLayer`

**(64-bit only)**

        -Xshareclasses:createLayer

: Creates layered caches.

: If there are multiple VMs in a race condition while creating a layered cache, more than one new layered cache can be created. To avoid this situation, use the `-Xshareclasses:layer=<number>` suboption to create a new layered cache with a specific layer number. See [`layer`](xshareclasses.md#layer) for more information about layered caches.

### `destroy` (Cache utility)

        -Xshareclasses:destroy

: Destroys a cache that is specified by the `name`, `cacheDir`, and `nonpersistent` suboptions. A cache can be destroyed only if all VMs that are using it have ended and the user has sufficient permissions.

### `destroyAll` (Cache utility)

        -Xshareclasses:destroyAll

: Tries to destroy all the caches that are specified by the `cacheDir` and `nonpersistent` suboptions.

: On Windows and z/OS systems, a cache can be destroyed only if all VMs that are using it have shut down and the user has sufficient permissions.

: :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On z/OS, when the `destroyAll` option is invoked from a 31-bit VM, 64-bit caches are not destroyed. Similarly, when the `destroyAll` option is invoked from a 64-bit VM, 31-bit caches are not destroyed. The following message is displayed:

        JVMSHRC735I: Use a nn-bit VM to perform the requested operation on the
        nn-bit shared cache "cachename" as the nn-bit VM
        cannot verify that the shared memory was created by the VM.

: On AIX, Linux, and macOS systems:

- Non-persistent caches can be destroyed only if all VMs that are using it have shut down and the user has sufficient permissions.

- Persistent caches that are still in use continue to exist even when you use this option, but they are unlinked from the file system so they are not visible to new VM invocations. If you update the VM then restart an application for which a persistent shared cache already exists, the VM unlinks the existing cache and creates a new cache. Because the unlinked caches are not visible to new VMs, you cannot find them by using the `-Xshareclasses:listAllCaches` option, and you cannot use the `-Xshareclasses:printStats` option on them. You can therefore have multiple unlinked caches that consume file system space until they are no longer in use.

### `destroyAllLayers` (Cache utility)

**(64-bit only)**

        -Xshareclasses:destroyAllLayers

: Destroys all shared cache layers that are specified by the `name` suboption. For example, `-Xshareclasses:name=Cache1,destroyAllLayers` destroys all layers of the cache called `Cache1`. If you use the `destroy` suboption on a layered cache, for example `-Xshareclasses:name=Cache1,destroy`, only the top layer of the cache is destroyed.

: For more information about layered caches, see [Creating layer caches](shrc.md#creating-layer-caches).

### `destroyAllSnapshots` (Cache utility)

: **(Not Windows)**

        -Xshareclasses:destroyAllSnapshots

: Destroys all shared cache snapshots that are available as a result of the specified `cacheDir` suboption.

### `destroySnapshot` (Cache utility)

: **(Not Windows)**

        -Xshareclasses:destroySnapshot

: Destroys a snapshot that is specified by the `name` and `cacheDir` suboptions.

### `disableBCI`

        -Xshareclasses:disableBCI

: Turns off BCI support. This option can be used to override [`-XXShareClassesEnableBCI`](xxshareclassesenablebci.md).

### `enableBCI`

        -Xshareclasses:enableBCI

: This option is enabled by default.

: Allows a JVMTI `ClassFileLoadHook` event to be triggered every time, for classes that are loaded from the cache. This mode also prevents caching of classes that are modified by JVMTI agents. For more information about bytecode modification, see [Support for bytecode instrumentation](shrc.md#support-for-bytecode-instrumentation). This option is incompatible with the [`cacheRetransformed`](#cacheretransformed) option. Using the two options together causes the VM to end with an error message, unless [`-Xshareclasses:nonfatal`](#nonfatal) is specified. In this
case, the VM continues without using shared classes.

: A cache that is created without the `enableBCI` suboption cannot be reused with the `enableBCI` suboption. Attempting to do so causes the VM to end with an error message, unless [`-Xshareclasses:nonfatal`](#nonfatal) is specified. In this case, the VM continues without using shared classes. A cache that is created with the `enableBCI` suboption can be reused without specifying this suboption. In this case, the VM detects that the cache was created with the `enableBCI` suboption and uses the cache in this mode.

### `expire` (Cache utility)

        -Xshareclasses:expire=<time_in_minutes>

: Destroys all caches that are unused for the time that is specified before loading shared classes. This option is not a utility option because it does not cause the VM to exit. On Windows systems, which have NTFS file systems, the `expire` option is accurate to the nearest hour.

### `extraStartupHints`

        -Xshareclasses:extraStartupHints=<number>

: where, `<number>` is greater than or equal to 0.

: Adjusts the number of startup hints that can be stored in a shared cache. By default, you can store only up to 64 startup hints in a shared cache. This count decrements by 1 each time a startup hint is stored. After the count reaches 0, no more hints can be added to the cache. You can use `-Xshareclasses:extraStartupHints=<number>` to adjust this count as needed. For example, `-Xshareclasses:extraStartupHints=0` prevents any new hints to be stored and `-Xshareclasses:extraStartupHints=10` allows 10 more new hints in addition to the default number of 64 startup hints.

: You can use [`-Xshareclasses:printStats=startuphint`](#printstats-cache-utility) to check how many startup hints are already stored in the shared cache. You can also use [`-Xshareclasses:printDetails`](#printdetails-cache-utility) to check how many hints in addition to the default startup hints can be stored in the cache. In the output of the `printDetails` suboption, the number of extra startup hints that can be stored is found on the line `# Additional startup hints allowed`.

### `fatal`

        -Xshareclasses:fatal

: The VM does not start if class data sharing fails, for example because there was an error when accessing the cache directory. An error message is generated. This suboption is specified by default unless you use the `bootClassesOnly` suboption, which is equivalent to `-Xshareclasses:bootClassesOnly,nonfatal`. You can override this behavior by specifying `-Xshareclasses:bootClassesOnly,fatal`. See also [`nonfatal`](#nonfatal).

### `findAotMethods` (Cache utility)

        -Xshareclasses:findAotMethods=<method_specification>
        -Xshareclasses:findAotMethods=help

: Print the AOT methods in the shared classes cache that match the method specifications. Methods that are already invalidated are indicated in the output. Use this suboption to check which AOT methods in the shared classes cache would be invalidated by using the same method specifications with the `invalidateAotMethods` suboption. To learn more about the syntax to use for `<method_specification>`, including how to specify more than one method, see [Method specification syntax](#method-specification-syntax).

### `groupAccess`

: **(Not Windows)**

        -Xshareclasses:groupAccess

: Sets operating system permissions on a new cache to allow group access to the cache. Group access can be set only when permitted by the operating system `umask` setting. The default is user access only.

: On AIX, Linux, and macOS systems, if a user creates a cache by specifying the `groupAccess` suboption, other users in the same group must also specify this suboption to be granted access to the same cache.

: When `groupAccess` is specified, the default directory for a cache is `/tmp/javasharedresources`. Some systems might clear the content of the `/tmp` directory on a reboot, removing the shared cache. To avoid that problem, you are therefore recommended to use [`cacheDir`](#cachedir) to set a different location for the cache. If necessary, use [`cacheDirPerm`](#cachedirperm) to ensure that the cache directory permissions are set appropriately.

: In certain situations, warning messages might be generated when the `groupAccess` suboption is used.

: This message can occur when persistent caches are used:

        JVMSHRC756W Failed to set group access permission on the shared cache
        file as requested by the 'groupAccess' sub-option

: These messages can occur when non-persistent caches are used:

        JVMSHRC759W Failed to set group access permission as requested by the
        'groupAccess' sub-option on the semaphore control file associated
        with shared classes cache.

        JVMSHRC760W Failed to set group access permission as requested by the
        'groupAccess' sub-option on the shared memory control file associated
        with shared classes cache.

: This message can occur in combination with the `snapshotCache` suboption:

        JVMSHRC761W Failed to set group access permission as requested by the
        'groupAccess' sub-option on the shared cache snapshot file.

: All of these warning messages mean that the user's **umask** setting does not allow either, or both, of the group `read` and `write` permission to be set on the file. The typical umask setting restricts only the `write` permission. To resolve the warning, either change the **umask** setting or remove the `groupAccess` suboption.

### `help`

        -Xshareclasses:help

: Lists all the command-line options.

### `invalidateAotMethods` (Cache utility)

        -Xshareclasses:invalidateAotMethods=<method_specification>
        -Xshareclasses:invalidateAotMethods=help

: Modify the existing shared cache to invalidate the AOT methods that match the method specifications. Use this suboption to invalidate AOT methods that cause a failure in the application, without having to destroy the shared cache. Invalidated AOT methods remain in the shared cache, but are then excluded from being loaded. VMs that have not processed the methods, or new VMs that use the cache are not affected by the invalidated methods. The AOT methods are invalidated for the lifetime of the cache, but do not prevent the AOT methods from being compiled again if a new shared cache is created. To prevent AOT method compilation into a new shared cache, use the `-Xaot:exclude` option. For more information, see [-Xaot](xaot.md#exclude).

: To identify AOT problems, see [Diagnosing a JIT or AOT problem](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.vm.80.doc/docs/jit_pd_diagnose.html).

: To revalidate an AOT method, see the `revalidateAotMethods` suboption. Use the `findAotMethod` suboption to determine which AOT methods match the method specifications. To learn more about the syntax to use for `<method_specification>`, including how to specify more than one method, see [Method specification syntax](#method-specification-syntax).

### `layer`

(64-bit only)

        -Xshareclasses:layer=<number>

: Creates layered caches.

: This suboption has the same effect as the [`createLayer`](xshareclasses.md#createlayer) suboption, but with the added ability to specify the layer number.

: For more information about creating a shared classes cache with layers, see [Creating layer caches](shrc.md#creating-layer-caches).


### `listAllCaches` (Cache utility)

        -Xshareclasses:listAllCaches

: Lists all the compatible and incompatible caches, and snapshots that exist in the specified cache directory. If you do not specify `cacheDir`, the default directory is used.

: Summary information, such as Java version and current usage, is displayed for each cache. Output includes `cache-type` (persistent or non-persistent) and `feature` (compressed references (`cr`) or non-compressed references (`non-cr`)).

### `mprotect`

: AIX, z/OS 31-bit:

        -Xshareclasses:mprotect=[default|all|none]

: Linux, Windows, macOS:

        -Xshareclasses:mprotect=[default|all|partialpagesonstartup|onfind|nopartialpages|none]

: where:

    - `default`: By default, the memory pages that contain the cache are always protected, unless a specific page is being updated. This protection helps prevent accidental or deliberate corruption to the cache. The cache header is not protected by default because this protection has a performance cost. On Linux, macOS, and Windows systems, after the startup phase, the Java virtual machine (VM) protects partially filled pages whenever new data is added to the shared classes cache in the following sequence:
        - The VM changes the memory protection of any partially filled pages to read/write.
        - The VM adds the data to the cache.
        - The VM changes the memory protection of any partially filled pages to read only.
    - `all`: This value ensures that all the cache pages are protected, including the header. See Note.
    - `partialpagesonstartup`: This value causes the VM to protect partially filled pages during startup as well as after the startup phase. This value is available only on Linux, macOS, and Windows systems.
    - `onfind`: When this option is specified, the VM protects partially filled pages when it reads new data in the cache that is added by another VM. This option is available only on Linux, macOS, and Windows systems.
    - `nopartialpages`: Use this value to turn off the protection of partially filled pages. This value is available only on Linux, macOS, and Windows systems.
    - `none`: Specifying this value disables the page protection.

  : :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Specifying `all` has a negative impact on performance. You should specify `all` only for problem diagnosis and not for production. Specifying values `partialpagesonstartup` or `onfind` can also have a negative impact on performance when the cache is being populated. There is no further impact when the cache is full or no longer being modified.

### `modified`

        -Xshareclasses:modified=<modified_context>

: Used when a JVMTI agent is installed that might modify bytecode at run time. If you do not specify this suboption and a bytecode modification agent is installed, classes are safely shared with an extra performance cost. The `<modified context>` is a descriptor that is chosen by the user; for example, *myModification1*. This option partitions the cache so that only VMs that are using context *myModification1* can share the same classes. So if, for example, you run an application with a modification context and then run it again with a different modification context, all classes are stored twice in the cache.

: For more information, see [Sharing modified bytecode](shrc.md#sharing-modified-bytecode).

: If you are migrating from IBM&reg; SDK, Java Technology Edition, Version 7, or earlier releases, you must set [`-Xshareclasses:disableBCI`](#disablebci) when you use this option to retain the same behavior.

### `name`

        -Xshareclasses:name=<name>

:   Connects to a cache of a given name, creating the cache if it does not exist. This option is also used to indicate the cache that is to be modified by cache utilities; for example, `destroy`. Use the `listAllCaches` utility to show which named caches are currently available. If you do not specify a name, the default name *"sharedcc\_%u"* is used. "%u" in the cache name inserts the current user name. On operating systems other than Windows, you can specify *"%g"* in the cache name to insert the current group name.

: :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** It is good practice to explicitly specify a cache for your application. This avoids the application sharing a cache that is enabled by default or with another application that doesn't set a name, and ensures that the size of your application cache can be set appropriately and that cache space is used exclusively for your application. Note that you cannot change the size of a default cache that already exists by using the [`-Xscmx`](xscmx.md) option, as that option has no effect on a pre-existing cache. See [Class data sharing: Best practices for using `-Xshareclasses`](shrc.md#best-practices-for-using-xshareclasses).

### `noaot`

        -Xshareclasses:noaot

:   Disables caching and loading of AOT code. AOT code already in the shared data cache can be loaded.

### `noBootclasspath`

        -Xshareclasses:noBootclasspath

:   Disables the storage of classes that are loaded by the bootstrap class loader in the shared classes cache. Often used with the `SharedClassURLFilter` API to control exactly which classes are cached. For more information about shared class filtering, see [The Java shared classes Helper API](shrc.md#the-java-shared-classes-helper-api).

### `noTimestampChecks`

        -Xshareclasses:noTimestampChecks

:   Turns off timestamp checking when finding classes in the shared cache. Use this option only when you know there are no updates to the classes from the class paths or module paths in your application. Otherwise, obsolete classes might be loaded from the shared cache. If this happens, remove the `noTimestampChecks` option.

### `nocheckURLTimestamps`

        -Xshareclasses:nocheckURLTimestamps

:   Timestamps of `jar` or `zip` files are checked only when they are added to a class loader and used for the first time to look up a class. This is the default
behavior, which can improve the performance of class loading from the shared classes cache, especially on Windows systems. To revert to the behavior of the shared classes cache in earlier releases, use the [`CheckURLTimeStamps`](xshareclasses.md#checkurltimestamps) suboption.

: :fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** When the `nocheckURLTimestamps` suboption is used (default), if `jar` or `zip` files are updated after a class loader starts loading classes from them, an older version of the class might be loaded from the shared classes cache. If this scenario occurs, use the `checkURLTimestamps` option.

### `nojitdata`

        -Xshareclasses:nojitdata

:   Disables caching of JIT data. JIT data already in the shared data cache can be loaded.

### `none`

        -Xshareclasses:none

:   Added to the end of a command line, disables class data sharing. This suboption overrides class sharing arguments found earlier on the command line. This suboption disables the shared class utility APIs. To disable class data sharing without disabling the utility APIs, use the `utilities` suboption. For more information about the shared class utility APIs, see [The Java shared classes utility API](shrc.md#the-java-shared-classes-utility-api).

### `nonfatal`

        -Xshareclasses:nonfatal

:   Allows the VM to start, in most cases, even if class data sharing fails. Normal behavior for the VM is to refuse to start if class data sharing fails. If you select `nonfatal` and the shared classes cache fails to initialize, the VM attempts to connect to the cache in read-only mode. If this attempt fails, the VM starts without class data sharing. See also [`fatal`](#fatal).

:   :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Unless it is important that your application runs with class data sharing, it is good practice to set this parameter. See [Creating a shared classes cache](shrc.md#creating-a-shared-classes-cache). However, cache corruption as a result of a bug in the operating system, VM, or user code might not be detected when opening the cache. In this situation, the cache is used and the application might crash.

### `nonpersistent`

        -Xshareclasses:nonpersistent

:   Uses a non-persistent cache. The cache is lost when the operating system shuts down. Non-persistent and persistent caches can have the same name. On Linux, macOS, and Windows systems, you must always use the `nonpersistent` suboption when you run utilities such as `destroy` on a non-persistent cache.

:   :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On macOS systems, you must set `kern.sysv.shmmax` and `kern.sysv.shmall` when using a non-persistent cache.

### `noPersistentDiskSpaceCheck`

        -Xshareclasses:noPersistentDiskSpaceCheck

:   Instructs the VM not to check for available storage on the file system before creating a persistent shared classes cache. This option prevents an error on file systems that do not support the checking of free space, where a value of 0 is returned and a shared cache cannot be created. Regardless of whether you choose to set this option, if there isn't enough disk space available when the VM writes to the shared cache memory, a **SIGBUS** or **SIGSEGV** signal occurs and the VM ends.

:   If you are using the [`readonly`](#readonly) suboption, the VM does not check the available disk space, so you do not need to set the `noPersistentDiskSpaceCheck` suboption.

### `persistent`

        -Xshareclasses:persistent

:   Uses a persistent cache. The cache is created on disk, which persists beyond operating system restarts. Non-persistent and persistent caches can have the same name. On z/OS, you must always use the `persistent` suboption when you run utilities such as `destroy` on a persistent cache.

:   :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

:    - With the 31-bit VM on all versions of z/OS, the shared cache is memory mapped within the 0-2 GB address range. The maximum size of the persistent shared classes cache is limited by the system limit `MAXMMAPAREA`. For more information about the suggested value for the `MAXMMAPAREA` limit, see [Setting resource limits (z/OS)](https://www.eclipse.org/openj9/docs/configuring/#setting-resource-limits-zos).
     - With the 64-bit VM, the shared cache is mapped as follows:
         - On z/OS version 2.3 and earlier, the persistent shared cache is memory mapped within the 0-2 GB address range. The maximum size of the persistent shared classes cache is limited by the system limit `MAXMMAPAREA`.
         - On z/OS version 2.4 and later, the persistent shared cache is mapped above the 2 GB address range. The maximum size of persistent shared classes cache is limited by the `MAXSHARE` value within the `SMFLIMxx` member of `SYS1.PARMLIB`. The default persistent shared cache size is 300MB, and the following machine configuration settings will allow a shared cache up to that size.

              1. Add the following line in the `SMFLIMxx` member of `SYS1.PARMLIB`. `MAXSHARE` value is defined in number of 4K pages. The `JOBNAME(*)` filter should be customized to apply the setting to the relevant Java address spaces. The `JOBMSG(SUPPRESS)` parameter can be used to optionally suppress `IEF043I` diagnostic messages.

             ```
              REGION JOBNAME(*) JOBMSG(SUPPRESS) MAXSHARE(76800)
             ```

              2. Load the change with the following command in the SDSF panel.

             ```
              /SET SMFLIM=xx
             ```

        For more information about the SMFLIMxx member of SYS1.PARMLIB, see [Statements and parameters for SMFLIMxx](https://www.ibm.com/docs/en/zos/2.5.0?topic=values-statements-parameters-smflimxx).

        On z/OS version 2.4, fixes for APARs [OA60306](https://www.ibm.com/support/pages/apar/OA60306) and [PH32235](https://www.ibm.com/support/pages/apar/PH32235) must be installed for the support of this memory mapping. On z/OS version 2.5 and later, the fixes for APARs OA60306 and PH32235 are included by default.

### `printAllStats` (Cache utility)

        -Xshareclasses:printAllStats

:   Displays detailed information about the contents of the cache that is specified in the [`name`](#name) suboption. If the name is not specified, statistics are displayed about the default cache. For layered caches, information is shown for all layers (to see information for the top layer cache only, use [`printTopLayerStats=all`](#printtoplayerstats-cache-utility)). Every class is listed in chronological order with a reference to the location from which it was loaded. For more information, see [Shared classes cache diagnostic utilities](shrc_diag_util.md#shared-classes-cache-diagnostic-utilities).

### `printDetails` (Cache utility)

        -Xshareclasses:printDetails

:   Displays detailed cache statistics. The output of this suboption is similar to the output of [`-Xshareclasses:printStats`](#printstats-cache-utility) but with additional information. For example, instead of "AOT bytes" which is a total, the output shows "AOT code bytes", "AOT data bytes", "AOT class hierarchy bytes", and "AOT thunk bytes".

### `printStats` (Cache utility)

        -Xshareclasses:printStats=<data_type>[+<data_type>]

:   Displays summary information for the cache that is specified by the [`name`](#name), [`cacheDir`](#cachedir), and [`nonpersistent`](#nonpersistent) suboptions. For layered caches, information is shown for all layers (to see information for the top layer cache only, use [`printTopLayerStats`](#printtoplayerstats-cache-utility)). The most useful information that is displayed is how full the cache is and how many classes it contains. Stale classes are classes that are updated on the file system and which the cache has therefore marked as "stale". Stale classes are not purged from the cache and can be reused. Use the `printStats=stale` option to list all the stale entries and stale bytes.

: Specify one or more data types, which are separated by a plus symbol (+), to see more detailed information about the cache content. Data types include AOT data, class paths, and ROMMethods.

: For more information and for a full list of data types, see [Shared classes cache diagnostic utilities](shrc_diag_util.md#shared-classes-cache-diagnostic-utilities).

### `printTopLayerStats` (Cache utility)

        -Xshareclasses:printTopLayerStats=<data_type>[+<data_type>]

:   Equivalent to [`printStats`](#printstats-cache-utility) but for the top layer cache only. For more information about layered caches, see [Creating a layer cache](shrc.md#creating-layer-caches).

### `readonly`

        -Xshareclasses:readonly

: By default, a shared classes cache is created with read/write access. Use the `readonly` suboption to open an existing cache with read-only permissions. Opening a cache read-only prevents the VM from making any updates to the cache. If you specify this suboption, the VM can connect to caches that were created by other users or groups without requiring write access.

: On AIX, Linux, and macOS systems, this access is permitted only if the cache was created by using the [`-Xshareclasses:cacheDir`](#cachedir) option to specify a directory with appropriate permissions. If you do not use the `-Xshareclasses:cacheDir` option, the cache is created with default permissions, which do not permit access by other users or groups.

: By default, this suboption is not specified.

: The `-Xshareclasses:readonly` option is ignored under the following conditions:

: - The JITServer AOT cache feature is enabled ([`-XX:+JITServerUseAOTCache`](xxjitserveruseaotcache.md)), and the VM is a client.
 - The VM is running in a container.
 - AOT compilation is enabled.<br>
For more information about AOT compilation, see the [Ahead-Of-Time (AOT) compiler](aot.md) topic.
 - The shared classes cache is persistent. ([`-Xshareclasses:persistent`](xshareclasses.md#persistent))

: If a persistent shared classes cache is started under the mentioned conditions, the cache startup creates a temporary new (writable) top layer. The JITServer AOT cache uses the new top layer to store a small amount of metadata that the cache needs to function. With this top layer, the JITServer AOT cache can be used without modifying the existing layers.

### `reset`

        -Xshareclasses:reset

: Causes a cache to be destroyed and then re-created when the VM starts up. This option can be added to the end of a command line as `-Xshareclasses:reset`.

### `restoreFromSnapshot` (Cache utility)

: **(Not Windows)**

        -Xshareclasses:restoreFromSnapshot

: Restores a new non-persistent shared cache from a snapshot file. The snapshot and shared cache have the same name and location, as specified by the [`name`](#name) and [`cacheDir`](#cachedir) suboptions. The non-persistent cache cannot already exist when the snapshot is restored. Restoring a snapshot does not remove the snapshot file; it can be restored multiple times. On platforms that support persistent caches, the [`nonpersistent`](#nonpersistent) suboption must be specified in order to restore a snapshot.

### `restrictClasspaths`

        -Xshareclasses:restrictClasspaths

: Allows only the first VM that is initializing a shared cache to store classpaths in the cache. Subsequent VMs are not allowed to store classpaths in the cache unless the `allowClasspaths` option is specified.

: Use the `restrictClasspaths` option only if the application is designed to create class loaders of type `java.net.URLClassloader` or its subclass, such that their classpaths are unique to the instance of the application, but the classes that are loaded from these classpaths are the same. In such cases application classpaths that are stored by one VM cannot be used by another VM.

: For example, consider two VMs, VM1 and VM2, that are using class paths CP1 and CP2 respectively, where:

    - CP1: `url1;url2;url3;tempurl1;url4;url5`
    - CP2: `url1;url2;url3;tempurl2;url4;url5`


: These class paths differ only by one entry, which is the `tempurl`. The `url1`, `url2`, `url3`, `url4`, and `url5` entries never change from run to run, whereas the `tempurl` entry is always different. This difference means that a class that is loaded from `url4` or `url5`, and stored into the shared cache by VM1, cannot be located by VM2. Therefore, an attempt by VM2 to load a class from `url4` or `url5` would cause it to store its own classpath `CP2` into the shared cache, and also add new metadata for classes that are loaded from `url4` or `url5`. Addition of such unique class paths into the shared cache is not useful. Moreover, the additional metadata might adversely affect the performance of other VMs that connect to the shared cache. Because classes loaded from `url4` or `url5` are not loaded from the shared cache when the `tempurl` differs from the original, it is good practice to put the `tempurl` as the last entry in the class path.

: In situations such as that described in the example, the `restrictClasspaths` option can be used to restrict the addition of classpaths by ensuring that the first VM initializes the shared cache, and then prevents the addition of unique classpaths by subsequent VMs that attach to the shared cache. Note that use of the `restrictClasspaths` option in any other scenario is likely to negatively impact a VM's performance when it is using an existing cache.


### `revalidateAotMethods` (Cache utility)

        -Xshareclasses:revalidateAotMethods=<method_specification>
        -Xshareclasses:revalidateAotMethods=help

:   Modify the shared cache to revalidate the AOT methods that match the method specifications. Use this suboption to revalidate AOT methods that were invalidated by using the [`invalidateAotMethods`](#invalidateaotmethods-cache-utility) suboption. Revalidated AOT methods are then eligible for loading into a VM, but do not affect VMs where the methods have already been processed. To learn more about the syntax to use for `<method_specification>`, including how to specify more than one method, see [Method specification syntax](#method-specification-syntax).

### `silent`

        -Xshareclasses:silent

:   Disables all shared class messages, including error messages. Unrecoverable error messages, which prevent the VM from initializing, are displayed.

### `snapshotCache` (Cache utility)

: **(Not Windows)**

        -Xshareclasses:snapshotCache

:   Creates a snapshot file of an existing non-persistent shared cache. The snapshot has the same name and location as the shared cache, as specified by the [`name`](#name) and [`cacheDir`](#cachedir) suboptions. The shared cache can be in use when the snapshot is taken, but VMs are blocked when they try to write to the shared cache, while the cache data is copied to the file.

: Typically, after a system is reinitialized, the snapshot file is used to restore the copy of the non-persistent cache into shared memory, via the [`restoreFromSnapshot`](#restorefromsnapshot-cache-utility) suboption. Since this process removes all non-persistent caches from shared memory, restoring the cache from the snapshot file can result in better VM startup performance, because the contents of the shared cache, including classes and AOT code, do not have to be re-created.

: A snapshot can be created only if the user has sufficient permissions to create the destination snapshot file. If a snapshot of the same name exists already, it is overwritten. On platforms that support persistent caches, the [`nonpersistent`](#nonpersistent) suboption must be specified in order to create a snapshot. For information about removing snapshot files, see the [`destroySnapshot`](#destroysnapshot-cache-utility) and [`destroyAllSnapshots`](#destroyallsnapshots-cache-utility) suboptions.

### `utilities`

        -Xshareclasses:utilities

:   Can be added to the end of a command line to disable class data sharing. This suboption overrides class sharing arguments found earlier on the command line. This suboption is like [`none`](#none), but does not disable the shared class utility APIs. For more information, see [The Java shared classes utility API](shrc.md#the-java-shared-classes-utility-api).

### `verbose`

        -Xshareclasses:verbose

:   Gives detailed output on the cache I/O activity, listing information about classes that are stored and found. Each class loader is given a unique ID (the bootstrap loader is always 0) and the output shows the class loader hierarchy at work, where class loaders ask their parents for a class if they can't find it themselves. It is typical to see many failed requests; this behavior is expected for the class loader hierarchy. The standard option `-verbose:class` also enables class sharing verbose output if class sharing is enabled.

### `verboseAOT`

        -Xshareclasses:verboseAOT

:   Enables verbose output when compiled AOT code is being found or stored in the cache. AOT code is generated heuristically. You might not see any AOT code that is generated at all for a small application. You can disable AOT caching by using the `noaot` suboption. See the [VM Messages Guide](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.messages/diag/appendixes/messages/messages.html) for a list of the messages produced.

### `verboseHelper`

        -Xshareclasses:verboseHelper

: Enables verbose output for the Java Helper API. This output shows you how the Helper API is used by your class loader.

### `verboseIO`

        -Xshareclasses:verboseIO

: Gives detailed output on the cache I/O activity, listing information about classes that are stored and found. Each class loader is given a unique ID (the bootstrap loader is always 0) and the output shows the class loader hierarchy at work, where class loaders must ask their parents for a class before they can load it themselves. It is typical to see many failed requests; this behavior is expected for the class loader hierarchy.

## Method specification syntax

The following examples show how to specify more than one method specification when you are using the [`findAotMethods`](#findaotmethods-cache-utility), [`invalidateAotMethods`](#invalidateaotmethods-cache-utility), or [`revalidateAotMethods`](#revalidateaotmethods-cache-utility) suboptions.

Each method specification is defined as follows:


    <packagename>/<classname>[.<methodname>[(<parameters>)]]

If you want to include more than one method specification in a single option, separate the specifications with a comma and enclose all the specifications in {braces}. For example:


    {<packagename/classname>}[.{<methodname>}[({<parameters>})]]
<!--
    {<method_specification1>,<method_specification2>,<method_specification3>}
-->


- You can use an asterisk (*) in most places as a wildcard.
- You can use an exclamation point (!) before the specification to mean "everything *except* this".
- Parameters are optional, but if specified, should be enclosed in parentheses and the following native signature formats must be used:
    - `B` for byte
    - `C` for char
    - `D` for double
    - `F` for float
    - `I` for int
    - `J` for long
    - `S` for short
    - `Z` for Boolean
    - `L<classname>;` for objects
    - `[` before the signature means array

If you want to specify parameters to distinguish between methods, you can use [`-Xshareclasses:findAotMethods=*`](#findaotmethods-cache-utility) (with a wildcard) to list all the parameter variations. Copy the signature for the method that you want from the output. For example, the signature for the parameters

    (byte[] bytes, int offset, int length, Charset charset)

is

    ([BIILjava/nio/charset/Charset;)

Here are some examples:


| Method signature                             | Matches...                                                                                                      |
|----------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| `*`                                          | All AOT methods.                                                                                                |
| `java/lang/Object`                           | All AOT methods in the `java.lang.Object` class                                                                 |
| `java/util/*`                                | All AOT classes and methods in the `java.util` package                                                          |
| `java/util/HashMap.putVal`                   | All `putVal` methods in the `java.util.HashMap` class                                                           |
| `java/util/HashMap.hash(Ljava/lang/Object;)` | The private `java.util.HashMap.hash(java.lang.Object)` method                                                   |
| `*.equals`                                   | All `equals` methods in all classes                                                                             |
| `{java/lang/Object,!java/lang/Object.clone}` | All methods in `java.lang.Object` except `clone`                                                                |
| `{java/util/*.*(),java/lang/Object.*(*)}`    | All classes or methods with no input parameter in the `java.util` package, and all methods in `java.lang.Object`|
| `{java/util/*.*(),!java/util/*.*()}`         | Nothing.                                                                                                        |


- [Introduction to class data sharing](shrc.md)
- [-Xscmx](xscmx.md)
- [-XX:SharedCacheHardLimit](xxsharedcachehardlimit.md)


<!-- ==== END OF TOPIC ==== docs/xshareclasses.md ==== -->
