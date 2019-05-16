<!--
* Copyright (c) 2017, 2019 IBM Corp. and others
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


# What's new in version 0.14.2

The following new features and notable changes since v.0.14.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Support for OpenSSL 1.0.2](#support-for-openssl-102)
- [OpenSSL Digest algorithm disabled](#openssl-digest-algorithm-disabled)


## Features and changes

### Binaries and supported environments

OpenJ9 release 0.14.2 supports OpenJDK 8 and 11. Binaries are available from the AdoptOpenJDK community at the following links:

- [OpenJDK version 8](https://adoptopenjdk.net/archive.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK version 11](https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=openj9)

The Windows (MSI) installer for these binaries can now be used to optionally install the IcedTea-Web package, which provides
equivalent functionality to Java Web Start. For more information about the installer, see the AdoptOpenJDK [Installation page](https://adoptopenjdk.net/installation.html). For more information about migrating to IcedTea-Web, read the AdoptOpenJDK
[Migration Guide](https://adoptopenjdk.net/migration.html).

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Support for OpenSSL 1.0.1

OpenSSL version 1.0.1 support is now enabled; Earlier releases supported only OpenJDK 1.0.2 and 1.1.x. On Linux&reg; and AIX&reg; platforms, the OpenSSL libraries are expected to be available on the system path. For more information about cryptographic acceleration with OpenSSL, see [Cryptographic operations](introduction.md#cryptographic-operations).

### OpenSSL Digest algorithm disabled

Due to issue [#5611](https://github.com/eclipse/openj9/issues/5611), the Digest algorithm is disabled. 


<!-- ==== END OF TOPIC ==== version0.14.2.md ==== -->
