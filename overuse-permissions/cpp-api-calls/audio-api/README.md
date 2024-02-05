# Overprivileged Application - Invoking Audio Api through JNI

In this case, the application **vulnerable** makes use of Java Native Interface (JNI) to call the Audio API. The app adujsts the audio volume. The code snippet below demonstrates how the app invokes the Location API through JNI:

````cpp
//See jnp/audio-control.cpp for more details
jclass audioServiceClass = env->FindClass("com/android/server/audio/AudioService");
// Find the releasePlayer method ID
jmethodID releasePlayerMethod = env->GetMethodID(audioServiceClass, "releasePlayer", "(I)V");
// Modify the audio volume (replace 42 with your desired volume level)
env->CallVoidMethod(obj, releasePlayerMethod, 42);

````

For the proper functioning of this API call, the app requires the following permission (refer to AndroidManifest.xml):

 ````xml
 <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
 ````

However, the developer unintentionally included others permissiopns which are not necessary for adjusting the AUDIO volume:

 ````xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 ````

These unnecessary permissions might pose potential security risks.


To build the vulnerable app project, don't forget to specify the path/to/ndk in the CMakeLists.txt file, and the path to CMakeLists.txt in the app's build.gradle file.


## References

[1]. https://developer.android.com/training/articles/perf-jni

[2].https://android.googlesource.com/platform/frameworks/base/+/refs/heads/main/services/core/java/com/android/server/audio/AudioService.java
