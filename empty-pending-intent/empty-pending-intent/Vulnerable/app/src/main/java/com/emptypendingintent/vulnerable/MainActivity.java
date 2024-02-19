package com.emptypendingintent.vulnerable;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private TextView chronoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        chronoText = findViewById(R.id.chronoText);
        configureChronometer(chronometer);

        Button broadcastButton = findViewById(R.id.button);

        broadcastButton.setOnClickListener(v -> {
            Intent emptyIntent = new Intent();

            // Create the Pending Intent that will be sent

            Bundle bundle = new Bundle();
            PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

            bundle.putParcelable("pendingIntent", pi);

            // Create the broadcast intent

            Intent intent = new Intent("action.com.emptypendingintent.broadcast");
            intent.putExtra("bundle", bundle);

            Log.d("Vulnerable","Broadcast Send");

            sendBroadcast(intent);

            chronoText.setVisibility(View.VISIBLE);

            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        });
    }

    private void configureChronometer(Chronometer chrono){
        chrono.setOnChronometerTickListener(cArg -> {
            long time = SystemClock.elapsedRealtime() - cArg.getBase();
            int h   = (int)(time /3600000);

            if( h > 0){
                if( h == 1)
                    cArg.setText(String.format( Locale.ENGLISH,"%d hour ago", h));
                else
                    cArg.setText(String.format( Locale.ENGLISH,"%d hours ago", h));
                return;
            }

            int m = (int)(time - h*3600000)/60000;

            if( m > 0){
                if( m == 1)
                    cArg.setText(String.format( Locale.ENGLISH,"%d minute ago", m));
                else
                    cArg.setText(String.format( Locale.ENGLISH,"%d minutes ago", m));
                return;
            }

            int s = (int)(time - h*3600000- m*60000)/1000 ;

            if( s > 2)
                cArg.setText(String.format( Locale.ENGLISH,"%d seconds ago", s));
            else
                cArg.setText(String.format( Locale.ENGLISH,"%d second ago", s));
        });

        chronometer.setText("");
    }
}