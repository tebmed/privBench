package com.example.benign;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


/**
 * This program was made using the tutorial find on <a href="https://www.geeksforgeeks.org/how-to-build-a-step-counting-application-in-android-studio/">...</a>
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager = null;
    private boolean running = false;
    private float totalSteps = 0f;
    private float previousTotalSteps = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckActivityPermission();
        loadData();
        resetSteps();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    private void CheckActivityPermission() {
        // If the app was installed on Android 9 and then the device updated to Android 10 then this permission will be granted without asking the user
        if(ContextCompat.checkSelfPermission(this, "android.permission.ACTIVITY_RECOGNITION") == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACTIVITY_RECOGNITION"}, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        running = true;
        final Sensor stepSensor = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) : null;

        if( stepSensor == null || sensorManager == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        }
        else{
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private void resetSteps() {
        final TextView tv_stepsTaken = findViewById(R.id.tv_stepsTaken);

        tv_stepsTaken.setOnClickListener(view -> Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show());

        tv_stepsTaken.setOnLongClickListener(view -> {

            previousTotalSteps = totalSteps;

            // When the user will click long tap on the screen,
            // the steps will be reset to 0
            tv_stepsTaken.setText("0");

            // This will save the data
            saveData();

            return true;
        });
    }

    private void saveData() {
        final SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("previousSteps", previousTotalSteps);
        editor.apply();

    }

    private void loadData() {
        final SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        final float savedNumber = sharedPreferences.getFloat("previousSteps", 0f);

        Log.d("MainActivity", "$savedNumber");

        previousTotalSteps = savedNumber;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final TextView tv_stepsTaken = findViewById(R.id.tv_stepsTaken);

        if (running) {
            totalSteps = sensorEvent.values[0];

            // Current steps are calculated by taking the difference of total steps
            // and previous steps
            float currentSteps = totalSteps - previousTotalSteps;

            // It will show the current steps to the user
            tv_stepsTaken.setText(String.valueOf(currentSteps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}