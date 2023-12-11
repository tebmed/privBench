In this scenario, the custom permission is present in the malicious app.

In the malicious app, the custom permission is set at a dangerous protection level, belongs to the Undefined group and is explicitly declared in the manifest.xml file as follows:

```xml
<permission android:name="com.example.customPermission"
        android:permissionGroup="android.permission-group.UNDEFINED"
        android:protectionLevel="dangerous"/>

```
