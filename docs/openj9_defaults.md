<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
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
|Javadump                        |Enabled                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Heapdump                        |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|System dump                     |Enabled                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Snap traces                     |Enabled                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|JIT dump                        |Enabled                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Verbose output                  |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Compressed references           |(See **Note 1**)         |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Boot classpath search           |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|JNI checks                      |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Remote debugging                |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Strict conformance checks       |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Quickstart                      |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Remote debug info server        |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Reduced signaling               |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Signal handler chaining         |Enabled                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Classpath                       |Not set                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Class data sharing              |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Accessibility support           |Enabled                  |                                                                                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|JIT compiler                    |Enabled                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|AOT compiler (See **Note 2**)   |Enabled                  |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|JIT debug options               |Disabled                 |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">command-line</span> |                                                                                                |
|Java2D max size of fonts with algorithmic bold | 14 point |                                                                                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Java2D use rendered bitmaps in scalable fonts  | Enabled  |                                                                                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Java2D freetype font rasterizing|Enabled                  |                                                                                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |
|Java2D use AWT fonts            |Disabled                 |                                                                                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">environment variable</span> |

<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span class="sr-only">Notes</span> **Notes:**

1. On AIX<sup>&reg;</sup>, Linux<sup>&trade;</sup>, and Windows<sup>&trade;</sup>: Enabled for **-Xmx** values &le; 57 GB, otherwise disabled.</p>

    On z/OS<sup>&reg;</sup>: Enabled for **-Xmx** values &le; 25 GB, otherwise disabled. With <i class="fa fa-external-link" aria-hidden="true"></i> [APAR OA49416](http://www.ibm.com/support/docview.wss?uid=isg1OA49416), enabled for **-Xmx** values &le; 57 GB.

2. AOT is not used by the VM unless shared classes are also enabled.




|VM setting                                                    |AIX       |Linux     |Windows          |z/OS      | Command line           | Env. variable          |
|--------------------------------------------------------------|----------|----------|-----------------|----------|------------------------|------------------------|
|Default locale                                                |None      |None      |N/A              |None      |                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by an environment variable</span>|
|Time to wait before starting plug-in                          |N/A       |Zero      |N/A              |N/A       |                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by an environment variable</span>|
|Temporary directory                                           |`/tmp`    |`/tmp`    |`c:\temp`        |`/tmp`    |                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by an environment variable</span>|
|Plug-in redirection                                           |None      |None      |N/A              |None      |                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by an environment variable</span>|
|IM switching                                                  |Disabled  |Disabled  |N/A              |Disabled  |                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by an environment variable</span>|
|IM modifiers                                                  |Disabled  |Disabled  |N/A              |Disabled  |                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by an environment variable</span>|
|Thread model                                                  |N/A       |N/A       |N/A              |Native    |                        |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by an environment variable</span>|
|Initial stack size for Java<sup>&trade;</sup> Threads 32-bit. Use `‑Xiss<size>`|2 KB      |2 KB      |2 KB             |2 KB      |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Maximum stack size for Java Threads 32-bit. Use `‑Xss<size>`  |320 KB    |320 KB    |320 KB           |320 KB    |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Stack size for OS Threads 32-bit. Use `‑Xmso<size>`           |256 KB    |256 KB    |32 KB            |256 KB    |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Initial stack size for Java Threads 64-bit. Use `‑Xiss<size>` |2 KB      |2 KB      |2 KB             |2 KB      |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Maximum stack size for Java Threads 64-bit. Use `‑Xss<size>`  |1024 KB   |1024 KB   |1024 KB          |1024 KB   |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Stack size for OS Threads 64-bit. Use `‑Xmso<size>`           |256 KB    |256 KB    |256 KB           |256 KB    |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Initial heap size. Use `‑Xms<size>`                           |8 MB      |8 MB      |8 MB             |8 MB      |<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Maximum Java heap size. Use `‑Xmx<size>`                      |See **Notes**|See **Notes**|See **Notes**|See **Notes**|<i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>|                        |
|Page size for the Java object heap and code cache (For restrictions, see the `‑Xlp:codecache` and `‑Xlp:objectheap` options).|Operating system default                                                           |Architecture: x86: 2MB, IBM Z<sup>&reg;</sup>: 1MB, Other architectures: Operating system default | Operating system default | 1M pageable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Controlled by a command line option</span>  |                                                                                                                                                                                                                                                                           


<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span class="sr-only">Notes</span> **Notes:**

The default value of `-Xmx` depends on the version of Java.

Java 8
: The value is half the available memory with a minimum of 16MB and a maximum of 512 MB.    

Java 9 and later
: The value is 25% of the available memory with a maximum of 25 GB. However, where there is 2 GB or less of physical memory, the value set is 50% of available memory with a minimum value of 16 MB
    and a maximum value of 512 MB.

*Available memory* is defined as being the smallest of two values: The real or *physical* memory or the *RLIMIT_AS* value.
