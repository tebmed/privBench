#include <jni.h>
#include <stdio.h>
#include <android/log.h>

extern "C" {

JNIEXPORT void JNICALL
Java_com_overperm_jni_vulnerable_AudioControlJNI_setVolume(JNIEnv *env, jobject obj, jint volumeLevel) {
    jclass audioServiceClass = env->FindClass("com/android/server/audio/AudioService");
    if (audioServiceClass == nullptr) {
    __android_log_print(ANDROID_LOG_ERROR, "AudioControlJNI", "Class not found");
    return;
}

// Find the releasePlayer method ID
jmethodID releasePlayerMethod = env->GetMethodID(audioServiceClass, "releasePlayer", "(I)V");
if (releasePlayerMethod == nullptr) {
__android_log_print(ANDROID_LOG_ERROR, "AudioControlJNI", "Method not found");
return;
}

// Modify the audio volume (replace 42 with your desired volume level)
env->CallVoidMethod(obj, releasePlayerMethod, 42);
__android_log_print(ANDROID_LOG_INFO, "AudioControlJNI", "Volume changed");
}

} // extern "C"
