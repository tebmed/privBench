package com.overperm.jni.vulnerable;

// AudioControlJNI.java
public class AudioControlJNI {
    static {
        System.loadLibrary("audio-control");
    }

    public native void setVolume(int volumeLevel);
}
