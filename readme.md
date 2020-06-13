Lineage Health Mod (HealthStore)
================================

## Contents

- [Info](#info)
- [Build](#build)
- [Usage](#usage)
- [Components](#components)
    - [core](#core)
    - [mod](#mod)
    - [sdk](#sdk)
        - [sdk-ktx](#sdk-ktx)

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

### SEPolicies & init

Additional SEPolicies and init files are required as well. These will be publicly available soon.

## Usage

App developers may access the HealthStore through the standard Android's 
[ContentResolver](https://developer.android.com/guide/topics/providers/content-provider-basics)
class without requiring the addition of any new external dependency.

However, it's also possible (and encouraged) the usage of the official [sdk](#sdk), 
a simple library that reduces the amount of boilerplate and provides the data using
Java objects and repositories.
The SDK works with both Java and Kotlin applications, and a [-ktx](#sdk-ktx) variant
is available for those who make use of Kotlin coroutines.

## Components

The Lineage Health Mod is written in Java and Kotlin. It supports
Android platforms providing SDK API level 27 or higher.

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

### :sdk

Provides simple APIs for the usage of the `ContentProvider`s through data objects and repositories.

- Language: Java
- Dependencies
    - [`:core`](#core)
        - [androidX/annotation](https://developer.android.com/jetpack/androidx/releases/annotation)


#### :sdk-ktx

Wraps provides extensions of repositories of the `:sdk` module that make use of Kotlin coroutines
using `suspend` functions.

- Language: Kotlin
- Dependencies
    - [`:sdk`](#sdk)
        - [`:core`](#core)
            - [androidX/annotation](https://developer.android.com/jetpack/androidx/releases/annotation)
    - [kotlin/stdlib/jvm](https://github.com/JetBrains/kotlin/releases)
    - [kotlinX/coroutines/jvm](https://github.com/Kotlin/kotlinx.coroutines/releases)
    - [kotlinX/coroutines/android](https://github.com/Kotlin/kotlinx.coroutines/releases)
