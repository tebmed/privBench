package inconsistent.customPermission.groupMapping;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button checkCameraPermission = findViewById(R.id.CameraPermissionButton);

        checkCameraPermission.setOnClickListener(view -> CheckForCameraPermission());

        updateTextViews();
    }

    private void CheckForCameraPermission() {
        if(!isPermissionGranted( android.Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            Toast.makeText(MainActivity.this, "Camera Permission already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTextViews(){
        updateText(R.id.CameraPermissionStatusTextView, isPermissionGranted( android.Manifest.permission.CAMERA));
        updateText(R.id.FilePermissionStatusTextView, isPermissionGranted( Manifest.permission.ACCESS_MEDIA_LOCATION));
        updateText(R.id.SMSPermissionStatusTextView, isPermissionGranted( Manifest.permission.SEND_SMS));
    }

    private boolean isPermissionGranted(String permissionName){
        return ContextCompat.checkSelfPermission(MainActivity.this, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    private void updateText(int textViewId, boolean granted){
        TextView textView = findViewById(textViewId);

        if(granted){
            textView.setText(R.string.AccessGranted);
            textView.setTextColor(Color.rgb(40,240,40));
        }
        else {
            textView.setText(R.string.AccessDenied);
            textView.setTextColor(Color.rgb(240,40,40));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        updateTextViews();
    }
}