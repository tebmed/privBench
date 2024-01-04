package overPrivilege.reflection;

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
import overPrivilege.stringlibrary.SmsService;
import overPrivilege.stringlibrary.StringService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PERMISSIONS_REQUEST_READ_SMS);
        }

        buttonReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    reverseText();
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void reverseText() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String inputText = editText.getText().toString();

        Method method = StringService.class.getMethod("reverseString", String.class);
        Object reversedText = method.invoke(null, inputText);

        if (reversedText != null){
            textViewResult.setText((String) reversedText);
        }
        else {
            textViewResult.setText("null");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_SMS) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                textViewResult.setText("Permission pour lire les SMS refusée.");
            }
        }
    }
}