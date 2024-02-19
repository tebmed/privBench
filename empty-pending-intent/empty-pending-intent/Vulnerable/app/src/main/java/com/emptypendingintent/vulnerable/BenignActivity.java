package com.emptypendingintent.vulnerable;

import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class BenignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benign);
        Log.d("Vulnerable","Benign Activity Started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Vulnerable","Benign Activity Stopped");
        stopService(new Intent(getApplicationContext(), BenignService.class));
    }
}