# Data entities

## Records

A Record is a data entity that represents an health-related information.
Records are divided in metrics in order to be grouped with similar data entities.

Metrics are grouped in four macro-groups:

### Activity

Fitness-related metrics

| Metric id | Type | Description | Values |
| ------ | ------------- | ---------------- | ----------------------------------------------------------------------- |
| `0001` | CyclingRecord | Cycling          | distance [`km`], elevationGain [`m`], avgSpeed [`km/h`], time, duration |
| `0002` | RunningRecord | Running          | distance [`km`], avgSpeed [`km/h`], time, duration                      |
| `0003` | WalkingRecord | Steps counter    | distance [`km`], steps, time, duration                                  |
| `0004` | WorkoutRecord | Workout activity | calories [`cal`], notes, time range                                     |

### Body

Body-related metrics

| Metric id | Type | Description | Values |
| ------ | ---------------------------- | -------------------------- | ------------------------ |
| `1001` | AbdominalCircumferenceRecord | Abdominal circumference    | value [`cm`], time       |
| `1002` | BodyMassIndexRecord          | BMI                        | value [`kg/(m^2)`], time |
| `1003` | BodyTemperatureRecord        | Body temperature           | value [`ºC`], time       |
| `1004` | LeanBodyMassRecord           | Lean body mass             | value, time              |
| `1005` | MenstrualCycleRecord         | Menstrual cycle flow       | sexualActivity, physicalSymptoms, otherSymptoms, value (flow), time |
| `1006` | UvIndexRecord                | Uv index                   | value, time              |
| `1007` | WaterIntakeRecord            | Intake of a glass of water | value (glasses), time    |
| `1008` | WeightRecord                 | Mass (aka Weight)          | value [`kg`], time       |

### Breathing

Breathing-related metrics

| Metric id | Type | Description | Values |
| ------ | ------------------------ | ---------------- | -------------------- |
| `2001` | InhalerUsageRecord       | Inhaler usage    | notes, time          |
| `2002` | OxygenSaturationRecord   | Inhaler usage    | value [`%`], time    |
| `2003` | PeakExpiratoryFlowRecord | Inhaler usage    | value [`PEF`], time  |
| `2004` | RespiratoryRateRecord    | Respiratory rate | value [`bpm`], time  |
| `2005` | VitalCapacityRecord      | Vital capacity   | value [`cm^3`], time |

### Heart & blood

Heart and blood metrics

| Metric id | Type | Description | Values |
| ------ | ------------------------------- | ----------------------- | ------------------------------------------- |
| `3001` | BloodAlcoholConcentrationRecord | Blood / Alcohol content | value [`%`], time                           |
| `3002` | BloodPressureRecord             | Blood pressure          | systolic [`mmHg`], diastolic [`mmHg`], time |
| `3003` | GlucoseRecord                   | Blood glucose           | value [`mg/dL`], is before meal, time       |
| `3004` | HeartRateRecord                 | Heart rate              | value [`bpm`], time                         |
| `3005` | PerfusionIndexRecord            | Perfusion index         | value [`%`], time                           |

### Mindfulness

Mindfulness and well-being metrics

| Metric id | Type | Description | Values |
| ------ | ---------------- | ------------ | -------------------------------- |
| `4001` | MeditationRecord | Meditation   | time, duration                   |
| `4002` | MoodRecord       | Mood journal | moodLevel, notes, time, duration |
| `4003` | SleepRecord      | Sleep        | time, duration                   |

## Medical profile

A medical profile consists of a set of fixed basic medical information
about the user.

This data will not have the same level of protection like the Records in order
to allow access from when the screen is locked in case of emergency.

| Name                | Description                                             |
| ------------------- | ------------------------------------------------------- |
| Allergies           | A note about user's allergies                           |
| Biological sex      | User's biological sex                                   |
| Blood type          | User's blood type                                       |
| Height              | Height of the user [`cm`]                               |
| (Is an) organ donor | Whether the user is an organ donor                      |
| Medications         | A note about user's medications                         |
| Notes               | Any additional information the user may want to provide |

## Reference values

Records and Medical profiles have some fields that accept only a fixed set of
possible values.

### Biological sex

| Name    | Value |
| ------- | ----- |
| Unknown | `0`   |
| Female  | `1`   |
| Male    | `2`   |

### BloodType

| Name    | Value |
| ------- | ----- |
| Unknown | `0`   |
| 0-      | `1`   |
| 0+      | `2`   |
| A-      | `3`   |
| A+      | `4`   |
| B-      | `5`   |
| B+      | `6`   |
| AB-     | `7`   |
| AB+     | `8`   |
| HH      | `9`   |

### Meal relation

| Name    | Value |
| ------- | ----- |
| Unknown | `0`   |
| Before  | `1`   |
| After   | `2`   |

### Menstrual Cycle

#### Physical symptoms

| Name               | Value         |
| ------------------ | ------------- |
| None               | `0b000000000` |
| Acne               | `0b000000001` |
| Bloating           | `0b000000010` |
| Cramps             | `0b000000100` |
| Constipation       | `0b000001000` |
| Fatigue            | `0b000010000` |
| Headache           | `0b000100000` |
| Joint muscle pain  | `0b001000000` |
| Spotting           | `0b010000000` |
| Tender breasts     | `0b100000000` |

#### Other symptoms

| Name               | Value         |
| ------------------ | ------------- |
| None               | `0b00000000`  |
| Anxiety            | `0b00000001`  |
| Crying spells      | `0b00000010`  |
| Depression         | `0b00000100`  |
| High sex drive     | `0b00001000`  |
| Insomnia           | `0b00010000`  |
| Mood swings        | `0b00100000`  |
| Poor concentration | `0b01000000`  |
| Social withdrawal  | `0b10000000`  |

#### Sexual activity

| Name          | Value    |
| ------------- | -------- |
| None          | `0b0000` |
| Masturbation  | `0b0001` |
| No sex        | `0b0010` |
| Protected sex | `0b0100` |
| Sex           | `0b1000` |

### Mood level

| Name      | Value           |
| --------- | --------------- |
| Unknown   | `0b00000000000` |
| Amazing   | `0b00000000001` |
| Happy     | `0b00000000010` |
| Excited   | `0b00000000100` |
| Stressed  | `0b00000001000` |
| Focused   | `0b00000010000` |
| Tired     | `0b00000100000` |
| Sad       | `0b00001000000` |
| Sick      | `0b00010000000` |
| Exhausted | `0b00100000000` |
| Nervous   | `0b01000000000` |
| Angry     | `0b10000000000` |

### Organ donor

| Name    | Value |
| ------- | ----- |
| Unknown | `0`   |
| Yes     | `1`   |
| No      | `2`   |

### Access policies permissions

| Name  | Value  |
| ----- | ------ |
| None  | `0b00` |
| Read  | `0b01` |
| Write | `0b10` |
| All   | `0b11` |
