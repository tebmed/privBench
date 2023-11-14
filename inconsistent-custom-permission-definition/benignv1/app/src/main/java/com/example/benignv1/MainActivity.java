package com.example.benignv1;

import android.view.View;
import android.widget.TextClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickUs(View view) {
        TextClock clock = findViewById(R.id.textClock);
        clock.setTimeZone("America/Los_Angeles");
    }

    public void onClickFrance(View view) {
        TextClock clock = findViewById(R.id.textClock);
        clock.setTimeZone("Europe/Berlin");
    }
}