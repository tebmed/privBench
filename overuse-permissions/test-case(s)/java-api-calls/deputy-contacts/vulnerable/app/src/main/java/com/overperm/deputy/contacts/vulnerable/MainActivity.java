package com.overperm.deputy.contacts.vulnerable;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewContacts(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Set the data (URI) for the intent to view contacts
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        // Verify that there's at least one app available to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the activity (open the contacts app)
            startActivity(intent);
        } else {
            showToast("No app available to handle viewing contacts");
        }
    }
    // Helper method to display toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}