package com.autominder.autominder

import android.app.Application
import com.autominder.autominder.carinfo.data.CarMaintenanceRepository
import com.autominder.autominder.carinfo.data.dummyCarMaintenanceData
import com.autominder.autominder.myCars.data.MyCarsRepository
import com.autominder.autominder.myCars.data.myCarsdummy
import com.autominder.autominder.principalMenu.data.AlertsRepository
import com.autominder.autominder.principalMenu.data.dummyAlerts

class AutoMinderApplication : Application() {
    val alertsRepository: AlertsRepository by lazy {
        AlertsRepository(dummyAlerts)
    }
    val myCarsRepository: MyCarsRepository by lazy {
        MyCarsRepository(myCarsdummy)
    }

    val CarMaintenanceRepository: CarMaintenanceRepository by lazy {
        CarMaintenanceRepository(dummyCarMaintenanceData)
    }
}