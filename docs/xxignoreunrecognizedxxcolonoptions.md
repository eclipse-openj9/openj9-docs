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

# -XX:\[+|-\]IgnoreUnrecognizedXXColonOptions

By default, any `-XX:` options that you specify on the command line are ignored if they are not recognized, which prevents an application failing to start. However, if you want to determine whether any of your `-XX:` options are unrecognized, you can turn off the behavior with this option. You might want to do this, for example, if you are switching to Eclipse OpenJ9&trade; from an alternative VM implementation where you are using `-XX:` options to tune the
runtime environment.

## Syntax

        -XX:[+|-]IgnoreUnrecognizedXXColonOptions

| Setting                            | Effect  | Default                                                                            |
|------------------------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+IgnoreUnrecognizedXXColonOptions` | Enable  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
| `-XX:-IgnoreUnrecognizedXXColonOptions` | Disable |                                                                               |

When you specify `-XX:-IgnoreUnrecognizedXXColonOptions`, if you also specify a `-XX:` option that is not recognized, that option is reported and the VM does not start. For example:

```
JVMJ9VM007E Command-line option unrecognised: -XX:InvalidOption
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.
```


<!-- ==== END OF TOPIC ==== xxignoreunrecognizedxxcolonoptions.md ==== -->
