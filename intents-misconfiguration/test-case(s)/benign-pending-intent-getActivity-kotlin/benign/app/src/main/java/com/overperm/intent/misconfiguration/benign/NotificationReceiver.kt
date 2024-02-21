package com.overperm.intent.misconfiguration.benign

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.example.reminder.ACTION_SHOW_NOTIFICATION") {
            Toast.makeText(context, "Time to do something!", Toast.LENGTH_SHORT).show()
        }
    }
}
