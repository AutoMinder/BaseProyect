package com.autominder.autominder.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel() : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: MutableStateFlow<String> = _name
    private val _email = MutableStateFlow("")
    val email: MutableStateFlow<String> = _email
    private val _password = MutableStateFlow("")
    val password: MutableStateFlow<String> = _password
    private val _passwordConfirm = MutableStateFlow("")
    val passwordConfirm: MutableStateFlow<String> = _passwordConfirm
    private val _registerEnabled = MutableStateFlow(false)
    val registerEnabled: MutableStateFlow<Boolean> = _registerEnabled
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun onRegisterChange(name: String, email: String, password: String, passwordConfirm: String) {
        _name.value = name
        _email.value = email
        _password.value = password
        _passwordConfirm.value = passwordConfirm
        _registerEnabled.value =
            isValidEmail(email) && isValidPassword(password) && isValidPasswordConfirm(
                password,
                passwordConfirm
            )
    }

    private fun isValidPassword(password: String): Boolean = password.length >= 8
    private fun isValidPasswordConfirm(password: String, passwordConfirm: String): Boolean =
        password == passwordConfirm


}