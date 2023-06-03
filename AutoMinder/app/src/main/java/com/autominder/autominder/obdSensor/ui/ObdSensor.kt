package com.autominder.autominder.obdSensor.ui

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
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
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
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

    val bluetoothScanner by lazy {
        bluetoothAdapter?.bluetoothLeScanner ?: error("Bluetooth is not enabled")
    }


    bluetoothAdapter?.bondedDevices?.forEach {
        Log.d("AQUI", "${it.name} ${it.address} ${it.type}")
    }

    val permiso = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.BLUETOOTH_SCAN
    ) == PackageManager.PERMISSION_GRANTED

    LazyColumn() {
        item {
            Button(onClick = {
                if (bluetoothAdapter?.isEnabled == true) {
                    val reciever = onBluetoothEnable(context)
                    if (permiso) {
                        bluetoothAdapter.startDiscovery()
                        bluetoothScanner.startScan(
                            listOf(
                                ScanFilter.Builder().build()
                            ),
                            ScanSettings.Builder().build(),
                            object : ScanCallback() {
                                override fun onScanResult(
                                    callbackType: Int,
                                    result: ScanResult
                                ) {
                                    super.onScanResult(callbackType, result)
                                    Log.d("AQUI", "${result.device.name} ${result.device.address}")
                                }

                            })

                        val connectedDevices = bluetoothAdapter.bondedDevices
                        val isConnected = connectedDevices.any { it.name == "OBDII" }
                        if (isDeviceConnectedByName(context, "OBDII", bluetoothAdapter, bluetoothManager)) {
                            navController.navigate("obd_reader")
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


@SuppressLint("MissingPermission")
fun isDeviceConnectedByName(
    context: Context,
    deviceName: String,
    bluetoothAdapter: BluetoothAdapter,
    bluetoothManager: BluetoothManager?
): Boolean {

    val bluetootProfile = bluetoothManager?.getConnectedDevices(BluetoothProfile.GATT)

    for (device in bluetootProfile!!) {
        if (device.name == deviceName) {
            return true
        }
    }
    return false
}