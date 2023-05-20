package com.autominder.autominder.addcar.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.addcar.data.AddCarRepository
import com.autominder.autominder.addcar.data.CarModel

class AddCarViewModel(
    private val repository: AddCarRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _profileCarName = MutableLiveData<String>()
    val profileCarName: LiveData<String> = _profileCarName

    //TODO(): Se deben recibir las marcas del API

    private val _carBrand = MutableLiveData<String>()
    val carBrand: LiveData<String> = _carBrand
    //TODO(): Se deben recibir los modelos del API

    private val _carModel = MutableLiveData<String>()
    val carModel: LiveData<String> = _carModel

    private val _carYear = MutableLiveData<String>()
    val carYear: LiveData<String> = _carYear

    private val _carKilometers = MutableLiveData<String>()
    val carKilometers: LiveData<String> = _carKilometers

    private val _carLastOilChange = MutableLiveData<String>()
    val carLastOilChange: LiveData<String> = _carLastOilChange

    private val _carLastMaintenance = MutableLiveData<String>()
    val carLastMaintenance: LiveData<String> = _carLastMaintenance

    private val _newCar = MutableLiveData<CarModel>()
    val newCar: LiveData<CarModel> = _newCar

    private val _addCarEnable = MutableLiveData<Boolean>()
    val addCarEnable: LiveData<Boolean> = _addCarEnable

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