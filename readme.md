Lineage Health Mod (HealthStore)
================================

## Contents

- [Info](#info)
- [Build](#build)
- [Usage](#usage)
- [Testing](#testing)
- [Components](#components)
    - [core](#core)
    - [mod](#mod)
    - [sdk](#sdk)

## Info

The Lineage Health Mod (also called "HealthStore") allows applications to
store and share health-related data in a secure environment that never leaves
your device and gives the user strong control over the data flow.

HealthStore can store multiple data entities:

- `Record`
    - A record is a data entity that represents an health-related information.
    - Records are divided in metrics in order to be grouped with similar data entities
    - Metrics are grouped in four macro-groups:
        - **Activity**: fitness-related metrics
        - **Body**: body-related metrics
        - **Breathing**: breathing-related metrics
        - **Heart & blood**: heart and blood metrics
        - **Mindfulness**: mindfulness and well-being metrics
- `MedicalProfile`
    - A medical profile consists of a set of "constant" basic medical information about the user
    - This data will not have the same level of protection like the Records in order to allow access from when the screen is locked in case of emergency

While this is a LineageOS' project, it is designed to be easily portable to
different Android distribution that provide the standard Android SDK APIs at level
27 or higher (Android 8.1).

HealthStore takes advantages of existing robust standard security mechanisms of
Android (such as runtime permissions, FBE and SELinux) to safeguard user's data.

The Lineage Health Mod consists of a group of
[ContentProviders](https://developer.android.com/guide/topics/providers/content-providers)
that have their data stored in a secure system location. Each `ContentProvider` is guarded by its
[runtime permission](https://developer.android.com/distribute/best-practices/develop/runtime-permissions).

OEMs have access to an additional `ContentProvider` that can deny read and/or write access
for each particular metric to every single app. This should be used for "OEM health manager
applications" in order to allow the user to have complete control over the data flow.

### Permissions

The following Android permissions are used by the Lineage Health platform

- `lineageos.permission.HEALTH_ACCESS`
    - Access: system, signature
    - Usage: Implement policies that define granular per-app access to each metric regardless of other permissions
- `lineageos.permission.HEALTH_ACTIVITY`
    - Access: runtime (dangerous)
    - Usage: Access to fitness-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_BODY`
    - Access: runtime (dangerous)
    - Usage: Access to body-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_BREATHING`
    - Access: runtime (dangerous)
    - Usage: Access to breathing-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_HEART_BLOOD`
    - Access: runtime (dangerous)
    - Usage: Access to heart and blood-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_MINDFULNESS`
    - Access: runtime (dangerous)
    - Usage: Access to mindfulness-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_MEDICAL_PROFILE`
    - Access: runtime (dangerous)
    - Usage: Access to medical profile data.

## Build

In order to add support for the Lineage Health Mod and the apps that make use of
this feature a couple of simple steps are required:

### Android 10 and higher

If you happen to be working on a platform that targets Android 10 or higher (API 29)
it is possible to just include this repo in your build environment and make sure the
package `HealthStoreMod` is included in your build.

### Android 9 (P) and lower

Due to the lack of proper kotlin support in the Android build environment it may not
be possible to successfully compile the `HealthStoreMod` target. However, it's possible
to generate a prebuilt apk using `./gradlew :mod:assemble`.

### SEPolicies

Additional SEPolicies are available. This repository provides example implementation
of SEPolicies to increase the security of stored user data by restricting access to
the Mod app data:

* [Example for api 29 (Android 10)](/selinux/api29/)

## Usage

App developers may access the HealthStore through the standard Android's
[ContentResolver](https://developer.android.com/guide/topics/providers/content-provider-basics)
class without requiring the addition of any new external dependency.

However, it's also possible (and encouraged) the usage of the official [sdk](#sdk),
a simple library that reduces the amount of boilerplate and provides the data using
Java objects and repositories.
The SDK works with both Java and Kotlin applications, and a [-ktx](#sdk-ktx) variant
is available for those who make use of Kotlin coroutines.

## Testing

Tests are implemented in the [`:mod`](#mod) module. Both end-to-end (sdk-to-db) tests
and unit tests should be put here in order to facilitate debugging of the [Mod component](#mod).
Tests found here can be also used to verify whether the current implementation of the Health
Mod installed on the device adheres to the _official_ implementation details.

You can execute the tests by running:

```
./gradlew :mod:connectedCheck
```

## Components

The Lineage Health Mod is written in Java and Kotlin. It supports
Android platforms providing SDK API level 27 or higher.

The following architectures are supported by the Lineage Health Mod:
`armeabi-v7a`, `arm64-v8a`, `x86`, `x86_64`. Other architectures are not
supported due to the usage of the
[sqlcipher library](https://github.com/sqlcipher/android-database-sqlcipher).

### :core

Code shared across the [`:mod`](#mod) and [`:sdk`](#sdk) modules.

- Language: Java
- Dependencies
    - [androidX/annotation](https://developer.android.com/jetpack/androidx/releases/annotation)

### :mod

The module to be installed in the system (or product) partition of the device.
Holds the ContentProviders that host the health data.

- Language: Kotlin
- Dependencies
    - [`:core`](#core)
        - [androidX/annotation](https://developer.android.com/jetpack/androidx/releases/annotation)
    - [kotlin/stdlib/jvm](https://github.com/JetBrains/kotlin/releases)
    - [androidX/sqlite](https://developer.android.com/jetpack/androidx/releases/sqlite)
    - [sqlcipher](https://github.com/sqlcipher/android-database-sqlcipher)

### :sdk

Provides simple APIs for the usage of the `ContentProvider`s through data objects and repositories.

- Language: Java
- Dependencies
    - [`:core`](#core)
        - [androidX/annotation](https://developer.android.com/jetpack/androidx/releases/annotation)
