package com.autominder.autominder.ui.myCars.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.network.ApiResponse
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MyCarsViewModel(
    private val repository: MyCarsRepository,
    private val credentialsRepository: CredentialsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _status = MutableLiveData<OwnCarsUiStatus>(OwnCarsUiStatus.Resume)
    val status: MutableLiveData<OwnCarsUiStatus> = _status

    fun getCars() = repository.getCarsPage(8)

    fun setLoading(loading: Boolean) {
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
                    (application as AutoMinderApplication).credentialsRepository,

                    savedStateHandle
                ) as T
            }
        }
    }
}