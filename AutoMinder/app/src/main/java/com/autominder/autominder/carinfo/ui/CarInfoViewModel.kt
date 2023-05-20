package com.autominder.autominder.carinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.carinfo.data.CarMaintenanceData
import com.autominder.autominder.carinfo.data.CarMaintenanceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarInfoViewModel(
    private val repository: CarMaintenanceRepository,
) : ViewModel() {

    private val _carInfoList = MutableStateFlow<List<CarMaintenanceData>>(emptyList())
    val carInfoList: StateFlow<List<CarMaintenanceData>> = _carInfoList
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchCarMaintenanceInfoByCarId(carId: Int) {
        viewModelScope.launch {
            try {
                setLoading(true)
                delay(100)
                _carInfoList.value = repository.getCarMaintenanceByCarId(carId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                CarInfoViewModel(app.CarMaintenanceRepository)
            }
        }
    }
}