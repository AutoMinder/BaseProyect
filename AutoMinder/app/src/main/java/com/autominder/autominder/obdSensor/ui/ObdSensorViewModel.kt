package com.autominder.autominder.obdSensor.ui

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel

class ObdSensorViewModel(private val applicationContext: Context) : ViewModel() {

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager =
            applicationContext.getSystemService(Application.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    fun connectToDevice() {
        if (bluetoothAdapter == null) {

        }
        if (bluetoothAdapter?.isEnabled == true) {
            // Bluetooth is not enabled, request the user to enable it
            // You can use LiveData or StateFlow to observe the Bluetooth status and notify the UI
            // e.g., _bluetoothEnabled.value = false
            return
        }

        // Bluetooth is enabled, initiate device discovery
        // You can use LiveData or StateFlow to observe the discovery process and notify the UI
        // e.g., _isDiscovering.value = true

        val discoveryIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        // Start an activity for result to handle the user's response
        // e.g., startActivityForResult(discoveryIntent, REQUEST_DISCOVERABLE)
    }
}
