package com.autominder.autominder.principalMenu.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autominder.autominder.principalMenu.data.Alerts
import com.autominder.autominder.principalMenu.data.AlertsRepository
import com.autominder.autominder.principalMenu.data.alertsdummy

class PrincipalMenuViewModel(private val alertsRepository: AlertsRepository) : ViewModel() {
    val alertsList = MutableLiveData<List<Alerts>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    init {
        fetchAlerts()
    }
    private fun fetchAlerts() {
        setLoading(true)
        alertsRepository.getAlerts().observeForever {
            alertsList.postValue(it)
            setLoading(false)
        }
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}