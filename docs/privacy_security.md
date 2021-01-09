# Privacy & Security

The Health Mod never shares your data to any remote server directly, so your data
never leaves your device unless it's being pulled by a 3rd party system that will
host it on an external platform.

Users have great controls over how their health data flows across different services,
while the Health Mod takes steps to ensure that local data remains protected.

HealthStore takes advantages of existing robust standard security mechanisms of
Android (such as runtime permissions and FBE) to safeguard user's data.

It can offer even more protection thanks to its custom set of [SEPolicies](#sepolicies)
that can be enabled when building the Health Mod inside the system.

At the low level, the Health Mod consists of a number of
[ContentProviders](https://developer.android.com/guide/topics/providers/content-providers)
that have their data stored in a secure location. Each `ContentProvider` is guarded by its own
[runtime permission](https://developer.android.com/distribute/best-practices/develop/runtime-permissions).

OEMs have access to an additional `ContentProvider` that can deny read and/or write access policies
for each particular metric to every single app. This should be used by OEMs to enforce custom policies
or to allow the user to have complete control over the data flow (as in the Lineage Health app).
Moreover OEMs may provide a "partner customization" app in their system builds to customize some
parts of the Health Mod. See the partner documentation for more information.

## Permissions

To access any Record belonging to a specific metric group, the acting app must
request an [Android runtime permission](https://developer.android.com/distribute/best-practices/develop/runtime-permissions)
so that the user may approve access to a specific set of data.

The following runtime permissions are used:

- `lineageos.permission.HEALTH_ACCESS`
    - Can be accessed by: `system`, `signature`
    - Usage: Implement policies that define granular per-app access to each metric regardless of other permissions
- `lineageos.permission.HEALTH_ACTIVITY`
    - Can be accessed by: runtime (`dangerous`)
    - Usage: Access to fitness-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_BODY`
    - Can be accessed by: runtime (`dangerous`)
    - Usage: Access to body-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_BREATHING`
    - Can be accessed by: runtime (`dangerous`)
    - Usage: Access to breathing-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_HEART_BLOOD`
    - Can be accessed by: runtime (`dangerous`)
    - Usage: Access to heart and blood-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_MINDFULNESS`
    - Can be accessed by: runtime (`dangerous`)
    - Usage: Access to mindfulness-related metrics. May be overridden by system policies
- `lineageos.permission.HEALTH_MEDICAL_PROFILE`
    - Can be accessed by: runtime (`dangerous`)
    - Usage: Access to medical profile data. Cannot be overridden by system policies

## SEPolicies

Additional SEPolicies are available for OEMs who whish to implement. HealthStore in their
builds. The source code repository provides example implementation of SEPolicies to
increase the security of stored user data by restricting access to the Mod app data.
It is recommend to make use of the additional rules to make users' data safer when building
the Health Mod inside the system.
