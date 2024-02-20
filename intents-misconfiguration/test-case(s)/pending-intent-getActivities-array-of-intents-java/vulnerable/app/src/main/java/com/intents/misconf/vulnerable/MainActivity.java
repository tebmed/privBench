package com.intents.misconf.vulnerable;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void shareImages(View view) {
        Intent shareIntent = new Intent(); // Intent without an explicit action
        shareIntent.setType("image/*");

        Uri imageUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.cat);
        Uri imageUri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.fish);

        // Add selected image URIs to the intent
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, new ArrayList<>(Arrays.asList(imageUri1, imageUri2)));

        // Create a PendingIntent to display activities capable of receiving multiple images
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{shareIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            // Start the PendingIntent, which will display a list of apps capable of receiving multiple images
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
