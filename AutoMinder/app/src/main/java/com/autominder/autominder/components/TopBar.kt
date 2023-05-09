package com.autominder.autominder.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.autominder.autominder.navigation.Destinations

@Composable
fun TopBar(navController: NavHostController) {
    androidx.compose.material.TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary,

        ) {
        Text(
            text = navController.currentBackStackEntryAsState().value?.destination?.route?.let {
                getTitleByRoute(
                    it
                )
            }
                ?: "AutoMinder",
            modifier = Modifier.padding(18.dp, 0.dp, 0.dp, 0.dp),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

fun getTitleByRoute(route: String): String {
    return when (route) {
        Destinations.MyCars.route -> "Mis Carros"
        Destinations.PrincipalMenu.route -> "AutoMinder"
        Destinations.UserInfo.route -> "Usuario"
        else -> "AutoMinder"
    }
}