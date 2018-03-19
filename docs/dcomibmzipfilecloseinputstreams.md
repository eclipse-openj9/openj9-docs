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

# -Dcom.ibm.zipfile.closeinputstreams


The `Java.util.zip.ZipFile` class allows you to create `InputStreams` on files held in a compressed archive.

## Syntax

        -Dcom.ibm.zipfile.closeinputstreams=[true|false]

| Setting      | Effect  | Default                                                                            |
|--------------|---------|:----------------------------------------------------------------------------------:|
| true         | Enable  |                                                                                    |
| false        | Disable | <i class="fa fa-check" aria-hidden="true"></i><span class="sr-only">Default</span> |

## Explanation

Under some conditions, using `ZipFile.close()` to close all `InputStreams` that have been opened on the compressed archive might result in a 56-byte-per-`InputStream` native memory leak. Setting the `-Dcom.ibm.zipfile.closeinputstreams=true` forces the OpenJ9 VM to track and close `InputStreams` without the memory impact caused by retaining native-backed objects. Native-backed objects are objects that are stored in native memory, rather than the Java<sup>&trade;</sup> heap. By default, the value of this system property is not enabled.



<!-- ==== END OF TOPIC ==== dcomibmzipfilecloseinputstreams.md ==== -->
