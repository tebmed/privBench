package com.overperm.jni.vulnerable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private TextView gpsTextView;
    private LocationManager locationManager;
    private LocationListener locationListener;

    static {
        System.loadLibrary("gps-jni");
    }

    public native String getGPSLocation(Context context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gpsTextView = findViewById(R.id.gpsTextView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    String latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());
                    String gpsInfo = "Latitude: " + latitude + ", Longitude: " + longitude;
                    gpsTextView.setText(gpsInfo);
                }
            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Permission denied. Cannot show location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            String simulatedLocation = getGPSLocation(MainActivity.this);
            gpsTextView.setText(simulatedLocation);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}