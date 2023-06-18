package com.autominder.autominder.ui.myCars.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.models_dummy.CarModel
import com.autominder.autominder.data.network.ApiResponse
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.data.network.dto.ownCars.OwnResponse
import com.autominder.autominder.ui.login.ui.LoginUiStatus
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MyCarsViewModel(
    private val repository: MyCarsRepository,
    private val savedStateHandle: SavedStateHandle,
    private val credentialsRepository: CredentialsRepository
) : ViewModel() {

    /*
    *   Variable declaration section
     */

    val myCarsList = MutableLiveData<List<CarModel>>()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _status = MutableLiveData<OwnCarsUiStatus>(OwnCarsUiStatus.Resume)
    val status: MutableLiveData<OwnCarsUiStatus> = _status

    //init is called when the class is instantiated
    init {
        fetchMyCars() //fetchMyCars is called when the class is instantiated
    }

    //fetchCarById searches for a car by its id
    fun fetchCarById(id: String): CarModel? {
        return repository.getCarById(id)
    }

    //fetchMyCars fetches the cars owned by the user
    private fun fetchMyCars() {


        Log.d("MyCarsViewModel", "Estoy en fetchMyCars() del VM de MyCars")

        viewModelScope.launch {

            setLoading(true) //Loading starts

            Log.d("MyCarsViewModel", "Voy a entrar a credentials: ")

            val response = credentialsRepository.ownCars() //response is the result of the request to the server

            _status.postValue( //postValue is used to update the value of a MutableLiveData object
                    when (response) {//Checking the type of response
                        is ApiResponse.Error -> OwnCarsUiStatus.Error(response.exception) //If the response is an error, the status is set to Error
                        is ApiResponse.ErrorWithMessage -> OwnCarsUiStatus.ErrorWithMessage(response.message) //If the response is an error with message, the status is set to ErrorWithMessage
                        is ApiResponse.Success -> OwnCarsUiStatus.Success(response.data.cars) //If the response is a success, the status is set to Success
                    }

                )

            setLoading(false) //Loading ends

            Log.d("MyCarsViewModel", "Ya sali de credentials, la respuesta ya está seteada.")

            myCarsList.value = repository.getMyCars()
        }
    }

    //Sets the loading state
    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }


    //Factory pattern to create the ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {//ViewModelProvider.Factory is an interface that creates ViewModels
            @Suppress("UNCHECKED_CAST")//Suppresses the unchecked cast warning
            override fun <T : ViewModel> create(
                modelClass: Class<T>,//Class of the ViewModel
                extras: CreationExtras//CreationExtras is a class that contains the extras passed to the ViewModelProvider
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle() //Creates a SavedStateHandle
                return MyCarsViewModel(//Returns a MyCarsViewModel
                    (application as AutoMinderApplication).myCarsRepository,//(application as AutoMinderApplication).myCarsRepository is the repository of the application
                    savedStateHandle,//savedStateHandle is the savedStateHandle of the application
                    (application as AutoMinderApplication).credentialsRepository //credentialsRepository is the credentialsRepository of the application
                ) as T
            }
        }
    }
}