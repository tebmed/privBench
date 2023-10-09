package com.example.benign_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private TextView CameraPermissionText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraPermissionText = findViewById(R.id.CameraPermissionText);
        Button checkCameraPermission = findViewById(R.id.CameraPermissionButton);

        checkCameraPermission.setOnClickListener(view -> CheckForCameraPermission());

        GrantDangerousPermissions();

        UpdateText();
    }

    private void GrantDangerousPermissions() {

        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[] {Manifest.permission.SEND_SMS , Manifest.permission.ACCESS_MEDIA_LOCATION},
                0);

    }

    private void CheckForCameraPermission() {

        if( ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }

    }

    private void UpdateText(){
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            CameraPermissionText.setText( R.string.camera_denied );
        }
        else if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            CameraPermissionText.setText( R.string.camera_granted );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE){
            UpdateText();
        }
    }
}