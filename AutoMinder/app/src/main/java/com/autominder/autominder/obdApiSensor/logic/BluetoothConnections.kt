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

    /**
     * Callback invoked when the connection state of the Bluetooth GATT changes.
     *
     * @param gatt The BluetoothGatt object associated with the connection.
     * @param status The status of the GATT connection operation.
     * @param newState The new connection state. Can be one of [BluetoothProfile.STATE_CONNECTED]
     *                 or [BluetoothProfile.STATE_DISCONNECTED].
     */

    private val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            val deviceAddress = gatt!!.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("bluele", "Successfully connected to $deviceAddress")
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

    /**
     * Callback invoked when a BLE scan result is found.
     *
     * @param callbackType The callback type.
     * @param result The scan result containing the Bluetooth device information.
     */

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
                        gatt.discoverServices()

                    }

                }
            }
        }
    }

    /**
     * Sends a VIN command to the car via Bluetooth and retrieves the VIN number.
     *
     * @param uuidSended The UUID string used for Bluetooth communication.
     * @param context The context of the application.
     */
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

    /**
     * Sends a temperature command to the car via Bluetooth and retrieves the coolant temperature.
     *
     * @param uuidSended The UUID string used for Bluetooth communication.
     * @param context The context of the application.
     */
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
                val temperature = translateTemperature(response)

                Toast.makeText(
                    context,
                    "La temperatura del refrigerante es: $temperature",
                    Toast.LENGTH_LONG
                ).show()

                delay(1000)
                bluetoothSocket.close()

            } catch (e: IOException) {
                Log.e("bluele", "Socket connection failed: ${e.message}")
            }
        }
    }

    private fun translateTemperature(response: String): Int {
        val hexValue =
            response.replace(" ", "").substring(6, 8) // Remove spaces and extract the hex value
        val decimalValue = hexValue.toInt(16) // Convert the hex value to decimal
        return decimalValue - 40
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

    /**
     * Scans for nearby Bluetooth Low Energy (BLE) devices.
     * If scanning is not already in progress, starts the scan and sets a timeout.
     * If scanning is already in progress, stops the scan.
     */
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
