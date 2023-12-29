package com.danglingpermission.vulnerableconsumer;

import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void use_bt_action(View v){
        if(checkSelfPermission("com.danglingpermission.vulnerable.CUSTOM_PERMISSION") != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{"com.danglingpermission.vulnerable.CUSTOM_PERMISSION"}, 1);
        }else {
            Intent intent = new Intent();
            intent.setAction("com.daglingpermission.vulnerable.SENSITIVE_ACTION");
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1:
                Intent intent = new Intent();
                intent.setAction("com.daglingpermission.vulnerable.SENSITIVE_ACTION");
                startActivity(intent);

        }
    }
}
