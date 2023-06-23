package com.autominder.autominder.ui.carinfo.ui


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.models_dummy.CarModel
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import com.autominder.autominder.ui.myCars.ui.MyCarsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarInfoViewModel(
    private val repository: MyCarsRepository
    //TODO(): Descomentar cuando se pueda implementar correctamente:
    // private val credentialsRepository: CredentialsRepository,
) : ViewModel() {


    private val _carInfoList = MutableStateFlow<CarModel>(
        CarModel(
            name = "",
            brand = "",
            year = 0,
            id = "",
            kilometers = 0,
            lastOilChange = "",
            nextMaintenances = "",
            kilometersDate = "",
            errorModel = null,
            hidden = false,
            lastCoolantDate = null,
            lastMayorTuning = null,
            lastMinorTuning = null,
            lastMaintenance = "",
            model = "",
            vin = ""
        )
    )
    val carInfoList: StateFlow<CarModel> = _carInfoList
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /*fun fetchCarMaintenanceInfoByCarId(carId: String) {
        viewModelScope.launch {
            try {
                setLoading(true)

                _carInfoList.value = repository.getCarById(carId)!!

                //TODO(): Descomentar cuando se haya implementado correctamente el paso y renderizacion del auto seleccionado
                //_carInfoList.value = credentialsRepository.getCarById(carId)!!

                Log.d("INFO", "Car info fetched: ${_carInfoList.value.name}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }
    }*/

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                CarInfoViewModel(app.myCarsRepository)

                //TODO(): Descomentar cuando se pueda implementar correctamente:
                //CarInfoViewModel((app).credentialsRepository)
            }
        }
    }
}