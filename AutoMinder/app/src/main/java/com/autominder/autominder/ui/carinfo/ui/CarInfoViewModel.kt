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
    private val _isChanged = MutableStateFlow<Boolean>(false)
    val isChanged: StateFlow<Boolean> = _isChanged


    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setCarUpdatedToName(name: String) {
        _carName.value = name
    }

    fun setCarInfo(carName: String) {
        _carName.value = carName
    }

    fun setUpdatedCarName(updatedCarName: String) {
        _updatedCarName.value = updatedCarName
    }

    fun setChanged(changed: Boolean) {
        _isChanged.value = changed
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