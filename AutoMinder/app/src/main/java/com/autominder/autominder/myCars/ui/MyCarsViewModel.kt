package com.autominder.autominder.myCars.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.myCars.data.CarDataModel
import com.autominder.autominder.myCars.data.MyCarsRepository
import com.autominder.autominder.principalMenu.ui.PrincipalMenuViewModel

class MyCarsViewModel(
    private val repository: MyCarsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val myCarsList = MutableLiveData<List<CarDataModel>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    init {
        fetchMyCars()
    }

    private fun fetchMyCars() {
        setLoading(true)
        repository.getMyCars().let { car ->
            myCarsList.postValue(car)
            setLoading(false)
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
                    savedStateHandle
                ) as T
            }
        }
    }
}