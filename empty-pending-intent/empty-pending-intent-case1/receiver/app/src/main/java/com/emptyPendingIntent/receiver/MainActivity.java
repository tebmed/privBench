package com.emptyPendingIntent.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
            list.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH'h' mm'm' ss's'")) + ": '"+ intent.getStringExtra("Data") + "'");
            adapter.notifyDataSetChanged();
        }
    };
    private ArrayAdapter<String> adapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listview);
        registerReceiver(br, new IntentFilter("emptyPendingIntent.sender.send"), RECEIVER_EXPORTED);


        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, list);
        listView.setAdapter(adapter);
    }
}