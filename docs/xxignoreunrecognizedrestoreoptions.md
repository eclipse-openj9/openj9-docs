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

# -XX:\[+|-\]IgnoreUnrecognizedRestoreOptions

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

This option specifies whether the VM ignores any unrecognized options at the time of restore.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This option takes effect only when `-XX:+EnableCRIUSupport` is enabled. This option can be used only on restore.


## Syntax

        -XX:[+|-]IgnoreUnrecognizedRestoreOptions

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+IgnoreUnrecognizedRestoreOptions` | Enable  |                                                                                 |
| `-XX:-IgnoreUnrecognizedRestoreOptions` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  |


## Explanation

When you restore a checkpoint image, you can specify additional command-line options by using the `CRIUSupport.registerRestoreOptionsFile` API. You can also use the `OPENJ9_RESTORE_JAVA_OPTIONS` environment variable (`CRIUSupport.registerRestoreEnvVariables` API) to add the VM options on restore. If you specify options that do not exist, or are unsupported, the VM, by default, fails to start and throws a `JVMRestoreException` error. The `-XX:+IgnoreUnrecognizedRestoreOptions` option disables the failure and allows the VM to continue even if some options are not used.

## See also

- [CRIU support](criusupport.md)

<!-- ==== END OF TOPIC ==== xxignorenrecognizedestoreptions.md ==== -->
