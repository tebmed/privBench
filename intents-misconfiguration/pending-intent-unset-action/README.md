## Unset Action in PendingIntent Creation - Java
This test case shows how the vulnerable app created a pendingIntent with an empty base action using the following java code pattern:

````java
PendingIntent pendingIntent = PendingIntent.getActivity(
    MainActivity.this,
    0,
    new Intent(),
    PendingIntent.FLAG_UPDATE_CURRENT
);

````

## References

[1]. https://developer.android.com/reference/android/app/PendingIntent

[2]. https://support.google.com/faqs/answer/10437428?hl=en
