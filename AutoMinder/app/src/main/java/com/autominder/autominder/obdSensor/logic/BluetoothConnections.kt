package com.autominder.autominder.obdSensor.logic

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.autominder.autominder.obdSensor.ui.ObdSensorViewModel
import com.github.eltonvs.obd.command.control.VINCommand
import com.github.eltonvs.obd.command.engine.RPMCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import java.lang.reflect.Method
import java.util.UUID

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
    var scanning = false

    private fun BluetoothGatt.printGattTable() {
        if (services.isEmpty()) {
            Log.i(
                "printGattTable",
                "No service and characteristic available, call discoverServices() first?"
            )
            return
        }
        services.forEach { service ->
            val characteristicsTable = service.characteristics.joinToString(
                separator = "\n|--",
                prefix = "|--"
            ) { it.uuid.toString() }
            Log.i(
                "printGattTable",
                "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
            )
        }
    }

    private val handler = android.os.Handler()

    private var validator: Boolean = false

    //gatt callback pls
    private val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            val deviceAddress = gatt!!.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("bluele", "Successfully connected to $deviceAddress")
                    // TODO: Store a reference to BluetoothGatt
                    val obdDevice = gatt.device
                    //get InputOutputStream
                    val gattService = gatt.services
                    gatt.discoverServices()
                    validator = true
                    //sipudo()

                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.w("bluele", "Successfully disconnected from $deviceAddress")
                    gatt.close()
                }
            } else {
                Log.w(
                    "bluele",
                    "Error $status encountered for $deviceAddress! Disconnecting..."
                )
                gatt.close()
            }

        }

        @SuppressLint("MissingPermission")
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val services = gatt?.services
                if (services != null) {
                    for (service in services) {
                        Log.d("bluele", "Service: ${service.uuid}")
                        for (characteristic in service.characteristics) {
                            Log.d("bluele", "Characteristic: ${characteristic.uuid}")
                            gatt.readCharacteristic(characteristic.service.getCharacteristic(characteristic.uuid))
                        }


                    }
                }
            } else {
                Log.d("bluele", "No services found")
            }
        }

        @SuppressLint("MissingPermission")
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, value, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(
                    "bluele", "onCharacteristicRead: ${characteristic.uuid} -> ${
                        value.toString(
                            Charsets.UTF_8
                        )
                    }"
                )
                gatt.printGattTable()
                val socket = gatt.device.createRfcommSocketToServiceRecord(characteristic.uuid)
                socket.connect()
                val obdConnection = ObdDeviceConnection(socket.inputStream, socket.outputStream)
                GlobalScope.launch {
                    while (true) {
                        obdConnection.run(RPMCommand())
                        obdConnection.run(VINCommand())
                        Log.d("bluele", "RPM: ${obdConnection.run(RPMCommand()).formattedValue}")
                        delay(1000)
                    }
                }

            }
        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorWrite(gatt, descriptor, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(
                    "bluele",
                    "onDescriptorWrite: ${descriptor?.characteristic?.uuid} -> ${descriptor?.value?.contentToString()}"
                )
                gatt?.printGattTable()

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
                        Log.d("bluele", "conectando")

                        //val gatt = device.connectGatt(context, true, gattCallback)
                        //gatt.connect()
                        device.connectGatt(context, true, gattCallback)

                    }

                }
            }
        }
    }

    private val SCAN_PERIOD: Long = 100000

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    fun sipudo() {
        val device = leDeviceListAdapter.find { it.name == "OBDII" }

        Log.d("bluele", "DISPOSITIVO SIENDO LEIDO: ${device!!.name}")
        val uuid = UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb")
        val bluetoothSocket =
            device.createInsecureRfcommSocketToServiceRecord(uuid)



        bluetoothSocket.use { socket ->
            socket.connect()
            val inputStream = socket.inputStream
            val outputStream = socket.outputStream
            val obdDeviceConnection = ObdDeviceConnection(inputStream, outputStream)
            GlobalScope.launch {
                try {
                    Log.d("bluele", "ENTRANDO AL TRY")
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
        //bluetoothSocket.connect()


        /*if (inputStream != null && outputStream != null) {
            // Call the necessary write operations on the outputStream
            val obdDeviceConnection = ObdDeviceConnection(inputStream, outputStream)
            GlobalScope.launch {
            try {
                Log.d("bluele", "ENTRANDO AL TRY")
                val response = obdDeviceConnection.run(VINCommand())
                Log.d("bluele", "LA RESPUESTA DEL OBD:::: $response")

                // Process the response or update UI as needed
                val rpm = obdDeviceConnection.run(RPMCommand())
                Log.d("bluele", "LA RESPUESTA DEL OBD:::: $rpm")
            } catch (e: Exception) {
                Log.e("bluele", "Error in coroutine: ${e.message}")
            }

        }



    }*/
    }

    @SuppressLint("MissingPermission")
    fun scanLeDevice() {
        if (!scanning) {

            bluetoothLeScanner.startScan(leScanCallback)
            Log.d("bluele", "escaneando")


            handler.postDelayed({
                scanning = false
                viewModel.setIsLoading(false)
                bluetoothLeScanner.stopScan(leScanCallback)
                Log.d("bluele", "Stopped")
            }, SCAN_PERIOD)
        } else {
            scanning = true
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }
}