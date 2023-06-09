package com.autominder.autominder.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.RetrofitApplication
import com.autominder.autominder.database.repository.CredentialsRepository
import com.autominder.autominder.network.ApiResponse
import kotlinx.coroutines.delay
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


    private val _status =  MutableLiveData<LoginUiStatus>(LoginUiStatus.Resume)
    val status: MutableLiveData<LoginUiStatus>
        get() = _status

    private fun login(email: String, password: String){
        viewModelScope.launch {
            _status.postValue(
                when(val response = repository.login(email, password)){
                    is ApiResponse.Error -> LoginUiStatus.Error(response.exception)
                    is ApiResponse.ErrorWithMessage -> LoginUiStatus.ErrorWithMessage(response.message)
                    is ApiResponse.Success -> LoginUiStatus.Success(response.data)
                }
            )
        }
    }

    fun onLogin(){
        if(!validateData()) {
            _status.value = LoginUiStatus.ErrorWithMessage("Invalid data")
            return
        }

        login(email.value!!, password.value!!)

    }

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






    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length >= 8

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    suspend fun OnLoginSelected(email: String, password: String) {
        _isLoading.value = true
        delay(4000)
        _isLoading.value = false
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as RetrofitApplication
                LoginViewModel(app.credentialsRepository)
            }
        }

    }


}