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

# -Xzero

Enables reduction of the memory footprint of the Java&trade; runtime environment when concurrently running multiple Java invocations.

This option is deprecated and will be removed in a future release.

![Start of content that applies only to Java 8 (LTS)](cr/java8.png) This option can be used only with Java SE version 8 runtime environments. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)

`-Xzero` might not be appropriate for all types of applications because it changes the implementation of `java.util.ZipFile`, which might cause extra memory usage.

## Syntax

| Setting                 | Effect                               |
|-------------------------|--------------------------------------|
| `-Xzero:none`           | Disable all sub options              |
| `-Xzero:describe`       | Prints the sub options in effect     |
| `-Xzero:sharebootzip`   | Enables the sharebootzip sub option  |
| `-Xzero:nosharebootzip` | Disables the sharebootzip sub option |

The following parameters are no longer supported. The options are parsed but do nothing:

| Setting                 | Effect                               |
|-------------------------|--------------------------------------|
| `-Xzero:j9zip`          | Enables the j9zip sub option         |
| `-Xzero:noj9zip`        | Disables the j9zip sub option        |
| `-Xzero:sharezip`       | Enables the sharezip sub option      |
| `-Xzero:nosharezip`     | Disables the sharezip sub option     |




<!-- ==== END OF TOPIC ==== xzero.md ==== -->
