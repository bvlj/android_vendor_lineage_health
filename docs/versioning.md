# Versioning

The Health Mod and SDK both follow a parallel [semantic versioning](https://semver.org/) model.
This means that a Mod app (version `X.Y.Z`) installed on a device is guaranteed to support
any app that makes use of the SDK version `X.*.*`).

The Mod app might also support older SDKs by attempting to "convert" the given data internally, but
it is not guaranteed to work.

Adding making changes to the metrics (adding a new field or even a new metric) requires a major
version bump because there are strict checks in place to avoid invalid data being inserted into the
database.
