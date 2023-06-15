package com.autominder.autominder.ui.login.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.data.network.ApiResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: CredentialsRepository) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnabled = MutableLiveData<Boolean>()
    val loginEnabled: LiveData<Boolean> = _loginEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token


    private val _status = MutableLiveData<LoginUiStatus>(LoginUiStatus.Resume)
    val status: MutableLiveData<LoginUiStatus> = _status


    fun login(email: String, password: String, token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _status.postValue(
                when (val response = repository.login(email, password)) {
                    is ApiResponse.Error -> LoginUiStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> LoginUiStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> LoginUiStatus.Success(response.data)
                }
            )
            repository.saveUserData(token)
            _isLoading.value = false
        }
    }

    /*
    suspend fun onLogin() {
        if (!validateData()) {
            _status.value = LoginUiStatus.ErrorWithMessage("Invalid data")
            return
        }
        Log.d("LoginViewModel", "onLogin: ${_email.value} ${_password.value}")
        login(_email.value!!, _password.value!!)
        repository.login(_email.value!!, _password.value!!)
    }*/

    private fun validateData(): Boolean {
        when {
            _email.value.isNullOrEmpty() -> return false
            _password.value.isNullOrEmpty() -> return false
        }
        return true
    }

    fun clearData() {
        _email.value = ""
        _password.value = ""
    }

    fun clearStatus() {
        _status.value = LoginUiStatus.Resume
    }

    private fun updateStatus(result: LoginUiStatus) {
        _status.value = result
    }

    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length >= 8

    private fun isValidEmail(email: String): Boolean = email.contains("@") && email.contains(".")
/*
    suspend fun OnLoginSelected(email: String, password: String) {
        login(email, password)

    } */

    fun updateToken(token: String) {
        _token.value = token
    }

    fun setStatus(status: LoginUiStatus) {
        _status.value = status
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                LoginViewModel(app.credentialsRepository)
            }
        }

    }
}