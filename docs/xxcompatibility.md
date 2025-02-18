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

# -XX:Compatibility

This option enables a compatibility mode that OpenJ9 can run in to support applications that require specific capabilities. For example, support for applications that rely on vendor-specific command line arguments or to make available unspecified APIs.

## Syntax

        -XX:Compatibility=<mode>

Where, `<mode>` is the application for which the compatibility mode is provided. The `<mode>` is case-insensitive.

In release 0.43.0, the compatibility mode is provided for the Elasticsearch application only.

        -XX:Compatibility=Elasticsearch

If the `-XX:Compatibility` option is specified more than once, or with a mode other than "Elasticsearch" (not case-sensitive), the VM fails to start.

Although this option is available in all OpenJDK versions, the Elasticsearch application requires OpenJDK version 17 or later.

## Explanation

The Elasticsearch application was facing incompatibility issues when it was running on OpenJ9 and required many workarounds. With the `Elasticsearch` compatibility mode, OpenJ9 accepts and supports HotSpot-specific options or APIs that enhance compatibility to run the Elasticsearch application.

## See also

- [What's new in version 0.43.0](version0.43.md#new-xxcompatibility-option-added)

<!-- ==== END OF TOPIC ==== xxcompatibility.md ==== -->
