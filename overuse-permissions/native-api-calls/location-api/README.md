# Overprivileged Application - Invoking Location Api through JNI
In this case, the application **vulnerable** makes use of Java Native Interface (JNI) to call the Location API. The code snippet below demonstrates how the app invokes the Location API through JNI:

````cpp
//See jnp/gps.cpp for more details
jclass locationClass = env->FindClass("android/location/Location");
jmethodID getLatitudeMethodID = env->GetMethodID(locationClass, "getLatitude", "()D");
jmethodID getLongitudeMethodID = env->GetMethodID(locationClass, "getLongitude", "()D");

jdouble latitude = env->CallDoubleMethod(location, getLatitudeMethodID);
jdouble longitude = env->CallDoubleMethod(location, getLongitudeMethodID);
````

For the proper functioning of this API call, the app requires the following permissions (refer to AndroidManifest.xml):

 ````xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" >
 ````

However, the developer unintentionally included an unnecessary permission, **ACCESS_BACKGROUND_LOCATION**, introduced in Android 10, allowing access to the device's location while the app operates in the background. The app, which should only access location data while in the foreground, has no functionality or API calls that utilize this permission in its source code:

 ````xml
<uses-permission android:name="android.permissionACCESS_BACKGROUND_LOCATION" />
 ````
This unnecessary permission might pose potential security risks, allowing the app to access location in the background without a legitimate reason.


 ## References
  [1]. https://developer.android.com/training/articles/perf-jni

  [2]. https://developer.android.com/reference/android/location/LocationManager
