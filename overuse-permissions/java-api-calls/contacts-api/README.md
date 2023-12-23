# Overprivileged Application - Invoking Location Api through JNI

In this case, the application **vulnerable** makes use of Java api to call the Contacts Provider. 
The code snippet below demonstrates how the app invokes the Contacts READ Provider:

````java
ContentResolver contentResolver = getContentResolver();
Cursor cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        null,
        null,
        null,
        null
);
ArrayList<String> contactNames = new ArrayList<>();
if (cursor != null && cursor.getCount() > 0) {
    while (cursor.moveToNext()) {
        // Get contact details
        String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        contactNames.add(contactName);
    }
    cursor.close();
} else {
    showToast("No contacts found.");
}
````

For the proper functioning of this API call, the app requires the following permissions (refer to *AndroidManifest.xml*):

 ````xml
<uses-permission android:name="android.permission.READ_CONTACTS" />
 ````

However, the developer unintentionally included an unnecessary permission, **READ_EXTERNAL_STORAGE**, introduced in Android 16, allowing access to the device's storage (READ Device Files and Folders).
The final user may thought that the permission is necessary to access Contacts:

 ````xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="32"/>
 ````
 The following code allows to access files and directories and perform any risky operation after being granted to read storage:

````java`
// Get the external storage directory
File externalStorageDirectory = Environment.getExternalStorageDirectory();
// List all files and folders in the external storage directory
if (externalStorageDirectory.isDirectory()) {
    File[] files = externalStorageDirectory.listFiles();
    if (files != null) {
        for (File file : files) {
            // Here I am manipulating the privilege without any necessary
            System.out.println("Access to File Name without real need: " + file.getName());
        }
    }
}
````

This unnecessary permission might pose potential security risks, allowing the app to access device files and exploit them harmful without legitimate reason for the normal functioning

## References

[1]. https://developer.android.com/guide/topics/providers/contacts-provider
