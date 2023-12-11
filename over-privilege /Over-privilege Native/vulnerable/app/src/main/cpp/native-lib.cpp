#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_benignappnative_MainActivity_reverseString(JNIEnv* env, jobject /* this */, jstring inputString) {
    const char* str = env->GetStringUTFChars(inputString, nullptr);
    std::string reversedStr = str;
    std::reverse(reversedStr.begin(), reversedStr.end());
    env->ReleaseStringUTFChars(inputString, str);

    return env->NewStringUTF(reversedStr.c_str());
}
