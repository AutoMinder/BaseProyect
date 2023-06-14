package com.autominder.autominder.obdApiSensor.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import com.github.eltonvs.obd.command.control.VINCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
@Composable
fun ObdReader(
    obdSensorViewModel: ObdSensorViewModel,
    bluetoothDevice: MutableState<BluetoothDevice?>
) {
    val responseText = MutableStateFlow<String>("Aqui debería ir la respuesta")
    val dispositivo: BluetoothDevice? = bluetoothDevice.value
    val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val coroutineScope = rememberCoroutineScope()

    Text(text = bluetoothDevice.value?.name ?: "No hay dispositivo seleccionado")

    Button(onClick = {

        val bluetoothSocket: BluetoothSocket = try {
            dispositivo?.createRfcommSocketToServiceRecord(uuid)
        } catch (e: IOException) {
            Log.e("OBD", "Socket's create() method failed", e)
            throw e
        }!!

        try {
            bluetoothSocket.connect()
        } catch (connectException: IOException) {
            Log.e("OBD", "Could not connect the client socket", connectException)
            try {
                bluetoothSocket.close()
            } catch (closeException: IOException) {
                Log.e("OBD", "Could not close the client socket", closeException)
            }
        }
        Log.d("OBD", bluetoothSocket.inputStream.toString())
        Log.d("OBD", bluetoothSocket.outputStream.toString())




        coroutineScope.launch {

            val obdDeviceConnection = ObdDeviceConnection(bluetoothSocket.inputStream, bluetoothSocket.outputStream)
            val response = obdDeviceConnection.run(VINCommand(), useCache = true)
            Log.d("OBD", obdDeviceConnection.toString())
            Log.d("OBD", response.formattedValue)
        }


    }) {
        Text(text = "Conectar")
    }

}