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

# -XX:\[+|-\]EnableCRIUSupport

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

 This option enables or disables support for Checkpoint/Restore In Userspace (CRIU).

 :fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:** If CRIU support is enabled,

- you cannot use a Java&trade; security manager. If you try to specify a Java security manager, for example by using the `-Djava.security.manager` system property on the Java command line or the `java.lang.System.setSecurityManager` method in your application, the VM throws the `java.lang.UnsupportedOperationException` error.
- By default, `CRIUSECProvider` is the only security provider that is available in the checkpoint phase until restore. ![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) But, you can continue to use the existing security providers by disabling `CRIUSECProvider` with the [`-XX:-CRIUSecProvider`](xxcriusecprovider.md) option. ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)
- JITServer is disabled in the checkpoint phase even if the server exists and you have specified the `-XX:+UseJITServer` option. When you specify the `-XX:+EnableCRIUSupport` and [`-XX:+CRIURestoreNonPortableMode`](xxcriurestorenonportablemode.md) options along with the [`-XX:+UseJITServer`](xxusejitserver.md) option, and the server exists, the VM enables the JITServer server automatically at the restore point. The VM client connects to a JITServer in the context of CRIU as outlined in the following table (where :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> means that the VM connects to a JITServer instance and :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span> means that the VM does not connect to a JITServer instance):

|                               | Nonportable mode (default) <br> Pre-checkpoint | Nonportable mode (default) <br> Post restore | Portable mode Pre-checkpoint | Portable mode Post restore|
|-------------------------------|:-----------------:|:------------------:|:-------------:|:-------------:|
| Pre-checkpoint: No options <br>Post restore: No options  | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>    | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>  | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>  | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>  |
| Pre-checkpoint: No options <br>Post restore: `-XX:+UseJITServer` | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>    | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  |  :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>   | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>    |
| Pre-checkpoint: `-XX:+UseJITServer`<br>Post restore: No options | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>           | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>   |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>   |
| Pre-checkpoint: `-XX:+UseJITServer`<br>Post restore: `-XX:-UseJITServer` | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>    | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>  |  :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>   | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>    |
| Pre-checkpoint: `-XX:-UseJITServer`<br>Post restore: `-XX:+UseJITServer` | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>  | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>  | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>    | :fontawesome-solid-xmark:{: .no aria-hidden="true"}<span class="sr-only">no</span>    |

- you cannot use the `balanced` and `metronome` garbage collection (GC) policies. If you use the `-Xgcpolicy:balanced` or `-Xgcpolicy:metronome` options to specify those policies, the VM throws the following errors:

      ```
      JVMJ9GC090E -Xgcpolicy:balanced is currently unsupported with -XX:+EnableCRIUSupport
      JVMJ9VM007E Command-line option unrecognised: -Xgcpolicy:balanced
      Error: Could not create the Java Virtual Machine.
      Error: A fatal exception has occurred. Program will exit.
      ```

      ```
      JVMJ9GC090E -Xgcpolicy:metronome is currently unsupported with -XX:+EnableCRIUSupport
      JVMJ9VM007E Command-line option unrecognised: -Xgcpolicy:metronome
      Error: Could not create the Java Virtual Machine.
      Error: A fatal exception has occurred. Program will exit.
      ```

## Syntax

        -XX:[+|-]EnableCRIUSupport

| Setting               | Effect  | Default                                                                            |
|-----------------------|---------|:----------------------------------------------------------------------------------:|
| `-XX:+EnableCRIUSupport` | Enable  |                                                                                 |
| `-XX:-EnableCRIUSupport` | Disable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>  |


## Explanation

When you enable the option, the VM starts with the support for checkpoint and restore processes. The VM starts the JIT, GC, Security, and other internal components that make the checkpoint and restore points feasible. The option enables the use of `org.eclipse.openj9.criu.CRIUSupport` APIs. With CRIU support, you can take a checkpoint of the VM and restore the checkpoint image. When you specify the `-XX:+EnableCRIUSupport` option, the VM generates only those compiled codes that are portable (see [`-XX:[+|-]PortableSharedCache`](https://www.eclipse.org/openj9/docs/xxportablesharedcache/)) to facilitate restoring of the checkpoint image in multiple environments. VM does not load any code from the shared classes cache that is nonportable pre-checkpoint.

Taking a checkpoint and restoring the checkpoint image generally happens on different environments with different behaviors. OpenJ9 makes compensations for such differences to ensure successful restore. For example, the VM compensates for the downtime between checkpoint and restore for elapsed time sensitive APIs such as `System.nanotime()` and `java.util.Timer`.

For more information about the OpenJ9 compensations and Time APIs, see the Compensation section in the [OpenJ9 CRIU Support: A look under the hood (part II)](https://blog.openj9.org/2022/10/14/openj9-criu-support-a-look-under-the-hood-part-ii/) blog post.

## See also

- [CRIU support](criusupport.md)

<!-- ==== END OF TOPIC ==== xxenablecriusupport.md ==== -->
