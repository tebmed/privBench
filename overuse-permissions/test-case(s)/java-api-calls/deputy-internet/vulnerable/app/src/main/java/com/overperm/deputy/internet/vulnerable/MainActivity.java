package com.overperm.deputy.internet.vulnerable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText urlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlEditText = findViewById(R.id.urlEditText);
    }

    // Method to handle button click and open the URL
    public void openUrl(View view) {
        // Retrieve the URL from the EditText field
        String url = urlEditText.getText().toString().trim();

        // Check if the URL is empty
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an intent with the ACTION_VIEW action
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // Set the data (URL) for the intent
        intent.setData(Uri.parse(url));

        // Explicitly set the package name of the browser app
        intent.setPackage("com.android.chrome"); // Change this to the package name of your preferred browser app

        // Verify that there's at least one app available to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the activity (open the browser)
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app available to handle this URL", Toast.LENGTH_SHORT).show();
        }
    }
}
