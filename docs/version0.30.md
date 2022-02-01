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

# What's new in version 0.30.0

The following new features and notable changes since version 0.29.0 are included in this release:

- [New binaries and changes to supported environments](#binaries-and-supported-environments)
- [Changes to the shared classes cache generation number](#changes-to-the-shared-classes-cache-generation-number)
- [Ignored options now captured in java dumps](#ignored-options-captured-in-java-dumps)
- [New `-XX:[+|-]EnsureHashed` option added](#new-xx-ensurehashed-option-added)

## Features and changes

### Binaries and supported environments

OpenJ9 release 0.30.0 supports OpenJDK 8, 11 and 17.

To learn more about support for OpenJ9 releases, including OpenJDK levels and platform support, see [Supported environments](openj9_support.md).

### Changes to the shared classes cache generation number

The format of classes that are stored in the shared classes cache is changed, which causes the JVM to create a new shared classes cache rather than re-creating or reusing an existing cache. To save space, you can remove all existing shared caches unless they are in use by an earlier release. For more information about the `-Xshareclasses` option, including the `destroy` options that you can use to remove caches, see [`-Xshareclasses`](xshareclasses.md).

### Ignored options captured in java dumps

For improved compatibility with other Java implementations, OpenJ9 ignores many command-line options. If any were ignored, they are now listed in java dump files. For example, this command
<pre>
java -Xdump:java:events=vmstop -XX:+UseCompressedOop -XX:CompressedClassSpaceSize=528482304 -version
</pre>
would yield the following in the <tt>ENVINFO</tt> section after the complete list of all command-line arguments
<pre>
1CIIGNOREDARGS Ignored Args:
2CIIGNOREDARG            -XX:+UseCompressedOop
2CIIGNOREDARG            -XX:CompressedClassSpaceSize=528482304
</pre>

### New `-XX:[+|-]EnsureHashed` option added

This option specifies/unspecifies classes of objects that will be hashed and extended with a hash slot upon object creation or first move. This option may improve performance for applications that frequently hash objects of a certain type. See [-XX:[+|-]EnsureHashed](xxensurehashed.md) for more details.

## Known problems and full release information

To see known problems and a complete list of changes between Eclipse OpenJ9 v0.29.0 and v0.30.0 releases, see the [Release notes](https://github.com/eclipse-openj9/openj9/blob/master/doc/release-notes/0.30/0.30.md).

<!-- ==== END OF TOPIC ==== version0.30.md ==== -->
