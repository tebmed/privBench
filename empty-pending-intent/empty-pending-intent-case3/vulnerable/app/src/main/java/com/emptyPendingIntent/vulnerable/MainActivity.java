package com.emptyPendingIntent.vulnerable;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listview);
        final Button button = findViewById(R.id.button1);

        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, list);
        listView.setAdapter(adapter);

        button.setOnClickListener(view -> {
            Intent intent = CreateBasicIndent();
            SendIntent(intent);

            LogIntent(intent);
        });
    }

    private void LogIntent(Intent intent){
        list.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH'h' mm'm' ss's'")) + ": '"+ intent.getStringExtra("Data") + "'");
        adapter.notifyDataSetChanged();
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

    private Intent CreateBasicIndent(){
        Intent intent = InnerIntentAction.getInnerIntent(this);
        String message = ((EditText)findViewById(R.id.MessageText)).getText().toString();

        if(message.isEmpty()){
            message = "Message de base";
        }

        intent.putExtra("Data",message);

        return intent;
    }

    private static class InnerIntentAction{
        static Intent getInnerIntent(Context context) {
            Intent intent = new Intent();
            intent.setAction("emptyPendingIntent.sender.send");
            return intent;
        }
    }

}