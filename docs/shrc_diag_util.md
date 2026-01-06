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

# Diagnosing problems with class data sharing

If you encounter problems with class data sharing, VM messages are typically generated that point to an underlying cause. In some situations, a cache might fail to initialize correctly. In other situations classes might not be found or stored in the shared classes cache. To provide more information about a problem, you can generate verbose output, use diagnostic cache utilities, or use the Eclipse OpenJ9&trade; trace facility.

## Initialization problems

If you do not specify a directory for the shared classes cache by using the `cacheDir` suboption, the cache is created in the `javasharedresources` directory in the following default location:

- On Windows&reg; systems, this directory is created in the user's `C:\Users\<username>\AppData\Local\` directory.
- On z/OS&reg; systems, this directory is created in the `/tmp` directory.
- On other systems, this directory is located in the `.cache` directory in the user's home directory. Do not set the user's home directory on an NFS mount or a shared mount across systems or LPARs.

Initialization problems can occur on systems other than Windows because caches are created with read/write access for the user only and subsequent users do not have permission to write to the home directory. If you specify the `-Xshareclasses:groupAccess` suboption, the cache is created in the `/tmp` directory instead where all users have permission to write to the cache.

For a persistent cache, initialization problems can also occur if there is insufficient disk space available or if you are attempting to locate the cache on a remote networked file system, which is not supported.

For a non-persistent cache, a shared memory area is required. Check that your system is configured with sufficient resources as described in [Setting shared memory values](configuring.md#setting-shared-memory-values). If initialization problems persist, try deleting the cache by using the `-Xshareclasses:destroyAll` cache utility, which removes all known shared memory areas and semaphores for caches in the `cacheDir` specified or the default directory. These actions force the VM to re-create the cache.

## Problems finding or storing classes

The most common cause for classes not being stored in the cache is due to space constraints. Make sure that you set an appropriate size for the cache, as described in [Creating a shared classes cache](shrc.md#creating-a-shared-classes-cache). Setting a soft maximum size is recommended, because you can adjust the soft maximum size that is set for the cache after it is created. See [Setting a soft maximum size](xscmx.md#setting-a-soft-maximum-size).

Storing classes can also be a problem if the cache is opened read-only or if the class does not exist on the file system because it is sourced from a URL location.

If you are attempting to share modified bytecode, you must use a modification context, as described in [Sharing modified bytecode](shrc.md#sharing-modified-bytecode). Otherwise, classes are stored in a private area that is not accessible to other VMs.

If you are using a custom class loader, class path entries in the `SharedClassURLClasspathHelper` must be *confirmed* before classes can be found for these entries. More information about confirmed entries is available in the `SharedClassURLClasspathHelper` interface in the [`com.ibm.oti.shared` API documentation](api-overview.md#monitoring-and-management).

If you are instrumenting bytecode by using a JVMTI agent or `java.lang.instrument` agent, the following rules apply:

- Redefined classes are never stored in the cache.
- Retransformed classes are stored only if you specify the `-Xshareclasses:cacheRetransformed` suboption when you start your application.

If a running application uses its own class loader and you are using a `SecurityManager`, you must grant the class loader permission to `SharedCachePermission` before they can share classes. For more information, see [Support for custom class loaders](shrc.md#support-for-custom-class-loaders).

In very rare cases, problems with finding or storing classes might be due to cache corruption. If the VM detects that a cache is corrupt, it attempts to destroy the cache and re-create it. If the VM cannot re-create the cache, it starts only if the `-Xshareclasses:nonfatal` suboption is specified on the command line, but without using the shared cache. Try using the `-Xshareclasses:destroy` cache utility to remove the specific cache and re-create it. You might need to specify the `cacheDir=<directory>` and `name=<cache_name>` suboptions if the cache is not using the default settings.

## Generating verbose output

A number of `-Xshareclasses` suboptions are available for generating verbose output during class data sharing operations, which can help you identify the root cause of a problem.

### `verbose`

The [`-Xshareclasses:verbose`](xshareclasses.md#verbose) suboption provides basic output on cache usage. In the following example, a persistent cache is opened and attached to the VM for class sharing. Information is provided about the size of the cache, the unstored bytes due to the setting of a soft maximum size, and maximum AOT and JIT data size.

```
java -Xshareclasses:name=myCache,verbose HelloWorld
[-Xshareclasses persistent cache enabled]
[-Xshareclasses verbose output enabled]
JVMSHRC237I Opened shared classes persistent cache myCache
JVMSHRC246I Attached shared classes persistent cache myCache
JVMSHRC765I Memory page protection on runtime data, string read-write data and partially filled pages is successfully enabled
Hello World
JVMSHRC168I Total shared class bytes read=2532416. Total bytes stored=268156
JVMSHRC818I Total unstored bytes due to the setting of shared cache soft max is 0. Unstored AOT bytes due to the setting of -Xscmaxaot is 0. Unstored JIT bytes due to the setting of -Xscmaxjitdata is 0.

```

### `verboseIO`

The [`-Xshareclasses:verboseIO`](xshareclasses.md#verboseio) suboption provides more detailed information about class sharing operations. In the following example, some classes are found when the cache is accessed. However, class `openj9/internal/tools/attach/target/CommonDirectory` is not found and is therefore stored for sharing.

```
java -Xshareclasses:name=myCache,verboseIO HelloWorld
[-Xshareclasses verbose I/O output enabled]
Found class java/lang/Object in shared cache for class-loader id 0.
Found class java/lang/J9VMInternals in shared cache for class-loader id 0.
Found class com/ibm/oti/vm/VM in shared cache for class-loader id 0.
Found class java/lang/J9VMInternals$ClassInitializationLock in shared cache for class-loader id 0.
...
Failed to find class openj9/internal/tools/attach/target/CommonDirectory in shared cache for class-loader id 0.
Stored class openj9/internal/tools/attach/target/CommonDirectory in shared cache for class-loader id 0 with URL /root/sdk/jre/lib/amd64/compressedrefs/jclSC180/vm.jar (index 0).
...
```

The bootstrap class loader has an ID of `0`; other class loaders are given a unique ID. Class loaders follow the class loader hierarchy by asking the parent class loader for a class. If a parent fails to find the class in the cache, the child class loader stores the class in the cache.

In some situations, verbose output might not show classes being found. For example, classes are typically not found if the class is stale, as described in [Class data sharing operations](shrc.md#class-data-sharing-operations). Stale classes are redeemed if the same class is subsequently fetched by the class loader from another VM and checked against the stale class in the cache.

### `verboseAOT`

To troubleshoot AOT problems, use the [`-Xshareclasses:verboseAOT`](xshareclasses.md#verboseaot) suboption on the command line, which generates output about AOT code that is found or stored in the cache.

In the following example output, a populated cache is being accessed to look for compiled AOT code. Some AOT code is found, which can be shared, and some AOT code is stored for reuse.

```
java -Xshareclasses:name=myCache,verboseAOT HelloWorld
[-Xshareclasses AOT verbose output enabled]
Found AOT code for ROMMethod 0x00007F658005C180 in shared cache.
Found AOT code for ROMMethod 0x00007F65800723EC in shared cache.
Found AOT code for ROMMethod 0x00007F6580071D14 in shared cache.
Stored AOT code for ROMMethod 0x00007F65801847B8 in shared cache.
Stored AOT code for ROMMethod 0x00007F65800D38A4 in shared cache.
Stored AOT code for ROMMethod 0x00007F65800723CC in shared cache.
Found AOT code for ROMMethod 0x00007F65800D38A4 in shared cache.
Stored AOT code for ROMMethod 0x00007F65800724C4 in shared cache.
...
```

### `verboseHelper`

To troubleshoot problems with custom class loaders that use the Java `SharedClassHelper` API, specify the [`-Xshareclasses:verboseHelper`](xshareclasses.md#verbosehelper) suboption. Information messages and error messages are generated in the output, which can help you diagnose problems with finding or storing classes in the shared cache.

The following example output shows only information messages:

```
java -Xshareclasses:name=myCache,verboseHelper HelloWorld
[-Xshareclasses Helper API verbose output enabled]
Info for SharedClassURLClasspathHelper id 1: Verbose output enabled for SharedClassURLClasspathHelper id 1
Info for SharedClassURLClasspathHelper id 1: Created SharedClassURLClasspathHelper with id 1
Info for SharedClassURLClasspathHelper id 2: Verbose output enabled for SharedClassURLClasspathHelper id 2
Info for SharedClassURLClasspathHelper id 2: Created SharedClassURLClasspathHelper with id 2
Info for SharedClassURLClasspathHelper id 1: There are no confirmed elements in the classpath. Returning null.
Info for SharedClassURLClasspathHelper id 2: There are no confirmed elements in the classpath. Returning null.
Info for SharedClassURLClasspathHelper id 2: setClasspath() updated classpath. No invalid URLs found
Info for SharedClassURLClasspathHelper id 2: Number of confirmed entries is now 1
Hello World
```

## Diagnostic cache utilities

These utilities display information about the contents of a shared classes cache. Run the utilities by specifying them as suboptions of `-Xshareclasses`. The utilities run on the default cache unless you specify a cache by adding the `name=<cache_name>` and `cacheDir=<directory>` suboptions.

### `printStats`

```
-Xshareclasses:printStats
-Xshareclasses:printStats,name=<cache_name>
-Xshareclasses:printStats=<data_type1>[+<data_type2>][...],name=<cache_name>
```

Displays summary information about the cache. For layered caches, `-Xshareclasses:printStats` shows some information for the top layer cache, and summary information (bytes and counts only) for all layers combined. To see information for the top layer cache only, use [`printTopLayerStats`](#printtoplayerstats).

You can request more detail about items of a specific data type that are stored in the shared cache by using `printStats=<data_type>`. Use the plus symbol (+) to separate the data types. For example, use `printStats=romclass+url,name=myCache` to see information about `ROMClass` and `URL` items in all the layer caches of the cache called `Cache1`. The valid data types are as follows (case insensitive):

-   `help` (displays the list of valid data types)
-   `all` (equivalent to `printAllStats`)
-   `classpath`
-   `url`
-   `token`
-   `romclass`
-   `rommethod`
-   `aot`
-   `jitprofile`
-   `jithint`
-   `zipcache`
-   `stale`
-   `startuphint`

Example output for a traditional cache (no cache layers: `cache layer = 0`), with summary information only:

```
Current statistics for cache "Cache1":

Cache created with:
        -Xnolinenumbers                      = false
        BCI Enabled                          = true
        Restrict Classpaths                  = false
        Feature                              = cr

Cache contains only classes with line numbers

base address                         = 0x00007F60B807A000
end address                          = 0x00007F60B905E000
allocation pointer                   = 0x00007F60B81BE3A8

cache layer                          = 0
cache size                           = 16776608
softmx bytes                         = 16776608
free bytes                           = 12740572
Reserved space for AOT bytes         = -1
Maximum space for AOT bytes          = -1
Reserved space for JIT data bytes    = -1
Maximum space for JIT data bytes     = -1
Metadata bytes                       = 30440
Metadata % used                      = 0%
Class debug area size                = 1331200
Class debug area used bytes          = 189358
Class debug area % used              = 14%

ROMClass bytes                       = 1328040
AOT bytes                            = 98404
JIT data bytes                       = 168
Zip cache bytes                      = 1133704
Startup hint bytes                   = 0
Data bytes                           = 114080

# ROMClasses                         = 452
# AOT Methods                        = 2
# Classpaths                         = 1
# URLs                               = 0
# Tokens                             = 0
# Zip caches                         = 21
# Startup hints                      = 0
# Stale classes                      = 0
% Stale classes                      = 0%


Cache is 24% full

Cache is accessible to current user = true
```

Example output for a cache with 2 layers (`cache layer = 1`), with summary information only:

```
Current statistics for top layer of cache "Cache1":

Cache created with:
        -Xnolinenumbers                      = false
        BCI Enabled                          = true
        Restrict Classpaths                  = false
        Feature                              = cr


base address                         = 0x00007FCAB2766000
end address                          = 0x00007FCAB374A000
allocation pointer                   = 0x00007FCAB2766000

cache layer                          = 1
cache size                           = 16776608
softmx bytes                         = 16776608
free bytes                           = 15299372
Reserved space for AOT bytes         = -1
Maximum space for AOT bytes          = -1
Reserved space for JIT data bytes    = -1
Maximum space for JIT data bytes     = -1
Class debug area size                = 1331200
Class debug area used bytes          = 0
Class debug area % used              = 0%

Cache is 8% full

Cache is accessible to current user = true
---------------------------------------------------------

Current statistics for all layers of cache "Cache1":

ROMClass bytes                       = 1328040
AOT bytes                            = 128924
JIT data bytes                       = 812
Zip cache bytes                      = 1133704
Startup hint bytes                   = 0
Data bytes                           = 114080

# ROMClasses                         = 452
# AOT Methods                        = 20
# Classpaths                         = 1
# URLs                               = 0
# Tokens                             = 0
# Zip caches                         = 21
# Startup hints                      = 0
# Stale classes                      = 0
% Stale classes                      = 0%
```

The `Cache created with` section indicates the options that were used when the cache was created. `BCI Enabled` relates to the [`-Xshareclasses:enableBCI`](xshareclasses.md#enablebci) option (enabled by default) and `Restrict Classpaths` relates to the [`-Xshareclasses:restrictClasspaths`](xshareclasses.md#restrictclasspaths) option. `Feature = cr` indicates that the cache is a 64-bit compressed references cache.

Line number information for classes in the cache is then shown with one of the following messages:

- `Cache contains only classes with line numbers` : VM line number processing was enabled for all the classes that were stored in this shared cache (the `-Xlinenumbers` option is enabled by default). All classes in the cache contain line numbers if the original classes contained line number data.
- `Cache contains only classes without line numbers` : The `-Xnolinenumbers` option was used to disable VM line number processing for all the classes that were stored in this shared cache, so none of the classes contain line numbers.
- `Cache contains classes with line numbers and classes without line numbers` : VM line number processing was enabled for some classes and disabled for others (the `-Xnolinenumbers` option was specified when some of the classes were added to the cache).

The following summary data is displayed:

- `baseAddress` and `endAddress`
    - The boundary addresses of the shared memory area that contains the classes. These addresses vary from run to run, depending on where the operating system allocates the memory.
- `allocation pointer`
    - The address where `ROMClass` data is currently being allocated in the cache.
- `cache layer`
    - The layer number that the cache stats relate to.
- `cache size` and `free bytes`
    - `cache size` shows the total size of the shared memory area in bytes
    - `free bytes` shows the free bytes that remain. The free space is not necessarily all available for storing new classes. The cache contains separate areas for different data, and can reserve space for AOT and JIT data, as shown by subsequent summary data.
- `softmx bytes`
    - The soft maximum size for the cache. For more information, see [`-Xscmx`](xscmx.md).
- `ROMClass bytes`
    - The number of bytes of class data in the cache, which does not include data that is stored in the class debug area (see separate output for the class debug area).
- `AOT bytes`
    - The number of bytes of AOT-compiled code in the cache.
- `Reserved space for AOT bytes`
    - The number of bytes reserved for AOT-compiled code in the cache.
- `Maximum space for AOT bytes`
    - The maximum number of bytes of AOT-compiled code that can be stored in the cache.
- `JIT data bytes`
    - The number of bytes of JIT-related data stored in the cache.
- `Reserved space for JIT data bytes`
    - The number of bytes reserved for JIT-related data in the cache.
- `Maximum space for JIT data bytes`
    - The maximum number of bytes of JIT-related data that can be stored in the cache.
- `Zip cache bytes`
    - The number of zip entry cache bytes stored in the cache. On Java 11 and later, this value is zero unless a `jar` file is added to the boot classpath.
- `Startup hint bytes`
    - The number of bytes of data stored to describe startup hints.
- `Data bytes`
    - The number of bytes of non-class data stored by the VM.
- `Metadata bytes`
    - The number of bytes of data stored to describe the cached classes. :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This field is available only in the top layer cache output or when a cache is not layered.
- `Metadata % used`
    - The proportion of metadata bytes to class bytes, which indicates how efficiently cache space is being used. The value shown does consider the `Class debug area size`.
- `Class debug area size`
    - The size in bytes of the class debug area. This area is reserved to store `LineNumberTable` and `LocalVariableTable` class attribute information.
- `Class debug area bytes used`
    - The size in bytes of the Class Debug Area that contains data.
- `Class debug area % used`
    - The percentage of the Class Debug Area that contains data.
- `ROMClasses`
    - The number of classes in the cache. The cache stores `ROMClasses` \(the class data itself, which is read-only\) and information about the location from which the classes were loaded. This information is stored in different ways, depending on the Java `SharedClassHelper` API that was used to store the classes. For more information, see [Support for custom class loaders](shrc.md#support-for-custom-class-loaders).
- `AOT methods`
    - Optionally, `ROMClass` methods can be compiled and the AOT code stored in the cache. The `AOT methods` information shows the total number of methods in the cache that have AOT code compiled for them. This number includes AOT code for stale classes.
- `Classpaths`, `URLs`, and `Tokens`
    - The number of class paths, URLs, and tokens in the cache. Classes stored from a `SharedClassURLClasspathHelper` are stored with a Classpath. Classes stored using a `SharedClassURLHelper` are stored with a URL. Classes stored using a `SharedClassTokenHelper` are stored with a Token. Most class loaders, including the bootstrap and application class loaders, use a `SharedClassURLClasspathHelper`. The result is that it is most common to see class paths in the cache.
    - The number of Classpaths, URLs, and Tokens stored is determined by a number of factors. For example, every time an element of a Classpath is updated, such as when a `.jar` file is rebuilt, a new Classpath is added to the cache. Additionally, if partitions or modification contexts are used, they are associated with the Classpath, URL, or Token. A Classpath, URL, or Token is stored for each unique combination of partition and modification context. For more information, see [Sharing modified bytecode and `SharedClassHelper` cache partitions](shrc.md#sharing-modified-bytecode).
- `Zip caches`
    - The number of `.zip` files that have entry caches stored in the shared cache. On Java 11 and later, this value is zero unless a `jar` file is added to the boot classpath.
- `Startup hints`
    - The number of startup hints stored in the cache. There can be a startup hint for each unique set of command line options used to start the VM.
- `Stale classes`
    - The number of classes that have been marked as "potentially stale" by the cache code, because of a VM or Java application update. See [Class data sharing operations](shrc.md#class-data-sharing-operations).
- `% Stale classes`
    - The percentage of classes in the cache that are stale.
- `Cache is XXX% full`
    - The percentage of the cache that is currently used. This line is displayed only if the soft maximum size is not set. This value is calculated as follows:
        - `% Full = (('Cache Size' - 'Free Bytes') * 100) / ('Cache Size')`
- `Cache is XXX% soft full`
    - The percentage of the soft maximum size that is currently used. This line is displayed only if the soft maximum size is set. The `free bytes` in the cache statistics means the free bytes within the soft maximum limit. This value is calculated as follows:
        - `% soft Full = (('Soft max bytes' - 'Free Bytes') * 100) / ('Soft max  bytes')`
    - For more information about the soft maximum size, see [`-Xscmx`](xscmx.md).
- `Cache is accessible to current user`
    - Whether the current user can access the cache.

### `printAllStats`

```
-Xshareclasses:printAllStats
-Xshareclasses:printAllStats,name=<cache_name>
```

Displays the contents of the cache in chronological order. You can use this output to see the history of updates that were made to the cache. For layered caches, some information is shown for the top layer cache only, and some is shown for all layers combined. To see information for the top layer cache only, use [`printTopLayerStats=all`](#printtoplayerstats).

Each entry in the output starts with a VM ID, so you can see which VM wrote the associated data. Here are example entries for various types of cache data, with explanations:

#### Class paths

The following example shows one class path with 4 entries:

        1: 0x2234FA6C CLASSPATH
                /myVM/Apps/application1.jar
                /myVM/Apps/application2.jar
                /myVM/Apps/application3.jar
                /myVM/Apps/application4.jar

- `1`: the ID of the VM that wrote this data.
- `0x2234FA6C`: the address where this data is stored.
- `CLASSPATH`: the type of data that was written.

#### ROMClasses

This example shows an entry for a single `ROMClass`:

        1: 0x2234F7DC ROMCLASS: java/lang/Runnable at 0x213684A8
                Index 1 in class path 0x2234FA6C

- `1`: the ID of the VM that wrote this data.
- `0x2234F7DC`: the address where the metadata about the class is stored.
- `ROMCLASS`: the type of data that was stored.
- `java/lang/Runnable`: the name of the class.
- `0x213684A8`: the address where the class was stored.
- `Index 1`: the index in the class path where the class was loaded from.
- `0x2234FA6C`: the address of the class path against which this class is stored.

Stale classes are marked with `!STALE!`. Any partition or modification context that is used when the class is stored is also shown.

#### AOT methods

This example shows an entry for one AOT-compiled method:

        1: 0x00007F841A800554 AOT: hashCode Signature: ()I Address: 0x00007F83F6859280
                for ROMClass java/lang/Object at 0x00007F83F6859000.

- `1`: the ID of the VM that wrote this data.
- `0x00007F841A800554`: the address where the data is stored.
- `AOT`: the type of data that was stored.
- `hashCode`: the method for which AOT-compiled code is stored.
- `()I`: the signature of the ROM method.
- `0x00007F83F6859280`: the ROM method address.
- `java/lang/Object`: the class that contains the method.
- `0x00007F83F6859000`: the address of the class that contains the method.

Stale methods are marked with `!STALE!`.

#### URLs and tokens

The output for these data types has the same format as that for class paths, but with a single entry. A `Token` is a string that is passed to the Java `SharedClassHelper` API.

#### Zip entry caches

The following example shows 4 separate entries for zip entry caches:

        1: 0x042FE07C ZIPCACHE: luni-kernel.jar_347075_1272300300_1 Address: 0x042FE094 Size: 7898
        1: 0x042FA878 ZIPCACHE: luni.jar_598904_1272300546_1 Address: 0x042FA890 Size: 14195
        1: 0x042F71F8 ZIPCACHE: nio.jar_405359_1272300546_1 Address: 0x042F7210 Size: 13808
        1: 0x042F6D58 ZIPCACHE: annotation.jar_13417_1272300554_1 Address: 0x042F6D70 Size: 1023

- `1` : the ID of the VM that wrote this data.
- `0x042FE07C` : the address where the metadata for the zip entry cache is stored.
- `ZIPCACHE` : the type of data that was stored.
- `luni-kernel.jar_347075_1272300300_1` : the name of the zip entry cache.
- `0x042FE094` : the address where the data is stored.
- `7898` : the size of the stored data, in bytes.

#### JIT data

Information about JIT data is shown in `JITPROFILE` and `JITHINT` entries. For example:

        1: 0xD6290368 JITPROFILE: getKeyHash Signature: ()I Address: 0xD55118C0
        for ROMClass java/util/Hashtable$Entry at 0xD5511640.
        2: 0xD6283848 JITHINT: loadClass Signature: (Ljava/lang/String;)Ljava/lang/Class; Address: 0xD5558F98
        for ROMClass com/ibm/oti/vm/BootstrapClassLoader at 0xD5558AE0.

#### Startup hints

Information about startup hints is shown in `STARTUP HINTS KEY` and `STARTUP HINTS DETAIL`. For example:

        1: 0x000000002237C6E0 STARTUP HINTS KEY: -Xoptionsfile=jre\bin\compressedrefs\options.default -Xlockword:mode=default -Xjcl:jclse29 -Dcom.ibm.oti.vm.bootstrap.library.path=jre\bin\compressedrefs;jre\bin -Djava.home=jre -Djava.ext.dirs=jre\lib\ext -Duser.dir=bin -Djava.class.path=. -Dsun.java.launcher=SUN_STANDARD Address: 0x000000002237C700 Size: 96
        STARTUP HINTS DETAIL Flags: 1 DATA1: 1677721 DATA2: 5033165


### `printTopLayerStats`

Use this utility with a layered cache. This utility works in the same way as `printStats`. By default, this utility shows information for the top layer cache. To view statistics for a specific layer, use the `layer=<number>` option. For example, to show statistics for the second layer in a 2-layer cache, run `printTopLayerStats,layer=1`.

Example output:

```
Current statistics for cache "Cache1":

Cache created with:
        -Xnolinenumbers                      = false
        BCI Enabled                          = true
        Restrict Classpaths                  = false
        Feature                              = cr


base address                         = 0x00007F234C054000
end address                          = 0x00007F234D038000
allocation pointer                   = 0x00007F234C054000

cache layer                          = 1
cache size                           = 16776608
softmx bytes                         = 16776608
free bytes                           = 15299372
Reserved space for AOT bytes         = -1
Maximum space for AOT bytes          = -1
Reserved space for JIT data bytes    = -1
Maximum space for JIT data bytes     = -1
Metadata bytes                       = 792
Metadata % used                      = 0%
Class debug area size                = 1331200
Class debug area used bytes          = 0
Class debug area % used              = 0%

ROMClass bytes                       = 0
AOT bytes                            = 30520
JIT data bytes                       = 644
Zip cache bytes                      = 0
Startup hint bytes                   = 0
Data bytes                           = 114080

# ROMClasses                         = 0
# AOT Methods                        = 18
# Classpaths                         = 0
# URLs                               = 0
# Tokens                             = 0
# Zip caches                         = 0
# Startup hints                      = 0
# Stale classes                      = 0
% Stale classes                      = 0%


Cache is 8% full

Cache is accessible to current user = true
```

## Using the trace facility

Trace output contains very detailed information that might be used by a VM developer to diagnose complex problems with class data sharing. Trace is configured by using the [`-Xtrace`](xtrace.md) option and the binary output of the trace facility must be processed by the [Trace formatter](tool_traceformat.md) into a human-readable format.

The trace component for class data sharing is `j9shr`. Five levels of trace are available, starting from basic initialization and runtime information in level 1 up to the most detailed trace output in level 5.

To trace memory-mapped files, shared memory, and shared semaphores, include the `j9prt` trace component.

To trace operations with Java Helper API methods, include the `j9jcl` trace component.

<!-- ==== END OF TOPIC ==== shrc_diag_util.md ==== -->
