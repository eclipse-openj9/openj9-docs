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

# Heap dump

Heap dumps contain a snapshot of all the live objects that are being used by a running Java&trade; application on the Java heap.
You can obtain detailed information for each object instance, such as the address, type, class name, or size, and whether the instance has references to other objects.

There are two formats for heap dumps; the classic format and the Portable Heap Dump (PHD) format, which is the default.
Whilst the classic format is generated in ascii text and can be read, the PHD format is binary and and must be processed
for analysis.

## Obtaining dumps

Heap dumps are generated by default in PHD format when the Java heap runs out of space. If you want to trigger the production of a heap dump in response to other situations, or in classic format, you can use one of the following options:

- Configure the **heap** dump agent. For more information, see the [-Xdump](xdump.md) option.
- Use the `com.ibm.jvm.Dump` API programmatically in your application code. For more information, see the 
[JVM diagnostic utilities API documentation](api-jvm.md). <!-- Link to API -->

## Analyzing dumps

The best method to analyze a PHD heap dump is to use the [Eclipse Memory Analyzer tool (MAT)](https://www.eclipse.org/mat/) or the [IBM Memory Analyzer tool](https://www.ibm.com/support/knowledgecenter/en/SSYKE2_8.0.0/com.ibm.java.80.doc/diag/tools/tool_memoryanalyzer.html). These tools process the dump file and provide a visual representation of the objects in the Java Heap. Both tools require the Diagnostic Tool Framework for Java (DTFJ) plugin. To install the DTFJ plugin in the Eclipse IDE, select the following menu items:

```
Help > Install New Software > Work with "IBM Diagnostic Tool Framework for Java" > IBM Monitoring and Diagnostic Tools > Diagnostic Tool Framework for Java   
```
The following sections contain detailed information about the content of each type of heap dump file.

### Portable Heap Dump (PHD) format

A PHD format dump file contains a header section and a body section. The body section can contain information about object, array, or class records.
Primitive numbers are used to describe the file format, as detailed in the following table:

| Primitive number | Length in bytes                              |
|------------------|----------------------------------------------|
| `byte`           | 1                                            |
| `short`          | 2                                            |
| `int`            | 4                                            |
| `long`           | 8                                            |
| `word`           | 4 (32-bit platforms) or 8 (64-bit platforms) |

#### General structure

The following structure comprises the header section of a PHD file:

- A UTF string indicating that the file is a `portable heap dump`
- An `int` containing the PHD version number
- An `int` containing flags:
    - `1` indicates that the `word` length is 64-bit.
    - `2` indicates that all the objects in the dump are hashed. This flag is set for heap dumps that use 16-bit hash codes. OpenJ9 heap dumps use 32-bit hash codes that are created only when used. For example, these hash codes are created when the APIs `Object.hashCode()` or `Object.toString()` are called in a Java application. If this flag is not set, the presence of a hash code is indicated by the hash code flag on the individual PHD records.
    - `4` indicates that the dump is from an OpenJ9 VM.
- A `byte` containing a tag with a value of `1` that indicates the start of the header.
- A number of optional header records, each preceded by a one-byte header tag. Header record tags have a different range of values from the body, or object record tags. The end of the header is indicated by the end of header tag. The following tags are included:
    - `header tag 1` - not used
    - `header tag 2` - indicates the end of the header
    - `header tag 3` - not used
    - `header tag 4` - indicates the VM version (Variable length UTF string)

The body of a PHD file is indicated by a `byte` that contains a tag with a value of 2, after which there are a number of dump records. Dump records are preceded by a 1 byte tag with the following record types:

  - Short object: `0x80` bit of the tag is set
  - Medium object: `0x40` bit of the tag is set (top bit value is 0)
  - Primitive Array: `0x20` bit if the tag is set (all other tag values have the top 3 bits with a value of 0)
  - Long record: tag value is `4`
  - Class record: tag value is `6`
  - Long primitive array: tag value is `7`
  - Object array: tag value is `8`

These records are described in more detail in the sections that follow.

The end of the PHD body is indicated by a `byte` that contains a tag with a value of 3.

#### Object records

Object records can be short, medium, or long, depending on the number of object references in the heap dump.

**1. Short object record**

The following information is contained within the tag byte:

- The 1 byte tag, which consists of the following bits:

    | Bit number | Value or description                                                                                    |
    |------------|---------------------------------------------------------------------------------------------------------|
    | 1          | Bit is set (0x80)                                                                                       |
    | 2 and 3    | Indicates the class cache index. The value represents an index into a cache of the last 4 classes used. |         
    | 4 and 5    | Contain the number of references. Most objects contain 0 - 3 references. If there are 4 - 7 references, the **Medium object record** is used. If there are more than 7 references, the **Long object record** is used.                             |
    | 6          | Indicates whether the gap is a 1 `byte` value or a `short`. The gap is the difference between the address of this object and the previous object. If set, the gap is a `short`. If the gap does not fit into a `short`, the **Long object record** format is used.|
    | 7 and 8    | Indicates the size of each reference (0=`byte`, 1=`short`, 2=`int`, 3=`long`)                           |

- A `byte` or a `short` containing the gap between the address of this object and the address of the preceding object. The value is signed and represents the number of 32-bit words between the two addresses. Most gaps fit into 1 byte.
- If all objects are hashed, a `short` containing the hash code.
- The array of references, if references exist. The tag shows the number of elements, and the size of each element. The value in each element is the gap between the address of the references and the address of the current object. The value is a signed number of 32-bit words. Null references are not included.

**2. Medium object record**

These records provide the actual address of the class rather than a cache index. The following format is used:

- The 1 byte tag, consisting of the following bits:

    | Bit number | Value or description                                                                                 |
    |------------|------------------------------------------------------------------------------------------------------|
    | 1          |  0                                                                                                   |
    | 2          |  Set (0x40)                                                                                          |
    | 3, 4, and 5| Contain the number of references                                                                     |
    | 6          | Indicates whether the gap is a 1 `byte` value or a `short` (see **Short object record** description) |
    | 7 and 8    | Indicates the size of each reference (0=`byte`, 1=`short`, 2=`int`, 3=`long`)                        |

- A `byte` or a `short` containing the gap between the address of this object and the address of the preceding object (See the **Short object record** description)
- A `word` containing the address of the class of this object.
- If all objects are hashed, a `short` containing the hash code.
- The array of references (See the **Short object record** description).

**3. Long object record**

This record format is used when there are more than 7 references, or if there are extra flags or a hash code. The following format is used:

- The 1 byte tag, containing the value 4.

- A `byte` containing flags, consisting of the following bits:

    | Bit number | Value or description                                                                             |
    |------------|--------------------------------------------------------------------------------------------------|
    | 1 and 2    | Indicates whether the gap is a `byte`, `short`, `int` or `long` format                           |
    | 3 and 4    | Indicates the size of each reference  (0=`byte`, 1=`short`, 2=`int`, 3=`long`)                   |
    | 5 and 6    | Unused                                                                                           |
    | 7          | Indicates if the object was hashed and moved. If this bit is set, the record includes the hash code |
    | 8          | Indicates if the object was hashed                                                               |

- A `byte`, `short`, `int`, or `long` containing the gap between the address of this object and the address of the preceding object (See the **Short object record** description).
- A `word` containing the address of the class of this object.
- If all objects are hashed, a `short` containing the hash code. Otherwise, an optional `int` containing the hash code if the hashed and moved bit is set in the record flag byte.
- An `int` containing the length of the array of references.
- The array of references (See the **Short object record** description).



#### Array records

PHD arrays can be primitive arrays or object arrays, as described in the sections that follow.

**1. Primitive array record**

The following information is contained in an array record:

- The 1 byte tag, consisting of the following bits:

    | Bit number | Value or description                                                                                       |
    |------------|------------------------------------------------------------------------------------------------------------|
    | 1 and 2    |   0                                                                                                        |
    | 3          |  Set (0x20)                                                                                                |
    | 4, 5, and 6| Contains the array type ( 0=bool, 1=char, 2=float, 3=double, 4=`byte`, 5=`short`, 6=`int`, and 7=`long`)   |
    | 7 and 8    | Indicates the length of the array size and the length of the gap (0=`byte`, 1=`short`, 2=`int`, 3=`long`)  |

- `byte`, `short`, `int` or `long` containing the gap between the address of this object and the address of the preceding object (See the **Short object record** description).
- `byte`, `short`, `int` or `long` containing the array length.
- If all objects are hashed, a `short` containing the hash code.
- An unsigned `int` containing the size of the instance of the array on the heap, including header and padding. The size is measured in 32-bit words, which you can multiply by four to obtain the size in bytes. This format allows encoding of lengths up to 16GB in an unsigned `int`.

**2. Long primitive array record**

This type of record is used when a primitive array has been hashed.

- The 1 byte tag with a  value of 7.

- A byte containing the following flags:

    | Bit number  | Value or description                                                                                           |
    |-------------|----------------------------------------------------------------------------------------------------------------|
    | 1, 2, and 3 | Contains the array type ( 0=bool, 1=char, 2=float, 3=double, 4=`byte`, 5=`short`, 6=`int`, and 7=`long`)       |
    | 4           | Indicates the length of the array size and the length of the gap (0=`byte`, 1=`word`).                         |
    | 5 and 6     | Unused                                                                                                         |
    | 7           | Indicates if the object was hashed and moved. If this bit is set, the record includes the hash code.           |
    | 8           | Indicates if the object was hashed                                                                             |

- a `byte` or `word` containing the gap between the address of this object and the address of the preceding object (See the **Short object record**  description).
- a `byte` or `word` containing the array length.
- If all objects are hashed, a `short` containing the hash code. Otherwise, an optional `int` containing the hash code if the hashed and moved bit is set in the record flag byte.
- An unsigned `int` containing the size of the instance of the array on the heap, including header and padding. The size is measured in 32-bit words, which you can multiply by four to obtain the size in bytes. This format allows encoding of lengths up to 16GB in an unsigned `int`.

**3. Object array record**

The following format applies:

- The 1 byte tag with a  value of 8.

- A byte containing the following flags:

    | Bit number | Value or description                                                                                  |
    |------------|-------------------------------------------------------------------------------------------------------|
    | 1 and 2    | Indicates whether the gap is `byte`, `short`, `int` or `long`.                                        |
    | 3 and 4    | Indicates the size of each reference  (0=`byte`, 1=`short`, 2=`int`, 3=`long`)                        |
    | 5 and 6    | Unused                                                                                                |
    | 7          | Indicates if the object was hashed and moved. If this bit is set, the record includes the hash code.  |
    | 8          | Indicates if the object was hashed                                                                    |

- A `byte`, `short`, `int` or `long` containing the gap between the address of this object and the address of the preceding object (See the **Short object record** format description).
- A `word` containing the address of the class of the objects in the array. Object array records do not update the class cache.
- If all objects are hashed, a `short` containing the hash code. If the hashed and moved bit is set in the records flag, this field contains an `int`.
- An `int` containing the length of the array of references.
- The array of references (See the **Short object record** description).
- An unsigned `int` containing the size of the instance of the array on the heap, including header and padding. The size is measured in 32-bit words, which you can multiply by four to obtain the size in bytes. This format allows encoding of lengths up to 16GB in an unsigned `int`.
- An final `int` value is shown at the end. This `int` contains the true array length, shown as a number of array elements. The true array length might differ from the length of the array of references because null references are excluded.


#### Class records

The PHD class record encodes a class object and contains the following format:

- The 1 byte tag, containing the value 6.

- A byte containing the following flags:

    | Bit number | Value or description                                                                      |
    |------------|-------------------------------------------------------------------------------------------|
    | 1 and 2    | Indicates whether the gap is byte, `short`, `int` or `long`                               |
    | 3 and 4    | Indicates the size of each static reference  (0=`byte`, 1=`short`, 2=`int`, 3=`long`)     |
    | 5          | Indicates if the object was hashed                                                        |

- A byte, `short`, `int` or `long` containing the gap between the address of this class and the address of the preceding object (See the **Short object record** description).
- An `int` containing the instance size.
- If all objects are hashed, a `short` containing the hash code. Otherwise, an optional `int` containing the hash code if the hashed and moved bit is set in the record flag byte.
- A `word` containing the address of the superclass.
- A UTF string containing the name of this class.
- An `int` containing the number of static references.
- The array of static references (See the **Short object record** description).

### Classic Heap Dump format

Classic heap dumps are produced in ascii text on all platforms except z/OS, which are encoded in EBCDIC. The dump is divided into the following sections:

#### Header record

A single string containing information about the runtime environment, platform, and build levels, similar to the following example:

```
// Version: JRE 1.8.0 Linux amd64-64 (build 1.8.0_232-b09)
```

#### Object records

A record of each object instance in the heap with the following format:

```
<object address, in hexadecimal> [<length in bytes of object instance, in decimal>] OBJ <object type>
<heap reference, in hexadecimal> <heap reference, in hexadecimal> ...
```

The following object types (`object type`) might be shown:

- class name (including package name)
- class array type
- primitive array type

These types are abbreviated in the record. To determine the type, see the [Java VM Type Signature table](#java-vm-type-signatures).

Any references found are also listed, excluding references to an object's class or NULL references.

The following example shows an object instance (16 bytes in length) of type `java/lang/String`, with a reference to a char array:

```
0x00000000E0000AF0 [16] OBJ java/lang/String
	0x00000000E0000B00
```

The object instance (length 32 bytes) of type char array, as referenced from the `java/lang/String`, is shown in the following example:

```
0x00000000E0000B00 [32] OBJ [C
```

The following example shows an object instance (24 bytes in length) of type array of `java/lang/String`:

```
0x00000000FFF07498 [24] OBJ [Ljava/lang/String;
	0x00000000E0005D78 0x00000000E0005D50 0x00000000E0005D28 0x00000000E0005D00
```

#### Class records

A record of each class in the following format:

```
<class object address, in hexadecimal> [<length in bytes of class object, in decimal>] CLS <class type>
<heap reference, in hexadecimal> <heap reference, in hexadecimal>...
```

The following class types (`<class type>`) might be shown:

- class name (including package name)
- class array type
- primitive array types

These types are abbreviated in the record. To determine the type, see the [Java VM Type Signature table](#java-vm-type-signatures).

Any references found in the class block are also listed, excluding NULL references.

The following example shows a class object (80 bytes in length) for `java/util/Date`, with heap references:

```
0x00000000E00174F0 [80] CLS java/util/Date
	0x00000000FFF1BB60 0x00000000FFF29630
```

#### Trailer record 1

A single record containing record counts, in decimal.

For example:

```
// Breakdown - Classes: 630, Objects: 3692, ObjectArrays: 576, PrimitiveArrays: 2249
```

#### Trailer record 2

A single record containing totals, in decimal.

For example:

```
// EOF:  Total 'Objects',Refs(null) : 7147,22040(12379)
```

The values in the example reflect the following counts:

- `7147` total objects
- `22040` total references
- `(12379)` total NULL references as a proportion of the total references count

#### Java VM Type Signatures

The following table shows the abbreviations used for different Java types in the heap dump records:

| Java VM Type Signature      | Java Type                     |
|-----------------------------|-------------------------------|
| `Z`                         | `boolean`                     |
| `B`                         | `byte`                        |
| `C`                         | `char`                        |
| `S`                         | `short`                       |
| `I`                         | `int`                         |
| `J`                         | `long`                        |
| `F`                         | `float`                       |
| `D`                         | `double`                      |
| `L<fully-qualified class>;` | `<fully-qualified class>`     |
| `[<type>`                   | `<type>[](array of <type>)`   |
| `(<arg-types>)<ret-type>`   | `method`                      |




## See also

- [DTFJ interface](interface_dtfj.md)

<!-- ==== END OF TOPIC ==== dump_heapdump.md ==== -->