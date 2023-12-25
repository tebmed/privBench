package com.intents.misconf.vulnerable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void setAlarm(View view) {
        Intent intent;
        intent = new Intent(this, MyAlarmService.class);
        pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        // Set the alarm to start after 60 seconds
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5); // Alarm starts after 60 seconds

        // Schedule the alarm using AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(View view) {
        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
