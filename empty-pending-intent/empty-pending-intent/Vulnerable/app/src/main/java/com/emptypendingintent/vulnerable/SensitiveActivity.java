package com.emptypendingintent.vulnerable;

import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SensitiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensitive);
        Log.d("Vulnerable","Sensitive Activity Started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Vulnerable","Sensitive Activity Stopped");
        stopService(new Intent(getApplicationContext(), SensitiveService.class));
    }
}