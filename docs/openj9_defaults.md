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

# Default settings for the OpenJ9 VM

The following tables provide a quick reference to the default settings for the VM when it is first installed.

The last 2 columns show whether the default setting can be changed by a command-line parameter or an environment variable. Note that if both are set, the command-line parameter always takes precedence.


|  VM setting                    | Default                 | Command line                                                                           | Env. variable                                                                                  |
|--------------------------------|-------------------------|:--------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------:|
|Javadump                        |Enabled                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Heapdump                        |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|System dump                     |Enabled                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Snap traces                     |Enabled                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|JIT dump                        |Enabled                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Verbose output                  |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |  :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>  |                                                           
|Compressed references           |(See **Note 1**)         |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Boot classpath search           |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                                
|JNI checks                      |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                                
|Remote debugging                |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                               
|Strict conformance checks       |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                                
|Quickstart                      |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                                
|Remote debug info server        |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                               
|Reduced signaling               |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                               
|Signal handler chaining         |Enabled                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                               
|Classpath                       |Not set                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Class data sharing              |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                              
|Accessibility support           |Enabled                  |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|JIT compiler                    |Enabled                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|AOT compiler (See **Note 2**)   |Enabled                  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                                
|JIT debug options               |Disabled                 |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>   |                                                                                                
|Java2D max size of fonts with algorithmic bold | 14 point |:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>  |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Java2D use rendered bitmaps in scalable fonts  | Enabled  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Java2D freetype font rasterizing|Enabled                  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|Java2D use AWT fonts            |Disabled                 | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

1. On AIX&reg;, Linux&reg;, macOS&reg;, and Windows&trade;: Enabled for **-Xmx** values &le; 57 GB, otherwise disabled.</p>

    On z/OS&reg;: Enabled for **-Xmx** values &le; 25 GB, otherwise disabled. With [APAR OA49416](https://www.ibm.com/support/docview.wss?uid=isg1OA49416), enabled for **-Xmx** values &le; 57 GB.

2. AOT is not used by the VM unless shared classes are also enabled.




|VM setting                                                    |AIX       |Linux    |macOS      |Windows          |z/OS      | Command line           | Env. variable          |
|--------------------------------------------------------------|----------|---------|----------|-----------------|----------|------------------------|------------------------|
|Default locale                                                |None      |None      |None     |N/A              |None      | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|
|Time to wait before starting plug-in                          |N/A       |Zero      |N/A       |N/A              |N/A       | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|
|Temporary directory                                           |`/tmp`    |`/tmp`    |`/tmp`    |`c:\temp`        |`/tmp`    | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|
|Plug-in redirection                                           |None      |None      |None      |N/A              |None      | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|
|IM switching                                                  |Disabled  |Disabled  |Disabled  |N/A              |Disabled  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|
|IM modifiers                                                  |Disabled  |Disabled  |Disabled  |N/A              |Disabled  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|
|Thread model                                                  |N/A       |N/A       |N/A       |N/A              |Native    | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>|
|Initial stack size for Java Threads **(32/64-bit)**. Use `-Xiss<size>`        |2 KB      |2 KB      |2 KB             |2 KB             |2 KB      |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |
|Maximum stack size for Java Threads **(32-bit)**. Use `-Xss<size>`  |320 KB    |320 KB    |N/A              |320 KB           |320 KB    |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |
|Maximum stack size for Java Threads **(64-bit)**. Use `-Xss<size>`  |1024 KB   |1024 KB   |1024 KB          |1024 KB          |1024 KB   |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |
|Stack size for OS Threads **(32-bit)**. Use `-Xmso<size>`           |256 KB    |256 KB    |N/A              |32 KB            |256 KB    |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |
|Stack size for OS Threads **(64-bit)**. Use `-Xmso<size>`           |512 KB    |256 KB (512 KB on PPC)    |256 KB           |256 KB           |1 MB      |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |
|Initial heap size. Use `-Xms<size>`                           |8 MB      |8 MB      |8 MB             |8 MB             |8 MB      |:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |
|Maximum Java heap size. Use `-Xmx<size>`                      |See **Notes**|See **Notes**|See **Notes**|See **Notes**|See **Notes**|:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>| :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span> |
|Page size for the Java object heap and code cache (For restrictions, see the `-Xlp:codecache` and `-Xlp:objectheap` options).|Operating system default|Architecture: x86: 2MB, IBM Z&reg;: 1MB, Other architectures: Operating system default |4 KB        | Operating system default | 1M pageable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  | :fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>|


:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Notes:**

The default value of [`-Xmx`](xms.md) :

- The value is 25% of the available memory with a maximum of 25 GB. However, where there is 2 GB or less of physical memory, the value set is 50% of available memory with a minimum value of 16 MB and a maximum value of 512 MB.

- On Linux&reg; sytems, if the VM is running in a container and [`-XX:+UseContainerSupport](xxusecontainersupport.md) is enabled, the value is 75% of the container memory limit, with a maximum of 25 GB. However, if the container memory limit is less than 1 GB, the value is 50% of the container memory limit. If the container memory limit is between 1GB and 2GB, the value is the container memory limit minus 512 MB.

- The default value is capped at 25 GB, which is the limit of heap size for 3-bit shift of compressed references (see [-Xcompressedrefs](xcompressedrefs.md)), to prevent silent switching to 4-bit shift of compressed references, which has possible performance penalties. You can use the [`-Xmx`](xms.md) option to overwrite the 25 GB limit.

- ![Start of content that applies only to Java 8 (LTS)](cr/java8.png) If you have set the [-XX:+OriginalJDK8HeapSizeCompatibilityMode](xxoriginaljdk8heapsizecompatibilitymode.md) option for compatibility with earlier releases, the value is half the available memory with a minimum of 16 MB and a maximum of 512 MB. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)

*Available memory* is defined as being the smallest of two values: The real or *physical* memory or the *RLIMIT_AS* value.
