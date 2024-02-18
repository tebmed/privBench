package com.overperm.deputy.calendar.vulnerable;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText eventTitleEditText;
    EditText eventDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventTitleEditText = findViewById(R.id.eventTitleEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
    }

    public void createEvent(View view) {
        String title = eventTitleEditText.getText().toString();
        String description = eventDescriptionEditText.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter event title", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create calendar event intent
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description);

        // Verify that there's at least one app available to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the activity (create calendar event)
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app available to handle this action", Toast.LENGTH_SHORT).show();
        }
    }
}