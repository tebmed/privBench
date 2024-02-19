## PendingIntent - Unset Action - Immediate Intent Creation Pattern - Java
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
This pattern immediately creates an Intent object and then uses it to create a PendingIntent for a getActivity

## References

[1]. https://developer.android.com/reference/android/app/PendingIntent

[2]. https://support.google.com/faqs/answer/10437428?hl=en
