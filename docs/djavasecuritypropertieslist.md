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

# -Djava.security.propertiesList

This system property is used to specify different java.security property files that have the profiles you want to use instead of putting those different profiles into a single java.security file.

## Syntax

        -Djava.security.propertiesList=<file_names>

For example,

Windows&trade; systems: `-Djava.security.propertiesList=file1;file2;file3`
Unix&reg; type systems: `-Djava.security.propertiesList=file1:fil2:file3`

## See also

- [What's new in version 0.55.0](version0.55.md#new-system-property-added-to-support-a-list-of-javasecurity-property-files)

<!-- ==== END OF TOPIC ==== djavasecuritypropertieslist.md ==== -->
