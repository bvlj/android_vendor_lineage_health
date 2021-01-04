# Metrics reference

## Activity

| Metric id | Type | Description | Values |
| ------ | ------------- | ---------------- | ----------------------------------------------------------------- |
| `0001` | CyclingRecord | Cycling          | distance [km], elevationGain [m], avgSpeed [km/h], time, duration |
| `0002` | RunningRecord | Running          | distance [km], avgSpeed [km/h], time, duration                    |
| `0003` | WalkingRecord | Steps counter    | distance [km], steps [int], time, duration                        |
| `0004` | WorkoutRecord | Workout activity | calories [cal], notes, time range                                 |

## Body

| Metric id | Type | Description | Values |
| ------ | ---------------------------- | -------------------------- | ---------------------- |
| `1001` | AbdominalCircumferenceRecord | Abdominal circumference    | value [cm], time       |
| `1002` | BodyMassIndexRecord          | BMI                        | value [kg/(m^2)], time |
| `1003` | BodyTemperatureRecord        | Body temperature           | value [ºC], time       |
| `1004` | LeanBodyMassRecord           | Lean body mass             | value [float], time    |
| `1005` | MenstrualCycleRecord         | Menstrual cycle flow       | sexualActivity, physicalSymptoms, otherSymptoms, value [flow], time |
| `1006` | UvIndexRecord                | Uv index                   | value [float], time    |
| `1007` | WaterIntakeRecord            | Intake of a glass of water | value [glasses], time  |
| `1008` | WeightRecord                 | Mass (aka Weight)          | value [kg], time       |

## Breathing

| Metric id | Type | Description | Values |
| ------ | ------------------------ | ---------------- | ------------------ |
| `2001` | InhalerUsageRecord       | Inhaler usage    | notes, time        |
| `2002` | OxygenSaturationRecord   | Inhaler usage    | value [%], time    |
| `2003` | PeakExpiratoryFlowRecord | Inhaler usage    | value [PEF], time  |
| `2004` | RespiratoryRateRecord    | Respiratory rate | value [bpm], time  |
| `2005` | VitalCapacityRecord      | Vital capacity   | value [cm^3], time |

## Heart & blood

| Metric id | Type | Description | Values |
| ------ | ------------------------------- | ----------------------- | --------------------------------------- |
| `3001` | BloodAlcoholConcentrationRecord | Blood / Alcohol content | value [%], time                         |
| `3002` | BloodPressureRecord             | Blood pressure          | systolic [mmHg], diastolic [mmHg], time |
| `3003` | GlucoseRecord                   | Blood glucose           | value [mg/dL], before meal [bool], time |
| `3004` | HeartRateRecord                 | Heart rate              | value [bpm], time                       |
| `3005` | PerfusionIndexRecord            | Perfusion index         | value [%], time |

## Mindfulness

| Metric id | Type | Description | Values |
| ------ | ---------------- | ------------ | -------------------------------- |
| `4001` | MeditationRecord | Meditation   | time, duration                   |
| `4002` | MoodRecord       | Mood journal | moodLevel, notes, time, duration |
| `4003` | SleepRecord      | Sleep        | time, duration                   |
