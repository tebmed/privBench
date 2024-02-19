package com.emptypendingintent.benignapp;

import android.content.IntentFilter;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

    public class MainActivity extends AppCompatActivity {

    private BenignReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        receiver = new BenignReceiver(findViewById(R.id.chronometer), findViewById(R.id.chronoText), button);

        registerReceiver(receiver, new IntentFilter("action.com.emptypendingintent.broadcast"), RECEIVER_EXPORTED);

        button.setOnClickListener(v -> {
            receiver.SendServiceRequest();
        });
    }

}