package com.autominder.autominder.obdSensor.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.navigation.NavHostController


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
    val permiso = if (VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        )
    } else {
        TODO("VERSION.SDK_INT < S")
    }

    if (permiso) {
        bluetoothManager!!.adapter?.startDiscovery()
        bluetoothAdapter?.bondedDevices?.forEach {
            Log.d("AQUI", "${it.name} ${it.address} ${it.type}")
        }


    }

    /* bluetoothAdapter?.bondedDevices?.forEach {
         Log.d("AQUI", "${it.name} ${it.address} ${it.type}")
     }*/

    //bluetoothManager!!.adapter?.startDiscovery()

    //Permission check

    LazyColumn() {
        item {
            Button(onClick = {
                if (bluetoothAdapter?.isEnabled == true) {
                    val reciever = onBluetoothEnable(context)
                    if (permiso) {
                        bluetoothManager!!.adapter?.startDiscovery()
                        bluetoothAdapter.bondedDevices?.forEach {
                            Log.d("AQUI", "${it.name} ${it.address} ${it.type}")
                        }


                    }
                    val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                    context.registerReceiver(reciever, filter)
                }

            }) {
                Text(text = "Enable Bluetooth")
            }
        }

    }
}


@Composable
fun rememberBluetoothManager(context: Context): BluetoothManager {
    val service = remember(context) {
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }
    return service
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
                            BluetoothDevice.EXTRA_DEVICEL
                        )
                    }
                device?.let { Log.d("AQUI", "${device.name} HOLA HOLA ", ) }

            }

        }
    }
}

fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}
