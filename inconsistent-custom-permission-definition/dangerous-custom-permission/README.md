In this scenario, the custom permission is present in both vulnerable apps.

- In the vulnerable_v1 app, the custom permission is set at a normal protection level and is explicitly declared in the manifest.xml file as follows:

```xml
 <permission
   android:name="inconsistent.customPermission.customPermission"
   android:protectionLevel="normal"/>

```

- In the vulnerable_v2 app, the custom permission is set at a dangerous protection level, bekongs to the storage group and is explicitly declared in the manifest.xml file as follows:

````xml
<permission
  android:name="inconsistent.customPermission.customPermission"
  android:permissionGroup="android.permission-group.STORAGE"
  android:protectionLevel="dangerous"/>

```
