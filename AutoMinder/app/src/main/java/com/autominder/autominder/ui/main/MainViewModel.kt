package com.autominder.autominder.ui.main

import androidx.lifecycle.ViewModel
import com.autominder.autominder.ui.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {
    private val _startDestination = MutableStateFlow(Destinations.WelcomeScreen1.route)
    val startDestination: StateFlow<String> = _startDestination

    fun setStartDestination(destination: String){
        _startDestination.value = destination
    }
}