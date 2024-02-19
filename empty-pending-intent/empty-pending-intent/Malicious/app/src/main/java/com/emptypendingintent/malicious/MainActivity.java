package com.emptypendingintent.malicious;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MaliciousReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        receiver = new MaliciousReceiver(findViewById(R.id.chronometer), findViewById(R.id.chronoText), button);

        registerReceiver(receiver, new IntentFilter("action.com.emptypendingintent.broadcast"), RECEIVER_EXPORTED);

        button.setOnClickListener(v -> {
            receiver.SendServiceRequest();
        });
    }
}