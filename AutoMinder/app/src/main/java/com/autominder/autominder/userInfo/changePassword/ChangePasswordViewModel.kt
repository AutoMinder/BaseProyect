package com.autominder.autominder.userInfo.changePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class ChangePasswordViewModel : ViewModel() {


    private val _actualPassword = MutableLiveData<String>()
    val actualPassword: LiveData<String> = _actualPassword

    private val _newPassword = MutableLiveData<String>()
    val newPassword: LiveData<String> = _newPassword

    private val _confirmNewPassword = MutableLiveData<String>()
    val confirmNewPassword: LiveData<String> = _confirmNewPassword

    private val _changePasswordEnable = MutableLiveData<Boolean>()
    val changePasswordEnable: LiveData<Boolean> = _changePasswordEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onPasswordChange(actualPassword: String, newPassword: String, confirmNewPassword: String) {
        _actualPassword.value = actualPassword
        _newPassword.value = newPassword
        _confirmNewPassword.value = confirmNewPassword

        _changePasswordEnable.value = isValidActualPassword(actualPassword) && isValidNewPassword(
            newPassword,
            confirmNewPassword
        )
    }


    /*
    TODO(): Debe validar que la clave ingresada es la misma de la cuenta
     */
    private fun isValidActualPassword(actualPassword: String): Boolean = actualPassword.length > 6

    /*
    Valida el formato de contraseñas y confirma que se haya confirmado la contraseña
    TODO(): Almacenar la nueva contraseña
     */
    private fun isValidNewPassword(newPassword: String, confirmNewPassword: String): Boolean =
        newPassword.length > 6 && newPassword == confirmNewPassword

    suspend fun onPasswordSelected() {
        _isLoading.value = true
        delay(2000)
        _isLoading.value = false
    }
}