package com.autominder.autominder.ui.principalMenu.ui


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.ui.principalMenu.data.Alerts
import com.autominder.autominder.ui.principalMenu.data.AlertsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrincipalMenuViewModel(
    private val repository: AlertsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _alertsList = MutableStateFlow<List<Alerts>>(emptyList())
    val alertsList: StateFlow<List<Alerts>> = _alertsList
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchAlerts()
    }

    private fun fetchAlerts() {
        viewModelScope.launch {
            delay(1000)
            try {
                setLoading(true)
                _alertsList.value = repository.getAlerts()
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
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AutoMinderApplication
                PrincipalMenuViewModel(app.alertsRepository, createSavedStateHandle())
            }
        }
    }
}