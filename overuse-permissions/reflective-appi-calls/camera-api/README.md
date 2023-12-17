# Overprivileged Application - Invoking Camera Api through Java Reflection
In this case, the application **vulnerable** make call to the CAMER Api using java reflection as follow (See openCamera() in MainActivity class for more details):

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