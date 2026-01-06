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

# Dump extractor (`jpackcore`)

**(AIX&reg;, Linux&reg;, macOS&reg;)**

On some operating systems, copies of executable files and libraries are required for a full analysis of a core dump (you can get some information from the dump without these files, but not as much). Run the `jpackcore` utility to collect these extra files and package them into an archive file along with the core dump. To analyze the output, use the [dump viewer (`jdmpview`)](tool_jdmpview.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This tool replaces OpenJ9 `jextract`, which is deprecated in Eclipse OpenJ9&trade; version 0.26.0.

![Start of content that applies to Java 21 (LTS) and later](cr/java21plus.png) The OpenJ9 `jextract` tool is removed from Java 21 and later. ![End of content that applies to Java 21 (LTS) and later](cr/java_close_lts.png)

## Syntax

    jpackcore [-r] [-x] <core file name> [<zip_file>]

where:

- `-r` forces the `jpackcore` utility to proceed when the system dump is created from an SDK with a different build ID. See **Restriction**.
- `-x` causes the `jpackcore` utility to omit the system dump itself from the archive produced. In its place, the file `excluded-files.txt` is added which names the excluded file.
- `<core file name>` is the name of the system dump.
- `<zip_file>` is the name you want to give to the processed file. If you do not specify a name, output is written to `<core file name>.zip` by default. The output is written to the same directory as the core file.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restriction:** You should run `jpackcore` on the same system that produced the system dump in order to collect the correct executables and libraries referenced in the system dump. You should also run `jpackcore` using the same VM level, to avoid any problems. From Eclipse OpenJ9 V0.24.0, the utility always checks that the build ID of the SDK that created the dump file matches the `jpackcore` build ID. Where these IDs do not match, the following exception is thrown:

```
J9RAS.buildID is incorrect (found XXX, expecting YYY). This version of jpackcore is incompatible with this dump (use '-r' option to relax this check).
```

To continue, despite the mismatch, use the `-r` option.

## See also

- [Dump viewer (`jdmpview`)](tool_jdmpview.md)

<!-- ==== END OF TOPIC ==== tool_jextract.md ==== -->
