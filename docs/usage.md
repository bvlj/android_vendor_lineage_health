# Develop a new integration

## Android ContentResolver

App developers may access the HealthStore through the standard Android's
[ContentResolver](https://developer.android.com/guide/topics/providers/content-provider-basics)
without requiring the addition of any new external dependency. Please take a look
at the [sdk code](/sdk/src/main/java/org/lineageos/mod/health/sdk/) to see how to properly use
the raw ContentProvider.
This is **not** recommended for most use cases: no guarantee is made to the stability of the
ContentProvider interfaces (although they'll be kept as stable as possible).
The usage of the official [sdk library](#sdk-library) is encouraged.

## SDK library

The official SDK library reduces the amount of boilerplate required for communicating
with the underlying ContentProvider and provides a swift and simple way to join the
ecosystem. The SDK is written in Java but is expected to work with Kotlin projects as well.

### Adding to your project

You can find the SDK library on `jcenter`, so it can be easily imported in your project:

```groovy
dependencies {
     implementation "org.lineageos:mod.health-sdk:1.0.0-alpha01"
}
```

### Requesting access to a category

In order to interact with any kind of data, you need to your app granted the required
[runtime permission](https://developer.android.com/distribute/best-practices/develop/runtime-permissions).

Simple example for requesting access to the Body metrics group:

```java
// Check whether the app has the required permission
boolean hasBodyPermission = checkSelfPermission(HsRuntimePermission.BODY) ==
        PackageManager.PERMISSION_GRANTED;
if (!hasBodyPermission) {
    // Ask the user for the permission.
    // REQUEST_BODY_PERMISSION is an integer value that's going to be re-used in the
    // onRequestPermissionsResult() Activity method
    final String[] permissions = new String[] { HsRuntimePermission.BODY };
    requestPermissions(permissions, REQUEST_BODY_PERMISSION);
}
```

### Reading records

Reading operations can take some time, so it's important to not run them
on the main thread. If you're using kotlin coroutines, the `IO` dispatcher
should be used to wrap all the queries.

To access all the records of a specific metric, use the `getAll*Records()` method
of the appropriate category repository object (replace the `*` with the metric name).

To access a specific record (given its `id`), use the `get*Record(id)` method
of the appropriate category repository object (replace the `*` with the metric name).

Simple example for reading all walking records in the past week:

```java
// Obtain the repository
ActivityRecordsRepo repo = ActivityRecordsRepo.getInstance(getContentResolver());
// Define a filter interval
long now = System.currentTimeMillis();
long oneWeekAgo = now - (1000L * 60L * 60L * 24L * 7L);
// Get records from the repository and filter them
List<WalkingRecord> walkingRecordsLastWeek = repo.getAllWalkingRecords()
                .stream()
                .filter(it -> it.getTime() > oneWeekAgo && it.getTime() < now)
                .collect(Collectors.toList());
```

### Inserting, updating and deleting records

Insertion and updating operations can take some time, so it's important to not
run them on the main thread. If you're using kotlin coroutines, the `IO`
dispatcher should be used to wrap all the queries.

- To insert a new record object, simply use the `insert(record)` method of the repository object.
- To update a new record object, simply use the `update(record)` method of the repository object.
- To delete a new record object, simply use the `delete(record)` method of the repository object.

Each of these operations will return an `OperationResult` object. This object is an instance
of exactly one of the following:

- `OperationResult.Success<T>` where `<T>` is 
    - `Long` for the `insert` method. The `.result` field will hold the newly inserted record id 
    - `Integer` for the `update` method. The `.result` field will hold the number of updated records
    - `Integer` for the `delete` method. The `.result` field will hold the number of delete records
- `OperationResult.PolicyError` if a(n user-defined) policy prevented this app from performing
   such action
- `OperationResult.Failure` if an error occurred while performing the operation. Make sure your
   data is valid (e.g. negative mass values) and check the logs for further details.

Simple example for inserting a new running activity record with the following values:
`{Time: just ended, Lasted: 30 minutes (1800000 ms), Distance: 3.21km, Average speed: 6.42 km/h}`:

```java
RunningRecord record = new RunningRecord(0L,
        System.currentTimeMillis(), 
        1800000L,
        SpeedValue.kilometersPerHour(6.42),
        LengthValue.kilometers(3.21));
OperationResult insertionResult = repo.insert(record);
if (insertionResult instanceof OperationResult.Success) {
    // Insertion completed successfully
} else if (insertionResult instanceof OperationResult.PolicyError) {
    // A(n user-defined) policy prevented this application from performing this action
} else if (updateResult instanceof OperationResult.Failure) {
    // Operation failed due to some error. Check data validity
}
```

### Batch operations

It is possible (and recommended) to perform multiple operations in batch where possible by
using the repository object's `.executeBatch(builder)` method.

See this example that imports glucose readings from a source, converts them to `GlucoseRecord`
objects and inserts them in batch:

```java
HeartBloodRecordsRepo repo = HeartBloodRecordsRepo.getInstance(getContentResolver());
OperationResult[] results = repo.executeBatch(composer -> {
    otherDataSource.getNewGlucoseReadings().forEach(item -> {
        // convertToRecord implementation made by the developer
        GlucoseRecord record = convertToRecord(item);
        // Compose batch operations with many .insert() for each item from the
        // the other data source
        composer.insert(record);
    });
});
// Check the results now...
```

### JavaDoc

The JavaDoc is available
[here](https://healthstore.github.io/android_vendor_lineage_health/javadocs/index.html).

