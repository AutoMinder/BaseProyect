package com.autominder.autominder.ui.carinfo.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CarInfoViewModel(
    private val repository: MyCarsRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }



    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                CarInfoViewModel(app.myCarsRepository)
            }
        }
    }
}