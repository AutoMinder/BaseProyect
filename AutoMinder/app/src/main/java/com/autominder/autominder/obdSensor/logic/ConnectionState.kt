package com.autominder.autominder.obdSensor.logic

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.util.UUID

sealed class ConnectionState{
    class Connecting(val bluetoothDevice: BluetoothDevice): ConnectionState()
    class Connected(val socket: BluetoothSocket) : ConnectionState()
    class ConnectionFailed(val failureReason: String): ConnectionState()
    object Disconnected: ConnectionState()


}
