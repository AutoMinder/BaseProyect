package com.autominder.autominder.ui.addcar.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.ui.addcar.data.AddCarRepository
import com.autominder.autominder.data.network.ApiResponse
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeParseException

class AddCarViewModel(
    private val repository: AddCarRepository,
    savedStateHandle: SavedStateHandle,
    private val credentialsRepository: CredentialsRepository
) : ViewModel() {

    private val _carId = MutableStateFlow("")
    val carId: StateFlow<String> = _carId

    private val _profileCarName = MutableStateFlow("")
    val profileCarName: StateFlow<String> = _profileCarName

    private val _carBrand = MutableStateFlow("")
    val carBrand: StateFlow<String> = _carBrand

    private val _carModel = MutableStateFlow("")
    val carModel: StateFlow<String> = _carModel

    private val _carYear = MutableStateFlow("")
    val carYear: StateFlow<String> = _carYear

    private val _carKilometers = MutableStateFlow("")
    val carKilometers: StateFlow<String> = _carKilometers

    private val _carLastMaintenance = MutableStateFlow("")
    val carLastMaintenance: StateFlow<String> = _carLastMaintenance

    private val _carLastOilChange = MutableStateFlow("")
    val carLastOilChange: StateFlow<String> = _carLastOilChange

    private val _carLastCoolantDate = MutableStateFlow("")
    val carLastCoolantDate: StateFlow<String> = _carLastCoolantDate

    private val _addCarEnable = MutableStateFlow(false)
    val addCarEnable: StateFlow<Boolean> = _addCarEnable

    private val _carBrandsList = MutableStateFlow<List<String>>(emptyList())
    val carBrandsList: StateFlow<List<String>> = _carBrandsList

    private val _carModelsList = MutableStateFlow<List<String>>(emptyList())
    val carModelsList: StateFlow<List<String>> = _carModelsList

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _status = MutableLiveData<CreateUiStatus>(CreateUiStatus.Resume)
    val status: MutableLiveData<CreateUiStatus>
        get() = _status


    init {
        fetchBrands()
    }

    suspend fun addCarToDatabase(
        name: String,
        model: String,
        brand: String,
        year: String,
        kilometers: String,
        kilometersDate: String?,
        lastMaintenance: String,
        lastOilChange: String,
        lastCoolantChange: String,
        mayorTuning: String?,
        minorTuning: String?,
        errorRecord: List<String>?,
    ) {
        viewModelScope.launch {
            _status.postValue(
                when (
                    val response = credentialsRepository.addCar(
                        name,
                        model,
                        brand,
                        year,
                        kilometers,
                        kilometersDate,
                        lastMaintenance,
                        lastOilChange,
                        lastCoolantChange,
                        mayorTuning,
                        minorTuning,
                        errorRecord
                    )
                ) {
                    is ApiResponse.Success -> CreateUiStatus.Success
                    is ApiResponse.Error -> CreateUiStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> CreateUiStatus.ErrorWithMessage(response.message)
                }
            )
        }
    }


    fun fetchCarModels(brand: String){
        viewModelScope.launch {
            try {
                setLoading(true)

                fun models(brand: String): MutableList<String>{
                    when(brand){
                        "Toyota" -> return mutableListOf("Yaris", "Corolla", "Camry", "RAV4", "Prius", "Supra")
                        "Nissan" -> return mutableListOf("Versa", "Sentra", "Altima", "Maxima", "Rogue", "Pathfinder")
                        "Honda" -> return mutableListOf("Civic", "Accord", "CR-V", "Pilot", "Odyssey", "Fit")
                        "Hyundai" -> return mutableListOf("Accent", "Elantra", "Sonata", "Tucson", "Santa Fe", "Palisade")
                        "Kia" -> return mutableListOf("Rio", "Forte", "Optima", "Soul", "Seltos", "Sorento")
                        "Mazda" -> return mutableListOf("Mazda3", "Mazda6", "CX-3", "CX-30", "CX-5", "CX-9")
                        "Mitsubishi" -> return mutableListOf("Mirage", "Attrage", "Lancer", "Outlander", "Montero", "Eclipse")
                        else -> return mutableListOf("Lamborghini", "Ferrari", "Porsche", "Audi", "BMW", "Mercedes-Benz")
                    }
                }

                _carModelsList.value = models(brand)

                Log.d("fetchCarModels", "fetchCarModels: ${_carModelsList.value}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }

    }

    private fun fetchBrands() {
        viewModelScope.launch {
            try {
                setLoading(true)
                _carBrandsList.value = repository.getCarBrands()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onAddCarChange(
        profileCarName: String,
        carBrand: String,
        carModel: String,
        carYear: String,
        carKilometers: String,
        carLastMaintenance: String,
        carLastOilChange: String,
        carLastCoolantDate: String
    ) {

        _profileCarName.value = profileCarName
        _carBrand.value = carBrand
        _carModel.value = carModel
        _carYear.value = carYear
        _carKilometers.value = carKilometers
        _carLastOilChange.value = carLastOilChange
        _carLastMaintenance.value = carLastMaintenance
        _carLastCoolantDate.value = carLastCoolantDate


        _addCarEnable.value = validFields(
            profileCarName,
            carBrand,
            carModel,
            carYear,
            carKilometers,
            carLastOilChange,
            carLastMaintenance,
            carLastCoolantDate
        )
    }

    fun addCar(
        profileCarName: String,
        carBrand: String,
        carModel: String,
        carYear: String,
        carKilometers: String,
        carLastMaintenance: String,
        carLastOilChange: String,
        carLastCoolantDate: String
    ) {

        try {

            val carYearParsed = carYear.toInt()
            val carKilometersParsed = carKilometers.toInt()
            //repository.addCar(newCar)
            //addCarToDatabase(newCar)
            Log.d("APP TAG", getCars().toString())

        } catch (e: DateTimeParseException) {
            println("Error al parsear la fecha: ${e.message}")
        }
    }

    private fun getCars() = repository.getCars()

    private fun validFields(
        profileCarName: String,
        carBrand: String,
        carModel: String,
        carYear: String,
        carKilometers: String,
        carLastOilChange: String,
        carLastMaintenance: String,
        carLastCoolantDate: String
    ): Boolean =
        profileCarName != "" && carBrand != "" && carModel != "" && carYear != "" && carKilometers != "" && carLastOilChange != "" && carLastMaintenance != "" && carLastCoolantDate != ""

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                AddCarViewModel(
                    app.addCarRepository,
                    createSavedStateHandle(),
                    app.credentialsRepository
                )
            }
        }
    }
}