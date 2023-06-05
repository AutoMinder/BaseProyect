package com.autominder.autominder.obdSensor.ui

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.util.Log
import android.widget.Spinner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.autominder.autominder.components.LoadingScreen
import com.autominder.autominder.obdSensor.logic.BluetoothConnections


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


    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Button(onClick = {
            if (bluetoothAdapter?.isEnabled == true) {
                val reciever = onBluetoothEnable(context)
                val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                bluetoothConnection.scanLeDevice()
                context.registerReceiver(reciever, filter)
                obdSensorViewModel.setIsLoading(true)
            }

        }) {
            Text(text = "Enable Bluetooth")
        }
        Text(text = "Hola")
        if (!isLoading) {
            Text(text = "NO CARGO")
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