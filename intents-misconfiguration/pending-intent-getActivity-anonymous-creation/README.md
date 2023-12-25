## PendingIntent - Unset Action - Anonymous Intent Creation Pattern - Java
This test case shows how the vulnerable app created a pendingIntent with an empty base action using the following java code pattern:

````java
PendingIntent pendingIntent = PendingIntent.getActivity(
    MainActivity.this,
    0,
    new Intent(),
    PendingIntent.FLAG_UPDATE_CURRENT
);
````
This pattern directly creates an Intent object within the getActivity method call without explicitly assigning it to a variable.


## References

[1]. https://developer.android.com/reference/android/app/PendingIntent

[2]. https://support.google.com/faqs/answer/10437428?hl=en
