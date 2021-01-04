Lineage Health Mod partner customization
========================================

It is possible for OEMs to provide a package (which must be put in a system partition)
for it to be picked up by the Health Mod to configure a number of customizations.

## Default Access Policy

It is possible to provide a default configuration to the AccessContentProvider to allow
OEMs to set some policies as soon as the ContentProvider is initialized. It will still be
possible to override those policies later with the usual set of APIs.

The import of the default policies happens only once when the partner customization app is
first found by the mod. No later changes are allowed by design, to avoid disrupting user's
configuration.

### Config Structure

The default access policy file must be located in `res/xml/access_policy.xml` to be parsed.

Reference implementation:

```xml
<policies version="1">
    <package name="com.example.a">
        <policy
            metric="1001"
            permissions="1" />
        <policy
            metric="1002"
            permissions="0" />
        <policy metric="1003" />
        <policy metric="1004" />
        <policy metric="1005" />
    </package>
    <package name="com.example.b">
        <policy
            metric="2003"
            permissions="1" />
        <policy
            metric="2005"
            permissions="0" />
        <policy metric="1003" />
    </package>
</policies>
```

- **policies**: The root tag. The `version` attribute (type int) must be specified to match
                the [version specified](../mod/src/main/java/org/lineageos/mod/health/partner/DefaultPolicyParser.kt)
                by the Mod app in order for the file to be parsed.
    - **package**: Identifies a set of policies for a specific app. The `name` attribute (type string)
                   must be specified to set the target package for the included policies.
        - **policy**: Defines a policy for a given package (parent tag). The `metric` attribute
                      (type int) must be defined and be a [valid metric id value](/docs/metrics.md).
                      The `permissions` attribute is optional but it must be a
                      [valid access permissions value](/docs/access_control.md). Defaults to 0 / None
