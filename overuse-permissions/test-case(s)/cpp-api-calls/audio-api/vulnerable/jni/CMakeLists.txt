cmake_minimum_required(VERSION 3.22.1)

project(vulnerable)

add_library(audio-control SHARED audio-control.cpp)
target_link_libraries(audio-control
        android
        log
        )

# Specify the path to the Android NDK (replace path/to/ndk with your actual path).
set(pathToNdk path/to/ndk)

set(ANDROID_NDK "${pathToNdk}")

set(ANDROID_TOOLCHAIN ${ANDROID_NDK}/build/cmake/android.toolchain.cmake)

set(CMAKE_TOOLCHAIN_FILE ${ANDROID_TOOLCHAIN})

set(CMAKE_ANDROID_MIN_SDK_VERSION 21)

set(CMAKE_ANDROID_PLATFORM android-21)