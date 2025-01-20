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

# -XX:SharedCacheHardLimit


Specifies the size for a new shared classes cache. Use this option together with the `-Xscmx` option to set actual and soft maximum size limits respectively.

## Syntax

        -XX:SharedCacheHardLimit=<size>

| Setting   |  Value                             | Default  |
|-----------|------------------------------------|----------|
|`<size>`   | *[1[k\|K\|m\|M\|g\|G] or greater]* |          |

See [Using -X command-line options](x_jvm_commands.md) for more information about the `<size>` parameter.

When you use this option with the `-Xscmx` option, the `-Xscmx` option sets the soft maximum size, and the `-XX:SharedCacheHardLimit` option sets the actual size, of a new shared classes cache. For more information, see [-Xscmx](xscmx.md#xscmx "For a new shared classes cache, specifies either the actual size of the cache (if the -XX:SharedCacheHardLimit option is not present) or the soft maximum size of the cache (if used with the -XX:SharedCacheHardLimit option). In earlier releases, the default cache size is platform-dependent.").

If you use this option without the `-Xscmx` option, the behavior is the same as using the `-Xscmx` option by itself; both options set the actual size of the shared classes cache.

For more information about cache sizes, see [Cache size limits](xscmx.md#cache-size-limits).

## Example

The following settings, when used together, set the soft maximum size of the shared classes cache to 16 MB and the actual maximum cache size to 64 MB.

```
-XX:SharedCacheHardLimit=64m -Xscmx16m
```

## See also

- [-Xscmx](xscmx.md#xscmx "For a new shared classes cache, specifies either the actual size of the cache (if the -XX:SharedCacheHardLimit option is not present) or the soft maximum size of the cache (if used with the -XX:SharedCacheHardLimit option). In earlier releases, the default cache size is platform-dependent.")


<!-- ==== END OF TOPIC ==== xxsharedcachehardlimit.md ==== -->
