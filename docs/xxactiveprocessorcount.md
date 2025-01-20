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

# -XX:ActiveProcessorCount

This HotSpot option is recognized by Eclipse OpenJ9&trade; for compatibility. Use this option to override the number of CPUs that the VM automatically detects and uses when creating threads for various subsystems.

## Syntax

        -XX:ActiveProcessorCount=<n>

Where `<n>` is the number of CPUs.

| Setting | Value          | Default                                                                            |
|---------|----------------|:----------------------------------------------------------------------------------:|
| `<n>`   | *1 or greater* | There is no default value. This option is not enabled by default. If set to `0`, there is no effect.                  |

When you set this option the following line in a Java dump file is updated to indicate the number of CPUs specified:

```
2CIACTIVECPU Active CPUs
```

If this option is not set, the value for this line is `0` Active CPUs.

<!-- ==== END OF TOPIC ==== xxallowvmshutdown.md ==== -->
