package com.autominder.autominder.addcar.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.addcar.data.AddCarRepository
import com.autominder.autominder.addcar.data.CarModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AddCarViewModel(
    private val repository: AddCarRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _profileCarName = MutableStateFlow("")
    val profileCarName: Flow<String> = _profileCarName

    //TODO(): Se deben recibir las marcas del API

    private val _carBrand = MutableStateFlow("")
    val carBrand: Flow<String> = _carBrand
    //TODO(): Se deben recibir los modelos del API

    private val _carModel = MutableStateFlow("")
    val carModel: Flow<String> = _carModel

    private val _carYear = MutableStateFlow("")
    val carYear: Flow<String> = _carYear

    private val _carKilometers = MutableStateFlow("")
    val carKilometers: Flow<String> = _carKilometers

    private val _carLastOilChange = MutableStateFlow("")
    val carLastOilChange: Flow<String> = _carLastOilChange

    private val _carLastMaintenance = MutableStateFlow("")
    val carLastMaintenance: Flow<String> = _carLastMaintenance

    private val _newCar = MutableStateFlow(CarModel(
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    ))
    val newCar: Flow<CarModel> = _newCar

    private val _addCarEnable = MutableStateFlow(false)
    val addCarEnable: Flow<Boolean> = _addCarEnable

    val carBrandsList = repository.getCarBrands()
    val carModelsList = repository.getCarModels()

    fun onAddCarChange(
        profileCarName: String,
        carBrand: String,
        carModel: String,
        carYear: String,
        carKilometers: String,
        carLastOilChange: String,
        carLastMaintenance: String
    ) {
        _profileCarName.value = profileCarName
        _carBrand.value = carBrand
        _carModel.value = carModel
        _carYear.value = carYear
        _carKilometers.value = carKilometers
        _carLastOilChange.value = carLastOilChange
        _carLastMaintenance.value = carLastMaintenance
        _newCar.value =
            CarModel(profileCarName, carBrand, carModel, carYear, carKilometers, carLastOilChange, carLastMaintenance)

        _addCarEnable.value = validFields(profileCarName, carBrand, carModel, carYear, carKilometers, carLastOilChange, carLastMaintenance)
    }

    fun addCar(newCar: CarModel) {

        repository.addCar(newCar)
        Log.d("APP TAG", getCars().toString())
    }

    private fun getCars() = repository.getCars()

    private fun validFields(
        profileCarName: String,
        carBrand: String,
        carModel: String,
        carYear: String,
        carKilometers: String,
        carLastOilChange: String,
        carLastMaintenance: String
    ): Boolean =
        profileCarName != "" && carBrand !="" && carModel !="" && carYear != "" && carKilometers != "" && carLastOilChange != "" && carLastMaintenance != ""

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