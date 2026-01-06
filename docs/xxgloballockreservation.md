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

# -XX:\[+|-\]GlobalLockReservation

**(AIX&reg; and Linux on Power Systems&trade; only)**

The `-XX:+GlobalLockReservation` option enables an optimization targeted towards more efficient handling of locking and unlocking Java&trade; objects. The `-XX:-GlobalLockReservation` option is used to disable this optimization. The optimization is enabled by default.

## Syntax

        -XX:[+|-]GlobalLockReservation
        -XX:+GlobalLockReservation:<parameter>

| Setting                    | Effect | Default                                                                              |
|----------------------------|--------|:------------------------------------------------------------------------------------:|
|`-XX:+GlobalLockReservation`| Enable | :fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span> |
|`-XX:-GlobalLockReservation`| Disable|                                                                                      |

This optimization is targeted towards applications with lots of uncontended locked objects that are being locked just to be safe. When enabled, heuristics are used to try and determine when an object will be exclusively locked by a single thread so that faster, more specialized code can be used for locking the object. If the heuristics incorrectly identify an object as a target for the optimization, performance might be adversely affected.

The `-XX:-GlobalLockReservation` option turns off global lock reservation.

The `-XX:+GlobalLockReservation` option can be used to enable global lock reservation if it was disabled by an option that occurs earlier in command line processing or to modify some of the global lock reservation related suboptions that are described later in this document.

## Parameters

Advanced tuning parameters are shown in the following table:

| Parameter                                                     | Effect                                                               |
|---------------------------------------------------------------|----------------------------------------------------------------------|
| [`reservedTransitionThreshold`](#reservedtransitionthreshold) | Changes amount of time spent analyzing an object.                    |
| [`reservedAbsoluteThreshold`  ](#reservedabsolutethreshold  ) | Changes amount of time spent analyzing a class for compatibility.    |
| [`minimumReservedRatio`       ](#minimumreservedratio       ) | Changes aggression level for marking a class as highly compatible.   |
| [`cancelAbsoluteThreshold`    ](#cancelabsolutethreshold    ) | Changes amount of time spent analyzing a class for incompatibility.  |
| [`minimumLearningRatio`       ](#minimumlearningratio       ) | Changes aggression level for marking a class as highly incompatible. |


### reservedTransitionThreshold
        -XX:+GlobalLockReservation:reservedTransitionThreshold=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | number         | 1                     |

: Number of times an object is locked by the same thread before it is considered reserved minus a value of 2. So, with a default value of 1, an object can be reserved the third time it is locked. `<value>` can be 0-3 inclusive. Values of 4 or higher are treated as infinity.

### reservedAbsoluteThreshold
        -XX:+GlobalLockReservation:reservedAbsoluteThreshold=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | number         | 10                    |

: Minimum number of objects of a class that get reserved before the class can be considered highly compatible. Objects of that class are reserved the first time they are locked. Values of 65536 or higher are treated as infinity.

### minimumReservedRatio
        -XX:+GlobalLockReservation:minimumReservedRatio=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | number         | 1024                  |

: Minimum ratio of reserved objects to flat objects before a class can be considered highly compatible. Values of 65536 or higher are treated as infinity.

### cancelAbsoluteThreshold
        -XX:+GlobalLockReservation:cancelAbsoluteThreshold=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | number         | 10                    |

: Minimum number of objects of a class that get converted to flat before the class can be considered highly incompatible. Objects of that class are never reserved. Values of 65536 or higher are treated as infinity.

### minimumLearningRatio
        -XX:+GlobalLockReservation:minimumLearningRatio=<value>

: | Setting       | Value          | Default               |
  |---------------|----------------|-----------------------|
  | `<value>`     | number         | 256                   |

: Minimum ratio of reserved objects to flat objects to prevent class from being considered highly incompatible. Values of 65536 or higher are treated as infinity.

## See also

- [What's new in version 0.20.0](version0.20.md#new-xx-globallockreservation-option-added)
- [What's new in version 0.27.0](version0.27.md#global-lock-reservation-enabled-by-default)

<!-- ==== END OF TOPIC ==== xxgloballockreservation.md ==== -->
