package com.autominder.autominder.obdApiSensor.logic

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
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import com.autominder.autominder.obdApiSensor.ui.ObdSensorViewModel
import com.github.eltonvs.obd.command.control.VINCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


class BluetoothConnections(
    bluetoothAdapter: BluetoothAdapter,
    bluetoothManager: BluetoothManager?,
    context: Context,
    private val viewModel: ObdSensorViewModel
) {
    //**************Variables declaration for later use**************
    private val handler = android.os.Handler()
    private var validator: Boolean = false
    val leDeviceListAdapter = mutableStateListOf<BluetoothDevice>()
    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    var scanning = false
    private val SCAN_PERIOD: Long = 100000


    //*****************************************************************//
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

    //**********************************************
    // gattCallback, used to get the services and characteristics of the bluetooth device
    // also to check if the device is successfully connected to the phone.
    // *********************************************//
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
                            gatt.readCharacteristic(
                                characteristic.service.getCharacteristic(
                                    characteristic.uuid
                                )
                            )
                            gatt.writeCharacteristic(
                                characteristic.service.getCharacteristic(
                                    characteristic.uuid
                                )
                            )
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

    //**********************************************
    // LeScanCallback, used to scan for bluetooth devices le, called in the startLeDevices
    // If a device named OBD2 is found, the connection is established
    // *********************************************//
    private val leScanCallback = object : android.bluetooth.le.ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult?) {

            result?.device?.let { device ->
                if (!leDeviceListAdapter.contains(device)) {

                    leDeviceListAdapter.add(device)
                    Log.d("bluele", "${device.name} ${device.address}")
                    if (device.name == "OBDII") {
                        Log.d("bluele", "conectando")

                        val gatt = device.connectGatt(context, true, gattCallback)
                        //gatt.connect()
                        gatt.discoverServices()

                        //val socket = gatt.device.createRfcommSocketToServiceRecord(UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb"))
                        //socket.connect()
                        //val obdDeviceConnection = ObdDeviceConnection(socket.inputStream, socket.outputStream)
                        //Log.d("bluele", "conectado AL SOCKET")
                        //device.connectGatt(context, true, gattCallback)

                    }

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun sendVinCommandToCar(uuidSended: String, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val bluetoothDevice = bluetoothAdapter.getRemoteDevice("00:10:CC:4F:36:03")
            Log.d("bluele", "Device: ${bluetoothDevice.name} ${bluetoothDevice.address}")
            val uuid = UUID.fromString(uuidSended)
            val bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid)

            try {
                bluetoothSocket.connect()

                // Socket connection successful, proceed with communication
                val inputStream = bluetoothSocket.inputStream
                val outputStream = bluetoothSocket.outputStream

                delay(1000)
                sendCommand("ATZ\r", outputStream, inputStream)
                delay(1000)
                sendCommand("ATSP0\r", outputStream, inputStream)
                delay(1000)
                sendCommand("ATL0\r", outputStream, inputStream)
                delay(1000)
                sendCommand("ATI\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0902\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0902\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0902\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0902\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0902\r", outputStream, inputStream)
                delay(1000)

                delay(1000)
                val response = sendCommand("0902\r", outputStream, inputStream)
                Log.d("bluele", "Response inside fun: ${response}")
                val vin = translateVin(response)
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "VIN: $vin", Toast.LENGTH_LONG).show()
                }
                Log.d("bluele", "VIN: $vin")


                bluetoothSocket.close()

            } catch (e: IOException) {
                Log.e("bluele", "Socket connection failed: ${e.message}")
            }
        }
    }

    private fun translateVin(response: String): String {
        val vinSection = response.substringBefore(">").trim()
        val hexValues = vinSection.split(" ")

        val vin = StringBuilder()
        var includePrefix = true
        for (hex in hexValues) {
            if (hex.startsWith("0")) {
                includePrefix = false // Set includePrefix to false if a hex value starts with 0
                continue
            }
            val asciiValue = hex.toIntOrNull(16)?.toChar()
            if (asciiValue != null) {
                if (includePrefix) {
                    vin.append(asciiValue) // Append ASCII character to VIN
                } else {
                    includePrefix = true // Reset includePrefix for subsequent characters
                }
            }
        }

        return vin.toString()
    }

    @SuppressLint("MissingPermission")
    fun sendTemperatureCommandToCar(uuidSended: String, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val bluetoothDevice = bluetoothAdapter.getRemoteDevice("00:10:CC:4F:36:03")
            Log.d("bluele", "Device: ${bluetoothDevice.name} ${bluetoothDevice.address}")
            val uuid = UUID.fromString(uuidSended)
            val bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid)

            try {
                bluetoothSocket.connect()

                // Socket connection successful, proceed with communication
                val inputStream = bluetoothSocket.inputStream
                val outputStream = bluetoothSocket.outputStream

                delay(1000)
                sendCommand("ATZ\r", outputStream, inputStream)
                delay(1000)
                sendCommand("ATSP0\r", outputStream, inputStream)
                delay(1000)
                sendCommand("ATL0\r", outputStream, inputStream)
                delay(1000)
                sendCommand("ATI\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0105\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0105\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0105\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0105\r", outputStream, inputStream)
                delay(1000)
                sendCommand("0105\r", outputStream, inputStream)
                delay(1000)
                val response = sendCommand("0105\r", outputStream, inputStream)
                Toast.makeText(context, "Tu VIN es $response", Toast.LENGTH_LONG).show()

                delay(1000)
                bluetoothSocket.close()

            } catch (e: IOException) {
                Log.e("bluele", "Socket connection failed: ${e.message}")
            }
        }
    }

    private fun sendCommand(
        command: String,
        outputStream: OutputStream,
        inputStream: InputStream
    ): String {
        outputStream.write((command + "\r").toByteArray())
        outputStream.flush()

        val buffer = ByteArray(2056)
        val bytesRead = inputStream.read(buffer)

        val response = String(buffer, 0, bytesRead ?: 0).trim()

        Log.d("bluele", "RESPONSE: $response")

        return response
    }

    //**********************************************
    // Scanning function, find bluetooth le devices
    // *********************************************//
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

fun convertHexToRpm(hexValue: String): Int {
    try {
        val rpmHex =
            hexValue.substring(4) // Extract the relevant part of the response (exclude the mode and PID)
        val rpmDec = Integer.parseInt(rpmHex, 16)
        return rpmDec / 4 // Divide by 4 to get the actual RPM value
    } catch (e: NumberFormatException) {
        // Handle invalid input or empty string
        e.printStackTrace()
        return -1 // or any other appropriate default value
    }
}
