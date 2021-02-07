# Build

It is possible to build the CareCache app both from Android Studio / gradle and from
an Android (AOSP) build environment (for Android 10 / API 29 and higher).

## Gradle

You can compile with gradle by running `./gradlew :mod:assemble`

## Android 10(+) build environment

It is possible to simply include the source repo in your build environment at `vendor/lineage/health`
and make sure the target package `CareCache` is included in your build.

## Testing

Tests are implemented in the `:mod` module. Both end-to-end (sdk-to-db) tests
and unit tests should be put here in order to facilitate debugging of the module component.
Tests found here could be also used to verify whether the current implementation of the Health
Mod installed on the device adheres to the _official_ implementation details.

You can execute the tests by running:

```
./gradlew :mod:connectedCheck
```
