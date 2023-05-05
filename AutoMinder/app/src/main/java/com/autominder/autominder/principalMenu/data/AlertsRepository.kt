package com.autominder.autominder.principalMenu.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AlertsRepository() {
    fun getAlerts(): LiveData<List<Alerts>> {
        return MutableLiveData(alertsdummy)
    }
}