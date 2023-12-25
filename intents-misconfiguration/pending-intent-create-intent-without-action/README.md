## PendingIntent - Creation of Intent without Action - Java
This test case demonstrates how the **vulnerable** app generated a **PendingIntent** with an empty base action using the following Java code pattern:

````java
Intent intent = new Intent();
PendingIntent pendingIntent = PendingIntent.getActivity(
    MainActivity.this,
    0,
    intent,
    PendingIntent.FLAG_UPDATE_CURRENT
);

````

## References

[1]. https://developer.android.com/reference/android/app/PendingIntent

[2]. https://support.google.com/faqs/answer/10437428?hl=en
