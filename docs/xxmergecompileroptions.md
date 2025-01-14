<!--
* Copyright (c) 2017, 2024 IBM Corp. and others
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

# -XX:[+|-]MergeCompilerOptions

This option enables or disables the merging of multiple `-Xjit` or `-Xaot` options into a single `-Xjit` or `-Xaot` option.

## Syntax

        -XX:[+|-]MergeCompilerOptions

| Setting                    | Effect  | Default                                                                              |
|----------------------------|---------|:------------------------------------------------------------------------------------:|
|`-XX:+MergeCompilerOptions` | Enable  |                                                                                      |
|`-XX:-MergeCompilerOptions` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |

## Explanation

By default, if you specify `-Xjit` option (or `-Xaot` option) multiple times, only the last option takes effect. Therefore, on adding an `-Xjit` option, the resultant behavior might not be as expected, if any instance of this option already exists. It is time-consuming to find the existing instances and try combining them manually so that the system behaves as required.

You can use the `-XX:+MergeCompilerOptions` option to merge all the existing instances of the `-Xjit` options (or `-Xaot` options) logically. To retain the default behavior, whereby only the last option takes effect, you can use the `-XX:-MergeCompilerOptions` option.

If both `-Xjit` and `-Xaot` options exist and you use the `-XX:+MergeCompilerOptions` option, then multiple `-Xjit` options are merged into a single `-Xjit` option and multiple `-Xaot` options are merged into a single `-Xaot` option.

Typically, `-Xjit` and `-Xaot` options are ignored if these options are followed by `-Xint` or `-Xnojit` or `-Xnoaot` options:

- `-Xint` option overrides any `-Xjit` and `-Xaot` options
- `-Xnojit` option overrides `-Xjit` options
- `-Xnoaot` option overrides `-Xaot` options

However, with `-XX:+MergeCompilerOptions` all `-Xjit` and `-Xaot` options are merged, even if those options are followed by `-Xint`, `-Xnojit`, or `-Xnoaot` options. At least one `-Xjit` or `-Xaot` option must be specified after these options.

## Examples

### One `-Xjit` option

```
java -Xshareclasses:none  -Xjit:version -version
```
`-Xjit` option that is applied - `version`

```
java -XX:+MergeCompilerOptions -Xshareclasses:none  -Xjit:version -version
```
`-Xjit` option that is applied - `version`

```
java -XX:-MergeCompilerOptions -Xshareclasses:none  -Xjit:version -version
```
`-Xjit` option that is applied - `version`

### Multiple `-Xjit` options

```
java -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` option that is applied - `version`

```
java -XX:+MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` options that are applied - `verbose={compilePerformance}`,`vlog=vlog`,`version`

```
java -XX:-MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` option that is applied - `version`

### Both `-XX:+MergeCompilerOptions` and `-XX:-MergeCompilerOptions`

If there are multiple `-XX:[+|-]MergeCompilerOptions` options with multiple `-Xjit` options, the last `-XX:[+|-]MergeCompilerOptions` setting takes effect before the `-Xjit` options are processed.

```
java -XX:+MergeCompilerOptions -XX:-MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` option that is applied - `version`

```
java -XX:-MergeCompilerOptions -XX:+MergeCompilerOptions -Xshareclasses:none '-Xjit:verbose={compilePerformance},vlog=vlog' -Xjit:version -version
```
`-Xjit` options that are applied - `verbose={compilePerformance}`,`vlog=vlog`,`version`

### `-Xnojit`, `-Xnoaot`, or `-Xint` options with and without `-XX:+MergeCompilerOptions`

```
java -Xshareclasses:none -Xjit:version -Xnojit -version
```
No `-Xjit` option is applied

```
java -XX:+MergeCompilerOptions -Xshareclasses:none -Xjit:verbose={compilePerformance} -Xjit:vlog=vlog -Xint -Xjit:version -version
```
`-Xjit` options that are applied - `verbose={compilePerformance}`,`vlog=vlog`,`version`

```
java -XX:+MergeCompilerOptions -Xshareclasses:none -Xjit:version -Xnojit -version
```
No `-Xjit` option is applied because no `-Xjit` option is specified after the `-Xnojit` option

## See also

- [-Xjit](xjit.md)
- [-Xaot](xaot.md)

<!-- ==== END OF TOPIC ==== xxmergecompileroptions.md ==== -->
