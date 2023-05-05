package com.autominder.autominder.principalMenu.data

class AlertsRepository(private var alertsdummy: List<Alerts>) {
    fun getAlerts() = alertsdummy
}