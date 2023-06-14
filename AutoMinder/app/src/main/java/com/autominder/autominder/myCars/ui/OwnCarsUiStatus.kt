package com.autominder.autominder.myCars.ui

import com.autominder.autominder.data.models_dummy.CarModel

sealed class OwnCarsUiStatus {
        object Resume : OwnCarsUiStatus()
        data class Error(val exception: Exception) : OwnCarsUiStatus()
        data class ErrorWithMessage(val message: String) : OwnCarsUiStatus()
        data class Success(val cars: CarModel) : OwnCarsUiStatus()
}