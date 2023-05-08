package com.autominder.autominder.principalMenu.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.principalMenu.data.Alerts
import com.autominder.autominder.principalMenu.data.AlertsRepository

class PrincipalMenuViewModel(
    private val repository: AlertsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val alertsList = MutableLiveData<List<Alerts>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    init {
        fetchAlerts()
    }

    private fun fetchAlerts() {
        setLoading(true)
        repository.getAlerts().let { alerts ->
            alertsList.postValue(alerts)
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
                val application = checkNotNull(extras[APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                return PrincipalMenuViewModel(
                    (application as AutoMinderApplication).alertsRepository,
                    savedStateHandle
                ) as T
            }
        }
    }
}