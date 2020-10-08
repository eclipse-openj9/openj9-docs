<!--
* Copyright (c) 2017, 2020 IBM Corp. and others
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
* [2] http://openjdk.java.net/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->


# Verbose garbage collection logs 

Garbage collection logs are collected as soon as garbage collection is initialised.

Verbose garbage collection output or log files contain information on garbage collection operations to assist with the following:

- tuning 
- troubleshooting.....
- ADD

The verbose garbage collection logs can be used with a variety of diagnostics tools and interfaces to provide monitoring and analysis capabilities.

To help you understand how a Java dump can help you with problem diagnosis, this topic includes a few scenarios to help you interpret the data:

1. Determining optimum initial and maximum heap sizes for your JVM. For more information see ()[mm_heapsizing_verbosegc.html]
2. Tuning particular parameters for the type of garbage collection policy your JVM is using. ()[mm_gc_pd_mgc_verbose.html]
3. 


*Note:* To perform further, detailed analysis, you can call one or more trace garbage collector (TGC) traces ('-XTgc' options). The  TGC traces collect more information than the '-verbose:gc' option. For more information, see ()[mm_gc_pd_tracing.html].

## How to generate a verbose garbage collection log

You can generate a verbose garbage collection log file using the `-verbose:gc`command. 

Alternatiely, to output the verbose gc log to a file, use the command `-Xverbosegclog` 

In addition, verbose garbage collection logs are triggered in the following instances TRUE?:

- stop-the-world operations?
- 


The following options can be used with the '-verbose:gc' command:

ADD A TABLE HERE? OR SEE THE 

-Xgc: verboseGCCycleTime=N

## Verbose garbage collection log contents

### Garbage Collection configuration

When garbage collection is initialised, the verbose garbage collection log records the garbage collection configuration, including:
- the garbage collection policy type
- the policy options, such as ADD
- JVM command-line options that are active at the time of garbage collection initialisation, such as ADD

### stop-the-world operations

During a stop-the-world  operation, an application is stopped so that the garbage collector has exclusive access to the JVM for actioning the freeing up of memory and memory compaction. The Verbose garbage collection log records the following information of the event:

### Garbage collection cycle

A garbage collection cycle is WHAT IS THIS?

### Garbage collection increment

## Scenarios

