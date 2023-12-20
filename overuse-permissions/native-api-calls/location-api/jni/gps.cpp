#include <jni.h>

extern "C" JNIEXPORT jstring
Java_com_overperm_jni_vulnerable_MainActivity_getGPSLocation(JNIEnv *env,
                                                             jobject /* this */,
                                                             jobject context){
jclass contextClass = env->FindClass("android/content/Context");
jfieldID locationServiceFieldID = env->GetStaticFieldID(contextClass, "LOCATION_SERVICE",
                                                        "Ljava/lang/String;");
jobject locationServiceString = env->GetStaticObjectField(contextClass, locationServiceFieldID);
jstring providerName = static_cast<jstring>(locationServiceString);

jobject locationManager = env->CallObjectMethod(
        context, env->GetMethodID(contextClass, "getSystemService",
                                  "(Ljava/lang/String;)Ljava/lang/Object;"), providerName);

jclass locationManagerClass = env->FindClass("android/location/LocationManager");
jmethodID getLastKnownLocationMethodID = env->GetMethodID(locationManagerClass,
                                                          "getLastKnownLocation",
                                                          "(Ljava/lang/String;)Landroid/location/Location;");
jobject location = env->CallObjectMethod(locationManager, getLastKnownLocationMethodID,
                                         providerName);

jclass locationClass = env->FindClass("android/location/Location");
jmethodID getLatitudeMethodID = env->GetMethodID(locationClass, "getLatitude", "()D");
jmethodID getLongitudeMethodID = env->GetMethodID(locationClass, "getLongitude", "()D");

jdouble latitude = env->CallDoubleMethod(location, getLatitudeMethodID);
jdouble longitude = env->CallDoubleMethod(location, getLongitudeMethodID);

char locationInfo[100];
//sprintf(locationInfo, "Latitude: %f, Longitude: %f", latitude, longitude);

return env-> NewStringUTF(locationInfo);
}
