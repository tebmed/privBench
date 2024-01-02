package com.intents.misconf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Alarm! Wake up!", "Alarm! Wake up!");
        Toast.makeText(context, "Alarm! Wake up!", Toast.LENGTH_SHORT).show();
    }
}
