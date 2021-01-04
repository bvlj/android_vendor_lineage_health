HealthStore
===========

> Copy of the original development product document written by @bvlj and @vazguard

# Intro & goal

Our goal is to make HealthStore the best option for tracking and safeguarding your personal medical
information. While other apps in this space focus on fitness analysis and highlights, Lineage
will offer a wider view of stored data while keeping it protected under a strong API that gives
users ultimate power over its access.

- The current release plan is to launch HealthStore in both the current active branches
- Unless the project has verified medical backing, a warning should be presented that HealthStore
  is meant to help track data as a recreational tool and should not be used as a primary source
  during medical emergencies

# Who's it for?

1. **Health conscious** - those with active interests in maintaining a record of their own health,
   whether it be for fitness or due to a health condition
2. **Privacy nerds** - those who want to track even basic fitness but want clarity on how its
   shared around

# Why build it?

1. Health tracking is a part of the
   [growing](https://www.globenewswire.com/news-release/2019/01/24/1704860/0/en/Global-mHealth-Apps-Market-Will-Reach-USD-111-1-Billion-By-2025-Zion-Market-Research.html)
   [global](https://www.grandviewresearch.com/industry-analysis/mhealth-app-market)
   [market](https://liquid-state.com/mhealth-apps-market-snapshot/) for digital health solutions
2. Android's health data API's are not adequate - there are no standard or open source options
3. Google Fit is focused on displaying and processing high-level fitness data only
4. Advanced users care about control, and others in this space don't offer it directly
5. As an open source project, HealthStore will be built transparently and thus gains a unique element of trust and verification

# Survey

The survey goal is to understand the Lineage community's perspective on health and well-being, its
application in the mobile space, and unique needs members may have that aren't covered in currently
available options:
[reddit](https://www.reddit.com/r/LineageOS/comments/cvqhyz/lineageos_ux_research_survey/)
[twitter](https://twitter.com/LineageAndroid/status/1166062947078004737)

# What is it?

HealthStore is composed of two elements - the _HealthStore API_, and the
_Health app_ to manage it.

## API

The API controls two main groups of data: _Records_ and _MedicalProfile_.
Records are organized under various categories based on the type of data they hold.

## MedicalProfile

This group contains "static" information about the user/person.
Medical Profile has the following fields:

- Sex
- Blood type
- Weight
- Height
- (Is an) Organ donor
- Medications
- Allergies
- Conditions
- Additional notes

## Records

A record is the most generic data structure. Any data inside the store is a record.
Records are stored inside the database and can be indexed by _time_ and _metric_.
Records have the following fields:

## Categories

Metrics (see below) are organized into 5 categories: these categories are used to group
metrics of related data.

### Metrics

Metrics organize record data under a hierarchy.

HealthStore API updates can add new metrics as needed. Developers are encouraged to submit new
categories.

#### Activity

| Type          | Description      | Values                                                          |
| ------------- | ---------------- | --------------------------------------------------------------- |
| CyclingRecord | Cycling          | distance [km], elevation gain [m], avg speed [km/h], time range |
| RunningRecord | Running          | distance [km], avg speed [km/h], time range                     |
| WalkingRecord | Steps counter    | distance [km], steps [int], time range                          |
| WorkoutRecord | Workout activity | burned calories [cal], notes, time range                        |

#### Body

| Type                         | Description                | Values                 |
| ---------------------------- | -------------------------- | ---------------------- |
| AbdominalCircumferenceRecord | Abdominal circumference    | value [cm], time       |
| BodyMassIndexRecord          | BMI                        | value [kg/(m^2)], time |
| BodyTemperatureRecord        | Body temperature           | value [ºC], time       |
| LeanBodyMassRecord           | Lean body mass             | value [float], time    |
| MenstrualCycleRecord         | Menstrual cycle flow       | sexual activity, physical & other symptoms, value [flow], time |
| UvIndexRecord                | Uv index                   | value [float], time    |
| WaterIntakeRecord            | Intake of a glass of water | value [glasses], time  |
| WeightRecord                 | Mass (aka Weight)          | value [kg], time       |

#### Breathing

| Type                     | Description      | Values             |
| ------------------------ | ---------------- | ------------------ |
| InhalerUsageRecord       | Inhaler usage    | notes, time        |
| OxygenSaturationRecord   | Inhaler usage    | value [%], time    |
| PeakExpiratoryFlowRecord | Inhaler usage    | value [PEF], time  |
| RespiratoryRateRecord    | Respiratory rate | value [bpm], time  |
| VitalCapacityRecord      | Vital capacity   | value [cm^3], time |

#### Heart & blood

| Type                            | Description             | Values                                  |
| ------------------------------- | ----------------------- | --------------------------------------- |
| BloodAlcoholConcentrationRecord | Blood / Alcohol content | value [%], time                         |
| BloodPressureRecord             | Blood pressure          | systolic [mmHg], diastolic [mmHg], time |
| GlucoseRecord                   | Blood glucose           | value [mg/dL], before meal [bool], time |
| HeartRateRecord                 | Heart rate              | value [bpm], time                       |
| PerfusionIndexRecord            | Perfusion index         | value [%], time |

#### Mindfulness

| Type             | Description  | Values                  |
| ---------------- | ------------ | ----------------------- |
| MeditationRecord | Meditation   | time range              |
| MoodRecord       | Mood journal | mood level, notes, time |
| SleepRecord      | Sleep        | time range              |

## Compatibility

HealthMod uses standard Android features that are supposed to work across all GMS-certified devices
and all devices running official LineageOS builds (with the exception of custom SELinux context for
non-LineageOS devices except those that include such rules at build time).
HealthMod is also ensured to not break any compatibility with the Android CDD as it's tested on
GMS-certified production builds as well by an external trusted 3rd-party.
HealthMod is currently built with minSdk 27 (Android 8.1) and targetSdk 29 (Android 10, soon to
be upgraded to 30 / Android 11).
Theoretically it's possible to install the HealthMod on any device running Android 8.1+.

# Persona

## User 1

- Background
    - Is familiar with fitness applications
    - Has a wearable device
- Goals
    - Get motivation to keep fit (Nudges, stats…)
- Needs
    - A centralized place to store all the fitness data from different sources
    - Possibility to import data from the wearable

## User 2

- Background
    - Is privacy-conscious, doesn't trust proprietary apps to handle any sensitive information
    - Is interested in keeping track of his / her health conditions
- Goals
    - Store health data in a trustable environment for easy access
- Needs
    - Peace of mind through the control over data flow (granular access to each app, ability to
      delete data at will)
    - The service provider is transparent in how data is handled

## User 3

- Background
    - Data nerd
    - Loves to keep track of all his / her life
- Goals
    - Record, access and analyze all health data to gain insights
- Needs
    - Easy way to export data to a format that can be used for data analysis
    - On-device overview and stats

## User 4

- Background
    - Interested in mental health to improve performance at work
- Goals
    - Track and improve mental health
- Needs
    - A method to help stay motivated in doing meditation
    - A way to analyze sleep and mood variations across days and events

# Privacy

HealthStore takes cues from the Trust interface and Privacy Guard. Access to different categories
of data have their own separate Android [runtime permissions](https://developer.android.com/training/permissions/requesting).
Even if an app is granted access through standard permissions, apps can still be blacklisted
from selected metrics by users.
If an app tries to read data from a metric it has been blacklisted from, an empty data set will
be returned. Likewise, if an app tries to write to a blacklisted metric, the operation will fail.

A special permission, granted only to privileged / sys-signature apps, guards access to
modifying the blacklist.

A possible variation of this permission model is one that works in a similar way, but the read /
write concepts are separated in the blacklist rather than at android runtime permission level.
This allows us to provide more granular controls (eg. for each metric an user could choose
between "Read + Write", "Read", "Write", "Nothing"). On the other hand this model gives the app
requesting permission immediate read and write access until the user goes to the Health app and
toggles the access on its own. This means that more responsibility is put on the user's hands as
the operative system will be more permissive by default (unfortunately we can't redirect the user
to the blacklist ui upon permission grant as iOS does because of Android limitations we can't
remove).

## Permission combination

This table shows the various levels of segregation and protection of data. Each column
represents a specific point where anyone/thing trying to access may be blocked at any point by
the user.

| **The app**        | **The category**                       | **The action**          | **The metric**                                   |
| -----------------: | :------------------------------------- | ----------------------: | :----------------------------------------------- |
| **MyBike**         | wants to access all the **activities** | to **get** information  | on **cycling records**                           |
| **HappyHeart**     | wants to access **heart** related data | to **insert**           | a **pulse reading**                              |
| **BadStepCounter** | wants to access **mindfulness** data   | to **read** information | about the user **mood** for advertising purposes |
|  `Android runtime` | `permission`                           |                `Access` | `policies`                                       |

# Security

For storing data we took an approach similar to how other sensitive data such as contacts, call
logs and messages are stored, and applied extra protection to ensure data is safe.
All the security measures used here are built using existing battle-tested solutions such as
Android ContentProviders, Android Keystore, SELinux and SQLite encryption.
All the low level data access operations are performed by the HealthStore Mod, an app which hosts
content providers in which records and other data are saved. The content providers are a standard
way for android apps to expose their data to other processes in a safe way thanks to the support of
Android Runtime permissions.

The HealthStore Mod app can also be put in a special SELinux context with tight rules that prevent
other apps from accessing its data folder when SELinux is being enforced.
There exists multiple separate databases: the records database, the access-control database and
the medical profile database.

The records database and the access-control database are stored in the credential-encrypted area
of the userdata partition, available only when the device is unlocked. Moreover an additional layer
of encryption is provided by the usage of an encrypted database (SQLCipher) and the key required
to open that database is stored in an encrypted string which can be decrypted using another key
is stored inside the device's Android Keystore.

The medical profile database on the other hand is stored in the device-encrypted area of the
userdata partition, which means that data is accessible in "plain text" once the device is booted,
even before the user has authenticated through pin / password / pattern / biometrical unlock.
This is done to make sure that this data is accessible from the emergency information app, which
is to remain available even if the device is locked in case of emergency situations.

Since Lineage has no cloud systems, there are no backups or off-site storage beyond the locally
stored data.

If the device has outstanding security issues, such as permissive SELinux, notices will appear in
the health app warning users that their private data is at risk, along with ways to remedy the
issue.
