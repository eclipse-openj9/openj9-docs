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
# Eclipse Foundation Software User Agreement

August 31, 2017

### Usage Of Content

THE ECLIPSE FOUNDATION MAKES AVAILABLE SOFTWARE, DOCUMENTATION, INFORMATION AND/OR OTHER MATERIALS FOR OPEN SOURCE PROJECTS
(COLLECTIVELY *CONTENT*).  USE OF THE CONTENT IS GOVERNED BY THE TERMS AND CONDITIONS OF THIS AGREEMENT AND/OR THE TERMS AND
CONDITIONS OF LICENSE AGREEMENTS OR NOTICES INDICATED OR REFERENCED BELOW.  BY USING THE CONTENT, YOU AGREE THAT YOUR USE
OF THE CONTENT IS GOVERNED BY THIS AGREEMENT AND/OR THE TERMS AND CONDITIONS OF ANY APPLICABLE LICENSE AGREEMENTS OR
NOTICES INDICATED OR REFERENCED BELOW.  IF YOU DO NOT AGREE TO THE TERMS AND CONDITIONS OF THIS AGREEMENT AND THE TERMS AND
CONDITIONS OF ANY APPLICABLE LICENSE AGREEMENTS OR NOTICES INDICATED OR REFERENCED BELOW, THEN YOU MAY NOT USE THE CONTENT.

### Applicable Licenses

Unless otherwise indicated, all Content made available by the Eclipse Foundation is provided to you under the terms and conditions of the Eclipse Public License Version 2.0
   (*EPL*).  A copy of the EPL is provided with this Content and is also available at [https://www.eclipse.org/legal/epl-2.0</a>.](https://www.eclipse.org/legal/epl-2.0).
   For purposes of the EPL, *Program*; will mean the Content.</p>

Content includes, but is not limited to, source code, object code, documentation and other files maintained in the Eclipse Foundation source code
   repository (*Repository*) in software modules (*Modules*) and made available as downloadable archives (*Downloads*).

- Content may be structured and packaged into modules to facilitate delivering, extending, and upgrading the Content.  Typical modules may include plug-ins (*Plug-ins*), plug-in fragments (*Fragments*), and features (*Features*).
- Each Plug-in or Fragment may be packaged as a sub-directory or JAR (Java&trade; ARchive) in a directory named *plugins*.
- A Feature is a bundle of one or more Plug-ins and/or Fragments and associated material.  Each Feature may be packaged as a sub-directory in a directory named *features*.  Within a Feature, files named *feature.xml* may contain a list of the names and version numbers of the Plug-ins
and/or Fragments associated with that Feature.
- Features may also include other Features (*Included Features*). Within a Feature, files named *feature.xml* may contain a list of the names and version numbers of Included Features.


The terms and conditions governing Plug-ins and Fragments should be contained in files named *about.html* (*Abouts*). The terms and conditions governing Features and
Included Features should be contained in files named *license.html* (*Feature Licenses*).  Abouts and Feature Licenses may be located in any directory of a Download or Module
including, but not limited to the following locations:

- The top-level (root) directory
- Plug-in and Fragment directories
- Inside Plug-ins and Fragments packaged as JARs
- Sub-directories of the directory named *src* of certain Plug-ins
- Feature directories

Note: if a Feature made available by the Eclipse Foundation is installed using the Provisioning Technology (as defined below), you must agree to a license (*Feature Update License*) during the installation process.  If the Feature contains Included Features, the Feature Update License should either provide you with the terms and conditions governing the Included Features or inform you where you can locate them.  Feature Update Licenses may be found in the *license* property of files named *feature.properties* found within a Feature. Such Abouts, Feature Licenses, and Feature Update Licenses contain the terms and conditions (or references to such terms and conditions) that govern your use of the associated Content in that directory.

THE ABOUTS, FEATURE LICENSES, AND FEATURE UPDATE LICENSES MAY REFER TO THE EPL OR OTHER LICENSE AGREEMENTS, NOTICES OR TERMS AND CONDITIONS.  SOME OF THESE
OTHER LICENSE AGREEMENTS MAY INCLUDE (BUT ARE NOT LIMITED TO):

- Eclipse Distribution License Version 1.0 (available at [https://www.eclipse.org/licenses/edl-v10.html](https://www.eclipse.org/licenses/edl-v1.0.html)
- Eclipse Distribution License Version 2.0 (available at [https://www.eclipse.org/legal/epl-2.0](https://www.eclipse.org/legal/epl-2.0)
- Common Public License Version 1.0 (available at [https://www.eclipse.org/legal/cpl-v10.html](https://www.eclipse.org/legal/cpl-v10.html)
- Apache Software License 1.1 (available at [http://www.apache.org/licenses/LICENSE](http://www.apache.org/licenses/LICENSE)
- Apache Software License 2.0 (available at [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
- Mozilla Public License Version 1.1 (available at [http://www.mozilla.org/MPL/MPL-1.1.html](http://www.mozilla.org/MPL/MPL-1.1.html)


IT IS YOUR OBLIGATION TO READ AND ACCEPT ALL SUCH TERMS AND CONDITIONS PRIOR TO USE OF THE CONTENT.  If no About, Feature License, or Feature Update License is provided, please contact the Eclipse Foundation to determine what terms and conditions govern that particular Content.


### Use of Provisioning Technology

The Eclipse Foundation makes available provisioning software, examples of which include, but are not limited to, p2 and the Eclipse
Update Manager (*Provisioning Technology*) for the purpose of allowing users to install software, documentation, information and/or
other materials (collectively *Installable Software*). This capability is provided with the intent of allowing such users to
install, extend and update Eclipse-based products. Information about packaging Installable Software is available at
[https://www.eclipse.org/equinox/p2/repository_packaging.html](https://www.eclipse.org/equinox/p2/repository_packaging.html)
(*Specification*).

You may use Provisioning Technology to allow other parties to install Installable Software. You shall be responsible for enabling the
applicable license agreements relating to the Installable Software to be presented to, and accepted by, the users of the Provisioning Technology
in accordance with the Specification. By using Provisioning Technology in such a manner and making it available in accordance with the
Specification, you further acknowledge your agreement to, and the acquisition of all necessary rights to permit the following:

- A series of actions may occur (*Provisioning Process*) in which a user may execute the Provisioning Technology
on a machine (*Target Machine*) with the intent of installing, extending or updating the functionality of an Eclipse-based
product.
- During the Provisioning Process, the Provisioning Technology may cause third party Installable Software or a portion thereof to be
accessed and copied to the Target Machine.
- Pursuant to the Specification, you will provide to the user the terms and conditions that govern the use of the Installable
Software (*Installable Software Agreement*) and such Installable Software Agreement shall be accessed from the Target
Machine in accordance with the Specification. Such Installable Software Agreement must inform the user of the terms and conditions that govern
the Installable Software and must solicit acceptance by the end user in the manner prescribed in such Installable Software Agreement. Upon such
indication of agreement by the user, the provisioning Technology will complete installation of the Installable Software.

### Cryptography

Content may contain encryption software. The country in which you are currently may have restrictions on the import, possession, and use, and/or re-export to
another country, of encryption software. BEFORE using any encryption software, please check the country's laws, regulations and policies concerning the import,
possession, or use, and re-export of encryption software, to see if this is permitted.

Java and all Java-based trademarks are trademarks of Oracle Corporation in the United States, other countries, or both.

### Third-party code

These notices are provided for informational purposes. The third party tools are not distributed with the source files.

**Section A: SUMMARY**

- A1. Mkdocs 0.17
- A2. Mkdocs-material 2
- A3. Pygments 2.2

END OF Section A: SUMMARY

**Section B: NOTICES**

B1. [Mkdocs](http://www.mkdocs.org/about/license/)

BSD license

Copyright © 2014, Tom Christie. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

B2. [Mkdocs-material](https://squidfunk.github.io/mkdocs-material/license/)

MIT License

Copyright © 2016 - 2017 Martin Donath

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

B3. [Pygments](https://pypi.python.org/pypi/Pygments)

Copyright (c) 2006-2017 by the respective authors (see AUTHORS file).
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

- Redistributions of source code must retain the above copyright
  notice, this list of conditions and the following disclaimer.

- Redistributions in binary form must reproduce the above copyright
  notice, this list of conditions and the following disclaimer in the
  documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

END OF Section B: Notices
