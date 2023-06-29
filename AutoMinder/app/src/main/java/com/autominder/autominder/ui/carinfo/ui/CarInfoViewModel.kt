package com.autominder.autominder.ui.carinfo.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.network.ApiResponse
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.ui.addcar.ui.CreateUiStatus
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarInfoViewModel(
    private val repository: MyCarsRepository,
    private val credentialsRepository: CredentialsRepository
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

    private val _lastMaintenance = MutableStateFlow<String>("")
    val lastMaintenance: StateFlow<String> = _lastMaintenance
    private val _lastMaintenanceUpdated = MutableStateFlow<String>(_lastMaintenance.value)
    val lastMaintenanceUpdated: StateFlow<String> = _lastMaintenanceUpdated
    private val _isMaintenanceChanged = MutableStateFlow<Boolean>(false)
    val isMaintenanceChanged: StateFlow<Boolean> = _isMaintenanceChanged

    private val _status = MutableLiveData<CreateUiStatus>(CreateUiStatus.Resume)
    val status: MutableLiveData<CreateUiStatus>
        get() = _status


    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }


    fun setNameInfo(carName: String) {
        _carName.value = carName
    }

    fun setMileageInfo(mileage: String) {
        _mileage.value = mileage
    }

    fun setLastMaintenanceInfo(lastMaintenance: String) {
        _lastMaintenance.value = lastMaintenance
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

    fun setChanged(changed: Boolean) {
        _isChangedName.value = changed
    }

    fun setChangedMileage(changed: Boolean) {
        _isChangedMileage.value = changed
    }

    fun setChangedLastMaintenance(changed: Boolean) {
        _isMaintenanceChanged.value = changed
    }

    fun setChangedLastOilChange(changed: Boolean) {
        _isChangedLastOilChange.value = changed
    }

    fun setChangedLastCoolantChange(changed: Boolean) {
        _isChangedLastCoolantChange.value = changed
    }

    suspend fun sendUpdatesToDatabase(
        carId: String,
        carName: String,
        kilometers: String,
        lastMaintenance: String,
        mayorTuning: String,
        minorTuning: String,
        lastOilChange: String,
        lastCoolantChange: String,
        brand: String,
        model: String,
        year: String,

    ) {
        viewModelScope.launch {
            _status.postValue(
                when (
                    val response = credentialsRepository.updateCar(
                        carId,
                        carName,
                        kilometers,
                        lastMaintenance,
                        mayorTuning,
                        minorTuning,
                        lastOilChange,
                        lastCoolantChange,
                        brand,
                        model,
                        year
                    )
                ) {
                    is ApiResponse.Success -> CreateUiStatus.Success
                    is ApiResponse.Error -> CreateUiStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> CreateUiStatus.ErrorWithMessage(response.message)
                }
            )
        }
    }

    suspend fun hideCar(mongoId: String){
        viewModelScope.launch {
            _status.postValue(
                when (
                    val response = credentialsRepository.hideCar(mongoId)
                ) {
                    is ApiResponse.Success -> CreateUiStatus.Success
                    is ApiResponse.Error -> CreateUiStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> CreateUiStatus.ErrorWithMessage(response.message)
                }
            )
        }
    }
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                CarInfoViewModel(app.myCarsRepository, app.credentialsRepository)
            }
        }
    }
}