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


# Migrating from Java 8 to Java 11

Support for OpenJDK 11 was added in OpenJ9 version 0.10.0.

The following new OpenJ9 features and notable changes apply when OpenJ9 is built with Java SE 11 class libraries. This information exists elsewhere in the documentation but is summarized here for convenience.

## Support for JDK enhancement proposals (JEP)

The new JEPs that are supported are listed in the following topics:

- JDK 9: [What's new in version 0.9.0](version0.9.md)
- JDK 10: [What's new in version 0.9.0](version0.9.md)
- JDK 11: [What's new in version 0.10.0](version0.10.md)

## New OpenJ9 features and changes

The following table lists the new OpenJ9 features and notable changes with the OpenJ9 release in which they were added:

| Features and changes  | OpenJ9 release|
|-----------------------|-------------------------------------|
|The path that is specified by the default [`-Xoptionsfile`](xoptionsfile.md) value is the `<java_home>/lib` directory, where `<java_home>` is the directory for your runtime environment. For Java 8, the path is the VM directory that is listed in [Directory conventions](openj9_directories.md).              | First release   |
| Low-overhead heap profiling is supported. [JEP 331](http://openjdk.java.net/jeps/331) provides a mechanism for sampling Java heap allocations with a low overhead via the JVM Tool Interface (JVMTI).<br><br>:fontawesome-solid-exclamation-triangle:{: .warn aria-hidden="true"} **Restrictions:** JEP 331 is implemented for OpenJ9 with the following limitations:<ul><li>The `balanced` and `metronome` garbage collection policies are not supported.</li><li>The JEP331 JVMTI agent and the Health Center agent both set a sampling interval, which by default is different. If both agents are used at the same time the Health Center agent will get incorrect results, unless the sampling intervals are adjusted to use the same value.</li>    |  0.15.0     |
| OpenSSL support for the ChaCha20 and ChaCha20-Poly1305 algorithms is available. The version of OpenSSL must be version 1.1.x or later. For more information, see [`-Djdk.nativeChaCha20`](djdknativechacha20.md).          | 0.15.0                 |
| The [`-verbose:module`](cmdline_general.md) option, which writes information to stderr for each module that is loaded and unloaded, is now supported.     | 0.25.0       |
|The default value of the [`-XX:MaxDirectMemorySize`](xxmaxdirectmemorysize.md) option, which limits the amount of heap memory that is used for direct byte buffers, is the same as the maximum heap size. For JDK 8, the VM default value is 87.5% of the maximum heap size.              | 0.32.0   |
