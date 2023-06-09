package com.autominder.autominder.ui.myCars.ui

import com.autominder.autominder.data.database.models.CarModel

sealed class OwnCarsUiStatus {
        object Resume : OwnCarsUiStatus()
        data class Error(val exception: Exception) : OwnCarsUiStatus()
        data class ErrorWithMessage(val message: String) : OwnCarsUiStatus()
        data class Success(val cars: List<CarModel>) : OwnCarsUiStatus()
}