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

# -XX:\[+|-\]EnsureHashed

Objects allocated from the specified classes will be hashed and extended with a slot to store the hash upon object creation or first move. This option may improve performance for applications that frequently hash objects of a certain type.

## Syntax

        -XX:+EnsureHashed:<classes>
        -XX:-EnsureHashed:<classes>

| Setting                     | Effect           |
|-----------------------------|------------------|
|`-XX:+EnsureHashed:<classes>`| Specify classes  |
|`-XX:-EnsureHashed:<classes>`| Ignore classes|

Where `<classes>` is a comma-separated list of fully qualified class names, e.g. `java/lang/String`.

The `-XX:+EnsureHashed:<classes>` option specifies the classes and the `-XX:-EnsureHashed:<classes>` option ignores classes that were previously specified. These options are parsed left to right.

For example, `-XX:+EnsureHashed:Class1,Class2,Class3 -XX:-EnsureHashed:Class2 -XX:+EnsureHashed:Class2,Class4 -XX:-EnsureHashed:Class1,Class3` results in the set of `EnsureHashed` classes {`Class2`, `Class4`}.

<!-- ==== END OF TOPIC ==== xensurehashed.md ==== -->
