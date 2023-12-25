## Unset Action in PendingIntent Creation - Java
This test case show the vulnerable app how the pending intent is created with an empty base action using the follwing java code pattern:

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
