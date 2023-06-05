package com.autominder.autominder.addcar.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.addcar.data.AddCarRepository
import com.autominder.autominder.models.CarModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class AddCarViewModel(
    private val repository: AddCarRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {



    private val _carId = MutableStateFlow("")
    val carId: StateFlow<String> = _carId

    private val _profileCarName = MutableStateFlow("")
    val profileCarName: StateFlow<String> = _profileCarName

    private val _carBrand = MutableStateFlow("")
    val carBrand: StateFlow<String> = _carBrand

    private val _carModel = MutableStateFlow("")
    val carModel: StateFlow<String> = _carModel

    private val _carYear = MutableStateFlow(0)
    val carYear: StateFlow<Int> = _carYear

    private val _carKilometers = MutableStateFlow(0)
    val carKilometers: StateFlow<Int> = _carKilometers

    private val _carKilometersDate = MutableStateFlow(Date(1/1/2021))
    val carKilometersDate: StateFlow<Date> = _carKilometersDate

    private val _carLastMaintenance = MutableStateFlow(Date(1/1/2021))
    val carLastMaintenance: StateFlow<Date> = _carLastMaintenance

    private val _carNextMaintenance = MutableStateFlow(Date(1/1/2021))
    val carNextMaintenance: StateFlow<Date> = _carNextMaintenance

    private val _carLastOilChange = MutableStateFlow(Date(1/1/2021))
    val carLastOilChange: StateFlow<Date> = _carLastOilChange

    private val _carLastCoolantDate = MutableStateFlow(Date(1/1/2021))
    val carLastCoolantDate: StateFlow<Date> = _carLastCoolantDate

    private val _carLastMayorTuning = MutableStateFlow(Date(1/1/2021))
    val carLastMayorTuning: StateFlow<Date> = _carLastMayorTuning

    private val _carLastMinorTuning = MutableStateFlow(Date(1/1/2021))
    val carLastMinorTuning: StateFlow<Date> = _carLastMinorTuning

    private val _carHidden = MutableStateFlow(false)
    val carHidden: StateFlow<Boolean> = _carHidden

    private val _carErrorModel = MutableStateFlow(mutableListOf<String>())
    val carErrorModel: StateFlow<MutableList<String>> = _carErrorModel

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val defaultDate = dateFormat.parse("01/01/2021") as Date //????

    private val _newCar = MutableStateFlow(
        CarModel(
            "",
            "",
            "",
            "",
            0,
            0,
            defaultDate,
            defaultDate,
            defaultDate,
            defaultDate,
            defaultDate,
            defaultDate,
            defaultDate,
            false,
            mutableListOf()
        )
    )
    val newCar: StateFlow<CarModel> = _newCar

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

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onAddCarChange(
        carId:String,
        profileCarName: String,
        carBrand: String,
        carModel: String,
        carYear: Int,
        carKilometers: Int,
        carKilometersDate: Date,
        carLastMaintenance: Date,
        carNextMaintenance: Date,
        carLastOilChange: Date,
        carLastCoolantDate: Date,
        carLastMayorTuning: Date,
        carLastMinorTuning: Date,
        carHidden: Boolean,
        carErrorModel: MutableList<String>
    ) {
        _profileCarName.value = profileCarName
        _carBrand.value = carBrand
        _carModel.value = carModel
        _carYear.value = carYear
        _carKilometers.value = carKilometers
        _carLastOilChange.value = carLastOilChange
        _carLastMaintenance.value = carLastMaintenance
        _newCar.value =
            CarModel(
                carId,
                profileCarName,
                carBrand,
                carModel,
                carYear,
                carKilometers,
                carKilometersDate,
                carLastMaintenance,
                carNextMaintenance,
                carLastOilChange,
                carLastCoolantDate,
                carLastMayorTuning,
                carLastMinorTuning,
                carHidden,
                carErrorModel

            )

        _addCarEnable.value = validFields(
            profileCarName,
            carBrand,
            carModel,
            carYear,
            carKilometers,
            carLastOilChange,
            carLastMaintenance
        )
    }

    fun addCar(newCar: CarModel) {

        repository.addCar(newCar)
        repository.getCars()
        Log.d("APP TAG", getCars().toString())
    }

    private fun getCars() = repository.getCars()

    private fun validFields(
        profileCarName: String,
        carBrand: String,
        carModel: String,
        carYear: Int,
        carKilometers: Int,
        carLastOilChange: Date,
        carLastMaintenance: Date
    ): Boolean =
        profileCarName != "" && carBrand != "" && carModel != "" && carYear != null && carKilometers != null && carLastOilChange != null && carLastMaintenance != null

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