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

# Java Virtual Machine Tool Interface

The Java&trade; Virtual Machine Tool Interface (JVMTI) is a two-way interface that allows communication between the VM and a native agent. It replaces both the Java Virtual Machine Debug Interface (JVMDI) and Java Virtual Machine Profiler Interface (JVMPI).

## Overview

The JVMTI allows third parties to develop debugging, profiling, and monitoring tools for the VM. The interface contains mechanisms for the agent to notify the VM about the kinds of information it requires, and also provides a means of receiving relevant notifications.

Several agents can be attached to a VM at any one time.

JVMTI agents can be loaded at startup using short or long forms of the command-line option:

    -agentlib:<agent-lib-name>=<options>

or

    -agentpath:<path-to-agent>=<options>

In the example that follows (see [Sample JVMTI agent](#sample-jvmti-agent)), the directory containing the `jdwp` library is assumed to be on the library path. If you require a specific library, such as `jdwp`, with your JVMTI agent, you can specify the path at startup, for example:

    -agentlib:jdwp=<options>

For more information about `-agentlib:jdwp`, see [`-Xrunjdwp`](xrunjdwp.md).

For more information about JVMTI, see [https://docs.oracle.com/javase/8/docs/technotes/guides/management/index.html](https://docs.oracle.com/javase/8/docs/technotes/guides/management/index.html).

For a guide about writing a JVMTI agent, see [http://www.oracle.com/technetwork/articles/javase/jvmti-136367.html](https://www.oracle.com/technetwork/articles/javase/jvmti-136367.html).

## Eclipse OpenJ9 extensions

Eclipse OpenJ9&trade; extensions to the JVMTI allow a JVMTI agent to query or automatically trigger operations in the VM, including the following tasks:


| Task  | OpenJ9 extensions |
|-------|-------------------|
|Get the OS thread ID                                                             |[`GetOSThreadID`](#getosthreadid)|
|Query, set, and reset the VM dump options                                        |[`QueryVmDump`](#queryvmdump), [`SetVmDump`](#setvmdump), [`ResetVmDump`](#resetvmdump)|
|Trigger a VM dump, and monitor JVMTI event functions when VM dumps start and end |[`TriggerVmDump`](#triggervmdump), [`VMDumpStart`](#vmdumpstart), [`VMDumpEnd`](#vmdumpend)|
|Set VM trace options                                                             |[`SetVmTrace`](#setvmtrace)|
|Subscribe to and unsubscribe from VM tracepoints                                 |[`RegisterTracePointSubscriber`](#registertracepointsubscriber), [`DeregisterTracePointSubscriber`](#deregistertracepointsubscriber)|
|Query runtime environment native memory categories                               |[`GetMemoryCategories`](#getmemorycategories)|
|Query and set VM log options                                                     |[`QueryVmLogOptions`](#queryvmlogoptions), [`SetVmLogOptions`](#setvmlogoptions)|
|Search for and remove a shared classes cache                                     |[`IterateSharedCaches`](#iteratesharedcaches), [`DestroySharedCache`](#destroysharedcache)|
|Subscribe to and unsubscribe from verbose garbage collection (GC) data logging   |[`RegisterVerboseGCSubscriber`](#registerverbosegcsubscriber), [`DeregisterVerboseGCSubscriber`](#deregisterverbosegcsubscriber)|

The definitions that you need when you write a JVMTI agent are provided in the header files `jvmti.h` and `ibmjvmti.h`, in the  `include` directory.

## Sample JVMTI agent

The following sample shows you how to write a simple JVMTI agent that uses OpenJ9 extensions to the JVMTI.

<details>
  <summary>Sample JVMTI agent written in C/C++, which uses the OpenJ9 extensions</summary>

````
/*
 * tiSample.c
 *
 * Sample JVMTI agent to demonstrate the OpenJ9 JVMTI dump extensions
 */

#include "jvmti.h"
#include "ibmjvmti.h"

/* Forward declarations for JVMTI callback functions */
void JNICALL VMInitCallback(jvmtiEnv *jvmti_env, JNIEnv* jni_env, jthread thread);
void JNICALL DumpStartCallback(jvmtiEnv *jvmti_env, char* label, char* event, char* detail, ...);


/*
 * Agent_Onload()
 *
 * JVMTI agent initialisation function, invoked as agent is loaded by the VM
 */
JNIEXPORT jint JNICALL Agent_OnLoad(JavaVM *jvm, char *options, void *reserved) {

  jvmtiEnv *jvmti = NULL;
  jvmtiError rc;
  jint extensionEventCount = 0;
  jvmtiExtensionEventInfo *extensionEvents = NULL;
  jint extensionFunctionCount = 0;
  jvmtiExtensionFunctionInfo *extensionFunctions = NULL;
  int i = 0, j = 0;

  printf("tiSample: Loading JVMTI sample agent\n");

    /* Get access to JVMTI */
    (*jvm)->GetEnv(jvm, (void **)&jvmti, JVMTI_VERSION_1_0);

     /* Look up all the JVMTI extension events and functions */
   (*jvmti)->GetExtensionEvents(jvmti, &extensionEventCount, &extensionEvents);
   (*jvmti)->GetExtensionFunctions(jvmti, &extensionFunctionCount, &extensionFunctions);

    printf("tiSample: Found %i JVMTI extension events, %i extension functions\n", extensionEventCount, extensionFunctionCount);

   /* Find the JVMTI extension event we want */
   while (i++ < extensionEventCount) {

        if (strcmp(extensionEvents->id, COM_IBM_VM_DUMP_START) == 0) {
            /* Found the dump start extension event, now set up a callback for it */
            rc = (*jvmti)->SetExtensionEventCallback(jvmti, extensionEvents->extension_event_index, &DumpStartCallback);
            printf("tiSample: Setting JVMTI event callback %s, rc=%i\n", COM_IBM_VM_DUMP_START, rc);
            break;
        }
        extensionEvents++; /* move on to the next extension event */
    }

    /* Find the JVMTI extension function we want */
    while (j++ < extensionFunctionCount) {
        jvmtiExtensionFunction function = extensionFunctions->func;

        if (strcmp(extensionFunctions->id, COM_IBM_SET_VM_DUMP) == 0) {
            /* Found the set dump extension function, now set a dump option to generate javadumps on
          thread starts */
            rc = function(jvmti, "java:events=thrstart");
            printf("tiSample: Calling JVMTI extension %s, rc=%i\n", COM_IBM_SET_VM_DUMP, rc);
            break;
        }
        extensionFunctions++; /* move on to the next extension function */
     }

     return JNI_OK;
}


/*
 * DumpStartCallback()
 * JVMTI callback for dump start event (IBM JVMTI extension)  */
void JNICALL
DumpStartCallback(jvmtiEnv *jvmti_env, char* label, char* event, char* detail, ...) {
      printf("tiSample: Received JVMTI event callback, for event %s\n", event);
}
````

</details>

The sample JVMTI agent consists of two functions, `Agent_OnLoad()` and `DumpStartCallback()`:

### `Agent_OnLoad()`

This function is called by the VM when the agent is loaded at VM startup, which allows the JVMTI agent to modify VM behavior before initialization is complete. The sample agent obtains access to the JVMTI interface by using the JNI Invocation API function `GetEnv()`. The agent calls the APIs `GetExtensionEvents()` and `GetExtensionFunctions()` to find the JVMTI extensions that are supported by the VM. These APIs provide access to the list of extensions available in the `jvmtiExtensionEventInfo` and `jvmtiExtensionFunctionInfo` structures. The sample uses an extension event and an extension function in the following way:

**Extension event:** The sample JVMTI agent searches for the extension event `VmDumpStart` in the list of `jvmtiExtensionEventInfo` structures, by using the identifier `COM_IBM_VM_DUMP_START` provided in `ibmjvmti.h`. When the event is found, the JVMTI agent calls the JVMTI interface `SetExtensionEventCallback()` to enable the event, providing a function `DumpStartCallback()` that is called when the event is triggered.

**Extension function:** Next, the sample JVMTI agent searches for the extension function `SetVMDump` in the list of `jvmtiExtensionFunctionInfo` structures, by using the identifier `COM_IBM_SET_VM_DUMP` provided in `ibmjvmti.h`. The JVMTI agent calls the function by using the `jvmtiExtensionFunction` pointer to set a VM dump option `java:events=thrstart`. This option requests the VM to trigger a Java dump every time a VM thread is started.

### `DumpStartCallback()`

This callback function issues a message when the associated extension event is called. In the sample code, `DumpStartCallback()` is used when the `VmDumpStart` event is triggered.

### Using the sample JVMTI agent

- Build the sample JVMTI agent:

    Windows:

        cl /I<jre_path>\include /MD /FetiSample.dll tiSample.c /link /DLL

    Linux, AIX®, and z/OS®:

        gcc -I<jre_path>/include -o libtiSample.so -shared tiSample.c

    where `<jre_path>` is the path to your Java runtime environment installation.

- To run the sample JVMTI agent, use the command:

        java -agentlib:tiSample -version

    When the sample JVMTI agent loads, messages are generated. When the JVMTI agent initiates a Java dump, the message `JVMDUMP010` is issued.

## API reference

The following sections provide reference information for the OpenJ9 extensions to the JVMTI.

<!-- ==================================================================================================== -->

### `GetOSThreadID`

You can get the OS thread ID by using the `GetOSThreadID()` API:

    jvmtiError GetOSThreadID(jvmtiEnv* jvmti_env, jthread thread, jlong * threadid_ptr);

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `thread`: The thread for which the ID is required.
- `threadid_ptr`: A pointer to a variable, used to return the thread ID that corresponds to the thread specified by the `thread` parameter.

**Returns**

`JVMTI_ERROR_NONE`: Success  
`JVMTI_ERROR_NULL_POINTER`: The `threadid_ptr` parameter is null.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_INVALID_THREAD`: The thread is not valid.  
`JVMTI_ERROR_THREAD_NOT_ALIVE`: The VM state of the thread is not started or has died.  
`JVMTI_ERROR_UNATTACHED_THREAD`: The current thread is not attached to the VM.
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI start or live phase.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.GetOSThreadID`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_GET_OS_THREAD_ID`

---

<!-- ==================================================================================================== -->

### `QueryVmDump`

You can query the VM dump options that are set for a VM by using the `QueryVmDump()` API:

    jvmtiError QueryVmDump(jvmtiEnv* jvmti_env, jint buffer_size, void* options_buffer, jint* data_size_ptr)

This extension returns a set of dump option specifications as ASCII strings. The syntax of the option string is the same as the `-Xdump` command-line option, with the initial `-Xdump:` omitted. See [-Xdump](xdump.md). The option strings are separated by newline characters. If the memory buffer is too small to contain the current VM dump option strings, you can expect the following results:

- The error message `JVMTI_ERROR_ILLEGAL_ARGUMENT` is returned.
- The variable for `data_size_ptr` is set to the required buffer size.

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `buffer_size`: The size of the supplied memory buffer in bytes.
- `options_buffer`: A pointer to the supplied memory buffer.
- `data_size_ptr`: A pointer to a variable, used to return the total size of the option strings.

**Returns**

`JVMTI_ERROR_NONE`: Success  
`JVMTI_ERROR_NULL_POINTER`: The `options_buffer` or `data_size_ptr` parameters are null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_NOT_AVAILABLE`: The dump configuration is locked because a dump is in progress.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: The supplied memory buffer in `options_buffer` is too small.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.QueryVmDump`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_QUERY_VM_DUMP`

---

<!-- ==================================================================================================== -->

### `SetVmDump`

You can set VM dump options by using the `SetVmDump()` API:

    jvmtiError SetVmDump(jvmtiEnv* jvmti_env, char* option)

The dump option is passed in as an ASCII character string. Use the same syntax as the `-Xdump` command-line option, with the initial `-Xdump:` omitted. See [-Xdump](xdump.md).

When dumps are in progress, the dump configuration is locked, and calls to `SetVmDump()` fail with a return value of `JVMTI_ERROR_NOT_AVAILABLE`.

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `option`: The VM dump option string.  
:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On z/OS, you might need to convert the option string from EBCDIC to ASCII before using this JVMTI extension function.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: The parameter option is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_NOT_AVAILABLE`: The dump configuration is locked because a dump is in progress.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: The parameter option contains an invalid `-Xdump` string.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.SetVmDump`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_SET_VM_DUMP`

---

<!-- ==================================================================================================== -->

### `TriggerVmDump`

You can trigger a VM dump and specify the type of dump you want by using the `TriggerVmDump()` API:

    jvmtiError TriggerVmDump(jvmtiEnv* jvmti_env, char* option)

Choose the type of dump required by specifying an ASCII string that contains one of the supported dump agent types. See [-Xdump](xdump.md). JVMTI events are provided at the start and end of the dump.

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `option`: A pointer to the dump type string, which can be one of the following types:  

    `stack`  
    `java`  
    `system`  
    `console`  
    `tool`  
    `heap`  
    `snap`  
    `ceedump` (z/OS only)  

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On z/OS, you might need to convert the option string from EBCDIC to ASCII before using this JVMTI extension function.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: The option parameter is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_NOT_AVAILABLE`: The dump configuration is locked because a dump is in progress.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.TriggerVmDump`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_TRIGGER_VM_DUMP`

---

<!-- ==================================================================================================== -->

### `ResetVmDump`

You can reset VM dump options to the values at VM initialization by using the `ResetVmDump()` API:

    jvmtiError ResetVmDump(jvmtiEnv* jvmti_env)

**Parameters**

- `jvmti_env`: The JVMTI environment pointer.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_NOT_AVAILABLE`: The dump configuration is locked because a dump is in progress.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.ResetVmDump`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_RESET_VM_DUMP`

---

<!-- ==================================================================================================== -->

### `VMDumpStart`

The following JVMTI event function is called when a VM dump starts:

    void JNICALL VMDumpStart(jvmtiEnv *jvmti_env, JNIEnv* jni_env, char* label, char* event, char* detail)

The event function provides the dump file name, the name of the JVMTI event, and the detail string from the dump event. The detail string provides additional information about the event that triggered the dump. This information is the same as the information detailed in the `JVMDUMP039I` message. For example:

    JVMDUMP039I Processing dump event "systhrow", detail "java/lang/OutOfMemoryError" at 2014/10/17 13:31:03 - please wait."

**Parameters**

- `jvmti_env`: JVMTI environment pointer.
- `jni_env`: JNI environment pointer for the thread on which the event occurred.
- `label`: The dump file name, including directory path.
- `event`: The extension event name, such as `com.ibm.VmDumpStart`.
- `detail`: The dump event detail string. The string can be empty.

**Returns**

None

---

<!-- ==================================================================================================== -->

### `VMDumpEnd`

The following JVMTI event function is called when a VM dump ends:

    void JNICALL VMDumpEnd(jvmtiEnv *jvmti_env, JNIEnv* jni_env, char* label, char* event, char* detail)

The event function provides the dump file name, the name of the JVMTI event, and the detail string from the dump event. The detail string provides additional information about the event that triggered the dump. This information is the same as the information detailed in the `JVMDUMP039I` message. For example:

    JVMDUMP039I Processing dump event "systhrow", detail "java/lang/OutOfMemoryError" at 2014/10/17 13:31:03 - please wait.

**Parameters**

- `jvmti_env`: JVMTI environment pointer.  
- `jni_env`: JNI environment pointer for the thread on which the event occurred.  
- `label`: The dump file name, including directory path.  
- `event`: The extension event name `com.ibm.VmDumpEnd`.  
- `detail`: The dump event detail string. The string can be empty.  

**Returns**

None

<!-- ==================================================================================================== -->

---

### `SetVmTrace`

You can set VM trace options by using the `SetVmTrace()` API:

    jvmtiError SetVmTrace(jvmtiEnv* jvmti_env, char* option)

The trace option is passed in as an ASCII character string. Use the same syntax as the `-Xtrace` command-line option, with the initial `-Xtrace:` omitted. See [-Xtrace](xtrace.md).

**Parameters**

- `jvmti_env`: JVMTI environment pointer.
- `option`: Enter the VM trace option string.  
:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On z/OS, you might need to convert the option string from EBCDIC to ASCII before using this JVMTI extension function.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: The option parameter is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: The option parameter contains an invalid `-Xtrace` string.  

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.SetVmTrace`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_SET_VM_TRACE`

---

<!-- ==================================================================================================== -->

### `RegisterTracePointSubscriber`

You can subscribe to VM tracepoints by using the `RegisterTracePointSubscriber()` API:

    jvmtiError RegisterTracePointSubscriber(jvmtiEnv* jvmti_env, char *description, jvmtiTraceSubscriber subscriber, jvmtiTraceAlarm alarm, void *userData, void **subscriptionID)

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `description`: An ASCII character string that describes the subscriber.
- `subscriber`: A function of type [`jvmtiTraceSubscriber`](#jvmtitracesubscriber-function).
- `alarm`: A function pointer of type [`jvmtiTraceAlarm`](#jvmtitracealarm-function).
- `user_data`: A pointer to user data. This pointer is passed to the subscriber and alarm functions each time these functions are called. This pointer can be a null value.
- `subscription_id`: A pointer to a subscription identifier. This pointer is returned by the `RegisterTracePointSubscriber` call if successful. The value must be supplied to a future call to the `DeregisterTracePointSubscriber` API, which is used to unsubscribe from the VM tracepoint.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: One of the supplied parameters is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is not valid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_NOT_AVAILABLE`: VM trace is not available.  
`JVMTI_ERROR_INTERNAL`: An internal error occurred.  

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.RegisterTracePointSubscriber`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_REGISTER_TRACEPOINT_SUBSCRIBER`

#### `jvmtiTraceSubscriber` function

: The subscriber function type is defined as follows:

        typedef jvmtiError (*jvmtiTraceSubscriber)(jvmtiEnv *jvmti_env, void *record, jlong length, void *user_data);

    The subscriber function must be of type `jvmtiTraceSubscriber`, which is declared in `ibmjvmti.h`.

    This function is called with each tracepoint record that is selected through the `-Xtrace:external` option.

    The tracepoint record that is supplied to the subscriber function is valid only for the duration of the function. If the subscriber wants to save the data, the data must be copied elsewhere.

    If the subscriber function returns an error, the alarm function is called, the subscription is disconnected, and no further tracepoints are sent to the subscriber.

    **Subscriber function parameters**

    - `jvmti_env`: A pointer to the JVMTI environment.
    - `record`: A UTF-8 string that contains a tracepoint record.
    - `length`: The number of UTF-8 characters in the tracepoint record.
    - `user_data`: User data that is supplied when the subscriber is registered.

#### `jvmtiTraceAlarm` function

: The alarm function type is defined as follows:

        typedef jvmtiError (*jvmtiTraceAlarm)(jvmtiEnv *jvmti_env, void *subscription_id, void *user_data);

    The alarm function must be of type `jvmtiTraceAlarm`, which is declared in `ibmjvmti.h`. This function is called if the subscriber function returns an error.

    **Alarm function parameters**

    - `jvmti_env`: A pointer to the JVMTI environment.
    - `subscription_id`: The subscription identifier.
    - `user_data`: User data that is supplied when the subscriber is registered.

---

<!-- ==================================================================================================== -->

### `DeregisterTracePointSubscriber`

You can unsubscribe from VM tracepoints by using the `DeregisterTracePointSubscriber()` API:

    jvmtiError DeregisterTracePointSubscriber(jvmtiEnv* jvmti_env, void *userData, void *subscription_id)

After the `DeregisterTracePointSubscriber()` API is called, no further calls are made to the subscriber function.

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `subscription_id`: The subscription identifier that is returned by the call to the [RegisterTracePointSubscriber](#registertracepointsubscriber) API.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: The `subscription_id` parameter is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is not valid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.DeregisterTracePointSubscriber`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_DEREGISTER_TRACEPOINT_SUBSCRIBER`

---

<!-- ==================================================================================================== -->

### `GetMemoryCategories`

You can query runtime environment native memory categories by using the `GetMemoryCategories()` API:

    jvmtiError GetMemoryCategories(jvmtiEnv* env, jint version, jint max_categories, jvmtiMemoryCategory * categories_buffer, jint * written_count_ptr, jint * total_categories_ptr);

You can query the total native memory consumption of the runtime environment for each memory category by using this API. Native memory is memory requested from the operating system using library functions such as `malloc()` and `mmap()`. Runtime environment native memory use is grouped under high-level memory categories, as described in the `NATIVEMEMINFO` section of the *Java dump* topic. The data returned by the `GetMemoryCategories()` API is consistent with this format. See [Java dump: NATIVEMEMINFO](dump_javadump.md#nativememinfo).

The extension writes native memory information to a memory buffer specified by the user. Each memory category is recorded as a `jvmtiMemoryCategory` structure, whose format is defined in `ibmjvmti.h`.

You can use the `GetMemoryCategories()` API to work out the buffer size you must allocate to hold all memory categories defined inside the VM. To calculate the size, call the API with a null `categories_buffer` argument and a non-null `total_categories_ptr` argument.

**Parameters**

- `env`: A pointer to the JVMTI environment.
- `version`: The version of the `jvmtiMemoryCategory` structure that you are using. Use `COM_IBM_GET_MEMORY_CATEGORIES_VERSION_1` for this argument, unless you must work with an obsolete version of the `jvmtiMemoryCategory` structure.
- `max_categories`: The number of `jvmtiMemoryCategory` structures that can fit in the `categories_buffer` memory buffer.
- `categories_buffer`: A pointer to the memory buffer for holding the result of the `GetMemoryCategories()` call. The number of `jvmtiMemoryCategory` slots available in the `categories_buffer` memory buffer must be accurately specified with `max_categories`, otherwise `GetMemoryCategories()` can overflow the memory buffer. The value can be null.
- `written_count_ptr`: A pointer to `jint` to store the number of `jvmtiMemoryCategory` structures to be written to the `categories_buffer` memory buffer. The value can be null.
- `total_categories_ptr`: A pointer to `jint` to store the total number of memory categories declared in the VM. The value can be null.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_UNSUPPORTED_VERSION`: Unrecognized value passed for version.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: Illegal argument; `categories_buffer`, `count_ptr`, and `total_categories_ptr` all have null values.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `env` parameter is invalid.  
`JVMTI_ERROR_OUT_OF_MEMORY`: Memory category data is truncated because `max_categories` is not large enough.  

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.GetMemoryCategories`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_GET_MEMORY_CATEGORIES`

---

<!-- ==================================================================================================== -->

### `QueryVmLogOptions`

You can query VM log options by using the `QueryVmLogOptions()` API:

    jvmtiError QueryVmLogOptions(jvmtiEnv* jvmti_env, jint buffer_size, void* options, jint* data_size_ptr)

This extension returns the current log options as an ASCII string. The syntax of the string is the same as the `-Xsyslog` command-line option, with the initial `-Xsyslog:` omitted. For example, the string "error,warn" indicates that the VM is set to log error and warning messages only. For more information, see [`-Xsyslog`](xsyslog.md).

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.  
- `buffer_size`: The size of the supplied memory buffer in bytes. If the memory buffer is too small to contain the current VM log option string, the `JVMTI_ERROR_ILLEGAL_ARGUMENT` error message is returned.  
- `options_buffer`: A pointer to the supplied memory buffer.  
- `data_size_ptr`: A pointer to a variable, used to return the total size of the option string.

**Returns**

`JVMTI_ERROR_NONE`: Success  
`JVMTI_ERROR_NULL_POINTER`: The `options` or `data_size_ptr` parameters are null.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: The supplied memory buffer is too small.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.QueryVmLogOptions`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_QUERY_VM_LOG_OPTIONS`

---

<!-- ==================================================================================================== -->

### `SetVmLogOptions`

You can set VM log options by using the `SetVmLogOptions()` API:

    jvmtiError SetVmLogOptions(jvmtiEnv* jvmti_env, char* options_buffer)

The log option is passed in as an ASCII character string. Use the same syntax as the `-Xsyslog` command-line option, with the initial `-Xsyslog:` omitted. For example, to set the VM to log error and warning messages, pass in a string containing "error,warn". For more information, see [`-Xsyslog`](xsyslog.md).

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `options_buffer`: A pointer to memory containing the log option.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: The parameter option is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is invalid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: The parameter option contains an invalid `-Xsyslog` string.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.SetVmLogOptions`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_SET_VM_LOG_OPTIONS`

---

<!-- ==================================================================================================== -->

### `IterateSharedCaches`

You can search for shared classes caches that exist in a specified cache directory by using the `IterateSharedCaches()` API:

    jvmtiError IterateSharedCaches(jvmtiEnv* env, jint version, const char *cacheDir, jint flags, jboolean useCommandLineValues, jvmtiIterateSharedCachesCallback callback, void *user_data);

Information about the caches is returned in a structure that is populated by a user-specified callback function. You can specify the search directory in two ways:

- Set the value of `useCommandLineValues` to `true` and specify the directory on the command line. If the directory is not specified on the command line, the default location for the platform is used.
- Set the value of `useCommandLineValues` to `false` and use the `cacheDir` parameter. To accept the default location for the platform, specify `cacheDir` with a `null` value.

**Parameters**

- `env`: A pointer to the JVMTI environment.
- `version`: Version information for `IterateSharedCaches`, which describes the `jvmtiSharedCacheInfo` structure passed to the `jvmtiIterateSharedCachesCallback` function. The values allowed are:
    - `COM_IBM_ITERATE_SHARED_CACHES_VERSION_1`
    - `COM_IBM_ITERATE_SHARED_CACHES_VERSION_2`
    - `COM_IBM_ITERATE_SHARED_CACHES_VERSION_3`
    - `COM_IBM_ITERATE_SHARED_CACHES_VERSION_4`
    - `COM_IBM_ITERATE_SHARED_CACHES_VERSION_5`
- `cacheDir`: When the value of `useCommandLineValues` is `false`, specify the absolute path of the directory for the shared classes cache. If the value is `null`, the platform-dependent default is used.
- `flags`: Reserved for future use. The only value allowed is `COM_IBM_ITERATE_SHARED_CACHES_NO_FLAGS`.
- `useCommandLineValues`: Set this value to `true` when you want to specify the cache directory on the command line. Set this value to `false` when you want to use the `cacheDir` parameter.
- `callback`: A function pointer to a user provided callback routine `jvmtiIterateSharedCachesCallback`.
- `user_data`: User supplied data, passed as an argument to the callback function.  

        jint (JNICALL *jvmtiIterateSharedCachesCallback)(jvmtiEnv *env,jvmtiSharedCacheInfo *cache_info, void *user_data);

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `env` parameter is not valid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_UNSUPPORTED_VERSION`: The `version` parameter is not valid.  
`JVMTI_ERROR_NULL_POINTER`: The `callback` parameter is null.  
`JVMTI_ERROR_NOT_AVAILABLE`: The shared classes feature is not enabled in the VM.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: The `flags` parameter is not valid.  
`JVMTI_ERROR_INTERNAL`: This error is returned when the `jvmtiIterateSharedCachesCallback` returns `JNI_ERR`.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.IterateSharedCaches`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_ITERATE_SHARED_CACHES`

#### `jvmtiIterateSharedCachesCallback` function

: **Callback function parameters**

    - `env`: A pointer to the JVMTI environment when calling `COM_IBM_ITERATE_SHARED_CACHES`.
    - `cache_info`: A `jvmtiSharedCacheInfo` structure containing information about a shared cache.
    - `user_data`: User-supplied data, passed as an argument to `IterateSharedCaches`.

    **Callback function returns**

    `JNI_OK`: Continue iterating.  
    `JNI_ERR`: Stop iterating, which causes `IterateSharedCaches` to return `JVMTI_ERROR_INTERNAL`

#### `jvmtiSharedCacheInfo` structure

: The structure of `jvmtiSharedCacheInfo`

        typedef struct jvmtiSharedCacheInfo {
        const char *name;      // the name of the shared cache
        jboolean isCompatible; // if the shared cache is compatible with this VM
        jboolean isPersistent; // true if the shared cache is persistent, false if its non-persistent
        jint os_shmid;         // the OS shared memory ID associated with a non-persistent cache, -1 otherwise
        jint os_semid;         // the OS shared semaphore ID associated with a non-persistent cache, -1 otherwise
        jint modLevel;         // one of:
                               //   COM_IBM_SHARED_CACHE_MODLEVEL_JAVA5
                               //   COM_IBM_SHARED_CACHE_MODLEVEL_JAVA6
                               //   COM_IBM_SHARED_CACHE_MODLEVEL_JAVA7
                               //   COM_IBM_SHARED_CACHE_MODLEVEL_JAVA8
                               //   COM_IBM_SHARED_CACHE_MODLEVEL_JAVA9
                               //   from Java 10: the version number of the Java level on which the shared cache is created
        jint addrMode;         // the address mode of the VM creating the shared cache: includes additional
                               // information on whether it is a 64-bit compressedRefs cache when
                               // COM_IBM_ITERATE_SHARED_CACHES_VERSION_3 or later is specified.
        jboolean isCorrupt;    // if the cache is corrupted
        jlong cacheSize;       // the total usable shared classes cache size, or -1 when isCompatible is false
        jlong freeBytes;       // the number of free bytes in the shared classes cache, or -1 when isCompatible is false
        jlong lastDetach;      // the last detach time specified in milliseconds since 00:00:00 on 1 January 1970 UTC,
                               // or -1 when the last detach time is not available
        jint cacheType;        // the type of the cache
        jlong softMaxBytes;    // the soft limit for the available space in the cache
        jint layer;            // the shared cache layer number
        } jvmtiSharedCacheInfo;

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Notes:**

    - The field `cacheType` is included when `COM_IBM_ITERATE_SHARED_CACHES_VERSION_2` or later is specified.

    - `jvmtiSharedCacheInfo.addrMode` encodes both address mode and the compressed reference mode when `COM_IBM_ITERATE_SHARED_CACHES_VERSION_3` or later is specified. In this case, use the following set of macros to access the address mode and compressed reference mode:

    : To get the address mode, use:

            COM_IBM_ITERATE_SHARED_CACHES_GET_ADDR_MODE(jvmtiSharedCacheInfo.addrMode)

        This macro returns one of the following values:  
            `COM_IBM_SHARED_CACHE_ADDRMODE_32`  
            `COM_IBM_SHARED_CACHE_ADDRMODE_64`  

    : To get the compressed references mode, use:

            COM_IBM_ITERATE_SHARED_CACHES_GET_CMPRSSREF_MODE(jvmtiSharedCacheInfo.addrMode)

        This macro returns one of the following values:  
            `COM_IBM_ITERATE_SHARED_CACHES_UNKNOWN_COMPRESSED_POINTERS_MODE`  
            `COM_IBM_ITERATE_SHARED_CACHES_COMPRESSED_POINTERS_MODE`  
            `COM_IBM_ITERATE_SHARED_CACHES_NON_COMPRESSED_POINTERS_MODE`  

    - The field `softMaxBytes` is included when `COM_IBM_ITERATE_SHARED_CACHES_VERSION_4` or later is specified.

    - The field `layer` is included when `COM_IBM_ITERATE_SHARED_CACHES_VERSION_5` or later is specified. If the shared cache does not have a layer number, the value for `layer` is `-1`.

---

<!-- ==================================================================================================== -->

### `DestroySharedCache`

You can remove a shared classes cache by using the `DestroySharedCache()` API:

    jvmtiError DestroySharedCache(jvmtiEnv *env, const char *cacheDir, const char *name, jint persistence, jboolean useCommandLineValues, jint *internalErrorCode);

This extension removes a named shared classes cache of a given persistence type, in a given directory. You can specify the cache name, persistence type, and directory in one of these ways:

- Set `useCommandLineValues` to `true` and specify the values on the command line. If a value is not available, the default values for the platform are used.

- Set `useCommandLineValues` to `false` and use the `cacheDir`, `persistence` and `cacheName` parameters to identify the cache to be removed. To accept the default value for `cacheDir` or `cacheName`, specify the parameter with a `null` value.

**Parameters**

- `env`: A pointer to the JVMTI environment.
- `cacheDir`: When the value of `useCommandLineValues` is `false`, specify the absolute path of the directory for the shared classes cache. If the value is `null`, the platform-dependent default is used.
- `cacheName`: When the value of `useCommandLineValues` is `false`, specify the name of the cache to be removed. If the value is `null`, the platform-dependent default is used.
- `persistence`: When the value of `useCommandLineValues` is false, specify the type of cache to remove. This parameter must have one of the following values:  
    `PERSISTENCE_DEFAULT` (The default value for the platform).  
    `PERSISTENT`  
    `NONPERSISTENT`  
- `useCommandLineValues`: Set this value to `true` when you want to specify the shared classes cache name, persistence type, and directory on the command line. Set this value to `false` when you want to use the `cacheDir`, `persistence`, and `cacheName` parameters instead.
- `internalErrorCode`: If not `null`, this value is set to one of the following constants when `JVMTI_ERROR_INTERNAL` is returned:  
    - `COM_IBM_DESTROYED_ALL_CACHE`: Set when `JVMTI_ERROR_NONE is` returned.
    - `COM_IBM_DESTROYED_NONE`: Set when the function fails to remove any caches.  
    - `COM_IBM_DESTROY_FAILED_CURRENT_GEN_CACHE`: Set when the function fails to remove the existing current generation cache, irrespective of the state of older generation caches.  
    - `COM_IBM_DESTROY_FAILED_OLDER_GEN_CACHE`: Set when the function fails to remove any older generation caches. The current generation cache does not exist or is successfully removed.

**Returns**

`JVMTI_ERROR_NONE`: Success. No cache exists or all existing caches of all generations are removed.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `env` parameter is not valid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_NOT_AVAILABLE`: The shared classes feature is not enabled in the VM.  
`JVMTI_ERROR_ILLEGAL_ARGUMENT`: The `persistence` parameter is not valid.
`JVMTI_ERROR_INTERNAL`: Failed to remove any existing cache with the given name. See the value of the `internalErrorCode` parameter for more information about the failure.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.DestroySharedCache`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_DESTROY_SHARED_CACHE`

---

<!-- ==================================================================================================== -->

### `RegisterVerboseGCSubscriber`

You can subscribe to verbose garbage collection (GC) data logging by using the `RegisterVerboseGCSubscriber()` API:

    jvmtiError RegisterVerboseGCSubscriber(jvmtiEnv* jvmti_env, char *description, jvmtiVerboseGCSubscriber subscriber, jvmtiVerboseGCAlarm alarm, void *user_data, void **subscription_id)

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `description`: An ASCII character string that describes the subscriber.
- `subscriber`: A function of type [`jvmtiVerboseGCSubscriber`](#jvmtiverbosegcsubscriber-function).
- `alarm`: A function pointer of type [`jvmtiVerboseGCAlarm`](#jvmtiverbosegcalarm-function).
- `user_data`: A pointer to user data. This pointer is passed to the subscriber and alarm functions each time these functions are called. This pointer can be a null value.
- `subscription_id`: A pointer to a subscription identifier. This pointer is returned by the `RegisterVerboseGCSubscriber` call if successful. The value must be supplied to a future call to `DeregisterVerboseGCSubscriber` API, which is used to unsubscribe from verbose GC data logging.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: One of the supplied parameters is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is not valid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.  
`JVMTI_ERROR_NOT_AVAILABLE`: GC verbose logging is not available.  
`JVMTI_ERROR_INTERNAL`: An internal error has occurred.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.RegisterVerboseGCSubscriber`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_REGISTER_VERBOSEGC_SUBSCRIBER`

#### `jvmtiVerboseGCSubscriber` function

: The subscriber function type is defined as follows:

        typedef jvmtiError (*jvmtiVerboseGCSubscriber)(jvmtiEnv *jvmti_env, const char *record, jlong length, void *user_data);

    The subscriber function must be of type `jvmtiVerboseGCSubscriber`, which is declared in `ibmjvmti.h`.

    This function is called with each record of verbose logging data produced by the VM.

    This function runs under the same restrictions as the `GarbageCollectionStart` and `GarbageCollectionFinish` events in the standard JVMTI specification. For more information about these events, see the [JVMTI Event Index](https://docs.oracle.com/en/java/javase/20/docs/specs/jvmti.html#EventIndex) for your OpenJDK version.

    The verbose logging record supplied to the subscriber function is valid only for the duration of the function. If the subscriber wants to save the data, the data must be copied elsewhere.

    If the subscriber function returns an error, the alarm function is called, and the subscription is deregistered.

    **Subscriber function parameters**

    - `jvmti_env`: A pointer to the JVMTI environment.
    - `record`: An ASCII string that contains a verbose log record.
    - `length`: The number of ASCII characters in the verbose log record.
    - `user_data`: User data supplied when the subscriber is registered.


#### `jvmtiVerboseGCAlarm` function

: The alarm function type is defined as follows:

        typedef jvmtiError (*jvmtiVerboseGCAlarm)(jvmtiEnv *jvmti_env, void *subscription_id, void *user_data);

    The alarm function must be of type `jvmtiVerboseGCAlarm`, which is declared in `ibmjvmti.h`. This function is called if the subscriber function returns an error.

    This function runs under the same restrictions as the `GarbageCollectionStart` and `GarbageCollectionFinish` events in the standard JVMTI specification. For more information about these events, see the [JVMTI Event Index](https://docs.oracle.com/en/java/javase/20/docs/specs/jvmti.html#EventIndex) for your OpenJDK version.

    **Alarm function parameters**

    - `jvmti_env`: A pointer to the JVMTI environment.
    - `user_data`: User data supplied when the subscriber is registered.
    - `subscription_id`: The subscription identifier.

---

<!-- ==================================================================================================== -->

### `DeregisterVerboseGCSubscriber`

You can unsubscribe from verbose Garbage Collection (GC) data logging by using the `DeregisterVerboseGCSubscriber()` API:

    jvmtiError DeregisterVerboseGCSubscriber(jvmtiEnv* jvmti_env, void *userData, void *subscription_id)

After the `DeregisterVerboseGCSubscriber()` API is called, no further calls are made to the previously registered subscriber function.

**Parameters**

- `jvmti_env`: A pointer to the JVMTI environment.
- `subscription_id`: The subscription identifier that is returned by the call to the [RegisterVerboseGCSubscriber()](#registerverbosegcsubscriber) API.

**Returns**

`JVMTI_ERROR_NONE`: Success.  
`JVMTI_ERROR_NULL_POINTER`: The `subscription_id` parameter is null.  
`JVMTI_ERROR_OUT_OF_MEMORY`: There is insufficient system memory to process the request.  
`JVMTI_ERROR_INVALID_ENVIRONMENT`: The `jvmti_env` parameter is not valid.  
`JVMTI_ERROR_WRONG_PHASE`: The extension has been called outside the JVMTI live phase.

**Identifiers**

JVMTI Extension Function identifier: `com.ibm.DeregisterVerboseGCSubscriber`  
Macro declaration in the `ibmjvmti.h` file: `COM_IBM_DEREGISTER_VERBOSEGC_SUBSCRIBER`

---

<!-- ==================================================================================================== -->


<!-- ==== END OF TOPIC ==== interface_jvmti.md ==== -->
