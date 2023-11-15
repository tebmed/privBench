package com.example.stringlibraryapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    private EditText inputText;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        Button reverseButton = findViewById(R.id.reverseButton);
        Button listSmsTitlesButton = findViewById(R.id.listSmsButton);
        resultText = findViewById(R.id.reversedText);

        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reversed = StringUtils.reverseString(inputText.getText().toString());
                resultText.setText(reversed);
            }
        });

        listSmsTitlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, MY_PERMISSIONS_REQUEST_READ_SMS);
                } else {
                    listSmsTitles();
                }
            }
        });
    }

    private void listSmsTitles() {
        List<String> smsTitles = SmsService.getSmsTitles(this);
        StringBuilder titles = new StringBuilder();
        for (String title : smsTitles) {
            titles.append(title).append("\n");
        }
        resultText.setText(titles.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listSmsTitles();
            } else {
                resultText.setText("Permission refusée, ne peut pas lister les titres des SMS");
            }
        }
    }
}
