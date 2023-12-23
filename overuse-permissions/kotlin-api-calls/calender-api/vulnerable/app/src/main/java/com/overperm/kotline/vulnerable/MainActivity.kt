package com.overperm.kotline.vulnerable

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val READ_CALENDAR_PERMISSION_CODE = 101
    private val WRITE_CALENDAR_PERMISSION_CODE = 102

    private lateinit var eventsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventsTextView = findViewById(R.id.eventsTextView)
        val loadEventsButton: Button = findViewById(R.id.loadEventsButton)

        loadEventsButton.setOnClickListener {
            checkCalendarPermission()
        }
    }

    private fun checkCalendarPermission() {
        if ( (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED) ||
            ( (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED))
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR),
                READ_CALENDAR_PERMISSION_CODE
            )
        } else {
            loadCalendarEvents()
        }
    }

    @SuppressLint("Range")
    private fun loadCalendarEvents() {
        val projection = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART
        )

        val cursor = contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.let {
            val stringBuilder = StringBuilder()
            while (it.moveToNext()) {
                val eventTitle = it.getString(it.getColumnIndex(CalendarContract.Events.TITLE))
                val eventStart = it.getString(it.getColumnIndex(CalendarContract.Events.DTSTART))
                stringBuilder.append("Title: $eventTitle, Start: $eventStart\n")
            }
            eventsTextView.text = stringBuilder.toString()
            it.close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_CALENDAR_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadCalendarEvents()
            }
        }
    }
}

