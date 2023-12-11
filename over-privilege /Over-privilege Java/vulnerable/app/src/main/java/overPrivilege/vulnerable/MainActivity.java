package overPrivilege.vulnerable;

import com.vulnerable.R;
import overPrivilege.stringlibrary.SmsService;
import overPrivilege.stringlibrary.StringService;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_SMS = 1;
    private TextView textViewResult;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextInput);
        Button buttonReverse = findViewById(R.id.buttonReverse);
        textViewResult = findViewById(R.id.textViewResult);


        buttonReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PERMISSIONS_REQUEST_READ_SMS);
                }
                else{
                    startSmsService();
                }
                reverseText();
            }
        });
    }

    private void reverseText() {
        String inputText = editText.getText().toString();
        String reversedText = StringService.reverseString(inputText);
        textViewResult.setText(reversedText);
    }

    private void startSmsService() {
        Intent intent = new Intent(this, SmsService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSmsService();
            } else {
                textViewResult.setText("Permission pour lire les SMS refusée.");
            }
        }
    }
}