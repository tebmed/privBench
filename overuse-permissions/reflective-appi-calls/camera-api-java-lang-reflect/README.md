# Overprivileged Application - Invoking Camera Api through Java Reflection
In this case, the application **vulnerable** makes use of Java reflection to call the Camera API. The code snippet below demonstrates how the app invokes the Camera API through reflection:

````java
   Method openCameraMethod = 
        cameraManager.getClass().getMethod(
            "openCamera",
            String.class,
            CameraDeviceStateCallback.class,
            null
        );
    
    openCameraMethod.invoke(
        cameraManager, cameraId, new CameraDeviceStateCallback(), null
    );
````

For the proper functioning of this API call, the app requires the following permissions (refer to AndroidManifest.xml):

 ````xml
   <uses-feature android:name="android.hardware.camera" />
   <uses-permission android:name="android.permission.CAMERA" />
 ````

However, the developer unintentionally declares an unnecessary permission that is unrelated to its functionality. The app's source code does not utilize any API calls related to this permission:

 ````xml
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 ````
 
 This unnecessary permission might lead to potential security risks and may grant the app access to users' external storage without a legitimate reason.


 ## References
  [1]. https://developer.android.com/reference/java/lang/reflect/package-summary

  [2]. https://developer.android.com/reference/android/hardware/camera2/CameraManager