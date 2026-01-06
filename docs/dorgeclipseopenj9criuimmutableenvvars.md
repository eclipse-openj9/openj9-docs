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

# -Dorg.eclipse.openj9.criu.ImmutableEnvVars

This system property specifies the environment variables that you want to add to the `ImmutableEnvVars` list.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** This option takes effect only when the `-XX:+EnableCRIUSupport` option is enabled. This option can be used only during the checkpoint phase.

## Syntax

        -Dorg.eclipse.openj9.criu.ImmutableEnvVars=<environment_variables>

Where `<environment_variables>` is a comma-separated list of environment variables. For example:

```
-Dorg.eclipse.openj9.criu.ImmutableEnvVars=INVOKED,WLP_USER_DIR,WLP_OUTPUT_DIR,LOG_DIR,X_LOG_DIR,LOG_FILE,X_LOG_FILE,VARIABLE_SOURCE_DIRS,X_CMD
```

The following environment variables are included in the list by default and cannot be removed:

- `LANG`
- `LC_ALL`
- `LC_CTYPE`

## Explanation

The environment variables are a property of the operating system environment and not the VM. The VM considers these properties as immutable and does not allow the environment variables to change values between checkpoint and restore.

During the checkpoint and restore phases, the VM can detect the environment variables that are in the `immutableEnvvars` list. You can add any other immutable variables that you want the VM to detect in the `ImmutableEnvVars` list with the `-Dorg.eclipse.openj9.criu.ImmutableEnvVars` option. If an environment variable is not in the list, the VM does not detect that variable even if the underlying system has defined it. These environment variables exist for the entire lifetime of the VM (checkpoint and restore).

At restore, the VM can also detect the environment variables that are added through the `CRIUSupport.registerRestoreEnvVariables` API.

For more information about how environment variables are handled during the checkpoint and restore process, see the Environment Variables section in the [OpenJ9 CRIU Support: A look under the hood (part II)](https://blog.openj9.org/2022/10/14/openj9-criu-support-a-look-under-the-hood-part-ii/) blog post.

## See also

- [CRIU support](criusupport.md)

<!-- ==== END OF TOPIC ==== dorgeclipseopenj9criuimmutableenvvars.md ==== -->
