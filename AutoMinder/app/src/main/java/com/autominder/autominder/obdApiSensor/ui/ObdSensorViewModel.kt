package com.autominder.autominder.obdApiSensor.ui

import android.app.Application
import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ObdSensorViewModel() : ViewModel() {

    val bluetoothDevice: MutableState<BluetoothDevice?> = mutableStateOf(null)
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setIsLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 200
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as Application
                ObdSensorViewModel()
            }
        }
    }
}
