package com.intents.misconf.vulnerable;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyAlarmService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform any action you want when the alarm triggers
        Toast.makeText(this, "Alarm triggered!", Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
