package com.autominder.autominder.obdSensor.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.icu.util.Output
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.github.eltonvs.obd.command.engine.RPMCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

@SuppressLint("MissingPermission")
@Composable
fun ObdReader(
    obdSensorViewModel: ObdSensorViewModel,
    bluetoothDevice: MutableState<BluetoothDevice?>
) {
    val responseText = MutableStateFlow<String>("Aqui debería ir la respuesta")
    val coroutineScope = rememberCoroutineScope()
    val dispositivo: BluetoothDevice? = bluetoothDevice.value
    var obdDevice: ObdDeviceConnection? = null


    val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val bluetoothSocket: BluetoothSocket = dispositivo!!.createRfcommSocketToServiceRecord(uuid)

    try {
        bluetoothSocket.connect()
        // Connection successful
        // Proceed with reading and writing data
        val inputStream: InputStream = bluetoothSocket.inputStream
        val outputStream: OutputStream = bluetoothSocket.outputStream
        val _obdDevice = ObdDeviceConnection(inputStream, outputStream)
        obdDevice = _obdDevice
        // Perform OBD-II communication using the input and output streams
        Log.d("OBD", "Connected")



    } catch (e: IOException) {
        // Connection failed
        e.printStackTrace()
    } finally {
        // Close the Bluetooth socket when done
        bluetoothSocket.close()
    }
}