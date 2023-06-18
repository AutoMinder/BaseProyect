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
    val myCarsList = MutableLiveData<List<CarModel>>()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _status = MutableLiveData<OwnCarsUiStatus>(OwnCarsUiStatus.Resume)
    val status: MutableLiveData<OwnCarsUiStatus> = _status



    init {
        fetchMyCars()
    }

    fun fetchCarById(id: String): CarModel? {
        return repository.getCarById(id)
    }



    private fun fetchMyCars() {

        Log.d("MyCarsViewModel", "Estoy en fetchMyCars del VM de MyCars")

        viewModelScope.launch {
            setLoading(true)

            Log.d("MyCarsViewModel", "Voy a entrar a credentials: ")

            val response = credentialsRepository.ownCars()

            _status.postValue(
                    when (response) {
                        is ApiResponse.Error -> OwnCarsUiStatus.Error(response.exception)
                        is ApiResponse.ErrorWithMessage -> OwnCarsUiStatus.ErrorWithMessage(response.message)
                        is ApiResponse.Success -> OwnCarsUiStatus.Success(response.data.cars)
                    }

                )

            setLoading(false)

            Log.d("MyCarsViewModel", "Ya sali de credentials: ")

            myCarsList.value = repository.getMyCars()

        }
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                return MyCarsViewModel(
                    (application as AutoMinderApplication).myCarsRepository,
                    savedStateHandle,
                    (application as AutoMinderApplication).credentialsRepository
                ) as T
            }
        }
    }
}