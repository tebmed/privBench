package inconsistent.customPermission.definition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.benignv1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GrantDangerousPermissions();
    }


    private void GrantDangerousPermissions() {
        if( !isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE) ){
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }
    }

    private boolean isPermissionGranted(String permissionName){
        return ContextCompat.checkSelfPermission(MainActivity.this, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    public void onClickUs(View view) {
        TextClock clock = findViewById(R.id.textClock);
        clock.setTimeZone("America/Los_Angeles");
    }

    public void onClickFrance(View view) {
        TextClock clock = findViewById(R.id.textClock);
        clock.setTimeZone("Europe/Berlin");
    }


}