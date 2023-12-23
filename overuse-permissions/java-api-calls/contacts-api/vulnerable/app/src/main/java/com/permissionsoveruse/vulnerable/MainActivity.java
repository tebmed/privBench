package com.permissionsoveruse.vulnerable;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_READ_CONTACTS_AND_EXTERNAL_STORAGE = 100;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // Check for runtime permissions on Android 6.0 and above
        checkContactsAndStoragePermissions();
    }

    private void checkContactsAndStoragePermissions() {
        if (
                ContextCompat.checkSelfPermission(
                        this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                        ||
                        ContextCompat.checkSelfPermission(
                                this, Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            // Over privilege permission, unused but requested: READ_EXTERNAL_STORAGE
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_READ_CONTACTS_AND_EXTERNAL_STORAGE
            );
        } else {
            // Permission is already granted, read contacts
            readContacts();
            listFilesAndFolders();
        }
    }

    /**
     * Access to files without real need
     */
    private void listFilesAndFolders() {
        // Get the external storage directory
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        // List all files and folders in the external storage directory
        if (externalStorageDirectory.isDirectory()) {
            File[] files = externalStorageDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Here I am manipulating the privilege without any necessary
                    System.out.println("Access to File Name without real need: " + file.getName());
                }
            }
        }
    }

    @SuppressLint("Range")
    private void readContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        ArrayList<String> contactNames = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // Get contact details
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactNames.add(contactName);
            }
            cursor.close();
        } else {
            showToast("No contacts found.");
        }

        // Display contact names in a ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactNames);
        listView.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS_AND_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, read contacts
                readContacts();
                listFilesAndFolders();
            } else {
                showToast("Permission denied. Cannot read contacts.");
            }
        }
    }
}
