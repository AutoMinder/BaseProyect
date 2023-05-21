package com.autominder.autominder.carinfo

import androidx.lifecycle.viewModelScope
import com.autominder.autominder.carinfo.ui.CarInfoViewModel
import com.autominder.autominder.myCars.data.CarDataModel
import kotlinx.coroutines.launch

class CarInfoInteractor(private val viewModel: CarInfoViewModel) {
    fun getCarInfoCoroutine(car: CarDataModel) {
        viewModel.viewModelScope.launch {
            viewModel.fetchCarMaintenanceInfoByCarId(car.id)
        }
    }
}