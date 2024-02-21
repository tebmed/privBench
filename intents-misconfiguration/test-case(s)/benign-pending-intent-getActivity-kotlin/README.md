## PendingIntent - Benign app - Intent with a defined action - kotlin

In this test case the **vulnerable** app creates a **PendingIntent** to display activities capable of receiving multiple images.

<img src="screenshots/1.png" alt="Alt text" title="vulnerable app main screen">
<img src="screenshots/2.png" alt="Alt text" title="vulnerable app main screen">

The following code snippet illustrates the utilization of an array containing intents lacking a specific action when employed with **getActivities()** within a **PendingIntent**



````kotlin
//refer to MainActivity.kt for more details
val intent = Intent(this, ReminderBroadcastReceiver::class.java) 
intent.action = "com.example.reminder.ACTION_SHOW_NOTIFICATION"
val pendingIntent = PendingIntent.getBroadcast(
    this,
    0,
    intent,
    PendingIntent.FLAG_IMMUTABLE
)
````

## References

[1]. https://developer.android.com/reference/android/app/PendingIntent

[2]. https://support.google.com/faqs/answer/10437428?hl=en
