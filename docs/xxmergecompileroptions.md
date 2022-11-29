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

# -XX:[+|-]MergeCompilerOptions

This option enables or disables the merging of multiple `-Xjit` or `-Xaot` options into a single `-Xjit` or `-Xaot` option.

## Syntax

        -XX:[+|-]MergeCompilerOptions

| Setting                    | Effect  | Default                                                                              |
|----------------------------|---------|:------------------------------------------------------------------------------------:|
|`-XX:+MergeCompilerOptions` | Enable  |                                                                                      |
|`-XX:-MergeCompilerOptions` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

 By default, if you specify `-Xjit` option (or `-Xaot` option) multiple times, only its last option takes effect. Therefore, on adding an `-Xjit` option, the resultant behavior might not be as expected, if any instance of this option already exists. It is time-consuming to find the existing instances and try combining them manually so that the system behaves as required.

 You can use the `-XX:+MergeCompilerOptions` option to merge all the existing instances of the `-Xjit` options logically. To retain the default behavior, whereby only the last option takes effect, you can use the `-XX:-MergeCompilerOptions` option.

 If both `-Xjit` and `-Xaot` options exist and you use the `-XX:+MergeCompilerOptions` option, then multiple `-Xjit` options are merged into a single `-Xjit` option and multiple `-Xaot` options are merged into a single `-Xaot` option.

## Examples

### No compiler options:

```
java -Xshareclasses:none -version
```
`-Xjit` option applied - N/A

```
java -XX:+MergeCompilerOptions -Xshareclasses:none -version
```
`-Xjit` option applied - N/A

```
java -XX:-MergeCompilerOptions -Xshareclasses:none -version
```
`-Xjit` option applied - N/A

### One `-Xjit` option:

```
java -Xshareclasses:none  -Xjit:version -version
```
`-Xjit` option applied - `version`

```
java -XX:+MergeCompilerOptions -Xshareclasses:none  -Xjit:version -version
```
`-Xjit` option applied - `version`

```
java -XX:-MergeCompilerOptions -Xshareclasses:none  -Xjit:version -version
```
`-Xjit` option applied - `version`

### Multiple `-Xjit` options:

```
java -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` option applied - `version`

```
java -XX:+MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` options applied - `verbose={compilePerformance}`,`vlog=vlog`,`version`

```
java -XX:-MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` option applied - `version`

### Both `-XX:+MergeCompilerOptions` and `-XX:-MergeCompilerOptions`:

If there are multiple `-XX:[+|-]MergeCompilerOptions` options with multiple `-Xjit` options, the last `-XX:[+|-]MergeCompilerOptions` setting takes effect before the `-Xjit` options are processed.

```
java -XX:+MergeCompilerOptions -XX:-MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` option applied - `version`

```
java -XX:-MergeCompilerOptions -XX:+MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` options applied - `{compilePerformance}`,`vlog=vlog`,`version`

## See also

- [-Xjit](xjit.md)
- [-Xaot](xaot.md)

<!-- ==== END OF TOPIC ==== xxmergecompileroptions.md ==== -->
