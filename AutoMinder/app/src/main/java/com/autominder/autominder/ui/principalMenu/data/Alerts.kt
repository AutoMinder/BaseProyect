package com.autominder.autominder.ui.principalMenu.data

data class Alerts(
    val alertName: String,
    val alertDescription: String,
    val alertDate: String,
    val alertTime: String,
    val alertType: String,
    val alertStatus: String /*TODO Change this model according to the database*/
)
