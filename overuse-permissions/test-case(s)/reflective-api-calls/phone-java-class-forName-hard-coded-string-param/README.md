# Overprivileged App - Java Reflection - Class.forname() Pattern - Hard coded String Parameter

In this case, the **vulnerable** app allows users to make phone calls

<img src="screenshots/phone_call_reflection_java.png">
<img src="screenshots/phone_call.png">

It makes use of the java reflection technique to access Intent.ACTION_CALL API. The following code snippet illustrates how the app utilizes reflection to realize this:

````java
//More implementation details can be found in MainActivity.java
Class<?> c = Class.forName("android.content.Intent");
Intent intent = new Intent((String) c.getField("ACTION_CALL").get(null));
.
.
startActivity(intent);
````

This only requires the following permission:

 ````xml
<uses-permission android:name="android.permission.CALL_PHONE"/>
 ````
However, the developer additionally declared two unnecessary permissions

````xml
<uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
<uses-permission android:name="android.permission.READ_CALL_LOG"/>
````
 
## References
[1]. https://www.tutorialspoint.com/java/lang/class_forname_loader.htm

