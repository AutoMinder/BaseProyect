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
import com.autominder.autominder.obdSensor.ui.ObdSensorViewModel
import com.github.eltonvs.obd.command.control.VINCommand
import com.github.eltonvs.obd.command.engine.RPMCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

import java.lang.reflect.Method

class BluetoothConnections(
    bluetoothAdapter: BluetoothAdapter,
    bluetoothManager: BluetoothManager?,
    context: Context,
    private val viewModel: ObdSensorViewModel
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
    private val _scanning = MutableStateFlow<Boolean>(false)
    val scanning = _scanning.value

    private val handler = android.os.Handler()

    private var validator: Boolean = false

    //gatt callback pls
    private val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            val deviceAddress = gatt!!.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("escaneando", "Successfully connected to $deviceAddress")
                    // TODO: Store a reference to BluetoothGatt
                    val obdDevice = gatt.device
                    //get InputOutputStream
                    val gattService = gatt.services
                    validator = true
                    sipudo()

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
                    Log.d("bluele", "${device.name} ${device.address}")
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

    private val SCAN_PERIOD: Long = 1000

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    fun sipudo() {
        val device = leDeviceListAdapter.find { it.name == "OBDII" }
        Log.d("bluele", "DISPOSITIVO SIENDO LEIDO: ${device!!.name}")
        val bluetoothSocket =
            device.createInsecureRfcommSocketToServiceRecord(device.uuids[2].uuid)
        val inputStream = bluetoothSocket.inputStream
        val outputStream = bluetoothSocket.outputStream

        val obdDeviceConnection = ObdDeviceConnection(inputStream, outputStream)
        //TODO DECIRLE A NESTOR
        GlobalScope.launch {
            try {
                val response = obdDeviceConnection.run(VINCommand())
                Log.d("bluele", "LA RESPUESTA DEL OBD:::: $response")

                // Process the response or update UI as needed
                val rpm = obdDeviceConnection.run(RPMCommand())
                Log.d("bluele", "LA RESPUESTA DEL OBD:::: $rpm")
            } catch (e: Exception) {
                Log.e("bluele", "Error in coroutine: ${e.message}")
            }

        }
    }

    @SuppressLint("MissingPermission")
    fun scanLeDevice() {
        if (!_scanning.value) {

            bluetoothLeScanner.startScan(leScanCallback)
            Log.d("bluele", "escaneando")


            handler.postDelayed({
                _scanning.value = false
                viewModel.setIsLoading(false)
                bluetoothLeScanner.stopScan(leScanCallback)
                Log.d("bluele", "Stopped")
            }, SCAN_PERIOD)
        } else {
            _scanning.value = true
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }
}