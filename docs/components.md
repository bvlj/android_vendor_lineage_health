# Components

The Lineage Health Platform is written in Java and Kotlin. It supports
Android devices providing SDK API level 27 or higher.

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

The module to be installed in the device. If included in a system build, it is recommend to
put it in the system (or product) partition but not in vendor.
Holds the ContentProviders that host the health data.

- Language: Kotlin
- Dependencies
    - [`:core`](#core)
        - [androidX/annotation](https://developer.android.com/jetpack/androidx/releases/annotation)
    - [kotlin/stdlib/jvm](https://github.com/JetBrains/kotlin/releases)
    - [sqlcipher](https://github.com/sqlcipher/android-database-sqlcipher)
        - [androidX/sqlite](https://developer.android.com/jetpack/androidx/releases/sqlite)

### :sdk

Provides simple APIs for the usage of the Mod `ContentProvider`s through data objects and repositories.

- Language: Java
- Dependencies
    - [`:core`](#core)
        - [androidX/annotation](https://developer.android.com/jetpack/androidx/releases/annotation)
