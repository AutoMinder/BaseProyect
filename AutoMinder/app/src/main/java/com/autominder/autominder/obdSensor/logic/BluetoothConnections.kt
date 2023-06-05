package com.autominder.autominder.obdSensor.logic

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext

import java.lang.reflect.Method
import java.util.logging.Handler

class BluetoothConnections(
    bluetoothAdapter: BluetoothAdapter,
    bluetoothManager: BluetoothManager?,
    context: Context
) {
    enum class ScanStatus {
        SCANNING,
        NOT_SCANNING
    }

    private fun isConnected(device: BluetoothDevice): Boolean {
        return try {
            val m: Method = device.javaClass.getMethod("isConnected")
            m.invoke(device) as Boolean
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }
    }

    @SuppressLint("MissingPermission")
    fun isDeviceConnectedByName(
        context: Context,
        deviceName: String,
        bluetoothAdapter: BluetoothAdapter,
        bluetoothManager: BluetoothManager?
    ): BluetoothDevice? {

        val pairedDevices = bluetoothAdapter.bondedDevices
        val contexto = context

        for (device in pairedDevices) {
            if (device.name == "G435 Bluetooth Gaming Headset" || device.name == deviceName || device.name == "OBDII" || deviceName.contains(
                    "OBD"
                )
            ) {
                if (isConnected((device))) {
                    return device
                }
            }

        }
        return null
    }

    val leDeviceListAdapter = mutableStateListOf<BluetoothDevice>()

    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    var scanning = false
    private val handler = android.os.Handler()

    //gatt callback pls
    private val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            val deviceAddress = gatt!!.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("escaneando", "Successfully connected to $deviceAddress")
                    // TODO: Store a reference to BluetoothGatt
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.w("escaneando", "Successfully disconnected from $deviceAddress")
                    gatt.close()
                }
            } else {
                Log.w(
                    "escaneando",
                    "Error $status encountered for $deviceAddress! Disconnecting..."
                )
                gatt.close()
            }

        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d("escaneando", "SUCCESSSSS")
            }
        }


    }


    private val leScanCallback = object : android.bluetooth.le.ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult?) {

            result?.device?.let { device ->
                if (!leDeviceListAdapter.contains(device)) {

                    leDeviceListAdapter.add(device)
                    Log.d("escaneando", "${device.name} ${device.address}")
                    if (device.name == "OBDII") {
                        Log.d("escaneando", "conectando")

                        //val gatt = device.connectGatt(context, true, gattCallback)
                        //gatt.connect()
                        device.connectGatt(context, true, gattCallback)
                    }

                }
            }
        }
    }

    private val SCAN_PERIOD: Long = 100000

    @SuppressLint("MissingPermission")
    fun scanLeDevice() {
        if (!scanning) {
            bluetoothLeScanner.startScan(leScanCallback)
            Log.d("escaneando", "escaneando")


            handler.postDelayed({
                scanning = false
                bluetoothLeScanner.stopScan(leScanCallback)
                Log.d("escaneando", "Stopped")
            }, SCAN_PERIOD)
        } else {
            scanning = true
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }
}