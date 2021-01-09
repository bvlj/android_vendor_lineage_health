# Usage

## Android ContentResolver

App developers might access the HealthStore through the standard Android's
[ContentResolver](https://developer.android.com/guide/topics/providers/content-provider-basics)
without requiring the addition of any new external dependency. Please take a look
at the [sdk code](/sdk/src/main/java/org/lineageos/mod/health/sdk/) inside this repository to
see how to properly use the raw ContentProvider.
This is **not** recommended for most use cases, unless you have a reason not to, please use the
[sdk library](#sdk-library).

## SDK library

The official SDK library reduces the amount of boilerplate required for communicating
with the underlying ContentProvider and provides easy-to-use interfaces.
The SDK is written in Java but works with Kotlin projects as well.

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

## JavaDoc

The JavaDoc is available [here](https://healthstore.github.io/android_vendor_lineage_health/javadocs/index.html).

