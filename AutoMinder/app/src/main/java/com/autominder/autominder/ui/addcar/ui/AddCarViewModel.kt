package com.autominder.autominder.ui.addcar.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.ui.addcar.data.AddCarRepository
import com.autominder.autominder.data.models_dummy.CarModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.format.DateTimeParseException

class AddCarViewModel(
    private val repository: AddCarRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    //TODO() : Autogenerar id
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


    init {
        fetchCarModels()
        fetchBrands()
    }

    //TODO(): Clasificar por modelos
    private fun fetchCarModels() {
        viewModelScope.launch {
            try {
                setLoading(true)
                _carModelsList.value = repository.getCarModels()
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

        try{

            val carYearParsed = carYear.toInt()
            val carKilometersParsed = carKilometers.toInt()

            val newCar =
                CarModel(
                    "",
                    "",
                    profileCarName,
                    carBrand,
                    carModel,
                    carYearParsed,
                    carKilometersParsed,
                    null,
                    carLastMaintenance,
                    null,
                    carLastOilChange,
                    carLastCoolantDate,
                    null,
                    null,
                    false,
                    mutableListOf("")
                )

            repository.addCar(newCar)

            Log.d("APP TAG", getCars().toString())

        } catch (e: DateTimeParseException){
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
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                return AddCarViewModel(
                    (application as AutoMinderApplication).addCarRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
}