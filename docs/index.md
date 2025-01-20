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

# Eclipse OpenJ9

Welcome to the user documentation for the Eclipse OpenJ9&trade; virtual machine (VM).

This user documentation supports the configuration, tuning, and diagnosis of the OpenJ9 VM in an OpenJDK runtime. However, due to differences between the Java SE class libraries, specific options might apply only to one Java SE version. Icons are used to indicate where differences apply. For example:

![Start of content that applies only to Java 11 (LTS)](cr/java11.png) This sentence applies only to Java 11 binaries that include the OpenJ9 VM. Icons for LTS releases are this colour. ![End of content that applies only to Java 11 (LTS)](cr/java_close_lts.png)

![Start of content that applies only to Java 16 and later](cr/java16plus.png) This sentence applies only to Java 16 or later binaries that include the OpenJ9 VM. Icons for feature releases are this colour. ![End of content that applies only to Java 16 or later](cr/java_close.png)

To see which Java releases are LTS releases and which are feature releases, and for information about release cadence, supported platforms, and build environments, see [Supported environments](openj9_support.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** Documentation to support OpenJ9 is still under construction. The current content covers
some high level information about OpenJ9 components together with the command-line options and environment variables that you can use to configure the VM when you start your application. Because OpenJ9 was contributed to the Eclipse Foundation by IBM, this content contains some links to additional information that forms part of the [IBM&reg; SDK, Java&trade; Technology Edition product documentation](https://www.ibm.com/support/knowledgecenter/SSYKE2/welcome_javasdk_family.html) in IBM Documentation. That content supplements the documentation here until a more complete set of user documentation is available.

We welcome contributions to the user documentation. If you would like to get involved, please read our [Contribution guidelines](https://github.com/eclipse-openj9/openj9-docs/blob/master/CONTRIBUTING.md). If you spot any errors in the documentation, please raise an [issue](https://github.com/eclipse-openj9/openj9-docs/issues/new?template=documentation-error.md) at our GitHub repository.

## Supported environments

OpenJDK binaries that contain the OpenJ9 VM are supported on a range of hardware and operating systems. This range is expanding as work progresses at the Eclipse foundation. See the [current list of supported environments](openj9_support.md) for details.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This user guide also contains information about configuring, tuning, and debugging OpenJ9 on the z/OS&reg; platform.

## Documentation for specific releases

Several versions of the documentation are available, covering all releases of OpenJ9:

- [Online documentation for the last release](https://www.eclipse.org/openj9/docs/index.html)
- [Online, in-progress documentation for the forthcoming release](https://eclipse-openj9.github.io/openj9-docs/)
- [Downloads of earlier releases](https://github.com/eclipse-openj9/openj9-docs/tree/master/downloads): to download a zip file, click the filename, then click **Download**. After downloading a `.zip` file, extract it, then open the `index.html` file in your browser.

## Useful links

- [Eclipse OpenJ9 website home page](https://www.eclipse.org/openj9)
- [Eclipse OpenJ9 GitHub repository](https://github.com/eclipse-openj9/openj9)
- [Eclipse Foundation OpenJ9 project page](https://projects.eclipse.org/projects/technology.openj9)

<!-- ==== END OF TOPIC ==== index.md ==== -->
