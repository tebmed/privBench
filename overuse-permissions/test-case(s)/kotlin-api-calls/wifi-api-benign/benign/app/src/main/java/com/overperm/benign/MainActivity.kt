package com.overperm.benign

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var wifiStateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiStateTextView = findViewById(R.id.wifiStateTextView)

        val enableWifiButton = findViewById<Button>(R.id.enableWifiButton)
        enableWifiButton.setOnClickListener {
            setWifiEnabled(true)
        }

        val disableWifiButton = findViewById<Button>(R.id.disableWifiButton)
        disableWifiButton.setOnClickListener {
            setWifiEnabled(false)
        }

        updateWifiStateText()
    }

    private fun setWifiEnabled(enabled: Boolean) {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = enabled
        updateWifiStateText()
    }

    private fun updateWifiStateText() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiState = if (wifiManager.isWifiEnabled) "Enabled" else "Disabled"
        wifiStateTextView.text = "WiFi State: $wifiState"
    }
}
