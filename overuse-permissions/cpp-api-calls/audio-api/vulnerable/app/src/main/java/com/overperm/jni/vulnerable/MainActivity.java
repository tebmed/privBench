package com.overperm.jni.vulnerable;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// MainActivity.java
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;

    // Load the JNI class
    static {
        System.loadLibrary("audio-control");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button changeVolumeButton = findViewById(R.id.changeVolumeButton);
        changeVolumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    // Call the JNI method to change volume (replace 42 with desired volume)
                    AudioControlJNI audioControlJNI = new AudioControlJNI();
                    audioControlJNI.setVolume(42);
                } else {
                    // Permission not granted, request it
                    requestPermission();
                }

            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.MODIFY_AUDIO_SETTINGS}, PERMISSION_REQUEST_CODE);
    }
}
