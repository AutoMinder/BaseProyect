package com.autominder.autominder.principalMenu.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autominder.autominder.principalMenu.data.Alerts
import com.autominder.autominder.principalMenu.data.alertsdummy

class PrincipalMenuViewModel():ViewModel() {
    private val _alerts = MutableLiveData<List<Alerts>>()
    val alerts = _alerts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun loadAlerts(){

    }
}