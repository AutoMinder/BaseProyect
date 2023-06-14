package com.autominder.autominder.ui.myCars.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.data.models_dummy.CarModel
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MyCarsViewModel(
    private val repository: MyCarsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val myCarsList = MutableLiveData<List<CarModel>>()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMyCars()
    }

    fun fetchCarById(id: String): CarModel? {
        return repository.getCarById(id)
    }

    private fun fetchMyCars() {

        viewModelScope.launch {
            try {
                setLoading(true)
                delay(100)
                myCarsList.value = repository.getMyCars()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
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