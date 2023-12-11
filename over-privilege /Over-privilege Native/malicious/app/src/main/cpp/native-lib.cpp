#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_maliciousappnative_MainActivity_getStringFromNative(JNIEnv* env, jobject /* this */) {
    std::string hello = "This is a string from native code";
    return env->NewStringUTF(hello.c_str());
}
