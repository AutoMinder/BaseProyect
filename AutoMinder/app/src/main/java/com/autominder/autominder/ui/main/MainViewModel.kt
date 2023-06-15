package com.autominder.autominder.ui.main

import androidx.lifecycle.ViewModel
import com.autominder.autominder.ui.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {
    var startDestination = MutableStateFlow(Destinations.Login.route)

    fun setStartDestination(destination: Destinations){
        startDestination.value = destination.route
    }
}