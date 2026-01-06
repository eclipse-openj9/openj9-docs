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

# Dump viewer (`jdmpview`)


The dump viewer is a command-line tool that allows you to examine the contents of system dumps produced from the Eclipse OpenJ9&trade; VM. The dump viewer allows you to view both Java&trade; and native information from the time the dump was produced. You can run the dump viewer on one platform to work with dumps from another platform.

For long running tasks, the dump viewer can also be run in batch mode.

The dump viewer is useful for diagnosing `OutOfMemoryError` exceptions in Java&trade; applications. For problems like general protection faults (GPFs), system abends, and SIGSEGVs, a system debugger such as **gdb** (Linux) provides more information.


## Syntax

### Starting the dump viewer

`jdmpview [-J<vm option>] (-core <core file> | -zip <zip file>) [-notemp]`


| Input option            | Explanation                                                                                            |
|-------------------------|--------------------------------------------------------------------------------------------------------|
| `-core <core file>`     | Specifies a dump file.                                                                                 |
| `-zip <zip file>`       | Specifies a compressed file containing the core file (produced by the [dump extractor (`jpackcore`)](tool_jextract.md) tool on AIX&reg;, Linux&reg;, and macOS&reg; systems). |
| `-notemp`               | By default, when you specify a file by using the `-zip` option, the contents are extracted to a temporary directory before processing. Use the `-notemp` option to prevent this extraction step, and run all subsequent commands in memory. |
| `-J-Dcom.ibm.j9ddr.path.mapping=<mappings>` | The variable `<mappings>` is a list of native library mappings of the form `old-path=new-path`, using the usual path separator (a semi-colon (';') on Windows, and a colon (':') on other platforms). |
| `-J-Dcom.ibm.j9ddr.library.path=<path>` | The variable `<path>` is a list of paths to search for native libraries, using the usual path separator (a semi-colon (';') on Windows, and a colon (':') on other platforms). |

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The `-core` option can be used with the `-zip` option to specify the core file in the compressed file. With these options, `jdmpview` shows multiple contexts, one for each source file that it identified in the compressed file.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** On AIX and Linux, some `jdmpview` commands must locate the executable and the native libraries that are referenced by the core. For example, commands that relate to call-sites.
A common scenario involves using `jdmpview` to examine core files that originate on different systems. However, if the executable and the libraries are in their original locations, `jdmpview` might not consider them. Therefore, first check the executable and the list of native libraries by running `jdmpview` on a core with the `info mod` command.

- One way to assist `jdmpview` to locate those files is by specifying on the command line one or both of the path mapping option (`-J-Dcom.ibm.j9ddr.path.mapping=<mappings>`) and the library path option (`-J-Dcom.ibm.j9ddr.library.path=<path>`).

- Alternatively, on the system where the core file was produced, you can use `jpackcore` to collect all the relevant files into a single zip archive. That archive can be unpacked, possibly on another system, into a new, empty directory. Running `jdmpview` in that new directory (where the core file will be located) should enable it to find all the data it needs, including information that might not be included in the core file itself, such as symbols or sections. When you use an archive produced by `jpackcore`, setting the path or library mapping system properties should not be necessary.

On z/OS&reg;, you can copy the dump to an HFS file and supply that as input to `jdmpview`, or you can supply a fully qualified MVS&trade; data set name. For example:

```
> jdmpview -core USER1.JVM.TDUMP.SSHD6.D070430.T092211
DTFJView version 4.29.5, using DTFJ version 1.12.29003
Loading image from DTFJ...
```

MVS data set names may contain the dollar sign ($). Names that contain a dollar sign must be enclosed by single quotation marks ('). For example:

```
> jdmpview -core 'USER1.JVM.$TDUMP.SSH$D7.D141211.T045506'
```

After `jdmpview` processes the dump files, a session starts, showing this message:

```
For a list of commands, type "help"; for how to use "help", type "help help"
>
```

If you run the `jdmpview` tool on a compressed file that contains multiple dumps, the tool detects and shows all the dump files, whether these are system dumps, Java dumps, or heap dumps. Because of this behavior, more than one context might be displayed when you start `jdmpview`. To switch context, type `context <n>`, where `<n>` is the context value for the dump you want to investigate.

On z/OS, a system dump can contain multiple address spaces and an address space can contain multiple VM instances. In this case, the context allows you to select the address space and VM instance within the dump file. The following z/OS example shows address spaces (`ASID`), with two JVMs occupying address space `0x73` (context 5 and 6). The current context is 5 (`CTX:5>`), shown with an asterisk. To view the JVM in context 6, you can switch by specifying `context 6`.  

```
CTX:5> context
Available contexts (* = currently selected context) :

0 : ASID: 0x1 : No JRE : No JRE
1 : ASID: 0x3 : No JRE : No JRE
2 : ASID: 0x4 : No JRE : No JRE
3 : ASID: 0x6 : No JRE : No JRE
4 : ASID: 0x7 : No JRE : No JRE
*5 : ASID: 0x73 EDB: 0x83d2053a0 : JRE 1.8.0 z/OS s390x-64 build 20181117_128845 (pmz6480-20181120_01)
6 : ASID: 0x73 EDB: 0x8004053a0 : JRE 1.8.0 z/OS s390x-64 build 20181117_128845 (pmz6480-20181120_01)
7 : ASID: 0x73 EDB: 0x4a7bd9e8 : No JRE
8 : ASID: 0xffff : No JRE : No JRE
```

If you are using `jdmpview` to view Java dumps and heap dumps, some options do not produce any output. For example, a heap dump doesn't contain the information requested by the `info system` command, but does contain information requested by the `info class` command.

If you are viewing a dump where there are a large number of objects on the heap, you can speed up the performance of `jdmpview` by ensuring that your system has enough memory available and does not need to page memory to disk. To achieve this, start `jdmpview` with a larger heap size by specifying the `-Xmx` option. Use the `-J` option to pass the `-Xmx` command line option to the JVM. For example:

```
jdmpview -J-Xmx<n> -core <core file>
```

The options available to the dump viewer session are shown under [Session parameters](#session-parameters)

### Starting in batch mode

For long running or routine jobs, `jdmpview` can be used in batch mode.

You can run a single command without specifying a command file by appending the command to the end of the `jdmpview` command line. For example:

    jdmpview -core mycore.dmp info class

When specifying jdmpview commands that accept a wildcard parameter, you must replace the wildcard symbol with `ALL` to prevent the shell interpreting the wildcard symbol. For example, in interactive mode, the command `info thread *` must be specified in the following way:

    jdmpview -core mycore.dmp info thread ALL

Batch mode is controlled with the following command line options:

| Option                         | Explanation                                                                                                                                                           |
|--------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| -cmdfile &lt;path to command file&gt;| A file that contains a series of jdmpview commands, which are read and run sequentially.                                                                              |
| -charset &lt;character set name&gt;  | The character set for the commands specified in -cmdfile (name must be a supported charset as defined in java.nio.charset.Charset. For example, US-ASCII)             |
| -outfile &lt;path to output file&gt; | The file to record any output generated by commands.                                                                                                                  |
| -overwrite                     | If the file specified in -outfile exists, this option overwrites the file.                                                                                            |
| -append                        |If the file specified in -outfile exists, new output messages are appended to the end of that file. The -append and -overwrite options cannot be used at the same time.|

The command file can have empty lines that contain spaces, or comment lines that start with // or #. These lines are ignored by jdmpview. Example command file:

    // commands.txt
    info system
    info proc

To run jdmpview in batch mode, using this command file, specify:

    jdmpview -outfile out.txt [-overwrite|-append] -cmdfile commands.txt -core <path to core file>

When the output file exists, you need to specify either the `-overwrite` option or the `-append` option. If you do not, an error message is generated.

### Processing output

You can redirect command output to a file, or pipe the command output to another command.

To redirect jdmpview command output to a file, use one of the following formats:

    command > <target_file>

If the target file exists, this redirection overwrites the content within it.

    command >> <target_file>

If the target file exists, this redirection appends the output to it.

Where `<target_file>` is the file name, which can include the full path to the file.

To pipe `jdmpview` command output to another command, use the vertical bar (|) character. For example:

    command | grep string

You can chain more than two commands together by using multiple vertical bar characters.

The following commands can be used to interrogate the output:

- [`charsFrom`](#using-`charsfrom`)
- [`charsTo`](#using-`charsto`)
- [`grep`](#using-`grep`)
- [`tokens`](#using-`tokens`)

#### Using `CharsFrom`

Use the `charsFrom` command after the vertical bar character to exclude all characters that come before a specified pattern in a resulting line.

    charsFrom <options> pattern

Where `<options>`:

- `-e` or `-exclude` : Exclude the matched pattern from the resulting line. By default, the matched pattern is included in the resulting line.
- `-keep` : Keep lines that do not contain a match to the pattern. By default, lines that do not contain a match are excluded from the results.
- `-i` or `-ignoreCase` : Ignore case.

For example, the following command displays resulting lines that contain the pattern `jre`, and trims each line to exclude all characters that come before this pattern:

    > info mod | charsFrom jre
    jre/lib/ppc64/libzip.so @ 0x0, sections:
    jre/lib/ppc64/libdbgwrapper80.so @ 0x0, sections:
    jre/lib/ppc64/libverify.so @ 0x0, sections:
    jre/lib/ppc64/libjava.so @ 0x0, sections:
    jre/lib/ppc64/compressedrefs/libjclse7b_28.so @ 0x0, sections:

#### Using `CharsTo`

Use the `CharsTo` command after the vertical bar character to include the characters in a resulting line until a specific pattern is found.

    charsTo <options> pattern

Where `<options>`:

- `-include` : Include the matched pattern in the resulting line. By default, the matched pattern is excluded from the resulting line.
- `-keep` : Keep lines that do not contain a match to the pattern. By default, lines that do not contain a match are excluded from the results.
- `-i` or `-ignoreCase` : Ignore case.

For example, the following command displays lines that contain the pattern `@`, and trims each line to exclude all characters from `@` onwards:

    > info mod | charsTo @
    bin/java
    /usr/lib64/gconv/UTF-16.so
    /test/sdk/lib/ppc64le/libnet.so
    /test/sdk/lib/ppc64le/libnio.so
    /test/sdk/lib/ppc64le/libzip.so
    /test/sdk/lib/ppc64le/libjsig.so
    libjsig.so

You can also use `charsFrom` and `charsTo` together, separated by a vertical bar character. For example, the following command displays lines that contain the pattern `lib`, and trims each line to exclude all characters that come before this pattern, as well as all characters from the pattern `@` :

    > info mod | charsFrom lib | charsTo @
    lib/ppc64le/libzip.so
    lib/ppc64le/libjsig.so
    lib/ppc64le/libverify.so
    lib/ppc64le/libjava.so
    lib/ppc64le/compressedrefs/libj9jit29.so

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The line will not be displayed if the `charsFrom` and `charsTo` are used together, but only one of the patterns are matched in a line. Furthermore, the line will not be displayed if both patterns are matched in a line, but the `charsTo` pattern appears before, and not after, the `charsFrom` pattern.

#### Using `grep`

Use the `grep` command after the vertical bar character to show which lines match a specified pattern.

    grep <options> pattern

Where `<options>`:

- `-i` : Ignore case.
- `-r`, `-G`, or `--regex`: Use a regular expression as defined in the Java documentation of the java.utils.regex.Pattern class.
- `-b` or `--block` : Show blocks of lines where at least one of the lines matches the pattern. Blocks of lines are separated by empty lines.
- `-A` &lt;NUM&gt; or +&lt;NUM&gt; : Show at most &lt;NUM&gt; lines after the matching line. For example `grep -A 2 <pattern>` or `grep +2 <pattern>`.
- `-B` &lt;NUM&gt; or -&lt;NUM&gt; : Show at most &lt;NUM&gt; lines before the matching line.
- `-C` &lt;NUM&gt; or +-&lt;NUM&gt; : Show at most &lt;NUM&gt; lines before and after the matching line.
- `-v` or `--invert-match` : Use with the grep command to show lines that do not match the pattern. These options are equivalent to the `grep` command.
- `-F` or `--fixed-strings` : Do not treat the asterisk (\*) as a wildcard character. Use these options with the `-r`, `-G`, or `--regex` options.

Pattern rules:

- An asterisk (\*) in a pattern is treated as a wildcard character unless you specify the `-F` or `--fixed-strings` options.
- If a pattern contains spaces, enclose the pattern in a pair of double quotation marks (").
- If a pattern contains double quotation marks, enclose the pattern in a pair of single quotation marks (').
- You can specify multiple sub-patterns to match by using the following format, but only if you do not use the `-r`, `-G`, or `--regex` options:

    ```
    "[pattern1|pattern2|...|patternN]"
    ```  

    The initial and trailing double quotation marks and brackets ([ ]) are required. Use a vertical bar character to separate the sub-patterns. Quotation marks and the vertical bar are not allowed in a sub-pattern. Spaces are allowed in the middle of a sub-pattern, but leading and trailing spaces will be trimmed.

- Use the `grep` command to show lines that do not match the pattern.

In the following example, the command displays the number of instances and total heap size for the `java/lang/String` class:

    > info class | grep java/lang/String
    94 7688 [Ljava/lang/String;
    1822 58304 java/lang/String               
    1 16 java/lang/String$CaseInsensitiveComparator              
    0 0 java/lang/String$UnsafeHelpers

In the following example, the command uses two pipes in combination to display the number of instances and total heap size for the `java/lang/StringCoding.StringDecoder` class:

    > info class | grep java/lang/String | grep -i decoder
    1 48 java/lang/StringCoding$StringDecoder

#### Using `tokens`

Use the `tokens` command after the vertical bar character to isolate specified tokens in the resulting lines.

    tokens [options] range[,range][..range]

You can define range in the following formats:

- `x`
- `x,y`
- `x..y`

A set of rules applies to these formats:

- `x` or `y` can be prefixed with `-`. This means that `x` or `y` are counting backwards from the end of a list. For example, a `y` value of `-1` represents the last token in a list, while -2 represents the penultimate token in a list.
- `x` must represent a token that either precedes or is at the same position as `y`.

In this format, if `x` is omitted, it is assumed to be `1`. If `y` is omitted, it is assumed to be `-1`.

For example, the following command displays the first and second token for each resulting line:

    > info mmap | grep -r ^0x | tokens 1,2
    0x0000000000012fff 0x000000000000d000
    0x0000000000017fff 0x0000000000004000
    0x00000000009dafff 0x0000000000018000
    0x00000000009fffff 0x000000000001f000
    0x0000000000cbefff 0x0000000000002000
    0x0000000000d76fff 0x0000000000001000
    0x0000000003145fff 0x0000000000071000
    0x0000000003b93fff 0x0000000000003000

## Session parameters

When `jdmpview` is started, many parameters can be used during the session to interrogate the system dump data, which are divided into *general* and *expert* parameters. The *general* parameters are documented in this section. To see a list of *expert* parameters, use the `!j9help` option.

### !j9help

        !j9help

: Lists all *expert* parameters that can be used in a session, with a brief description.

:fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** The *expert* parameters are subject to change and not intended as a supported interface.

### cd

        cd <directory_name>


: Changes the working directory to `<directory_name>`. The working directory is used for log files. Logging is controlled by the `set logging` command. Use the `pwd` command to query the current working directory.

### cmdfile

        cmdfile <directory_name>

: Runs all of the commands in a file. The commands are read line by line and run sequentially. Empty lines, and lines that start with **//** or **#**, are ignored. Use the option charset to identify the character set that is used in the chosen file. The character set must be supported, as defined in `java.nio.charset.Charset`, such as `US-ASCII`.

### deadlock

: This command detects deadlock situations in the Java application that was running when the system dump was produced. Example output:

        deadlock loop:
        thread: Thread-2 (monitor object: 0x9e32c8) waiting for =>
        thread: Thread-3 (monitor object: 0x9e3300) waiting for =>
        thread: Thread-2 (monitor object: 0x9e32c8)

    In this example, the deadlock analysis shows that `Thread-2` is waiting for a lock held by `Thread-3`, which is in turn waiting for a lock held earlier by `Thread-2`.

    Threads are identified by their Java thread name, whereas object monitors are identified by the address of the object in the Java heap. You can obtain further information about the threads using the `info thread *` command. You can obtain further information about the monitors using the `x/J <0xaddr>` command.

### find

        find <pattern>,<start_address>,<end_address>,<memory_boundary>, <bytes_to_print>,<matches_to_display>

: This command searches for `<pattern>` in the memory segment from `<start_address>` to `<end_address>` (both inclusive), and shows the number of matching addresses you specify with `<matches_to_display>`. You can also display the next `<bytes_to_print>` bytes for the last match.

    By default, the `find` command searches for the pattern at every byte in the range. If you know the pattern is aligned to a particular byte boundary, you can specify `<memory_boundary>` to search every `<memory_boundary>` bytes. For example, if you specify a `<memory_boundary>` of "4", the command searches for the pattern every 4 bytes.

### findnext

: Finds the next instance of the last string passed to `find` or `findptr`. It repeats the previous `find` or `findptr` command, depending on which one was issued last, starting from the last match.

### findptr

        findptr <pattern>,<start_address>,<end_address>,<memory_boundary>,<bytes_to_print>,<matches_to_display>


:  Searches memory for the given pointer. `findptr` searches for `<pattern>` as a pointer in the memory segment from `<start_address>` to `<end_address>` (both inclusive), and shows the number of matching addresses you specify with `<matches_to_display>`. You can also display the next `<bytes_to_print>` bytes for the last match.

    By default, the `findptr` command searches for the pattern at every byte in the range. If you know the pattern is aligned to a particular byte boundary, you can specify `<memory_boundary>` to search every `<memory_boundary>` bytes. For example, if you specify a `<memory_boundary>` of "4", the command searches for the pattern every 4 bytes.

### help

        help [<command_name>]

: Shows information for a specific command. If you supply no parameters, help shows the complete list of supported commands.

### history

        history|his [-r][<N>]

: Recalls and displays the history of commands that you have run. The default behavior is to display the 20 most recent commands. If you use the argument `<N>`, then N commands are displayed. For example, if you run history 35, then the 35 most recent commands are displayed. You can also use the `-r` option with `<N>` to run the Nth most recent command in your history. Using the `-r` option alone runs the most recent command in your history.

### info thread

        info thread [*|all|<native_thread_ID>|<zos_TCB_address>]

: Displays information about Java and native threads. The following information is displayed for all threads ("\*"), or the specified thread:

    - Thread id
    - Registers
    - Stack sections
    - Thread frames: procedure name and base pointer
    - Thread properties: list of native thread properties and their values. For example: thread priority.
    - Associated Java thread, if applicable:
        - Name of Java thread
        - Address of associated `java.lang.Thread` object
        - State (shown in JVMTI and `java.lang.Thread.State` formats)
        - The monitor the thread is waiting for
        - Thread frames: base pointer, method, and filename:line

    If you supply no parameters, the command shows information about the current thread.

### info system

: Displays the following information about the system that produced the core dump:

    - Amount of memory
    - Operating system
    - Virtual machine or virtual machines present

### info class

        info class [<class_name>] [-sort:<name>|<count>|<size>]

: Displays the inheritance chain and other data for a given class.

: If a class name is passed to info class, the following information is shown about that class:

    - Name
    - ID
    - Superclass ID
    - Class loader ID
    - Modifiers
    - Number of instances and total size of instances
    - Inheritance chain
    - Fields with modifiers (and values for static fields)
    - Methods with modifiers

: If no parameters are passed to `info class`, the following information is shown:

    - The number of instances of each class.
    - The total size of all instances of each class.
    - The class name
    - The total number of instances of all classes.
    - The total size of all objects.

: The `sort` option allows the list of classes to be sorted by name (default), by number of instances of each class, or by the total size of instances of each class.


### info proc

: Displays threads, command-line arguments, environment variables, and shared modules of the current process.

    To view the shared modules used by a process, use the `info sym` command.

### info jitm

: Displays JIT compiled methods and their addresses:

    - Method name and signature
    - Method start address
    - Method end address

### info lock

: Displays a list of available monitors and locked objects.

### info sym

: Displays a list of available modules. For each process in the address spaces, this command shows a list of module sections for each module, their start and end addresses, names, and sizes.

### info mmap

        info mmap [<address>] [-verbose] [-sort:<size>|<address>]

: Displays a summary list of memory sections in the process address space, with start and end address, size, and properties. If an address parameter is specified, the results show details of only the memory section containing the address. If `-verbose` is specified, full details of the properties of each memory section are displayed. The `-sort` option allows the list of memory sections to be sorted by size or by start address (default).

### info mod

: Displays a list of native library modules in the process address space, which includes file paths and other information about each module.

### info heap

        info heap [*|<heap_name>*]

: If no parameters are passed to this command, the heap names and heap sections are shown.

    Using either "\*" or a heap name shows the following information about all heaps or the specified heap:

    - Heap name
    - (Heap size and occupancy)
    - Heap sections
        - Section name
        - Section size
        - Whether the section is shared
        - Whether the section is executable
        - Whether the section is read only


### heapdump

        heapdump [<heaps>]

: Generates a Java heap dump to a file. You can select which Java heaps to dump by listing the heap names, separated by spaces. To see which heaps are available, use the `info heap` command. By default, all Java heaps are dumped.

### hexdump

        hexdump <hex_address> <bytes_to_print>

: Displays a section of memory in a hexdump-like format. Displays `<bytes_to_print>` bytes of memory contents starting from `<hex_address>`.

### +

: Displays the next section of memory in hexdump-like format. This command is used with the hexdump command to enable easy scrolling forwards through memory. The previous hexdump command is repeated, starting from the end of the previous one.

### -

: Displays the previous section of memory in hexdump-like format. This command is used with the hexdump command to enable easy scrolling backwards through memory. The previous hexdump command is repeated, starting from a position before the previous one.

### pwd

: Displays the current working directory, which is the directory where log files are stored.

### quit

: Exits the core file viewing tool; any log files that are currently open are closed before exit.

### set heapdump

: Configures Heapdump generation settings.

        set heapdump <options>

: where `<options>` are:

    - `phd`: Set the Heapdump format to Portable Heapdump, which is the default.
    - `txt`: Set the Heapdump format to classic.
    - `file <file>`: Set the destination of the Heapdump.
    - `multiplefiles [on|off]`: If multiplefiles is set to on, each Java heap in the system dump is written to a separate file. If multiplefiles is set to off, all Java heaps are written to the same file. The default is off.

### set logging

        set logging <options>

: Configures logging settings, starts logging, or stops logging. This parameter enables the results of commands to be logged to a file, where `<options>` are:

    - `[on|off]`: Turns logging on or off. (Default: off)
    - `file <filename>`: Sets the file to log to. The path is relative to the directory returned by the pwd command, unless an absolute path is specified. If the file is set while logging is on, the change takes effect the next time logging is started. Not set by default.
    - `overwrite [on|off]`: Turns overwriting of the specified log file on or off. When overwrite is off, log messages are appended to the log file. When overwrite is on, the log file is overwritten after the set logging command. (Default: off)
    - `redirect [on|off]`: Turns redirecting to file on or off, with off being the default. When logging is set to on:
        - A value of on for redirect sends non-error output only to the log file.
        - A value of off for redirect sends non-error output to the console and log file.

    Redirect must be turned off before logging can be turned off. (Default: off)

### show heapdump

        show heapdump <options>

: Displays the current heap dump generation settings.

### show logging

        show logging <options>

: Displays the current logging settings:

    - set_logging = [on|off]
    - set_logging_file =
    - set_logging_overwrite = [on|off]
    - set_logging_redirect = [on|off]
    - current_logging_file =

    The file that is currently being logged to might be different from set_logging_file, if that value was changed after logging was started.

### whatis `<hex_address>`

: Displays information about `whatis` stored at the given memory address, `<hex_address>`. This command examines the memory location at `<hex_address>` and tries to find out more information about this address. For example:


        > whatis 0x8e76a8

        heap #1 - name: Default@19fce8
        0x8e76a8 is within heap segment: 8b0000 -- cb0000
        0x8e76a8 is start of an object of type java/lang/Thread


### x/ (examine)

: Passes the number of items to display and the unit size, as listed in the following table, to the sub-command. For example, `x/12bd`.

    |Abbreviation | Unit       | Size    |
    |-------------|------------|---------|
    |b            | Byte       | 8-bit   |
    |h            | Half word  | 16-bit  |
    |w            | Word       | 32-bit  |
    |g            | Giant word | 64-bit  |

    This command is similar to the use of the `x/` command in gdb, including the use of defaults.


### x/J [`<class_name>`|`<0xaddr>`]

: Displays information about a particular object, or all objects of a class. If `<class_name>` is supplied, all static fields with their values are shown, followed by all objects of that class with their fields and values. If an object address (in hex) is supplied, static fields for that object's class are not shown; the other fields and values of that object are printed along with its address.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This command ignores the number of items and unit size passed to it by the `x/` command.

### x/D <`0xaddr`>

: Displays the integer at the specified address, adjusted for the hardware architecture this dump file is from. For example, the file might be from a big-endian architecture.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} This command uses the number of items and unit size passed to it by the `x/` command.

### x/X <`0xaddr`>

: Displays the hex value of the bytes at the specified address, adjusted for the hardware architecture this dump file is from. For example, the file might be from a big-endian architecture.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This command uses the number of items and unit size passed to it by the `x/` command.

### x/K <`0xaddr`>

: Where the size is defined by the pointer size of the architecture, this parameter shows the value of each section of memory. The output is adjusted for the hardware architecture this dump file is from, starting at the specified address. It also displays a module with a module section and an offset from the start of that module section in memory if the pointer points to that module section. If no symbol is found, it displays a "\*" and an offset from the current address if the pointer points to an address in 4KB (4096 bytes) of the current address. Although this command can work on an arbitrary section of memory, it is probably more useful on a section of memory that refers to a stack frame. To find the memory section of a thread stack frame, use the info thread command.

    :fontawesome-solid-pencil:{: .note aria-hidden="true"} **Note:** This command uses the number of items and unit size passed to it by the `x/` command.


## Example

This example session illustrates a selection of the commands available and their use.

In the example session, which is generated on a Linux system, some lines have been removed for clarity (and terseness).

User input is prefaced by a greater than symbol (>).

```
    test@madras:~/test> sdk/bin/jdmpview -core core.20121116.154147.16838.0001.dmp
    DTFJView version 4.29.5, using DTFJ version 1.12.29003
    Loading image from DTFJ...

    For a list of commands, type "help"; for how to use "help", type "help help"
    Available contexts (* = currently selected context) :

    Source : file:///home/test/core.20121116.154147.16838.0001.dmp
            *0 : PID: 16867 : JRE 1.8.0 Linux ppc64-64 build 20121115_128521 (pxp6480-20121116_01 )

    > help
    +                                              displays the next section of memory in hexdump-like format
    -                                              displays the previous section of memory in hexdump-like format
    cd                                             changes the current working directory, used for log files
    close                     [context id]         closes the connection to a core file
    context                   [ID|asid ID]         switch to the selected context
    deadlock                                       displays information about deadlocks if there are any
    exit                                           Exit the application
    find                                           searches memory for a given string
    findnext                                       finds the next instance of the last string passed to "find"
    findptr                                        searches memory for the given pointer
    heapdump                                       generates a PHD or classic format heapdump
    help                      [command name]       displays list of commands or help for a specific command
    hexdump                                        outputs a section of memory in a hexdump-like format
    info                      <component>          Information about the specified component
    info class                <Java class name>    Provides information about the specified Java class
    info heap                 [*|heap name]        Displays information about Java heaps
    info jitm                                      Displays JIT'ed methods and their addresses
    info lock                                      outputs a list of system monitors and locked objects
    info mmap                                      Outputs a list of all memory segments in the address space
    info mod                                       outputs module information
    info proc                                      shortened form of info process
    info process                                   displays threads, command line arguments, environment
    info sym                                       an alias for 'mod'
    info sys                                       shortened form of info system
    info system                                    displays information about the system the core dump is from
    info thread                                    displays information about Java and native threads
    log                       [name level]         display and control instances of java.util.logging.Logger
    open                      [path to core or zip] opens the specified core or zip file
    plugins                                        Plugin management commands
                         list                      Show the list of loaded plug-ins for the current context
                       reload                      Reload plug-ins for the current context
                     showpath                      Show the DTFJ View plug-in search path for the current context
                      setpath                      Set the DTFJ View plug-in search path for the current context
    pwd                                            displays the current working directory
    quit                                           Exit the application
    set                       [logging|heapdump]   Sets options for the specified command
    set heapdump                                   configures heapdump format, filename and multiple heap support
    set logging                                    configures several logging-related parameters, starts/stops logging
                           on                      turn on logging
                          off                      turn off logging
                         file                      turn on logging
                    overwrite                      controls the overwriting of log files
    show                      [logging|heapdump]   Displays the current set options for a command
    show heapdump                                  displays heapdump settings
    show logging                                   shows the current logging options
    whatis                    [hex address]        gives information about what is stored at the given memory address
    x/d                       <hex address>        displays the integer at the specified address
    x/j                       <object address> [class name] displays information about a particular object or all objects of a class
    x/k                       <hex address>        displays the specified memory section as if it were a stack frame parameters
    x/x                       <hex address>        displays the hex value of the bytes at the specified address

    > set logging file log.txt
    logging turned on; outputting to "/home/test/log.txt"

    > info system
    Machine OS:     Linux
    Hypervisor:     PowerVM
    Machine name:   madras
    Machine IP address(es):
                    9.20.88.155
    System memory:  8269201408

    Dump creation time: 2015/08/10 14:44:36:019
    Dump creation time (nanoseconds): 21314421467539

    Java version:
    JRE 1.8.0 Linux ppc64-64 build 20121115_128521 (pxp6490-20121116_01)

    JVM start time: 2015/08/10 14:44:05:690
    JVM start time (nanoseconds): 21284086192267

    > info thread *
    native threads for address space
     process id: 16838

      thread id: 16839
       registers:

       native stack sections:
       native stack frames:
       properties:
       associated Java thread:
        name:          main
        Thread object: java/lang/Thread @ 0x2ffd1e08
        Priority:      5
        Thread.State:  RUNNABLE
        JVMTI state:   ALIVE RUNNABLE
        Java stack frames:
         bp: 0x0000000000085b28  method: void com/ibm/jvm/Dump.SystemDumpImpl()  (Native Method)
          objects: <no objects in this frame>
         bp: 0x0000000000085b40  method: void com/ibm/jvm/Dump.SystemDump()  source: Dump.java:41
          objects: <no objects in this frame>
         bp: 0x0000000000085b68  method: void mySystemDump.main(String[])  source: mySystemDump.java:29
          objects: <no objects in this frame>
    ===Lines Removed===

        name:          GC Worker
        id:            16860
        Thread object: java/lang/Thread @ 0x3001b980
        Priority:      5
        Thread.State:  WAITING
        JVMTI state:   ALIVE WAITING WAITING_INDEFINITELY IN_OBJECT_WAIT
          waiting to be notified on: "MM_ParallelDispatcher::workerThread" with ID 0x1004cbc8 owner name: <unowned>
        Java stack frames: <no frames to print>

        name:          GC Worker
        id:            16861
        Thread object: java/lang/Thread @ 0x3001c180
        Priority:      5
        Thread.State:  WAITING
        JVMTI state:   ALIVE WAITING WAITING_INDEFINITELY IN_OBJECT_WAIT
          waiting to be notified on: "MM_ParallelDispatcher::workerThread" with ID 0x1004cbc8 owner name: <unowned>
        Java stack frames: <no frames to print>

        name:          Signal Dispatcher
        id:            16847
        Thread object: com/ibm/misc/SignalDispatcher @ 0x3000f268
        Priority:      5
        Thread.State:  RUNNABLE
        JVMTI state:   ALIVE RUNNABLE
        Java stack frames:
         bp: 0x00000000000df748  method: int com/ibm/misc/SignalDispatcher.waitForSignal()  (Native Method)
          objects: <no objects in this frame>
         bp: 0x00000000000df788  method: void com/ibm/misc/SignalDispatcher.run()  source: SignalDispatcher.java:54
          objects: 0x30015828 0x30015828
    ===Lines Removed===


    > info heap *

             Heap #1:  Generational@fff78303d30
              Section #1:  Heap extent at 0x100d0000 (0x300000 bytes)
               Size:        3145728 bytes
               Shared:      false
               Executable:  false
               Read Only:   false
              Section #2:  Heap extent at 0x2ffd0000 (0x80000 bytes)
               Size:        524288 bytes
               Shared:      false
               Executable:  false
               Read Only:   false
              Section #3:  Heap extent at 0x30050000 (0x80000 bytes)
               Size:        524288 bytes
               Shared:      false
               Executable:  false
               Read Only:   false

    > info class java/lang/String
    name = java/lang/String

            ID = 0x37c00    superID = 0x30300
            classLoader = 0x2ffe9b58    modifiers: public final

            number of instances:     2146
            total size of instances: 51504 bytes

    Inheritance chain....
            java/lang/Object
               java/lang/String

    Fields......
              static fields for "java/lang/String"
                private static final long serialVersionUID = -6849794470754667710 (0xa0f0a4387a3bb342)
                public static final java.util.Comparator CASE_INSENSITIVE_ORDER = <object> @ 0x2ffd0278
                private static final char[] ascii = <object> @ 0x2ffd02c8
                private static String[] stringArray = <object> @ 0x2ffd0298
                private static final int stringArraySize = 10 (0xa)
                static boolean enableCopy = false
                private static int seed = -126504465 (0xfffffffff875b1ef)
                private static char[] startCombiningAbove = <object> @ 0x100d0c40
                private static char[] endCombiningAbove = <object> @ 0x100d0cc0
                private static final char[] upperValues = <object> @ 0x100d0d40
                private static final java.io.ObjectStreamField[] serialPersistentFields = <object> @ 0x2ffd0920

              non-static fields for "java/lang/String"
                private final char[] value
                private final int offset
                private final int count
                private int hashCode
                private int hashCode32

    Methods......

    Bytecode range(s): :  private static native int getSeed()
    Bytecode range(s): fff76d8ce48 -- fff76d8ce5e:  public void <init>()
    Bytecode range(s): fff76d8ce88 -- fff76d8cecd:  private void <init>(String, char)
    Bytecode range(s): fff76d8cf10 -- fff76d8cf19:  public void <init>(byte[])
    Bytecode range(s): fff76d8cf40 -- fff76d8cf4a:  public void <init>(byte[], int)
    Bytecode range(s): fff76d8cf7c -- fff76d8cfb5:  public void <init>(byte[], int, int)
    Bytecode range(s): fff76d8cff8 -- fff76d8d065:  public void <init>(byte[], int, int, int)
    Bytecode range(s): fff76d8d0c4 -- fff76d8d10c:  public void <init>(byte[], int, int, String)
    ===Lines Removed===

    > whatis  0x2ffd0298
            heap #1 - name: Generational@fff78303d30
                    0x2ffd0298 is within heap segment: 2ffd0000 -- 30050000
                    0x2ffd0298 is the start of an object of type [Ljava/lang/String;
```


<!-- ==== END OF TOPIC ==== tool_jdmpview.md ==== -->
