package com.autominder.autominder.obdSensor.ui

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ObdSensorViewModel() : ViewModel() {
    private val _bluetoothEnabled = MutableStateFlow(false)
    val bluetoothEnabled: StateFlow<Boolean> = _bluetoothEnabled

    private val _discoveredDevices = MutableStateFlow(emptyList<BluetoothDevice>())
    val discoveredDevices: StateFlow<List<BluetoothDevice>> = _discoveredDevices

    fun verifyBluetoothEnabled(context: Context) {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        val bluetoothAdapter = bluetoothManager?.adapter
        _bluetoothEnabled.value = bluetoothAdapter?.isEnabled ?: false
    }

    fun startBluetoothDiscovery(context: Context) {
        val bluetoothAdapter =
            (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            viewModelScope.launch {
                bluetoothAdapter.startDiscovery()
                _discoveredDevices.value = bluetoothAdapter.bondedDevices.toList()
            }
        }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as Application
                ObdSensorViewModel()
            }
        }
    }
}
