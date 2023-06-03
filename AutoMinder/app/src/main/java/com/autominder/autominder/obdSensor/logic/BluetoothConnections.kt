package com.autominder.autominder.obdSensor.logic

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import java.lang.reflect.Method

class BluetoothConnections(
    bluetoothAdapter: BluetoothAdapter,
    bluetoothManager: BluetoothManager?
) {

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
    ): Boolean {

        val pairedDevices = bluetoothAdapter.bondedDevices


        for (device in pairedDevices) {
            if (device.name == "G435 Bluetooth Gaming Headset" || device.name == deviceName) {
                if (isConnected((device))) {
                    return true
                }
            }

        }
        return false
    }
}