package com.autominder.autominder.ui.carinfo.ui

sealed class CreateUiStatus{
    object Resume : CreateUiStatus()

    data class ErrorWithMessage(val message: String) : CreateUiStatus()

    data class Error(val exception: Exception) : CreateUiStatus()

    object Success : CreateUiStatus()
}
