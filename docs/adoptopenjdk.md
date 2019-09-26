

# AdoptOpenJDK builds

The AdoptOpenJDK community project provide pre-built binaries of OpenJDK with OpenJ9, which have been rigorously tested
to meet expected quality standards. Binaries for the latest release of Eclipse OpenJ9 can be obtained from the
following links:

- [OpenJDK8 with OpenJ9](https://adoptopenjdk.net/releases.html?variant=openjdk8&jvmVariant=openj9)
- [OpenJDK11 with OpenJ9](https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=openj9)
- [OpenJDK13 with OpenJ9](https://adoptopenjdk.net/releases.html?variant=openjdk13&jvmVariant=openj9)

Nightly builds of OpenJDK with OpenJ9 are also available from the project.

## Supported platforms

The community develop and maintain a build and test infrastructure for the OpenJDK source across a broad
range of platforms. For information about the platforms and minimum operating system levels supported for the builds, see the AdoptOpenJDK [Platform support matrix](https://adoptopenjdk.net/supported_platforms.html).

## Installation pre-requisites

If you obtain binaries from the AdoptOpenJDK community, the following pre-requisites are required:

- From Eclipse OpenJ9 release 0.13.0, OpenJDK binaries for Linux and AIX platforms from the AdoptOpenJDK community no longer bundle the OpenSSL cryptographic library. The library is expected to be found on the system path. If you want to use OpenSSL cryptographic acceleration, you must install OpenSSL 1.0.2 or 1.1.X on your system. If the library is not found on the system path, the in-built Java crytographic implementation is used instead, which performs less well.  
- ![Start of content that applies only to Java 8](cr/java8.png) On Linux systems, the `fontconfig.x86_64` package should be installed to avoid a `NullPointerException` error when the AWT font subsystem is initialized. Work is ongoing at the AdoptOpenJDK project to fix this issue for their OpenJDK binaries.
- From Eclipse OpenJ9 release 0.16.0 (OpenJDK 13) and release 0.17.0 (OpenJDK 8 and 11), CUDA is now enabled on Windows (x86-64) and Linux (x86-64 and IBM POWER LE) platforms, which allows you to offload certain Java application processing tasks to a general purpose graphics processing unit (GPU). To take advantage of this feature, your system must support NVIDIA Compute Unified Device Architecture (CUDA). The JIT requires the CUDA Toolkit 7.5 and your GPU device must have a minimum compute capability of 3.0. 

<!-- ==== END OF TOPIC ==== adoptopenjdk.md ==== -->
