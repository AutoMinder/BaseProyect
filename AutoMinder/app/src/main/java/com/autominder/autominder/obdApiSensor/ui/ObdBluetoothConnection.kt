package com.autominder.autominder.obdApiSensor.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Build.VERSION
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.autominder.autominder.obdApiSensor.logic.BluetoothConnections
import com.autominder.autominder.ui.components.LoadingScreen


@SuppressLint("MissingPermission")
@Composable
fun ObdSensorConnectScreen(
    obdSensorViewModel: ObdSensorViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    val bluetoothManager = remember {
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
    }
    val bluetoothAdapter: BluetoothAdapter? = remember {
        bluetoothManager!!.adapter
    }
    val bluetoothConnection = remember {
        BluetoothConnections(bluetoothAdapter!!, bluetoothManager, context, obdSensorViewModel)
    }
    val isLoading by obdSensorViewModel.isLoading.collectAsState()
    val carVin by obdSensorViewModel.carVin.collectAsState()
    val carTemperature by obdSensorViewModel.carTemperature.collectAsState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Esta pantalla es experimental, si sufres muchos errores, te agradeceriamos que nos los reportaras",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            modifier = Modifier.padding(10.dp)
        )
        Button(
            shape = MaterialTheme.shapes.small,
            colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            onClick = {
                if (bluetoothAdapter?.isEnabled == true) {
                    val reciever = onBluetoothEnable(context)
                    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                    bluetoothConnection.scanLeDevice()
                    context.registerReceiver(reciever, filter)
                }

            }) {
            Text(text = "Enable Bluetooth", color = MaterialTheme.colorScheme.onPrimary)
        }
        Button(
            shape = MaterialTheme.shapes.small,
            colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            onClick = {
                bluetoothConnection.sendVinCommandToCar(
                    "00001101-0000-1000-8000-00805f9b34fb",
                    context,
                    obdSensorViewModel
                )
            }) {
            Text(text = "Obtener VIN de mi carro", color = MaterialTheme.colorScheme.onPrimary)
        }
        Button(
            shape = MaterialTheme.shapes.small,
            colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            onClick = {
                bluetoothConnection.sendTemperatureCommandToCar(
                    "00001101-0000-1000-8000-00805f9b34fb",
                    context,
                    obdSensorViewModel
                )
            }) {
            Text(
                text = "Obtener temperatura del refrigerante de de mi carro",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        if (isLoading) {
            LoadingScreen()
        } else {
            if (carVin != "" && carTemperature != "") {
                Text(
                    text = "VIN de tu carro: $carVin",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                )
                Text(
                    text = "Temperatura del refrigerante de tu carro: $carTemperature",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "No has obtenido el VIN de tu carro",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                )
                Text(
                    text = "No has obtenido la temperatura del refrigerante de tu carro",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                )
            }
        }
    }
}


fun onBluetoothEnable(context: Context): BroadcastReceiver {
    return object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == BluetoothDevice.ACTION_FOUND) {
                val device: BluetoothDevice? =
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            BluetoothDevice::class.java
                        )
                    } else {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE
                        )
                    }
                device?.let { Log.d("AQUI", "${device.name} HOLA HOLA ") }

            }

        }
    }
}