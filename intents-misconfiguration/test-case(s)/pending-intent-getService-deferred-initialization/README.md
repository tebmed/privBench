## PendingIntent - Unset Action - Deferred Intent Initialization Pattern - Java
In the tests case we implement a **vulnerable** app that use a **PendingIntent**  to configure an alarm to start at a specific time. The **vulnerable** app demonstrates the use of getService with an unset action in a PendingIntent using the following java pattern:

````java
Intent intent;
intent = new Intent(this, MyAlarmService.class);

pendingIntent = PendingIntent.getService(
    this,
    0,
    intent,
    0
);
````
This pattern initializes the Intent object separately from its declaration and later utilizes it to create a PendingIntent for a service

## References

[1]. https://developer.android.com/reference/android/app/PendingIntent

[2]. https://support.google.com/faqs/answer/10437428?hl=en
