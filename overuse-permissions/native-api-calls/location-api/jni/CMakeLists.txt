cmake_minimum_required(VERSION 3.22.1)

add_library(gps-jni SHARED gps.cpp)
target_link_libraries(gps-jni)

# Specify the path to the Android NDK (replace path/to/ndk with your actual path).
set(pathToNdk path/to/ndk)

set(ANDROID_NDK "${pathToNdk}")

set(ANDROID_TOOLCHAIN ${ANDROID_NDK}/build/cmake/android.toolchain.cmake)

set(CMAKE_TOOLCHAIN_FILE ${ANDROID_TOOLCHAIN})

set(CMAKE_ANDROID_MIN_SDK_VERSION 21)

set(CMAKE_ANDROID_PLATFORM android-21)