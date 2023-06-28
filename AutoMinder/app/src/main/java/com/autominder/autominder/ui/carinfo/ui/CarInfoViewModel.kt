package com.autominder.autominder.ui.carinfo.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CarInfoViewModel(
    private val repository: MyCarsRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _carName = MutableStateFlow<String>("")
    val carName: StateFlow<String> = _carName
    private val _updatedCarName = MutableStateFlow<String>(_carName.value)
    val updatedCarName: StateFlow<String> = _updatedCarName
    private val _isChangedName = MutableStateFlow<Boolean>(false)
    val isChangedName: StateFlow<Boolean> = _isChangedName
    private val _isChangedMileage = MutableStateFlow<Boolean>(false)
    val isChangedMileage: StateFlow<Boolean> = _isChangedMileage
    private val _isChangedLastOilChange = MutableStateFlow<Boolean>(false)
    val isChangedLastOilChange: StateFlow<Boolean> = _isChangedLastOilChange
    private val _isChangedLastCoolantChange = MutableStateFlow<Boolean>(false)
    val isChangedLastCoolantChange: StateFlow<Boolean> = _isChangedLastCoolantChange

    private val _mileage = MutableStateFlow<String>("")
    val mileage: StateFlow<String> = _mileage
    private val _mileageUpdated = MutableStateFlow<String>(_mileage.value)
    val mileageUpdated: StateFlow<String> = _mileageUpdated

    private val _lastOilChange = MutableStateFlow<String>("")
    val lastOilChange: StateFlow<String> = _lastOilChange
    private val _lastOilChangeUpdated = MutableStateFlow<String>(_lastOilChange.value)
    val lastOilChangeUpdated: StateFlow<String> = _lastOilChangeUpdated
    private val _lastCoolantChange = MutableStateFlow<String>("")
    val lastCoolantChange: StateFlow<String> = _lastCoolantChange
    private val _lastCoolantChangeUpdated = MutableStateFlow<String>(_lastCoolantChange.value)
    val lastCoolantChangeUpdated: StateFlow<String> = _lastCoolantChangeUpdated



    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setCarUpdatedToName(name: String) {
        _carName.value = name
    }
    fun setMileageUpdated(mileage: String) {
        _mileage.value = mileage
    }

    fun setNameInfo(carName: String) {
        _carName.value = carName
    }
    fun setMileageInfo(mileage: String) {
        _mileage.value = mileage
    }

    fun setLastOilChangeInfo(lastOilChange: String) {
        _lastOilChange.value = lastOilChange
    }

    fun setLastCoolantChangeInfo(lastCoolantChange: String) {
        _lastCoolantChange.value = lastCoolantChange
    }


    fun setUpdatedCarName(updatedCarName: String) {
        _updatedCarName.value = updatedCarName
    }
    fun setUpdatedMileage(updatedMileage: String) {
        _mileageUpdated.value = updatedMileage
    }



    fun setLastOilChangeUpdated(lastOilChange: String) {
        _lastOilChangeUpdated.value = lastOilChange
    }

    fun setLastCoolantChangeUpdated(lastCoolantChange: String) {
        _lastCoolantChangeUpdated.value = lastCoolantChange
    }

    fun setChanged(changed: Boolean) {
        _isChangedName.value = changed
    }
    fun setChangedMileage(changed: Boolean) {
        _isChangedMileage.value = changed
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                CarInfoViewModel(app.myCarsRepository)
            }
        }
    }
}