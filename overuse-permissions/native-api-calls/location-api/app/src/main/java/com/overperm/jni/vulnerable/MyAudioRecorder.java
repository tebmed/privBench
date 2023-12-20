package com.overperm.jni.vulnerable;

import android.media.MediaRecorder;

public class MyAudioRecorder {

    static {
        System.loadLibrary("audioapi");
    }

    public native void startRecording();
}
