# Overprivileged Application - Invoking Calender Provider With Kotlin

In this case, the application **vulnerable** is implemented with  to fetch and display calendar events from the device's calendar.

````kotlin
//See MainActivity.kt for more details
val cursor = contentResolver.query(
      CalendarContract.Events.CONTENT_URI,
      projection,
      null,
      null,
      null
)
````

For the proper functioning of this API call, the app requires the following permission(s) (refer to
AndroidManifest.xml):

 ````xml
 <uses-permission android:name="android.permission.READ_CALENDAR" />
 ````

However, the developer unintentionally included an unnecessary permission, **WRITE_CALENDAR** that allows modify or create calendar events on the device.

 ````xml
<uses-permission android:name="android.permission.WRITE_CALENDAR"/>
 ````

The inclusion of this unnecessary permission could potentially pose security risks, although it is deemed necessary for the proper functioning of the app.


## API Level: 
This app is functional across all Android API levels.

## References

[1]. https://developer.android.com/guide/topics/providers/calendar-provider
