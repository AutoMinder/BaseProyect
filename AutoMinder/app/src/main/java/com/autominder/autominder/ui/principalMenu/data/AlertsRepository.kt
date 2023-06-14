package com.autominder.autominder.ui.principalMenu.data

class AlertsRepository(private var alertsdummy: List<Alerts>) {
    fun getAlerts() = alertsdummy
}