package com.autominder.autominder.register

import android.text.Spannable.Factory
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.RetrofitApplication
import com.autominder.autominder.network.ApiResponse
import com.autominder.autominder.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.network.services.AutominderApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: CredentialsRepository) : ViewModel() {
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


    private val _status = MutableLiveData<RegisterUiStatus>(RegisterUiStatus.Resume)
    val status: MutableLiveData<RegisterUiStatus>
        get() = _status

    private fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _status.postValue(
                when (val response = repository.register(name, email, password)) {
                    is ApiResponse.Error -> RegisterUiStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> RegisterUiStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> RegisterUiStatus.Success
                }
            )
        }
    }

    fun onRegister() {
        if (!validateData()) {
            _status.value = RegisterUiStatus.ErrorWithMessage("Invalid data")
            return
        }

        register(name.value!!, email.value!!, password.value!!)

    }

    private fun validateData(): Boolean {
        when{
            name.value.isNullOrEmpty() -> return false
            email.value.isNullOrEmpty() -> return false
            password.value.isNullOrEmpty() -> return false
        }
        return true
    }

    fun clearStatus() {
        _status.value = RegisterUiStatus.Resume
    }

    fun clearData() {
        name.value = ""
        email.value = ""
        password.value = ""
        passwordConfirm.value = ""
    }

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


    companion object{
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as RetrofitApplication
                RegisterViewModel(app.credentialsRepository)
            }
        }
    }


}