package com.overpem.java.reflection.vulnerable;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE = 1;
    private static final String INTENT_CLASS_NAME = "android.content.Intent";
    private static final String ACTION_CALL_FIELD = "ACTION_CALL";
    private EditText phoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);

        // Button click listener to initiate the call
        findViewById(R.id.call_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        // Check for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the call if phone number is not empty
                if (!phoneNumberEditText.getText().toString().isEmpty()) {
                    makePhoneCall();
                } else {
                    Toast.makeText(this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Permission denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to make the phone call
    private void makePhoneCall() {
        try {
            // Using Class.forName to access Intent.ACTION_CALL
            Class<?> c = Class.forName(INTENT_CLASS_NAME);
            Intent intent = new Intent((String) c.getField(ACTION_CALL_FIELD).get(null));
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            if (!phoneNumber.isEmpty()) {
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Enter a phone number", Toast.LENGTH_SHORT).show();
            }
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
