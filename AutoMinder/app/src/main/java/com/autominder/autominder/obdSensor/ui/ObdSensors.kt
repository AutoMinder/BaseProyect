package com.autominder.autominder.obdSensor.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.github.eltonvs.obd.connection.ObdDeviceConnection

@SuppressLint("MissingPermission")
@Composable
fun ObdReader(
    obdSensorViewModel: ObdSensorViewModel,
    bluetoothDevice: MutableState<BluetoothDevice?>
) {

    Column {
        Text(text = "HOLA ME PASE UWU")
        Text(text = bluetoothDevice.value!!.name)
        Text(text = bluetoothDevice.value!!.address)
        Text(text = bluetoothDevice.value!!.bluetoothClass.toString())
    }

}