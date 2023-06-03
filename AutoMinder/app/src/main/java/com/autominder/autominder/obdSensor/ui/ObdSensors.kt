package com.autominder.autominder.obdSensor.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@SuppressLint("MissingPermission")
@Composable
fun ObdReader(
    obdSensorViewModel: ObdSensorViewModel,
    bluetoothDevice: BluetoothDevice
) {

    Column {
        Text(text = "HOLA ME PASE UWU")
        Text(text = bluetoothDevice.name)
        Text(text = bluetoothDevice.address)
        Text(text = bluetoothDevice.bluetoothClass.toString())
    }

}