package com.autominder.autominder.login.ui

sealed class LoginUiStatus {
    object Resume : LoginUiStatus()
    class Error(val exception: Exception) : LoginUiStatus()
    data class ErrorWithMessage(val message: String) : LoginUiStatus()
    data class Success(val token: String) : LoginUiStatus()
}