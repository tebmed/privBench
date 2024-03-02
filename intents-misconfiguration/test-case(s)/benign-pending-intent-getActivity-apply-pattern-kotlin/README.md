## PendingIntent - Benign app - Intent with a defined action - apply() pattern - kotlin

In this test case the **benign** allows the user to set a reminder by specifying a time using a TimePickerDialog. When the specified time is reached, a notification is shown to remind the user. 


<img src="screenshots/set-reminder.png"><img src="screenshots/reminder-set-ok.png"><img src="screenshots/reminder-notification.png">



The benign app code is written in Kotlin, and it uses a PendingIntent with an action. The code below shows how the action is defined using **apply()** pattern.

````kotlin
//Refer to MainActivity.kt for more details
val intent = Intent().apply {
  action = "com.example.reminder.ACTION_SHOW_NOTIFICATION"
}

val pendingIntent = PendingIntent.getBroadcast(
    this,
    0,
    intent,
    PendingIntent.FLAG_IMMUTABLE
)
````

This app has been tested unsing Android 10 version

## References

[1]. https://developer.android.com/reference/android/app/PendingIntent
