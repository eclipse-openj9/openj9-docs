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

# Eclipse OpenJ9 VM messages

Messages are issued by the Eclipse OpenJ9&trade; virtual machine (VM) in response to certain conditions. Understanding what the messages
mean can help you with problem determination.

## Message categories

There are three main categories of message:

**Information**
:   Information messages provide information about VM processing. For example, a dump information message is typically issued when a dump agent requests a dump.

**Warning**
:   Warning messages are issued by the VM to indicate conditions that might need user intervention.

**Error**
:   Error messages are issued by the VM when normal processing cannot proceed, because of unexpected conditions.

OpenJ9 virtual machine messages have the following format:

```
    JVM<type><number><code>
```

where:

-   `JVM` is a standard prefix.
-   `<type>` refers to the VM subcomponent that issued the message.
-   `<number>` is a unique numerical number.
-   `<code>` is one of the following codes:
    -   `I` - Information message
    -   `W` - Warning message
    -   `E` - Error message


These messages can help you with problem determination.

By default, all error and some information messages are routed to the system log and also written to `stderr` or `stdout`. The specific information messages are `JVMDUMP039I`, `JVMDUMP032I`, and `JVMDUMP033I`, which provide valuable additional information about dumps produced by the VM. To route additional message types to the system log, or turn off message logging to the system log, use the [`-Xsyslog`](xsyslog.md) option. The `-Xsyslog` option does not affect messages written to the standard error stream (stderr). See [OpenJ9 command-line options](cmdline_specifying.md).

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The `-Xsyslog` option replaces the `-Xlog` option in OpenJ9 version 0.24.0.


## Finding logged messages

Logged messages can be found in different locations, according to platform.

### Finding AIX messages

On AIX&reg;, messages are logged by the syslog daemon (`/usr/sbin/syslogd`). Logged messages are written to the syslog file that is configured in `/etc/syslog.conf`. If the syslog daemon is not running, logged messages are lost.

You can redirect messages from the syslog daemon to the AIX error log facility by performing the following configuration steps:

1.  Set up a redirect in the file `syslog.conf` so that syslog messages are sent to the error log, by adding the following line:

```
        user.debug errlog
```

2.  If **syslogd** is already running, reload the updated configuration by running the following command:

```
        refresh -s syslogd
```

3.  The updated configuration is used each time **syslogd** starts. 4.  Use the AIX **errpt** command or the System Management Interface Tool (SMIT) to read the messages sent to the error log.

For more information about AIX logging, see: [Error-logging overview](https://www.ibm.com/support/knowledgecenter/ssw_aix_72/generalprogramming/logoverview.html).

### Finding Linux messages

On Linux&reg;, messages are logged by the **syslog** daemon. To find where messages are logged, check the syslog configuration file.

### Finding macOS messages

On macOS&reg;, messages are logged by the **syslog** daemon. However, on Sierra and High Sierra, syslog does not work. If `/var/log/system.log` is not available, `Console.app` can be used instead.

### Finding Windows messages

On Windows&trade;, messages are logged in the application events section of the event viewer.

### Finding z/OS messages

On z/OS&reg;, messages are sent to the operator console. To see the messages, go from the **ispf** panel to the **sdsf** panel, then open the **log** panel.

## Obtaining detailed message descriptions

Detailed message information is available to help with problem diagnosis.

Understanding the warning or error message issued by the VM can help you diagnose problems. All warning and error messages issued by the VM are listed by type in the messages guide: [IBM&reg; VM messages](https://www.ibm.com/support/knowledgecenter/SSYKE2_8.0.0/com.ibm.java.messages/diag/appendixes/messages/messages.html).

The messages, error codes, and exit codes in this guide apply to multiple versions of the VM.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** If the VM fills all available memory, the message number might be produced without a description for the error that caused the problem. Look for the message number in the relevant section of the J9 VM  Messages guide to see the message description and the additional information provided.

<!-- ==== END OF TOPIC ==== messages_intro.md ==== -->
