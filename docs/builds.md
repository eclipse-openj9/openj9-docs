

# OpenJ9 builds

Eclipse Foundation projects are not permitted to distribute, market or promote JDK binaries unless they have
passed a Java SE Technology Compatibility Kit licensed from Oracle, to which the OpenJ9 project does
not currently have access. See the [Eclipse Adoptium Project Charter](https://projects.eclipse.org/projects/adoptium/charter).

## Supported platforms

The community develops and maintains a test infrastructure for the OpenJ9 source across a broad
range of platforms. For information about the platforms and minimum operating system levels supported, see the [Platform support matrix](openj9_support.md).

## Building your own binaries

If you want to build your own binaries of OpenJDK with OpenJ9, a complete set of build instructions for several platforms can be found in the [OpenJ9 GitHub repository](https://github.com/eclipse-openj9/openj9/tree/master/doc/build-instructions).  

## Installation pre-requisites

Note the following:

- For the best performance, OpenSSL support should be enabled in the build. In builds that aren't configured with `--enable-openssl-bundling`, the OpenSSL library is expected to be found on the system path. If you want to use OpenSSL cryptographic acceleration, you must install OpenSSL 1.0.2 or 1.1.X on your system. If the library is not found on the system path, the in-built Java crytographic implementation is used instead, which performs less well.  
- ![Start of content that applies only to Java 8](cr/java8.png) On Linux systems, the `fontconfig.x86_64` package should be installed to avoid a `NullPointerException` error when the AWT font subsystem is initialized.
- From Eclipse OpenJ9 release 0.16.0 (OpenJDK 13) and release 0.17.0 (OpenJDK 8 and 11), CUDA is now enabled on Windows (x86-64) and Linux (x86-64 and IBM POWER LE) platforms, which allows you to offload certain Java application processing tasks to a general purpose graphics processing unit (GPU). To take advantage of this feature, your system must support NVIDIA Compute Unified Device Architecture (CUDA). The JIT requires the CUDA Toolkit 7.5 and your GPU device must have a minimum compute capability of 3.0.



<!-- ==== END OF TOPIC ==== builds.md ==== -->
