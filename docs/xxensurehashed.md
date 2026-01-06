<!--
* Copyright (c) 2017, 2026 IBM Corp. and others
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

# -XX:\[+|-\]EnsureHashed

This option specifies classes to be pre-hashed. Objects that are created from these classes are hashed and extended with a slot to store the assigned hash value when the object is created or first moved during the garbage collection process. This option might improve performance for applications that frequently hash objects of a certain type.

## Syntax

        -XX:+EnsureHashed:<classes>
        -XX:-EnsureHashed:<classes>

| Setting                     | Effect           |
|-----------------------------|------------------|
| `-XX:+EnsureHashed:<classes>` | Specify classes  |
| `-XX:-EnsureHashed:<classes>` | Ignore classes |

Where `<classes>` is a comma-separated list of fully qualified class names, for example, `java/lang/String`.

## Explanation

The `-XX:+EnsureHashed:<classes>` option specifies the classes and the `-XX:-EnsureHashed:<classes>` option ignores classes that were previously specified. These options are parsed left to right.

For example, `-XX:+EnsureHashed:Class1,Class2,Class3 -XX:-EnsureHashed:Class2 -XX:+EnsureHashed:Class2,Class4 -XX:-EnsureHashed:Class1,Class3` results in the set of `EnsureHashed` classes {`Class2`, `Class4`}.

Objects that are created from classes `Thread` and `Class` are allocated in the tenure region directly and therefore, do not get moved by the garbage collector often. It takes time for such pre-tenured objects to get hashed and extended with a slot. To pre-hash those objects from the start and hence, improve the performance,`-XX:+EnsureHashed:java/lang/Class,java/lang/Thread` is added to the list of default options in the `options.default` file.

## See also

- [`-Xoptionsfile`](xoptionsfile.md)
- [GC processing](gc.md#gc-processing)
- [What's new in version 0.30.0](version0.30.md#new-xx-ensurehashed-option-added)

<!-- ==== END OF TOPIC ==== xxensurehashed.md ==== -->
