package com.autominder.autominder.addcar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddCarViewModel : ViewModel() {

    private val _profileCarName = MutableLiveData<String>()
    val profileCarName: LiveData<String> = _profileCarName

    private val _carBrand = MutableLiveData<String>()
    val carBrand: LiveData<String> = _carBrand

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

    fun onAddCarChange(
        profileCarName: String,
        carYear: String,
        carKilometers: String,
        carLastOilChange: String,
        carLastMaintenance: String
    ) {
        _profileCarName.value = profileCarName
        _carYear.value = carYear
        _carKilometers.value = carKilometers
        _carLastOilChange.value = carLastOilChange
        _carLastMaintenance.value = carLastMaintenance
    }
}