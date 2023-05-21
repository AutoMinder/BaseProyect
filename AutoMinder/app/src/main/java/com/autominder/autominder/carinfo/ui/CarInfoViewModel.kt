package com.autominder.autominder.carinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.carinfo.data.CarMaintenanceData
import com.autominder.autominder.carinfo.data.CarMaintenanceRepository

class CarInfoViewModel(
    private val repository: CarMaintenanceRepository,
) : ViewModel() {
    val carInfoList = MutableLiveData<List<CarMaintenanceData>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _millage = MutableLiveData<Int>()
    val millage: LiveData<Int> = _millage
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    private val _id = MutableLiveData<Int>()
    val carId: LiveData<Int> = _id


    fun setCarId(id: Int) {
        _id.value = id
    }


    fun fetchCarMaintenanceInfoByCarId(carId: Int) {
        setLoading(true)
        repository.getCarMaintenanceByCarId(carId).let { car ->
            carInfoList.postValue(car)
            setLoading(false)
        }
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                CarInfoViewModel(app.CarMaintenanceRepository)
            }
        }
    }
}