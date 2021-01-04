Lineage Health Mod (HealthStore)
================================

## Contents

- [Info](#info)
- [Build](#build)
- [Usage](#usage)
- [Testing](#testing)
- [Documentation](#documentation)
- [Versioning](#versioning)
- [License](#license)

## Info

The Lineage Health Mod (also called "HealthStore" or "Health Mod") allows applications to
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

While this is a LineageOS' project, it is designed to be used on
any Android distribution that provides the standard Android SDK APIs at level
27 or higher (Android 8.1) as well.

HealthStore takes advantages of existing robust standard security mechanisms of
Android (such as runtime permissions and FBE) to safeguard user's data.

It can offer even more protection thanks to its custom set of [SEPolicies](#sepolicies)
that can be enabled when building the Health Mod inside the system.

At the low level, the Lineage Health Mod consists of a number of
[ContentProviders](https://developer.android.com/guide/topics/providers/content-providers)
that have their data stored in a secure location. Each `ContentProvider` is guarded by its own
[runtime permission](https://developer.android.com/distribute/best-practices/develop/runtime-permissions).

OEMs have access to an additional `ContentProvider` that can deny read and/or write access policies
for each particular metric to every single app. This should be used by OEMs to enforce custom policies
or to allow the user to have complete control over the data flow (as in the Lineage Health app).
Moreover OEMs may provide a "partner customization" app in their system builds to customize some
parts of the Health Mod. See [the documentation for more information](/docs/partner.md).

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
    - Usage: Access to medical profile data. Cannot be overridden by system policies

## Build

It is possible to build the Health Mod app both from Android Studio / gradle and from
an Android (AOSP) build environment (for Android 10 / API 29 and higher).

### Gradle

You can compile with gradle by running `./gradlew :mod:assemble`

### Android 10(+) build environment

It is possible to simply include this repo in your build environment at `vendor/lineage/health`
and make sure the target package `HealthStoreMod` is included in your build.

### SEPolicies

Additional SEPolicies are available. This repository provides example implementation
of SEPolicies to increase the security of stored user data by restricting access to
the Mod app data. It is recommend to make use of the additional rules to make users' data
safer when building the Health Mod inside the system.

* [Example implementation for API 29 (Android 10)](/selinux/api29/)

## Usage

### Android ContentResolver

App developers might access the HealthStore through the standard Android's
[ContentResolver](https://developer.android.com/guide/topics/providers/content-provider-basics)
without requiring the addition of any new external dependency. Please take a look
at the [sdk code](/sdk/src/main/java/org/lineageos/mod/health/sdk/) inside this repository to
see how to properly use the raw ContentProvider.
This is **not** recommended for most use cases, unless you have a reason not to, please use the
[sdk library](#sdk-library).

### SDK library

The official [SDK](#sdk) library reduces the amount of boilerplate required for communicating
with the underlying ContentProvider and provides easy-to-use interfaces.
The SDK is written in Java but works with Kotlin projects as well.

The JavaDoc is available on [GitHub](https://healthstore.github.io/android_vendor_lineage_health).

You can find the SDK on bintray to easily import it in your maven / gradle project:

```groovy
repositories {
    maven {
        url "https://dl.bintray.com/lineageos/org.lineageos"
    }
}

dependencies {
     implementation "org.lineageos:mod.health-sdk:1.0.0-alpha00"
}
```

## Testing

Tests are implemented in the [`:mod`](#mod) module. Both end-to-end (sdk-to-db) tests
and unit tests should be put here in order to facilitate debugging of the [Mod component](#mod).
Tests found here could be also used to verify whether the current implementation of the Health
Mod installed on the device adheres to the _official_ implementation details.

You can execute the tests by running:

```
./gradlew :mod:connectedCheck
```

## Documentation

Additional documentation is provided within this repository

- [Copy of the original product document](/docs/product_document.md)
- [Access control information](/docs/access_control.md)
- [Components](/docs/components.md)
- [Custom data types reference values](/docs/custom_data_types.md)
- [Record metric specifications](/docs/metrics.md)
- [Partner customizations](/docs/partner.md)

## Versioning

The Health Mod and SDK both follow a parallel [semantic versioning](https://semver.org/) model.
This means that a Mod app (version `X.Y.Z`) installed on a device is guaranteed to support
any app that makes use of the SDK version `X.*.*`).

The Mod app might also support older SDKs by attempting to "convert" the given data internally, but
it is not guaranteed to work.

Adding making changes to the metrics (adding a new field or even a new metric) requires a major
version bump because there are strict checks in place to avoid invalid data being inserted into the
database.

## License

```
Copyright (C) 2020-2021 The LineageOS Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
