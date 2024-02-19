package com.overperm.deputy.location.benign

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var locationEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationEditText = findViewById(R.id.locationEditText)
    }

    fun openMap(view: View) {
        // Retrieve the location from the EditText field
        val location = locationEditText.text.toString().trim()

        if (location.isEmpty()) {
            locationEditText.error = "Please enter a location"
            return
        }

        val mapUri = Uri.parse("geo:0,0?q=$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            // Start the activity (open Google Maps)
            startActivity(mapIntent)
        }
    }
}