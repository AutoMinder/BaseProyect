package com.autominder.autominder.carinfo.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.carinfo.data.CarMaintenanceData
import com.autominder.autominder.carinfo.data.CarMaintenanceRepository
import com.autominder.autominder.models.CarModel
import com.autominder.autominder.myCars.data.MyCarsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CarInfoViewModel(
    private val repository: MyCarsRepository,
) : ViewModel() {

    private val _carInfoList = MutableStateFlow<CarModel>(
        CarModel(
            name = "",
            brand = "",
            year = 0,
            id = "",
            kilometers = 0,
            lastOilChange = Date(),
            nextMaintenances = Date(),
            kilometersDate = Date(),
            errorModel = null,
            hidden = false,
            lastCoolantDate = null,
            lastMayorTuning = null,
            lastMinorTuning = null,
            model = ""
        )
    )
    val carInfoList: StateFlow<CarModel> = _carInfoList
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchCarMaintenanceInfoByCarId(carId: String) {
        viewModelScope.launch {
            try {
                setLoading(true)
                delay(100)
                _carInfoList.value = repository.getCarById(carId)!!
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
                CarInfoViewModel(app.myCarsRepository)
            }
        }
    }
}