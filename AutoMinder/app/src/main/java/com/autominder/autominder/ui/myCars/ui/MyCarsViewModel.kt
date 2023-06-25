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

/**
 * ViewModel class for managing cars data and loading state.
 *
 * @property repository The repository for handling cars data operations.
 */
class MyCarsViewModel(
    private val repository: MyCarsRepository,

) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /**
     * Fetches cars data using the repository.
     *
     * @return The result of fetching cars data
     */
    fun getCars() = repository.getCarsPage(8)

    /**
     * Updates the loading state of the ViewModel.
     *
     * @param loading The new loading state value.
     */
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }


    /**
     * Companion object providing a ViewModelProvider.Factory implementation for creating instances of MyCarsViewModel.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return MyCarsViewModel(
                    (application as AutoMinderApplication).myCarsRepository,
                ) as T
            }
        }
    }
}