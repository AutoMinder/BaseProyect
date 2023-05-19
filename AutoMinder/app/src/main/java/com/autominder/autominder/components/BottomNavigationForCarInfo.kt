package com.autominder.autominder.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.autominder.autominder.navigation.Destinations
//TODO add bottom bar
@Composable
fun BottomNavigationForCarInfo(
    navHostController: NavHostController,
    items: List<Destinations>,
    bottomBarState: Boolean,
) {

    val currentRoute = currentRoute(navHostController)
}