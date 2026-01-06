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

# CRIU support

**(Linux&reg; x86, Linux on POWER&reg; (Little Endian), Linux on AArch64, and Linux on IBM Z&reg; only)**

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

- Support for the Checkpoint/Restore In Userspace (CRIU) tool is currently provided as a technical preview in container environments. CRIU support is available for the customized CRIU version that is packaged with the Semeru container image. This preview is supported for use in production environments, however, all APIs and command-line options are subject to change.
- CRIU is supported only on Red Hat Universal Images (UBI) 8 and 9.

In OpenJ9, the CRIU support includes an API that you can use to stop the VM at a checkpoint, save its state, and then run the VM from the point where it was stopped. The period of time between when the VM starts and when the application takes a checkpoint is referred to as the *checkpoint phase*. The application writes the VM state that was saved at the checkpoint to one or more image files. The saved state includes memory pages, methods, file systems, processes, and network connections. You can restore the VM from these files multiple times from the same point and in multiple environments.

Because the checkpoint image files have the live state of the VM that is used to restore the VM in different systems, they should not contain cryptographically-sensitive security data. If the image files contain sensitive security data, the security components are vulnerable to exploitation even if you don't move the image files between systems. The CRIU technical preview introduces the `CRIUSECProvider` security provider, which provides the following limited set of security services:

- `MessageDigest`: `MD5`, `SHA-1` and `SHA-256`
- `SecureRandom`
- `MAC: HmacSHA256`

When you enable CRIU support, all existing security providers are removed from the security provider list during the checkpoint phase, by default and `CRIUSECProvider` is added. When you restore the VM in the nonportable restore mode ([`-XX:+CRIURestoreNonPortableMode`](xxcriurestorenonportablemode.md)), `CRIUSECProvider` is removed from the security provider list and the previous security providers are added back again.

![Start of content that applies to Java 11 (LTS) and later](cr/java11plus.png) You can disable `CRIUSECProvider` during the checkpoint phase with the `-XX:-CRIUSecProvider` option. For more information, see [`-XX:[+|-]CRIUSecProvider`](xxcriusecprovider.md). ![End of content that applies to Java 11 (LTS) and later](cr/java_close.png)

When the VM starts from the saved point instead of the beginning, the VM startup time improves.

## Enabling CRIU support

CRIU support is not enabled by default. You must enable it by specifying the [`-XX:+EnableCRIUSupport`](xxenablecriusupport.md) command-line option when you start your application.

## Configuring CRIU support

You can access the OpenJ9 CRIU support capabilities by specifying different options. The VM enables the addition of VM options on restore through the `CRIUSupport.registerRestoreOptionsFile` API and environment variables through the `CRIUSupport.registerRestoreEnvVariables` API. `OPENJ9_RESTORE_JAVA_OPTIONS` is a special environment variable for adding the VM options on restore.

There are new options that work at checkpoint as well as at restore and some new options that work at restore only. There are also some existing options that work on restore but some of them behave differently.

### New options

You can use the following options only during the checkpoint phase:

- [`-XX:[+|-]CRIURestoreNonPortableMode`](xxcriurestorenonportablemode.md): Specifies whether the JIT and AOT compiler generates nonportable compiled code on restore.
- [`-XX:CheckpointGCThreads`](xxcheckpointgcthread.md): Reduces the number of garbage collection (GC) threads that exist when you create a checkpoint image, to speed up the later restoration of the checkpoint image.
- [`-Dorg.eclipse.openj9.criu.ImmutableEnvVars`](dorgeclipseopenj9criuimmutableenvvars.md): Adds the environment variables in the `immutableEnvvars` list. The VM can detect these variables during the checkpoint and restore phases.

You can use the following options only when you restore a VM. If you specify these options when you start the VM (during the checkpoint phase), the VM does not start:

- [`-XX:[+|-]IgnoreUnrecognizedRestoreOptions`](xxignoreunrecognizedrestoreoptions.md): Specifies whether a restored VM ignores unrecognized command-line options and starts anyway or throws a `JVMRestoreException` error and does not start.
- [`-Xshareclasses:disableOnRestore`](xshareclassesdisableonrestore.md): Disables further use of the shared classes cache (SCC) on restore.
- [`-Xrs:onRestore`](xrsonrestoresynconrestore.md) and [`-Xrs:syncOnRestore`](xrsonrestoresynconrestore.md): Disables signal handling on restore. These options behave in a similar way to the existing [`-Xrs`](xrs.md) and [`-Xrs:sync`](xrs.md#parameters) options. However, there are differences because some signal handlers might exist when a checkpoint is taken, and remain after restoration.

### Existing options

Of the existing command-line options, only the following are supported when you run a restored VM and some of these options have changed behavior:

| Options                    | Changed behavior  |
|----------------------------|---------|
| [`-Xverbosegclog`](xverbosegclog.md) |      |
| [`-XcompilationThreads`](xcompilationthreads.md) |     |
| [`-XsamplingExpirationTime`](xsamplingexpirationtime.md)     |     |
| [`-XX:[+|-]PrintCodeCache`](xxprintcodecache.md)        |     |
| [`-Xtrace`](xtrace.md) | If you specify an `output` file before a checkpoint and another `output` file for restore, both the files are written according to the `-Xtrace` options associated with the `output` file. |
| [`-Xdump`](xdump.md) | Dump events that are triggered on exception throws or catches cannot be enabled on restore. |
| [`-Xgcthreads`](xgcthreads.md) | This option is ignored if the number of GC threads is less than the checkpoint GC thread count. |
| [`-Xjit`](xjit.md) | If `-Xint` or `-Xnoaot` and `-Xnojit` are specified pre-checkpoint, the compiler remains disabled post-restore. |
| [`-Xaot`](xaot.md) | If `-Xnoaot` is specified pre-checkpoint, then specifying `-Xaot` post-restore does not enable AOT compilation or load. |
| [`-Xjit`](xjit.md) / [`-Xaot`](xaot.md)  | You can specify the following parameters with the `-Xjit` and `-Xaot` options when you run a restored VM:<br>`count`: Applies only to new methods; existing interpreted methods will not have their counts that are updated.<br> `limit`, `limitFile`, `exclude`: Invalidates the existing compiled methods that do not match the filters. The filters are then enforced for all future JIT and AOT compilations.<br> `loadLimit`, `loadLimitFile`, `loadExclude`: Applies only to future AOT loads; does not impact existing compiled methods.<br> `verbose`: Effective post-restore; does not override verbose options specified pre-checkpoint.<br> `vlog`: A vlog file is opened post-restore. If this option was specified pre-checkpoint, the old file is closed first.    |
| [`-Xnoaot`](xaot.md) | Prevents AOT compilations and loads; does not affect the existing compiled methods and does not prevent JIT compilation.  |
| [`-Xnojit`](xjit.md#syntax) | Invalidates all existing compiled methods and prevents JIT compilations; does not prevent AOT compilations and loads.  |

If you specify an unsupported option, the VM throws a `JVMRestoreException` error by default. If you specify the `-XX:+IgnoreUnrecognizedRestoreOptions` option, the VM does not throw any exception and just ignores the unsupported option.

For more more information on CRIU support, see the `openj9.criu` module in your OpenJ9 JDK version (OpenJDK 11 and later) API documentation.

:fontawesome-solid-triangle-exclamation:{: .warn aria-hidden="true"} **Restrictions:**

- If an `-Xtrace` or JIT log output file is specified on startup and doesn’t exist on restore, or is modified in any way between checkpoint and restore, the restore operation fails.
- Method tracing might not work consistently if a method is compiled pre-checkpoint.
- The Java&trade; heap is configured at startup. The VM detects the available memory on the system and sizes the heap based on it. With the CRIU support, this means that the size of the Java heap ([`-Xms`](xms.md), [`-Xmx`](xms.md)) and the respective heap regions, such as nursery and tenure at checkpoint will be same on restore. If a checkpoint is taken in a container with no memory limits and then restored in a container with memory limits, the restored VM instance does not detect the memory limits.
- There is currently no support for changing locales. A checkpoint must be taken with the intended target locale on restore for it to function properly.

## See Also

- [CRIU](https://criu.org/Main_Page)
- [Fast JVM startup with OpenJ9 CRIU Support](https://blog.openj9.org/2022/09/26/fast-jvm-startup-with-openj9-criu-support/)
- [Getting started with OpenJ9 CRIU Support](https://blog.openj9.org/2022/09/26/getting-started-with-openj9-criu-support/)
- [OpenJ9 CRIU Support: A look under the hood](https://blog.openj9.org/2022/10/14/openj9-criu-support-a-look-under-the-hood/)
- [OpenJ9 CRIU Support: A look under the hood (part II)](https://blog.openj9.org/2022/10/14/openj9-criu-support-a-look-under-the-hood-part-ii/)

<!-- ==== END OF TOPIC ==== criusupport.md ==== -->
