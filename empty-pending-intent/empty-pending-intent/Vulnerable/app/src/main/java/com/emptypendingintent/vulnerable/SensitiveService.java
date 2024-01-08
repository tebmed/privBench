package com.emptypendingintent.vulnerable;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SensitiveService extends Service {
    public SensitiveService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Vulnerable","Sensitive Service Started");
        startActivity(new Intent(getApplicationContext(), SensitiveActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("Vulnerable","Sensitive Service Stopped");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}