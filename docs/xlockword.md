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

# -Xlockword

Test whether performance optimizations are negatively impacting an application.

## Syntax

        -Xlockword:<parameters>

## Parameters

### `mode`

        -Xlockword:mode=all
        -Xlockword:mode=default

Locking optimizations typically reduce memory usage and improve performance. However, there might be some situations where a smaller heap size is achieved for an application, but overall application performance decreases.

For example, if your application synchronizes on objects that are not typically synchronized on, such as `Java.lang.String`, run the following test:  
Use the following command-line option to revert to behavior that is closer to earlier versions and monitor application performance:

    -Xlockword:mode=all

If performance does not improve, remove the previous command-line options or use the following command-line option to reestablish the new behavior:

    -Xlockword:mode=default

### `nolockword`

        -Xlockword:nolockword=<class_name>

: Removes the lockword from object instances of the class `<class_name>`, reducing the space required for these objects. However, this action might have an adverse effect on synchronization for those objects.

    You should only use this option for troubleshooting.

### `what`

        -Xlockword:what

: Shows the current lockword configuration.

<!-- ==== END OF TOPIC ==== xlockword.md ==== -->
